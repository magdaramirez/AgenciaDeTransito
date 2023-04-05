/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.dominio;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Magda Ramírez
 */
@Entity
@Table(name = "personas")
public class Persona implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "apellidoPaterno", nullable = false, length = 100)
    private String apellidoPaterno;

    @Column(name = "apellidoMaterno", nullable = false, length = 100)
    private String apellidoMaterno;

    @Column(name = "fechaNacimiento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar fechaNacimiento;

    @Column(name = "telefono", nullable = false, length = 10)
    private String telefono;

    @Column(name = "rfc", nullable = false, length = 13)
    private String rfc;

    @OneToMany(mappedBy = "persona", cascade = {CascadeType.REMOVE})
    private List<Tramite> tramites;

    /**
     * Método constructor por defecto
     */
    public Persona() {
    }

    /**
     * Método constructor que establece los atributos de la clase Persona a
     * excepción del id.
     *
     * @param nombre Cadena de texto con el nombre de la persona.
     * @param apellidoPaterno Cadena de texto con el apellido paterno de la
     * persona.
     * @param apellidoMaterno Cadena de texto con el apellido materno de la
     * persona.
     * @param fechaNacimiento Fecha de nacimiento de la persona con el formato
     * yyyy-MM-dd.
     * @param telefono Cadena de texto con el número telefónico de la persona.
     * @param rfc Cadena de texto con el Registro Federal de Contribuyentes de
     * la persona.
     */
    public Persona(String nombre, String apellidoPaterno, String apellidoMaterno, Calendar fechaNacimiento, String telefono,String rfc) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.rfc = rfc;
    }

    public Persona(String nombre, String apellidoPaterno, String apellidoMaterno, Calendar fechaNacimiento, String telefono, String rfc, List<Tramite> tramites) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.rfc = rfc;
        this.tramites = tramites;
    }
    
    

    /**
     * Método que obtiene el id de la persona.
     *
     * @return Número entero grande con el id de la persona.
     */
    public Long getId() {
        return id;
    }

    /**
     * Método que establece el id de la persona.
     *
     * @param id Número entero grande con el id de la persona.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Método que obtiene el nombre de la persona.
     *
     * @return Cadena de texto con el nombre de la persona.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que establece el nombre de la persona.
     *
     * @param nombre Cadena de texto con el nombre de la persona.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que obtiene el apellido paterno de la persona.
     *
     * @return Cadena de texto con el apellido paterno de la persona.
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Método que establece el apellido paterno de la persona.
     *
     * @param apellidoPaterno Cadena de texto con el apellido paterno de la
     * persona.
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Método que obtiene el apellido materno de la persona.
     *
     * @return Cadena de texto con el apellido materno de la persona.
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Método que establece el apellido materno de la persona.
     *
     * @param apellidoMaterno Cadena de texto con el apellido materno de la
     * persona.
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * Método que obtiene la fecha de nacimiento de la persona.
     *
     * @return Fecha de nacimiento de la persona con el formato yyyy-MM-dd.
     */
    public Calendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Método que establece la fecha de nacimiento de la persona.
     *
     * @param fechaNacimiento Fecha de nacimiento de la persona con el formato
     * yyyy-MM-dd.
     */
    public void setFechaNacimiento(Calendar fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Método que obtiene el número telefónico de la persona.
     *
     * @return Cadena de texto con el número telefónico de la persona.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Método que establece el número telefónico de la persona.
     *
     * @param telefono Cadena de texto con el número telefónico de la persona.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Método que obtiene el Registro Federal de Contribuyentes de la persona.
     *
     * @return Cadena de texto con el Registro Federal de Contribuyentes de la
     * persona.
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * Método que establece el Registro Federal de Contribuyentes de la persona.
     *
     * @param rfc Cadena de texto con el Registro Federal de Contribuyentes de
     * la persona.
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public List<Tramite> getTramites() {
        return tramites;
    }

    public void setTramites(List<Tramite> tramites) {
        this.tramites = tramites;
    }

    /**
     * Método que regresa el código Hash del id de la persona.
     *
     * @return Código Hash del id de la persona.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Método que compara la persona con el objeto del parámetro.
     *
     * @param obj Objeto a comparar.
     * @return Verdadero si el objeto del parámetro es de la clase Persona y
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
        final Persona other = (Persona) obj;
        return Objects.equals(this.id, other.id);
    }

    /**
     * Método que convierte cualquier objeto en una cadena de texto.
     *
     * @return Cadena de texto con el id de la persona.
     */
    @Override
    public String toString() {
        return "Persona{" + "id=" + id + '}';
    }

}
