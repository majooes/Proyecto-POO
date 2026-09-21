package model;

/**
* Lleva el marcador de la batalla: cuenta los mutantes vivos y muertos 
 * de cada equipo, y en qué ronda va la partida.
 * <p>
 * Usa {@link IdentificadorEquipo} (A o B) para evitar que la capa Model 
 * dependa de la capa Game. Es Game quien le avisa a este objeto a qué equipo 
 * pertenece cada mutante cuando ocurre un cambio.
 */
public class Marcador {

    private int vivosA;
    private int vivosB;
    private int muertosA;
    private int muertosB;
    private int rondaActual;

    public Marcador() {
        this.vivosA = 0;
        this.vivosB = 0;
        this.muertosA = 0;
        this.muertosB = 0;
        this.rondaActual = 0;
    }

    /**
     * Registra la muerte de un mutante del equipo indicado, incrementando
     * su contador de muertos y decrementando el de vivos.
     */
    public void registrarMuerte(IdentificadorEquipo equipo) {
        if (equipo == IdentificadorEquipo.A) {
            muertosA++;
            if (vivosA > 0) {
                vivosA--;
            }
        } else if (equipo == IdentificadorEquipo.B) {
            muertosB++;
            if (vivosB > 0) {
                vivosB--;
            }
        }
    }

    /**
     * Actualiza el número de mutantes vivos de cada equipo y avanza de ronda.
     * Se llama constantemente desde la capa Game o Control durante la batalla, 
     * enviándole cuántos mutantes siguen vivos en cada lado.
     */
    public void actualizar(int vivosA, int vivosB) {
        this.vivosA = vivosA;
        this.vivosB = vivosB;
        rondaActual++;
    }

    /**
     * Devuelve el equipo ganador si el rival se quedó sin mutantes vivos. 
     * Si la batalla aún no termina, devuelve null.
     */
    public IdentificadorEquipo getGanadorParcial() {
        if (vivosA == 0 && vivosB > 0) {
            return IdentificadorEquipo.B;
        }
        if (vivosB == 0 && vivosA > 0) {
            return IdentificadorEquipo.A;
        }
        return null;
    }

    public int getVivosA() {
        return vivosA;
    }

    public void setVivosA(int vivosA) {
        this.vivosA = vivosA;
    }

    public int getVivosB() {
        return vivosB;
    }

    public void setVivosB(int vivosB) {
        this.vivosB = vivosB;
    }

    public int getMuertosA() {
        return muertosA;
    }

    public int getMuertosB() {
        return muertosB;
    }

    public int getRondaActual() {
        return rondaActual;
    }

    @Override
    public String toString() {
        return String.format("Ronda %d | Equipo A -> vivos=%d, muertos=%d | Equipo B -> vivos=%d, muertos=%d",
                rondaActual, vivosA, muertosA, vivosB, muertosB);
    }
}