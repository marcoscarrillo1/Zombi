import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class InterfazCliente extends Application {
    private MonitorZombi monitor;

    private Label humanosRefugio = new Label("0");
    private Label[] humanosTuneles = new Label[4];
    private Label[] humanosZonas = new Label[4];
    private Label[] zombisZonas = new Label[4];
    private Label rankingZombis = new Label("Ranking de Zombis:"); // Título para el ranking
    private TextArea rankingArea = new TextArea(); // Área de texto para el ranking

    @Override
    public void start(Stage stage) throws Exception {
        monitor = (MonitorZombi) Naming.lookup("//localhost/MonitorZombi");

        // Crear el GridPane y establecer márgenes y espaciado
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(20);

        // Fila 0: Humanos en Refugio
        grid.add(new Label("Humanos en Refugio:"), 0, 0);
        // Celda con borde para el número de humanos en refugio
        humanosRefugio.setStyle("-fx-border-color: black; -fx-padding: 10px; -fx-alignment: center;");
        grid.add(humanosRefugio, 1, 0);

        // Fila 1: Número de Humanos en Túneles
        grid.add(new Label("Número de Humanos en Túneles:"), 0, 1);
        for (int i = 0; i < 4; i++) {
            humanosTuneles[i] = new Label("0");
            humanosTuneles[i].setStyle("-fx-border-color: black; -fx-padding: 10px; -fx-alignment: center;");
            grid.add(humanosTuneles[i], i + 1, 1);
        }

        // Fila 2: Número de Humanos en Zonas Inseguras
        grid.add(new Label("Número de Humanos en Zonas Inseguras:"), 0, 2);
        for (int i = 0; i < 4; i++) {
            humanosZonas[i] = new Label("0");
            humanosZonas[i].setStyle("-fx-border-color: black; -fx-padding: 10px; -fx-alignment: center;");
            grid.add(humanosZonas[i], i + 1, 2);
        }

        // Fila 3: Número de Zombis en Zonas Inseguras
        grid.add(new Label("Número de Zombis en Zonas Inseguras:"), 0, 3);
        for (int i = 0; i < 4; i++) {
            zombisZonas[i] = new Label("0");
            zombisZonas[i].setStyle("-fx-border-color: black; -fx-padding: 10px; -fx-alignment: center;");
            grid.add(zombisZonas[i], i + 1, 3);
        }

        // Fila 4: Ranking de Zombis
        grid.add(rankingZombis, 0, 4); // Título del ranking
        rankingArea.setEditable(false); // Desactivar la edición del área de texto
        rankingArea.setStyle("-fx-border-color: black; -fx-padding: 10px; -fx-alignment: center; -fx-font-size: 14px;");
        rankingArea.setMaxSize(250, 100);
        grid.add(rankingArea, 1, 4, 4, 1); // Colocamos el área de texto ocupando 3 columnas

        // Actualización periódica
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> actualizarDatos()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // Crear y mostrar la escena
        stage.setScene(new Scene(grid));
        stage.setTitle("Monitor Apocalipsis Avanzado");
        stage.show();
    }

    private void actualizarDatos() {
        Platform.runLater(() -> {
            try {
                humanosRefugio.setText(String.valueOf(monitor.getNumHumanosRefugio()));

                Map<String, Integer> tuneles = monitor.getHumanosPorTunel();
                for (int i = 0; i < 4; i++) {
                    humanosTuneles[i].setText(String.valueOf(tuneles.get("tunel" + i)));
                }

                Map<String, Integer> humanosZ = monitor.getHumanosZonasInseguras();
                for (int i = 0; i < 4; i++) {
                    humanosZonas[i].setText(String.valueOf(humanosZ.get("zona" + i)));
                }

                Map<String, Integer> zombisZ = monitor.getZombisZonasInseguras();
                for (int i = 0; i < 4; i++) {
                    zombisZonas[i].setText(String.valueOf(zombisZ.get("zona" + i)));
                }

                // Actualizamos el ranking de zombis usando getRankingZombies
                Map<String, Integer> ranking = monitor.getRankingZombis();
                StringBuilder rankingTexto = new StringBuilder();
                for (Map.Entry<String, Integer> entry : ranking.entrySet()) {
                    rankingTexto.append(entry.getKey()).append(" - Muertes: ").append(entry.getValue()).append("\n");
                }

                rankingArea.setText(rankingTexto.toString());

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
