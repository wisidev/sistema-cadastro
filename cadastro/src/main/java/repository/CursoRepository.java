package repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Curso;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class CursoRepository {
    private final MongoCollection<Document> collection;

    public CursoRepository(MongoDatabase database) {
        this.collection = database.getCollection("cursos");
    }

    public void adicionar(Curso curso) {
        Document doc = new Document("id", curso.getId()).append("nome", curso.getNome());
        collection.insertOne(doc);
        System.out.println("Curso cadastrado com sucesso!");
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
        System.out.println("Curso atualizado com sucesso!");
    }

    public void remover(int id) {
        collection.deleteOne(Filters.eq("id", id));
        System.out.println("Curso removido com sucesso!");
    }
}
