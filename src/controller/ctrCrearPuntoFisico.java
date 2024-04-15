/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

// param_foreign
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
import javafx.stage.Stage;
import model.MdlDirecciones;

/**
 * FXML Controller class
 *
 * @author Dragnell
 */
public class ctrCrearPuntoFisico implements Initializable {

	@FXML
	private ComboBox<String> cbxDepartamentos;
	@FXML
	private Button btnAceptar;
	// almacena los datos de el modelo para cargar en la tabla
	private ObservableList<MdlDirecciones> listUbicaciones;
	@FXML
	private Button btnCancelar;
	@FXML
	private ComboBox<String> cbxMunicipios;
	@FXML
	private TextField txtBarrio;
	@FXML
	private TextField txtDireccion;
	@FXML
	private TextField txtPiso;

	private ObservableList<String> departamentosList = FXCollections.observableArrayList();
	private ObservableList<String> municipioList = FXCollections.observableArrayList();
	private ObservableList<String> estadosList = FXCollections.observableArrayList();

	Map<String, String> idsDepartamentos = new HashMap<>();
	Map<String, String> idsMunicipios = new HashMap<>();
	@FXML
	private ComboBox<String> cbxEstado;

	Map<String, String> idsEstados = new HashMap<>();

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		listUbicaciones = FXCollections.observableArrayList();
		cbxDepartamentos.setValue("--Seleccionar--");
		cbxMunicipios.setValue("--Seleccionar--");
		cargarCbxDepartamentos();
		cargarEstados();
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

	@FXML
	private void clickCbxDepartamentos(ActionEvent event) {
		try {
			cargarCbxMunicipios();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Inserta un nuevo municipio en la base de datos.
	 *
	 * @param event
	 */
	@FXML
	private void clickAgregar(ActionEvent event) {
		StringBuilder vacios = new StringBuilder();

		String cbx = "--Seleccionar--";
		boolean textosVacios = true;
		// valida los textFlied vacios.
		if (cbxDepartamentos.getValue().equalsIgnoreCase(cbx)
				|| cbxDepartamentos.getValue().equals("")) {
			vacios.append("Departamento no puede estar vacío. \n");
			textosVacios = false;
		}
		if (cbxMunicipios.getValue().equalsIgnoreCase(cbx)
				|| cbxMunicipios.getValue().equals("")) {
			vacios.append("Municipio no puede estar vacío. \n");
			textosVacios = false;
		}
		if (txtBarrio.getText().isEmpty()) {
			vacios.append("Barrio no puede estar vacío. \n");
			textosVacios = false;
		}
		if (txtDireccion.getText().isEmpty()) {
			vacios.append("Dirección no puede estar vacío. \n");
			textosVacios = false;
		}
		if (textosVacios) {
			// Creo el modelo y lo lleno.
			MdlDirecciones modelo = new MdlDirecciones();
			ctrDirecciones control = new ctrDirecciones();

			String palabra = txtBarrio.getText();
			String nombre = palabra.substring(0, 1).toUpperCase() + palabra.substring(1).toLowerCase();

			modelo.setBarrio(nombre);
			modelo.setDireccion(txtDireccion.getText());
			modelo.setMunicipio(this.idsMunicipios.get(cbxMunicipios.getValue()));
			modelo.setPiso(txtPiso.getText());
			modelo.setEstado(this.idsEstados.get(cbxEstado.getValue()));

			if (control.crearPuntoFisico(modelo)) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Información");
				alert.setContentText("Punto físico creado exitosamente.");
				limpiarTxt();
				alert.showAndWait();
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Error");
				alert.setContentText("Error al crear punto físico.");
				alert.show();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("valores vacios");
			alert.setContentText(vacios.toString());
			alert.show();
		}
	}

	/**
	 * Limpia los textFlied.
	 */
	void limpiarTxt() {
		txtBarrio.setText("");
		txtDireccion.setText("");
		txtPiso.setText("");
	}

	void cargarCbxDepartamentos() {
		// Limpio la lista en caso de que se añada algo nuevo.
		departamentosList.clear();
		Conexion con = Conexion.getInstance();
		String sql = "SELECT name,id FROM params where paramtype_id = 6";
		ResultSet resultado = con.consultar(sql);
		try {
			while (resultado.next()) {
				// cargo la lista de departamentos
				String clave = null, valor = null;
				clave = resultado.getString("name");
				valor = resultado.getString("id");
				this.idsDepartamentos.put(clave, valor);
				departamentosList.add(resultado.getString("name"));

			}
			// Añado la lista al comboBox de paises.
			cbxDepartamentos.setItems(departamentosList);
		} catch (SQLException e) {
			System.out.println("Error en cargar departamentos: " + e.getMessage());
		}
		con.cerrarConexion();
	}

	/**
	 * Carga los departamendos dependiendo de el pais seleccionado.
	 */
	void cargarCbxMunicipios() {
		// Limpio la lista en caso de nuevos datos
		municipioList.clear();
		Conexion con = Conexion.getInstance();
		String departamento = this.idsDepartamentos.get(cbxDepartamentos.getValue());
		// genero una consulta que retorne solo los datos de el departamento
		// seleccionado.
		String sql = "SELECT name,id FROM params WHERE param_foreign = " + departamento;
		ResultSet resultado = con.consultar(sql);
		try {
			while (resultado.next()) {
				// Lleno la lista con los datos de la base de datos.
				String clave = null, valor = null;
				clave = resultado.getString("name");
				valor = resultado.getString("id");
				this.idsMunicipios.put(clave, valor);
				municipioList.add(resultado.getString("name"));
			}
			// Cargo el comboBox con la lista.
			cbxMunicipios.setItems(municipioList);
		} catch (SQLException e) {
			System.out.println("Error en cargar municipios: " + e.getMessage());
		}
		con.cerrarConexion();
	}

	@FXML
	private void clickCancelar(ActionEvent event) {
		Stage stage = (Stage) this.btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void clickCbxMunicipios(ActionEvent event) {
	}
}
