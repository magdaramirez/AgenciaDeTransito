/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.persistencia;

import org.itson.dominio.Persona;

/**
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
public class ControlPersistencia {

    PersonaJpaController personaJpa = new PersonaJpaController();

    /**
     * Método que guarda personas en la base de datos.
     *
     * @param persona Persona a guardar en la base de datos.
     */
    public void guardar(Persona persona) {
        personaJpa.create(persona);
    }
}
