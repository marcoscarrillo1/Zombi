import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ZonaInsegura {

    private final List<Humano> humanos = new ArrayList<>();
    private final List<Zombi> zombies = new ArrayList<>();
    private final int id;
    private final Random random = new Random();

    public ZonaInsegura(int id) {
        this.id = id;
    }

    public synchronized void entrar(Humano h) {
        humanos.add(h);
    }

    public synchronized void entrarZ(Zombi z) {
        zombies.add(z);
    }

    public synchronized void salir(Humano h) {
        humanos.remove(h);
    }

    public synchronized void salirZ(Zombi z) {
        zombies.remove(z);
    }

    public synchronized int getHumans() {
        return humanos.size();
    }

    public synchronized int getZombies() {
        return zombies.size();
    }

    public synchronized Humano elegirpresa() {
        if (!humanos.isEmpty()) {
            int elegir = random.nextInt(humanos.size());
            return humanos.remove(elegir);
        }
        return null;
    }

    public int recolectarComida(Humano h) throws InterruptedException {
        entrar(h);
        Log.info(h.getIdh() + " ha entrado en la zona exterior " + id);
        Thread.sleep(3000 + new Random().nextInt(2000));
        int comidaRecolectada = 1 + new Random().nextInt(3);
        Log.info(h.getIdh() + " ha recolectado " + comidaRecolectada + " unidades de comida.");
        return comidaRecolectada;
    }

}