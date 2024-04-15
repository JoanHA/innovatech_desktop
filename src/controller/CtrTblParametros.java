/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import conexion.Conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.MdlDirecciones;
import model.MdlParametros;

/**
 * FXML Controller class
 *
 * @author SENA
 */
public class CtrTblParametros implements Initializable {

	private ObservableList<MdlParametros> listParametros;

	@FXML
	private TextField txtBuscar;
	@FXML
	private Button btnNuevo;
	@FXML
	private TableColumn colTipo;
	@FXML
	private TableColumn colParametro;
	@FXML
	private TableColumn colOpciones;
	@FXML
	private TableView<MdlParametros> tblParametros;
	@FXML
	private TableColumn colId;

	int maxRowsToShow = 50;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		listParametros = FXCollections.observableArrayList();

		this.colId.setCellValueFactory(new PropertyValueFactory("id"));
		this.colTipo.setCellValueFactory(new PropertyValueFactory("nombreTipo"));
		this.colParametro.setCellValueFactory(new PropertyValueFactory("nombre"));
		this.colOpciones.setCellValueFactory(new PropertyValueFactory("opciones"));
		cargarTabla();
	}

	@FXML
	private void eventKey(KeyEvent event) {
		Object evt = event.getSource();
		if (evt.equals(txtBuscar)) {

			if (listParametros.isEmpty()) {
				Label lb = new Label("Ningun dato encontrado");
				tblParametros.setPlaceholder(lb);
			} else {
				buscar(txtBuscar.getText());
			}
			if (txtBuscar.getText().equals("")) {
				ObservableList<MdlParametros> limitedItems = FXCollections.observableArrayList();
				limitedItems.addAll(listParametros.subList(0, Math.min(maxRowsToShow, listParametros.size())));
				tblParametros.setItems(limitedItems);
			}
		}
	}

	@FXML
	private void clickNuevo(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VwCrearParametros.fxml"));
			Parent root = loader.load();

			CtrCrearParametros controlador = loader.getController();
			String tipo = "";
			controlador.initAttributtes(this.listParametros, tipo);

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.showAndWait();
			cargarTabla();
			if (txtBuscar.getText().equals("")) {
				cargarTabla();
			} else {
//				buscar(txtBuscar.getText());
			}
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	void cargarTabla() {
		listParametros.clear();
		Conexion con = Conexion.getInstance();
		String sql = "SELECT * FROM params "
			+ "JOIN param_types ON param_types.id = params.paramtype_id WHERE param_types.param_state = 5";

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

				//Creo dos botones
				Button btnModificar = new Button("Actualizar");

				//Creo acciones para cada uno de ellos.
				btnModificar.setOnAction((event) -> {
					modificar(model);
				});

				btnModificar.setStyle("-fx-font-weight: bold;");
				btnModificar.setPrefSize(150, 30);
				btnModificar.getStylesheets().add("/style/Style.css");
				btnModificar.getStyleClass().setAll("btnWarning");

				HBox opciones = new HBox(btnModificar);
				opciones.setAlignment(Pos.CENTER);
				opciones.setSpacing(5);

				model.setOpciones(opciones);

				this.listParametros.add(model);
			}
		} catch (Exception e) {
		}
		con.cerrarConexion();
		ObservableList<MdlParametros> limitedItems = FXCollections.observableArrayList();
		limitedItems.addAll(listParametros.subList(0, Math.min(maxRowsToShow, listParametros.size())));
		tblParametros.setItems(limitedItems);
	}

	private void modificar(MdlParametros model) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VwModificarParametros.fxml"));
			Parent root = loader.load();

			CtrModificarParametros controlador = loader.getController();
			controlador.initAttributtes(model, listParametros);

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.showAndWait();

			if (txtBuscar.getText().isEmpty()) {
				//Remuevo todo lo de la lista y refresco la tabla.
				listParametros.clear();
				tblParametros.refresh();

				//Cargo la tabla.
				cargarTabla();
			}
		} catch (IOException e) {
		}
	}

	void buscar(String municipio) {
		ObservableList<MdlParametros> listaBuscar = FXCollections.observableArrayList();
		for (int i = 0; i < listParametros.size(); i++) {
			if (listParametros.get(i).getNombreTipo().toLowerCase().contains(municipio.toLowerCase())
				|| listParametros.get(i).getNombre().toLowerCase().contains(municipio.toLowerCase())) {
				listaBuscar.add(listParametros.get(i));
			}
		}
		ObservableList<MdlParametros> limitedItems = FXCollections.observableArrayList();
		limitedItems.addAll(listaBuscar.subList(0, Math.min(maxRowsToShow, listaBuscar.size())));
		tblParametros.setItems(limitedItems);
	}
}
