import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class VentanaPrincipal extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Refugio refugio = new Refugio();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Ventana.fxml"));
        Parent root = loader.load();

        VentanaController controller = loader.getController();
        controller.setRefugio(refugio); // Pásale el refugio al controlador

        Scene scene = new Scene(root, 1000, 900);
        stage.setTitle("Simulador de Apocalipsis Zombi");
        stage.setScene(scene);
        stage.show();

        // Crear paciente cero
        Zombi pacienteCero = new Zombi("Z0000", refugio);
        pacienteCero.start();

        // Crear humanos
        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                String id = String.format("H%04d", i);
                Humano h = new Humano(id, refugio);
                h.start();

                try {
                    Thread.sleep(500 + new java.util.Random().nextInt(1500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start(); // Lanza en hilo separado para no bloquear la interfaz
    }

}
