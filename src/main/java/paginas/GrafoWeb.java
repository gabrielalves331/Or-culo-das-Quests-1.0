package paginas;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;

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

        JButton voltar = new JButton("Voltar");

        voltar.addActionListener(e -> {
            paginaInicial.setVisible(true); // 👈 REABRE
            dispose();                      // 👈 FECHA GRAFO
        });

        add(voltar, BorderLayout.SOUTH);
        add(jfxPanel, BorderLayout.CENTER);

        Platform.runLater(() -> {
            WebView webView = new WebView();
            WebEngine engine = webView.getEngine();

            engine.load(
                getClass().getClassLoader()
                .getResource("grafo.html")
                .toExternalForm()
            );

            engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                    
                    System.out.println("JSON enviado ao WebView: " + json);

                    String safeJson = json.replace("\\", "\\\\")
                                          .replace("'", "\\'")
                                          .replace("\"", "\\\"");

                    engine.executeScript("window.javaJson = \"" + safeJson + "\";");
                }
            });

            jfxPanel.setScene(new Scene(webView));
        });

        setVisible(true);
    }
}