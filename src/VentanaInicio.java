import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Objects;

public class VentanaInicio extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simulador Apocalipsis Zombi");

        // Imagen de fondo
        ImageView backgroundView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/apocap.png"))));
        backgroundView.setFitWidth(800);
        backgroundView.setFitHeight(700);
        backgroundView.setPreserveRatio(false);

        // Texto de bienvenida
        Label textoBienvenida = new Label("Bienvenido al juego de los zombis.\n¡Coge a tus mejores aliados antes de empezar!");
        textoBienvenida.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        textoBienvenida.setTextFill(Color.WHITE);
        textoBienvenida.setAlignment(Pos.CENTER);
        textoBienvenida.setWrapText(true);

        // Botón Nueva Partida
        Button btnNuevaPartida = new Button("Nueva Partida");
        btnNuevaPartida.setFont(Font.font("Arial", 20));
        btnNuevaPartida.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        btnNuevaPartida.setOnAction(e -> {
            VentanaPrincipal ventanaJuego = new VentanaPrincipal();
            try {
                ventanaJuego.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            primaryStage.close();
        });

        // Botón Información
        Button btnInfo = new Button("Información");
        btnInfo.setFont(Font.font("Arial", 18));
        btnInfo.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        btnInfo.setOnAction(e -> {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Instrucciones del Juego");
            alerta.setHeaderText("¿Cómo se juega?");
            alerta.setContentText("""
            1. Empieza una nueva partida pulsando el botón correspondiente.
            2. Los humanos deben sobrevivir moviéndose entre zonas del refugio.
            3. Los zombis intentarán infectar a los humanos si los encuentran fuera del refugio.
            4. Observa en tiempo real cómo evoluciona la situación.
            5. ¡Asegúrate de que tus aliados sobrevivan!
        """);
            alerta.showAndWait();
        });

        // Contenedor vertical para los elementos
        VBox content = new VBox(20, textoBienvenida, btnNuevaPartida, btnInfo);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));

        // StackPane con fondo y contenido
        StackPane root = new StackPane(backgroundView, content);
        Scene scene = new Scene(root, 800, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
