/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.itson.agenciatransito;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
        
//        Persona persona = new Persona();
        
//        persona.setNombre("Magda");
//        persona.setApellidoPaterno("Ramirez");
//        persona.setApellidoMaterno("Escalante");
//        persona.setFechaNacimiento("2003-03-03");
//        persona.setTelefono("6688293570");
//        persona.setCurp("RAEM030303MSLMSGA4");
//        persona.setRfc("RAEM0303031L0");
        
//        entityManager.persist(persona);
//        entityManager.getTransaction().commit();
        
    }
}
