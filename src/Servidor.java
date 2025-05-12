import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
    public static void iniciar(Juegozombie juego, Refugio refugio) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            Registry registry;

            try {
                registry = LocateRegistry.getRegistry(1099);
                registry.list(); // Verifica si el registro está activo
                System.out.println("Registro RMI ya está corriendo en el puerto 1099...");
            } catch (Exception e) {
                // Si no existe, entonces se crea
                registry = LocateRegistry.createRegistry(1099);
                System.out.println("Registro RMI creado en el puerto 1099...");
            }

            try {
                Naming.lookup("rmi://localhost/MonitorZombi");
                System.out.println("MonitorZombi ya está registrado en el RMI registry.");
                return;
            } catch (Exception e) {
                // No está registrado, se crea y registra
                MonitorZombiImpl monitor = new MonitorZombiImpl(refugio, juego);  // ya exportado
                Naming.rebind("rmi://localhost/MonitorZombi", monitor);
                System.out.println("Servidor RMI corriendo en puerto 1099...");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
