package br.com.grupodois.cadastroturmas.model;

public class Turma {
    private int id;
    private String nome;

    public Turma(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
