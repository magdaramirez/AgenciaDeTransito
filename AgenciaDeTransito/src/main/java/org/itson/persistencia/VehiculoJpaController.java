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
import org.itson.dominio.Vehiculo;
import org.itson.excepciones.IllegalOrphanException;
import org.itson.excepciones.NonexistentEntityException;

/**
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
public class VehiculoJpaController implements Serializable {

    public VehiculoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

   public VehiculoJpaController() {
        emf = Persistence.createEntityManagerFactory("org.itson.agenciatransito");
    } 
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vehiculo vehiculo) {
        if (vehiculo.getPlacas() == null) {
            vehiculo.setPlacas(new ArrayList<TramitePlaca>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TramitePlaca> attachedPlacas = new ArrayList<TramitePlaca>();
            for (TramitePlaca placasTramitePlacaToAttach : vehiculo.getPlacas()) {
                placasTramitePlacaToAttach = em.getReference(placasTramitePlacaToAttach.getClass(), placasTramitePlacaToAttach.getId());
                attachedPlacas.add(placasTramitePlacaToAttach);
            }
            vehiculo.setPlacas(attachedPlacas);
            em.persist(vehiculo);
            for (TramitePlaca placasTramitePlaca : vehiculo.getPlacas()) {
                Vehiculo oldVehiculoOfPlacasTramitePlaca = placasTramitePlaca.getVehiculo();
                placasTramitePlaca.setVehiculo(vehiculo);
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

    public void edit(Vehiculo vehiculo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehiculo persistentVehiculo = em.find(Vehiculo.class, vehiculo.getId());
            List<TramitePlaca> placasOld = persistentVehiculo.getPlacas();
            List<TramitePlaca> placasNew = vehiculo.getPlacas();
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
            vehiculo.setPlacas(placasNew);
            vehiculo = em.merge(vehiculo);
            for (TramitePlaca placasNewTramitePlaca : placasNew) {
                if (!placasOld.contains(placasNewTramitePlaca)) {
                    Vehiculo oldVehiculoOfPlacasNewTramitePlaca = placasNewTramitePlaca.getVehiculo();
                    placasNewTramitePlaca.setVehiculo(vehiculo);
                    placasNewTramitePlaca = em.merge(placasNewTramitePlaca);
                    if (oldVehiculoOfPlacasNewTramitePlaca != null && !oldVehiculoOfPlacasNewTramitePlaca.equals(vehiculo)) {
                        oldVehiculoOfPlacasNewTramitePlaca.getPlacas().remove(placasNewTramitePlaca);
                        oldVehiculoOfPlacasNewTramitePlaca = em.merge(oldVehiculoOfPlacasNewTramitePlaca);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = vehiculo.getId();
                if (findVehiculo(id) == null) {
                    throw new NonexistentEntityException("The vehiculo with id " + id + " no longer exists.");
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
            Vehiculo vehiculo;
            try {
                vehiculo = em.getReference(Vehiculo.class, id);
                vehiculo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vehiculo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TramitePlaca> placasOrphanCheck = vehiculo.getPlacas();
            for (TramitePlaca placasOrphanCheckTramitePlaca : placasOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vehiculo (" + vehiculo + ") cannot be destroyed since the TramitePlaca " + placasOrphanCheckTramitePlaca + " in its placas field has a non-nullable vehiculo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(vehiculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vehiculo> findVehiculoEntities() {
        return findVehiculoEntities(true, -1, -1);
    }

    public List<Vehiculo> findVehiculoEntities(int maxResults, int firstResult) {
        return findVehiculoEntities(false, maxResults, firstResult);
    }

    private List<Vehiculo> findVehiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vehiculo.class));
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

    public Vehiculo findVehiculo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vehiculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getVehiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vehiculo> rt = cq.from(Vehiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
