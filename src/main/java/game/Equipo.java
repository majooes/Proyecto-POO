package game;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import model.IdentificadorEquipo;
import model.Mutante;

/**
  Representa a uno de los dos equipos de la batalla. Se identifica con
  un color y un simbolo (Image), y guarda la lista de mutantes que le
  pertenecen. No lleva su propio marcador: el marcador es UNICO y
  compartido para toda la partida (ver CampoBatalla), ya que asi lo
  definio la clase Marcador de la capa Model (lleva vivos/muertos de
  ambos equipos en un solo objeto, identificados por IdentificadorEquipo).
 */
public class Equipo {

    private final IdentificadorEquipo identificador;
    private final Color color;
    private final Image simbolo;
    private final List<Mutante> mutantes;

    public Equipo(IdentificadorEquipo identificador, Color color, Image simbolo) {
        this.identificador = identificador;
        this.color = color;
        this.simbolo = simbolo;
        this.mutantes = new ArrayList<>();
    }

    /**
      Agrega un mutante al equipo y lo marca con el identificador de
      este equipo (A o B), para que Mutante y Marcador sepan de que
      lado esta sin depender de esta clase.
     */
    public void agregarMutante(Mutante mutante) {
        mutante.setEquipo(identificador);
        mutantes.add(mutante);
    }

    public boolean estaEliminado() {
        return contarVivos() == 0;
    }

    public int contarVivos() {
        int vivos = 0;
        for (Mutante mutante : mutantes) {
            if (mutante.estaVivo()) {
                vivos++;
            }
        }
        return vivos;
    }

    public int contarMuertos() {
        return mutantes.size() - contarVivos();
    }

    public IdentificadorEquipo getIdentificador() {
        return identificador;
    }

    public Color getColor() {
        return color;
    }

    public Image getSimbolo() {
        return simbolo;
    }

    public List<Mutante> getMutantes() {
        return mutantes;
    }

    @Override
    public String toString() {
        return "Equipo " + identificador + " [vivos=" + contarVivos() + ", muertos=" + contarMuertos() + "]";
    }
}