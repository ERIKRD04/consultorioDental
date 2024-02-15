/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultorio;

import Views.Sistema;
import java.awt.Frame;
import javax.swing.UIManager;

/**
 *
 * @author ERIK RUBIO
 */
public class Consultorio {

  public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
     
        }
        Sistema sistema= new Sistema();
        sistema.setExtendedState(Frame.MAXIMIZED_BOTH);
        sistema.setVisible(true);
    }
}
