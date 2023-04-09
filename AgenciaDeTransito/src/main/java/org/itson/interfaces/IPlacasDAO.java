/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.interfaces;

import java.util.List;
import org.itson.dominio.PlacaDTO;
import org.itson.dominio.Tramite;
import org.itson.dominio.TramitePlaca;
import org.itson.excepciones.PersistenciaException;
import org.itson.utils.ConfiguracionPaginado;

/**
 *
 * @author magda
 */
public interface IPlacasDAO {
    public String generarPlaca();
    public TramitePlaca buscarPlacaPorNumero(String placa) throws PersistenciaException;
    public TramitePlaca registrarPlaca(TramitePlaca tramPl);
    public List<TramitePlaca> consultarPlacasPersona(String rfc) throws PersistenciaException;
    public List<TramitePlaca> consultarPlacasNoSerie(String noSerie) throws PersistenciaException;
    public List<TramitePlaca> consultarPlacas(ConfiguracionPaginado paginado,PlacaDTO placasDTO) throws PersistenciaException;
    public void cambiarEstadoPlaca(TramitePlaca tramPl);
    public char generarLetra();
    public char generarNumero();
    public char generarRandomChar(String st);
}
