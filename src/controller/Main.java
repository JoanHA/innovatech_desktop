/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import conexion.Conexion;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Dragnell
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		
		try {
			FXMLLoader preloaderLoader = new FXMLLoader(getClass().getResource("/view/VwPreloader.fxml"));
			Parent preloaderRoot = preloaderLoader.load();
			Scene preloaderScene = new Scene(preloaderRoot);
			Stage preloaderStage = new Stage();
			preloaderStage.initModality(Modality.APPLICATION_MODAL);
			preloaderStage.initStyle(StageStyle.UNDECORATED);
			preloaderStage.setScene(preloaderScene);
			preloaderStage.show();

			new Thread(() -> {
				Conexion con = Conexion.getInstance();
				String sql = "SELECT * FROM users WHERE param_rol = 2";
				int comprobarUserAdmin = 0;
				ResultSet resultado = null;
				try {
					resultado = con.consultar(sql);
					while (resultado.next()) {
						comprobarUserAdmin++;
					}
				} catch (SQLException e) {
					if (resultado == null) {
						preloaderStage.close();
					}
				} catch(Exception e){
					 System.exit(0);
				}
				con.cerrarConexion();
				final int index = comprobarUserAdmin;
				Platform.runLater(() -> {
					if (index != 0) {
						inicio("viewlogin", primaryStage);
					} else {
						inicio("vwFormRegistros", primaryStage);
					}
					preloaderStage.close();
				});
			}).start();
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	void inicio(String url, Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/" + url + ".fxml"));
			Pane root = (Pane) loader.load();

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
