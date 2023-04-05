/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.itson.dominio.TramitePlaca;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.itson.dominio.VehiculoAutomovil;
import org.itson.utils.exceptions.IllegalOrphanException;
import org.itson.utils.exceptions.NonexistentEntityException;

/**
 *
 * @author magda
 */
public class VehiculoAutomovilJpaController implements Serializable {

    public VehiculoAutomovilJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public VehiculoAutomovilJpaController() {
        emf = Persistence.createEntityManagerFactory("org.itson.agenciatransito");
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VehiculoAutomovil vehiculoAutomovil) {
        if (vehiculoAutomovil.getPlacas() == null) {
            vehiculoAutomovil.setPlacas(new ArrayList<TramitePlaca>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TramitePlaca> attachedPlacas = new ArrayList<TramitePlaca>();
            for (TramitePlaca placasTramitePlacaToAttach : vehiculoAutomovil.getPlacas()) {
                placasTramitePlacaToAttach = em.getReference(placasTramitePlacaToAttach.getClass(), placasTramitePlacaToAttach.getId());
                attachedPlacas.add(placasTramitePlacaToAttach);
            }
            vehiculoAutomovil.setPlacas(attachedPlacas);
            em.persist(vehiculoAutomovil);
            for (TramitePlaca placasTramitePlaca : vehiculoAutomovil.getPlacas()) {
                org.itson.dominio.Vehiculo oldVehiculoOfPlacasTramitePlaca = placasTramitePlaca.getVehiculo();
                placasTramitePlaca.setVehiculo(vehiculoAutomovil);
                placasTramitePlaca = em.merge(placasTramitePlaca);
                if (oldVehiculoOfPlacasTramitePlaca != null) {
                    oldVehiculoOfPlacasTramitePlaca.getPlacas().remove(placasTramitePlaca);
                    oldVehiculoOfPlacasTramitePlaca = em.merge(oldVehiculoOfPlacasTramitePlaca);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VehiculoAutomovil vehiculoAutomovil) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VehiculoAutomovil persistentVehiculoAutomovil = em.find(VehiculoAutomovil.class, vehiculoAutomovil.getId());
            List<TramitePlaca> placasOld = persistentVehiculoAutomovil.getPlacas();
            List<TramitePlaca> placasNew = vehiculoAutomovil.getPlacas();
            List<String> illegalOrphanMessages = null;
            for (TramitePlaca placasOldTramitePlaca : placasOld) {
                if (!placasNew.contains(placasOldTramitePlaca)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TramitePlaca " + placasOldTramitePlaca + " since its vehiculo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<TramitePlaca> attachedPlacasNew = new ArrayList<TramitePlaca>();
            for (TramitePlaca placasNewTramitePlacaToAttach : placasNew) {
                placasNewTramitePlacaToAttach = em.getReference(placasNewTramitePlacaToAttach.getClass(), placasNewTramitePlacaToAttach.getId());
                attachedPlacasNew.add(placasNewTramitePlacaToAttach);
            }
            placasNew = attachedPlacasNew;
            vehiculoAutomovil.setPlacas(placasNew);
            vehiculoAutomovil = em.merge(vehiculoAutomovil);
            for (TramitePlaca placasNewTramitePlaca : placasNew) {
                if (!placasOld.contains(placasNewTramitePlaca)) {
                    VehiculoAutomovil oldVehiculoOfPlacasNewTramitePlaca = (VehiculoAutomovil) placasNewTramitePlaca.getVehiculo();
                    placasNewTramitePlaca.setVehiculo(vehiculoAutomovil);
                    placasNewTramitePlaca = em.merge(placasNewTramitePlaca);
                    if (oldVehiculoOfPlacasNewTramitePlaca != null && !oldVehiculoOfPlacasNewTramitePlaca.equals(vehiculoAutomovil)) {
                        oldVehiculoOfPlacasNewTramitePlaca.getPlacas().remove(placasNewTramitePlaca);
                        oldVehiculoOfPlacasNewTramitePlaca = em.merge(oldVehiculoOfPlacasNewTramitePlaca);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = vehiculoAutomovil.getId();
                if (findVehiculoAutomovil(id) == null) {
                    throw new NonexistentEntityException("The vehiculoAutomovil with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VehiculoAutomovil vehiculoAutomovil;
            try {
                vehiculoAutomovil = em.getReference(VehiculoAutomovil.class, id);
                vehiculoAutomovil.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vehiculoAutomovil with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TramitePlaca> placasOrphanCheck = vehiculoAutomovil.getPlacas();
            for (TramitePlaca placasOrphanCheckTramitePlaca : placasOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This VehiculoAutomovil (" + vehiculoAutomovil + ") cannot be destroyed since the TramitePlaca " + placasOrphanCheckTramitePlaca + " in its placas field has a non-nullable vehiculo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(vehiculoAutomovil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VehiculoAutomovil> findVehiculoAutomovilEntities() {
        return findVehiculoAutomovilEntities(true, -1, -1);
    }

    public List<VehiculoAutomovil> findVehiculoAutomovilEntities(int maxResults, int firstResult) {
        return findVehiculoAutomovilEntities(false, maxResults, firstResult);
    }

    private List<VehiculoAutomovil> findVehiculoAutomovilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VehiculoAutomovil.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public VehiculoAutomovil findVehiculoAutomovil(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VehiculoAutomovil.class, id);
        } finally {
            em.close();
        }
    }

    public int getVehiculoAutomovilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VehiculoAutomovil> rt = cq.from(VehiculoAutomovil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
