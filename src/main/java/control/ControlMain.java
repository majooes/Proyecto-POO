package control;

import java.util.ArrayList;
import java.util.List;

import model.IdentificadorEquipo;
import model.Mutante;
import model.Posicion;
import model.PoderFactory;
import model.util.GameConstants;

/**
 * Programa de prueba EXCLUSIVO de la capa Control.
 
  No usa Game ni UI: crea mutantes usando solo clases de Model, los
  mueve unas cuantas rondas con ControladorMovimiento, detecta
  encuentros con DetectorColisiones y los resuelve en paralelo con
  GestorHilos, para verificar que el manejo de hilos y las reglas de
  combate funcionan bien por si solos.
 */
public final class ControlMain {

    private static final int RONDAS_PRUEBA = 5;
    private static final int ANCHO_CAMPO = 20;
    private static final int ALTO_CAMPO = 20;

    private ControlMain() {
    }

    public static void main(String[] args) {
        System.out.println("PRUEBA INDEPENDIENTE DE LA CAPA CONTROL \n");

        List<Mutante> equipoA = crearEquipoDePrueba("A", IdentificadorEquipo.A, 3);
        List<Mutante> equipoB = crearEquipoDePrueba("B", IdentificadorEquipo.B, 3);

        ControladorMovimiento controladorMovimiento =
                new ControladorMovimiento(GameConstants.RADIO_DETECCION_DEFECTO);
        DetectorColisiones detectorColisiones =
                new DetectorColisiones(GameConstants.RADIO_DETECCION_DEFECTO);
        GestorHilos gestorHilos = new GestorHilos(GameConstants.NUM_HILOS_COMBATE_DEFECTO);

        for (int ronda = 1; ronda <= RONDAS_PRUEBA; ronda++) {
            System.out.println("--- Ronda " + ronda + " ---");

            controladorMovimiento.moverTodos(equipoA, ANCHO_CAMPO, ALTO_CAMPO);
            controladorMovimiento.moverTodos(equipoB, ANCHO_CAMPO, ALTO_CAMPO);
            imprimirPosiciones(equipoA, equipoB);

            List<ParCombate> encuentros = detectorColisiones.detectarEncuentros(equipoA, equipoB);
            System.out.println("Encuentros detectados: " + encuentros.size());
            for (ParCombate encuentro : encuentros) {
                System.out.println("  " + encuentro);
            }

            gestorHilos.ejecutarCombates(encuentros);
            gestorHilos.esperarFinalizacion();

            imprimirEnergias(equipoA, equipoB);
            System.out.println();
        }

        gestorHilos.apagar();
        System.out.println(" FIN DE LA PRUEBA DE LA CAPA CONTROL");
    }

    private static List<Mutante> crearEquipoDePrueba(String prefijoNombre, IdentificadorEquipo identificador,
            int cantidad) {
        List<Mutante> equipo = new ArrayList<>();
        for (int i = 1; i <= cantidad; i++) {
            Mutante mutante = new Mutante(
                    prefijoNombre + i,
                    GameConstants.DEFENSA_MIN + (i % GameConstants.DEFENSA_MAX),
                    new Posicion(i, i),
                    GameConstants.VELOCIDAD_MIN + i);
            mutante.setEquipo(identificador);
            mutante.asignarPoder(PoderFactory.crearPoderAleatorio("Poder de " + mutante.getNombre()));
            equipo.add(mutante);
        }
        return equipo;
    }

    private static void imprimirPosiciones(List<Mutante> equipoA, List<Mutante> equipoB) {
        for (Mutante mutante : equipoA) {
            System.out.println("  " + mutante.getNombre() + " -> " + mutante.getPosicion());
        }
        for (Mutante mutante : equipoB) {
            System.out.println("  " + mutante.getNombre() + " -> " + mutante.getPosicion());
        }
    }

    private static void imprimirEnergias(List<Mutante> equipoA, List<Mutante> equipoB) {
        for (Mutante mutante : equipoA) {
            System.out.println("  " + mutante.getNombre() + ": vivo=" + mutante.estaVivo()
                    + ", energia=" + mutante.getEnergia());
        }
        for (Mutante mutante : equipoB) {
            System.out.println("  " + mutante.getNombre() + ": vivo=" + mutante.estaVivo()
                    + ", energia=" + mutante.getEnergia());
        }
    }
}