/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import FTP.ConexionFTP;
import javafx.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Mdlcarrusel;
import org.controlsfx.control.Notifications;
import conexion.Conexion;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.util.converter.LocalDateStringConverter;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author jhonMurillo
 */
public class viewinsertarController implements Initializable {

	public Boolean verdadero = null;
	String image;
	LocalDateStringConverter Fecha_registro = new LocalDateStringConverter();
	LocalDateStringConverter Fecha_modificacion = new LocalDateStringConverter();
	File file;
//    String local = "http://localhost/Carrusel/";

	@FXML
	private ToggleGroup event_check;
	@FXML
	private Button btnarchivo;
	@FXML
	private RadioButton tgactivo;
	@FXML
	private RadioButton tginactivo;
	@FXML
	private Button btnguardar;
	@FXML
	private ImageView imagen;
	@FXML
	private Spinner<Integer> sp;
	SpinnerValueFactory<Integer> spf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 8, 1);
	@FXML
	private Label lblimagen;
	@FXML
	private TextField txtname;

	String query;
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet rs;
	Mdlcarrusel carrusel;
	String estate = "0";
	ViewprincipalsController controlador = new ViewprincipalsController();
	private ObservableList<Mdlcarrusel> carruselList = FXCollections.observableArrayList();

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		sp.setValueFactory(spf);
	}

	public void cerrarventana() {
		Stage stage = (Stage) btnguardar.getScene().getWindow();
		stage.close();
	}

	public void quitar() {
		Notifications vacio = Notifications.create()
			.title("Campos vacíos")
			.text("Campos vacíos")
			.hideAfter(Duration.seconds(2))
			.position(Pos.CENTER);
		vacio.showError();
	}

	public static boolean aspectoValido(String imagePath, double targetAspectRatio) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setHeaderText(null);
		alert.setTitle("Recomendación de aspecto.");
		alert.setContentText("Es recomendable que la relación de aspecto sea de 10:4 para no afectar visualmente la página  web.");
		alert.showAndWait();
		return true;
	}

	@FXML
	private void guardar(ActionEvent event) {
		boolean cierto = true;
		StringBuilder respuestas = new StringBuilder();
		String img = lblimagen.getText();
		if (img.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("¡Imagen obligatoria!");
			alert.setContentText("Debes de agregar una imagen para realizar el registro correctamente");
			alert.showAndWait();
			cierto = false;
			respuestas.append("Este campo es obligatorio\n");
		}
		for (int i = 0; i < carruselList.size(); i++) {
			if (carruselList.get(i).getPosicion() == sp.getValue()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("¡Posición no disponible!");
				alert.setContentText("La posición que intentas escoger ya está registrada");
				alert.showAndWait();
				cierto = false;
				respuestas.append("Este campo es obligatorio\n");
			}
		}
		if (cierto) {
			registrar();
			notificar();
			cerrarventana();
		}
	}

	public void notificar() {
		Image img = new Image("/image/cheque.png");
		Notifications exito = Notifications.create()
			.title("Felicidades")
			.text("Registro exitoso")
			.graphic(new ImageView(img))
			.hideAfter(Duration.seconds(2))
			.position(Pos.CENTER)
			.hideCloseButton()
			.onAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("Clicked on notificación");
				}
			});
		exito.show();
	}

	@FXML
	private void abrirarchivo(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Seleccionar imagen");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de imagen (*.jpg, *.jpeg, *.png)", "*.jpg", "*.jpeg", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);
		Stage stage = (Stage) imagen.getScene().getWindow();
		this.file = fileChooser.showOpenDialog(stage);
		if (this.file != null) {
			if (aspectoValido(this.file.toURI().toString(), 2.5)) {
				Image image = new Image(this.file.toURI().toString());
				imagen.setImage(image);
				lblimagen.setText(this.file.toURI().toString());
			} else {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setHeaderText(null);
				alert.setTitle("Relación de aspecto no valida");
				alert.setContentText("La relación de aspecto no es válida te recomendamos que sea de 10:4 o cercana");
				alert.showAndWait();
			}
		}
	}

	private void estado() {
		if (event_check.getSelectedToggle() == tgactivo) {
			this.estate = "5";
		} else if (event_check.getSelectedToggle() == tginactivo) {
			this.estate = "6";
		}
	}

	private void registrar() {
		estado();
		Conexion con = Conexion.getInstance();
		ConexionFTP ftp = new ConexionFTP();
		String imagen = lblimagen.getText().replace("file:/", "");
		try {
			query = "Insert into carrusels(position,image,param_state)"
				+ "value('" + sp.getValue() + "','" + ftp.subirImg("carrusel", imagen) + "','"
				+ this.estate + "')";
			con.ejecutar(query);
		} catch (Exception e) {
			System.out.println("No se pudo hacer el registo" + e);
		}
		con.cerrarConexion();
	}

	void initAttributtes(ObservableList<Mdlcarrusel> carruselList) {
		this.carruselList = carruselList;
	}
}
