package controller;

import conexion.Conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.MdlVenta;

public class CtrVerVenta implements Initializable {

	@FXML
	private TextField txtBuscar;
	@FXML
	private TableView<MdlVenta> tblVentas;
	@FXML
	private TableColumn<MdlVenta, String> colId;
	@FXML
	private TableColumn<MdlVenta, String> colTotal;
	@FXML
	private TableColumn<MdlVenta, String> colFormaEnvio;
	@FXML
	private TableColumn<MdlVenta, String> colEstado;
	@FXML
	private TableColumn<MdlVenta, String> colUsuario;
	@FXML
	private TableColumn<MdlVenta, String> colRegistro;
	@FXML
	private TableColumn<MdlVenta, Button> colOpciones;
	Map<String, String> listaParametros = new HashMap<>();
	private ObservableList<MdlVenta> ventasList;

	@Override

	public void initialize(URL url, ResourceBundle rb) {
		ventasList = FXCollections.observableArrayList();

		this.colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.colTotal.setCellValueFactory(new PropertyValueFactory<>("totalPago"));
		this.colFormaEnvio.setCellValueFactory(new PropertyValueFactory<>("formaEnvio"));
		this.colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
		this.colUsuario.setCellValueFactory(new PropertyValueFactory<>("documento"));
		this.colRegistro.setCellValueFactory(new PropertyValueFactory<>("fechaRegistro"));
		this.colOpciones.setCellValueFactory(new PropertyValueFactory<>("opciones"));
		parametros();
		cargarListas();
	}

	void parametros() {
		Conexion con = Conexion.getInstance();
		String sql = "SELECT * FROM params";
		ResultSet rs = con.consultar(sql);
		try {
			while (rs.next()) {
				listaParametros.put(rs.getString("id"), rs.getString("name"));
			}
		} catch (Exception e) {
		}
	}

	public void cargarListas() {
		tblVentas.getItems().clear();
		Conexion con = Conexion.getInstance();
		String sql = "SELECT\n" +
"    sales.user_id,\n" +
"    SUM(sales_details.qty) AS total,\n" +
"    MAX(sales.id) AS id,\n" +
"    MAX(sales.address_id) AS address_id,\n" +
"    MAX(sales.param_status) AS param_status,\n" +
"    MAX(sales.param_paymethod) AS param_paymethod,\n" +
"    MAX(sales.param_shipping) AS param_shipping,\n" +
"    MAX(sales.created_at) AS created_at,\n" +
"    MAX(sales.updated_at) AS updated_at\n" +
"FROM sales\n" +
"JOIN sales_details ON sales_details.sale_id = sales.id\n" +
"WHERE sales.param_shipping != 14\n" +
"GROUP BY sales.id;";
		ResultSet rs = con.consultar(sql);
		try {
			while (rs.next()) {
				MdlVenta venta = new MdlVenta();
				int id = rs.getInt("id");
				venta.setId(id);
				venta.setMetodoPago(this.listaParametros.get(rs.getString("param_paymethod")));
				venta.setTotalPago(rs.getInt("total"));
				venta.setFormaEnvio(this.listaParametros.get(rs.getString("param_shipping")));
				venta.setEstado(this.listaParametros.get(rs.getString("param_status")));
				venta.setFechaRegistro(rs.getString("created_at"));
				venta.setFechaModificacion(rs.getString("updated_at"));
				venta.setUsuarioId(rs.getString("user_id"));
				venta.setDireccion(rs.getString("address_id"));
				venta = getUsuario(venta);

				Button btnVerDetalles = new Button("Ver Detalles");
				btnVerDetalles.getStylesheets().add("/style/Style.css");
				btnVerDetalles.getStyleClass().setAll("btnSuccess");
				btnVerDetalles.setPrefHeight(30);
				btnVerDetalles.setPrefWidth(100);

				final MdlVenta dd = venta;
				btnVerDetalles.setOnAction(e -> {
					detalleVenta(id, dd);
				});

				venta.setOpciones(btnVerDetalles);
				ventasList.add(venta);
			}
			tblVentas.setItems(ventasList);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			con.cerrarConexion();
		}
	}

	private MdlVenta getUsuario(MdlVenta venta) {
		Conexion con = Conexion.getInstance();
		Statement sentencia = null;
		ResultSet rs = null;
		try {
			sentencia = con.getConnection().createStatement();
			rs = sentencia.executeQuery("SELECT * FROM users where id = " + venta.getUsuarioId());

			if (rs.next()) {
				venta.setDocumento(rs.getString("document"));
				venta.setNombres(rs.getString("first_name"));
				venta.setApellidos(rs.getString("last_name"));
				venta.setEmail(rs.getString("email"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return venta;
	}

	public void detalleVenta(int id, MdlVenta venta) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VstDetalleVenta.fxml"));
			Parent root = loader.load();
			CtrDetalleVenta ctrDetalleVenta = loader.getController();
			ctrDetalleVenta.setVenta(id);
			ctrDetalleVenta.cargarDatos(venta);

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	void buscar(String municipio) {
		ObservableList<MdlVenta> listaBuscar = FXCollections.observableArrayList();
		for (int i = 0; i < ventasList.size(); i++) {
			if (ventasList.get(i).getDocumento().toLowerCase().contains(municipio.toLowerCase())
				|| ventasList.get(i).getFechaRegistro().toLowerCase().contains(municipio.toLowerCase())) {
				listaBuscar.add(ventasList.get(i));
			}
		}
		this.tblVentas.setItems(listaBuscar);
	}

	@FXML
	private void eventKey(KeyEvent event) {
		Object evt = event.getSource();
		if (evt.equals(txtBuscar)) {

			if (ventasList.isEmpty()) {
				Label lb = new Label("Ningún dato encontrado.");
				tblVentas.setPlaceholder(lb);
			} else {
				buscar(txtBuscar.getText());

			}
			if (txtBuscar.getText().equals("")) {
				tblVentas.setItems(ventasList);
			}
		}
	}
}
