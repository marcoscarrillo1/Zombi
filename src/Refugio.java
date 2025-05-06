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
    private ArrayList<Humano> humanosDescansando = new ArrayList<>();
    private ListaHilos humanodescansa;
    private ArrayList<Humano> humanosComedor = new ArrayList<>();
    private ArrayList<Humano> humanosEnfermeria = new ArrayList<>();
    private ArrayList<Humano> humanosZonaComun = new ArrayList<>();
    private Juegozombie juego;

    public  int getComidaDisponible() {
        return comidaDisponible;
    }

    public void setJuego(Juegozombie juego) {
        this.juego = juego;
    }

    public ArrayList<Humano> getHumanosDescansando() {
        return humanosDescansando;
    }

    public void setHumanosDescansando(ArrayList<Humano> humanosDescansando) {
        this.humanosDescansando = humanosDescansando;
    }

    public ArrayList<Humano> getHumanosComedor() {
        return humanosComedor;
    }

    public void setHumanosComedor(ArrayList<Humano> humanosComedor) {
        this.humanosComedor = humanosComedor;
    }

    public ArrayList<Humano> getHumanosEnfermeria() {
        return humanosEnfermeria;
    }

    public void setHumanosEnfermeria(ArrayList<Humano> humanosEnfermeria) {
        this.humanosEnfermeria = humanosEnfermeria;
    }

    public ArrayList<Humano> getHumanosZonaComun() {
        return humanosZonaComun;
    }

    public void setHumanosZonaComun(ArrayList<Humano> humanosZonaComun) {
        this.humanosZonaComun = humanosZonaComun;
    }

    private static int comidaDisponible = 0;

    public Refugio(List<ListaHilos> irtuneles, List<ListaHilos> dentrotuneles, List<ListaHilos> volvertuneles) {
        for (int i = 0; i < 4; i++) {
            tuneles[i] = new Tunel(i, irtuneles.get(i), dentrotuneles.get(i), volvertuneles.get(i));
            zonas[i] = new ZonaInsegura(i);
        }
    }

    public  void zonaComun(Humano h) throws InterruptedException {
        h.setUbicacion("Zona común");
        synchronized(humanosZonaComun){
            humanosZonaComun.add(h);
            juego.entrarZcomun(h);
        }
        Log.info(h.getIdh() + " está en la zona común.");
    }

    public int entrarTunelExterior(Humano h) throws InterruptedException {
        synchronized (humanosZonaComun){
            humanosZonaComun.remove(h);
            juego.salirZcomun(h);
        }

        h.setUbicacion("Túnel (saliendo)");
        Tunel tunel = tuneles[new Random().nextInt(4)];
        tunel.cruzarHaciaFuera(h);
        Log.info(h.getIdh() + " ha cruzado hacia el exterior en el túnel " + tunel.getId());
        return tunel.getId();
    }

    public ZonaInsegura explorarZonaExterior(int idZona) throws InterruptedException {
        ZonaInsegura zona = zonas[idZona];
        return zona;
    }

    public void volverAlRefugio(Humano h, int idTunel, ZonaInsegura zona) throws InterruptedException {
        Tunel tunel = tuneles[idTunel];
        h.setUbicacion("Túnel (entrando)");
        tunel.cruzarHaciaDentro(h);
        zona.salir(h);
        Log.info(h.getIdh() + " ha vuelto al refugio a través del túnel " + tunel.getId());

    }

    public void zonaDescanso(Humano h) throws InterruptedException {
        h.setUbicacion("Zona descanso");
        synchronized (humanosDescansando) {
            humanosDescansando.add(h);
            juego.entrarDescanso(h);
        }
        Log.info(h.getIdh() + " está descansando.");
        h.sleep(2000 + new Random().nextInt(2000));
        synchronized (humanosDescansando) {
            humanosDescansando.remove(h);
            juego.salirDescanso(h);
        }
    }

    public void comedor(Humano h) throws InterruptedException {
        h.setUbicacion("Comedor");
        humanosComedor.add(h);
        juego.entrarZcomedor(h);

        try {
            lockComida.lock();
            while (comidaDisponible == 0) {
                vacio.await();
            }
            if (consumirComida(1)) {
                Thread.sleep(3000 + new Random().nextInt(2000));
                humanosComedor.remove(h);
                juego.salirZcomedor(h);
            } else {
                Log.info(h.getIdh() + " no pudo comer porque no hay comida.");
            }
        } finally {
            lockComida.unlock();
        }
    }

    public  void recuperarse(Humano h) throws InterruptedException {
        h.setUbicacion("Enfermería");
        synchronized(humanosEnfermeria){
            humanosEnfermeria.add(h);
        }
        Thread.sleep(3000 + new Random().nextInt(2000));
        h.setMarcado(false);
        Log.info(h.getIdh() + " se ha recuperado.");
        synchronized (humanosEnfermeria){
            humanosEnfermeria.remove(h);
        }
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
            Log.info("Se han agregado " + cantidad + " unidades de comida al refugio. Total: " + comidaDisponible);
        } finally {
            lockComida.unlock();
        }

    }

    public boolean consumirComida(int cantidad) {
        if (comidaDisponible >= cantidad) {
            comidaDisponible -= cantidad;
            Log.info("Se han consumido " + cantidad + " unidades de comida. Restante: " + comidaDisponible);
            return true;
        } else {
            return false;
        }
    }
}