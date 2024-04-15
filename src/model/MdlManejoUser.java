/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;
import javafx.scene.control.Button;

/**
 *
 * @author darkj
 */
public class MdlManejoUser {

	private String nombre;
	private String apellido;
	private String id;
	private String fecha;
	private String total;
	private String email;
	private String tipo_documento;
	private Button boton;

	public MdlManejoUser(String nombre, String apellido, String id, String fecha, String total, String email, Button boton) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.id = id;
		this.fecha = fecha;
		this.total = total;
		this.email = email;
		this.boton = boton;
		this.boton.setText("Ver detalles");

	}

	public Button getBoton() {
		return boton;
	}

	public void setBoton(Button boton) {
		this.boton = boton;
	}

	public MdlManejoUser(String nombre, String apellido, String id, String fecha, String total, String email, String tipo_documento) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.id = id;
		this.fecha = fecha;
		this.total = total;
		this.email = email;
		this.tipo_documento = tipo_documento;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MdlManejoUser other = (MdlManejoUser) obj;
		if (!Objects.equals(this.nombre, other.nombre)) {
			return false;
		}
		if (!Objects.equals(this.apellido, other.apellido)) {
			return false;
		}
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		if (!Objects.equals(this.fecha, other.fecha)) {
			return false;
		}
		if (!Objects.equals(this.email, other.email)) {
			return false;
		}
		if (!Objects.equals(this.total, other.total)) {
			return false;
		}
		return true;
	}

	public MdlManejoUser() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTipo_documento() {
		return tipo_documento;
	}

	public void setTipo_documento(String tipo_documento) {
		this.tipo_documento = tipo_documento;
	}

}
