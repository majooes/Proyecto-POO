package control;

import java.util.Random;

import model.Mutante;

/**
  Contiene la regla que valida cada encuentro: decide, para un mutante
  dado, si en ese instante ataca o se defiende. El calculo del dano en
 si mismo ya vive dentro de Mutante.atacar() (usa la capacidadDano del
  poder y, si el objetivo se defendio, la divide entre su capacidadDefensa),
  asi que esta clase solo se encarga de la decision de accion.
 */
public final class ReglasCombate {

    private static final Random RANDOM = new Random();

    private ReglasCombate() {
    }

    public static AccionCombate decidirAccion(Mutante mutante) {
        boolean ataca = RANDOM.nextBoolean();
        return ataca ? AccionCombate.ATACAR : AccionCombate.DEFENDER;
    }
}