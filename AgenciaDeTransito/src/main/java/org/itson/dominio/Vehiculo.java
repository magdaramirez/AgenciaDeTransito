/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.dominio;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase abstracta que crea la tabla vehiculos y los respectivos métodos para su
 * implementación.
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
@Entity
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "vehiculos")
public abstract class Vehiculo implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "noSerie", nullable = false, length = 7, unique = true)
    private String noSerie;

    @Column(name = "marca", nullable = false, length = 50)
    private String marca;

    @Column(name = "modelo", nullable = false, length = 50)
    private String modelo;

    @Column(name = "color", nullable = false, length = 50)
    private String color;

    @Column(name = "linea", nullable = false, length = 50)
    private String linea;

    @OneToMany(mappedBy = "vehiculo", cascade = {CascadeType.PERSIST})
    private List<TramitePlaca> placas;

    /**
     * Método constructor por defecto.
     */
    public Vehiculo() {
    }

    /**
     * Método constructor que establece los atributos de la clase Vehiculo a
     * excepción del id y placas.
     *
     * @param noSerie Cadena de texto con el número de serie del vehículo, con
     * el formato AAA-111.
     * @param marca Cadena de texto con la marca del vehículo.
     * @param modelo Cadena de texto con el modelo del vehículo.
     * @param color Cadena de texto con el color del vehículo.
     * @param linea Cadena de texto con la línea del vehículo.
     */
    public Vehiculo(String noSerie, String marca, String modelo, String color, String linea) {
        this.noSerie = noSerie;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.linea = linea;
    }

    /**
     * Método constructor que establece los atributos de la clase Vehiculo a
     * excepción del id.
     *
     * @param noSerie Cadena de texto con el número de serie del vehículo, con
     * el formato AAA-111.
     * @param marca Cadena de texto con la marca del vehículo.
     * @param modelo Cadena de texto con el modelo del vehículo.
     * @param color Cadena de texto con el color del vehículo.
     * @param linea Cadena de texto con la línea del vehículo.
     * @param placas Lista de las placas tramitadas.
     */
    public Vehiculo(String noSerie, String marca, String modelo, String color, String linea, List<TramitePlaca> placas) {
        this.noSerie = noSerie;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.linea = linea;
        this.placas = placas;
    }

    /**
     * Método que obtiene el id del vehículo.
     *
     * @return Número entero largo con el id del vehículo.
     */
    public Long getId() {
        return id;
    }

    /**
     * Método que establece el id del vehículo.
     *
     * @param id Número entero largo con el id del vehículo.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Método que obtiene el número de serie del vehículo.
     *
     * @return Cadena de texto con el número de serie del vehículo, con el
     * formato AAA-111.
     */
    public String getNoSerie() {
        return noSerie;
    }

    /**
     * Método que establece el número de serie del vehículo.
     *
     * @param noSerie Cadena de texto con el número de serie del vehículo, con
     * el formato AAA-111.
     */
    public void setNoSerie(String noSerie) {
        this.noSerie = noSerie;
    }

    /**
     * Método que obtiene la marca del vehículo.
     *
     * @return Cadena de texto con la marca del vehículo.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Método que establece la marca del vehículo.
     *
     * @param marca Cadena de texto con la marca del vehículo.
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Método que obtiene el modelo del vehículo.
     *
     * @return Cadena de texto con el modelo del vehículo.
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Método que establece el modelo del vehículo.
     *
     * @param modelo Cadena de texto con el modelo del vehículo.
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Método que obtiene el color del vehículo.
     *
     * @return Cadena de texto con el color del vehículo.
     */
    public String getColor() {
        return color;
    }

    /**
     * Método que establece el color del vehículo.
     *
     * @param color Cadena de texto con el color del vehículo.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Método que obtiene la línea del vehículo.
     *
     * @return Cadena de texto con la línea del vehículo.
     */
    public String getLinea() {
        return linea;
    }

    /**
     * Método que establece la línea del vehículo.
     *
     * @param linea Cadena de texto con la línea del vehículo.
     */
    public void setLinea(String linea) {
        this.linea = linea;
    }

    /**
     * Método que obtiene la lista de las placas tramitadas.
     *
     * @return Lista de las placas tramitadas.
     */
    public List<TramitePlaca> getPlacas() {
        return placas;
    }

    /**
     * Método que establece la lista de las placas tramitadas.
     *
     * @param placas Lista de las placas tramitadas.
     */
    public void setPlacas(List<TramitePlaca> placas) {
        this.placas = placas;
    }

    /**
     * Método que regresa el código Hash del id del vehículo.
     *
     * @return Código Hash del id del vehículo.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Método que compara el vehículo con el objeto del parámetro.
     *
     * @param obj Objeto a comparar.
     * @return Verdadero si el objeto del parámetro es de la clase Vehiculo y
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
        final Vehiculo other = (Vehiculo) obj;
        return Objects.equals(this.id, other.id);
    }

    /**
     * Método que convierte cualquier objeto en una cadena de texto.
     *
     * @return Cadena de texto con el id del vehículo.
     */
    @Override
    public String toString() {
        return "Vehiculo{" + "id=" + id + '}';
    }

}
