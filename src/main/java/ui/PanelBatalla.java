package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JPanel;

import game.CampoBatalla;
import game.Equipo;
import model.Mutante;
import model.util.GameConstants;
import ui.util.UIConstants;

/**
 * Vista (canvas) encargada de dibujar a los mutantes vivos (posición, color y energía).
 * <p>
 * - Implementa {@link ObservadorBatalla} llamando a {@code repaint()} con cada nuevo estado.
 * - Obtiene todos los datos consultando a {@code CampoBatalla}.
 * - No calcula posiciones, daño ni resultados; delega toda la lógica al modelo.
 */
public class PanelBatalla extends JPanel implements ObservadorBatalla {

    private final SujetoBatalla sujetoBatalla;

    public PanelBatalla(SujetoBatalla sujetoBatalla) {
        this.sujetoBatalla = sujetoBatalla;
        setPreferredSize(new Dimension(UIConstants.ANCHO_VENTANA, UIConstants.ALTO_CANVAS));
        setBackground(UIConstants.COLOR_FONDO_CAMPO);
    }

    @Override
    public void actualizar() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        CampoBatalla campoBatalla = sujetoBatalla.getCampoBatalla();
        if (campoBatalla == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int[] dimensionesLogicas = campoBatalla.getDimensiones();
        double escalaX = getWidth() / (double) dimensionesLogicas[0];
        double escalaY = getHeight() / (double) dimensionesLogicas[1];

        dibujarEquipo(g2, campoBatalla.getEquipoA(), escalaX, escalaY);
        dibujarEquipo(g2, campoBatalla.getEquipoB(), escalaX, escalaY);
    }

    private void dibujarEquipo(Graphics2D g2, Equipo equipo, double escalaX, double escalaY) {
        if (equipo == null) {
            return;
        }
        List<Mutante> mutantes = equipo.getMutantes();
        for (Mutante mutante : mutantes) {
            if (!mutante.estaVivo()) {
                continue;
            }
            int x = (int) (mutante.getPosicion().getX() * escalaX);
            int y = (int) (mutante.getPosicion().getY() * escalaY);
            int radio = UIConstants.RADIO_MUTANTE_PX;

            g2.setColor(equipo.getColor());
            g2.fillOval(x - radio, y - radio, radio * 2, radio * 2);
            g2.setColor(Color.WHITE);
            g2.drawOval(x - radio, y - radio, radio * 2, radio * 2);

            g2.setFont(UIConstants.FUENTE_ETIQUETA);
            g2.drawString(mutante.getNombre(), x - radio, y - radio - 4);

            dibujarBarraEnergia(g2, mutante, x, y, radio);
        }
    }

    private void dibujarBarraEnergia(Graphics2D g2, Mutante mutante, int x, int y, int radio) {
        int anchoBarra = radio * 2;
        int altoBarra = 4;
        int barraX = x - radio;
        int barraY = y + radio + 3;

        double proporcion = mutante.getEnergia() / (double) GameConstants.ENERGIA_INICIAL;
        proporcion = Math.max(0, Math.min(1, proporcion));
        int anchoRelleno = (int) (anchoBarra * proporcion);

        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(barraX, barraY, anchoBarra, altoBarra);
        g2.setColor(proporcion > 0.3 ? Color.GREEN : Color.RED);
        g2.fillRect(barraX, barraY, anchoRelleno, altoBarra);
    }
}