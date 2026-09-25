package ui;
/* 
* Interfaz del patrón Observer para actualizar componentes visuales (canvas, marcador).
 * Las clases que la implementan deben registrarse en {@link SujetoBatalla}.
 * <p>
 * El método de notificación no recibe parámetros. El observador es responsable de 
 * consultar los datos necesarios directamente al modelo (SujetoBatalla/CampoBatalla), 
 * garantizando que la UI no implemente lógica del juego.
*/
public interface ObservadorBatalla {

    void actualizar();
}