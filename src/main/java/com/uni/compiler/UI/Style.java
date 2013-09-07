package com.uni.compiler.UI;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pablo
 */
public final class Style {

    JTextPane panel;
    SimpleAttributeSet attrs = new SimpleAttributeSet();

    private void insertaNuevaLinea(JTextPane editor)
        throws BadLocationException
    {
      // Atributos null
        editor.getStyledDocument().insertString(editor.getStyledDocument().getLength(),System.getProperty("line.separator"), null);
    }

    public void newLine(){
        try {
            insertaNuevaLinea(panel);
        } catch (BadLocationException ex) {
            Logger.getLogger(Style.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Style (JTextPane panel){
        this.panel=panel;
    }

    public void setNormal(String text){
            // En normal
            StyleConstants.setItalic(attrs, false);
            StyleConstants.setBold(attrs, false);
        try {
            panel.getStyledDocument().insertString(panel.getStyledDocument().getLength(), text, attrs);
            //insertaNuevaLinea(panel);
        } catch (BadLocationException ex) {
            Logger.getLogger(Style.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setNegrita(String text){
            // En negrita
            StyleConstants.setBold(attrs, true);
            StyleConstants.setItalic(attrs, false);
        try {
            panel.getStyledDocument().insertString(panel.getStyledDocument().getLength(), text, attrs);
            //insertaNuevaLinea(panel);
        } catch (BadLocationException ex) {
            Logger.getLogger(Style.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCursiva(String text){
        // En cursiva
            StyleConstants.setItalic(attrs, true);
            StyleConstants.setBold(attrs, false);
        try {
            panel.getStyledDocument().insertString(panel.getStyledDocument().getLength(), text, attrs);
            //insertaNuevaLinea(panel);
        } catch (BadLocationException ex) {
            Logger.getLogger(Style.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setIcon(String pathIco){
        // Un icono
            ImageIcon icono = new ImageIcon(pathIco);
            panel.insertIcon(icono);
    }
    public void setCursor(int renglon){
        // Ponemos el cursor al final del texto
         panel.setCaretPosition(renglon);
    }

}

