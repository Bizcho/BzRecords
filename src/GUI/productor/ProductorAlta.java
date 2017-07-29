/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.productor;

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
public class ProductorAlta extends JFrame {
    private JTextField tNombre;
    private JLabel lNombre;
    private JButton bNuevo, bGuardar, bCancelar, bSalir;
    private JPanel pLabels, pTxts, pDefault, pGroup;
    private Container c;
    
    public ProductorAlta(){
        super("Productor");
        //inicializacion de componentes
        tNombre = new JTextField(15);
        
        lNombre = new JLabel("Nombre");
        
        bNuevo  = new JButton("Nuevo");
        bGuardar = new JButton("Guardar");
        bCancelar = new JButton("Cancelar");
        bSalir = new JButton("Salir");
        
        bloquear();
        // configuracion de paneles
        pGroup = new JPanel();
        pGroup.setLayout(new BorderLayout());
        
        pLabels = new JPanel();
        pLabels.setLayout(new GridLayout(1, 1, 5, 5));
        
        pTxts = new JPanel();
        pTxts.setLayout(new GridLayout(1, 1, 5, 5));
        
        pDefault = new JPanel();
        pDefault.setLayout(new BoxLayout(pDefault, BoxLayout.LINE_AXIS));
        
        // agregando labels al panel de labels
        pLabels.add(lNombre);
        
        // agregando textfields al panel de text fields
        pTxts.add(tNombre);
        
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
        pGroup.add(pTxts, BorderLayout.EAST);
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
        
        bSalir.addActionListener((ActionEvent e) -> {
            this.dispose();
        });//fin actionlistener salir
        
        bNuevo.addActionListener((ActionEvent e) -> {
            desbloquear();
            tNombre.requestFocus();
        });// fin action listener nuevo
        
        bGuardar.addActionListener((ActionEvent e) -> {
            guardar();
        });// fin action listener guardar
        
        bCancelar.addActionListener((ActionEvent e) -> {
            bloquear();
        });// fin action listener
    }
    public final void bloquear(){
        tNombre.setText("");
        tNombre.setEnabled(false);
        bGuardar.setEnabled(false);
        bCancelar.setEnabled(false);
    }
    
    public final void desbloquear(){
        tNombre.setEnabled(true);
        bGuardar.setEnabled(true);
        bCancelar.setEnabled(true);
    }
    private void guardar(){
        boolean aux = true;
        String nombre = tNombre.getText().trim();
        try{
            if("".equals(nombre)){
                throw new IllegalArgumentException("los campos no pueden estar vacios");
            }
            Conexciones.insert(Conexciones.PRODUCTOR, nombre);
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
        ProductorAlta app = new ProductorAlta();
    }
}
