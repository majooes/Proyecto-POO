package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.util.UIConstants;

/**
* Contenedor principal de la Vista (JFrame) en el patrón MVC.
 * <p>
 * - Alterna entre las pantallas de configuración y batalla mediante un {@code CardLayout}.
 * - Registra a {@link PanelBatalla} y {@link PanelMarcador} como observadores en {@link SujetoBatalla}.
 * - Delega las interacciones del usuario al {@link ControladorUI}.
 * - Su única responsabilidad es ensamblar la interfaz gráfica, sin implementar reglas del juego.
 */
public class VentanaBatalla extends JFrame {

    private static final String TARJETA_CONFIGURACION = "configuracion";
    private static final String TARJETA_BATALLA = "batalla";

    private final CardLayout cardLayout;
    private final JPanel panelContenedor;
    private final PanelConfiguracion panelConfiguracion;
    private final SujetoBatalla sujetoBatalla;

    public VentanaBatalla() {
        super(UIConstants.TITULO_VENTANA);

        this.sujetoBatalla = new SujetoBatalla();
        this.cardLayout = new CardLayout();
        this.panelContenedor = new JPanel(cardLayout);

        this.panelConfiguracion = new PanelConfiguracion();
        PanelBatalla panelBatalla = new PanelBatalla(sujetoBatalla);
        PanelMarcador panelMarcador = new PanelMarcador(sujetoBatalla);

        // Patron Observer: el canvas y el marcador se registran para que
        // SujetoBatalla los notifique en cada tick del refresco.
        sujetoBatalla.agregarObservador(panelBatalla);
        sujetoBatalla.agregarObservador(panelMarcador);

        ControladorUI controladorUI = new ControladorUI(this, sujetoBatalla);

        panelConfiguracion.getBotonIniciar().setActionCommand(UIConstants.COMANDO_INICIAR);
        panelConfiguracion.getBotonIniciar().addActionListener(controladorUI);

        JButton botonNuevaPartida = new JButton("Nueva Partida");
        botonNuevaPartida.setActionCommand(UIConstants.COMANDO_NUEVA_PARTIDA);
        botonNuevaPartida.addActionListener(controladorUI);

        JPanel panelBatallaCompleto = new JPanel(new BorderLayout());
        panelBatallaCompleto.add(panelMarcador, BorderLayout.NORTH);
        panelBatallaCompleto.add(panelBatalla, BorderLayout.CENTER);
        panelBatallaCompleto.add(botonNuevaPartida, BorderLayout.SOUTH);

        panelContenedor.add(panelConfiguracion, TARJETA_CONFIGURACION);
        panelContenedor.add(panelBatallaCompleto, TARJETA_BATALLA);

        setContentPane(panelContenedor);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Vuelve a la pantalla de configuracion. Se usa al terminar una
     * partida (boton "Nueva Partida"), para pedir de nuevo el unico dato
     * que pide el juego: el tamano de equipo.
     */
    public void mostrarPantallaConfiguracion() {
        sujetoBatalla.detenerPartida();
        cardLayout.show(panelContenedor, TARJETA_CONFIGURACION);
    }

    public void mostrarPantallaBatalla() {
        cardLayout.show(panelContenedor, TARJETA_BATALLA);
    }

    public int getTamanoEquipoSeleccionado() {
        return panelConfiguracion.getTamanoSeleccionado();
    }
}