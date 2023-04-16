/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itson.dominio;

import java.util.Calendar;

/**
 * Clase que guarda los datos para las consultas personalizadas de placas
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
public class Placa {

    private String nombre;
    private String rfc;
    private Integer anioNacimiento;
    private float costo;
    private Calendar fechaInicio;
    private Calendar fechaFin;

    /**
     * Método constructor por defecto.
     */
    public Placa() {
    }

    /**
     * Método constructor que establece los atributos de la clase Placa.
     *
     * @param nombre Cadena de texto con el nombre de la persona que tramita la
     * placa.
     * @param rfc Cadena de texto con el Registro Federal de Contribuyentes de
     * la persona que tramita la placa.
     * @param anioNacimiento Número entero con el año de nacimiento de la
     * persona que tramita la placa.
     * @param costo Número decimal del costo de la placa.
     * @param fechaInicio Fecha del inicio de la vigencia de la placa con el
     * formato yyyy-MM-dd.
     * @param fechaFin Fecha del fin de la vigencia de la placa con el formato
     * yyyy-MM-dd.
     */
    public Placa(String nombre, String rfc, Integer anioNacimiento, float costo, Calendar fechaInicio, Calendar fechaFin) {
        this.nombre = nombre;
        this.rfc = rfc;
        this.anioNacimiento = anioNacimiento;
        this.costo = costo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    /**
     * Método que obtiene el nombre de la persona que tramita la placa.
     *
     * @return Cadena de texto con el nombre de la persona que tramita la placa.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que establece el nombre de la persona que tramita la placa.
     *
     * @param nombre Cadena de texto con el nombre de la persona que tramita la
     * placa.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que obtiene el Registro Federal de Contribuyentes de la persona
     * que tramita la placa.
     *
     * @return Cadena de texto con el Registro Federal de Contribuyentes de la
     * persona que tramita la placa.
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * Método que establece el Registro Federal de Contribuyentes de la persona
     * que tramita la placa.
     *
     * @param rfc Cadena de texto con el Registro Federal de Contribuyentes de
     * la persona que tramita la placa.
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * Método que obtiene el año de nacimiento de la persona que tramita la
     * placa.
     *
     * @return Número entero con el año de nacimiento de la persona que tramita
     * la placa.
     */
    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    /**
     * Método que establece el año de nacimiento de la persona que tramita la
     * placa.
     *
     * @param añoNacimiento Número entero con el año de nacimiento de la persona
     * que tramita la placa.
     */
    public void setAnioNacimiento(Integer añoNacimiento) {
        this.anioNacimiento = añoNacimiento;
    }

    /**
     * Método que obtiene el costo de la placa.
     *
     * @return Número decimal del costo de la placa.
     */
    public float getCosto() {
        return costo;
    }

    /**
     * Método que establece el costo de la placa.
     *
     * @param costo Número decimal del costo de la placa.
     */
    public void setCosto(float costo) {
        this.costo = costo;
    }

    /**
     * Método que obtiene la fecha de inicio de la vigencia de la placa.
     *
     * @return Fecha del inicio de la vigencia de la placa con el formato
     * yyyy-MM-dd.
     */
    public Calendar getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Método que establece la fecha de inicio de la vigencia de la placa.
     *
     * @param fechaInicio Fecha del inicio de la vigencia de la placa con el
     * formato yyyy-MM-dd.
     */
    public void setFechaInicio(Calendar fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Método que obtiene la fecha de fin de la vigencia de la placa.
     *
     * @return Fecha del fin de la vigencia de la placa con el formato
     * yyyy-MM-dd.
     */
    public Calendar getFechaFin() {
        return fechaFin;
    }

    /**
     * Método que establece la fecha de inicio de la vigencia de la placa.
     *
     * @param fechaFin Fecha del fin de la vigencia de la placa con el formato
     * yyyy-MM-dd.
     */
    public void setFechaFin(Calendar fechaFin) {
        this.fechaFin = fechaFin;
    }

}
