package modelo;

public class Missao {

    private int id;
    private String titulo;
    private String descricao;
    private String dificuldade;
    private String recompensa;
    private String origem;
    private String status;
    private int campanhaId;

    public Missao() {}

    public Missao(int id, String titulo, String descricao,
              String dificuldade, String recompensa,
              String origem, String status) {
    this.id = id;
    this.titulo = titulo;
    this.descricao = descricao;
    this.dificuldade = dificuldade;
    this.recompensa = recompensa;
    this.origem = origem;
    this.status = status;
}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getDificuldade() { return dificuldade; }
    public void setDificuldade(String dificuldade) { this.dificuldade = dificuldade; }

    public String getRecompensa() { return recompensa; }
    public void setRecompensa(String recompensa) { this.recompensa = recompensa; }
    
    public String getOrigem() { return origem; }
    public void setOrigem(String origem) { this.origem = origem; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public int getCampanhaId() { return campanhaId; }
public void setCampanhaId(int campanhaId) { this.campanhaId = campanhaId; }

}
