package conexionSQLServer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class logicaTipoconsulta {

    ResultSet rs;
    private conexionSQL con = new conexionSQL();
    private Connection cn = con.conexion();

    public DefaultComboBoxModel mostrarComboBox() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();

        try {
            CallableStatement csta = cn.prepareCall("mostrarTipoConsultaComboBox");
            rs = csta.executeQuery();
            while (rs.next()) {
                modelo.addElement(rs.getString(1));
            }

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);

        }
        return modelo;
    }

    public DefaultTableModel mostrarproductosservicios() {
        DefaultTableModel modelo;

        String[] tipoconsulta = {"ID TIPO CONSULTA", "CONSULTA", "PRECIO"};

        String[] Registro = new String[3];
        modelo = new DefaultTableModel(null, tipoconsulta);

        try {
            CallableStatement csta = cn.prepareCall("getmostrarListaProductoServicios");
            rs = csta.executeQuery();

            while (rs.next()) {
                Registro[0] = rs.getString("idtipoconsulta");
                Registro[1] = rs.getString("consulta");
                Registro[2] = rs.getString("precio");

                modelo.addRow(Registro);
            }

            return modelo;

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }

    }

    public String buscarTipoConsultorio(String insepa) {
        String[] Registro = new String[7];

        try {
            CallableStatement csta = cn.prepareCall("{call getmostrarTipoConsulta (?)}");
            csta.setString(1, insepa);

            rs = csta.executeQuery();
            while (rs.next()) {

                Registro[2] = rs.getString("precio");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String resultado = Registro[2];
        System.out.println(resultado);
        return resultado;

    }

}
