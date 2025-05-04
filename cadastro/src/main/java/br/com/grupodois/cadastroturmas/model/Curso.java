package br.com.grupodois.cadastroturmas.model;

public class Curso {
    private int id;
    private String nome;

    public Curso(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
}
