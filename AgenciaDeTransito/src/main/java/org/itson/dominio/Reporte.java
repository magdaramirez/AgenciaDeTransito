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
    private String fechaEmision;
    private String nombre;
    private String tipo;
    private String costo;

    public Reporte(String fechaEmision, String nombre, String tipo, String costo) {
        this.fechaEmision = fechaEmision;
        this.nombre = nombre;
        this.tipo = tipo;
        this.costo = costo;
    }

    public String getFechaRealizacion() {
        return fechaEmision;
    }

    public void setFechaRealizacion(String fechaEmision) {
        this.fechaEmision = fechaEmision;
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
