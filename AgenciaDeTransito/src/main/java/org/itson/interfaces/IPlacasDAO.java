/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.interfaces;

/**
 *
 * @author magda
 */
public interface IPlacasDAO {
    public String generarPlaca();
    public char generarLetra();
    public char generarNumero();
    public char generarRandomChar(String st);
}
