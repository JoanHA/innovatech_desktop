/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.scene.control.Button;

/**
 *
 * @author Dragnell
 */


public class MdlInformes {
    
    private String descripcioInfo ;
    
    private String estadoInfo ;
    
    private String fechaModificacionInfo;
    
    private String fechaRegistroInfo;
    
    private int  idInfo;
    
    private String tipo;
    
    private String titulo;
    
    private Button btnVer;

    public Button getBtnVer() {
        return btnVer;
    }

    public void setBtnVer(Button btnver) {
        this.btnVer = btnver;
    }

    private String imagen;

    public String getDescripcioInfo() {
        return descripcioInfo;
    }

    public void setDescripcioInfo(String descripcioInfo) {
        this.descripcioInfo = descripcioInfo;
    }

    public String getEstadoInfo() {
        return estadoInfo;
    }

    public void setEstadoInfo(String estadoInfo) {
        this.estadoInfo = estadoInfo;
    }

    public String getInformeFechaModificacionInfo() {
        return fechaModificacionInfo;
    }

    public void setInformeFechaModificacionInfo(String informeFechaModificacionInfo) {
        this.fechaModificacionInfo = informeFechaModificacionInfo;
    }

    public String getFechaRegistroInfo() {
        return fechaRegistroInfo;
    }

    public void setFechaRegistroInfo(String fechaRegistroInfo) {
        this.fechaRegistroInfo = fechaRegistroInfo;
    }

    public int getIdInfo() {
        return idInfo;
    }

    public void setIdInfo(int idInfo) {
        this.idInfo = idInfo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

		public MdlInformes(String descripcioInfo, String estadoInfo, String fechaModificacionInfo, String fechaRegistroInfo, int idInfo, String tipo, String titulo, Button btnVer, String imagen) {
				this.descripcioInfo = descripcioInfo;
				this.estadoInfo = estadoInfo;
				this.fechaModificacionInfo = fechaModificacionInfo;
				this.fechaRegistroInfo = fechaRegistroInfo;
				this.idInfo = idInfo;
				this.tipo = tipo;
				this.titulo = titulo;
				this.btnVer = btnVer;
				this.imagen = imagen;
		}

		public MdlInformes() {
		}

		public String getImagen() {
				return imagen;
		}

		public void setImagen(String imagen) {
				this.imagen = imagen;
		}

		
}
