package game;

import java.awt.Color;
import java.util.Random;

import model.EstadoBatalla;
import model.IdentificadorEquipo;
import model.Marcador;
import model.Mutante;
import model.PoderFactory;
import model.Posicion;
import model.util.GameConstants;

/**
 * Representa el campo de batalla: crea los dos equipos, controla sus
 * dimensiones, y lleva un unico Marcador compartido para ambos equipos
 * (ese diseno de Marcador ya lo trae la capa Model). El juego termina
 * cuando el Marcador indica que un equipo se quedo sin mutantes vivos.
 */
public class CampoBatalla {

    private final int ancho;
    private final int alto;
    private final Marcador marcador;
    private Equipo equipoA;
    private Equipo equipoB;
    private volatile EstadoBatalla estado;

    public CampoBatalla(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        this.marcador = new Marcador();
        this.estado = EstadoBatalla.EN_CURSO;
    }

    /**
     * Crea los dos equipos con la misma cantidad de mutantes cada uno
     * (entre TAMANO_EQUIPO_MIN y TAMANO_EQUIPO_MAX), ubicados en
     * posiciones aleatorias dentro del campo, cada uno con un poder
     * aleatorio asignado por PoderFactory.
     */
    public void crearEquipos(int tamano) {
        if (tamano < GameConstants.TAMANO_EQUIPO_MIN || tamano > GameConstants.TAMANO_EQUIPO_MAX) {
            throw new IllegalArgumentException(
                    "El tamano del equipo debe estar entre " + GameConstants.TAMANO_EQUIPO_MIN
                            + " y " + GameConstants.TAMANO_EQUIPO_MAX);
        }

        Random random = new Random();
        equipoA = new Equipo(IdentificadorEquipo.A, Color.RED, null);
        equipoB = new Equipo(IdentificadorEquipo.B, Color.BLUE, null);

        for (int i = 1; i <= tamano; i++) {
            equipoA.agregarMutante(crearMutanteAleatorio("A" + i, random));
            equipoB.agregarMutante(crearMutanteAleatorio("B" + i, random));
        }

        actualizarMarcador();
    }

    private Mutante crearMutanteAleatorio(String nombre, Random random) {
        int defensa = GameConstants.DEFENSA_MIN
                + random.nextInt(GameConstants.DEFENSA_MAX - GameConstants.DEFENSA_MIN + 1);
        double velocidad = GameConstants.VELOCIDAD_MIN
                + random.nextDouble() * (GameConstants.VELOCIDAD_MAX - GameConstants.VELOCIDAD_MIN);
        Posicion posicionInicial = new Posicion(random.nextInt(ancho), random.nextInt(alto));

        Mutante mutante = new Mutante(nombre, defensa, posicionInicial, velocidad);
        mutante.asignarPoder(PoderFactory.crearPoderAleatorio("Poder de " + nombre));
        return mutante;
    }

    /**
     * Sincroniza el Marcador con la cantidad real de vivos de cada
     * equipo. Se llama despues de cada ronda de combate.
     */
    public void actualizarMarcador() {
        marcador.actualizar(equipoA.contarVivos(), equipoB.contarVivos(),
                equipoA.contarMuertos(), equipoB.contarMuertos());
    }

    /**
     * Devuelve el equipo ganador si el Marcador ya puede determinarlo
     * (el rival se quedo sin mutantes vivos), o null si la batalla
     * sigue en curso. Si hay ganador, marca la batalla como terminada.
     */
    public Equipo verificarGanador() {
        IdentificadorEquipo ganadorId = marcador.getGanadorParcial();
        if (ganadorId == null) {
            return null;
        }
        estado = EstadoBatalla.TERMINADA;
        return ganadorId == IdentificadorEquipo.A ? equipoA : equipoB;
    }

    public int[] getDimensiones() {
        return new int[] { ancho, alto };
    }

    public boolean isTerminado() {
        return estado == EstadoBatalla.TERMINADA;
    }

    public Equipo getEquipoA() {
        return equipoA;
    }

    public Equipo getEquipoB() {
        return equipoB;
    }

    public Marcador getMarcador() {
        return marcador;
    }

    public EstadoBatalla getEstado() {
        return estado;
    }
}