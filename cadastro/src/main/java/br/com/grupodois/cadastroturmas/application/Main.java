package br.com.grupodois.cadastroturmas.application;

import br.com.grupodois.cadastroturmas.database.MongoConnection;
import br.com.grupodois.cadastroturmas.menu.MenuCurso;
import br.com.grupodois.cadastroturmas.repository.CursoRepository;
import br.com.grupodois.cadastroturmas.menu.MenuTurma;
import br.com.grupodois.cadastroturmas.repository.TurmaRepository;
import br.com.grupodois.cadastroturmas.menu.MenuPeriodo;
import br.com.grupodois.cadastroturmas.repository.PeriodoRepository;
import br.com.grupodois.cadastroturmas.menu.MenuDesafio;
import br.com.grupodois.cadastroturmas.repository.DesafioRepository;
import br.com.grupodois.cadastroturmas.menu.MenuAluno;
import br.com.grupodois.cadastroturmas.repository.AlunoRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            var database = MongoConnection.getDatabase();

            var cursoRepository = new CursoRepository(database);
            var menuCurso = new MenuCurso(scanner, cursoRepository);
            
            var turmaRepository = new TurmaRepository(database);
            var menuTurma = new MenuTurma(scanner, turmaRepository);
            
            var periodoRepository = new PeriodoRepository(database);
            var menuPeriodo = new MenuPeriodo(scanner, periodoRepository);

            var desafioRepository = new DesafioRepository(database);
            var menuDesafio = new MenuDesafio(scanner, desafioRepository);

            var alunoRepository = new AlunoRepository(database);
            var menuAluno = new MenuAluno(scanner, alunoRepository);

            int opcao;
            do {
                System.out.println("\n==== MENU PRINCIPAL ====");
                System.out.println("1. Menu de Cursos");
                System.out.println("2. Menu de Turma");
                System.out.println("3. Menu de Periodo");
                System.out.println("4. Menu de Desafio");
                System.out.println("5. Menu de Alunos");
                System.out.println("6. Sair");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();

                switch (opcao) {
                    case 1 -> menuCurso.exibir();
                    case 2 -> menuTurma.exibir();
                    case 3 -> menuPeriodo.exibir();
                    case 4 -> menuDesafio.exibir();
                    case 5 -> menuAluno.exibir();
                    case 6 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida!");
                }
            } while (opcao != 6);
        }
    }
}
