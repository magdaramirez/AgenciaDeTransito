/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itson.GUI;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.itson.dominio.EstadoTramite;
import org.itson.dominio.Pago;
import org.itson.dominio.Persona;
import org.itson.dominio.TipoLicencia;
import org.itson.dominio.TramiteLicencia;
import org.itson.excepciones.PersistenciaException;
import org.itson.implementaciones.ConstantesGUI;
import org.itson.implementaciones.LicenciasDAO;
import org.itson.implementaciones.PagosDAO;
import org.itson.implementaciones.PersonasDAO;
import org.itson.interfaces.ILicenciasDAO;
import org.itson.interfaces.IPagosDAO;
import org.itson.interfaces.IPersonasDAO;
import org.itson.utils.Encriptador;
import org.itson.utils.Validadores;

/**
 *
 * @author koine
 */
public class DlgTramiteLicencia extends javax.swing.JDialog {
    IPersonasDAO personas = new PersonasDAO();
    ILicenciasDAO licencias = new LicenciasDAO();
    IPagosDAO pagos = new PagosDAO();
    Encriptador encriptador = new Encriptador();
    /**
     * Método que crea el JDialog DlgTramiteLicencia.
     *
     * @param parent
     * @param modal
     */
    public DlgTramiteLicencia(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("LICENCIA");
        txtCosto.setText("$0.00");
    }

    /**
     * Método que extrae los datos del JDialog.
     *
     * @return datos.
     */
    private HashMap<String, String> extraerDatos() {
        String rfc = this.txtRfc.getText();
        String tipo = this.cbxTipo.getSelectedItem().toString();
        String vigencia = this.cbxVigencia.getSelectedItem().toString();

        HashMap<String, String> datos = new HashMap<>();
        datos.put("rfc", rfc);
        datos.put("tipo", tipo);
        datos.put("vigencia", vigencia);

        return datos;
    }

    /**
     * Método que valida los datos del JDialog.
     *
     * @param datos Datos a validar.
     * @return errores encontrados al momento de validar.
     */
    private List<String> validarDatos(HashMap<String, String> datos) {
        List<String> erroresValidacion = new LinkedList<>();
        String rfc = datos.get("rfc");
        String tipo = datos.get("tipo");
        String vigencia = datos.get("vigencia");

        if (Validadores.esTextoVacio(rfc)) {
            erroresValidacion.add("Datos vacíos");
        }
        if (!Validadores.esRFC(rfc)) {
            erroresValidacion.add("Formato de RFC incorrecto");
        }
        if (Validadores.excedeLimite(rfc, 13)) {
            erroresValidacion.add("El RFC excede el límite de caracteres");
        }

        return erroresValidacion;
    }

    /**
     * Método que muestra los errores de validación.
     *
     * @param erroresValidacion Lista con los errores de validación.
     */
    private void mostrarErroresValidacion(List<String> erroresValidacion) {
        String mensaje = String.join("\n", erroresValidacion);

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "ERROR",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Método que lleva a cabo el trámite de licencia.
     */
    private void tramitarLicencia() {
        
        HashMap<String, String> datos = this.extraerDatos();
        
        List<String> erroresValidacion = this.validarDatos(datos);
        if (!erroresValidacion.isEmpty()) {
            this.mostrarErroresValidacion(erroresValidacion);
        }
        
        TramiteLicencia licencia;
        try {
            licencia = crearTramiteLicencia();
        
            licencias.registrarLicencia(licencia);
            registroPagoRealizado(licencia);
            JOptionPane.showMessageDialog(
                this,
                "El registro de la licencia se ha realizado correctamente",
                "INFORMACIÓN",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (PersistenciaException ex) {
            Logger.getLogger(DlgTramiteLicencia.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(
                this,
                "ERROR: El costo o la persona no han sido validados",
                "ERROR",
                JOptionPane.ERROR_MESSAGE);
        }
        
    }

    /**
     * Método que calcula los costos de las licencias dependiendo el tipo y la
     * vigencia seleccionada.
     */
    private Float calcularCosto() {
        Float costo=0f;
        if (cbxTipo.getSelectedIndex() == 1 && cbxVigencia.getSelectedIndex() == 1) {
            costo = 600.00f;
            txtCosto.setText("$" + costo);
        } else if (cbxTipo.getSelectedIndex() == 1 && cbxVigencia.getSelectedIndex() == 2) {
            costo = 900.00f;
            txtCosto.setText("$" + costo);
        } else if (cbxTipo.getSelectedIndex() == 1 && cbxVigencia.getSelectedIndex() == 3) {
            costo = 1100.00f;
            txtCosto.setText("$" + costo);
        } else if (cbxTipo.getSelectedIndex() == 2 && cbxVigencia.getSelectedIndex() == 1) {
            costo = 200.00f;
            txtCosto.setText("$" + costo);
        } else if (cbxTipo.getSelectedIndex() == 2 && cbxVigencia.getSelectedIndex() == 2) {
            costo = 500.00f;
            txtCosto.setText("$" + costo);
        } else if (cbxTipo.getSelectedIndex() == 2 && cbxVigencia.getSelectedIndex() == 3) {
            costo = 700.00f;
            txtCosto.setText("$" + costo);
        } else {
            txtCosto.setText("$0.00");
        }
        return costo;
    }
    /**
     * Metodo que desencripte el nombre de la persona ingresada
     * @param persona1 la persona a desencriptar el nombre
     * @return el nombre completo de la persona desencriptado
     */
    private String DesencriptarNombreCompleto(Persona persona1) {
        
        String nombre = persona1.getNombre();
        String apellidoPat = persona1.getApellidoPaterno();
        String apellidoMat = persona1.getApellidoMaterno();
        
        String nomDes = encriptador.desencriptar(nombre);
        String apellidoPatDes = encriptador.desencriptar(apellidoPat);
        String apellidoMatDes = encriptador.desencriptar(apellidoMat);
        
        String nombreCompleto = nomDes+" "+apellidoPatDes+" "+apellidoMatDes;
        
        return nombreCompleto;
    }
    
    /**
     * Método que vacía los datos del JDialog.
     */
    private void vaciarDatos() {
        txtRfc.setText(null);
        txtNombreCom.setText(null);
        txtCosto.setText("$0.00");
        cbxTipo.setSelectedIndex(0);
        cbxVigencia.setSelectedIndex(0);
    }
    /**
     * Metodo que busca la persona por el rfc ingresado en la interfaz grafica
     * @return la persona registrada en el sistema
     * @throws PersistenciaException en caso de que no haya sido encontrada la persona
     */
    private Persona buscarPersonaRFC() throws PersistenciaException{
        HashMap<String, String> datos = extraerDatos();
        String rfc = datos.get("rfc");
        try{
            Persona persona1 = personas.buscarPersona(rfc);
              
            return persona1;
        }catch (PersistenciaException ex){
            Logger.getLogger(DlgTramiteLicencia.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return null;
        
    }
    /**
     * Metodo que crea el tramite de la licencia a registrar en el sistema
     * @return un tramite licencia para registrar
     * @throws PersistenciaException en caso de que no haya sido posible obtener un dato para la creacion
     */
    private TramiteLicencia crearTramiteLicencia() throws PersistenciaException{
        HashMap<String, String> datos = extraerDatos();
        String tipo = datos.get("tipo");
        String vigencia = datos.get("vigencia");
        
        TramiteLicencia licencia = new TramiteLicencia();

        Persona persona1 = buscarPersonaRFC();
        Float costo = calcularCosto();
        if(!(costo == 0.0f)){
          licencia.setCosto(costo);
        }else{
            throw new PersistenciaException("El costo de la licencia no ha sido calculado");
        }
        licencia.setEstado(EstadoTramite.ACTIVO);
        licencia.setPersona(persona1);
        
        //TIPO
        if(tipo.equals("Normal")){
            licencia.setTipo(TipoLicencia.NORMAL);
        }else{
            licencia.setTipo(TipoLicencia.DISCAPACITADO);
        }
        
        //VIGENCIA AÑOS
        switch (vigencia) {
            case "1 año":
                licencia.setVigencia(ConstantesGUI.ANIO1);
                break;
            case "2 años":
                licencia.setVigencia(ConstantesGUI.ANIO2);
                break;
            case "3 años":
                licencia.setVigencia(ConstantesGUI.ANIO3);
                break;
        }
        
        Calendar fechaEmision = Calendar.getInstance();
        licencia.setFechaEmision(fechaEmision);
        
        return licencia;
    }
    /**
     * Metodo que registra el pago realizado para el tramite de la licencia
     * @param licencia el tramite a registrar su pago
     */
    private void registroPagoRealizado(TramiteLicencia licencia){
        Pago pago = new Pago();
        pago.setMonto(licencia.getCosto());
        pago.setTramite(licencia);
        
        Calendar fecha1 = Calendar.getInstance();
        pago.setFechaRealizacion(fecha1);
        
        pagos.registarPago(pago);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblRfc = new javax.swing.JLabel();
        lblNombreC = new javax.swing.JLabel();
        lbltipoLicencia = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxVigencia = new javax.swing.JComboBox<>();
        cbxTipo = new javax.swing.JComboBox<>();
        txtRfc = new javax.swing.JTextField();
        txtNombreCom = new javax.swing.JTextField();
        txtCosto = new javax.swing.JTextField();
        btnCalcularCosto = new javax.swing.JButton();
        lblBuscarRFC = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        btnVaciar = new javax.swing.JButton();
        btnTramitar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(249, 143, 143));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblTitulo.setText("Tramite de licencia");
        jPanel2.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 370, -1));

        jPanel3.setBackground(new java.awt.Color(251, 183, 183));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Datos del solicitante");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, -1, -1));

        lblRfc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRfc.setText("RFC:");
        jPanel3.add(lblRfc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        lblNombreC.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNombreC.setText("Nombre completo:");
        jPanel3.add(lblNombreC, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        lbltipoLicencia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbltipoLicencia.setText("Tipo de licencia");
        jPanel3.add(lbltipoLicencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Vigencia");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, -1, -1));

        cbxVigencia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxVigencia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "1 año", "2 años", "3 años" }));
        jPanel3.add(cbxVigencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 150, 110, 30));

        cbxTipo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "Normal", "Discapacitado" }));
        cbxTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTipoActionPerformed(evt);
            }
        });
        jPanel3.add(cbxTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, 110, 30));

        txtRfc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtRfc, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 230, 40));

        txtNombreCom.setEditable(false);
        txtNombreCom.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtNombreCom, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 270, 40));

        txtCosto.setEditable(false);
        txtCosto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCosto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostoActionPerformed(evt);
            }
        });
        jPanel3.add(txtCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 210, 90, 30));

        btnCalcularCosto.setBackground(new java.awt.Color(212, 100, 107));
        btnCalcularCosto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCalcularCosto.setForeground(new java.awt.Color(255, 255, 255));
        btnCalcularCosto.setText("Calcular Costo");
        btnCalcularCosto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularCostoActionPerformed(evt);
            }
        });
        jPanel3.add(btnCalcularCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, 120, 30));

        lblBuscarRFC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        lblBuscarRFC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBuscarRFCMouseClicked(evt);
            }
        });
        jPanel3.add(lblBuscarRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, -1, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 560, 270));

        btnRegresar.setBackground(new java.awt.Color(212, 100, 107));
        btnRegresar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRegresar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/volver.png"))); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        jPanel2.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 130, 40));

        btnVaciar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnVaciar.setForeground(new java.awt.Color(212, 100, 107));
        btnVaciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vaciar.png"))); // NOI18N
        btnVaciar.setText("Vaciar");
        btnVaciar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(212, 100, 107)));
        btnVaciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVaciarActionPerformed(evt);
            }
        });
        jPanel2.add(btnVaciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 360, 100, 30));

        btnTramitar.setBackground(new java.awt.Color(212, 100, 107));
        btnTramitar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTramitar.setForeground(new java.awt.Color(255, 255, 255));
        btnTramitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Aceptar.png"))); // NOI18N
        btnTramitar.setText("Tramitar");
        btnTramitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTramitarActionPerformed(evt);
            }
        });
        jPanel2.add(btnTramitar, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 350, 130, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 600, 400));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 440));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTramitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTramitarActionPerformed
        // TODO add your handling code here:
        tramitarLicencia();
        
        vaciarDatos();
    }//GEN-LAST:event_btnTramitarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.dispose();
        FrmRealizarTramite realizarTramite = new FrmRealizarTramite();
        realizarTramite.setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnVaciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVaciarActionPerformed
        // TODO add your handling code here:
        vaciarDatos();
    }//GEN-LAST:event_btnVaciarActionPerformed

    private void cbxTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxTipoActionPerformed

    private void btnCalcularCostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularCostoActionPerformed
        // TODO add your handling code here:
        calcularCosto();
    }//GEN-LAST:event_btnCalcularCostoActionPerformed

    private void txtCostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostoActionPerformed

    private void lblBuscarRFCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBuscarRFCMouseClicked

        Persona persona1;
        try {
            persona1 = buscarPersonaRFC();
            String nombreCompleto = DesencriptarNombreCompleto(persona1);
            JOptionPane.showMessageDialog(
                this,
                "Persona encontrada con el rfc ingresado",
                "INFORMACIÓN",
                JOptionPane.INFORMATION_MESSAGE);
            this.txtNombreCom.setText(nombreCompleto);
        } catch (PersistenciaException ex) {
            Logger.getLogger(DlgTramiteLicencia.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(
                this,
                "ERROR: No se encontro ninguna persona registrada con ese rfc",
                "ERROR",
                JOptionPane.ERROR_MESSAGE);
        }      
        
    }//GEN-LAST:event_lblBuscarRFCMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalcularCosto;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnTramitar;
    private javax.swing.JButton btnVaciar;
    private javax.swing.JComboBox<String> cbxTipo;
    private javax.swing.JComboBox<String> cbxVigencia;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblBuscarRFC;
    private javax.swing.JLabel lblNombreC;
    private javax.swing.JLabel lblRfc;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lbltipoLicencia;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtNombreCom;
    private javax.swing.JTextField txtRfc;
    // End of variables declaration//GEN-END:variables
}
