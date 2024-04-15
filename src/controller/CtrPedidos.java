/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package controller;

import model.MdlPedidos;
import model.MdlProveedores;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import conexion.Conexion;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.Properties;
import java.util.Set;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author root
 */
public class CtrPedidos implements Initializable {
    //matriz para guardar el nombre y id del proveedor

    HashMap<String, String> Nombreproveedores = new HashMap<>();

    private ArrayList<HBox> hboxes = new ArrayList<>();
    private ArrayList proveedores = new ArrayList();
    public ArrayList camposLlenos = new ArrayList();
    private int contadorHboxes = 7;
    int cantidadProductos = 0;
    Boolean rbtnActivado = false;
    boolean pedidoEnviado = false;
    boolean detalleEnviado = false;

    //arrayList para crear el excel
    ArrayList<MdlPedidos> p = new ArrayList<>();
    //fecha de hoy en timeStamp
    Long datetime = System.currentTimeMillis();
    Timestamp timestamp = new Timestamp(datetime);
    public String pedido;

    public ObservableList<String> obProve = FXCollections.observableArrayList();
    @FXML
    private BorderPane rootPane;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtNumero;
    @FXML
    private ComboBox<String> cbbProveedores;

    @FXML
    private RadioButton RbtnCorreo;
    @FXML
    private VBox rootVbox;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEnviar;
    @FXML
    private HBox hb1;
    @FXML
    private HBox hb2;
    @FXML
    private HBox hb3;
    @FXML
    private HBox hb4;
    @FXML
    private HBox hb5;
    @FXML
    private HBox hb6;
    @FXML
    private HBox hb7;
    @FXML
    private TextField txtid;
    DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
    String date = dateFormat.format(new Date());
    //variable para ver si el pedido fue enviado o no
    Boolean enviado = false;
    @FXML
    private Button btnCancelar;

    private int ultimo_pedido;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pedido = "Pedido por parte de Innova Tech. \n \n  Realizado el dia " + date + ". \n \n";
        //Guardar los primeros Hbox en un array
        hboxes.add(hb1);
        hboxes.add(hb2);
        hboxes.add(hb3);
        hboxes.add(hb4);
        hboxes.add(hb5);
        hboxes.add(hb6);
        hboxes.add(hb7);

        //Wrap in el border pane dentro de un Scroll pane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(rootVbox);
        scrollPane.setFitToWidth(true);
        try {
            btnEnviar.setOnAction(Even -> GuardarDatos());
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("Tenemos un error por favor intenta mas tarde");
            alerta.setTitle("Lo sentimos... :c");
            alerta.showAndWait();
        }
        btnEnviar.setOnAction(Even -> GuardarDatos());
        // make the scrollpane uneditable
        scrollPane.setFocusTraversable(false);
        scrollPane.setStyle("");

        rootVbox.setAlignment(Pos.CENTER);
        rootPane.setCenter(scrollPane);

        rootPane.setMaxWidth(Double.MAX_VALUE);
        rootPane.setMaxHeight(Double.MAX_VALUE);
        cbbInit();

    }

    public Boolean editarExcel(ArrayList<MdlPedidos> p) {

        boolean sent = false;
        try {
            FileInputStream file = new FileInputStream(new File("./src/Pedidos/New_Plantilla_pedidos.xlsx"));

            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet sheet = wb.getSheetAt(0);

            int filas = 7;
            //Fecha
            XSSFRow filaFecha = sheet.getRow(0);
            XSSFCell celdaFecha = filaFecha.getCell(4);
            celdaFecha.setCellValue(String.valueOf(LocalDate.now()));

            //proveedor
            XSSFRow filaProvee = sheet.getRow(2);
            XSSFCell celdaProvee = filaProvee.getCell(4);
            celdaProvee.setCellValue(txtNombre.getText());

            for (int i = 0; i < p.size(); i++) {
                XSSFRow fila = sheet.getRow(filas);
                if (fila == null) {
                    sheet.createRow(filas);
                }

                XSSFCell celda = fila.getCell(5);
                XSSFCell celda2 = fila.getCell(7);
                XSSFCell celda3 = fila.getCell(9);
                XSSFCell celda4 = fila.getCell(11);

                if (celda == null) {
                    celda = fila.createCell(5);
                }

                celda.setCellValue(String.valueOf(p.get(i).getReferencia()));

                if (celda2 == null) {
                    celda2 = fila.createCell(7);
                }

                celda2.setCellValue(String.valueOf(p.get(i).getNombre()));

                if (celda3 == null) {
                    celda3 = fila.createCell(9);
                }

                celda3.setCellValue(String.valueOf(p.get(i).getCantidad()));

                if (celda4 == null) {
                    celda4 = fila.createCell(11);
                }

                celda4.setCellValue(String.valueOf(p.get(i).getColor()));
                filas++;
            }

            File pedidosFolder = new File("src/Pedidos");
            if (!pedidosFolder.exists()) {
                pedidosFolder.mkdir();
            }
            file.close();
            FileOutputStream output = new FileOutputStream("./src/Pedidos/Pedido_innovaTech " + this.ultimo_pedido + ".xlsx");

            wb.write(output);
            output.close();
            sent = true;
        } catch (IOException e) {
            System.out.println(e);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("Tu archivo excel no ha podido ser descargado. Por favor intenta mas tarde ");
            alerta.setTitle("Lo sentimos...");
            alerta.showAndWait();
        }

        return sent;
    }

    public void Enviar(ArrayList info) {
        // Información del servidor de correo y cuenta
        String host = "smtp.gmail.com"; // Cambia esto al servidor de correo saliente que uses
        String usuario = info.get(0).toString();
        String contraseña = info.get(1).toString();

        // Configuración de las propiedades de JavaMail
        Properties propiedades = System.getProperties();
        propiedades.put("mail.smtp.host", host);
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.port", "587"); // Puerto para correo saliente (SMTP)

        // Autenticación del usuario
        Session sesion = Session.getInstance(propiedades,
        new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, contraseña);
            }
        });

        try {
            // Crear un objeto MimeMessage
            MimeMessage mensaje = new MimeMessage(sesion);

            // Configurar el remitente
            mensaje.setFrom(new InternetAddress(usuario));

            // Configurar el destinatario
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(info.get(0).toString()));

            // Configurar el asunto
            mensaje.setSubject("Copia del pedido " + ultimoPedido());

            // Crear un objeto MimeBodyPart para el archivo adjunto
            BodyPart adjunto = new MimeBodyPart();
            DataSource fuente = new FileDataSource("src/Pedidos/Pedido_innovaTech " + ultimoPedido() + ".xlsx"); // Ruta del archivo Excel
            adjunto.setDataHandler(new DataHandler(fuente));
            adjunto.setFileName("Pedido_innovaTech " + ultimoPedido() + ".xlsx"); // Nombre del archivo adjunto

            // Crear un objeto MimeMultipart para combinar el texto y el archivo adjunto
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(adjunto);

            // Configurar el contenido del mensaje
            mensaje.setContent(multipart);

            // Enviar el mensaje
            Transport.send(mensaje);
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText(null);
            alerta.setContentText("La copia de tu pedido esta en tu correo. Búscala en 'Enviados'. ");
            alerta.setTitle("Copia enviada");
            alerta.showAndWait();
        } catch (MessagingException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("Tu archivo excel no ha podido ser descargado. Por favor intenta mas tarde. ");
            alerta.setTitle("Lo sentimos... :c");
            alerta.showAndWait();
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }

    }

    public void GuardarDatos() {

        String mensaje = null;
        String referencia = "";
        String nombreProducto = "";
        String color = "";
        int cantidad = 0;

        if (!validarCampos(hboxes)) {
            if (!campoVacio(hboxes)) {
                if (!ValidarProveedor()) {
                    for (int i = 0; i < camposLlenos.size(); i++) {

                        HBox campoHbx = hboxes.get((int) camposLlenos.get(i));
                        TextField ref = (TextField) campoHbx.getChildren().get(0);
                        TextField name = (TextField) campoHbx.getChildren().get(1);
                        TextField cant = (TextField) campoHbx.getChildren().get(2);
                        TextField colr = (TextField) campoHbx.getChildren().get(3);

                        //arrayList para para insertar los detalles
                        ArrayList listaProductos = new ArrayList();
                        listaProductos.add(ref.getText().trim());
                        listaProductos.add(name.getText().trim());
                        listaProductos.add(Integer.parseInt(cant.getText().trim()));
                        listaProductos.add(colr.getText().trim());
                        if (!pedidoEnviado) {
                            InsertarPedido(cantidadProductos(camposLlenos));
                        }
                        detalleEnviado = false;
                        if (!detalleEnviado) {
                            insertarDetalles(listaProductos);
                        }

                        //arrayList para crear excel
                        p.add(new MdlPedidos(capitalize(ref.getText().trim()), capitalize(name.getText().trim()), Integer.parseInt(cant.getText().trim()), capitalize(colr.getText().trim())));

                        //Este es el mensaje del pedido que se esta realizando 
                        pedido += "Producto " + (i + 1) + ": Referencia " + ref.getText().trim() + ", Nombre del producto: " + name.getText().trim() + ", Cantidad: " + cant.getText().trim() + ", Color: " + colr.getText().trim() + " \n";
                    }

                    if (detalleEnviado && pedidoEnviado) {
                        enviado = true;
                    } else {
                        enviado = false;
                        Alert alerta = new Alert(Alert.AlertType.ERROR);
                        alerta.setContentText("No pudimos realizar el pedido");
                        alerta.setHeaderText(null);
                        alerta.setTitle("Lo sentimos no pudimos realizar el pedido, intenta mas tarde.");
                        alerta.showAndWait();

                    }

                    if (enviado) {
                        if (RbtnCorreo.isSelected()) {
                            rbtnActivado = true;
                            if (crearPDF(pedido)) {
                                if (pedidoCorreo(correoYcontra())) {
                                    limpiar(camposLlenos);
                                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                                    alerta.setTitle("Descargar Pedido en excel");
                                    alerta.setHeaderText(null);
                                    alerta.setContentText("Deseas descargar el pedido en formato excel?");
                                    Optional<ButtonType> action = alerta.showAndWait();

                                    //aqui se crea el excel
                                    if (action.get() == ButtonType.OK) {

                                        try {

                                            if (editarExcel(p)) {
                                                ExcelCorreo(correoYcontra()); 
                                            }

                                        } catch (Exception e) {
                                            System.out.println(e);
                                            Alert alertica = new Alert(Alert.AlertType.ERROR);
                                            alertica.setContentText("El pedido no se ha podido descargar en formato de excel.\n Por favor inténtalo mas tarde");
                                            alertica.setHeaderText(null);
                                            alertica.setTitle("Lo sentimos...");
                                            alertica.showAndWait();
                                        }

                                    }

                                }
                            }
                        }//Aqui cierra el if para ver si el correo fue activado

                        if (!rbtnActivado) {
                            Alert alerta = new Alert(Alert.AlertType.ERROR);
                            alerta.setContentText("Debes seleccionar si enviar el pedido al correo o al WhatsApp");
                            alerta.setHeaderText(null);
                            alerta.setTitle("Envió del pedido cancelado");
                            alerta.showAndWait();

                        }
                    }

                }

            }
        }

    }

    public String capitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public void limpiar(ArrayList campos) {
        for (int i = 0; i < campos.size(); i++) {
            HBox campoHbx = hboxes.get((int) campos.get(i));
            TextField ref = (TextField) campoHbx.getChildren().get(0);
            TextField name = (TextField) campoHbx.getChildren().get(1);
            TextField cant = (TextField) campoHbx.getChildren().get(2);
            TextField colr = (TextField) campoHbx.getChildren().get(3);
            ref.setText("");
            name.setText("");
            cant.setText("");
            colr.setText("");
        }
    }

    public int cantidadProductos(ArrayList campos) {
        int cantidad = 0;
        for (int i = 0; i < campos.size(); i++) {
            HBox campoHbx = hboxes.get((int) campos.get(i));
            TextField ref = (TextField) campoHbx.getChildren().get(0);
            TextField name = (TextField) campoHbx.getChildren().get(1);
            TextField cant = (TextField) campoHbx.getChildren().get(2);
            TextField colr = (TextField) campoHbx.getChildren().get(3);
            cantidad += (Integer.parseInt(cant.getText()));
        }

        return cantidad;
    }

    public int idProductos(ArrayList producto) {

        int id = 0;
        Conexion conectar = Conexion.getInstance();
        String sql = "Select id from products where name='" + producto.get(1) + "'";
        ResultSet rs = conectar.consultar(sql);
        try {
            while (rs.next()) {
                id = rs.getInt("id");
            }

            //la fecha esta en formato  AAAA-MM-DD ahora en timeStamp
            if (id == 0) {
                String sql2 = "INSERT INTO products (id, name, price, tax, code, stock  , param_mark, param_color, description,param_category, param_tags, discount, param_state, created_at)"//14
                        + " VALUES (NULL, '" + producto.get(1) + "' ," + null + " ," + null + ", '" + producto.get(0) + "', " + producto.get(2) + "," + null + ", " + null + ", NULL, NULL, NULL, NULL, 6, '" + timestamp + "' ) ";

                conectar.ejecutar(sql2);

                rs = conectar.consultar(sql);

                while (rs.next()) {
                    id = rs.getInt("id");
                }

            }

        } catch (SQLException ex) {
            System.out.println(ex);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Algo anda mal, por favor intenta mas tarde");
            alerta.setHeaderText(null);
            alerta.setTitle("Tenemos un error");
            alerta.showAndWait();
        }
        return id;
    }

    public void insertarDetalles(ArrayList products) {
        ultimo_pedido = ultimoPedido();
        Conexion conectar = Conexion.getInstance();
        //debo cambiar la fecha de hoy para que quede DD/MM/YYYY *Hecho*
        String sql = "Insert into order_details (qty,param_status, created_at, updated_at, order_id, product_id)"
                + "values(" + products.get(2) + ",10,'" + timestamp + "'," + null + "," + ultimo_pedido + ", " + idProductos(products) + ")";
        try {

            if (conectar.ejecutar(sql)) {
                enviado = true;
                detalleEnviado = true;

            }

        } catch (Exception e) {
            System.out.println(e);
            enviado = false;
            detalleEnviado = false;
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Algo anda mal, por favor intenta mas tarde");
            alerta.setHeaderText(null);
            alerta.setTitle("Tenemos un error");
            alerta.showAndWait();
        } finally {

            conectar.cerrarConexion();
        }

    }

    public void InsertarPedido(int cantidad) {
        Conexion conectar = Conexion.getInstance();
        String sql = "Insert into orders (id,total,param_status, created_at, provider_id) "
                + "values( 0," + cantidad + ",10,'" + timestamp + "'," + txtid.getText() + " )";
        try {
            if (conectar.ejecutar(sql)) {
                enviado = true;
                pedidoEnviado = true;

            }

        } catch (Exception e) {
            System.out.println(e);
            enviado = false;
            pedidoEnviado = false;
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Algo anda mal, por favor intenta mas tarde");
            alerta.setHeaderText(null);
            alerta.setTitle("Tenemos un error");
            alerta.showAndWait();

        } finally {
            conectar.cerrarConexion();
        }

    }

    public Boolean ValidarProveedor() {
        Boolean vacio = true;
        String errores = "";

        int nErrores = 0;
        if (txtCorreo.getText().isEmpty()) {
            errores += "El correo del proveedor, esta vacío  por favor selecciona un proveedor \n";
            nErrores++;
        }
        if (txtNombre.getText().isEmpty()) {
            errores += "El nombre del proveedor esta vacío, por favor selecciona un proveedor \n";
            nErrores++;
        }
        if (txtNumero.getText().isEmpty()) {
            errores += "El numero del proveedor esta vacío, por favor selecciona un proveedor \n";
            nErrores++;
        }
        if (nErrores == 0) {
            vacio = false;
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(errores);
            alerta.setHeaderText(null);
            alerta.setTitle("Campos de proveedores vacío");
            alerta.showAndWait();
        }

        return vacio;
    }

    public Boolean crearPDF(String mensaje) {

        Boolean pdfCreado = false;
        try {
            //donde se va a guardar el pdf

            String filePath = "./src/Pedidos/Pedido_innovaTech " + ultimoPedido() + "";
            File file = new File(filePath);

            file.getParentFile().mkdirs(); // Crea las carpetas si no existen
            file.createNewFile(); // Crea el archivo

            PdfWriter pdfWriter = new PdfWriter(filePath);

            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            Paragraph paragraph = new Paragraph(mensaje);

            document.add(paragraph);
            document.close();

            pdfWriter.close();
            pdfCreado = true;

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return pdfCreado;
    }

    //metodo add es para agregar las mas campos
    @FXML
    private void Add(ActionEvent event) {
        HBox hbox = new HBox();
        hbox.setSpacing(70);
        hbox.setAlignment(Pos.CENTER);
        int ancho = 150;
        int largo = 40;
        TextField refencia = new TextField();
        refencia.setPrefWidth(ancho);
        refencia.setPrefHeight(40);
        refencia.setPromptText("#");

        TextField NombreProducto = new TextField();
        NombreProducto.setPrefWidth(ancho);
        NombreProducto.setPrefHeight(largo);

        TextField Cantidad = new TextField();
        Cantidad.setPrefWidth(ancho);
        Cantidad.setPrefHeight(largo);

        TextField Color = new TextField();
        Color.setPrefWidth(ancho);
        Color.setPrefHeight(largo);

        hbox.getChildren().addAll(refencia, NombreProducto, Cantidad, Color);
        hboxes.add(hbox);
        contadorHboxes++;
        this.rootVbox.getChildren().add(hbox);
    }

    //metodo para inicializar los comboBox
    public void cbbInit() {

        Conexion conectar = Conexion.getInstance();

        int id;
        int contador = 0;

        String sql = "select * from providers where param_state = 5";

        ResultSet rs = conectar.consultar(sql);
        try {
            while (rs.next()) {
                String nombre;
                String apellido;
                String celular;
                String email;
                String ids;
                ids = rs.getString("id");
                nombre = rs.getString("legal_name");
                apellido = rs.getString("commercial_name");
                celular = rs.getString("phone");
                email = rs.getString("email");
                //guardo la matriz del nombre completo con el id de esa persona
                Nombreproveedores.put(ids, nombre + "-" + apellido);

                MdlProveedores modelo = new MdlProveedores(celular, nombre, apellido, email);
                obProve.add(nombre);
            }
            this.cbbProveedores.setItems(obProve);
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Algo anda mal, por favor intenta mas tarde");
            alerta.setHeaderText(null);
            alerta.setTitle("Tenemos un error");
            alerta.showAndWait();
        } finally {
            conectar.cerrarConexion();
        }

    }

    //metodo para poner los proveedores en el campo
    //Deberia ser getProveedor, pero ya quedo asi
    @FXML
    private void getCliente(ActionEvent event) {
        Conexion conectar = Conexion.getInstance();
        String sql = "Select providers.email, phone, providers.id, legal_name from providers "
                + "where  providers.legal_name = '" + cbbProveedores.getValue() + "'";
        ResultSet rs = conectar.consultar(sql);
        try {
            while (rs.next()) {
                this.txtCorreo.setText(rs.getString("email"));
                this.txtNombre.setText(rs.getString("legal_name"));
                this.txtNumero.setText(rs.getString("phone"));
                this.txtid.setText(String.valueOf(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Algo anda mal, por favor intenta mas tarde");
            alerta.setHeaderText(null);
            alerta.setTitle("Tenemos un error");
            alerta.showAndWait();
        } finally {
            conectar.cerrarConexion();
        }
    }

    public boolean validarCampos(ArrayList<HBox> hbox) {
        //este metodo devuelve TRUE si todos los campos estan vacios y devulve false si todos hay almenos un campo lleno

        Boolean vacio = false;
        int vacios = 0;
        int llenos = 0;
        for (int i = 0; i < hbox.size(); i++) {

            HBox box = hbox.get(i);
            for (Node hijo : box.getChildren()) {
                if (hijo instanceof TextField) {
                    if (((TextField) hijo).getText().isEmpty()) {
                        vacios++;
                    } else {
                        llenos++;

                    }
                }

            }

        }

        if (llenos <= 0) {

            vacio = true;
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Todo los campos están vacíos, debes llenar al menos un campo para hacer el pedido");
            alerta.setHeaderText(null);
            alerta.showAndWait();
        }

        return vacio;
    }

    public Boolean campoVacio(ArrayList<HBox> hbox) {
        //Este metodo devuelve False si no hay campos vacios y devuelve TRUE si hay campos vacios
        //setea los campos que estan llenos a el arrayList Camposllenos 

        Boolean vacio = true;
        String errores = "";
        String mensaje = "";
        int nErrores = 0;

        for (int i = 0; i < hbox.size(); i++) {
            HBox box = hboxes.get(i);

            TextField referencia = (TextField) box.getChildren().get(0);
            TextField nombre = (TextField) (box.getChildren().get(1));
            TextField cantidad = (TextField) (box.getChildren().get(2));
            TextField color = (TextField) (box.getChildren().get(3));

            if (referencia.getText().isEmpty()) {
                errores += "El campo 'Referencia en la fila " + (i + 1) + " está vacío \n";
                nErrores++;
            }
            if (nombre.getText().isEmpty()) {
                errores += "El campo 'Nombre de producto' en la fila " + (i + 1) + " está vacío \n";
                nErrores++;

            }
            if (cantidad.getText().isEmpty()) {
                errores += "El campo 'Cantidad' en la fila " + (i + 1) + " está vacío \n";
                nErrores++;
            } else if (!cantidad.getText().matches("^\\d+$")) {
                //.matches("^\\d+$") es para ver si contiene solo numeros
                errores += "En el campo  'cantidad' de la fila " + (i + 1) + "\n solo debes escribir números. \n";
                nErrores++;

            }
            if (color.getText().isEmpty()) {
                errores += "El campo 'Color' en la fila " + (i + 1) + " esta vacio\n";
                nErrores++;

            }
            if (nErrores == 0) {
                if (!camposLlenos.contains(i)) {
                    camposLlenos.add(i);
                }

                vacio = false;
            }
            if (nErrores > 0 && nErrores < 4) {
                mensaje += errores;
                errores = null;
                nErrores = 0;
                vacio = true;
            }

            if (nErrores == 4) {
                nErrores = 0;
                errores = "";
                vacio = true;
            }

        }//Aqui cierra el for loop

        if (!mensaje.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Tienes campos vacíos");
            alerta.setContentText(mensaje);
            alerta.showAndWait();

        } else {

            vacio = false;
        }
        return vacio;
    }

    public int ultimoPedido() {

        int id = 0;
        Conexion conectar = Conexion.getInstance();

        String sql = "Select id from orders ORDER by id ASC";
        ResultSet rs = conectar.consultar(sql);
        try {
            while (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Algo anda mal, por favor intenta mas tarde");
            alerta.setHeaderText(null);
            alerta.setTitle("Tenemos un error");
            alerta.showAndWait();
        }
				conectar.cerrarConexion();
        return id;
    }

    //metodo para sacar el correo y la contraseña del administrador, aun no esta terminado 
    public ArrayList correoYcontra() {
//        ArrayList<String> correoYContraseña = null;
        ArrayList array = new ArrayList();
        String correo;
        String contra;
        Conexion conectar = Conexion.getInstance();

        String sql = "Select users.email, token from users "
                + "where users.param_rol = '2' ";
        ResultSet rs = conectar.consultar(sql);
        try {

            while (rs.next()) {
                correo = rs.getString("email");
                contra = rs.getString("token");

                array.add(correo);
                array.add(contra);

//                correoYContraseña.add(correo);
//                correoYContraseña.add(contra);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CtrPedidos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en sacar la contraseña y pedidos");
        } finally {

            conectar.cerrarConexion();
        }

        return array;
    }

    //este metodo envia el pedido al correo
    public Boolean pedidoCorreo(ArrayList info) {
        Boolean envio = false;
        if (!info.isEmpty()) {
            try {
                String correo = info.get(0).toString();
                String password = info.get(1).toString();
                System.out.println(correo);
                System.out.println(password);
                String receptor = txtCorreo.getText();
                String titulo = "Pedido de Innova tech";
                //        String Contenido = "Este mensaje contiene un PDF el cual lleva todos los productos que les queremos solicitar";
                Session mSession;
                MimeMessage mCorreo;
                Properties mProperties = new Properties();
                //protocolo de trasnferencia de email
                //sgyjoeorqqytnlil
                mProperties.put("mail.smtp.host", "smtp.gmail.com");
                mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                mProperties.setProperty("mail.smtp.starttls.enable", "true");
                mProperties.setProperty("mail.smtp.port", "587");
                mProperties.setProperty("mail.smtp.users", correo);
                mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
                mProperties.setProperty("mail.smtp.auth", "true");
                mSession = Session.getDefaultInstance(mProperties);

                mCorreo = new MimeMessage(mSession);

                BodyPart texto = new MimeBodyPart();
                texto.setText("Tienes un nuevo pedido de Innova tech.");

                BodyPart adjunto = new MimeBodyPart();

                adjunto.setDataHandler(new DataHandler(new FileDataSource("./src/Pedidos/Pedido_innovaTech " + this.ultimo_pedido)));
                adjunto.setFileName("Pedido_innovaTech" + this.ultimo_pedido + ".pdf");
                MimeMultipart m = new MimeMultipart();
                m.addBodyPart(texto);
                m.addBodyPart(adjunto);

                mCorreo.setFrom(new InternetAddress(correo));

                mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
                mCorreo.setSubject(titulo);
                mCorreo.setContent(m);

                Transport t = mSession.getTransport("smtp");
                t.connect(correo, info.get(1).toString());
                t.sendMessage(mCorreo, mCorreo.getAllRecipients());
                t.close();
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("El pedido fue enviado.");
                alerta.setHeaderText(null);
                alerta.setContentText("El pedido fue enviado exitosamente.");
                alerta.showAndWait();
                envio = true;
            } catch (AddressException ex) {
                System.out.println(ex);
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText(null);
                alerta.setContentText("Lo sentimos no pudimos enviar su pedido, por favor verifica si estas conectado a una red Wifi");
                alerta.setHeaderText("El pedido no fue enviado");
                alerta.showAndWait();

            } catch (MessagingException ex) {
                System.out.println(ex);
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText(null);
                alerta.setContentText("Lo sentimos no pudimos enviar su pedido, por favor verifica si estas conectado a una red Wifi");
                alerta.setHeaderText("El pedido no fue enviado");
                alerta.showAndWait();

            }
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("Lo sentimos no pudimos enviar su pedido, Por favor intenta mas tarde");
            alerta.setHeaderText("El pedido no fue enviado");
            alerta.showAndWait();

        }

        return envio;
    }

    public Boolean ExcelCorreo(ArrayList info) {
        Boolean envio = false;

        try {

//Info es de donde va a obtener el correo y la contraseña del usuario administrador
            String correo = info.get(0).toString();
            String password = info.get(1).toString();
            String receptor = info.get(0).toString();
            String titulo = "Copia del pedido enviado";
//        String Contenido = "Este mensaje contiene un PDF el cual lleva todos los productos que les queremos solicitar";
            Session mSession;
            MimeMessage mCorreo;
            Properties mProperties = new Properties();
            //protocolo de trasnferencia de email
    
            mProperties.put("mail.smtp.host", "smtp.gmail.com");
            mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            mProperties.setProperty("mail.smtp.starttls.enable", "true");
            mProperties.setProperty("mail.smtp.port", "587");
            mProperties.setProperty("mail.smtp.users", correo);
            mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
            mProperties.setProperty("mail.smtp.auth", "true");
            mSession = Session.getDefaultInstance(mProperties);

            mCorreo = new MimeMessage(mSession);

            BodyPart texto = new MimeBodyPart();
            texto.setText("Esta es la copia del pedido enviado..");

            BodyPart adjunto = new MimeBodyPart();

            adjunto.setDataHandler(new DataHandler(new FileDataSource("./src/Pedidos/Pedido_innovaTech " + ultimoPedido()+".xlsx")));
            adjunto.setFileName("Pedido_innovaTech" + ultimoPedido() + ".xlsx");
            MimeMultipart m = new MimeMultipart();
            m.addBodyPart(texto);
            m.addBodyPart(adjunto);

            mCorreo.setFrom(new InternetAddress(correo));

            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
            mCorreo.setSubject(titulo);
            mCorreo.setContent(m);

            Transport t = mSession.getTransport("smtp");
            t.connect(correo, password);
            t.sendMessage(mCorreo, mCorreo.getAllRecipients());
            t.close();
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText(null);
            alerta.setContentText("Tu copia ha sido enviada correctamente a tu correo electrónico. ");
            alerta.setTitle("Pedido descargado con éxito!");
            alerta.showAndWait();
            envio = true;
        } catch (AddressException ex) {
            System.out.println(ex);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("Lo sentimos no pudimos enviar su copia, por favor verifica si estas conectado a una red Wifi");
            alerta.setTitle("El pedido no fue enviado");
            alerta.showAndWait();

        } catch (MessagingException ex) {
            System.out.println(ex);
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("Lo sentimos no pudimos enviar su copia, por favor verifica si estas conectado a una red Wifi");
            alerta.setTitle("El pedido no fue enviado");
            alerta.showAndWait();

        }

        return envio;

    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

}
