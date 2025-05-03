package menu;

import model.Desafio;
import repository.DesafioRepository;

import java.util.Scanner;

public class MenuDesafio {
    private final Scanner scanner;
    private final DesafioRepository repository;

    public MenuDesafio(Scanner scanner, DesafioRepository repository) {
        this.scanner = scanner;
        this.repository = repository;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n==== MENU DESAFIO ====");
            System.out.println("1. Adicionar Desafio");
            System.out.println("2. Listar Desafios");
            System.out.println("3. Atualizar Desafio");
            System.out.println("4. Remover Desafio");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa o buffer

            switch (opcao) {
                case 1:
                    System.out.print("ID do desafio: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nome do desafio: ");
                    String nome = scanner.nextLine();
                    System.out.print("Curso: ");
                    String curso = scanner.nextLine();
                    System.out.print("Período: ");
                    String periodo = scanner.nextLine();
                    System.out.print("Turma: ");
                    String turma = scanner.nextLine();
                    System.out.print("Aluno: ");
                    String aluno = scanner.nextLine();
                    repository.adicionar(new Desafio(id, nome, curso, periodo, turma, aluno));
                    break;
                case 2:
                    repository.listar();
                    break;
                case 3:
                    System.out.print("ID do desafio a atualizar: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();
                    System.out.print("Novo curso: ");
                    String novoCurso = scanner.nextLine();
                    System.out.print("Novo período: ");
                    String novoPeriodo = scanner.nextLine();
                    System.out.print("Nova turma: ");
                    String novaTurma = scanner.nextLine();
                    System.out.print("Novo aluno: ");
                    String novoAluno = scanner.nextLine();
                    repository.atualizar(idAtualizar, novoNome, novoCurso, novoPeriodo, novaTurma, novoAluno);
                    break;
                case 4:
                    System.out.print("ID do desafio a remover: ");
                    int idRemover = scanner.nextInt();
                    repository.remover(idRemover);
                    break;
                case 5:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 5);
    }
}
