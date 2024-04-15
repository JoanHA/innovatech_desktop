package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import conexion.Conexion;
import java.io.File;
import java.sql.PreparedStatement;
import javafx.scene.control.RadioButton;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.MdlParametros;

public class CtrCrearProducto implements Initializable {

	@FXML
	private ImageView imgProducto;
	@FXML
	private Button btnAgregar;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtPrecio;
	@FXML
	private TextField txtReferencia;
	@FXML
	private TextField txtStock;
	@FXML
	private ComboBox<String> cmbMarca;
	@FXML
	private Button btnMarca;
	@FXML
	private ComboBox<String> cmbColor;
	@FXML
	private Button btnColor;
	@FXML
	private TextArea txtDescripcion;
	@FXML
	private ComboBox<String> cmbCategoria;
	@FXML
	private Button btnCategoria;
	@FXML
	private ComboBox<String> cmbEtiquetas;
	@FXML
	private Button btnEtiquetas;
	@FXML
	private TextField txtDescuento;
	@FXML
	private TextField txtIva;
	@FXML
	private ToggleGroup estado;
	@FXML
	private Button btnRegistrar;
	@FXML
	private Button btnCancelar;
	@FXML
	private RadioButton rbtnSi;
	@FXML
	private RadioButton rbtnNo;
	@FXML
	private HBox hboxImagen;

	private ArrayList<String> listaColores = new ArrayList();
	private ArrayList<String> listaEtiquetas = new ArrayList();
	private ArrayList<String> listaImagenes = new ArrayList();

	private ObservableList<MdlParametros> listParametros = FXCollections.observableArrayList();
	Map<String, String> parametros = new HashMap<>();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		listaPram();
		llenarParametros("");
		cmbEtiquetas.getItems().add(0, String.join(",", listaEtiquetas));
		cmbColor.getItems().add(0, String.join(",", listaColores));
	}

	void llenarParametros(String index) {
		for (int i = 0; i < listParametros.size(); i++) {
			parametros.put(listParametros.get(i).getNombre(), listParametros.get(i).getId());
		}
		if (index.equalsIgnoreCase("")) {
			cargarParametros();
		}else{
			cargarParametros(index);
		}
	}

	@FXML
	private void txtNombreKeyTypedEvent(KeyEvent event) {
		if (txtNombre.getText().length() >= 30) {
			event.consume();
		}
	}

	@FXML
	private void txtReferenciaKeyTypedEvent(KeyEvent event) {
		if (txtReferencia.getText().length() >= 12) {
			event.consume();
		}
	}

	@FXML
	private void txtDescripcionKeyTypedEvent(KeyEvent event) {
		if (txtDescripcion.getText().length() >= 1000) {
			event.consume();
		}
	}

	@FXML
	private void txtPrecioKeyTypedEvent(KeyEvent event) {
		if (txtPrecio.getText().length() >= 15) {
			event.consume();
		}
	}

	@FXML
	private void txtStockKeyTypedEvent(KeyEvent event) {
		if (txtStock.getText().length() >= 5) {
			event.consume();
		}
	}

	@FXML
	private void txtDescuentoKeyTypedEvent(KeyEvent event) {
		if (txtDescuento.getText().length() >= 3) {
			event.consume();
		}
	}

	@FXML
	private void txtIvaKeyTypedEvent(KeyEvent event) {
		if (txtIva.getText().length() >= 3) {
			event.consume();
		}
	}

	@FXML
	private void btnRegistrarEvent(ActionEvent event) {
		if (verificacion()) {
			ingresarProducto();
		}
	}

	@FXML
	private void btnAgregarEvent(ActionEvent event) {
		crearImagen();
	}

	@FXML
	private void btnCancelarEvent(ActionEvent event) {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void btnColorEvent(ActionEvent event) {
		cmbColor.getItems().clear();
		nuevoParametro("Colores");
		cmbColor.getItems().add(0, String.join(",", listaColores));
		llenarParametros("11");
	}

	@FXML
	private void btnCategoriaEvent(ActionEvent event) {
		cmbCategoria.getItems().clear();
		nuevoParametro("Categorías");
		llenarParametros("12");
	}

	@FXML
	private void btnEtiquetasEvent(ActionEvent event) {
		cmbEtiquetas.getItems().clear();
		nuevoParametro("Etiquetas");
		cmbEtiquetas.getItems().add(0, String.join(",", listaEtiquetas));
		llenarParametros("9");
	}

	@FXML
	private void btnMarcaEvent(ActionEvent event) {
		cmbMarca.getItems().clear();
		nuevoParametro("Marcas");
		llenarParametros("10");
	}

	@FXML
	private void cmbColorEvent(ActionEvent event) {
		if (!cmbColor.getItems().isEmpty()) {
			if (cmbColor.getSelectionModel().getSelectedIndex() != 0) {
				if (listaColores.contains(cmbColor.getValue())) {
					listaColores.remove(cmbColor.getValue());
				} else {
					listaColores.add(cmbColor.getValue());
				}
			}
		}
		cmbColor.getItems().set(0, String.join(",", listaColores));
		cmbColor.setValue(String.join(",", listaColores));
	}

	@FXML
	private void cmbEtiquetasEvent(ActionEvent event) {
		if (!cmbEtiquetas.getItems().isEmpty()) {
			if (cmbEtiquetas.getSelectionModel().getSelectedIndex() != 0) {
				if (listaEtiquetas.contains(cmbEtiquetas.getValue())) {
					listaEtiquetas.remove(cmbEtiquetas.getValue());
				} else {
					listaEtiquetas.add(cmbEtiquetas.getValue());
				}
			}
		}
		cmbEtiquetas.getItems().set(0, String.join(",", listaEtiquetas));
		cmbEtiquetas.setValue(String.join(",", listaEtiquetas));

	}

	private void nuevoParametro(String nombre) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VwCrearParametros.fxml"));
			Parent root = loader.load();
			CtrCrearParametros ctrCrearParametros = loader.getController();
			ctrCrearParametros.initAttributtes(listParametros, nombre);

			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();
			listaPram();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	void listaPram() {
		listParametros.clear();
		Conexion con = Conexion.getInstance();
		String sql = "SELECT * FROM params JOIN param_types ON param_types.id = params.paramtype_id WHERE params.param_state = 5 AND params.paramtype_id IN (9,10,11,12);";
		ResultSet rs = con.consultar(sql);
		try {
			while (rs.next()) {
				MdlParametros model = new MdlParametros();
				model.setId(rs.getString("params.id"));
				model.setTipo_parametro(rs.getString("paramtype_id"));
				model.setNombre(rs.getString("params.name"));
				model.setNombreTipo(rs.getString("param_types.name"));
				model.setEstado(rs.getString("params.param_state"));
				model.setForeign(rs.getString("param_foreign"));
				this.listParametros.add(model);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			con.cerrarConexion();
		}
	}

	private void cargarParametros() {
		for (int i = 0; i < listParametros.size(); i++) {
			switch (listParametros.get(i).getTipo_parametro()) {
			case "9":
				cmbEtiquetas.getItems().add(listParametros.get(i).getNombre());
				break;
			case "10":
				cmbMarca.getItems().add(listParametros.get(i).getNombre());
				break;
			case "11":
				cmbColor.getItems().add(listParametros.get(i).getNombre());
				break;
			case "12":
				cmbCategoria.getItems().add(listParametros.get(i).getNombre());
				break;
			}
		}
	}

	private void cargarParametros(String opcion) {
		for (int i = 0; i < listParametros.size(); i++) {
			switch (listParametros.get(i).getTipo_parametro()) {
			case "9":
				if (opcion.equals("9")) {
					cmbEtiquetas.getItems().add(listParametros.get(i).getNombre());
				}
				break;
			case "10":
				if (opcion.equals("10")) {
					cmbMarca.getItems().add(listParametros.get(i).getNombre());
				}
				break;
			case "11":
				if (opcion.equals("11")) {
					cmbColor.getItems().add(listParametros.get(i).getNombre());
				}
				break;
			case "12":
				if (opcion.equals("12")) {
					cmbCategoria.getItems().add(listParametros.get(i).getNombre());
				}
				break;
			}
		}
	}

	private void ingresarProducto() {
		Conexion con = Conexion.getInstance();
		PreparedStatement sentencia = null;
		try {
			String sql = "INSERT INTO products (name, price, code, stock, param_mark, images, param_color, description, param_category, param_tags, tax, discount, param_state, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			sentencia = con.getConnection().prepareStatement(sql);

			sentencia.setString(1, txtNombre.getText());
			sentencia.setInt(2, Integer.parseInt(txtPrecio.getText()));
			sentencia.setString(3, txtReferencia.getText());
			sentencia.setInt(4, Integer.parseInt(txtStock.getText()));
			sentencia.setString(5, this.parametros.get(cmbMarca.getValue()));
			sentencia.setString(6, getConcatenacion(listaImagenes, 2));
			sentencia.setString(7, getConcatenacion(listaColores, 1));
			sentencia.setString(8, txtDescripcion.getText());
			sentencia.setString(9, this.parametros.get(cmbCategoria.getValue()));
			sentencia.setString(10, getConcatenacion(listaEtiquetas, 1));
			sentencia.setInt(11, Integer.parseInt(txtIva.getText()));
			sentencia.setInt(12, Integer.parseInt(txtDescuento.getText()));
			sentencia.setInt(13, getEstado());
			sentencia.setString(14, getFechaHoy());

			sentencia.executeUpdate();

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Confirmación");
			alert.setHeaderText("Guardado exitoso");
			alert.setContentText("EL producto se registró correctamente.");
			alert.showAndWait();

			Stage stage = (Stage) btnAgregar.getScene().getWindow();
			stage.close();
		} catch (NumberFormatException | SQLException ex) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error al guardar");
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
		} finally {
			con.cerrarConexion();
		}

	}

	private String getConcatenacion(ArrayList<String> lista, int tipo) {
		String cadena = null;

		if (tipo == 1) {
			for (int i = 0; i < lista.size(); i++) {
				lista.set(i, this.parametros.get(lista.get(i)));
			}
		}
		cadena = String.join(":", lista);

		return cadena;
	}

	private int getEstado() {
		int estado = 0;

		if (rbtnSi.isSelected()) {
			estado = 5;
		} else if (rbtnNo.isSelected()) {
			estado = 6;
		}

		return estado;
	}

	private String getFechaHoy() {
		String fecha = "";

		Instant instant = Instant.now();
		Timestamp timestamp = Timestamp.from(instant);
		fecha = timestamp.toString();
		return fecha;
	}

	private void crearImagen() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VstImagenes.fxml"));
			Parent root = loader.load();
			CtrImagenes ctrImagenes = loader.getController();
			ctrImagenes.setLista(listaImagenes);

			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();
			listaImagenes = ctrImagenes.getLista();
			setImagen();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void setImagen() {
		if (!listaImagenes.isEmpty()) {
			File file = new File(listaImagenes.get(0));
			Image img = new Image("https://innovatechcol.com.co/img/productos/" + file);
			imgProducto.setImage(img);
		}
	}

	private boolean verificacion() {
		boolean verificar = true;
		Alert alertaError = new Alert(Alert.AlertType.ERROR);
		alertaError.setTitle("Error");
		alertaError.setHeaderText("Error al registrar");

		if (txtNombre.getText() == null || txtPrecio.getText().isEmpty() || txtReferencia.getText().isEmpty() || txtStock.getText().isEmpty() || txtDescripcion.getText().isEmpty() || txtDescuento.getText().isEmpty() || txtIva.getText().isEmpty() || cmbCategoria.getValue() == null || listaEtiquetas.isEmpty() || listaColores.isEmpty() || cmbMarca.getValue() == null) {
			alertaError.setContentText("No deben haber campos vacíos.");
			verificar = false;
		} else if (!txtNombre.getText().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*$")) {
			alertaError.setContentText("El nombre no puede contener números o letras especiales.");
			verificar = false;
		} else if (!txtPrecio.getText().matches("^[0-9]+$")) {
			alertaError.setContentText("El precio solo debe contener números.");
			verificar = false;
		} else if (Integer.parseInt(txtPrecio.getText()) <= 0) {
			alertaError.setContentText("El precio debe ser mayor a 0.");
			verificar = false;
		} else if (!txtIva.getText().matches("^[0-9]+$")) {
			alertaError.setContentText("El IVA solo debe contener números.");
			verificar = false;
		} else if (Integer.parseInt(txtIva.getText()) < 0 || Integer.parseInt(txtIva.getText()) > 100) {
			alertaError.setContentText("El IVA debe estar entre 0 y 100.");
			verificar = false;
		} else if (!txtStock.getText().matches("^[0-9]+$")) {
			alertaError.setContentText("El stock solo debe contener números.");
			verificar = false;
		} else if (Integer.parseInt(txtStock.getText()) <= 0) {
			alertaError.setContentText("El stock debe ser mayor a 0.");
			verificar = false;
		} else if (!txtDescuento.getText().matches("^[0-9]+$")) {
			alertaError.setContentText("El descuento solo debe contener números.");
			verificar = false;
		} else if (Integer.parseInt(txtDescuento.getText()) < 0) {
			alertaError.setContentText("El descuento no puede ser negativo.");
			verificar = false;
		} else if (listaImagenes.isEmpty()) {
			alertaError.setContentText("Debe de agregar una imagen.");
			verificar = false;
		}

		if (verificar == false) {
			alertaError.showAndWait();
		}
		return verificar;
	}
}
