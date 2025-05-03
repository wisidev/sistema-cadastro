package repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Aluno;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class AlunoRepository {
    private final MongoCollection<Document> collection;

    public AlunoRepository(MongoDatabase database) {
        this.collection = database.getCollection("alunos");
    }

    public void adicionar(Aluno aluno) {
        Document doc = new Document("id", aluno.getId())
                .append("nome", aluno.getNome())
                .append("ra", aluno.getRa());
        collection.insertOne(doc);
        System.out.println("Aluno cadastrado com sucesso!");
    }

    public void listar() {
        for (Document doc : collection.find()) {
            System.out.println("ID: " + doc.getInteger("id"));
            System.out.println("Nome: " + doc.getString("nome"));
            System.out.println("RA: " + doc.getString("ra"));
            System.out.println("-----------------------");
        }
    }

    public void atualizar(int id, String novoNome) {
        collection.updateOne(Filters.eq("id", id), Updates.set("nome", novoNome));
        System.out.println("Aluno atualizado com sucesso!");
    }

    public void remover(int id) {
        collection.deleteOne(Filters.eq("id", id));
        System.out.println("Aluno removido com sucesso!");
    }
}
