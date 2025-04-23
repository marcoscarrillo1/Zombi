import java.util.*;
import java.util.concurrent.Semaphore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Humano extends Thread {
    protected String id;
    protected boolean marcado = false;
    protected boolean comida=false;
    private boolean vivo=true;
    protected Refugio refugio;
    protected String ubicacion = "Refugio"; // Valor inicial por defecto



    public Humano(String id, Refugio refugio) {
        this.id = id;
        this.refugio = refugio;
    }
    public boolean isMarcado() {
        return marcado;
    }
    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }
    public boolean isComida() {
        return comida;
    }
    public void setComida(boolean comida) {
        this.comida = comida;
    }




    public void morir() {
        this.vivo = false;
        Log.escribir(id + " ha muerto.");
    }

    public void marcar() {
        this.marcado = true;
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
                int comida = zona.recolectarComida(this);

                // 4. Cruzando túnel hacia adentro
                refugio.volverAlRefugio(this,idTunel,zona);
                refugio.agregarComida(comida);

                // 5. Zona de descanso
                refugio.zonaDescanso(this);
                sleep(2000 + new Random().nextInt(2000));

                // 6. Comedor
                refugio.comedor(this);

                // 7. Enfermería (si está marcado)
                if (marcado) {
                    setUbicacion("Enfermería");
                    refugio.recuperarse(this);
                }

                // 8. Vuelta al refugio
                setUbicacion("Refugio");

            } catch (InterruptedException e) {
                Log.escribir(id + " interrumpido.");
            }
        }

        // Si sale del bucle porque muere
        setUbicacion("Muerto");
    }

    // Método para acceder al comedor
    public void comedor() throws InterruptedException {
        Log.escribir(id + " accede al comedor.");
        // Simula el tiempo de comer (entre 3 y 5 segundos)
        Thread.sleep((long)(Math.random() * 2000 + 3000));
    }

    // Método para descansar y recuperarse
    public void recuperarse() throws InterruptedException {
        Log.escribir(id + " necesita recuperarse de sus heridas.");
        // Simula el tiempo de recuperación (entre 3 y 5 segundos)
        Thread.sleep((long)(Math.random() * 2000 + 3000));
        this.marcado = false;
        Log.escribir(id + " se ha recuperado.");
    }

    // Método para matar un zombi
    public void matarZombi(Zombi zombi) {
        if (zombi != null) {
            Log.escribir(id + " mata al zombi " + zombi.getIdz());
            // El humano mata al zombi. Aquí puedes manejar la lógica de muerte del zombi.
            zombi.morir();
        }
    }

    // Método para acceder a la zona de descanso
    public void accederZonaDescanso() throws InterruptedException {
        Log.escribir(id + " accede a la zona de descanso.");
        // Simula el tiempo de descanso.
        Thread.sleep((long)(Math.random() * 2000 + 3000));
    }

    // Método para entrar al refugio
    public void entrarEnRefugio() throws InterruptedException {
        Log.escribir(id + " entra al refugio.");
        // Simula el tiempo que tarda en entrar.
        Thread.sleep((long)(Math.random() * 1000 + 1000));
    }

    // Método para salir del refugio
    public void salirDelRefugio() throws InterruptedException {
        Log.escribir(id + " sale del refugio.");
        // Simula el tiempo que tarda en salir.
        Thread.sleep((long)(Math.random() * 1000 + 1000));
    }

    // Método para acceder al comedor de forma sincronizada
    public void accederComedor() throws InterruptedException {
        synchronized (refugio) {
            Log.escribir(id + " accede al comedor.");
            // Simula el tiempo de comer (entre 3 y 5 segundos).
            Thread.sleep((long)(Math.random() * 2000 + 3000));
        }
    }

    // Método para comprobar si el humano está en peligro (marcado por un zombi)
    public boolean estaEnPeligro() {
        return marcado; // Devuelve verdadero si el humano está marcado por un zombi.
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
