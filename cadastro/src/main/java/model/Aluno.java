package model;

public class Aluno {
    private int id;
    private String nome;
    private String ra;

    public Aluno(int id, String nome, String ra) {
        this.id = id;
        this.nome = nome;
        this.ra = ra;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getRa() {
        return ra;
    }
}
