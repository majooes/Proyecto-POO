package control;

import java.util.ArrayList;
import java.util.List;

import model.Mutante;

/**
  Revisa a todos los mutantes vivos de ambos equipos y arma la lista
  completa de encuentros (pares dentro del radio de deteccion) que hay
  que resolver en la ronda actual. Un mutante puede terminar en varios
  encuentros a la vez si esta cerca de varios oponentes, pero cada par
  solo genera un encuentro.
 */
public class DetectorColisiones {

    private final double radioDeteccion;

    public DetectorColisiones(double radioDeteccion) {
        this.radioDeteccion = radioDeteccion;
    }

    public List<ParCombate> detectarEncuentros(List<Mutante> equipoA, List<Mutante> equipoB) {
        List<ParCombate> encuentros = new ArrayList<>();

        for (Mutante mutanteA : equipoA) {
            if (!mutanteA.estaVivo()) {
                continue;
            }
            for (Mutante mutanteB : equipoB) {
                if (!mutanteB.estaVivo()) {
                    continue;
                }
                double distancia = mutanteA.getPosicion().calcularDistancia(mutanteB.getPosicion());
                if (distancia <= radioDeteccion) {
                    encuentros.add(new ParCombate(mutanteA, mutanteB));
                }
            }
        }

        return encuentros;
    }

    public double getRadioDeteccion() {
        return radioDeteccion;
    }
}