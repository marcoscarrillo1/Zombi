import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.text.Text;



public class VentanaPrincipal extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Ventana.fxml"));
        Parent root = loader.load();
        VentanaController controller = loader.getController();
        Refugio refugio = new Refugio();
        TextArea textoZonaComun = controller.getTextAreaZonaComun();
        TextArea textoZonaDescanso =controller.getTextAreaDescanso();
        TextArea textoZonaComedor = controller.getTextAreaComedor();

        // Crear las instancias de ListaHilos pasando el Text correspondiente
        ListaHilos zonaComun = new ListaHilos(textoZonaComun);
        ListaHilos zonaDescanso = new ListaHilos(textoZonaDescanso);
        ListaHilos zonaComedor = new ListaHilos(textoZonaComedor);
        int comida = 100;  // O el valor que necesites
        ArrayList<ZonaInsegura> enzonariesgo = new ArrayList<>();
        ArrayList<ListaHilos> zonariesgoZZ = new ArrayList<>();

        // Crear la instancia de Juegozombie
        Juegozombie juego = new Juegozombie(zonaComun, zonaDescanso, zonaComedor, comida, enzonariesgo, zonariesgoZZ);



        controller.setRefugio(refugio);
        controller.setJuego(juego);
        controller.setZonaComedor(zonaComedor);
        controller.setZonaComun(zonaComun);
        controller.setZonaDescanso(zonaDescanso);// Pásale el refugio al controlador

        Scene scene = new Scene(root, 1000, 900);
        stage.setTitle("Simulador de Apocalipsis Zombi");
        stage.setScene(scene);
        stage.show();

        // Crear paciente cero
        Zombi pacienteCero = new Zombi("Z0000", refugio);
        pacienteCero.start();
        // Iniciar humanos
        // Crear humanos
        for (int i = 0; i < 10; i++) {
            String id = String.format("H%04d", i);
            Humano h = new Humano(id, refugio);

            // Crear un nuevo hilo para el humano
            new Thread(() -> {
                try {
                    // Simular retardo en la creación de humanos
                    Thread.sleep(500 + new java.util.Random().nextInt(1500));

                    // Llamar a start() del humano dentro del hilo
                    h.start();

                    // Asegurarse de que la actualización de la interfaz gráfica se haga en el hilo principal
                    Platform.runLater(() -> {
                        zonaComun.añadir(h);  // Añadir el humano a la zona
                       ;  // Actualizar el TextArea con los IDs de los humanos
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start(); // Lanza el hilo de creación del humano
        }





    }}
