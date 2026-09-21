package control;

import model.Mutante;

/**
  Representa un encuentro entre dos mutantes que quedaron dentro del
  radio de deteccion. Es el objeto que se le pasa a cada HiloCombate
  para que resuelva ese encuentro en paralelo con los demas.
 */
public class ParCombate {

    private final Mutante mutanteA;
    private final Mutante mutanteB;

    public ParCombate(Mutante mutanteA, Mutante mutanteB) {
        this.mutanteA = mutanteA;
        this.mutanteB = mutanteB;
    }

    public Mutante getMutanteA() {
        return mutanteA;
    }

    public Mutante getMutanteB() {
        return mutanteB;
    }

    @Override
    public String toString() {
        return mutanteA.getNombre() + " vs " + mutanteB.getNombre();
    }
}
