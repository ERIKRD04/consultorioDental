/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ERIK RUBIO
 */
public class TextFieldEvent {
    
    public void textKeyPress(KeyEvent evt){
    //declaramos una variable y le asignamos unevento
    char car = evt.getKeyChar();
    if ((car<'a' || car > 'z') && (car < 'A'|| car> 'Z')&&
           (car!=(char)KeyEvent.VK_BACK_SPACE)&&(car!=(char)KeyEvent.VK_SPACE)){
    evt.consume();
    } 
    }
    
    public void numberKeyPress(KeyEvent evt){
    //declaramos una variable y le asignamos unevento
    char car = evt.getKeyChar();
    if ((car<'0' || car > '9') && (car!=(char)KeyEvent.VK_BACK_SPACE)){
    evt.consume();
    } 
    
    }
       public boolean isEmail(String correo){
        Pattern pat = Pattern.compile("^[\\w\\-\\_\\+]+(\\.[\\w\\-\\_]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$");
        Matcher mat = pat.matcher(correo);
        return mat.find();
    }
       
    public void solomayusculas(KeyEvent evt){
    char c=evt.getKeyChar();
    if(Character.isLowerCase(c)){
    String cad = (""+c).toUpperCase();
    c=cad.charAt(0);
    evt.setKeyChar(c);
        
    }
    
    
    }
       
      
}