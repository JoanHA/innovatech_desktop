package controller;

import conexion.Conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author Dragnell
 */
public class ctrModificarAdmin implements Initializable {

	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtApellido;
	@FXML
	private TextField txtIdentificacion;
	@FXML
	private TextField txtCelular;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtToken;
	@FXML
	private Button btnAceptar;

	private String id;
	private static String emailFrom;
	private static String passwordFrom;
	private String receptor;
	private String asunto;
	private String contenido;

	private Properties mProperties;
	private Session mSession;
	private MimeMessage mCorreo;
	private String codigo;
	private boolean viewValidar;
	@FXML
	private ComboBox<String> cbxTipoDocumentos;

	private ObservableList<String> documentosList = FXCollections.observableArrayList();
	Map<String, String> idsDocumentos = new HashMap<>();
	Map<String, String> idsDocumentosRevers = new HashMap<>();

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		mProperties = new Properties();
		cargarDocumentos();
		consultarDatos();
	}

	void consultarDatos() {
		Conexion con = Conexion.getInstance();
		String sql = "SELECT * FROM users WHERE users.param_rol = 2";
		ResultSet resultado = con.consultar(sql);
		try {
			while (resultado.next()) {
				txtApellido.setText(resultado.getString("last_name").toUpperCase());
				txtNombre.setText(resultado.getString("first_name").toUpperCase());
				txtCelular.setText(resultado.getString("phone"));
				txtEmail.setText(resultado.getString("email"));
				txtIdentificacion.setText(resultado.getString("document"));
				txtToken.setText(resultado.getString("token"));
				cbxTipoDocumentos.setValue(this.idsDocumentosRevers.get(resultado.getString("param_type")));
			}
			id = txtIdentificacion.getText();
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}
		con.cerrarConexion();
	}

	@FXML
	private void clckContraseña(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewRestablecerContrasena.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException ex) {
			Logger.getLogger(ctrModificarAdmin.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	boolean validarVacios() {
		boolean sw = true;
		StringBuilder capturarVacios = new StringBuilder();
		if (txtNombre.getText().isEmpty()) {
			capturarVacios.append("Nombre no puede estar vacío \n");
			sw = false;
		}
		if (txtApellido.getText().isEmpty()) {
			capturarVacios.append("Apellido no puede estar vacío \n");
			sw = false;
		}
		if (txtIdentificacion.getText().isEmpty()) {
			capturarVacios.append("Identificación no puede estar vacío \n");
			sw = false;
		}
		if (txtCelular.getText().isEmpty()) {
			capturarVacios.append("Celular no puede estar vacío \n");
			sw = false;
		}
		if (txtEmail.getText().isEmpty()) {
			capturarVacios.append("Email  no puede estar vacío \n");
			sw = false;
		}
		if (txtToken.getText().isEmpty()) {
			capturarVacios.append("Token  no puede estar vacío \n");
			sw = false;
		}
		if (!sw) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Campos vacíos");
			alert.setContentText(capturarVacios.toString());
			alert.showAndWait();
		}
		return sw;
	}

	boolean validarContenido() {
		ctrValidaciones val = new ctrValidaciones();
		boolean validaciones = true;
		//Valida que los datos correspondan a su tipo y cumplan con ciertas condiciones.
		StringBuilder capturarValidaciones = new StringBuilder();
		capturarValidaciones.append("");
		if (!val.validarString(txtNombre.getText().toLowerCase())) {
			capturarValidaciones.append("El nombre no es valido \n");
			validaciones = false;
		}
		if (!val.validarString(txtApellido.getText().toLowerCase())) {
			capturarValidaciones.append("El apellido no es valido \n");
			validaciones = false;
		}
		if (!val.validarCelular(txtCelular.getText())) {
			capturarValidaciones.append("El celular no es valido \n");
			validaciones = false;
		}
		if (!val.validarIdentificacion(txtIdentificacion.getText())) {
			capturarValidaciones.append("La identificación no es valida \n");
			validaciones = false;
		}
		if (!val.validarEmail(txtEmail.getText())) {
			capturarValidaciones.append("El email no es valido \n");
			validaciones = false;
		}
		if (!val.validarToken(txtToken.getText())) {
			capturarValidaciones.append("El token no es valido \n");
			validaciones = false;
		}
		if (!validaciones) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Campos no validos");
			alert.setContentText(capturarValidaciones.toString());
			alert.showAndWait();
		}
		return validaciones;
	}

	@FXML
	private void clickModificar(ActionEvent event) {
		this.viewValidar = true;
		//valida si los textField estan vacios.
		if (validarVacios()) {
			if (validarContenido()) {
				Instant instant = Instant.now();
				Timestamp timestamp = Timestamp.from(instant);
				String nombre, apellido, celular, email, identificacion, token, tipo;
				
				nombre = txtNombre.getText().toLowerCase();
				apellido = txtApellido.getText().toLowerCase();
				celular = txtCelular.getText();
				email = txtEmail.getText().toLowerCase();
				identificacion = txtIdentificacion.getText();
				token = txtToken.getText().toLowerCase();
				tipo = idsDocumentos.get(cbxTipoDocumentos.getValue());
				
				this.codigo = GenerarContrasena();
				crearEmail();
				
				if (this.viewValidar) {
					try {
						FXMLLoader loaderVerificar = new FXMLLoader(getClass().getResource("/view/VwConfirmacionEmail.fxml"));
						Parent rootVerificar = loaderVerificar.load();
						ctrConfirmacionEmail controlador = loaderVerificar.getController();
						Scene sceneVerificar = new Scene(rootVerificar);
						Stage stageVerificar = new Stage();
						stageVerificar.setScene(sceneVerificar);
						stageVerificar.showAndWait();

						if (controlador.getCodigo().equals(this.codigo)) {
							Conexion con = Conexion.getInstance();
							String sql = "UPDATE users "
							 + "set first_name = '" + nombre + "', last_name = '" + apellido + "', "
							 + "document = '" + identificacion + "', updated_at = '" + timestamp + "', "
							 + "email = '" + email + "', phone = '" + celular + "', token = '" + token + "', "
							 + "param_type = "+tipo+" where document = '" + this.id + "'";
							if (con.ejecutar(sql)) {
								con.cerrarConexion();
								Alert alert = new Alert(Alert.AlertType.INFORMATION);
								alert.setHeaderText(null);
								alert.setTitle("Información.");
								alert.setContentText("Datos modificados.");
								alert.showAndWait();
								consultarDatos();
							} else {
								con.cerrarConexion();
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setHeaderText(null);
								alert.setTitle("Error.");
								alert.setContentText("Error al modificar los datos.");
								alert.showAndWait();
							}
						} else {
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setHeaderText(null);
							alert.setTitle("Error");
							alert.setContentText("El código no coincide.");
							alert.showAndWait();
						}
					} catch (IOException ex) {
						Logger.getLogger(ctrModificarAdmin.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
		}
	}

	static String GenerarContrasena() {
		char[] numeros = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
		StringBuilder codigo = new StringBuilder();
		codigo.append(numeros);
		StringBuilder retornoCod = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			int cantidadNumeros = codigo.length();
			int NumeroRamdom = (int) (Math.random() * cantidadNumeros);
			retornoCod.append((codigo.toString()).charAt(NumeroRamdom));
		}
		return retornoCod.toString();
	}

	private void crearEmail() {
		asunto = "Confirmacion de Correo";
		contenido = "Su codigo de verificacion es: " + codigo;
		receptor = txtEmail.getText().trim();
		emailFrom = txtEmail.getText().trim();
		passwordFrom = txtToken.getText().trim();

		// Simple mail transfer protocol
		mProperties.put("mail.smtp.host", "smtp.gmail.com");
		mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		mProperties.setProperty("mail.smtp.starttls.enable", "true");
		mProperties.setProperty("mail.smtp.port", "587");
		mProperties.setProperty("mail.smtp.user", emailFrom);
		mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		mProperties.setProperty("mail.smtp.auth", "true");

		mSession = Session.getDefaultInstance(mProperties);

		try {
			mCorreo = new MimeMessage(mSession);
			mCorreo.setFrom(new InternetAddress(emailFrom));
			mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
			mCorreo.setSubject(asunto);
			mCorreo.setText(contenido, "UTF-8", "html");
			sendEmail();
		} catch (AddressException ex) {
			Logger.getLogger(ctrFormRegistros.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MessagingException ex) {
			Logger.getLogger(ctrFormRegistros.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void sendEmail() {
		try {
			Transport mTransport = mSession.getTransport("smtp");
			mTransport.connect(emailFrom, passwordFrom);
			mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
			mTransport.close();
		} catch (MessagingException ex) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("Por favor comprueba que los campos Email y Token estén correctos "
			 + "y también tu conexión a internet.");
			this.viewValidar = false;
			alert.showAndWait();
		}
	}

	void cargarDocumentos() {
		this.documentosList.clear();
		Conexion con = Conexion.getInstance();
		String sql = "SELECT name,id FROM params WHERE paramtype_id = 15";
		ResultSet rs = con.consultar(sql);
		try {
			while (rs.next()) {
				// cargo la lista de departamentos
				String clave = null, valor = null;
				clave = rs.getString("name");
				valor = rs.getString("id");
				this.idsDocumentosRevers.put(valor, clave);
				this.idsDocumentos.put(clave, valor);
				documentosList.add(rs.getString("name"));
			}
			this.cbxTipoDocumentos.setItems(documentosList);
		} catch (SQLException e) {
			System.out.println("Error en cargar estados: " + e.getMessage());
		}
		con.cerrarConexion();
	}

	@FXML
	private void eventKey(KeyEvent event) {
		Object evt = event.getSource();
		ctrFormRegistros registros = new ctrFormRegistros();
		String numeros = "0123456789";
		String letrasNombre = "abcdefghijklmnñopqrstuvwxyz ";
		String letrasToken = "abcdefghijklmnñopqrstuvwxyz";
		String letrasEmail = letrasToken + "@." + numeros;

		letrasNombre = letrasNombre + letrasNombre.toUpperCase();
		if (evt.equals(txtCelular)) {
			if (!numeros.contains(event.getCharacter()) || txtCelular.getText().length() >= 10) {
				event.consume();
			}
		}
		if (evt.equals(txtIdentificacion)) {
			if (!numeros.contains(event.getCharacter()) || txtIdentificacion.getText().length() >= 10) {
				event.consume();
			}
		}
		if (evt.equals(txtNombre)) {

			if (!letrasNombre.contains(event.getCharacter())) {
				event.consume();
			}
			if (registros.contarCaracteres(txtNombre.getText(), " ") >= 1) {
				if (event.getCharacter().equals(" ")) {
					event.consume();
				}
			}
		}
		if (evt.equals(txtApellido)) {

			if (!letrasNombre.contains(event.getCharacter())) {
				event.consume();
			}
			if (registros.contarCaracteres(txtApellido.getText(), " ") >= 1) {
				if (event.getCharacter().equals(" ")) {
					event.consume();
				}
			}
		}
		if (evt.equals(txtToken)) {
			if (!letrasToken.contains(event.getCharacter()) || txtToken.getText().length() >= 16) {
				event.consume();
			}
		}
		if (evt.equals(txtEmail)) {
			if (!letrasEmail.contains(event.getCharacter())) {
				event.consume();
			}
		}
	}
}
