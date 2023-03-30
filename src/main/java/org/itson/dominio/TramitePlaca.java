/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.dominio;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author magda
 */
@Entity
@DiscriminatorValue("placa")
@PrimaryKeyJoinColumn(name = "idTramitePlaca")
@Table(name = "tramitePlacas")
public class TramitePlaca extends Tramite implements Serializable {

    @Column(name = "placa", nullable = true)
    private String placa;

    @Column(name = "numero", nullable = true)
    private Long numero;

    @Column(name = "fechaEmision", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar fechaEmision;

    @Column(name = "fechaRecepcion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar fechaRecepcion;

    @ManyToOne()
    @JoinColumn(name = "idVehiculo", nullable = false)
    private Vehiculo vehiculo;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Calendar getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Calendar fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Calendar getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Calendar fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    @Override
    public String toString() {
        return "TramitePlaca{ id=" + this.getId() + "placa=" + placa + ", numero=" + numero + ", fechaEmision=" + fechaEmision + ", fechaRecepcion=" + fechaRecepcion + '}';
    }

}
