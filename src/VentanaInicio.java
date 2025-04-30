import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

        // Imagen de fondo (se expandirá al tamaño de la ventana)
        ImageView backgroundView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/apocap.png"))));
        backgroundView.setFitWidth(800);
        backgroundView.setFitHeight(700);
        backgroundView.setPreserveRatio(false); // Para que cubra todo

        // Mensaje de bienvenida
        Label mensaje = new Label("Bienvenido al juego de los zombis. ¡Coge a tus mejores aliados antes de empezar!");
        mensaje.setTextFill(Color.ORANGERED);
        mensaje.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        mensaje.setWrapText(true);
        mensaje.setAlignment(Pos.BOTTOM_LEFT);
        mensaje.setTextAlignment(TextAlignment.CENTER);

        // Botón Nueva Partida más grande
        Button btnNuevaPartida = new Button("Nueva Partida");
        btnNuevaPartida.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        btnNuevaPartida.setPadding(new Insets(15, 30, 15, 30)); // Más grande
        btnNuevaPartida.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        btnNuevaPartida.setOnAction(e -> {
            VentanaPrincipal ventanaJuego = new VentanaPrincipal();
            try {
                ventanaJuego.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            primaryStage.close();
        });

        // Contenedor centrado con el mensaje y el botón
        VBox content = new VBox(40, mensaje, btnNuevaPartida);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(50));

        // Apilar fondo + contenido
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundView, content);

        Scene scene = new Scene(root, 800, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
