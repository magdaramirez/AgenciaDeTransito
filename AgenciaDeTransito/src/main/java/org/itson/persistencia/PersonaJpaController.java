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
import org.itson.dominio.Tramite;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.itson.dominio.Persona;
import org.itson.excepciones.*;

/**
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    
    public PersonaJpaController() {
        emf = Persistence.createEntityManagerFactory("org.itson.agenciatransito");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) {
        if (persona.getTramites() == null) {
            persona.setTramites(new ArrayList<Tramite>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Tramite> attachedTramites = new ArrayList<Tramite>();
            for (Tramite tramitesTramiteToAttach : persona.getTramites()) {
                tramitesTramiteToAttach = em.getReference(tramitesTramiteToAttach.getClass(), tramitesTramiteToAttach.getId());
                attachedTramites.add(tramitesTramiteToAttach);
            }
            persona.setTramites(attachedTramites);
            em.persist(persona);
            for (Tramite tramitesTramite : persona.getTramites()) {
                Persona oldPersonaOfTramitesTramite = tramitesTramite.getPersona();
                tramitesTramite.setPersona(persona);
                tramitesTramite = em.merge(tramitesTramite);
                if (oldPersonaOfTramitesTramite != null) {
                    oldPersonaOfTramitesTramite.getTramites().remove(tramitesTramite);
                    oldPersonaOfTramitesTramite = em.merge(oldPersonaOfTramitesTramite);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getId());
            List<Tramite> tramitesOld = persistentPersona.getTramites();
            List<Tramite> tramitesNew = persona.getTramites();
            List<String> illegalOrphanMessages = null;
            for (Tramite tramitesOldTramite : tramitesOld) {
                if (!tramitesNew.contains(tramitesOldTramite)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tramite " + tramitesOldTramite + " since its persona field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Tramite> attachedTramitesNew = new ArrayList<Tramite>();
            for (Tramite tramitesNewTramiteToAttach : tramitesNew) {
                tramitesNewTramiteToAttach = em.getReference(tramitesNewTramiteToAttach.getClass(), tramitesNewTramiteToAttach.getId());
                attachedTramitesNew.add(tramitesNewTramiteToAttach);
            }
            tramitesNew = attachedTramitesNew;
            persona.setTramites(tramitesNew);
            persona = em.merge(persona);
            for (Tramite tramitesNewTramite : tramitesNew) {
                if (!tramitesOld.contains(tramitesNewTramite)) {
                    Persona oldPersonaOfTramitesNewTramite = tramitesNewTramite.getPersona();
                    tramitesNewTramite.setPersona(persona);
                    tramitesNewTramite = em.merge(tramitesNewTramite);
                    if (oldPersonaOfTramitesNewTramite != null && !oldPersonaOfTramitesNewTramite.equals(persona)) {
                        oldPersonaOfTramitesNewTramite.getTramites().remove(tramitesNewTramite);
                        oldPersonaOfTramitesNewTramite = em.merge(oldPersonaOfTramitesNewTramite);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = persona.getId();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Tramite> tramitesOrphanCheck = persona.getTramites();
            for (Tramite tramitesOrphanCheckTramite : tramitesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Tramite " + tramitesOrphanCheckTramite + " in its tramites field has a non-nullable persona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
