package model;

import model.util.GameConstants;

/**
*representa un mutante en la batalla
*usa {@link IdentificadorEquipo} para saber su equipo (A o B) sin depender 
*de la clase {@code game.Equipo}. Así, esta clase se mantiene independiente 
*del resto del juego y es más fácil de probar.
*/

public class Mutante {

    private final String nombre;
    private int energia;
    private final int capacidadDefensa;
    private PoderMutante poder;
    private IdentificadorEquipo equipo;
    private Posicion posicion;
    private double velocidad;
    private boolean vivo;

/**
*indica si el mutante decidió defenderse en la pelea actual
*se activa usando {@link #defenderse()} justo antes de un encuentro, 
*y el método {@link #atacar(Mutante)} la vuelve a apagar cuando termina.
*/

    private boolean defendiendo;

    public Mutante(String nombre, int capacidadDefensa, Posicion posicion, double velocidad) {
        if (capacidadDefensa < GameConstants.DEFENSA_MIN || capacidadDefensa > GameConstants.DEFENSA_MAX) {
            throw new IllegalArgumentException(
                    "capacidadDefensa debe estar entre " + GameConstants.DEFENSA_MIN
                            + " y " + GameConstants.DEFENSA_MAX);
        }
        this.nombre = nombre;
        this.capacidadDefensa = capacidadDefensa;
        this.posicion = posicion;
        this.velocidad = velocidad;
        this.energia = GameConstants.ENERGIA_INICIAL;
        this.vivo = true;
        this.defendiendo = false;
        this.poder = null;
        this.equipo = null;
    }

/**
*actualiza la posición del mutante, siempre y cuando siga vivo. 
*las coordenadas exactas del destino las calcula y envía el 
*controladorMovimiento
*/

    public void mover(Posicion destino) {
        if (!estaVivo()) {
            return;
        }
        this.posicion = destino;
    }

/**
*ataca a otro mutante con el poder actual
*revisa si el objetivo decidió defenderse previamente usando {@link #defenderse()}
*sin defensa: El objetivo recibe todo el daño del poder
*con defensa: El daño se divide entre la defensa del objetivo
*si el ataque logra quitarle energía al rival, el poder del atacante sube un nivel
*/

    public void atacar(Mutante objetivo) {
        if (!estaVivo() || objetivo == null || !objetivo.estaVivo() || this.poder == null) {
            return;
        }

        int danoBase = this.poder.getCapacidadDano();
        int danoFinal = objetivo.isDefendiendo()
                ? danoBase / objetivo.getCapacidadDefensa()
                : danoBase;

        objetivo.recibirDano(danoFinal);

        if (danoFinal > 0) {
            this.poder.subirNivel();
        }

// el encuentro quedo resuelto entonces se limpia la bandera de defensa
        objetivo.defendiendo = false;
    }

/**
*marca a este mutante como "defendiendose" para el proximo ataque que
*reciba. Lo decide la capa de control en el instante del encuentro
*/
    public void defenderse() {
        this.defendiendo = true;
    }

/**
*reduce la energia del mutante. Si llega a 0 (o menos), el mutante
*muere y la energia se limita a 0
*/
    public void recibirDano(int dano) {
        if (!estaVivo() || dano <= 0) {
            return;
        }
        this.energia = Math.max(GameConstants.ENERGIA_MINIMA, this.energia - dano);
        if (this.energia == GameConstants.ENERGIA_MINIMA) {
            this.vivo = false;
        }
    }

    public boolean estaVivo() {
        return vivo;
    }

    public int getEnergia() {
        return energia;
    }

    public boolean isDefendiendo() {
        return defendiendo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidadDefensa() {
        return capacidadDefensa;
    }

    public PoderMutante getPoder() {
        return poder;
    }

/**
*asigna el poder del mutante. Un mutante solo puede portar un poder a
*la vez, por lo que asignar uno nuevo reemplaza al anterior
*/
    public void asignarPoder(PoderMutante poder) {
        this.poder = poder;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public IdentificadorEquipo getEquipo() {
        return equipo;
    }

    public void setEquipo(IdentificadorEquipo equipo) {
        this.equipo = equipo;
    }

    @Override
    public String toString() {
        return String.format("%s [vivo=%s, energia=%d, defensa=%d, poder=%s, pos=%s]",
                nombre, vivo, energia, capacidadDefensa,
                poder == null ? "ninguno" : poder.getTipoElemental(), posicion);
    }
}