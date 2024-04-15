/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.ImageView;


/**
 *
 * @author jhona
 */
public class Mdlcarrusel extends mdlUsuario{

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty posicion = new SimpleIntegerProperty();
    private final StringProperty imagen = new SimpleStringProperty();
    private final StringProperty estado = new SimpleStringProperty();
    private ImageView foto;

    public Mdlcarrusel(String usuarioId, String documento, String nombres, String apellidos, String celular, String email, String contrasena, String param_tipoDocumento, String param_rol, String param_suscripcion, String usuarioParam_estado, String token, Timestamp usuarioFecha_registro, Timestamp usuarioFecha_modificacion) {
        super(usuarioId, documento, nombres, apellidos, celular, email, contrasena, param_tipoDocumento, param_rol, param_suscripcion, usuarioParam_estado, token, usuarioFecha_registro, usuarioFecha_modificacion);
    }
     
    public Mdlcarrusel() {
    }

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String value) {
        estado.set(value);
    }

    public StringProperty estadoProperty() {
        return estado;
    }
    
    public String getImagen() {
        return imagen.get();
    }

    public void setImagen(String value) {
        imagen.set(value);
    }

    public StringProperty imagenProperty() {
        return imagen;
    }
    

    public int getPosicion() {
        return posicion.get();
    }

    public void setPosicion(int value) {
        posicion.set(value);
    }

    public IntegerProperty posicionProperty() {
        return posicion;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int value) {
        id.set(value);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public ImageView getFoto() {
        return foto;
    }

    public void setFoto(ImageView foto) {
        this.foto = foto;
    }

    public Mdlcarrusel(ImageView foto) {
        this.foto = foto;
    }
    
}
//    int id;
//    String nombre;
//    int posicion;
//    String imagen;
//    String estado;
//    String fecha_registro;
//    String fecha_modificacion;
//    
//    public Mdlcarrusel(int id, String nombre, int posicion, String imagen, String estado, String fecha_registro, String fecha_modificacion) {
//        this.id = id;
//        this.nombre = nombre;
//        this.posicion = posicion;
//        this.imagen = imagen;
//        this.estado = estado;
//        this.fecha_registro = fecha_registro;
//        this.fecha_modificacion = fecha_modificacion;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getNombre() {
//        return nombre;
//    }
//
//    public void setNombre(String nombre) {
//        this.nombre = nombre;
//    }
//
//    public int getPosicion() {
//        return posicion;
//    }
//
//    public void setPosicion(int posicion) {
//        this.posicion = posicion;
//    }
//
//    public String getImagen() {
//        return imagen;
//    }
//
//    public void setImagen(String imagen) {
//        this.imagen = imagen;
//    }
//
//    public String getEstado() {
//        return estado;
//    }
//
//    public void setEstado(String estado) {
//        this.estado = estado;
//    }
//
//    public String getFecha_registro() {
//        return fecha_registro;
//    }
//
//    public void setFecha_registro(String fecha_registro) {
//        this.fecha_registro = fecha_registro;
//    }
//
//    public String getFecha_modificacion() {
//        return fecha_modificacion;
//    }
//
//    public void setFecha_modificacion(String fecha_modificacion) {
//        this.fecha_modificacion = fecha_modificacion;
//    }
//
//
////
////
////    private final StringProperty Nombre = new SimpleStringProperty();
////
////    public String getNombre() {
////        return Nombre.get();
////    }
////
////    public void setNombre(String value) {
////        Nombre.set(value);
////    }
////
////    public StringProperty NombreProperty() {
////        return Nombre;
////    }
////    
//
//   
//    
//}
//
//
//    