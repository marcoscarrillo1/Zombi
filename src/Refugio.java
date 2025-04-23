import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Semaphore;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Refugio {
    private Tunel[] tuneles = new Tunel[4];
    private ZonaInsegura[] zonas = new ZonaInsegura[4];
    private Lock lockComida = new ReentrantLock();
    private Condition vacio = lockComida.newCondition();

    private static int comidaDisponible = 0;

    public Refugio() {
        for (int i = 0; i < 4; i++) {
            tuneles[i] = new Tunel(i);
            zonas[i] = new ZonaInsegura(i);
        }
    }

    public synchronized void zonaComun(Humano h) throws InterruptedException {
        h.setUbicacion("Zona común");
        Log.escribir(h.getIdh() + " está en la zona común.");
    }

    public synchronized int entrarTunelExterior(Humano h) throws InterruptedException {
        h.setUbicacion("Túnel (saliendo)");
        Tunel tunel = tuneles[new Random().nextInt(4)];
        tunel.cruzarHaciaFuera(h);
        Log.escribir(h.getIdh() + " ha cruzado hacia el exterior en el túnel " + tunel.getId());
        return tunel.getId();
    }

    public synchronized ZonaInsegura explorarZonaExterior(int idZona) throws InterruptedException {
        ZonaInsegura zona = zonas[idZona];
        return zona;
    }

    public synchronized void volverAlRefugio(Humano h,int idTunel,ZonaInsegura zona) throws InterruptedException {
        Tunel tunel = tuneles[idTunel];
        h.setUbicacion("Túnel (entrando)");
        tunel.cruzarHaciaDentro(h);
        zona.salir(h);
        Log.escribir(h.getIdh() + " ha vuelto al refugio a través del túnel " + tunel);

    }

    public void zonaDescanso(Humano h) throws InterruptedException {
        h.setUbicacion("Zona descanso");
        Log.escribir(h.getIdh() + " está descansando.");
    }

    public void comedor(Humano h) throws InterruptedException {
        h.setUbicacion("Comedor");
        try {
            lockComida.lock();
            while (comidaDisponible == 0) {
                vacio.await();
            }
            if (consumirComida(1)) {
                Thread.sleep(3000 + new Random().nextInt(2000));
            } else {
                Log.escribir(h.getIdh() + " no pudo comer porque no hay comida.");
            }
        }finally {
            lockComida.unlock();
        }
    }

    public synchronized void recuperarse(Humano h) throws InterruptedException {
        Thread.sleep(3000 + new Random().nextInt(2000));
        Log.escribir(h.getIdh() + " se ha recuperado.");
    }

    // --- NUEVAS FUNCIONES DE COMIDA ---

    public static synchronized int getCantidadComida() {
        return comidaDisponible;
    }

    public void agregarComida(int cantidad) {
        try {
            lockComida.lock();

            if (comidaDisponible == 0) {
                comidaDisponible += cantidad;
                for (int y = 0; y < cantidad; ++y) {
                    vacio.signal();
                }
            } else {
                comidaDisponible += cantidad;
            }
            Log.escribir("Se han agregado " + cantidad + " unidades de comida al refugio. Total: " + comidaDisponible);
        }finally {
            lockComida.unlock();
        }

    }

    public boolean consumirComida(int cantidad) {
        if (comidaDisponible >= cantidad) {
            comidaDisponible -= cantidad;
            Log.escribir("Se han consumido " + cantidad + " unidades de comida. Restante: " + comidaDisponible);
            return true;
        } else {
            return false;
        }
    }
}
