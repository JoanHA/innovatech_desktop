/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;
// param_forign

import conexion.Conexion;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MdlParametros;

/**
 * FXML Controller class
 *
 * @author Dragnell
 */
public class CtrModificarParametros implements Initializable {

	@FXML
	private ComboBox<String> cbxTipoParametros;
	@FXML
	private TextField txtParametro;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnModificar;

	private ObservableList<MdlParametros> listParametros;

	private ObservableList<String> parametrosList = FXCollections.observableArrayList();
	private ObservableList<String> estadosList = FXCollections.observableArrayList();
	private ObservableList<String> foreignList = FXCollections.observableArrayList();

	Map<String, String> idsTipos = new HashMap<>();
	Map<String, String> idsEstados = new HashMap<>();
	Map<String, String> idsForeign = new HashMap<>();

	private String id;
	@FXML
	private ComboBox<String> cbxEstado;
	@FXML
	private VBox vboxForeign;
	@FXML
	private ComboBox<String> cbxForeign;
	@FXML
	private HBox hboxPertenece;
	@FXML
	private VBox vboxEstado;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		cargarTipos();
		cargarEstados();
		cbxTipoParametros.setDisable(true);
	}

	@FXML
	private void clickCbxTipoParametro(ActionEvent event) {
	}

	@FXML
	private void clickCancelar(ActionEvent event) {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void clickModificar(ActionEvent event) {
		MdlParametros model = new MdlParametros();
		model.setNombre(txtParametro.getText());
		model.setNombreTipo(cbxTipoParametros.getValue());
		Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
		alertaConfirmacion.setTitle("Confirmación");
		alertaConfirmacion.setHeaderText(null);
		alertaConfirmacion.setContentText("¿Estás seguro de que quieres actualizar este parámetro?");
		Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();
		if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
			if (validarVacios()) {
				if (!buscarRepetido(txtParametro.getText())) {

					Instant instant = Instant.now();
					Timestamp timestamp = Timestamp.from(instant);

					String palabra = txtParametro.getText();
					String nombre = palabra.substring(0, 1).toUpperCase() + palabra.substring(1).toLowerCase();

					Conexion con = Conexion.getInstance();
					String sql = "UPDATE params SET name = '" + nombre + "', "
						+ "paramtype_id = " + this.idsTipos.get(cbxTipoParametros.getValue()) + ", updated_at = '" + timestamp + "', "
						+ "param_state = " + this.idsEstados.get(cbxEstado.getValue()) + ", param_foreign = " + idsForeign.get(cbxForeign.getValue())
						+ " WHERE id = " + id + "";
					if (con.ejecutar(sql)) {
						con.cerrarConexion();
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setTitle("Información");
						alert.setContentText("Modificado exitosamente");
						listParametros.add(model);
						alert.showAndWait();
						Stage stage = (Stage) btnCancelar.getScene().getWindow();
						stage.close();
					} else {
						con.cerrarConexion();
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setHeaderText(null);
						alert.setTitle("Error");
						alert.setContentText("Error al modificar parámetro.");
						alert.showAndWait();
					}
				} else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setTitle("Error");
					alert.setContentText("Este parámetro ya existe.");
					alert.showAndWait();
				}
			}
		}
	}

	public boolean buscarRepetido(String cadena) {
		MdlParametros modelo;
		for (int i = 0; i < listParametros.toArray().length; i++) {
			modelo = listParametros.get(i);
			if (modelo.getNombre().equalsIgnoreCase(cadena)
				&& modelo.getNombreTipo().equalsIgnoreCase(this.cbxTipoParametros.getValue())) {
				return true;
			}
		}
		return false;
	}

	boolean validarVacios() {
		boolean sw = true;
		String cbx = "--seleccionar--";
		StringBuilder vacios = new StringBuilder();
		if (cbxTipoParametros.getValue().equals(cbx)) {
			vacios.append("Debes elegir un tipo de parámetro.\n");
			sw = false;
		}
		if (txtParametro.getText().equals("")) {
			vacios.append("El parámetro no puede estar vació.");
			sw = false;
		}
		if (!sw) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText(vacios.toString());
			alert.show();
		}
		return sw;
	}

	void cargarTipos() {
		parametrosList.clear();
		Conexion con = Conexion.getInstance();
		String sql = "SELECT name,id FROM param_types";
		ResultSet rs = con.consultar(sql);
		try {
			while (rs.next()) {
				// cargo la lista de departamentos
				String clave = null, valor = null;
				clave = rs.getString("name");
				valor = rs.getString("id");
				this.idsTipos.put(clave, valor);
				parametrosList.add(rs.getString("name"));
			}
			//Añado la lista al comboBox de paises.
			this.cbxTipoParametros.setItems(parametrosList);
		} catch (SQLException e) {
			System.out.println("Error en cargar parámetros: " + e.getMessage());
		}
		con.cerrarConexion();
	}

	void cargarEstados() {
		this.estadosList.clear();
		Conexion con = Conexion.getInstance();
		String sql = "SELECT name,id FROM params WHERE paramtype_id = 2";
		ResultSet rs = con.consultar(sql);
		try {
			while (rs.next()) {
				// cargo la lista de departamentos
				String clave = null, valor = null;
				clave = rs.getString("name");
				valor = rs.getString("id");
				this.idsEstados.put(clave, valor);
				estadosList.add(rs.getString("name"));
			}
			this.cbxEstado.setItems(estadosList);
		} catch (SQLException e) {
			System.out.println("Error en cargar estados: " + e.getMessage());
		}
		con.cerrarConexion();
	}

	void cargarForeign(String eleccion) {
		this.foreignList.clear();
		Conexion con = Conexion.getInstance();
		String sql = "SELECT name,id FROM params WHERE paramtype_id = " + eleccion;
		ResultSet rs = con.consultar(sql);
		try {
			while (rs.next()) {
				// cargo la lista de departamentos
				String clave = null, valor = null;
				clave = rs.getString("name");
				valor = rs.getString("id");
				this.idsForeign.put(clave, valor);
				this.foreignList.add(rs.getString("name"));
			}
			this.cbxForeign.setItems(foreignList);
		} catch (SQLException e) {
			System.out.println("Error en cargar estados: " + e.getMessage());
		}
		con.cerrarConexion();
	}

	@FXML
	private void clickCbxForeign(ActionEvent event) {
	}

	String nombrePertenece(String foreign) {
		Conexion con = Conexion.getInstance();
		String nombre = null;
		String sql = "SELECT name FROM params WHERE id = " + foreign;
		ResultSet rs = con.consultar(sql);
		try {
			while (rs.next()) {
				nombre = rs.getString("name");
			}
		} catch (SQLException e) {

		}
		con.cerrarConexion();
		return nombre;
	}

	void initAttributtes(MdlParametros model, ObservableList<MdlParametros> listParametros) {
		cbxTipoParametros.setValue(model.getNombreTipo());
		txtParametro.setText(model.getNombre());
		this.id = model.getId();
		this.listParametros = listParametros;

		if (model.getEstado().equals("5")) {
			cbxEstado.setValue("Activo");
		} else if (model.getEstado().equals("6")) {
			cbxEstado.setValue("Inactivo");
		}

		switch (model.getTipo_parametro()) {
		case "6":
			cargarForeign("5");
			cbxForeign.setValue(nombrePertenece(model.getForeign()));
			break;
		case "7":
			cargarForeign("6");
			cbxForeign.setValue(nombrePertenece(model.getForeign()));
			break;
		default:
			this.vboxForeign.getChildren().remove(this.cbxForeign);
			this.hboxPertenece.getChildren().remove(this.vboxForeign);
			break;
		}
	}
}
