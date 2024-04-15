package controller;

import conexion.Conexion;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.MdlParametros;
import model.MdlProductos;

public class CtrActualizarProducto implements Initializable {

	@FXML
	private ImageView imgProducto;
	@FXML
	private Button btnActualizarProducto;
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
	private Button btnCancelar;
	@FXML
	private RadioButton rbtnSi;
	@FXML
	private RadioButton rbtnNo;
	@FXML
	private HBox hboxImagen;
	@FXML
	private Button btnActualizarImagen;

	private ArrayList<String> listaColores = new ArrayList();
	private ArrayList<String> listaEtiquetas = new ArrayList();
	private ArrayList<String> listaImagenes = new ArrayList();
	private MdlProductos producto = new MdlProductos();

	Map<String, String> parametros = new HashMap<>();
	Map<String, String> parametrosMuestra = new HashMap<>();

	private ObservableList<MdlParametros> listParametros = FXCollections.observableArrayList();
	private int idProducto;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		listaPram();
		llenarParametros("");
	}

	void llenarParametros(String index) {
		for (int i = 0; i < listParametros.size(); i++) {
			parametros.put(listParametros.get(i).getNombre(), listParametros.get(i).getId());
			parametrosMuestra.put(listParametros.get(i).getId(), listParametros.get(i).getNombre());
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
		if (txtDescripcion.getText().length() >= 255) {
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
	private void btnActualizarProductoEvent(ActionEvent event) {
		if (verificacion()) {
			Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
			alertaConfirmacion.setTitle("Confirmación");
			alertaConfirmacion.setHeaderText(null);
			alertaConfirmacion.setContentText("¿Estás seguro de que quieres actualizar este producto?");
			Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();

			if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
				actualizarProducto();
				Stage stage = (Stage) btnActualizarProducto.getScene().getWindow();
				stage.close();
			}
		}
	}

	@FXML
	private void btnActualizarImagenEvent(ActionEvent event) {
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
		} catch (SQLException e) {
			System.out.println(e);
		}
		con.cerrarConexion();
	}

	private void nuevoParametro(String nombre) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VwCrearParametros.fxml"));
			Parent root = loader.load();
			CtrCrearParametros ctrCrear = loader.getController();
			ctrCrear.initAttributtes(listParametros, nombre);

			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();
			listaPram();
			setProducto(this.idProducto);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
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

	public void setProducto(int id) {
		Conexion con = Conexion.getInstance();
		ResultSet rs = null;
		Statement sentencia = null;
		try {
			sentencia = con.getConnection().createStatement();
			rs = sentencia.executeQuery("SELECT * FROM products WHERE id = " + id);
			if (rs.next()) {
				this.producto.setId(id);
				this.producto.setNombre(rs.getString("name"));
				this.producto.setPrecio(rs.getInt("price"));
				this.producto.setReferencia(rs.getString("code"));
				this.producto.setStock(rs.getInt("stock"));
				this.producto.setImagen(rs.getString("images"));
				if (this.producto.getImagen() != null) {
					listaImagenes = cargarLista(producto.getImagen(), 2);
				}
				this.producto.setDescripcion(rs.getString("description"));
				this.producto.setIva(rs.getInt("tax"));
				this.producto.setDescuento(rs.getInt("discount"));
				this.producto.setEstado(rs.getString("param_state"));
				this.producto.setFechaRegistro(rs.getString("created_at"));
				this.producto.setFechaModificacion(rs.getString("updated_at"));
				this.producto.setCategoria(this.parametrosMuestra.get(rs.getString("param_category")));
				this.producto.setEtiquetas(rs.getString("param_tags"));
				if (this.producto.getEtiquetas() != null) {
					listaEtiquetas = cargarLista(producto.getEtiquetas(), 1);
				}
				this.producto.setColor(rs.getString("param_color"));
				if (this.producto.getColor() != null) {
					listaColores = cargarLista(producto.getColor(), 1);
				}
				producto.setMarca(this.parametrosMuestra.get(rs.getString("param_mark")));
				listaPram();
				cmbEtiquetas.getItems().add(0, String.join(",", listaEtiquetas));
				cmbColor.getItems().add(0, String.join(",", listaColores));
				cmbEtiquetas.getSelectionModel().select(0);
				cmbColor.getSelectionModel().select(0);
				setImagen();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		con.cerrarConexion();
		cargarDatos();
	}

	public void cargarDatos() {
		txtNombre.setText(producto.getNombre());
		txtPrecio.setText(String.valueOf(producto.getPrecio()));
		txtStock.setText(String.valueOf(producto.getStock()));
		txtReferencia.setText(producto.getReferencia());
		txtDescripcion.setText(producto.getDescripcion());
		txtDescuento.setText(String.valueOf(producto.getDescuento()));
		cmbCategoria.setValue(producto.getCategoria());
		cmbMarca.setValue(producto.getMarca());
		cmbEtiquetas.getItems().set(0, String.join(",", listaEtiquetas));
		cmbColor.getItems().set(0, String.join(",", listaColores));
		setEstado();
		setImagen();
	}

	private void setEstado() {
		if (producto.getEstado().equals("5")) {
			rbtnNo.setSelected(false);
			rbtnSi.setSelected(true);
		} else if (producto.getEstado().equals("6")) {
			rbtnSi.setSelected(false);
			rbtnNo.setSelected(true);
		}
	}

	private String getEstado() {
		String estado = "";

		if (rbtnSi.isSelected()) {
			estado = "5";
		} else if (rbtnNo.isSelected()) {
			estado = "6";
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
			ctrImagenes.actua();

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

	private void actualizarProducto() {
		Conexion con = Conexion.getInstance();
		String sql = null;
		PreparedStatement sentencia = null;

		try {
			sql = "UPDATE products SET name = ?, price = ?, code = ?, stock = ?, param_mark = ?, images = ?, param_color = ?, description = ?, param_category = ?, param_tags = ?, discount = ?, param_state = ?, updated_at = ? WHERE id = ?";
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
			sentencia.setInt(11, Integer.parseInt(txtDescuento.getText()));
			sentencia.setString(12, getEstado());
			sentencia.setString(13, getFechaHoy());
			sentencia.setInt(14, producto.getId());
			int filas = sentencia.executeUpdate();

			if (filas > 0) {
				Alert alertaInformacion = new Alert(Alert.AlertType.INFORMATION);
				alertaInformacion.setTitle("Actualización exitosa");
				alertaInformacion.setHeaderText(null);
				alertaInformacion.setContentText("El producto se actualizó correctamente.");
				alertaInformacion.showAndWait();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		con.cerrarConexion();
	}

	private ArrayList<String> cargarLista(String datos, int tipo) {
		ArrayList<String> lista = new ArrayList<>(Arrays.asList(datos.split(":")));

		if (tipo == 1) {
			for (int i = 0; i < lista.size(); i++) {
				lista.set(i, this.parametrosMuestra.get(lista.get(i)));
			}
		}
		return lista;
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

	private boolean verificacion() {
		boolean verificar = true;
		Alert alertaError = new Alert(Alert.AlertType.ERROR);
		alertaError.setTitle("Error");
		alertaError.setHeaderText("Error al registrar");

		if (txtNombre.getText() == null || txtPrecio.getText().isEmpty() || txtReferencia.getText().isEmpty() || txtStock.getText().isEmpty() || txtDescripcion.getText().isEmpty() || txtDescuento.getText().isEmpty() || txtIva.getText().isEmpty() || listaEtiquetas.isEmpty() || listaColores.isEmpty() || cmbCategoria.getValue() == null || cmbMarca.getValue() == null) {
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
		} else if (!txtStock.getText().matches("^[0-9]+$")) {
			alertaError.setContentText("La cantidad solo debe contener números.");
			verificar = false;
		} else if (Integer.parseInt(txtStock.getText()) <= 0) {
			alertaError.setContentText("La cantidad debe ser mayor a 0.");
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

	void initAttributtes(int id) {
		this.idProducto = id;
		setProducto(id);
	}
}
