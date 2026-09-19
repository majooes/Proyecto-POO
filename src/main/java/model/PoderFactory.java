package model;

import java.util.Random;

import model.util.GameConstants;

/**
 * Se encarga de crear poderes de forma aleatoria para los mutantes.
 * Gracias a esto, las capas Game y Control no necesitan conocer los detalles 
 * de cada poder; simplemente le piden uno a esta clase y listo. 
 */
public final class PoderFactory {

    private static final Random RANDOM = new Random();

    private PoderFactory() {
    }

    /**
     * Crea un poder de un tipo elemental aleatorio, con un nombre dado y
     * una capacidad de dano aleatoria dentro del rango permitido
     * (GameConstants.DANO_MIN..DANO_MAX).
     */
    public static PoderMutante crearPoderAleatorio(String nombre) {
        int capacidadDano = GameConstants.DANO_MIN
                + RANDOM.nextInt(GameConstants.DANO_MAX - GameConstants.DANO_MIN + 1);
        int tipo = RANDOM.nextInt(GameConstants.CANTIDAD_TIPOS_PODER);

        switch (tipo) {
            case 0:
                return new PoderFuego(nombre, capacidadDano);
            case 1:
                return new PoderHielo(nombre, capacidadDano);
            default:
                return new PoderAgua(nombre, capacidadDano);
        }
    }
}