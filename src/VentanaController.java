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
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class VentanaController implements Initializable {

    @FXML
    private Juegozombie juego;
    @FXML
    private Label lblComida;

    private Refugio refugio;

    private MonitorZombi monitor;

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
    private ArrayList<ListaHilos> enzonariesgo = new ArrayList<>();
    private ArrayList<ListaHilos> zonariesgoZZ = new ArrayList<>();
    private ArrayList<ListaHilos> irtuneles=new ArrayList<>();
    private ArrayList<ListaHilos> volvertuneles=new ArrayList<>();
    private ArrayList<ListaHilos> dentrotuenel=new ArrayList<>();
    private int cantidadHumanos;

    public void setCantidadHumanos(int cantidadHumanos) {
        this.cantidadHumanos = cantidadHumanos;
    }


    @FXML private Button botonPausa;

    // Listas de hilos para cada celda (zona)
    private List<ListaHilos> listaHilosPorZona;

    @FXML
    private void manejarPausa() {
        if (juego.estaPausado()) {
            juego.reanudar();
            botonPausa.setText("Pausar");
            try {
                monitor.reanudar(); // Llamada RMI para reanudar
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            juego.pausar();
            botonPausa.setText("Reanudar");
            try {
                monitor.pausar(); // Llamada RMI para pausar
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Inicializar las listas de hilos por zona
        listaHilosPorZona = new ArrayList<>();
        listaHilosPorZona.add(zonaComedor); // Comedor
        listaHilosPorZona.add(zonaDescanso); // Descanso
        listaHilosPorZona.add(zonaComun); // Zona Común


        ListaHilos zona1=new ListaHilos(textAreasZonaRiesgo1);
        ListaHilos zona2=new ListaHilos(textAreasZonaRiesgo2);
        ListaHilos zona3=new ListaHilos(textAreasZonaRiesgo3);
        ListaHilos zona4=new ListaHilos(textAreasZonaRiesgo4);
        ListaHilos zona5=new ListaHilos(textAreasZonaRiesgo5);
        ListaHilos zona6=new ListaHilos(textAreasZonaRiesgo6);
        ListaHilos zona7=new ListaHilos(textAreasZonaRiesgo7);
        ListaHilos zona8=new ListaHilos(textAreasZonaRiesgo8);

        zonariesgoZZ.addAll(Arrays.asList(zona2,zona4,zona6,zona8));
        enzonariesgo.addAll(Arrays.asList(zona1,zona3,zona5,zona7));
        ListaHilos tunel1=new ListaHilos( textAreaTunel1);
        ListaHilos tunel2=new ListaHilos( textAreaTunel2);
        ListaHilos tunel3=new ListaHilos( textAreaTunel3);
        ListaHilos tunel4=new ListaHilos( textAreaTunel4);
        ListaHilos tunel5=new ListaHilos( textAreaTunel5);
        ListaHilos tunel6=new ListaHilos( textAreaTunel6);
        ListaHilos tunel7=new ListaHilos( textAreaTunel7);
        ListaHilos tunel8=new ListaHilos( textAreaTunel8);
        ListaHilos tunel9=new ListaHilos( textAreaTunel9);
        ListaHilos tunel10=new ListaHilos( textAreaTunel10);
        ListaHilos tunel11=new ListaHilos( textAreaTunel11);
        ListaHilos tunel12=new ListaHilos( textAreaTunel12);

        irtuneles.addAll(Arrays.asList(tunel1,tunel4,tunel7,tunel10));
        dentrotuenel.addAll(Arrays.asList(tunel2,tunel5,tunel8,tunel11));
        volvertuneles.addAll(Arrays.asList(tunel3,tunel6,tunel9,tunel12));


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
        for (ListaHilos l:irtuneles){
            l.imprimir();
        }
        for (ListaHilos l : dentrotuenel) {
            l.imprimir();
        }
        for (ListaHilos l : volvertuneles) {
            l.imprimir();
        }

        // Zonas de riesgo
        for (ListaHilos l : enzonariesgo) {
            l.imprimir();
        }
        for (ListaHilos l : zonariesgoZZ) {
            l.imprimir();
        }
        lblComida.setText(""+refugio.getComidaDisponible());
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
            default: return -1;
        }
    }
    public void iniciarSimulacion() {
        int comida = 0;


        this.juego = new Juegozombie(zonaComun, zonaDescanso, zonaComedor,irtuneles,volvertuneles,dentrotuenel, comida, enzonariesgo, zonariesgoZZ);
        this.refugio = new Refugio(irtuneles,dentrotuenel,volvertuneles,juego);
        refugio.setJuego(juego);
        Servidor.iniciar(juego,refugio);
        try {
            monitor = (MonitorZombi) Naming.lookup("rmi://127.0.0.1/MonitorZombi");
            System.out.println("Conectado a MonitorZombi RMI.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al conectar con el MonitorZombi: " + e.getMessage());
        }
        Zombi pacienteCero = new Zombi("Z0000", juego,refugio);
        pacienteCero.start();

        new Thread(() -> {
            for (int i = 1; i <= cantidadHumanos; i++) {
                String id = String.format("H%04d", i);
                Humano h = new Humano(id, juego, refugio);
                h.start();
                try {
                    Thread.sleep(500 + new java.util.Random().nextInt(1500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
