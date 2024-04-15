/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FTP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Dragnell
 */
public class ConexionFTP {

	String server = "154.49.247.213";
	int port = 21;
	String user = "u759008589";
	String password = "Adsi207-";

	public String subirImg(String carpetaDestino, String rutaImagen) {
		String rutaRemota = "/img/" + carpetaDestino + "/";
		String nombreImg = "0";
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(this.server, this.port);
			ftpClient.login(this.user, this.password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			String[] directorios = rutaRemota.split("/");
			for (String directorio : directorios) {
				if (!directorio.isEmpty()) {
					if (!ftpClient.changeWorkingDirectory(directorio)) {
						boolean directoryCreated = ftpClient.makeDirectory(directorio);
						if (directoryCreated) {
							System.out.println("Creada la carpeta remota: " + directorio);
							ftpClient.changeWorkingDirectory(directorio);
						} else {
							System.out.println("No se pudo crear la carpeta remota: " + directorio);
							return nombreImg; // Retorna "0" si no se pudo crear la carpeta remota
						}
					}
				}
			}
			String extension = rutaImagen.substring(rutaImagen.lastIndexOf(".") + 1);
			File localFile = new File(rutaImagen);
			String remoteFileName = UUID.randomUUID().toString()+"."+extension;
			nombreImg = remoteFileName;
			FileInputStream inputStream = new FileInputStream(localFile);

			boolean done = ftpClient.storeFile("/public_html" + rutaRemota + remoteFileName, inputStream);
			inputStream.close();
			if (done) {
				System.out.println(rutaImagen + " subida exitosamente.");
			} else {
				System.out.println("imagen no subida");
			}
			ftpClient.logout();
			ftpClient.disconnect();
		} catch (IOException e) {
			System.out.println("Error al subir la imagen: " + e);
		}
		return nombreImg;
	}

	public boolean eliminarImg(String carpeta, String nombreImg) {

		String rutaImg = "/public_html/img/" + carpeta + "/" + nombreImg;
		FTPClient ftpClient = new FTPClient();
		boolean sw = false;
		try {
			ftpClient.connect(this.server, this.port);
			ftpClient.login(this.user, this.password);
			ftpClient.enterLocalPassiveMode();

			boolean success = ftpClient.deleteFile(rutaImg);
			if (success) {
				sw = true;
				System.out.println("Eliminado");
			} else {
				System.out.println("No se pudo eliminar la imagen: " + rutaImg);
				sw = false;
			}
			ftpClient.logout();
			ftpClient.disconnect();
		} catch (IOException e) {
			System.out.println("Error al eliminar la imagen: " + e);
			sw = false;
		}
		return sw;
	}

	public void actualizarImg(String carpeta, String rutaImagen, String nombreImagen) {

		String directorio = "/public_html/img/" + carpeta + "/";
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(this.server, this.port);
			ftpClient.login(this.user, this.password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			File localFile = new File(rutaImagen);
			String remoteFileName = nombreImagen;
			FileInputStream inputStream = new FileInputStream(localFile);

			boolean done = ftpClient.storeFile(directorio + remoteFileName, inputStream);
			inputStream.close();
			if (done) {
				System.out.println(nombreImagen + ": Modificada exitosamente.");
			} else {
				System.out.println("imagen no Modificada");
			}

			ftpClient.logout();
			ftpClient.disconnect();
		} catch (IOException e) {
			System.out.println("Error al subir la imagen: " + e);
		}
	}
}
