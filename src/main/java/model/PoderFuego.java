package model;

/**
 * aumenta de forma agresiva con el nivel:
 * cada 2 niveles ganados aporta 1 punto extra de dano.
 */
public class PoderFuego extends PoderMutante {

    public PoderFuego(String nombre, int capacidadDano) {
        super(nombre, capacidadDano);
    }

    @Override
    protected int calcularBonificacionPorNivel() {
        return getNivel() / 2;
    }

    @Override
    public String getTipoElemental() {
        return "Fuego";
    }
}