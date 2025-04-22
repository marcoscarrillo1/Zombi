import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.Semaphore;

class Tunel {
    private final int id;
    private final Semaphore acceso = new Semaphore(1);  // Controla el acceso al túnel
    private final Object lockPrioridad = new Object();  // Control de prioridad de acceso
    private int esperandoEntrar = 0;  // Contador de humanos esperando para entrar al túnel

    public Tunel(int id) {
        this.id = id;  // Asigna un identificador único al túnel
    }

    // Método para cruzar el túnel hacia fuera
    public void cruzarHaciaFuera(Humano h) throws InterruptedException {
        // Adquiere el semáforo para asegurar que solo un humano pueda cruzar a la vez
        acceso.acquire();
        Log.escribir(h.getIdh() + " está cruzando hacia fuera en el túnel " + id);
        Thread.sleep(1000);  // Simula el tiempo que tarda en cruzar el túnel
        Log.escribir(h.getIdh() + " ha cruzado hacia fuera en el túnel " + id);
        acceso.release();  // Libera el semáforo para que otro humano pueda usar el túnel
    }

    // Método para cruzar el túnel hacia dentro
    public void cruzarHaciaDentro(Humano h) throws InterruptedException {
        synchronized (lockPrioridad) {
            esperandoEntrar++;  // Incrementa el contador de espera
        }

        // Adquiere el semáforo para asegurar que solo un humano pueda cruzar a la vez
        acceso.acquire();
        Log.escribir(h.getIdh() + " está cruzando hacia dentro en el túnel " + id);
        Thread.sleep(1000);  // Simula el tiempo que tarda en cruzar el túnel
        Log.escribir(h.getIdh() + " ha cruzado hacia dentro en el túnel " + id);
        acceso.release();  // Libera el semáforo para que otro humano pueda usar el túnel

        synchronized (lockPrioridad) {
            esperandoEntrar--;  // Decrementa el contador de espera
        }
    }

    // Método para obtener el número de personas esperando para entrar
    public int getEsperandoEntrar() {
        synchronized (lockPrioridad) {
            return esperandoEntrar;
        }
    }

    // Método para obtener el identificador del túnel
    public int getId() {
        return id;
    }
}
