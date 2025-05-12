import java.util.*;
import java.util.concurrent.Semaphore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Humano extends Thread {
    protected String id;
    private ZonaInsegura zona;
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
                juego.esperarSiPausado();
                refugio.zonaComun(this);
                juego.esperarSiPausado();
                sleep(1000 + new Random().nextInt(1000));

                // 2. Cruzando túnel hacia afuera
                juego.esperarSiPausado();
                int idTunel = refugio.entrarTunelExterior(this);
                setUbicacion("Zona insegura");

                // 3. Zona insegura
                juego.esperarSiPausado();
                zona = refugio.explorarZonaExterior(idTunel);
                juego.esperarSiPausado();
                juego.entrarZriesgoH(this,idTunel);
                zona.entrar(this);
                if (marcado) {
                    juego.esperarSiPausado();
                    juego.salirZriesgoH(this,idTunel);
                    zona.salir(this);
                    refugio.volverAlRefugio(this,idTunel,zona);

                }
                else{
                    juego.esperarSiPausado();
                    int comida = zona.recolectarComida(this);


                // 4. Cruzando túnel hacia adentro
                juego.esperarSiPausado();
                juego.salirZriesgoH(this,idTunel);
                refugio.volverAlRefugio(this,idTunel,zona);
                refugio.agregarComida(comida);
                }
                // 5. Zona de descanso
                juego.esperarSiPausado();
                refugio.zonaDescanso(this);


                // 6. Comedor
                juego.esperarSiPausado();
                refugio.comedor(this);


                // 7. Enfermería (si está marcado)
                if (marcado) {
                    refugio.zonaDescanso(this);
                }


            } catch (InterruptedException e) {
                Log.info(id + " interrumpido.");
            }
        }
        if (zona!=null){
            zona.salir(this);
        }

        // Si sale del bucle porque muere
        setUbicacion("Muerto");
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
