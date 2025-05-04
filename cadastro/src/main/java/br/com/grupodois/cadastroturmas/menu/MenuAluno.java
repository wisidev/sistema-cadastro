package br.com.grupodois.cadastroturmas.menu;

import br.com.grupodois.cadastroturmas.model.Aluno;
import br.com.grupodois.cadastroturmas.repository.AlunoRepository;

import java.util.Scanner;

public class MenuAluno {
    private final Scanner scanner;
    private final AlunoRepository repository;

    public MenuAluno(Scanner scanner, AlunoRepository repository) {
        this.scanner = scanner;
        this.repository = repository;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n==== MENU ALUNO ====");
            System.out.println("1. Adicionar Aluno");
            System.out.println("2. Listar Alunos");
            System.out.println("3. Atualizar Aluno");
            System.out.println("4. Remover Aluno");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa o buffer

            switch (opcao) {
                case 1:
                    System.out.print("ID do aluno: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nome do aluno: ");
                    String nome = scanner.nextLine();
                    System.out.print("RA do aluno: ");
                    int ra = scanner.nextInt();
                    repository.adicionar(new Aluno(id, nome, ra));
                    break;
                case 2:
                    repository.listar();
                    break;
                case 3:
                    System.out.print("ID do aluno a atualizar: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();
                    repository.atualizar(idAtualizar, novoNome);
                    break;
                case 4:
                    System.out.print("ID do aluno a remover: ");
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
