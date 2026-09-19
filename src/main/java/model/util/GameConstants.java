package model.util;

public final class GameConstants {

    // Evita que se instancie esta clase; es solo un contenedor de constantes.
    private GameConstants() {
    }

    // Mutante
    public static final int ENERGIA_INICIAL = 100;
    public static final int ENERGIA_MINIMA = 0;

    public static final int DEFENSA_MIN = 1;
    public static final int DEFENSA_MAX = 3;

    public static final double VELOCIDAD_MIN = 1.0;
    public static final double VELOCIDAD_MAX = 5.0;

    // PoderMutante
    public static final int DANO_MIN = 1;
    public static final int DANO_MAX = 3;

    public static final int NIVEL_PODER_INICIAL = 1;
    public static final int NIVEL_PODER_MAXIMO = 7;
    public static final int INCREMENTO_NIVEL = 1;

    // Cantidad de tipos elementales de poder disponibles en la fabrica
    // (Fuego, Hielo, Electrico). Si se agrega un nuevo tipo, se actualiza
    // este numero y PoderFactory.
    public static final int CANTIDAD_TIPOS_PODER = 3;

    // Equipo / Batalla (usadas tambien por otras capas) 
    public static final int TAMANO_EQUIPO_MIN = 3;
    public static final int TAMANO_EQUIPO_MAX = 11;
}