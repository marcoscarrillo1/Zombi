import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Objects;

public class VentanaInicio extends Application {

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Simulador Apocalipsis Zombi");

        // Imagen de fondo
        ImageView backgroundView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/apocap.png"))));
        backgroundView.setFitWidth(900);
        backgroundView.setFitHeight(900);
        backgroundView.setPreserveRatio(false);

        // Texto de bienvenida
        Label textoBienvenida = new Label("Bienvenido al juego de los zombis.\n¡Coge a tus mejores aliados antes de empezar!");
        textoBienvenida.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        textoBienvenida.setTextFill(Color.WHITE);
        textoBienvenida.setAlignment(Pos.CENTER);
        textoBienvenida.setWrapText(true);

        // Slider para seleccionar la cantidad de humanos
        Label labelSlider = new Label("Cantidad de humanos:");
        labelSlider.setTextFill(Color.WHITE);
        labelSlider.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Slider sliderHumanos = new Slider(10, 1000, 100);
        sliderHumanos.setShowTickLabels(false);
        sliderHumanos.setShowTickMarks(false);
        sliderHumanos.setBlockIncrement(100);
        sliderHumanos.setSnapToTicks(true);
        sliderHumanos.setPrefWidth(150);

        Label valorSlider = new Label("100");
        valorSlider.setTextFill(Color.WHITE);
        valorSlider.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        sliderHumanos.valueProperty().addListener((obs, oldVal, newVal) -> {
            valorSlider.setText(String.valueOf(newVal.intValue()));
        });

        HBox sliderBox = new HBox(10, sliderHumanos, valorSlider);
        sliderBox.setAlignment(Pos.CENTER);

        // Botón Nueva Partida
        Button btnNuevaPartida = new Button("Nueva Partida");
        btnNuevaPartida.setFont(Font.font("Arial", 20));
        btnNuevaPartida.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        btnNuevaPartida.setPrefWidth(220);
        btnNuevaPartida.setPrefHeight(50);
        btnNuevaPartida.setOnMouseEntered(e -> btnNuevaPartida.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;"));
        btnNuevaPartida.setOnMouseExited(e -> btnNuevaPartida.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;"));

        btnNuevaPartida.setOnAction(e -> {
            int cantidadHumanos = (int) sliderHumanos.getValue();
            VentanaPrincipal ventanaJuego = new VentanaPrincipal(cantidadHumanos);
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
        btnInfo.setPrefWidth(220);
        btnInfo.setPrefHeight(45);
        btnInfo.setOnMouseEntered(e -> btnInfo.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-weight: bold;"));
        btnInfo.setOnMouseExited(e -> btnInfo.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;"));

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

        // Distribución: botones arriba, slider en medio, texto abajo
        VBox botonesBox = new VBox(10, btnNuevaPartida, btnInfo);
        botonesBox.setAlignment(Pos.CENTER);

        VBox sliderSection = new VBox(10, labelSlider, sliderBox);
        sliderSection.setAlignment(Pos.CENTER);

        VBox content = new VBox(40, botonesBox, sliderSection, textoBienvenida);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        StackPane root = new StackPane(backgroundView, content);
        Scene scene = new Scene(root, 900, 900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
