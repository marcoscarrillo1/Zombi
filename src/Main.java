

import javafx.application.Application;
import javafx.application.Platform;

public class Main {
    public static void main(String[] args) {
        // Lanzar la interfaz JavaFX
        new Thread(() -> Application.launch(VentanaPrincipal.class)).start();

        // Esperar un poco para que cargue la interfaz
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Refugio refugio = new Refugio();

        // Iniciar paciente cero
        Zombi pacienteCero = new Zombi("Z0000",refugio);
        pacienteCero.start();

        // Iniciar humanos
        for (int i = 0; i < 10000; i++) {
            String id = String.format("H%04d", i);
            Humano h = new Humano(id, refugio);

            try {
                h.sleep(500 + new java.util.Random().nextInt(1500));
                h.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
