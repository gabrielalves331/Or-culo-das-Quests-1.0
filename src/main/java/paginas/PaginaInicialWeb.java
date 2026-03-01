package paginas;

import dao.DAOGenerico;
import dao.GrafoDAO;
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
        setLayout(new BorderLayout());

        jfxPanel = new JFXPanel();
        add(jfxPanel, BorderLayout.CENTER);

        Platform.runLater(this::criarCena);

        setVisible(true);
    }

    private void criarCena() {

        WebView webView = new WebView();
        engine = webView.getEngine();

        engine.load(
            getClass()
            .getClassLoader()
            .getResource("paginainicial.html")
            .toExternalForm()
        );

        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {

                JSObject window = (JSObject) engine.executeScript("window");
                window.setMember("javaApp", this);

                System.out.println("Pagina Inicial carregada.");
            }
        });

        Scene scene = new Scene(webView);
        jfxPanel.setScene(scene);
    }

    // =========================
    // NAVEGAÇÕES
    // =========================

    public void irParaLogin() {
    new LoginWeb();  // volta como era antes
    dispose();
}

    // Acesso direto ao Grafo
 public void abrirGrafo() {
    String json = GrafoDAO.gerarJsonGrafo();
    new GrafoWeb(json, this);  // ✅ passa a referência da tela atual
    setVisible(false);
}

    public void sair() {
        System.exit(0);
    }

    // =========================
    // MAIN
    // =========================
    public static void main(String[] args) {

        dao.DAOGenerico.inicializarBanco();

        new PaginaInicialWeb();
    }
}