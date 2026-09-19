package model;

import model.util.GameConstants;

/**
 * Representa el (unico) poder que puede llevar un mutante.
 * <p>
 * Es abstracta a proposito: cada tipo elemental de poder (Fuego, Hielo,
 * Electrico, etc. - ver subclases) sube de nivel de forma distinta, lo cual
 * demuestra herencia y polimorfismo dentro de la propia capa de modelo.
 * La capa de control solo conoce esta clase base y nunca necesita saber
 * de que subtipo concreto se trata para calcular el dano.
 */
public abstract class PoderMutante {

    private final String nombre;
    private int capacidadDano;
    private int nivel;

    protected PoderMutante(String nombre, int capacidadDano) {
        if (capacidadDano < GameConstants.DANO_MIN || capacidadDano > GameConstants.DANO_MAX) {
            throw new IllegalArgumentException(
                    "capacidadDano debe estar entre " + GameConstants.DANO_MIN
                            + " y " + GameConstants.DANO_MAX);
        }
        this.nombre = nombre;
        this.capacidadDano = capacidadDano;
        this.nivel = GameConstants.NIVEL_PODER_INICIAL;
    }

    /**
     * Sube el nivel del poder en 1, sin superar el maximo permitido.
     * Se invoca cada vez que el mutante que lo porta logra reducir la
     * energia de un oponente.
     */
    public void subirNivel() {
        if (nivel < GameConstants.NIVEL_PODER_MAXIMO) {
            nivel += GameConstants.INCREMENTO_NIVEL;
        }
    }

    /**
     * Capacidad de dano efectiva del poder: la capacidad base mas la
     * bonificacion que aporta el nivel actual. El calculo de la
     * bonificacion es polimorfico: cada subclase elemental lo resuelve
     * de forma distinta (ver calcularBonificacionPorNivel).
     */
    public int getCapacidadDano() {
        int danoEfectivo = capacidadDano + calcularBonificacionPorNivel();
        return Math.max(danoEfectivo, GameConstants.DANO_MIN);
    }

    /**
     * Bonificacion de dano que aporta el nivel actual del poder.
     * Cada subclase concreta decide como escala su elemento con el nivel.
     */
    protected abstract int calcularBonificacionPorNivel();

    /**
     * Nombre del tipo elemental (Fuego, Hielo, Electrico, ...).
     * Util para que la UI muestre de que poder se trata.
     */
    public abstract String getTipoElemental();

    public String getNombre() {
        return nombre;
    }

    public int getNivel() {
        return nivel;
    }

    protected int getCapacidadDanoBase() {
        return capacidadDano;
    }

    @Override
    public String toString() {
        return String.format("%s [%s] (dano base=%d, nivel=%d, dano efectivo=%d)",
                nombre, getTipoElemental(), capacidadDano, nivel, getCapacidadDano());
    }
}