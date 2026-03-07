package paginas;

import util.Sessao;

public class HubWeb extends BaseWebFrame {

    public HubWeb() {
        setTitle("Hub da Guilda");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        carregarPagina("hub.html");

        setVisible(true);
        
          if (Sessao.usuarioLogado == null) {
    new LoginWeb();
    dispose();
    return;
}
    }

    public void addMissao() {
        new InsercaoWeb();
        dispose();
    }

    public void editarMissao() {
        new EdicaoescolhaWeb();
        dispose();
    }

    public void removerMissao() {
        new RemocaoWeb();
        dispose();
    }

    public void verMural() {
        new MuralWeb();
        dispose();
    }
    
    public void verRegistro() {
    new RegistroWeb(Sessao.campanhaAtual.getId());
    dispose();
}
    
    public String getNomeUsuario() {
    return Sessao.usuarioLogado.getNome();
}

    
     public void voltarcampanha(){
         new CampanhasWeb();
         dispose();
     }
      
    

}
