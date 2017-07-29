/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import conexion.Conexciones;
import static conexion.Conexciones.ARTISTA;
import static conexion.Conexciones.CANCION;
import static conexion.Conexciones.CONTRATO;
import static conexion.Conexciones.DISCO;
import static conexion.Conexciones.PRODUCTOR;
import java.awt.GridLayout;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author bizcho
 */
public class Baja {

    private static int registro;

    public static boolean borrar(int tabla) throws IllegalArgumentException {
        boolean aux = true;
        String mensaje;
        String[] datos = {"selecione..."};
        int seleccion;
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 5, 5));
        JLabel consigna;
        JComboBox lista;
        try {
            datos = Conexciones.getData(tabla);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
            aux = false;
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de argumentos", JOptionPane.ERROR_MESSAGE);
            aux = false;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            aux = false;
        }
        lista = new JComboBox(datos);
        switch (tabla) {
            case ARTISTA:
                consigna = new JLabel("seleccione el artista a borrar");
                mensaje = "borrar artista";
                break;
            case CANCION:
                consigna = new JLabel("seleccione la cancion a borrar");
                mensaje = "borrar cancion";
                break;
            case CONTRATO:
                consigna = new JLabel("seleccione el contrato a borrar");
                mensaje = "borrar contrato";
                break;
            case DISCO:
                consigna = new JLabel("seleccione el disco a borrar");
                mensaje = "borrar disco";
                break;
            case PRODUCTOR:
                consigna = new JLabel("seleccione el productor a borrar");
                mensaje = "borrar productor";
                break;
            default:
                throw new IllegalArgumentException("no existe la tabla deseada");
        }
        panel.add(consigna);
        panel.add(lista);
        seleccion = JOptionPane.showConfirmDialog(null, panel, mensaje, JOptionPane.YES_NO_OPTION);
        if (seleccion == JOptionPane.YES_OPTION) {
            try {
                Conexciones.delete(tabla, Conexciones.getID(tabla, (String) lista.getSelectedItem()));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                aux = false;
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de argumentos", JOptionPane.ERROR_MESSAGE);
                aux = false;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                aux = false;
            }
        }else{
            aux = false;
        }
        return aux;
    }
}
