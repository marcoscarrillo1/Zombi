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

    private  List<Humano> humanos = new ArrayList<>();
    private Lock cerrojo=new ReentrantLock();
    private final int id;
    private Random random=new Random();

    public ZonaInsegura(int id) {
        this.id = id;// Añade la zona a la lista global de zonas
    }
    public synchronized void entrar(Humano h) {
        humanos.add(h);  // Añade al humano a la lista de humanos en la zona
    }

    public Humano elegirpresa() {
        cerrojo.lock();
        try {
            if (!humanos.isEmpty()) {
                int elegir = random.nextInt(humanos.size());
                Humano presa = humanos.get(elegir);
                humanos.remove(elegir);
                return presa;
            }

        } catch (Exception e) {
            System.out.println("NO se ha elegio bien");
        } finally {
            cerrojo.unlock();
        }
        return null;
    }
    public void eliminarHumano(Humano h) {
        cerrojo.lock();
        try{
            if(humanos.contains(h)){
                humanos.remove(h);

            }
        } catch (Exception e) {
            System.out.println("NO se ha eliminado");
        }
        finally {
            cerrojo.unlock();
        }
    }
    public void añadirhumano(Humano h){
        cerrojo.lock();
        try{
            humanos.add(h);

        }catch (Exception e){

        }finally{
            cerrojo.unlock();
        }
    }










    // Método sincronizado para que un humano salga de la zona
    public synchronized void salir(Humano h) {
        humanos.remove(h);  // Elimina al humano de la lista de humanos en la zona
    }

    // Método para recolectar comida (simulado) en la zona
    public int recolectarComida(Humano h) throws InterruptedException {
        entrar(h);  // El humano entra a la zona para recolectar comida
        Log.info(h.getIdh() + " ha entrado en la zona exterior " + id);
        Thread.sleep(3000 + new Random().nextInt(2000));  // Simula el tiempo que tarda en recolectar comida
        int comidaRecolectada = 1 + new Random().nextInt(3);
        Log.info(h.getIdh() + " ha recolectado " + comidaRecolectada + " unidades de comida.");
        return comidaRecolectada;
    }

    // Métodos adicionales para obtener información sobre la zona
    public List<Humano> getHumanos() {
        return humanos;
    }


    public int getId() {
        return id;
    }
}
