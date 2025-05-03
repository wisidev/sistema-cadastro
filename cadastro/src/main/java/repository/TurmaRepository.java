package repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Turma;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class TurmaRepository {
    private final MongoCollection<Document> collection;

    public TurmaRepository(MongoDatabase database) {
        this.collection = database.getCollection("turmas");
    }

    public void adicionar(Turma turma) {
        Document doc = new Document("id", turma.getId()).append("nome", turma.getNome());
        collection.insertOne(doc);
        System.out.println("Turma cadastrada com sucesso!");
    }

    public void listar() {
        for (Document doc : collection.find()) {
            System.out.println("ID: " + doc.getInteger("id"));
            System.out.println("Nome: " + doc.getString("nome"));
            System.out.println("-----------------------");
        }
    }

    public void atualizar(int id, String novoNome) {
        collection.updateOne(Filters.eq("id", id), Updates.set("nome", novoNome));
        System.out.println("Turma atualizada com sucesso!");
    }

    public void remover(int id) {
        collection.deleteOne(Filters.eq("id", id));
        System.out.println("Turma removida com sucesso!");
    }
}
