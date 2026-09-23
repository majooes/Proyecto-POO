package game;

/**
  Programa de prueba de la capa Game. Se apoya en Model y en Control
  (ya probadas cada una por su cuenta) para verificar que CampoBatalla,
  Equipo y MotorJuego funcionan bien juntos: crea una partida completa
  y la corre ronda a ronda hasta que haya un ganador o se agoten las
  rondas de prueba.
 */
public final class GameMain {

    private static final int TAMANO_EQUIPO_PRUEBA = 4;
    private static final int ANCHO_CAMPO = 8;
    private static final int ALTO_CAMPO = 8;
    private static final int RONDAS_MAXIMAS = 30;

    private GameMain() {
    }

    public static void main(String[] args) {
        System.out.println("PRUEBA INDEPENDIENTE DE LA CAPA GAME\n");

        CampoBatalla campoBatalla = new CampoBatalla(ANCHO_CAMPO, ALTO_CAMPO);
        campoBatalla.crearEquipos(TAMANO_EQUIPO_PRUEBA);

        System.out.println("Equipos creados:");
        System.out.println("  " + campoBatalla.getEquipoA());
        System.out.println("  " + campoBatalla.getEquipoB());

        MotorJuego motorJuego = new MotorJuego(campoBatalla, 0);

        Equipo ganador = null;
        int ronda = 1;
        while (ronda <= RONDAS_MAXIMAS && ganador == null) {
            System.out.println("\n--- Ronda " + ronda + " ---");
            motorJuego.ejecutarCiclo();
            System.out.println(campoBatalla.getMarcador());
            ganador = campoBatalla.verificarGanador();
            ronda++;
        }

        if (ganador != null) {
            System.out.println("\nGano el " + ganador);
        } else {
            System.out.println("\nNo hubo ganador dentro de las " + RONDAS_MAXIMAS + " rondas de prueba.");
        }

        motorJuego.finalizar();
        System.out.println("\n FIN DE LA PRUEBA DE LA CAPA GAME ");
    }
}