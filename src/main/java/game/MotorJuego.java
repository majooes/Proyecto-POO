package game;

import java.util.List;

import control.ControladorMovimiento;
import control.DetectorColisiones;
import control.GestorHilos;
import control.ParCombate;
import model.util.GameConstants;

/**
  Coordina el ciclo completo del juego: mover a los mutantes, detectar
  encuentros, resolver los combates en paralelo (usando las clases de
  la capa Control) y verificar si ya hay un ganador. Es quien "inicia"
  y controla cada vuelta de la partida.
 */
public class MotorJuego {

    private final CampoBatalla campoBatalla;
    private final ControladorMovimiento controladorMovimiento;
    private final DetectorColisiones detectorColisiones;
    private final GestorHilos gestorHilos;
    private final int refrescoMs;
    private boolean corriendo;

    public MotorJuego(CampoBatalla campoBatalla, int refrescoMs) {
        this.campoBatalla = campoBatalla;
        this.refrescoMs = refrescoMs;
        this.controladorMovimiento = new ControladorMovimiento(GameConstants.RADIO_DETECCION_DEFECTO);
        this.detectorColisiones = new DetectorColisiones(GameConstants.RADIO_DETECCION_DEFECTO);
        this.gestorHilos = new GestorHilos(GameConstants.NUM_HILOS_COMBATE_DEFECTO);
        this.corriendo = false;
    }

    /**
      Arranca la partida: repite ejecutarCiclo() hasta que el campo de
      batalla quede terminado (un equipo sin mutantes vivos) o hasta
      que se llame a detenerPartida().
     */
    public void iniciarPartida() {
        corriendo = true;
        while (corriendo && !campoBatalla.isTerminado()) {
            ejecutarCiclo();
            try {
                Thread.sleep(refrescoMs);
            } catch (InterruptedException excepcionInterrupcion) {
                Thread.currentThread().interrupt();
                corriendo = false;
            }
        }
        gestorHilos.apagar();
    }

    /**
      Ejecuta una sola vuelta del juego: mueve a todos los mutantes,
      detecta los encuentros y los resuelve en paralelo, y actualiza
      el marcador y el estado de la partida.
     */
    public void ejecutarCiclo() {
        int[] dimensiones = campoBatalla.getDimensiones();
        int ancho = dimensiones[0];
        int alto = dimensiones[1];

        controladorMovimiento.moverTodos(campoBatalla.getEquipoA().getMutantes(), ancho, alto);
        controladorMovimiento.moverTodos(campoBatalla.getEquipoB().getMutantes(), ancho, alto);

        List<ParCombate> encuentros = detectorColisiones.detectarEncuentros(
                campoBatalla.getEquipoA().getMutantes(), campoBatalla.getEquipoB().getMutantes());

        gestorHilos.ejecutarCombates(encuentros);
        gestorHilos.esperarFinalizacion();

        campoBatalla.actualizarMarcador();
        campoBatalla.verificarGanador();
    }

    public void detenerPartida() {
        corriendo = false;
    }

    /**
      Apaga el pool de hilos de combate. Se debe llamar siempre al
      terminar de usar este MotorJuego (la partida termino, o se probo
      un numero fijo de rondas), o el programa se queda colgado porque
      el ExecutorService mantiene hilos vivos de fondo.
     */
    public void finalizar() {
        gestorHilos.apagar();
    }

    public CampoBatalla getCampoBatalla() {
        return campoBatalla;
    }
}