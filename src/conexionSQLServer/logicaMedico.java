
package conexionSQLServer;

import Models.medico;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class logicaMedico {
    ResultSet rs;
    private conexionSQL con = new conexionSQL();
    private Connection cn = con.conexion();
    
            public void insertarMedico(medico insemedico) {

        try {
            CallableStatement csta = cn.prepareCall("{call setMedico (?,?,?,?,?,?,?,?,?,?)}");
            csta.setString(1, insemedico.getIdmedico());
            csta.setString(2, insemedico.getApellidos());
            csta.setString(3, insemedico.getNombres());
            csta.setString(4, insemedico.getFechanacimiento());
            csta.setString(5, insemedico.getGenero());
            csta.setString(6, insemedico.getTelefono());
            csta.setString(7,insemedico.getDireccion());
            csta.setString(8,insemedico.getCorreo());
            csta.setString(9,insemedico.getFoto());
            csta.setString(10,insemedico.getDescripcion()); 
    

            rs = csta.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void EliminarMedico(medico insemedico) {

        try {
            CallableStatement csta = cn.prepareCall("{call deleteMedico (?)}");
            csta.setString(1, insemedico.getIdmedico());
            rs = csta.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void ActualizarMedico(medico insemedico) {

        try {
            CallableStatement csta = cn.prepareCall("{call updateMedico (?,?,?,?,?,?,?,?,?)}");
            csta.setString(1, insemedico.getIdmedico());
            csta.setString(2, insemedico.getApellidos());
            csta.setString(3, insemedico.getNombres());
            csta.setString(4, insemedico.getFechanacimiento());
            csta.setString(5, insemedico.getGenero());
            csta.setString(6, insemedico.getTelefono());
            csta.setString(7, insemedico.getDireccion());
            csta.setString(8, insemedico.getCorreo());
            csta.setString(9, insemedico.getFoto());

            rs = csta.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void BuscarMedico(String insemedico, JTable tabla ) {
            int contador = 0;
        DefaultTableModel modelo;

        String[] medico = {"DNI", "APELLIDOS", "NOMBRES", "FECHA DE NACIMIENTO", "GENERO", "TELEFONO", "DIRECCION", "CORREO", "FOTO"};

        String[] Registro = new String[9];
        modelo = new DefaultTableModel(null, medico);

        try {
              CallableStatement csta = cn.prepareCall("{call BuscarListaMedico (?)}");
            csta.setString(1, insemedico);
            rs = csta.executeQuery();
            
                    while (rs.next()) {
                Registro[0] = rs.getString("idMedico");
                Registro[1] = rs.getString("apellidos");
                Registro[2] = rs.getString("nombres");
                Registro[3] = rs.getString("fechanacimiento");
                Registro[4] = rs.getString("genero");
                Registro[5] = rs.getString("telefono");        
                Registro[6] = rs.getString("direccion");
                Registro[7] = rs.getString("correo");
                Registro[8] = rs.getString("foto");
                modelo.addRow(Registro);
                contador ++;
            }
                                   
                tabla.setModel(modelo);                   
                
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }  
        if(contador==1){
          JOptionPane.showMessageDialog(null, "SE ENCONTRO EL MEDICO CON EXITO"
                + " CON EXITO..!!");
        }else{ 
          JOptionPane.showMessageDialog(null, "MEDICO"
                + " NO EXISTE..!!");
        
        }
            
            
    }

    public DefaultTableModel mostrarMedico() {
        DefaultTableModel modelo;

        String[] medico = {"DNI", "APELLIDOS", "NOMBRES", "FECHA DE NACIMIENTO", "GENERO", "TELEFONO", "DIRECCION", "CORREO", "FOTO"};

        String[] Registro = new String[9];
        modelo = new DefaultTableModel(null, medico);

        try {
            CallableStatement csta = cn.prepareCall("mostrarMedicoTabla");
            rs = csta.executeQuery();

            while (rs.next()) {
                Registro[0] = rs.getString("idmedico");
                Registro[1] = rs.getString("apellidos");
                Registro[2] = rs.getString("nombres");
                Registro[3] = rs.getString("fechanacimiento");
                Registro[4] = rs.getString("genero");
                Registro[5] = rs.getString("telefono");
                Registro[6] = rs.getString("direccion");
                Registro[7] = rs.getString("correo");
                Registro[8] = rs.getString("foto");
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
            CallableStatement csta = cn.prepareCall("mostrarMedicoComboBox");
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
