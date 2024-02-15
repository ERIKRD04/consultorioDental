package Views;

import java.io.IOException;
import Models.cita;
import Models.consultorio;
import Models.especialidad;
import Models.historia;
import views.nuevoPago;
import views.ListarCitas;
import views.ListarServicios;
import views.ListarPagosImpre;
import Models.medico;
import Views.pacientesVm;
import Models.paciente;
import Models.pago;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import library.Objectos;
import conexionSQLServer.inserPaciente;
import conexionSQLServer.ExportarExcel;
import conexionSQLServer.logicaCita;
import conexionSQLServer.logicaConsultorio;
import conexionSQLServer.logicaEspecialidad;
import conexionSQLServer.logicaHistoria;
import conexionSQLServer.logicaMedico;
import conexionSQLServer.logicaTipoconsulta;
import conexionSQLServer.logicaPago;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.JTable;

import org.json.JSONArray;

public class Sistema extends javax.swing.JFrame {

    DefaultTableModel modelo = new DefaultTableModel();
//    DefaultTableModel modelo2 = new DefaultTableModel();
//    String titulo[] = {"Fecha", "Tratamiento", "Costo", "Saldo"};
//    String registros[] = new String[4];

    public Sistema() {
        initComponents();
        //inicializacion de la tabla pago 

        this.tabla_pago.setModel(modelo);
        String titulo1[] = {"CODICO PRODUCTO", "DESCRIPCION", "CANTIDAD", "COSTO", "ACUENTA", "SALDO", "SUB TOTAL"};
        modelo.setColumnIdentifiers(titulo1);

        jpanel_cita.setVisible(false);
        txt_hemorragias_historia.setEnabled(false);
        txt_medicamento_historia.setEnabled(false);
        txt_enfermedad_historia.setEnabled(false);
        btn_Editar_Paciente.setEnabled(false);
        btn_agregar_paciente.setEnabled(true);
        btn_cancelar_paciente.setEnabled(false);
        btn_Editar_medico.setEnabled(false);
        btn_agregar_medico.setEnabled(true);
        btn_cancelar_medico.setEnabled(false);
        btn_guardar_cita.setEnabled(false);

        txt_descuento_pago.setText("0");
        txt_acuenta_pago.setEnabled(false);
        txt_saldo_pago.setEnabled(false);
        txt_serie_Pago.setEditable(false);
        FechasJdate();
        mostrarTablaListadoPaciente();
        mostrarTablaListadoMedico();
        mostrarTablaListadoConsultorio();
        mostrarTablaListadoEspecialidad();
        mostrarTablaListadoCita();

        // mostrarTablaListadoHistoria();
        logicaEspecialidad lg = new logicaEspecialidad();
        combo_especialidad_medico.setModel(lg.mostrarComboBox());
//        combo_especialidad_medico.addItem("SELECCIONE");
        logicaConsultorio lc = new logicaConsultorio();
        combo_consultorio_cita.setModel(lc.mostrarComboBox());

        logicaTipoconsulta ltp = new logicaTipoconsulta();
        combo_tipoconsulta_cita.setModel(ltp.mostrarComboBox());

        logicaMedico lm = new logicaMedico();
        combo_medico_cita.setModel(lm.mostrarComboBox());
        logicaMedico lm2 = new logicaMedico();
        combo_medico_historia.setModel(lm2.mostrarComboBox());

        // this.tabla_tratamiento_historia.setModel(modelo);
//        modelo.setColumnIdentifiers(titulo);
    }

    public void mostrarTablaListadoPaciente() {

        try {
            DefaultTableModel modelo;
            inserPaciente func = new inserPaciente();
            modelo = func.mostrarPaciente();
            TablaListadoPaciente.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

    }

    public void mostrarlistadoPagos() {
        try {
            DefaultTableModel modelo;
            logicaPago func = new logicaPago();

            modelo = func.mostrarlistadoPagos();
            tabla_listadoPago.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

    }

    public void mostrarlistadoAbonos() {
        try {
            DefaultTableModel modelo;
            logicaPago func = new logicaPago();
            modelo = func.mostrarlistadoAbonos();
            tabla_listadoPago.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

    }

    public void mostrarTablaListadoCita() {

        try {
            DefaultTableModel modelo;
            logicaCita func = new logicaCita();
            modelo = func.mostrarCita();
            tablaListadoCita.setModel(modelo);
            //tablaListadoCitaPrincipal.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

    }

    public void mostrarTablaListadoCitaPrincipal() {

        try {
            DefaultTableModel modelo;
            logicaCita func = new logicaCita();
            modelo = func.mostrarCitaPrincipal(combo_fechainicio_principal.getDateFormatString(), combo_fechafin_principal.getDateFormatString(), combo_estadoCita_principal.getItemAt(0), tablaListadoCitaPrincipal);
            tablaListadoCitaPrincipal.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

    }

//        public void mostrarTablaListadoHistoria( String insepa  ) {
//
//        try {
//            DefaultTableModel modelo;
//            logicaHistoria func = new logicaHistoria();
//            modelo = func.MostrarHistoria(insepa,tabla);
//            tabla_historia_paciente.setModel(modelo);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(rootPane, e);
//        }
//
//    }
    public void generarSerie() {
        logicaPago logicpago = new logicaPago();
        String Resultado = logicpago.mostrarSeriePago();
        if (Resultado == null) {
            txt_numeroserie_Pago.setText("0000001");
        } else {
            int increment = Integer.parseInt(Resultado);
            increment = increment + 1;
            txt_numeroserie_Pago.setText("000000" + increment);
        }
    }

    public void FechasJdate() {
        Calendar micalendario = new GregorianCalendar();

        combo_fechainicio_principal.setCalendar(micalendario);
        combo_fechafin_principal.setCalendar(micalendario);
        jdate_fecha_cita.setCalendar(micalendario);
        Jdate_fechanaci_paciente.setCalendar(micalendario);
        jdate_fechatratamiento_historia.setCalendar(micalendario);
        jadate_fecha_historia.setCalendar(micalendario);
        jdate_fechanacimiento_medico.setCalendar(micalendario);
        jdate_fecha_pago.setCalendar(micalendario);
        jdate_fechainicio_listadoPago.setCalendar(micalendario);
        jdate_fechafin_listadopago.setCalendar(micalendario);
        jdate_fechainicio_listadoAbono.setCalendar(micalendario);
        jdate_fechafin_listadoAbono.setCalendar(micalendario);

        // combo_fechainicio_listarcita.setCalendar(micalendario);
    }

    public void mostrarTablaListadoMedico() {

        try {
            DefaultTableModel modelo;
            logicaMedico func = new logicaMedico();
            modelo = func.mostrarMedico();
            TablaListadoMedico.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

    }

    public void mostrarTablaListadoEspecialidad() {

        try {
            DefaultTableModel modelo;
            logicaEspecialidad func = new logicaEspecialidad();
            modelo = func.mostrarEspecialidad();
            tablaListadoEspecialidad.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

    }

    public void mostrarTablaListadoConsultorio() {

        try {
            DefaultTableModel modelo;
            logicaConsultorio func = new logicaConsultorio();
            modelo = func.mostrarConsultorio();
            tablalistadoConsultorio.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PopupMenuPaciente = new javax.swing.JPopupMenu();
        opcEliminarPopPaciente = new javax.swing.JMenuItem();
        PopupMenuMedico = new javax.swing.JPopupMenu();
        opcEliminarPopMedico = new javax.swing.JMenuItem();
        popupMenuCita = new javax.swing.JPopupMenu();
        opcEditarCita = new javax.swing.JMenuItem();
        opcEliminarCita = new javax.swing.JMenuItem();
        PopupMenuHistoriaPaciente = new javax.swing.JPopupMenu();
        opcEditarHistoria = new javax.swing.JMenuItem();
        PopupMenuPrincipal = new javax.swing.JPopupMenu();
        opcRealizarPago = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        btn_Paciente = new javax.swing.JButton();
        btn_Paciente2 = new javax.swing.JButton();
        btn_Paciente3 = new javax.swing.JButton();
        btn_Paciente1 = new javax.swing.JButton();
        btn_Paciente4 = new javax.swing.JButton();
        btn_Paciente5 = new javax.swing.JButton();
        btn_Paciente6 = new javax.swing.JButton();
        btn_Paciente7 = new javax.swing.JButton();
        jPanel54 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTabbedPane_Principal = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        combo_fechainicio_principal = new com.toedter.calendar.JDateChooser();
        combo_fechafin_principal = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        combo_estadoCita_principal = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btn_sincronizar_listadopago1 = new javax.swing.JButton();
        jPanel53 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaListadoCitaPrincipal = new javax.swing.JTable();
        jPanel50 = new javax.swing.JPanel();
        btn_pagar_principal = new javax.swing.JButton();
        btn_historiaclinica_principal = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btn_AgregarCliente2 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        txt_BuscarPacientePrincipal = new javax.swing.JTextField();
        btn_actualizar_cita1 = new javax.swing.JButton();
        btn_buscar_CitaPrincipal1 = new javax.swing.JButton();
        btn_exportar_excel_principal1 = new javax.swing.JButton();
        pnlPaciente = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txt_BuscarCliente = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btn_actualizar_Paciente = new javax.swing.JButton();
        btn_buscar_Paciente = new javax.swing.JButton();
        PanelPrincipal = new javax.swing.JPanel();
        Panel_Paciente = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        Labelimagen_Paciente = new javax.swing.JLabel();
        LabelPaciente_Dni = new javax.swing.JLabel();
        txt_dni_paciente = new javax.swing.JTextField();
        txt_nombres_paciente = new javax.swing.JTextField();
        LabelPaciente_Nombre = new javax.swing.JLabel();
        LabelPaciente_Apellido = new javax.swing.JLabel();
        LabelPaciente_FechaNacimiento = new javax.swing.JLabel();
        LabelPaciente_Genero = new javax.swing.JLabel();
        LabelPaciente_Email = new javax.swing.JLabel();
        txt_telefono_paciente = new javax.swing.JTextField();
        btn_agregar_paciente = new javax.swing.JButton();
        txt_apellidos_paciente = new javax.swing.JTextField();
        txt_ocupacion_paciente = new javax.swing.JTextField();
        LabelPaciente_Ocupacion = new javax.swing.JLabel();
        LabelPaciente_Provincia = new javax.swing.JLabel();
        txt_provincia_paciente = new javax.swing.JTextField();
        txt_departamento_paciente = new javax.swing.JTextField();
        LabelPaciente_Departamento = new javax.swing.JLabel();
        LabelPaciente_Distrito = new javax.swing.JLabel();
        txt_distrito_paciente = new javax.swing.JTextField();
        txt_direccion_paciente = new javax.swing.JTextField();
        LabelPaciente_Direccion = new javax.swing.JLabel();
        LabelPaciente_Telefono = new javax.swing.JLabel();
        Jdate_fechanaci_paciente = new com.toedter.calendar.JDateChooser();
        comb_genero_paciente = new javax.swing.JComboBox<>();
        txt_email_paciente = new javax.swing.JTextField();
        btn_Editar_Paciente = new javax.swing.JButton();
        btn_cancelar_paciente = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txt_Buscar_medico = new javax.swing.JTextField();
        btn_buscar_medico = new javax.swing.JButton();
        btn_actualizar_medico = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jTabbedPane11 = new javax.swing.JTabbedPane();
        jPanel41 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        Label_dni_medico = new javax.swing.JLabel();
        txt_Dni_medico = new javax.swing.JTextField();
        label_apellidos_medico = new javax.swing.JLabel();
        txt_Apellidos_medico = new javax.swing.JTextField();
        label_nombres_medico = new javax.swing.JLabel();
        txt_Nombres_medico = new javax.swing.JTextField();
        label_fechanacimiento_medico = new javax.swing.JLabel();
        label_telefono_medico = new javax.swing.JLabel();
        label_genero_medico = new javax.swing.JLabel();
        txt_telefono_medico = new javax.swing.JTextField();
        label_direccion_medico = new javax.swing.JLabel();
        txt_direccion_medico = new javax.swing.JTextField();
        label_email_medico = new javax.swing.JLabel();
        txt_email_medico = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        label_imagen_medico = new javax.swing.JLabel();
        jdate_fechanacimiento_medico = new com.toedter.calendar.JDateChooser();
        btn_agregar_medico = new javax.swing.JButton();
        btn_Editar_medico = new javax.swing.JButton();
        btn_cancelar_medico = new javax.swing.JButton();
        comb_genero_medico = new javax.swing.JComboBox<>();
        label_especialidad_medico = new javax.swing.JLabel();
        combo_especialidad_medico = new javax.swing.JComboBox<>();
        jPanel15 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        TablaListadoMedico = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel19 = new javax.swing.JPanel();
        label_nombre_consultorio = new javax.swing.JLabel();
        txt_nombre_consultorio = new javax.swing.JTextField();
        label_numero_consultorio = new javax.swing.JLabel();
        txt_numero_consultorio = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        Labelimagen_Cliente3 = new javax.swing.JLabel();
        btn_agregar_consultorio = new javax.swing.JButton();
        btn_cancelar_consultorio = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablalistadoConsultorio = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        jPanel28 = new javax.swing.JPanel();
        LabelCliente_Dni3 = new javax.swing.JLabel();
        txt_nombre_especialidad = new javax.swing.JTextField();
        LabelCliente_Apellido3 = new javax.swing.JLabel();
        txt_observacion_especialidad = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        Labelimagen_Cliente4 = new javax.swing.JLabel();
        btn_cancelar_especialidad = new javax.swing.JButton();
        btn_agregar_especialidad = new javax.swing.JButton();
        jPanel30 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jTabbedPane8 = new javax.swing.JTabbedPane();
        jPanel32 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaListadoEspecialidad = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        PanelPrincipal1 = new javax.swing.JPanel();
        TabbedPaneHistoriaClinica = new javax.swing.JTabbedPane();
        jPanel43 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        Label_imagen_historia = new javax.swing.JLabel();
        LabelCliente_Dni1 = new javax.swing.JLabel();
        txt_dni_Historia = new javax.swing.JTextField();
        txt_nombrepaciente_Historia = new javax.swing.JTextField();
        LabelCliente_Nombre1 = new javax.swing.JLabel();
        LabelCliente_Email3 = new javax.swing.JLabel();
        LabelCliente_Telefono1 = new javax.swing.JLabel();
        txt_genero_historia = new javax.swing.JTextField();
        LabelCliente_Direccion5 = new javax.swing.JLabel();
        txt_telefono_historia = new javax.swing.JTextField();
        txt_provincia_historia = new javax.swing.JTextField();
        LabelCliente_Email4 = new javax.swing.JLabel();
        LabelCliente_Direccion6 = new javax.swing.JLabel();
        txt_Departamento_historia = new javax.swing.JTextField();
        txt_ocupacion_historia = new javax.swing.JTextField();
        LabelCliente_Email5 = new javax.swing.JLabel();
        LabelCliente_Direccion7 = new javax.swing.JLabel();
        txt_distrito_historia = new javax.swing.JTextField();
        txt_direccion_historia = new javax.swing.JTextField();
        LabelCliente_Direccion8 = new javax.swing.JLabel();
        LabelCliente_Direccion9 = new javax.swing.JLabel();
        txt_email_historia = new javax.swing.JTextField();
        btn_buscar_historia = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        tabla_historia_paciente = new javax.swing.JTable();
        LabelCliente_Nombre4 = new javax.swing.JLabel();
        txt_fechanacimiento_historia = new javax.swing.JTextField();
        PanelPrincipal2 = new javax.swing.JPanel();
        TabbedPaneHistoriaClinica2 = new javax.swing.JTabbedPane();
        jPanel45 = new javax.swing.JPanel();
        jPanel61 = new javax.swing.JPanel();
        jPanel55 = new javax.swing.JPanel();
        LabelCliente_Direccion16 = new javax.swing.JLabel();
        jPanel64 = new javax.swing.JPanel();
        jcombo_enfermedad_historia = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_enfermedad_historia = new javax.swing.JTextPane();
        LabelCliente_Direccion17 = new javax.swing.JLabel();
        jPanel65 = new javax.swing.JPanel();
        jcombo_medicamento_historia = new javax.swing.JComboBox<>();
        jScrollPane11 = new javax.swing.JScrollPane();
        txt_medicamento_historia = new javax.swing.JTextPane();
        LabelCliente_Direccion18 = new javax.swing.JLabel();
        jPanel73 = new javax.swing.JPanel();
        jcombo_hemorragias_historia = new javax.swing.JComboBox<>();
        jScrollPane12 = new javax.swing.JScrollPane();
        txt_hemorragias_historia = new javax.swing.JTextPane();
        jPanel60 = new javax.swing.JPanel();
        jPanel58 = new javax.swing.JPanel();
        jPanel56 = new javax.swing.JPanel();
        LabelCliente_Nombre3 = new javax.swing.JLabel();
        jadate_fecha_historia = new com.toedter.calendar.JDateChooser();
        jPanel57 = new javax.swing.JPanel();
        LabelCliente_Dni9 = new javax.swing.JLabel();
        combo_medico_historia = new javax.swing.JComboBox<>();
        jPanel74 = new javax.swing.JPanel();
        LabelCliente_Dni6 = new javax.swing.JLabel();
        txt_numero_historia = new javax.swing.JTextField();
        jPanel75 = new javax.swing.JPanel();
        LabelCliente_Email12 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        txt_diagnostico_historia = new javax.swing.JTextPane();
        jPanel63 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        jPanel71 = new javax.swing.JPanel();
        jPanel69 = new javax.swing.JPanel();
        jdate_fechatratamiento_historia = new com.toedter.calendar.JDateChooser();
        LabelCliente_Direccion24 = new javax.swing.JLabel();
        jPanel70 = new javax.swing.JPanel();
        LabelCliente_Direccion23 = new javax.swing.JLabel();
        txt_tratamiento_historia = new javax.swing.JTextField();
        jPanel68 = new javax.swing.JPanel();
        jPanel66 = new javax.swing.JPanel();
        LabelCliente_Direccion25 = new javax.swing.JLabel();
        txt_costo_tratamiento = new javax.swing.JTextField();
        jPanel67 = new javax.swing.JPanel();
        LabelCliente_Direccion26 = new javax.swing.JLabel();
        txt_saldo_tratamiento = new javax.swing.JTextField();
        LabelCliente_Email13 = new javax.swing.JLabel();
        jPanel72 = new javax.swing.JPanel();
        jPanel62 = new javax.swing.JPanel();
        btn_agregar_historia = new javax.swing.JButton();
        btn_cancelar_historia = new javax.swing.JButton();
        btn_actualizar_historia = new javax.swing.JButton();
        jPanel46 = new javax.swing.JPanel();
        jPanel51 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel48 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        combo_tipodocumento_Pago = new javax.swing.JComboBox<>();
        LabelCliente_Dni10 = new javax.swing.JLabel();
        txt_numeroserie_Pago = new javax.swing.JTextField();
        LabelCliente_Apellido11 = new javax.swing.JLabel();
        jdate_fecha_pago = new com.toedter.calendar.JDateChooser();
        LabelCliente_Apellido13 = new javax.swing.JLabel();
        LabelCliente_Dni11 = new javax.swing.JLabel();
        txt_nombrepaciente_pago = new javax.swing.JTextField();
        txt_descripcion_producto_pago = new javax.swing.JTextField();
        btn_buscarPaciente_Pago = new javax.swing.JButton();
        LabelCliente_Apellido14 = new javax.swing.JLabel();
        txt_cantidad_producto_pago = new javax.swing.JTextField();
        LabelCliente_Dni5 = new javax.swing.JLabel();
        LabelCliente_Apellido15 = new javax.swing.JLabel();
        txt_serie_Pago = new javax.swing.JTextField();
        txt_ruc_pago = new javax.swing.JTextField();
        LabelCliente_Apellido16 = new javax.swing.JLabel();
        LabelCliente_Dni12 = new javax.swing.JLabel();
        LabelCliente_Apellido17 = new javax.swing.JLabel();
        txt_costo_producto_pago = new javax.swing.JTextField();
        LabelCliente_Dni13 = new javax.swing.JLabel();
        txt_codigo_Producto_pago = new javax.swing.JTextField();
        LabelCliente_Apellido19 = new javax.swing.JLabel();
        txt_monto_pago = new javax.swing.JTextField();
        jScrollPane15 = new javax.swing.JScrollPane();
        tabla_pago = new javax.swing.JTable();
        btn_agregar_pago = new javax.swing.JButton();
        btn_registrar_pago = new javax.swing.JButton();
        btn_eliminar_pago = new javax.swing.JButton();
        btn_editar_pago = new javax.swing.JButton();
        btn_imprimir_pago = new javax.swing.JButton();
        btn_cancelar_pago = new javax.swing.JButton();
        txt_dniPaciente_Pago = new javax.swing.JTextField();
        txt_idcita_pago = new javax.swing.JTextField();
        btn_buscarcita_pago = new javax.swing.JButton();
        LabelCliente_Apellido21 = new javax.swing.JLabel();
        txt_acuenta_pago = new javax.swing.JTextField();
        LabelCliente_Apellido18 = new javax.swing.JLabel();
        txt_saldo_pago = new javax.swing.JTextField();
        LabelCliente_Apellido20 = new javax.swing.JLabel();
        btn_buscarProductoServicio_pago = new javax.swing.JButton();
        txt_total_pago = new javax.swing.JTextField();
        LabelCliente_Apellido22 = new javax.swing.JLabel();
        LabelCliente_Apellido23 = new javax.swing.JLabel();
        txt_descuento_pago = new javax.swing.JTextField();
        txt_subtotal_pago = new javax.swing.JTextField();
        LabelCliente_Apellido24 = new javax.swing.JLabel();
        jPanel52 = new javax.swing.JPanel();
        jTabbedPane12 = new javax.swing.JTabbedPane();
        jPanel49 = new javax.swing.JPanel();
        LabelCliente_Dni7 = new javax.swing.JLabel();
        txt_dni_listadoPago = new javax.swing.JTextField();
        btn_buscar_pagoListado = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        tabla_listadoPago = new javax.swing.JTable();
        LabelCliente_Nombre5 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jdate_fechainicio_listadoPago = new com.toedter.calendar.JDateChooser();
        jdate_fechafin_listadopago = new com.toedter.calendar.JDateChooser();
        jLabel24 = new javax.swing.JLabel();
        btn_sincronizar_listadopago = new javax.swing.JButton();
        btn_exportarExcel_listadoPago = new javax.swing.JButton();
        jPanel59 = new javax.swing.JPanel();
        LabelCliente_Nombre6 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jdate_fechainicio_listadoAbono = new com.toedter.calendar.JDateChooser();
        jLabel26 = new javax.swing.JLabel();
        jdate_fechafin_listadoAbono = new com.toedter.calendar.JDateChooser();
        btn_sincronizar_listadoAbono = new javax.swing.JButton();
        LabelCliente_Dni14 = new javax.swing.JLabel();
        txt_dni_listadoAbono = new javax.swing.JTextField();
        btn_buscarPaciente_listadoAbono = new javax.swing.JButton();
        jScrollPane16 = new javax.swing.JScrollPane();
        tabla_listadoAbono = new javax.swing.JTable();
        btn_exportarExcel_listadoAbono = new javax.swing.JButton();
        jPanel34 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txt_Buscar_cita = new javax.swing.JTextField();
        btn_buscar_Cita = new javax.swing.JButton();
        btn_actualizar_cita = new javax.swing.JButton();
        jPanel36 = new javax.swing.JPanel();
        jTabbedPane9 = new javax.swing.JTabbedPane();
        jpanel_cita = new javax.swing.JPanel();
        LabelCliente_Dni4 = new javax.swing.JLabel();
        txt_dnipaciente_cita = new javax.swing.JTextField();
        LabelCliente_Apellido4 = new javax.swing.JLabel();
        txt_nombreCompleto_cita = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        LabelCliente_Apellido5 = new javax.swing.JLabel();
        LabelCliente_Apellido6 = new javax.swing.JLabel();
        LabelCliente_Apellido7 = new javax.swing.JLabel();
        txt_precio_cita = new javax.swing.JTextField();
        LabelCliente_Apellido8 = new javax.swing.JLabel();
        LabelCliente_Apellido9 = new javax.swing.JLabel();
        LabelCliente_Apellido10 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txt_observaciones_cita = new javax.swing.JTextPane();
        Labelimagen_Cliente2 = new javax.swing.JLabel();
        LabelCliente_Apellido12 = new javax.swing.JLabel();
        btn_buscar_cita = new javax.swing.JButton();
        combo_medico_cita = new javax.swing.JComboBox<>();
        combo_tipoconsulta_cita = new javax.swing.JComboBox<>();
        combo_consultorio_cita = new javax.swing.JComboBox<>();
        btn_agregar_cita = new javax.swing.JButton();
        btn_guardar_cita = new javax.swing.JButton();
        combo_hora_cita = new javax.swing.JComboBox<>();
        jdate_fecha_cita = new com.toedter.calendar.JDateChooser();
        btn_cancelar_cita = new javax.swing.JButton();
        txt_idcita_cita = new javax.swing.JTextField();
        jPanel39 = new javax.swing.JPanel();
        jTabbedPane10 = new javax.swing.JTabbedPane();
        jPanel40 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaListadoCita = new javax.swing.JTable();

        opcEliminarPopPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Delete.png"))); // NOI18N
        opcEliminarPopPaciente.setText("Eliminar");
        opcEliminarPopPaciente.setInheritsPopupMenu(true);
        opcEliminarPopPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcEliminarPopPacienteActionPerformed(evt);
            }
        });
        PopupMenuPaciente.add(opcEliminarPopPaciente);

        opcEliminarPopMedico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Delete.png"))); // NOI18N
        opcEliminarPopMedico.setText("Eliminar Medico");
        opcEliminarPopMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcEliminarPopMedicoActionPerformed(evt);
            }
        });
        PopupMenuMedico.add(opcEliminarPopMedico);

        opcEditarCita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/editar.png"))); // NOI18N
        opcEditarCita.setText("Editar Cita");
        opcEditarCita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                opcEditarCitaMouseClicked(evt);
            }
        });
        opcEditarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcEditarCitaActionPerformed(evt);
            }
        });
        popupMenuCita.add(opcEditarCita);

        opcEliminarCita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Delete.png"))); // NOI18N
        opcEliminarCita.setText("Eliminar Cita");
        opcEliminarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcEliminarCitaActionPerformed(evt);
            }
        });
        popupMenuCita.add(opcEliminarCita);

        opcEditarHistoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Delete.png"))); // NOI18N
        opcEditarHistoria.setText("Editar Historia");
        opcEditarHistoria.setAutoscrolls(true);
        opcEditarHistoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcEditarHistoriaActionPerformed(evt);
            }
        });
        PopupMenuHistoriaPaciente.add(opcEditarHistoria);

        opcRealizarPago.setText("Realizar Pago");
        opcRealizarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcRealizarPagoActionPerformed(evt);
            }
        });
        PopupMenuPrincipal.add(opcRealizarPago);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 60)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SISTEMA ODONTOLÓGICO");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel37.setLayout(new java.awt.GridLayout(1, 0));

        btn_Paciente.setBackground(new java.awt.Color(51, 51, 51));
        btn_Paciente.setFont(new java.awt.Font("Dialog", 3, 28)); // NOI18N
        btn_Paciente.setForeground(new java.awt.Color(255, 255, 255));
        btn_Paciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/principal.png"))); // NOI18N
        btn_Paciente.setText("Principal");
        btn_Paciente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Paciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PacienteActionPerformed(evt);
            }
        });
        jPanel37.add(btn_Paciente);

        btn_Paciente2.setBackground(new java.awt.Color(51, 51, 51));
        btn_Paciente2.setFont(new java.awt.Font("Dialog", 3, 28)); // NOI18N
        btn_Paciente2.setForeground(new java.awt.Color(255, 255, 255));
        btn_Paciente2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/pacientes.png"))); // NOI18N
        btn_Paciente2.setText("Pacientes");
        btn_Paciente2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Paciente2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Paciente2ActionPerformed(evt);
            }
        });
        jPanel37.add(btn_Paciente2);

        btn_Paciente3.setBackground(new java.awt.Color(51, 51, 51));
        btn_Paciente3.setFont(new java.awt.Font("Dialog", 3, 28)); // NOI18N
        btn_Paciente3.setForeground(new java.awt.Color(255, 255, 255));
        btn_Paciente3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/cita.png"))); // NOI18N
        btn_Paciente3.setText("Cita");
        btn_Paciente3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Paciente3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Paciente3ActionPerformed(evt);
            }
        });
        jPanel37.add(btn_Paciente3);

        btn_Paciente1.setBackground(new java.awt.Color(51, 51, 51));
        btn_Paciente1.setFont(new java.awt.Font("Dialog", 3, 28)); // NOI18N
        btn_Paciente1.setForeground(new java.awt.Color(255, 255, 255));
        btn_Paciente1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/medico.png"))); // NOI18N
        btn_Paciente1.setText("Medico");
        btn_Paciente1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Paciente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Paciente1ActionPerformed(evt);
            }
        });
        jPanel37.add(btn_Paciente1);

        btn_Paciente4.setBackground(new java.awt.Color(51, 51, 51));
        btn_Paciente4.setFont(new java.awt.Font("Dialog", 3, 28)); // NOI18N
        btn_Paciente4.setForeground(new java.awt.Color(255, 255, 255));
        btn_Paciente4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/historia.png"))); // NOI18N
        btn_Paciente4.setText("Historia");
        btn_Paciente4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Paciente4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Paciente4ActionPerformed(evt);
            }
        });
        jPanel37.add(btn_Paciente4);

        btn_Paciente5.setBackground(new java.awt.Color(51, 51, 51));
        btn_Paciente5.setFont(new java.awt.Font("Dialog", 3, 28)); // NOI18N
        btn_Paciente5.setForeground(new java.awt.Color(255, 255, 255));
        btn_Paciente5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/consultorio.png"))); // NOI18N
        btn_Paciente5.setText("Consultorio");
        btn_Paciente5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Paciente5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Paciente5ActionPerformed(evt);
            }
        });
        jPanel37.add(btn_Paciente5);

        btn_Paciente6.setBackground(new java.awt.Color(51, 51, 51));
        btn_Paciente6.setFont(new java.awt.Font("Dialog", 3, 28)); // NOI18N
        btn_Paciente6.setForeground(new java.awt.Color(255, 255, 255));
        btn_Paciente6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/pago.png"))); // NOI18N
        btn_Paciente6.setText("Pago");
        btn_Paciente6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Paciente6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Paciente6ActionPerformed(evt);
            }
        });
        jPanel37.add(btn_Paciente6);

        btn_Paciente7.setBackground(new java.awt.Color(51, 51, 51));
        btn_Paciente7.setFont(new java.awt.Font("Dialog", 3, 28)); // NOI18N
        btn_Paciente7.setForeground(new java.awt.Color(255, 255, 255));
        btn_Paciente7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/especialidad.png"))); // NOI18N
        btn_Paciente7.setText("Especialidad");
        btn_Paciente7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Paciente7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Paciente7ActionPerformed(evt);
            }
        });
        jPanel37.add(btn_Paciente7);

        jPanel1.add(jPanel37, java.awt.BorderLayout.SOUTH);

        jPanel54.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102), 20));
        jPanel54.setOpaque(false);
        jPanel54.setPreferredSize(new java.awt.Dimension(340, 220));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/LOGO FINAL2.png"))); // NOI18N

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel54Layout.createSequentialGroup()
                    .addGap(0, 3, Short.MAX_VALUE)
                    .addComponent(jLabel11)
                    .addGap(0, 3, Short.MAX_VALUE)))
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
            .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel54Layout.createSequentialGroup()
                    .addGap(0, 3, Short.MAX_VALUE)
                    .addComponent(jLabel11)
                    .addGap(0, 3, Short.MAX_VALUE)))
        );

        jPanel1.add(jPanel54, java.awt.BorderLayout.WEST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jTabbedPane_Principal.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane_Principal.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPane_Principal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane_PrincipalMouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        combo_fechainicio_principal.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        combo_fechafin_principal.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(70, 106, 124));
        jLabel2.setText("Estado de Cita");

        combo_estadoCita_principal.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        combo_estadoCita_principal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS", "PENDIENTE", "ATENDIDO", "VENCIDO", "ANULADO" }));

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(70, 106, 124));
        jLabel9.setText("Fecha inicio");

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(70, 106, 124));
        jLabel14.setText("Fecha Fin");

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(70, 106, 124));
        jLabel13.setText("Principal");

        btn_sincronizar_listadopago1.setBackground(new java.awt.Color(51, 51, 51));
        btn_sincronizar_listadopago1.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_sincronizar_listadopago1.setForeground(new java.awt.Color(255, 255, 255));
        btn_sincronizar_listadopago1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/actualizar.png"))); // NOI18N
        btn_sincronizar_listadopago1.setText("Sincronizar");
        btn_sincronizar_listadopago1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_sincronizar_listadopago1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sincronizar_listadopago1ActionPerformed(evt);
            }
        });
        btn_sincronizar_listadopago1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_sincronizar_listadopago1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(149, 149, 149)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(combo_fechainicio_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combo_fechafin_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(jLabel2)
                .addGap(30, 30, 30)
                .addComponent(combo_estadoCita_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btn_sincronizar_listadopago1)
                .addContainerGap(629, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(jLabel13))
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(combo_fechainicio_principal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(combo_fechafin_principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(combo_estadoCita_principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_sincronizar_listadopago1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel20, java.awt.BorderLayout.NORTH);

        jPanel53.setLayout(new java.awt.BorderLayout());

        tablaListadoCitaPrincipal.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tablaListadoCitaPrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        tablaListadoCitaPrincipal.setComponentPopupMenu(PopupMenuPrincipal);
        jScrollPane3.setViewportView(tablaListadoCitaPrincipal);

        jPanel53.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel50.setPreferredSize(new java.awt.Dimension(150, 848));

        btn_pagar_principal.setBackground(new java.awt.Color(51, 51, 51));
        btn_pagar_principal.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_pagar_principal.setForeground(new java.awt.Color(255, 255, 255));
        btn_pagar_principal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/actualizar.png"))); // NOI18N
        btn_pagar_principal.setText("Pagar");
        btn_pagar_principal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_pagar_principal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pagar_principalActionPerformed(evt);
            }
        });
        btn_pagar_principal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_pagar_principalKeyReleased(evt);
            }
        });

        btn_historiaclinica_principal.setBackground(new java.awt.Color(51, 51, 51));
        btn_historiaclinica_principal.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_historiaclinica_principal.setForeground(new java.awt.Color(255, 255, 255));
        btn_historiaclinica_principal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/actualizar.png"))); // NOI18N
        btn_historiaclinica_principal.setText("Historia Clinica");
        btn_historiaclinica_principal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_historiaclinica_principal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_historiaclinica_principalActionPerformed(evt);
            }
        });
        btn_historiaclinica_principal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_historiaclinica_principalKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_pagar_principal)
                    .addComponent(btn_historiaclinica_principal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btn_historiaclinica_principal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_pagar_principal)
                .addContainerGap())
        );

        jPanel53.add(jPanel50, java.awt.BorderLayout.EAST);

        btn_AgregarCliente2.setBackground(new java.awt.Color(51, 51, 51));
        btn_AgregarCliente2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_AgregarCliente2.setForeground(new java.awt.Color(255, 255, 255));
        btn_AgregarCliente2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Agregar.png"))); // NOI18N
        btn_AgregarCliente2.setText("Crear Cita");
        btn_AgregarCliente2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_AgregarCliente2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AgregarCliente2ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(70, 106, 124));
        jLabel17.setText("Buscar cita de paciente");

        txt_BuscarPacientePrincipal.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        btn_actualizar_cita1.setBackground(new java.awt.Color(51, 51, 51));
        btn_actualizar_cita1.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_actualizar_cita1.setForeground(new java.awt.Color(255, 255, 255));
        btn_actualizar_cita1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/actualizar.png"))); // NOI18N
        btn_actualizar_cita1.setText("Actualizar");
        btn_actualizar_cita1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_actualizar_cita1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizar_cita1ActionPerformed(evt);
            }
        });
        btn_actualizar_cita1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_actualizar_cita1KeyReleased(evt);
            }
        });

        btn_buscar_CitaPrincipal1.setBackground(new java.awt.Color(51, 51, 51));
        btn_buscar_CitaPrincipal1.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_buscar_CitaPrincipal1.setForeground(new java.awt.Color(255, 255, 255));
        btn_buscar_CitaPrincipal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/buscar.png"))); // NOI18N
        btn_buscar_CitaPrincipal1.setText("Buscar");
        btn_buscar_CitaPrincipal1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_buscar_CitaPrincipal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_CitaPrincipal1ActionPerformed(evt);
            }
        });
        btn_buscar_CitaPrincipal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_buscar_CitaPrincipal1KeyReleased(evt);
            }
        });

        btn_exportar_excel_principal1.setBackground(new java.awt.Color(51, 51, 51));
        btn_exportar_excel_principal1.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_exportar_excel_principal1.setForeground(new java.awt.Color(255, 255, 255));
        btn_exportar_excel_principal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/actualizar.png"))); // NOI18N
        btn_exportar_excel_principal1.setText("Exportar Excel");
        btn_exportar_excel_principal1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_exportar_excel_principal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exportar_excel_principal1ActionPerformed(evt);
            }
        });
        btn_exportar_excel_principal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_exportar_excel_principal1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(btn_AgregarCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(839, 839, 839)
                .addComponent(btn_buscar_CitaPrincipal1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_actualizar_cita1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 496, Short.MAX_VALUE)
                .addComponent(btn_exportar_excel_principal1)
                .addGap(305, 305, 305))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(290, 290, 290)
                    .addComponent(jLabel17)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(txt_BuscarPacientePrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1024, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_buscar_CitaPrincipal1)
                    .addComponent(btn_actualizar_cita1)
                    .addComponent(btn_exportar_excel_principal1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_AgregarCliente2)
                .addGap(19, 19, 19))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(33, 33, 33)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_BuscarPacientePrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17))
                    .addContainerGap(16, Short.MAX_VALUE)))
        );

        jPanel53.add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanel2.add(jPanel53, java.awt.BorderLayout.CENTER);

        jTabbedPane_Principal.addTab("Principal", jPanel2);

        pnlPaciente.setBackground(new java.awt.Color(255, 255, 255));
        pnlPaciente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlPaciente.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(70, 106, 124));
        jLabel3.setText("Ingresa el Numero de Dni");

        txt_BuscarCliente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(70, 106, 124));
        jLabel6.setText("Pacientes");

        btn_actualizar_Paciente.setBackground(new java.awt.Color(51, 51, 51));
        btn_actualizar_Paciente.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_actualizar_Paciente.setForeground(new java.awt.Color(255, 255, 255));
        btn_actualizar_Paciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/actualizar.png"))); // NOI18N
        btn_actualizar_Paciente.setText("Actualizar");
        btn_actualizar_Paciente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_actualizar_Paciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizar_PacienteActionPerformed(evt);
            }
        });
        btn_actualizar_Paciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_actualizar_PacienteKeyReleased(evt);
            }
        });

        btn_buscar_Paciente.setBackground(new java.awt.Color(51, 51, 51));
        btn_buscar_Paciente.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_buscar_Paciente.setForeground(new java.awt.Color(255, 255, 255));
        btn_buscar_Paciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/buscar.png"))); // NOI18N
        btn_buscar_Paciente.setText("Buscar");
        btn_buscar_Paciente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_buscar_Paciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_PacienteActionPerformed(evt);
            }
        });
        btn_buscar_Paciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_buscar_PacienteKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(923, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_buscar_Paciente, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_actualizar_Paciente)
                .addGap(286, 286, 286))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(2057, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_buscar_Paciente)
                    .addComponent(btn_actualizar_Paciente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addComponent(jLabel6)
                    .addContainerGap(10, Short.MAX_VALUE)))
        );

        pnlPaciente.add(jPanel4, java.awt.BorderLayout.NORTH);

        PanelPrincipal.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        PanelPrincipal.setForeground(new java.awt.Color(255, 255, 255));
        PanelPrincipal.setLayout(new java.awt.BorderLayout());

        Panel_Paciente.setBackground(new java.awt.Color(255, 255, 255));
        Panel_Paciente.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel6KeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(70, 106, 124));
        jLabel5.setText("REGISTRAR  PACIENTE");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Labelimagen_Paciente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Labelimagen_Paciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/user.png"))); // NOI18N
        Labelimagen_Paciente.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Labelimagen_Paciente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Labelimagen_Paciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Labelimagen_PacienteMouseClicked(evt);
            }
        });
        Labelimagen_Paciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Labelimagen_PacienteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(Labelimagen_Paciente, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Labelimagen_Paciente, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addContainerGap())
        );

        LabelPaciente_Dni.setBackground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Dni.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelPaciente_Dni.setForeground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Dni.setText("Dni");

        txt_dni_paciente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_dni_paciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_dni_pacienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dni_pacienteKeyTyped(evt);
            }
        });

        txt_nombres_paciente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_nombres_paciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_nombres_pacienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombres_pacienteKeyTyped(evt);
            }
        });

        LabelPaciente_Nombre.setBackground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Nombre.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelPaciente_Nombre.setForeground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Nombre.setText("Nombres");

        LabelPaciente_Apellido.setBackground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Apellido.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelPaciente_Apellido.setForeground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Apellido.setText("Apellidos");

        LabelPaciente_FechaNacimiento.setBackground(new java.awt.Color(70, 106, 124));
        LabelPaciente_FechaNacimiento.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelPaciente_FechaNacimiento.setForeground(new java.awt.Color(70, 106, 124));
        LabelPaciente_FechaNacimiento.setText("Fecha de nacimiento");

        LabelPaciente_Genero.setBackground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Genero.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelPaciente_Genero.setForeground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Genero.setText("Genero");

        LabelPaciente_Email.setBackground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Email.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelPaciente_Email.setForeground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Email.setText("Email");

        txt_telefono_paciente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_telefono_paciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_telefono_pacienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telefono_pacienteKeyTyped(evt);
            }
        });

        btn_agregar_paciente.setBackground(new java.awt.Color(51, 51, 51));
        btn_agregar_paciente.setForeground(new java.awt.Color(255, 255, 255));
        btn_agregar_paciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Agregar.png"))); // NOI18N
        btn_agregar_paciente.setText("Agregar");
        btn_agregar_paciente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_agregar_paciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregar_pacienteActionPerformed(evt);
            }
        });

        txt_apellidos_paciente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_apellidos_paciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_apellidos_pacienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_apellidos_pacienteKeyTyped(evt);
            }
        });

        txt_ocupacion_paciente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_ocupacion_paciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_ocupacion_pacienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_ocupacion_pacienteKeyTyped(evt);
            }
        });

        LabelPaciente_Ocupacion.setBackground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Ocupacion.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelPaciente_Ocupacion.setForeground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Ocupacion.setText("Ocupación");

        LabelPaciente_Provincia.setBackground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Provincia.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelPaciente_Provincia.setForeground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Provincia.setText("Provincia");

        txt_provincia_paciente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_provincia_paciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_provincia_pacienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_provincia_pacienteKeyTyped(evt);
            }
        });

        txt_departamento_paciente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_departamento_paciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_departamento_pacienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_departamento_pacienteKeyTyped(evt);
            }
        });

        LabelPaciente_Departamento.setBackground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Departamento.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelPaciente_Departamento.setForeground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Departamento.setText("Departamento");

        LabelPaciente_Distrito.setBackground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Distrito.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelPaciente_Distrito.setForeground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Distrito.setText("Distrito");

        txt_distrito_paciente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_distrito_paciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_distrito_pacienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_distrito_pacienteKeyTyped(evt);
            }
        });

        txt_direccion_paciente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_direccion_paciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_direccion_pacienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_direccion_pacienteKeyTyped(evt);
            }
        });

        LabelPaciente_Direccion.setBackground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Direccion.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelPaciente_Direccion.setForeground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Direccion.setText("Dirección");

        LabelPaciente_Telefono.setBackground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Telefono.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelPaciente_Telefono.setForeground(new java.awt.Color(70, 106, 124));
        LabelPaciente_Telefono.setText("Telefono");

        Jdate_fechanaci_paciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Jdate_fechanaci_pacienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Jdate_fechanaci_pacienteKeyTyped(evt);
            }
        });

        comb_genero_paciente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONE", "MASCULINO", "FEMENINO" }));
        comb_genero_paciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                comb_genero_pacienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comb_genero_pacienteKeyTyped(evt);
            }
        });

        txt_email_paciente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_email_paciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_email_pacienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_email_pacienteKeyTyped(evt);
            }
        });

        btn_Editar_Paciente.setBackground(new java.awt.Color(51, 51, 51));
        btn_Editar_Paciente.setForeground(new java.awt.Color(255, 255, 255));
        btn_Editar_Paciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/editar.png"))); // NOI18N
        btn_Editar_Paciente.setText("Editar");
        btn_Editar_Paciente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Editar_Paciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Editar_PacienteActionPerformed(evt);
            }
        });

        btn_cancelar_paciente.setBackground(new java.awt.Color(51, 51, 51));
        btn_cancelar_paciente.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancelar_paciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Cancelar.png"))); // NOI18N
        btn_cancelar_paciente.setText("Cancelar");
        btn_cancelar_paciente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cancelar_paciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelar_pacienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(jLabel5))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_ocupacion_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelPaciente_Ocupacion)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_nombres_paciente, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                                    .addComponent(LabelPaciente_Genero)
                                    .addComponent(LabelPaciente_Nombre)
                                    .addComponent(comb_genero_paciente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_telefono_paciente, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                                    .addComponent(LabelPaciente_Telefono)
                                    .addComponent(LabelPaciente_FechaNacimiento)
                                    .addComponent(Jdate_fechanaci_paciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelPaciente_Dni)
                                    .addComponent(txt_dni_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_apellidos_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LabelPaciente_Apellido)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_provincia_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LabelPaciente_Provincia)
                                    .addComponent(LabelPaciente_Direccion))
                                .addGap(59, 59, 59)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelPaciente_Distrito)
                                    .addComponent(LabelPaciente_Email)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txt_direccion_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_email_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_departamento_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LabelPaciente_Departamento)
                                    .addComponent(txt_distrito_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(btn_agregar_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_Editar_Paciente, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_cancelar_paciente)))))
                .addGap(71, 71, 71))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelPaciente_Apellido)
                            .addComponent(LabelPaciente_Dni))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_dni_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_apellidos_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelPaciente_Nombre)
                            .addComponent(LabelPaciente_FechaNacimiento))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_nombres_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Jdate_fechanaci_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelPaciente_Telefono)
                            .addComponent(LabelPaciente_Genero, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(3, 3, 3)
                        .addComponent(txt_telefono_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(comb_genero_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(LabelPaciente_Ocupacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_ocupacion_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(LabelPaciente_Departamento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_departamento_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelPaciente_Provincia)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelPaciente_Distrito)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_provincia_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_distrito_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(LabelPaciente_Direccion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_direccion_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_email_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelPaciente_Email)
                        .addGap(0, 33, Short.MAX_VALUE)))
                .addGap(31, 31, 31)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btn_agregar_paciente, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btn_Editar_Paciente, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(btn_cancelar_paciente))
                .addContainerGap(315, Short.MAX_VALUE))
        );

        Panel_Paciente.addTab("Registro de Pacientes", jPanel6);

        PanelPrincipal.add(Panel_Paciente, java.awt.BorderLayout.CENTER);

        pnlPaciente.add(PanelPrincipal, java.awt.BorderLayout.WEST);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        TablaListadoPaciente.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        TablaListadoPaciente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "DNI", "APELLIDOS", "NOMBRES", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "null", "null", "null", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaListadoPaciente.setComponentPopupMenu(PopupMenuPaciente);
        TablaListadoPaciente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        TablaListadoPaciente.getTableHeader().setReorderingAllowed(false);
        TablaListadoPaciente.setUpdateSelectionOnSort(false);
        TablaListadoPaciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaListadoPacienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaListadoPaciente);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(481, 481, 481))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 1303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Listado de Pacientes", jPanel8);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(84, Short.MAX_VALUE)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(173, 173, 173))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        pnlPaciente.add(jPanel7, java.awt.BorderLayout.CENTER);

        jTabbedPane_Principal.addTab("Pacientes", pnlPaciente);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(70, 106, 124));
        jLabel10.setText("Médicos");

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(70, 106, 124));
        jLabel20.setText("Ingresa el Numero de Dni");

        txt_Buscar_medico.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        btn_buscar_medico.setBackground(new java.awt.Color(51, 51, 51));
        btn_buscar_medico.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_buscar_medico.setForeground(new java.awt.Color(255, 255, 255));
        btn_buscar_medico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/buscar.png"))); // NOI18N
        btn_buscar_medico.setText("Buscar");
        btn_buscar_medico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_buscar_medico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_medicoActionPerformed(evt);
            }
        });
        btn_buscar_medico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_buscar_medicoKeyReleased(evt);
            }
        });

        btn_actualizar_medico.setBackground(new java.awt.Color(51, 51, 51));
        btn_actualizar_medico.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_actualizar_medico.setForeground(new java.awt.Color(255, 255, 255));
        btn_actualizar_medico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/actualizar.png"))); // NOI18N
        btn_actualizar_medico.setText("Actualizar");
        btn_actualizar_medico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_actualizar_medico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizar_medicoActionPerformed(evt);
            }
        });
        btn_actualizar_medico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_actualizar_medicoKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 819, Short.MAX_VALUE)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_Buscar_medico, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btn_buscar_medico, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btn_actualizar_medico)
                .addGap(187, 187, 187))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_Buscar_medico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel20)
                        .addComponent(btn_buscar_medico)
                        .addComponent(btn_actualizar_medico))
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel11, java.awt.BorderLayout.NORTH);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel38.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));
        jPanel41.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(70, 106, 124));
        jLabel19.setText("REGISTRAR MÉDICO");

        Label_dni_medico.setBackground(new java.awt.Color(70, 106, 124));
        Label_dni_medico.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        Label_dni_medico.setForeground(new java.awt.Color(70, 106, 124));
        Label_dni_medico.setText("Dni");

        txt_Dni_medico.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_Dni_medico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_Dni_medicoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_Dni_medicoKeyTyped(evt);
            }
        });

        label_apellidos_medico.setBackground(new java.awt.Color(70, 106, 124));
        label_apellidos_medico.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_apellidos_medico.setForeground(new java.awt.Color(70, 106, 124));
        label_apellidos_medico.setText("Apellidos");

        txt_Apellidos_medico.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_Apellidos_medico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_Apellidos_medicoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_Apellidos_medicoKeyTyped(evt);
            }
        });

        label_nombres_medico.setBackground(new java.awt.Color(70, 106, 124));
        label_nombres_medico.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_nombres_medico.setForeground(new java.awt.Color(70, 106, 124));
        label_nombres_medico.setText("Nombres");

        txt_Nombres_medico.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_Nombres_medico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_Nombres_medicoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_Nombres_medicoKeyTyped(evt);
            }
        });

        label_fechanacimiento_medico.setBackground(new java.awt.Color(70, 106, 124));
        label_fechanacimiento_medico.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_fechanacimiento_medico.setForeground(new java.awt.Color(70, 106, 124));
        label_fechanacimiento_medico.setText("Fecha de nacimiento");

        label_telefono_medico.setBackground(new java.awt.Color(70, 106, 124));
        label_telefono_medico.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_telefono_medico.setForeground(new java.awt.Color(70, 106, 124));
        label_telefono_medico.setText("Teléfono");

        label_genero_medico.setBackground(new java.awt.Color(70, 106, 124));
        label_genero_medico.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_genero_medico.setForeground(new java.awt.Color(70, 106, 124));
        label_genero_medico.setText("Genero");

        txt_telefono_medico.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_telefono_medico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_telefono_medicoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telefono_medicoKeyTyped(evt);
            }
        });

        label_direccion_medico.setBackground(new java.awt.Color(70, 106, 124));
        label_direccion_medico.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_direccion_medico.setForeground(new java.awt.Color(70, 106, 124));
        label_direccion_medico.setText("Direccion");

        txt_direccion_medico.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_direccion_medico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_direccion_medicoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_direccion_medicoKeyTyped(evt);
            }
        });

        label_email_medico.setBackground(new java.awt.Color(70, 106, 124));
        label_email_medico.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_email_medico.setForeground(new java.awt.Color(70, 106, 124));
        label_email_medico.setText("Email");

        txt_email_medico.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_email_medico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_email_medicoKeyReleased(evt);
            }
        });

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        label_imagen_medico.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_imagen_medico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/user.png"))); // NOI18N
        label_imagen_medico.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        label_imagen_medico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_imagen_medico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_imagen_medicoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(label_imagen_medico, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_imagen_medico, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_agregar_medico.setBackground(new java.awt.Color(51, 51, 51));
        btn_agregar_medico.setForeground(new java.awt.Color(255, 255, 255));
        btn_agregar_medico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Agregar.png"))); // NOI18N
        btn_agregar_medico.setText("Agregar");
        btn_agregar_medico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_agregar_medico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregar_medicoActionPerformed(evt);
            }
        });

        btn_Editar_medico.setBackground(new java.awt.Color(51, 51, 51));
        btn_Editar_medico.setForeground(new java.awt.Color(255, 255, 255));
        btn_Editar_medico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/editar.png"))); // NOI18N
        btn_Editar_medico.setText("Editar");
        btn_Editar_medico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Editar_medico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Editar_medicoActionPerformed(evt);
            }
        });

        btn_cancelar_medico.setBackground(new java.awt.Color(51, 51, 51));
        btn_cancelar_medico.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancelar_medico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Cancelar.png"))); // NOI18N
        btn_cancelar_medico.setText("Cancelar");
        btn_cancelar_medico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cancelar_medico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelar_medicoActionPerformed(evt);
            }
        });

        comb_genero_medico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONE", "MASCULINO", "FEMENINO" }));
        comb_genero_medico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                comb_genero_medicoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comb_genero_medicoKeyTyped(evt);
            }
        });

        label_especialidad_medico.setBackground(new java.awt.Color(70, 106, 124));
        label_especialidad_medico.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_especialidad_medico.setForeground(new java.awt.Color(70, 106, 124));
        label_especialidad_medico.setText("Especialidad");

        combo_especialidad_medico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                combo_especialidad_medicoMouseClicked(evt);
            }
        });
        combo_especialidad_medico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_especialidad_medicoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(33, 33, 33))))
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(btn_agregar_medico, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Editar_medico, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_cancelar_medico))
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_Dni_medico, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Label_dni_medico))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_Apellidos_medico, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label_apellidos_medico)))
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel41Layout.createSequentialGroup()
                                        .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(label_direccion_medico)
                                                .addComponent(txt_direccion_medico)
                                                .addComponent(label_nombres_medico)
                                                .addComponent(txt_Nombres_medico, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel41Layout.createSequentialGroup()
                                                .addComponent(comb_genero_medico, 0, 184, Short.MAX_VALUE)
                                                .addGap(59, 59, 59)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel41Layout.createSequentialGroup()
                                        .addComponent(label_genero_medico)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_email_medico, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label_email_medico)
                                    .addComponent(label_fechanacimiento_medico)
                                    .addComponent(label_telefono_medico)
                                    .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jdate_fechanacimiento_medico, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_telefono_medico, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))))
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label_especialidad_medico)
                                    .addComponent(combo_especialidad_medico, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(62, 243, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_dni_medico)
                    .addComponent(label_apellidos_medico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Dni_medico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Apellidos_medico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_fechanacimiento_medico)
                    .addComponent(label_nombres_medico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_Nombres_medico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdate_fechanacimiento_medico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(txt_telefono_medico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_genero_medico)
                            .addComponent(label_telefono_medico))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comb_genero_medico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_direccion_medico)
                    .addComponent(label_email_medico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_direccion_medico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_email_medico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_especialidad_medico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combo_especialidad_medico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_agregar_medico)
                    .addComponent(btn_Editar_medico)
                    .addComponent(btn_cancelar_medico)))
        );

        jTabbedPane11.addTab("Médicos", jPanel41);

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane11)
                .addContainerGap())
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane11)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(157, Short.MAX_VALUE)
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(157, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel12, java.awt.BorderLayout.WEST);

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        TablaListadoMedico.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        TablaListadoMedico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ));
        TablaListadoMedico.setComponentPopupMenu(PopupMenuMedico);
        TablaListadoMedico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaListadoMedicoMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(TablaListadoMedico);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 1708, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 936, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("Lista de Médicos", jPanel16);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane4)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane4))
        );

        jPanel10.add(jPanel15, java.awt.BorderLayout.CENTER);

        jTabbedPane_Principal.addTab("Mèdico", jPanel10);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel18.setLayout(new java.awt.BorderLayout());

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        label_nombre_consultorio.setBackground(new java.awt.Color(70, 106, 124));
        label_nombre_consultorio.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_nombre_consultorio.setForeground(new java.awt.Color(70, 106, 124));
        label_nombre_consultorio.setText("Nombre");

        txt_nombre_consultorio.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_nombre_consultorio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_nombre_consultorioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombre_consultorioKeyTyped(evt);
            }
        });

        label_numero_consultorio.setBackground(new java.awt.Color(70, 106, 124));
        label_numero_consultorio.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label_numero_consultorio.setForeground(new java.awt.Color(70, 106, 124));
        label_numero_consultorio.setText("Numero de consultorio");

        txt_numero_consultorio.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_numero_consultorio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_numero_consultorioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_numero_consultorioKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(70, 106, 124));
        jLabel12.setText("REGISTRAR CONSULTORIO");

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Labelimagen_Cliente3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Labelimagen_Cliente3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/edificio-del-hospital.png"))); // NOI18N
        Labelimagen_Cliente3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Labelimagen_Cliente3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Labelimagen_Cliente3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Labelimagen_Cliente3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Labelimagen_Cliente3, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Labelimagen_Cliente3, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_agregar_consultorio.setBackground(new java.awt.Color(51, 51, 51));
        btn_agregar_consultorio.setForeground(new java.awt.Color(255, 255, 255));
        btn_agregar_consultorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Agregar.png"))); // NOI18N
        btn_agregar_consultorio.setText("Agregar");
        btn_agregar_consultorio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_agregar_consultorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregar_consultorioActionPerformed(evt);
            }
        });

        btn_cancelar_consultorio.setBackground(new java.awt.Color(51, 51, 51));
        btn_cancelar_consultorio.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancelar_consultorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Cancelar.png"))); // NOI18N
        btn_cancelar_consultorio.setText("Cancelar");
        btn_cancelar_consultorio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cancelar_consultorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelar_consultorioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(jLabel12))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 144, Short.MAX_VALUE))
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(label_numero_consultorio)
                        .addGap(0, 309, Short.MAX_VALUE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(label_nombre_consultorio)
                            .addComponent(txt_nombre_consultorio, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                            .addComponent(txt_numero_consultorio))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(btn_agregar_consultorio, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btn_cancelar_consultorio)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(label_nombre_consultorio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nombre_consultorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_numero_consultorio)
                .addGap(2, 2, 2)
                .addComponent(txt_numero_consultorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_agregar_consultorio)
                    .addComponent(btn_cancelar_consultorio))
                .addContainerGap(502, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Consultorio", jPanel19);

        jPanel18.add(jTabbedPane5, java.awt.BorderLayout.CENTER);

        jPanel17.add(jPanel18, java.awt.BorderLayout.WEST);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(70, 106, 124));
        jLabel15.setText("Consultorio");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(2057, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel15)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel17.add(jPanel21, java.awt.BorderLayout.NORTH);

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tablalistadoConsultorio.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tablalistadoConsultorio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        jScrollPane4.setViewportView(tablalistadoConsultorio);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1704, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 898, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane6.addTab("Listado de consultorio", jPanel24);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane6)
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane6)
                .addContainerGap())
        );

        jPanel17.add(jPanel23, java.awt.BorderLayout.CENTER);

        jTabbedPane_Principal.addTab("Consultorio", jPanel17);

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setLayout(new java.awt.BorderLayout());

        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel27.setLayout(new java.awt.BorderLayout());

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        LabelCliente_Dni3.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Dni3.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni3.setText("Nombre de la Especialidad");

        txt_nombre_especialidad.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_nombre_especialidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_nombre_especialidadKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombre_especialidadKeyTyped(evt);
            }
        });

        LabelCliente_Apellido3.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido3.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido3.setText("Observacion");

        txt_observacion_especialidad.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_observacion_especialidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_observacion_especialidadKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_observacion_especialidadKeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(70, 106, 124));
        jLabel16.setText("REGISTRAR ESPECIALIDAD");

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));
        jPanel29.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Labelimagen_Cliente4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Labelimagen_Cliente4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/especialista.png"))); // NOI18N
        Labelimagen_Cliente4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Labelimagen_Cliente4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Labelimagen_Cliente4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Labelimagen_Cliente4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Labelimagen_Cliente4, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Labelimagen_Cliente4, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_cancelar_especialidad.setBackground(new java.awt.Color(51, 51, 51));
        btn_cancelar_especialidad.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancelar_especialidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Cancelar.png"))); // NOI18N
        btn_cancelar_especialidad.setText("Cancelar");
        btn_cancelar_especialidad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cancelar_especialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelar_especialidadActionPerformed(evt);
            }
        });

        btn_agregar_especialidad.setBackground(new java.awt.Color(51, 51, 51));
        btn_agregar_especialidad.setForeground(new java.awt.Color(255, 255, 255));
        btn_agregar_especialidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Agregar.png"))); // NOI18N
        btn_agregar_especialidad.setText("Agregar");
        btn_agregar_especialidad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_agregar_especialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregar_especialidadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(jLabel16))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 144, Short.MAX_VALUE))
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(LabelCliente_Apellido3)
                        .addGap(0, 380, Short.MAX_VALUE))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(LabelCliente_Dni3)
                            .addComponent(txt_nombre_especialidad, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                            .addComponent(txt_observacion_especialidad))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(btn_agregar_especialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btn_cancelar_especialidad)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelCliente_Dni3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nombre_especialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(LabelCliente_Apellido3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_observacion_especialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_agregar_especialidad)
                    .addComponent(btn_cancelar_especialidad))
                .addContainerGap(463, Short.MAX_VALUE))
        );

        jTabbedPane7.addTab("Especialidad", jPanel28);

        jPanel27.add(jTabbedPane7, java.awt.BorderLayout.CENTER);

        jPanel26.add(jPanel27, java.awt.BorderLayout.WEST);

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));
        jPanel30.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(70, 106, 124));
        jLabel28.setText("Especialidad");

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(2059, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel28)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel26.add(jPanel30, java.awt.BorderLayout.NORTH);

        jPanel31.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel31.setLayout(new java.awt.BorderLayout());

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));
        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tablaListadoEspecialidad.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tablaListadoEspecialidad.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        jScrollPane5.setViewportView(tablaListadoEspecialidad);

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1718, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 912, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane8.addTab("Listado de consultorio", jPanel32);

        jPanel31.add(jTabbedPane8, java.awt.BorderLayout.CENTER);

        jPanel26.add(jPanel31, java.awt.BorderLayout.CENTER);

        jTabbedPane_Principal.addTab("Especialidad", jPanel26);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.GridLayout(1, 2));

        PanelPrincipal1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        PanelPrincipal1.setForeground(new java.awt.Color(255, 255, 255));
        PanelPrincipal1.setLayout(new java.awt.BorderLayout());

        TabbedPaneHistoriaClinica.setBackground(new java.awt.Color(255, 255, 255));
        TabbedPaneHistoriaClinica.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel43.setBackground(new java.awt.Color(255, 255, 255));

        jPanel44.setBackground(new java.awt.Color(255, 255, 255));
        jPanel44.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Label_imagen_historia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Label_imagen_historia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/user.png"))); // NOI18N
        Label_imagen_historia.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Label_imagen_historia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Label_imagen_historia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Label_imagen_historiaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(Label_imagen_historia, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Label_imagen_historia, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addContainerGap())
        );

        LabelCliente_Dni1.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Dni1.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni1.setText("Dni");

        txt_dni_Historia.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_dni_Historia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_dni_HistoriaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dni_HistoriaKeyTyped(evt);
            }
        });

        txt_nombrepaciente_Historia.setEditable(false);
        txt_nombrepaciente_Historia.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_nombrepaciente_Historia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_nombrepaciente_HistoriaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombrepaciente_HistoriaKeyTyped(evt);
            }
        });

        LabelCliente_Nombre1.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Nombre1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Nombre1.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Nombre1.setText("Nombre del Paciente");

        LabelCliente_Email3.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Email3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Email3.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Email3.setText("Fecha de nacimiento");

        LabelCliente_Telefono1.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Telefono1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Telefono1.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Telefono1.setText("Genero");

        txt_genero_historia.setEditable(false);
        txt_genero_historia.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_genero_historia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_genero_historiaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_genero_historiaKeyTyped(evt);
            }
        });

        LabelCliente_Direccion5.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Direccion5.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion5.setText("Email");

        txt_telefono_historia.setEditable(false);
        txt_telefono_historia.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_telefono_historia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_telefono_historiaKeyReleased(evt);
            }
        });

        txt_provincia_historia.setEditable(false);
        txt_provincia_historia.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_provincia_historia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_provincia_historiaKeyReleased(evt);
            }
        });

        LabelCliente_Email4.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Email4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Email4.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Email4.setText("Ocupación");

        LabelCliente_Direccion6.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Direccion6.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion6.setText("Provincia");

        txt_Departamento_historia.setEditable(false);
        txt_Departamento_historia.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_Departamento_historia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_Departamento_historiaKeyReleased(evt);
            }
        });

        txt_ocupacion_historia.setEditable(false);
        txt_ocupacion_historia.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_ocupacion_historia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_ocupacion_historiaKeyReleased(evt);
            }
        });

        LabelCliente_Email5.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Email5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Email5.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Email5.setText("Departamento");

        LabelCliente_Direccion7.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Direccion7.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion7.setText("Distrito");

        txt_distrito_historia.setEditable(false);
        txt_distrito_historia.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_distrito_historia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_distrito_historiaKeyReleased(evt);
            }
        });

        txt_direccion_historia.setEditable(false);
        txt_direccion_historia.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_direccion_historia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_direccion_historiaKeyReleased(evt);
            }
        });

        LabelCliente_Direccion8.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Direccion8.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion8.setText("Dirección");

        LabelCliente_Direccion9.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Direccion9.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion9.setText("Telefono");

        txt_email_historia.setEditable(false);
        txt_email_historia.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_email_historia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_email_historiaKeyReleased(evt);
            }
        });

        btn_buscar_historia.setText("Buscar");
        btn_buscar_historia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_historiaActionPerformed(evt);
            }
        });

        tabla_historia_paciente.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tabla_historia_paciente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        tabla_historia_paciente.setComponentPopupMenu(PopupMenuHistoriaPaciente);
        jScrollPane9.setViewportView(tabla_historia_paciente);
        if (tabla_historia_paciente.getColumnModel().getColumnCount() > 0) {
            tabla_historia_paciente.getColumnModel().getColumn(0).setPreferredWidth(1);
            tabla_historia_paciente.getColumnModel().getColumn(1).setPreferredWidth(500);
        }

        LabelCliente_Nombre4.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Nombre4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Nombre4.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Nombre4.setText("Historial del Paciente");

        txt_fechanacimiento_historia.setEditable(false);
        txt_fechanacimiento_historia.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_fechanacimiento_historia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_fechanacimiento_historiaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_fechanacimiento_historiaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_nombrepaciente_Historia, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_dni_Historia, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelCliente_Nombre1)
                            .addComponent(LabelCliente_Dni1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel43Layout.createSequentialGroup()
                                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(txt_fechanacimiento_historia, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(LabelCliente_Email3)
                                        .addComponent(LabelCliente_Direccion9)
                                        .addComponent(txt_telefono_historia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(25, 25, 25)
                                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelCliente_Telefono1)
                                    .addComponent(txt_ocupacion_historia, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LabelCliente_Email4)
                                    .addComponent(txt_genero_historia, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel43Layout.createSequentialGroup()
                                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_Departamento_historia, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LabelCliente_Email5))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_provincia_historia, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LabelCliente_Direccion6)))
                            .addGroup(jPanel43Layout.createSequentialGroup()
                                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_distrito_historia, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_email_historia, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LabelCliente_Direccion5)
                                    .addComponent(LabelCliente_Direccion7)
                                    .addComponent(LabelCliente_Nombre4))
                                .addGap(24, 24, 24)
                                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelCliente_Direccion8)
                                    .addComponent(txt_direccion_historia, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel43Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(btn_buscar_historia, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 946, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(162, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelCliente_Dni1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_dni_Historia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_buscar_historia))
                .addGap(3, 3, 3)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelCliente_Nombre1)
                            .addComponent(LabelCliente_Email3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_nombrepaciente_Historia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2))
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addComponent(LabelCliente_Telefono1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_genero_historia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_fechanacimiento_historia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_provincia_historia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel43Layout.createSequentialGroup()
                                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel43Layout.createSequentialGroup()
                                        .addComponent(LabelCliente_Direccion9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_telefono_historia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel43Layout.createSequentialGroup()
                                        .addComponent(LabelCliente_Email4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_ocupacion_historia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel43Layout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(txt_Departamento_historia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(LabelCliente_Email5)
                                        .addComponent(LabelCliente_Direccion6)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelCliente_Direccion8)
                            .addComponent(LabelCliente_Direccion7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_distrito_historia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_direccion_historia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel43Layout.createSequentialGroup()
                                .addComponent(LabelCliente_Direccion5)
                                .addGap(33, 33, 33))
                            .addComponent(txt_email_historia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelCliente_Nombre4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(290, Short.MAX_VALUE))
        );

        TabbedPaneHistoriaClinica.addTab("HISTORIA PACIENTE", jPanel43);

        PanelPrincipal1.add(TabbedPaneHistoriaClinica, java.awt.BorderLayout.CENTER);

        jPanel14.add(PanelPrincipal1);

        PanelPrincipal2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        PanelPrincipal2.setForeground(new java.awt.Color(255, 255, 255));
        PanelPrincipal2.setLayout(new java.awt.BorderLayout());

        TabbedPaneHistoriaClinica2.setBackground(new java.awt.Color(255, 255, 255));
        TabbedPaneHistoriaClinica2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel45.setBackground(new java.awt.Color(255, 255, 255));
        jPanel45.setLayout(new java.awt.BorderLayout());

        jPanel61.setLayout(new java.awt.BorderLayout(0, 15));

        jPanel55.setLayout(new java.awt.GridLayout(6, 1, 5, 5));

        LabelCliente_Direccion16.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion16.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Direccion16.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion16.setText("Padece alguna enfermedad ?");
        jPanel55.add(LabelCliente_Direccion16);

        jPanel64.setLayout(new java.awt.BorderLayout(15, 0));

        jcombo_enfermedad_historia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NO", "SI" }));
        jcombo_enfermedad_historia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcombo_enfermedad_historiaActionPerformed(evt);
            }
        });
        jPanel64.add(jcombo_enfermedad_historia, java.awt.BorderLayout.WEST);

        jScrollPane2.setViewportView(txt_enfermedad_historia);

        jPanel64.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel55.add(jPanel64);

        LabelCliente_Direccion17.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion17.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Direccion17.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion17.setText("Es alérgico algún medicamento ?");
        jPanel55.add(LabelCliente_Direccion17);

        jPanel65.setLayout(new java.awt.BorderLayout(15, 0));

        jcombo_medicamento_historia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NO", "SI" }));
        jcombo_medicamento_historia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcombo_medicamento_historiaActionPerformed(evt);
            }
        });
        jPanel65.add(jcombo_medicamento_historia, java.awt.BorderLayout.WEST);

        jScrollPane11.setViewportView(txt_medicamento_historia);

        jPanel65.add(jScrollPane11, java.awt.BorderLayout.CENTER);

        jPanel55.add(jPanel65);

        LabelCliente_Direccion18.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion18.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Direccion18.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion18.setText("Es propenso a Hemorragias ?");
        jPanel55.add(LabelCliente_Direccion18);

        jPanel73.setLayout(new java.awt.BorderLayout(15, 0));

        jcombo_hemorragias_historia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NO", "SI" }));
        jcombo_hemorragias_historia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcombo_hemorragias_historiaActionPerformed(evt);
            }
        });
        jPanel73.add(jcombo_hemorragias_historia, java.awt.BorderLayout.WEST);

        jScrollPane12.setViewportView(txt_hemorragias_historia);

        jPanel73.add(jScrollPane12, java.awt.BorderLayout.CENTER);

        jPanel55.add(jPanel73);

        jPanel61.add(jPanel55, java.awt.BorderLayout.CENTER);

        jPanel60.setLayout(new java.awt.BorderLayout(15, 0));

        jPanel58.setPreferredSize(new java.awt.Dimension(470, 50));
        jPanel58.setLayout(new java.awt.BorderLayout(10, 0));

        jPanel56.setLayout(new java.awt.BorderLayout(10, 2));

        LabelCliente_Nombre3.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Nombre3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Nombre3.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Nombre3.setText("Fecha");
        jPanel56.add(LabelCliente_Nombre3, java.awt.BorderLayout.NORTH);

        jadate_fecha_historia.setPreferredSize(new java.awt.Dimension(160, 29));
        jPanel56.add(jadate_fecha_historia, java.awt.BorderLayout.CENTER);

        jPanel58.add(jPanel56, java.awt.BorderLayout.WEST);

        jPanel57.setPreferredSize(new java.awt.Dimension(300, 70));
        jPanel57.setLayout(new java.awt.BorderLayout());

        LabelCliente_Dni9.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Dni9.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni9.setText("Médico");
        jPanel57.add(LabelCliente_Dni9, java.awt.BorderLayout.NORTH);

        combo_medico_historia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONAR" }));
        combo_medico_historia.setPreferredSize(new java.awt.Dimension(15, 26));
        combo_medico_historia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_medico_historiaActionPerformed(evt);
            }
        });
        jPanel57.add(combo_medico_historia, java.awt.BorderLayout.CENTER);

        jPanel58.add(jPanel57, java.awt.BorderLayout.CENTER);

        jPanel60.add(jPanel58, java.awt.BorderLayout.CENTER);

        jPanel74.setLayout(new java.awt.BorderLayout(0, 5));

        LabelCliente_Dni6.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Dni6.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni6.setText("Numero de Historia");
        jPanel74.add(LabelCliente_Dni6, java.awt.BorderLayout.NORTH);

        txt_numero_historia.setEditable(false);
        txt_numero_historia.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_numero_historia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_numero_historiaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_numero_historiaKeyTyped(evt);
            }
        });
        jPanel74.add(txt_numero_historia, java.awt.BorderLayout.CENTER);

        jPanel60.add(jPanel74, java.awt.BorderLayout.WEST);

        jPanel61.add(jPanel60, java.awt.BorderLayout.NORTH);

        jPanel75.setLayout(new java.awt.BorderLayout());

        LabelCliente_Email12.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Email12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Email12.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Email12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelCliente_Email12.setText("Diagnosticos");
        LabelCliente_Email12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel75.add(LabelCliente_Email12, java.awt.BorderLayout.CENTER);

        jScrollPane10.setPreferredSize(new java.awt.Dimension(40, 50));
        jScrollPane10.setViewportView(txt_diagnostico_historia);

        jPanel75.add(jScrollPane10, java.awt.BorderLayout.SOUTH);

        jPanel61.add(jPanel75, java.awt.BorderLayout.SOUTH);

        jPanel45.add(jPanel61, java.awt.BorderLayout.NORTH);

        jPanel63.setLayout(new java.awt.BorderLayout());

        jPanel47.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel47.setLayout(new java.awt.BorderLayout());

        jPanel71.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel69.setLayout(new java.awt.BorderLayout(0, 5));

        jdate_fechatratamiento_historia.setAlignmentX(1.0F);
        jdate_fechatratamiento_historia.setAlignmentY(1.0F);
        jdate_fechatratamiento_historia.setAutoscrolls(true);
        jdate_fechatratamiento_historia.setDateFormatString("dd-MM-yyyy");
        jdate_fechatratamiento_historia.setPreferredSize(new java.awt.Dimension(160, 29));
        jPanel69.add(jdate_fechatratamiento_historia, java.awt.BorderLayout.CENTER);

        LabelCliente_Direccion24.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion24.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Direccion24.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion24.setText("Fecha");
        jPanel69.add(LabelCliente_Direccion24, java.awt.BorderLayout.NORTH);

        jPanel71.add(jPanel69, java.awt.BorderLayout.WEST);

        jPanel70.setLayout(new java.awt.BorderLayout(0, 5));

        LabelCliente_Direccion23.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion23.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Direccion23.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion23.setText("Tratamiento");
        jPanel70.add(LabelCliente_Direccion23, java.awt.BorderLayout.NORTH);

        txt_tratamiento_historia.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_tratamiento_historia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_tratamiento_historia.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_tratamiento_historia.setPreferredSize(new java.awt.Dimension(150, 27));
        txt_tratamiento_historia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tratamiento_historiaActionPerformed(evt);
            }
        });
        txt_tratamiento_historia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_tratamiento_historiaKeyReleased(evt);
            }
        });
        jPanel70.add(txt_tratamiento_historia, java.awt.BorderLayout.CENTER);
        txt_tratamiento_historia.getAccessibleContext().setAccessibleName("");

        jPanel71.add(jPanel70, java.awt.BorderLayout.CENTER);

        jPanel68.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        jPanel66.setLayout(new java.awt.BorderLayout(0, 5));

        LabelCliente_Direccion25.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion25.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Direccion25.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion25.setText("Costo");
        jPanel66.add(LabelCliente_Direccion25, java.awt.BorderLayout.NORTH);

        txt_costo_tratamiento.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_costo_tratamiento.setPreferredSize(new java.awt.Dimension(30, 27));
        txt_costo_tratamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_costo_tratamientoActionPerformed(evt);
            }
        });
        txt_costo_tratamiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_costo_tratamientoKeyReleased(evt);
            }
        });
        jPanel66.add(txt_costo_tratamiento, java.awt.BorderLayout.CENTER);

        jPanel68.add(jPanel66);

        jPanel67.setLayout(new java.awt.BorderLayout(0, 5));

        LabelCliente_Direccion26.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion26.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Direccion26.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Direccion26.setText("Saldo");
        jPanel67.add(LabelCliente_Direccion26, java.awt.BorderLayout.NORTH);

        txt_saldo_tratamiento.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_saldo_tratamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_saldo_tratamientoActionPerformed(evt);
            }
        });
        txt_saldo_tratamiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_saldo_tratamientoKeyReleased(evt);
            }
        });
        jPanel67.add(txt_saldo_tratamiento, java.awt.BorderLayout.CENTER);

        jPanel68.add(jPanel67);

        jPanel71.add(jPanel68, java.awt.BorderLayout.EAST);

        LabelCliente_Email13.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Email13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Email13.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Email13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelCliente_Email13.setText("Registro de Tratamiento");
        LabelCliente_Email13.setPreferredSize(new java.awt.Dimension(169, 25));
        jPanel71.add(LabelCliente_Email13, java.awt.BorderLayout.NORTH);

        jPanel47.add(jPanel71, java.awt.BorderLayout.NORTH);

        jPanel72.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        jPanel47.add(jPanel72, java.awt.BorderLayout.SOUTH);

        jPanel63.add(jPanel47, java.awt.BorderLayout.CENTER);

        btn_agregar_historia.setBackground(new java.awt.Color(51, 51, 51));
        btn_agregar_historia.setForeground(new java.awt.Color(255, 255, 255));
        btn_agregar_historia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Agregar.png"))); // NOI18N
        btn_agregar_historia.setText("Agregar");
        btn_agregar_historia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_agregar_historia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregar_historiaActionPerformed(evt);
            }
        });
        jPanel62.add(btn_agregar_historia);

        btn_cancelar_historia.setBackground(new java.awt.Color(51, 51, 51));
        btn_cancelar_historia.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancelar_historia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Cancelar.png"))); // NOI18N
        btn_cancelar_historia.setText("Cancelar");
        btn_cancelar_historia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cancelar_historia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelar_historiaActionPerformed(evt);
            }
        });
        jPanel62.add(btn_cancelar_historia);

        btn_actualizar_historia.setBackground(new java.awt.Color(51, 51, 51));
        btn_actualizar_historia.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_actualizar_historia.setForeground(new java.awt.Color(255, 255, 255));
        btn_actualizar_historia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/actualizar.png"))); // NOI18N
        btn_actualizar_historia.setText("Actualizar");
        btn_actualizar_historia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_actualizar_historia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizar_historiaActionPerformed(evt);
            }
        });
        btn_actualizar_historia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_actualizar_historiaKeyReleased(evt);
            }
        });
        jPanel62.add(btn_actualizar_historia);

        jPanel63.add(jPanel62, java.awt.BorderLayout.SOUTH);

        jPanel45.add(jPanel63, java.awt.BorderLayout.CENTER);

        TabbedPaneHistoriaClinica2.addTab("HISTORIA CLINICA", jPanel45);

        PanelPrincipal2.add(TabbedPaneHistoriaClinica2, java.awt.BorderLayout.CENTER);

        jPanel14.add(PanelPrincipal2);

        jTabbedPane_Principal.addTab("Historia", jPanel14);

        jPanel46.setBackground(new java.awt.Color(255, 255, 255));

        jPanel51.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jTabbedPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        combo_tipodocumento_Pago.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        combo_tipodocumento_Pago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DOCUMENTO DE PAGO", "DOCUMENTO DE ABONO" }));
        combo_tipodocumento_Pago.setEditor(null);
        combo_tipodocumento_Pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_tipodocumento_PagoActionPerformed(evt);
            }
        });

        LabelCliente_Dni10.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Dni10.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni10.setText("R.U.C");

        txt_numeroserie_Pago.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_numeroserie_Pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numeroserie_PagoActionPerformed(evt);
            }
        });
        txt_numeroserie_Pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_numeroserie_PagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_numeroserie_PagoKeyTyped(evt);
            }
        });

        LabelCliente_Apellido11.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido11.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido11.setText("Dni");

        LabelCliente_Apellido13.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido13.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido13.setText("tipo documento");

        LabelCliente_Dni11.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Dni11.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni11.setText("Descripción");

        txt_nombrepaciente_pago.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_nombrepaciente_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombrepaciente_pagoActionPerformed(evt);
            }
        });
        txt_nombrepaciente_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_nombrepaciente_pagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombrepaciente_pagoKeyTyped(evt);
            }
        });

        txt_descripcion_producto_pago.setEditable(false);
        txt_descripcion_producto_pago.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_descripcion_producto_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_descripcion_producto_pagoActionPerformed(evt);
            }
        });
        txt_descripcion_producto_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_descripcion_producto_pagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_descripcion_producto_pagoKeyTyped(evt);
            }
        });

        btn_buscarPaciente_Pago.setBackground(new java.awt.Color(51, 51, 51));
        btn_buscarPaciente_Pago.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_buscarPaciente_Pago.setForeground(new java.awt.Color(255, 255, 255));
        btn_buscarPaciente_Pago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/buscar.png"))); // NOI18N
        btn_buscarPaciente_Pago.setText("Buscar");
        btn_buscarPaciente_Pago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_buscarPaciente_Pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarPaciente_PagoActionPerformed(evt);
            }
        });
        btn_buscarPaciente_Pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_buscarPaciente_PagoKeyReleased(evt);
            }
        });

        LabelCliente_Apellido14.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido14.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido14.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido14.setText("Costo");

        txt_cantidad_producto_pago.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_cantidad_producto_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cantidad_producto_pagoActionPerformed(evt);
            }
        });
        txt_cantidad_producto_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cantidad_producto_pagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_cantidad_producto_pagoKeyTyped(evt);
            }
        });

        LabelCliente_Dni5.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Dni5.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni5.setText("Cantidad");

        LabelCliente_Apellido15.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido15.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido15.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido15.setText("Fecha ");

        txt_serie_Pago.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_serie_Pago.setText("B001");
        txt_serie_Pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_serie_PagoActionPerformed(evt);
            }
        });
        txt_serie_Pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_serie_PagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_serie_PagoKeyTyped(evt);
            }
        });

        txt_ruc_pago.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_ruc_pago.setText("1046304312");
        txt_ruc_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ruc_pagoActionPerformed(evt);
            }
        });
        txt_ruc_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_ruc_pagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_ruc_pagoKeyTyped(evt);
            }
        });

        LabelCliente_Apellido16.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido16.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido16.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido16.setText("Serie");

        LabelCliente_Dni12.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Dni12.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni12.setText("Paciente");

        LabelCliente_Apellido17.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido17.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido17.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido17.setText("REGISTRO DE PAGO");

        txt_costo_producto_pago.setEditable(false);
        txt_costo_producto_pago.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_costo_producto_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_costo_producto_pagoActionPerformed(evt);
            }
        });
        txt_costo_producto_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_costo_producto_pagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_costo_producto_pagoKeyTyped(evt);
            }
        });

        LabelCliente_Dni13.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Dni13.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni13.setText("Código");

        txt_codigo_Producto_pago.setEditable(false);
        txt_codigo_Producto_pago.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_codigo_Producto_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_codigo_Producto_pagoActionPerformed(evt);
            }
        });
        txt_codigo_Producto_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_codigo_Producto_pagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_codigo_Producto_pagoKeyTyped(evt);
            }
        });

        LabelCliente_Apellido19.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido19.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido19.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido19.setText("Monto");

        txt_monto_pago.setEditable(false);
        txt_monto_pago.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_monto_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_monto_pagoActionPerformed(evt);
            }
        });
        txt_monto_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_monto_pagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_monto_pagoKeyTyped(evt);
            }
        });

        tabla_pago.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tabla_pago.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        jScrollPane15.setViewportView(tabla_pago);
        if (tabla_pago.getColumnModel().getColumnCount() > 0) {
            tabla_pago.getColumnModel().getColumn(0).setPreferredWidth(1);
            tabla_pago.getColumnModel().getColumn(1).setPreferredWidth(500);
            tabla_pago.getColumnModel().getColumn(3).setHeaderValue("Title 4");
            tabla_pago.getColumnModel().getColumn(4).setHeaderValue("Title 5");
            tabla_pago.getColumnModel().getColumn(5).setHeaderValue("Title 6");
            tabla_pago.getColumnModel().getColumn(6).setHeaderValue("Title 7");
            tabla_pago.getColumnModel().getColumn(7).setHeaderValue("Title 8");
        }

        btn_agregar_pago.setBackground(new java.awt.Color(51, 51, 51));
        btn_agregar_pago.setForeground(new java.awt.Color(255, 255, 255));
        btn_agregar_pago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Agregar.png"))); // NOI18N
        btn_agregar_pago.setText("Agregar");
        btn_agregar_pago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_agregar_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregar_pagoActionPerformed(evt);
            }
        });

        btn_registrar_pago.setBackground(new java.awt.Color(51, 51, 51));
        btn_registrar_pago.setForeground(new java.awt.Color(255, 255, 255));
        btn_registrar_pago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Agregar.png"))); // NOI18N
        btn_registrar_pago.setText("Registrar");
        btn_registrar_pago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_registrar_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrar_pagoActionPerformed(evt);
            }
        });

        btn_eliminar_pago.setBackground(new java.awt.Color(51, 51, 51));
        btn_eliminar_pago.setForeground(new java.awt.Color(255, 255, 255));
        btn_eliminar_pago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Cancelar.png"))); // NOI18N
        btn_eliminar_pago.setText("Eliminar");
        btn_eliminar_pago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_eliminar_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_pagoActionPerformed(evt);
            }
        });

        btn_editar_pago.setBackground(new java.awt.Color(51, 51, 51));
        btn_editar_pago.setForeground(new java.awt.Color(255, 255, 255));
        btn_editar_pago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/editar.png"))); // NOI18N
        btn_editar_pago.setText("Editar");
        btn_editar_pago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_editar_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editar_pagoActionPerformed(evt);
            }
        });

        btn_imprimir_pago.setBackground(new java.awt.Color(51, 51, 51));
        btn_imprimir_pago.setForeground(new java.awt.Color(255, 255, 255));
        btn_imprimir_pago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/editar.png"))); // NOI18N
        btn_imprimir_pago.setText("Imprimir");
        btn_imprimir_pago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_imprimir_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimir_pagoActionPerformed(evt);
            }
        });

        btn_cancelar_pago.setBackground(new java.awt.Color(51, 51, 51));
        btn_cancelar_pago.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancelar_pago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Cancelar.png"))); // NOI18N
        btn_cancelar_pago.setText("Cancelar");
        btn_cancelar_pago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cancelar_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelar_pagoActionPerformed(evt);
            }
        });

        txt_dniPaciente_Pago.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_dniPaciente_Pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dniPaciente_PagoActionPerformed(evt);
            }
        });
        txt_dniPaciente_Pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_dniPaciente_PagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dniPaciente_PagoKeyTyped(evt);
            }
        });

        txt_idcita_pago.setEditable(false);
        txt_idcita_pago.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_idcita_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idcita_pagoActionPerformed(evt);
            }
        });
        txt_idcita_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_idcita_pagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_idcita_pagoKeyTyped(evt);
            }
        });

        btn_buscarcita_pago.setBackground(new java.awt.Color(51, 51, 51));
        btn_buscarcita_pago.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_buscarcita_pago.setForeground(new java.awt.Color(255, 255, 255));
        btn_buscarcita_pago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/buscar.png"))); // NOI18N
        btn_buscarcita_pago.setText("Buscar");
        btn_buscarcita_pago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_buscarcita_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarcita_pagoActionPerformed(evt);
            }
        });
        btn_buscarcita_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_buscarcita_pagoKeyReleased(evt);
            }
        });

        LabelCliente_Apellido21.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido21.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido21.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido21.setText("Buscar Cita");

        txt_acuenta_pago.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_acuenta_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_acuenta_pagoActionPerformed(evt);
            }
        });
        txt_acuenta_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_acuenta_pagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_acuenta_pagoKeyTyped(evt);
            }
        });

        LabelCliente_Apellido18.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido18.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido18.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido18.setText("A cuenta");

        txt_saldo_pago.setEditable(false);
        txt_saldo_pago.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_saldo_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_saldo_pagoActionPerformed(evt);
            }
        });
        txt_saldo_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_saldo_pagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_saldo_pagoKeyTyped(evt);
            }
        });

        LabelCliente_Apellido20.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido20.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido20.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido20.setText("Saldo");

        btn_buscarProductoServicio_pago.setBackground(new java.awt.Color(51, 51, 51));
        btn_buscarProductoServicio_pago.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_buscarProductoServicio_pago.setForeground(new java.awt.Color(255, 255, 255));
        btn_buscarProductoServicio_pago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/buscar.png"))); // NOI18N
        btn_buscarProductoServicio_pago.setText("Buscar");
        btn_buscarProductoServicio_pago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_buscarProductoServicio_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarProductoServicio_pagoActionPerformed(evt);
            }
        });
        btn_buscarProductoServicio_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_buscarProductoServicio_pagoKeyReleased(evt);
            }
        });

        txt_total_pago.setEditable(false);
        txt_total_pago.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_total_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_total_pagoActionPerformed(evt);
            }
        });
        txt_total_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_total_pagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_total_pagoKeyTyped(evt);
            }
        });

        LabelCliente_Apellido22.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido22.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido22.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido22.setText("Total");

        LabelCliente_Apellido23.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido23.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido23.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido23.setText("Descuento");

        txt_descuento_pago.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_descuento_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_descuento_pagoActionPerformed(evt);
            }
        });
        txt_descuento_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_descuento_pagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_descuento_pagoKeyTyped(evt);
            }
        });

        txt_subtotal_pago.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_subtotal_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_subtotal_pagoActionPerformed(evt);
            }
        });
        txt_subtotal_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_subtotal_pagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_subtotal_pagoKeyTyped(evt);
            }
        });

        LabelCliente_Apellido24.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido24.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido24.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido24.setText("sub total");

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel48Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel48Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jLabel7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel48Layout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addComponent(LabelCliente_Apellido17)
                        .addGap(121, 121, 121)))
                .addGap(329, 329, 329))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel48Layout.createSequentialGroup()
                .addGap(131, 149, Short.MAX_VALUE)
                .addComponent(btn_registrar_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_imprimir_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_editar_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_eliminar_pago)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_cancelar_pago)
                .addGap(120, 120, 120))
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel48Layout.createSequentialGroup()
                        .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel48Layout.createSequentialGroup()
                                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel48Layout.createSequentialGroup()
                                            .addComponent(txt_idcita_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btn_buscarcita_pago))
                                        .addComponent(LabelCliente_Apellido21))
                                    .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel48Layout.createSequentialGroup()
                                            .addComponent(txt_dniPaciente_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btn_buscarPaciente_Pago))
                                        .addComponent(LabelCliente_Apellido11)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel48Layout.createSequentialGroup()
                                        .addComponent(LabelCliente_Dni12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(LabelCliente_Apellido16)
                                            .addComponent(LabelCliente_Apellido13)
                                            .addComponent(LabelCliente_Dni10)
                                            .addComponent(LabelCliente_Apellido15))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_ruc_pago)
                                            .addGroup(jPanel48Layout.createSequentialGroup()
                                                .addComponent(txt_serie_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txt_numeroserie_Pago))
                                            .addComponent(jdate_fecha_pago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(combo_tipodocumento_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(txt_nombrepaciente_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel48Layout.createSequentialGroup()
                                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel48Layout.createSequentialGroup()
                                        .addComponent(txt_codigo_Producto_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_buscarProductoServicio_pago))
                                    .addComponent(LabelCliente_Dni13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelCliente_Dni11)
                                    .addComponent(txt_descripcion_producto_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel48Layout.createSequentialGroup()
                        .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel48Layout.createSequentialGroup()
                                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel48Layout.createSequentialGroup()
                                        .addComponent(LabelCliente_Dni5)
                                        .addGap(38, 38, 38)
                                        .addComponent(LabelCliente_Apellido14)
                                        .addGap(53, 53, 53)
                                        .addComponent(LabelCliente_Apellido18)
                                        .addGap(42, 42, 42)
                                        .addComponent(LabelCliente_Apellido20))
                                    .addGroup(jPanel48Layout.createSequentialGroup()
                                        .addComponent(txt_cantidad_producto_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_costo_producto_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_acuenta_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_saldo_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel48Layout.createSequentialGroup()
                                        .addComponent(LabelCliente_Apellido19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 320, Short.MAX_VALUE))
                                    .addGroup(jPanel48Layout.createSequentialGroup()
                                        .addComponent(txt_monto_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn_agregar_pago))))
                            .addGroup(jPanel48Layout.createSequentialGroup()
                                .addGap(0, 615, Short.MAX_VALUE)
                                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel48Layout.createSequentialGroup()
                                        .addComponent(LabelCliente_Apellido22)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_total_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel48Layout.createSequentialGroup()
                                        .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel48Layout.createSequentialGroup()
                                                .addComponent(LabelCliente_Apellido24)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txt_subtotal_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel48Layout.createSequentialGroup()
                                                .addComponent(LabelCliente_Apellido23)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txt_descuento_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(6, 6, 6)))))
                        .addGap(48, 48, 48))))
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel48Layout.createSequentialGroup()
                        .addComponent(LabelCliente_Apellido17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LabelCliente_Apellido21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_buscarcita_pago)
                            .addComponent(txt_idcita_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addGap(9, 9, 9)
                        .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelCliente_Apellido11)
                            .addComponent(LabelCliente_Dni12)))
                    .addGroup(jPanel48Layout.createSequentialGroup()
                        .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelCliente_Dni10)
                            .addComponent(txt_ruc_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(combo_tipodocumento_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelCliente_Apellido13))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_serie_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_numeroserie_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelCliente_Apellido16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jdate_fecha_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel48Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(LabelCliente_Apellido15)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_buscarPaciente_Pago)
                    .addComponent(txt_dniPaciente_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nombrepaciente_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelCliente_Dni11)
                    .addComponent(LabelCliente_Dni13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_descripcion_producto_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_codigo_Producto_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_buscarProductoServicio_pago))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LabelCliente_Dni5)
                        .addComponent(LabelCliente_Apellido14)
                        .addComponent(LabelCliente_Apellido18)
                        .addComponent(LabelCliente_Apellido20))
                    .addComponent(LabelCliente_Apellido19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cantidad_producto_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_costo_producto_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_acuenta_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_saldo_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_monto_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_agregar_pago))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_subtotal_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelCliente_Apellido24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelCliente_Apellido23)
                    .addComponent(txt_descuento_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_total_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelCliente_Apellido22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_registrar_pago)
                    .addComponent(btn_eliminar_pago)
                    .addComponent(btn_imprimir_pago)
                    .addComponent(btn_cancelar_pago)
                    .addComponent(btn_editar_pago))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Pagos", jPanel48);

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel52.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jTabbedPane12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        LabelCliente_Dni7.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Dni7.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni7.setText("Ingrese DNI");

        txt_dni_listadoPago.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_dni_listadoPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_dni_listadoPagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dni_listadoPagoKeyTyped(evt);
            }
        });

        btn_buscar_pagoListado.setText("Buscar");
        btn_buscar_pagoListado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_pagoListadoActionPerformed(evt);
            }
        });

        tabla_listadoPago.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tabla_listadoPago.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        jScrollPane13.setViewportView(tabla_listadoPago);
        if (tabla_listadoPago.getColumnModel().getColumnCount() > 0) {
            tabla_listadoPago.getColumnModel().getColumn(0).setPreferredWidth(1);
            tabla_listadoPago.getColumnModel().getColumn(1).setPreferredWidth(500);
        }

        LabelCliente_Nombre5.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Nombre5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Nombre5.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Nombre5.setText("Listado de pagos");

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(70, 106, 124));
        jLabel18.setText("Fecha inicio");

        jdate_fechainicio_listadoPago.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        jdate_fechafin_listadopago.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(70, 106, 124));
        jLabel24.setText("Fecha Fin");

        btn_sincronizar_listadopago.setBackground(new java.awt.Color(51, 51, 51));
        btn_sincronizar_listadopago.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_sincronizar_listadopago.setForeground(new java.awt.Color(255, 255, 255));
        btn_sincronizar_listadopago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/actualizar.png"))); // NOI18N
        btn_sincronizar_listadopago.setText("Sincronizar");
        btn_sincronizar_listadopago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_sincronizar_listadopago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sincronizar_listadopagoActionPerformed(evt);
            }
        });
        btn_sincronizar_listadopago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_sincronizar_listadopagoKeyReleased(evt);
            }
        });

        btn_exportarExcel_listadoPago.setBackground(new java.awt.Color(51, 51, 51));
        btn_exportarExcel_listadoPago.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_exportarExcel_listadoPago.setForeground(new java.awt.Color(255, 255, 255));
        btn_exportarExcel_listadoPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/actualizar.png"))); // NOI18N
        btn_exportarExcel_listadoPago.setText("Exportar Excel");
        btn_exportarExcel_listadoPago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_exportarExcel_listadoPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exportarExcel_listadoPagoActionPerformed(evt);
            }
        });
        btn_exportarExcel_listadoPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_exportarExcel_listadoPagoKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addGap(335, 335, 335)
                        .addComponent(LabelCliente_Nombre5))
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 828, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel49Layout.createSequentialGroup()
                                .addComponent(LabelCliente_Dni7)
                                .addGap(45, 45, 45)
                                .addComponent(txt_dni_listadoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_buscar_pagoListado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_exportarExcel_listadoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jdate_fechainicio_listadoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jdate_fechafin_listadopago, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_sincronizar_listadopago)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addComponent(LabelCliente_Nombre5)
                        .addGap(19, 19, 19)
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18)
                            .addComponent(jdate_fechainicio_listadoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jdate_fechafin_listadopago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)))
                    .addComponent(btn_sincronizar_listadopago))
                .addGap(41, 41, 41)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_buscar_pagoListado)
                    .addComponent(txt_dni_listadoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelCliente_Dni7)
                    .addComponent(btn_exportarExcel_listadoPago))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(578, 578, 578))
        );

        jTabbedPane12.addTab("Listado de Pagos", jPanel49);

        LabelCliente_Nombre6.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Nombre6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Nombre6.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Nombre6.setText("Listado de abonos del Paciente");

        jLabel25.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(70, 106, 124));
        jLabel25.setText("Fecha inicio");

        jdate_fechainicio_listadoAbono.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(70, 106, 124));
        jLabel26.setText("Fecha Fin");

        jdate_fechafin_listadoAbono.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        btn_sincronizar_listadoAbono.setBackground(new java.awt.Color(51, 51, 51));
        btn_sincronizar_listadoAbono.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_sincronizar_listadoAbono.setForeground(new java.awt.Color(255, 255, 255));
        btn_sincronizar_listadoAbono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/actualizar.png"))); // NOI18N
        btn_sincronizar_listadoAbono.setText("Sincronizar");
        btn_sincronizar_listadoAbono.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_sincronizar_listadoAbono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sincronizar_listadoAbonoActionPerformed(evt);
            }
        });
        btn_sincronizar_listadoAbono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_sincronizar_listadoAbonoKeyReleased(evt);
            }
        });

        LabelCliente_Dni14.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni14.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Dni14.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni14.setText("Dni");

        txt_dni_listadoAbono.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_dni_listadoAbono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_dni_listadoAbonoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dni_listadoAbonoKeyTyped(evt);
            }
        });

        btn_buscarPaciente_listadoAbono.setText("Buscar");
        btn_buscarPaciente_listadoAbono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarPaciente_listadoAbonoActionPerformed(evt);
            }
        });

        tabla_listadoAbono.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tabla_listadoAbono.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        jScrollPane16.setViewportView(tabla_listadoAbono);
        if (tabla_listadoAbono.getColumnModel().getColumnCount() > 0) {
            tabla_listadoAbono.getColumnModel().getColumn(0).setPreferredWidth(1);
            tabla_listadoAbono.getColumnModel().getColumn(1).setPreferredWidth(500);
            tabla_listadoAbono.getColumnModel().getColumn(3).setHeaderValue("Title 4");
            tabla_listadoAbono.getColumnModel().getColumn(4).setHeaderValue("Title 5");
            tabla_listadoAbono.getColumnModel().getColumn(5).setHeaderValue("Title 6");
            tabla_listadoAbono.getColumnModel().getColumn(6).setHeaderValue("Title 7");
            tabla_listadoAbono.getColumnModel().getColumn(7).setHeaderValue("Title 8");
        }

        btn_exportarExcel_listadoAbono.setBackground(new java.awt.Color(51, 51, 51));
        btn_exportarExcel_listadoAbono.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_exportarExcel_listadoAbono.setForeground(new java.awt.Color(255, 255, 255));
        btn_exportarExcel_listadoAbono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/actualizar.png"))); // NOI18N
        btn_exportarExcel_listadoAbono.setText("Exportar Excel");
        btn_exportarExcel_listadoAbono.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_exportarExcel_listadoAbono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exportarExcel_listadoAbonoActionPerformed(evt);
            }
        });
        btn_exportarExcel_listadoAbono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_exportarExcel_listadoAbonoKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel59Layout.createSequentialGroup()
                        .addGap(329, 329, 329)
                        .addComponent(LabelCliente_Nombre6))
                    .addGroup(jPanel59Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel59Layout.createSequentialGroup()
                                .addComponent(LabelCliente_Dni14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_dni_listadoAbono, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_buscarPaciente_listadoAbono))
                            .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 828, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel59Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_exportarExcel_listadoAbono, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel59Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jdate_fechainicio_listadoAbono, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jdate_fechafin_listadoAbono, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(btn_sincronizar_listadoAbono)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(LabelCliente_Nombre6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel25)
                    .addComponent(jdate_fechainicio_listadoAbono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdate_fechafin_listadoAbono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(btn_sincronizar_listadoAbono))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_buscarPaciente_listadoAbono)
                        .addComponent(txt_dni_listadoAbono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(LabelCliente_Dni14))
                    .addComponent(btn_exportarExcel_listadoAbono, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jTabbedPane12.addTab("Listado de Abonos", jPanel59);

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane12)
                .addContainerGap())
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane12.getAccessibleContext().setAccessibleName("Pagos");

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(397, Short.MAX_VALUE))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane_Principal.addTab("Pago", jPanel46);

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.setLayout(new java.awt.BorderLayout());

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(70, 106, 124));
        jLabel21.setText("Citas");

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(70, 106, 124));
        jLabel23.setText("Ingresa el Numero de Dni");

        txt_Buscar_cita.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        btn_buscar_Cita.setBackground(new java.awt.Color(51, 51, 51));
        btn_buscar_Cita.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_buscar_Cita.setForeground(new java.awt.Color(255, 255, 255));
        btn_buscar_Cita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/buscar.png"))); // NOI18N
        btn_buscar_Cita.setText("Buscar");
        btn_buscar_Cita.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_buscar_Cita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_CitaActionPerformed(evt);
            }
        });
        btn_buscar_Cita.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_buscar_CitaKeyReleased(evt);
            }
        });

        btn_actualizar_cita.setBackground(new java.awt.Color(51, 51, 51));
        btn_actualizar_cita.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_actualizar_cita.setForeground(new java.awt.Color(255, 255, 255));
        btn_actualizar_cita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/actualizar.png"))); // NOI18N
        btn_actualizar_cita.setText("Actualizar");
        btn_actualizar_cita.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_actualizar_cita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizar_citaActionPerformed(evt);
            }
        });
        btn_actualizar_cita.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_actualizar_citaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 764, Short.MAX_VALUE)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_Buscar_cita, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btn_buscar_Cita, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btn_actualizar_cita)
                .addGap(244, 244, 244))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_Buscar_cita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel23)
                        .addComponent(btn_buscar_Cita)
                        .addComponent(btn_actualizar_cita))
                    .addComponent(jLabel21))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel34.add(jPanel35, java.awt.BorderLayout.NORTH);

        jPanel36.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel36.setLayout(new java.awt.BorderLayout());

        jpanel_cita.setBackground(new java.awt.Color(255, 255, 255));
        jpanel_cita.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        LabelCliente_Dni4.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Dni4.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Dni4.setText("Dni");

        txt_dnipaciente_cita.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_dnipaciente_cita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dnipaciente_citaActionPerformed(evt);
            }
        });
        txt_dnipaciente_cita.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_dnipaciente_citaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dnipaciente_citaKeyTyped(evt);
            }
        });

        LabelCliente_Apellido4.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido4.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido4.setText("Nombre del paciente ");

        txt_nombreCompleto_cita.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_nombreCompleto_cita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombreCompleto_citaActionPerformed(evt);
            }
        });
        txt_nombreCompleto_cita.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_nombreCompleto_citaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombreCompleto_citaKeyTyped(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(70, 106, 124));
        jLabel22.setText("REGISTRAR CITA");

        LabelCliente_Apellido5.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido5.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido5.setText("Fecha ");

        LabelCliente_Apellido6.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido6.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido6.setText("Tipo de consulta");

        LabelCliente_Apellido7.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido7.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido7.setText("Hora ");

        txt_precio_cita.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_precio_cita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_precio_citaActionPerformed(evt);
            }
        });
        txt_precio_cita.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_precio_citaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_precio_citaKeyTyped(evt);
            }
        });

        LabelCliente_Apellido8.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido8.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido8.setText("Precio");

        LabelCliente_Apellido9.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido9.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido9.setText("Médico");

        LabelCliente_Apellido10.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido10.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido10.setText("Observaciones");

        txt_observaciones_cita.setFont(new java.awt.Font("Dialog", 0, 17)); // NOI18N
        jScrollPane7.setViewportView(txt_observaciones_cita);

        Labelimagen_Cliente2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Labelimagen_Cliente2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/cita (1).png"))); // NOI18N
        Labelimagen_Cliente2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Labelimagen_Cliente2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Labelimagen_Cliente2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Labelimagen_Cliente2MouseClicked(evt);
            }
        });

        LabelCliente_Apellido12.setBackground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        LabelCliente_Apellido12.setForeground(new java.awt.Color(70, 106, 124));
        LabelCliente_Apellido12.setText("Consultorio");

        btn_buscar_cita.setBackground(new java.awt.Color(51, 51, 51));
        btn_buscar_cita.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        btn_buscar_cita.setForeground(new java.awt.Color(255, 255, 255));
        btn_buscar_cita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/buscar.png"))); // NOI18N
        btn_buscar_cita.setText("Buscar");
        btn_buscar_cita.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_buscar_cita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_citaActionPerformed(evt);
            }
        });
        btn_buscar_cita.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btn_buscar_citaKeyReleased(evt);
            }
        });

        combo_medico_cita.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        combo_tipoconsulta_cita.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        combo_tipoconsulta_cita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_tipoconsulta_citaActionPerformed(evt);
            }
        });

        combo_consultorio_cita.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        btn_agregar_cita.setBackground(new java.awt.Color(51, 51, 51));
        btn_agregar_cita.setForeground(new java.awt.Color(255, 255, 255));
        btn_agregar_cita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Agregar.png"))); // NOI18N
        btn_agregar_cita.setText("Agregar");
        btn_agregar_cita.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_agregar_cita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregar_citaActionPerformed(evt);
            }
        });

        btn_guardar_cita.setBackground(new java.awt.Color(51, 51, 51));
        btn_guardar_cita.setForeground(new java.awt.Color(255, 255, 255));
        btn_guardar_cita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/guardar.png"))); // NOI18N
        btn_guardar_cita.setText("Guardar");
        btn_guardar_cita.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_guardar_cita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardar_citaActionPerformed(evt);
            }
        });

        combo_hora_cita.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        combo_hora_cita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00", "19:30:00", "20:00:00", "20:30:00", "21:00:00", "21:30:00", "22:00:00", "22:30:00", "23:00:00" }));

        btn_cancelar_cita.setBackground(new java.awt.Color(51, 51, 51));
        btn_cancelar_cita.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancelar_cita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Cancelar.png"))); // NOI18N
        btn_cancelar_cita.setText("Cancelar");
        btn_cancelar_cita.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cancelar_cita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelar_citaActionPerformed(evt);
            }
        });

        txt_idcita_cita.setForeground(new java.awt.Color(255, 255, 255));
        txt_idcita_cita.setBorder(null);
        txt_idcita_cita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idcita_citaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpanel_citaLayout = new javax.swing.GroupLayout(jpanel_cita);
        jpanel_cita.setLayout(jpanel_citaLayout);
        jpanel_citaLayout.setHorizontalGroup(
            jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel_citaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanel_citaLayout.createSequentialGroup()
                        .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelCliente_Apellido4)
                            .addComponent(txt_nombreCompleto_cita, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanel_citaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelCliente_Apellido10)
                            .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jpanel_citaLayout.createSequentialGroup()
                                    .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(combo_medico_cita, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jpanel_citaLayout.createSequentialGroup()
                                            .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(LabelCliente_Apellido6)
                                                .addComponent(combo_tipoconsulta_cita, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(LabelCliente_Apellido9)
                                                .addGroup(jpanel_citaLayout.createSequentialGroup()
                                                    .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(jpanel_citaLayout.createSequentialGroup()
                                                            .addComponent(LabelCliente_Apellido5)
                                                            .addGap(109, 109, 109))
                                                        .addGroup(jpanel_citaLayout.createSequentialGroup()
                                                            .addComponent(jdate_fecha_cita, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                                    .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(LabelCliente_Apellido7)
                                                        .addComponent(combo_hora_cita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                            .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(LabelCliente_Apellido12)
                                                .addComponent(LabelCliente_Apellido8)
                                                .addComponent(txt_precio_cita, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(combo_consultorio_cita, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGap(11, 11, 11))))
                        .addGap(98, 98, 98))
                    .addGroup(jpanel_citaLayout.createSequentialGroup()
                        .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelCliente_Dni4)
                            .addGroup(jpanel_citaLayout.createSequentialGroup()
                                .addComponent(txt_dnipaciente_cita, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_buscar_cita))
                            .addGroup(jpanel_citaLayout.createSequentialGroup()
                                .addComponent(txt_idcita_cita)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel22)
                                .addGap(40, 40, 40)))
                        .addComponent(Labelimagen_Cliente2)
                        .addGap(92, 92, 92))))
            .addGroup(jpanel_citaLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(btn_agregar_cita, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btn_guardar_cita)
                .addGap(18, 18, 18)
                .addComponent(btn_cancelar_cita)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jpanel_citaLayout.setVerticalGroup(
            jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel_citaLayout.createSequentialGroup()
                .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanel_citaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txt_idcita_cita, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelCliente_Dni4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_dnipaciente_cita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_buscar_cita)))
                    .addComponent(Labelimagen_Cliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelCliente_Apellido4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_nombreCompleto_cita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LabelCliente_Apellido7)
                        .addComponent(LabelCliente_Apellido12))
                    .addComponent(LabelCliente_Apellido5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(combo_consultorio_cita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(combo_hora_cita))
                    .addComponent(jdate_fecha_cita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelCliente_Apellido6)
                    .addComponent(LabelCliente_Apellido8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_tipoconsulta_cita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_precio_cita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(LabelCliente_Apellido9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combo_medico_cita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelCliente_Apellido10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jpanel_citaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_agregar_cita)
                    .addComponent(btn_guardar_cita)
                    .addComponent(btn_cancelar_cita))
                .addContainerGap(369, Short.MAX_VALUE))
        );

        jTabbedPane9.addTab("Cita", jpanel_cita);

        jPanel36.add(jTabbedPane9, java.awt.BorderLayout.CENTER);

        jPanel34.add(jPanel36, java.awt.BorderLayout.WEST);

        jPanel39.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));
        jPanel42.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tablaListadoCita.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tablaListadoCita.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        tablaListadoCita.setComponentPopupMenu(popupMenuCita);
        jScrollPane6.setViewportView(tablaListadoCita);

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1639, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 902, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane10.addTab("Listado de citas", jPanel40);

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane10)
                .addContainerGap())
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane10)
                .addContainerGap())
        );

        jPanel34.add(jPanel39, java.awt.BorderLayout.CENTER);

        jTabbedPane_Principal.addTab("Cita", jPanel34);

        getContentPane().add(jTabbedPane_Principal, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
 // <editor-fold defaultstate="collapsed" desc="CODIGO DE PACIENTE">
    private pacientesVm paciente;


    private void btn_PacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PacienteActionPerformed
        ArrayList<JLabel> label = new ArrayList();
        label.add(LabelPaciente_Dni);
        label.add(LabelPaciente_Apellido);
        label.add(LabelPaciente_Nombre);
        // label.add(LabelPaciente_FechaNacimiento);
        // label.add(LabelPaciente_Genero);
        label.add(LabelPaciente_Telefono);
        label.add(LabelPaciente_Ocupacion);
        label.add(LabelPaciente_Departamento);
        label.add(LabelPaciente_Provincia);
        label.add(LabelPaciente_Distrito);
        label.add(LabelPaciente_Direccion);
        label.add(LabelPaciente_Email);

        jTabbedPane_Principal.setSelectedIndex(0);
        ArrayList<JTextField> textField = new ArrayList();

        textField.add(txt_dni_paciente);
        textField.add(txt_apellidos_paciente);
        textField.add(txt_nombres_paciente);
        textField.add(txt_telefono_paciente);
        textField.add(txt_ocupacion_paciente);
        textField.add(txt_departamento_paciente);
        textField.add(txt_provincia_paciente);
        textField.add(txt_distrito_paciente);
        textField.add(txt_direccion_paciente);
        textField.add(txt_email_paciente);

        Object[] objects = {};
        paciente = new pacientesVm(objects, label, textField);

        jTabbedPane_Principal.setSelectedIndex(0);

    }//GEN-LAST:event_btn_PacienteActionPerformed

    private void txt_email_pacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_email_pacienteKeyReleased
        if (txt_email_paciente.getText().equals("")) {
            LabelPaciente_Email.setForeground(new Color(102, 102, 102));
        } else {
            LabelPaciente_Email.setText("Email");
            LabelPaciente_Email.setForeground(new Color(0, 153, 51));

        }
    }//GEN-LAST:event_txt_email_pacienteKeyReleased

    private void txt_direccion_pacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_direccion_pacienteKeyReleased
        if (txt_direccion_paciente.getText().equals("")) {
            LabelPaciente_Direccion.setForeground(new Color(102, 102, 102));
        } else {
            LabelPaciente_Direccion.setText("Dirección");
            LabelPaciente_Direccion.setForeground(new Color(0, 153, 51));

        }
    }//GEN-LAST:event_txt_direccion_pacienteKeyReleased

    private void txt_distrito_pacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_distrito_pacienteKeyReleased
        if (txt_distrito_paciente.getText().equals("")) {
            LabelPaciente_Distrito.setForeground(new Color(102, 102, 102));
        } else {
            LabelPaciente_Distrito.setText("Distrito");
            LabelPaciente_Distrito.setForeground(new Color(0, 153, 51));

        }

    }//GEN-LAST:event_txt_distrito_pacienteKeyReleased

    private void txt_departamento_pacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_departamento_pacienteKeyReleased
        if (txt_departamento_paciente.getText().equals("")) {
            LabelPaciente_Departamento.setForeground(new Color(102, 102, 102));
        } else {
            LabelPaciente_Departamento.setText("Departamento");
            LabelPaciente_Departamento.setForeground(new Color(0, 153, 51));

        }
    }//GEN-LAST:event_txt_departamento_pacienteKeyReleased

    private void txt_provincia_pacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_provincia_pacienteKeyReleased
        if (txt_provincia_paciente.getText().equals("")) {
            LabelPaciente_Provincia.setForeground(new Color(102, 102, 102));
        } else {
            LabelPaciente_Provincia.setText("Provincia");
            LabelPaciente_Provincia.setForeground(new Color(0, 153, 51));

        }
    }//GEN-LAST:event_txt_provincia_pacienteKeyReleased

    private void txt_ocupacion_pacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ocupacion_pacienteKeyReleased
        if (txt_ocupacion_paciente.getText().equals("")) {
            LabelPaciente_Ocupacion.setForeground(new Color(102, 102, 102));
        } else {
            LabelPaciente_Ocupacion.setText("Ocupación");
            LabelPaciente_Ocupacion.setForeground(new Color(0, 153, 51));

        }
    }//GEN-LAST:event_txt_ocupacion_pacienteKeyReleased

    private void txt_apellidos_pacienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_apellidos_pacienteKeyTyped
        Objectos.eventos.solomayusculas(evt);
        Objectos.eventos.textKeyPress(evt);
    }//GEN-LAST:event_txt_apellidos_pacienteKeyTyped

    private void txt_apellidos_pacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_apellidos_pacienteKeyReleased
        if (txt_apellidos_paciente.getText().equals("")) {
            LabelPaciente_Apellido.setForeground(new Color(102, 102, 102));
        } else {
            LabelPaciente_Apellido.setText("Apellidos");
            LabelPaciente_Apellido.setForeground(new Color(0, 153, 51));

        }
    }//GEN-LAST:event_txt_apellidos_pacienteKeyReleased

    public void RegistrarPaciente() {
        //      paciente.ValidadorRegistrarpaciente();
        paciente dts = new paciente();
        inserPaciente func = new inserPaciente();

        SimpleDateFormat simpleDF = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = simpleDF.format(Jdate_fechanaci_paciente.getDate());

        //System.out.println("Jdate_fechanaci_paciente.getDateFormatString()");
        // System.out.println(Jdate_fechanaci_paciente.getDateFormatString());
        dts.setIdpaciente(txt_dni_paciente.getText());
        dts.setApellidos(txt_apellidos_paciente.getText());
        dts.setNombres(txt_nombres_paciente.getText());
        dts.setFechanacimiento(fecha);
        int seleccionado = comb_genero_paciente.getSelectedIndex();
        dts.setGenero(comb_genero_paciente.getItemAt(seleccionado));
        dts.setTelefono(txt_telefono_paciente.getText());
        dts.setOcupacion(txt_ocupacion_paciente.getText());
        dts.setDepartamento(txt_departamento_paciente.getText());
        dts.setProvincia(txt_provincia_paciente.getText());
        dts.setDistrito(txt_distrito_paciente.getText());
        dts.setDireccion(txt_direccion_paciente.getText());
        dts.setCorreo(txt_email_paciente.getText());
        dts.setFoto(Labelimagen_Paciente.getText());
        func.insertarPaciente(dts);

        limpiar();
//        paciente.restablecer();
//        JOptionPane.showMessageDialog(null, "EL PACIENTE SE REGISTRO CON EXITO..!!");
        mostrarTablaListadoPaciente();
    }

    public void RegistrarMedico() {
        //      paciente.ValidadorRegistrarpaciente();
        medico dts = new medico();
        logicaMedico func = new logicaMedico();

        SimpleDateFormat simpleDF = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = simpleDF.format(jdate_fechanacimiento_medico.getDate());

        dts.setIdmedico(txt_Dni_medico.getText());
        dts.setApellidos(txt_Apellidos_medico.getText());
        dts.setNombres(txt_Nombres_medico.getText());
        dts.setFechanacimiento(fecha);
        int seleccionado = comb_genero_medico.getSelectedIndex();
        dts.setGenero(comb_genero_medico.getItemAt(seleccionado));
        dts.setTelefono(txt_telefono_medico.getText());
        dts.setDireccion(txt_direccion_medico.getText());
        dts.setCorreo(txt_email_medico.getText());
        dts.setFoto(label_imagen_medico.getText());
        int seleccionado1 = combo_especialidad_medico.getSelectedIndex();
        dts.setDescripcion(combo_especialidad_medico.getItemAt(seleccionado1));

        func.insertarMedico(dts);

        limpiarMedico();
//         restablecer();
        JOptionPane.showMessageDialog(null, "EL MEDICO SE REGISTRO CON EXITO..!!");
        mostrarTablaListadoMedico();
    }

    public void RegistrarCita() {

        cita dts = new cita();
        logicaCita func = new logicaCita();

        SimpleDateFormat simpleDF = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = simpleDF.format(jdate_fecha_cita.getDate());

        dts.setIdpaciente(txt_dnipaciente_cita.getText());
        dts.setFechacita(fecha);
        int seleccionado1 = combo_hora_cita.getSelectedIndex();
        dts.setHora(combo_hora_cita.getItemAt(seleccionado1));
        int seleccionado2 = combo_consultorio_cita.getSelectedIndex();
        dts.setConsultorio(combo_consultorio_cita.getItemAt(seleccionado2));
        int seleccionado3 = combo_tipoconsulta_cita.getSelectedIndex();
        dts.setTipoconsulta(combo_tipoconsulta_cita.getItemAt(seleccionado3));
        int seleccionado4 = combo_medico_cita.getSelectedIndex();
        dts.setIdmedico(combo_medico_cita.getItemAt(seleccionado4));
        dts.setObservaciones(txt_observaciones_cita.getText());

        func.insertarCita(dts);

        limpiarCita();
//         restablecer();
        //JOptionPane.showMessageDialog(null, "LA CITA SE REGISTRO CON EXITO..!!");
        mostrarTablaListadoCita();
    }

//    public void RegistrarDetallePago() {
//
//        pago dts = new pago();
//        logicaPago func = new logicaPago();
//
//        try {
//
//            for (int i = 0; i < tabla_pago.getRowCount(); i++) {
//
//                dts.setIdtipoconsulta(tabla_pago.getValueAt(i, 0).toString());
//                dts.setDescripcion(tabla_pago.getValueAt(i, 1).toString());
//                dts.setCantidad(tabla_pago.getValueAt(i, 2).toString());
//                dts.setCosto(tabla_pago.getValueAt(i, 3).toString());
//                dts.setAcuenta(tabla_pago.getValueAt(i, 4).toString());
//                dts.setSaldo(tabla_pago.getValueAt(i, 5).toString());
//                dts.setMonto(tabla_pago.getValueAt(i, 6).toString());
//
//            }
//        } catch (Exception e) {
//            System.out.print(e.getMessage());
//        }
//
////        dts.setSubtotal(txt_subtotal_pago.getText());
////        dts.setDescuento(txt_descuento_pago.getText());
////        dts.setTotal(txt_total_pago.getText());
//        func.insertDetallePago(dts);
//        System.out.println(dts);
//
//    }
    public void RegistrarPago() {
        logicaPago func = new logicaPago();
        pago dts = new pago();

        JSONArray jarray = new JSONArray();

        SimpleDateFormat simpleDF = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = simpleDF.format(jdate_fecha_pago.getDate());

        dts.setRuc(txt_ruc_pago.getText());
        int seleccionado1 = combo_tipodocumento_Pago.getSelectedIndex();
        dts.setTipodocumento(combo_tipodocumento_Pago.getItemAt(seleccionado1));
        dts.setSerie(txt_serie_Pago.getText());
        dts.setNumeroserie(txt_numeroserie_Pago.getText());
        dts.setFecha(fecha);
        dts.setIdcita(txt_idcita_pago.getText());
        dts.setIdpaciente(txt_dniPaciente_Pago.getText());
        dts.setNombre(txt_nombrepaciente_pago.getText());
        try {

            for (int i = 0; i < tabla_pago.getRowCount(); i++) {
                JSONObject pago = new JSONObject();

                pago.put("idtipoconsulta", tabla_pago.getValueAt(i, 0).toString());
                pago.put("descripcion", tabla_pago.getValueAt(i, 1).toString());
                pago.put("cantidad", tabla_pago.getValueAt(i, 2).toString());
                pago.put("costo", tabla_pago.getValueAt(i, 3).toString());
                pago.put("acuenta", tabla_pago.getValueAt(i, 4).toString());
                pago.put("saldo", tabla_pago.getValueAt(i, 5).toString());
                pago.put("monto", tabla_pago.getValueAt(i, 6).toString());

                System.out.println("for >> " + i);
                System.out.println("jsonpago >> " + pago);

                jarray.put(pago);

            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        dts.setSubtotal(txt_subtotal_pago.getText());
        dts.setDescuento(txt_descuento_pago.getText());
        dts.setTotal(txt_total_pago.getText());

        System.out.println("jarray >>" + jarray);
        func.insertarPago(dts, jarray);// SE ENVIA 2 PARAMETROS UNO DE TIPO PAGO Y OTRO DE TIPO JSON
        System.out.println("dts >>" + dts);

    }

    public void RegistrarEspecialidad() {

        especialidad dts = new especialidad();
        logicaEspecialidad func = new logicaEspecialidad();

        dts.setNombre(txt_nombre_especialidad.getText());
        dts.setObservacion(txt_observacion_especialidad.getText());

        func.insertarEspecialidad(dts);

        limpiarEspecialidad();
//         restablecer();
        JOptionPane.showMessageDialog(null, "LA ESPECIALIDAD SE REGISTRO CON EXITO..!!");
        mostrarTablaListadoEspecialidad();
    }

    public void RegistrarConsultorio() {
        //      paciente.ValidadorRegistrarpaciente();
        consultorio dts = new consultorio();
        logicaConsultorio func = new logicaConsultorio();
        dts.setNombre(txt_nombre_consultorio.getText());
        dts.setNumeroconsultorio(txt_numero_consultorio.getText());
        func.insertarConsultorio(dts);
        limpiarConsultorio();
//         restablecer();
        mostrarTablaListadoConsultorio();
    }

    private void btn_agregar_pacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregar_pacienteActionPerformed

        if (txt_dni_paciente.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESE UN PACIENTE"
                    + "..!!");
        } else {
            RegistrarPaciente();
            JOptionPane.showMessageDialog(null, "EL PACIENTE SE REGISTRO"
                    + " CON EXITO..!!");
        }

    }//GEN-LAST:event_btn_agregar_pacienteActionPerformed
    public void limpiar() {

        txt_dni_paciente.setText("");
        txt_apellidos_paciente.setText("");
        txt_nombres_paciente.setText("");
        JDateChooser dateChooser = new JDateChooser();
        Jdate_fechanaci_paciente.setCalendar(null);
        comb_genero_paciente.setSelectedItem("SELECCIONAR");
        txt_telefono_paciente.setText("");
        txt_ocupacion_paciente.setText("");
        txt_departamento_paciente.setText("");
        txt_provincia_paciente.setText("");
        txt_distrito_paciente.setText("");
        txt_direccion_paciente.setText("");
        txt_email_paciente.setText("");
    }

    public void limpiarCita() {
        txt_dnipaciente_cita.setText("");
        txt_nombreCompleto_cita.setText("");
        //    JDateChooser dateChooser = new JDateChooser();
        //  jdate_fecha_cita.setCalendar(null);
        txt_precio_cita.setText("");
        txt_observaciones_cita.setText("");

        btn_buscar_cita.setEnabled(true);
        btn_agregar_cita.setEnabled(true);

    }

    public void limpiarEspecialidad() {
        txt_nombre_especialidad.setText("");
        txt_observacion_especialidad.setText("");
    }

    public void limpiarMedico() {

        txt_Dni_medico.setText("");
        txt_Apellidos_medico.setText("");
        txt_Nombres_medico.setText("");
        JDateChooser dateChooser = new JDateChooser();
        jdate_fechanacimiento_medico.setCalendar(null);
        comb_genero_medico.setSelectedItem("SELECCIONAR");
        txt_telefono_medico.setText("");
        txt_direccion_medico.setText("");
        txt_email_medico.setText("");
    }

    public void limpiarhistoriaparte1() {
        txt_numero_historia.setText("");
        JDateChooser dateChooser2 = new JDateChooser();
        jadate_fecha_historia.setDate(new Date());

        combo_medico_historia.setSelectedItem("");
        jcombo_enfermedad_historia.setSelectedItem("NO");
        txt_enfermedad_historia.setText("");
        jcombo_medicamento_historia.setSelectedItem("NO");
        txt_medicamento_historia.setText("");
        jcombo_hemorragias_historia.setSelectedItem("NO");
        txt_hemorragias_historia.setText("");
        txt_diagnostico_historia.setText("");

        JDateChooser dateChooser3 = new JDateChooser();
        jdate_fechatratamiento_historia.setCalendar(null);

        txt_tratamiento_historia.setText("");
        txt_costo_tratamiento.setText("");
        txt_saldo_tratamiento.setText("");

    }

    public void limpiarhistoria() {
        txt_dni_Historia.setText("");
        txt_nombrepaciente_Historia.setText("");
        txt_fechanacimiento_historia.setText("");
        JDateChooser dateChooser1 = new JDateChooser();
        Jdate_fechanaci_paciente.setDate(new Date());
        txt_genero_historia.setText("");
        txt_telefono_historia.setText("");
        txt_ocupacion_historia.setText("");
        txt_Departamento_historia.setText("");
        txt_provincia_historia.setText("");
        txt_distrito_historia.setText("");
        txt_direccion_historia.setText("");
        txt_email_historia.setText("");

        txt_numero_historia.setText("");
        JDateChooser dateChooser2 = new JDateChooser();
        jadate_fecha_historia.setDate(new Date());

        combo_medico_historia.setSelectedItem("");
        jcombo_enfermedad_historia.setSelectedItem("NO");
        txt_enfermedad_historia.setText("");
        jcombo_medicamento_historia.setSelectedItem("NO");
        txt_medicamento_historia.setText("");
        jcombo_hemorragias_historia.setSelectedItem("NO");
        txt_hemorragias_historia.setText("");
        txt_diagnostico_historia.setText("");

        JDateChooser dateChooser3 = new JDateChooser();
        jdate_fechatratamiento_historia.setDate(new Date());

        txt_tratamiento_historia.setText("");
        txt_costo_tratamiento.setText("");
        txt_saldo_tratamiento.setText("");

    }

    public void limpiarConsultorio() {

        txt_nombre_consultorio.setText("");
        txt_numero_consultorio.setText("");

    }

    public void limpiardnipago() {
        txt_dni_listadoPago.setText("");

    }

    public void limpiardniprincipal() {
        txt_BuscarPacientePrincipal.setText("");
    }

    public void limpiardniabono() {
        txt_dni_listadoAbono.setText("");

    }

    public void limpiarTextdelBuscador() {

        txt_BuscarCliente.setText("");
        txt_Buscar_cita.setText("");
    }

    public void limpiarTextdelBuscadorMedico() {

        txt_Buscar_medico.setText("");
    }

    private void txt_telefono_pacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefono_pacienteKeyReleased
        if (txt_telefono_paciente.getText().equals("")) {
            LabelPaciente_Telefono.setForeground(new Color(102, 102, 102));
        } else {
            LabelPaciente_Telefono.setText("Telefono");
            LabelPaciente_Telefono.setForeground(new Color(0, 153, 51));
        }
    }//GEN-LAST:event_txt_telefono_pacienteKeyReleased

    private void txt_nombres_pacienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombres_pacienteKeyTyped
        Objectos.eventos.solomayusculas(evt);
        Objectos.eventos.textKeyPress(evt);
    }//GEN-LAST:event_txt_nombres_pacienteKeyTyped

    private void txt_nombres_pacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombres_pacienteKeyReleased
        if (txt_nombres_paciente.getText().equals("")) {
            LabelPaciente_Nombre.setForeground(new Color(102, 102, 102));
        } else {
            LabelPaciente_Nombre.setText("Nombres");
            LabelPaciente_Nombre.setForeground(new Color(0, 153, 51));
        }
    }//GEN-LAST:event_txt_nombres_pacienteKeyReleased

    private void txt_dni_pacienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dni_pacienteKeyTyped
        Objectos.eventos.numberKeyPress(evt);
    }//GEN-LAST:event_txt_dni_pacienteKeyTyped

    private void txt_dni_pacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dni_pacienteKeyReleased
        if (txt_dni_paciente.getText().equals("")) {
            LabelPaciente_Dni.setForeground(new Color(102, 102, 102));
        } else {
            LabelPaciente_Dni.setText("Dni");
            LabelPaciente_Dni.setForeground(new Color(0, 153, 51));

        }
    }//GEN-LAST:event_txt_dni_pacienteKeyReleased

    private void Labelimagen_PacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Labelimagen_PacienteMouseClicked

        Objectos.uploadimage.CargarImagen(Labelimagen_Paciente);
    }//GEN-LAST:event_Labelimagen_PacienteMouseClicked

    private void txt_nombre_consultorioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombre_consultorioKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombre_consultorioKeyReleased

    private void txt_nombre_consultorioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombre_consultorioKeyTyped
        Objectos.eventos.solomayusculas(evt);
        Objectos.eventos.textKeyPress(evt);
    }//GEN-LAST:event_txt_nombre_consultorioKeyTyped

    private void txt_numero_consultorioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numero_consultorioKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numero_consultorioKeyReleased

    private void txt_numero_consultorioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numero_consultorioKeyTyped
        Objectos.eventos.numberKeyPress(evt);
    }//GEN-LAST:event_txt_numero_consultorioKeyTyped

    private void btn_AgregarCliente2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AgregarCliente2ActionPerformed
        jTabbedPane_Principal.setSelectedIndex(7);
    }//GEN-LAST:event_btn_AgregarCliente2ActionPerformed

    private void Labelimagen_Cliente3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Labelimagen_Cliente3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Labelimagen_Cliente3MouseClicked

    private void Labelimagen_Cliente4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Labelimagen_Cliente4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Labelimagen_Cliente4MouseClicked

    private void txt_observacion_especialidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_observacion_especialidadKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_observacion_especialidadKeyTyped

    private void txt_observacion_especialidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_observacion_especialidadKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_observacion_especialidadKeyReleased

    private void txt_nombre_especialidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombre_especialidadKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombre_especialidadKeyTyped

    private void txt_nombre_especialidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombre_especialidadKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombre_especialidadKeyReleased

    private void txt_Dni_medicoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_Dni_medicoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_Dni_medicoKeyReleased

    private void txt_Dni_medicoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_Dni_medicoKeyTyped
        Objectos.eventos.numberKeyPress(evt);
    }//GEN-LAST:event_txt_Dni_medicoKeyTyped

    private void txt_Apellidos_medicoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_Apellidos_medicoKeyReleased

    }//GEN-LAST:event_txt_Apellidos_medicoKeyReleased

    private void txt_Apellidos_medicoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_Apellidos_medicoKeyTyped
        Objectos.eventos.solomayusculas(evt);
        Objectos.eventos.textKeyPress(evt);
    }//GEN-LAST:event_txt_Apellidos_medicoKeyTyped

    private void txt_Nombres_medicoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_Nombres_medicoKeyReleased

    }//GEN-LAST:event_txt_Nombres_medicoKeyReleased

    private void txt_Nombres_medicoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_Nombres_medicoKeyTyped
        Objectos.eventos.solomayusculas(evt);
        Objectos.eventos.textKeyPress(evt);
    }//GEN-LAST:event_txt_Nombres_medicoKeyTyped

    private void txt_telefono_medicoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefono_medicoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_telefono_medicoKeyReleased

    private void txt_direccion_medicoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_direccion_medicoKeyReleased
        Objectos.eventos.solomayusculas(evt);
        Objectos.eventos.textKeyPress(evt);
    }//GEN-LAST:event_txt_direccion_medicoKeyReleased

    private void txt_email_medicoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_email_medicoKeyReleased
        if (txt_email_medico.getText().equals("")) {
            label_email_medico.setForeground(new Color(102, 102, 102));
        } else {
            label_email_medico.setText("Email");
            label_email_medico.setForeground(new Color(0, 153, 51));

        }
    }//GEN-LAST:event_txt_email_medicoKeyReleased

    private void label_imagen_medicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_imagen_medicoMouseClicked
        Objectos.uploadimage.CargarImagen(label_imagen_medico);
    }//GEN-LAST:event_label_imagen_medicoMouseClicked

    private void txt_numero_historiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numero_historiaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numero_historiaKeyTyped

    private void txt_numero_historiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numero_historiaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numero_historiaKeyReleased

    private void txt_costo_tratamientoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_costo_tratamientoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_costo_tratamientoKeyReleased

    private void txt_saldo_tratamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_saldo_tratamientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_saldo_tratamientoActionPerformed

    private void txt_saldo_tratamientoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_saldo_tratamientoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_saldo_tratamientoKeyReleased

//    public void agregatratamientotabla() {
//        SimpleDateFormat simpleDF = new SimpleDateFormat("yyyy-MM-dd");
//        String fecha = simpleDF.format(jdate_fechatratamiento_historia.getDateFormatString());
//        if (txt_tratamiento_historia.getText().equals("")) {
//            System.out.println("INGRESE TRATAMIENTO");
//            JOptionPane.showMessageDialog(null, "INGRESE TRATAMIENTO"
//                    + "..!!");
//        } else {
//            registros[0] = jdate_fechatratamiento_historia.getDateFormatString();
//            registros[1] = txt_tratamiento_historia.getText();
//            registros[2] = txt_costo_tratamiento.getText();
//            registros[3] = txt_saldo_tratamiento.getText();
//
//            modelo.addRow(registros);
//        }
//
//    }
//    public void limpiartratamiento() {
//       JDateChooser dateChooser = new JDateChooser();
//       dateChooser.setCalendar(null);
//   
//       jdate_fechatratamiento_historia.setCalendar(null);
//        txt_tratamiento_historia.setText("");
//        txt_costo_tratamiento.setText("");
//        txt_saldo_tratamiento.setText("");
//    }

    private void Labelimagen_Cliente2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Labelimagen_Cliente2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Labelimagen_Cliente2MouseClicked

    private void txt_precio_citaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_precio_citaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_precio_citaKeyTyped

    private void txt_precio_citaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_precio_citaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_precio_citaKeyReleased

    private void txt_precio_citaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_precio_citaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_precio_citaActionPerformed

    private void txt_nombreCompleto_citaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombreCompleto_citaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombreCompleto_citaKeyTyped

    private void txt_nombreCompleto_citaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombreCompleto_citaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombreCompleto_citaKeyReleased

    private void txt_nombreCompleto_citaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombreCompleto_citaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombreCompleto_citaActionPerformed

    private void txt_dnipaciente_citaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dnipaciente_citaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dnipaciente_citaKeyTyped

    private void txt_dnipaciente_citaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dnipaciente_citaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dnipaciente_citaKeyReleased

    private void txt_dnipaciente_citaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dnipaciente_citaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dnipaciente_citaActionPerformed

    private void txt_dni_listadoPagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dni_listadoPagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dni_listadoPagoKeyReleased

    private void txt_dni_listadoPagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dni_listadoPagoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dni_listadoPagoKeyTyped

    private void btn_buscar_pagoListadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_pagoListadoActionPerformed
        if (txt_dni_listadoPago.getText().isEmpty()) {
            mostrarlistadoPagos();
            limpiar();
        } else {
            pacien.BuscarPaciente(txt_dni_listadoPago.getText(), tabla_listadoPago);
            limpiardnipago();
        }
    }//GEN-LAST:event_btn_buscar_pagoListadoActionPerformed

    private void jPanel6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel6KeyPressed

    private void Labelimagen_PacienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Labelimagen_PacienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Labelimagen_PacienteKeyPressed

    private void Jdate_fechanaci_pacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jdate_fechanaci_pacienteKeyReleased
        if (Jdate_fechanaci_paciente.getCalendar().equals("")) {
            LabelPaciente_FechaNacimiento.setForeground(new Color(102, 102, 102));
        } else {
            LabelPaciente_FechaNacimiento.setText("Fecha de nacimiento");
            LabelPaciente_FechaNacimiento.setForeground(new Color(0, 153, 51));
        }
    }//GEN-LAST:event_Jdate_fechanaci_pacienteKeyReleased

    private void Jdate_fechanaci_pacienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jdate_fechanaci_pacienteKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_Jdate_fechanaci_pacienteKeyTyped

    private void comb_genero_pacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comb_genero_pacienteKeyReleased
        if (comb_genero_paciente.getSelectedItem().equals("")) {
            LabelPaciente_Genero.setForeground(new Color(102, 102, 102));
        } else {
            LabelPaciente_Genero.setText("Genero");
            LabelPaciente_Genero.setForeground(new Color(0, 153, 51));
        }
    }//GEN-LAST:event_comb_genero_pacienteKeyReleased

    private void comb_genero_pacienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comb_genero_pacienteKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_comb_genero_pacienteKeyTyped

    private void txt_telefono_pacienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefono_pacienteKeyTyped

        Objectos.eventos.numberKeyPress(evt);
    }//GEN-LAST:event_txt_telefono_pacienteKeyTyped

    private void txt_ocupacion_pacienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ocupacion_pacienteKeyTyped
        Objectos.eventos.solomayusculas(evt);
        Objectos.eventos.textKeyPress(evt);
    }//GEN-LAST:event_txt_ocupacion_pacienteKeyTyped

    private void txt_departamento_pacienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_departamento_pacienteKeyTyped
        Objectos.eventos.solomayusculas(evt);
        Objectos.eventos.textKeyPress(evt);
    }//GEN-LAST:event_txt_departamento_pacienteKeyTyped

    private void txt_provincia_pacienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_provincia_pacienteKeyTyped
        Objectos.eventos.solomayusculas(evt);
        Objectos.eventos.textKeyPress(evt);
    }//GEN-LAST:event_txt_provincia_pacienteKeyTyped

    private void txt_distrito_pacienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_distrito_pacienteKeyTyped
        Objectos.eventos.solomayusculas(evt);
        Objectos.eventos.textKeyPress(evt);
    }//GEN-LAST:event_txt_distrito_pacienteKeyTyped

    private void txt_direccion_pacienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_direccion_pacienteKeyTyped
        Objectos.eventos.solomayusculas(evt);

    }//GEN-LAST:event_txt_direccion_pacienteKeyTyped

    private void txt_email_pacienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_email_pacienteKeyTyped

    }//GEN-LAST:event_txt_email_pacienteKeyTyped

    private void opcEliminarPopPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcEliminarPopPacienteActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(null, "Realmente desea Eliminar el Paciente?", "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION) {
            deletePaciente();
            JOptionPane.showMessageDialog(null, "PACIENTE ELIMINADO"
                    + " CON EXITO..!!");
        } else if (respuesta == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, "SE CANCELO LA ELIMINACION"
                    + "..!!");
        } else if (respuesta == JOptionPane.CLOSED_OPTION) {

        }
    }//GEN-LAST:event_opcEliminarPopPacienteActionPerformed

    public void deletePaciente() {

        paciente dts = new paciente();
        inserPaciente func = new inserPaciente();

        dts.setIdpaciente(txt_dni_paciente.getText());

        func.EliminarPaciente(dts);

        limpiar();

        mostrarTablaListadoPaciente();
    }

    public void deleteMedico() {

        medico dts = new medico();
        logicaMedico func = new logicaMedico();

        dts.setIdmedico(txt_Dni_medico.getText());

        func.EliminarMedico(dts);

        limpiar();

        mostrarTablaListadoMedico();
    }

    public void updateMedico() {
        medico dts = new medico();
        logicaMedico func = new logicaMedico();

        dts.setIdmedico(txt_Dni_medico.getText());
        dts.setApellidos(txt_Apellidos_medico.getText());
        dts.setNombres(txt_Nombres_medico.getText());
        dts.setFechanacimiento(jdate_fechanacimiento_medico.getDateFormatString());
        int seleccionado = comb_genero_medico.getSelectedIndex();
        dts.setGenero(comb_genero_medico.getItemAt(seleccionado));
        dts.setTelefono(txt_telefono_medico.getText());
        dts.setDireccion(txt_direccion_medico.getText());
        dts.setCorreo(txt_email_medico.getText());
        dts.setFoto(label_imagen_medico.getText());

        func.ActualizarMedico(dts);

        // limpiar();
//        restablecer();
        JOptionPane.showMessageDialog(null, "EL MEDICO SE ACTUALIZO"
                + " CON EXITO..!!");
        mostrarTablaListadoMedico();

    }

    public void updatePaciente() {
        paciente dts = new paciente();
        inserPaciente func = new inserPaciente();

        dts.setIdpaciente(txt_dni_paciente.getText());
        dts.setApellidos(txt_apellidos_paciente.getText());
        dts.setNombres(txt_nombres_paciente.getText());
        dts.setFechanacimiento(Jdate_fechanaci_paciente.getDateFormatString());
        int seleccionado = comb_genero_paciente.getSelectedIndex();
        dts.setGenero(comb_genero_paciente.getItemAt(seleccionado));
        dts.setTelefono(txt_telefono_paciente.getText());
        dts.setOcupacion(txt_ocupacion_paciente.getText());
        dts.setDepartamento(txt_departamento_paciente.getText());
        dts.setProvincia(txt_provincia_paciente.getText());
        dts.setDistrito(txt_distrito_paciente.getText());
        dts.setDireccion(txt_direccion_paciente.getText());
        dts.setCorreo(txt_email_paciente.getText());
        dts.setFoto(Labelimagen_Paciente.getText());

        func.ActualizarPaciente(dts);

        limpiar();
//        restablecer();
        JOptionPane.showMessageDialog(null, "EL PACIENTE SE ACTUALIZO"
                + " CON EXITO..!!");
        mostrarTablaListadoPaciente();

    }


    private void btn_Editar_PacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Editar_PacienteActionPerformed

        updatePaciente();
    }//GEN-LAST:event_btn_Editar_PacienteActionPerformed

    private void TablaListadoPacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaListadoPacienteMouseClicked

        String ls_fecha = String.valueOf(TablaListadoPaciente.getValueAt(TablaListadoPaciente.getSelectedRow(), 3));
        SimpleDateFormat s = new SimpleDateFormat("YYYY-MM-dd");
        java.util.Date fecha;

        try {
            fecha = (java.util.Date) s.parse(ls_fecha);
            Jdate_fechanaci_paciente.setDate(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(paciente.class.getName()).log(Level.SEVERE, null, ex);
        }
        int fila = TablaListadoPaciente.getSelectedRow();
        if (fila >= 0) {

            int Seleccionar = TablaListadoPaciente.rowAtPoint(evt.getPoint());
            txt_dni_paciente.setText(String.valueOf(TablaListadoPaciente.getValueAt(Seleccionar, 0)));
            txt_apellidos_paciente.setText(String.valueOf(TablaListadoPaciente.getValueAt(Seleccionar, 1)));
            txt_nombres_paciente.setText(String.valueOf(TablaListadoPaciente.getValueAt(Seleccionar, 2)));
            Jdate_fechanaci_paciente.setDateFormatString(String.valueOf(TablaListadoPaciente.getValueAt(Seleccionar, 3)));
            comb_genero_paciente.setSelectedItem(String.valueOf(TablaListadoPaciente.getValueAt(Seleccionar, 4).toString()));
            txt_telefono_paciente.setText(String.valueOf(TablaListadoPaciente.getValueAt(Seleccionar, 5)));
            txt_ocupacion_paciente.setText(String.valueOf(TablaListadoPaciente.getValueAt(Seleccionar, 6)));
            txt_departamento_paciente.setText(String.valueOf(TablaListadoPaciente.getValueAt(Seleccionar, 7)));
            txt_provincia_paciente.setText(String.valueOf(TablaListadoPaciente.getValueAt(Seleccionar, 8)));
            txt_distrito_paciente.setText(String.valueOf(TablaListadoPaciente.getValueAt(Seleccionar, 9)));
            txt_direccion_paciente.setText(String.valueOf(TablaListadoPaciente.getValueAt(Seleccionar, 10)));
            txt_email_paciente.setText(String.valueOf(TablaListadoPaciente.getValueAt(Seleccionar, 11)));
            Labelimagen_Paciente.setText((TablaListadoPaciente.getValueAt(Seleccionar, 12).toString()));

            btn_Editar_Paciente.setEnabled(true);
            btn_agregar_paciente.setEnabled(false);
            btn_cancelar_paciente.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "NO SELECCIONO FILA");
        }


    }//GEN-LAST:event_TablaListadoPacienteMouseClicked

    private void btn_cancelar_pacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelar_pacienteActionPerformed
        limpiar();
        btn_Editar_Paciente.setEnabled(false);
        btn_agregar_paciente.setEnabled(true);
        btn_cancelar_paciente.setEnabled(false);
    }//GEN-LAST:event_btn_cancelar_pacienteActionPerformed
    inserPaciente pacien = new inserPaciente();
    logicaMedico medico = new logicaMedico();

    private void btn_actualizar_PacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizar_PacienteActionPerformed

        mostrarTablaListadoPaciente();
        limpiar();


    }//GEN-LAST:event_btn_actualizar_PacienteActionPerformed

    private void btn_actualizar_PacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_actualizar_PacienteKeyReleased

    }//GEN-LAST:event_btn_actualizar_PacienteKeyReleased


    private void btn_buscar_PacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_PacienteActionPerformed
        if (txt_BuscarCliente.getText().isEmpty()) {
            mostrarTablaListadoPaciente();
            limpiar();
        } else {
            pacien.BuscarPaciente(txt_BuscarCliente.getText(), TablaListadoPaciente);
            limpiarTextdelBuscador();
        }
    }//GEN-LAST:event_btn_buscar_PacienteActionPerformed

    private void btn_buscar_PacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_buscar_PacienteKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_buscar_PacienteKeyReleased

    private void btn_buscar_medicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_medicoActionPerformed
        if (txt_Buscar_medico.getText().isEmpty()) {
            mostrarTablaListadoMedico();
            limpiarMedico();
        } else {
            medico.BuscarMedico(txt_Buscar_medico.getText(), TablaListadoMedico);
            limpiarTextdelBuscadorMedico();
        }
    }//GEN-LAST:event_btn_buscar_medicoActionPerformed

    private void btn_buscar_medicoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_buscar_medicoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_buscar_medicoKeyReleased

    private void btn_actualizar_medicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizar_medicoActionPerformed
        mostrarTablaListadoMedico();
        limpiarMedico();
    }//GEN-LAST:event_btn_actualizar_medicoActionPerformed

    private void btn_actualizar_medicoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_actualizar_medicoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_actualizar_medicoKeyReleased

    private void btn_agregar_medicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregar_medicoActionPerformed
        if (txt_Dni_medico.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESE UN MEDICO"
                    + "..!!");
        } else {
            RegistrarMedico();
            JOptionPane.showMessageDialog(null, "EL MEDICO SE REGISTRO"
                    + " CON EXITO..!!");
        }


    }//GEN-LAST:event_btn_agregar_medicoActionPerformed

    private void btn_Editar_medicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Editar_medicoActionPerformed
        updateMedico();
        limpiarMedico();
    }//GEN-LAST:event_btn_Editar_medicoActionPerformed

    private void btn_cancelar_medicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelar_medicoActionPerformed
        limpiarMedico();
        btn_Editar_medico.setEnabled(false);
        btn_agregar_medico.setEnabled(true);
        btn_cancelar_medico.setEnabled(false);
    }//GEN-LAST:event_btn_cancelar_medicoActionPerformed


    private void comb_genero_medicoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comb_genero_medicoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comb_genero_medicoKeyReleased

    private void comb_genero_medicoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comb_genero_medicoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_comb_genero_medicoKeyTyped

    private void txt_telefono_medicoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefono_medicoKeyTyped
        Objectos.eventos.numberKeyPress(evt);
    }//GEN-LAST:event_txt_telefono_medicoKeyTyped

    private void txt_direccion_medicoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_direccion_medicoKeyTyped
        Objectos.eventos.solomayusculas(evt);
        Objectos.eventos.textKeyPress(evt);
    }//GEN-LAST:event_txt_direccion_medicoKeyTyped

    private void TablaListadoMedicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaListadoMedicoMouseClicked
        String ls_fecha = String.valueOf(TablaListadoMedico.getValueAt(TablaListadoMedico.getSelectedRow(), 3));
        SimpleDateFormat s = new SimpleDateFormat("YYYY-MM-dd");
        java.util.Date fecha;

        try {
            fecha = (java.util.Date) s.parse(ls_fecha);
            jdate_fechanacimiento_medico.setDate(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(paciente.class.getName()).log(Level.SEVERE, null, ex);
        }
        int fila = TablaListadoMedico.getSelectedRow();
        if (fila >= 0) {

            int Seleccionar = TablaListadoMedico.rowAtPoint(evt.getPoint());
            txt_Dni_medico.setText(String.valueOf(TablaListadoMedico.getValueAt(Seleccionar, 0)));
            txt_Apellidos_medico.setText(String.valueOf(TablaListadoMedico.getValueAt(Seleccionar, 1)));
            txt_Nombres_medico.setText(String.valueOf(TablaListadoMedico.getValueAt(Seleccionar, 2)));
            jdate_fechanacimiento_medico.setDateFormatString(String.valueOf(TablaListadoMedico.getValueAt(Seleccionar, 3)));
            comb_genero_medico.setSelectedItem(String.valueOf(TablaListadoMedico.getValueAt(Seleccionar, 4).toString()));
            txt_telefono_medico.setText(String.valueOf(TablaListadoMedico.getValueAt(Seleccionar, 5)));
            txt_direccion_medico.setText(String.valueOf(TablaListadoMedico.getValueAt(Seleccionar, 6)));
            txt_email_medico.setText(String.valueOf(TablaListadoMedico.getValueAt(Seleccionar, 7)));
            label_imagen_medico.setText((TablaListadoMedico.getValueAt(Seleccionar, 8).toString()));

            btn_Editar_medico.setEnabled(true);
            btn_agregar_medico.setEnabled(false);
            btn_cancelar_medico.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "NO SELECCIONO FILA");
        }

    }//GEN-LAST:event_TablaListadoMedicoMouseClicked

    private void jTabbedPane_PrincipalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane_PrincipalMouseClicked

    }//GEN-LAST:event_jTabbedPane_PrincipalMouseClicked

    private void opcEliminarPopMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcEliminarPopMedicoActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(null, "Realmente desea Eliminar el Medico?", "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION) {
            deleteMedico();
            JOptionPane.showMessageDialog(null, "MEDICO ELIMINADO"
                    + " CON EXITO..!!");
        } else if (respuesta == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, "SE CANCELO LA ELIMINACION"
                    + "..!!");
        } else if (respuesta == JOptionPane.CLOSED_OPTION) {

        }
    }//GEN-LAST:event_opcEliminarPopMedicoActionPerformed

    private void btn_agregar_consultorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregar_consultorioActionPerformed

        if (txt_nombre_consultorio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESE UN CONSULTORIO"
                    + "..!!");
        } else {
            RegistrarConsultorio();
            JOptionPane.showMessageDialog(null, "EL CONSULTORIO SE REGISTRO"
                    + " CON EXITO..!!");
        }

    }//GEN-LAST:event_btn_agregar_consultorioActionPerformed

    private void btn_cancelar_consultorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelar_consultorioActionPerformed
        limpiarConsultorio();
    }//GEN-LAST:event_btn_cancelar_consultorioActionPerformed

    private void btn_cancelar_especialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelar_especialidadActionPerformed
        limpiarEspecialidad();
    }//GEN-LAST:event_btn_cancelar_especialidadActionPerformed

    private void btn_agregar_especialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregar_especialidadActionPerformed

        if (txt_nombre_especialidad.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESE UNA ESPECIALIDAD"
                    + "..!!");
        } else {
            RegistrarEspecialidad();
            JOptionPane.showMessageDialog(null, "LA ESPECIALIDAD SE REGISTRO"
                    + " CON EXITO..!!");
        }

    }//GEN-LAST:event_btn_agregar_especialidadActionPerformed

    private void combo_especialidad_medicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_especialidad_medicoActionPerformed

//    ListadoEspecialidad.setText(combo_especialidad_medico.getSelectedItem().toString());

    }//GEN-LAST:event_combo_especialidad_medicoActionPerformed

    private void combo_especialidad_medicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_combo_especialidad_medicoMouseClicked

    }//GEN-LAST:event_combo_especialidad_medicoMouseClicked


    private void btn_buscar_citaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_citaActionPerformed

        consultarPacienteCita();
    }//GEN-LAST:event_btn_buscar_citaActionPerformed
    public void consultarPacienteCita() {

        logicaCita func = new logicaCita();

        String resultado = func.buscarPacienteCita(txt_dnipaciente_cita.getText());

        txt_nombreCompleto_cita.setText(resultado);

    }

    private void btn_buscar_citaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_buscar_citaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_buscar_citaKeyReleased

    public void recogiendodatos(String dni, String fechainicio, String fechafin) {
        txt_idcita_cita.setText(dni);
        txt_dnipaciente_cita.setText(fechainicio);
        txt_nombreCompleto_cita.setText(fechafin);
    }

    logicaCita citapaciente = new logicaCita();
    private void btn_buscar_CitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_CitaActionPerformed
        String resultado;

        if (txt_Buscar_cita.getText().isEmpty()) {
            mostrarTablaListadoPaciente();
            limpiar();
        } else {

            citapaciente.Buscarcita(txt_Buscar_cita.getText(), combo_fechainicio_principal.getDate().toString(), combo_fechafin_principal.getDate().toString(), tablaListadoCita);
            limpiarTextdelBuscador();

        }
    }//GEN-LAST:event_btn_buscar_CitaActionPerformed

    private void btn_buscar_CitaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_buscar_CitaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_buscar_CitaKeyReleased

    private void btn_actualizar_citaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizar_citaActionPerformed
        limpiarCita();
        txt_dnipaciente_cita.setEditable(true);
        btn_guardar_cita.setEnabled(false);
        mostrarTablaListadoCita();
    }//GEN-LAST:event_btn_actualizar_citaActionPerformed

    private void btn_actualizar_citaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_actualizar_citaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_actualizar_citaKeyReleased

    private void btn_agregar_citaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregar_citaActionPerformed
        txt_dnipaciente_cita.setEditable(true);
        if (txt_dnipaciente_cita.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESE PACIENTE"
                    + "..!!");
        } else {
            RegistrarCita();
            JOptionPane.showMessageDialog(null, "LA CITA SE REGISTRO"
                    + " CON EXITO..!!");
        }
    }//GEN-LAST:event_btn_agregar_citaActionPerformed


    private void btn_guardar_citaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardar_citaActionPerformed
        updatecita();
        limpiarCita();
    }//GEN-LAST:event_btn_guardar_citaActionPerformed

    public void updatecita() {
        cita dts = new cita();
        logicaCita func = new logicaCita();

        SimpleDateFormat simpleDF = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = simpleDF.format(jdate_fecha_cita.getDate());

        dts.setIdcita(txt_idcita_cita.getText());
        dts.setIdpaciente(txt_dnipaciente_cita.getText());
        dts.setFechacita(fecha);
        int seleccionado = combo_hora_cita.getSelectedIndex();
        dts.setHora(combo_hora_cita.getItemAt(seleccionado));
        int seleccionado1 = combo_consultorio_cita.getSelectedIndex();
        dts.setConsultorio(combo_consultorio_cita.getItemAt(seleccionado1));
        int seleccionado2 = combo_tipoconsulta_cita.getSelectedIndex();
        dts.setTipoconsulta(combo_tipoconsulta_cita.getItemAt(seleccionado2));
        int seleccionado3 = combo_medico_cita.getSelectedIndex();
        dts.setIdmedico(combo_medico_cita.getItemAt(seleccionado3));
        dts.setObservaciones(txt_observaciones_cita.getText());
        func.ActualizarCita(dts);

        // limpiar();
//        restablecer();
        JOptionPane.showMessageDialog(null, "LA CITA SE ACTUALIZO"
                + " CON EXITO..!!");
        mostrarTablaListadoCita();

    }


    private void combo_tipoconsulta_citaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_tipoconsulta_citaActionPerformed
//        DecimalFormat df = new DecimalFormat("#.00");
        logicaTipoconsulta func = new logicaTipoconsulta();

        String resultado = func.buscarTipoConsultorio(combo_tipoconsulta_cita.getSelectedItem().toString());

        txt_precio_cita.setText(resultado);

    }//GEN-LAST:event_combo_tipoconsulta_citaActionPerformed

    logicaCita logcita = new logicaCita();
    private void btn_buscar_CitaPrincipal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_CitaPrincipal1ActionPerformed
        SimpleDateFormat simpleDF1 = new SimpleDateFormat("yyyy-MM-dd");
        String fechainicio = simpleDF1.format(combo_fechainicio_principal.getDate());
        SimpleDateFormat simpleDF2 = new SimpleDateFormat("yyyy-MM-dd");
        String fechafin = simpleDF2.format(combo_fechafin_principal.getDate());

        String Resultado = combo_estadoCita_principal.getSelectedItem().toString();
        System.out.println(Resultado + "** **" + fechainicio + "** **" + fechafin);

        if (txt_BuscarPacientePrincipal.getText().isEmpty()) {
            //mostrarlistadoPagos();
            // limpiar();
        } else {
            logcita.Buscarcita(txt_BuscarPacientePrincipal.getText(), fechainicio, fechafin, tablaListadoCitaPrincipal);
            limpiardniprincipal();
        }
    }//GEN-LAST:event_btn_buscar_CitaPrincipal1ActionPerformed

    private void btn_buscar_CitaPrincipal1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_buscar_CitaPrincipal1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_buscar_CitaPrincipal1KeyReleased

    private void btn_actualizar_cita1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizar_cita1ActionPerformed
        tablaListadoCitaPrincipal.setModel(new DefaultTableModel());
    }//GEN-LAST:event_btn_actualizar_cita1ActionPerformed

    private void btn_actualizar_cita1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_actualizar_cita1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_actualizar_cita1KeyReleased

    private void btn_Paciente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Paciente1ActionPerformed
        jTabbedPane_Principal.setSelectedIndex(2);
    }//GEN-LAST:event_btn_Paciente1ActionPerformed

    private void btn_Paciente3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Paciente3ActionPerformed
        jTabbedPane_Principal.setSelectedIndex(7);
    }//GEN-LAST:event_btn_Paciente3ActionPerformed

    private void btn_Paciente4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Paciente4ActionPerformed
        jTabbedPane_Principal.setSelectedIndex(5);

    }//GEN-LAST:event_btn_Paciente4ActionPerformed

    private void btn_Paciente5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Paciente5ActionPerformed
        jTabbedPane_Principal.setSelectedIndex(3);
    }//GEN-LAST:event_btn_Paciente5ActionPerformed

    private void btn_Paciente6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Paciente6ActionPerformed
        jTabbedPane_Principal.setSelectedIndex(6);
    }//GEN-LAST:event_btn_Paciente6ActionPerformed

    private void btn_Paciente7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Paciente7ActionPerformed
        jTabbedPane_Principal.setSelectedIndex(4);
    }//GEN-LAST:event_btn_Paciente7ActionPerformed

    private void btn_Paciente2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Paciente2ActionPerformed
        jTabbedPane_Principal.setSelectedIndex(1);
    }//GEN-LAST:event_btn_Paciente2ActionPerformed

    private void opcEditarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcEditarCitaActionPerformed

        tablaListadoCita.getSelectedRow();

        tablaListadoCita.getSelectedColumn();
        String idcita = tablaListadoCita.getValueAt(tablaListadoCita.getSelectedRow(), 0).toString();
        String dniPaciente = tablaListadoCita.getValueAt(tablaListadoCita.getSelectedRow(), 3).toString();
        String paciente = tablaListadoCita.getValueAt(tablaListadoCita.getSelectedRow(), 4).toString();
        String fecha = tablaListadoCita.getValueAt(tablaListadoCita.getSelectedRow(), 1).toString();
        String hora = tablaListadoCita.getValueAt(tablaListadoCita.getSelectedRow(), 2).toString();
        String idconsultorio = tablaListadoCita.getValueAt(tablaListadoCita.getSelectedRow(), 10).toString();
        String consultorio = tablaListadoCita.getValueAt(tablaListadoCita.getSelectedRow(), 11).toString();
        String resultadoconsultorio = idconsultorio + '-' + consultorio;
        String idtipoconsulta = tablaListadoCita.getValueAt(tablaListadoCita.getSelectedRow(), 5).toString();
        String tipoconsulta = tablaListadoCita.getValueAt(tablaListadoCita.getSelectedRow(), 6).toString();
        String resultadotipoconsulta = idtipoconsulta + '-' + tipoconsulta;
        String idmedico = tablaListadoCita.getValueAt(tablaListadoCita.getSelectedRow(), 8).toString();
        String medico1 = tablaListadoCita.getValueAt(tablaListadoCita.getSelectedRow(), 9).toString();
        String resultadomedico = idmedico + '-' + medico1;
        String observaciones = tablaListadoCita.getValueAt(tablaListadoCita.getSelectedRow(), 12).toString();

        editarcita(idcita, dniPaciente, paciente, fecha, hora, resultadotipoconsulta, resultadoconsultorio, resultadomedico, observaciones, "precio");
        txt_dnipaciente_cita.setEditable(false);
        txt_nombreCompleto_cita.setEditable(false);
        btn_guardar_cita.setEnabled(true);
    }//GEN-LAST:event_opcEditarCitaActionPerformed

    public void editarcita(String idcita, String dni, String nombresPacientes, String fecha, String hora, String resultadotipoconsulta, String resultadoconsultorio, String resultadomedico, String observaciones, String precio) {
        txt_idcita_cita.setText(idcita);
        txt_dnipaciente_cita.setText(dni);
        txt_nombreCompleto_cita.setText(nombresPacientes);
        jdate_fecha_cita.setDateFormatString(fecha);
        combo_hora_cita.setSelectedItem(hora);
        combo_consultorio_cita.setSelectedItem(resultadoconsultorio);
        combo_tipoconsulta_cita.setSelectedItem(resultadotipoconsulta);
        combo_medico_cita.setSelectedItem(resultadomedico);
        txt_observaciones_cita.setText(observaciones);

        btn_buscar_cita.setEnabled(false);
        btn_agregar_cita.setEnabled(false);
    }

    public void editarhistoria(String idhistoriapaciente, String fechahistoria,
            String idmedico, String padeceenfermedad, String respuestapadece, String alergico,
            String respuestaalergico, String hemorragia, String respuestahemorragia, String diagnostico,
            String fechatratamiento, String tratamiento, String costo, String saldo) {
        txt_numero_historia.setText(idhistoriapaciente);
        jadate_fecha_historia.setDateFormatString(fechahistoria);
        combo_medico_historia.setSelectedItem(idmedico);
        jcombo_enfermedad_historia.setSelectedItem(padeceenfermedad);
        txt_enfermedad_historia.setText(respuestapadece);
        jcombo_medicamento_historia.setSelectedItem(alergico);
        txt_medicamento_historia.setText(respuestaalergico);
        jcombo_hemorragias_historia.setSelectedItem(hemorragia);
        txt_hemorragias_historia.setText(respuestahemorragia);
        txt_diagnostico_historia.setText(diagnostico);
        jdate_fechatratamiento_historia.setDateFormatString(fechatratamiento);
        txt_tratamiento_historia.setText(tratamiento);
        txt_costo_tratamiento.setText(costo);
        txt_saldo_tratamiento.setText(saldo);

//        btn_buscar_cita.setEnabled(false);
//        btn_agregar_cita.setEnabled(false);
    }


    private void opcEditarCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opcEditarCitaMouseClicked

    }//GEN-LAST:event_opcEditarCitaMouseClicked

    private void btn_cancelar_citaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelar_citaActionPerformed
        limpiarCita();
        txt_dnipaciente_cita.setEditable(true);
        btn_guardar_cita.setEnabled(false);
    }//GEN-LAST:event_btn_cancelar_citaActionPerformed

    private void opcEliminarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcEliminarCitaActionPerformed

        cita dts = new cita();

        logicaCita func = new logicaCita();

        tablaListadoCita.getSelectedRow();
        tablaListadoCita.getSelectedColumn();
        String idcita = tablaListadoCita.getValueAt(tablaListadoCita.getSelectedRow(), 0).toString();
        System.out.println(idcita);

        int respuesta = JOptionPane.showConfirmDialog(null, "Realmente desea Eliminar la Cita?", "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION) {
            func.deletecita(idcita);
            JOptionPane.showMessageDialog(null, "CITA ELIMINADO"
                    + " CON EXITO..!!");
        } else if (respuesta == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, "SE CANCELO LA ELIMINACION"
                    + "..!!");
        } else if (respuesta == JOptionPane.CLOSED_OPTION) {

        }

        mostrarTablaListadoCita();
    }//GEN-LAST:event_opcEliminarCitaActionPerformed

    private void txt_idcita_citaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idcita_citaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idcita_citaActionPerformed
    logicaHistoria mosHistoria = new logicaHistoria();

    private void btn_buscar_historiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_historiaActionPerformed

        consultarPacienteHistoria();

        if (txt_numero_historia.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESE NUMERO DE DNI");
            limpiar();
        } else {
            mosHistoria.MostrarHistoria(txt_numero_historia.getText(), tabla_historia_paciente);
            // limpiarTextdelBuscador();
        }


    }//GEN-LAST:event_btn_buscar_historiaActionPerformed
    public void consultarPacienteHistoria() {

        logicaHistoria func = new logicaHistoria();

        String[] registro = func.buscarPacienteHistoria(txt_dni_Historia.getText());
//        System.out.println(registro);
        txt_nombrepaciente_Historia.setText(registro[13]);
        txt_fechanacimiento_historia.setText(registro[3]);
        txt_genero_historia.setText(registro[4]);
        txt_telefono_historia.setText(registro[5]);
        txt_ocupacion_historia.setText(registro[6]);
        txt_Departamento_historia.setText(registro[7]);
        txt_provincia_historia.setText(registro[8]);
        txt_distrito_historia.setText(registro[9]);
        txt_direccion_historia.setText(registro[10]);
        txt_email_historia.setText(registro[11]);
        txt_numero_historia.setText(registro[18]);

    }
    private void txt_email_historiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_email_historiaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_email_historiaKeyReleased

    private void txt_direccion_historiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_direccion_historiaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_direccion_historiaKeyReleased

    private void txt_distrito_historiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_distrito_historiaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_distrito_historiaKeyReleased

    private void txt_ocupacion_historiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ocupacion_historiaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ocupacion_historiaKeyReleased

    private void txt_Departamento_historiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_Departamento_historiaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_Departamento_historiaKeyReleased

    private void txt_provincia_historiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_provincia_historiaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_provincia_historiaKeyReleased

    private void txt_telefono_historiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefono_historiaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_telefono_historiaKeyReleased

    private void txt_genero_historiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_genero_historiaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_genero_historiaKeyTyped

    private void txt_genero_historiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_genero_historiaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_genero_historiaKeyReleased

    private void txt_nombrepaciente_HistoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombrepaciente_HistoriaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombrepaciente_HistoriaKeyTyped

    private void txt_nombrepaciente_HistoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombrepaciente_HistoriaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombrepaciente_HistoriaKeyReleased

    private void txt_dni_HistoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dni_HistoriaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dni_HistoriaKeyTyped

    private void txt_dni_HistoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dni_HistoriaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dni_HistoriaKeyReleased

    private void Label_imagen_historiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Label_imagen_historiaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Label_imagen_historiaMouseClicked

    private void txt_fechanacimiento_historiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fechanacimiento_historiaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fechanacimiento_historiaKeyReleased

    private void txt_fechanacimiento_historiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fechanacimiento_historiaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fechanacimiento_historiaKeyTyped

    private void txt_costo_tratamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_costo_tratamientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_costo_tratamientoActionPerformed

    private void combo_medico_historiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_medico_historiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_medico_historiaActionPerformed

    logicaHistoria mostrar1 = new logicaHistoria();
    private void btn_agregar_historiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregar_historiaActionPerformed

        //txt_dnipaciente_cita.setEditable(true);
        if (txt_numero_historia.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "BUSQUE PACIENTE !! NO HAY CODIGO DE HISTORIA"
                    + "..!!");
        } else {
            RegistrarHistoria();
            // mostrar1.MostrarHistoria(txt_numero_historia.getText(), tabla_historia_paciente);
            JOptionPane.showMessageDialog(null, "LA HISTORIA SE REGISTRO"
                    + " CON EXITO..!!");
            // mostrarTablaListadoHistoria();
        }


    }//GEN-LAST:event_btn_agregar_historiaActionPerformed

    public void RegistrarHistoria() {

        historia dts = new historia();
        logicaHistoria func = new logicaHistoria();

        SimpleDateFormat simpleDF = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = simpleDF.format(jadate_fecha_historia.getDate());

        SimpleDateFormat simpleDF1 = new SimpleDateFormat("yyyy-MM-dd");
        String fecha1 = simpleDF1.format(jdate_fechatratamiento_historia.getDate());

        dts.setIdhistoriapaciente(txt_numero_historia.getText());
        //String insepa=txt_numero_historia.getText();
        dts.setFechahistoria(fecha);

        int seleccionado1 = combo_medico_historia.getSelectedIndex();
        dts.setIdmedico(combo_medico_historia.getItemAt(seleccionado1));

        int seleccionado2 = jcombo_enfermedad_historia.getSelectedIndex();
        dts.setPadeceenfermedad(jcombo_enfermedad_historia.getItemAt(seleccionado2));

        dts.setRespuestapadece(txt_enfermedad_historia.getText());

        int seleccionado3 = jcombo_medicamento_historia.getSelectedIndex();
        dts.setAlergico(jcombo_medicamento_historia.getItemAt(seleccionado3));

        dts.setRespuestaalergico(txt_medicamento_historia.getText());

        int seleccionado4 = jcombo_hemorragias_historia.getSelectedIndex();
        dts.setHemorragia(jcombo_hemorragias_historia.getItemAt(seleccionado4));

        dts.setRespuestahemorragia(txt_hemorragias_historia.getText());

        dts.setDiagnostico(txt_diagnostico_historia.getText());

        dts.setFechatratamiento(fecha1);
        dts.setTratamiento(txt_tratamiento_historia.getText());
        dts.setCosto(txt_costo_tratamiento.getText());
        dts.setSaldo(txt_saldo_tratamiento.getText());

        func.insertarHistoria(dts);
        mostrar1.MostrarHistoria(txt_numero_historia.getText(), tabla_historia_paciente);
        System.out.print("la historia se registro");
        limpiarhistoriaparte1();
        //  mostrarTablaListadoHistoria(insepa, tabla_historia_paciente);

    }


    private void btn_cancelar_historiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelar_historiaActionPerformed
        limpiarhistoria();
    }//GEN-LAST:event_btn_cancelar_historiaActionPerformed

    private void jcombo_enfermedad_historiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcombo_enfermedad_historiaActionPerformed
        if (jcombo_enfermedad_historia.getSelectedItem().toString().equals("SELECCIONAR")) {
//            JOptionPane.showMessageDialog(null, "INVALIDO ITEM");
            txt_enfermedad_historia.setEnabled(false);
        } else if (jcombo_enfermedad_historia.getSelectedItem().toString().equals("SI")) {
            txt_enfermedad_historia.setEnabled(true);
        } else if (jcombo_enfermedad_historia.getSelectedItem().toString().equals("NO")) {
            txt_enfermedad_historia.setEnabled(false);
        }
    }//GEN-LAST:event_jcombo_enfermedad_historiaActionPerformed

    private void jcombo_medicamento_historiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcombo_medicamento_historiaActionPerformed
        if (jcombo_medicamento_historia.getSelectedItem().toString().equals("SELECCIONAR")) {
//            JOptionPane.showMessageDialog(null, "INVALIDO ITEM");
            txt_medicamento_historia.setEnabled(false);
        } else if (jcombo_medicamento_historia.getSelectedItem().toString().equals("SI")) {
            txt_medicamento_historia.setEnabled(true);
        } else if (jcombo_medicamento_historia.getSelectedItem().toString().equals("NO")) {
            txt_medicamento_historia.setEnabled(false);
        }
    }//GEN-LAST:event_jcombo_medicamento_historiaActionPerformed

    private void jcombo_hemorragias_historiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcombo_hemorragias_historiaActionPerformed
        if (jcombo_hemorragias_historia.getSelectedItem().toString().equals("SELECCIONAR")) {
//            JOptionPane.showMessageDialog(null, "INVALIDO ITEM");
            txt_hemorragias_historia.setEnabled(false);
        } else if (jcombo_hemorragias_historia.getSelectedItem().toString().equals("SI")) {
            txt_hemorragias_historia.setEnabled(true);
        } else if (jcombo_hemorragias_historia.getSelectedItem().toString().equals("NO")) {
            txt_hemorragias_historia.setEnabled(false);
        }
    }//GEN-LAST:event_jcombo_hemorragias_historiaActionPerformed

    private void opcEditarHistoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcEditarHistoriaActionPerformed

        tabla_historia_paciente.getSelectedRow();

        tabla_historia_paciente.getSelectedColumn();
        String idhistoriapaciente = tabla_historia_paciente.getValueAt(tabla_historia_paciente.getSelectedRow(), 0).toString();
        String fechahistoria = tabla_historia_paciente.getValueAt(tabla_historia_paciente.getSelectedRow(), 1).toString();
        String idmedico = tabla_historia_paciente.getValueAt(tabla_historia_paciente.getSelectedRow(), 2).toString();
        String padeceenfermedad = tabla_historia_paciente.getValueAt(tabla_historia_paciente.getSelectedRow(), 3).toString();
        String respuestapadece = tabla_historia_paciente.getValueAt(tabla_historia_paciente.getSelectedRow(), 4).toString();
        String alergico = tabla_historia_paciente.getValueAt(tabla_historia_paciente.getSelectedRow(), 5).toString();
        String respuestaalergico = tabla_historia_paciente.getValueAt(tabla_historia_paciente.getSelectedRow(), 6).toString();
        String hemorragia = tabla_historia_paciente.getValueAt(tabla_historia_paciente.getSelectedRow(), 7).toString();
        String respuestahemorragia = tabla_historia_paciente.getValueAt(tabla_historia_paciente.getSelectedRow(), 8).toString();
        String diagnostico = tabla_historia_paciente.getValueAt(tabla_historia_paciente.getSelectedRow(), 9).toString();
        String fechatratamiento = tabla_historia_paciente.getValueAt(tabla_historia_paciente.getSelectedRow(), 10).toString();
        String tratamiento = tabla_historia_paciente.getValueAt(tabla_historia_paciente.getSelectedRow(), 11).toString();
        String costo = tabla_historia_paciente.getValueAt(tabla_historia_paciente.getSelectedRow(), 12).toString();
        String saldo = tabla_historia_paciente.getValueAt(tabla_historia_paciente.getSelectedRow(), 13).toString();

        editarhistoria(idhistoriapaciente, fechahistoria,
                idmedico, padeceenfermedad, respuestapadece, alergico,
                respuestaalergico, hemorragia, respuestahemorragia, diagnostico,
                fechatratamiento, tratamiento, costo, saldo);
//        txt_dnipaciente_cita.setEditable(false);
//        txt_nombreCompleto_cita.setEditable(false);
//        btn_guardar_cita.setEnabled(true);


    }//GEN-LAST:event_opcEditarHistoriaActionPerformed

    private void txt_tratamiento_historiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_tratamiento_historiaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tratamiento_historiaKeyReleased

    private void txt_tratamiento_historiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tratamiento_historiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tratamiento_historiaActionPerformed

    private void btn_actualizar_historiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizar_historiaActionPerformed
        limpiarhistoria();
    }//GEN-LAST:event_btn_actualizar_historiaActionPerformed

    private void btn_actualizar_historiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_actualizar_historiaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_actualizar_historiaKeyReleased

    private void opcRealizarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcRealizarPagoActionPerformed
        nuevoPago nuevopago = new nuevoPago();
//        int fila = tablaListadoCitaPrincipal.getSelectedRow();
//        String atencion = tablaListadoCitaPrincipal.getValueAt(tablaListadoCitaPrincipal.getSelectedRow(), 15).toString();

//        if(atencion.equals("PENDIENTE")){
//            if (fila >= 0){
//                String idCita = tablaListadoCitaPrincipal.getValueAt(fila, 0).toString();
//                String precio = tablaListadoCitaPrincipal.getValueAt(fila, 15).toString();
//                
//                
//               // nuevopago dialogpago = new nuevoPago(this, true, conn, idCita, pago);
//                //dialogpago.setVisible(true);
//                System.out.println(idCita+" "+precio);
//            }else{
//                JOptionPane.showMessageDialog(this, "La fila esta vacia","Error",JOptionPane.QUESTION_MESSAGE);
//            }
//        }else{
//            JOptionPane.showMessageDialog(this, "Este pasiente ya ha sido atendido, verifique en la tabla Pagos","Error",JOptionPane.ERROR_MESSAGE);
//        }
        //obtenerCitasxFecha(fechaActual());
        nuevopago.setVisible(true);

    }//GEN-LAST:event_opcRealizarPagoActionPerformed

    private void txt_ruc_pagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ruc_pagoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ruc_pagoKeyTyped

    private void txt_ruc_pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ruc_pagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ruc_pagoKeyReleased

    private void txt_ruc_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ruc_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ruc_pagoActionPerformed

    private void txt_serie_PagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_serie_PagoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_serie_PagoKeyTyped

    private void txt_serie_PagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_serie_PagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_serie_PagoKeyReleased

    private void txt_serie_PagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_serie_PagoActionPerformed

    }//GEN-LAST:event_txt_serie_PagoActionPerformed

    private void txt_cantidad_producto_pagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cantidad_producto_pagoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cantidad_producto_pagoKeyTyped
    public double proceso(String a, String b) {
        double n1 = Double.parseDouble(a);
        double n2 = Double.parseDouble(b);
        double r = n1 * n2;
        return r;
    }
    private void txt_cantidad_producto_pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cantidad_producto_pagoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_monto_pago.setText("" + proceso(txt_costo_producto_pago.getText(), txt_cantidad_producto_pago.getText()));

        }
    }//GEN-LAST:event_txt_cantidad_producto_pagoKeyReleased

    private void txt_cantidad_producto_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cantidad_producto_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cantidad_producto_pagoActionPerformed

    private void btn_buscarPaciente_PagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_buscarPaciente_PagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_buscarPaciente_PagoKeyReleased

    private void btn_buscarPaciente_PagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarPaciente_PagoActionPerformed

        consultarPacientePago();


    }//GEN-LAST:event_btn_buscarPaciente_PagoActionPerformed

    public void consultarPacientePago() {

        inserPaciente func = new inserPaciente();

        String resultado = func.buscarPacientePago(txt_dniPaciente_Pago.getText());

        txt_nombrepaciente_pago.setText(resultado);
    }


    private void txt_descripcion_producto_pagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_descripcion_producto_pagoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_descripcion_producto_pagoKeyTyped

    private void txt_descripcion_producto_pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_descripcion_producto_pagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_descripcion_producto_pagoKeyReleased

    private void txt_descripcion_producto_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_descripcion_producto_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_descripcion_producto_pagoActionPerformed

    private void txt_nombrepaciente_pagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombrepaciente_pagoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombrepaciente_pagoKeyTyped

    private void txt_nombrepaciente_pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombrepaciente_pagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombrepaciente_pagoKeyReleased

    private void txt_nombrepaciente_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombrepaciente_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombrepaciente_pagoActionPerformed

    private void txt_numeroserie_PagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numeroserie_PagoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numeroserie_PagoKeyTyped

    private void txt_numeroserie_PagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numeroserie_PagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numeroserie_PagoKeyReleased

    private void txt_numeroserie_PagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numeroserie_PagoActionPerformed

    }//GEN-LAST:event_txt_numeroserie_PagoActionPerformed

    private void combo_tipodocumento_PagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_tipodocumento_PagoActionPerformed
        if (combo_tipodocumento_Pago.getSelectedItem().toString().equals("DOCUMENTO DE PAGO")) {
            txt_acuenta_pago.setEnabled(false);
            txt_saldo_pago.setEnabled(false);
            txt_serie_Pago.setText("B001");
            txt_descuento_pago.setText("0");
            txt_acuenta_pago.setText("");
            txt_saldo_pago.setText("");

        } else if (combo_tipodocumento_Pago.getSelectedItem().toString().equals("DOCUMENTO DE ABONO")) {
            txt_acuenta_pago.setEnabled(true);
            txt_saldo_pago.setEnabled(true);
            txt_serie_Pago.setText("A001");
            txt_descuento_pago.setText("0");
            txt_acuenta_pago.setText("");
            txt_saldo_pago.setText("");
        }
    }//GEN-LAST:event_combo_tipodocumento_PagoActionPerformed

    private void txt_costo_producto_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_costo_producto_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_costo_producto_pagoActionPerformed

    private void txt_costo_producto_pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_costo_producto_pagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_costo_producto_pagoKeyReleased

    private void txt_costo_producto_pagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_costo_producto_pagoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_costo_producto_pagoKeyTyped

    private void txt_codigo_Producto_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_codigo_Producto_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_codigo_Producto_pagoActionPerformed

    private void txt_codigo_Producto_pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codigo_Producto_pagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_codigo_Producto_pagoKeyReleased

    private void txt_codigo_Producto_pagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codigo_Producto_pagoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_codigo_Producto_pagoKeyTyped

    private void txt_monto_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_monto_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_monto_pagoActionPerformed

    private void txt_monto_pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_monto_pagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_monto_pagoKeyReleased

    private void txt_monto_pagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_monto_pagoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_monto_pagoKeyTyped

    private void btn_agregar_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregar_pagoActionPerformed

        String codigo = txt_codigo_Producto_pago.getText();
        String descripcion = txt_descripcion_producto_pago.getText();
        String cantidad = txt_cantidad_producto_pago.getText();
        String costo = txt_costo_producto_pago.getText();
        String acuenta = txt_acuenta_pago.getText();
        String saldo = txt_saldo_pago.getText();
        String monto = txt_monto_pago.getText();

        pago obj = new pago();
        obj.setIdtipoconsulta(codigo);
        obj.setDescripcion(descripcion);
        obj.setCantidad(cantidad);
        obj.setCosto(costo);
        obj.setAcuenta(acuenta);
        obj.setSaldo(saldo);
        obj.setMonto(monto);
        obj.agregar();
        limpiarPago();

        Vector lista = obj.mostrar();
        mostrarListado(lista);
    }//GEN-LAST:event_btn_agregar_pagoActionPerformed
    public void limpiarPago() {
        txt_codigo_Producto_pago.setText("");
        txt_descripcion_producto_pago.setText("");
        txt_cantidad_producto_pago.setText("");
        txt_costo_producto_pago.setText("");
        txt_acuenta_pago.setText("");
        txt_monto_pago.setText("");

    }
    private void btn_registrar_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrar_pagoActionPerformed

        if (txt_dniPaciente_Pago.getText().isEmpty() && txt_idcita_pago.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "FALTA INGRESAR DATOS "
                    + "..!!");
        } else {
            RegistrarPago();
            JOptionPane.showMessageDialog(null, "EL PAGO SE REGISTRO"
                    + " CON EXITO..!!");

            ListarPagosImpre impre = new ListarPagosImpre();
            impre.setVisible(true);

        }


    }//GEN-LAST:event_btn_registrar_pagoActionPerformed


    private void btn_eliminar_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminar_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_eliminar_pagoActionPerformed

    private void btn_editar_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editar_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_editar_pagoActionPerformed

    private void btn_imprimir_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimir_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_imprimir_pagoActionPerformed

    private void btn_cancelar_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelar_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_cancelar_pagoActionPerformed

    private void txt_dniPaciente_PagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dniPaciente_PagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dniPaciente_PagoActionPerformed

    private void txt_dniPaciente_PagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dniPaciente_PagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dniPaciente_PagoKeyReleased

    private void txt_dniPaciente_PagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dniPaciente_PagoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dniPaciente_PagoKeyTyped

    logicaPago Pagocita = new logicaPago();
    private void btn_sincronizar_listadopagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sincronizar_listadopagoActionPerformed
        SimpleDateFormat simpleDF1 = new SimpleDateFormat("yyyy-MM-dd");
        String fechainicio = simpleDF1.format(jdate_fechainicio_listadoPago.getDate());
        SimpleDateFormat simpleDF2 = new SimpleDateFormat("yyyy-MM-dd");
        String fechafin = simpleDF2.format(jdate_fechafin_listadopago.getDate());

//        String Resultado = combo_estadoCita_principal.getSelectedItem().toString();
//        System.out.println(Resultado + "** **" + fechainicio + "** **" + fechafin);
        Pagocita.listadoPagocita(fechainicio, fechafin, tabla_listadoPago);


    }//GEN-LAST:event_btn_sincronizar_listadopagoActionPerformed

    private void btn_sincronizar_listadopagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_sincronizar_listadopagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_sincronizar_listadopagoKeyReleased


    private void btn_sincronizar_listadoAbonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sincronizar_listadoAbonoActionPerformed
        SimpleDateFormat simpleDF1 = new SimpleDateFormat("yyyy-MM-dd");
        String fechainicio = simpleDF1.format(jdate_fechainicio_listadoAbono.getDate());
        SimpleDateFormat simpleDF2 = new SimpleDateFormat("yyyy-MM-dd");
        String fechafin = simpleDF2.format(jdate_fechafin_listadoAbono.getDate());

//        String Resultado = combo_estadoCita_principal.getSelectedItem().toString();
//        System.out.println(Resultado + "** **" + fechainicio + "** **" + fechafin);
        Pagocita.listadoPagoAbono(fechainicio, fechafin, tabla_listadoAbono);
    }//GEN-LAST:event_btn_sincronizar_listadoAbonoActionPerformed

    private void btn_sincronizar_listadoAbonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_sincronizar_listadoAbonoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_sincronizar_listadoAbonoKeyReleased

    private void txt_dni_listadoAbonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dni_listadoAbonoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dni_listadoAbonoKeyReleased

    private void txt_dni_listadoAbonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dni_listadoAbonoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dni_listadoAbonoKeyTyped

    logicaPago pago = new logicaPago();
    private void btn_buscarPaciente_listadoAbonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarPaciente_listadoAbonoActionPerformed
        if (txt_dni_listadoAbono.getText().isEmpty()) {
            mostrarlistadoPagos();
            // limpiar();
        } else {
            pago.BuscarDniAbono(txt_dni_listadoAbono.getText(), tabla_listadoAbono);
            limpiardniabono();
        }
    }//GEN-LAST:event_btn_buscarPaciente_listadoAbonoActionPerformed


    private void btn_exportarExcel_listadoPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportarExcel_listadoPagoActionPerformed

        try {
            ExportarExcel obj = new ExportarExcel();
            obj.exportarExcel(tabla_listadoPago);
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }//GEN-LAST:event_btn_exportarExcel_listadoPagoActionPerformed

    private void btn_exportarExcel_listadoPagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_exportarExcel_listadoPagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_exportarExcel_listadoPagoKeyReleased

    private void btn_exportarExcel_listadoAbonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportarExcel_listadoAbonoActionPerformed
        try {
            ExportarExcel obj = new ExportarExcel();
            obj.exportarExcel(tabla_listadoAbono);
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }//GEN-LAST:event_btn_exportarExcel_listadoAbonoActionPerformed

    private void btn_exportarExcel_listadoAbonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_exportarExcel_listadoAbonoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_exportarExcel_listadoAbonoKeyReleased

    private void txt_idcita_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idcita_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idcita_pagoActionPerformed

    private void txt_idcita_pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_idcita_pagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idcita_pagoKeyReleased

    private void txt_idcita_pagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_idcita_pagoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idcita_pagoKeyTyped

    private void btn_buscarcita_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarcita_pagoActionPerformed
          if (txt_idcita_pago.getText().isEmpty()) {
             
              ListarCitas listarcitas = new ListarCitas();
                listarcitas.setVisible(true);
              
        } else {
              
         
        }
        
        

    }//GEN-LAST:event_btn_buscarcita_pagoActionPerformed

    private void btn_buscarcita_pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_buscarcita_pagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_buscarcita_pagoKeyReleased

    private void txt_acuenta_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_acuenta_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_acuenta_pagoActionPerformed

    public double calculo(String a, String b) {
        double n1 = Double.parseDouble(a);
        double n2 = Double.parseDouble(b);
        double r = n1 - n2;
        return r;
    }
    private void txt_acuenta_pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_acuenta_pagoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_total_pago.setText("" + calculo(txt_monto_pago.getText(), txt_descuento_pago.getText()));

        }
    }//GEN-LAST:event_txt_acuenta_pagoKeyReleased

    private void txt_acuenta_pagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_acuenta_pagoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_acuenta_pagoKeyTyped

    private void txt_saldo_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_saldo_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_saldo_pagoActionPerformed

    private void txt_saldo_pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_saldo_pagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_saldo_pagoKeyReleased

    private void txt_saldo_pagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_saldo_pagoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_saldo_pagoKeyTyped

    private void btn_buscarProductoServicio_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarProductoServicio_pagoActionPerformed
        ListarServicios listarproductosservicios = new ListarServicios();
        listarproductosservicios.setVisible(true);
    }//GEN-LAST:event_btn_buscarProductoServicio_pagoActionPerformed

    private void btn_buscarProductoServicio_pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_buscarProductoServicio_pagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_buscarProductoServicio_pagoKeyReleased

    logicaCita citaprincipal = new logicaCita();
    private void btn_sincronizar_listadopago1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sincronizar_listadopago1ActionPerformed
        SimpleDateFormat simpleDF1 = new SimpleDateFormat("yyyy-MM-dd");
        String fechainicio = simpleDF1.format(combo_fechainicio_principal.getDate());
        SimpleDateFormat simpleDF2 = new SimpleDateFormat("yyyy-MM-dd");
        String fechafin = simpleDF2.format(combo_fechafin_principal.getDate());

        String Resultado = combo_estadoCita_principal.getSelectedItem().toString();
        System.out.println(Resultado + "** **" + fechainicio + "** **" + fechafin);
        citaprincipal.mostrarCitaPrincipal(fechainicio, fechafin, combo_estadoCita_principal.getSelectedItem().toString(), tablaListadoCitaPrincipal);


    }//GEN-LAST:event_btn_sincronizar_listadopago1ActionPerformed

    private void btn_sincronizar_listadopago1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_sincronizar_listadopago1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_sincronizar_listadopago1KeyReleased

    private void txt_total_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_total_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_total_pagoActionPerformed

    private void txt_total_pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_total_pagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_total_pagoKeyReleased

    private void txt_total_pagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_total_pagoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_total_pagoKeyTyped

    private void txt_descuento_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_descuento_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_descuento_pagoActionPerformed

    private void txt_descuento_pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_descuento_pagoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_total_pago.setText("" + proceso2(txt_subtotal_pago.getText(), txt_descuento_pago.getText()));

        }


    }//GEN-LAST:event_txt_descuento_pagoKeyReleased

    private void txt_descuento_pagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_descuento_pagoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_descuento_pagoKeyTyped

    private void txt_subtotal_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_subtotal_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_subtotal_pagoActionPerformed

    private void txt_subtotal_pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_subtotal_pagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_subtotal_pagoKeyReleased

    private void txt_subtotal_pagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_subtotal_pagoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_subtotal_pagoKeyTyped

    private void btn_historiaclinica_principalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_historiaclinica_principalActionPerformed
        jTabbedPane_Principal.setSelectedIndex(5);

//      String ls_fecha = String.valueOf(tablaListadoCitaPrincipal.getValueAt(tablaListadoCitaPrincipal.getSelectedRow(), 3));

        int fila = tablaListadoCitaPrincipal.getSelectedRow();
        if (fila >= 0) {

            txt_dni_Historia.setText(String.valueOf(tablaListadoCitaPrincipal.getValueAt(fila, 3)));

        } else {
            JOptionPane.showMessageDialog(null, "NO SELECCIONO FILA");
        }


    }//GEN-LAST:event_btn_historiaclinica_principalActionPerformed

    private void btn_historiaclinica_principalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_historiaclinica_principalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_historiaclinica_principalKeyReleased

    private void btn_exportar_excel_principal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportar_excel_principal1ActionPerformed
       try {
            ExportarExcel obj = new ExportarExcel();
            obj.exportarExcel(tablaListadoCitaPrincipal);
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }//GEN-LAST:event_btn_exportar_excel_principal1ActionPerformed

    private void btn_exportar_excel_principal1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_exportar_excel_principal1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_exportar_excel_principal1KeyReleased

    private void btn_pagar_principalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pagar_principalActionPerformed
        jTabbedPane_Principal.setSelectedIndex(6);

        int fila = tablaListadoCitaPrincipal.getSelectedRow();
        if (fila >= 0) {

            txt_idcita_pago.setText(String.valueOf(tablaListadoCitaPrincipal.getValueAt(fila, 0)));
            txt_dniPaciente_Pago.setText(String.valueOf(tablaListadoCitaPrincipal.getValueAt(fila, 3)));
            txt_nombrepaciente_pago.setText(String.valueOf(tablaListadoCitaPrincipal.getValueAt(fila, 4)));
            
        } else {
            JOptionPane.showMessageDialog(null, "NO SELECCIONO FILA");
        }
       
        
        
        
        
        
        
    }//GEN-LAST:event_btn_pagar_principalActionPerformed

    private void btn_pagar_principalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_pagar_principalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_pagar_principalKeyReleased

    public double proceso2(String a, String b) {
        double n1 = Double.parseDouble(a);
        double n2 = Double.parseDouble(b);
        double r = n1 - n2;
        return r;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public com.toedter.calendar.JDateChooser Jdate_fechanaci_paciente;
    private javax.swing.JLabel LabelCliente_Apellido10;
    private javax.swing.JLabel LabelCliente_Apellido11;
    private javax.swing.JLabel LabelCliente_Apellido12;
    private javax.swing.JLabel LabelCliente_Apellido13;
    private javax.swing.JLabel LabelCliente_Apellido14;
    private javax.swing.JLabel LabelCliente_Apellido15;
    private javax.swing.JLabel LabelCliente_Apellido16;
    private javax.swing.JLabel LabelCliente_Apellido17;
    private javax.swing.JLabel LabelCliente_Apellido18;
    private javax.swing.JLabel LabelCliente_Apellido19;
    private javax.swing.JLabel LabelCliente_Apellido20;
    private javax.swing.JLabel LabelCliente_Apellido21;
    private javax.swing.JLabel LabelCliente_Apellido22;
    private javax.swing.JLabel LabelCliente_Apellido23;
    private javax.swing.JLabel LabelCliente_Apellido24;
    private javax.swing.JLabel LabelCliente_Apellido3;
    private javax.swing.JLabel LabelCliente_Apellido4;
    private javax.swing.JLabel LabelCliente_Apellido5;
    private javax.swing.JLabel LabelCliente_Apellido6;
    private javax.swing.JLabel LabelCliente_Apellido7;
    private javax.swing.JLabel LabelCliente_Apellido8;
    private javax.swing.JLabel LabelCliente_Apellido9;
    private javax.swing.JLabel LabelCliente_Direccion16;
    private javax.swing.JLabel LabelCliente_Direccion17;
    private javax.swing.JLabel LabelCliente_Direccion18;
    private javax.swing.JLabel LabelCliente_Direccion23;
    private javax.swing.JLabel LabelCliente_Direccion24;
    private javax.swing.JLabel LabelCliente_Direccion25;
    private javax.swing.JLabel LabelCliente_Direccion26;
    private javax.swing.JLabel LabelCliente_Direccion5;
    private javax.swing.JLabel LabelCliente_Direccion6;
    private javax.swing.JLabel LabelCliente_Direccion7;
    private javax.swing.JLabel LabelCliente_Direccion8;
    private javax.swing.JLabel LabelCliente_Direccion9;
    private javax.swing.JLabel LabelCliente_Dni1;
    private javax.swing.JLabel LabelCliente_Dni10;
    private javax.swing.JLabel LabelCliente_Dni11;
    private javax.swing.JLabel LabelCliente_Dni12;
    private javax.swing.JLabel LabelCliente_Dni13;
    private javax.swing.JLabel LabelCliente_Dni14;
    private javax.swing.JLabel LabelCliente_Dni3;
    private javax.swing.JLabel LabelCliente_Dni4;
    private javax.swing.JLabel LabelCliente_Dni5;
    private javax.swing.JLabel LabelCliente_Dni6;
    private javax.swing.JLabel LabelCliente_Dni7;
    private javax.swing.JLabel LabelCliente_Dni9;
    private javax.swing.JLabel LabelCliente_Email12;
    private javax.swing.JLabel LabelCliente_Email13;
    private javax.swing.JLabel LabelCliente_Email3;
    private javax.swing.JLabel LabelCliente_Email4;
    private javax.swing.JLabel LabelCliente_Email5;
    private javax.swing.JLabel LabelCliente_Nombre1;
    private javax.swing.JLabel LabelCliente_Nombre3;
    private javax.swing.JLabel LabelCliente_Nombre4;
    private javax.swing.JLabel LabelCliente_Nombre5;
    private javax.swing.JLabel LabelCliente_Nombre6;
    private javax.swing.JLabel LabelCliente_Telefono1;
    private javax.swing.JLabel LabelPaciente_Apellido;
    private javax.swing.JLabel LabelPaciente_Departamento;
    private javax.swing.JLabel LabelPaciente_Direccion;
    private javax.swing.JLabel LabelPaciente_Distrito;
    private javax.swing.JLabel LabelPaciente_Dni;
    private javax.swing.JLabel LabelPaciente_Email;
    private javax.swing.JLabel LabelPaciente_FechaNacimiento;
    private javax.swing.JLabel LabelPaciente_Genero;
    private javax.swing.JLabel LabelPaciente_Nombre;
    private javax.swing.JLabel LabelPaciente_Ocupacion;
    private javax.swing.JLabel LabelPaciente_Provincia;
    private javax.swing.JLabel LabelPaciente_Telefono;
    private javax.swing.JLabel Label_dni_medico;
    private javax.swing.JLabel Label_imagen_historia;
    private javax.swing.JLabel Labelimagen_Cliente2;
    private javax.swing.JLabel Labelimagen_Cliente3;
    private javax.swing.JLabel Labelimagen_Cliente4;
    private javax.swing.JLabel Labelimagen_Paciente;
    private javax.swing.JPanel PanelPrincipal;
    private javax.swing.JPanel PanelPrincipal1;
    private javax.swing.JPanel PanelPrincipal2;
    private javax.swing.JTabbedPane Panel_Paciente;
    private javax.swing.JPopupMenu PopupMenuHistoriaPaciente;
    private javax.swing.JPopupMenu PopupMenuMedico;
    private javax.swing.JPopupMenu PopupMenuPaciente;
    private javax.swing.JPopupMenu PopupMenuPrincipal;
    private javax.swing.JTabbedPane TabbedPaneHistoriaClinica;
    private javax.swing.JTabbedPane TabbedPaneHistoriaClinica2;
    private javax.swing.JTable TablaListadoMedico;
    public final javax.swing.JTable TablaListadoPaciente = new javax.swing.JTable();
    private javax.swing.JButton btn_AgregarCliente2;
    private javax.swing.JButton btn_Editar_Paciente;
    private javax.swing.JButton btn_Editar_medico;
    private javax.swing.JButton btn_Paciente;
    private javax.swing.JButton btn_Paciente1;
    private javax.swing.JButton btn_Paciente2;
    private javax.swing.JButton btn_Paciente3;
    private javax.swing.JButton btn_Paciente4;
    private javax.swing.JButton btn_Paciente5;
    private javax.swing.JButton btn_Paciente6;
    private javax.swing.JButton btn_Paciente7;
    private javax.swing.JButton btn_actualizar_Paciente;
    private javax.swing.JButton btn_actualizar_cita;
    private javax.swing.JButton btn_actualizar_cita1;
    private javax.swing.JButton btn_actualizar_historia;
    private javax.swing.JButton btn_actualizar_medico;
    private javax.swing.JButton btn_agregar_cita;
    private javax.swing.JButton btn_agregar_consultorio;
    private javax.swing.JButton btn_agregar_especialidad;
    private javax.swing.JButton btn_agregar_historia;
    private javax.swing.JButton btn_agregar_medico;
    private javax.swing.JButton btn_agregar_paciente;
    private javax.swing.JButton btn_agregar_pago;
    private javax.swing.JButton btn_buscarPaciente_Pago;
    private javax.swing.JButton btn_buscarPaciente_listadoAbono;
    private javax.swing.JButton btn_buscarProductoServicio_pago;
    private javax.swing.JButton btn_buscar_Cita;
    private javax.swing.JButton btn_buscar_CitaPrincipal1;
    private javax.swing.JButton btn_buscar_Paciente;
    private javax.swing.JButton btn_buscar_cita;
    private javax.swing.JButton btn_buscar_historia;
    private javax.swing.JButton btn_buscar_medico;
    private javax.swing.JButton btn_buscar_pagoListado;
    private javax.swing.JButton btn_buscarcita_pago;
    private javax.swing.JButton btn_cancelar_cita;
    private javax.swing.JButton btn_cancelar_consultorio;
    private javax.swing.JButton btn_cancelar_especialidad;
    private javax.swing.JButton btn_cancelar_historia;
    private javax.swing.JButton btn_cancelar_medico;
    private javax.swing.JButton btn_cancelar_paciente;
    private javax.swing.JButton btn_cancelar_pago;
    private javax.swing.JButton btn_editar_pago;
    private javax.swing.JButton btn_eliminar_pago;
    private javax.swing.JButton btn_exportarExcel_listadoAbono;
    private javax.swing.JButton btn_exportarExcel_listadoPago;
    private javax.swing.JButton btn_exportar_excel_principal1;
    private javax.swing.JButton btn_guardar_cita;
    private javax.swing.JButton btn_historiaclinica_principal;
    private javax.swing.JButton btn_imprimir_pago;
    private javax.swing.JButton btn_pagar_principal;
    private javax.swing.JButton btn_registrar_pago;
    private javax.swing.JButton btn_sincronizar_listadoAbono;
    private javax.swing.JButton btn_sincronizar_listadopago;
    private javax.swing.JButton btn_sincronizar_listadopago1;
    public javax.swing.JComboBox<String> comb_genero_medico;
    public javax.swing.JComboBox<String> comb_genero_paciente;
    private javax.swing.JComboBox<String> combo_consultorio_cita;
    public javax.swing.JComboBox<String> combo_especialidad_medico;
    private javax.swing.JComboBox<String> combo_estadoCita_principal;
    private com.toedter.calendar.JDateChooser combo_fechafin_principal;
    private com.toedter.calendar.JDateChooser combo_fechainicio_principal;
    private javax.swing.JComboBox<String> combo_hora_cita;
    private javax.swing.JComboBox<String> combo_medico_cita;
    private javax.swing.JComboBox<String> combo_medico_historia;
    private javax.swing.JComboBox<String> combo_tipoconsulta_cita;
    private javax.swing.JComboBox<String> combo_tipodocumento_Pago;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel71;
    private javax.swing.JPanel jPanel72;
    private javax.swing.JPanel jPanel73;
    private javax.swing.JPanel jPanel74;
    private javax.swing.JPanel jPanel75;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane10;
    private javax.swing.JTabbedPane jTabbedPane11;
    private javax.swing.JTabbedPane jTabbedPane12;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTabbedPane jTabbedPane8;
    private javax.swing.JTabbedPane jTabbedPane9;
    private javax.swing.JTabbedPane jTabbedPane_Principal;
    private com.toedter.calendar.JDateChooser jadate_fecha_historia;
    private javax.swing.JComboBox<String> jcombo_enfermedad_historia;
    private javax.swing.JComboBox<String> jcombo_hemorragias_historia;
    private javax.swing.JComboBox<String> jcombo_medicamento_historia;
    private com.toedter.calendar.JDateChooser jdate_fecha_cita;
    private com.toedter.calendar.JDateChooser jdate_fecha_pago;
    private com.toedter.calendar.JDateChooser jdate_fechafin_listadoAbono;
    private com.toedter.calendar.JDateChooser jdate_fechafin_listadopago;
    private com.toedter.calendar.JDateChooser jdate_fechainicio_listadoAbono;
    private com.toedter.calendar.JDateChooser jdate_fechainicio_listadoPago;
    private com.toedter.calendar.JDateChooser jdate_fechanacimiento_medico;
    private com.toedter.calendar.JDateChooser jdate_fechatratamiento_historia;
    private javax.swing.JPanel jpanel_cita;
    private javax.swing.JLabel label_apellidos_medico;
    private javax.swing.JLabel label_direccion_medico;
    private javax.swing.JLabel label_email_medico;
    private javax.swing.JLabel label_especialidad_medico;
    private javax.swing.JLabel label_fechanacimiento_medico;
    private javax.swing.JLabel label_genero_medico;
    private javax.swing.JLabel label_imagen_medico;
    private javax.swing.JLabel label_nombre_consultorio;
    private javax.swing.JLabel label_nombres_medico;
    private javax.swing.JLabel label_numero_consultorio;
    private javax.swing.JLabel label_telefono_medico;
    private javax.swing.JMenuItem opcEditarCita;
    private javax.swing.JMenuItem opcEditarHistoria;
    private javax.swing.JMenuItem opcEliminarCita;
    private javax.swing.JMenuItem opcEliminarPopMedico;
    private javax.swing.JMenuItem opcEliminarPopPaciente;
    private javax.swing.JMenuItem opcRealizarPago;
    private javax.swing.JPanel pnlPaciente;
    private javax.swing.JPopupMenu popupMenuCita;
    private javax.swing.JTable tablaListadoCita;
    private javax.swing.JTable tablaListadoCitaPrincipal;
    private javax.swing.JTable tablaListadoEspecialidad;
    private javax.swing.JTable tabla_historia_paciente;
    private javax.swing.JTable tabla_listadoAbono;
    private javax.swing.JTable tabla_listadoPago;
    private javax.swing.JTable tabla_pago;
    private javax.swing.JTable tablalistadoConsultorio;
    private javax.swing.JTextField txt_Apellidos_medico;
    private javax.swing.JTextField txt_BuscarCliente;
    private javax.swing.JTextField txt_BuscarPacientePrincipal;
    private javax.swing.JTextField txt_Buscar_cita;
    private javax.swing.JTextField txt_Buscar_medico;
    private javax.swing.JTextField txt_Departamento_historia;
    private javax.swing.JTextField txt_Dni_medico;
    private javax.swing.JTextField txt_Nombres_medico;
    private javax.swing.JTextField txt_acuenta_pago;
    private javax.swing.JTextField txt_apellidos_paciente;
    public static javax.swing.JTextField txt_cantidad_producto_pago;
    public static javax.swing.JTextField txt_codigo_Producto_pago;
    public static javax.swing.JTextField txt_costo_producto_pago;
    private javax.swing.JTextField txt_costo_tratamiento;
    private javax.swing.JTextField txt_departamento_paciente;
    public static javax.swing.JTextField txt_descripcion_producto_pago;
    public static javax.swing.JTextField txt_descuento_pago;
    private javax.swing.JTextPane txt_diagnostico_historia;
    private javax.swing.JTextField txt_direccion_historia;
    private javax.swing.JTextField txt_direccion_medico;
    private javax.swing.JTextField txt_direccion_paciente;
    private javax.swing.JTextField txt_distrito_historia;
    private javax.swing.JTextField txt_distrito_paciente;
    public static javax.swing.JTextField txt_dniPaciente_Pago;
    private javax.swing.JTextField txt_dni_Historia;
    private javax.swing.JTextField txt_dni_listadoAbono;
    private javax.swing.JTextField txt_dni_listadoPago;
    private javax.swing.JTextField txt_dni_paciente;
    private javax.swing.JTextField txt_dnipaciente_cita;
    private javax.swing.JTextField txt_email_historia;
    private javax.swing.JTextField txt_email_medico;
    private javax.swing.JTextField txt_email_paciente;
    private javax.swing.JTextPane txt_enfermedad_historia;
    private javax.swing.JTextField txt_fechanacimiento_historia;
    private javax.swing.JTextField txt_genero_historia;
    private javax.swing.JTextPane txt_hemorragias_historia;
    private javax.swing.JTextField txt_idcita_cita;
    public static javax.swing.JTextField txt_idcita_pago;
    private javax.swing.JTextPane txt_medicamento_historia;
    public static javax.swing.JTextField txt_monto_pago;
    private javax.swing.JTextField txt_nombreCompleto_cita;
    private javax.swing.JTextField txt_nombre_consultorio;
    private javax.swing.JTextField txt_nombre_especialidad;
    private javax.swing.JTextField txt_nombrepaciente_Historia;
    public static javax.swing.JTextField txt_nombrepaciente_pago;
    private javax.swing.JTextField txt_nombres_paciente;
    private javax.swing.JTextField txt_numero_consultorio;
    private javax.swing.JTextField txt_numero_historia;
    private javax.swing.JTextField txt_numeroserie_Pago;
    private javax.swing.JTextField txt_observacion_especialidad;
    private javax.swing.JTextPane txt_observaciones_cita;
    private javax.swing.JTextField txt_ocupacion_historia;
    private javax.swing.JTextField txt_ocupacion_paciente;
    private javax.swing.JTextField txt_precio_cita;
    private javax.swing.JTextField txt_provincia_historia;
    private javax.swing.JTextField txt_provincia_paciente;
    private javax.swing.JTextField txt_ruc_pago;
    private javax.swing.JTextField txt_saldo_pago;
    private javax.swing.JTextField txt_saldo_tratamiento;
    private javax.swing.JTextField txt_serie_Pago;
    public static javax.swing.JTextField txt_subtotal_pago;
    private javax.swing.JTextField txt_telefono_historia;
    private javax.swing.JTextField txt_telefono_medico;
    private javax.swing.JTextField txt_telefono_paciente;
    public static javax.swing.JTextField txt_total_pago;
    private javax.swing.JTextField txt_tratamiento_historia;
    // End of variables declaration//GEN-END:variables

    private void mostrarListado(Vector lista) {

        DefaultTableModel tableModel = new DefaultTableModel();
        String titulo[] = {"CODICO PRODUCTO", "DESCRIPCION", "CANTIDAD", "COSTO", "ACUENTA", "SALDO", "SUB TOTAL"};
        tableModel.setColumnIdentifiers(titulo);
        this.tabla_pago.setModel(tableModel);

        Object[] datos = new Object[7];
        pago obj;
        float suma = 0;

        for (int i = 0; i < lista.size(); i++) {
            obj = (pago) lista.elementAt(i);
            datos[0] = obj.getIdtipoconsulta();
            datos[1] = obj.getDescripcion();
            datos[2] = obj.getCantidad();
            datos[3] = obj.getCosto();
            datos[4] = obj.getAcuenta();
            datos[5] = obj.getSaldo();
            datos[6] = obj.getMonto();
            float result = Float.parseFloat(obj.getMonto());
            suma = suma + result;

            tableModel.addRow(datos);
        }

        tabla_pago.setModel(tableModel);
        txt_subtotal_pago.setText(String.valueOf(suma));
        txt_total_pago.setText(String.valueOf(suma));

    }

}
