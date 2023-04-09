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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Magda Ramírez
 */
@Entity
@DiscriminatorValue("licencia")
@PrimaryKeyJoinColumn(name = "idTramiteLicencia")
@Table(name = "tramiteLicencias")
public class TramiteLicencia extends Tramite implements Serializable {

    @Column(name = "tipoLicencia", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoLicencia tipo;

    @Column(name = "vigencia", nullable = false)
    private Integer vigencia;
    
    @Column(name = "fechaEmision", nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar fechaEmision ;
    
    public TramiteLicencia() {
    }

    public TramiteLicencia(TipoLicencia tipo, Integer vigencia, EstadoTramite estado, float costo) {
        super(estado, costo);
        this.tipo = tipo;
        this.vigencia = vigencia;
    }

    public TramiteLicencia(Integer vigencia, EstadoTramite estado, float costo) {
        super(estado, costo);
        this.vigencia = vigencia;
    }

    public TramiteLicencia(TipoLicencia tipo, Integer vigencia, EstadoTramite estado, float costo, Persona persona) {
        super(estado, costo, persona);
        this.tipo = tipo;
        this.vigencia = vigencia;
    }

    public Calendar getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Calendar fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    
    public TipoLicencia getTipo() {
        return tipo;
    }

    public void setTipo(TipoLicencia tipo) {
        this.tipo = tipo;
    }

    public Integer getVigencia() {
        return vigencia;
    }

    public void setVigencia(Integer vigencia) {
        this.vigencia = vigencia;
    }

    @Override
    public String toString() {
        return "TramiteLicencia{ id=" + this.getId() + "tipo=" + tipo + ", vigencia=" + vigencia + '}';
    }

}
