package control;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import model.Mutante;
import model.Posicion;
import model.util.GameConstants;

/**
  Mueve a los mutantes dentro del campo de batalla.

 El movimiento es aleatorio, pero sigue un patron: cada mutante
  mantiene un angulo de direccion (guardado en anguloPorMutante) que
  solo se ajusta un poco en cada paso, en vez de escoger una direccion
  totalmente nueva cada vez. Esto produce un recorrido mas parecido a
  un paseo natural que a un salto erratico, tal como recomienda el
  enunciado ("se recomienda darle algun tipo de patron").
 */
public class ControladorMovimiento {

    private final double radioDeteccion;
    private final Random random;
    private final Map<Mutante, Double> anguloPorMutante;

    public ControladorMovimiento(double radioDeteccion) {
        this.radioDeteccion = radioDeteccion;
        this.random = new Random();
        this.anguloPorMutante = new HashMap<>();
    }

    /**
     * Calcula y aplica el siguiente paso de movimiento de un mutante,
     * respetando los limites del campo de batalla.
     */
    public void moverMutante(Mutante mutante, int ancho, int alto) {
        if (!mutante.estaVivo()) {
            return;
        }

        double anguloActual = obtenerOAsignarAngulo(mutante);
        double ajuste = (random.nextDouble() * 2 * GameConstants.AJUSTE_ANGULO_MAXIMO)
                - GameConstants.AJUSTE_ANGULO_MAXIMO;
        double nuevoAngulo = anguloActual + ajuste;
        anguloPorMutante.put(mutante, nuevoAngulo);

        double paso = mutante.getVelocidad() * GameConstants.PASO_MOVIMIENTO_MAXIMO / GameConstants.VELOCIDAD_MAX;
        double dx = Math.cos(nuevoAngulo) * paso;
        double dy = Math.sin(nuevoAngulo) * paso;

        double destinoX = limitar(mutante.getPosicion().getX() + dx, ancho);
        double destinoY = limitar(mutante.getPosicion().getY() + dy, alto);

        mutante.mover(new Posicion(destinoX, destinoY));
    }

    /**
     * Mueve a todos los mutantes de una lista, uno por uno.
     */
    public void moverTodos(java.util.List<Mutante> mutantes, int ancho, int alto) {
        for (Mutante mutante : mutantes) {
            moverMutante(mutante, ancho, alto);
        }
    }

    /**
     * Indica si dos mutantes quedaron dentro del radio de deteccion,
     * es decir, si les toca decidir atacar o defenderse.
     */
    public boolean detectarColision(Mutante m1, Mutante m2) {
        if (!m1.estaVivo() || !m2.estaVivo()) {
            return false;
        }
        return m1.getPosicion().calcularDistancia(m2.getPosicion()) <= radioDeteccion;
    }

    public double getRadioDeteccion() {
        return radioDeteccion;
    }

    private double obtenerOAsignarAngulo(Mutante mutante) {
        Double anguloGuardado = anguloPorMutante.get(mutante);
        if (anguloGuardado == null) {
            double anguloInicial = random.nextDouble() * 2 * Math.PI;
            anguloPorMutante.put(mutante, anguloInicial);
            return anguloInicial;
        }
        return anguloGuardado;
    }

    private double limitar(double valor, int maximo) {
        if (valor < 0) {
            return 0;
        }
        if (valor > maximo) {
            return maximo;
        }
        return valor;
    }
}