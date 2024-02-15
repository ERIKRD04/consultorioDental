/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionSQLServer;

import Models.especialidad;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class logicaEspecialidad {

    ResultSet rs;
    private conexionSQL con = new conexionSQL();
    private Connection cn = con.conexion();

    public void insertarEspecialidad(especialidad inseEspecialidad) {

        try {
            CallableStatement csta = cn.prepareCall("{call setEspecialidad (?,?)}");
            csta.setString(1, inseEspecialidad.getNombre());
            csta.setString(2, inseEspecialidad.getObservacion());

            rs = csta.executeQuery();
  
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public DefaultTableModel mostrarEspecialidad() {
        DefaultTableModel modelo;

        String[] especialidad = {"ID ESPECIALIDAD", "NOMBRE", "OBSERVACION"};

        String[] Registro = new String[3];
        modelo = new DefaultTableModel(null, especialidad);

        try {
            CallableStatement csta = cn.prepareCall("mostrarEspecialidadTabla");
            rs = csta.executeQuery();

            while (rs.next()) {
                Registro[0] = rs.getString("idespecialidad");
                Registro[1] = rs.getString("nombre");
                Registro[2] = rs.getString("observacion");

                modelo.addRow(Registro);
            }

            return modelo;

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }

    }

    public DefaultComboBoxModel mostrarComboBox() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();

        try {
            CallableStatement csta = cn.prepareCall("mostrarEspecialidadComboBox");
            rs = csta.executeQuery();
            while (rs.next()) {
                modelo.addElement(rs.getString(1));
            }          

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);  
          
        }
           return modelo;
    }

  
}
