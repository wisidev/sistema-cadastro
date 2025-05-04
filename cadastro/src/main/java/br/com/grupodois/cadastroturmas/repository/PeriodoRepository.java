package br.com.grupodois.cadastroturmas.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import br.com.grupodois.cadastroturmas.model.Periodo;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class PeriodoRepository {
    private final MongoCollection<Document> collection;

    public PeriodoRepository(MongoDatabase database) {
        this.collection = database.getCollection("periodos");
    }

    public void adicionar(Periodo periodo) {
        Document doc = new Document("id", periodo.getId()).append("nome", periodo.getNome());
        collection.insertOne(doc);
        System.out.println("Período cadastrado com sucesso!");
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
        System.out.println("Período atualizado com sucesso!");
    }

    public void remover(int id) {
        collection.deleteOne(Filters.eq("id", id));
        System.out.println("Período removido com sucesso!");
    }
}
