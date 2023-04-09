/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itson.dominio;

import java.util.Calendar;

/**
 * Clase que guarda los datos para las consultas personalizadas de licencias
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

    public LicenciaDTO() {
    }

    public LicenciaDTO(String nombre, String rfc, Integer anioNacimiento, float costo, TipoPlaca tipo, Calendar fechaInicio, Calendar fechaFin) {
        this.nombre = nombre;
        this.rfc = rfc;
        this.anioNacimiento = anioNacimiento;
        this.costo = costo;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public TipoPlaca getTipo() {
        return tipo;
    }

    public void setTipo(TipoPlaca tipo) {
        this.tipo = tipo;
    }

    public Calendar getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Calendar fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Calendar getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Calendar fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    
    
}
