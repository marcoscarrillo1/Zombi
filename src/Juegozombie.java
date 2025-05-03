import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
public class Juegozombie {
    private static final Logger logger=new Log().getLogger();
    private ListaHilos zonaComun;
    private ListaHilos zonaDescanso;
    private ListaHilos zonaComedor;
    private LinkedBlockingDeque colacomedor=new LinkedBlockingDeque<>();
    private Random rand=new Random();
    private ArrayList<ListaHilos> irtuneles;
    private ArrayList<ListaHilos> volvertuneles;
    private ArrayList<ListaHilos> dentrotuenel;
    private ArrayList<ListaHilos> zonariesgoZZ;
    private ArrayList<ZonaInsegura> enzonariesgo;
    private int comida;
    private Text comidatxt;
    private final Lock cerrojopausa= new ReentrantLock();
    private final Condition condicionpausa=cerrojopausa.newCondition();
    private boolean pausado=false;

    public Juegozombie( ListaHilos zonaComun,ListaHilos zonaDescanso,ListaHilos zonaComedor,ArrayList<ListaHilos> irtuneles,ArrayList<ListaHilos> volvertuneles,ArrayList<ListaHilos> dentrotuenel,int comida,ArrayList<ZonaInsegura> enzonariesgo,ArrayList<ListaHilos> zonariesgoZZ) {
        this.zonaComun=zonaComun;
        this.zonaDescanso=zonaDescanso;
        this.zonaComedor=zonaComedor;
        this.irtuneles=irtuneles;
        this.volvertuneles=volvertuneles;
        this.dentrotuenel=dentrotuenel;
        this.comida=comida;
        this.zonariesgoZZ=zonariesgoZZ;
        this.enzonariesgo=enzonariesgo;


    }
    public void entrarZcomun(Humano h){
        zonaComun.añadir(h);
        logger.info("Humano con id:+"+h.getIdh()+"ha entrado a zona comun");
    }
    public void entrarDescanso(Humano h){
        zonaDescanso.añadir(h);
        logger.info("Humano con id:+"+h.getIdh()+"ha entrado a zona descanso");

    }
    public void salirZcomun(Humano h){
        zonaComun.fuera(h);
        logger.info("Humano con id:+"+h.getIdh()+"ha salido de la zona comun");

    }
    public void salirDescanso(Humano h){
        zonaDescanso.fuera(h);
        logger.info("Humano con id:+"+h.getIdh()+"ha salido de la zona descanso");

    }
    public void salirZcomedor(Humano h){
        zonaComedor.fuera(h);
        logger.info("Humano con id:+"+h.getIdh()+"ha salido del comedor");

    }
    public void entrarZcomedor(Humano h){
        zonaComedor.añadir(h);
        logger.info("Humano con id:+"+h.getIdh()+"ha entrado al comedor");

    }
    public void entrarZriesgoH(Humano h,int i){
        enzonariesgo.get(i).añadirhumano(h);
        logger.info("Humano con id:+"+h.getIdh()+"ha entrado a zona riesgo");

    }
    public void salirZriesgoH(Humano h,int i){
        enzonariesgo.get(i).eliminarHumano(h);
        logger.info("Humano con id:+"+h.getIdh()+"ha salido de la zona de riesgo");

    }
    public void salirZriesgoZ(Zombi z,int i){
        zonariesgoZZ.get(i).fuera(z);
        logger.info("Zombie con id:+"+z.getIdz()+"ha salido zona riesgo");

    }
    public void entrarZriesgoZ(Zombi z,int i){
        zonariesgoZZ.get(i).añadir(z);
        logger.info("Zombie con id:+"+z.getIdz()+"ha entrado zona riesgo");
    }
   public synchronized void  entrarcolacomedor(Humano h){
        colacomedor.add(h);
       logger.info("Humano con id:+"+h.getIdh()+"esperando pa comer");

   }
   public synchronized void salircolacomedor(Humano h){
        colacomedor.remove(h);
       logger.info("Humano con id:+"+h.getIdh()+" no esperando pa comer");
   }
   public synchronized void dejarComida(){
        try{
                comida=comida+2;
                comidatxt.setTextContent("Comida:" +comida);
                logger.info("Comida actualizada :+"+comida);
                notifyAll();

        }catch(Exception e){
            logger.warning("Error al dejar la comida");
        }
   }
   public void comer(Humano h){
        try{
            entrarcolacomedor(h);
            logger.info("El humano con id:"+h.getIdh()+"esta esperando pa comer");
            while (colacomedor.peek()!=h){
                synchronized (this){
                    wait();
                }
            }
            entrarZcomedor(h);
            logger.info("El humano con id:"+h.getIdh()+"ha entrado a comer");
            synchronized (this){
                while(comida==0){
                    logger.info("El humano con id:"+h.getIdh()+"esta esperando que le traigan comida");
                    wait();
                }
                comida--;
                comidatxt.setTextContent("Comida:" +comida);
                logger.info("El humano con id:"+h.getIdh()+"ha comido");
                logger.info("Comida actualizada:"+comida);
            }
            Thread.sleep(1000);
            salirZcomedor(h);
            salircolacomedor(h);
            logger.info("El humano con id:"+h.getIdh()+"ha terminado de comer");
            synchronized (this){
                notifyAll();
            }

        }catch(Exception e){
            logger.warning("Los humanos no saben comer");
        }}
       public void pausar() {
           cerrojopausa.lock();
           try {
               pausado = true;
           } finally {
               cerrojopausa.unlock();
           }
       }

       public void reanudar() {
           cerrojopausa.lock();
           try {
               pausado = false;
               condicionpausa.signalAll();
           } finally {
               cerrojopausa.unlock();
           }
       }

       public boolean estaPausado() {
           cerrojopausa.lock();
           try {
               return pausado;
           } finally {
               cerrojopausa.unlock();
           }
       }

       public void esperarSiPausado() {
           cerrojopausa.lock();
           try {
               while (pausado) {
                   condicionpausa.await();
               }
           } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
           } finally {
               cerrojopausa.unlock();
           }
       }
   }





