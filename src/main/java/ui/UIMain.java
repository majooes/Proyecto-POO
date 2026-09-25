package ui;

import javax.swing.SwingUtilities;

/**
 * Programa de prueba de la capa UI (y, a la vez, el punto de entrada
 * que corre el juego completo de principio a fin).
 * <p>
 * Arranca la ventana en la pantalla de configuracion, donde el usuario
 * da el unico dato que pide el juego (el tamano de cada equipo), y a
 * partir de ahi todo lo demas (creacion de mutantes, movimiento,
 * combates, marcador, anuncio del ganador y reinicio) corre solo,
 * usando las capas Model, Game y Control ya construidas y probadas por
 * su cuenta con sus propios *Main.
 */
public final class UIMain {

    private UIMain() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaBatalla ventanaBatalla = new VentanaBatalla();
            ventanaBatalla.setVisible(true);
        });
    }
}