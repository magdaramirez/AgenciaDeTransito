/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itson.GUI;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.itson.dominio.EstadoTramite;
import org.itson.dominio.Pago;
import org.itson.dominio.Persona;
import org.itson.dominio.TipoPlaca;
import org.itson.dominio.TramiteLicencia;
import org.itson.dominio.TramitePlaca;
import org.itson.dominio.VehiculoAutomovil;
import org.itson.excepciones.PersistenciaException;
import org.itson.implementaciones.ConstantesGUI;
import org.itson.implementaciones.LicenciasDAO;
import org.itson.implementaciones.PagosDAO;
import org.itson.implementaciones.PersonasDAO;
import org.itson.implementaciones.PlacasDAO;
import org.itson.implementaciones.VehiculoAutomovilDAO;
import org.itson.interfaces.ILicenciasDAO;
import org.itson.interfaces.IPagosDAO;
import org.itson.interfaces.IPersonasDAO;
import org.itson.interfaces.IPlacasDAO;
import org.itson.interfaces.IVehiculoDAO;
import org.itson.utils.Validadores;

/**
 * Clase que tramita placa para vehículo previamente registrado.
 *
 * @author Michell Cedano - 233230, Magda Ramírez - 233523
 */
public class DlgPlacaVehiculoUsado extends javax.swing.JDialog {

    IPersonasDAO personas = new PersonasDAO();
    ILicenciasDAO licencias = new LicenciasDAO();
    IPlacasDAO placas = new PlacasDAO();
    IVehiculoDAO automoviles = new VehiculoAutomovilDAO();
    IPagosDAO pagos = new PagosDAO();

    /**
     * Método que crea el JDialog DlgPlacaVehiculoUsado.
     *
     * @param parent Clase padre de la ventana.
     * @param modal Foco de la aplicación.
     */
    public DlgPlacaVehiculoUsado(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("VEHÍCULO USADO");
        txtCosto.setText("$1000.00");
        establecerFechaEmision();
        TramitePlaca placaNvo = new TramitePlaca();
        PlacasDAO placaDAO = new PlacasDAO();
        txtPlacaNueva.setText(placaDAO.generarPlaca());
    }

    /**
     * Método que establece la fecha de emisión para la fecha en la que se
     * cambie la placa de un vehículo usado.
     */
    private void establecerFechaEmision() {
        jdcFechaEmision.setDate(new Date());
        jdcFechaEmision.setMinSelectableDate(new Date());
        jdcFechaEmision.setMaxSelectableDate(new Date());
    }

    /**
     * Método que extrae los datos del JDialog.
     *
     * @return datos.
     */
    private HashMap<String, String> extraerDatos() {
        String rfc = this.txtRfc.getText();
        String placa = this.txtPlaca.getText();

        HashMap<String, String> datos = new HashMap<>();
        datos.put("rfc", rfc);
        datos.put("placa", placa);

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
        String placa = datos.get("placa");

        if (Validadores.esTextoVacio(rfc) || Validadores.esTextoVacio(placa)) {
            erroresValidacion.add("Datos vacíos");
        }
        if (!Validadores.esRFC(rfc)) {
            erroresValidacion.add("Formato de RFC incorrecto");
        }
        if (!Validadores.esPlaca(placa)) {
            erroresValidacion.add("Formato de placa incorrecto");
        }
        if (Validadores.excedeLimite(rfc, 13)) {
            erroresValidacion.add("El RFC excede el límite de caracteres");
        }
        if (Validadores.excedeLimite(placa, 9)) {
            erroresValidacion.add("La placa excede el límite de caracteres");
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
     * Método que lleva a cabo el trámite de placa de vehículo usado.
     */
    private void tramitarPlacaVehiculoUsado() {
        HashMap<String, String> datos = this.extraerDatos();

        List<String> erroresValidacion = this.validarDatos(datos);
        if (!erroresValidacion.isEmpty()) {
            this.mostrarErroresValidacion(erroresValidacion);
        }

        try {
            VehiculoAutomovil auto = new VehiculoAutomovil();
            String placa = datos.get("placa");
            String rfc = datos.get("rfc");
            TramitePlaca plac = placas.buscarPlacaPorNumero(placa);

            String noSerie = plac.getVehiculo().getNoSerie();
            auto = automoviles.buscarAutomovil(noSerie);

            TramitePlaca placaNvo = new TramitePlaca();

            float costo = ConstantesGUI.COSTOVEHICULO_USADO;

            placaNvo.setCosto(costo);
            placaNvo.setEstado(EstadoTramite.ACTIVO);
            placaNvo.setFechaEmision(this.jdcFechaEmision.getCalendar());
            placaNvo.setFechaRecepcion(null);
            placaNvo.setPersona(buscarPersonaRFC());
            placaNvo.setPlaca(this.txtPlacaNueva.getText());
            placaNvo.setTipoPlaca(TipoPlaca.USADO);
            placaNvo.setVehiculo(auto);

            placas.registrarPlaca(placaNvo);
            registroPagoRealizado(placaNvo);

            registrarPlacaAutomovil();

            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "El trámite de la placa se ha realizado correctamente." + "\n¿Desea regresar al menú?",
                    "INFORMACION",
                    JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                FrmMenu menu = new FrmMenu();
                menu.setVisible(true);
                this.dispose();
            }

            vaciarDatos();
        } catch (Exception ex) {
            Logger.getLogger(DlgPlacaVehiculoNuevo.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(
                    this,
                    "No se ha podido realizar el trámite de la placa. \nFavor de proporcionar datos correctos.",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Metodo que registra una placa nueva a la lista de placas de un automovil
     *
     * @throws En caso de que no sea posible agregarla a la lista
     */
    private void registrarPlacaAutomovil() throws PersistenciaException {
        HashMap<String, String> datos = this.extraerDatos();
        List<TramitePlaca> placasA = new ArrayList<>();
        VehiculoAutomovil auto = new VehiculoAutomovil();
        String placa = datos.get("placa");

        TramitePlaca placaNvo = placas.buscarPlacaPorNumero(placa);
        String noSerie = placaNvo.getVehiculo().getNoSerie();

        placaNvo = placas.buscarPlacaPorNumero(this.txtPlacaNueva.getText());
        auto = automoviles.buscarAutomovil(noSerie);

        placasA = auto.getPlacas();

        placasA.add(placaNvo);

        auto.setPlacas(placasA);
        automoviles.cambiarListaPlacas(auto);
    }

    /**
     * Metodo que convierte las placas anteriores a inactivas para usar la nueva
     * a registrar
     *
     * @throws PersistenciaException en caso de que no sea posible cambiar el
     * estado de alguna placa
     */
    private void cambiarEstadoPlacaAnterior() throws PersistenciaException {
        HashMap<String, String> datos = this.extraerDatos();
        List<TramitePlaca> placasA = new ArrayList<>();
        VehiculoAutomovil auto = new VehiculoAutomovil();
        String placa = datos.get("placa");

        TramitePlaca placaNvo = placas.buscarPlacaPorNumero(placa);
        String noSerie = placaNvo.getVehiculo().getNoSerie();

        auto = automoviles.buscarAutomovil(noSerie);

        placasA = auto.getPlacas();

        for (int i = 0; i < placasA.size(); i++) {
            if (placasA.get(i).getEstado().equals(EstadoTramite.ACTIVO)) {
                placasA.get(i).setEstado(EstadoTramite.INACTIVO);
                placasA.get(i).setFechaRecepcion(this.jdcFechaRecepcion.getCalendar());
                placas.cambiarEstadoPlaca(placasA.get(i));
            }
        }
    }

    /**
     * Método que vacía los textFields del JDialog.
     */
    private void vaciarDatos() {
        txtRfc.setText(null);
        txtNombreCom.setText(null);
        txtLicencia.setText(null);
        txtPlaca.setText(null);
        txtMarca.setText(null);
        txtLinea.setText(null);
        txtColor.setText(null);
        txtModelo.setText(null);
    }

    /**
     * Metodo que busca una persona por el rfc ingresado en la interfaz
     *
     * @return la persona con el rfc ingresado
     */
    private Persona buscarPersonaRFC() {
        HashMap<String, String> datos = extraerDatos();
        String rfc = datos.get("rfc");
        try {
            Persona persona1 = personas.buscarPersona(rfc);

            return persona1;
        } catch (Exception ex) {
            Logger.getLogger(DlgPlacaVehiculoNuevo.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(
                    this,
                    "No se ha encontrado una persona con el rfc ingresado",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    /**
     * Metodo que comprueba que una persona consultada tenga una licencia activa
     * en el momento
     *
     * @return un verdadero si tiene una activa, falso si no tiene ninguna
     * activa al momento
     * @throws PersistenciaException en caso de que no haya sido posible
     * encontrar alguna licencia
     */
    private boolean comprobarLicenciaPersonaActiva() throws PersistenciaException {
        Persona persona = buscarPersonaRFC();

        List<TramiteLicencia> listaLicencias = licencias.consultarLicenciasPersona(persona.getRfc());
        for (int i = 0; i < listaLicencias.size(); i++) {
            if (listaLicencias.get(i).getEstado().equals(EstadoTramite.ACTIVO)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Licencia activa para la persona con el rfc ingresado",
                        "INFORMACION",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo que escribe vigente o caducado segun el estado de la licencia de
     * la persona consultada
     */
    public void buscarDatosPersonaLicencia() {
        Persona persona;

        try {
            PersonasDAO dao = new PersonasDAO();
            persona = buscarPersonaRFC();
            String nombreCompleto = dao.DesencriptarNombreCompleto(persona);
            String textoEstado = "";

            if (comprobarLicenciaPersonaActiva() == true) {
                textoEstado = "Vigente";
                this.txtRfc.setText(persona.getRfc());
                this.txtNombreCom.setText(nombreCompleto);
                this.txtLicencia.setText(textoEstado);
            } else {
                textoEstado = "No vigente";
                JOptionPane.showMessageDialog(
                        this,
                        "No se ha encontrado una licencia activa para: \n" + dao.DesencriptarNombreCompleto(persona),
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            Logger.getLogger(DlgPlacaVehiculoNuevo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que busca los datos del automovil a registrarle una placa
     *
     * @throws PersistenciaException en caso de que no haya sido posible
     * conseguir sus datos
     */
    private void buscarDatosAutomovil() throws PersistenciaException {
        HashMap<String, String> datos = extraerDatos();
        String placa = datos.get("placa");
        VehiculoAutomovil auto = new VehiculoAutomovil();
        try {
            TramitePlaca plac = placas.buscarPlacaPorNumero(placa);
            auto = automoviles.buscarAutomovil(plac.getVehiculo().getNoSerie());
            if (!(auto.getPlacas().isEmpty())) {
                JOptionPane.showMessageDialog(
                        this,
                        "Automóvil encontrado con la placa ingresada",
                        "INFORMACION",
                        JOptionPane.INFORMATION_MESSAGE);
                this.txtPlaca.setText(plac.getPlaca());
                this.txtColor.setText(auto.getColor());
                this.txtLinea.setText(auto.getLinea());
                this.txtMarca.setText(auto.getMarca());
                this.txtModelo.setText(auto.getModelo());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se ha encontrado un automovil registrado con la placa ingresada",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Metodo que registra el pago realizado del tramite recien realizado
     *
     * @param placa el tramite a registrar el pago
     */
    private void registroPagoRealizado(TramitePlaca placa) {
        Pago pago = new Pago();
        pago.setMonto(placa.getCosto());
        pago.setTramite(placa);

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
        jPanel3 = new javax.swing.JPanel();
        lblDatosSolicitante = new javax.swing.JLabel();
        lblDatosVehiculo = new javax.swing.JLabel();
        lblRfc = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblLicencia = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblFechaRecepcion = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtPlacaNueva = new javax.swing.JTextField();
        jdcFechaEmision = new com.toedter.calendar.JDateChooser();
        jdcFechaRecepcion = new com.toedter.calendar.JDateChooser();
        lblVehiculo = new javax.swing.JLabel();
        cbxVehiculo = new javax.swing.JComboBox<>();
        lblCosto = new javax.swing.JLabel();
        txtCosto = new javax.swing.JTextField();
        lblNoSerie = new javax.swing.JLabel();
        txtPlaca = new javax.swing.JTextField();
        txtRfc = new javax.swing.JTextField();
        txtNombreCom = new javax.swing.JTextField();
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
        lblBuscarPlaca = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        btnVaciar = new javax.swing.JButton();
        btnTramitar = new javax.swing.JButton();

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
        jPanel3.add(lblDatosSolicitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        lblDatosVehiculo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblDatosVehiculo.setText("Datos del vehículo");
        jPanel3.add(lblDatosVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));

        lblRfc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRfc.setText("RFC:");
        jPanel3.add(lblRfc, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        lblNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNombre.setText("Nombre:");
        jPanel3.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        lblLicencia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblLicencia.setText("Licencia:");
        jPanel3.add(lblLicencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        jPanel4.setBackground(new java.awt.Color(240, 139, 139));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFechaRecepcion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFechaRecepcion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblFechaRecepcion.setText("Fecha de recepción:");
        jPanel4.add(lblFechaRecepcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 120, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Fecha Emision:");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Placa nueva:");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        txtPlacaNueva.setEditable(false);
        txtPlacaNueva.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel4.add(txtPlacaNueva, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 110, 30));
        jPanel4.add(jdcFechaEmision, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, 120, 30));
        jPanel4.add(jdcFechaRecepcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 120, 30));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 490, 90));

        lblVehiculo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblVehiculo.setText("Vehículo:");
        jPanel3.add(lblVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, -1, -1));

        cbxVehiculo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbxVehiculo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Automovil" }));
        jPanel3.add(cbxVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, 100, 30));

        lblCosto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCosto.setText("Costo:");
        jPanel3.add(lblCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 70, -1, -1));

        txtCosto.setEditable(false);
        txtCosto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, 90, 30));

        lblNoSerie.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNoSerie.setText("Placa:");
        jPanel3.add(lblNoSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 120, -1, -1));

        txtPlaca.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtPlaca, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 110, 200, 40));

        txtRfc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtRfc, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 130, 40));

        txtNombreCom.setEditable(false);
        txtNombreCom.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtNombreCom, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 220, 40));

        txtLicencia.setEditable(false);
        txtLicencia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtLicencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, 110, 40));

        lblMarca.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMarca.setText("Marca:");
        jPanel3.add(lblMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, -1, -1));

        lblLinea.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblLinea.setText("Línea:");
        jPanel3.add(lblLinea, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 210, -1, -1));

        lblColor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblColor.setText("Color:");
        jPanel3.add(lblColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 170, -1, -1));

        lblModelo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblModelo.setText("Modelo:");
        jPanel3.add(lblModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 210, -1, -1));

        txtMarca.setEditable(false);
        txtMarca.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 160, 100, 30));

        txtLinea.setEditable(false);
        txtLinea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtLinea, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 200, 100, 30));

        txtColor.setEditable(false);
        txtColor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 160, 110, 30));

        txtModelo.setEditable(false);
        txtModelo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 200, 110, 30));

        lblBuscarRFC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        lblBuscarRFC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBuscarRFCMouseClicked(evt);
            }
        });
        jPanel3.add(lblBuscarRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, -1, -1));

        lblBuscarPlaca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        lblBuscarPlaca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBuscarPlacaMouseClicked(evt);
            }
        });
        jPanel3.add(lblBuscarPlaca, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 110, -1, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 710, 360));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Tramite de placa:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(156, 51, 57));
        jLabel2.setText("Vehículo usado");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, -1, -1));

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
        jPanel2.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, 130, 40));

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
        jPanel2.add(btnVaciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 450, 100, 30));

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
        jPanel2.add(btnTramitar, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 440, 130, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 750, 490));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 530));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
/**
     * Método que enfoca el jdcFechaEmisión.
     *
     * @param evt objeto de evento de acción.
     */
    private void jdcFechaEmisionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdcFechaEmisionKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jdcFechaEmision.transferFocus();
        }
    }//GEN-LAST:event_jdcFechaEmisionKeyPressed
    /**
     * Método que enfoca el jdcFechaRecepción.
     *
     * @param evt objeto de evento de acción.
     */
    private void jdcFechaRecepcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdcFechaRecepcionKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jdcFechaRecepcion.transferFocus();
        }
    }//GEN-LAST:event_jdcFechaRecepcionKeyPressed
    /**
     * Botón que al hacerle clic vacía los datos del JDialog.
     *
     * @param evt objeto de evento de acción.
     */
    private void btnVaciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVaciarActionPerformed
        // TODO add your handling code here:
        vaciarDatos();
    }//GEN-LAST:event_btnVaciarActionPerformed
    /**
     * Botón que al hacerle clic regresa a la ventana FrmTramitePlaca.
     *
     * @param evt objeto de evento de acción.
     */
    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.dispose();
        FrmTramitePlaca tramitePlaca = new FrmTramitePlaca();
        tramitePlaca.setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed
    /**
     * Botón que al hacerle clic realiza el trámite de la placa.
     *
     * @param evt objeto de evento de acción.
     */
    private void btnTramitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTramitarActionPerformed
        try {
            Calendar fechaR = this.jdcFechaRecepcion.getCalendar();
            if(fechaR != null){
                cambiarEstadoPlacaAnterior();
                tramitarPlacaVehiculoUsado();
            }else{
                JOptionPane.showMessageDialog(
                    this,
                    "ERROR: Porfavor seleccione una fecha de recepción para la placa",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            } 
        } catch (PersistenciaException ex) {
            Logger.getLogger(DlgPlacaVehiculoUsado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnTramitarActionPerformed
    /**
     * Método que al hacerle clic al lblBuscarRFC busca los datos personales de acuerdo al RFC ingresado.
     *
     * @param evt objeto de evento de acción.
     */
    private void lblBuscarRFCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBuscarRFCMouseClicked
        // TODO add your handling code here:
        buscarDatosPersonaLicencia();
    }//GEN-LAST:event_lblBuscarRFCMouseClicked
    /**
     * Método que al hacerle clic al lblBuscarPlaca busca los datos del vehículo de acuerdo a la placa ingresada.
     *
     * @param evt objeto de evento de acción.
     */
    private void lblBuscarPlacaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBuscarPlacaMouseClicked
        try {
            // TODO add your handling code here:
            buscarDatosAutomovil();
        } catch (PersistenciaException ex) {
            Logger.getLogger(DlgPlacaVehiculoUsado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lblBuscarPlacaMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnTramitar;
    private javax.swing.JButton btnVaciar;
    private javax.swing.JComboBox<String> cbxVehiculo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private com.toedter.calendar.JDateChooser jdcFechaEmision;
    private com.toedter.calendar.JDateChooser jdcFechaRecepcion;
    private javax.swing.JLabel lblBuscarPlaca;
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
    private javax.swing.JLabel lblRfc;
    private javax.swing.JLabel lblVehiculo;
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtLicencia;
    private javax.swing.JTextField txtLinea;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNombreCom;
    private javax.swing.JTextField txtPlaca;
    private javax.swing.JTextField txtPlacaNueva;
    private javax.swing.JTextField txtRfc;
    // End of variables declaration//GEN-END:variables
}
