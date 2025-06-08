package br.com.grupodois.cadastroturmas.repository;

import br.com.grupodois.cadastroturmas.database.RedisCache;
import br.com.grupodois.cadastroturmas.model.Turma;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TurmaRepository {
    private final MongoCollection<Document> collection;
    private final RedisCache redisCache;
    private final Gson gson;
    private final String CACHE_KEY = "turmas:listar";

    public TurmaRepository(MongoDatabase database) {
        this.collection = database.getCollection("turmas");
        this.redisCache = new RedisCache();
        this.gson = new Gson();
    }

    public void adicionar(Turma turma) {
        long start = System.currentTimeMillis();

        Document doc = new Document("id", turma.getId())
                .append("nome", turma.getNome());
        collection.insertOne(doc);
        redisCache.del(CACHE_KEY);

        System.out.println("Turma cadastrada com sucesso!");

        long duration = System.currentTimeMillis() - start;
        System.out.println("Tempo de execução (ADICIONAR): " + duration + " ms");
    }

    public void listar() {
        long start = System.currentTimeMillis();

        String jsonCache = redisCache.get(CACHE_KEY);

        if (jsonCache != null) {
            // Cache encontrado
            Turma[] turmas = gson.fromJson(jsonCache, Turma[].class);
            for (Turma t : turmas) {
                System.out.println("ID: " + t.getId());
                System.out.println("Nome: " + t.getNome());
                System.out.println("-----------------------");
            }

            long duration = System.currentTimeMillis() - start;
            System.out.println("Tempo de execução (LISTAR - CACHE): " + duration + " ms");
        } else {
            // Cache não encontrado
            List<Turma> turmasList = new ArrayList<>();
            for (Document doc : collection.find()) {
                Turma turma = new Turma(
                        doc.getInteger("id"),
                        doc.getString("nome")
                );
                turmasList.add(turma);
            }
            String json = gson.toJson(turmasList);
            redisCache.set(CACHE_KEY, json);

            for (Turma t : turmasList) {
                System.out.println("ID: " + t.getId());
                System.out.println("Nome: " + t.getNome());
                System.out.println("-----------------------");
            }

            long duration = System.currentTimeMillis() - start;
            System.out.println("Tempo de execução (LISTAR - MONGO): " + duration + " ms");
        }
    }

    public void atualizar(int id, String novoNome) {
        long start = System.currentTimeMillis();

        collection.updateOne(Filters.eq("id", id), Updates.set("nome", novoNome));
        redisCache.del(CACHE_KEY);

        System.out.println("Turma atualizada com sucesso!");

        long duration = System.currentTimeMillis() - start;
        System.out.println("Tempo de execução (ATUALIZAR): " + duration + " ms");
    }

    public void remover(int id) {
        long start = System.currentTimeMillis();

        collection.deleteOne(Filters.eq("id", id));
        redisCache.del(CACHE_KEY);

        System.out.println("Turma removida com sucesso!");

        long duration = System.currentTimeMillis() - start;
        System.out.println("Tempo de execução (REMOVER): " + duration + " ms");
    }

    public void fechar() {
        redisCache.close();
    }
}
