/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigInteger;
import java.sql.Timestamp;


public class mdlUsuario{

		private String usuarioId;
		private String documento;
		private String nombres;
		private String apellidos;
		private String celular;
		private String email;
		private String contrasena;
		private String param_tipoDocumento;
		private String param_rol;
		private String param_suscripcion;
		private String usuarioParam_estado;
		private String token;
		private Timestamp usuarioFecha_registro;
		private Timestamp usuarioFecha_modificacion;

	public mdlUsuario(String usuarioId, String documento, String nombres, String apellidos, String celular, String email, String contrasena,String param_tipoDocumento, String param_rol, String param_suscripcion, String usuarioParam_estado,String token, Timestamp usuarioFecha_registro, Timestamp usuarioFecha_modificacion) {
		this.usuarioId = usuarioId;
		this.documento = documento;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.celular = celular;
		this.email = email;
		this.contrasena = contrasena;
		this.param_tipoDocumento = param_tipoDocumento;
		this.param_rol = param_rol;
		this.param_suscripcion = param_suscripcion;
		this.token = token;
		this.usuarioParam_estado = usuarioParam_estado;
		this.usuarioFecha_registro = usuarioFecha_registro;
		this.usuarioFecha_modificacion = usuarioFecha_modificacion;
	}
        
	public mdlUsuario() {
	}

	public String getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getParam_tipoDocumento() {
		return param_tipoDocumento;
	}

	public void setParam_tipoDocumento(String param_tipoDocumento) {
		this.param_tipoDocumento = param_tipoDocumento;
	}

	public String getParam_rol() {
		return param_rol;
	}

	public void setParam_rol(String param_rol) {
		this.param_rol = param_rol;
	}

	public String getParam_suscripcion() {
		return param_suscripcion;
	}

	public void setParam_suscripcion(String param_suscripcion) {
		this.param_suscripcion = param_suscripcion;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsuarioParam_estado() {
		return usuarioParam_estado;
	}

	public void setUsuarioParam_estado(String usuarioParam_estado) {
		this.usuarioParam_estado = usuarioParam_estado;
	}

	public Timestamp getUsuarioFecha_registro() {
		return usuarioFecha_registro;
	}

	public void setUsuarioFecha_registro(Timestamp usuarioFecha_registro) {
		this.usuarioFecha_registro = usuarioFecha_registro;
	}

	public Timestamp getUsuarioFecha_modificacion() {
		return usuarioFecha_modificacion;
	}

	public void setUsuarioFecha_modificacion(Timestamp usuarioFecha_modificacion) {
		this.usuarioFecha_modificacion = usuarioFecha_modificacion;
	}
}
