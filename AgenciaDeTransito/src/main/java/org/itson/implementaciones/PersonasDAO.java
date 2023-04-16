/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.implementaciones;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.itson.dominio.Persona;
import org.itson.excepciones.PersistenciaException;
import org.itson.interfaces.IPersonasDAO;
import org.itson.persistencia.ControlPersistencia;
import org.itson.persistencia.PersonaJpaController;
import org.itson.persistencia.TramiteLicenciaJpaController;
import org.itson.utils.Encriptador;

/**
 * Clase que maneja la lógica de las personas.
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
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
        Persona persona1 = new Persona(encriptador.encriptar("Magda Paola"), encriptador.encriptar("Ramirez"), encriptador.encriptar("Escalante"), new GregorianCalendar(2003, Calendar.MARCH, 3), "6688293570", "RAEM0303031L0");
        Persona persona2 = new Persona(encriptador.encriptar("Juan"), encriptador.encriptar("Martinez"), encriptador.encriptar("Ramos"), new GregorianCalendar(1987, Calendar.JANUARY, 15), "5546789012", "MARJ870115XYZ");
        Persona persona3 = new Persona(encriptador.encriptar("Karla"), encriptador.encriptar("Lopez"), encriptador.encriptar("Jimenez"), new GregorianCalendar(1995, Calendar.MARCH, 12), "2894561230", "LOJK950312HAJ");
        Persona persona4 = new Persona(encriptador.encriptar("Marco"), encriptador.encriptar("Sanchez"), encriptador.encriptar("Torres"), new GregorianCalendar(1983, Calendar.APRIL, 7), "7778901234", "SATM830407TAS");
        Persona persona5 = new Persona(encriptador.encriptar("Ana Lucía"), encriptador.encriptar("Flores"), encriptador.encriptar("Hernandez"), new GregorianCalendar(1992, Calendar.MAY, 22), "9876543201", "FOHA920522JUS");
        Persona persona6 = new Persona(encriptador.encriptar("Pedro"), encriptador.encriptar("Torres"), encriptador.encriptar("Perez"), new GregorianCalendar(1999, Calendar.AUGUST, 9), "4561238907", "TOPP990809XXG");
        Persona persona7 = new Persona(encriptador.encriptar("Sofia"), encriptador.encriptar("Mendoza"), encriptador.encriptar("Garcia"), new GregorianCalendar(1995, Calendar.NOVEMBER, 15), "5512345678", "MEGS951115FTS");
        Persona persona8 = new Persona(encriptador.encriptar("Maria Fernanda"), encriptador.encriptar("Islas"), encriptador.encriptar("Rojo"), new GregorianCalendar(2001, Calendar.FEBRUARY, 26), "6622345678", "IARM010226KAS");
        Persona persona9 = new Persona(encriptador.encriptar("Alonso"), encriptador.encriptar("Rivera"), encriptador.encriptar("Martinez"), new GregorianCalendar(1970, Calendar.JUNE, 5), "6671234567", "RIMA700605LSD");
        Persona persona10 = new Persona(encriptador.encriptar("Daniela"), encriptador.encriptar("Rodriguez"), encriptador.encriptar("Cota"), new GregorianCalendar(2004, Calendar.JULY, 30), "6445678901", "ROCD040730KSA");
        Persona persona11 = new Persona(encriptador.encriptar("Gema"), encriptador.encriptar("Aguilar"), encriptador.encriptar("Picos"), new GregorianCalendar(1982, Calendar.SEPTEMBER, 1), "5472189630", "AUPG820901ODH");
        Persona persona12 = new Persona(encriptador.encriptar("Valentina"), encriptador.encriptar("Castro"), encriptador.encriptar("Rios"), new GregorianCalendar(1975, Calendar.OCTOBER, 7), "6231758946", "CARV751007SLV");
        Persona persona13 = new Persona(encriptador.encriptar("Alejandro"), encriptador.encriptar("Ruiz"), encriptador.encriptar("Torres"), new GregorianCalendar(1988, Calendar.DECEMBER, 23), "9375082194", "RUTA881223EFI");
        Persona persona14 = new Persona(encriptador.encriptar("Michell"), encriptador.encriptar("Cedano"), encriptador.encriptar("Lopez"), new GregorianCalendar(2003, Calendar.JULY, 6), "6448216093", "CELM030706GJS");
        Persona persona15 = new Persona(encriptador.encriptar("Diego"), encriptador.encriptar("Lujan"), encriptador.encriptar("Gonzalez"), new GregorianCalendar(2000, Calendar.JANUARY, 27), "6675558899", "LUGD000127WKV");
        Persona persona16 = new Persona(encriptador.encriptar("Valeria Guadalupe"), encriptador.encriptar("Montiel"), encriptador.encriptar("Luna"), new GregorianCalendar(1991, Calendar.MAY, 2), "6641234567", "MOLV910502KHL");
        Persona persona17 = new Persona(encriptador.encriptar("Licema"), encriptador.encriptar("Sevilla"), encriptador.encriptar("Soto"), new GregorianCalendar(1994, Calendar.FEBRUARY, 11), "6647894697", "SESL940211LGH");
        Persona persona18 = new Persona(encriptador.encriptar("Victor Manuel"), encriptador.encriptar("Osuna"), encriptador.encriptar("Benard"), new GregorianCalendar(2002, Calendar.APRIL, 17), "6688965412", "OSBV020417PSI");
        Persona persona19 = new Persona(encriptador.encriptar("Aria"), encriptador.encriptar("Fernandez"), encriptador.encriptar("Rios"), new GregorianCalendar(2004, Calendar.DECEMBER, 13), "6688145654", "FERA041213LFV");
        Persona persona20 = new Persona(encriptador.encriptar("Federico"), encriptador.encriptar("Rivas"), encriptador.encriptar("Chávez"), new GregorianCalendar(1985, Calendar.AUGUST, 14), "6645874562", "RICF850814BFJ");

        Persona[] personas = new Persona[]{persona1, persona2, persona3, persona4, persona5, persona6, persona7, persona8, persona9, persona10, persona11, persona12, persona13, persona14, persona15, persona16, persona17, persona18, persona19, persona20};
        PersonaJpaController personaJpa = new PersonaJpaController();
        
        for (int i = 0; i < personas.length; i++) {
            Persona persona = personas[i];
            controlPersistencia.guardar(persona);
        }
    }

    /**
     * Metodo que busca a una persona por su numero de rfc
     *
     * @param rfc el rfc de la persona consutlada
     * @return la persona que se busca
     * @throws PersistenciaException en caso de que no se haya encontrado
     * ninguna persona con el rfc consultado
     */
    @Override
    public Persona buscarPersona(String rfc) throws PersistenciaException {
        EntityManager entityManager = jpaCont.getEntityManager();

        String jpqlQuery2 = "SELECT v FROM Persona v WHERE v.rfc = :rfc";
        TypedQuery<Persona> query2 = entityManager.createQuery(jpqlQuery2, Persona.class);
        query2.setParameter("rfc", rfc);

        Persona persona = query2.getSingleResult();

        if (persona == null) {
            throw new PersistenciaException("No se encontraron personas con el rfc ingresado");
        }

        return persona;

    }

    /**
     * Metodo que busca a una persona por su numero de rfc
     *
     * @return la persona que se busca
     * @throws PersistenciaException en caso de que no se haya encontrado
     * ninguna persona con el rfc consultado
     */
    @Override
    public List<Persona> buscarPersonasActivas() throws PersistenciaException {
        EntityManager entityManager = jpaCont.getEntityManager();

        String jpqlQuery2 = "SELECT v FROM Persona v";
        TypedQuery<Persona> query2 = entityManager.createQuery(jpqlQuery2, Persona.class);

        List<Persona> personas = query2.getResultList();

        if (personas == null) {
            throw new PersistenciaException("No se encontraron personas en el sistema");
        }

        return personas;

    }

    /**
     * Metodo que desencripta el nombre de la persona para mostrarlo en los
     * campos de apoyo.
     *
     * @param persona1 la persona que se desencriptara
     * @return el nombre completo de la persona
     */
    @Override
    public String DesencriptarNombreCompleto(Persona persona1) {

        String nombre = persona1.getNombre();
        String apellidoPat = persona1.getApellidoPaterno();
        String apellidoMat = persona1.getApellidoMaterno();

        String nomDes = encriptador.desencriptar(nombre);
        String apellidoPatDes = encriptador.desencriptar(apellidoPat);
        String apellidoMatDes = encriptador.desencriptar(apellidoMat);

        String nombreCompleto = nomDes + " " + apellidoPatDes + " " + apellidoMatDes;

        return nombreCompleto;
    }

}
