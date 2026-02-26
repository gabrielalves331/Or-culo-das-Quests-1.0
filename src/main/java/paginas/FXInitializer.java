package paginas;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

public class FXInitializer {

    private static boolean started = false;

    public static void init() {

        if (!started) {
            new JFXPanel(); // inicializa JavaFX
            Platform.setImplicitExit(false);
            started = true;
        }
    }
}
