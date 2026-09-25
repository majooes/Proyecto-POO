package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.CampoBatalla;
import game.Equipo;
import model.Marcador;
import ui.util.UIConstants;

/**
* Vista textual para estadísticas de la partida (vivos, muertos, ronda y ganador).
 * <p>
 * - Funciona exclusivamente como vista.
 * - Lee los datos del {@link Marcador} gestionado por {@code CampoBatalla}.
 * - No calcula muertes, victorias ni lógica del juego, solo muestra los resultados.
 */
public class PanelMarcador extends JPanel implements ObservadorBatalla {

    private final SujetoBatalla sujetoBatalla;
    private final JLabel etiquetaEquipoA;
    private final JLabel etiquetaEquipoB;
    private final JLabel etiquetaRonda;
    private final JLabel etiquetaGanador;

    public PanelMarcador(SujetoBatalla sujetoBatalla) {
        this.sujetoBatalla = sujetoBatalla;

        setLayout(new FlowLayout(FlowLayout.CENTER, UIConstants.ESPACIADO_MARCADOR, UIConstants.ESPACIADO_MARCADOR));
        setPreferredSize(new Dimension(UIConstants.ANCHO_VENTANA, UIConstants.ALTO_PANEL_MARCADOR));
        setBackground(UIConstants.COLOR_FONDO_MARCADOR);

        etiquetaEquipoA = crearEtiqueta(Color.RED);
        etiquetaEquipoB = crearEtiqueta(Color.BLUE);
        etiquetaRonda = crearEtiqueta(Color.WHITE);
        etiquetaGanador = crearEtiqueta(Color.YELLOW);
        etiquetaGanador.setFont(UIConstants.FUENTE_GANADOR);

        add(etiquetaEquipoA);
        add(etiquetaEquipoB);
        add(etiquetaRonda);
        add(etiquetaGanador);
    }

    private JLabel crearEtiqueta(Color color) {
        JLabel etiqueta = new JLabel();
        etiqueta.setForeground(color);
        etiqueta.setFont(UIConstants.FUENTE_MARCADOR);
        etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
        return etiqueta;
    }

    @Override
    public void actualizar() {
        CampoBatalla campoBatalla = sujetoBatalla.getCampoBatalla();
        if (campoBatalla == null) {
            return;
        }
        Marcador marcador = campoBatalla.getMarcador();

        etiquetaEquipoA.setText(String.format("Equipo A -> vivos: %d | muertos: %d",
                marcador.getVivosA(), marcador.getMuertosA()));
        etiquetaEquipoB.setText(String.format("Equipo B -> vivos: %d | muertos: %d",
                marcador.getVivosB(), marcador.getMuertosB()));
        etiquetaRonda.setText("Ronda: " + marcador.getRondaActual());

        if (campoBatalla.isTerminado()) {
            Equipo ganador = campoBatalla.verificarGanador();
            etiquetaGanador.setText(ganador == null ? "" : "GANADOR: EQUIPO " + ganador.getIdentificador());
        } else {
            etiquetaGanador.setText("");
        }
    }
}