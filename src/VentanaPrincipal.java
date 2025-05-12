import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.cell.PropertyValueFactory;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.text.Text;


public class VentanaPrincipal extends Application {


    private int cantidadHumanos;

    public VentanaPrincipal(int cantidadHumanos) {
        this.cantidadHumanos = cantidadHumanos;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Ventana.fxml"));
        Parent root = loader.load();
        VentanaController controller = loader.getController();

        controller.setCantidadHumanos(cantidadHumanos);

        // Iniciar lógica de simulación desde el controlador
        controller.iniciarSimulacion();

        Scene scene = new Scene(root, 1000, 900);
        stage.setTitle("Simulador de Apocalipsis Zombi");
        stage.setScene(scene);
        stage.show();
    }}


