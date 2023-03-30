/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.itson.agenciatransito;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.itson.dominio.EstadoTramite;
import org.itson.dominio.Pago;
import org.itson.dominio.Persona;
import org.itson.dominio.TipoLicencia;
import org.itson.dominio.TipoPlaca;
import org.itson.dominio.TramiteLicencia;
import org.itson.dominio.TramitePlaca;
import org.itson.dominio.VehiculoAutomovil;
import org.itson.implementaciones.ConstantesGUI;

/**
 *
 * @author magda
 */
public class AgenciaTransito {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("org.itson.agenciatransito");
        EntityManager entityManager = emFactory.createEntityManager();
    
        //INICIO DE LA TRANSACCIÓN
        entityManager.getTransaction().begin();
        
        Persona persona = new Persona();
        Calendar fecha = new GregorianCalendar();
        fecha.set(2008, 8, 1);
        
        persona.setNombre("Magda");
        persona.setApellidoPaterno("Ramirez");
        persona.setApellidoMaterno("Escalante");
        persona.setFechaNacimiento(fecha);
        persona.setTelefono("6688293570");
        persona.setCurp("RAEM030303MSLMSGA4");
        persona.setRfc("RAEM0303031L0");
        
        Long idPersona = 1L;
        
        Long idAuto = 1L;
        
        //---- SELECT BUSQUEDA
        
        Persona persona2 = entityManager.find(Persona.class, idPersona);
        
        VehiculoAutomovil auto2 = entityManager.find(VehiculoAutomovil.class, idAuto);
        
        
        //--------------------
        
        TramiteLicencia licencia1 = new TramiteLicencia(TipoLicencia.NORMAL,ConstantesGUI.ANIO3,EstadoTramite.ACTIVO,ConstantesGUI.COSTOLICENCIA_NORMAL3,persona2);
        
        Pago pago = new Pago(licencia1.getCosto(), fecha, licencia1);
        
        //---------------------
        
        VehiculoAutomovil auto1 = new VehiculoAutomovil("BAG-324", "Mercedes", "2013", "Azul", "Civic");
        
        TramitePlaca placa1 = new TramitePlaca("BNS-432", fecha, auto2, TipoPlaca.NUEVO, EstadoTramite.ACTIVO, ConstantesGUI.COSTOVEHICULO_NUEVO, persona2);
        
        Pago pago2 = new Pago(placa1.getCosto(), fecha, placa1);
        
        //PRUEBA 1
//        entityManager.persist(persona);
//        entityManager.persist(licencia1);
//        entityManager.persist(pago);

        //PRUEBA 2
//        entityManager.persist(auto1);
        entityManager.persist(placa1);
        entityManager.persist(pago2);
        entityManager.getTransaction().commit();
        
    }
}
