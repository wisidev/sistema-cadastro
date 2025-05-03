package model;

public class Aluno {
    private int id;
    private String nome;
    private int ra;

    public Aluno(int id, String nome, int ra) {
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

    public int getRa() {
        return ra;
    }
}
