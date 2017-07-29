/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Owner
 */
public class Pestaña extends JPanel{
    private JLabel lIcono;
    JButton bAgregar, bBorrar, bConsutar, bEditar;
    private JPanel pDerecha, pIzquierda;
    
    Pestaña(String texto, ImageIcon icono){
        
        lIcono = new JLabel(icono);
        lIcono.setSize(250, 250);
        bAgregar = new JButton("Agregar "+texto);
        bBorrar = new JButton("Borrar "+texto);
        bConsutar = new JButton("Consultar "+texto);
        bEditar = new JButton("Editar "+texto);
        
        pDerecha = new JPanel();
        pDerecha.setLayout(new GridLayout(4, 1, 3, 5));
        pDerecha.add(bAgregar);
        pDerecha.add(bBorrar);
        pDerecha.add(bConsutar);
//        pDerecha.add(bEditar);
        pDerecha.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 3));
        
        pIzquierda = new JPanel();
        pIzquierda.add(lIcono);
        pIzquierda.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        
        this.setLayout(new BorderLayout());
        this.add(pIzquierda, BorderLayout.WEST);
        
        this.add(pDerecha, BorderLayout.EAST);
    
    }
}
