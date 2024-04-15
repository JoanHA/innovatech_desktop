/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import conexion.Conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.mdlPQRS;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ctrSugerencias implements Initializable {

	@FXML
	private BorderPane borderpane;
	@FXML
	private VBox vbox;
	@FXML
	private Button btnActualizar;
	@FXML
	private Button btnVerPendientes;
	@FXML
	private TableView<mdlPQRS> tblSugerencias;
	@FXML
	private TableColumn<mdlPQRS, Integer> clnidPqrsd;
	@FXML
	private TableColumn<mdlPQRS, String> clnFecha_Registro;
	@FXML
	private TableColumn<mdlPQRS, String> clnEstado;
	@FXML
	private TableColumn<mdlPQRS, String> clnCorreo;
	@FXML
	private TableColumn<mdlPQRS, Integer> clnContacto;
	@FXML
	private TableColumn<mdlPQRS, Button> clnVer;
	@FXML
	private Button btnVerLeidos;

	// Conexion objcon = new Conexion();
	// Connection conn = objcon.getConnection();
	// PreparedStatement ps;
	// ResultSet rs;
	String query;

	private ObservableList<mdlPQRS> listaPQRS = FXCollections.observableArrayList();

	/*
     * Metodos:
     * 1. Whilerecorredor
     * 2. mostrarboton
     * 3. Cargar
     * 4. MostrarDatosCompletos
     * 5. mostrarDatosPentientes
     * 6. mostrarDatosContestados
	 */
	public void WhileRecorredor(ResultSet rs) throws SQLException {
		while (rs.next()) {
			mdlPQRS pqrs = new mdlPQRS();
			pqrs.setId(rs.getString("id"));
			pqrs.setCreated_at(rs.getString("created_at"));
			pqrs.setParam_state(getParam(rs.getString("param_state")));
			pqrs.setBody(rs.getString("body"));
			pqrs.setEmail(rs.getString("email"));
			pqrs.setParam_type(rs.getString("param_type"));
			pqrs.setBody(rs.getString("body"));
			pqrs.setFirst_name(rs.getString("first_name"));
			pqrs.setLast_name(rs.getString("last_name"));
			pqrs.setPhone(rs.getString("phone"));
			Button btnVerDetalles = new Button("Ver Detalles");
			btnVerDetalles.getStylesheets().add("/style/Style.css");
			btnVerDetalles.getStyleClass().setAll("btnSuccess");
			btnVerDetalles.setPrefHeight(30);
			btnVerDetalles.setPrefWidth(100);
			btnVerDetalles.setOnAction(e -> {
				detalles(pqrs);
			});
			pqrs.setVer(btnVerDetalles);
			listaPQRS.add(pqrs);
		}
	}

	private String getParam(String param) {
		Conexion con = Conexion.getInstance();
		Statement sentencia = null;
		ResultSet rs = null;
		try {
			sentencia = con.getConnection().createStatement();
			rs = sentencia.executeQuery("SELECT name FROM params where id = " + param);

			if (rs.next()) {
				param = rs.getString("name");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return param;
	}

	void detalles(mdlPQRS usuario) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/vtnPeticiones.fxml"));
			Parent root = loader.load();
			ctrVentanaPeticiones accionardatos = loader.getController();
			accionardatos.mostrarDatos(usuario);
			root.setUserData(usuario);
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			mostrarDatosCompletos();
		} catch (IOException ex) {
			Logger.getLogger(ctrPeticiones.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void cargar() {
		clnidPqrsd.setCellValueFactory(new PropertyValueFactory<>("id"));
		clnFecha_Registro.setCellValueFactory(new PropertyValueFactory<>("created_at"));
		clnEstado.setCellValueFactory(new PropertyValueFactory<>("param_state"));
		clnCorreo.setCellValueFactory(new PropertyValueFactory<>("email"));
		clnContacto.setCellValueFactory(new PropertyValueFactory<>("phone"));
		clnVer.setCellValueFactory(new PropertyValueFactory<>("ver"));
		tblSugerencias.setItems(listaPQRS);
	}

	public void mostrarDatosCompletos() {
		listaPQRS.clear();
		query = "SELECT * FROM faqs \n"
			+ "INNER JOIN users ON users.id = faqs.user_id \n"
			+ "WHERE faqs.param_type = 1417";

		Conexion objcon = Conexion.getInstance();
		Connection conn = objcon.getConnection();
		ResultSet rs = null;
		try {
			rs = conn.createStatement().executeQuery(query);

			WhileRecorredor(rs);

		} catch (Exception e) {
			System.out.println("No se puede mostrar registro");
		}

		cargar();
		objcon.cerrarConexion();
	}

	public void mostrarDatosPendientes() {
		listaPQRS.clear();
		query = "SELECT * FROM faqs \n"
			+ "INNER JOIN users ON users.id = faqs.user_id \n"
			+ "WHERE faqs.param_type = 1417 AND faqs.param_state = 2301";
		Conexion objcon = Conexion.getInstance();
		Connection conn = objcon.getConnection();
		ResultSet rs = null;
		try {
			rs = conn.createStatement().executeQuery(query);
			WhileRecorredor(rs);
		} catch (Exception e) {
			System.out.println("No se puede mostrar registro");
		}
		cargar();
		objcon.cerrarConexion();
	}

	public void mostrarDatosLeidos() {
		listaPQRS.clear();
		query = "SELECT * FROM faqs \n"
			+ "INNER JOIN users ON users.id = faqs.user_id \n"
			+ "WHERE faqs.param_type = 1417 AND faqs.param_state = 2300";
		Conexion objcon = Conexion.getInstance();
		Connection conn = objcon.getConnection();
		ResultSet rs = null;
		try {
			rs = conn.createStatement().executeQuery(query);
			WhileRecorredor(rs);

		} catch (Exception e) {
			System.out.println("No se puede mostrar registro");
		}
		cargar();
		objcon.cerrarConexion();
	}

	@FXML
	private void actioneventActualizar(ActionEvent event) {
		listaPQRS.clear();
		mostrarDatosCompletos();
	}

	@FXML
	private void actioneventVerPendientes(ActionEvent event) {
		listaPQRS.clear();
		mostrarDatosPendientes();
	}

	@FXML
	private void actioneventVerLeidos(ActionEvent event) {
		listaPQRS.clear();
		mostrarDatosLeidos();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		mostrarDatosCompletos();
	}

}
