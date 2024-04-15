package model;

import javafx.scene.layout.HBox;

public class MdlProductos {

    private int id;
    private String nombre;
    private int precio;
    private String referencia;
    private int stock;
    private String marca;
    private String imagen;
    private String color;
    private String descripcion;
    private String categoria;
    private String etiquetas;
    private int descuento;
    private int iva;
    private String estado;
    private String fechaRegistro;
    private String fechaModificacion;
    private HBox opciones;

    public MdlProductos(int id, String nombre, int precio, String referencia, int stock, String marca, String imagen, String color, String descripcion, String categoria, String etiquetas, int descuento, int iva, String estado, String fechaRegistro, String fechaModificacion, HBox opciones) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.referencia = referencia;
        this.stock = stock;
        this.marca = marca;
        this.imagen = imagen;
        this.color = color;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.etiquetas = etiquetas;
        this.descuento = descuento;
        this.iva = iva;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.fechaModificacion = fechaModificacion;
        this.opciones = opciones;
    }

    public MdlProductos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(String etiquetas) {
        this.etiquetas = etiquetas;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }
    
    public int getIva() {
        return iva;
    }
    
    public void setIva(int iva) {
        this.iva = iva;
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
