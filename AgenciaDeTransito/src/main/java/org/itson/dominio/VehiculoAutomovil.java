/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.dominio;

import java.io.Serializable;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Clase que hereda de la clase Vehiculo, crea la tabla vehiculosAutomoviles y
 * los respectivos métodos para su implementación.
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
@Entity
@DiscriminatorValue("Automovil")
@PrimaryKeyJoinColumn(name = "idVehiculoAutomovil")
@Table(name = "vehiculosAutomoviles")
public class VehiculoAutomovil extends Vehiculo implements Serializable {

    /**
     * Método constructor por defecto.
     */
    public VehiculoAutomovil() {
    }

    /**
     * Método constructor que establece los atributos de la clase padre a
     * excepción de id y placas.
     *
     * @param noSerie Cadena de texto con el número de serie del vehículo, con
     * el formato AAA-111.
     * @param marca Cadena de texto con la marca del vehículo.
     * @param modelo Cadena de texto con el modelo del vehículo.
     * @param color Cadena de texto con el color del vehículo.
     * @param linea Cadena de texto con la línea del vehículo.
     * @param placas Lista de las placas tramitadas.
     */
    public VehiculoAutomovil(String noSerie, String marca, String modelo, String color, String linea, List<TramitePlaca> placas) {
        super(noSerie, marca, modelo, color, linea, placas);
    }

    /**
     * Método constructor que establece los atributos de la clase padre a
     * excepción de placas.
     *
     * @param noSerie Cadena de texto con el número de serie del vehículo, con
     * el formato AAA-111.
     * @param marca Cadena de texto con la marca del vehículo.
     * @param modelo Cadena de texto con el modelo del vehículo.
     * @param color Cadena de texto con el color del vehículo.
     * @param linea Cadena de texto con la línea del vehículo.
     */
    public VehiculoAutomovil(String noSerie, String marca, String modelo, String color, String linea) {
        super(noSerie, marca, modelo, color, linea);
    }

}
