/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.artista;

import conexion.Conexciones;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author bizcho
 */
public class ArtistaConsulta extends JFrame {

    private JScrollPane sPanel;
    private JPanel pBuscar, pTabla, pDefault;
    JTable tabla;
    private JLabel lBuscar;
    private JTextField tBuscar;
    private JButton bBuscarPor, bCambiar, bSalir;
    String[] titulos = {"ID artista", "Nombre", "Genero"};

    public ArtistaConsulta() {
        super("Artista consulta");
        //auxiliares
        Dimension dim = new Dimension(3, 3);
        //configuracion de la tabla
        tabla = new JTable(getModelo(""));
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sPanel = new JScrollPane(tabla);
        //configuracion de botones
        bBuscarPor = new JButton("Buscar por...");
        bCambiar = new JButton("Cambiar");
        bSalir = new JButton("Salir");
        //configuracion del label y el text field
        tBuscar = new JTextField(10);
        lBuscar = new JLabel("Buscar");
        //configuracion de paneles
        pBuscar = new JPanel(new GridLayout(1, 3, 3, 3));
        pBuscar.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        pTabla = new JPanel();
        pTabla.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pDefault = new JPanel();
        pDefault.setLayout(new BoxLayout(pDefault, BoxLayout.LINE_AXIS));
        pDefault.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        //agregando los componentes
        pBuscar.add(Box.createHorizontalGlue());
        pBuscar.add(lBuscar);
        pBuscar.add(tBuscar);

        pTabla.add(sPanel);

        pDefault.add(Box.createHorizontalGlue());
        pDefault.add(bBuscarPor);
        pDefault.add(Box.createRigidArea(dim));
        pDefault.add(bCambiar);
        pDefault.add(Box.createRigidArea(dim));
        pDefault.add(bSalir);
        //configuraciond del contenedor
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(pBuscar, BorderLayout.NORTH);
        c.add(pTabla, BorderLayout.CENTER);
        c.add(pDefault, BorderLayout.SOUTH);
        this.pack();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
        //test

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {

        }
        //listeners

        tBuscar.addKeyListener(new Buscador(this));

        bBuscarPor.addActionListener((ActionEvent e) -> {
            buscar();
        });

        bCambiar.addActionListener((ActionEvent e) -> {
            cambiar();
        });

        bSalir.addActionListener((ActionEvent e) -> {
            this.dispose();
        });//fin actionlistener salir

    }

    private void buscar() {
        String[] titulos = {"Seleccione campo", this.titulos[0], this.titulos[1], this.titulos[2]};
        boolean aux = true;
        int seleccion;
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 5, 5));
        JLabel lMensaje = new JLabel("Sleccione el campo en donde buscar y escriba el dato a buscar");
        JComboBox cLista = new JComboBox(titulos);
        JTextField tDato = new JTextField(15);
        panel.add(lMensaje);
        panel.add(cLista);
        panel.add(tDato);
        seleccion = JOptionPane.showConfirmDialog(null, panel, "Buscar por", JOptionPane.YES_NO_OPTION);
        if (seleccion == JOptionPane.YES_OPTION) {
            int campo = cLista.getSelectedIndex();
            String dato = tDato.getText().trim();
            if (campo != 0) {
                ResultSet rs = null;
                try {
                    rs = Conexciones.search(Conexciones.ARTISTA, getColumn(campo), dato);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                tabla.setModel(mkModelo(rs));
            } else {
                JOptionPane.showMessageDialog(null, "debe de seleccionar un campo");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cancelado");
        }
    }

    private String getColumn(int campo) {
        switch (campo) {
            case 1:
                return "idartista";
            case 2:
                return "nombre";
            case 3:
                return "genero";
            default:
                return "";
        }
    }

    private TableModel mkModelo(ResultSet rs) {
        String[] campos = new String[3];
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        try {
            if (rs != null) {
                while (rs.next()) {
                    campos[0] = rs.getString("idartista");
                    campos[1] = rs.getString("nombre");
                    campos[2] = rs.getString("genero");
                    modelo.addRow(campos);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        Conexciones.closeConn();
        return modelo;
    }

    private class Buscador implements KeyListener {

        ArtistaConsulta consulta;

        Buscador(ArtistaConsulta consulta) {
            this.consulta = consulta;
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            consulta.tabla.setModel(getModelo(consulta.tBuscar.getText()));
        }
    }

    TableModel getModelo(String consulta) {
        String[] campos = new String[3];
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        ResultSet rs = null;
        try {
            rs = (Conexciones.searchData(Conexciones.ARTISTA, consulta));
            if (rs != null) {
                while (rs.next()) {
                    campos[0] = rs.getString("idartista");
                    campos[1] = rs.getString("nombre");
                    campos[2] = rs.getString("genero");
                    modelo.addRow(campos);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        Conexciones.closeConn();
        return modelo;
    }

    private void cambiar() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            Object[] datos = new Object[3];
            datos[0] = tabla.getValueAt(fila, 0);
            datos[1] = tabla.getValueAt(fila, 1);
            datos[2] = tabla.getValueAt(fila, 2);
            ArtistaCambios ventana = new ArtistaCambios(this, datos);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un registro para cambiar");
        }
    }
}
