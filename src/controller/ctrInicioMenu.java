/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import conexion.Conexion;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Camilo Alzate
 */
public class ctrInicioMenu implements Initializable {


    @FXML
    private Label lblFirst_name;
    @FXML
    private Label lblLast_name;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        consultardatos();
    }

    public void consultardatos() {
        Conexion con = Conexion.getInstance();
        String sql = "SELECT first_name,last_name FROM users WHERE users.param_rol =2 ";
        ResultSet resultado = con.consultar(sql);
         try {
            while (resultado.next()) {
//                PRIMER NOMBRE
                   String nombre=resultado.getString("first_name");
                   String[] partsN = nombre.split("\\s+");
                   String firstName = partsN[0];
                   this.lblFirst_name.setText(firstName.toUpperCase());
                   //PRIMER APELLIDO
                    String apellido=resultado.getString("last_name");
                   String[] partsA = apellido.split("\\s+");
                   String LastName= partsA[0];
                   this.lblLast_name.setText(LastName.toUpperCase());
       
            }
         
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        con.cerrarConexion();
    }
       
       public static String primerNombre(String fullName){
           String[] parts = fullName.split("\\s+");
           return parts [0];
           
       }
//        try {
//            ResultSet resultado = con.consultar(sql);
//            while (resultado.next()) {
//                this.lblFirst_name.setText(resultado.getString("first_name").toUpperCase());
//            }
//        } catch (SQLException e) {
//            System.out.println("Error" + e);
//        }
//
  }


