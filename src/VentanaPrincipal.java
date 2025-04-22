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
import java.util.stream.Collectors;

public class VentanaPrincipal extends Application {
    private VBox root;
    private TableView<String> tblDescanso;
    private TableView<String> tblComedor;
    private TableView<String> tblZonaComun;
    private TableView<String> tblTuneles;
    private TableView<String> tblZonaRiesgo;
    private Label lblComida;

    public VentanaPrincipal() {
        root = new VBox(10);
        root.setPadding(new Insets(10));

        // Crear tablas para cada zona
        tblDescanso = crearTablaZona("Descanso", "#F0E68C");
        tblComedor = crearTablaZona("Comedor", "#ADD8E6");
        tblZonaComun = crearTablaZona("Zona común", "#90EE90");
        tblTuneles = crearTablaZona("Túneles", "#D3D3D3");
        tblZonaRiesgo = crearTablaZona("Zona de riesgo", "#FF6347");

        // Label de comida
        lblComida = new Label("Comida: " + Refugio.getCantidadComida());
        lblComida.setStyle("-fx-background-color: white; -fx-padding: 5; -fx-font-weight: bold;");

        // Añadir las cajas al root
        root.getChildren().addAll(
                crearCaja("REFUGIO", tblDescanso, lblComida),
                crearCaja("TÚNELES", tblTuneles),
                crearCaja("ZONA DE RIESGO", tblZonaRiesgo)
        );

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> actualizar()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private TableView<String> crearTablaZona(String zona, String colorFondo) {
        TableView<String> table = new TableView<>();
        TableColumn<String, String> column = new TableColumn<>(zona);
        column.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));
        table.getColumns().add(column);
        table.setStyle("-fx-background-color: " + colorFondo + "; -fx-padding: 5;");
        return table;
    }

    private VBox crearCaja(String titulo, TableView<String> tblZona, Label... contenidos) {
        VBox caja = new VBox(5);
        caja.setStyle("-fx-border-color: blue; -fx-border-width: 2; -fx-padding: 5;");
        Label tituloLabel = new Label(titulo);
        tituloLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        caja.getChildren().add(tituloLabel);

        // Añadir la tabla de la zona
        caja.getChildren().add(tblZona);

        // Añadir cualquier contenido adicional (labels como el de comida)
        for (Label contenido : contenidos) {
            if (contenido != null) {
                caja.getChildren().add(contenido);
            }
        }

        return caja;
    }

    private void actualizar() {
        tblDescanso.getItems().setAll(getIdsEnZona("descanso"));
        tblComedor.getItems().setAll(getIdsEnZona("comedor"));
        tblZonaComun.getItems().setAll(getIdsEnZona("zonaComun"));
        tblTuneles.getItems().setAll(getIdsEnZona("tunel"));
        tblZonaRiesgo.getItems().setAll(getIdsEnZona("zonaRiesgo"));

        lblComida.setText("Comida: " + Refugio.getCantidadComida());
    }

    private List<String> getIdsEnZona(String zona) {
        return Humano.humanosVivos.stream()
                .filter(h -> h.vivo && zona.equals(h.getUbicacion()))
                .map(Humano::getIdh)
                .collect(Collectors.toList());
    }

    private String getZombisEnZona() {
        List<String> ids = Zombi.getZombis().stream()
                .map(Zombi::getIdz)
                .collect(Collectors.toList());
        return String.join(", ", ids);
    }

    public VBox getRoot() {
        return root;
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Crear la escena con el nodo raíz (root)
        Scene scene = new Scene(root, 600, 500); // Puedes ajustar el tamaño de la ventana

        // Configurar el título de la ventana
        stage.setTitle("Simulador de Apocalipsis Zombi");

        // Establecer la escena en el escenario
        stage.setScene(scene);

        // Mostrar la ventana
        stage.show();
    }
}
