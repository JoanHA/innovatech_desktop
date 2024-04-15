package controller;

import conexion.Conexion;
import java.sql.Timestamp;
import java.time.Instant;
import model.mdlUsuario;

public class ctrUsuario {

    public boolean agregarUsuario(mdlUsuario user) {
        // Obtener una instancia de la clase Conexion
        Conexion conexion = Conexion.getInstance();
        
        // Obtener la marca de tiempo actual
        Instant instant = Instant.now();
        Timestamp timestamp = Timestamp.from(instant);
        
        try {
            // Construir la consulta SQL para insertar un nuevo usuario
            String sql = "INSERT INTO users(document, first_name, last_name, phone, email, password, param_type, param_rol, param_suscription, param_state, token, created_at)"
                    + " VALUES('" + user.getDocumento() + "','" + user.getNombres() + "','" + user.getApellidos() + "','"
                    + user.getCelular() + "','" + user.getEmail() + "','" + user.getContrasena() + "',"
                    + user.getParam_tipoDocumento() + "," + user.getParam_rol() + "," + user.getParam_suscripcion() + ","
                    + user.getUsuarioParam_estado() + ",'" + user.getToken() + "','" + timestamp + "')";
            
            // Ejecutar la consulta utilizando la conexión
            return conexion.ejecutar(sql);
        } catch (Exception e) {
            // Manejar cualquier excepción y retornar false en caso de error
            return false;
        } finally {
            // Cerrar la conexión después de usarla
            conexion.cerrarConexion();
        }
    }
}
