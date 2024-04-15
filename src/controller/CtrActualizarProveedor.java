package controller;

import conexion.Conexion;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.MdlProveedores;

public class CtrActualizarProveedor implements Initializable {
    
    @FXML
    private TextField txtNit;
    @FXML
    private TextField txtNombreLegal;
    @FXML
    private TextField txtNombreComercial;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtCelular;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnCancelar;
    @FXML
    private ComboBox<String> cmbEstado;
    
    private MdlProveedores proveedor = new MdlProveedores();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    private void btnActualizarEvent(ActionEvent event) {
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle("Confirmación");
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setContentText("¿Estás seguro de que quieres actualizar este proveedor?");
        Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            if (verificacion()){
                actualizar();
                Stage stage = (Stage) btnActualizar.getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML
    private void btnCancelarEvent(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
    public void setProveedor(int id) {
        Conexion con = Conexion.getInstance();
        Statement sentencia = null;
        ResultSet rs = null;
        try {
            sentencia = con.getConnection().createStatement();
            rs = sentencia.executeQuery("SELECT * FROM providers WHERE id = " + id);

            while (rs.next()) {
                proveedor.setId(rs.getInt("id"));
                proveedor.setNit(rs.getString("nit"));
                proveedor.setNombreLegal(rs.getString("legal_name"));
                proveedor.setNombreComercial(rs.getString("commercial_name"));
                proveedor.setCelular(rs.getString("phone"));
                proveedor.setEmail(rs.getString("email"));
                proveedor.setEstado(rs.getString("param_state"));
                proveedor.setFechaRegistro(rs.getString("created_at"));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        con.cerrarConexion();
    }
    
    public void cargarDatos() {
        cmbEstado.getItems().addAll("Activo", "Inactivo");
        txtNit.setText(String.valueOf(proveedor.getNit()));
        txtNombreLegal.setText(proveedor.getNombreLegal());
        txtNombreComercial.setText(proveedor.getNombreComercial());
        txtCorreo.setText(proveedor.getEmail());
        txtCelular.setText(String.valueOf(proveedor.getCelular()));
        setEstado(proveedor.getEstado());
    }
    
    private void setEstado(String estado){
        if (estado.equals("5")){
            cmbEstado.setValue("Activo");
        } else {
            cmbEstado.setValue("Inactivo");
        }
    }
    
    private void actualizar() {
        int filas = 0;
        
        Conexion con = Conexion.getInstance();
        String sql = "";
        PreparedStatement sentencia = null;
        try {
            sql = "UPDATE providers SET nit = ?, legal_name = ?, commercial_name = ?, phone = ?, email = ?, param_state = ?, updated_at = ? WHERE id = ?";
            sentencia = con.getConnection().prepareStatement(sql);
            
            sentencia.setString(1, txtNit.getText());
            sentencia.setString(2, txtNombreLegal.getText());
            sentencia.setString(3, txtNombreComercial.getText());
            sentencia.setString(4, txtCelular.getText());
            sentencia.setString(5, txtCorreo.getText());
            sentencia.setInt(6, getEstado());
            sentencia.setString(7, getFechaHoy());
            sentencia.setInt(8, proveedor.getId());
            
            filas = sentencia.executeUpdate();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        con.cerrarConexion();
        
        if (filas > 0) {
            Alert alertaInformacion = new Alert(Alert.AlertType.INFORMATION);
            alertaInformacion.setTitle("Actualización exitosa");
            alertaInformacion.setHeaderText(null);
            alertaInformacion.setContentText("El proveedor se actualizó correctamente.");
            alertaInformacion.showAndWait();
        }
    }
    
    private int getEstado() {
        int estado;
        
        if (cmbEstado.getValue().equals("Activo")){
            estado = 5;
        } else {
            estado = 6;
        }
        return estado;
    }
    
    private String getFechaHoy() {
        String fecha = "";
		Instant instant = Instant.now();
		Timestamp timestamp = Timestamp.from(instant);
		fecha = timestamp.toString();
		return fecha;
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
            alertaError.setContentText("El NIT no es valido.");
            verificar = false;
        } else if (!verificarCorreo()) {
            alertaError.setContentText("El correo no es valido.");
            verificar = false;
        } else if(!verificarCelular()) {
            alertaError.setContentText("El celular no es valido.");
            verificar = false;
        } else if (!txtNit.getText().equals(proveedor.getNit())){
            System.out.println(txtNit.getText());
            System.out.println(proveedor.getNit());
            if(!verificarNit()) {
                alertaError.setContentText("No puede insertar un NIT duplicado.");
                verificar = false;
            } 
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
        Statement sentencia = null;
        ResultSet rs = null;
        try{
            sentencia = con.getConnection().createStatement();
            rs = sentencia.executeQuery(sql);
            
            if (rs.next() && rs.getInt(1) > 0)
                verificacion = false;
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        con.cerrarConexion();
        return verificacion;
    }
}
