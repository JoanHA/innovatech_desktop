/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import conexion.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Mdlcarrusel;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author jhona
 */
public class VieweditarController implements Initializable {

	@FXML
	private Button btnguardar;
	@FXML
	private TextField txtid;
	@FXML
	private RadioButton rbactivo;
	@FXML
	private ToggleGroup estado;
	@FXML
	private RadioButton rbinactivo;
	@FXML
	private Spinner<Integer> spineer;
	SpinnerValueFactory<Integer> spr = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 8, 1);

	String query;
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet rs;
	private Conexion con;
	String estate;
	Toggle selecionado;
	int miValor;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		spineer.setValueFactory(spr);
	}

	private void notificar() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setTitle("Información");
		alert.setContentText("Registro modificado correctamente.");
		alert.showAndWait();
	}

	private String setParam(String param) {
		Conexion con = Conexion.getInstance();
		Statement sentencia = null;
		ResultSet rs = null;
		try {
			sentencia = con.getConnection().createStatement();
			rs = sentencia.executeQuery("SELECT id FROM params WHERE name = '" + param + "'");

			while (rs.next()) {
				param = rs.getString("id");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		con.cerrarConexion();
		return param;
	}

	public void editar(Mdlcarrusel carrusel) {
		txtid.setText(String.valueOf(carrusel.getId()));
		estate = setParam(carrusel.getEstado());
		System.out.println(estate);
		if (estate.equals("5")) {
			try {
				rbactivo.setSelected(true);
			} catch (Exception e) {
				System.out.println("No se puede presionar");
				System.out.println("Error" + e);
			}
		} else {
			rbinactivo.setSelected(true);
		}
		miValor = carrusel.getPosicion();
		spineer.getValueFactory().setValue(miValor);
	}

	// funcion modificar imagen
	public void modificar() {
		Conexion con = Conexion.getInstance();
		String identidad = txtid.getText();
		selecionado = estado.getSelectedToggle();
		if (selecionado == rbactivo) {
			estate = "5";
		} else {
			estate = "6";
		}
		int spiner = spineer.getValue();
		try {
			query = "UPDATE carrusels SET param_state='" + estate + "', position='" + spiner + "' Where id='" + identidad + "'";
			if (con.ejecutar(query)) {
				notificar();
			}
		} catch (Exception e) {
			System.out.println("Registro no modificado" + e);
		}
		con.cerrarConexion();

	}

	public void cerrarVentana() {
		Stage stage = (Stage) btnguardar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void guardarcambios(ActionEvent event) {
		modificar();
		cerrarVentana();
		notificar();

	}

}
