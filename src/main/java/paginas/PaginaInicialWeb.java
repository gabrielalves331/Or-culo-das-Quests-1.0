package paginas;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.concurrent.Worker;
import netscape.javascript.JSObject;

public class PaginaInicialWeb extends JFrame {

    private JFXPanel jfxPanel;
    private WebEngine engine;

    public PaginaInicialWeb() {
        setTitle("O Oráculo das Quests");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jfxPanel = new JFXPanel(); 
        add(jfxPanel, BorderLayout.CENTER);

        Platform.runLater(this::criarCena);

        setVisible(true);
    }

    private void criarCena() {
        WebView webView = new WebView();
        engine = webView.getEngine();

        engine.load(getClass().getResource("/paginainicial.html").toExternalForm());
        
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) engine.executeScript("window");
                window.setMember("javaApp", this);
                System.out.println("javaApp injetado");
            }
        });

        engine.locationProperty().addListener((obs, oldLoc, newLoc) -> {
            if (newLoc.endsWith("iniciar")) {
                Platform.runLater(() -> {
                    new HubWeb().setVisible(true);
                    dispose();
                });
            }
        });

        Scene scene = new Scene(webView);
        jfxPanel.setScene(scene);
    }

    // MAIN ÚNICO E CORRIGIDO
    public static void main(String[] args) {
        // 1. Inicializa o banco de dados (Cria o arquivo .db e tabelas)
        dao.DAOGenerico.inicializarBanco(); 
        
        // 2. Abre a interface
        new PaginaInicialWeb();
    }
    
    public void sair() {
        System.exit(0);
    }

    public void irParaLogin() {
        new LoginWeb();
        dispose();
    }
}