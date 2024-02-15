package Views;

import conexionSQLServer.consultaConexion;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import library.Objectos;

/**
 *
 * @author ERIK RUBIO
 */
public class pacientesVm extends consultaConexion{

    public String _accion = "inser";
    public final ArrayList<JLabel> _Label;
    public final ArrayList<JTextField> _TextField;
    
    public pacientesVm(Object[] objects, ArrayList<JLabel> label, ArrayList<JTextField> textField) {
        _Label = label;
        _TextField = textField;
        
    conexion();

    }


    public final void restablecer(){
        _accion = "inser";
    _TextField.get(0).setText("");
    _TextField.get(1).setText("");
    _TextField.get(2).setText("");
    _TextField.get(3).setText("");
    _TextField.get(4).setText("");
    _TextField.get(5).setText("");
    _TextField.get(6).setText("");
    _TextField.get(7).setText("");
    _TextField.get(8).setText("");
    _TextField.get(9).setText("");
    
     _Label.get(0).setForeground(new Color(102,102,102));
    _Label.get(0).setText("Ingrese el Dni");
    _Label.get(1).setText("Ingrese el Apellido");
    _Label.get(1).setForeground(new Color(102,102,102));
    _Label.get(2).setText("Ingrese Nombre");
    _Label.get(2).setForeground(new Color(102,102,102));
    _Label.get(3).setText("Ingrese Telefono");
    _Label.get(3).setForeground(new Color(102,102,102));
    _Label.get(4).setText("Ingrese Ocupación");
    _Label.get(4).setForeground(new Color(102,102,102));
    _Label.get(5).setText("Ingrese Departamento");
    _Label.get(5).setForeground(new Color(102,102,102));
    _Label.get(6).setText("Ingrese Provincia");
    _Label.get(6).setForeground(new Color(102,102,102));
    _Label.get(7).setText("Ingrese Distrito");
    _Label.get(7).setForeground(new Color(102,102,102));
    _Label.get(8).setText("Ingrese la Direccion");
    _Label.get(8).setForeground(new Color(102,102,102));
    _Label.get(9).setText("Ingrese un email Valido");
    _Label.get(9).setForeground(new Color(102,102,102));
     
    }

    public void ValidadorRegistrarpaciente() {
        if (_TextField.get(0).getText().equals("")) {
            _Label.get(0).setText("Ingrese el Dni");
            _Label.get(0).setForeground(Color.RED);
            _TextField.get(0).requestFocus();

        } else {
            if (_TextField.get(1).getText().equals("")) {
                _Label.get(1).setText("Ingrese el Apellido");
                _Label.get(1).setForeground(Color.RED);
                _TextField.get(1).requestFocus();

            } else {
                if (_TextField.get(2).getText().equals("")) {
                    _Label.get(2).setText("Ingrese Nombre");
                    _Label.get(2).setForeground(Color.RED);
                    _TextField.get(2).requestFocus();
                
                        } else {
                            if (_TextField.get(3).getText().equals("")) {
                                _Label.get(3).setText("Ingrese Telefono");
                                _Label.get(3).setForeground(Color.RED);
                                _TextField.get(3).requestFocus();
                            } else {
                                if (_TextField.get(4).getText().equals("")) {
                                    _Label.get(4).setText("Ingrese Ocupación");
                                    _Label.get(4).setForeground(Color.RED);
                                    _TextField.get(4).requestFocus();
                                } else {

                                    if (_TextField.get(5).getText().equals("")) {
                                        _Label.get(5).setText("Ingrese Departamento");
                                        _Label.get(5).setForeground(Color.RED);
                                        _TextField.get(5).requestFocus();
                                    } else {

                                        if (_TextField.get(6).getText().equals("")) {
                                            _Label.get(6).setText("Ingrese Provincia");
                                            _Label.get(6).setForeground(Color.RED);
                                            _TextField.get(6).requestFocus();

                                        } else {

                                            if (_TextField.get(7).getText().equals("")) {
                                                _Label.get(7).setText("Ingrese Distrito");
                                                _Label.get(7).setForeground(Color.RED);
                                                _TextField.get(7).requestFocus();
                                            } else {

                                                if (_TextField.get(8).getText().equals("")) {
                                                    _Label.get(8).setText("Ingrese la Direccion");
                                                    _Label.get(8).setForeground(Color.RED);
                                                    _TextField.get(8).requestFocus();

                                                }

                                                if (!Objectos.eventos.isEmail(_TextField.get(9).getText())) {
                                                    _Label.get(9).setText("Ingrese un email Valido");
                                                    _Label.get(9).setForeground(Color.RED);
                                                    _TextField.get(9).requestFocus();

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    

                    
