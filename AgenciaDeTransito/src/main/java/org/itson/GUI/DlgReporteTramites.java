/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itson.GUI;

import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Persistence;
import javax.swing.table.DefaultTableModel;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.itson.dominio.*;
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
     *
     * @param parent Clase padre de la ventana.
     * @param modal Foco de la aplicación.
     */
    public DlgReporteTramites(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("REPORTES");
        this.paginado = new ConfiguracionPaginado(0, 3);
        if (this.cbxTramite.getSelectedItem().toString().equals("Expedicion de placa")) {
            this.llenarTablaPlacas();
        } else {
            this.llenarTablaLicencias();
        }
    }

    /**
     * Método que llena la tabla de placas.
     */
    private void llenarTablaPlacas() {  
        

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date fecha1;
            String fechaEmision;
            List<TramitePlaca> listaTramitesPlacas = placas.consultarPlacas(paginado, obtenerDatosTramitePlaca());
            DefaultTableModel modeloTabla = (DefaultTableModel) this.tblTramites.getModel();

            String nombDes = null, apellidoPDes = null, apellidoMDes = null, nomCompDes = null;
            String tipo = "Expedicion de placa";

            modeloTabla.setRowCount(0);
            for (TramitePlaca trans : listaTramitesPlacas) {
                fecha1 = trans.getFechaEmision().getTime();
                fechaEmision = dateFormat.format(fecha1);
                nombDes = encriptador.desencriptar(trans.getPersona().getNombre());
                apellidoPDes = encriptador.desencriptar(trans.getPersona().getApellidoPaterno());
                apellidoMDes = encriptador.desencriptar(trans.getPersona().getApellidoMaterno());
                nomCompDes = nombDes + " " + apellidoPDes + " " + apellidoMDes;

                Object[] fila = {
                    fechaEmision,
                    nomCompDes,
                    tipo,
                    trans.getCosto()
                };
                modeloTabla.addRow(fila);
            }
        } catch (PersistenciaException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }
    }

    /**
     * Método que llena la tabla de licencias.
     */
    private void llenarTablaLicencias() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date fecha1;
            String fechaEmision;
            List<TramiteLicencia> listaLicencias = this.licencias.consultarLicencias(paginado, obtenerDatosTramiteLicencia());
            DefaultTableModel modeloTabla = (DefaultTableModel) this.tblTramites.getModel();
            String nombDes = null, apellidoPDes = null, apellidoMDes = null, nomCompDes = null;
            String tipo = "Expedicion de licencia";

            modeloTabla.setRowCount(0);
            for (TramiteLicencia licencia : listaLicencias) {
                fecha1 = licencia.getFechaEmision().getTime();
                fechaEmision = dateFormat.format(fecha1);
                nombDes = encriptador.desencriptar(licencia.getPersona().getNombre());
                apellidoPDes = encriptador.desencriptar(licencia.getPersona().getApellidoPaterno());
                apellidoMDes = encriptador.desencriptar(licencia.getPersona().getApellidoMaterno());
                nomCompDes = nombDes + " " + apellidoPDes + " " + apellidoMDes;

                Object[] fila = {
                    fechaEmision,
                    nomCompDes,
                    tipo,
                    licencia.getCosto()
                };
                modeloTabla.addRow(fila);
            }
        } catch (PersistenciaException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }
    }

    /**
     * Metodo que obtiene los datos para la placa de la interfaz grafica para la
     * consutla en caso de haber sido ingresados.
     *
     * @return Datos para la placa.
     */
    private Placa obtenerDatosTramitePlaca() {
        Placa placaD = new Placa();
        String nomEncriptado = encriptador.encriptar(this.txtNombre.getText());

        placaD.setFechaInicio(this.jdcFechaInicio.getCalendar());
        placaD.setFechaFin(this.jdcFechaFin.getCalendar());

        if (this.txtNombre.getText().equals("")) {
            placaD.setNombre(null);
        } else {
            placaD.setNombre(nomEncriptado);
        }

        return placaD;

    }

    /**
     * Metodo que obtiene los datos para la licencia de la interfaz grafica para
     * la consulta en caso de haber sido ingresados.
     *
     * @return Datos para la licencia.
     */
    private LicenciaDTO obtenerDatosTramiteLicencia() {
        LicenciaDTO licenciaD = new LicenciaDTO();
        String nomEncriptado = encriptador.encriptar(this.txtNombre.getText());

        licenciaD.setFechaInicio(this.jdcFechaInicio.getCalendar());
        licenciaD.setFechaFin(this.jdcFechaFin.getCalendar());

        if (this.txtNombre.getText().equals("")) {
            licenciaD.setNombre(null);
        } else {
            licenciaD.setNombre(nomEncriptado);
        }

        return licenciaD;
    }
    
    private float calcularMontoTotalTramites(){
        float costoTotal = 0f, costo = 0f;
        for (int i = 0; i < tblTramites.getRowCount(); i++) {
                costo = (float) tblTramites.getValueAt(i, 3);
                costoTotal = costoTotal + costo;
            }
        return costoTotal;
    }
    
    /**
     * Método que avanza de página de trámites.
     */
    private void avanzarPaginaTramites() {
        this.paginado.avanzarPagina();
        if (this.cbxTramite.getSelectedItem().toString().equals("Expedicion de placa")) {
            this.llenarTablaPlacas();
        } else {
            this.llenarTablaLicencias();
        }
    }

    /**
     * Método que retrocede de página de trámites.
     */
    private void retrocederPaginaTramites() {
        this.paginado.retrocederPagina();
        if (this.cbxTramite.getSelectedItem().toString().equals("Expedicion de placa")) {
            this.llenarTablaPlacas();
        } else {
            this.llenarTablaLicencias();
        }
    }

    /**
     * Método que genera el reporte.
     */
//    private void generarReporte() {
//        try {
//            ArrayList lista = new ArrayList();
//            for (int i = 0; i < tblTramites.getRowCount(); i++) {
//                Reporte reporte = new Reporte(
//                tblTramites.getValueAt(i, 0)+"",
//                tblTramites.getValueAt(i, 1)+"",
//                tblTramites.getValueAt(i, 2)+"",
//                tblTramites.getValueAt(i, 3)+"");
//                lista.add(reporte);
//            }
//
//            JasperReport jr = null;
//            jr = (JasperReport) JRLoader.loadObjectFromFile("src\\main\\java\\org\\itson\\utils\\Reporte.jasper");
//
//            HashMap parametro = new HashMap();
//            parametro.put("monto", calcularMontoTotalTramites());
//
//            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, new JRBeanCollectionDataSource(lista));
//            JasperViewer jv = new JasperViewer(jp, false);
//            jv.setVisible(true);
//            
//            
//            
//        } catch (JRException ex) {
//            JOptionPane.showMessageDialog(null,
//                    "No fue posible descargar el reporte.",
//                    "ERROR",
//                    JOptionPane.ERROR_MESSAGE);
//        }
//
//    }
    
    private void generarReporteLicencias(List<TramiteLicencia> listaTramites) throws JRException{
        
        String outputFile = "src\\main\\resources\\pdfs" + "ReporteTramites.pdf";

        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listaTramites);
        String monto = ""+calcularMontoTotalTramites()+"";
        
        Map<String, Object> parameters = new HashMap<>();
         parameters.put("CollectionBeanParam", itemsJRBean);
         parameters.put("nombre", this.txtNombre.getText());
         parameters.put("monto", monto);
         parameters.put("tipo", "Expedicion de licencia");

        try {
            InputStream input = new FileInputStream(new File("src\\main\\java\\org\\itson\\utils\\Reporte.jrxml"));

            JasperDesign jasperDesign = JRXmlLoader.load(input);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            JasperViewer.viewReport(jasperPrint, false);

            System.out.println("Archivo generado...");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DlgReporteTramites.class.getName()).log(Level.SEVERE, null, ex);
        }   
            
    }
    
    private void generarReportePlacas(List<TramitePlaca> listaTramites) throws JRException{
        
        String outputFile = "src\\main\\resources\\pdfs" + "ReporteTramites.pdf";

        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listaTramites);
        String monto = ""+calcularMontoTotalTramites()+"";
        
        Map<String, Object> parameters = new HashMap<>();
         parameters.put("CollectionBeanParam", itemsJRBean);
         parameters.put("nombre", this.txtNombre.getText());
         parameters.put("monto", monto);
         parameters.put("tipo", "Expedicion de placa");

        try {
            InputStream input = new FileInputStream(new File("src\\main\\java\\org\\itson\\utils\\Reporte.jrxml"));

            JasperDesign jasperDesign = JRXmlLoader.load(input);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            JasperViewer.viewReport(jasperPrint, false);

            System.out.println("Archivo generado...");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DlgReporteTramites.class.getName()).log(Level.SEVERE, null, ex);
        }   
            
    }
    
    private void imprimirPDF() throws PersistenciaException{
        if (this.cbxTramite.getSelectedItem().equals("Expedicion de placa")) {
                List<TramitePlaca> listaPlacas = placas.consultarPlacas(paginado, obtenerDatosTramitePlaca());
            try {
                generarReportePlacas(listaPlacas);
            } catch (JRException ex) {
                Logger.getLogger(DlgReporteTramites.class.getName()).log(Level.SEVERE, null, ex);
            }
            } else {
               List<TramiteLicencia> listaLicencias = this.licencias.consultarLicencias(paginado, obtenerDatosTramiteLicencia());
                try {
                    generarReporteLicencias(listaLicencias);
                } catch (JRException ex) {
                    Logger.getLogger(DlgReporteTramites.class.getName()).log(Level.SEVERE, null, ex);
                }
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
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTramites = new javax.swing.JTable();
        btnDescargarPdf = new javax.swing.JButton();
        lblReporteTramite = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        cbxElementosPagina = new javax.swing.JComboBox<>();
        btnRetroceder = new javax.swing.JButton();
        btnAvanzar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
        jPanel3.add(lblInicioPeriodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        lblPeriodoTiempo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblPeriodoTiempo.setText("Periodo de tiempo");
        jPanel3.add(lblPeriodoTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, -1, -1));

        lblFinPeriodo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblFinPeriodo.setText("Fin:");
        jPanel3.add(lblFinPeriodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, -1, -1));

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
        jPanel3.add(jdcFechaInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 110, 30));
        jPanel3.add(jdcFechaFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 120, 140, 30));

        btnBuscar.setBackground(new java.awt.Color(212, 100, 107));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel3.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 110, -1, 40));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 700, 170));

        tblTramites.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblTramites.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Fecha Realización", "Nombre", "Tipo", "Costo"
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

        btnDescargarPdf.setBackground(new java.awt.Color(212, 100, 107));
        btnDescargarPdf.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDescargarPdf.setForeground(new java.awt.Color(255, 255, 255));
        btnDescargarPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/descargar-pdf.png"))); // NOI18N
        btnDescargarPdf.setText("Descargar PDF");
        btnDescargarPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescargarPdfActionPerformed(evt);
            }
        });
        jPanel2.add(btnDescargarPdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 500, 170, 40));

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
        jPanel2.add(cbxElementosPagina, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 460, 60, 30));

        btnRetroceder.setBackground(new java.awt.Color(212, 100, 107));
        btnRetroceder.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 15)); // NOI18N
        btnRetroceder.setForeground(new java.awt.Color(255, 255, 255));
        btnRetroceder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/before.png"))); // NOI18N
        btnRetroceder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetrocederActionPerformed(evt);
            }
        });
        jPanel2.add(btnRetroceder, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 460, 40, 30));

        btnAvanzar.setBackground(new java.awt.Color(212, 100, 107));
        btnAvanzar.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 15)); // NOI18N
        btnAvanzar.setForeground(new java.awt.Color(255, 255, 255));
        btnAvanzar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/next.png"))); // NOI18N
        btnAvanzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvanzarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAvanzar, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 460, 40, 30));

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

    private void cbxElementosPaginaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxElementosPaginaItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            int elementosMostrados = Integer.parseInt(evt.getItem().toString());
            this.paginado.setElementosPagina(elementosMostrados);
            if (this.cbxTramite.getSelectedItem().equals("Expedicion de placa")) {
                this.llenarTablaPlacas();
            } else {
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

    private void btnDescargarPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescargarPdfActionPerformed
        try {
            imprimirPDF();
        } catch (PersistenciaException ex) {
            Logger.getLogger(DlgReporteTramites.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDescargarPdfActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if (this.cbxTramite.getSelectedItem().equals("Expedicion de placa")) {
                this.llenarTablaPlacas();
            } else {
                this.llenarTablaLicencias();
            }
    }//GEN-LAST:event_btnBuscarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAvanzar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnDescargarPdf;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnRetroceder;
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
