import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorZombiImpl extends UnicastRemoteObject implements MonitorZombi {
    private final Refugio refugio;
    private final Juegozombie juego;
    private final Map<String, Integer> rankingZombies;
    private final Lock locktunel = new ReentrantLock();
    private final Lock lockzona = new ReentrantLock();

    @Override
    public Map<String, Integer> getHumanosPorTunel() throws RemoteException {
        locktunel.lock();
        try {
            return refugio.getContadorHumanosPorTunel();
        } finally {
            locktunel.unlock();
        }
    }



    public MonitorZombiImpl(Refugio refugio, Juegozombie juego) throws RemoteException {
        super();
        this.refugio = refugio;
        this.juego = juego;
        this.rankingZombies = new HashMap<>();
    }

    @Override
    public int getNumHumanosRefugio() throws RemoteException {
        return refugio.getTotalHumanos();
    }


    @Override
    public Map<String, Integer> getHumanosZonasInseguras() throws RemoteException {
        lockzona.lock();
        try {
            return refugio.getContadorHumanosZonasInseguras();
        }
        finally {
            lockzona.unlock();
        }
    }

    @Override
    public Map<String, Integer> getZombisZonasInseguras() throws RemoteException {
        return refugio.getContadorZombisZonasInseguras();
    }


    @Override
    public Map<String, Integer> getRankingZombis() throws RemoteException {
        return refugio.obtenerRankingZombis();

    }


    @Override
    public void pausar() throws RemoteException {
        juego.pausar();
    }

    @Override
    public void reanudar() throws RemoteException {
        juego.reanudar();
    }

    @Override
    public boolean estaPausado() throws RemoteException {
        return juego.estaPausado();
    }
}
