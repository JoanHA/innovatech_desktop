/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import conexion.Conexion;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.mindrot.jbcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author Andres-Estudios
 */
public class ViewRestablecerContrasenaController implements Initializable {

    @FXML
    private Label lblTemporalC;
    @FXML
    private Label lblNuevaC;
    @FXML
    private Label lblConfirmarC;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    private static String emailFrom;
    private static String passwordFrom;
    private String receptor;
    private String asunto;
    private String contenido;

    private Properties mProperties;
    private Session mSession;
    private MimeMessage mCorreo;

    private String codigo;
    @FXML
    private TextField txtCodigo;
    @FXML
    private PasswordField pxtNueva;
    @FXML
    private PasswordField pxtConfirmar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mProperties = new Properties();
        codigo = GenerarContrasena();
        sacarEmail();
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

    public void sacarEmail() {
        Conexion conectar = Conexion.getInstance();
        String sql = //"SELECT users.token,users.email FROM personas JOIN users ON personas.id = "
                //+ "users.persona_id WHERE "
                //+ "users.param_rol = 2";
        "select email,token FROM users WHERE users.param_rol= 2";
        ResultSet rs = conectar.consultar(sql);
        String token = "";
        String correo = "";
        try {
            while (rs.next()) {
                token = rs.getString("token");
                correo = rs.getString("email");
            }

        } catch (SQLException e) {
            System.out.println("Error al sacar el email " + e);
        }
        passwordFrom = token;
        emailFrom = correo;
        conectar.cerrarConexion();
        crearEmail();
        
    }

    private void crearEmail() {
        // Simple mail transfer protocol
        asunto = "codigo verificación";
        contenido = "su codigo de verificación es: \n<h1> " + this.codigo + "</h1>";
        receptor = emailFrom;

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
            System.out.println("correo creado");
            sendEmail();
        } catch (AddressException ex) {
            Logger.getLogger(ViewRestablecerContrasenaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ViewRestablecerContrasenaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void sendEmail() {
        try {
            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect(emailFrom, passwordFrom);
            System.out.println(passwordFrom);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();
            System.out.println("correo enviado a: " + receptor);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(ViewRestablecerContrasenaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ViewRestablecerContrasenaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();
        String numeros = "0123456789";
        if (evt.equals(txtCodigo)) {
            if (!numeros.contains(event.getCharacter())) {
                event.consume();
            }
        }
        if (txtCodigo.getText().length() >= 6) {
            event.consume();
        }
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        cerrarVentana();
    }

    void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
        ctrFechas fh = new ctrFechas();
        if (txtCodigo.getText().equals(this.codigo)) {
            if (!pxtNueva.getText().isEmpty() || !pxtConfirmar.getText().isEmpty()) {
                if (pxtNueva.getText().equals(pxtConfirmar.getText())) {
                    if (pxtNueva.getText().length() > 7) {
                        Conexion con = Conexion.getInstance();
                        String password = pxtNueva.getText();
                        password = BCrypt.hashpw(password, BCrypt.gensalt());
                        String sql = "UPDATE users SET password='" + password + "', "
                                + "updated_at = '"+fh.fechaHoy()+"' "
                                + "where param_rol ='2'";
                        if (con.ejecutar(sql)) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setTitle("Informacion");
                            alert.setContentText("Contraseña restablecida.");
                            alert.showAndWait();
                            cerrarVentana();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText(null);
                            alert.setTitle("Error");
                            alert.setContentText("Error al restablecer la contraseña.");
                            alert.showAndWait();
                        }
                        con.cerrarConexion();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText(null);
                        alert.setTitle("Contraseña no segura");
                        alert.setContentText("Por seguridad la contraseña debe tener 8 o mas caracteres");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("Las contraseñas no son iguales.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Los campos no pueden estar vacíos.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Código incorrecto.");
            alert.showAndWait();
        }
    }

}