/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itson.dominio;

/**
 *
 * @author koine
 */
public class Reporte {
    private String fechaRealizacion;
    private String nombre;
    private String tipo;
    private String costo;

    public Reporte(String fechaRealizacion, String nombre, String tipo, String costo) {
        this.fechaRealizacion = fechaRealizacion;
        this.nombre = nombre;
        this.tipo = tipo;
        this.costo = costo;
    }

    public String getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(String fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }
    
    
    
}
