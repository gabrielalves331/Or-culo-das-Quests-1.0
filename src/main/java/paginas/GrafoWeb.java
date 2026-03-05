package paginas;

import dao.GrafoDAO;
import util.DFSService;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.sql.*;
import java.util.*;

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

   public String executarDFS(String inicio) {

    Map<String, List<String>> adjacencia = new HashMap<>();

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:oraculo_das_quests.db");
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(
                 "SELECT " +
                 "u.id as usuario_id, " +
                 "c.id as campanha_id, " +
                 "m.id as missao_id " +
                 "FROM usuarios u " +
                 "LEFT JOIN campanhas c ON c.usuario_id = u.id " +
                 "LEFT JOIN missoes m ON m.campanha_id = c.id"
         );) {

        while (rs.next()) {

            String usuarioId = "u" + rs.getInt("usuario_id");
            int campanhaInt = rs.getInt("campanha_id");
            int missaoInt = rs.getInt("missao_id");

            if (campanhaInt > 0) {
                String campanhaId = "c" + campanhaInt;

                adjacencia.putIfAbsent(usuarioId, new ArrayList<>());
                adjacencia.get(usuarioId).add(campanhaId);

                if (missaoInt > 0) {
                    String missaoId = "m" + missaoInt;

                    adjacencia.putIfAbsent(campanhaId, new ArrayList<>());
                    adjacencia.get(campanhaId).add(missaoId);
                }
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    DFSService dfs = new DFSService(adjacencia);

    List<String> visitados = dfs.executarDFS(inicio);

    return new com.google.gson.Gson().toJson(visitados);
    
}

    public void voltar() {
        new PaginaInicialWeb();
        dispose();
    }
}