package conexionSQLServer;

import java.sql.*;
import javax.swing.JOptionPane;
import Models.paciente;

import javax.swing.table.DefaultTableModel;

import javax.swing.JTable;


public class inserPaciente {

    ResultSet rs;
    private conexionSQL con = new conexionSQL();
    private Connection cn = con.conexion();

    
    public void insertarPaciente(paciente insepa) {

        try {
            CallableStatement csta = cn.prepareCall("{call getpaciente (?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            csta.setString(1, insepa.getIdpaciente());
            csta.setString(2, insepa.getApellidos());
            csta.setString(3, insepa.getNombres());
            csta.setString(4, insepa.getFechanacimiento());
            csta.setString(5, insepa.getGenero());
            csta.setString(6, insepa.getTelefono());
            csta.setString(7, insepa.getOcupacion());
            csta.setString(8, insepa.getDepartamento());
            csta.setString(9, insepa.getProvincia());
            csta.setString(10, insepa.getDistrito());
            csta.setString(11, insepa.getDireccion());
            csta.setString(12, insepa.getCorreo());
            csta.setString(13, insepa.getFoto());

            rs = csta.executeQuery();
        
          
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
     
        
    }

    public void EliminarPaciente(paciente insepa) {

        try {
            CallableStatement csta = cn.prepareCall("{call deletePaciente (?)}");
            csta.setString(1, insepa.getIdpaciente());
            rs = csta.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void ActualizarPaciente(paciente insepa) {

        try {
            CallableStatement csta = cn.prepareCall("{call updatePaciente (?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            csta.setString(1, insepa.getIdpaciente());
            csta.setString(2, insepa.getApellidos());
            csta.setString(3, insepa.getNombres());
            csta.setString(4, insepa.getFechanacimiento());
            csta.setString(5, insepa.getGenero());
            csta.setString(6, insepa.getTelefono());
            csta.setString(7, insepa.getOcupacion());
            csta.setString(8, insepa.getDepartamento());
            csta.setString(9, insepa.getProvincia());
            csta.setString(10, insepa.getDistrito());
            csta.setString(11, insepa.getDireccion());
            csta.setString(12, insepa.getCorreo());
            csta.setString(13, insepa.getFoto());

            rs = csta.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
public String buscarPacientePago(String insepa) {
        String[] Registro = new String[14];

        try {
            CallableStatement csta = cn.prepareCall("{call getBuscarPacientePago (?)}");
            csta.setString(1, insepa);

            rs = csta.executeQuery();
            while (rs.next()) {

                Registro[13] = rs.getString("nombresall");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String resultado = Registro[13];
        System.out.println(resultado);
        return resultado;

    }
    public void BuscarPaciente(String insepa, JTable tabla ) {
            int contador = 0;
        DefaultTableModel modelo;

        String[] pacientes = {"DNI", "APELLIDOS", "NOMBRES", "FECHA DE NACIMIENTO", "GENERO", "TELEFONO", "OCUPACION", "DEPARTAMENTO", "PROVINCIA", "DISTRITO", "DIRECCION", "CORREO", "FOTO"};

        String[] Registro = new String[13];
        modelo = new DefaultTableModel(null, pacientes);

        try {
              CallableStatement csta = cn.prepareCall("{call BuscarListaPaciente (?)}");
            csta.setString(1, insepa);
            rs = csta.executeQuery();
            
                    while (rs.next()) {
                Registro[0] = rs.getString("idpaciente");
                Registro[1] = rs.getString("apellidos");
                Registro[2] = rs.getString("nombres");
                Registro[3] = rs.getString("fechanacimiento");
                Registro[4] = rs.getString("genero");
                Registro[5] = rs.getString("telefono");
                Registro[6] = rs.getString("ocupacion");
                Registro[7] = rs.getString("departamento");
                Registro[8] = rs.getString("provincia");
                Registro[9] = rs.getString("distrito");
                Registro[10] = rs.getString("direccion");
                Registro[11] = rs.getString("correo");
                Registro[12] = rs.getString("foto");
                modelo.addRow(Registro);
                contador ++;
            }
                                   
                tabla.setModel(modelo);                   
                
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }  
        if(contador==1){
          JOptionPane.showMessageDialog(null, "SE ENCONTRO EL PACIENTE CON EXITO"
                + " CON EXITO..!!");
        }else{ 
          JOptionPane.showMessageDialog(null, "PACIENTE"
                + " NO EXISTE..!!");
        
        }
            
            
    }

    public DefaultTableModel mostrarPaciente() {
        DefaultTableModel modelo;

        String[] pacientes = {"DNI", "APELLIDOS", "NOMBRES", "FECHA DE NACIMIENTO", "GENERO", "TELEFONO", "OCUPACION", "DEPARTAMENTO", "PROVINCIA", "DISTRITO", "DIRECCION", "CORREO", "FOTO","NOMBRE COMPLETO"};

        String[] Registro = new String[14];
        modelo = new DefaultTableModel(null, pacientes);

        try {
            CallableStatement csta = cn.prepareCall("mostrarPacientesTabla");
            rs = csta.executeQuery();

            while (rs.next()) {
                Registro[0] = rs.getString("idpaciente");
                Registro[1] = rs.getString("apellidos");
                Registro[2] = rs.getString("nombres");
                Registro[3] = rs.getString("fechanacimiento");
                Registro[4] = rs.getString("genero");
                Registro[5] = rs.getString("telefono");
                Registro[6] = rs.getString("ocupacion");
                Registro[7] = rs.getString("departamento");
                Registro[8] = rs.getString("provincia");
                Registro[9] = rs.getString("distrito");
                Registro[10] = rs.getString("direccion");
                Registro[11] = rs.getString("correo");
                Registro[12] = rs.getString("foto");
                Registro[13] = rs.getString("nombresall");
                modelo.addRow(Registro);
            }

            return modelo;

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }

    }
    
        public DefaultTableModel mostrarMedico() {
        DefaultTableModel modelo;

        String[] pacientes = {"DNI", "APELLIDOS", "NOMBRES", "FECHA DE NACIMIENTO", "GENERO", "TELEFONO", "DIRECCION", "CORREO", "FOTO"};

        String[] Registro = new String[9];
        modelo = new DefaultTableModel(null, pacientes);

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

    
        


   



}
