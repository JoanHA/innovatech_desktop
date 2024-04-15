package controller;

import conexion.Conexion;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.mdlUsuario;
import org.mindrot.jbcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author Dragnell
 */
public class ctrFormRegistros implements Initializable {

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
	private PasswordField pxtContrasena;
	@FXML
	private PasswordField pxtConfirnarContrasena;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnAceptar;
	@FXML
	private TextField txtToken;

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
	@FXML
	private Label clickVideo;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		clickVideo.setOnMouseClicked(e->video());
		mProperties = new Properties();
		cbxTipoDocumentos.setValue("--");
		cargarDocumentos();
	}
	void video(){
		try {
			URI uri = new URI("https://www.youtube.com/watch?v=Q74nxFBCHCI");
			Desktop.getDesktop().browse(uri);
		} catch (IOException | URISyntaxException e) {
		}
	}

	boolean validarVacios() {
		boolean sw = true;
		StringBuilder capturarVacios = new StringBuilder();
		if (txtNombre.getText().isEmpty()) {
			capturarVacios.append("Nombre no puede estar vacío. \n");
			sw = false;
		}
		if (txtApellido.getText().isEmpty()) {
			capturarVacios.append("Apellido no puede estar vacío. \n");
			sw = false;
		}
		if(cbxTipoDocumentos.getValue().equalsIgnoreCase("--")){
			capturarVacios.append("Debes elegir un tipo de documento. \n");
			sw = false;
		}
		if (txtIdentificacion.getText().isEmpty()) {
			capturarVacios.append("Identificación no puede estar vacío. \n");
			sw = false;
		}
		if (txtCelular.getText().isEmpty()) {
			capturarVacios.append("Celular no puede estar vacío. \n");
			sw = false;
		}
		if (txtEmail.getText().isEmpty()) {
			capturarVacios.append("Email  no puede estar vacío. \n");
			sw = false;
		}
		if (txtToken.getText().isEmpty()) {
			capturarVacios.append("Token  no puede estar vacío. \n");
			sw = false;
		}
		if (pxtConfirnarContrasena.getText().isEmpty() || pxtContrasena.getText().isEmpty()) {
			capturarVacios.append("Contraseña no puede estar vacío \n");
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
			capturarValidaciones.append("El nombre no es valido. \n");
			validaciones = false;
		}
		if (!val.validarString(txtApellido.getText().toLowerCase())) {
			capturarValidaciones.append("El apellido no es valido. \n");
			validaciones = false;
		}
		if (!val.validarCelular(txtCelular.getText())) {
			capturarValidaciones.append("El celular no es valido. \n");
			validaciones = false;
		}
		if (!val.validarIdentificacion(txtIdentificacion.getText())) {
			capturarValidaciones.append("La identificación no es valida. \n");
			validaciones = false;
		}
		if (!val.validarEmail(txtEmail.getText())) {
			capturarValidaciones.append("El email no es valido. \n");
			validaciones = false;
		}
		if (!val.validarToken(txtToken.getText())) {
			capturarValidaciones.append("El token no es valido. \n");
			validaciones = false;
		}
		if (!pxtContrasena.getText().equals(pxtConfirnarContrasena.getText())) {
			capturarValidaciones.append("Las contraseñas no son iguales. \n");
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
	private void clickAceptar(ActionEvent event) {
		this.viewValidar = true;
		//valida si los textField están vacíos.
		if (validarVacios()) {

			//Valida que los datos correspondan a su tipo y cumplan con ciertas condiciones.
			if (validarContenido()) {
				if (pxtContrasena.getText().length() > 7) {
					//cifra la contraseña 
					String password;
					password = BCrypt.hashpw(pxtContrasena.getText(), BCrypt.gensalt());

					ctrUsuario ctrUs = new ctrUsuario();
					mdlUsuario mdlUs = new mdlUsuario();

					mdlUs.setDocumento(txtIdentificacion.getText());
					mdlUs.setNombres(txtNombre.getText());
					mdlUs.setApellidos(txtApellido.getText());
					mdlUs.setCelular(txtCelular.getText());
					mdlUs.setEmail(txtEmail.getText());
					mdlUs.setContrasena(password);
					mdlUs.setParam_rol("2");
					mdlUs.setParam_suscripcion("20");
					mdlUs.setUsuarioParam_estado("5");
					mdlUs.setToken(txtToken.getText());
					mdlUs.setParam_tipoDocumento(this.idsDocumentos.get(cbxTipoDocumentos.getValue()));

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
								//Verifica que el usuario fue registrado con exito.
								if (ctrUs.agregarUsuario(mdlUs)) {
									Alert alert = new Alert(Alert.AlertType.INFORMATION);
									alert.setHeaderText(null);
									alert.setTitle("Información");
									alert.setContentText("Registrado correctamente");
									alert.showAndWait();
									try {
										cerrarVentana();
										FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/view/viewlogin.fxml"));
										Parent rootLogin = loaderLogin.load();
										Scene sceneLogin = new Scene(rootLogin);
										Stage stageLogin = new Stage();
										stageLogin.setScene(sceneLogin);
										stageLogin.setResizable(false);
										stageLogin.showAndWait();
									} catch (IOException e) {

									}
								} else {
									Alert alert = new Alert(Alert.AlertType.ERROR);
									alert.setHeaderText(null);
									alert.setTitle("Error");
									alert.setContentText("Error al registrar usuario");
									alert.showAndWait();
								}
							} else {
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setHeaderText(null);
								alert.setTitle("Error");
								alert.setContentText("Código incorrecto.");
								alert.showAndWait();
							}
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}
					}
				} else {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setHeaderText(null);
					alert.setTitle("Contraseña no segura");
					alert.setContentText("Por seguridad la contraseña debe tener 8 o mas caracteres");
					alert.showAndWait();
				}
			}
		}
	}

	@FXML
	private void clickCancelar(ActionEvent event) {
		cerrarVentana();
	}

	void cerrarVentana() {
		Stage stage = (Stage) btnAceptar.getScene().getWindow();
		stage.close();
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
		asunto = "Confirmación de Correo";
		contenido = "Su codigo de verificación es: " + codigo;
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

	@FXML
	private void eventKey(KeyEvent event) {
		Object evt = event.getSource();
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
			if (contarCaracteres(txtNombre.getText(), " ") >= 1) {
				if (event.getCharacter().equals(" ")) {
					event.consume();
				}
			}
		}
		if (evt.equals(txtApellido)) {

			if (!letrasNombre.contains(event.getCharacter())) {
				event.consume();
			}
			if (contarCaracteres(txtApellido.getText(), " ") >= 1) {
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

	void cargarDocumentos() {
		this.documentosList.clear();
		Conexion con = Conexion.getInstance();
		String sql = "SELECT name,id FROM params WHERE paramtype_id = 15";
		ResultSet resultado = con.consultar(sql);
		try {
			while (resultado.next()) {
				// cargo la lista de departamentos
				String clave = null, valor = null;
				clave = resultado.getString("name");
				valor = resultado.getString("id");
				this.idsDocumentos.put(clave, valor);
				documentosList.add(resultado.getString("name"));
			}
			this.cbxTipoDocumentos.setItems(documentosList);
		} catch (SQLException e) {
			System.out.println("Error en cargar estados: " + e.getMessage());
		}
		con.cerrarConexion();
	}

	public int contarCaracteres(String cadena, String letra) {
		int posicion, contador = 0;
		//se busca la primera vez que aparece
		posicion = cadena.indexOf(letra);
		while (posicion != -1) { //mientras se encuentre el caracter
			contador++;           //se cuenta
			//se sigue buscando a partir de la posición siguiente a la encontrada                                 
			posicion = cadena.indexOf(letra, posicion + 1);
		}
		return contador;
	}
}
