/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.implementaciones;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.itson.dominio.Persona;
import org.itson.dominio.TramitePlaca;
import org.itson.excepciones.PersistenciaException;
import org.itson.interfaces.IPersonasDAO;
import org.itson.persistencia.ControlPersistencia;
import org.itson.persistencia.TramiteLicenciaJpaController;
import org.itson.utils.Encriptador;

/**
 *
 * @author magda
 */
public class PersonasDAO implements IPersonasDAO {
    private static final Logger LOG = Logger.getLogger(PersonasDAO.class.getName());
    TramiteLicenciaJpaController jpaCont = new TramiteLicenciaJpaController();
    ControlPersistencia controlPersistencia = new ControlPersistencia();
    Encriptador encriptador = new Encriptador();
    
    /**
     * Método que registra 20 personas en la base de datos.
     */
    @Override
    public void insertarPersonas() {
     Persona persona1 = new Persona(encriptador.encriptar("Magda Paola"), encriptador.encriptar("Ramirez"), encriptador.encriptar("Escalante"), new GregorianCalendar(2003, 3, Calendar.MARCH), "6688293570", "RAEM0303031L0");
        controlPersistencia.guardar(persona1);

        Persona persona2 = new Persona(encriptador.encriptar("Juan"), encriptador.encriptar("Martinez"), encriptador.encriptar("Ramos"), new GregorianCalendar(1987, 15, Calendar.JANUARY), "5546789012", "MARJ870115XYZ");
        controlPersistencia.guardar(persona2);

        Persona persona3 = new Persona(encriptador.encriptar("Karla"), encriptador.encriptar("Lopez"), encriptador.encriptar("Jimenez"), new GregorianCalendar(1995, 12, Calendar.MARCH), "2894561230", "LOJK950312HAJ");
        controlPersistencia.guardar(persona3);

        Persona persona4 = new Persona(encriptador.encriptar("Marco"), encriptador.encriptar("Sanchez"), encriptador.encriptar("Torres"), new GregorianCalendar(1983, 7, Calendar.APRIL), "7778901234", "SATM830407TAS");
        controlPersistencia.guardar(persona4);

        Persona persona5 = new Persona(encriptador.encriptar("Ana Lucía"), encriptador.encriptar("Flores"), encriptador.encriptar("Hernandez"), new GregorianCalendar(1992, 22, Calendar.MAY), "9876543201", "FOHA920522JUS");
        controlPersistencia.guardar(persona5);

        Persona persona6 = new Persona(encriptador.encriptar("Pedro"), encriptador.encriptar("Torres"), encriptador.encriptar("Perez"), new GregorianCalendar(1999, 9, Calendar.AUGUST), "4561238907", "TOPP990809XXG");
        controlPersistencia.guardar(persona6);

        Persona persona7 = new Persona(encriptador.encriptar("Sofia"), encriptador.encriptar("Mendoza"), encriptador.encriptar("Garcia"), new GregorianCalendar(1995, 15, Calendar.NOVEMBER), "5512345678", "MEGS951115FTS");
        controlPersistencia.guardar(persona7);

        Persona persona8 = new Persona(encriptador.encriptar("Maria Fernanda"), encriptador.encriptar("Islas"), encriptador.encriptar("Rojo"), new GregorianCalendar(2001, 26, Calendar.FEBRUARY), "6622345678", "IARM010226KAS");
        controlPersistencia.guardar(persona8);

        Persona persona9 = new Persona(encriptador.encriptar("Alonso"), encriptador.encriptar("Rivera"), encriptador.encriptar("Martinez"), new GregorianCalendar(1970, 5, Calendar.JUNE), "6671234567", "RIMA700605LSD");
        controlPersistencia.guardar(persona9);

        Persona persona10 = new Persona(encriptador.encriptar("Daniela"), encriptador.encriptar("Rodriguez"),encriptador.encriptar("Cota"), new GregorianCalendar(2004, 30, Calendar.JULY), "6445678901", "ROCD040730KSA");
        controlPersistencia.guardar(persona10);

        Persona persona11 = new Persona(encriptador.encriptar("Gema"),encriptador.encriptar("Aguilar"),encriptador.encriptar("Picos"), new GregorianCalendar(1982, 1, Calendar.SEPTEMBER), "5472189630", "AUPG820901ODH");
        controlPersistencia.guardar(persona11);

        Persona persona12 = new Persona(encriptador.encriptar("Valentina"), encriptador.encriptar("Castro"), encriptador.encriptar("Rios"), new GregorianCalendar(1975, 7, Calendar.OCTOBER), "6231758946", "CARV751007SLV");
        controlPersistencia.guardar(persona12);

        Persona persona13 = new Persona(encriptador.encriptar("Alejandro"), encriptador.encriptar("Ruiz"), encriptador.encriptar("Torres"), new GregorianCalendar(1988, 23, Calendar.DECEMBER), "9375082194", "RUTA881223EFI");
        controlPersistencia.guardar(persona13);

        Persona persona14 = new Persona(encriptador.encriptar("Michell"), encriptador.encriptar("Cedano"), encriptador.encriptar("Lopez"), new GregorianCalendar(2003, 6, Calendar.JULY), "6448216093", "CELM030706GJS");
        controlPersistencia.guardar(persona14);

        Persona persona15 = new Persona(encriptador.encriptar("Diego"), encriptador.encriptar("Lujan"), encriptador.encriptar("Gonzalez"), new GregorianCalendar(2000, 27, Calendar.JANUARY), "6675558899", "LUGD000127WKV");
        controlPersistencia.guardar(persona15);

        Persona persona16 = new Persona(encriptador.encriptar("Valeria Guadalupe"),encriptador.encriptar("Montiel"),encriptador.encriptar("Luna"), new GregorianCalendar(1991, 2, Calendar.MAY), "6641234567", "MOLV910502KHL");
        controlPersistencia.guardar(persona16);

        Persona persona17 = new Persona(encriptador.encriptar("Licema"), encriptador.encriptar("Sevilla"), encriptador.encriptar("Soto"), new GregorianCalendar(1994, 11, Calendar.FEBRUARY), "6647894697", "SESL940211LGH");
        controlPersistencia.guardar(persona17);

        Persona persona18 = new Persona(encriptador.encriptar("Victor Manuel"), encriptador.encriptar("Osuna"), encriptador.encriptar("Benard"), new GregorianCalendar(2002, 17, Calendar.APRIL), "6688965412", "OSBV020417PSI");
        controlPersistencia.guardar(persona18);

        Persona persona19 = new Persona(encriptador.encriptar("Aria"), encriptador.encriptar("Fernandez"), encriptador.encriptar("Rios"), new GregorianCalendar(2004, 13, Calendar.DECEMBER), "6688145654", "FERA041213LFV");
        controlPersistencia.guardar(persona19);

        Persona persona20 = new Persona(encriptador.encriptar("Federico"),encriptador.encriptar("Rivas"), encriptador.encriptar("Chávez"), new GregorianCalendar(1985, 14, Calendar.AUGUST), "6645874562", "RICF850814BFJ");
        controlPersistencia.guardar(persona20);    
    }
    /**
     * Metodo que busca a una persona por su numero de rfc
     * @param rfc el rfc de la persona consutlada
     * @return la persona que se busca
     * @throws PersistenciaException en caso de que no se haya encontrado ninguna persona con el rfc consultado
     */
    @Override
    public Persona buscarPersona(String rfc) throws PersistenciaException{
        EntityManager entityManager = jpaCont.getEntityManager();
      
        String jpqlQuery2 = "SELECT v FROM Persona v WHERE v.rfc = :rfc";
        TypedQuery<Persona> query2 = entityManager.createQuery(jpqlQuery2,Persona.class);
        query2.setParameter("rfc", rfc);
        
        Persona persona = query2.getSingleResult();
        
        if(persona == null){
            throw new PersistenciaException("No se encontraron personas con el rfc ingresado");
        }
        
        return persona;

    }
    
    /**
     * Metodo que busca a una persona por su numero de rfc
     * @param rfc el rfc de la persona consutlada
     * @return la persona que se busca
     * @throws PersistenciaException en caso de que no se haya encontrado ninguna persona con el rfc consultado
     */
    @Override
    public List<Persona> buscarPersonasActivas() throws PersistenciaException{
        EntityManager entityManager = jpaCont.getEntityManager();
      
        String jpqlQuery2 = "SELECT v FROM Persona v";
        TypedQuery<Persona> query2 = entityManager.createQuery(jpqlQuery2,Persona.class);
        
        List<Persona> personas = query2.getResultList();
        
        if(personas == null){
            throw new PersistenciaException("No se encontraron personasen el sistema");
        }
        
        return personas;

    }

}
