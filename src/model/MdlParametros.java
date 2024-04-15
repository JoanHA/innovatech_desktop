/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.scene.layout.HBox;

/**
 *
 * @author SENA
 */
public class MdlParametros {

	private String id;
	private String tipo_parametro;
	private String nombre;
	private String nombreTipo;
	private String foreign;
	private String estado;
	private HBox opciones;

	public MdlParametros(String id, String tipo_parametro, String nombre,String nombreTipo, String foreign, String estado, HBox opciones) {
		this.id = id;
		this.tipo_parametro = tipo_parametro;
		this.nombre = nombre;		
		this.nombreTipo = nombreTipo;
		this.foreign = foreign;
		this.estado = estado;
		this.opciones = opciones;
	}

	public MdlParametros() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipo_parametro() {
		return tipo_parametro;
	}

	public void setTipo_parametro(String tipo_parametro) {
		this.tipo_parametro = tipo_parametro;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getForeign() {
		return foreign;
	}

	public void setForeign(String foreign) {
		this.foreign = foreign;
	}

	public HBox getOpciones() {
		return opciones;
	}

	public void setOpciones(HBox opciones) {
		this.opciones = opciones;
	}

	public String getNombreTipo() {
		return nombreTipo;
	}

	public void setNombreTipo(String nombreTipo) {
		this.nombreTipo = nombreTipo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
