package menu;

import model.Periodo;
import repository.PeriodoRepository;

import java.util.Scanner;

public class MenuPeriodo {
    private final Scanner scanner;
    private final PeriodoRepository repository;

    public MenuPeriodo(Scanner scanner, PeriodoRepository repository) {
        this.scanner = scanner;
        this.repository = repository;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n==== MENU PERÍODO ====");
            System.out.println("1. Adicionar Período");
            System.out.println("2. Listar Períodos");
            System.out.println("3. Atualizar Período");
            System.out.println("4. Remover Período");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa o buffer

            switch (opcao) {
                case 1:
                    System.out.print("ID do período: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nome do período: ");
                    String nome = scanner.nextLine();
                    repository.adicionar(new Periodo(id, nome));
                    break;
                case 2:
                    repository.listar();
                    break;
                case 3:
                    System.out.print("ID do período a atualizar: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();
                    repository.atualizar(idAtualizar, novoNome);
                    break;
                case 4:
                    System.out.print("ID do período a remover: ");
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
