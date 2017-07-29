/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.contrato;

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
public class ContratoCambios extends JFrame {

    private JTextField tNoDiscos, tDuracion, tFecha;
    private JComboBox cArtista, cProductor;
    private JLabel lArtista, lProductor, lNoDiscos, lDuracion, lFecha;
    private JButton bGuardar, bCancelar;
    private JPanel pLabels, pComps, pDefault, pGroup;
    private Container c;
    ContratoConsulta consulta;
    Object[] datos;

    public ContratoCambios(ContratoConsulta consulta, Object[] datos) {
        super("Contrato cambios");

        this.datos = datos;
        this.consulta = consulta;
        //inicializacion de componentes
        tNoDiscos = new JTextField(12);
        tDuracion = new JTextField(12);
        tFecha = new JTextField(12);
        
        tNoDiscos.setText((String)datos[4]);
        tDuracion.setText((String)datos[5]);
        tFecha.setText((String)datos[1]);

        try {
            cArtista = new JComboBox(Conexciones.getData(Conexciones.ARTISTA));
            cProductor = new JComboBox(Conexciones.getData(Conexciones.PRODUCTOR));
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(this, sqle.getMessage(), "Error de SQL", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(this, iae.getMessage(), "Error de argumentos", JOptionPane.ERROR_MESSAGE);
        }
        
        cArtista.setSelectedItem(datos[2]);
        cProductor.setSelectedItem(datos[3]);

        lArtista = new JLabel("Artista");
        lProductor = new JLabel("Productor");
        lNoDiscos = new JLabel("Cantidad de discos");
        lDuracion = new JLabel("Duracion");
        lFecha = new JLabel("Fecha de inicio");

        bGuardar = new JButton("Guardar");
        bCancelar = new JButton("Cancelar");

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
        pLabels.add(lArtista);
        pLabels.add(lProductor);
        pLabels.add(lNoDiscos);
        pLabels.add(lDuracion);
        pLabels.add(lFecha);

        // agregando textfields al panel de text fields
        pComps.add(cArtista);
        pComps.add(cProductor);
        pComps.add(tNoDiscos);
        pComps.add(tDuracion);
        pComps.add(tFecha);

        //agregando botonoes al panel default
        pDefault.add(Box.createHorizontalGlue());
        pDefault.add(bGuardar);
        pDefault.add(Box.createRigidArea(new Dimension(3, WIDTH)));
        pDefault.add(bCancelar);
        pDefault.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        //agregando paneles de labels y text fields al panel grupo
        pGroup.add(pLabels, BorderLayout.WEST);
        pGroup.add(pComps, BorderLayout.EAST);
        pGroup.add(Box.createRigidArea(new Dimension(10, 10)), BorderLayout.CENTER);
        pGroup.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // configuracion del contenedor proncipal
        c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(pGroup, BorderLayout.CENTER);
        c.add(pDefault, BorderLayout.SOUTH);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {

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
    
    private boolean guardar() {
        boolean aux = true;
        
        String noDiscos = tNoDiscos.getText().trim();
        String duracion = tDuracion.getText().trim();
        String sArtista = (String)cArtista.getSelectedItem();
        String sProductor = (String)cProductor.getSelectedItem();
        String fecha = tFecha.getText().trim();
        
        try{
            String artista = Conexciones.getID(Conexciones.ARTISTA, sArtista);
            String productor = Conexciones.getID(Conexciones.PRODUCTOR, sProductor);
            if ("".equals(noDiscos) || "".equals(duracion) || "".equals(sArtista) || "".equals(sProductor) || "".equals(fecha)){
                throw new IllegalArgumentException("los campos no pueden estar vacios");
            }
            if (!fecha.equals((String)datos[1]) || !sArtista.equals((String)datos[2]) || !sProductor.equals((String)datos[3])
                || noDiscos.equals((String)datos[4])  ||  duracion.equals((String)datos[5])) {
                Conexciones.update(Conexciones.CONTRATO,(String)datos[0],fecha,artista,productor,noDiscos,duracion);
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
