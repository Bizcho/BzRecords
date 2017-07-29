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
public class ProductorCambios extends JFrame {
    private JTextField tNombre;
    private JLabel lNombre;
    private JButton bGuardar, bCancelar;
    private JPanel pLabels, pTxts, pDefault, pGroup;
    private Container c;
    private ProductorConsulta consulta;
    private Object[] datos;
    
    public ProductorCambios(ProductorConsulta consulta, Object[] datos){
        super("Productor cambios");
        
        this.datos = datos;
        this.consulta = consulta;
        //inicializacion de componentes
        tNombre = new JTextField(15);
        tNombre.setText((String)datos[1]);
        
        lNombre = new JLabel("Nombre");
        
        bGuardar = new JButton("Guardar");
        bCancelar = new JButton("Cancelar");
        
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
        pDefault.add(bGuardar);
        pDefault.add(Box.createRigidArea(new Dimension(3, WIDTH)));
        pDefault.add(bCancelar);
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
        //lsteners
        bCancelar.addActionListener((ActionEvent e) -> {
            this.dispose();
        });//fin actionlistener cancelar
        bGuardar.addActionListener((ActionEvent e) -> {
            if (guardar()){
                JOptionPane.showMessageDialog(null, "Exito!");
                consulta.tabla.setModel(consulta.getModelo(""));
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "no se completo la transaccion");
            }
        });
    }
    private boolean guardar(){
        boolean aux = true;
        String nombre = tNombre.getText().trim();
        try{
            if("".equals(nombre)){
                throw new IllegalArgumentException("los campos no pueden estar vacios");
            }
            if (!nombre.equals((String)datos[1])) {
                Conexciones.update(Conexciones.PRODUCTOR,(String)datos[0],nombre);
            }
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(this, sqle.getMessage(), "Error de SQL", JOptionPane.ERROR_MESSAGE);
            aux = false;
        }catch(IllegalArgumentException iae){
            JOptionPane.showMessageDialog(this, iae.getMessage(), "Error de argumentos", JOptionPane.ERROR_MESSAGE);
            aux = false;
        }
        return aux;
    }
}
