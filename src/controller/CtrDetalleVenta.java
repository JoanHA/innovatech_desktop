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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MdlProductos;
import model.MdlVenta;

public class CtrDetalleVenta implements Initializable {

	@FXML
	private Button btnAceptar;
	@FXML
	private TextField txtUsuario;
	@FXML
	private TextField txtDireccion;
	@FXML
	private TextField txtCiudad;
	@FXML
	private TextField txtMetodoPago;
	@FXML
	private TextField txtTotal;
	@FXML
	private TextField txtEstado;
	@FXML
	private TextField txtFecha;
	@FXML
	private VBox vboxProductos;

	
	Map<String, String> listaParametros = new HashMap<>();
	
	private ObservableList<MdlProductos> productos;
	int total = 0;
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		productos = FXCollections.observableArrayList();
		parametros();
	}

	@FXML
	private void btnAceptarEvent(ActionEvent event) {
		Stage stage = (Stage) btnAceptar.getScene().getWindow();
		stage.close();
	}

	public void setVenta(int id) {
		Conexion con = Conexion.getInstance();
		Statement sentencia = null;
		ResultSet rs = null;
		try {
			sentencia = con.getConnection().createStatement();
			rs = sentencia.executeQuery("SELECT * FROM sales WHERE id =" + id);

			while (rs.next()) {
				MdlVenta venta = new MdlVenta();
				venta.setId(rs.getInt("id"));
				venta.setMetodoPago(rs.getString("param_paymethod"));
				venta.setTotalPago(rs.getInt("total"));
				venta.setFormaEnvio(listaParametros.get(rs.getString("param_shipping")));
				venta.setEstado(listaParametros.get(rs.getString("param_status")));
				venta.setFechaRegistro(rs.getString("created_at"));
				venta.setFechaModificacion(rs.getString("updated_at"));
				venta.setUsuarioId(rs.getString("user_id"));
				venta.setDireccion(rs.getString("address_id"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally{
			con.cerrarConexion();
		}
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
	
	private void setDireccion(String address_id) {
		Conexion con = Conexion.getInstance();
		Statement sentencia = null;
		ResultSet rs = null;
		try {
			sentencia = con.getConnection().createStatement();
			rs = sentencia.executeQuery("SELECT * FROM address JOIN params ON param_city = params.id WHERE address.id =" +address_id);
			if (rs.next()) {
				txtDireccion.setText(rs.getString("address.address"));
				txtCiudad.setText(rs.getString("params.name"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}con.cerrarConexion();
	}

	private String getUsuario(String usuario) {
		Conexion con = Conexion.getInstance();
		Statement sentencia = null;
		ResultSet rs = null;
		try {
			sentencia = con.getConnection().createStatement();
			rs = sentencia.executeQuery("SELECT first_name FROM users WHERE id = " + usuario);

			if (rs.next()) {
				usuario = rs.getString("first_name");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		con.cerrarConexion();

		return usuario;
	}

	void cargarDatos(MdlVenta venta) {
		txtUsuario.setText(getUsuario(venta.getUsuarioId()));
		txtMetodoPago.setText(venta.getMetodoPago());
		txtEstado.setText(venta.getEstado());
		txtFecha.setText(venta.getFechaRegistro());
		setDireccion(venta.getDireccion());
		cargarListaProductos(venta.getId());
	}

	private void cargarListaProductos(int id) {
		Conexion con = Conexion.getInstance();

		Statement sentencia = null;
		ResultSet rs = null;
		try {
			sentencia = con.getConnection().createStatement();
			rs = sentencia.executeQuery("SELECT * FROM sales_details JOIN products ON sales_details.product_id = products.id WHERE sale_id =" + id);
			
			while (rs.next()) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VstVenta.fxml"));
				MdlProductos producto = new MdlProductos();
				producto.setNombre(rs.getString("name"));
				producto.setReferencia(rs.getString("code"));
				producto.setImagen(rs.getString("images"));
				total = total +rs.getInt("qty");
				try {
					VBox vbox = loader.load();
					CtrVenta ctrVenta = loader.getController();
					ctrVenta.cargarVenta(producto.getNombre(), producto.getReferencia(), rs.getString("qty"),
						producto.getImagen());
					vboxProductos.getChildren().add(vbox);
				} catch (IOException ex) {
					System.out.println("Error: " + ex);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			txtTotal.setText(String.valueOf(this.total));
			con.cerrarConexion();
		}
	}
}
