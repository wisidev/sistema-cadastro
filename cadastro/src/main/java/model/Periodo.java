package model;

public class Periodo {
    private int id;
    private String nome;

    public Periodo(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
}
