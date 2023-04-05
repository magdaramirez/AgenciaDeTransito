/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.persistencia;

import org.itson.dominio.Persona;
import org.itson.dominio.TramiteLicencia;

/**
 *
 * @author magda
 */
public class ControlPersistencia {

    PagoJpaController pagoJpa = new PagoJpaController();
    PersonaJpaController personaJpa = new PersonaJpaController();
    TramiteJpaController tramiteJpa = new TramiteJpaController();
    TramiteLicenciaJpaController licenciaJpa = new TramiteLicenciaJpaController();
    TramitePlacaJpaController placaJpa = new TramitePlacaJpaController();
    VehiculoJpaController vehiculoJpa = new VehiculoJpaController();
    VehiculoAutomovilJpaController automovilJpa = new VehiculoAutomovilJpaController();

    /**
     * Método que guarda personas en la base de datos.
     *
     * @param persona Persona a guardar en la base de datos.
     */
    public void guardar(Persona persona) {
        personaJpa.create(persona);
    }
}
