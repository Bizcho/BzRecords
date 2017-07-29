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
public class DiscoCambios extends JFrame{
    private JTextField tTitulo, tTipo, tGenero, tNoCanciones;
    private JComboBox cContrato;
    private JLabel lTitulo, lTipo, lContrato, lGenero, lNoCanciones;
    private JButton bGuardar, bCancelar;
    private JPanel pLabels, pComps, pDefault, pGroup;
    private String fecha = "";
    private Container c;
    private Object[] datos;
    private DiscoConsulta consulta;
    
    public DiscoCambios(DiscoConsulta consulta, Object[] datos){
        super("Disco cambios");
        
        this.datos = datos;
        this.consulta = consulta;
        //inicializacion de componentes
        tTitulo       = new JTextField(12);
        tTipo         = new JTextField(12);
        tGenero       = new JTextField(12);
        tNoCanciones  = new JTextField(12);
        
        tTitulo.setText((String)datos[1]);
        tTipo.setText((String)datos[2]);
        tGenero.setText((String)datos[4]);
        tNoCanciones.setText((String)datos[5]);
        
        try{
            cContrato = new JComboBox(Conexciones.getData(Conexciones.CONTRATO));
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(this, sqle.getMessage(), "Error de SQL", JOptionPane.ERROR_MESSAGE);
        }catch(IllegalArgumentException iae){
            JOptionPane.showMessageDialog(this, iae.getMessage(), "Error de argumentos", JOptionPane.ERROR_MESSAGE);
        }
        
        cContrato.setSelectedItem(datos[3]);
        
        lTitulo       = new JLabel ("Titulo");
        lTipo         = new JLabel ("Tipo");
        lContrato     = new JLabel ("Contrato");
        lGenero       = new JLabel ("Genero");
        lNoCanciones  = new JLabel ("Cantidad de canciones");
        
        bGuardar   = new JButton("Guardar");
        bCancelar  = new JButton("Cancelar");
        
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
        pDefault.add(bGuardar);
        pDefault.add(Box.createRigidArea(new Dimension(3, WIDTH)));
        pDefault.add(bCancelar);
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
    private boolean guardar() {
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
            if (!titulo.equals((String)datos[1]) || !tipo.equals((String)datos[2]) || !genero.equals((String)datos[2])
                    || !sContrato.equals((String)datos[2])|| !noCanciones.equals((String)datos[2])) {
                Conexciones.update(Conexciones.DISCO,(String)datos[0],titulo,tipo,sContrato,genero,noCanciones);
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
