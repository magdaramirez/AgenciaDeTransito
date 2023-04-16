/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package org.itson.GUI;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.itson.dominio.Persona;
import org.itson.excepciones.PersistenciaException;
import org.itson.implementaciones.PersonasDAO;

/**
 * Clase encargada del menú.
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
public class FrmMenu extends javax.swing.JFrame {

    PersonasDAO personasDAO = null;

    /**
     * Método que crea el Form FrmMenu.
     */
    public FrmMenu() {
        initComponents();
        personasDAO = new PersonasDAO();
        this.setTitle("MENÚ");
    }

    /**
     * Método que muestra la pantalla de realizar trámite.
     */
    private void mostrarPantallaRealizarTramite() {
        this.dispose();
        FrmRealizarTramite realizarTramite = new FrmRealizarTramite();
        realizarTramite.setVisible(true);
    }

    /**
     * Método que muestra la pantalla de consulta trámites.
     */
    private void mostrarPantallaConsultaTramites() {
        this.dispose();
        DlgConsultaTramites consultaTramites = new DlgConsultaTramites(this, true);
        consultaTramites.setVisible(true);
    }

    /**
     * Método que muestra la pantalla de reportes de trámites.
     */
    private void mostrarPantallaReporteTramites() {
        this.dispose();
        DlgReporteTramites reporteTramites = new DlgReporteTramites(this, true);
        reporteTramites.setVisible(true);
    }

    /**
     * Método que realiza la inserción masiva de 20 personas.
     */
    private void realizarInsercionMasiva() {
        personasDAO.insertarPersonas();

        JOptionPane.showMessageDialog(
                this,
                "La inserción se ha realizado correctamente",
                "INFORMACIÓN",
                JOptionPane.INFORMATION_MESSAGE);
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
        jLabel1 = new javax.swing.JLabel();
        btnInsercionMasiva = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnRealizarTramite = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnModuloReportes = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnModuloConsultas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(249, 143, 143));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Agencia de Tránsito");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, -1, -1));

        btnInsercionMasiva.setBackground(new java.awt.Color(212, 100, 107));
        btnInsercionMasiva.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnInsercionMasiva.setForeground(new java.awt.Color(255, 255, 255));
        btnInsercionMasiva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Personas.png"))); // NOI18N
        btnInsercionMasiva.setText("Inserción Masiva");
        btnInsercionMasiva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsercionMasivaActionPerformed(evt);
            }
        });
        jPanel2.add(btnInsercionMasiva, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, 200, 40));

        jPanel3.setBackground(new java.awt.Color(251, 183, 183));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Tramite.png"))); // NOI18N
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        btnRealizarTramite.setBackground(new java.awt.Color(251, 183, 183));
        btnRealizarTramite.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRealizarTramite.setText("Realizar Trámite");
        btnRealizarTramite.setAlignmentX(1.0F);
        btnRealizarTramite.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(212, 100, 107)));
        btnRealizarTramite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRealizarTramiteActionPerformed(evt);
            }
        });
        jPanel3.add(btnRealizarTramite, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 170, 60));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 170, 220));

        jPanel4.setBackground(new java.awt.Color(251, 183, 183));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Reporte.png"))); // NOI18N
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        btnModuloReportes.setBackground(new java.awt.Color(251, 183, 183));
        btnModuloReportes.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnModuloReportes.setText("Módulo de Reportes");
        btnModuloReportes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(212, 100, 107)));
        btnModuloReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModuloReportesActionPerformed(evt);
            }
        });
        jPanel4.add(btnModuloReportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 170, 60));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 170, 220));

        jPanel5.setBackground(new java.awt.Color(251, 183, 183));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Consulta.png"))); // NOI18N
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        btnModuloConsultas.setBackground(new java.awt.Color(251, 183, 183));
        btnModuloConsultas.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnModuloConsultas.setText("Módulo de Consultas");
        btnModuloConsultas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(212, 100, 107)));
        btnModuloConsultas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModuloConsultasActionPerformed(evt);
            }
        });
        jPanel5.add(btnModuloConsultas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 170, 60));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 170, 220));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 470));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
/**
     * Botón que al hacerle clic abre la ventana DlgConsultaTramites.
     *
     * @param evt objeto de evento de acción.
     */
    private void btnModuloConsultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModuloConsultasActionPerformed
        try {
            List<Persona> personasActivas = personasDAO.buscarPersonasActivas();
            if (!personasActivas.isEmpty()) {
                mostrarPantallaConsultaTramites();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Deben haber personas registradas en la base de datos antes de continuar.",
                        "INFORMACIÓN",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PersistenciaException ex) {
            Logger.getLogger(FrmMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnModuloConsultasActionPerformed
    /**
     * Botón que al hacerle clic registra 20 personas en la base de datos.
     *
     * @param evt objeto de evento de acción.
     */
    private void btnInsercionMasivaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsercionMasivaActionPerformed
        try {
            List<Persona> personasActivas = personasDAO.buscarPersonasActivas();
            if (personasActivas.isEmpty()) {
                realizarInsercionMasiva();
                this.btnInsercionMasiva.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Las personas ya han sido ingresadas anteriormente, borra la base de datos antes de volverlo a intentar.",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (PersistenciaException ex) {
            Logger.getLogger(FrmMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnInsercionMasivaActionPerformed
    /**
     * Botón que al hacerle clic abre la ventana FrmRealizarTramite.
     *
     * @param evt objeto de evento de acción.
     */
    private void btnRealizarTramiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRealizarTramiteActionPerformed
        try {
            // TODO add your handling code here:
            List<Persona> personasActivas = personasDAO.buscarPersonasActivas();
            if (!personasActivas.isEmpty()) {
                mostrarPantallaRealizarTramite();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Deben haber personas registradas en la base de datos antes de continuar.",
                        "INFORMACIÓN",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PersistenciaException ex) {
            Logger.getLogger(FrmMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRealizarTramiteActionPerformed
    /**
     * Botón que al hacerle clic abre la ventana DlgReporteTramites.
     *
     * @param evt objeto de evento de acción.
     */
    private void btnModuloReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModuloReportesActionPerformed
        try {
            List<Persona> personasActivas = personasDAO.buscarPersonasActivas();
            if (!personasActivas.isEmpty()) {
                mostrarPantallaReporteTramites();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Deben haber personas registradas en la base de datos antes de continuar.",
                        "INFORMACIÓN",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PersistenciaException ex) {
            Logger.getLogger(FrmMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnModuloReportesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInsercionMasiva;
    private javax.swing.JButton btnModuloConsultas;
    private javax.swing.JButton btnModuloReportes;
    private javax.swing.JButton btnRealizarTramite;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    // End of variables declaration//GEN-END:variables
}
