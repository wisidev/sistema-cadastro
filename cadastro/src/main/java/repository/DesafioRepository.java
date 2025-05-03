package repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Desafio;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class DesafioRepository {
    private final MongoCollection<Document> collection;

    public DesafioRepository(MongoDatabase database) {
        this.collection = database.getCollection("desafios");
    }

    public void adicionar(Desafio desafio) {
        Document doc = new Document("id", desafio.getId()).append("nome", desafio.getNome())
                .append("descricao", desafio.getDescricao());
        collection.insertOne(doc);
        System.out.println("Desafio cadastrado com sucesso!");
    }

    public void listar() {
        for (Document doc : collection.find()) {
            System.out.println("ID: " + doc.getInteger("id"));
            System.out.println("Nome: " + doc.getString("nome"));
            System.out.println("Descrição: " + doc.getString("descricao"));
            System.out.println("-----------------------");
        }
    }

    public void atualizar(int id, String novoNome, String novaDescricao) {
        collection.updateOne(Filters.eq("id", id), Updates.set("nome", novoNome));
        System.out.println("Desafio atualizado com sucesso!");
    }

    public void remover(int id) {
        collection.deleteOne(Filters.eq("id", id));
        System.out.println("Desafio removido com sucesso!");
    }
}
