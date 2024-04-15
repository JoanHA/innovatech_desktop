/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Camilo Alzate
 */
public class ControllerMenu implements Initializable {

	@FXML
	private Button btninicio;
	@FXML
	private Button btnproductos;
	@FXML
	private Button btninfo;
	@FXML
	private Button btncerrar;
	@FXML
	private Button btnpqrs;

	@FXML
	private BorderPane brdmostrar;
	private boolean MostrarBtn = false;
	private VBox VboxBtnProductos;
	private VBox VboxBtninfo;
	private VBox VboxBtnpqrs;
	private Button BtnGeventas;
	private Button BtnGeProductos;
	private Button BtnInformes;
	private Button BtnUsuarios;
	private Button BtnPeticiones;
	private Button BtnQuejas;
	private Button BtnReclamos;
	private Button BtnSugerencias;
	@FXML
	private Button btnProvee;
	private VBox VboxBtnProvee;
	private Button btnGeProvee;
	private Button btnGePedidos;
	@FXML
	private VBox vboxPadre;

	@FXML
	private Button btnMas;
	private VBox VboxBtnMas;
	private Button BtnCarrusel;
	private Button BtnLuEnvio;
	private Button BtnGePara;
	@FXML
	private AnchorPane paneparent;
	@FXML
	private Button btnPerfil;
	@FXML
	private Button ayuda;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			Parent parent;
			parent = FXMLLoader.load(getClass().getResource("/view/ViewInicioMenu.fxml"));
			this.brdmostrar.setCenter(parent);
		} catch (IOException ex) {
			Logger.getLogger(ControllerMenu.class.getName()).log(Level.SEVERE, null, ex);
		}
		this.btnproductos.setOnAction(e -> menuProduc(btnproductos));
		this.btninfo.setOnAction(e -> menuInfo(btninfo));
		this.btnpqrs.setOnAction(e -> menuPqrs(btnpqrs));
		this.btnProvee.setOnAction(e -> menuProvee(btnProvee));
		this.btnMas.setOnAction(e -> menuMas(btnMas));
	}

	@FXML
	private void abririnicio(MouseEvent event) {
		if (event.getSource() == this.btninicio) {
			CargarPagina("ViewInicioMenu");
		}

	}

	public void CargarPagina(String url) {
		try {
			// Muestra el preloader
			Parent preloaderParent = FXMLLoader.load(getClass().getResource("/view/VwPreloader.fxml"));
			this.brdmostrar.setCenter(preloaderParent);

			// inicio un segundo hilo para la otra vista
			new Thread(() -> {
				try {
					Thread.sleep(1000); // Simula una tarea para que el preloader sea aprecie
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Platform.runLater(() -> {
					try {
						Parent parent = FXMLLoader.load(getClass().getResource("/view/" + url + ".fxml"));
						this.brdmostrar.setCenter(parent);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void setStyle(Node node, String style) {
		node.setStyle(style);
	}

	private void menuProduc(Button boton) {
		if (!this.MostrarBtn) {
			//BOTON  GESTIÓN DE PRODUCTOS 
			this.BtnGeProductos = new Button("Gestión de productos");
			//CARGAR INTERFAZ DEL BOTON  GESTION PRODUCTOS
			this.BtnGeProductos.setOnAction(e -> CargarPagina("VstVerProducto"));
			this.BtnGeProductos.getStylesheets().add("/style/Style.css");
			this.BtnGeProductos.getStyleClass().setAll("btnSubBoton");

			//BOTON  GESTIÓN DE COMPRAS
			this.BtnGeventas = new Button("Gestión Ventas");
			//CARGAR INTERFAZ DEL BOTON  GESTION COMPRAS
			this.BtnGeventas.setOnAction(e -> CargarPagina("VstVerVenta"));
			this.BtnGeventas.getStylesheets().add("/style/Style.css");
			this.BtnGeventas.getStyleClass().setAll("btnSubBoton");

			this.VboxBtnProductos = new VBox(this.BtnGeProductos, this.BtnGeventas);
			this.VboxBtnProductos.setAlignment(Pos.CENTER);
			VboxBtnProductos.setSpacing(5);
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(boton) + 1, this.VboxBtnProductos);
			this.MostrarBtn = true;

		} else {

			this.vboxPadre.getChildren().remove(this.VboxBtnProvee);
			this.vboxPadre.getChildren().remove(this.VboxBtnProductos);
			this.vboxPadre.getChildren().remove(this.VboxBtninfo);
			this.vboxPadre.getChildren().remove(this.VboxBtnpqrs);
			this.vboxPadre.getChildren().remove(this.VboxBtnMas);

			MostrarBtn = false;
		}

	}

	private void menuInfo(Button boton) {
		if (!this.MostrarBtn) {
			//BOTON INFORMES
			this.BtnInformes = new Button("Informes");
			//CARGAR LA INTERFAZ DEL BOTON INFORME
//             this.BtnInformes.setOnAction(e -> CargarPagina("Aqui interfaz de gestion de informes"));
			this.BtnInformes.getStylesheets().add("/style/Style.css");
			this.BtnInformes.getStyleClass().setAll("btnSubBoton");
			this.BtnInformes.setOnAction(e -> CargarPagina("vwTblInformes"));

			//BOTON USUARIO
			this.BtnUsuarios = new Button("Usuarios");
//             CARGAR LA INTERFAZ DEL BOTON USUARIOS
//            this.BtnUsuarios.setOnAction(e -> CargarPagina("Aqui interfaz de gestion del boton usuarios"));
			this.BtnUsuarios.getStylesheets().add("/style/Style.css");
			this.BtnUsuarios.getStyleClass().setAll("btnSubBoton");
			this.BtnUsuarios.setOnAction(e -> CargarPagina("viewBuscarUser"));

			this.VboxBtninfo = new VBox(this.BtnInformes, this.BtnUsuarios);
			this.VboxBtninfo.setAlignment(Pos.CENTER);
			VboxBtninfo.setSpacing(5);
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(boton) + 1, this.VboxBtninfo);
			this.MostrarBtn = true;
		} else {
			this.vboxPadre.getChildren().remove(this.VboxBtnMas);
			this.vboxPadre.getChildren().remove(this.VboxBtnProvee);
			this.vboxPadre.getChildren().remove(this.VboxBtnProductos);
			this.vboxPadre.getChildren().remove(this.VboxBtninfo);
			this.vboxPadre.getChildren().remove(this.VboxBtnpqrs);

			MostrarBtn = false;
		}

	}

	private void menuPqrs(Button boton) {
		if (!this.MostrarBtn) {
			//BOTON  GESTIÓN DE PETICIONES 
			this.BtnPeticiones = new Button("Peticiones");
			//ABRIR INTERFAZ DEL BOTON PETICIONES
			this.BtnPeticiones.setOnAction(e -> CargarPagina("tblPeticiones"));
			this.BtnPeticiones.getStylesheets().add("/style/Style.css");
			this.BtnPeticiones.getStyleClass().setAll("btnSubBoton");

			//BOTON  QUEJAS
			this.BtnQuejas = new Button("Quejas");
			//ABRIR INTERFAZ DEL BOTON QUEJAS
			this.BtnQuejas.setOnAction(e -> CargarPagina("tblQuejas"));
			this.BtnQuejas.getStylesheets().add("/style/Style.css");
			this.BtnQuejas.getStyleClass().setAll("btnSubBoton");

			//BOTON  RECLAMOS
			this.BtnReclamos = new Button("Reclamos");
			//ABRIR INTERFAZ DEL BOTON RECLAMOS
			this.BtnReclamos.setOnAction(e -> CargarPagina("tblReclamos"));
			this.BtnReclamos.getStylesheets().add("/style/Style.css");
			this.BtnReclamos.getStyleClass().setAll("btnSubBoton");

			//BOTON SUGERENCIAS
			this.BtnSugerencias = new Button("Sugerencias");
			//ABRIR INTERFAZ DEL BOTON SUGERENCIAS
			this.BtnSugerencias.setOnAction(e -> CargarPagina("tblSugerencias"));
			this.BtnSugerencias.getStylesheets().add("/style/Style.css");
			this.BtnSugerencias.getStyleClass().setAll("btnSubBoton");

			this.VboxBtnpqrs = new VBox(this.BtnPeticiones, this.BtnQuejas, this.BtnReclamos, this.BtnSugerencias);
			this.VboxBtnpqrs.setAlignment(Pos.CENTER);
			VboxBtnpqrs.setSpacing(5);
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(boton) + 1, this.VboxBtnpqrs);
			this.MostrarBtn = true;
		} else {

			this.vboxPadre.getChildren().remove(this.VboxBtnProvee);
			this.vboxPadre.getChildren().remove(this.VboxBtnProductos);
			this.vboxPadre.getChildren().remove(this.VboxBtninfo);
			this.vboxPadre.getChildren().remove(this.VboxBtnpqrs);
			this.vboxPadre.getChildren().remove(this.VboxBtnMas);
			MostrarBtn = false;
		}

	}

	private void menuProvee(Button boton) {
		if (!this.MostrarBtn) {
			this.btnGePedidos = new Button("Gestión de pedidos");
			//CARGAR LA INTERFAZ DEL BOTON GESTION PRODUCTOS
//             this.btnGePedidos.setOnAction(e -> CargarPagina("Aqui interfaz de gestion de Pedidos"));
			this.btnGePedidos.getStylesheets().add("/style/Style.css");
			this.btnGePedidos.getStyleClass().setAll("btnSubBoton");
			this.btnGePedidos.setOnAction(e -> CargarPagina("vwVerPedidos"));

			//BOTON DE GESTIONAR PEDIDOS
			this.btnGeProvee = new Button("Gestión de proveedores ");
			//CARGAR LA INTERFAZ DEL BOTON DE GESTIONAR Proveedores
			this.btnGeProvee.setOnAction(e -> CargarPagina("VstVerProveedor"));
			this.btnGeProvee.getStylesheets().add("/style/Style.css");
			this.btnGeProvee.getStyleClass().setAll("btnSubBoton");

			this.VboxBtnProvee = new VBox(this.btnGeProvee, this.btnGePedidos);
			this.VboxBtnProvee.setAlignment(Pos.CENTER);
			VboxBtnProvee.setSpacing(5);
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(boton) + 1, this.VboxBtnProvee);
			this.MostrarBtn = true;
		} else {

			this.vboxPadre.getChildren().remove(this.VboxBtnProvee);
			this.vboxPadre.getChildren().remove(this.VboxBtnProductos);
			this.vboxPadre.getChildren().remove(this.VboxBtninfo);
			this.vboxPadre.getChildren().remove(this.VboxBtnpqrs);
			this.vboxPadre.getChildren().remove(this.VboxBtnMas);

			MostrarBtn = false;
		}

	}

	private void menuMas(Button boton) {
		if (!this.MostrarBtn) {
			//BOTON  DEL BOTON CARRUSEL
			this.BtnCarrusel = new Button("Carrusel");
			//CARGAR INTERFAZ DEL BOTON  CARRUSEL
			this.BtnCarrusel.setOnAction(e -> CargarPagina("Viewprincipals"));
			this.BtnCarrusel.getStylesheets().add("/style/Style.css");
			this.BtnCarrusel.getStyleClass().setAll("btnSubBoton");

			//BOTON  DE LUGAR DE ENVIO
			this.BtnLuEnvio = new Button("Puntos físicos");
			//CARGAR INTERFAZ DEL BOTON  DE LUGAR DE ENVIO
			this.BtnLuEnvio.setOnAction(e -> CargarPagina("vwTblPuntosFisicos"));
			this.BtnLuEnvio.getStylesheets().add("/style/Style.css");
			this.BtnLuEnvio.getStyleClass().setAll("btnSubBoton");

			//BOTON  DE Gestion DE PARAMETROS
			this.BtnGePara = new Button("Gestión de parámetros");
			//CARGAR INTERFAZ DEL BOTON  Gestion DE PARAMETROS
			this.BtnGePara.setOnAction(e -> CargarPagina("VwTblParametros"));
			this.BtnGePara.getStylesheets().add("/style/Style.css");
			this.BtnGePara.getStyleClass().setAll("btnSubBoton");

			this.VboxBtnMas = new VBox(this.BtnCarrusel, this.BtnLuEnvio, this.BtnGePara);
			this.VboxBtnMas.setAlignment(Pos.CENTER);
			VboxBtnMas.setSpacing(5);
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(boton) + 1, this.VboxBtnMas);
			this.MostrarBtn = true;

		} else {

			this.vboxPadre.getChildren().remove(this.VboxBtnProvee);
			this.vboxPadre.getChildren().remove(this.VboxBtnProductos);
			this.vboxPadre.getChildren().remove(this.VboxBtninfo);
			this.vboxPadre.getChildren().remove(this.VboxBtnpqrs);
			this.vboxPadre.getChildren().remove(this.VboxBtnMas);
			MostrarBtn = false;
		}
	}

	@FXML
	private void clickPerfil(ActionEvent event) {
		CargarPagina("vwModificarAdmin");
	}

	@FXML
	private void cerrarSession(ActionEvent event) throws IOException {
		if (event.getSource() == this.btncerrar) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Cerrar sesión");
			alert.setHeaderText(null);
			alert.setContentText("¿desea cerrar sesión?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				Stage stage = (Stage) this.btncerrar.getScene().getWindow();
				stage.close();

				//ESTA PARTE VA HACER QUE LA INTERFAZ DEL LOGIN APAREZCA FLOTANTE
				Stage loginFloat = new Stage();
				loginFloat.initModality(Modality.APPLICATION_MODAL);
				loginFloat.setResizable(false);
				Parent root = FXMLLoader.load(getClass().getResource("/view/viewlogin.fxml"));
				Scene popupScene = new Scene(root);
				loginFloat.setScene(popupScene);
				loginFloat.showAndWait();

			}

		}
	}

	@FXML
	private void clickAyuda(ActionEvent event) {
		CargarPagina("VwAyuda");
	}
}
