package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ui.util.UIConstants;

/**
* Controlador del patrón MVC encargado de los eventos de la interfaz.
 * <p>
 * - Traduce los clics de los botones en órdenes de ejecución para {@link SujetoBatalla}.
 * - Coordina los cambios de vista en {@link VentanaBatalla}.
 * - Actúa exclusivamente como orquestador; no implementa reglas ni lógica del juego.
 */
public class ControladorUI implements ActionListener {

    private final VentanaBatalla ventanaBatalla;
    private final SujetoBatalla sujetoBatalla;

    public ControladorUI(VentanaBatalla ventanaBatalla, SujetoBatalla sujetoBatalla) {
        this.ventanaBatalla = ventanaBatalla;
        this.sujetoBatalla = sujetoBatalla;
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        String comando = evento.getActionCommand();

        if (UIConstants.COMANDO_INICIAR.equals(comando)) {
            iniciarBatalla();
        } else if (UIConstants.COMANDO_NUEVA_PARTIDA.equals(comando)) {
            ventanaBatalla.mostrarPantallaConfiguracion();
        }
    }

    private void iniciarBatalla() {
        int tamanoEquipo = ventanaBatalla.getTamanoEquipoSeleccionado();
        sujetoBatalla.iniciarPartida(tamanoEquipo);
        ventanaBatalla.mostrarPantallaBatalla();
    }
}