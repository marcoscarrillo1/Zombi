import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class VentanaController implements Initializable {

    @FXML private GridPane cajaRefugio;
    @FXML private GridPane cajaTuneles;  // Usamos un GridPane para dividir los túneles en 4 partes
    @FXML private GridPane cajaZonaRiesgo;

    @FXML private Label lblComida; // Asegúrate de que este Label esté definido en el FXML
    @FXML private Label lblDescanso; // Asegúrate de que este Label esté definido en el FXML
    @FXML TableView<String> tblDescanso;
    private TableView<String> tblTuneles;
    private TableView<String> tblZonaRiesgo;

    @FXML private ListView<String> listaRefugio;    // Lista de la zona de Refugio
    @FXML private ListView<String> listaTuneles;    // Lista de la zona de Túneles
    @FXML private ListView<String> listaZonaRiesgo; // Lista de la zona de Riesgo

    // Método para actualizar las listas con más elementos
    public void actualizarZonas() {
        // Agregar elementos a las listas
        listaRefugio.getItems().add("Humano 3");
        listaTuneles.getItems().add("Zombi 5");
        listaZonaRiesgo.getItems().add("Zombi 6");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Crear componentes y meterlos en sus cajas
        tblDescanso = crearTablaZona("Descanso", "#F0E68C");
        tblTuneles = crearTablaZona("Túneles", "#D3D3D3");
        tblZonaRiesgo = crearTablaZona("Zona de riesgo", "#FF6347");

        // Asegúrate de que lblComida ya esté conectado en el FXML
        lblComida.setText("Comida: " + Refugio.getCantidadComida());
        lblComida.setStyle("-fx-background-color: white; -fx-padding: 5; -fx-font-weight: bold;");

        // Agregar los elementos a las cajas
        cajaRefugio.getChildren().addAll(crearCaja("REFUGIO", tblDescanso, lblComida));
        crearTúneles();
        cajaZonaRiesgo.getChildren().add(crearCaja("ZONA DE RIESGO", tblZonaRiesgo));

        // Configurar el Timeline
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

    private VBox crearCaja(String titulo, TableView<String> tabla, Label... extras) {
        VBox caja = new VBox(5);
        caja.setStyle("-fx-border-color: blue; -fx-border-width: 2; -fx-padding: 5;");
        Label tituloLabel = new Label(titulo);
        tituloLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        caja.getChildren().add(tituloLabel);
        caja.getChildren().add(tabla);
        for (Label extra : extras) {
            caja.getChildren().add(extra);
        }
        return caja;
    }

    // Crear las cuatro secciones de túneles dentro de un GridPane
    private void crearTúneles() {
        // Limpiar el GridPane antes de agregar los túneles
        cajaTuneles.getChildren().clear();
        cajaTuneles.setVgap(10);
        cajaTuneles.setHgap(10);

        // Crear cuatro secciones (dos filas por dos columnas)
        for (int i = 0; i < 4; i++) {
            VBox tunnel = crearCajaDeTúneles("TÚNEL " + (i + 1));
            // Determinar la posición de cada túnel en el GridPane
            int row = i / 2;  // Dividir en 2 filas
            int col = i % 2;  // Dividir en 2 columnas
            cajaTuneles.add(tunnel, col, row);
        }
    }

    // Método para crear una caja de túnel
    private VBox crearCajaDeTúneles(String nombre) {
        VBox vbox = new VBox();
        Label label = new Label(nombre);
        vbox.getChildren().add(label);
        // Aquí puedes agregar más elementos, como tablas u otros controles específicos para los túneles
        vbox.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        vbox.setSpacing(10);
        return vbox;
    }

    private void actualizar() {
        tblDescanso.getItems().setAll(getIdsEnZona("descanso"));
        tblTuneles.getItems().setAll(getIdsEnZona("tunel"));
        tblZonaRiesgo.getItems().setAll(getIdsEnZona("zonaRiesgo"));
        lblComida.setText("Comida: " + Refugio.getCantidadComida());
    }

    // Método de ejemplo para obtener los IDs (esto lo debes reemplazar por tu lógica real)
    private List<String> getIdsEnZona(String zona) {
        // Esta lógica debe ser adaptada para obtener los IDs en función de la zona
        return List.of("ID 1", "ID 2", "ID 3");
    }
}
