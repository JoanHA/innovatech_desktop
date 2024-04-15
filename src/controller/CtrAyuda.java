/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Dragnell
 */
public class CtrAyuda implements Initializable {

	@FXML
	private VBox vboxPadre;
	@FXML
	private Label clickRecuperarContrasena;
	@FXML
	private Label clickEditarDatos;
	@FXML
	private Label clickCrearProducto;
	@FXML
	private Label clickActualizarProducto;
	@FXML
	private Label clickDeshabilitarProducto;
	@FXML
	private Label clickInformes;
	@FXML
	private Label clickReenviarInformes;
	@FXML
	private Label clickPQRS;
	@FXML
	private Label clickProveedor;
	@FXML
	private Label clickEnviarPedido;
	@FXML
	private Label clickConfirmarPedido;
	@FXML
	private Label clickCarrusels;
	@FXML
	private Label clickPuntosFisicos;
	@FXML
	private Label clickParametros;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		clickRecuperarContrasena.setOnMouseClicked(e -> recuperarContrasena(clickRecuperarContrasena));
		clickEditarDatos.setOnMouseClicked(e -> editarDatos(clickEditarDatos));
		clickCrearProducto.setOnMouseClicked(e -> crearProducto(clickCrearProducto));
		clickActualizarProducto.setOnMouseClicked(e -> actualizarProducto(clickActualizarProducto));
		clickDeshabilitarProducto.setOnMouseClicked(e -> deshabilitarProducto(clickDeshabilitarProducto));
		clickInformes.setOnMouseClicked(e -> crearInforme(clickInformes));
		clickReenviarInformes.setOnMouseClicked(e -> reenviarInforme(clickReenviarInformes));
		clickPQRS.setOnMouseClicked(e -> pqrs(clickPQRS));
		clickProveedor.setOnMouseClicked(e -> proveedor(clickProveedor));
		clickEnviarPedido.setOnMouseClicked(e -> enviarPedido(clickEnviarPedido));
		clickConfirmarPedido.setOnMouseClicked(e -> confirmarPedido(clickConfirmarPedido));
		clickCarrusels.setOnMouseClicked(e -> carrusel(clickCarrusels));
		clickPuntosFisicos.setOnMouseClicked(e -> puntosFisicos(clickPuntosFisicos));
		clickParametros.setOnMouseClicked(e -> parametros(clickParametros));
		recuperarContrasena(clickRecuperarContrasena);
		editarDatos(clickEditarDatos);
	}

	int rc = 0;
	Label labelRecuperar = new Label();

	void recuperarContrasena(Label label) {
		labelRecuperar.setFont(Font.font("System", 14));
		labelRecuperar.setText("1.	Presiona olvide mi contraseña en el inicio de sesión.\n"
			+ "2.	Espera el código de verificación en el correo registrado.\n"
			+ "3.	Llena los campos.\n"
			+ "4.	Presiona el botón de guardar.\n"
			+ "5.	Inicia sesión con la nueva contraseña.");
		VBox vBox = new VBox(labelRecuperar);
		if (rc == 0) {
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(label) + 1, vBox);
			rc = 1;
		} else {
			this.vboxPadre.getChildren().remove(vBox);
			rc = 0;
		}
	}

	int ed = 0;
	Label labelEditarDatos = new Label();

	void editarDatos(Label label) {
		labelEditarDatos.setFont(Font.font("System", 14));
		labelEditarDatos.setText("1.	Edita los datos que deseas cambiar.\n"
			+ "2.	Presiona el botón modificar.\n"
			+ "3.	Escriba el código de verificación que llego a su correo electrónico.\n"
			+ "4.	Por último, presiona aceptar.");
		VBox vBox = new VBox(labelEditarDatos);
		if (ed == 0) {
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(label) + 1, vBox);
			ed = 1;
		} else {
			this.vboxPadre.getChildren().remove(vBox);
			ed = 0;
		}
	}

	int cp = 0;
	Label labelCrearProductos = new Label();

	void crearProducto(Label label) {
		labelCrearProductos.setFont(Font.font("System", 14));
		labelCrearProductos.setText("1.	Llena los campos requeridos para los productos.\n"
			+ "2.	En los campos colores y etiquetas al seleccionar se agrega a la lista, si se vuelve a seleccionar se elimina de la lista.\n"
			+ "3.	Si deseas puedes seleccionar el botón de más para crear un nuevo parámetro.\n"
			+ "4.	Para agregar imágenes presiona agregar imágenes y luego nueva imagen, puedes agregar un máximo de 5 imágenes por producto.\n"
			+ "5.	Para observar las imágenes subidas presiona el botón ver de la lista."
			+ "7. En caso de querer cambiar la imagen actual presiona actualizar\n"
			+ "8.	Presiona aceptar y agregar para guardar el producto."
			+ "9. Ten en cuenta la visibilidad del producto, ya que eso determina de si está disponible para su venta.");
		VBox vBox = new VBox(labelCrearProductos);
		if (cp == 0) {
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(label) + 1, vBox);
			cp = 1;
		} else {
			this.vboxPadre.getChildren().remove(vBox);
			cp = 0;
		}
	}

	int ap = 0;
	Label labelActualizarProductos = new Label();

	void actualizarProducto(Label label) {
		labelActualizarProductos.setFont(Font.font("System", 14));
		labelActualizarProductos.setText("1.	Selecciona el producto que deseas modificar.\n"
			+ "2.	Cambia los datos necesarios.\n"
			+ "3.	De ser necesario agrega las imágenes o modifica las que ya estén en la lista.\n"
			+ "4.	Guarda los cambios. ");
		VBox vBox = new VBox(labelActualizarProductos);
		if (ap == 0) {
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(label) + 1, vBox);
			ap = 1;
		} else {
			this.vboxPadre.getChildren().remove(vBox);
			ap = 0;
		}
	}

	int dp = 0;
	Label labelDeshabilitarProductos = new Label();

	void deshabilitarProducto(Label label) {
		labelDeshabilitarProductos.setFont(Font.font("System", 14));
		labelDeshabilitarProductos.setText("1.	Busca la venta que desees.\n"
			+ "2.	Presiona el botón de ver detalles para observar los detalles de esa venta.");
		VBox vBox = new VBox(labelDeshabilitarProductos);
		if (dp == 0) {
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(label) + 1, vBox);
			dp = 1;
		} else {
			this.vboxPadre.getChildren().remove(vBox);
			dp = 0;
		}
	}

	int ci = 0;
	Label labelCrearInforme = new Label();

	void crearInforme(Label label) {
		labelCrearInforme.setFont(Font.font("System", 14));
		labelCrearInforme.setText("1.	Presiona nuevo informe.\n"
			+ "2.	Llena los campos y carga una imagen si lo deseas (El campo descripción permite etiquetas HTML básicas)\n"
			+ "4.	Presiona enviar (el correo les llegara a todos los usuarios que permitieron correos de la plataforma).");
		VBox vBox = new VBox(labelCrearInforme);
		if (ci == 0) {
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(label) + 1, vBox);
			ci = 1;
		} else {
			this.vboxPadre.getChildren().remove(vBox);
			ci = 0;
		}
	}

	int ri = 0;
	Label labelReenviarInforme = new Label();

	void reenviarInforme(Label label) {
		labelReenviarInforme.setFont(Font.font("System", 14));
		labelReenviarInforme.setText("1.	En la lista de los informes presiona ver en un informe.\n"
			+ "2.	Presiona reenviar.");
		VBox vBox = new VBox(labelReenviarInforme);
		if (ri == 0) {
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(label) + 1, vBox);
			ri = 1;
		} else {
			this.vboxPadre.getChildren().remove(vBox);
			ri = 0;
		}
	}

	int pqrs = 0;
	Label labelPQRS = new Label();

	void pqrs(Label label) {
		labelPQRS.setFont(Font.font("System", 14));
		labelPQRS.setText("1.	En cualquier lista de los PQRS presiona Ver detalles en el de tu preferencia.\n"
			+ "2.	En caso de entablar una conversación con el usuario presiona responder.\n"
			+ "3.	Se abrirá un link en el cual si sigues los pasos correctamente, abre una conversación por WhatsApp con el usuario.");
		VBox vBox = new VBox(labelPQRS);
		if (pqrs == 0) {
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(label) + 1, vBox);
			pqrs = 1;
		} else {
			this.vboxPadre.getChildren().remove(vBox);
			pqrs = 0;
		}
	}

	int pr = 0;
	Label labelProveedor = new Label();

	void proveedor(Label label) {
		labelProveedor.setFont(Font.font("System", 14));
		labelProveedor.setText("1.	En la lista de los proveedores presiona nuevo.\n"
			+ "2.	Llena los campos y presiona registrar.\n"
			+ "3.	Para actualizar los datos de un proveedor presiona actualizar en la lista.\n"
			+ "4.	Cambia los datos que sean necesarios.\n"
			+ "5.	Presiona actualizar.");
		VBox vBox = new VBox(labelProveedor);
		if (pr == 0) {
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(label) + 1, vBox);
			pr = 1;
		} else {
			this.vboxPadre.getChildren().remove(vBox);
			pr = 0;
		}
	}

	int ep = 0;
	Label labelEnviarPedido = new Label();

	void enviarPedido(Label label) {
		labelEnviarPedido.setFont(Font.font("System", 14));
		labelEnviarPedido.setText("1.	En la lista de compras presiona nueva compra.\n"
			+ "2.	Selecciona un proveedor.\n"
			+ "3.	Agrega y llena los campos necesarios.\n"
			+ "4.	Presiona enviar pedido y se enviara un archivo PDF con una lista de los pedidos solicitados al correo del proveedor."
			+ "5.	Presiona actualizar.");
		VBox vBox = new VBox(labelEnviarPedido);
		if (ep == 0) {
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(label) + 1, vBox);
			ep = 1;
		} else {
			this.vboxPadre.getChildren().remove(vBox);
			ep = 0;
		}
	}

	int cop = 0;
	Label labelConfirmarPedido = new Label();

	void confirmarPedido(Label label) {
		labelConfirmarPedido.setFont(Font.font("System", 14));
		labelConfirmarPedido.setText("1.	En la lista de compras presiona nueva compra.\n"
			+ "2.	Presiona Detalles.\n"
			+ "3.	Cambia el estado del pedido a Recibido.\n"
			+ "4.	Cambia el estado del producto a disponible.\n"
			+ "5.	Llena los campos y guarda el producto.\n"
			+ "6.	Has esto para cada uno de los productos.");
		VBox vBox = new VBox(labelConfirmarPedido);
		if (cop == 0) {
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(label) + 1, vBox);
			cop = 1;
		} else {
			this.vboxPadre.getChildren().remove(vBox);
			cop = 0;
		}
	}

	int cs = 0;
	Label labelCarrusel = new Label();

	void carrusel(Label label) {
		labelCarrusel.setFont(Font.font("System", 14));
		labelCarrusel.setText("1.	En la lista del carrusel presiona nuevo.\n"
			+ "2.	Carga una imagen y selecciona la posición en la que se mostrara luego presiona guardar.\n"
			+ "3.	Puedes eliminar y actualizar la posición de un registro.");
		VBox vBox = new VBox(labelCarrusel);
		if (cs == 0) {
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(label) + 1, vBox);
			cs = 1;
		} else {
			this.vboxPadre.getChildren().remove(vBox);
			cs = 0;
		}
	}

	int pf = 0;
	Label labelPuntosFisicos = new Label();

	void puntosFisicos(Label label) {
		labelPuntosFisicos.setFont(Font.font("System", 14));
		labelPuntosFisicos.setText("1.	Una vez en la lista de los puntos físicos presiona Nuevo.\n"
			+ "2.	Luego selecciona el departamento.\n"
			+ "3.	Una vez seleccionado el departamento selecciona el municipio.\n"
			+ "4.	Luego proceda a llenar los campos y seleccionar el estado del punto físico.\n"
			+ "5.	Puede editar cuando desee sus puntos físicos.");
		VBox vBox = new VBox(labelPuntosFisicos);
		if (pf == 0) {
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(label) + 1, vBox);
			pf = 1;
		} else {
			this.vboxPadre.getChildren().remove(vBox);
			pf = 0;
		}
	}

	int par = 0;
	Label labelParametros = new Label();

	void parametros(Label label) {
		labelParametros.setFont(Font.font("System", 14));
		labelParametros.setText("1.	En la lista de parámetros presiona Nuevo.\n"
			+ "2.	Elige el tipo de parámetro.\n"
			+ "3.	Llena los campos requeridos y secciona su estado.\n"
			+ "4.	Presiona Agregar.\n"
			+ "5.	Una vez creado no podrás editar el tipo de parámetro.");
		VBox vBox = new VBox(labelParametros);
		if (par == 0) {
			this.vboxPadre.getChildren().add(this.vboxPadre.getChildren().indexOf(label) + 1, vBox);
			par = 1;
		} else {
			this.vboxPadre.getChildren().remove(vBox);
			par = 0;
		}
	}
}
