package model;

/**
 * Representa una coordenada (x, y) en el campo de batalla.
 * Encapsula la lógica geométrica en su propia clase, permitiendo 
 * al ControladorMovimiento verificar de forma más limpia si dos mutantes 
 * están dentro del radio de detección.
 */
public class Posicion {

    private double x;
    private double y;

    public Posicion(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Distancia entre esta posicion y otra.
     * La usa ControladorMovimiento para saber si un mutante entro en el
     * radio de deteccion de un oponente.
     */
    public double calcularDistancia(Posicion otra) {
        double deltaX = this.x - otra.x;
        double deltaY = this.y - otra.y;
        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%.1f, %.1f)", x, y);
    }
}