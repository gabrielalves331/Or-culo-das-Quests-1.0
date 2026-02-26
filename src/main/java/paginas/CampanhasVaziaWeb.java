package paginas;

public class CampanhasVaziaWeb extends BaseWebFrame {

    public CampanhasVaziaWeb() {
        setTitle("Nenhuma Campanha");
        setExtendedState(MAXIMIZED_BOTH);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        carregarPagina("campanha_vazia.html");
        setVisible(true);
    }

    public void criarCampanha() {
        new CriarCampanhaWeb();
        dispose();
    }
}
