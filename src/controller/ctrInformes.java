/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import FTP.ConexionFTP;
import conexion.Conexion;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
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
 *
 * @author Andres Montoya
 */
public class ctrInformes implements Initializable {

	@FXML

	private Label lblTipoInforme;

	@FXML
	private Text lblMotivoInforme;

	@FXML
	private Text lblDescripcionInforme;

	@FXML
	private TextField txtTipoInforme;

	@FXML
	private TextField txtMotivoInforme;

	@FXML
	private TextArea txtDescripcion;

	@FXML
	private Button btnEnviar;

	@FXML
	private Button btnCargar;

	@FXML
	private ImageView imagen;
	//Variables para la creacion del Correo
	private String correo;
	private String contrasenia;
	private String tipoInforme;
	private String motivo;
	private String descripcion;
	private Properties mProperties;
	private Session mSession;
	private MimeMessage mCorreo;
	private String receptor;

	//Variable para sacar la URL de las Imagenes
	private File file;
	private String rutaAbsoluta = "";
	@FXML
	private Button btnVerInfo;

	@Override

	//Contraseña generada en gmail: mysmweafcegpbxpm
	public void initialize(URL url, ResourceBundle rb) {
		mProperties = new Properties();
	}

	//Metodo para cargar la imagen del Informe
	@FXML
	private void abrirArchivo(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Seleccionar Imagen");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de imagen (*.jpg, *.jpeg, *.png)", "*.jpg", "*.jpeg", "*.png");
		Stage stage = (Stage) this.imagen.getScene().getWindow();
		fileChooser.getExtensionFilters().add(extFilter);
		file = fileChooser.showOpenDialog(stage);
		//System.out.println(mFile);
		if (file != null) {
			Image imagee = new Image(file.toURI().toString());
			this.imagen.setImage(imagee);
		}

		this.rutaAbsoluta = file.toURI().toString().replace("file:/", "");
	}

	//Metodo para enviar al servidor(LocalHost)
//    private String guardarImagen(File nombreImg) {
//        Image img = imagen.getImage();
//        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(img, null);
//
//        String ubicacionCarpeta = "src/images/informes/";
//        File carpetas = new File(ubicacionCarpeta);
//        carpetas.mkdirs();
//        String nombreImagen = UUID.randomUUID().toString() + ".png";
//        String ubicacionImagen = ubicacionCarpeta + nombreImagen;
//
//        try {
//            ImageIO.write(bufferedImage, "png", new File(ubicacionImagen));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return ubicacionImagen;
//    }
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

	//Metodo para limpiar los campos despues de enviar un informe
	void vaciarCampos() {
		txtTipoInforme.setText("");
		txtMotivoInforme.setText("");
		txtDescripcion.setText("");
		imagen.setImage(null);
	}

	// Metodo para el encio de Informacion para el Informe 
	@FXML
	private void clickEnviar(ActionEvent event) {
		if (validacionCampos()) {
			try {
				MdlInformes modelo = new MdlInformes();

//              modelo.setTipo(txtTipoInforme.getText());
				modelo.setTitulo(txtMotivoInforme.getText());
				modelo.setDescripcioInfo(txtDescripcion.getText());
				modelo.setEstadoInfo("1");
				modelo.setFechaRegistroInfo(fechaRegistro());

				guardarInforme(modelo);
				sacarEmail(event);

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Información");
				alert.setContentText("Informe Enviado");
				vaciarCampos();
				alert.showAndWait();

				//Stage mysStage = (Stage) this.btnEnviar.getScene().getWindow();
				// mysStage.close();
			} catch (Exception e) {
			}
		}
	}

	//Metodo para obtener el id de los usuarios
	public int obtenerIdUsuario() {
		Conexion con = Conexion.getInstance();
		String sql = "SELECT id FROM users WHERE param_rol = '2'";
		ResultSet resultado = con.consultar(sql);
		int idUsuario = 0;
		try {

			while (resultado.next()) {
				idUsuario = resultado.getInt("id");
			}
			return idUsuario;

		} catch (Exception e) {
			System.out.println("No se encontraron los Usuarios");

		}
		con.cerrarConexion();
		return 0;
	}

	public String fechaRegistro() {
		Instant instant = Instant.now();
		Timestamp timestamp = Timestamp.from(instant);

		return timestamp.toString();
	}

	//Metodo para poder abrir la interfaz de ver los Informes
	/**
	 *
	 * @param Modelo
	 */
	//Sentencia para insertar datos en la BD  
	public void guardarInforme(MdlInformes Modelo) {
		//aqui se incializa la conexion
		Conexion con = Conexion.getInstance();
		ConexionFTP ftp = new ConexionFTP();

		try {
			//aqui va la sentencia que va a traer los datos de la  base de datos
			String sql = "INSERT INTO reports (subject,body,image,param_state,created_at,updated_at)VALUES('" + Modelo.getTitulo() + "','" + Modelo.getDescripcioInfo() + "','" + ftp.subirImg("informes", rutaAbsoluta) + "','"
				+ Modelo.getEstadoInfo() + "','" + fechaRegistro() + "'," + null + ")";

			//resulset donde se van a guardar los datos que vienen de la base de datos
			if (con.ejecutar(sql)) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("Informe enviado.");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Error al enviar informe.");
				alert.showAndWait();
			}
			//guardando los datos de la base de datos para mandarlos al modelo
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Error al enviar informe: " + e.getMessage());
			alert.showAndWait();
		}
		con.cerrarConexion();

	}

	//Metodo para consultar Email y token del admin.
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
		}finally{
		conectar.cerrarConexion();
		}
	}

	//Metodo para sacar correo de la BD
	public void sacarEmail(ActionEvent event) {
		Conexion conectar = Conexion.getInstance();
		String sql = "SELECT email FROM users WHERE param_suscription = 20 AND param_state = 5";
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
		}finally{
		conectar.cerrarConexion();
		}
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
			image.setDataHandler(new DataHandler(new FileDataSource(file)));
			image.setFileName("imagen.jpeg");

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
			System.out.println("Correo Creado");
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
			System.out.println("Correo Enviado");
		} catch (NoSuchProviderException ex) {
			Logger.getLogger(ctrInformes.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MessagingException ex) {
			Logger.getLogger(ctrInformes.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
