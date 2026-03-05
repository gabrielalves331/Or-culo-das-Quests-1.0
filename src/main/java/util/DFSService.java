package util;

import java.util.*;

public class DFSService {

    private Map<String, List<String>> adjacencia;

    public DFSService(Map<String, List<String>> adjacencia) {
        this.adjacencia = adjacencia;
    }

    public List<String> executarDFS(String inicio) {

        List<String> visitados = new ArrayList<>();
        Set<String> marcados = new HashSet<>();

        dfs(inicio, marcados, visitados);

        return visitados;
    }

    private void dfs(String atual,
                     Set<String> marcados,
                     List<String> visitados) {

        if (marcados.contains(atual)) return;

        marcados.add(atual);
        visitados.add(atual);

        if (!adjacencia.containsKey(atual)) return;

        for (String vizinho : adjacencia.get(atual)) {
            dfs(vizinho, marcados, visitados);
        }
    }
}