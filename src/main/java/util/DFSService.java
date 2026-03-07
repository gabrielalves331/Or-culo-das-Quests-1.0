package util;

import java.util.*;

public class DFSService {

    private Map<String, List<String>> adjacencia;

    public DFSService(Map<String, List<String>> adjacencia) {
        this.adjacencia = adjacencia;
    }

   public List<String> executarDFS(String inicio, String destino) {

    List<String> caminho = new ArrayList<>();
    Set<String> visitados = new HashSet<>();

    boolean encontrou = dfs(inicio, destino, visitados, caminho);

    if (!encontrou) {
        return new ArrayList<>(); // caminho vazio
    }

    return caminho;
}

private boolean dfs(String atual,
                    String destino,
                    Set<String> visitados,
                    List<String> caminho) {

    visitados.add(atual);
    caminho.add(atual);

    if (atual.equals(destino)) {
        return true;
    }

    if (!adjacencia.containsKey(atual)) {
        caminho.remove(caminho.size()-1);
        return false;
    }

    for (String vizinho : adjacencia.get(atual)) {

        if (!visitados.contains(vizinho)) {

            boolean encontrou = dfs(vizinho, destino, visitados, caminho);

            if (encontrou) return true;
        }
    }

    // backtracking
    caminho.remove(caminho.size()-1);
    return false;
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