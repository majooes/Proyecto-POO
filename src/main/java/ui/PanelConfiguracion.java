package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import model.util.GameConstants;

/**
 * Vista de configuracion inicial. Tal como indica el enunciado, el
 * unico dato que el juego le pide al usuario es el tamano de cada
 * equipo (igual para ambos), respetando el rango valido definido en
 * {@code GameConstants} (3 a 11). El boton "Iniciar Batalla" no arranca
 * nada por si mismo: solo dispara el evento que escucha
 * {@link ControladorUI}.
 */
public class PanelConfiguracion extends JPanel {

    private final JSpinner selectorTamano;
    private final JButton botonIniciar;

    public PanelConfiguracion() {
        setLayout(new GridBagLayout());
        GridBagConstraints restricciones = new GridBagConstraints();
        restricciones.insets = new Insets(10, 10, 10, 10);
        restricciones.gridx = 0;

        JLabel titulo = new JLabel("Batalla de Mutantes");
        restricciones.gridy = 0;
        add(titulo, restricciones);

        JLabel etiquetaTamano = new JLabel("Tamano de cada equipo ("
                + GameConstants.TAMANO_EQUIPO_MIN + " - " + GameConstants.TAMANO_EQUIPO_MAX + "):");
        restricciones.gridy = 1;
        add(etiquetaTamano, restricciones);

        selectorTamano = new JSpinner(new SpinnerNumberModel(
                GameConstants.TAMANO_EQUIPO_MIN, GameConstants.TAMANO_EQUIPO_MIN,
                GameConstants.TAMANO_EQUIPO_MAX, 1));
        restricciones.gridy = 2;
        add(selectorTamano, restricciones);

        botonIniciar = new JButton("Iniciar Batalla");
        restricciones.gridy = 3;
        add(botonIniciar, restricciones);
    }

    public int getTamanoSeleccionado() {
        return (Integer) selectorTamano.getValue();
    }

    public JButton getBotonIniciar() {
        return botonIniciar;
    }
}