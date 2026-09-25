package ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import game.CampoBatalla;
import game.Equipo;
import game.MotorJuego;
import ui.util.UIConstants;
/* 
* "Sujeto" del patrón Observer encargado de actualizar la vista.
 * <p>
 * - Envuelve a {@link CampoBatalla} y {@link MotorJuego}.
 * - Utiliza un {@code javax.swing.Timer} para notificar periódicamente a los {@link ObservadorBatalla} registrados según {@link UIConstants#REFRESCO_MS_DEFECTO}.
 * - Delega toda la lógica de combate y movimiento al {@link MotorJuego} (que opera en hilos independientes).
 * - Su única responsabilidad es iniciar la simulación y avisar a la interfaz cuándo debe repintarse.
*/

public class SujetoBatalla {

    private final List<ObservadorBatalla> observadores;
    private final Timer timerRefresco;

    private CampoBatalla campoBatalla;
    private MotorJuego motorJuego;
    private Thread hiloPartida;

    public SujetoBatalla() {
        this.observadores = new ArrayList<>();
        this.timerRefresco = new Timer(UIConstants.REFRESCO_MS_DEFECTO, evento -> refrescar());
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
    Crea una nueva partida (delegando a {@code CampoBatalla.crearEquipos}) con el tamaño indicado.
    Inicia el {@link MotorJuego} en un hilo independiente para no bloquear la interfaz gráfica, 
    mientras un Timer actualiza la vista en paralelo sobre el hilo de Swing.
     */
    public void iniciarPartida(int tamanoEquipo) {
        detenerPartida();

        campoBatalla = new CampoBatalla(UIConstants.ANCHO_CAMPO_LOGICO, UIConstants.ALTO_CAMPO_LOGICO);
        campoBatalla.crearEquipos(tamanoEquipo);
        motorJuego = new MotorJuego(campoBatalla, UIConstants.REFRESCO_MOTOR_MS);

        hiloPartida = new Thread(motorJuego::iniciarPartida, "hilo-partida-ui");
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