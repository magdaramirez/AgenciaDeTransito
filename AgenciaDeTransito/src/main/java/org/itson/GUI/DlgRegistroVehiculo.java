/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itson.GUI;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.itson.dominio.VehiculoAutomovil;
import org.itson.excepciones.PersistenciaException;
import org.itson.implementaciones.VehiculoAutomovilDAO;
import org.itson.utils.Validadores;
import org.itson.interfaces.IVehiculoDAO;

/**
 *
 * @author koine
 */
public class DlgRegistroVehiculo extends javax.swing.JDialog {
    IVehiculoDAO autos = new VehiculoAutomovilDAO();
    /**
     * Método que crea el JDialog DlgRegistroVehiculo.
     *
     * @param parent
     * @param modal
     */
    public DlgRegistroVehiculo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("REGISTRO");
    }

    /**
     * Método que extrae los datos del JDialog.
     *
     * @return datos.
     */
    private HashMap<String, String> extraerDatos() {
        String noSerie = this.txtNoSerie.getText();
        String marca = this.txtMarca.getText();
        String linea = this.txtLinea.getText();
        String color = this.txtColor.getText();
        String modelo = this.txtModelo.getText();

        HashMap<String, String> datos = new HashMap<>();
        datos.put("noSerie", noSerie);
        datos.put("marca", marca);
        datos.put("linea", linea);
        datos.put("color", color);
        datos.put("modelo", modelo);

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
        String noSerie = datos.get("noSerie");
        String marca = datos.get("marca");
        String linea = datos.get("linea");
        String color = datos.get("color");
        String modelo = datos.get("modelo");

        if (Validadores.esTextoVacio(noSerie) || Validadores.esTextoVacio(marca) || Validadores.esTextoVacio(linea) || Validadores.esTextoVacio(color) || Validadores.esTextoVacio(modelo)) {
            erroresValidacion.add("Datos vacíos");
        }
        if (!Validadores.esNoSerie(noSerie)) {
            erroresValidacion.add("Formato de no. Serie incorrecto");
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
     * Metodo que crea el automovil a registrar en el sistema
     * @return el objeto automovil a registrar
     */
    private VehiculoAutomovil crearAutomovil(){
        HashMap<String, String> datos = extraerDatos();
        
        String noSerie = datos.get("noSerie");
        String marca = datos.get("marca");
        String linea = datos.get("linea");
        String color = datos.get("color");
        String modelo = datos.get("modelo");
        
       VehiculoAutomovil automovil = new VehiculoAutomovil();
       
       automovil.setNoSerie(noSerie);
       automovil.setMarca(marca);
       automovil.setLinea(linea);
       automovil.setColor(color);
       automovil.setModelo(modelo);
   
       return automovil;
    }
    
    /**
     * Método que lleva a cabo el registro del vehículo.
     */
    private void registrarVehiculo() throws PersistenciaException{
        
        HashMap<String, String> datos = this.extraerDatos();
        
        List<String> erroresValidacion = this.validarDatos(datos);
        if (!erroresValidacion.isEmpty()) {
            this.mostrarErroresValidacion(erroresValidacion);
        } else {
            VehiculoAutomovil automovil;
        try {
            automovil = crearAutomovil();
        
            autos.registrarAutomovil(automovil);
            JOptionPane.showMessageDialog(
                this,
                "El registro del automovil se ha realizado correctamente",
                "INFORMACIÓN",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (PersistenciaException ex) {
            Logger.getLogger(DlgTramiteLicencia.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(
                this,
                "ERROR: Algun dato no ha sido ingresado correctamente",
                "ERROR",
                JOptionPane.ERROR_MESSAGE);
        }
            mostrarPantallaPlacaVehiculoNuevo();
        }
   
    }

    /**
     * Método que vacía los textFields del JDialog.
     */
    private void vaciarDatos() {
        txtNoSerie.setText(null);
        txtMarca.setText(null);
        txtLinea.setText(null);
        txtColor.setText(null);
        txtModelo.setText(null);
    }

    /**
     * Método que muestra la pantalla del trámite de placa para vehículo nuevo.
     */
    private void mostrarPantallaPlacaVehiculoNuevo() {
        this.dispose();
        DlgPlacaVehiculoNuevo placaVehiculoNuevo = new DlgPlacaVehiculoNuevo(new javax.swing.JFrame(), true);
        placaVehiculoNuevo.setVisible(true);
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
        lblDatosVehiculo = new javax.swing.JLabel();
        lblVehiculo = new javax.swing.JLabel();
        lblNoSerie = new javax.swing.JLabel();
        lblMarca = new javax.swing.JLabel();
        lblLinea = new javax.swing.JLabel();
        lblColor = new javax.swing.JLabel();
        lblModelo = new javax.swing.JLabel();
        txtNoSerie = new javax.swing.JTextField();
        txtMarca = new javax.swing.JTextField();
        txtLinea = new javax.swing.JTextField();
        txtColor = new javax.swing.JTextField();
        txtModelo = new javax.swing.JTextField();
        cbxVehiculo = new javax.swing.JComboBox<>();
        lblRegistroVehiculo = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        btnVaciar = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();

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

        lblDatosVehiculo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblDatosVehiculo.setText("Datos del vehículo");
        jPanel3.add(lblDatosVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        lblVehiculo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblVehiculo.setText("Vehículo:");
        jPanel3.add(lblVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        lblNoSerie.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNoSerie.setText("No. Serie:");
        jPanel3.add(lblNoSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        lblMarca.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMarca.setText("Marca:");
        jPanel3.add(lblMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, -1));

        lblLinea.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblLinea.setText("Línea:");
        jPanel3.add(lblLinea, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, -1, -1));

        lblColor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblColor.setText("Color:");
        jPanel3.add(lblColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 150, -1, -1));

        lblModelo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblModelo.setText("Modelo:");
        jPanel3.add(lblModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 200, -1, -1));

        txtNoSerie.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel3.add(txtNoSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 160, 30));

        txtMarca.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel3.add(txtMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 160, 30));

        txtLinea.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel3.add(txtLinea, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, 160, 30));

        txtColor.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel3.add(txtColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 140, 160, 30));

        txtModelo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel3.add(txtModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 190, 160, 30));

        cbxVehiculo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxVehiculo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Automóvil" }));
        jPanel3.add(cbxVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 120, 30));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 580, 270));

        lblRegistroVehiculo.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblRegistroVehiculo.setText("Registro de Vehículo");
        jPanel2.add(lblRegistroVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));

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
        jPanel2.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 130, 40));

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
        jPanel2.add(btnVaciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 390, 100, 30));

        btnRegistrar.setBackground(new java.awt.Color(212, 100, 107));
        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Aceptar.png"))); // NOI18N
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        jPanel2.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 380, 130, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 640, 440));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 480));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        try {
            // TODO add your handling code here:
            registrarVehiculo();
        } catch (PersistenciaException ex) {
            Logger.getLogger(DlgRegistroVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.dispose();
        FrmTramitePlaca tramitePlaca = new FrmTramitePlaca();
        tramitePlaca.setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnVaciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVaciarActionPerformed
        // TODO add your handling code here:
        vaciarDatos();
    }//GEN-LAST:event_btnVaciarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnVaciar;
    private javax.swing.JComboBox<String> cbxVehiculo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblColor;
    private javax.swing.JLabel lblDatosVehiculo;
    private javax.swing.JLabel lblLinea;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblNoSerie;
    private javax.swing.JLabel lblRegistroVehiculo;
    private javax.swing.JLabel lblVehiculo;
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtLinea;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNoSerie;
    // End of variables declaration//GEN-END:variables
}
