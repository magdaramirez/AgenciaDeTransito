/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itson.interfaces;

import java.util.List;
import org.itson.dominio.*;
import org.itson.dominio.TramiteLicencia;
import org.itson.excepciones.PersistenciaException;
import org.itson.utils.ConfiguracionPaginado;

/**
 *
 * @author koine
 */
public interface ILicenciasDAO {
    public TramiteLicencia registrarLicencia(TramiteLicencia licencia) throws PersistenciaException;
    public List<TramiteLicencia> consultarLicenciasPersona(String rfc) throws PersistenciaException;
    public List<TramiteLicencia> consultarLicencias(ConfiguracionPaginado paginado,LicenciaDTO licenciaDTO) throws PersistenciaException;
    public List<Tramite> consultarTramites(ConfiguracionPaginado paginado, LicenciaDTO licenciaDTO) throws PersistenciaException;
    public void cambiarEstadoLicencia(TramiteLicencia tramLic);
}
