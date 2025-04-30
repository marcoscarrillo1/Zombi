import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class VentanaInicio extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simulador Apocalipsis Zombi");

        // Cargar la imagen del zombi
        ImageView zombiView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/zzzombie.png"))));
        zombiView.setFitHeight(150);  // Ajustar el tamaño
        zombiView.setPreserveRatio(true);  // Mantener la proporción

        // Botón Nueva Partida con estilo
        Button btnNuevaPartida = new Button("Nueva Partida");
        btnNuevaPartida.setFont(Font.font("Arial", 18));
        btnNuevaPartida.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        btnNuevaPartida.setOnAction(e -> {
            // Lanza la ventana principal del juego
           VentanaPrincipal ventanaJuego = new VentanaPrincipal();
            try {
                ventanaJuego.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Cierra esta ventana
            primaryStage.close();
        });

        // Layout principal
        VBox layout = new VBox(20, zombiView, btnNuevaPartida);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setBackground(new Background(new BackgroundFill(Color.web("#1e1e1e"), CornerRadii.EMPTY, Insets.EMPTY)));

        // Configuración de la escena
        Scene scene = new Scene(layout, 800, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
