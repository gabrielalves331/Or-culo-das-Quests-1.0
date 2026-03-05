package util;

import java.util.List;
import modelo.Missao;

public class ProgressaoService {

    public static boolean todasFaceisConcluidas(List<Missao> missoes) {
        return missoes.stream()
                .filter(m -> m.getDificuldade().equalsIgnoreCase("FACIL"))
                .allMatch(m -> m.getStatus().equalsIgnoreCase("CONCLUIDA"));
    }

    public static boolean todasMediasConcluidas(List<Missao> missoes) {
        return missoes.stream()
                .filter(m -> m.getDificuldade().equalsIgnoreCase("MEDIA"))
                .allMatch(m -> m.getStatus().equalsIgnoreCase("CONCLUIDA"));
    }

    public static boolean podeAcessarMissao(Missao missao, List<Missao> todas) {

        if (missao.getDificuldade().equalsIgnoreCase("FACIL")) {
            return true;
        }

        if (missao.getDificuldade().equalsIgnoreCase("MEDIA")) {
            return todasFaceisConcluidas(todas);
        }

        if (missao.getDificuldade().equalsIgnoreCase("DIFICIL")) {
            return todasMediasConcluidas(todas);
        }

        return false;
    }
    
    public static void executarProgressao(List<Missao> missoes) {

    System.out.println("Explorando missões FÁCEIS...");
    missoes.stream()
            .filter(m -> m.getDificuldade().equalsIgnoreCase("FACIL"))
            .forEach(m -> System.out.println(m.getTitulo()));

    if (todasFaceisConcluidas(missoes)) {
        System.out.println("Explorando missões MÉDIAS...");
        missoes.stream()
                .filter(m -> m.getDificuldade().equalsIgnoreCase("MEDIA"))
                .forEach(m -> System.out.println(m.getTitulo()));
    }

    if (todasMediasConcluidas(missoes)) {
        System.out.println("Explorando missões DIFÍCEIS...");
        missoes.stream()
                .filter(m -> m.getDificuldade().equalsIgnoreCase("DIFICIL"))
                .forEach(m -> System.out.println(m.getTitulo()));
    }
}
}