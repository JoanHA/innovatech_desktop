package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Conexion implements Configuracion {

	private static Connection con = null;
	private static Conexion instance = null;

	private Conexion() {
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
			if (con != null) {
				System.out.println("Conexion exitosa");
			} else {
				System.out.println("Conexion fallida");
			}
		} catch (ClassNotFoundException error) {
			String mensaje = "Error en los archivos internos de la aplicación. \n"
				+ "Si el problema persiste intenta reinstalando la aplicación. \n"
				+ "Mensaje de error: "+ error;
			JOptionPane.showMessageDialog(null, mensaje);
		} catch (SQLException ex) {
			String mensaje = "Por favor revisa tu conexión a internet e intenta nuevamente.\n";
			JOptionPane.showMessageDialog(null, mensaje);
		}
	}

	public static Conexion getInstance() {
		if (instance == null || !isConnectionOpen()) {
			instance = new Conexion();
		}
		return instance;
	}

	// Resto de los métodos...
	private static boolean isConnectionOpen() {
		try {
			return con != null && !con.isClosed();
		} catch (SQLException ex) {
			Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public Connection getConnection() {
		return con;
	}

	public boolean ejecutar(String sql) {
		boolean var;
		try {
			Statement sentencia = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			var = sentencia.executeUpdate(sql) != 0;
		} catch (SQLException error) {
			System.out.println("Error en ejecución: " + error);
			var = false;
		}
		return var;
	}

	public ResultSet consultar(String sql) {
		ResultSet resultado;
		try {
			Statement sentencia = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			resultado = sentencia.executeQuery(sql);
		} catch (SQLException error) {
			System.out.println("Error en la consulta: " + error.getMessage());
			resultado = null;
		}
		return resultado;
	}

	public void cerrarConexion() {
		try {
			if (con != null) {
				con.close();
				System.out.println("Conexion cerrada");
			}
		} catch (SQLException ex) {
			Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
