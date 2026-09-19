package model;

/**
* Identifica si un mutante o el Marcador es del equipo A o del B.
 * <p>
 * Lo definimos en la capa Model para separar responsabilidades. Así, 
 * Mutante y Marcador no necesitan conocer la clase real {@code game.Equipo}. 
 * El modelo solo necesita saber "de qué lado" está el mutante, y dejamos 
 * que la capa Game decida cómo se ve o se gestiona cada equipo internamente.
 */
public enum IdentificadorEquipo {
    A,
    B
}