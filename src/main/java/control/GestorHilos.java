package control;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
  Administra el pool de hilos (ExecutorService) que resuelve, en
  paralelo, todos los encuentros de combate detectados en una ronda.
  Esto es lo que permite que varios pares de mutantes peleen al mismo
  tiempo sin bloquearse entre si.
 */
public class GestorHilos {

    private final ExecutorService executor;
    private final List<Future<?>> futurosPendientes;

    public GestorHilos(int numHilos) {
        this.executor = Executors.newFixedThreadPool(numHilos);
        this.futurosPendientes = new ArrayList<>();
    }

    /**
      Envia un HiloCombate al pool por cada encuentro detectado. No
      espera a que terminen; para eso esta esperarFinalizacion().
     */
    public void ejecutarCombates(List<ParCombate> encuentros) {
        for (ParCombate encuentro : encuentros) {
            Future<?> futuro = executor.submit(new HiloCombate(encuentro));
            futurosPendientes.add(futuro);
        }
    }

    /**
      Bloquea hasta que todos los combates enviados en esta ronda hayan
      terminado, para que la siguiente ronda (movimiento) no arranque
      con combates todavia sin resolver.
     */
    public void esperarFinalizacion() {
        for (Future<?> futuro : futurosPendientes) {
            try {
                futuro.get();
            } catch (InterruptedException excepcionInterrupcion) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException excepcionEjecucion) {
                excepcionEjecucion.printStackTrace();
            }
        }
        futurosPendientes.clear();
    }

    /**
      Apaga el pool de hilos. Se llama una sola vez, al terminar la
      partida por completo.
     */
    public void apagar() {
        executor.shutdown();
    }
}