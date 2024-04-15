/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import conexion.Conexion;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import controller.CtrVerPedidos;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import model.mdlVerPedidos;

/**
 * FXML Controller class
 *
 * @author Joan H @DarkJ
 */
public class CtrDetallesPedidos implements Initializable {

	@FXML
	private Button btnConfirmar;
	@FXML
	private TextField txtProveedor;
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtCantidad;
	@FXML
	private TextField txtfecha;
	@FXML
	private ComboBox<String> cbbEstado;
	@FXML
	private VBox vbProductos;

	private Stage stage;

	public ObservableList<String> Estados = FXCollections.observableArrayList();
	@FXML
	private TextField txtCelular;
	@FXML
	private Button btnConfirm;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Estados.addAll("Pendiente", "Recibido", "Cancelado");
		cbbEstado.getItems().addAll(Estados);

	}

	public ArrayList buscarProveedor(int id) {
		Conexion conectar = Conexion.getInstance();

		ArrayList info = new ArrayList();
		String sql = "Select id, legal_name, phone from providers "
			+ " where providers.id=" + id;
		ResultSet rs = conectar.consultar(sql);
		String celular = "", legal_name = "";
		try {
			while (rs.next()) {
				celular = rs.getString("phone");
				legal_name = rs.getString("legal_name");
			}
			//agrego la informacion del proveedor a un array para luego llamar la posicion 0 que es el nombre
			//y la 1 que es el celular
			info.add(legal_name);
			info.add(celular);
		} catch (SQLException ex) {
			Logger.getLogger(CtrDetallesPedidos.class.getName()).log(Level.SEVERE, null, ex);
		}
		conectar.cerrarConexion();

		return info;
	}

	//Esta funcion sirve para devolver el nombre del producto segun el id
	public String devolverProducto(int idProducto) {
		String nombreProducto = "";
		Conexion conectar = Conexion.getInstance();
		String sql2 = "Select name from products where id=" + idProducto;
		ResultSet rs = conectar.consultar(sql2);
		try {
			while (rs.next()) {
				nombreProducto = rs.getString("name");
			}

		} catch (SQLException ex) {
			Logger.getLogger(CtrDetallesPedidos.class.getName()).log(Level.SEVERE, null, ex);
		}
		return nombreProducto;

	}

	//traer los productos del pedido
	public ArrayList getProductos(int id) {
		HashMap<String, Integer> InfoProductos = new HashMap<String, Integer>();
		Conexion conectar = Conexion.getInstance();
		try {
			//sentencia para traer los id de los productos del pedido
			String sql = "Select product_id, qty from order_details where order_id =" + id;

			ResultSet rs = conectar.consultar(sql);
			while (rs.next()) {
//                idProductos.add(rs.getInt("producto_id"));
				int cant = rs.getInt("qty");
				InfoProductos.put(devolverProducto(rs.getInt("product_id")), cant);
			}

			for (String nombre : InfoProductos.keySet()) {
				HBox hbox = new HBox();
				hbox.setSpacing(40);
				hbox.setAlignment(Pos.CENTER);
				//Debo arreglar el margin
				hbox.setMargin(hbox, new Insets(10, 10, 10, 10));
				int ancho = 299;
				int largo = 50;

				Label tituloNombre = new Label();
				tituloNombre.setText("Nombre del producto: ");

				Label tituloCantidad = new Label();
				tituloCantidad.setText("Cantidad pedida: ");
				Label NombreProducto = new Label();
				NombreProducto.setText(nombre);

				Label Cantidad = new Label();
				Cantidad.setText(InfoProductos.get(nombre).toString());

				hbox.getChildren().addAll(tituloNombre, NombreProducto, tituloCantidad, Cantidad);

				this.vbProductos.getChildren().add(hbox);
			}
		} catch (SQLException ex) {
			Logger.getLogger(CtrDetallesPedidos.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public void llenarCampos(int id) {
		Conexion conectar = Conexion.getInstance();
		String sql = "Select * from orders where id=" + id;

		ResultSet rs = conectar.consultar(sql);
		//Definicion de variables para guardar campos      
		int idCompra = 0, cantidad = 0, proveedor_id = 0;
		int estado = 0;
		Timestamp created_at = null;
		try {
			while (rs.next()) {
				idCompra = rs.getInt("id");
				cantidad = rs.getInt("total");
				estado = rs.getInt("param_status");
				created_at = rs.getTimestamp("created_at");
				proveedor_id = rs.getInt("provider_id");
			}
			conectar.cerrarConexion();

			ArrayList info = buscarProveedor(proveedor_id);

			//paso los campos a la vista
			this.txtCantidad.setText(String.valueOf(cantidad));
			this.txtProveedor.setText(info.get(0).toString());
			this.txtCelular.setText(info.get(1).toString());
			this.txtId.setText(String.valueOf(idCompra));

			//Sacar solo el año mes y dia de la fecha
			String[] parts = String.valueOf(created_at).split(" ");

			this.txtfecha.setText(String.valueOf(parts[0]));

			switch (estado) {
			case 10:
				cbbEstado.getSelectionModel().select("Pendiente");
				break;
			case 13:
				cbbEstado.getSelectionModel().select("Recibido");
				break;
			case 11:
				cbbEstado.getSelectionModel().select("Cancelado");
				break;
			default:
				throw new AssertionError();
			}
			getProductos(idCompra);

		} catch (SQLException ex) {
			Logger.getLogger(CtrDetallesPedidos.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			conectar.cerrarConexion();
		}

	}

	@FXML
	private void actualizarProductos(ActionEvent event) {
		CtrVerPedidos ctr = new CtrVerPedidos();
		String estado = cbbEstado.getSelectionModel().getSelectedItem();
		CtrProductosPedidos ctrl = null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/vwProductosPedidos.fxml"));

		switch (estado) {
		case "Recibido":
			Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
			alerta.setHeaderText(null);
			alerta.setTitle("Desea continuar?");
			alerta.setContentText("Desea cambiar el estado del pedido a Recibido?\n "
				+ "Al confirmar deberás actualizar la información de cada producto.\n \n"
				+ " \n ¿Deseas continuar?");

			Optional<ButtonType> result = alerta.showAndWait();
			if (result.get() == ButtonType.OK) {
				try {
					Parent root = loader.load();
					ctrl = loader.getController();

					ctrl.initProductos(Integer.valueOf(txtId.getText()));
					Scene scene = new Scene(root);
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.setScene(scene);
					stage.showAndWait();

				} catch (Exception ex) {
					Logger.getLogger(CtrDetallesPedidos.class.getName()).log(Level.SEVERE, null, ex);
				}
			} else {
				cbbEstado.getSelectionModel().select("Pendiente");
			}

			break;
		case "Cancelado":
			Alert alerta2 = new Alert(Alert.AlertType.CONFIRMATION);
			alerta2.setHeaderText(null);
			alerta2.setTitle("¿Desea continuar?");
			alerta2.setContentText("Desea cambiar el estado del pedido a Cancelado?\n "
				+ "Al confirmar el pedido se tomara como cancelado y no se guardaran los productos pedidos. \n\n"
				+ "\n ¿Deseas continuar?");
			Optional<ButtonType> result2 = alerta2.showAndWait();

			if (result2.get() == ButtonType.OK) {
				Conexion conectar = Conexion.getInstance();
				////Cambiar el estado de la compra
				String sql = "UPDATE orders SET param_status = 11 where id=" + txtId.getText();
				conectar.ejecutar(sql);

				////cambiar el estado de  detalle de compras
				String sql2 = "UPDATE order_details SET param_status  =11 where order_id=" + txtId.getText();
				conectar.ejecutar(sql2);

				conectar.cerrarConexion();
			}

			break;
		default:

		}

	}

	@FXML
	private void volver(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();

	}

	@FXML
	private void confirm(ActionEvent event) {
		Conexion conectar = Conexion.getInstance();
		String estado = cbbEstado.getSelectionModel().getSelectedItem();
		String newEstado = "";
		switch (estado) {
		case "Pendiente":
			newEstado = "10";
			break;
		case "Recibido":
			newEstado = "13";
			break;
		case "Cancelado":
			newEstado = "11";
			break;
		default:
			throw new AssertionError();
		}
		String sql = "UPDATE orders SET param_status= " + newEstado + "  where id=" + txtId.getText();
		String sqlDetalles = "UPDATE order_details SET  param_status= " + newEstado + "  where order_id=" + txtId.getText();
		if (conectar.ejecutar(sql) && conectar.ejecutar(sqlDetalles)) {
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setHeaderText(null);
			alerta.setTitle("Pedido actualizado correctamente");
			alerta.setContentText("El pedido fue actualizado exitosamente");
			alerta.showAndWait();
		} else {
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setHeaderText(null);
			alerta.setTitle("Lo sentimos, tuvimos un problema...");
			alerta.setContentText("El pedido no pudo ser actualizado exitosamente... \n Intenta mas tarde");
			alerta.showAndWait();
		}

		conectar.cerrarConexion();
		volver(event);

	}

	void initAttributtes(int id) {
		llenarCampos(id);
	}

}
