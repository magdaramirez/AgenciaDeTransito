/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.dominio;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author magda
 */
@Entity
@DiscriminatorValue("vehiculo")
@PrimaryKeyJoinColumn(name = "idVehiculoAutomovil")
@Table(name = "vehiculosAutomovil")
public class VehiculoAutomovil extends Vehiculo implements Serializable {

}
