import java.util.*;
import java.util.concurrent.Semaphore;
package zombie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Zombi extends Thread {
    private int  id;
    private int muertes = 0;
    private ZonaInsegura zonaActual;
    private boolean vivo = true;

    private static final List<Zombi> zombisActivos = Collections.synchronizedList(new ArrayList<>());

    public Zombi(int  id) {
        this.id = id;
        zombisActivos.add(this);
    }

    public static int getZombisActivos() {
        return zombisActivos.size();
    }

    public static List<Zombi> getZombis() {
        return zombisActivos;
    }

    // Método para incrementar las muertes del zombi
    public void incrementarMuertes() {
        muertes++;
        Log.escribir(id + " ha matado a un humano. Muertes totales: " + muertes);
    }

    // Método que renace un humano como zombi
    public void renacerHumano(String idHumano) {
        Log.escribir(id + " ha convertido a " + idHumano + " en zombi.");
        Zombi zombiRenacido = new Zombi(idHumano); // Humano renace con el mismo ID pero como zombi
        zombiRenacido.start();
    }

    @Override
    public void run() {
        while (vivo) {
            try {
                // El zombi se mueve por la zona
                zonaActual = ZonaInsegura.elegirAleatoria();
                zonaActual.moverZombi(this);
                // El zombi busca un humano al que atacar
                atacarHumano();
                // Simula el tiempo de espera entre ataques
                Thread.sleep((long)(Math.random() * 3000 + 1000)); // Espera aleatoria entre 1 y 4 segundos
            } catch (InterruptedException e) {
                Log.escribir(id + " ha sido interrumpido.");
            }
        }
    }

    // Método para atacar a un humano y convertirlo en zombi
    public void atacarHumano() {
        if (humano) {
            // Suponemos que el zombi elige aleatoriamente un humano para atacar
            Humano humanoAtacado = elegirHumanoAleatorio();
            if (humanoAtacado != null) {
                Log.escribir(id + " ha atacado al humano " + humanoAtacado.getIdh());
                humanoAtacado.morir(); // El humano muere
                incrementarMuertes(); // El zombi incrementa su número de muertes
                renacerHumano(humanoAtacado.getIdh()); // El humano se convierte en zombi
            }
        }
    }

    // Método para elegir un humano aleatorio
    private Humano elegirHumanoAleatorio() {
        List<Humano> vivos = new ArrayList<>(Humano.humanosVivos);
        if (vivos.isEmpty()) {
            return null;
        }
        return vivos.get((int)(Math.random() * vivos.size())); // Elige un humano aleatorio
    }

    // Método para matar a un zombi
    public void morir() {
        this.vivo = false;
        Log.escribir(id + " ha muerto.");
        zombisActivos.remove(this); // El zombi muere y se elimina de la lista de zombis activos
    }

    public String getIdz() {
        return id;
    }

    public int getMuertes() {
        return muertes;
    }
}
