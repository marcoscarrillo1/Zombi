import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

public class ListaHilos {
    private ArrayList<Thread> lista;
    private  TextArea TextArea;

    public ListaHilos(TextArea TextArea) {
        this.TextArea = TextArea;
        this.lista=new ArrayList<>();
    }

    public synchronized void añadir(Thread i) {
        lista.add(i);
        imprimir();
    }

    public synchronized void fuera(Thread i) {
        lista.remove(i);
        imprimir();
    }
    public List<String> getIds() {
        List<String> ids = new ArrayList<>();
        for (Thread hilo : lista) {
            ids.add(String.valueOf(hilo.getName()));
        }
        return ids;
    }

    public synchronized void imprimir() {
        List<String> ids = getIds();
        Platform.runLater(() -> TextArea.setText(String.join("\n", ids)));
        System.out.println("Actualizando TextArea con: " + ids);

    }


    public int getSize() {
        return lista.size();
    }

    public Thread get(int index){
        return lista.get(index);
    }
}