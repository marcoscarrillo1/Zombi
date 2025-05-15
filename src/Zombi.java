
import java.util.*;

class Zombi extends Thread {
    private String id;
    private int muertes = 0;
    private ZonaInsegura zonaActual;
    private Refugio refugio;
    private boolean vivo = true;
    private Random rand = new Random();
    private Juegozombie juego;

    public Zombi(String  id,Juegozombie juego,Refugio refugio) {
        this.id = id;
        this.juego=juego;
        this.refugio=refugio;
        super.setName(" "+id);
    }



    // Método para incrementar las muertes del zombi
    public void incrementarMuertes() {
        muertes++;
        try {
            refugio.incrementarMuerteZombi(id);
        }catch (Exception e) {
            e.printStackTrace();
        }
        Log.info(id + " ha matado a un humano. Muertes totales: " + muertes);
    }



    @Override
    public void run() {
        while (vivo) {
            try {
                // El zombi se mueve por la zona
                juego.esperarSiPausado();
                int zona= new Random().nextInt(4);
                zonaActual = refugio.explorarZonaExterior(zona);
                juego.esperarSiPausado();
                zonaActual.entrarZ(this);
                juego.entrarZriesgoZ(this,zona);

                // El zombi busca un humano al que atacar
                juego.esperarSiPausado();
                Humano presa = zonaActual.elegirpresa();
                if (presa!=null){
                    Thread.sleep((long)(rand.nextDouble(1,3) * 500)); // Espera aleatoria entre 1 y 4 segundos
                    juego.esperarSiPausado();
                    atacarHumano(presa);
                    juego.esperarSiPausado();
                    Log.info(id + " ha atacado a: "+presa.getIdh());
                }
                // Simula el tiempo de espera entre ataques
                Thread.sleep((long)(rand.nextDouble(2,3) * 1000));
                juego.esperarSiPausado();
                zonaActual.salirZ(this);
                juego.salirZriesgoZ(this,zona);
                juego.esperarSiPausado();

            } catch (InterruptedException e) {
                Log.info(id + " ha sido interrumpido.");
            }
        }
    }

    // Método para atacar a un humano y convertirlo en zombi
    public void atacarHumano(Humano h) {
        double atacque = rand.nextDouble(1);
        if (atacque < 0.66){
            String id = h.getIdh().substring(1);
            String zombiNuevo = "Z" +id;
            h.morir();
            this.incrementarMuertes();
            Zombi trans = new Zombi(zombiNuevo,this.juego,this.refugio);
            trans.start();
        }else {
            h.setMarcado(true);
        }

    }




    public String getIdz() {
        return id;
    }
}
