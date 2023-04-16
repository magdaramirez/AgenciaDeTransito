/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.interfaces;

import java.util.List;
import org.itson.dominio.Persona;
import org.itson.excepciones.PersistenciaException;

/**
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
public interface IPersonasDAO {

    public void insertarPersonas();

    public Persona buscarPersona(String rfc) throws PersistenciaException;

    public List<Persona> buscarPersonasActivas() throws PersistenciaException;

    public String DesencriptarNombreCompleto(Persona persona1);
}
