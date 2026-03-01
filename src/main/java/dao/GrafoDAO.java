package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class GrafoDAO {

    public static String gerarJsonGrafo() {

        StringBuilder nodes = new StringBuilder();
        StringBuilder edges = new StringBuilder();
        
        // 👈 Usamos Sets para evitar nós e arestas duplicados
        Set<String> addedNodes = new HashSet<>();
        Set<String> addedEdges = new HashSet<>();

        nodes.append("[");
        edges.append("[");

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:oraculo_das_quests.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
            "SELECT " +
            "u.id as usuario_id, " +
            "u.nome as usuario_nome, " +
            "c.id as campanha_id, " +
            "c.nome as campanha_nome, " +
            "m.id as missao_id, " +
            "m.titulo as missao_titulo " + 
            "FROM usuarios u " +
            "LEFT JOIN campanhas c ON c.usuario_id = u.id " +
            "LEFT JOIN missoes m ON m.campanha_id = c.id"
        );) {

            while (rs.next()) {
                int usuarioId = rs.getInt("usuario_id");
                String usuarioNome = rs.getString("usuario_nome");
                int campanhaId = rs.getInt("campanha_id");
                String campanhaNome = rs.getString("campanha_nome");
                int missaoId = rs.getInt("missao_id");
                String missaoTitulo = rs.getString("missao_titulo");

                // 👈 PRINT DE DEPURAÇÃO PARA VER DADOS NO TERMINAL
                System.out.println("Linha SQL: U=" + usuarioNome + " C=" + campanhaNome + " M=" + missaoTitulo);

                // --- Adicionar Nós ---
                // Usuário
                if (usuarioId > 0 && addedNodes.add("u" + usuarioId)) {
                    nodes.append("{\"id\":\"u" + usuarioId + "\",\"label\":\"" + usuarioNome + "\",\"group\":\"usuario\"},");
                }
                // Campanha
                if (campanhaId > 0 && addedNodes.add("c" + campanhaId)) {
                    nodes.append("{\"id\":\"c" + campanhaId + "\",\"label\":\"" + campanhaNome + "\",\"group\":\"campanha\"},");
                }
                // Missão
                if (missaoId > 0 && addedNodes.add("m" + missaoId)) {
                    nodes.append("{\"id\":\"m" + missaoId + "\",\"label\":\"" + missaoTitulo + "\",\"group\":\"missao\"},");
                }

                // --- Adicionar Arestas ---
                // U -> C
                if (usuarioId > 0 && campanhaId > 0 && addedEdges.add("u" + usuarioId + "c" + campanhaId)) {
                    edges.append("{\"from\":\"u" + usuarioId + "\",\"to\":\"c" + campanhaId + "\"},");
                }
                // C -> M
                if (campanhaId > 0 && missaoId > 0 && addedEdges.add("c" + campanhaId + "m" + missaoId)) {
                    edges.append("{\"from\":\"c" + campanhaId + "\",\"to\":\"m" + missaoId + "\"},");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // --- Finalizar JSON ---
        if (nodes.length() > 1 && nodes.charAt(nodes.length() - 1) == ',') {
            nodes.deleteCharAt(nodes.length() - 1);
        }
        if (edges.length() > 1 && edges.charAt(edges.length() - 1) == ',') {
            edges.deleteCharAt(edges.length() - 1);
        }

        nodes.append("]");
        edges.append("]");

        String finalJson = "{ \"nodes\": " + nodes.toString() + ", \"edges\": " + edges.toString() + " }";
        System.out.println("JSON Final: " + finalJson);
        return finalJson;
    }
}