/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.MdlManejoUser;
import java.sql.*;
import conexion.Conexion;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.MdlVenta;

/**
 * FXML Controller class
 *
 * @author darkj
 */
public class CtrBuscarUser implements Initializable {

	private Button btnVer;

	@FXML
	private TableColumn<MdlManejoUser, String> colNombre;
	@FXML
	private TableColumn<MdlManejoUser, String> colApellido;

	@FXML
	private TableColumn<MdlManejoUser, String> colFecha;
	@FXML
	private TableColumn<MdlManejoUser, Double> colTotal;
	@FXML
	private TableView<MdlManejoUser> tblUsuario;
	private Button btnFiltrar;

	private ObservableList<MdlManejoUser> datosUser;
	private ObservableList<MdlManejoUser> FiltroPersonas;
	MdlManejoUser UsuarioGlobal = new MdlManejoUser();
	@FXML
	private TableColumn<MdlManejoUser, String> colIdentificacion;
	@FXML
	private TableColumn<MdlManejoUser, String> colEmail;
	@FXML
	private TableColumn<MdlManejoUser, Button> colOpciones;
	@FXML
	private VBox vbox;
	@FXML
	private TextField txtBuscar;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		datosUser = FXCollections.observableArrayList();
		this.colIdentificacion.setCellValueFactory(new PropertyValueFactory("id"));
		this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
		this.colApellido.setCellValueFactory(new PropertyValueFactory("apellido"));
		this.colEmail.setCellValueFactory(new PropertyValueFactory("email"));
		this.colFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
		this.colTotal.setCellValueFactory(new PropertyValueFactory("total"));
		this.colOpciones.setCellValueFactory(new PropertyValueFactory("boton"));
		initTable();
	}

	// este metodo es para ver los detalles de la persona escogida
	public void Details(String id) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewManejoUsers.fxml"));
			Parent root = loader.load();

			CtrManejoUser ctr = loader.getController();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);

			ctr.buscarUser(id);
			stage.showAndWait();

		} catch (IOException ex) {
			Logger.getLogger(CtrBuscarUser.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("Error");
		}

	}

// metodo para inicializar la tabla de usuarios
	public void initTable() {
		MdlManejoUser model = new MdlManejoUser();
		Conexion conectar = Conexion.getInstance();
		ArrayList ids = new ArrayList();
		ResultSet rs = null;
		try {
			String sql = "SELECT document, \n"
				+ "       MAX(first_name) AS first_name, \n"
				+ "       MAX(last_name) AS last_name, \n"
				+ "       MAX(users.email) AS email, \n"
				+ "       MAX(sales.created_at) AS created_at, \n"
				+ "       MAX(sales.total) AS total \n"
				+ "FROM users \n"
				+ "INNER JOIN sales ON sales.user_id = users.id \n"
				+ "WHERE users.param_state = 5\n"
				+ "AND (sales.param_shipping > 14 OR sales.param_shipping < 14)\n"
				+ "GROUP BY document \n"
				+ "LIMIT 20;";
			rs = conectar.consultar(sql);
			while (rs.next()) {
				String identificacion = (rs.getString("document"));
				String nombre = rs.getString("first_name");
				String apellido = (rs.getString("last_name"));
				String email = (rs.getString("email"));
				String fecha = (rs.getString("created_at"));
				String total = totalCompra(identificacion);
				Button btnVer = new Button("Ver detalles");

				btnVer.getStylesheets().add("/style/Style.css");
				btnVer.getStyleClass().setAll("btnSuccess");
				btnVer.setPrefHeight(30);
				btnVer.setPrefWidth(100);

				btnVer.setOnAction(event -> {
					Details(identificacion);
				});
				model = new MdlManejoUser(nombre, apellido, identificacion, fecha, total, email, btnVer);
				datosUser.add(model);
			}
			tblUsuario.setItems(datosUser);
			tblUsuario.refresh();
		} catch (Exception e) {
			System.out.println(e);
		}
		conectar.cerrarConexion();
	}

	public String totalCompra(String id) {
		Conexion conectar = Conexion.getInstance();
		StringBuilder productos = new StringBuilder();
		ResultSet rs = null;
		int total = 0;
		try {
			String sql = "SELECT name, sales_details.qty  from products "
				+ "INNER JOIN sales_details on sales_details.product_id = products.id "
				+ "INNER JOIN sales on sales.id = sales_details .sale_id "
				+ "INNER JOIN users on users.id = sales.user_id "
				+ "where users.document = '" + id + "'";
			rs = conectar.consultar(sql);
			String producto;
			String cantidad;

			while (rs.next()) {
				total = total + rs.getInt("sales_details.qty");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return total + "";
	}

	void buscar(String municipio) {
		ObservableList<MdlManejoUser> listaBuscar = FXCollections.observableArrayList();
		for (int i = 0; i < datosUser.size(); i++) {
			if (datosUser.get(i).getId().toLowerCase().contains(municipio.toLowerCase())
				|| datosUser.get(i).getEmail().toLowerCase().contains(municipio.toLowerCase())) {
				listaBuscar.add(datosUser.get(i));
			}
		}
		this.tblUsuario.setItems(listaBuscar);
	}

	@FXML
	private void eventKey(KeyEvent event) {
		Object evt = event.getSource();
		if (evt.equals(txtBuscar)) {

			if (datosUser.isEmpty()) {
				Label lb = new Label("Ningún dato encontrado");
				tblUsuario.setPlaceholder(lb);
			} else {
				buscar(txtBuscar.getText());

			}
			if (txtBuscar.getText().equals("")) {
				tblUsuario.setItems(datosUser);
			}
		}
	}

}
