import javafx.application.Platform;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ListaHilos {
    private ArrayList<Thread> lista = new ArrayList<>();
    private Text text;

    public ListaHilos(Text text) {
        this.text = text;
    }

    public synchronized void añadir(Thread i) {
        lista.add(i);
        imprimir();
    }

    public synchronized void fuera(Thread i) {
        lista.remove(i);
        imprimir();
    }

    private synchronized void imprimir() {
        StringBuilder contenido = new StringBuilder();

        for (Thread hilo : lista) {
            contenido.append(hilo.getName()).append(" ");
        }

        String textoFinal = contenido.toString();

        if (text != null) {
            Platform.runLater(() -> {
                // Verificamos que el nodo esté en escena
                if (text.getScene() != null) {
                    text.setText(textoFinal);
                }
            });
        }
    }

    public int getSize() {
        return lista.size();
    }

    public Thread get(int index){
        return lista.get(index);
    }
}