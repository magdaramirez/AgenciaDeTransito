/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itson.implementaciones;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.itson.dominio.*;
import org.itson.dominio.TramiteLicencia;
import org.itson.excepciones.PersistenciaException;
import org.itson.interfaces.ILicenciasDAO;
import org.itson.interfaces.IPersonasDAO;
import org.itson.persistencia.ControlPersistencia;
import org.itson.persistencia.TramiteLicenciaJpaController;
import org.itson.utils.ConfiguracionPaginado;

/**
 *
 * @author koine
 */
public class LicenciasDAO implements ILicenciasDAO{
    private static final Logger LOG = Logger.getLogger(LicenciasDAO.class.getName());
    TramiteLicenciaJpaController jpaCont = new TramiteLicenciaJpaController();
    ControlPersistencia controlPersistencia = new ControlPersistencia();
    /**
     * Metodo que registra una licencia en el sistema
     * @param licencia la licencia a registrar
     * @return la licencia registrada
     * @throws PersistenciaException en caso de que no haya sido posible registrar la licencia ingresada
     */
    @Override
    public TramiteLicencia registrarLicencia(TramiteLicencia licencia) throws PersistenciaException{
       try {
        jpaCont.create(licencia);
        return licencia;
        } catch (Exception ex) {
            Logger.getLogger(PlacasDAO.class.getName()).log(Level.SEVERE, null, ex);
            LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible registrar la licencia");
        }
    }
    /**
     * Metodo que genera una lista con todas las licencias de una persona por su rfc
     * @param rfc el rfc de la persona a consultar
     * @return la lista de las licencias
     * @throws PersistenciaException en caso de que no haya sido posible consultar las licencias
     */
    @Override
    public List<TramiteLicencia> consultarLicenciasPersona(String rfc) throws PersistenciaException{   
        try {
        EntityManager entityManager = jpaCont.getEntityManager();
        
        IPersonasDAO persona1 = new PersonasDAO();
        
        Persona p1 = persona1.buscarPersona(rfc);
        
        String jpqlQuery1 = "SELECT v FROM TramiteLicencia v WHERE v.persona = :idPersona ";
        TypedQuery<TramiteLicencia> query1 = entityManager.createQuery(jpqlQuery1,TramiteLicencia.class);
        query1.setParameter("idPersona", p1);
               
        List<TramiteLicencia> licencias = query1.getResultList();
        
        if(licencias == null){
            throw new PersistenciaException("No se encontraron licencias de la persona consultada");
        }
        
        return licencias;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible consultar las licencias de la persona consultada");
        } 
    }
    /**
     * Metodo que cambia los datos de una licencia ya registrada en el sistema
     * @param tramLic la licencia a actualizar
     */
    @Override
    public void cambiarEstadoLicencia(TramiteLicencia tramLic) {
        try {
            jpaCont.edit(tramLic);
        } catch (Exception ex) {
            Logger.getLogger(PlacasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Metodo que crea una consulta personalizada dependiendo de los datos que se ingresen en la interfaz grafica
     * @param paginado el numero de los datos que apareceran en la tabla por pagina
     * @param licenciaDTO los datos que se usaran para los filtros
     * @return una lista de licencias de personas
     * @throws PersistenciaException en caso de no poder realizarse la busqueda
     */
    @Override
    public List<TramiteLicencia> consultarLicencias(ConfiguracionPaginado paginado, LicenciaDTO licenciaDTO) throws PersistenciaException {
        EntityManager entityManager = jpaCont.getEntityManager();    

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TramiteLicencia> criteria = builder.createQuery(TramiteLicencia.class);
        //ROOT para obtener la entidad videojuego
        Root<TramiteLicencia> root = criteria.from(TramiteLicencia.class);
        //JOIN para hacer una busqueda con inner join a una llame foranea
        Join<TramiteLicencia,Persona> dept = root.join("persona", JoinType.INNER);
        
        List<Predicate> filtros = new LinkedList<>();
        if(licenciaDTO.getNombre() != null){
            filtros.add(builder.like(dept.get("nombre"),"%"+licenciaDTO.getNombre()+"%"));
        }
        if(licenciaDTO.getAnioNacimiento()!= null){
            Calendar nacim1 = Calendar.getInstance();
            Calendar nacim2 = Calendar.getInstance();
            nacim1.set(licenciaDTO.getAnioNacimiento(), 0, 0);
            nacim2.set(licenciaDTO.getAnioNacimiento(), 11, 30);
            filtros.add(builder.greaterThanOrEqualTo(dept.<Calendar>get("fechaNacimiento"),nacim1));
            filtros.add(builder.lessThanOrEqualTo(dept.<Calendar>get("fechaNacimiento"),nacim2));
        }
        if(licenciaDTO.getRfc() != null){
            filtros.add(builder.equal(dept.get("rfc"),licenciaDTO.getRfc()));
        }
        if(licenciaDTO.getFechaInicio() != null && licenciaDTO.getFechaFin() != null ){
            Calendar nacim1 = licenciaDTO.getFechaInicio();
            Calendar nacim2 = licenciaDTO.getFechaFin();
            filtros.add(builder.greaterThanOrEqualTo(dept.<Calendar>get("fechaNacimiento"),nacim1));
            filtros.add(builder.lessThanOrEqualTo(dept.<Calendar>get("fechaNacimiento"),nacim2));
        }
        
        switch (filtros.size()) {
                case 1:
                    criteria = criteria.select(root).where(
                            builder.and(
                                    filtros.toArray(new Predicate[1]))
                    );  break;
                case 2:
                    criteria = criteria.select(root).where(
                            builder.and(
                                    filtros.toArray(new Predicate[2]))
                    );  break;
                case 3:
                    criteria = criteria.select(root).where(
                            builder.and(
                                    filtros.toArray(new Predicate[3]))
                    );  break;
                case 4:
                    criteria = criteria.select(root).where(
                            builder.and(
                                    filtros.toArray(new Predicate[4]))
                    );  break;
            }

        TypedQuery<TramiteLicencia> query = entityManager.createQuery(criteria);
        query.setMaxResults(paginado.getElementosPagina());
        query.setFirstResult(paginado.getNumPagina());
       
        
        List<TramiteLicencia> licencias = query.getResultList();
        
        
        return licencias;
        
    }
    
}
