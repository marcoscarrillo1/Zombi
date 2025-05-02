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
import java.util.ResourceBundle;

public class VentanaController implements Initializable {

    @FXML
    private ImageView zombiView;
    private Juegozombie juego;

    @FXML
    private Label lblComida;
    @FXML
    private Label lblDescanso;
    @FXML
    TableView<String> tblDescanso;
    private TableView<String> tblTuneles;
    private TableView<String> tblZonaRiesgo;
    private Refugio refugio;

    public void setRefugio(Refugio refugio) {
        this.refugio = refugio;
    }

    public void setJuego(Juegozombie juego) {
        this.juego = juego;
    }

    @FXML
    private ListView<String> listaRefugio;
    @FXML
    private ListView<String> listaTuneles;
    @FXML
    private ListView<String> listaZonaRiesgo;
    @FXML
    private TextArea textAreaComedor;
    @FXML
    private TextArea textAreaDescanso;
    @FXML
    private TextArea textAreaZonaComun;
    @FXML
    private TextArea textAreasZonaRiesgo1;
    @FXML
    private TextArea textAreasZonaRiesgo2;
    @FXML
    private TextArea textAreasZonaRiesgo3;
    @FXML
    private TextArea textAreasZonaRiesgo4;
    @FXML
    private TextArea textAreasZonaRiesgo5;
    @FXML
    private TextArea textAreasZonaRiesgo6;
    @FXML
    private TextArea textAreasZonaRiesgo7;
    @FXML
    private TextArea textAreasZonaRiesgo8;
    @FXML
    private TextArea textAreaTunel1;
    @FXML
    private TextArea textAreaTunel2;
    @FXML
    private TextArea textAreaTunel3;
    @FXML
    private TextArea textAreaTunel4;
    @FXML
    private TextArea textAreaTunel5;
    @FXML
    private TextArea textAreaTunel6;
    @FXML
    private TextArea textAreaTunel7;
    @FXML
    private TextArea textAreaTunel8;
    @FXML
    private TextArea textAreaTunel9;
    @FXML
    private TextArea textAreaTunel10;
    @FXML
    private TextArea textAreaTunel11;
    @FXML
    private TextArea textAreaTunel12;
    private ListaHilos zonaDescanso= new ListaHilos(textAreaDescanso);
    private ListaHilos zonaComun=new ListaHilos(textAreaZonaComun);
    private ListaHilos zonaComedor=new ListaHilos(textAreaComedor);

    public TextArea getTextAreaComedor() {
        return textAreaComedor;
    }

    public TextArea getTextAreaDescanso() {
        return textAreaDescanso;
    }

    public TextArea getTextAreaZonaComun() {
        return textAreaZonaComun;
    }

    @FXML
    private TextArea[] textAreasTuneles = new TextArea[12];
    private TextArea[] textAreasZonaRiesgo = new TextArea[8];
    private Tunel[] tuneles = new Tunel[4];
    private ZonaInsegura[] zonas = new ZonaInsegura[4];
    @FXML private Button botonPausa;
    public void setZonaComun(ListaHilos zonaComun) {
        this.zonaComun = zonaComun;
    }

    public void setZonaDescanso(ListaHilos zonaDescanso) {
        this.zonaDescanso = zonaDescanso;
    }

    public void setZonaComedor(ListaHilos zonaComedor) {
        this.zonaComedor = zonaComedor;
    }

    // Listas de hilos para cada celda (zona)
    private List<ListaHilos> listaHilosPorZona;

    @FXML
    private void manejarPausa() {
        if (juego.estaPausado()) {
            juego.reanudar();
            botonPausa.setText("Pausar");
        } else {
            juego.pausar();
            botonPausa.setText("Reanudar");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicializar las listas de hilos por zona
        listaHilosPorZona = new ArrayList<>();
        listaHilosPorZona.add(zonaComedor); // Comedor
        listaHilosPorZona.add(zonaDescanso); // Descanso
        listaHilosPorZona.add(zonaComun); // Zona Común

        // Crear una lista de hilos vacía para cada zona y túnel
        for (int i = 0; i < 12; i++) {
            listaHilosPorZona.add(new ListaHilos(textAreasTuneles[i])); // Para túneles
        }
        for (int i = 0; i < 8; i++) {
            listaHilosPorZona.add(new ListaHilos(textAreasZonaRiesgo[i])); // Para zonas (Refugio, Comedor, etc.)
        }


        // Asignar textAreasZonaRiesgo y textAreasTuneles
        textAreasZonaRiesgo[0] = textAreasZonaRiesgo1;
        textAreasZonaRiesgo[1] = textAreasZonaRiesgo2;
        textAreasZonaRiesgo[2] = textAreasZonaRiesgo3;
        textAreasZonaRiesgo[3] = textAreasZonaRiesgo4;
        textAreasZonaRiesgo[4] = textAreasZonaRiesgo5;
        textAreasZonaRiesgo[5] = textAreasZonaRiesgo6;
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

        // Configurar el Timeline
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> actualizar()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void actualizar() {
        // Actualizar los TextAreas de cada zona (Refugio, Comedor, etc.) con los IDs de los humanos
        actualizarZona("comedor", textAreaComedor);
        actualizarZona("descanso", textAreaDescanso);
        actualizarZona("zonacomun", textAreaZonaComun);
        for (int i = 1; i <= 12; i++) {
            actualizarZona("tunel" + i, textAreasTuneles[i - 1]);
        }
        for (int i = 1; i <= 8; i++) {
            actualizarZona("zona" + i, textAreasZonaRiesgo[i - 1]);
        }
    }

    private void actualizarZona(String zona, TextArea textArea) {
        // Obtiene los IDs de los hilos en la zona correspondiente y los muestra en el TextArea
        int zonaIndex = obtenerIndiceZona(zona);
        if (zonaIndex != -1) {
            List<String> ids = listaHilosPorZona.get(zonaIndex).getIds();
            textArea.setText(String.join("\n", ids));
        }
    }

    private int obtenerIndiceZona(String zona) {
        // Devuelve el índice correspondiente a la zona
        switch (zona) {
            case "comedor": return 0;
            case "descanso": return 1;
            case "zonacomun": return 2;
            case "tunel1": return 3;  // Túneles empiezan en índice 4
            case "tunel2": return 4;
            case "tunel3": return 5;
            case "tunel4": return 6;
            case "tunel5": return 7;
            case "tunel6": return 8;
            case "tunel7": return 9;
            case "tunel8": return 10;
            case "tunel9": return 11;
            case "tunel10": return 12;
            case "tunel11": return 13;
            case "tunel12": return 14;
            case "zona1": return 15;  // Zonas de riesgo empiezan en índice 12
            case "zona2": return 16;
            case "zona3": return 17;
            case "zona4": return 18;
            case "zona5": return 19;
            case "zona6": return 20;
            case "zona7": return 21;
            case "zona8": return 22;
            default: return -1;
        }
    }
}
