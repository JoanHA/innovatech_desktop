/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import conexion.Conexion;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import model.MdlInformes;

/**
 * FXML Controller class
 *
 * @author braya
 */
public class ctrVerInformes implements Initializable {

	@FXML
	private TextField txtTipoInforme;
	@FXML
	private ImageView imagen;
	@FXML
	private TextField txtMotivoInforme;
	@FXML
	private TextArea txtDescripcion;
	@FXML
	private Label lblTipoInforme;
	@FXML
	private Text lblMotivoInforme;
	@FXML
	private Text lblDescripcionInforme;
	@FXML
	private Button btnCerrar;

	//Variables para reenviar el correo
	@FXML
	private String correo;
	private String contrasenia;
	private String motivo;
	private String descripcion;
	private Properties mProperties;
	private Session mSession;
	private MimeMessage mCorreo;
	private String receptor;
	String url = "";

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		mProperties = new Properties();
	}

	void initAttributtes(MdlInformes verInfo) {
//        txtTipoInforme.setText(verInfo.getTipo());
		txtDescripcion.setText(verInfo.getDescripcioInfo());
		txtMotivoInforme.setText(verInfo.getTitulo());
		Image img = new Image("https://innovatechcol.com.co/img/informes/"+verInfo.getImagen());
		this.imagen.setImage(img);
		this.url = "https://innovatechcol.com.co/img/informes/" + verInfo.getImagen();
	}

	@FXML
	private void clickCerrar(ActionEvent event) {
		Stage mysStage = (Stage) this.btnCerrar.getScene().getWindow();
		mysStage.close();
	}

	public void datosAdmin() {
		Conexion conectar = Conexion.getInstance();
		String sql = "SELECT email, token, param_rol FROM users WHERE param_rol = '2'";
		ResultSet rs = conectar.consultar(sql);
		try {
			rs.next();
			this.correo = rs.getString("email");
			this.contrasenia = rs.getString("token");
		} catch (SQLException e) {
			System.out.println("Error al consultar datos" + e);
		}
		conectar.cerrarConexion();
	}

	//Metodo para sacar correo de la BD
	public void sacarEmail() {
		Conexion conectar = Conexion.getInstance();
		String sql = "SELECT email FROM users WHERE param_suscription = 20";
		ResultSet rs = conectar.consultar(sql);
		try {
			rs.last();
			int rowCount = rs.getRow();
			rs.beforeFirst();
			String[] Ecorreo = new String[rowCount];

			int i = 0;
			while (rs.next()) {
				Ecorreo[i] = rs.getString("Email");
				i++;
			}
			crearEmail(Ecorreo);
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		conectar.cerrarConexion();
	}
	//Metodo para validar que ningun campos este vacio

	boolean validacionCampos() {
		StringBuilder capturarVacios = new StringBuilder();
		boolean vacio = true;

//        if (txtTipoInforme.getText().isEmpty()) {
//            capturarVacios.append("Tipo de Informe Vacio\n");
//            vacio = false;
//        }
		if (txtMotivoInforme.getText().isEmpty()) {
			capturarVacios.append("Motivo de Informe vacío\n");
			vacio = false;
		}

		if (txtDescripcion.getText().isEmpty()) {
			capturarVacios.append("Descripción del informe vacía\n");
			vacio = false;

		}
		if (!vacio) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Campos vacíos");
			alert.setContentText(capturarVacios.toString());
			alert.showAndWait();
		}
		return vacio;
	}

	private void crearEmail(String[] receptores) {
		datosAdmin();
		motivo = txtMotivoInforme.getText().trim();
		descripcion = txtDescripcion.getText().trim();

		// Simple mail transfer protocol
		mProperties.put("mail.smtp.host", "smtp.gmail.com");
		mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		mProperties.setProperty("mail.smtp.starttls.enable", "true");
		mProperties.setProperty("mail.smtp.port", "587");
		mProperties.setProperty("mail.smtp.user", correo);
		mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		mProperties.setProperty("mail.smtp.auth", "true");

		mSession = Session.getDefaultInstance(mProperties);

		try {
			BodyPart texto = new MimeBodyPart();
			texto.setText(descripcion);
			BodyPart image = new MimeBodyPart();
			try {
				image.setDataHandler(new DataHandler(new URL(url)));
			} catch (MalformedURLException ex) {
				Logger.getLogger(ctrVerInformes.class.getName()).log(Level.SEVERE, null, ex);
			}
			image.setFileName("imagen.jpg");
			MimeMultipart partes = new MimeMultipart();
			partes.addBodyPart(texto);
			partes.addBodyPart(image);

			mCorreo = new MimeMessage(mSession);
			mCorreo.setFrom(new InternetAddress(this.correo));

			// Crear múltiples destinatarios
			InternetAddress[] recipientAddresses = new InternetAddress[receptores.length];
			for (int i = 0; i < receptores.length; i++) {
				recipientAddresses[i] = new InternetAddress(receptores[i]);
			}
			mCorreo.setRecipients(Message.RecipientType.TO, recipientAddresses);

			mCorreo.setSubject(motivo);
			mCorreo.setContent(partes);
			sendEmail();
		} catch (AddressException ex) {
			Logger.getLogger(ctrInformes.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MessagingException ex) {
			Logger.getLogger(ctrInformes.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void sendEmail() {
		datosAdmin();
		try {
			Transport mTransport = mSession.getTransport("smtp");
			mTransport.connect(correo, contrasenia);
			mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
			mTransport.close();
		} catch (NoSuchProviderException ex) {
			Logger.getLogger(ctrInformes.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MessagingException ex) {
			Logger.getLogger(ctrInformes.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@FXML
	private void clickReenviar(ActionEvent event) {
		if (validacionCampos()) {
			try {
				sacarEmail();
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Información");
				alert.setContentText("Informe enviado exitosamente.");
				alert.showAndWait();

				//Stage mysStage = (Stage) this.btnEnviar.getScene().getWindow();
				// mysStage.close();
			} catch (Exception e) {
			}
		}
	}

}
