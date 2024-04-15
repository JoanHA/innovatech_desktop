package model;

import javafx.scene.control.Button;

public class MdlVenta extends mdlUsuario {

    private int id;
    private String metodoPago;
    private int totalPago;
    private String formaEnvio;
    private String estado;
    private String fechaRegistro;
    private String fechaModificacion;
    private String direccion;
    private Button opciones;

    public MdlVenta(int idCompra, String metodoPago, int totalPago, int cantidad, String formaEnvio, String estado, String fechaRegistro, String fechaModificacion, String direccion, Button btnVerDetalles) {
        this.id = idCompra;
        this.metodoPago = metodoPago;
        this.totalPago = totalPago;
        this.formaEnvio = formaEnvio;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.fechaModificacion = fechaModificacion;
        this.direccion = direccion;
        this.opciones = btnVerDetalles;
    }

    public MdlVenta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public int getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(int totalPago) {
        this.totalPago = totalPago;
    }
    
    public String getFormaEnvio(){
        return formaEnvio;
    }
    
    public void setFormaEnvio(String formaEnvio){
        this.formaEnvio = formaEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Button getOpciones() {
        return opciones;
    }

    public void setOpciones(Button btnVerDetalles) {
        this.opciones = btnVerDetalles;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
