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
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase abstracta que crea la tabla tramites y los respectivos métodos para su
 * implementación.
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
@Entity
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tramites")
public abstract class Tramite implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoTramite estado;

    @Column(name = "costo", nullable = false)
    private float costo;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "idPersona", nullable = false)
    private Persona persona;

    @Column(name = "fechaEmision", nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar fechaEmision;

    /**
     * Método constructor por defecto.
     */
    public Tramite() {
    }

    /**
     * Método constructor que establece los atributos de estado y costo.
     *
     * @param estado Objeto de la clase EstadoTramite con el estado del trámite.
     * @param costo Número decimal con el costo del trámite.
     */
    public Tramite(EstadoTramite estado, float costo) {
        this.estado = estado;
        this.costo = costo;
    }

    /**
     * Método constructor que establece los atributos de la clase Tramite a
     * excepción del id y la fechaEmision.
     *
     * @param estado Objeto de la clase EstadoTramite con el estado del trámite.
     * @param costo Número decimal con el costo del trámite.
     * @param persona Objeto de la clase Persona con la persona que realizó el
     * trámite.
     */
    public Tramite(EstadoTramite estado, float costo, Persona persona) {
        this.estado = estado;
        this.costo = costo;
        this.persona = persona;
    }

    /**
     * Método que obtiene la fecha emisión del trámite.
     *
     * @return Fecha de la emisión del trámite con el formato yyyy-MM-dd
     */
    public Calendar getFechaEmision() {
        return fechaEmision;
    }

    /**
     * Método que establece la fecha emisión del trámite.
     *
     * @param fechaEmision Fecha de la emisión del trámite con el formato
     * yyyy-MM-dd
     */
    public void setFechaEmision(Calendar fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    /**
     * Método que obtiene el id del trámite.
     *
     * @return Número entero con el id del trámite.
     */
    public Long getId() {
        return id;
    }

    /**
     * Método que establece el id del trámite.
     *
     * @param id Número entero con el id del trámite.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Método que obtiene el estado del trámite.
     *
     * @return Objeto de la clase EstadoTramite con el estado del trámite.
     */
    public EstadoTramite getEstado() {
        return estado;
    }

    /**
     * Método que establece el estado del trámite.
     *
     * @param estado Objeto de la clase EstadoTramite con el estado del trámite.
     */
    public void setEstado(EstadoTramite estado) {
        this.estado = estado;
    }

    /**
     * Método que obtiene el costo del trámite.
     *
     * @return Número decimal con el costo del trámite.
     */
    public float getCosto() {
        return costo;
    }

    /**
     * Método que establece el costo del trámite.
     *
     * @param costo Número decimal con el costo del trámite.
     */
    public void setCosto(float costo) {
        this.costo = costo;
    }

    /**
     * Método que obtiene la persona que realizó el trámite.
     *
     * @return Objeto de la clase Persona con la persona que realizó el trámite.
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * Método que establece la persona que realizó el trámite.
     *
     * @param persona Objeto de la clase Persona con la persona que realizó el
     * trámite.
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    /**
     * Método que regresa el código Hash del id del trámite.
     *
     * @return Código Hash del id del trámite.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Método que compara el trámite con el objeto del parámetro.
     *
     * @param obj Objeto a comparar.
     * @return Verdadero si el objeto del parámetro es de la clase Tramite y
     * ambos tienen el mismo id, Falso en caso contrario.
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
        final Tramite other = (Tramite) obj;
        return Objects.equals(this.id, other.id);
    }

    /**
     * Método que convierte cualquier objeto en una cadena de texto.
     *
     * @return Cadena de texto con el id del trámite.
     */
    @Override
    public String toString() {
        return "Tramite{" + "id=" + id + '}';
    }

}
