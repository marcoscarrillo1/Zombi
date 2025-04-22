import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ZonaInsegura {
    private static final List<ZonaInsegura> zonas = new ArrayList<>();
    private final List<Humano> humanos = new ArrayList<>();
    private final List<Zombi> zombis = new ArrayList<>();
    private final int id;

    public ZonaInsegura(int id) {
        this.id = id;
        zonas.add(this);  // Añade la zona a la lista global de zonas
    }

    // Método estático para elegir una zona aleatoria
    public static ZonaInsegura elegirAleatoria() {
        Random rand = new Random();
        return zonas.get(rand.nextInt(zonas.size()));  // Selecciona una zona aleatoria
    }

    // Método sincronizado para mover un zombi a esta zona
    public synchronized void moverZombi(Zombi z) {
        zombis.add(z);  // Añade el zombi a la zona
        if (!humanos.isEmpty()) {  // Si hay humanos en la zona
            Humano objetivo = humanos.get(new Random().nextInt(humanos.size()));  // Selecciona un humano aleatorio
            Log.escribir(z.getIdz() + " ataca a " + objetivo.getIdh());  // Registra el ataque
            // Lógica de ataque simplificada, podrías agregar más detalle aquí
        }
        try {
            Thread.sleep(2000 + new Random().nextInt(1000));  // Simula el tiempo que tarda en moverse
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Maneja la interrupción correctamente
        }
        zombis.remove(z);  // El zombi sale de la zona después de su acción
    }

    // Método sincronizado para que un humano entre a la zona
    public synchronized void entrar(Humano h) {
        humanos.add(h);  // Añade al humano a la lista de humanos en la zona
    }

    // Método sincronizado para que un humano salga de la zona
    public synchronized void salir(Humano h) {
        humanos.remove(h);  // Elimina al humano de la lista de humanos en la zona
    }

    // Método para recolectar comida (simulado) en la zona
    public void recolectarComida(Humano h) throws InterruptedException {
        entrar(h);  // El humano entra a la zona para recolectar comida
        Thread.sleep(3000 + new Random().nextInt(2000));  // Simula el tiempo que tarda en recolectar comida
        salir(h);  // El humano sale de la zona después de recolectar la comida
    }

    // Métodos adicionales para obtener información sobre la zona
    public List<Humano> getHumanos() {
        return humanos;
    }

    public List<Zombi> getZombis() {
        return zombis;
    }

    public int getId() {
        return id;
    }
}
