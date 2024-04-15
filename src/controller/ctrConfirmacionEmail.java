/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SENA
 */
public class ctrConfirmacionEmail implements Initializable {

    @FXML
    private TextField txtCodigo;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAceptar;
    
    private String codigo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void clickCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickAceptar(ActionEvent event) {
        this.codigo = txtCodigo.getText();
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();
        String numeros = "0123456789";
        if(evt.equals(txtCodigo)){
            if(!numeros.contains(event.getCharacter())){
                event.consume();
            }
        }
        if (txtCodigo.getText().length() >= 6) {
            event.consume();
        }
    }


    public String getCodigo() {
        return codigo;
    }

}