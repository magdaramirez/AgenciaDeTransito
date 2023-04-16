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
 * Clase que hereda de la clase Tramite, crea la tabla tramitePlacas y los
 * respectivos métodos para su implementación.
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
@Entity
@DiscriminatorValue("placa")
@PrimaryKeyJoinColumn(name = "idTramitePlaca")
@Table(name = "tramitePlacas")
public class TramitePlaca extends Tramite implements Serializable {

    @Column(name = "placa", nullable = true, unique = true)
    private String placa;

    @Column(name = "fechaRecepcion", nullable = true)
    @Temporal(TemporalType.DATE)
    private Calendar fechaRecepcion;

    @ManyToOne()
    @JoinColumn(name = "idVehiculo", nullable = false)
    private Vehiculo vehiculo;

    @Column(name = "tipoPlaca", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPlaca tipoPlaca;

    /**
     * Método constructor por defecto.
     */
    public TramitePlaca() {
    }

    /**
     * Método constructor que establece los atributos de la clase TramitePlaca y
     * de la clase padre Tramite.
     *
     * @param placa Cadena de texto con la placa del vehículo.
     * @param fechaRecepcion Fecha de la recepción de la placa con el formato
     * yyyy-MM-dd.
     * @param vehiculo Objeto de la clase Vehiculo con el vehículo que requiere
     * placa.
     * @param tipoPlaca Objeto de la clase TipoPlaca con el tipo de la placa.
     * @param estado Objeto de la clase EstadoTramite con el estado del trámite.
     * @param costo Número decimal con el costo del trámite.
     * @param persona Objeto de la clase Persona con la persona que realizó el
     * trámite.
     */
    public TramitePlaca(String placa, Calendar fechaRecepcion, Vehiculo vehiculo, TipoPlaca tipoPlaca, EstadoTramite estado, float costo, Persona persona) {
        super(estado, costo, persona);
        this.placa = placa;
        this.fechaRecepcion = fechaRecepcion;
        this.vehiculo = vehiculo;
        this.tipoPlaca = tipoPlaca;
    }

    /**
     * Método constructor que establece los atributos de la clase TramitePlaca a
     * excepción de placa y de la clase padre Tramite.
     *
     * @param fechaRecepcion Fecha de la recepción de la placa con el formato
     * yyyy-MM-dd.
     * @param vehiculo Objeto de la clase Vehiculo con el vehículo que requiere
     * placa.
     * @param tipoPlaca Objeto de la clase TipoPlaca con el tipo de la placa.
     * @param estado Objeto de la clase EstadoTramite con el estado del trámite.
     * @param costo Número decimal con el costo del trámite.
     * @param persona Objeto de la clase Persona con la persona que realizó el
     * trámite.
     */
    public TramitePlaca(Calendar fechaRecepcion, Vehiculo vehiculo, TipoPlaca tipoPlaca, EstadoTramite estado, float costo, Persona persona) {
        super(estado, costo, persona);
        this.fechaRecepcion = fechaRecepcion;
        this.vehiculo = vehiculo;
        this.tipoPlaca = tipoPlaca;
    }

    /**
     * Método que obtiene el tipo de la placa.
     *
     * @return Objeto de la clase TipoPlaca con el tipo de la placa.
     */
    public TipoPlaca getTipoPlaca() {
        return tipoPlaca;
    }

    /**
     * Método que establece el tipo de la placa.
     *
     * @param tipoPlaca Objeto de la clase TipoPlaca con el tipo de la placa.
     */
    public void setTipoPlaca(TipoPlaca tipoPlaca) {
        this.tipoPlaca = tipoPlaca;
    }

    /**
     * Método que obtiene la placa.
     *
     * @return Cadena de texto con la placa del vehículo.
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Método que establece la placa.
     *
     * @param placa Cadena de texto con la placa del vehículo.
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * Método que obtiene la fecha de recepción de la placa.
     *
     * @return Fecha de la recepción de la placa con el formato yyyy-MM-dd.
     */
    public Calendar getFechaRecepcion() {
        return fechaRecepcion;
    }

    /**
     * Método que establece la fecha de recepción de la placa.
     *
     * @param fechaRecepcion Fecha de la recepción de la placa con el formato
     * yyyy-MM-dd.
     */
    public void setFechaRecepcion(Calendar fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    /**
     * Método que obtiene el vehículo.
     *
     * @return Objeto de la clase Vehiculo con el vehículo que requiere placa.
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /**
     * Método que establece el vehículo.
     *
     * @param vehiculo Objeto de la clase Vehiculo con el vehículo que requiere
     * placa.
     */
    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    /**
     * Método que convierte cualquier objeto en una cadena de texto.
     *
     * @return Cadena de texto con el id de tramitePlaca.
     */
    @Override
    public String toString() {
        return "TramitePlaca{ id=" + this.getId() + "placa=" + placa + ", numero=" + ", fechaEmision=" + super.getFechaEmision() + ", fechaRecepcion=" + fechaRecepcion + '}';
    }

}
