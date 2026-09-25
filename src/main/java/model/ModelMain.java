package model;

/**
 * Prueba exclusiva de la capa Model.
 * Simula interacciones (ataques, movimientos y uso de poder) de forma manual 
 * para verificar la lógica del juego sin usar interfaz gráfica, hilos 
 * ni depender de otras capas.
 */
public final class ModelMain {

    private ModelMain() {
    }

    public static void main(String[] args) {
        System.out.println("PRUEBA INDEPENDIENTE DE LA CAPA MODEL\n");

        // 1. Creacion de mutantes, cada uno identificado con su equipo (A o B)
        Mutante atacante = new Mutante("Key", 1, new Posicion(0, 0), 2.0);
        atacante.setEquipo(IdentificadorEquipo.A);

        Mutante defensor = new Mutante("Nacho", 3, new Posicion(5, 0), 1.5);
        defensor.setEquipo(IdentificadorEquipo.B);

        atacante.asignarPoder(PoderFactory.crearPoderAleatorio("Garra de Key"));
        defensor.asignarPoder(PoderFactory.crearPoderAleatorio("Escudo de Nacho"));

        System.out.println("Mutante 1: " + atacante + " | equipo=" + atacante.getEquipo());
        System.out.println("  Poder  : " + atacante.getPoder());
        System.out.println("Mutante 2: " + defensor + " | equipo=" + defensor.getEquipo());
        System.out.println("  Poder  : " + defensor.getPoder());

        // 2. Prueba de movimiento
        System.out.println("\n Prueba de movimiento");
        System.out.println("Posicion antes de moverse: " + atacante.getPosicion());
        atacante.mover(new Posicion(4.0, 0.0));
        System.out.println("Posicion despues de moverse: " + atacante.getPosicion());
        double distancia = atacante.getPosicion().calcularDistancia(defensor.getPosicion());
        System.out.printf("Distancia entre ambos mutantes: %.2f%n", distancia);

        // 3. Encuentro sin defensa: el atacante golpea de lleno
        System.out.println("\n  Encuentro 1: defensor NO se defiende ");
        int energiaAntes = defensor.getEnergia();
        atacante.atacar(defensor);
        System.out.println("Energia de " + defensor.getNombre() + " antes: " + energiaAntes + " -> despues: " + defensor.getEnergia());
        System.out.println("Nivel del poder del atacante tras golpear: " + atacante.getPoder().getNivel());

        // 4. Encuentro con defensa: el dano se divide entre la defensa del defensor
        System.out.println("\n Encuentro 2: defensor SI se defiende ");
        defensor.defenderse();
        energiaAntes = defensor.getEnergia();
        atacante.atacar(defensor);
        System.out.println("Energia de " + defensor.getNombre() + " antes: " + energiaAntes + " -> despues: " + defensor.getEnergia());

        // 5. Forzar varios golpes para comprobar que el poder no supera nivel 7
        System.out.println("\n Prueba de tope de nivel del poder (maximo 7)");
        for (int i = 0; i < 10; i++) {
            atacante.atacar(defensor);
        }
        System.out.println("Nivel final del poder del atacante: " + atacante.getPoder().getNivel()
                + " (debe ser <= 7)");

        // 6. Forzar la muerte del defensor a base de dano y verificar Marcador
        System.out.println("\n Prueba de muerte y Marcador ");
        Marcador marcador = new Marcador();
        marcador.setVivosA(1);
        marcador.setVivosB(1);

        while (defensor.estaVivo()) {
            atacante.atacar(defensor);
        }
        System.out.println(defensor.getNombre() + " vivo? " + defensor.estaVivo()
                + " (energia=" + defensor.getEnergia() + ")");

        marcador.registrarMuerte(IdentificadorEquipo.B);
        marcador.actualizar(marcador.getVivosA(), marcador.getVivosB(),
        marcador.getMuertosA(), marcador.getMuertosB());
        System.out.println(marcador);
        System.out.println("Ganador parcial segun el marcador: " + marcador.getGanadorParcial());

        System.out.println("\n FIN DE LA PRUEBA DE LA CAPA MODEL ");
    }
}