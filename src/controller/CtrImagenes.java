package controller;

import FTP.ConexionFTP;
import static controller.viewinsertarController.aspectoValido;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import model.MdlImagenes;

public class CtrImagenes implements Initializable {

	@FXML
	private Button btnActualizar;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnAceptar;
	@FXML
	private TableView tblImagenes;
	@FXML
	private TableColumn colId;
	@FXML
	private TableColumn colOpciones;
	@FXML
	private Button btnNuevo;
	@FXML
	private ImageView img;

	ConexionFTP ftp = new ConexionFTP();

	private ObservableList<MdlImagenes> listaImagenes = FXCollections.observableArrayList();
	public ArrayList<String> listaVieja = new ArrayList();
	public ArrayList<String> listaNueva = new ArrayList();
	private int posicionImagen;
	@FXML
	private HBox hboxBotones;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		colId.setCellValueFactory(new PropertyValueFactory("id"));
		colOpciones.setCellValueFactory(new PropertyValueFactory("opciones"));
	}

	public void actua() {
		btnCancelar.setText("Cerrar");
		hboxBotones.getChildren().remove(btnAceptar);
	}

	@FXML
	private void btnActualizarEvent(ActionEvent event) {
		actualizarImagen();
	}

	@FXML
	private void btnCancelarEvent(ActionEvent event) {
		deshacerCambios();
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void btnAceptarEvent(ActionEvent event) {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void btnNuevoEvent(ActionEvent event) {
		if (listaVieja.size() <= 5) {
			agregarImagen();
			cargarDatos();
		} else {
			Alert alertaError = new Alert(Alert.AlertType.ERROR);
			alertaError.setTitle("Error");
			alertaError.setHeaderText(null);
			alertaError.setContentText("No puedes agregar mas de 5 imágenes");
		}
	}

	private void agregarImagen() {
		FileChooser fileChooser = new FileChooser();
		Stage stage = new Stage();

		fileChooser.setTitle("Seleccionar imagen");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de imagen (*.jpg, *.jpeg, *.png)", "*.jpg", "*.jpeg", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(stage);

		if (file != null) {
			if (aspectoValido(file.toString(), 1)) {
				listaVieja.add(guardarImagen(file));
				listaNueva.add(listaVieja.get(listaVieja.size() - 1));
			}
		}
	}

	private String guardarImagen(File img) {
		String imagen = img.toURI().toString().replace("file:/", "");
		String nombre = ftp.subirImg("productos", imagen);
		return nombre;
	}

	private void cargarDatos() {
		listaImagenes.clear();
		for (int i = 0; i < listaVieja.size(); i++) {
			MdlImagenes imagen = new MdlImagenes();

			imagen.setId(i);
			imagen.setImagen(listaVieja.get(i));

			Button btnVer = new Button("Ver");
			Button btnEliminar = new Button("Eliminar");

			btnVer.setOnAction((event) -> {
				asignarImagen(imagen.getImagen());
				posicionImagen = imagen.getId();
			});
			btnEliminar.setOnAction((event) -> {
				eliminarImagen(imagen.getImagen());
			});

			btnEliminar.setPrefSize(100, 30);
			btnEliminar.getStylesheets().add("/style/Style.css");
			btnEliminar.getStyleClass().setAll("btnDanger");

			btnVer.setPrefSize(100, 30);
			btnVer.getStylesheets().add("/style/Style.css");
			btnVer.getStyleClass().setAll("btnSuccess");

			HBox opciones = new HBox(btnVer, btnEliminar);
			opciones.setAlignment(Pos.CENTER);
			opciones.setSpacing(5);

			imagen.setOpciones(opciones);

			listaImagenes.add(imagen);
		}

		tblImagenes.setItems(listaImagenes);
		tblImagenes.refresh();
	}

	private void eliminarImagen(String rutaImagen) {
		Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
		alertaConfirmacion.setTitle("Confirmación");
		alertaConfirmacion.setHeaderText(null);
		alertaConfirmacion.setContentText("¿Estás seguro de que quieres eliminar esta imagen?\nEsta acción es irreversible.");
		Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();

		if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
			ftp.eliminarImg("productos", rutaImagen);
			listaVieja.remove(rutaImagen);

			if (listaNueva.contains(rutaImagen)) {
				listaNueva.remove(rutaImagen);
			}

			Alert alertaInformacion = new Alert(Alert.AlertType.INFORMATION);
			alertaInformacion.setTitle("Eliminación exitosa");
			alertaInformacion.setHeaderText(null);
			alertaInformacion.setContentText("La imagen se eliminó correctamente.");
			alertaInformacion.showAndWait();
		}

		cargarDatos();
	}

	private void actualizarImagen() {
		FileChooser fileChooser = new FileChooser();
		Stage stage = new Stage();

		fileChooser.setTitle("Seleccionar imagen");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de imagen (*.jpg, *.jpeg, *.png)", "*.jpg", "*.jpeg", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(stage);

		if (file != null) {
			if (aspectoValido(file.toURI().toString(), 1)) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setTitle("Confirmación");
				alert.setContentText("¿Estas seguro de modificar?\nEsta acción es irreversible.");
				Optional<ButtonType> resultado = alert.showAndWait();
				if (resultado.get() == ButtonType.OK) {
					String imagen = file.toURI().toString().replace("file:/", "");
					ftp.actualizarImg("productos", imagen, listaVieja.get(posicionImagen));
					cargarDatos();
				}
			}
		}
	}

	private void deshacerCambios() {
		for (int i = 0; i < listaNueva.size(); i++) {
			ftp.eliminarImg("productos", listaNueva.get(i));
			listaVieja.remove(listaNueva.get(i));
		}
	}

	public static boolean aspectoValido(String imagePath, double targetAspectRatio) {
		try {
			File imageFile = new File(imagePath.replace("file:/", ""));
			BufferedImage image = ImageIO.read(imageFile);
			int width = image.getWidth();
			int height = image.getHeight();
			double currentAspectRatio = (double) width / height;
			if (Math.abs(currentAspectRatio - targetAspectRatio) < 0.10 || width > height) {
				return true;
			} else {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setHeaderText(null);
				alert.setTitle("Relación de aspecto no valida");
				alert.setContentText("La relación de aspecto no es válida te recomendamos que sea de 4:4 o el largo sea menor al ancho.");
				alert.showAndWait();
				return false;
			}
		} catch (IOException e) {
			return false; // En caso de error al leer la imagen
		}
	}

	private void asignarImagen(String rutaImagen) {
		File file = new File(rutaImagen);
		Image image = new Image("https://innovatechcol.com.co/img/productos/" + rutaImagen);
		img.setImage(image);
	}

	public void setLista(ArrayList lista) {
		this.listaVieja = lista;
		cargarDatos();
	}

	public ArrayList<String> getLista() {
		return listaVieja;
	}
}
