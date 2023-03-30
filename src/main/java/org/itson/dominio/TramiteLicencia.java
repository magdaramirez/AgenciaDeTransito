/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.dominio;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Magda Ramírez
 */
@Entity
@DiscriminatorValue("licencia")
@PrimaryKeyJoinColumn(name = "idTramiteLicencia")
@Table(name = "tramiteLicencias")
public class TramiteLicencia extends Tramite implements Serializable {

    @Column(name = "tipo", nullable = true)
    private LicenciaTipo tipo;

    @Column(name = "vigencia", nullable = false)
    private Integer vigencia;

    public LicenciaTipo getTipo() {
        return tipo;
    }

    public void setTipo(LicenciaTipo tipo) {
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
