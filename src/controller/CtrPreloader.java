package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.paint.Color;

public class CtrPreloader implements Initializable {

	@FXML
	private Circle punto1;
	@FXML
	private Circle punto2;
	@FXML
	private Circle punto3;
	@FXML
	private Circle punto4;

	private volatile boolean animating = true;
	@FXML
	private Circle punto5;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Thread animationThread = new Thread(this::animacion);
		animationThread.setDaemon(true);
		animationThread.start();
	}

	void animacion() {
		Circle[] puntos = {punto1, punto2, punto3, punto4,punto5};
		int currentIndex = 0;

		while (animating) {
			for (Circle punto : puntos) {
				punto.setFill(null);
			}
			puntos[currentIndex].setFill(javafx.scene.paint.Color.BLACK);
			
			for (int i = 0; i < currentIndex; i++) {
				puntos[i].setFill(javafx.scene.paint.Color.BLACK.interpolate(Color.SILVER,0.5));
			}
			for (int i = 1; i < currentIndex; i++) {
				puntos[i-1].setFill(javafx.scene.paint.Color.SILVER);
			}
			for (int i = 2; i < currentIndex; i++) {
				puntos[i-2].setFill(javafx.scene.paint.Color.WHITE.interpolate(Color.SILVER, 0.5));
			}
			for (int i = 3; i < currentIndex; i++) {
				puntos[i-3].setFill(javafx.scene.paint.Color.WHITE);
			}
			currentIndex = (currentIndex + 1) % puntos.length;

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void cerrarVentana() {
		Stage stage = (Stage) punto1.getScene().getWindow();
		stage.close();
	}
}
