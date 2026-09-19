package model;

/**
 * aumenta de forma mas lenta que el Fuego
 * cada 3 niveles ganados aporta 1 punto extra de dano.
 */
public class PoderHielo extends PoderMutante {

    public PoderHielo(String nombre, int capacidadDano) {
        super(nombre, capacidadDano);
    }

    @Override
    protected int calcularBonificacionPorNivel() {
        return getNivel() / 3;
    }

    @Override
    public String getTipoElemental() {
        return "Hielo";
    }
}