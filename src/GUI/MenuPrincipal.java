/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.artista.ArtistaAlta;
import GUI.artista.ArtistaConsulta;
import GUI.cancion.CancionAlta;
import GUI.cancion.CancionConsulta;
import GUI.contrato.ContratoAlta;
import GUI.contrato.ContratoConsulta;
import GUI.disco.DiscoAlta;
import GUI.disco.DiscoConsulta;
import GUI.productor.ProductorAlta;
import GUI.productor.ProductorConsulta;
import conexion.Conexciones;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

/**
 *
 * @author Owner
 */
public class MenuPrincipal extends JFrame {

    private JTabbedPane panel;
    private Pestaña artista, cancion, contrato, disco, productor;

    public MenuPrincipal() {
        super("BZ Records");
        panel = new JTabbedPane();

        ImageIcon iArtista
                = new ImageIcon(
                "C:/Users/bizcho/Documents/BZrecords/src/recursos/iconos/artista.jpg"
                );
        ImageIcon iCancion
                = new ImageIcon(
                "C:/Users/bizcho/Documents/BZrecords/src/recursos/iconos/cancion.jpg"
                );
        ImageIcon iContrato
                = new ImageIcon(
                "C:/Users/bizcho/Documents/BZrecords/src/recursos/iconos/contrato.jpg"
                );
        ImageIcon iDisco
                = new ImageIcon(
                "C:/Users/bizcho/Documents/BZrecords/src/recursos/iconos/disco.jpg"
                );
        ImageIcon iProductor
                = new ImageIcon(
                "C:/Users/bizcho/Documents/BZrecords/src/recursos/iconos/productor.jpg"
                );
        

        artista = new Pestaña("Artista", iArtista);
        cancion = new Pestaña("Cancion", iCancion);
        contrato = new Pestaña("Contrato", iContrato);
        disco = new Pestaña("Disco", iDisco);
        productor = new Pestaña("Productor", iProductor);

        panel.add("Artista", artista);
        panel.add("Productor", productor);
        panel.add("Contrato", contrato);
        panel.add("Disco", disco);
        panel.add("Cancion", cancion);
        
        this.getContentPane().add(panel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {

        }
        //LISTENERS
        //PARA EL PANEL DE ARTISTA
        artista.bAgregar.addActionListener((ActionEvent) -> {
            ArtistaAlta ventana = new ArtistaAlta();
        });
        artista.bBorrar.addActionListener((ActionEvent) -> {
            if (Baja.borrar(Conexciones.ARTISTA)){
                JOptionPane.showMessageDialog(this, "Extito!");
            }else{
                JOptionPane.showMessageDialog(this, "No se completo el borrado");
            }
        });
        artista.bConsutar.addActionListener((ActionEvent) -> {
            ArtistaConsulta ventana = new ArtistaConsulta();
        });
        //PARA EL PANEL DE CANCION
        cancion.bAgregar.addActionListener((ActionEvent) -> {
            if (Conexciones.validate(Conexciones.DISCO)) {
                CancionAlta ventana = new CancionAlta();
            }else{
                JOptionPane.showMessageDialog(this,
                        "No hay datos en la tabla 'disco'",
                        "datos no encontrados",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        cancion.bBorrar.addActionListener((ActionEvent) -> {
            if (Baja.borrar(Conexciones.CANCION)){
                JOptionPane.showMessageDialog(this, "Extito!");
            }else{
                JOptionPane.showMessageDialog(this, "No se completo el borrado");
            }
        });
        cancion.bConsutar.addActionListener((ActionEvent) -> {
            CancionConsulta ventana = new CancionConsulta();
        });
        //PARA EL PANEL DE CONTRATO
        contrato.bAgregar.addActionListener((ActionEvent) -> {
            if (Conexciones.validate(Conexciones.ARTISTA)
            &&  Conexciones.validate(Conexciones.PRODUCTOR)) {
                ContratoAlta ventana = new ContratoAlta();
            }else{
                JOptionPane.showMessageDialog(this,
                        "No hay datos en la tabla 'artista'"
                        + "\no en la tabla 'productor'",
                        "datos no encontrados",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        contrato.bBorrar.addActionListener((ActionEvent) -> {
            if (Baja.borrar(Conexciones.CONTRATO)){
                JOptionPane.showMessageDialog(this, "Extito!");
            }else{
                JOptionPane.showMessageDialog(this, "No se completo el borrado");
            }
                
        });
        contrato.bConsutar.addActionListener((ActionEvent) -> {
            ContratoConsulta ventana = new ContratoConsulta();
        });
        //PARA el PANEL DE DISCO
        disco.bAgregar.addActionListener((ActionEvent) -> {
            if (Conexciones.validate(Conexciones.CONTRATO)) {
                DiscoAlta ventana = new DiscoAlta();
            }else{
                JOptionPane.showMessageDialog(this,
                        "No hay datos en la tabla 'contrato'",
                        "datos no encontrados",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        disco.bBorrar.addActionListener((ActionEvent) -> {
            if (Baja.borrar(Conexciones.DISCO)){
                JOptionPane.showMessageDialog(this, "Extito!");
            }else{
                JOptionPane.showMessageDialog(this, "No se completo el borrado");
            }
        });
        disco.bConsutar.addActionListener((ActionEvent) -> {
            DiscoConsulta ventana = new DiscoConsulta();
        });
        //PARA EL PANEL PRODUCTOR
        productor.bAgregar.addActionListener((ActionEvent) -> {
            ProductorAlta ventana = new ProductorAlta();
        });
        productor.bBorrar.addActionListener((ActionEvent) -> {
            if (Baja.borrar(Conexciones.PRODUCTOR)){
                JOptionPane.showMessageDialog(this, "Extito!");
            }else{
                JOptionPane.showMessageDialog(this, "No se completo el borrado");
            }
        });
        productor.bConsutar.addActionListener((ActionEvent) -> {
            ProductorConsulta ventana = new ProductorConsulta();
        });
    }

    public static void main(String[] args) {
        MenuPrincipal app = new MenuPrincipal();
    }
}
