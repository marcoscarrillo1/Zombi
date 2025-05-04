import java.util.*;
import java.util.concurrent.Semaphore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Humano extends Thread {
    protected String id;
    private Juegozombie juego;
    protected boolean marcado = false;
    private boolean vivo=true;
    protected Refugio refugio;
    protected String ubicacion = "Refugio"; // Valor inicial por defecto



    public Humano(String id, Juegozombie juego,Refugio refugio) {
        this.id = id;
        this.juego = juego;
        this.refugio = refugio;
        super.setName(id);
    }
    public boolean isMarcado() {
        return marcado;
    }
    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }




    public void morir() {
        this.vivo = false;
        Log.info(id + " ha muerto.");
    }




    @Override
    public void run() {
        while (vivo) {
            try {
                // 1. Zona común
                refugio.zonaComun(this);
                sleep(1000 + new Random().nextInt(1000));

                // 2. Cruzando túnel hacia afuera
                int idTunel = refugio.entrarTunelExterior(this);
                setUbicacion("Zona insegura");

                // 3. Zona insegura
                ZonaInsegura zona = refugio.explorarZonaExterior(idTunel);
                juego.entrarZriesgoH(this,idTunel);
                if (marcado) {
                    refugio.volverAlRefugio(this,idTunel,zona);
                    juego.salirZriesgoH(this,idTunel);
                }
                else{
                    int comida = zona.recolectarComida(this);


                // 4. Cruzando túnel hacia adentro
                refugio.volverAlRefugio(this,idTunel,zona);
                juego.salirZriesgoH(this,idTunel);
                refugio.agregarComida(comida);
                }
                // 5. Zona de descanso
                refugio.zonaDescanso(this);


                // 6. Comedor
                refugio.comedor(this);

                // 7. Enfermería (si está marcado)
                if (marcado) {
                    refugio.recuperarse(this);
                }


            } catch (InterruptedException e) {
                Log.info(id + " interrumpido.");
            }
        }

        // Si sale del bucle porque muere
        setUbicacion("Muerto");
    }





    // Método para matar un zombi
    public void matarZombi(Zombi zombi) {
        if (zombi != null) {
            Log.info(id + " mata al zombi " + zombi.getIdz());
            // El humano mata al zombi. Aquí puedes manejar la lógica de muerte del zombi.
            zombi.morir();
        }
    }


    // Métodos getters y setters
    public String getIdh() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isVivo() {
        return vivo;
    }
}
