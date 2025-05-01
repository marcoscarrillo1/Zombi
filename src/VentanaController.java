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

        // Asegúrate de que lblComida ya esté conectado en el FXML
        lblComida.setText("Comida: " + refugio.getCantidadComida());


        // Agregar los elementos a las cajas

        // Cargar imagen del zombi
        Image zombiImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/zzzombie.png")));
        zombiView.setImage(zombiImage);

        // Configurar el Timeline
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> actualizar()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    private void actualizar() {
        lblComida.setText("" + refugio.getCantidadComida());
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
    private ArrayList<String> getIdsEnZona(String zona){
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
                if (!tuneles[1].getEsperandoEntrar().isEmpty()) {
                    for (Humano h : tuneles[1].getEsperandoEntrar()) {
                        ids.add(h.getIdh());
                    }
                } else {
                    ids.add("");
                }
                break;
            case "tunel2":
                if (tuneles[1].getHumanoDentro() != null) {
                    ids.add(tuneles[1].getHumanoDentro().getIdh());
                } else {
                    ids.add("");
                }
                break;
            case "tunel3":
                if (!tuneles[1].getEsperandosalir().isEmpty()) {
                    for (Humano h : tuneles[1].getEsperandosalir()) {
                        ids.add(h.getIdh());
                    }
                } else {
                    ids.add("");
                }
                break;
            case "tunel4":
                if (!tuneles[2].getEsperandoEntrar().isEmpty()) {
                    for (Humano h : tuneles[2].getEsperandoEntrar()) {
                        ids.add(h.getIdh());
                    }
                } else {
                    ids.add("");
                }
                break;
            case "tunel5":
                if (tuneles[2].getHumanoDentro() != null) {
                    ids.add(tuneles[2].getHumanoDentro().getIdh());
                } else {
                    ids.add("");
                }
                break;
            case "tunel6":
                if (!tuneles[2].getEsperandosalir().isEmpty()) {
                    for (Humano h : tuneles[2].getEsperandosalir()) {
                        ids.add(h.getIdh());
                    }
                } else {
                    ids.add("");
                }
                break;
            case "tunel7":
                if (!tuneles[3].getEsperandoEntrar().isEmpty()) {
                    for (Humano h : tuneles[3].getEsperandoEntrar()) {
                        ids.add(h.getIdh());
                    }
                } else {
                    ids.add("");
                }
                break;
            case "tunel8":
                if (tuneles[3].getHumanoDentro() != null) {
                    ids.add(tuneles[3].getHumanoDentro().getIdh());
                } else {
                    ids.add("");
                }
                break;
            case "tunel9":
                if (!tuneles[3].getEsperandosalir().isEmpty()) {
                    for (Humano h : tuneles[3].getEsperandosalir()) {
                        ids.add(h.getIdh());
                    }
                } else {
                    ids.add("");
                }
                break;
            case "tunel10":
                if (!tuneles[0].getEsperandoEntrar().isEmpty()) {
                    for (Humano h : tuneles[0].getEsperandoEntrar()) {
                        ids.add(h.getIdh());
                    }
                } else {
                    ids.add("");
                }
                break;
            case "tunel11":
                if (tuneles[0].getHumanoDentro() != null) {
                    ids.add(tuneles[0].getHumanoDentro().getIdh());
                } else {
                    ids.add("");
                }
                break;
            case "tunel12":
                if (!tuneles[0].getEsperandosalir().isEmpty()) {
                    for (Humano h : tuneles[0].getEsperandosalir()) {
                        ids.add(h.getIdh());
                    }
                } else {
                    ids.add("");
                }
                break;
            case "zona1":
                if (!zonas[1].getHumanos().isEmpty()) {
                    for (Humano h : zonas[1].getHumanos()) {
                        ids.add(h.getIdh());
                    }
                } else {
                    ids.add("");
                }
                break;
            case "zona2":
                if (!zonas[1].getZombies().isEmpty()) {
                    for (Zombi z : zonas[1].getZombies()) {
                        ids.add(z.getIdz());
                    }
                } else {
                    ids.add("");
                }
                break;
            case "zona3":
                if (!zonas[2].getHumanos().isEmpty()) {
                    for (Humano h : zonas[2].getHumanos()) {
                        ids.add(h.getIdh());
                    }
                } else {
                    ids.add("");
                }
                break;
            case "zona4":
                if (!zonas[2].getZombies().isEmpty()) {
                    for (Zombi z : zonas[2].getZombies()) {
                        ids.add(z.getIdz());
                    }
                } else {
                    ids.add("");
                }
                break;
            case "zona5":
                if (!zonas[3].getHumanos().isEmpty()) {
                    for (Humano h : zonas[3].getHumanos()) {
                        ids.add(h.getIdh());
                    }
                } else {
                    ids.add("");
                }
                break;
            case "zona6":
                if (!zonas[3].getZombies().isEmpty()) {
                    for (Zombi z : zonas[3].getZombies()) {
                        ids.add(z.getIdz());
                    }
                } else {
                    ids.add("");
                }
                break;
            case "zona7":
                if (!zonas[0].getHumanos().isEmpty()) {
                    for (Humano h : zonas[0].getHumanos()) {
                        ids.add(h.getIdh());
                    }
                } else {
                    ids.add("");
                }
                break;
            case "zona8":
                if (!zonas[0].getZombies().isEmpty()) {
                    for (Zombi z : zonas[0].getZombies()) {
                        ids.add(z.getIdz());
                    }
                } else {
                    ids.add("");
                }
                break;
        }
        System.out.println("IDs encontrados: " + ids);
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
