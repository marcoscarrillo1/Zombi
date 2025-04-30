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
    @FXML private TextArea textAreaComedor;
    @FXML private TextArea textAreaDescanso;
    @FXML private TextArea textAreaZonaComun;
    @FXML private TextArea textAreasZonaRiesgo1;
    @FXML private TextArea textAreasZonaRiesgo2;
    @FXML private TextArea textAreasZonaRiesgo3;
    @FXML private TextArea textAreasZonaRiesgo4;
    @FXML private TextArea textAreasZonaRiesgo5;
    @FXML private TextArea textAreasZonaRiesgo6;
    @FXML private TextArea textAreasZonaRiesgo7;
    @FXML private TextArea textAreasZonaRiesgo8;
    @FXML private TextArea textAreaTunel1;
    @FXML private TextArea textAreaTunel2;
    @FXML private TextArea textAreaTunel3;
    @FXML private TextArea textAreaTunel4;
    @FXML private TextArea textAreaTunel5;
    @FXML private TextArea textAreaTunel6;
    @FXML private TextArea textAreaTunel7;
    @FXML private TextArea textAreaTunel8;
    @FXML private TextArea textAreaTunel9;
    @FXML private TextArea textAreaTunel10;
    @FXML private TextArea textAreaTunel11;
    @FXML private TextArea textAreaTunel12;

    @FXML
    private TextArea[] textAreasTuneles = new TextArea[12];
    private TextArea[] textAreasZonaRiesgo = new TextArea[8];
    private Tunel[] tuneles = new Tunel[4];
    private ZonaInsegura[] zonas = new ZonaInsegura[4];




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Crear componentes y meterlos en sus cajas
        for (int i = 0; i < 4; i++) {
            tuneles[i] = new Tunel(i + 1);  // Creamos túneles con ID 1 a 4
        }
        for (int i = 0; i < 4; i++) {
            zonas[i] = new ZonaInsegura(i + 1);  // Creamos túneles con ID 1 a 4
        }
        textAreasZonaRiesgo[0] = textAreasZonaRiesgo1;
        textAreasZonaRiesgo[1] = textAreasZonaRiesgo2;
        textAreasZonaRiesgo[2]=textAreasZonaRiesgo3;
        textAreasZonaRiesgo[3] = textAreasZonaRiesgo4;
        textAreasZonaRiesgo[4] = textAreasZonaRiesgo5;
        textAreasZonaRiesgo[5]=textAreasZonaRiesgo6;
        textAreasZonaRiesgo[6] = textAreasZonaRiesgo7;
        textAreasZonaRiesgo[7] = textAreasZonaRiesgo8;


        textAreasTuneles[0] = textAreaTunel1;
        textAreasTuneles[1] = textAreaTunel2;
        textAreasTuneles[2] = textAreaTunel3;
        textAreasTuneles[3] = textAreaTunel4;
        textAreasTuneles[4] = textAreaTunel5;
        textAreasTuneles[5] = textAreaTunel6;
        textAreasTuneles[6] = textAreaTunel7;
        textAreasTuneles[7] = textAreaTunel8;
        textAreasTuneles[8] = textAreaTunel9;
        textAreasTuneles[9] = textAreaTunel10;
        textAreasTuneles[10] = textAreaTunel11;
        textAreasTuneles[11] = textAreaTunel12;

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
        lblComida.setText("" + Refugio.getCantidadComida());
        textAreaDescanso.setText(String.join("\n", getIdsEnZona("descanso")));
        textAreaComedor.setText(String.join("\n", getIdsEnZona("comedor")));
        textAreaZonaComun.setText(String.join("\n", getIdsEnZona("zonacomun")));
        for (int i = 1; i <= 12; i++) {
            String tunel = "tunel" + i;
            String ids = String.join("\n", getIdsEnZona(tunel));
            // Accede al TextArea correspondiente usando el índice del array
            textAreasTuneles[i - 1].setText(ids);  // Restamos 1 porque el índice del array empieza en 0
        }
        for (int i = 1; i <= 8; i++) {
            String zona = "zona" + i;
            String ids = String.join("\n", getIdsEnZona(zona));
            // Accede al TextArea correspondiente usando el índice del array
            textAreasZonaRiesgo[i - 1].setText(ids);  // Restamos 1 porque el índice del array empieza en 0
        }

    }

    // Método de ejemplo para obtener los IDs (esto lo debes reemplazar por tu lógica real)
    private ArrayList<String> getIdsEnZona(String zona) {
        ArrayList<String> ids = new ArrayList<>();

        switch (zona) {
            case "descanso":
                for (Humano humano : refugio.getHumanosDescansando()) {
                    ids.add(humano.getIdh());
                }
                break;
            case "comedor":
                for (Humano humano : refugio.getHumanosComedor()) {
                    ids.add(humano.getIdh());
                }
                break;
            case "comun":
                for (Humano humano : refugio.getHumanosZonaComun()) {
                    ids.add(humano.getIdh());
                }
                break;
            case "tunel1":
                for(Humano h: tuneles[1].getEsperandoEntrar()){
                    ids.add(h.getIdh());
                }

            case "tunel2":
                ids.add(tuneles[1].getHumanoDentro().getIdh());
            case "tunel3":
                for(Humano h: tuneles[1].getEsperandosalir()){
                    ids.add(h.getIdh());
                }
            case "tunel4":
                for(Humano h: tuneles[2].getEsperandoEntrar()){
                    ids.add(h.getIdh());
                }
            case "tunel5":
                ids.add(tuneles[2].getHumanoDentro().getIdh());
            case "tunel6":
                for(Humano h: tuneles[2].getEsperandosalir()){
                    ids.add(h.getIdh());
                }
            case "tunel7":
                for(Humano h: tuneles[3].getEsperandoEntrar()){
                    ids.add(h.getIdh());
                }
            case "tunel8":
                ids.add(tuneles[3].getHumanoDentro().getIdh());
            case "tunel9":
                for(Humano h: tuneles[3].getEsperandosalir()){
                    ids.add(h.getIdh());
                }
            case "tunel10":
                for(Humano h: tuneles[4].getEsperandoEntrar()){
                    ids.add(h.getIdh());
                }
            case "tunel11":
                ids.add(tuneles[4].getHumanoDentro().getIdh());
            case "tunel12":
                for(Humano h: tuneles[4].getEsperandosalir()){
                    ids.add(h.getIdh());
                }
            case "zona1":
                for(Humano h: zonas[1].getHumanos()){
                    ids.add(h.getIdh());
                }

            case "zona2":
                for (Zombi z :zonas[1].getZombies()){
                    ids.add(z.getIdz());
                }
            case "zona3":
                for(Humano h: zonas[2].getHumanos()){
                    ids.add(h.getIdh());
                }
            case "zona4":
                for (Zombi z :zonas[2].getZombies()){
                    ids.add(z.getIdz());
                }
            case "zona5":
                for(Humano h: zonas[3].getHumanos()){
                    ids.add(h.getIdh());
                }
            case "zona6":
                for (Zombi z :zonas[3].getZombies()){
                    ids.add(z.getIdz());
                }
            case "zona7":
                for(Humano h: zonas[4].getHumanos()){
                    ids.add(h.getIdh());
                }
            case "zona8":
                for (Zombi z :zonas[4].getZombies()){
                    ids.add(z.getIdz());
                }

        }

        return ids;
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

          // Retornamos la lista con los IDs


}
