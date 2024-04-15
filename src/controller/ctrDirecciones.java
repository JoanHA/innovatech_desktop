/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import model.MdlDirecciones;

/**
 *
 * @author Dragnell
 */
public class ctrDirecciones {

	public boolean crearPuntoFisico(MdlDirecciones direccion) {
		Instant instant = Instant.now();
		Timestamp timestamp = Timestamp.from(instant);
		Conexion con = Conexion.getInstance();
		String sql = "INSERT INTO address(user_id,address,floor,hood,param_city,param_state,created_at)"
				+ "VALUES('" + idAdmin() + "','" + direccion.getDireccion() + "','" + direccion.getPiso() + "','"
				+ direccion.getBarrio() + "'," + direccion.getMunicipio() + ",'" + direccion.getEstado() + "','"
				+ timestamp + "')";
		if (con.ejecutar(sql)) {
			con.cerrarConexion();
			return true;
		}else{
			con.cerrarConexion();
			return false;
		}
	}

	int idAdmin() {
		Conexion con = Conexion.getInstance();
		String sql = "select * from users where param_rol = 2";
		ResultSet rs = con.consultar(sql);
		int id = 0;
		try {
			while (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
		}
		return id;
	}
}
