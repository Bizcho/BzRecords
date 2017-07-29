/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.disco;

import conexion.Conexciones;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author bizcho
 */
public class DiscoAlta extends JFrame{
    private JTextField tTitulo, tTipo, tGenero, tNoCanciones;
    private JComboBox cContrato;
    private JLabel lTitulo, lTipo, lContrato, lGenero, lNoCanciones;
    private JButton bNuevo, bGuardar, bCancelar, bSalir;
    private JPanel pLabels, pComps, pDefault, pGroup;
    private String fecha = "";
    private Container c;
    
    public DiscoAlta(){
        super("Disco alta");
        //inicializacion de componentes
        tTitulo       = new JTextField(12);
        tTipo         = new JTextField(12);
        tGenero       = new JTextField(12);
        tNoCanciones  = new JTextField(12);
        
        try{
            cContrato = new JComboBox(Conexciones.getData(Conexciones.CONTRATO));
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(this, sqle.getMessage(), "Error de SQL", JOptionPane.ERROR_MESSAGE);
        }catch(IllegalArgumentException iae){
            JOptionPane.showMessageDialog(this, iae.getMessage(), "Error de argumentos", JOptionPane.ERROR_MESSAGE);
        }
        
        lTitulo       = new JLabel ("Titulo");
        lTipo         = new JLabel ("Tipo");
        lContrato     = new JLabel ("Contrato");
        lGenero       = new JLabel ("Genero");
        lNoCanciones  = new JLabel ("Cantidad de canciones");
        
        bNuevo     = new JButton("Nuevo");
        bGuardar   = new JButton("Guardar");
        bCancelar  = new JButton("Cancelar");
        bSalir     = new JButton("Salir");
        
        bloquear();
        // configuracion de paneles
        pGroup = new JPanel();
        pGroup.setLayout(new BorderLayout());
        
        pLabels = new JPanel();
        pLabels.setLayout(new GridLayout(5, 1, 5, 5));
        
        pComps = new JPanel();
        pComps.setLayout(new GridLayout(5, 1, 5, 5));
        
        pDefault = new JPanel();
        pDefault.setLayout(new BoxLayout(pDefault, BoxLayout.LINE_AXIS));
        
        // agregando labels al panel de labels
        pLabels.add(lTitulo);
        pLabels.add(lTipo);
        pLabels.add(lContrato);
        pLabels.add(lGenero);
        pLabels.add(lNoCanciones);
        
        // agregando textfields al panel de text fields
        pComps.add(tTitulo);
        pComps.add(tTipo);
        pComps.add(cContrato);
        pComps.add(tGenero);
        pComps.add(tNoCanciones);
        
        //agregando botonoes al panel default
        pDefault.add(Box.createHorizontalGlue());
        pDefault.add(bNuevo);
        pDefault.add(Box.createRigidArea(new Dimension(3, WIDTH)));
        pDefault.add(bGuardar);
        pDefault.add(Box.createRigidArea(new Dimension(3, WIDTH)));
        pDefault.add(bCancelar);
        pDefault.add(Box.createRigidArea(new Dimension(3, WIDTH)));
        pDefault.add(bSalir);
        pDefault.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        
        //agregando paneles de labels y text fields al panel grupo
        pGroup.add(pLabels, BorderLayout.WEST);
        pGroup.add(pComps, BorderLayout.EAST);
        pGroup.add(Box.createRigidArea(new Dimension(10,10)), BorderLayout.CENTER);
        pGroup.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // configuracion del contenedor proncipal
        c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(pGroup, BorderLayout.CENTER);
        c.add(pDefault, BorderLayout.SOUTH);
        
        try{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            
        }
        
        this.pack();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        //listeners
        
        bSalir.addActionListener((ActionEvent ae) -> {
            this.dispose();
        });//fin actionlistener salir
        
        bNuevo.addActionListener((ActionEvent ae) -> {
            desbloquear();
            tTitulo.requestFocus();
        });// fin action listener nuevo
        
        bGuardar.addActionListener((ActionEvent ae) -> {
            guardar();
        });// fin action listener guardar
        
        bCancelar.addActionListener((ActionEvent ae) -> {
            bloquear();
        });// fin action listener
        
    }
    
    public final void bloquear(){
        tTitulo.setText("");
        tTipo.setText("");
        cContrato.setSelectedIndex(0);
        tGenero.setText("");
        tNoCanciones.setText("");
        
        tTitulo.setEnabled(false);
        tTipo.setEnabled(false);
        cContrato.setEnabled(false);
        tGenero.setEnabled(false);
        tNoCanciones.setEnabled(false);
        bGuardar.setEnabled(false);
        bCancelar.setEnabled(false);
    }
    
    public final void desbloquear(){
        tTitulo.setEnabled(true);
        tTipo.setEnabled(true);
        cContrato.setEnabled(true);
        tGenero.setEnabled(true);
        tNoCanciones.setEnabled(true);
        bGuardar.setEnabled(true);
        bCancelar.setEnabled(true);
    }

    private void guardar() {
        boolean aux = true;
        String titulo = tTitulo.getText();
        String tipo = tTipo.getText();
        String sContrato = (String)cContrato.getSelectedItem();
        String genero = tGenero.getText();
        String noCanciones = tNoCanciones.getText();
        try{
            if ("".equals(titulo) || "".equals(tipo) || "".equals(sContrato) || "".equals(genero) || "".equals(noCanciones)){
                throw new IllegalArgumentException("los campos no pueden estar vacios");
            }
            Conexciones.insert(Conexciones.DISCO, titulo, tipo, sContrato, genero, noCanciones);
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(this, sqle.getMessage(), "Error de SQL", JOptionPane.ERROR_MESSAGE);
            aux = false;
        }catch(IllegalArgumentException iae){
            JOptionPane.showMessageDialog(this, iae.getMessage(), "Error de argumentos", JOptionPane.ERROR_MESSAGE);
            aux = false;
        }
        if (aux) {
            bloquear();
            JOptionPane.showMessageDialog(null, "exito!");
        }
    }
    
    public static void main(String[] args) {
        DiscoAlta app = new DiscoAlta();
    }
}
