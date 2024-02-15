/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionSQLServer;

import Models.pago;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;

/**
 *
 * @author ERIK RUBIO
 */
public class logicaPago {

    ResultSet rs;
    private conexionSQL con = new conexionSQL();
    private Connection cn = con.conexion();

    public String mostrarSeriePago() {
        String Registro = "";
        try {

            CallableStatement csta = cn.prepareCall("mostrarPagoNumeroSerie");
            rs = csta.executeQuery();

            rs = csta.executeQuery();
            while (rs.next()) {
                Registro = rs.getString(1);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Registro;

    }

    public void insertarPago(pago insepago, JSONArray pago) {

        try {
            CallableStatement csta = cn.prepareCall("{call setPago (?,?,?,?,?,?,?,?,?,?,?,?)}");
            csta.setString(1, insepago.getRuc());
            csta.setString(2, insepago.getTipodocumento());
            csta.setString(3, insepago.getSerie());
            csta.setString(4, insepago.getNumeroserie());
            csta.setString(5, insepago.getFecha());
            csta.setString(6, insepago.getIdcita());
            csta.setString(7, insepago.getIdpaciente());
            csta.setString(8, insepago.getNombre());
            csta.setString(9, pago.toString()); // JSON AGREGADO 
            csta.setString(10, insepago.getSubtotal());
            csta.setString(11, insepago.getDescuento());
            csta.setString(12, insepago.getTotal());
            rs = csta.executeQuery();
            System.out.print(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public DefaultTableModel mostrarlistadoPagos() {
        DefaultTableModel modelo;

        String[] pago = {"IDPAGO", "RUC", "TIPO DOCUMENTO", "SERIE", "NUMERO SERIE", "FECHA", "IDCITA", "DNI", "PACIENTE"};
        
        String[] Registro = new String[10];
        modelo= new  DefaultTableModel(null, pago);
        

        try {
            CallableStatement csta = cn.prepareCall("mostrarListadoPago");
            rs = csta.executeQuery();

          while (rs.next()) {
               Registro[0] = rs.getString("idpago");
                Registro[1] = rs.getString("ruc");
                Registro[2] = rs.getString("tipodocumento");
                Registro[3] = rs.getString("serie");
                Registro[4] = rs.getString("numeroserie");
                Registro[5] = rs.getString("fecha");
                Registro[6] = rs.getString("idcita");
                Registro[7] = rs.getString("idpaciente");
                Registro[8] = rs.getString("nombre");
                modelo.addRow(Registro);
            }

            return modelo;

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }

    }
    
    
        public void BuscarDniPago(String insepa, JTable tabla ) {
             int contador = 0;
            DefaultTableModel modelo;

        String[] pago = {"IDPAGO", "RUC", "TIPO DOCUMENTO", "SERIE", "NUMERO SERIE", "FECHA", "IDCITA", "DNI", "PACIENTE"};
        
        String[] Registro = new String[10];
        modelo= new  DefaultTableModel(null, pago);

        try {
              CallableStatement csta = cn.prepareCall("{call mostrarPagoRealizado (?)}");
            csta.setString(1, insepa);
            rs = csta.executeQuery();
            
                while (rs.next()) {
               Registro[0] = rs.getString("idpago");
                Registro[1] = rs.getString("ruc");
                Registro[2] = rs.getString("tipodocumento");
                Registro[3] = rs.getString("serie");
                Registro[4] = rs.getString("numeroserie");
                Registro[5] = rs.getString("fecha");
                Registro[6] = rs.getString("idcita");
                Registro[7] = rs.getString("idpaciente");
                Registro[8] = rs.getString("nombre");
                modelo.addRow(Registro);
            
               
                contador ++;
            }
                                   
                tabla.setModel(modelo);                   
                
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }  
        if(contador==1){
          JOptionPane.showMessageDialog(null, "SE ENCONTRO EL PAGO CON EXITO"
                + " CON EXITO..!!");
                
        }else{ 
          JOptionPane.showMessageDialog(null, "PAGO"
                + " NO EXISTE..!!");
        
        }
            
            
    }
    
         public DefaultTableModel mostrarlistadoAbonos() {
        DefaultTableModel modelo;

        String[] pago = {"IDPAGO", "RUC", "TIPO DOCUMENTO", "SERIE", "NUMERO SERIE", "FECHA", "IDCITA", "DNI", "PACIENTE"};
        
        String[] Registro = new String[10];
        modelo= new  DefaultTableModel(null, pago);
        

        try {
            CallableStatement csta = cn.prepareCall("mostrarListado");
            rs = csta.executeQuery();

          while (rs.next()) {
               Registro[0] = rs.getString("idpago");
                Registro[1] = rs.getString("ruc");
                Registro[2] = rs.getString("tipodocumento");
                Registro[3] = rs.getString("serie");
                Registro[4] = rs.getString("numeroserie");
                Registro[5] = rs.getString("fecha");
                Registro[6] = rs.getString("idcita");
                Registro[7] = rs.getString("idpaciente");
                Registro[8] = rs.getString("nombre");
                modelo.addRow(Registro);
            }

            return modelo;

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }

    }
        
        
        
        
        
    public DefaultTableModel listadoPagoAbono(String inicio, String fin, JTable tabla) {

        DefaultTableModel modelo;

        String[] abono = {"IDPAGO", "RUC", "TIPO DOCUMENTO", "SERIE", "NUMERO SERIE", "FECHA", "IDCITA", "DNI", "PACIENTE","MONTO","DETALLE"};
        
        String[] Registro = new String[12];
        modelo= new  DefaultTableModel(null, abono);
        
        try {
            CallableStatement csta = cn.prepareCall("{call GetlistadoAbonos (?,?)}");
            csta.setString(1, inicio);
            csta.setString(2, fin);
            
            rs= csta.executeQuery();
            
            while (rs.next()) {
               Registro[0] = rs.getString("idpago");
                Registro[1] = rs.getString("ruc");
                Registro[2] = rs.getString("tipodocumento");
                Registro[3] = rs.getString("serie");
                Registro[4] = rs.getString("numeroserie");
                Registro[5] = rs.getString("fecha");
                Registro[6] = rs.getString("idcita");
                Registro[7] = rs.getString("idpaciente");
                Registro[8] = rs.getString("nombre");
                Registro[9] = rs.getString("monto");
                Registro[10] = rs.getString("detalle");
                modelo.addRow(Registro);
            }
            
           tabla.setModel(modelo);
            
            
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }

        return modelo;

    }

    public DefaultTableModel listadoPagocita(String inicio, String fin, JTable tabla) {
        DefaultTableModel modelo;

        String[] cita = {"IDPAGO", "RUC", "TIPO DOCUMENTO", "SERIE", "NUMERO SERIE", "FECHA", "IDCITA", "DNI", "PACIENTE","DESCRIPCION","MONTO"};

        String[] Registro = new String[12];
        modelo = new DefaultTableModel(null, cita);

        try {
            CallableStatement csta = cn.prepareCall("{call GetlistadoPagos (?,?)}");
            csta.setString(1, inicio);
            csta.setString(2, fin);

            rs = csta.executeQuery();

            while (rs.next()) {
                Registro[0] = rs.getString("idpago");
                Registro[1] = rs.getString("ruc");
                Registro[2] = rs.getString("tipodocumento");
                Registro[3] = rs.getString("serie");
                Registro[4] = rs.getString("numeroserie");
                Registro[5] = rs.getString("fecha");
                Registro[6] = rs.getString("idcita");
                Registro[7] = rs.getString("idpaciente");
                Registro[8] = rs.getString("nombre");
                Registro[9] = rs.getString("descripcion");
                Registro[10] = rs.getString("monto");
                //Registro[10] = rs.getString("detalle");

                modelo.addRow(Registro);
            }

            tabla.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);

        }
        return modelo;
    }

    
        public void BuscarDniAbono(String insepa, JTable tabla ) {
             int contador = 0;
            DefaultTableModel modelo;

        String[] abono = {"IDPAGO", "RUC", "TIPO DOCUMENTO", "SERIE", "NUMERO SERIE", "FECHA", "IDCITA", "DNI", "PACIENTE","MONTO","DETALLE"};
        
        String[] Registro = new String[12];
        modelo= new  DefaultTableModel(null, abono);

        try {
              CallableStatement csta = cn.prepareCall("{call mostrarAbonoRealizado (?)}");
            csta.setString(1, insepa);
            rs = csta.executeQuery();
            
                while (rs.next()) {
               Registro[0] = rs.getString("idpago");
                Registro[1] = rs.getString("ruc");
                Registro[2] = rs.getString("tipodocumento");
                Registro[3] = rs.getString("serie");
                Registro[4] = rs.getString("numeroserie");
                Registro[5] = rs.getString("fecha");
                Registro[6] = rs.getString("idcita");
                Registro[7] = rs.getString("idpaciente");
                Registro[8] = rs.getString("nombre");
                Registro[9] = rs.getString("monto");
                Registro[10] = rs.getString("detalle");
                modelo.addRow(Registro);
            
               
                contador ++;
            }
                                   
                tabla.setModel(modelo);                   
                
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }  
        if(contador==1){
          JOptionPane.showMessageDialog(null, "SE ENCONTRO EL ABONO CON EXITO"
                + " CON EXITO..!!");
                
        }else{ 
          JOptionPane.showMessageDialog(null, "ABONO"
                + " NO EXISTE..!!");
        
        }
    
        }
    
    
    
}
