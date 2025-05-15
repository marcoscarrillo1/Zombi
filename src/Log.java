import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Semaphore;

import java.io.IOException;
import java.util.logging.*;

public class Log {

    private static final Logger logger = Logger.getLogger(Log.class.getName());

    static {
        try {
            logger.setUseParentHandlers(false);

            // Handler para consola
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
            consoleHandler.setFormatter(new SimpleFormatter());

            // Handler para archivo
            FileHandler fileHandler = new FileHandler("app.log", true); // true para añadir sin sobrescribir
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());

            // Agregar handlers
            logger.addHandler(consoleHandler);
            logger.addHandler(fileHandler);

            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("No se pudo configurar el logger: " + e.getMessage());
        }
    }
    public static Logger getLogger() {
        return logger;
    }

    public static void info(String message) {
        logger.info(message);
    }

}


