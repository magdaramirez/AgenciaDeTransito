/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itson.utils;

/**
 * Clase encargada de la configuración del paginado.
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
public class ConfiguracionPaginado {

    private int numPagina;
    private int elementosPagina;

    /**
     * Constructor que inicializa los atributos
     */
    public ConfiguracionPaginado() {
        this.numPagina = 0;
        this.elementosPagina = 5;
    }

    /**
     * Constructor que inicializa los atributos al valor de sus parámetros
     *
     * @param numPagina número de página.
     * @param elementosPagina número de elementos por página.
     */
    public ConfiguracionPaginado(int numPagina, int elementosPagina) {
        this.numPagina = numPagina;
        this.elementosPagina = elementosPagina;
    }

    /**
     * Método que regresa el número de página
     *
     * @return número de página
     */
    public int getNumPagina() {
        return numPagina;
    }

    /**
     * Método que setea el número de página
     *
     * @param numPagina número de página.
     */
    public void setNumPagina(int numPagina) {
        this.numPagina = numPagina;
    }

    /**
     * Método que regresa el número de elementos por página
     *
     * @return elementos por página
     */
    public int getElementosPagina() {
        return elementosPagina;
    }

    /**
     * Método que setea el número de elementos por página
     *
     * @param elementosPagina número de elementos por página.
     */
    public void setElementosPagina(int elementosPagina) {
        this.elementosPagina = elementosPagina;
    }

    /**
     * Método que regresa el OFFSET, el número de elementos a saltar.
     *
     * @return número de elementos a saltar.
     */
    public int getElementosASaltar() {
        return this.numPagina * this.elementosPagina;
    }

    /**
     * Método que avanza la página.
     */
    public void avanzarPagina() {
        this.numPagina++;
    }

    /**
     * Método que retrocede la página.
     */
    public void retrocederPagina() {
        while (this.numPagina != 0) {
            this.numPagina--;
        }
    }
}
