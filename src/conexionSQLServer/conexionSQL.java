package conexionSQLServer;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class conexionSQL {
   
    private String db = "consultorioDental";
    private String user = "sa"; 
    private String password = "123"; 
    private String urlSql = "jdbc:sqlserver://localhost:1433;databaseName="+ db + ";integratedSecurity=true;"; 

    public Connection conectar = null;

    public Connection conexion() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conectar = DriverManager.getConnection(urlSql,user,password);
            System.out.println("CONECTADO CON EXITO");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return conectar;

    }
    
    public int ejecutarSentenciaSQL(String strSentenciaSQL){
        try {
            PreparedStatement pstm = conectar.prepareStatement(strSentenciaSQL);
            pstm.execute();
            return 1;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    
    }
    
    public  ResultSet consultarRegistros(String strSentenciaSQL){
    
        try {
            PreparedStatement pstm = conectar.prepareStatement(strSentenciaSQL);
            ResultSet respuesta = pstm.executeQuery();
            return respuesta;
        } catch (Exception e) {
            System.out.println(e);
            return null;
            
        }
    
    }

   


    
    
    
    
    
}





