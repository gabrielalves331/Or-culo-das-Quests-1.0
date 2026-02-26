package util;

import modelo.Usuario;
import modelo.Campanha;

public class Sessao {

    public static Usuario usuarioLogado;
    public static Campanha campanhaAtual;

    public static void encerrar() {
        usuarioLogado = null;
        campanhaAtual = null;
    }
}
