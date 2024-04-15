package controller;

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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.MdlDirecciones;

/**
 * FXML Controller class
 *
 * @author Dragnell
 */
public class ctrModificarPuntoFisico implements Initializable {

	private TextField txtMunicipio;
	@FXML
	private Button btnAceptar;
	@FXML
	private Button btnCancelar;

	private ObservableList<String> departamentosList = FXCollections.observableArrayList();
	private ObservableList<String> municipioList = FXCollections.observableArrayList();
	private ObservableList<String> estadosList = FXCollections.observableArrayList();


	@FXML
	private ComboBox<String> cbxDepartamentos;
	@FXML
	private ComboBox<String> cbxMunicipios;
	@FXML
	private TextField txtBarrio;
	@FXML
	private TextField txtDireccion;
	@FXML
	private TextField txtPiso;
	private RadioButton rbActivo;
	private RadioButton rbInactivo;

	private String id;
	
	Map<String,String> idsDepartamentos = new HashMap<>();
	Map<String,String> idsMunicipios= new HashMap<>();
	Map<String,String> idsEstados = new HashMap<>();
	@FXML
	private ComboBox<String> cbxEstado;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		cargarCbxDepartamentos();
		cargarEstados();
	}

	private void clickAceptar(ActionEvent event) {
		modificar();
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

	void modificar() {
		Conexion con = Conexion.getInstance();
		StringBuilder vacios = new StringBuilder();
		String cbx = "--seleccionar--";
		boolean textosVacios = true;
		//valida los textFlied vacios.
		if (cbxDepartamentos.getValue().equalsIgnoreCase(cbx)
		 || cbxDepartamentos.getValue().equals("")) {
			vacios.append("Departamento no puede estar vació. \n");
			textosVacios = false;
		}
		if (cbxMunicipios.getValue().equalsIgnoreCase(cbx)
		 || cbxMunicipios.getValue().equals("")) {
			vacios.append("Municipio no puede estar vació. \n");
			textosVacios = false;
		}
		if (txtBarrio.getText().isEmpty()) {
			vacios.append("Barrio no puede estar vació. \n");
			textosVacios = false;
		}
		if (txtDireccion.getText().isEmpty()) {
			vacios.append("Dirección no puede estar vació. \n");
			textosVacios = false;
		}
		if (textosVacios) {
			Instant instant = Instant.now();
			Timestamp timestamp = Timestamp.from(instant);
			String estado = this.idsEstados.get(cbxEstado.getValue());

			String idMunicipio;
			idMunicipio = this.idsMunicipios.get(cbxMunicipios.getValue());
			String palabra = txtBarrio.getText();
			String nombre = palabra.substring(0, 1).toUpperCase() + palabra.substring(1).toLowerCase();
			

			String sql = "UPDATE address SET hood = '" + nombre + "', address = '" + txtDireccion.getText() + "', "
			 + "floor = '" + txtPiso.getText() + "', param_state = " + estado + ", updated_at = '" + timestamp + "', param_city = " + idMunicipio + " "
			 + "WHERE id = " + this.id + "";

			Alert veri = new Alert(Alert.AlertType.CONFIRMATION);
			veri.setHeaderText(null);
			veri.setTitle("Confirmación");
			veri.setContentText("¿Seguro que quieres modificar este punto?");
			Optional<ButtonType> result = veri.showAndWait();
			if (result.get() == ButtonType.OK) {
				if (con.ejecutar(sql)) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setTitle("Información");
					alert.setContentText("Modificado exitosamente.");
					alert.show();
				} else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setTitle("Error");
					alert.setContentText("Error al modificar");
					alert.show();
				}
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("valores vacios");
			alert.setContentText(vacios.toString());
			alert.show();
		}
		con.cerrarConexion();
	}

	void cerrarVentana() {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void clickCancelar(ActionEvent event) {
		cerrarVentana();
	}

	@FXML
	private void clickCbxDepartamentos(ActionEvent event) {
		try {
			cargarCbxMunicipios();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void cargarCbxDepartamentos() {
		//Limpio la lista en caso de que se añada algo nuevo.
		departamentosList.clear();
		Conexion con = Conexion.getInstance();
		String sql = "SELECT name,id FROM params where paramtype_id = 6";
		ResultSet rs = con.consultar(sql);
		try {
			while (rs.next()) {
				// cargo la lista de departamentos
				String clave = null,valor = null;
				clave =rs.getString("name");
				valor = rs.getString("id");
				this.idsDepartamentos.put(clave, valor);
				departamentosList.add(rs.getString("name"));
				
			}
			//Añado la lista al comboBox de paises.
			cbxDepartamentos.setItems(departamentosList);
		} catch (SQLException e) {
			System.out.println("Error en cargar departamentos: " + e.getMessage());
		}
		con.cerrarConexion();
	}

	void cargarCbxMunicipios() {
		//Limpio la lista en caso de nuevos datos
		municipioList.clear();
		ctrTblPuntosFisicos ctpuntos = new ctrTblPuntosFisicos();
		Conexion con = Conexion.getInstance();
		String departamento = this.idsDepartamentos.get(cbxDepartamentos.getValue());
		//genero una consulta que retorne solo los datos de el departamento seleccionado.
		String sql = "SELECT name,id FROM params WHERE param_foreign = "+departamento;
		ResultSet rs = con.consultar(sql);
		try {
			while (rs.next()) {
				//Lleno la lista con los datos de la base de datos.
				String clave = null,valor = null;
				clave =rs.getString("name");
				valor = rs.getString("id");
				this.idsMunicipios.put(clave, valor);
				municipioList.add(rs.getString("name"));
			}
			//Cargo el comboBox con la lista.
			cbxMunicipios.setItems(municipioList);
		} catch (SQLException e) {
			System.out.println("Error en cargar municipios: " + e.getMessage());
		}finally{
			con.cerrarConexion();
		}
	}

	@FXML
	private void clickCbxMunicipios(ActionEvent event) {
	}

	@FXML
	private void clickAgregar(ActionEvent event) {
		modificar();
	}

	void initAttributtes(MdlDirecciones modelo) {
		txtBarrio.setText(modelo.getBarrio());
		txtPiso.setText(modelo.getPiso());
		txtDireccion.setText(modelo.getDireccion());
		cbxMunicipios.setValue(modelo.getMunicipio());
		cbxDepartamentos.setValue(modelo.getDepartamento());
		cbxEstado.setValue(modelo.getEstado());
		
		this.id = modelo.getId();
		cargarCbxMunicipios();
	}
}
