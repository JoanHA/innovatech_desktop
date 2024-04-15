package model;

import javafx.scene.layout.HBox;

public class MdlProveedores{
    private int id;
    private String nit;
    private String nombreLegal;
    private String nombreComercial;
    private String celular;
    private String email;
    private String estado;
    private String fechaRegistro;
    private String fechaModificacion;
    
    private HBox opciones;
    
    public MdlProveedores() {
    }

    public MdlProveedores(int id, String nit, String nombreLegal, String nombreComercial, String celular, String email, String estado, String fechaRegistro, String fechaModificacion, HBox opciones) {
        this.id = id;
        this.nit = nit;
        this.nombreLegal = nombreLegal;
        this.nombreComercial = nombreComercial;
        this.celular = celular;
        this.email = email;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.fechaModificacion = fechaModificacion;
        this.opciones = opciones;
    }
    
    public MdlProveedores(String celular, String nombreLegal, String nombreComercial, String email){
        this.celular = celular;
        this.nombreLegal = nombreLegal;
        this.nombreComercial = nombreComercial;
        this.email = email;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombreLegal() {
        return nombreLegal;
    }

    public void setNombreLegal(String nombreLegal) {
        this.nombreLegal = nombreLegal;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public HBox getOpciones() {
        return opciones;
    }

    public void setOpciones(HBox opciones) {
        this.opciones = opciones;
    }
}
