package controller;

import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import model.MdlParametros;

public class CtrParametros {

	public boolean guardarParametro(MdlParametros model) {

		Instant instant = Instant.now();
		Timestamp timestamp = Timestamp.from(instant);
		Conexion con = Conexion.getInstance();
		boolean sw = false;
		String sql = "INSERT INTO params(id,paramtype_id,name,param_foreign,param_state,created_at)"
				+ "VALUES(" + verificarId(id(model.getNombreTipo()), model.getNombreTipo()) + ","
				+ model.getNombreTipo() + ",'" + model.getNombre() + "'," + model.getForeign() + ","
				+ model.getEstado() + ",'" + timestamp + "')";
		try {
			sw = con.ejecutar(sql);
			return sw; 
		} catch (Exception e) {
			System.out.println(e);
			return sw;
		}finally{
			con.cerrarConexion();
		}

	}

	int id(String tipo_param) {
		int id = 0;
		Conexion con = Conexion.getInstance();
		String sql = "SELECT MAX(id) AS id FROM params WHERE paramtype_id = " + tipo_param;
		ResultSet rs = con.consultar(sql);
		try {
			while (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			System.out.println("no se pudo recolectar el id" + e);
		}

		

		return id + 1;
	}

	int verificarId(int id, String idTipo) {
		if (id == 1) {
			Conexion con = Conexion.getInstance();
			String sql = "SELECT range_min FROM param_types WHERE id = " + idTipo;
			ResultSet rs = con.consultar(sql);
			try {
				while (rs.next()) {
					id = rs.getInt("range_min");
				}
			} catch (SQLException e) {
				System.out.println("no se verifico el id correctamente" + e);
			}
		}
		return id;
	}
}
