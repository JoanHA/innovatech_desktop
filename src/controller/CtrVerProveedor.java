package controller;

import conexion.Conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.MdlProductos;
import model.MdlProveedores;

public class CtrVerProveedor implements Initializable {

	@FXML
	private TextField txtBuscar;
	@FXML
	private Button btnNuevo;
	@FXML
	private TableColumn<MdlProveedores, String> colId;
	@FXML
	private TableColumn<MdlProveedores, String> colNit;
	@FXML
	private TableColumn<MdlProveedores, String> colNombreLegal;
	@FXML
	private TableColumn<MdlProveedores, String> colNombreComercial;
	@FXML
	private TableColumn<MdlProveedores, String> colCorreo;
	@FXML
	private TableColumn<MdlProveedores, String> colCelular;
	@FXML
	private TableColumn<MdlProveedores, String> colEstado;
	@FXML
	private TableColumn<MdlProveedores, String> colRegistro;
	@FXML
	private TableColumn<MdlProveedores, HBox> colOpciones;
	@FXML
	private TableView<MdlProveedores> tblProveedores;

	private ObservableList<MdlProveedores> proveedoresList;

	int maxRowsToShow = 50;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		proveedoresList = FXCollections.observableArrayList();

		this.colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.colNit.setCellValueFactory(new PropertyValueFactory<>("nit"));
		this.colNombreLegal.setCellValueFactory(new PropertyValueFactory<>("nombreLegal"));
		this.colNombreComercial.setCellValueFactory(new PropertyValueFactory<>("nombreComercial"));
		this.colCorreo.setCellValueFactory(new PropertyValueFactory<>("email"));
		this.colCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));
		this.colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
		this.colRegistro.setCellValueFactory(new PropertyValueFactory<>("fechaRegistro"));
		this.colOpciones.setCellValueFactory(new PropertyValueFactory<>("opciones"));

		cargarListas();
	}

	@FXML
	private void btnNuevoProveedorEVent(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VstCrearProveedor.fxml"));
			Parent root = loader.load();
			CtrCrearProveedor ctrCrear = loader.getController();

			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		cargarListas();
	}

	public void cargarListas() {
		tblProveedores.getItems().clear();
		proveedoresList.clear();
		MdlProveedores proveedor;
		Conexion con = Conexion.getInstance();

		Statement sentencia = null;
		ResultSet rs = null;
		try {
			sentencia = con.getConnection().createStatement();
			rs = sentencia.executeQuery("SELECT * FROM providers");

			while (rs.next()) {
				proveedor = new MdlProveedores();
				int id = rs.getInt("id");
				proveedor.setId(id);
				proveedor.setNit(rs.getString("nit"));
				proveedor.setNombreLegal(rs.getString("legal_name"));
				proveedor.setNombreComercial(rs.getString("commercial_name"));
				proveedor.setCelular(rs.getString("phone"));
				proveedor.setEmail(rs.getString("email"));
				proveedor.setEstado(getEstado(rs.getString("param_state")));
				proveedor.setFechaRegistro(rs.getString("created_at"));
				proveedor.setFechaModificacion(rs.getString("updated_at"));

				Button btnActualizar = new Button("Actualizar");
				btnActualizar.setStyle("-fx-font-weight: bold;");
				btnActualizar.getStylesheets().add("/style/Style.css");
				btnActualizar.getStyleClass().setAll("btnWarning");
				btnActualizar.setPrefHeight(30);
				btnActualizar.setPrefWidth(100);

				btnActualizar.setOnAction(e -> {
					actualizarProveedor(id);
				});

				HBox opciones = new HBox();
				opciones.setSpacing(5);
				opciones.setAlignment(Pos.CENTER);
				opciones.getChildren().addAll(btnActualizar);
				proveedor.setOpciones(opciones);
				proveedoresList.add(proveedor);
			}
			
			ObservableList<MdlProveedores> limitedItems = FXCollections.observableArrayList();
			limitedItems.addAll(proveedoresList.subList(0, Math.min(maxRowsToShow, proveedoresList.size())));
			tblProveedores.setItems(limitedItems);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		con.cerrarConexion();
	}

	private String getEstado(String estado) {
		if (estado.equals("5")) {
			estado = "Activo";
		} else {
			estado = "Inactivo";
		}

		return estado;
	}

	private void actualizarProveedor(int id) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VstActualizarProveedor.fxml"));
			Parent root = loader.load();
			CtrActualizarProveedor ctrActualizar = loader.getController();
			ctrActualizar.setProveedor(id);
			ctrActualizar.cargarDatos();

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		cargarListas();
	}

	private void eliminar(int id) {
		Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
		alertaConfirmacion.setTitle("Confirmación");
		alertaConfirmacion.setHeaderText(null);
		alertaConfirmacion.setContentText("¿Estás seguro de que quieres eliminar este proveedor?");
		Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();

		int lista = 0;

		if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
			Conexion con = Conexion.getInstance();
			PreparedStatement sentencia = null;
			try {
				sentencia = con.getConnection().prepareStatement("DELETE FROM providers WHERE id = ?");
				sentencia.setInt(1, id);
				lista = sentencia.executeUpdate();
			} catch (SQLException ex) {
				Alert alertaError = new Alert(Alert.AlertType.ERROR);
				alertaError.setTitle("Error");
				alertaError.setHeaderText("Error al eliminar proveedor");
				alertaError.setContentText(ex.getMessage());
				alertaError.showAndWait();
			}

			if (lista > 0) {
				Alert alertaInformacion = new Alert(Alert.AlertType.INFORMATION);
				alertaInformacion.setTitle("Eliminación exitosa");
				alertaInformacion.setHeaderText(null);
				alertaInformacion.setContentText("El proveedor se eliminó correctamente.");
				alertaInformacion.showAndWait();
			}
			con.cerrarConexion();
		}
		cargarListas();
	}

	void buscar(String proveedor) {
		ObservableList<MdlProveedores> listaBuscar = FXCollections.observableArrayList();
		for (int i = 0; i < proveedoresList.size(); i++) {
			if (proveedoresList.get(i).getNombreComercial().toLowerCase().contains(proveedor.toLowerCase())
				|| proveedoresList.get(i).getEmail().toLowerCase().contains(proveedor.toLowerCase())
				|| proveedoresList.get(i).getNit().toLowerCase().contains(proveedor.toLowerCase())) {
				listaBuscar.add(proveedoresList.get(i));
			}
		}
		ObservableList<MdlProveedores> limitedItems = FXCollections.observableArrayList();
		limitedItems.addAll(listaBuscar.subList(0, Math.min(maxRowsToShow, listaBuscar.size())));
		tblProveedores.setItems(limitedItems);
	}

	@FXML
	private void eventKey(KeyEvent event) {
		Object evt = event.getSource();
		if (evt.equals(txtBuscar)) {

			if (proveedoresList.isEmpty()) {
				Label lb = new Label("Ningún dato encontrado");
				tblProveedores.setPlaceholder(lb);
			} else {
				buscar(txtBuscar.getText());
			}
			if (txtBuscar.getText().equals("")) {
				ObservableList<MdlProveedores> limitedItems = FXCollections.observableArrayList();
				limitedItems.addAll(proveedoresList.subList(0, Math.min(maxRowsToShow, proveedoresList.size())));
				tblProveedores.setItems(limitedItems);
			}
		}
	}
}
