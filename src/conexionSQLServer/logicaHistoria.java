package conexionSQLServer;

import Models.historia;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class logicaHistoria {

    ResultSet rs;
    private conexionSQL con = new conexionSQL();
    private Connection cn = con.conexion();

    public String[] buscarPacienteHistoria(String insepa) {
        String[] Registro = new String[20];

        try {
            CallableStatement csta = cn.prepareCall("{call getBuscarPacienteCita (?)}");
            csta.setString(1, insepa);

            rs = csta.executeQuery();
            while (rs.next()) {

                Registro[0] = rs.getString("idpaciente");
                Registro[13] = rs.getString("nombresall");
                Registro[3] = rs.getString("fechanacimiento");
                Registro[4] = rs.getString("genero");
                Registro[5] = rs.getString("telefono");
                Registro[6] = rs.getString("ocupacion");
                Registro[7] = rs.getString("departamento");
                Registro[8] = rs.getString("provincia");
                Registro[9] = rs.getString("distrito");
                Registro[10] = rs.getString("direccion");
                Registro[11] = rs.getString("correo");
                Registro[18] = rs.getString("identificador");

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return Registro;

    }
    
    
      public String[] buscarHistoria(String insepa) {
        String[] Registro = new String[9];

        try {
            CallableStatement csta = cn.prepareCall("{call mostrarHistoriaTabla (?)}");
            csta.setString(1, insepa);

            rs = csta.executeQuery();
             while (rs.next()) {
                Registro[0] = rs.getString("idhistoria");
                Registro[1] = rs.getString("idhistoriapaciente");
                Registro[2] = rs.getString("idmedico");
                Registro[3] = rs.getString("nombre");                
                Registro[4] = rs.getString("diagnostico");
                Registro[5] = rs.getString("fechatratamiento");
                Registro[6] = rs.getString("tratamiento");
                Registro[7] = rs.getString("costo");
                Registro[8] = rs.getString("saldo");

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Registro;

    }
    
     public void MostrarHistoria(String insepa, JTable tabla ) {
        int contador = 0;
        DefaultTableModel modelo;

        String[] historia = {"IDHISTORIAPACIENTE", "FECHA HISTORIA", "NOMBRE MEDICO","PADECE ENFERMEDAD","RESPUESTA","ALERGICO",
            "RESPUESTA","HEMORRAGIA","RESPUESTA",
            "DIAGNOSTICO","FECHA TRATAMIENTO","TRATAMIENTO", "COSTO", "SALDO"};

        String[] Registro = new String[15];
        modelo = new DefaultTableModel(null, historia);

        try {
            CallableStatement csta = cn.prepareCall("{call mostrarHistoriaTabla (?)}");
            csta.setString(1,insepa);
            rs = csta.executeQuery();

            while (rs.next()) {
                Registro[0] = rs.getString("idhistoriapaciente");
                Registro[1] = rs.getString("fechahistoria");
                Registro[2] = rs.getString("idmedico");
                Registro[3] = rs.getString("padeceenfermedad");                
                Registro[4] = rs.getString("respuestapadece");
                Registro[5] = rs.getString("alergico");
                Registro[6] = rs.getString("respuestaalergico");
                Registro[7] = rs.getString("hemorragia");
                Registro[8] = rs.getString("respuestahemorragia");
                Registro[9] = rs.getString("diagnostico");
                Registro[10] = rs.getString("fechatratamiento");
                Registro[11] = rs.getString("tratamiento");
                Registro[12] = rs.getString("costo");
                 Registro[13] = rs.getString("saldo");
                modelo.addRow(Registro);
//                contador ++;
            }
                                   
                tabla.setModel(modelo);                   
                
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }  
//        if(contador==1){
//          JOptionPane.showMessageDialog(null, "NO SE ENCONTRO LA HISTORIA"
//                + "..!!");
//        }else{ 
          JOptionPane.showMessageDialog(null, "SE ENCONTRO LA HISTORIA"
                + " CON EXITO..!!");
        
//            }
            
            
    }

    public void insertarHistoria(historia insehistoria) {
        System.out.print("EXEC setHistoriaPaciente "+insehistoria.getIdhistoriapaciente()+","+insehistoria.getFechahistoria()+","
            +insehistoria.getIdmedico()+","+insehistoria.getPadeceenfermedad() +","+insehistoria.getRespuestapadece()+","+
            insehistoria.getAlergico()+","+insehistoria.getRespuestaalergico()+","+insehistoria.getHemorragia()+","+insehistoria.getRespuestahemorragia()+","+
            insehistoria.getDiagnostico()+","+insehistoria.getFechatratamiento()+","+insehistoria.getTratamiento()+","+insehistoria.getCosto()+","+
              insehistoria.getSaldo()  );
        try {
            CallableStatement csta = cn.prepareCall("{call setHistoriaPaciente (?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            
            csta.setString(1, insehistoria.getIdhistoriapaciente());
            csta.setString(2, insehistoria.getFechahistoria());
            csta.setString(3, insehistoria.getIdmedico());
            csta.setString(4, insehistoria.getPadeceenfermedad());
            csta.setString(5, insehistoria.getRespuestapadece());
            csta.setString(6, insehistoria.getAlergico());
            csta.setString(7, insehistoria.getRespuestaalergico());
            csta.setString(8, insehistoria.getHemorragia());
            csta.setString(9, insehistoria.getRespuestahemorragia());
            csta.setString(10, insehistoria.getDiagnostico());
            csta.setString(11, insehistoria.getFechatratamiento());
            csta.setString(12, insehistoria.getTratamiento());
            csta.setString(13, insehistoria.getCosto());
            csta.setString(14, insehistoria.getSaldo());
              
            rs = csta.executeQuery();
           //System.out.print(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
              
    }

    public void mostrarhistoriainsertada(String insepa){
        int contador = 0;
        DefaultTableModel modelo;

        String[] historia = {"IDHISTORIAPACIENTE", "FECHA HISTORIA", "DNI MEDICO", "NOMBRE MEDICO","DIAGNOSTICO","FECHA TRATAMIENTO","TRATAMIENTO", "COSTO", "SALDO"};

        String[] Registro = new String[10];
        modelo = new DefaultTableModel(null, historia);
        
        try {
            CallableStatement csta = cn.prepareCall("{call mostrarHistoriaTabla (?)}");
            csta.setString(1,insepa);
            rs = csta.executeQuery();

            while (rs.next()) {
                Registro[0] = rs.getString("idhistoriapaciente");
                Registro[1] = rs.getString("fechahistoria");
                Registro[2] = rs.getString("idmedico");
                Registro[3] = rs.getString("nombre");                
                Registro[4] = rs.getString("diagnostico");
                Registro[5] = rs.getString("fechatratamiento");
                Registro[6] = rs.getString("tratamiento");
                Registro[7] = rs.getString("costo");
                Registro[8] = rs.getString("saldo");
                contador++;
                modelo.addRow(Registro);
            }
                                   
                            
                
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }  
   
      if(contador>=1){
          JOptionPane.showMessageDialog(null, "SE REGISTRO LA HISTORIA CON EXITO"
                + " CON EXITO..!!");
        }else{ 
          JOptionPane.showMessageDialog(null, "HISTORIA"
                + " NO EXISTE..!!");
        
        }
    
    }
    
    
    
}
