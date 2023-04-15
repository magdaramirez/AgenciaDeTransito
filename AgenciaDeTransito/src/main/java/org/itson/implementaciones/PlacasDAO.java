/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import org.itson.dominio.Persona;
import org.itson.dominio.Placa;
import org.itson.dominio.TramitePlaca;
import org.itson.dominio.VehiculoAutomovil;
import org.itson.excepciones.PersistenciaException;
import org.itson.interfaces.IPersonasDAO;
import org.itson.interfaces.IPlacasDAO;
import org.itson.interfaces.IVehiculoDAO;
import org.itson.persistencia.TramitePlacaJpaController;
import org.itson.utils.ConfiguracionPaginado;

/**
 *
 * @author magda
 */
public class PlacasDAO implements IPlacasDAO {

    private static final Logger LOG = Logger.getLogger(PlacasDAO.class.getName());
    TramitePlacaJpaController jpaCont = new TramitePlacaJpaController();

    /**
     * Método que genera placas con el formato AAA-111.
     *
     * @return cadena de texto con el formato AAA-111.
     */
    @Override
    public String generarPlaca() {
        char placa[] = new char[9];
        placa[0] = generarLetra();
        placa[1] = generarLetra();
        placa[2] = generarLetra();
        placa[3] = '-';
        placa[4] = generarNumero();
        placa[5] = generarNumero();
        placa[6] = generarNumero();
        placa[7] = '-';
        placa[8] = generarLetra();

        return String.valueOf(placa);
    }

    /**
     * Método que genera una letra aleatoria.
     *
     * @return letra aleatoria.
     */
    @Override
    public char generarLetra() {
        return generarRandomChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /**
     * Método que genera un número aleatorio.
     *
     * @return número aleatorio.
     */
    @Override
    public char generarNumero() {
        return generarRandomChar("0123456789");
    }

    /**
     * Método que aleatoriza caracteres de una cadena de texto.
     *
     * @param st Cadena de texto con los caracteres a aleatorizar.
     * @return caracteres aleatorizados.
     */
    @Override
    public char generarRandomChar(String st) {
        char caracteres[] = st.toCharArray();
        int index = (int) (Math.random() * caracteres.length);
        return caracteres[index];
    }

    /**
     * Metodo que registra un Tramite de placa en la base de datos
     *
     * @param tramPl la placa a registrar en el sistema
     * @return la placa registrada al momento
     */
    @Override
    public TramitePlaca registrarPlaca(TramitePlaca tramPl) {
        jpaCont.create(tramPl);
        return tramPl;
    }

    /**
     * Metodo que busca una lista de placas por persona
     *
     * @param rfc el rfc de la persona a generar la lista de las placas
     * @return una lista con todas las placas que posee la persona consultada
     * @throws PersistenciaException en caso de que no se haya podido encontrar
     * alguna placa
     */
    @Override
    public List<TramitePlaca> consultarPlacasPersona(String rfc) throws PersistenciaException {
        EntityManager entityManager = jpaCont.getEntityManager();

        IPersonasDAO persona1 = new PersonasDAO();

        Persona p1;
        try {
            p1 = persona1.buscarPersona(rfc);
            String jpqlQuery1 = "SELECT v FROM TramitePlaca v WHERE v.idPersona = :idPersona";
            TypedQuery<TramitePlaca> query1 = entityManager.createQuery(jpqlQuery1, TramitePlaca.class);
            query1.setParameter("idPersona", p1.getId());

            List<TramitePlaca> placas = query1.getResultList();

            if (placas == null) {
                throw new PersistenciaException("No se encontraron placas de la persona consultada");
            }

            return placas;
        } catch (PersistenciaException ex) {
            Logger.getLogger(PlacasDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("No se pudieron consultar las placas de la persona consultada");
        }

    }

    /**
     * Metodo que cambia el estado una placa ya en el sistema
     *
     * @param tramPl la placa a modificar su estado
     */
    @Override
    public void cambiarEstadoPlaca(TramitePlaca tramPl) {
        try {
            jpaCont.edit(tramPl);
        } catch (Exception ex) {
            Logger.getLogger(PlacasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que busca una placa por su numero de placa
     *
     * @param placa la placa a buscar en el sistema
     * @return la placa consultada
     */
    @Override
    public TramitePlaca buscarPlacaPorNumero(String placa) throws PersistenciaException {
        EntityManager entityManager = jpaCont.getEntityManager();

        String jpqlQuery1 = "SELECT v FROM TramitePlaca v WHERE v.placa = :placa";
        TypedQuery<TramitePlaca> query1 = entityManager.createQuery(jpqlQuery1, TramitePlaca.class);
        query1.setParameter("placa", placa);

        TramitePlaca placaRes = query1.getSingleResult();

        if (placaRes == null) {
            throw new PersistenciaException("No se encontro una placa con el numero de placa ingresado");
        }

        return placaRes;

    }

    /**
     * Metodo que genera una lista con todas las placas de un automovil
     *
     * @param noSerie el numero de serie del automovil a consultar
     * @return la lista de todas las placas del automovil
     * @throws PersistenciaException en caso de que no se haya podido consultar
     * las placas
     */
    @Override
    public List<TramitePlaca> consultarPlacasNoSerie(String noSerie) throws PersistenciaException {
        EntityManager entityManager = jpaCont.getEntityManager();

        IVehiculoDAO automovil = new VehiculoAutomovilDAO();

        VehiculoAutomovil auto;
        try {
            auto = automovil.buscarAutomovil(noSerie);
            String jpqlQuery1 = "SELECT v FROM TramitePlaca v WHERE v.vehiculo = :vehiculo";
            TypedQuery<TramitePlaca> query1 = entityManager.createQuery(jpqlQuery1, TramitePlaca.class);
            query1.setParameter("vehiculo", auto);

            List<TramitePlaca> placas = query1.getResultList();

            if (placas == null) {
                throw new PersistenciaException("No se encontraron placas para el automovil consultado");
            }

            return placas;
        } catch (PersistenciaException ex) {
            Logger.getLogger(PlacasDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("No fue posible consultar las placas del automovil ingresado");
        }
    }

    /**
     * Metodo que crea una consulta personalizada dependiendo de los datos que
     * se ingresen en la interfaz grafica
     *
     * @param paginado la cantidad de elementos a mostrar en la tabla
     * @param placasDTO los parametros a utilizar para la busqueda
     * @return la lista de tramiteas de placas registradas en el sistema
     * @throws PersistenciaException en caso de no poder realizar la consulta
     * por algun error de sintaxis
     */
    @Override
    public List<TramitePlaca> consultarPlacas(ConfiguracionPaginado paginado, Placa placasDTO) throws PersistenciaException {
        EntityManager entityManager = jpaCont.getEntityManager();
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TramitePlaca> criteria = builder.createQuery(TramitePlaca.class);
            //ROOT para obtener la entidad videojuego
            Root<TramitePlaca> root = criteria.from(TramitePlaca.class);
            //JOIN para hacer una busqueda con inner join a una llame foranea
            Join<TramitePlaca, Persona> dept = root.join("persona", JoinType.INNER);

            List<Predicate> filtros = new LinkedList<>();
            if (placasDTO.getNombre() != null) {
                filtros.add(builder.like(dept.get("nombre"), "%" + placasDTO.getNombre() + "%"));
            }
            if (placasDTO.getAnioNacimiento() != null) {
                Calendar nacim1 = Calendar.getInstance();
                Calendar nacim2 = Calendar.getInstance();
                nacim1.set(placasDTO.getAnioNacimiento(), 0, 0);
                nacim2.set(placasDTO.getAnioNacimiento(), 11, 30);
                filtros.add(builder.greaterThanOrEqualTo(dept.<Calendar>get("fechaNacimiento"), nacim1));
                filtros.add(builder.lessThanOrEqualTo(dept.<Calendar>get("fechaNacimiento"), nacim2));
            }
            if (placasDTO.getRfc() != null) {
                filtros.add(builder.equal(dept.get("rfc"), placasDTO.getRfc()));
            }
            if (placasDTO.getFechaInicio() != null && placasDTO.getFechaFin() != null) {
                Calendar nacim1 = placasDTO.getFechaInicio();
                Calendar nacim2 = placasDTO.getFechaFin();
                filtros.add(builder.greaterThanOrEqualTo(root.<Calendar>get("fechaEmision"),nacim1));
                filtros.add(builder.lessThanOrEqualTo(root.<Calendar>get("fechaEmision"),nacim2));
            }

            switch (filtros.size()) {
                case 1:
                    criteria = criteria.select(root).where(
                            builder.and(
                                    filtros.toArray(new Predicate[1]))
                    );
                    break;
                case 2:
                    criteria = criteria.select(root).where(
                            builder.and(
                                    filtros.toArray(new Predicate[2]))
                    );
                    break;
                case 3:
                    criteria = criteria.select(root).where(
                            builder.and(
                                    filtros.toArray(new Predicate[3]))
                    );
                    break;
                case 4:
                    criteria = criteria.select(root).where(
                            builder.and(
                                    filtros.toArray(new Predicate[4]))
                    );
                    break;
            }

            TypedQuery<TramitePlaca> query = entityManager.createQuery(criteria);
            query.setMaxResults(paginado.getElementosPagina());
            query.setFirstResult(paginado.getNumPagina());

            List<TramitePlaca> placas = query.getResultList();

            return placas;
        } catch (Exception e) {
            System.out.println("No hay nada..." + e.getMessage());
            throw new PersistenciaException("No fue posible consultar las placas de la persona");
        }
    }
}
