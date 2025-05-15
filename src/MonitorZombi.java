import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface MonitorZombi extends Remote {
    int getNumHumanosRefugio() throws RemoteException;
    List<Integer> getHumanosPorTunel() throws RemoteException;
    List<Integer> getHumanosZonasInseguras() throws RemoteException;
    List<Integer> getZombisZonasInseguras() throws RemoteException;
    Map<String, Integer> getRankingZombis() throws RemoteException;
    void pausar() throws RemoteException;
    void reanudar() throws RemoteException;

}
