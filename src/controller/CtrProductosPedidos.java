/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import conexion.Conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Joan H @DarkJ
 */
public class CtrProductosPedidos implements Initializable {

	@FXML
	private Button btnActualizar;
	@FXML
	private Button btnCancelar;
	@FXML
	private VBox vbProductos;
	public ObservableList<String> estadoCbb = FXCollections.observableArrayList();
	int producto;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		estadoCbb.addAll("Disponible", "No Disponible");

	}

	void abrirVista(int id) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VwActualizarProducto.fxml"));
			Parent root = loader.load();
			CtrActualizarProducto ctrActualizar = loader.getController();
			ctrActualizar.initAttributtes(id);

			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void actualizarProducto(ActionEvent event) {
		ComboBox box = (ComboBox) event.getTarget();
		HBox parent = (HBox) box.getParent();
		TextField ref = null;
		ComboBox disponibilidad = (ComboBox) parent.getChildren().get(4);

		//Alerta para confirmar
		Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
		confirmacion.setTitle("Alerta!!");
		confirmacion.setHeaderText(null);
		confirmacion.setContentText("¿Estas seguro de cambiar la disponibilidad del producto?");
		Optional<ButtonType> result = confirmacion.showAndWait();

		if (result.get() == ButtonType.OK) {

			if (disponibilidad.getSelectionModel().getSelectedItem().equals("Disponible")) {
				abrirVista(this.producto);

			} else {
				Alert noDisponible = new Alert(Alert.AlertType.CONFIRMATION);
				noDisponible.setTitle("Alerta!!");
				noDisponible.setHeaderText(null);
				noDisponible.setContentText("¿Estas seguro de cambiar la disponibilidad del producto a 'No disponible'?. \n Esta acción no se podrá deshacer.! ");
				Optional<ButtonType> resultado = noDisponible.showAndWait();

				if (resultado.get() == ButtonType.OK) {
					Conexion conectar = Conexion.getInstance();
					Label idpro = (Label) parent.getChildren().get(0);
					String productoCancelado = idpro.getText();
					String sql = "UPDATE products SET param_status = 11 WHERE id ='" + productoCancelado + "'";
					if (conectar.ejecutar(sql)) {
						Alert alerta = new Alert(Alert.AlertType.INFORMATION);
						alerta.setTitle("El producto no va a ser guardado");
						alerta.setHeaderText(null);
						alerta.setContentText("Elegiste 'No disponible' el pedido no se va mostrar para venta");
						alerta.show();
					} else {
						Alert alerta = new Alert(Alert.AlertType.ERROR);
						alerta.setTitle("ERROR");
						alerta.setHeaderText(null);
						alerta.setContentText("Tuvimos un error al cambiar el estado de tu producto. Por favor intenta mas tarde...");
						alerta.show();

					}
					conectar.cerrarConexion();
				}

			}

		}
	}

	public void initProductos(int id) {

		Conexion conectar = Conexion.getInstance();
		String sql = "Select *  from order_details where order_id=" + id;

		HBox hboxPedidos = new HBox();

		int cantidad;
		ResultSet rs = conectar.consultar(sql);
		try {
			while (rs.next()) {
				cantidad = rs.getInt("qty");
				String sql2 = "select * from products where id =" + rs.getInt("product_id");
				ResultSet result = conectar.consultar(sql2);
				while (result.next()) {

					//Configuracion del HBOX
					HBox h = new HBox();
					h.getStylesheets().add("/style/Style.css");
					h.setSpacing(50);
					h.setPrefHeight(59);
					h.setPrefWidth(684);
					h.alignmentProperty().setValue(Pos.CENTER);

					this.producto = result.getInt("id");
					Label idProd = new Label(String.valueOf(this.producto));
					idProd.setPrefHeight(16);
					idProd.setPrefWidth(14);

					//txt para referencia
					TextField referencia = new TextField();
					referencia.setPrefHeight(26);
					referencia.setPrefWidth(114);
					referencia.setPromptText("Referencia");
					referencia.setText(result.getString("code"));

					//txt para el nombre del producti
					TextField Nombre_producto = new TextField();
					Nombre_producto.setPrefHeight(26);
					Nombre_producto.setPrefWidth(160);
					Nombre_producto.setPromptText("Nombre_producto");
					Nombre_producto.setText(result.getString("name"));

					//txt para cantidad
					TextField cant = new TextField();
					cant.setPrefHeight(26);
					cant.setPrefWidth(48);
					cant.setPromptText("#");
					cant.setText(String.valueOf(cantidad));

					//cbb para estado
					ComboBox<String> estado = new ComboBox<>();
					estado.getStylesheets().add("/style/Style.css");
					estado.getStyleClass().setAll("comboBox");
					estado.setItems(estadoCbb);
					estado.setPromptText("Seleccione estado");
					estado.setOnAction(event -> {
						actualizarProducto(event);
					});

					//se le agregan todos los TextFields al HBox
					h.getChildren().addAll(idProd, referencia, Nombre_producto, cant, estado);

					//Se le añade el hbox al vBox
					this.vbProductos.getChildren().add(h);

				}

			}

		} catch (SQLException ex) {
			Logger.getLogger(CtrProductosPedidos.class.getName()).log(Level.SEVERE, null, ex);
		}
		conectar.cerrarConexion();
	}

	@FXML
	private void volver(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();

	}

	@FXML
	private void cancel(ActionEvent event) {
		CtrDetallesPedidos ctr = new CtrDetallesPedidos();

		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();

	}
}
//   public void initPedido(String referencia){
//        Conexion conectar = new Conexion();
//        String sql ="Select * from productos where referencia='"+referencia+"'";
//        ResultSet rs = conectar.consultar(sql);
//         String ref = null;
//        try {
//            while (rs.next()) {
//                ref = rs.getString("referencia");
//            }
//            
//     
//        } catch (SQLException ex) {
//            Logger.getLogger(CtrActualizarPedidos.class.getName()).log(Level.SEVERE, null, ex);
//        }

//    

