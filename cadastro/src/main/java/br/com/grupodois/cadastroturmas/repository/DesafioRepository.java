package br.com.grupodois.cadastroturmas.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import br.com.grupodois.cadastroturmas.model.Desafio;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class DesafioRepository {
    private final MongoCollection<Document> collection;

    public DesafioRepository(MongoDatabase database) {
        this.collection = database.getCollection("desafios");
    }

    public void adicionar(Desafio desafio) {
        Document doc = new Document("id", desafio.getId())
                .append("nome", desafio.getNome())
                .append("curso", desafio.getCurso())
                .append("periodo", desafio.getPeriodo())
                .append("turma", desafio.getTurma())
                .append("aluno", desafio.getAluno());
        collection.insertOne(doc);
        System.out.println("Desafio cadastrado com sucesso!");
    }

    public void listar() {
        for (Document doc : collection.find()) {
            System.out.println("ID: " + doc.getInteger("id"));
            System.out.println("Nome: " + doc.getString("nome"));
            System.out.println("Curso: " + doc.getString("curso"));
            System.out.println("Período: " + doc.getString("periodo"));
            System.out.println("Turma: " + doc.getString("turma"));
            System.out.println("Aluno: " + doc.getString("aluno"));
            System.out.println("-----------------------");
        }
    }

    public void atualizar(int id, String novoNome, String novoCurso, String novoPeriodo, String novaTurma, String novoAluno) {
        collection.updateOne(Filters.eq("id", id), Updates.combine(
            Updates.set("nome", novoNome),
            Updates.set("curso", novoCurso),
            Updates.set("periodo", novoPeriodo),
            Updates.set("turma", novaTurma),
            Updates.set("aluno", novoAluno)
        ));
        System.out.println("Desafio atualizado com sucesso!");
    }

    public void remover(int id) {
        collection.deleteOne(Filters.eq("id", id));
        System.out.println("Desafio removido com sucesso!");
    }
}
