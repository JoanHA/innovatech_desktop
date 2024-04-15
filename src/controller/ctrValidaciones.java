package controller;
/**
 *
 * @author Dragnell
 */
public class ctrValidaciones {
    boolean validarString(String cadena) {
        String letras = "abcdefghijklmnñopqrstuvwxyz ";
        boolean sw = true;
        for (int i = 0; i < cadena.length(); i++) {
            if (!letras.contains(cadena.substring(i, i + 1))) {
                sw = false;
            }
        }
        return sw;
    }

    boolean validarCelular(String celular) {
        long convertirCelular;
        try {
            convertirCelular = Long.parseLong(celular);
            return celular.length() == 10 && convertirCelular > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    boolean validarIdentificacion(String cedula) {
        int almacenarCedula;
        try {
            almacenarCedula = Integer.parseInt(cedula);
            if (almacenarCedula > 0 && cedula.length() <= 10 && cedula.length() > 7) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    boolean validarEmail(String cadena) {
        if (cadena.contains("@")) {
            if ( cadena.contains(".co") || cadena.contains(".com")) {
                int index = cadena.indexOf("@");
                String usuario = cadena.substring(0, index);
                String prefijo = cadena.substring(index+1, cadena.length());
            return usuario.length() > 0 && prefijo.equals("gmail.com");
            }  
        } else {
            return false;
        }
        return false;
    }
    boolean validarToken(String cadena){
        return cadena.length() == 16;
    }
}
