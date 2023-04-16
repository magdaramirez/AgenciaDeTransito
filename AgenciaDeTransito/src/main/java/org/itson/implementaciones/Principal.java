/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.itson.implementaciones;

import org.itson.GUI.FrmMenu;

/**
 * Clase principal que despliega el menú.
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
public class Principal {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMenu().setVisible(true);
            }
        });
    }
}
