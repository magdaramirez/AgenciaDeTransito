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
 *
 * @author magda
 */
@Entity
@DiscriminatorValue("Automovil")
@PrimaryKeyJoinColumn(name = "idVehiculoAutomovil")
@Table(name = "vehiculosAutomovil")
public class VehiculoAutomovil extends Vehiculo implements Serializable {

    public VehiculoAutomovil() {
    }
    
    public VehiculoAutomovil(String noSerie, String marca, String modelo, String color, String linea, List<TramitePlaca> placas) {
        super(noSerie, marca, modelo, color, linea, placas);
    }
    
    public VehiculoAutomovil(String noSerie, String marca, String modelo, String color, String linea) {
        super(noSerie, marca, modelo, color, linea);
    }
    
    
}
