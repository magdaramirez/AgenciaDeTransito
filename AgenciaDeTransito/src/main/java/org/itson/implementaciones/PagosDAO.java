/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itson.implementaciones;

import org.itson.dominio.Pago;
import org.itson.interfaces.IPagosDAO;
import org.itson.persistencia.PagoJpaController;

/**
 *
 * @author koine
 */
public class PagosDAO implements IPagosDAO {

    PagoJpaController jpaCont = new PagoJpaController();

    /**
     * Metodo que registra un pago realizado en alguno de los tramites
     * disponibles
     *
     * @param pago el pago realizado a registrar en el sistema
     * @return el mismo pago que se registro
     */
    @Override
    public Pago registarPago(Pago pago) {
        jpaCont.create(pago);
        return pago;
    }

}
