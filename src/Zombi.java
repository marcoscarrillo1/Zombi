
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Zombi extends Thread {
    private String id;
    private int muertes = 0;
    private ZonaInsegura zonaActual;
    private Refugio refugio;
    private boolean vivo = true;
    private Random rand = new Random();


    public Zombi(String  id,Refugio refugio) {
        this.id = id;
        refugio = this.refugio;
    }



    // Método para incrementar las muertes del zombi
    public void incrementarMuertes() {
        muertes++;
        Log.info(id + " ha matado a un humano. Muertes totales: " + muertes);
    }


    @Override
    public void run() {
        while (vivo) {
            try {
                // El zombi se mueve por la zona
                zonaActual = refugio.explorarZonaExterior(rand.nextInt(4)-1);
                // El zombi busca un humano al que atacar
                Humano presa = zonaActual.elegirpresa();
                if (presa!=null){
                    Thread.sleep((long)(rand.nextDouble(1,3) * 500)); // Espera aleatoria entre 1 y 4 segundos
                    atacarHumano(presa);
                    Log.info(id + " ha atacado a: "+ presa.getIdh());
                }
                // Simula el tiempo de espera entre ataques
                Thread.sleep((long)(rand.nextDouble(2,3) * 1000));

            } catch (InterruptedException e) {
                Log.info(id + " ha sido interrumpido.");
            }
        }
    }

    // Método para atacar a un humano y convertirlo en zombi
    public void atacarHumano(Humano h) {
        double atacque = rand.nextDouble(1);
        if (atacque < 0.66){
            String id = h.getIdh();
            String zombiNuevo = 'Z' +id.substring(1);
            h.morir();
            this.incrementarMuertes();
            Zombi trans = new Zombi(zombiNuevo,this.refugio);
            trans.run();
        }else {
            h.setMarcado(true);
            zonaActual.añadirhumano(h);
            h.matarZombi(this);
        }

    }



    // Método para matar a un zombi
    public void morir() {
        this.vivo = false;
        Log.info(id + " ha muerto.");

    }

    public String getIdz() {
        return id;
    }

    public int getMuertes() {
        return muertes;
    }
}
