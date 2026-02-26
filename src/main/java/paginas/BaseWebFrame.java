package paginas;

import java.net.URL;
import java.awt.BorderLayout;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.concurrent.Worker;

import javax.swing.JFrame;
import netscape.javascript.JSObject;

public abstract class BaseWebFrame extends JFrame {

    protected WebEngine engine;

    protected void carregarPagina(String html) {

    setLayout(new BorderLayout());

    JFXPanel panel = new JFXPanel();
    add(panel, BorderLayout.CENTER);

    Platform.runLater(() -> {

        WebView view = new WebView();
        engine = view.getEngine();

        URL url = getClass().getResource("/" + html);
        if (url == null) {
            System.out.println("Arquivo NÃO encontrado: " + html);
            return;
        }

        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {

            if (newState == Worker.State.SUCCEEDED) {

                // injeta o Java na página
                JSObject window = (JSObject) engine.executeScript("window");
                window.setMember("javaApp", this);

                engine.executeScript("console.log('javaApp injetado')");

                // ================= MISSÕES =================

                if (this instanceof MuralWeb mural) {
                    String json = mural.buscarMissoes();
                    engine.executeScript("carregarMissoes(" + json + ");");
                }

                if (this instanceof EdicaoWeb edicao) {
                    String json = edicao.buscarMissao();
                    engine.executeScript("carregarMissao(" + json + ");");
                }

                if (this instanceof EdicaoescolhaWeb escolha) {
                    String json = escolha.buscarMissoes();
                    engine.executeScript("carregarMissoes(" + json + ");");
                }

                if (this instanceof RemocaoWeb remocao) {
                    String json = remocao.buscarMissoes();
                    engine.executeScript("carregarMissoes(" + json + ");");
                }

                // ================= CAMPANHAS =================

                if (this instanceof CampanhasWeb campanhas) {
                    String json = campanhas.buscarCampanhas();
                    engine.executeScript("carregarCampanhas(" + json + ");");
                }
 
            }
        });

        engine.load(url.toExternalForm());
        panel.setScene(new Scene(view));
    });
}

    
    public BaseWebFrame() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
    }
}
