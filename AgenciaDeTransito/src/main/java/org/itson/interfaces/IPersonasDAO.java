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
 * @author magda
 */
public interface IPersonasDAO {

    public void insertarPersonas();
    
    public Persona buscarPersona(String rfc) throws PersistenciaException;
    
    public List<Persona> buscarPersonasActivas() throws PersistenciaException;

}
