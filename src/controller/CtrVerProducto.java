package controller;

import FTP.ConexionFTP;
import conexion.Conexion;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
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

public class CtrVerProducto implements Initializable {

	@FXML
	private Button btnNuevoProducto;
	@FXML
	private TextField txtBuscar;
	@FXML
	private TableColumn<MdlProductos, String> colId;
	@FXML
	private TableColumn<MdlProductos, String> colNombre;
	@FXML
	private TableColumn<MdlProductos, String> colPrecio;
	@FXML
	private TableColumn<MdlProductos, String> colReferencia;
	@FXML
	private TableColumn<MdlProductos, String> colStock;
	@FXML
	private TableColumn<MdlProductos, String> colMarca;
	@FXML
	private TableColumn<MdlProductos, String> colCategoria;
	@FXML
	private TableColumn<MdlProductos, String> colDescuento;
	@FXML
	private TableColumn<MdlProductos, String> colIva;
	@FXML
	private TableColumn<MdlProductos, String> colEstado;
	@FXML
	private TableColumn<MdlProductos, String> colRegistro;
	@FXML
	private TableColumn<MdlProductos, HBox> colOpciones;
	@FXML
	private TableView<MdlProductos> tblProductos;

	ArrayList<String> listaImagenes = new ArrayList();
	ConexionFTP ftp = new ConexionFTP();

	private ObservableList<MdlProductos> productosList;

	Map<String, String> listaParametros = new HashMap<>();
	private int maxRowsToShow = 50;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		productosList = FXCollections.observableArrayList();
		cargarParametros();

		this.colId.setCellValueFactory(new PropertyValueFactory("id"));
		this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
		this.colPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
		this.colReferencia.setCellValueFactory(new PropertyValueFactory("referencia"));
		this.colStock.setCellValueFactory(new PropertyValueFactory("stock"));
		this.colMarca.setCellValueFactory(new PropertyValueFactory("marca"));
		this.colCategoria.setCellValueFactory(new PropertyValueFactory("categoria"));
		this.colDescuento.setCellValueFactory(new PropertyValueFactory("descuento"));
		this.colIva.setCellValueFactory(new PropertyValueFactory("iva"));
		this.colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
		this.colRegistro.setCellValueFactory(new PropertyValueFactory("fechaRegistro"));
		this.colOpciones.setCellValueFactory(new PropertyValueFactory("opciones"));

		cargarListas();
	}

	@FXML
	private void btnNuevoProductoEVent(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VstCrearProducto.fxml"));
			Parent root = loader.load();
			CtrCrearProducto ctrCrear = loader.getController();

			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		cargarListas();
	}

	public void cargarListas() {
		tblProductos.getItems().clear();
		productosList.clear();
		Conexion con = Conexion.getInstance();
		String sql = "SELECT * FROM products";
		ResultSet resultado = con.consultar(sql);
		try {
			while (resultado.next()) {
				MdlProductos producto = new MdlProductos();
				int id = resultado.getInt("id");
				producto.setId(id);
				producto.setNombre(resultado.getString("name"));
				producto.setPrecio(resultado.getInt("price"));
				producto.setReferencia(resultado.getString("code"));
				producto.setStock(resultado.getInt("stock"));
				producto.setMarca(this.listaParametros.get(resultado.getString("param_mark")));
				String imagen = resultado.getString("images");
				if (imagen != null) {
					listaImagenes = cargarLista(resultado.getString("images"), 2);
					imagen = resultado.getString("images");
					producto.setImagen(imagen);
				}
				final String img = imagen;
				producto.setDescripcion(resultado.getString("description"));
				producto.setCategoria(this.listaParametros.get(resultado.getString("param_category")));
				producto.setIva(resultado.getInt("tax"));
				producto.setDescuento(resultado.getInt("discount"));
				producto.setEstado(listaParametros.get(resultado.getString("param_state")));
				producto.setFechaRegistro(resultado.getString("created_at"));

				Button btnActualizar = new Button("Actualizar");
				btnActualizar.setStyle("-fx-font-weight: bold;");
				btnActualizar.getStylesheets().add("/style/Style.css");
				btnActualizar.getStyleClass().setAll("btnWarning");
				btnActualizar.setPrefHeight(30);
				btnActualizar.setPrefWidth(100);

				btnActualizar.setOnAction(event -> {
					actualizarProducto(id);
				});

				HBox opciones = new HBox();
				opciones.setAlignment(Pos.CENTER);
				opciones.setSpacing(5);
				opciones.getChildren().addAll(btnActualizar);

				producto.setOpciones(opciones);

				productosList.add(producto);
			}
			ObservableList<MdlProductos> limitedItems = FXCollections.observableArrayList();
			limitedItems.addAll(productosList.subList(0, Math.min(this.maxRowsToShow, productosList.size())));
			tblProductos.setItems(limitedItems);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		con.cerrarConexion();

	}

	private void habilitar(MdlProductos model) {
		Conexion con = Conexion.getInstance();
		String sql = "UPDATE address SET param_state = '5' WHERE id = " + model.getId();
		con.ejecutar(sql);
		con.cerrarConexion();
		cargarListas();
	}

	void cargarParametros() {
		Conexion con = Conexion.getInstance();
		String sql = "SELECT * FROM params WHERE paramtype_id IN (2,10,12)";
		ResultSet rs = con.consultar(sql);
		try {
			while (rs.next()) {
				listaParametros.put(rs.getString("id"), rs.getString("name"));
			}
		} catch (SQLException e) {
			System.out.println("error");
		}
	}

	private ArrayList<String> cargarLista(String datos, int tipo) {
		ArrayList<String> lista = new ArrayList<>(Arrays.asList(datos.split(":")));
		return lista;
	}

	public void actualizarProducto(int id) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VwActualizarProducto.fxml"));
			Parent root = loader.load();
			CtrActualizarProducto ctrActualizar = loader.getController();
			ctrActualizar.initAttributtes(id);

			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		} catch (Exception e) {
			System.out.println(e);
		}
		cargarListas();
	}

	private void eliminarProducto(int id, String imagen) {
		Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
		alertaConfirmacion.setTitle("Confirmación");
		alertaConfirmacion.setHeaderText(null);
		alertaConfirmacion.setContentText("¿Estás seguro de que quieres eliminar este producto?");
		Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();
		Conexion con = Conexion.getInstance();
		if (resultado.isPresent() && resultado.get() == ButtonType.OK) {

			PreparedStatement sentencia = null;
			try {
				sentencia = con.getConnection().prepareStatement("UPDATE productos SET param_state = '6' WHERE id  = ?");
				sentencia.setInt(1, id);
				int listas = sentencia.executeUpdate();
				con.cerrarConexion();
				if (listas > 0) {
					eliminarImagenes();
					Alert alertaInformacion = new Alert(Alert.AlertType.INFORMATION);
					alertaInformacion.setTitle("Eliminación exitosa");
					alertaInformacion.setHeaderText(null);
					alertaInformacion.setContentText("El producto se eliminó correctamente.");
					alertaInformacion.showAndWait();
				}
			} catch (SQLException ex) {
				Alert alertaError = new Alert(Alert.AlertType.ERROR);
				alertaError.setTitle("Error");
				alertaError.setHeaderText("Error al eliminar.");
				alertaError.setContentText(ex.getMessage());
				alertaError.showAndWait();
			}
		}
		cargarListas();
	}

	private void eliminarImagenes() {
		for (int i = 0; i < listaImagenes.size(); i++) {
			ftp.eliminarImg("productos", listaImagenes.get(i));
			listaImagenes.remove(listaImagenes.get(i));
		}
	}

	void buscar(String producto) {
		ObservableList<MdlProductos> listaBuscar = FXCollections.observableArrayList();
		for (int i = 0; i < productosList.size(); i++) {
			if (productosList.get(i).getNombre().toLowerCase().contains(producto.toLowerCase())
				|| productosList.get(i).getReferencia().toLowerCase().contains(producto.toLowerCase())) {
				listaBuscar.add(productosList.get(i));

			}
		}
		ObservableList<MdlProductos> limitedItems = FXCollections.observableArrayList();
		limitedItems.addAll(listaBuscar.subList(0, Math.min(maxRowsToShow, listaBuscar.size())));
		tblProductos.setItems(limitedItems);
	}

	@FXML
	private void eventKey(KeyEvent event) {
		Object evt = event.getSource();
		if (evt.equals(txtBuscar)) {

			if (productosList.isEmpty()) {
				Label lb = new Label("Ningún dato encontrado.");
				tblProductos.setPlaceholder(lb);
			} else {
				buscar(txtBuscar.getText());
			}
			if (txtBuscar.getText().equals("")) {
				ObservableList<MdlProductos> limitedItems = FXCollections.observableArrayList();
				limitedItems.addAll(productosList.subList(0, Math.min(maxRowsToShow, productosList.size())));
				tblProductos.setItems(limitedItems);
			}
		}
	}
}
