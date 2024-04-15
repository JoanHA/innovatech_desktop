/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dragnell
 */
public class ctrFechas {
    public String fechaHoy() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(date);
    }
    
    public String horaHoy() {
        Date date = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        return hourFormat.format(date);
    }

    public Date fecha() {
        Date date =null;
         SimpleDateFormat formato= new SimpleDateFormat("yyyy/MM/dd");
        try {
            date = formato.parse(formato.format(new Date()));
        } catch (ParseException ex) {
            Logger.getLogger(ctrFechas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

    public boolean validaFecha(Date fecha) {
        Date date = new Date();
        String e, h, eDia, eMes, eAnio, hDia, hMes, hAnio;
        boolean sw = false;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        e = dateFormat.format(fecha);
        h = dateFormat.format(date);
        // busco la primera ocurrencia de '/'
        int pos1 = e.indexOf('/');
        // busco la ultima ocurrencia de '/'
        int pos2 = e.lastIndexOf('/');

        // proceso el dia
        eDia = e.substring(0, pos1);
        hDia = h.substring(0, pos1);
        // proceso el mes
        eMes = e.substring(pos1 + 1, pos2);
        hMes = h.substring(pos1 + 1, pos2);
        // proceso el anio
        eAnio = e.substring(pos2 + 1);
        hAnio = h.substring(pos2 + 1);

        if (Integer.parseInt(eAnio) > (Integer.parseInt(hAnio) - 18)) {
            sw= false;
        }else{
            sw= true;
        }
        return sw;
    }
    
    public boolean validaFechaActual(Date fecha) {
        Date date = new Date();
        String e, h, eDia, eMes, eAnio, hDia, hMes, hAnio;
        boolean sw = false;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        e = dateFormat.format(fecha);
        h = dateFormat.format(date);
        // busco la primera ocurrencia de '/'
        int pos1 = e.indexOf('/');
        // busco la ultima ocurrencia de '/'
        int pos2 = e.lastIndexOf('/');

        // proceso el dia
        eDia = e.substring(0, pos1);
        hDia = h.substring(0, pos1);
        // proceso el mes
        eMes = e.substring(pos1 + 1, pos2);
        hMes = h.substring(pos1 + 1, pos2);
        // proceso el anio
        eAnio = e.substring(pos2 + 1);
        hAnio = h.substring(pos2 + 1);

        if (Integer.parseInt(eAnio) == Integer.parseInt(hAnio) && Integer.parseInt(eMes) == Integer.parseInt(hMes) && Integer.parseInt(eDia) >= Integer.parseInt(hDia)) {
            sw= true;
        }else{
            sw= false;
        }
        return sw;
    }
    public boolean validaFechaActual(Date actual,Date futura) {        
        String a, f, aDia, aMes, aAnio, fDia, fMes, fAnio;
        boolean sw = false;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        a = dateFormat.format(actual);
        f = dateFormat.format(futura);
        // busco la primera ocurrencia de '/'
        int pos1 = a.indexOf('/');
        // busco la ultima ocurrencia de '/'
        int pos2 = a.lastIndexOf('/');

        // proceso el dia
        aDia = a.substring(0, pos1);
        fDia = f.substring(0, pos1);
        // proceso el mes
        aMes = a.substring(pos1 + 1, pos2);
        fMes = f.substring(pos1 + 1, pos2);
        // proceso el anio
        aAnio = a.substring(pos2 + 1);
        fAnio = f.substring(pos2 + 1);

        if (Integer.parseInt(aAnio) == Integer.parseInt(fAnio) && Integer.parseInt(aMes) <= Integer.parseInt(fMes) && Integer.parseInt(aDia) <= Integer.parseInt(fDia)) {
            sw= true;            
        }else{
            sw= false;
        }
        return sw;
    }
    
    public void validaLetras(){
//        int k = (int) evt.getKeyChar();
//        if (k >= 33 && k < 65 || k >= 91 && k < 97 || k >= 123 && k < 128) {
//            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
//            JOptionPane.showMessageDialog(null, "No puede ingresar Caracteres especiales\nSólo letras!!!", "Ventana Error Datos", JOptionPane.ERROR_MESSAGE);
//        }
    }
    
    public String devuelveFecha(Date fecha){
        String formato="yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        
        return sdf.format(fecha);
    }
    
    public Date devuelveFecha(String fecha){
        SimpleDateFormat formato= new SimpleDateFormat("yyyy/MM/dd");
        Date fechaParseada=null;
        try {
            fechaParseada = formato.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(ctrFechas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaParseada;
    }
    
    public int comparaFechas(Date fecha){
        int sw;
        String formato="yyyy-LL-dd";
        Date fechasistema =new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formato);     
        sw=sdf.format(fecha).compareTo(sdf.format(fechasistema));
        return sw;
    }
    
    public boolean fechaVacia(Date fecha){
        boolean sw=false; 
        String formato="yyyy-LL-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(formato); 
        try {
             sdf.format(fecha);
        } catch (NullPointerException e) {
            sw=true;
        }       
        return sw;
    }    
}
