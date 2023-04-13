/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itson.dominio;

import java.util.Calendar;

/**
 * Clase que guarda los datos para las consultas personalizadas de licencias
 *
 * @author koine
 */
public class LicenciaDTO {

    private String nombre;
    private String rfc;
    private Integer anioNacimiento;
    private float costo;
    private TipoPlaca tipo;
    private Calendar fechaInicio;
    private Calendar fechaFin;

    /**
     * Método constructor por defecto.
     */
    public LicenciaDTO() {
    }

    /**
     * Método constructor que establece los atributos de la clase Licencia a
     * excepción del id.
     *
     * @param nombre Cadena de texto con el nombre de la persona que tramita la
     * licencia.
     * @param rfc Cadena de texto con el Registro Federal de Contribuyentes de
     * la persona que tramita la licencia.
     * @param anioNacimiento Número entero del año de nacimiento de la persona
     * que tramita la licencia.
     * @param costo Número decimal con el costo de la licencia.
     * @param tipo Enum con el tipo de la licencia.
     * @param fechaInicio Fecha de inicio de la licencia con el formato
     * yyyy-MM-dd.
     * @param fechaFin Fecha de fin de la licencia con el formato yyyy-MM-dd.
     */
    public LicenciaDTO(String nombre, String rfc, Integer anioNacimiento, float costo, TipoPlaca tipo, Calendar fechaInicio, Calendar fechaFin) {
        this.nombre = nombre;
        this.rfc = rfc;
        this.anioNacimiento = anioNacimiento;
        this.costo = costo;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    /**
     * Método que obtiene el nombre de la persona que tramita la licencia.
     *
     * @return Cadena de texto con el nombre de la persona.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que establece el nombre de la persona que tramita la licencia.
     *
     * @param nombre Cadena de texto con el nombre de la persona.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que obtiene el Registro Federal de Contribuyentes de la persona
     * que tramita la licencia.
     *
     * @return Cadena de texto con el Registro Federal de Contribuyentes de la
     * persona.
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * Método que establece el Registro Federal de Contribuyentes de la persona
     * que tramita la licencia.
     *
     * @param rfc Cadena de texto con el Registro Federal de Contribuyentes de
     * la persona.
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * Método que obtiene el número entero del año de nacimiento de la persona
     * que tramita la licencia.
     *
     * @return Número entero del año de nacimiento de la persona.
     */
    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    /**
     * Método que establece el número entero del año de nacimiento de la persona
     * que tramita la licencia.
     *
     * @param anioNacimiento Número entero del año de nacimiento de la persona.
     */
    public void setAnioNacimiento(Integer anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    /**
     * Método que obtiene el costo de la licencia.
     *
     * @return Número decimal del costo de la licencia.
     */
    public float getCosto() {
        return costo;
    }

    /**
     * Método que establece el costo de la licencia.
     *
     * @param costo Número decimal del costo de la licencia.
     */
    public void setCosto(float costo) {
        this.costo = costo;
    }

    /**
     * Método que obtiene el enum del tipo de la licencia.
     *
     * @return enum del tipo de la licencia.
     */
    public TipoPlaca getTipo() {
        return tipo;
    }

    /**
     * Método que establece el enum del tipo de la licencia.
     *
     * @param tipo enum del tipo de la licencia.
     */
    public void setTipo(TipoPlaca tipo) {
        this.tipo = tipo;
    }

    /**
     * Método que obtiene la fecha de inicio de la licencia.
     *
     * @return Fecha de inicio de la licencia con el formato yyyy-MM-dd.
     */
    public Calendar getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Método que establece la fecha de inicio de la licencia.
     *
     * @param fechaInicio Fecha de inicio de la licencia con el formato
     * yyyy-MM-dd.
     */
    public void setFechaInicio(Calendar fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Método que obtiene la fecha de fin de la licencia.
     *
     * @return Fecha de fin de la licencia con el formato yyyy-MM-dd.
     */
    public Calendar getFechaFin() {
        return fechaFin;
    }

    /**
     * Método que establece la fecha de fin de la licencia.
     *
     * @param fechaFin Fecha de fin de la licencia con el formato yyyy-MM-dd.
     */
    public void setFechaFin(Calendar fechaFin) {
        this.fechaFin = fechaFin;
    }

}
