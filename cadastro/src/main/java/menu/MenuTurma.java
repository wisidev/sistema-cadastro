package menu;

import model.Turma;
import repository.TurmaRepository;

import java.util.Scanner;

public class MenuTurma {
    private final Scanner scanner;
    private final TurmaRepository repository;

    public MenuTurma(Scanner scanner, TurmaRepository repository) {
        this.scanner = scanner;
        this.repository = repository;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n==== MENU TURMA ====");
            System.out.println("1. Adicionar Turma");
            System.out.println("2. Listar Turmas");
            System.out.println("3. Atualizar Turma");
            System.out.println("4. Remover Turma");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa o buffer

            switch (opcao) {
                case 1:
                    System.out.print("ID da turma: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nome da turma: ");
                    String nome = scanner.nextLine();
                    repository.adicionar(new Turma(id, nome));
                    break;
                case 2:
                    repository.listar();
                    break;
                case 3:
                    System.out.print("ID da turma a atualizar: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();
                    repository.atualizar(idAtualizar, novoNome);
                    break;
                case 4:
                    System.out.print("ID da turma a remover: ");
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
