/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.implementaciones;

import org.itson.interfaces.IPlacasDAO;

/**
 *
 * @author magda
 */
public class PlacasDAO implements IPlacasDAO {

    /**
     * Método que genera placas con el formato AAA-111.
     *
     * @return cadena de texto con el formato AAA-111.
     */
    @Override
    public String generarPlaca() {
        char placa[] = new char[9];
        placa[0] = generarLetra();
        placa[1] = generarLetra();
        placa[2] = generarLetra();
        placa[3] = '-';
        placa[4] = generarNumero();
        placa[5] = generarNumero();
        placa[6] = generarNumero();
        placa[7] = '-';
        placa[8] = generarLetra();

        return String.valueOf(placa);
    }

    /**
     * Método que genera una letra aleatoria.
     *
     * @return letra aleatoria.
     */
    @Override
    public char generarLetra() {
        return generarRandomChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /**
     * Método que genera un número aleatorio.
     *
     * @return número aleatorio.
     */
    @Override
    public char generarNumero() {
        return generarRandomChar("0123456789");
    }

    /**
     * Método que aleatoriza caracteres de una cadena de texto.
     *
     * @param st Cadena de texto con los caracteres a aleatorizar.
     * @return caracteres aleatorizados.
     */
    @Override
    public char generarRandomChar(String st) {
        char caracteres[] = st.toCharArray();
        int index = (int) (Math.random() * caracteres.length);
        return caracteres[index];
    }
}
