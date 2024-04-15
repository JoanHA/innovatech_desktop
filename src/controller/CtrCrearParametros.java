/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import conexion.Conexion;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MdlParametros;

/**
 * FXML Controller class
 *
 * @author SENA
 */
public class CtrCrearParametros implements Initializable {

	@FXML
	private ComboBox<String> cbxTipoParametro;
	@FXML
	private TextField txtParametro;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnAceptar;

	private ObservableList<MdlParametros> listParametros;
	private ObservableList<String> parametrosList = FXCollections.observableArrayList();
	private ObservableList<String> estadosList = FXCollections.observableArrayList();
	private ObservableList<String> foreignList = FXCollections.observableArrayList();

	Map<String, String> idsTipos = new HashMap<>();
	Map<String, String> idsEstados = new HashMap<>();
	Map<String, String> idsForeign = new HashMap<>();

	@FXML
	private ComboBox<String> cbxForeign;
	@FXML
	private ComboBox<String> cbxEstado;
	@FXML
	private VBox vboxForeign;

	private String eleccionPertenencia;
	@FXML
	private HBox hboxPertenece;
	@FXML
	private VBox vboxEstado;

	/**
	 * Initializes the controller class.
	 */
	boolean vboxExiste;
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		cbxTipoParametro.setValue("--Seleccionar--");
		cbxEstado.setValue("--Seleccionar--");
		cbxForeign.setValue("--Seleccionar--");
		vboxExiste=false;
		this.vboxForeign.getChildren().remove(this.cbxForeign);
		this.hboxPertenece.getChildren().remove(this.vboxForeign);
		cargarEstados();
		cargarTipos();
	}

	@FXML
	private void clickCbxTipoParametro(ActionEvent event) {
		this.eleccionPertenencia = cbxTipoParametro.getValue();
		switch (eleccionPertenencia) {
		case "Departamentos":
			vboxExiste = true;
			this.vboxForeign.getChildren().remove(this.cbxForeign);
			this.hboxPertenece.getChildren().remove(this.vboxForeign);
			this.hboxPertenece.getChildren().add(this.hboxPertenece.getChildren().indexOf(vboxEstado) + 1, this.vboxForeign);
			this.vboxForeign.getChildren().add(this.cbxForeign);
			this.eleccionPertenencia = this.idsTipos.get("Paises");
			cargarForeign();
			break;
		case "Ciudades":
			vboxExiste = true;
			this.vboxForeign.getChildren().remove(this.cbxForeign);
			this.hboxPertenece.getChildren().remove(this.vboxForeign);
			this.hboxPertenece.getChildren().add(this.hboxPertenece.getChildren().indexOf(vboxEstado) + 1, this.vboxForeign);
			this.vboxForeign.getChildren().add(this.cbxForeign);
			this.eleccionPertenencia = this.idsTipos.get("Departamentos");
			cargarForeign();
			break;
		default:
//			vboxExiste = false;
			this.vboxForeign.getChildren().remove(this.cbxForeign);
			this.hboxPertenece.getChildren().remove(this.vboxForeign);
			break;
		}
	}

	@FXML
	private void clickCancelar(ActionEvent event) {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void clickAgregar(ActionEvent event) {
		CtrParametros controller = new CtrParametros();
		MdlParametros model = new MdlParametros();
		if (validarVacios()) {
			String palabra = txtParametro.getText();
			String nombre = palabra.substring(0, 1).toUpperCase() + palabra.substring(1).toLowerCase();
			model.setNombre(nombre);
			model.setNombreTipo(this.idsTipos.get(cbxTipoParametro.getValue()));

			model.setForeign(this.idsForeign.get(cbxForeign.getValue()));
			model.setEstado(this.idsEstados.get(cbxEstado.getValue()));
			if (!buscarRepetido(txtParametro.getText())) {
				if (!validarRango()) {
					if (controller.guardarParametro(model)) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setTitle("Información");
						alert.setContentText("Creado exitosamente");
						listParametros.add(model);
						alert.showAndWait();
						limpiar();
					} else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setHeaderText(null);
						alert.setTitle("Error");
						alert.setContentText("Error al crear parámetro.");
						alert.showAndWait();
					}
				} else {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setHeaderText(null);
					alert.setTitle("Advertencia.");
					alert.setContentText("Se a alcanzo el máximo de parámetros para:\n" + cbxTipoParametro.getValue());
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

	void limpiar() {
		txtParametro.setText("");
	}

	boolean validarRango() {
		int actual = 0, rango_max = 1;
		Conexion con = Conexion.getInstance();
		String slq = "SELECT param_types.range_max, MAX(params.id) as actual FROM params "
			+ "JOIN param_types ON param_types.id = params.paramtype_id "
			+ "WHERE param_types.id = " + this.idsTipos.get(cbxTipoParametro.getValue());
		ResultSet rs = con.consultar(slq);
		try {
			while (rs.next()) {
				actual = rs.getInt("actual");
				rango_max = rs.getInt("range_max");
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		} finally {
			con.cerrarConexion();
		}

		return actual >= rango_max;
	}

	boolean validarVacios() {
		boolean sw = true;
		String cbx = "--Seleccionar--";
		StringBuilder vacios = new StringBuilder();
		if (cbxTipoParametro.getValue().equalsIgnoreCase(cbx)) {
			vacios.append("Debes elegir un tipo de parámetro.\n");
			sw = false;
		}
		if (txtParametro.getText().equalsIgnoreCase("")) {
			vacios.append("Debes escribir un parámetro.\n");
			sw = false;
		}
		if (cbxEstado.getValue().equalsIgnoreCase(cbx)) {
			vacios.append("Debes seleccionar un estado.\n");
			sw = false;
		}
		if (vboxExiste) {
			if (cbxForeign.getValue().equalsIgnoreCase(cbx)) {
				vacios.append("Debes seleccionar una pertenencia.");
				sw = false;
			}
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
		String sql = "SELECT name,id FROM param_types WHERE param_state = 5";
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
			// Añado la lista al comboBox de paises.
			this.cbxTipoParametro.setItems(parametrosList);
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

	void cargarForeign() {
		this.foreignList.clear();
		Conexion con = Conexion.getInstance();
		String sql = "SELECT name,id FROM params WHERE paramtype_id = " + this.eleccionPertenencia;
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

	public boolean buscarRepetido(String cadena) {
		MdlParametros modelo;
		for (int i = 0; i < listParametros.toArray().length; i++) {
			modelo = listParametros.get(i);
			if (modelo.getNombre().equalsIgnoreCase(cadena)
				&& modelo.getNombreTipo().equalsIgnoreCase(this.cbxTipoParametro.getValue())) {
				return true;
			}
		}
		return false;
	}

	@FXML
	private void clickCbxForeign(ActionEvent event) {
	}

	void initAttributtes(ObservableList<MdlParametros> listParametros, String tipo) {
		this.listParametros = listParametros;
		if (!tipo.equalsIgnoreCase("")) {
			cbxTipoParametro.setValue(tipo);
			cbxTipoParametro.setDisable(true);
		}
	}

}
