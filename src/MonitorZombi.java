import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface MonitorZombi extends Remote {
    int getNumHumanosRefugio() throws RemoteException;
    Map<String, Integer> getHumanosPorTunel() throws RemoteException;
    Map<String, Integer> getHumanosZonasInseguras() throws RemoteException;
    Map<String, Integer> getZombisZonasInseguras() throws RemoteException;
    Map<String, Integer> getRankingZombis() throws RemoteException;
    void pausar() throws RemoteException;
    void reanudar() throws RemoteException;
    boolean estaPausado() throws RemoteException;
}
