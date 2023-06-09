/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.interfaces;

import java.util.List;
import org.itson.dominio.Placa;
import org.itson.dominio.TramitePlaca;
import org.itson.excepciones.PersistenciaException;
import org.itson.utils.ConfiguracionPaginado;

/**
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
public interface IPlacasDAO {
    public String generarPlaca();
    public TramitePlaca buscarPlacaPorNumero(String placa) throws PersistenciaException;
    public TramitePlaca registrarPlaca(TramitePlaca tramPl);
    public List<TramitePlaca> consultarPlacasPersona(String rfc) throws PersistenciaException;
    public List<TramitePlaca> consultarPlacasNoSerie(String noSerie) throws PersistenciaException;
    public List<TramitePlaca> consultarPlacas(ConfiguracionPaginado paginado,Placa placasDTO) throws PersistenciaException;
    public void cambiarEstadoPlaca(TramitePlaca tramPl);
    public char generarLetra();
    public char generarNumero();
    public char generarRandomChar(String st);
}
