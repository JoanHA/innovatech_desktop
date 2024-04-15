/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javafx.scene.control.Button;

/**
 *
 * @author root
 */
public class mdlVerPedidos {
    private  int id;
    private String state;
    private int cantidad;
    private  String fecha;
    private String nombre;
    private Button boton;

    public mdlVerPedidos(int id, String state, int cantidad, String fecha, String nombre, Button boton) {
        this.id = id;
        this.state = state;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.nombre = nombre;
        this.boton = boton;
    }

    public mdlVerPedidos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Button getBoton() {
        return boton;
    }

    public void setBoton(Button boton) {
        this.boton = boton;
    }
    
}
