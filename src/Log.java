import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Semaphore;


public class Log {
    private static final Object lock = new Object();

    public static void escribir(String evento) {
        synchronized (lock) {
            try (FileWriter fw = new FileWriter("apocalipsis.txt", true)) {
                fw.write(LocalDateTime.now() + " - " + evento + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

