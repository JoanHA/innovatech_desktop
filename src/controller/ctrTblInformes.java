/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import conexion.Conexion;
import controller.ctrVerInformes;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.MdlInformes;

/**
 * FXML Controller class
 *
 * @author braya
 */
public class ctrTblInformes implements Initializable {

	@FXML
	private Button btnCrear;

	@FXML
	private TableView<MdlInformes> tblInformes;
	@FXML
	private TableColumn colTipo;
	@FXML
	private TableColumn colTitulo;
	@FXML
	private TableColumn colFechaReg;

	private ObservableList<MdlInformes> informeList;
	@FXML
	private TableColumn columVer;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		informeList = FXCollections.observableArrayList();

		this.colTitulo.setCellValueFactory(new PropertyValueFactory("titulo"));

		this.colFechaReg.setCellValueFactory(new PropertyValueFactory("fechaRegistroInfo"));

		this.columVer.setCellValueFactory(new PropertyValueFactory("btnVer"));

		cargarInformes();

	}

	public void cargarInformes() {
		informeList.clear();
		// aqui se incializa la conexion
		Conexion co = Conexion.getInstance();

		String sql = "SELECT * FROM  reports ";

		ResultSet Result = co.consultar(sql);
		try {
			// aqui va la sentencia que va a traer los datos de la base de datos

			while (Result.next()) {
				MdlInformes model = new MdlInformes();

				model.setIdInfo(Result.getInt("id"));

				model.setTitulo(Result.getString("subject"));

				model.setDescripcioInfo(Result.getString("body"));

				// model.setImagen(Result.getString("Imagen"));
				model.setEstadoInfo(Result.getString("param_state"));

				model.setFechaRegistroInfo(Result.getString("created_at"));

				model.setInformeFechaModificacionInfo(Result.getString("updated_at"));

				model.setImagen(Result.getString("image"));

				Button btnVer = new Button("ver");

				
				btnVer.setStyle("-fx-font-weight: bold;");
				btnVer.getStylesheets().add("/style/Style.css");
				btnVer.getStyleClass().setAll("btnSuccess");
				btnVer.setPrefHeight(30);
				btnVer.setPrefWidth(100);

				btnVer.setOnAction(e -> {
					ver(model);

				});

				model.setBtnVer(btnVer);

				informeList.add(model);

				tblInformes.setItems(informeList);

				tblInformes.refresh();

			}

			// resulset donde se van a guardar los datos que vienen de la base de datos
			// co.ejecutar(sql);
			// guardando los datos de la base de datos para mandarlos al modelo
		} catch (Exception e) {
		}
		co.cerrarConexion();

	}

	private void clickVer(ActionEvent event) {
		String tituloInforme = this.colTitulo.getText();
		String fechaRegistro = this.colFechaReg.getText();
	}

	private void ver(MdlInformes verInfo) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/vwVerInformes.fxml"));
			Parent root = loader.load();

			ctrVerInformes coninfo = loader.getController();

			coninfo.initAttributtes(verInfo);

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.showAndWait();
			cargarInformes();
		} catch (IOException ex) {
			Logger.getLogger(ctrTblInformes.class.getName()).log(Level.SEVERE, null, ex);

		}

	}

	@FXML
	private void nuevoInforme(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/vwInformes.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException ex) {
			Logger.getLogger(ctrInformes.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
