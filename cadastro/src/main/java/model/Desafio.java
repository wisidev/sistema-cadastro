package model;

public class Desafio {
    private int id;
    private String nome;
    private String descricao;

    public Desafio(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
}
