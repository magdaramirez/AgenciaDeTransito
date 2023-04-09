/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itson.GUI;

import java.awt.event.ItemEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.itson.dominio.*;
import org.itson.excepciones.PersistenciaException;
import org.itson.implementaciones.*;
import org.itson.interfaces.*;
import org.itson.utils.ConfiguracionPaginado;
import org.itson.utils.Encriptador;

/**
 *
 * @author koine
 */
public class DlgConsultaTramites extends javax.swing.JDialog {
    private static final Logger LOG = Logger.getLogger(DlgConsultaTramites.class.getName());
    IPersonasDAO personas = new PersonasDAO();
    ILicenciasDAO licencias = new LicenciasDAO();
    IPlacasDAO placas = new PlacasDAO();
    Encriptador encriptador = new Encriptador();
    private int tamañoLista;
//    private List<Cuenta> listaCuentas;
    private ConfiguracionPaginado paginadoPlacas;
    private ConfiguracionPaginado paginadoLicencias;
    /**
     * Crea el JDialog DlgConsultaTramites.
     * @param parent
     * @param modal 
     */
    public DlgConsultaTramites(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("CONSULTAS");
        this.paginadoPlacas = new ConfiguracionPaginado(0, 3);
        this.paginadoLicencias = new ConfiguracionPaginado(0, 3);
        this.llenarTablaPlacas();
        this.llenarTablaLicencias();
    }
    /**
     * Metodo que obtiene los datos para la placa de la interfaz grafica para la consutla en caso de haber sido ingresados
     * @return 
     */
    private PlacaDTO obtenerDatosTramitePlaca(){
        PlacaDTO placaD = new PlacaDTO();
        String nomEncriptado = encriptador.encriptar(this.txtNombre.getText());
        
        if(this.txtAnioNacimiento.getText().equals("")){
            placaD.setAnioNacimiento(null);
        }else{
            placaD.setAnioNacimiento(Integer.parseInt(this.txtAnioNacimiento.getText()));
        }
        
        if(this.txtNombre.getText().equals("")){
            placaD.setNombre(null); 
        }else{
            placaD.setNombre(nomEncriptado); 
        }
        
        if(this.txtRfc.getText().equals("")){
            placaD.setRfc(null);
        }else{
            placaD.setRfc(this.txtRfc.getText());
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
        
        if(this.txtAnioNacimiento.getText().equals("")){
            licenciaD.setAnioNacimiento(null);
        }else{
            licenciaD.setAnioNacimiento(Integer.parseInt(this.txtAnioNacimiento.getText()));
        }
        
        if(this.txtNombre.getText().equals("")){
            licenciaD.setNombre(null); 
        }else{
            licenciaD.setNombre(nomEncriptado); 
        }
        
        if(this.txtRfc.getText().equals("")){
            licenciaD.setRfc(null);
        }else{
            licenciaD.setRfc(this.txtRfc.getText());
        }
       
        return licenciaD;
    }
    
    /**
     * Método que llena la tabla de transferencias
     */
    private void llenarTablaPlacas() {
        try {
            Calendar cal = new GregorianCalendar();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date fecha1,fecha2;
            String fechaEmision,fechaRecepcion = null;
            List<TramitePlaca> listaTramitesPlacas = placas.consultarPlacas(paginadoPlacas, obtenerDatosTramitePlaca());
            DefaultTableModel modeloTabla = (DefaultTableModel) this.tblPlacas.getModel();
            
            modeloTabla.setRowCount(0);
            for (TramitePlaca trans : listaTramitesPlacas) {
                fecha1 = trans.getFechaEmision().getTime();
                if(trans.getFechaRecepcion()!=null){
                    fecha2 = trans.getFechaRecepcion().getTime();
                    fechaRecepcion = dateFormat.format(fecha2);
                }
                fechaEmision = dateFormat.format(fecha1); 
                
                Object[] fila = {
                    trans.getPersona().getRfc(),
                    trans.getPlaca(),
                    trans.getEstado(),
                    fechaEmision,
                    fechaRecepcion
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
            List<TramiteLicencia> listaLicencias = this.licencias.consultarLicencias(paginadoLicencias, obtenerDatosTramiteLicencia());
            DefaultTableModel modeloTabla = (DefaultTableModel) this.tblLicencias.getModel();
            modeloTabla.setRowCount(0);
            for (TramiteLicencia licencia : listaLicencias) {
                Object[] fila = {
                    licencia.getPersona().getRfc(),
                    licencia.getId(),
                    licencia.getVigencia(),
                    licencia.getEstado().toString(),
                    licencia.getTipo().toString()
                };
                modeloTabla.addRow(fila);
            }
        } catch (PersistenciaException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }
    }
    
    /**
     * Método que avanza de página de transferencias
     */
    private void avanzarPaginaPlacas() {
        this.paginadoPlacas.avanzarPagina();
        this.llenarTablaPlacas();
    }

    /**
     * Método que retrocede de página de transferencias
     */
    private void retrocederPaginaPlacas() {
        this.paginadoPlacas.retrocederPagina();
        this.llenarTablaPlacas();
    }

    /**
     * Método que avanza de página de retiros
     */
    private void avanzarPaginaLicencias() {
        this.paginadoLicencias.avanzarPagina();
        this.llenarTablaLicencias();
    }

    /**
     * Método que retrocede de página de retiros
     */
    private void retrocederPaginaLicencias() {
        this.paginadoLicencias.retrocederPagina();
        this.llenarTablaLicencias();
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
        btnRegresar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtAnioNacimiento = new javax.swing.JTextField();
        txtRfc = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        pnlPlacas = new javax.swing.JScrollPane();
        tblPlacas = new javax.swing.JTable();
        pnlLicencias = new javax.swing.JScrollPane();
        tblLicencias = new javax.swing.JTable();
        lblConsultasTramites = new javax.swing.JLabel();
        lblPlacas = new javax.swing.JLabel();
        lblLicencias = new javax.swing.JLabel();
        btnConsultlar = new javax.swing.JButton();
        cbxElementosPagina = new javax.swing.JComboBox<>();
        cbxElementosPaginaRetiros = new javax.swing.JComboBox<>();
        btnRetroceder = new javax.swing.JButton();
        btnAvanzar = new javax.swing.JButton();
        btnRetrocederRetiros = new javax.swing.JButton();
        btnAvanzarRetiros = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(249, 143, 143));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel2.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 130, 40));

        jPanel3.setBackground(new java.awt.Color(251, 183, 183));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("RFC:");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Nombre:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Año nacimiento:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, -1, -1));

        txtAnioNacimiento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel3.add(txtAnioNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 30, 200, 40));

        txtRfc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel3.add(txtRfc, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 220, 40));

        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel3.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 280, 40));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 1000, 170));

        pnlPlacas.setBackground(new java.awt.Color(255, 255, 255));
        pnlPlacas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        tblPlacas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblPlacas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "RFC", "Placa", "Estado", "Fecha Emisión", "Fecha Recepción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        pnlPlacas.setViewportView(tblPlacas);

        jPanel2.add(pnlPlacas, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 480, 240));

        pnlLicencias.setBackground(new java.awt.Color(255, 255, 255));
        pnlLicencias.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        tblLicencias.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblLicencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "RFC", "LicenciaID", "Vigencia", "Estado", "Tipo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        pnlLicencias.setViewportView(tblLicencias);

        jPanel2.add(pnlLicencias, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 480, 240));

        lblConsultasTramites.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblConsultasTramites.setText("Consulta Trámites");
        jPanel2.add(lblConsultasTramites, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 0, -1, -1));

        lblPlacas.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblPlacas.setText("Placas:");
        jPanel2.add(lblPlacas, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        lblLicencias.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblLicencias.setText("Licencias:");
        jPanel2.add(lblLicencias, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 50, -1, -1));

        btnConsultlar.setBackground(new java.awt.Color(212, 100, 107));
        btnConsultlar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConsultlar.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultlar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        btnConsultlar.setText("Consultar");
        btnConsultlar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultlarActionPerformed(evt);
            }
        });
        jPanel2.add(btnConsultlar, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 550, 150, 40));

        cbxElementosPagina.setFont(new java.awt.Font("Microsoft YaHei", 1, 12)); // NOI18N
        cbxElementosPagina.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "3", "5", "10" }));
        cbxElementosPagina.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxElementosPaginaItemStateChanged(evt);
            }
        });
        jPanel2.add(cbxElementosPagina, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 60, 30));

        cbxElementosPaginaRetiros.setFont(new java.awt.Font("Microsoft YaHei", 1, 12)); // NOI18N
        cbxElementosPaginaRetiros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "3", "5", "10" }));
        cbxElementosPaginaRetiros.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxElementosPaginaRetirosItemStateChanged(evt);
            }
        });
        jPanel2.add(cbxElementosPaginaRetiros, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 330, 60, 30));

        btnRetroceder.setBackground(new java.awt.Color(212, 100, 107));
        btnRetroceder.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 15)); // NOI18N
        btnRetroceder.setForeground(new java.awt.Color(255, 255, 255));
        btnRetroceder.setText("<--");
        btnRetroceder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetrocederActionPerformed(evt);
            }
        });
        jPanel2.add(btnRetroceder, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, 70, -1));

        btnAvanzar.setBackground(new java.awt.Color(212, 100, 107));
        btnAvanzar.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 15)); // NOI18N
        btnAvanzar.setForeground(new java.awt.Color(255, 255, 255));
        btnAvanzar.setText("-->");
        btnAvanzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvanzarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAvanzar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 330, 70, -1));

        btnRetrocederRetiros.setBackground(new java.awt.Color(212, 100, 107));
        btnRetrocederRetiros.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 15)); // NOI18N
        btnRetrocederRetiros.setForeground(new java.awt.Color(255, 255, 255));
        btnRetrocederRetiros.setText("<--");
        btnRetrocederRetiros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetrocederRetirosActionPerformed(evt);
            }
        });
        jPanel2.add(btnRetrocederRetiros, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 330, 70, -1));

        btnAvanzarRetiros.setBackground(new java.awt.Color(212, 100, 107));
        btnAvanzarRetiros.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 15)); // NOI18N
        btnAvanzarRetiros.setForeground(new java.awt.Color(255, 255, 255));
        btnAvanzarRetiros.setText("-->");
        btnAvanzarRetiros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvanzarRetirosActionPerformed(evt);
            }
        });
        jPanel2.add(btnAvanzarRetiros, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 330, 70, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 1060, 600));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 640));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.dispose();
        FrmMenu menu = new FrmMenu();
        menu.setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void cbxElementosPaginaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxElementosPaginaItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED) {
            int elementosMostrados = Integer.parseInt(evt.getItem().toString());
            this.paginadoPlacas.setElementosPagina(elementosMostrados);
            this.llenarTablaPlacas();
        }
    }//GEN-LAST:event_cbxElementosPaginaItemStateChanged

    private void cbxElementosPaginaRetirosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxElementosPaginaRetirosItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            int elementosMostrados = Integer.parseInt(evt.getItem().toString());
            this.paginadoLicencias.setElementosPagina(elementosMostrados);
            this.avanzarPaginaLicencias();
        }
    }//GEN-LAST:event_cbxElementosPaginaRetirosItemStateChanged

    private void btnRetrocederActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetrocederActionPerformed
        retrocederPaginaPlacas();
    }//GEN-LAST:event_btnRetrocederActionPerformed

    private void btnAvanzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvanzarActionPerformed
        avanzarPaginaPlacas();
    }//GEN-LAST:event_btnAvanzarActionPerformed

    private void btnRetrocederRetirosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetrocederRetirosActionPerformed
        retrocederPaginaLicencias();
    }//GEN-LAST:event_btnRetrocederRetirosActionPerformed

    private void btnAvanzarRetirosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvanzarRetirosActionPerformed
        avanzarPaginaLicencias();
    }//GEN-LAST:event_btnAvanzarRetirosActionPerformed

    private void btnConsultlarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultlarActionPerformed
       this.llenarTablaPlacas();
       this.llenarTablaLicencias();
    }//GEN-LAST:event_btnConsultlarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAvanzar;
    private javax.swing.JButton btnAvanzarRetiros;
    private javax.swing.JButton btnConsultlar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnRetroceder;
    private javax.swing.JButton btnRetrocederRetiros;
    private javax.swing.JComboBox<String> cbxElementosPagina;
    private javax.swing.JComboBox<String> cbxElementosPaginaRetiros;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblConsultasTramites;
    private javax.swing.JLabel lblLicencias;
    private javax.swing.JLabel lblPlacas;
    private javax.swing.JScrollPane pnlLicencias;
    private javax.swing.JScrollPane pnlPlacas;
    private javax.swing.JTable tblLicencias;
    private javax.swing.JTable tblPlacas;
    private javax.swing.JTextField txtAnioNacimiento;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRfc;
    // End of variables declaration//GEN-END:variables
}
