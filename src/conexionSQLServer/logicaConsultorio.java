package conexionSQLServer;

import Models.consultorio;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class logicaConsultorio {

    ResultSet rs;
    private conexionSQL con = new conexionSQL();
    private Connection cn = con.conexion();

    public void insertarConsultorio(consultorio inseconsultorio) {

        try {
            CallableStatement csta = cn.prepareCall("{call setConsultorio (?,?)}");
//            csta.setString(1, inseconsultorio.getIdconsultorio());
            csta.setString(1, inseconsultorio.getNombre());
            csta.setString(2, inseconsultorio.getNumeroconsultorio());

            rs = csta.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public DefaultTableModel mostrarConsultorio() {
        DefaultTableModel modelo;

        String[] consultorio = {"ID CONSULTORIO", "NOMBRE", "NUMERO CONSULTORIO"};

        String[] Registro = new String[3];
        modelo = new DefaultTableModel(null, consultorio);

        try {
            CallableStatement csta = cn.prepareCall("mostrarConsultorioTabla");
            rs = csta.executeQuery();

            while (rs.next()) {
                Registro[0] = rs.getString("idconsultorio");
                Registro[1] = rs.getString("nombre");
                Registro[2] = rs.getString("numeroconsultorio");

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
            CallableStatement csta = cn.prepareCall("mostrarConsultorioComboBox");
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
