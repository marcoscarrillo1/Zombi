import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Tunel {
    private final int id;
    private Lock cerrojo = new ReentrantLock();  // Controla el acceso al túnel
    private Condition paentrar = cerrojo.newCondition();
    private Condition pasalir = cerrojo.newCondition();
    private int esperandoEntrar = 0;
    private int esperandosalir = 0;
    private boolean haygente = false;// Contador de humanos esperando para entrar al túnel

    public Tunel(int id) {
        this.id = id;  // Asigna un identificador único al túnel
    }

    // Método para cruzar el túnel hacia fuera
    public void cruzarHaciaFuera(Humano h) {
        cerrojo.lock();
        try {
            esperandosalir++;
            while (haygente || esperandoEntrar > 0) {
                pasalir.await();
            }
            Log.escribir(h.getIdh() + " está cruzando hacia fuera en el túnel " + id);
            haygente = true;
            esperandosalir--;

        } catch (InterruptedException e) {
            System.out.println("No ha podido usar el tunel");
        } finally {
            cerrojo.unlock();
        }
    }

    public void cruzarHaciaDentro(Humano h) {
        cerrojo.lock();
        try {
            esperandoEntrar++;
            while (haygente) {
                paentrar.await();
            }
            Log.escribir(h.getIdh() + " está cruzando hacia dentro en el túnel " + id);
            haygente = true;
            esperandoEntrar--;

        } catch (InterruptedException e) {
            System.out.println("No ha podido usar el tunel");
        } finally {
            cerrojo.unlock();
        }
    }

    public void QuePaseELSiguiente() {
        cerrojo.lock();
        try {
            haygente = false;
            if (esperandoEntrar > 0) {
                paentrar.signal();
            } else {
                pasalir.signal();
            }
        } catch (Exception e) {
            System.out.println("No ha podido usar el tunel");
        } finally {
            cerrojo.unlock();
        }
    }
}




