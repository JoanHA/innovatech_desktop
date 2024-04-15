/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import FTP.ConexionFTP;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Mdlcarrusel;
import conexion.Conexion;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author jhona
 */
public class ViewprincipalsController implements Initializable {

	@FXML
	private Button btnagregar;
	@FXML
	private Button btnactualizar;
	@FXML
	private TableView<Mdlcarrusel> gestionTable;
	@FXML
	private TableColumn<Mdlcarrusel, Integer> colid;
	@FXML
	private TableColumn<Mdlcarrusel, Integer> colposicion;
	@FXML
	private TableColumn<Mdlcarrusel, String> colimagen;
	@FXML
	private TableColumn<Mdlcarrusel, String> colestado;
	@FXML
	private TableColumn<Mdlcarrusel, String> colcontron;

	String query;
	// Connection connection;
	// PreparedStatement preparedStatement;
	// ResultSet rs;
	public Mdlcarrusel carrusel;

	private ObservableList<Mdlcarrusel> carruselList = FXCollections.observableArrayList();

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		cargar();
	}

	@FXML
	private void agregar(ActionEvent event) {
		FXMLLoader abrirla = new FXMLLoader();
		abrirla.setLocation(getClass().getResource("/view/viewinsertar.fxml"));
		try {
			abrirla.load();
		} catch (Exception e) {
			System.out.println("No se pudo abrir el apartado de insertar imagen" + e);
		}
		viewinsertarController control = abrirla.getController();
		control.initAttributtes(carruselList);
		Parent root = abrirla.getRoot();

		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Insertar imagen");
		stage.showAndWait();
		actualizar();
	}

	@FXML
	private void actualizar() {
		Conexion con = Conexion.getInstance();
		carruselList.clear();

		query = "SELECT * FROM carrusels ORDER BY position";
		ResultSet rs = con.consultar(query);
		try {
			while (rs.next()) {
				Mdlcarrusel carruse = new Mdlcarrusel();
				carruse.setId(rs.getInt("id"));
				carruse.setPosicion(rs.getInt("position"));
				carruse.setImagen(rs.getString("image"));
				carruse.setEstado(getParam(rs.getString("param_state")));
				ImageView imagen = new ImageView("https://innovatechcol.com.co/img/carrusel/" + carruse.getImagen());
				imagen.setFitWidth(100);
				imagen.setFitHeight(80);
				carruse.setFoto(imagen);
				carruselList.add(carruse);

			}
		} catch (SQLException ex) {
			System.out.println("No se puede mirar los registros");
			System.out.println("Erros" + ex);
			ex.getErrorCode();
		}
		con.cerrarConexion();
	}

	private void notificar() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setTitle("Información");
		alert.setContentText("Registro eliminado correctamente");
		alert.showAndWait();
	}

	private void cargar() {
		actualizar();
		colposicion.setCellValueFactory(new PropertyValueFactory<>("posicion"));
		colimagen.setCellValueFactory(new PropertyValueFactory<>("foto"));
		colestado.setCellValueFactory(new PropertyValueFactory<>("estado"));

		Callback<TableColumn<Mdlcarrusel, String>, TableCell<Mdlcarrusel, String>> controls = (param) -> {
			final TableCell<Mdlcarrusel, String> cell = new TableCell<Mdlcarrusel, String>() {

				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (empty) {
						setGraphic(null);
						setText(null);
					} else {

						final Button btneditar = new Button("Editar");
						final Button btneliminar = new Button("Eliminar");

						btneditar.getStylesheets().add("/style/Style.css");
						btneditar.getStyleClass().setAll("btnEditar");

						btneliminar.getStylesheets().add("/style/Style.css");
						btneliminar.getStyleClass().setAll("btnEliminar");

						btneditar.setOnMouseClicked((MouseEvent event) -> {
							Mdlcarrusel carrusel = getTableView().getItems().get(getIndex());
							FXMLLoader abrir = new FXMLLoader();
							abrir.setLocation(getClass().getResource("/view/vieweditar.fxml"));
							try {
								abrir.load();
							} catch (IOException ex) {
								System.out.println("Erro de interfaz" + ex);
							}
							VieweditarController controlador = abrir.getController();
							controlador.editar(carrusel);
							Parent root = abrir.getRoot();
							Stage stage = new Stage();
							stage.setScene(new Scene(root));
							stage.setTitle("Editar imagen");
							stage.showAndWait();
							actualizar();
						});
						btneliminar.setOnMouseClicked((MouseEvent event) -> {
							Mdlcarrusel carrusel = gestionTable.getItems().get(getIndex());
							Alert mensaje = new Alert(Alert.AlertType.CONFIRMATION);
							mensaje.setHeaderText("Eliminar imagen");
							mensaje.setContentText("¿Estas seguro de eliminar la imagen?");
							ButtonType botonsi = new ButtonType("Si");
							ButtonType botonno = new ButtonType("No");
							mensaje.getButtonTypes().setAll(botonsi, botonno);
							Optional<ButtonType> opcion = mensaje.showAndWait();
							if (opcion.get() == botonsi) {
								Conexion con = Conexion.getInstance();
								query = "DELETE FROM carrusels WHERE id= " + carrusel.getId();
								eliminarimagen(carrusel.getImagen());
								con.ejecutar(query);
								try {
									actualizar();
									notificar();
								} catch (Exception ex) {
									System.out.println("No se pudo eliminar registro");
									System.out.println("Error " + ex);
								} finally {
									con.cerrarConexion();
								}

							} else if (opcion.get() == botonno) {
								mensaje.close();
							}
						});
						HBox botonfuncion = new HBox(btneditar, btneliminar);
						botonfuncion.setStyle("-fx-aligment:center");
						HBox.setMargin(btneditar, new Insets(25, 8, 2, 8));
						HBox.setMargin(btneliminar, new Insets(25, 10, 2, 8));

						setGraphic(botonfuncion);
						setText(null);
					}
				}

			};
			return cell;
		};

		colcontron.setCellFactory(controls);
		gestionTable.setItems(carruselList);
	}

	private String getParam(String param) {
		Conexion con = Conexion.getInstance();
		try {
			Statement sentencia = con.getConnection().createStatement();
			ResultSet rs = sentencia.executeQuery("SELECT name FROM params WHERE id = " + param);

			while (rs.next()) {
				param = rs.getString("name");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return param;
	}

	public void eliminarimagen(String imagen) {
		ConexionFTP ftp = new ConexionFTP();
		ftp.eliminarImg("carrusel", imagen);
	}
	// private Button creareditar(String toolTip){
	// Button boton = new Button("Editar");
	// boton.setTooltip(new Tooltip(toolTip));
	// return boton;
	// }
}
