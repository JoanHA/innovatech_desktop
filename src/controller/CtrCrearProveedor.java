package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class CtrCrearProveedor implements Initializable {

    @FXML
    private Button btnAgregar;
    @FXML
    private TextField txtNombreLegal;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtNit;
    @FXML
    private TextField txtNombreComercial;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtCelular;
    @FXML
    private ComboBox<String> cmbEstado;
    
    Conexion con;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setEstado();
    }
    
    @FXML
    private void txtNitKeyTypedEvent(KeyEvent event) {
        if (txtNit.getText().length() >= 15){
            event.consume();
        }
    }

    @FXML
    private void txtNombreLegalKeyTypedEvent(KeyEvent event) {
        if (txtNombreLegal.getText().length() >= 100) {
            event.consume();
        }
    }
    
    @FXML
    private void txtNombreComercialKeyTypedEvent(KeyEvent event) {
        if (txtNombreComercial.getText().length() >= 100) {
            event.consume();
        }
    }


    @FXML
    private void txtCorreoKeyTypedEvent(KeyEvent event) {
        if (txtCorreo.getText().length() >= 255) {
            event.consume();
        }
    }


    @FXML
    private void txtCelularKeyTypedEvent(KeyEvent event) {
        if (txtCelular.getText().length() >= 10) {
            event.consume();
        }
    }
    
    @FXML
    private void btnCancelarEvent(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void btnAgregarEvent(ActionEvent event) {
        if (verificacion()){
            ingresarProveedor();
        }
    }
    
    private void setEstado() {
        cmbEstado.getItems().addAll("Activo", "Inactivo");
    }
    
    private void ingresarProveedor() {
        Conexion con = Conexion.getInstance();
        String sql = "INSERT INTO providers (nit, legal_name, commercial_name, phone, email, param_state, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement sentencia = null;
        try{
            sentencia = con.getConnection().prepareStatement(sql);
            
            sentencia.setString(1, txtNit.getText());
            sentencia.setString(2, txtNombreLegal.getText());
            sentencia.setString(3, txtNombreComercial.getText());
            sentencia.setString(4, txtCelular.getText());
            sentencia.setString(5, txtCorreo.getText());
            sentencia.setInt(6, getEstado());
            sentencia.setString(7, getFechaHoy());
            
            sentencia.executeUpdate();
        }catch(Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al crear proveedor");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        con.cerrarConexion();
        
        Stage stage = (Stage) btnAgregar.getScene().getWindow();
        stage.close();
    }
    
    private String getFechaHoy() {
        String fecha = "";

		Instant instant = Instant.now();
		Timestamp timestamp = Timestamp.from(instant);
		fecha = timestamp.toString();
		return fecha;
    }
    
    private int getEstado() {
        int estado = 6;
        
        if (cmbEstado.getValue().equals("Activo")){
            estado = 5;
        } else {
            estado = 6;
        }
        return estado;
    }

    private boolean verificacion() {
        boolean verificar = true;
        Alert alertaError = new Alert(Alert.AlertType.ERROR);
        alertaError.setTitle("Error");
        alertaError.setHeaderText("Error al registrar");
        
        if(txtNit.getText().isEmpty() || txtNombreLegal.getText().isEmpty() || txtNombreComercial.getText().isEmpty() || txtCorreo.getText().isEmpty() || txtCelular.getText().isEmpty()){
            alertaError.setContentText("No deben haber campos vacíos.");
            verificar = false;
        } else if(txtNit.getText().length() < 7 || txtNit.getText().length() > 10 || !txtNit.getText().matches("^[0-9]+$")){
            alertaError.setContentText("El nit no es valido.");
            verificar = false;
        } else if (!verificarCorreo()) {
            alertaError.setContentText("El correo no es valido.");
            verificar = false;
        } else if(!verificarCelular()) {
            alertaError.setContentText("El celular no es valido.");
            verificar = false;
        } else if(!verificarNit()) {
            alertaError.setContentText("No puede insertar un nit duplicado.");
            verificar = false;
        }
        
        if (verificar == false) {
            alertaError.showAndWait();
        }

        return verificar;
    }
    
    private boolean verificarCorreo(){
        String formato = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern patron = Pattern.compile(formato);
        Matcher comprobador = patron.matcher(txtCorreo.getText());
        
        return comprobador.matches();
    }
    
    private boolean verificarCelular(){
        String formato = "^(3|6)\\d{9}$";
        Pattern patron = Pattern.compile(formato);
        Matcher comprobador = patron.matcher(txtCelular.getText());
        
        return comprobador.matches();
    }
    
    private boolean verificarNit(){
        boolean verificacion = true;
        Conexion con = Conexion.getInstance();
        String sql = "SELECT COUNT(*) FROM providers WHERE nit = '"+txtNit.getText()+"'" ;
        
        try{
            Statement sentencia = con.getConnection().createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            
            if (rs.next() && rs.getInt(1) > 0)
                verificacion = false;
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return verificacion;
    }
}
