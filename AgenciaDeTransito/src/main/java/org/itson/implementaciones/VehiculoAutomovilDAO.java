/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itson.implementaciones;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.itson.dominio.VehiculoAutomovil;
import org.itson.excepciones.PersistenciaException;
import org.itson.persistencia.VehiculoJpaController;
import org.itson.interfaces.IVehiculoDAO;

/**
 *
 * @author koine
 */
public class VehiculoAutomovilDAO implements IVehiculoDAO {

    private static final Logger LOG = Logger.getLogger(LicenciasDAO.class.getName());
    VehiculoJpaController jpaCont = new VehiculoJpaController();

    /**
     * Metodo que registra un automovil a la base de datos de los vehiculos
     *
     * @param vAutomovil el automovil a registrar
     * @return el mismo automovil registrado
     * @throws PersistenciaException en caso de que no haya sido posible
     * registrar el automovil
     */
    @Override
    public VehiculoAutomovil registrarAutomovil(VehiculoAutomovil vAutomovil) throws PersistenciaException {
        try {
            jpaCont.create(vAutomovil);
            return vAutomovil;
        } catch (Exception ex) {
            Logger.getLogger(PlacasDAO.class.getName()).log(Level.SEVERE, null, ex);
            LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible registrar el automovil");
        }
    }

    /**
     * Metodo que busca un automovil por su numero de serie
     *
     * @param noSerie el numero de serie por el que se buscara el automovil
     * @return el automovil que se busco en la base de datos
     * @throws PersistenciaException en caso de que el automovil no se haya
     * encontrado
     */
    @Override
    public VehiculoAutomovil buscarAutomovil(String noSerie) throws PersistenciaException {
        try {
            EntityManager entityManager = jpaCont.getEntityManager();

            String jpqlQuery1 = "SELECT v FROM Vehiculo v WHERE v.noSerie = :noSerie";
            TypedQuery<VehiculoAutomovil> query1 = entityManager.createQuery(jpqlQuery1, VehiculoAutomovil.class);
            query1.setParameter("noSerie", noSerie);

            VehiculoAutomovil automovil = query1.getSingleResult();

            return automovil;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible encontrar el automovil con ese numero de serie");
        }
    }

    /**
     * Metodo que edita un automovil para cambiar la lista de las placas que
     * contiene un automovil
     *
     * @param vAutomovil el automovil a modificar
     * @return el automovil ya modificado
     * @throws PersistenciaException en caso de que no haya sido posible
     * modificar el automovil ingresado
     */
    @Override
    public VehiculoAutomovil cambiarListaPlacas(VehiculoAutomovil vAutomovil) throws PersistenciaException {
        try {
            jpaCont.edit(vAutomovil);
            return vAutomovil;
        } catch (Exception ex) {
            Logger.getLogger(PlacasDAO.class.getName()).log(Level.SEVERE, null, ex);
            LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible cambiar el automovil");
        }
    }

}
