/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UIMain.java
 *
 * Created on Sep 7, 2013, 2:01:42 AM
 */

package com.uni.compiler.UI;

import com.uni.compiler.lexicAnalizer.LexicAnalizer;
import com.uni.compiler.lexicAnalizer.Token;
import com.uni.compiler.parser.Parser;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;

/**
 *
 */
public class UIMain extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    //File path
    private String path;
    private String archivo;
    
    private LexicAnalizer la;
    private Parser parser;

	{
	//Set Look & Feel
            try {
                    javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch(Exception e) {
            }
	}

    /** Creates new form Visual */
    public UIMain() {

        initComponents();
        
        List.setVisible(false);
       
        addWindowListener(new WindowAdapter() {
        @Override
        public void windowOpened(WindowEvent e) {
        setExtendedState(MAXIMIZED_BOTH);
        }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFileChooser1 = new javax.swing.JFileChooser();
        jSplitPane1 = new javax.swing.JSplitPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jSplitPane3 = new javax.swing.JSplitPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jPanel3 = new javax.swing.JPanel();
        jSplitPane4 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextPane3 = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        ButtonST = new javax.swing.JToggleButton();
        ButtonRWT = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        List = new javax.swing.JList();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Lexico + Parser '86");

        jSplitPane2.setDividerLocation(300);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jSplitPane3.setDividerLocation(45);
        jSplitPane3.setDividerSize(1);
        jSplitPane3.setEnabled(false);

        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setRows(10);
        jScrollPane4.setViewportView(jTextArea1);

        jSplitPane3.setRightComponent(jScrollPane4);

        jTextPane2.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        jTextPane2.setBorder(null);
        jTextPane2.setEditable(false);
        jTextPane2.setEnabled(false);
        jScrollPane3.setViewportView(jTextPane2);

        jSplitPane3.setLeftComponent(jScrollPane3);

        jScrollPane2.setViewportView(jSplitPane3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
        );

        jSplitPane2.setTopComponent(jPanel2);

        jSplitPane4.setDividerLocation(500);

        jTextPane1.setEditable(false);
        jScrollPane1.setViewportView(jTextPane1);

        jSplitPane4.setLeftComponent(jScrollPane1);

        jTextPane3.setEditable(false);
        jScrollPane5.setViewportView(jTextPane3);

        jSplitPane4.setRightComponent(jScrollPane5);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(jPanel3);

        jSplitPane1.setRightComponent(jSplitPane2);

        jButton1.setText("Start!!");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        ButtonST.setText("Show Simbol Table");
        ButtonST.setEnabled(false);
        ButtonST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSTActionPerformed(evt);
            }
        });

        ButtonRWT.setText("Show R. Words Table");
        ButtonRWT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRWTActionPerformed(evt);
            }
        });

        List.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        List.setVisibleRowCount(7);
        jScrollPane6.setViewportView(List);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(98, 98, 98))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(ButtonRWT, javax.swing.GroupLayout.PREFERRED_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(ButtonST, javax.swing.GroupLayout.PREFERRED_SIZE, 138, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton1)
                .addGap(23, 23, 23)
                .addComponent(ButtonST)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonRWT)
                .addGap(13, 13, 13)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jMenu1.setText("File");

        jMenuItem1.setText("Open");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        jFrame1.show();
        jFrame1.setBounds(100, 100, 530, 370);
        int retVal = jFileChooser1.showOpenDialog(this);
        switch( retVal )
        {
            case JFileChooser.CANCEL_OPTION:
                break;
            case JFileChooser.APPROVE_OPTION:
                path =  jFileChooser1.getSelectedFile().getAbsolutePath();
                this.setTextArea1(path);
                
                StringBuilder fileData = new StringBuilder();
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(
                    new FileReader(this.path));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(UIMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                        char[] buf = new char[1024];
                        int numRead = 0;
                try {
                    while((numRead=reader.read(buf)) != -1){
                        String readData = String.valueOf(buf, 0, numRead);
                        fileData.append(readData);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(UIMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(UIMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                String s = fileData.toString();
                
                
                
                /*
                String s = new String("");
                this.codigo = this.archivo.archivo();
                for (int i = 0; i < codigo.size(); i++){
                   s += codigo.elementAt(i)+ "\n";
                }*/
                this.setTextArea1(s);
                this.jTextPane2.setText("");
                
                String[] lines = s.split("\r\n|\r|\n");
                this.setRows(lines.length);

                //limpio los paneles
                this.jTextPane1.setText("");
                this.jTextPane3.setText("");

                this.jTextArea1.updateUI();
                this.jTextPane2.updateUI();
                this.jScrollPane2.updateUI();

                //crear el analizador lexico
                
                

            break;
        }

        this.jButton1.setEnabled(true);
        setTitle("Compiladores 2013 - " + path);
        jFrame1.hide();
       
    }//GEN-LAST:event_jMenuItem1ActionPerformed
    private int searchLine(JTextPane panel, int nro){
        //busco numero de linea para posicionar el cursor
        return nro;
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        this.ButtonST.setEnabled(true);        
        
        this.la = new LexicAnalizer(this.path);
                
        parser = new Parser(la,this,false);
                
        //ejecutar el parser
        //parser.run();
        
        
        //Panel de Errores
        Style errorPanel = new Style(this.jTextPane1);
        //Panel de Lexema
        Style lexPanel = new Style(this.jTextPane3);
        //Panel para marcar errores
        Style linePanel = new Style(this.jTextPane2);
        
        
        //pido los tokens
        
        Token t;
        try {
            while ((t = this.la.getToken()) != null && !t.getType().equals("EOF")) {

                if (t.hasError()){
                    errorPanel.setNormal("Error en linea " + t.getLine() +": " + t.getError());
                    errorPanel.newLine();
                    // Hacer dibujito de error en la linea
                    //linePanel.setCursor(searchLine(jTextPane2, t.getLinea()));
                    //linePanel.setIcon("..\\icoError11.png");
                }
                if (t.hasWarning()){
                    errorPanel.setNormal("Warning en linea " + t.getLine() +": " + t.getWarning());
                    errorPanel.newLine();
                    // Hacer dibujito de warning en la linea
                    //linePanel.setCursor(searchLine(jTextPane2,t.getLinea()));
                    //linePanel.setIcon("..\\icoWarning10.png");
                }
                if(!t.hasError()) {
                    if(this.la.getReservedWordsTable().containsKey(t.getToken())){
                        lexPanel.setCursiva("linea " + t.getLine() + ": Palabra Reservada : ");
                        lexPanel.setNegrita(t.getToken());
                        lexPanel.newLine();
                    }else{
                        if(t.getType().equals("linea " + t.getLine() + ": Identificador")){
                            lexPanel.setCursiva(t.getType()+ " : ");
                            lexPanel.setNegrita("<" + t.getToken() +">");
                            lexPanel.newLine();

                        }else{
                            if(!t.getType().equals("")){
                                lexPanel.setCursiva("linea " + t.getLine() + ": " + t.getType() + " : ");
                                lexPanel.setNegrita(t.getToken());
                                lexPanel.newLine();
                            } else {
                                lexPanel.setNegrita("linea " + t.getLine() + ": " + t.getToken());
                                lexPanel.newLine();
                            }
                        }
                    }   
                }
            }
        } catch (Exception e) {
            System.out.println("An exception was trhown \n - Cause: " + e.getCause()
                             + "\n Message: " + e.getMessage());
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void ButtonRWTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRWTActionPerformed

        DefaultListModel modelo = new DefaultListModel();
        
        if(ButtonRWT.isSelected()){
            this.ButtonST.setSelected(false);

            modelo.addElement("if");
            modelo.addElement("else");
            modelo.addElement("then");
            modelo.addElement("begin");
            modelo.addElement("end");
            modelo.addElement("print");
            modelo.addElement("function");
            modelo.addElement("return");
            modelo.addElement("import");
            modelo.addElement("int");
            modelo.addElement("loop");
            modelo.addElement("until");
            
            List.setModel(modelo);
            List.setVisible(true);
        }
        //cargo la lista de palabras reservadas    
        else{
            List.setVisible(false);
        }
    }//GEN-LAST:event_ButtonRWTActionPerformed

    private void ButtonSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSTActionPerformed
        DefaultListModel modelo = new DefaultListModel();

        if (this.ButtonST.isSelected()){
            this.ButtonRWT.setSelected(false);
            
            Iterator it = this.la.getSymbolsTable().iterator();
            while (it.hasNext()) {
                modelo.addElement( ((String) it.next()) );
            }
            List.setModel(modelo);
            List.setVisible(true);
        }
        else
            this.List.setVisible(false);
    }//GEN-LAST:event_ButtonSTActionPerformed

    public String getPath(){
        return this.path;
    }

    public void setTextArea1(String s) {
        this.jTextArea1.setText(s);
        
    }

    public void setRows(int i){
        this.jTextArea1.setRows(i);
         Style test = new Style(this.jTextPane2);

        for (int j = 0; j < i; j++){
            test.setNormal((new Integer (j+1)).toString());
            test.newLine();
        }
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
   
        java.awt.EventQueue.invokeLater(new Runnable() {
         
            @Override
            public void run() {
                new UIMain().setVisible(true);
               
            }

        });
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton ButtonRWT;
    private javax.swing.JToggleButton ButtonST;
    private javax.swing.JList List;
    private javax.swing.JButton jButton1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextPane jTextPane3;
    // End of variables declaration//GEN-END:variables

    public JTextPane getjTextPane1() {
        return jTextPane1;
    }

    public JTextPane getjTextPane2() {
        return jTextPane2;
    }

    public JTextPane getjTextPane3() {
        return jTextPane3;
    }

}
