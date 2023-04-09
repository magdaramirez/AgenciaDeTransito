/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itson.GUI;

import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.itson.dominio.LicenciaDTO;
import org.itson.dominio.PlacaDTO;
import org.itson.dominio.TramiteLicencia;
import org.itson.dominio.TramitePlaca;
import org.itson.excepciones.PersistenciaException;
import org.itson.implementaciones.LicenciasDAO;
import org.itson.implementaciones.PersonasDAO;
import org.itson.implementaciones.PlacasDAO;
import org.itson.interfaces.ILicenciasDAO;
import org.itson.interfaces.IPersonasDAO;
import org.itson.interfaces.IPlacasDAO;
import org.itson.utils.ConfiguracionPaginado;
import org.itson.utils.Encriptador;

/**
 *
 * @author koine
 */
public class DlgReporteTramites extends javax.swing.JDialog {
    private static final Logger LOG = Logger.getLogger(DlgConsultaTramites.class.getName());
    IPersonasDAO personas = new PersonasDAO();
    ILicenciasDAO licencias = new LicenciasDAO();
    IPlacasDAO placas = new PlacasDAO();
    Encriptador encriptador = new Encriptador();
    private int tamañoLista;
//    private List<Cuenta> listaCuentas;
    private ConfiguracionPaginado paginado;
    /**
     * Método que crea el JDialos DlgReporteTramites.
     * @param parent
     * @param modal 
     */
    public DlgReporteTramites(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("REPORTES");
        this.paginado = new ConfiguracionPaginado(0, 3);
        if(this.cbxTramite.getSelectedItem().toString().equals("Expedicion de placa")){
                this.llenarTablaPlacas();
            }else{
                this.llenarTablaLicencias();
        } 
    }
    
    /**
     * Método que llena la tabla de placas
     */
    private void llenarTablaPlacas() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date fecha1;
            String fechaEmision;
            List<TramitePlaca> listaTramitesPlacas = placas.consultarPlacas(paginado, obtenerDatosTramitePlaca());
            DefaultTableModel modeloTabla = (DefaultTableModel) this.tblTramites.getModel();
            
            String nombDes = null, apellidoPDes = null, apellidoMDes = null, nomCompDes = null;
            String tipo = "Expedición de placa";
            
            modeloTabla.setRowCount(0);
            for (TramitePlaca trans : listaTramitesPlacas) {
                fecha1 = trans.getFechaEmision().getTime();
                fechaEmision = dateFormat.format(fecha1); 
                nombDes = encriptador.desencriptar(trans.getPersona().getNombre());
                apellidoPDes = encriptador.desencriptar(trans.getPersona().getApellidoPaterno());
                apellidoMDes = encriptador.desencriptar(trans.getPersona().getApellidoMaterno());
                nomCompDes = nombDes+" "+apellidoPDes+" "+apellidoMDes;
                
                Object[] fila = {
                    fechaEmision,
                    tipo,
                    nomCompDes,
                    trans.getCosto()
                };
                modeloTabla.addRow(fila);
            }
        } catch (PersistenciaException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }
    }

    /**
     * Método que llena la tabla de licencias
     */
    private void llenarTablaLicencias() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date fecha1;
            String fechaEmision;
            List<TramiteLicencia> listaLicencias = this.licencias.consultarLicencias(paginado, obtenerDatosTramiteLicencia());
            DefaultTableModel modeloTabla = (DefaultTableModel) this.tblTramites.getModel();
            String nombDes = null, apellidoPDes = null, apellidoMDes = null, nomCompDes = null;
            String tipo = "Expedición de licencia";
            
            modeloTabla.setRowCount(0);
            for (TramiteLicencia licencia : listaLicencias) {
                fecha1 = licencia.getFechaEmision().getTime();
                fechaEmision = dateFormat.format(fecha1); 
                nombDes = encriptador.desencriptar(licencia.getPersona().getNombre());
                apellidoPDes = encriptador.desencriptar(licencia.getPersona().getApellidoPaterno());
                apellidoMDes = encriptador.desencriptar(licencia.getPersona().getApellidoMaterno());
                nomCompDes = nombDes+" "+apellidoPDes+" "+apellidoMDes;
                
                Object[] fila = {
                    fechaEmision,
                    tipo,
                    nomCompDes,
                    licencia.getCosto()
                };
                modeloTabla.addRow(fila);
            }
        } catch (PersistenciaException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }
    }
     
     /**
     * Metodo que obtiene los datos para la placa de la interfaz grafica para la consutla en caso de haber sido ingresados
     * @return 
     */
    private PlacaDTO obtenerDatosTramitePlaca(){
        PlacaDTO placaD = new PlacaDTO();
        String nomEncriptado = encriptador.encriptar(this.txtNombre.getText());
        
        placaD.setFechaInicio(this.jdcFechaInicio.getCalendar());
        placaD.setFechaFin(this.jdcFechaFin.getCalendar());
        
        if(this.txtNombre.getText().equals("")){
            placaD.setNombre(null); 
        }else{
            placaD.setNombre(nomEncriptado); 
        }

        return placaD;
        
    }
    /**
     * Metodo que obtiene los datos para la licencia de la interfaz grafica para la consutla en caso de haber sido ingresados
     * @return 
     */
    private LicenciaDTO obtenerDatosTramiteLicencia(){
        LicenciaDTO licenciaD = new LicenciaDTO();
        String nomEncriptado = encriptador.encriptar(this.txtNombre.getText());
        
        licenciaD.setFechaInicio(this.jdcFechaInicio.getCalendar());
        licenciaD.setFechaFin(this.jdcFechaFin.getCalendar());
            
        if(this.txtNombre.getText().equals("")){
            licenciaD.setNombre(null); 
        }else{
            licenciaD.setNombre(nomEncriptado); 
        }
        
       
        return licenciaD;
    }
    
    /**
     * Método que avanza de página de transferencias
     */
    private void avanzarPaginaTramites() {
        this.paginado.avanzarPagina();
        if(this.cbxTramite.getSelectedItem().toString().equals("Expedicion de placa")){
                this.llenarTablaPlacas();
            }else{
                this.llenarTablaLicencias();
            }  
    }

    /**
     * Método que retrocede de página de transferencias
     */
    private void retrocederPaginaTramites() {
        this.paginado.retrocederPagina();
        if(this.cbxTramite.getSelectedItem().toString().equals("Expedicion de placa")){
                this.llenarTablaPlacas();
            }else{
                this.llenarTablaLicencias();
            }  
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
        lblNombre = new javax.swing.JLabel();
        lblInicioPeriodo = new javax.swing.JLabel();
        lblPeriodoTiempo = new javax.swing.JLabel();
        lblFinPeriodo = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblTramite = new javax.swing.JLabel();
        cbxTramite = new javax.swing.JComboBox<>();
        jdcFechaInicio = new com.toedter.calendar.JDateChooser();
        jdcFechaFin = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTramites = new javax.swing.JTable();
        btnVisualizacionPrevia = new javax.swing.JButton();
        btnDescargarPdf = new javax.swing.JButton();
        lblReporteTramite = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        cbxElementosPagina = new javax.swing.JComboBox<>();
        btnRetroceder = new javax.swing.JButton();
        btnAvanzar = new javax.swing.JButton();

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

        lblNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNombre.setText("Nombre:");
        jPanel3.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        lblInicioPeriodo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblInicioPeriodo.setText("Inicio:");
        jPanel3.add(lblInicioPeriodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, -1, -1));

        lblPeriodoTiempo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblPeriodoTiempo.setText("Periodo de tiempo");
        jPanel3.add(lblPeriodoTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, -1, -1));

        lblFinPeriodo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblFinPeriodo.setText("Fin:");
        jPanel3.add(lblFinPeriodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 120, -1, -1));

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 220, 40));

        lblTramite.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTramite.setText("Tramite:");
        jPanel3.add(lblTramite, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, -1, -1));

        cbxTramite.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxTramite.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Expedicion de licencia", "Expedicion de placa" }));
        cbxTramite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTramiteActionPerformed(evt);
            }
        });
        jPanel3.add(cbxTramite, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, 190, 40));
        jPanel3.add(jdcFechaInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 110, 30));
        jPanel3.add(jdcFechaFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 110, 140, 30));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 720, 170));

        tblTramites.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tblTramites.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Fecha Realización", "Tipo", "Nombre", "Costo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblTramites);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, 690, 210));

        btnVisualizacionPrevia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnVisualizacionPrevia.setForeground(new java.awt.Color(212, 100, 107));
        btnVisualizacionPrevia.setText("Visualización previa");
        btnVisualizacionPrevia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(212, 100, 107)));
        btnVisualizacionPrevia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualizacionPreviaActionPerformed(evt);
            }
        });
        jPanel2.add(btnVisualizacionPrevia, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 460, 170, 30));

        btnDescargarPdf.setBackground(new java.awt.Color(212, 100, 107));
        btnDescargarPdf.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDescargarPdf.setForeground(new java.awt.Color(255, 255, 255));
        btnDescargarPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/descargar-pdf.png"))); // NOI18N
        btnDescargarPdf.setText("Descargar PDF");
        jPanel2.add(btnDescargarPdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 500, 170, 40));

        lblReporteTramite.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblReporteTramite.setText("Reportes de trámites");
        jPanel2.add(lblReporteTramite, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, -1, -1));

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
        jPanel2.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 130, 40));

        cbxElementosPagina.setFont(new java.awt.Font("Microsoft YaHei", 1, 12)); // NOI18N
        cbxElementosPagina.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "3", "5", "10" }));
        cbxElementosPagina.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxElementosPaginaItemStateChanged(evt);
            }
        });
        jPanel2.add(cbxElementosPagina, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 460, 60, 30));

        btnRetroceder.setBackground(new java.awt.Color(212, 100, 107));
        btnRetroceder.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 15)); // NOI18N
        btnRetroceder.setForeground(new java.awt.Color(255, 255, 255));
        btnRetroceder.setText("<--");
        btnRetroceder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetrocederActionPerformed(evt);
            }
        });
        jPanel2.add(btnRetroceder, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 460, 70, -1));

        btnAvanzar.setBackground(new java.awt.Color(212, 100, 107));
        btnAvanzar.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 15)); // NOI18N
        btnAvanzar.setForeground(new java.awt.Color(255, 255, 255));
        btnAvanzar.setText("-->");
        btnAvanzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvanzarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAvanzar, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 460, 70, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 830, 550));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtFechaInicioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaInicioKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jdcFechaInicio.transferFocus();
        }
    }//GEN-LAST:event_txtFechaInicioKeyPressed

    private void txtFechaFinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaFinKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jdcFechaFin.transferFocus();
        }
    }//GEN-LAST:event_txtFechaFinKeyPressed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.dispose();
        FrmMenu menu = new FrmMenu();
        menu.setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void cbxTramiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTramiteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxTramiteActionPerformed

    private void btnVisualizacionPreviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizacionPreviaActionPerformed
        if(this.cbxTramite.getSelectedItem().toString().equals("Expedicion de placa")){
                this.llenarTablaPlacas();
            }else{
                this.llenarTablaLicencias();
            }  
    }//GEN-LAST:event_btnVisualizacionPreviaActionPerformed

    private void cbxElementosPaginaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxElementosPaginaItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED) {
            int elementosMostrados = Integer.parseInt(evt.getItem().toString());
            this.paginado.setElementosPagina(elementosMostrados);
            if(this.cbxTramite.getSelectedItem().equals("Expedicion de placa")){
                this.llenarTablaPlacas();
            }else{
                this.llenarTablaLicencias();
            }  
        }
    }//GEN-LAST:event_cbxElementosPaginaItemStateChanged

    private void btnRetrocederActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetrocederActionPerformed
        retrocederPaginaTramites();
    }//GEN-LAST:event_btnRetrocederActionPerformed

    private void btnAvanzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvanzarActionPerformed
        avanzarPaginaTramites();
    }//GEN-LAST:event_btnAvanzarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAvanzar;
    private javax.swing.JButton btnDescargarPdf;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnRetroceder;
    private javax.swing.JButton btnVisualizacionPrevia;
    private javax.swing.JComboBox<String> cbxElementosPagina;
    private javax.swing.JComboBox<String> cbxTramite;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdcFechaFin;
    private com.toedter.calendar.JDateChooser jdcFechaInicio;
    private javax.swing.JLabel lblFinPeriodo;
    private javax.swing.JLabel lblInicioPeriodo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPeriodoTiempo;
    private javax.swing.JLabel lblReporteTramite;
    private javax.swing.JLabel lblTramite;
    private javax.swing.JTable tblTramites;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
