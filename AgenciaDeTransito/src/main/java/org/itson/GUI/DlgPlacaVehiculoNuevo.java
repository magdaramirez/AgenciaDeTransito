/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itson.GUI;

import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import org.itson.implementaciones.PlacasDAO;
import org.itson.utils.Validadores;

/**
 *
 * @author koine
 */
public class DlgPlacaVehiculoNuevo extends javax.swing.JDialog {

   /**
    * Crea el JDialog DlgPlacaVehiculoNuevo
    * @param parent
    * @param modal 
    */
    public DlgPlacaVehiculoNuevo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("VEHÍCULO NUEVO");
        txtCosto.setText("$1500.00");
        establecerFechaRecepcion();
        PlacasDAO placaDAO = new PlacasDAO();
        txtPlaca.setText(placaDAO.generarPlaca());
    }

    /**
     * Método que establece la fecha de recepción para la fecha en la que se
     * solicite la placa para un vehículo nuevo.
     */
    private void establecerFechaRecepcion() {
        jdcFechaRecepcion.setDate(new Date());
        jdcFechaRecepcion.setMinSelectableDate(new Date());
        jdcFechaRecepcion.setMaxSelectableDate(new Date());
    }

    /**
     * Método que extrae los datos del JDialog.
     *
     * @return datos.
     */
    private HashMap<String, String> extraerDatos() {
        String rfc = this.txtRfc.getText();
        String noSerie = this.txtNoSerie.getText();

        HashMap<String, String> datos = new HashMap<>();
        datos.put("rfc", rfc);
        datos.put("noSerie", noSerie);

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
        String noSerie = datos.get("noSerie");

        if (Validadores.esTextoVacio(rfc) || Validadores.esTextoVacio(noSerie)) {
            erroresValidacion.add("Datos vacíos");
        }
        if (!Validadores.esRFC(rfc)) {
            erroresValidacion.add("Formato de RFC incorrecto");
        }
        if (!Validadores.esNoSerie(noSerie)) {
            erroresValidacion.add("Formato de no. Serie incorrecto");
        }
        if (Validadores.excedeLimite(rfc, 13)) {
            erroresValidacion.add("El RFC excede el límite de caracteres");
        }
        if (Validadores.excedeLimite(noSerie, 7)) {
            erroresValidacion.add("El no. Serie excede el límite de caracteres");
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
     * Método que lleva a cabo el trámite de placa de vehículo nuevo.
     */
    private void tramitarPlacaVehiculoNuevo() {
        //NO TERMINADO, SÓLO VALIDA.
        HashMap<String, String> datos = this.extraerDatos();

        List<String> erroresValidacion = this.validarDatos(datos);
        if (!erroresValidacion.isEmpty()) {
            this.mostrarErroresValidacion(erroresValidacion);
        }
    }

    /**
     * Método que vacía los textFields del JDialog.
     */
    private void vaciarDatos() {
        txtRfc.setText(null);
        txtNombre.setText(null);
        txtLicencia.setText(null);
        txtNoSerie.setText(null);
        txtMarca.setText(null);
        txtLinea.setText(null);
        txtColor.setText(null);
        txtModelo.setText(null);
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
        jPanel3 = new javax.swing.JPanel();
        lblDatosSolicitante = new javax.swing.JLabel();
        lblDatosVehiculo = new javax.swing.JLabel();
        lblRfc = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblLicencia = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblPlaca = new javax.swing.JLabel();
        lblFechaRecepcion = new javax.swing.JLabel();
        txtPlaca = new javax.swing.JTextField();
        jdcFechaRecepcion = new com.toedter.calendar.JDateChooser();
        lblVehiculo = new javax.swing.JLabel();
        cbxVehiculo = new javax.swing.JComboBox<>();
        lblCosto = new javax.swing.JLabel();
        txtCosto = new javax.swing.JTextField();
        lblNoSerie = new javax.swing.JLabel();
        txtNoSerie = new javax.swing.JTextField();
        txtRfc = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtLicencia = new javax.swing.JTextField();
        lblMarca = new javax.swing.JLabel();
        lblLinea = new javax.swing.JLabel();
        lblColor = new javax.swing.JLabel();
        lblModelo = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        txtLinea = new javax.swing.JTextField();
        txtColor = new javax.swing.JTextField();
        txtModelo = new javax.swing.JTextField();
        lblBuscarRFC = new javax.swing.JLabel();
        lblBuscarNoSerie = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnTramitar = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        btnVaciar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(249, 143, 143));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(251, 183, 183));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDatosSolicitante.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblDatosSolicitante.setText("Datos del solicitante");
        jPanel3.add(lblDatosSolicitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        lblDatosVehiculo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblDatosVehiculo.setText("Datos del vehículo");
        jPanel3.add(lblDatosVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, -1));

        lblRfc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRfc.setText("RFC:");
        jPanel3.add(lblRfc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        lblNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNombre.setText("Nombre:");
        jPanel3.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        lblLicencia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblLicencia.setText("Licencia:");
        jPanel3.add(lblLicencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        jPanel4.setBackground(new java.awt.Color(240, 139, 139));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPlaca.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPlaca.setText("Placa:");
        jPanel4.add(lblPlaca, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        lblFechaRecepcion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFechaRecepcion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblFechaRecepcion.setText("Fecha de recepción:");
        jPanel4.add(lblFechaRecepcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 120, 20));

        txtPlaca.setEditable(false);
        txtPlaca.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel4.add(txtPlaca, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 120, 30));

        jdcFechaRecepcion.setDateFormatString("yyyy-MM-dd");
        jdcFechaRecepcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jdcFechaRecepcionKeyPressed(evt);
            }
        });
        jPanel4.add(jdcFechaRecepcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 110, 30));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 250, 90));

        lblVehiculo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblVehiculo.setText("Vehículo:");
        jPanel3.add(lblVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 60, -1, -1));

        cbxVehiculo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbxVehiculo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Automovil" }));
        jPanel3.add(cbxVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, 100, 30));

        lblCosto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCosto.setText("Costo:");
        jPanel3.add(lblCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 60, -1, -1));

        txtCosto.setEditable(false);
        txtCosto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 50, 90, 30));

        lblNoSerie.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNoSerie.setText("No. Serie:");
        jPanel3.add(lblNoSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 110, -1, -1));

        txtNoSerie.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtNoSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 100, 200, 40));

        txtRfc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtRfc, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 130, 40));

        txtNombre.setEditable(false);
        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 180, 40));

        txtLicencia.setEditable(false);
        txtLicencia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtLicencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 110, 40));

        lblMarca.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMarca.setText("Marca:");
        jPanel3.add(lblMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, -1, -1));

        lblLinea.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblLinea.setText("Línea:");
        jPanel3.add(lblLinea, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, -1, -1));

        lblColor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblColor.setText("Color:");
        jPanel3.add(lblColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 170, -1, -1));

        lblModelo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblModelo.setText("Modelo:");
        jPanel3.add(lblModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 210, -1, -1));

        txtMarca.setEditable(false);
        txtMarca.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 160, 100, 30));

        txtLinea.setEditable(false);
        txtLinea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtLinea, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 200, 100, 30));

        txtColor.setEditable(false);
        txtColor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 160, 110, 30));

        txtModelo.setEditable(false);
        txtModelo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 200, 110, 30));

        lblBuscarRFC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        jPanel3.add(lblBuscarRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, -1, -1));

        lblBuscarNoSerie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        jPanel3.add(lblBuscarNoSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, -1, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 670, 330));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Trámite de placa:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(156, 51, 57));
        jLabel2.setText("Vehículo nuevo");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, -1, -1));

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
        jPanel2.add(btnTramitar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 420, 130, 40));

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
        jPanel2.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 130, 40));

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
        jPanel2.add(btnVaciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 430, 100, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 710, 470));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 760, 520));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jdcFechaRecepcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdcFechaRecepcionKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jdcFechaRecepcion.transferFocus();
        }
    }//GEN-LAST:event_jdcFechaRecepcionKeyPressed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.dispose();
        DlgRegistroVehiculo registroVehiculo = new DlgRegistroVehiculo(new javax.swing.JFrame(), true);
        registroVehiculo.setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnTramitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTramitarActionPerformed
        // TODO add your handling code here:
        tramitarPlacaVehiculoNuevo();
    }//GEN-LAST:event_btnTramitarActionPerformed

    private void btnVaciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVaciarActionPerformed
        // TODO add your handling code here:
        vaciarDatos();
    }//GEN-LAST:event_btnVaciarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnTramitar;
    private javax.swing.JButton btnVaciar;
    private javax.swing.JComboBox<String> cbxVehiculo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private com.toedter.calendar.JDateChooser jdcFechaRecepcion;
    private javax.swing.JLabel lblBuscarNoSerie;
    private javax.swing.JLabel lblBuscarRFC;
    private javax.swing.JLabel lblColor;
    private javax.swing.JLabel lblCosto;
    private javax.swing.JLabel lblDatosSolicitante;
    private javax.swing.JLabel lblDatosVehiculo;
    private javax.swing.JLabel lblFechaRecepcion;
    private javax.swing.JLabel lblLicencia;
    private javax.swing.JLabel lblLinea;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblNoSerie;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPlaca;
    private javax.swing.JLabel lblRfc;
    private javax.swing.JLabel lblVehiculo;
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtLicencia;
    private javax.swing.JTextField txtLinea;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNoSerie;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPlaca;
    private javax.swing.JTextField txtRfc;
    // End of variables declaration//GEN-END:variables
}