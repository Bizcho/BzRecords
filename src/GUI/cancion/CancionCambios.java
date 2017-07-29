/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.cancion;

import conexion.Conexciones;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.WIDTH;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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
public class CancionCambios extends JFrame{
    private JTextField tTitulo, tPosicion, tGenero;
    private JComboBox  cDisco;
    private JLabel lTitulo, lPosicion, lDisco, lGenero, lLetra;
    private JButton bGuardar, bCancelar, bLetra;
    private JPanel pLabels, pComps, pDefault, pGroup;
    private String letra = "null";//URL para la letra
    private Container c;
    
    private Object[] datos;
    private CancionConsulta consulta;
    
    public CancionCambios(CancionConsulta consulta, Object[] datos){
        super("Cancion cambios");
        
        this.datos = datos;
        this.consulta = consulta;
//        letra = Conexciones.getLetra((String)datos[0]);
        //inicializacion de componentes
        tTitulo = new JTextField(15);
        tPosicion = new JTextField(15);
        tGenero = new JTextField(15);
        
        tTitulo.setText((String)datos[2]);
        tPosicion.setText((String)datos[1]);
        tGenero.setText((String)datos[3]);
        
        try{
            cDisco = new JComboBox(Conexciones.getData(Conexciones.DISCO));
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(this, sqle.getMessage(), "Error de SQL", JOptionPane.ERROR_MESSAGE);
        }catch(IllegalArgumentException iae){
            JOptionPane.showMessageDialog(this, iae.getMessage(), "Error de argumentos", JOptionPane.ERROR_MESSAGE);
        }
        
        cDisco.setSelectedItem(datos[4]);
        
        lTitulo = new JLabel("Titulo");
        lPosicion = new JLabel("Posicion");
        lDisco = new JLabel("Disco");
        lGenero = new JLabel("Genero");
        lLetra = new JLabel("Letra");
        
        bGuardar = new JButton("Guardar");
        bCancelar = new JButton("Cancelar");
        bLetra = new JButton("Explorar...");
        
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
        pLabels.add(lPosicion);
        pLabels.add(lDisco);
        pLabels.add(lGenero);
        pLabels.add(lLetra);
        
        // agregando textfields al panel de text fields
        pComps.add(tTitulo);
        pComps.add(tPosicion);
        pComps.add(cDisco);
        pComps.add(tGenero);
        pComps.add(bLetra);
        
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
        bLetra.addActionListener(new CancionCambios.Explorador(this));
    }
    
    private static class Explorador implements ActionListener {
        CancionCambios ventana;

        public Explorador(CancionCambios ventana) {
            this.ventana = ventana;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                if(fc.getSelectedFile().getName().contains(".txt")){
                    ventana.letra = fc.getSelectedFile().getName();
                }
            }
        }
    }
    
    private boolean guardar() {
        boolean aux = true;
        String posicion = tPosicion.getText().trim();
        String titulo = tTitulo.getText().trim();
        String sDisco = (String)cDisco.getSelectedItem();
        String genero = tGenero.getText().trim();
        try{
            String disco = Conexciones.getID(Conexciones.DISCO, sDisco);
            if ("".equals(posicion) || "".equals(titulo) || "0".equals(disco) || "".equals(genero)){
                throw new IllegalArgumentException("los campos no pueden estar vacios");
            }
            if (!posicion.equals((String)datos[1]) || !titulo.equals((String)datos[2])
             || !genero.equals((String)datos[3])  ||  !sDisco.equals((String)datos[4])) {
                Conexciones.update(Conexciones.CANCION,(String)datos[0],posicion,titulo,genero,disco,letra);
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
