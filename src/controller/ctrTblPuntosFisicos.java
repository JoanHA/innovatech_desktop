/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import conexion.Conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import model.MdlDirecciones;

/**
 * FXML Controller class
 *
 * @author Dragnell
 */
public class ctrTblPuntosFisicos implements Initializable {

	// almacena los datos de el modelo para cargar en la tabla
	private ObservableList<MdlDirecciones> listUbicaciones;

	private ObservableList<String> departamentosList = FXCollections.observableArrayList();
	private ObservableList<String> paisList = FXCollections.observableArrayList();
	@FXML
	private TableView<MdlDirecciones> tblUbicaciones;
	@FXML
	private TableColumn colMunicipio;
	@FXML
	private TableColumn colOpciones;
	@FXML
	private Button btnNuevo;
	@FXML
	private TextField txtBuscar;
	@FXML
	private TableColumn colPiso;
	@FXML
	private TableColumn colBarrio;
	@FXML
	private TableColumn colDireccion;
	@FXML
	private TableColumn colEstado;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		listUbicaciones = FXCollections.observableArrayList();

		this.colBarrio.setCellValueFactory(new PropertyValueFactory("barrio"));
		this.colDireccion.setCellValueFactory(new PropertyValueFactory("direccion"));
		this.colMunicipio.setCellValueFactory(new PropertyValueFactory("municipio"));
		this.colPiso.setCellValueFactory(new PropertyValueFactory("piso"));
		this.colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
		this.colOpciones.setCellValueFactory(new PropertyValueFactory("opciones"));
		cargarTabla();
	}

	String consultarDepartamento(String id) {
		Conexion con = Conexion.getInstance();
		String sql = "SELECT * FROM params "
			+ "WHERE id = " + id;

		ResultSet rs = con.consultar(sql);
		try {
			while (rs.next()) {
				return rs.getString("name");
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		con.cerrarConexion();
		return "0";
	}

	/**
	 * Consulta los datos, los cuales se convierten en modelos para llenar la
	 * tabla.
	 */
	void cargarTabla() {
		listUbicaciones.clear();
		Conexion co = Conexion.getInstance();
		String sql = "SELECT * FROM address "
			+ "JOIN params ON params.id = param_city "
			+ "JOIN users ON users.id = user_id "
			+ "WHERE param_rol = 2";
		ResultSet resultado = co.consultar(sql);
		try {
			while (resultado.next()) {
				MdlDirecciones modelo = new MdlDirecciones();

				modelo.setId(resultado.getString("address.id"));
				modelo.setBarrio(resultado.getString("hood"));
				modelo.setDireccion(resultado.getString("address"));
				modelo.setPiso(resultado.getString("floor"));
				modelo.setMunicipio(resultado.getString("name"));
				String departamento = resultado.getString("param_foreign");
				modelo.setDepartamento(consultarDepartamento(departamento));
				modelo.setUsuario(resultado.getString("users.id"));
				String estado = resultado.getString("address.param_state");
				if (estado.equals("5")) {
					modelo.setEstado("Activo");
				} else {
					modelo.setEstado("Inactivo");
				}

				// Creo dos botones
				Button btnModificar = new Button("Actualizar");

				// Creo acciones para cada uno de ellos.
				btnModificar.setOnAction((event) -> {
					Modificar(modelo);
				});
				btnModificar.setPrefSize(100, 30);
				btnModificar.setStyle("-fx-font-weight: bold;");
				btnModificar.getStylesheets().add("/style/Style.css");
				btnModificar.getStyleClass().setAll("btnWarning");

				HBox opciones = new HBox(btnModificar);
				opciones.setAlignment(Pos.CENTER);
				opciones.setSpacing(5);

				modelo.setOpciones(opciones);

				// creo un hbox le paso los dos botones y los cargo al modelo.
				// lleno el observable
				this.listUbicaciones.add(modelo);
			}
			// añado el observable a la tabla.
			this.tblUbicaciones.setItems(listUbicaciones);
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}
		co.cerrarConexion();
	}

	private void habilitar(MdlDirecciones model) {
		Conexion con = Conexion.getInstance();
		String sql = "UPDATE address SET param_state = '5' WHERE id = " + model.getId();
		con.ejecutar(sql);
		con.cerrarConexion();
		cargarTabla();
	}

	/**
	 * Eliminar una ciudad.
	 *
	 * @param modelo Recibe el modelo el cual debe eliminar.
	 */
	void Eliminar(MdlDirecciones modelo) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Confirmación");
		alert.setContentText("¿Estas seguro de eliminar este punto físico?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Conexion con = Conexion.getInstance();
			String sql;
			sql = "DELETE FROM address WHERE id = " + modelo.getId();
			if (con.ejecutar(sql)) {
				Alert alerta = new Alert(Alert.AlertType.INFORMATION);
				alerta.setHeaderText(null);
				alerta.setTitle("Información");
				alerta.setContentText("Punto físico  eliminado.");
				alerta.showAndWait();
			} else {
				sql = "UPDATE address SET param_state = '6' WHERE id = " + modelo.getId();
				con.ejecutar(sql);
				Alert alerta = new Alert(Alert.AlertType.INFORMATION);
				alerta.setHeaderText(null);
				alerta.setTitle("Información");
				alerta.setContentText(
					"Debido a que este punto físico ya fue usado por los usuarios.\n Su estado cambio a inactivo");
				alerta.showAndWait();
			}
			if (txtBuscar.getText().isEmpty()) {
				// Remuevo todo lo de la lista y refresco la tabla.
				listUbicaciones.clear();
				tblUbicaciones.refresh();

				// Cargo el comboBox de y la tabla.
				cargarTabla();
			}
			con.cerrarConexion();
		}
	}

	private void Modificar(MdlDirecciones model) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/vwModificarPuntoFisico.fxml"));
			Parent root = loader.load();

			ctrModificarPuntoFisico controlador = loader.getController();
			controlador.initAttributtes(model);

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.showAndWait();
			cargarTabla();
			txtBuscar.setText("");
		} catch (IOException e) {
		}
	}

	/**
	 * Consulta los datos de una tabla y busca su id para retornarlo.
	 *
	 * @param buscar Nombre de la columna donde se almacenan los id.
	 * @param tabla Tabla para realizar la consulta.
	 * @param columna Nombre de la columna para filtrar con where.
	 * @param condicion Valor a buscar en la columna del where.
	 * @return Retorna el id.
	 */
	public int id(String buscar, String tabla, String columna, String condicion) {
		Conexion co = Conexion.getInstance();
		int id = 0;
		String sql = "SELECT * FROM " + tabla + " WHERE " + columna + " = '" + condicion + "'";
		ResultSet result = co.consultar(sql);
		try {
			while (result.next()) {
				id = result.getInt(buscar);
			}
		} catch (SQLException e) {
			System.out.println("Error en recolectar id: " + e);
		}
		co.cerrarConexion();
		return id;
	}

	@FXML
	private void clickNuevo(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/vwCrearPuntoFisico.fxml"));
			Parent root = loader.load();

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.showAndWait();
			if (txtBuscar.getText().equals("")) {
				cargarTabla();
			} else {
				buscar(txtBuscar.getText());
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	void buscar(String municipio) {
		ObservableList<MdlDirecciones> listaBuscar = FXCollections.observableArrayList();
		for (int i = 0; i < listUbicaciones.size(); i++) {
			if (listUbicaciones.get(i).getMunicipio().toLowerCase().contains(municipio.toLowerCase())) {
				listaBuscar.add(listUbicaciones.get(i));
			}
		}
		this.tblUbicaciones.setItems(listaBuscar);
	}

	@FXML
	private void eventKey(KeyEvent event) {
		Object evt = event.getSource();
		if (evt.equals(txtBuscar)) {

			if (listUbicaciones.isEmpty()) {
				Label lb = new Label("Ningún dato encontrado");
				tblUbicaciones.setPlaceholder(lb);
			} else {
				buscar(txtBuscar.getText());

			}
			if (txtBuscar.getText().equals("")) {
				tblUbicaciones.setItems(listUbicaciones);
			}
		}
	}
}
