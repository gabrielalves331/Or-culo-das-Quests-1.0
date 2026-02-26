package modelo;

public class Campanha {

    private int id;
    private String nome;
    private int usuarioId;

    public Campanha() {}

    public Campanha(int id, String nome, int usuarioId) {
        this.id = id;
        this.nome = nome;
        this.usuarioId = usuarioId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
}
