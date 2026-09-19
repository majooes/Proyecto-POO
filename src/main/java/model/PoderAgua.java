package model;

/**
 * aumenta 1 a 1 con el nivel, pero arranca
 * sin bonificacion en el nivel inicial (nivel - 1).
 */
public class PoderAgua extends PoderMutante {

    public PoderAgua(String nombre, int capacidadDano) {
        super(nombre, capacidadDano);
    }

    @Override
    protected int calcularBonificacionPorNivel() {
        return getNivel() - 1;
    }

    @Override
    public String getTipoElemental() {
        return "Agua";
    }
}