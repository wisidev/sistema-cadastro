package menu;

import model.Curso;
import repository.CursoRepository;

import java.util.Scanner;

public class MenuCurso {
    private final Scanner scanner;
    private final CursoRepository repository;

    public MenuCurso(Scanner scanner, CursoRepository repository) {
        this.scanner = scanner;
        this.repository = repository;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n==== MENU CURSO ====");
            System.out.println("1. Adicionar Curso");
            System.out.println("2. Listar Cursos");
            System.out.println("3. Atualizar Curso");
            System.out.println("4. Remover Curso");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa o buffer

            switch (opcao) {
                case 1:
                    System.out.print("ID do curso: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nome do curso: ");
                    String nome = scanner.nextLine();
                    repository.adicionar(new Curso(id, nome));
                    break;
                case 2:
                    repository.listar();
                    break;
                case 3:
                    System.out.print("ID do curso a atualizar: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();
                    repository.atualizar(idAtualizar, novoNome);
                    break;
                case 4:
                    System.out.print("ID do curso a remover: ");
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
