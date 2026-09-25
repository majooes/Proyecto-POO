package ui.util;

import java.awt.Color;
import java.awt.Font;

/**
*  Constantes exclusivas de la capa visual (colores, dimensiones, comandos de botones, etc.).
 * <p>
 * Se separan intencionalmente de {@code model.util.GameConstants} para mantener la independencia de las capas. 
 * Aunque la interfaz puede leer las constantes del modelo compartido
 * todos los valores puramente estéticos y de configuración de la UI deben definirse únicamente aquí.
 */
public final class UIConstants {

    private UIConstants() {
    }

    //Ventana
    public static final String TITULO_VENTANA = "Mutant Battle";
    public static final int ANCHO_VENTANA = 800;
    public static final int ALTO_CANVAS = 600;
    public static final int ALTO_PANEL_MARCADOR = 60;

    //Dimensiones logicas del campo de batalla
    // Estas son las que se le pasan al constructor de CampoBatalla(ancho, alto);
    // la UI las escala a pixeles de pantalla al momento de dibujar.
    public static final int ANCHO_CAMPO_LOGICO = 28;
    public static final int ALTO_CAMPO_LOGICO = 21;

    //Refresco
    // Cada cuantos ms el Timer de Swing (Observer/Subject de la UI) repinta
    // la vista con el estado mas reciente del campo de batalla.
    public static final int REFRESCO_MS_DEFECTO = 100;
    // Cada cuantos ms el MotorJuego (capa Game) ejecuta un ciclo de juego
    // (mover + detectar + combatir). Se le pasa tal cual a su constructor.
    public static final int REFRESCO_MOTOR_MS = 150;

    //Dibujo
    public static final int RADIO_MUTANTE_PX = 10;
    public static final int ESPACIADO_MARCADOR = 20;

    public static final Color COLOR_FONDO_CAMPO = new Color(20, 20, 30);
    public static final Color COLOR_FONDO_MARCADOR = new Color(10, 10, 15);

    public static final Font FUENTE_ETIQUETA = new Font("SansSerif", Font.PLAIN, 10);
    public static final Font FUENTE_MARCADOR = new Font("SansSerif", Font.BOLD, 14);
    public static final Font FUENTE_GANADOR = new Font("SansSerif", Font.BOLD, 18);

    //Comandos de botones (Controller)
    public static final String COMANDO_INICIAR = "INICIAR_BATALLA";
    public static final String COMANDO_NUEVA_PARTIDA = "NUEVA_PARTIDA";
}