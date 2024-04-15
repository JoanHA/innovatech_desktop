package model;

import javafx.scene.layout.HBox;

public class MdlImagenes {
    int id;
    String imagen;
    HBox opciones;

    public MdlImagenes(int id, String imagen, HBox opciones) {
        this.id = id;
        this.imagen = imagen;
        this.opciones = opciones;
    }
    
    public MdlImagenes() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public HBox getOpciones() {
        return opciones;
    }

    public void setOpciones(HBox opciones) {
        this.opciones = opciones;
    }
}
