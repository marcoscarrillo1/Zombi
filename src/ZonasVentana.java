import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ZonasVentana {
    public static void mostrarVentana() {
        Stage ventanaZonas = new Stage();
        ventanaZonas.setTitle("Ubicación de Humanos y Zombis");

        // Crear la tabla para cada zona
        TableView<ZonaInfo> tablaZona1 = crearTablaZona(1);
        TableView<ZonaInfo> tablaZona2 = crearTablaZona(2);
        TableView<ZonaInfo> tablaZona3 = crearTablaZona(3);
        TableView<ZonaInfo> tablaZona4 = crearTablaZona(4);

        // Añadir las tablas al layout
        BorderPane root = new BorderPane();
        root.setTop(new Label("Cantidad de Humanos y Zombis en cada zona"));
        root.setLeft(tablaZona1);
        root.setCenter(tablaZona2);
        root.setRight(tablaZona3);
        root.setBottom(tablaZona4);

        Scene scene = new Scene(root, 600, 500);
        ventanaZonas.setScene(scene);
        ventanaZonas.show();

        // Iniciar el proceso de actualización en tiempo real
        iniciarActualizador(tablaZona1, tablaZona2, tablaZona3, tablaZona4);
    }

    private static TableView<ZonaInfo> crearTablaZona(int zonaId) {
        TableView<ZonaInfo> tabla = new TableView<>();
        tabla.setStyle("-fx-border-color: black; -fx-pref-width: 150;");

        TableColumn<ZonaInfo, String> colZona = new TableColumn<>("Zona " + zonaId);
        colZona.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty("Zona " + zonaId));

        TableColumn<ZonaInfo, Integer> colHumanos = new TableColumn<>("Humanos");
        colHumanos.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getHumanos()).asObject());

        TableColumn<ZonaInfo, Integer> colZombis = new TableColumn<>("Zombis");
        colZombis.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getZombis()).asObject());

        tabla.getColumns().addAll(colZona, colHumanos, colZombis);
        return tabla;
    }

    private static void iniciarActualizador(TableView<ZonaInfo>... tablas) {
        new Thread(() -> {
            while (true) {
                for (int i = 0; i < 4; i++) {
                    ZonaInsegura zona = ZonaInsegura.elegirAleatoria();
                    int humanos = zona.getHumanos().size();
                    int zombis = zona.getZombis().size();

                    ZonaInfo zonaInfo = new ZonaInfo(humanos, zombis);
                    tablas[i].getItems().clear();
                    tablas[i].getItems().add(zonaInfo);
                }

                try {
                    Thread.sleep(1000); // Actualización cada 1 segundo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static class ZonaInfo {
        private final int humanos;
        private final int zombis;

        public ZonaInfo(int humanos, int zombis) {
            this.humanos = humanos;
            this.zombis = zombis;
        }

        public int getHumanos() {
            return humanos;
        }

        public int getZombis() {
            return zombis;
        }
    }
    public BorderPane getRoot() {
        BorderPane root = new BorderPane();
        root.setTop(new Label("Cantidad de Humanos y Zombis en cada zona"));
        root.setLeft(crearTablaZona(1));
        root.setCenter(crearTablaZona(2));
        root.setRight(crearTablaZona(3));
        root.setBottom(crearTablaZona(4));
        return root;
    }

}
