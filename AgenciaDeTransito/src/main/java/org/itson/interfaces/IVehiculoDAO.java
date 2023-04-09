/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itson.interfaces;

import org.itson.dominio.VehiculoAutomovil;
import org.itson.excepciones.PersistenciaException;

/**
 *
 * @author koine
 */
public interface IVehiculoDAO {
    public VehiculoAutomovil registrarAutomovil(VehiculoAutomovil vAutomovil) throws PersistenciaException;
    public VehiculoAutomovil buscarAutomovil(String noSerie) throws PersistenciaException;
    public VehiculoAutomovil cambiarListaPlacas(VehiculoAutomovil vAutomovil)throws PersistenciaException;
    
}
