/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itson.dominio;

/**
 * Clase que guarda los datos para la generación de los reportes de los trámites
 * realizados.
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
public class Reporte {

    private String fecha;
    private String nombre;
    private String tipo;
    private String costo;

    /**
     * Método constructor por defecto.
     */
    public Reporte() {
    }

    /**
     * Método constructor que establece los atributos de la clase Reporte.
     *
     * @param fecha Cadena de texto de la realización del trámite con el formato
     * yyyy-MM-dd.
     * @param nombre Cadena de texto con el nombre de la persona que realizó el
     * trámite.
     * @param tipo Cadena de texto con el tipo del trámite.
     * @param costo Cadena de texto con el costo del trámite.
     */
    public Reporte(String fecha, String nombre, String tipo, String costo) {
        this.fecha = fecha;
        this.nombre = nombre;
        this.tipo = tipo;
        this.costo = costo;
    }

    /**
     * Método que obtiene la fecha.
     *
     * @return Cadena de texto de la realización del trámite con el formato
     * yyyy-MM-dd.
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Método que establece la fecha.
     *
     * @param fecha Cadena de texto de la realización del trámite con el formato
     * yyyy-MM-dd.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Método que obtiene el nombre de la persona que realizó el trámite.
     *
     * @return Cadena de texto con el nombre de la persona que realizó el
     * trámite.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que establece el nombre de la persona que realizó el trámite.
     *
     * @param nombre Cadena de texto con el nombre de la persona que realizó el
     * trámite.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que obtiene el tipo del trámite.
     *
     * @return Cadena de texto con el tipo del trámite.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Método que establece el tipo del trámite.
     *
     * @param tipo Cadena de texto con el tipo del trámite.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Método que obtiene el costo del trámite.
     *
     * @return Cadena de texto con el costo del trámite.
     */
    public String getCosto() {
        return costo;
    }

    /**
     * Método que establece el costo del trámite.
     *
     * @param costo Cadena de texto con el costo del trámite.
     */
    public void setCosto(String costo) {
        this.costo = costo;
    }

}
