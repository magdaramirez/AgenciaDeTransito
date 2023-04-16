/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.dominio;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase que crea la tabla Pagos y los respectivos métodos para su
 * implementación.
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
@Entity
@Table(name = "pagos")
public class Pago implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "monto", nullable = false)
    private float monto;

    @Column(name = "fechaRealizacion", nullable = false, columnDefinition = "timestamp default current_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar fechaRealizacion;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "idTramite", nullable = false)
    private Tramite tramite;

    /**
     * Método por defecto.
     */
    public Pago() {
    }

    /**
     * Método constructor que inicializa los atributos de la clase a excepción
     * del ID y de tramite.
     *
     * @param monto Número decimal del costo del pago.
     * @param fechaRealizacion Fecha de la realización del pago con el formato
     * con el formato yyyy-MM-dd.
     */
    public Pago(float monto, Calendar fechaRealizacion) {
        this.monto = monto;
        this.fechaRealizacion = fechaRealizacion;
    }

    /**
     * Método constructor que inicializa los atributos de la clase a excepción
     * del ID.
     *
     * @param monto Número decimal del costo del pago.
     * @param fechaRealizacion Fecha de la realización del pago con el formato
     * con el formato yyyy-MM-dd.
     * @param tramite Tipo de trámite del pago.
     */
    public Pago(float monto, Calendar fechaRealizacion, Tramite tramite) {
        this.monto = monto;
        this.fechaRealizacion = fechaRealizacion;
        this.tramite = tramite;
    }

    /**
     * Método que obtiene el id del pago.
     *
     * @return Número entero grande con el id del pago.
     */
    public Long getId() {
        return id;
    }

    /**
     * Método que establece el id del pago.
     *
     * @param id Número entero grande con el id del pago.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Método que obtiene el monto del pago.
     *
     * @return Número decimal del costo del pago.
     */
    public float getMonto() {
        return monto;
    }

    /**
     * Método que establece el monto del pago.
     *
     * @param monto Número decimal del costo del pago.
     */
    public void setMonto(float monto) {
        this.monto = monto;
    }

    /**
     * Método que obtiene la fecha de la realización del pago.
     *
     * @return Fecha de la realización del pago con el formato con el formato
     * yyyy-MM-dd.
     */
    public Calendar getFechaRealizacion() {
        return fechaRealizacion;
    }

    /**
     * Método que establece la fecha de la realización del pago.
     *
     * @param fechaRealizacion Fecha de la realización del pago con el formato
     * con el formato yyyy-MM-dd.
     */
    public void setFechaRealizacion(Calendar fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    /**
     * Método que obtiene el tipo del trámite del pago.
     *
     * @return tipo de trámite del pago.
     */
    public Tramite getTramite() {
        return tramite;
    }

    /**
     * Método que establece el tipo del trámite del pago.
     *
     * @param tramite tipo de trámite del pago.
     */
    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    /**
     * Método que regresa el código Hash del id de la persona.
     *
     * @return Código Hash del id del pago.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Método que compara el pago con el objeto del parámetro.
     *
     * @param obj Objeto a comparar.
     * @return Verdadero si el objeto del parámetro es de la clase Pago y ambos
     * tienen el mismo id, Falso en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pago other = (Pago) obj;
        return Objects.equals(this.id, other.id);
    }

    /**
     * Método que convierte cualquier objeto en una cadena de texto.
     *
     * @return Cadena de texto con el id del pago.
     */
    @Override
    public String toString() {
        return "Pago{" + "id=" + id + '}';
    }
}
