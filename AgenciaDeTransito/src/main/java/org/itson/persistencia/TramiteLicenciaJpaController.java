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
import org.itson.dominio.Persona;
import org.itson.dominio.TramiteLicencia;
import org.itson.excepciones.NonexistentEntityException;

/**
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
public class TramiteLicenciaJpaController implements Serializable {

    public TramiteLicenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public TramiteLicenciaJpaController() {
        emf = Persistence.createEntityManagerFactory("org.itson.agenciatransito");
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TramiteLicencia tramiteLicencia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona = tramiteLicencia.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getId());
                tramiteLicencia.setPersona(persona);
            }
            em.persist(tramiteLicencia);
            if (persona != null) {
                persona.getTramites().add(tramiteLicencia);
                persona = em.merge(persona);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TramiteLicencia tramiteLicencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TramiteLicencia persistentTramiteLicencia = em.find(TramiteLicencia.class, tramiteLicencia.getId());
            Persona personaOld = persistentTramiteLicencia.getPersona();
            Persona personaNew = tramiteLicencia.getPersona();
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getId());
                tramiteLicencia.setPersona(personaNew);
            }
            tramiteLicencia = em.merge(tramiteLicencia);
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.getTramites().remove(tramiteLicencia);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.getTramites().add(tramiteLicencia);
                personaNew = em.merge(personaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tramiteLicencia.getId();
                if (findTramiteLicencia(id) == null) {
                    throw new NonexistentEntityException("The tramiteLicencia with id " + id + " no longer exists.");
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
            TramiteLicencia tramiteLicencia;
            try {
                tramiteLicencia = em.getReference(TramiteLicencia.class, id);
                tramiteLicencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tramiteLicencia with id " + id + " no longer exists.", enfe);
            }
            Persona persona = tramiteLicencia.getPersona();
            if (persona != null) {
                persona.getTramites().remove(tramiteLicencia);
                persona = em.merge(persona);
            }
            em.remove(tramiteLicencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TramiteLicencia> findTramiteLicenciaEntities() {
        return findTramiteLicenciaEntities(true, -1, -1);
    }

    public List<TramiteLicencia> findTramiteLicenciaEntities(int maxResults, int firstResult) {
        return findTramiteLicenciaEntities(false, maxResults, firstResult);
    }

    private List<TramiteLicencia> findTramiteLicenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TramiteLicencia.class));
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

    public TramiteLicencia findTramiteLicencia(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TramiteLicencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getTramiteLicenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TramiteLicencia> rt = cq.from(TramiteLicencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
