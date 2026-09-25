package control;

import model.Mutante;

/**
  Resuelve, en su propio hilo, un encuentro entre dos mutantes.
  
  Ambos deciden su accion de forma independiente (ReglasCombate), y
  luego se ejecutan las dos acciones correspondientes: si un mutante
  decidio defenderse, esa decision se aplica antes de que el rival
  pueda atacarlo, para que Mutante.atacar() pueda leerla correctamente
  (ver Mutante.isDefendiendo()).
 */
public class HiloCombate implements Runnable {

    private final ParCombate par;

    public HiloCombate(ParCombate par) {
        this.par = par;
    }

    @Override
    public void run() {
        resolverCombate();
    }

    public void resolverCombate() {
        Mutante mutanteA = par.getMutanteA();
        Mutante mutanteB = par.getMutanteB();

        if (!mutanteA.estaVivo() || !mutanteB.estaVivo()) {
            return;
        }

        AccionCombate accionA = ReglasCombate.decidirAccion(mutanteA);
        AccionCombate accionB = ReglasCombate.decidirAccion(mutanteB);

        // B se defiende (si le toca) antes de que A pueda atacarlo.
        if (accionB == AccionCombate.DEFENDER) {
            mutanteB.defenderse();
        }
        if (accionA == AccionCombate.ATACAR) {
            mutanteA.atacar(mutanteB);
        }

        // A se defiende (si le toca) antes de que B pueda atacarlo.
        if (accionA == AccionCombate.DEFENDER) {
            mutanteA.defenderse();
        }
        if (accionB == AccionCombate.ATACAR) {
            mutanteB.atacar(mutanteA);
        }
    }
}