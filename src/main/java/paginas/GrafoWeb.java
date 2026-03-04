package paginas;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import netscape.javascript.JSObject;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class GrafoWeb extends JFrame {

    private PaginaInicialWeb paginaInicial;

    public GrafoWeb(String json, PaginaInicialWeb paginaInicial) {

        this.paginaInicial = paginaInicial;

        setTitle("Estrutura em Grafo");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JFXPanel jfxPanel = new JFXPanel();
        add(jfxPanel, BorderLayout.CENTER);

        Platform.runLater(() -> {

            WebView webView = new WebView();
            WebEngine engine = webView.getEngine();

            engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {

    if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {

        JSObject window = (JSObject) engine.executeScript("window");
        window.setMember("javaApp", this);

        System.out.println("JSON enviado ao WebView: " + json);

        String jsonSeguro = json.replace("'", "\\'");

        engine.executeScript("carregarGrafo('" + jsonSeguro + "');");
    }
});

            engine.load(
                getClass()
                .getClassLoader()
                .getResource("grafo.html")
                .toExternalForm()
            );

            jfxPanel.setScene(new Scene(webView));
        });

        setVisible(true);
    }
     public void voltar() {
        new PaginaInicialWeb();
        dispose();
    }
}