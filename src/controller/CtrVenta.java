package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CtrVenta implements Initializable {

	@FXML
	private ImageView imgVenta;
	@FXML
	private Label lblProducto;
	@FXML
	private Label lblReferencia;
	@FXML
	private Label lblCantidad;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	public void cargarVenta(String producto, String referencia, String cantidad, String img) {
		lblProducto.setText(producto);
		lblReferencia.setText(referencia);
		lblCantidad.setText(cantidad);
		cargarImagen(img);
	}

	private void cargarImagen(String imagen) {
		try {
			String[] images;
			images = imagen.split(":");
			imagen = "https://innovatechcol.com.co/img/productos/" + images[0];
			Image img =new Image(imagen);
			imgVenta.setImage(img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
