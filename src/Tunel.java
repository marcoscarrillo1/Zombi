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
    private Condition pasalir = cerrojo.newCondition();
    private List<Humano> esperandoEntrar = new ArrayList<>();
    private boolean grupoFormado = false;
    private List<Humano> esperandosalir = new ArrayList<>();

    private Semaphore ocupado = new Semaphore(1);  //Controla que solo halla un humano dentro.

    public Tunel(int id) {
        this.id = id;  // Asigna un identificador único al túnel
    }

    public int getId() {
        return id;
    }

    // Método para cruzar el túnel hacia fuera
    public void cruzarHaciaFuera(Humano h) {
        cerrojo.lock();
        try {
            esperandosalir.add(h);
            Log.info(h.getIdh() + " está esperando para salir en el túnel " + id);
            if (esperandosalir.size() == 3) {
                grupoFormado = true;
                if (esperandoEntrar.isEmpty()) {
                    pasalir.signalAll();
                }
            }
            while (esperandoEntrar.size() > 0 || !grupoFormado) {
                pasalir.await();
            }
            ocupado.acquire();
            Thread.sleep(1000);
            Log.info(h.getIdh() + " está cruzando hacia fuera en el túnel " + id);
            esperandosalir.remove(h);
            ocupado.release();
            if(esperandosalir.isEmpty()){
                grupoFormado= false;
            }
        } catch (InterruptedException e) {
            System.out.println("No ha podido usar el tunel");
        } finally {
            cerrojo.unlock();
        }
    }

    public void cruzarHaciaDentro(Humano h) {
        cerrojo.lock();
        try {
            esperandoEntrar.add(h);
            ocupado.acquire();
            Log.info(h.getIdh() + " está cruzando hacia dentro en el túnel " + id);
            esperandoEntrar.remove(h);
            Thread.sleep(1000);
            ocupado.release();
            if (grupoFormado && esperandoEntrar.isEmpty()) {
                pasalir.signalAll();
            }

        } catch (InterruptedException e) {
            System.out.println("No ha podido usar el tunel");
        } finally {
            cerrojo.unlock();
        }
    }


}




