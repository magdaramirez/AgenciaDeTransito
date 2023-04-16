/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.dominio;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Clase que hereda de la clase Tramite, crea la tabla tramiteLicencias y los
 * respectivos métodos para su implementación.
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
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

    /**
     * Método constructor por defecto.
     */
    public TramiteLicencia() {
    }

    /**
     * Método constructor que establece los atributos de la clase
     * TramiteLicencia y de la clase padre Tramite.
     *
     * @param tipo Objeto de la clase TipoLicencia con el tipo de la licencia.
     * @param vigencia Número entero de la vigencia de la licencia.
     * @param estado Objeto de la clase EstadoTramite con el estado del trámite.
     * @param costo Número decimal con el costo del trámite.
     * @param persona Objeto de la clase Persona con la persona que realizó el
     * trámite.
     */
    public TramiteLicencia(TipoLicencia tipo, Integer vigencia, EstadoTramite estado, float costo, Persona persona) {
        super(estado, costo, persona);
        this.tipo = tipo;
        this.vigencia = vigencia;
    }

    /**
     * Método que obtiene el tipo de licencia.
     *
     * @return Objeto de la clase TipoLicencia con el tipo de la licencia.
     */
    public TipoLicencia getTipo() {
        return tipo;
    }

    /**
     * Método que establece el tipo de licencia.
     *
     * @param tipo Objeto de la clase TipoLicencia con el tipo de la licencia.
     */
    public void setTipo(TipoLicencia tipo) {
        this.tipo = tipo;
    }

    /**
     * Método que obtiene la vigencia de la licencia.
     *
     * @return Número entero de la vigencia de la licencia.
     */
    public Integer getVigencia() {
        return vigencia;
    }

    /**
     * Método que establece la vigencia de la licencia.
     *
     * @param vigencia Número entero de la vigencia de la licencia.
     */
    public void setVigencia(Integer vigencia) {
        this.vigencia = vigencia;
    }

    /**
     * Método que convierte cualquier objeto en una cadena de texto.
     *
     * @return Cadena de texto con el id de tramiteLicencia.
     */
    @Override
    public String toString() {
        return "TramiteLicencia{ id=" + this.getId() + "tipo=" + tipo + ", vigencia=" + vigencia + '}';
    }

}
