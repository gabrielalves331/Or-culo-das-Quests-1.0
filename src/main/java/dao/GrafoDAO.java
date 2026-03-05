package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;

public class GrafoDAO {

    public static String gerarJsonGrafo() {

        StringBuilder nodes = new StringBuilder();
        StringBuilder edges = new StringBuilder();

        Set<String> addedNodes = new HashSet<>();
        Set<String> addedEdges = new HashSet<>();

        List<String> faceis = new ArrayList<>();
        List<String> medias = new ArrayList<>();
        List<String> dificeis = new ArrayList<>();

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
                "m.titulo as missao_titulo, " +
                "m.dificuldade as missao_dificuldade, " +
                "m.status as missao_status " +
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
                String missaoDificuldade = rs.getString("missao_dificuldade");
                String missaoStatus = rs.getString("missao_status");

                // --------- NÓS ---------

                if (usuarioId > 0 && addedNodes.add("u" + usuarioId)) {
                    nodes.append("{\"id\":\"u").append(usuarioId)
                         .append("\",\"label\":\"").append(usuarioNome)
                         .append("\",\"group\":\"usuario\"},");
                }

                if (campanhaId > 0 && addedNodes.add("c" + campanhaId)) {
                    nodes.append("{\"id\":\"c").append(campanhaId)
                         .append("\",\"label\":\"").append(campanhaNome)
                         .append("\",\"group\":\"campanha\"},");
                }

                if (missaoId > 0) {

                    String nodeId = "m" + missaoId;

                    if (addedNodes.add(nodeId)) {

                        nodes.append("{\"id\":\"").append(nodeId)
                             .append("\",\"label\":\"").append(missaoTitulo)
                             .append("\",\"group\":\"missao\",")
                             .append("\"dificuldade\":\"").append(missaoDificuldade).append("\",")
                             .append("\"status\":\"").append(missaoStatus).append("\"},");
                    }

                    if (missaoDificuldade != null) {
                        if (missaoDificuldade.equalsIgnoreCase("FACIL")) {
                            faceis.add(nodeId);
                        } else if (missaoDificuldade.equalsIgnoreCase("MEDIA")) {
                            medias.add(nodeId);
                        } else if (missaoDificuldade.equalsIgnoreCase("DIFICIL")) {
                            dificeis.add(nodeId);
                        }
                    }
                }

                // --------- ARESTAS BASE ---------

                if (usuarioId > 0 && campanhaId > 0 &&
                        addedEdges.add("u" + usuarioId + "c" + campanhaId)) {

                    edges.append("{\"from\":\"u").append(usuarioId)
                         .append("\",\"to\":\"c").append(campanhaId)
                         .append("\"},");
                }

                if (campanhaId > 0 && missaoId > 0 &&
                        addedEdges.add("c" + campanhaId + "m" + missaoId)) {

                    edges.append("{\"from\":\"c").append(campanhaId)
                         .append("\",\"to\":\"m").append(missaoId)
                         .append("\"},");
                }
            }

            // --------- HIERARQUIA ---------

            for (String f : faceis) {
                for (String m : medias) {
                    if (addedEdges.add(f + m)) {
                        edges.append("{\"from\":\"").append(f)
                             .append("\",\"to\":\"").append(m)
                             .append("\"},");
                    }
                }
            }

            for (String m : medias) {
                for (String d : dificeis) {
                    if (addedEdges.add(m + d)) {
                        edges.append("{\"from\":\"").append(m)
                             .append("\",\"to\":\"").append(d)
                             .append("\"},");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // --------- FINALIZAR JSON ---------

        if (nodes.length() > 1 && nodes.charAt(nodes.length() - 1) == ',') {
            nodes.deleteCharAt(nodes.length() - 1);
        }

        if (edges.length() > 1 && edges.charAt(edges.length() - 1) == ',') {
            edges.deleteCharAt(edges.length() - 1);
        }

        nodes.append("]");
        edges.append("]");

        return "{ \"nodes\": " + nodes +
               ", \"edges\": " + edges + " }";
    }
}