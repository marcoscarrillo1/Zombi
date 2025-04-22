import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Semaphore;

import java.util.Random;

class Refugio {
    private Tunel[] tuneles = new Tunel[4];
    private ZonaInsegura[] zonas = new ZonaInsegura[4];
    private static int comidaDisponible = 0;

    public Refugio() {
        for (int i = 0; i < 4; i++) {
            tuneles[i] = new Tunel(i);
            zonas[i] = new ZonaInsegura(i);
        }
    }

    public synchronized void zonaComun(Humano h) throws InterruptedException {
        Thread.sleep(1000 + new Random().nextInt(1000));
        Log.escribir(h.getIdh() + " está en la zona común.");
    }

    public synchronized void entrarTunelExterior(Humano h) throws InterruptedException {
        Tunel tunel = tuneles[new Random().nextInt(4)];
        tunel.cruzarHaciaFuera(h);
        Log.escribir(h.getIdh() + " ha cruzado hacia el exterior en el túnel " + tunel.getId());
    }

    public synchronized ZonaInsegura explorarZonaExterior(Humano h) throws InterruptedException {
        ZonaInsegura zona = zonas[new Random().nextInt(4)];
        zona.entrar(h);
        Log.escribir(h.getIdh() + " ha entrado en la zona exterior " + zona.getId());

        // Simulamos que recoge entre 1 y 3 unidades de comida
        int comidaRecolectada = 1 + new Random().nextInt(3);
        agregarComida(comidaRecolectada);
        Log.escribir(h.getIdh() + " ha recolectado " + comidaRecolectada + " unidades de comida.");

        return zona;
    }

    public synchronized void volverAlRefugio(Humano h) throws InterruptedException {
        Tunel tunel = tuneles[new Random().nextInt(4)];
        tunel.cruzarHaciaDentro(h);
        Log.escribir(h.getIdh() + " ha vuelto al refugio a través del túnel " + tunel.getId());
    }

    public synchronized void zonaDescanso(Humano h) throws InterruptedException {
        Thread.sleep(2000 + new Random().nextInt(2000));
        Log.escribir(h.getIdh() + " está descansando.");
    }

    public synchronized void comedor(Humano h) throws InterruptedException {
        if (consumirComida(1)) {
            Thread.sleep(3000 + new Random().nextInt(2000));
            Log.escribir(h.getIdh() + " está comiendo.");
        } else {
            Log.escribir(h.getIdh() + " no pudo comer porque no hay comida.");
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

    public synchronized void agregarComida(int cantidad) {
        comidaDisponible += cantidad;
        Log.escribir("Se han agregado " + cantidad + " unidades de comida al refugio. Total: " + comidaDisponible);
    }

    public synchronized boolean consumirComida(int cantidad) {
        if (comidaDisponible >= cantidad) {
            comidaDisponible -= cantidad;
            Log.escribir("Se han consumido " + cantidad + " unidades de comida. Restante: " + comidaDisponible);
            return true;
        } else {
            return false;
        }
    }
}
