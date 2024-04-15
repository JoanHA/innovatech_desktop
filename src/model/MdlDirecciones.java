package model;

import javafx.scene.layout.HBox;

/**
 *
 * @author Dragnell
 */
public class MdlDirecciones {

	private String id;
	private String usuario;
	private String direccion;
	private String piso;
	private String barrio;
	private String municipio;
	private String estado;
	private String departamento;
	private HBox opciones;

	public MdlDirecciones(String id, String usuario, String direccion, String piso, String barrio, String municipio, String estado, String departamento, HBox opciones) {
		this.id = id;
		this.usuario = usuario;
		this.direccion = direccion;
		this.piso = piso;
		this.barrio = barrio;
		this.municipio = municipio;
		this.estado = estado;
		this.departamento = departamento;
		this.opciones = opciones;
	}

	public MdlDirecciones() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public HBox getOpciones() {
		return opciones;
	}

	public void setOpciones(HBox opciones) {
		this.opciones = opciones;
	}

}
