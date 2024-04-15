/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.mdlUsuario;
import org.mindrot.jbcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author Dragnell
 */
public class viewloginController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblUsuario;
    @FXML
    private TextField txtUsuario;
    @FXML
    private Label lblContraseña;
    @FXML
    private PasswordField txtContraseña;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAceptar;
    @FXML
    private Label lblOlvideContrasena;

    private String retornarcontraseña = "";
    private String retornarEmail = "";

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        credenciales();
    }

    @FXML
    private void eventKey(KeyEvent event) {
        if (event.getCode()==KeyCode.ENTER) {
            login();
        }
    }

    @FXML
    private void clikCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickAceptar(ActionEvent event) {
        login();
    }

    void login() {
        mdlUsuario mdlUs = new mdlUsuario();
        String email;
        String password;
        email = txtUsuario.getText();
        password = txtContraseña.getText();
        if (email.equals(this.retornarEmail)
                && BCrypt.checkpw(password, this.retornarcontraseña)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Información");
            alert.setContentText("Inicio de sesión exitoso.");
            alert.showAndWait();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewMenu.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                Stage mysStage = (Stage) this.btnAceptar.getScene().getWindow();
                mysStage.close();
            } catch (IOException e) {

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Usuario y/o contraseña incorrecta");
            alert.showAndWait();
        }
    }

    public void credenciales() {
        Conexion con = Conexion.getInstance();
        String sql ="select password,email FROM users WHERE users.param_rol = 2";
		ResultSet resultado = con.consultar(sql);
        try {
            while (resultado.next()) {
                this.retornarEmail = resultado.getString("email");
                this.retornarcontraseña = resultado.getString("password");
            }
        } catch (SQLException e) {
        }
        con.cerrarConexion();
    }


    @FXML
    private void clickolvidecontra(MouseEvent event) {
        
           try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewRestablecerContrasena.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(viewloginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    public void CargarPagina(String url) {
//        try {
//            Parent parent;
//            parent = FXMLLoader.load(getClass().getResource("/view/" + url + ".fxml"));
//            this.brdmostrar.setCenter(parent);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
}