package conexionSQLServer;

import Models.cita;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class logicaCita {

    ResultSet rs;
    private conexionSQL con = new conexionSQL();
    private Connection cn = con.conexion();
    
 

    
    public String buscarPacienteCita(String insepa) {
        String[] Registro = new String[14];

        try {
            CallableStatement csta = cn.prepareCall("{call getBuscarPacienteCita (?)}");
            csta.setString(1, insepa);

            rs = csta.executeQuery();
            while (rs.next()) {
//                Registro[0] = rs.getString("idpaciente");
//                Registro[1] = rs.getString("apellidos");
//                Registro[2] = rs.getString("nombres");
//                Registro[3] = rs.getString("fechanacimiento");
//                Registro[4] = rs.getString("genero");
//                Registro[5] = rs.getString("telefono");
//                Registro[6] = rs.getString("ocupacion");
//                Registro[7] = rs.getString("departamento");
//                Registro[8] = rs.getString("provincia");
//                Registro[9] = rs.getString("distrito");
//                Registro[10] = rs.getString("direccion");
//                Registro[11] = rs.getString("correo");
//                Registro[12] = rs.getString("foto");
                Registro[13] = rs.getString("nombresall");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String resultado = Registro[13];
        System.out.println(resultado);
        return resultado;

    }

    public void insertarCita(cita inseCita) {

        try {
            CallableStatement csta = cn.prepareCall("{call setCitaPaciente (?,?,?,?,?,?,?)}");
            csta.setString(1, inseCita.getIdpaciente());
            csta.setString(2, inseCita.getFechacita());
            csta.setString(3, inseCita.getHora());
            csta.setString(4, inseCita.getConsultorio());
            csta.setString(5, inseCita.getTipoconsulta());
            csta.setString(6, inseCita.getIdmedico());
            csta.setString(7, inseCita.getObservaciones());

            rs = csta.executeQuery();
           System.out.print(rs);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
        public DefaultTableModel mostrarCita() {
        DefaultTableModel modelo;

        String[] cita = {"IDCITA", "FECHA CITA", "HORA", "DNI", "NOMBRE PACIENTE","IDCONSULTA","CONSULTA","PRECIO", "DNI MEDICO", "NOMBRE MEDICO","IDCONSULTORIO", "CONSULTORIO","OBSERVACIONES","IDATENCION","ATENCION"};

        String[] Registro = new String[15];
        modelo = new DefaultTableModel(null, cita);

        try {
            CallableStatement csta = cn.prepareCall("mostrarCitaTabla");
            rs = csta.executeQuery();

            while (rs.next()) {
                Registro[0] = rs.getString("idcita");
                Registro[1] = rs.getString("fechacita");
                Registro[2] = rs.getString("hora");
                Registro[3] = rs.getString("idpaciente");                
                Registro[4] = rs.getString("nombresall");
                Registro[5] = rs.getString("idtipoconsulta");
                Registro[6] = rs.getString("consulta");
                Registro[7] = rs.getString("precio");
                Registro[8] = rs.getString("idmedico");
                Registro[9] = rs.getString("Nombre Medico");
                Registro[10] = rs.getString("idconsultorio");
                Registro[11] = rs.getString("nombre");
                Registro[12] = rs.getString("observaciones");
                Registro[13] = rs.getString("atendido");
                Registro[14] = rs.getString("descripcion");
                
                modelo.addRow(Registro);
            }

            return modelo;

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }

    }
    
        
       public DefaultTableModel mostrarCitaPrincipal(String inicio, String fin, String estadocita, JTable  tabla) {
        DefaultTableModel modelo;

        String[] cita = {"IDCITA", "FECHA CITA", "HORA", "DNI", "NOMBRE PACIENTE","IDCONSULTA","CONSULTA","PRECIO", "DNI MEDICO", "NOMBRE MEDICO","IDCONSULTORIO", "CONSULTORIO","ESTADO","OBSERVACIONES","IDATENCION","ATENCION"};

        String[] Registro = new String[16];
        modelo = new DefaultTableModel(null, cita);

        try {
            CallableStatement csta = cn.prepareCall("{call getcitaprincipal (?,?,?)}");
            csta.setString(1, inicio);
            csta.setString(2, fin);
            csta.setString(3, estadocita);
            
            rs = csta.executeQuery();

            while (rs.next()) {
                Registro[0] = rs.getString("idcita");
                Registro[1] = rs.getString("fechacita");
                Registro[2] = rs.getString("hora");
                Registro[3] = rs.getString("idpaciente");                
                Registro[4] = rs.getString("nombresall");
                Registro[5] = rs.getString("idtipoconsulta");
                Registro[6] = rs.getString("consulta");
                Registro[7] = rs.getString("precio");
                Registro[8] = rs.getString("idmedico");
                Registro[9] = rs.getString("Nombre Medico");
                Registro[10] = rs.getString("idconsultorio");
                Registro[11] = rs.getString("nombre");
                Registro[12]=  rs.getString("estado");
                Registro[13] = rs.getString("observaciones");
                Registro[14] = rs.getString("atendido");
                Registro[15] = rs.getString("descripcion");
                
                modelo.addRow(Registro);
            }

          tabla.setModel(modelo);   
          
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        
        }
        return modelo;
    }
        
               
       public DefaultTableModel mostrarListaCitas(String inicio, String fin, JTable  tabla) {
        DefaultTableModel modelo;

        String[] cita = {"IDCITA", "FECHA CITA", "HORA", "DNI", "NOMBRE PACIENTE","IDCONSULTA","CONSULTA","PRECIO", "DNI MEDICO", "NOMBRE MEDICO","IDCONSULTORIO", "CONSULTORIO","OBSERVACIONES","IDATENCION","ATENCION"};

        String[] Registro = new String[15];
        modelo = new DefaultTableModel(null, cita);

        try {
            CallableStatement csta = cn.prepareCall("{call getmostrarListaCitas (?,?)}");
            csta.setString(1, inicio);
            csta.setString(2, fin);
         
            
            rs = csta.executeQuery();

            while (rs.next()) {
                Registro[0] = rs.getString("idcita");
                Registro[1] = rs.getString("fechacita");
                Registro[2] = rs.getString("hora");
                Registro[3] = rs.getString("idpaciente");                
                Registro[4] = rs.getString("nombresall");
                Registro[5] = rs.getString("idtipoconsulta");
                Registro[6] = rs.getString("consulta");
                Registro[7] = rs.getString("precio");
                Registro[8] = rs.getString("idmedico");
                Registro[9] = rs.getString("Nombre Medico");
                Registro[10] = rs.getString("idconsultorio");
                Registro[11] = rs.getString("nombre");
                Registro[12] = rs.getString("observaciones");
                Registro[13] = rs.getString("atendido");
                Registro[14] = rs.getString("descripcion");
                
                modelo.addRow(Registro);
            }

          tabla.setModel(modelo);   
          
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        
        }
        return modelo;
    }
       
       
       
       
       
       
        public void deletecita (String inseCita ){
            
                 try {
            CallableStatement csta = cn.prepareCall("{call deleteCita (?)}");
            csta.setString(1, inseCita);
            rs = csta.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        }

 
        public void ActualizarCita (cita inseCita){
     
             try {
            CallableStatement csta = cn.prepareCall("{call updateCita (?,?,?,?,?,?,?,?)}");
           
            csta.setString(1, inseCita.getIdcita());
            csta.setString(2, inseCita.getIdpaciente());
            csta.setString(3, inseCita.getFechacita());
            csta.setString(4, inseCita.getHora());
            csta.setString(5, inseCita.getConsultorio());
            csta.setString(6, inseCita.getTipoconsulta());
            csta.setString(7, inseCita.getIdmedico());
            csta.setString(8, inseCita.getObservaciones());
        

            rs = csta.executeQuery();
           
                  

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
 
 }
        
       
        public void Buscarcita(String insepa,String fechaini, String fechafin, JTable tabla ) {
            int contador = 0;
              DefaultTableModel modelo;

        String[] cita = 
        {"IDCITA", "FECHA CITA", "HORA", "PACIENTE", "NOMBRE PACIENTE","IDCONSULTA","CONSULTA",
        "PRECIO", "DNI MEDICO", "NOMBRE MEDICO","IDCONSULTORIO", "CONSULTORIO","OBSERVACIONES","IDATENCION","ATENCION"};

        String[] Registro = new String[15];
        modelo = new DefaultTableModel(null, cita);

        try {
            CallableStatement csta = cn.prepareCall("{call BuscarListaCita (?,?,?)}");
            csta.setString(1, insepa);
            csta.setString(2,fechaini);
            csta.setString(3,fechafin);
            rs = csta.executeQuery();
            
            while (rs.next()) {
                Registro[0] = rs.getString("idcita");
                Registro[1] = rs.getString("fechacita");
                Registro[2] = rs.getString("hora");
                Registro[3] = rs.getString("idpaciente");                
                Registro[4] = rs.getString("nombresall");
                Registro[5] = rs.getString("idtipoconsulta");
                Registro[6] = rs.getString("consulta");
                Registro[7] = rs.getString("precio");
                Registro[8] = rs.getString("idmedico");
                Registro[9] = rs.getString("Nombre Medico");
                Registro[10] = rs.getString("idconsultorio");
                Registro[11] = rs.getString("nombre");
                Registro[12] = rs.getString("observaciones");
                Registro[13] = rs.getString("atendido");
                Registro[14] = rs.getString("descripcion");
                contador++;
                modelo.addRow(Registro);
                
            }                             
                tabla.setModel(modelo);                   
                
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }  
        if(contador>=1){
          JOptionPane.showMessageDialog(null, "SE ENCONTRO LA CITA CON EXITO"
                + " CON EXITO..!!");
        }else{ 
              // imprime lo que esta enviando como parametro
            String dni = insepa;
            String resfechaini= fechaini;
            String resfechafin = fechafin;
            
            System.out.println(dni+" "+fechaini+" "+fechafin);
            
          JOptionPane.showMessageDialog(null, "CITA"
                + " NO EXISTE..!!");
        
        }
            
            
    }

 
    
    
    

}
