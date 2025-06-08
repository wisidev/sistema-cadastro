package br.com.grupodois.cadastroturmas.repository;

import br.com.grupodois.cadastroturmas.database.RedisCache;
import br.com.grupodois.cadastroturmas.model.Curso;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CursoRepository {
    private final MongoCollection<Document> collection;
    private final RedisCache redisCache;
    private final Gson gson;
    private final String CACHE_KEY = "cursos:listar";

    public CursoRepository(MongoDatabase database) {
        this.collection = database.getCollection("cursos");
        this.redisCache = new RedisCache();
        this.gson = new Gson();
    }

    public void adicionar(Curso curso) {
        long start = System.currentTimeMillis();

        Document doc = new Document("id", curso.getId())
                .append("nome", curso.getNome());
        collection.insertOne(doc);
        redisCache.del(CACHE_KEY);
        System.out.println("Curso cadastrado com sucesso!");

        long duration = System.currentTimeMillis() - start;
        System.out.println("Tempo de execução (ADICIONAR): " + duration + " ms");
    }

    public void listar() {
        long start = System.currentTimeMillis();

        String jsonCache = redisCache.get(CACHE_KEY);

        if (jsonCache != null) {
            Curso[] cursos = gson.fromJson(jsonCache, Curso[].class);
            for (Curso c : cursos) {
                System.out.println("ID: " + c.getId());
                System.out.println("Nome: " + c.getNome());
                System.out.println("-----------------------");
            }
            long duration = System.currentTimeMillis() - start;
            System.out.println("Tempo de execução (LISTAR - CACHE): " + duration + " ms");
        } else {
            List<Curso> cursosList = new ArrayList<>();
            for (Document doc : collection.find()) {
                Curso curso = new Curso(doc.getInteger("id"), doc.getString("nome"));
                cursosList.add(curso);
            }
            String json = gson.toJson(cursosList);
            redisCache.set(CACHE_KEY, json);

            for (Curso c : cursosList) {
                System.out.println("ID: " + c.getId());
                System.out.println("Nome: " + c.getNome());
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
        System.out.println("Curso atualizado com sucesso!");

        long duration = System.currentTimeMillis() - start;
        System.out.println("Tempo de execução (ATUALIZAR): " + duration + " ms");
    }

    public void remover(int id) {
        long start = System.currentTimeMillis();

        collection.deleteOne(Filters.eq("id", id));
        redisCache.del(CACHE_KEY);
        System.out.println("Curso removido com sucesso!");

        long duration = System.currentTimeMillis() - start;
        System.out.println("Tempo de execução (REMOVER): " + duration + " ms");
    }

    public void fechar() {
        redisCache.close();
    }
}
