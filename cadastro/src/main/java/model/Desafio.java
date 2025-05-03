package model;

public class Desafio {
    private int id;
    private String nome;
    private String curso;
    private String periodo;
    private String turma;
    private String aluno;

    public Desafio(int id, String nome, String curso, String periodo, String turma, String aluno) {
        this.id = id;
        this.nome = nome;
        this.curso = curso;
        this.periodo = periodo;
        this.turma = turma;
        this.aluno = aluno;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCurso() { return curso; }
    public String getPeriodo() { return periodo; }
    public String getTurma() { return turma; }
    public String getAluno() { return aluno; }
}
