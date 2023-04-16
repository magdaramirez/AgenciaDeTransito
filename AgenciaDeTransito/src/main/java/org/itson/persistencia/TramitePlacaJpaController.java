/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.itson.dominio.Vehiculo;
import org.itson.dominio.Persona;
import org.itson.dominio.TramitePlaca;
import org.itson.excepciones.NonexistentEntityException;

/**
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
public class TramitePlacaJpaController implements Serializable {

    public TramitePlacaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public TramitePlacaJpaController() {
        emf = Persistence.createEntityManagerFactory("org.itson.agenciatransito");
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TramitePlaca tramitePlaca) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehiculo vehiculo = tramitePlaca.getVehiculo();
            if (vehiculo != null) {
                vehiculo = em.getReference(vehiculo.getClass(), vehiculo.getId());
                tramitePlaca.setVehiculo(vehiculo);
            }
            Persona persona = tramitePlaca.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getId());
                tramitePlaca.setPersona(persona);
            }
            em.persist(tramitePlaca);
            if (vehiculo != null) {
                vehiculo.getPlacas().add(tramitePlaca);
                vehiculo = em.merge(vehiculo);
            }
            if (persona != null) {
                persona.getTramites().add(tramitePlaca);
                persona = em.merge(persona);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TramitePlaca tramitePlaca) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TramitePlaca persistentTramitePlaca = em.find(TramitePlaca.class, tramitePlaca.getId());
            Vehiculo vehiculoOld = persistentTramitePlaca.getVehiculo();
            Vehiculo vehiculoNew = tramitePlaca.getVehiculo();
            Persona personaOld = persistentTramitePlaca.getPersona();
            Persona personaNew = tramitePlaca.getPersona();
            if (vehiculoNew != null) {
                vehiculoNew = em.getReference(vehiculoNew.getClass(), vehiculoNew.getId());
                tramitePlaca.setVehiculo(vehiculoNew);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getId());
                tramitePlaca.setPersona(personaNew);
            }
            tramitePlaca = em.merge(tramitePlaca);
            if (vehiculoOld != null && !vehiculoOld.equals(vehiculoNew)) {
                vehiculoOld.getPlacas().remove(tramitePlaca);
                vehiculoOld = em.merge(vehiculoOld);
            }
            if (vehiculoNew != null && !vehiculoNew.equals(vehiculoOld)) {
                vehiculoNew.getPlacas().add(tramitePlaca);
                vehiculoNew = em.merge(vehiculoNew);
            }
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.getTramites().remove(tramitePlaca);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.getTramites().add(tramitePlaca);
                personaNew = em.merge(personaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tramitePlaca.getId();
                if (findTramitePlaca(id) == null) {
                    throw new NonexistentEntityException("The tramitePlaca with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TramitePlaca tramitePlaca;
            try {
                tramitePlaca = em.getReference(TramitePlaca.class, id);
                tramitePlaca.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tramitePlaca with id " + id + " no longer exists.", enfe);
            }
            Vehiculo vehiculo = tramitePlaca.getVehiculo();
            if (vehiculo != null) {
                vehiculo.getPlacas().remove(tramitePlaca);
                vehiculo = em.merge(vehiculo);
            }
            Persona persona = tramitePlaca.getPersona();
            if (persona != null) {
                persona.getTramites().remove(tramitePlaca);
                persona = em.merge(persona);
            }
            em.remove(tramitePlaca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TramitePlaca> findTramitePlacaEntities() {
        return findTramitePlacaEntities(true, -1, -1);
    }

    public List<TramitePlaca> findTramitePlacaEntities(int maxResults, int firstResult) {
        return findTramitePlacaEntities(false, maxResults, firstResult);
    }

    private List<TramitePlaca> findTramitePlacaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TramitePlaca.class));
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

    public TramitePlaca findTramitePlaca(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TramitePlaca.class, id);
        } finally {
            em.close();
        }
    }

    public int getTramitePlacaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TramitePlaca> rt = cq.from(TramitePlaca.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
