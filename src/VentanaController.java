import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class VentanaController implements Initializable {

    @FXML private GridPane cajaRefugio;
    @FXML private GridPane cajaTuneles;  // Usamos un GridPane para dividir los túneles en 4 partes
    @FXML private GridPane cajaZonaRiesgo;
    @FXML private ImageView zombiView;

    @FXML private Label lblComida; // Asegúrate de que este Label esté definido en el FXML
    @FXML private Label lblDescanso; // Asegúrate de que este Label esté definido en el FXML
    @FXML TableView<String> tblDescanso;
    private TableView<String> tblTuneles;
    private TableView<String> tblZonaRiesgo;
    private Refugio refugio;
    public void setRefugio(Refugio refugio) {
        this.refugio = refugio;
    }

    @FXML private ListView<String> listaRefugio;    // Lista de la zona de Refugio
    @FXML private ListView<String> listaTuneles;    // Lista de la zona de Túneles
    @FXML private ListView<String> listaZonaRiesgo; // Lista de la zona de Riesgo
    @FXML private Label labelDescanso;
    @FXML private Label Labelcomedor;
    @FXML private Label Labelcomida;
    @FXML private Label laabelzonacomun;

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

        // Cargar imagen del zombi
        Image zombiImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/zzzombi.png")));
        zombiView.setImage(zombiImage);

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
        Labelcomida.setText(""+Refugio.getCantidadComida());
        Labelcomedor.setText(""+getIdsEnZona("comedor"));
        labelDescanso.setText(""+getIdsEnZona("descanso"));
        laabelzonacomun.setText(""+getIdsEnZona("comun"));
    }

    // Método de ejemplo para obtener los IDs (esto lo debes reemplazar por tu lógica real)
    private ArrayList<String> getIdsEnZona(String zona) {
        ArrayList<String> ids = new ArrayList<>();  // Creamos una lista vacía para almacenar los IDs

        // Si la zona es "descanso", obtendremos los humanos que están descansando
        if ("descanso".equals(zona)) {
            // Recorremos la lista de humanos descansando en el refugio
            while(ids.toArray().length<3){
                for (Humano humano : refugio.getHumanosDescansando()) {
                    ids.add(humano.getIdh());  // Añadimos el ID de cada humano
                }
            }
        }

        else if ("comedor".equals(zona)) {
            // Recorremos la lista de humanos descansando en el refugio
            while(ids.toArray().length<3){
                for (Humano humano : refugio.getHumanosComedor()) {
                    ids.add(humano.getIdh());  // Añadimos el ID de cada humano
                }
            }
        }
        else if ("comun".equals(zona)) {
            // Recorremos la lista de humanos descansando en el refugio
            while(ids.toArray().length<3){
                for (Humano humano : refugio.getHumanosZonaComun()) {
                    ids.add(humano.getIdh());  // Añadimos el ID de cada humano
                }
            }
        }

        // Si la zona es "tunel", obtenemos los humanos que están esperando en los túneles
        /*else if ("tunel".equals(zona)) {
            // Recorremos todos los túneles
            for (Tunel tunel : Refugio.getTuneles()) {
                // Recorremos la lista de humanos esperando en este túnel
                for (Humano humano : tunel.getEsperando()) {
                    ids.add(humano.getIdh());  // Añadimos el ID de cada humano
                }
            }
        }

        // Si la zona es "zonaRiesgo", obtenemos los humanos que están en las zonas de riesgo
        else if ("zonaRiesgo".equals(zona)) {
            // Recorremos todas las zonas de riesgo
            for (ZonaInsegura zonaInsegura : Refugio.getZonas()) {
                // Recorremos los humanos en esta zona
                for (Humano humano : zonaInsegura.getHumanos()) {
                    ids.add(humano.getIdh());  // Añadimos el ID de cada humano
                }
            }
        }*/

        return ids;  // Retornamos la lista con los IDs
    }

}
