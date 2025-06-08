package br.com.grupodois.cadastroturmas.repository;

import br.com.grupodois.cadastroturmas.database.RedisCache;
import br.com.grupodois.cadastroturmas.model.Periodo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PeriodoRepository {
    private final MongoCollection<Document> collection;
    private final RedisCache redisCache;
    private final Gson gson;
    private final String CACHE_KEY = "periodos:listar";

    public PeriodoRepository(MongoDatabase database) {
        this.collection = database.getCollection("periodos");
        this.redisCache = new RedisCache();
        this.gson = new Gson();
    }

    public void adicionar(Periodo periodo) {
        long start = System.currentTimeMillis();

        Document doc = new Document("id", periodo.getId())
                .append("nome", periodo.getNome());
        collection.insertOne(doc);
        redisCache.del(CACHE_KEY);

        System.out.println("Período cadastrado com sucesso!");

        long duration = System.currentTimeMillis() - start;
        System.out.println("Tempo de execução (ADICIONAR): " + duration + " ms");
    }

    public void listar() {
        long start = System.currentTimeMillis();

        String jsonCache = redisCache.get(CACHE_KEY);

        if (jsonCache != null) {
            // Cache encontrado
            Periodo[] periodos = gson.fromJson(jsonCache, Periodo[].class);
            for (Periodo p : periodos) {
                System.out.println("ID: " + p.getId());
                System.out.println("Nome: " + p.getNome());
                System.out.println("-----------------------");
            }

            long duration = System.currentTimeMillis() - start;
            System.out.println("Tempo de execução (LISTAR - CACHE): " + duration + " ms");
        } else {
            // Cache não encontrado
            List<Periodo> periodosList = new ArrayList<>();
            for (Document doc : collection.find()) {
                Periodo periodo = new Periodo(
                        doc.getInteger("id"),
                        doc.getString("nome")
                );
                periodosList.add(periodo);
            }
            String json = gson.toJson(periodosList);
            redisCache.set(CACHE_KEY, json);

            for (Periodo p : periodosList) {
                System.out.println("ID: " + p.getId());
                System.out.println("Nome: " + p.getNome());
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

        System.out.println("Período atualizado com sucesso!");

        long duration = System.currentTimeMillis() - start;
        System.out.println("Tempo de execução (ATUALIZAR): " + duration + " ms");
    }

    public void remover(int id) {
        long start = System.currentTimeMillis();

        collection.deleteOne(Filters.eq("id", id));
        redisCache.del(CACHE_KEY);

        System.out.println("Período removido com sucesso!");

        long duration = System.currentTimeMillis() - start;
        System.out.println("Tempo de execução (REMOVER): " + duration + " ms");
    }

    public void fechar() {
        redisCache.close();
    }
}
