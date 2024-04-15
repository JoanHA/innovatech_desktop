/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package controller;

import conexion.Conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import model.mdlVerPedidos;

/**
 *
 * @author Joan H @Darkj
 */
public class CtrVerPedidos implements Initializable {

	@FXML
	private TableView<mdlVerPedidos> tblPedidos;
	@FXML
	private TableColumn<mdlVerPedidos, Integer> colId;
	@FXML
	private TableColumn<mdlVerPedidos, String> colEstado;
	@FXML
	private TableColumn<mdlVerPedidos, Integer> colCantidad;
	@FXML
	private TableColumn<mdlVerPedidos, String> colFecha;
	@FXML
	private TableColumn<mdlVerPedidos, String> colProveedor;
	@FXML
	private TableColumn<mdlVerPedidos, Button> colDetalles;

	private ObservableList<mdlVerPedidos> listaPedidos;

	@FXML
	private Button btnPedido;
	@FXML
	private Button btnActualizar;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		this.listaPedidos = FXCollections.observableArrayList();
		this.colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
		this.colDetalles.setCellValueFactory(new PropertyValueFactory<>("boton"));
		this.colEstado.setCellValueFactory(new PropertyValueFactory<>("state"));
		this.colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		this.colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.colProveedor.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		initTable();

	}
	//metodo para llamar las vistas, en ingles se ve mas  bonito

	public void callView(String url) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + url + ".fxml"));
			Parent root = loader.load();

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);

			stage.showAndWait();
			ActionEvent event = null;
			actualizarTabla(event);
		} catch (IOException ex) {
			Logger.getLogger(CtrVerPedidos.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public void initTable() {
		Conexion conectar = Conexion.getInstance();

		ObservableList<mdlVerPedidos> lista = null;
		String sql = "select * from orders";
		ResultSet rs = conectar.consultar(sql);
		try {
			while (rs.next()) {

				int id = rs.getInt("id");
				int cantidad = rs.getInt("total");
				int estado = rs.getInt("param_status");
				String fecha = rs.getString("created_at");
				int proveedor = rs.getInt("provider_id");
				Button btnDetalles = new Button("Detalles");
				//implementar la accion al boton cada que se cree
				btnDetalles.setOnAction((event) -> {
					details(id);
				});
				
				btnDetalles.setStyle("-fx-font-weight: bold;");
				btnDetalles.getStylesheets().add("/style/Style.css");
				btnDetalles.getStyleClass().setAll("btnSuccess");
				btnDetalles.setPrefSize(150, 30);

				//variable que lleva el estado completo
				String state = null;
				switch (estado) {
				case 12:
					state = "Entregado";
					break;
				case 13:
					state = "Recibido";
					break;
				case 11:
					state = "Cancelado";
					break;
				case 10:
					state = "Pendiente";
					break;
				default:
					state = "N/A";
				}
				mdlVerPedidos pedidos = new mdlVerPedidos(id, state, cantidad, fecha, nombreProveedor(proveedor), btnDetalles);
				llenarTabla(pedidos);

			}

		} catch (SQLException ex) {
			Logger.getLogger(CtrVerPedidos.class.getName()).log(Level.SEVERE, null, ex);
		}
		conectar.cerrarConexion();

	}

	public void llenarTabla(mdlVerPedidos modelo) {
		if (!this.listaPedidos.contains(modelo)) {

			this.listaPedidos.add(modelo);
			this.tblPedidos.setItems(listaPedidos);
			this.tblPedidos.refresh();
		}

	}

	public String nombreProveedor(int id) {
		String nombreProveedor = " ";
		String legalName = "";
		Conexion conectar = Conexion.getInstance();
		String sql = "SELECT legal_name from providers "
			+ " where providers.id =" + id + "";

		ResultSet rs = conectar.consultar(sql);
		try {
			while (rs.next()) {

				legalName = rs.getString("legal_name");

			}
			nombreProveedor = legalName;

		} catch (SQLException ex) {
			Logger.getLogger(CtrVerPedidos.class.getName()).log(Level.SEVERE, null, ex);
		}
		return nombreProveedor;
	}

	public void details(int id) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VwDetallesPedido.fxml"));
			Parent root = loader.load();

			CtrDetallesPedidos ctrl = loader.getController();
			ctrl.initAttributtes(id);
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();

		} catch (IOException ex) {
			Logger.getLogger(CtrVerPedidos.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println(ex);
		}

	}

	@FXML
	private void hacerPedido(ActionEvent event) {
		//Aqui se llama la vista donde se va a realizar el pedido
		callView("ViewPedidos");

	}

	@FXML
	private void actualizarTabla(ActionEvent event) {
		tblPedidos.getItems().clear();
		initTable();
	}

}
