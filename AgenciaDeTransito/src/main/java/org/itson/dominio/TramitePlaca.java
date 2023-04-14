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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @Column(name = "placa", nullable = true, unique = true)
    private String placa;

    @Column(name = "fechaEmision", nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar fechaEmision ;

    @Column(name = "fechaRecepcion", nullable = true)
    @Temporal(TemporalType.DATE)
    private Calendar fechaRecepcion;

    @ManyToOne()
    @JoinColumn(name = "idVehiculo", nullable = false)
    private Vehiculo vehiculo;
    
    @Column(name = "tipoPlaca", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPlaca tipoPlaca;

    public TramitePlaca() {
    }

    public TramitePlaca(String placa, Calendar fechaEmision, Vehiculo vehiculo, TipoPlaca tipoPlaca, EstadoTramite estado, float costo, Persona persona) {
        super(estado, costo, persona);
        this.placa = placa;
        this.fechaEmision = fechaEmision;
        this.vehiculo = vehiculo;
        this.tipoPlaca = tipoPlaca;
    }

    public TramitePlaca(String placa, Calendar fechaEmision, Calendar fechaRecepcion, Vehiculo vehiculo, TipoPlaca tipoPlaca, EstadoTramite estado, float costo, Persona persona) {
        super(estado, costo, persona);
        this.placa = placa;
        this.fechaEmision = fechaEmision;
        this.fechaRecepcion = fechaRecepcion;
        this.vehiculo = vehiculo;
        this.tipoPlaca = tipoPlaca;
    }
    
    
    
    public TipoPlaca getTipoPlaca() {
        return tipoPlaca;
    }

    public void setTipoPlaca(TipoPlaca tipoPlaca) {
        this.tipoPlaca = tipoPlaca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
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
        return "TramitePlaca{ id=" + this.getId() + "placa=" + placa + ", numero=" + ", fechaEmision=" + fechaEmision + ", fechaRecepcion=" + fechaRecepcion + '}';
    }

}
