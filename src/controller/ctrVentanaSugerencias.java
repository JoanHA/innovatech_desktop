/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import conexion.Conexion;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import model.mdlPQRS;

/**
 * FXML Controller class
 *
 * @author fofom
 */
public class ctrVentanaSugerencias implements Initializable {

	@FXML
	private BorderPane borpane;
	@FXML
	private HBox hbox_arriba;
	@FXML
	private ToolBar bardatos;
	@FXML
	private VBox vboxizquierda;
	@FXML
	private Label lblfecha;
	@FXML
	private Label lblnombre;
	@FXML
	private Label lblcorreo;
	@FXML
	private Label lblcontacto;
	@FXML
	private Label lbldescripcion;
	@FXML
	private Button btnAtras;
	@FXML
	private Button btnResponder;
	@FXML
	private VBox vboxderecha;
	@FXML
	private TextField txtcorreo;
	@FXML
	private TextField txtcontacto;
	@FXML
	private TextArea txtdescripcion;
	@FXML
	private TextField txtFechaRegistro;
	@FXML
	private TextField txtNombre;
	@FXML
	private Label lblTitle;
	@FXML
	private Button btnLeido;
	@FXML
	private Button btnPendiente;
	@FXML
	private Pane panebutton;

	String numero = "";
	String id = "";

	// Conexion objcon = new Conexion();
	// Connection conn = objcon.getConnection();
	// PreparedStatement stmt;
	// ResultSet rs;
	// String query;
	void mostrarDatos(mdlPQRS usuario) {
		txtcorreo.setText("  " + usuario.getEmail());
		txtdescripcion.setText(usuario.getBody());
		txtFechaRegistro.setText("  " + usuario.getCreated_at());
		txtcontacto.setText("  " + usuario.getPhone());
		txtNombre.setText("  " + usuario.getFirst_name() + " " + usuario.getLast_name());
		lblTitle.setText("SUGERENCIA");
		numero = usuario.getPhone();
		id = usuario.getId();
	}

	@FXML
	private void eventActionAtras(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	private void actionEventResponder(ActionEvent event) {
		try {

			URI uri = new URI("https://wa.me/" + numero);
			Desktop.getDesktop().browse(uri);

		} catch (IOException | URISyntaxException e) {
			JOptionPane.showMessageDialog(null, "El numero registrado no es valido.");
		}

	}

	@FXML
	private void eventActionConvertirLeido(ActionEvent event) {
		String query = "UPDATE faqs SET param_state = 2300 WHERE id  = " + this.id;
		Conexion objcon = Conexion.getInstance();
		objcon.ejecutar(query);
		objcon.cerrarConexion();
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Información");
		alert.setHeaderText(null);
		alert.setContentText("Cambio exitoso.");
		alert.showAndWait();
	}

	@FXML
	private void eventActionConvertirPendiente(ActionEvent event) {
		String query = "UPDATE faqs SET param_state = 2301 WHERE id  = " + this.id;
		Conexion objcon = Conexion.getInstance();
		objcon.ejecutar(query);
		objcon.cerrarConexion();
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Información");
		alert.setHeaderText(null);
		alert.setContentText("Cambio exitoso.");
		alert.showAndWait();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

}
