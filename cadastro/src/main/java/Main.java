import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("cadastro_turmas");

            // Operações de CRUD para Cursos
            MongoCollection<Document> cursos = database.getCollection("cursos");
            Document curso = new Document("id", 1).append("nome", "Sistemas de Informação");
            cursos.insertOne(curso);
            System.out.println("Curso cadastrado com sucesso!");

            // Operações de CRUD para Turmas
            MongoCollection<Document> turmas = database.getCollection("turmas");
            Document turma = new Document("id", 1).append("nome", "2023.1");
            turmas.insertOne(turma);
            System.out.println("Turma cadastrada com sucesso!");

            // Operações de CRUD para Períodos
            MongoCollection<Document> periodos = database.getCollection("periodos");
            Document periodo = new Document("id", 1).append("nome", "2020/1");
            periodos.insertOne(periodo);
            System.out.println("Período cadastrado com sucesso!");

            // Operações de CRUD para Alunos
            MongoCollection<Document> alunos = database.getCollection("alunos");
            Document aluno = new Document("nome", "Yuri Anonimo")
                    .append("ra", 12356)
                    .append("turma", "2023.1");
            alunos.insertOne(aluno);
            System.out.println("Aluno cadastrado com sucesso!");

            // Operações de CRUD para Desafios
            MongoCollection<Document> desafios = database.getCollection("desafios");
            Document desafio = new Document("curso", "Sistemas de Informação")
                    .append("periodo", "2020/1")
                    .append("turma", "2023.1")
                    .append("alunos", Arrays.asList("Yuri Anonimo"));  // Corrigido para uma lista
            desafios.insertOne(desafio);
            System.out.println("Desafio cadastrado com sucesso!");

            // Listagem de Cursos
            System.out.println("Listando cursos cadastrados:");
            for (Document doc : cursos.find()) {
                System.out.println(doc.toJson());
            }

            // Atualizar curso
            cursos.updateOne(Filters.eq("id", 1), Updates.set("nome", "Engenharia de Software"));
            System.out.println("Curso atualizado para Engenharia de Software");

            // Remover aluno
            alunos.deleteOne(Filters.eq("ra", 12356));
            System.out.println("Aluno removido com sucesso!");

            // Listar Alunos
            System.out.println("Listando alunos cadastrados:");
            for (Document doc : alunos.find()) {
                System.out.println(doc.toJson());
            }
        }
    }
}
