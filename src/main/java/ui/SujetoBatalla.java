package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import game.CampoBatalla;
import game.Equipo;
import game.MotorJuego;
import ui.util.UIConstants;

/**
 * "Sujeto" del patron Observer encargado de actualizar la vista.
 * <p>
 * - Envuelve a CampoBatalla y MotorJuego.
 * - Utiliza un javax.swing.Timer para notificar periodicamente a los
 *   ObservadorBatalla registrados segun UIConstants.REFRESCO_MS_DEFECTO.
 * - Delega toda la logica de combate y movimiento al MotorJuego (que
 *   opera en hilos independientes).
 * - Su unica responsabilidad es iniciar la simulacion y avisar a la
 *   interfaz cuando debe repintarse.
 */
public class SujetoBatalla {

    private final List<ObservadorBatalla> observadores;
    private final Timer timerRefresco;

    private CampoBatalla campoBatalla;
    private MotorJuego motorJuego;
    private Thread hiloPartida;

    public SujetoBatalla() {
        this.observadores = new ArrayList<>();

        // Cada vez que el Timer dispara un tick, llama a refrescar().
        // Se usa una clase anonima en vez de una lambda para que quede
        // explicito que ActionListener es una interfaz con un solo
        // metodo (actionPerformed) que estamos implementando aqui mismo.
        this.timerRefresco = new Timer(UIConstants.REFRESCO_MS_DEFECTO, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                refrescar();
            }
        });
    }

    public void agregarObservador(ObservadorBatalla observador) {
        observadores.add(observador);
    }

    private void notificarObservadores() {
        for (ObservadorBatalla observador : observadores) {
            observador.actualizar();
        }
    }

    private void refrescar() {
        notificarObservadores();
        if (campoBatalla != null && campoBatalla.isTerminado()) {
            timerRefresco.stop();
        }
    }

    /**
     * Crea una nueva partida (delegando a CampoBatalla.crearEquipos) con
     * el tamano indicado. Inicia el MotorJuego en un hilo independiente
     * para no bloquear la interfaz grafica, mientras un Timer actualiza
     * la vista en paralelo sobre el hilo de Swing.
     */
    public void iniciarPartida(int tamanoEquipo) {
        detenerPartida();

        campoBatalla = new CampoBatalla(UIConstants.ANCHO_CAMPO_LOGICO, UIConstants.ALTO_CAMPO_LOGICO);
        campoBatalla.crearEquipos(tamanoEquipo);
        motorJuego = new MotorJuego(campoBatalla, UIConstants.REFRESCO_MOTOR_MS);

        // Clase anonima de Runnable en vez de motorJuego::iniciarPartida,
        // para que el metodo que se ejecuta en el hilo quede explicito.
        hiloPartida = new Thread(new Runnable() {
            @Override
            public void run() {
                motorJuego.iniciarPartida();
            }
        }, "hilo-partida-ui");
        hiloPartida.setDaemon(true);
        hiloPartida.start();

        timerRefresco.start();
        notificarObservadores();
    }

    /**
     * Detiene el refresco visual y apaga tanto el hilo del MotorJuego
     * como su pool de hilos de combate (GestorHilos), para poder
     * arrancar una partida nueva sin dejar hilos huerfanos.
     */
    public void detenerPartida() {
        timerRefresco.stop();
        if (motorJuego != null) {
            motorJuego.detenerPartida();
            motorJuego.finalizar();
        }
        if (hiloPartida != null && hiloPartida.isAlive()) {
            hiloPartida.interrupt();
        }
    }

    public CampoBatalla getCampoBatalla() {
        return campoBatalla;
    }

    public boolean isTerminada() {
        return campoBatalla != null && campoBatalla.isTerminado();
    }

    public Equipo getGanador() {
        return campoBatalla == null ? null : campoBatalla.verificarGanador();
    }
}