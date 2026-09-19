package model;

/**
 * Representa el estado actual de la batalla. Lo usa principalmente la capa
 * Game (CampoBatalla), pero se define en el modelo porque describe un
 * porque es un concepto de dominio no algo visual
 */
public enum EstadoBatalla {
    EN_CURSO,
    PAUSADA,
    TERMINADA
}