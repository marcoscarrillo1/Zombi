import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class Tunel {
    private  int id;
    private Lock cerrojo = new ReentrantLock();  // Controla el acceso al túnel
    private Condition pasalir = cerrojo.newCondition();
    private Condition paentrar = cerrojo.newCondition();
    private List<Humano> esperandoEntrar = new ArrayList<>();
    private int esperadentro=0;
    private int esperanalir=0;
    private boolean hayalguien=false;
    private boolean grupoFormado = false;
    private List<Humano> esperandosalir = new ArrayList<>();
    private Humano humanoDentro = null;
    private ListaHilos irTunel;       // Lado izquierdo (esperando salir)
    private ListaHilos dentroTunel;   // Centro (cruzando)
    private ListaHilos volverTunel;
    private CyclicBarrier barreraSalida = new CyclicBarrier(3);
    private Juegozombie juego;
    private Semaphore ocupado = new Semaphore(1);  //Controla que solo halla un humano dentro.


    public void setJuego(Juegozombie juego) {
        this.juego = juego;
    }

    public Humano getHumanoDentro() {
        return humanoDentro;
    }

    public Lock getCerrojo() {
        return cerrojo;
    }

    public void setCerrojo(Lock cerrojo) {
        this.cerrojo = cerrojo;
    }

    public List<Humano> getEsperandoEntrar() {
        return esperandoEntrar;
    }

    public void setEsperandoEntrar(List<Humano> esperandoEntrar) {
        this.esperandoEntrar = esperandoEntrar;
    }

    public List<Humano> getEsperandosalir() {
        return esperandosalir;
    }

    public void setEsperandosalir(List<Humano> esperandosalir) {
        this.esperandosalir = esperandosalir;
    }


    public Tunel(int id, ListaHilos ir, ListaHilos dentro, ListaHilos volver,Juegozombie juego) {
        this.id = id;
        this.irTunel = ir;
        this.dentroTunel = dentro;
        this.volverTunel = volver;
        this.juego = juego;
    }
    public int getTotalHumanos() {
        return dentroTunel.getIds().size() + dentroTunel.getIds().size() + volverTunel.getIds().size(); // o lo que corresponda
    }

    // Asigna un identificador único al túnel


    public int getId() {
        return id;
    }


    // Método para cruzar el túnel hacia fuera
    public void cruzarHaciaFuera(Humano h) {
        cerrojo.lock();
        try {
            esperandosalir.add(h);
            irTunel.añadir(h);
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
            humanoDentro=h;
            irTunel.fuera(h);
            dentroTunel.añadir(h);
            juego.esperarSiPausado();
            Thread.sleep(1000);
            juego.esperarSiPausado();
            Log.info(h.getIdh() + " está cruzando hacia fuera en el túnel " + id);
            esperandosalir.remove(h);
            dentroTunel.fuera(h);
            humanoDentro=null;
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
            volverTunel.añadir(h);
            esperandoEntrar.add(h);
            Thread.sleep(500);
            ocupado.acquire();
            humanoDentro=h;
            volverTunel.fuera(h);
            dentroTunel.añadir(h);
            Log.info(h.getIdh() + " está cruzando hacia dentro en el túnel " + id);
            esperandoEntrar.remove(h);
            juego.esperarSiPausado();
            Thread.sleep(1000);
            juego.esperarSiPausado();
            dentroTunel.fuera(h);
            humanoDentro=null;
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
    public synchronized void insertarHdentro(Humano h){
        volverTunel.añadir(h);
    }

}




