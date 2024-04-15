/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.MdlManejoUser;
import conexion.Conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author darkj
 */
public class CtrManejoUser implements Initializable {

	@FXML
	private Button btnVolver;
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtApellido;
	@FXML
	private TextField txtCorreo;
	@FXML
	private TextField txtFecha;
	@FXML
	private TextField txtTotal;
	@FXML
	private TextArea txtProductos;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	@FXML
	private void back(ActionEvent event) {

		Stage stage = (Stage) this.btnVolver.getScene().getWindow();

		stage.close();
	}

	public String buscarProductos(String id) {
		Conexion conectar = Conexion.getInstance();
		StringBuilder productos = new StringBuilder();
		ResultSet rs = null;
		String Prod = null;

		try {
			String sql = "SELECT products.name, SUM(sales_details.qty) as qty\n"
				+ "FROM products\n"
				+ "INNER JOIN sales_details ON sales_details.product_id = products.id\n"
				+ "INNER JOIN sales ON sales.id = sales_details.sale_id\n"
				+ "INNER JOIN users ON users.id = sales.user_id\n"
				+ "WHERE users.document = '"+id+"' AND (sales.param_shipping > 14 OR sales.param_shipping < 14)\n"
				+ "GROUP BY products.name;";
			System.out.println(sql);
			rs = conectar.consultar(sql);
			String producto;
			String cantidad;
			int total = 0;
			while (rs.next()) {
				producto = rs.getString("name");
				cantidad = rs.getString("qty");
				productos.append(producto).append(" = ").append(cantidad).append("\n");
				total = total + rs.getInt("qty");
			}

			this.txtTotal.setText(String.valueOf(total));
			if (productos.length() == 0) {
				Prod = "No ha comprado productos aun";

			} else {
				//

				Prod = (productos.toString().substring(0, productos.length() - 1));

			}

		} catch (Exception e) {
			System.out.println(e);
		}
		conectar.cerrarConexion();
		return Prod;
	}

	public void getPersona(MdlManejoUser persona) {

		this.txtId.setText(persona.getTipo_documento() + ". " + persona.getId());
		this.txtApellido.setText(persona.getApellido());
		this.txtCorreo.setText(persona.getEmail());
		this.txtFecha.setText(persona.getFecha());
		this.txtNombre.setText(persona.getNombre());
		this.txtTotal.setText(String.valueOf(persona.getTotal()));
		this.txtProductos.setText(buscarProductos(persona.getId()));

	}

	public void buscarUser(String id) {
		ResultSet rs;
		String total = "";
		Conexion conectar = Conexion.getInstance();
		String sql;
		sql = "SELECT users.param_type, document, first_name ,last_name, users.email, sales.created_at, sales.total from users \n"
			+ " INNER JOIN sales on sales.user_id = users.id where document ='" + id + "'";
		rs = conectar.consultar(sql);
		try {
			while (rs.next()) {

				String identificacion = rs.getString("document");
				String nombre = rs.getString("first_name");
				String apellido = rs.getString("last_name");
				String email = rs.getString("email");
				String tipo = getParam(rs.getString("users.param_type"));
				String fecha = (rs.getString("sales.created_at"));
				total = (rs.getString("sales.total"));

				total = (rs.getString("sales.total"));

				MdlManejoUser model = new MdlManejoUser(nombre, apellido, identificacion, fecha, total, email, tipo);
				getPersona(model);

			}
		} catch (Exception e) {
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

}
