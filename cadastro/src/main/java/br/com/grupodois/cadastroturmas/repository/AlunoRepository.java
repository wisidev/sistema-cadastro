package br.com.grupodois.cadastroturmas.repository;

import br.com.grupodois.cadastroturmas.database.RedisCache;
import br.com.grupodois.cadastroturmas.model.Aluno;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AlunoRepository {
    private final MongoCollection<Document> collection;
    private final RedisCache redisCache;
    private final Gson gson;
    private final String CACHE_KEY = "alunos:listar";

    public AlunoRepository(MongoDatabase database) {
        this.collection = database.getCollection("alunos");
        this.redisCache = new RedisCache();
        this.gson = new Gson();
    }

    public void adicionar(Aluno aluno) {
        long start = System.currentTimeMillis();  // ⏱️ início

        Document doc = new Document("id", aluno.getId())
                .append("nome", aluno.getNome())
                .append("ra", aluno.getRa());
        collection.insertOne(doc);
        redisCache.del(CACHE_KEY);  // Invalida cache
        System.out.println("Aluno cadastrado com sucesso!");

        long duration = System.currentTimeMillis() - start;  // ⏱️ fim
        System.out.println("Tempo de execução (ADICIONAR): " + duration + " ms");
    }

    public void listar() {
        long start = System.currentTimeMillis();  // ⏱️ início

        String jsonCache = redisCache.get(CACHE_KEY);

        if (jsonCache != null) {
            // Cache encontrado
            Aluno[] alunos = gson.fromJson(jsonCache, Aluno[].class);
            for (Aluno a : alunos) {
                System.out.println("ID: " + a.getId());
                System.out.println("Nome: " + a.getNome());
                System.out.println("RA: " + a.getRa());
                System.out.println("-----------------------");
            }
            long duration = System.currentTimeMillis() - start;
            System.out.println("Tempo de execução (LISTAR - CACHE): " + duration + " ms");

        } else {
            // Cache não encontrado - busca no Mongo e salva no Redis
            List<Aluno> alunosList = new ArrayList<>();
            for (Document doc : collection.find()) {
                Aluno aluno = new Aluno(doc.getInteger("id"), doc.getString("nome"), doc.getInteger("ra"));
                alunosList.add(aluno);
            }
            String json = gson.toJson(alunosList);
            redisCache.set(CACHE_KEY, json);
            // Exibe os dados
            for (Aluno a : alunosList) {
                System.out.println("ID: " + a.getId());
                System.out.println("Nome: " + a.getNome());
                System.out.println("RA: " + a.getRa());
                System.out.println("-----------------------");
            }
            long duration = System.currentTimeMillis() - start;
            System.out.println("Tempo de execução (LISTAR - MONGO): " + duration + " ms");
        }
    }

    public void atualizar(int id, String novoNome) {
        long start = System.currentTimeMillis();  // ⏱️ início

        collection.updateOne(Filters.eq("id", id), Updates.set("nome", novoNome));
        redisCache.del(CACHE_KEY);  // Invalida cache
        System.out.println("Aluno atualizado com sucesso!");

        long duration = System.currentTimeMillis() - start;  // ⏱️ fim
        System.out.println("Tempo de execução (ATUALIZAR): " + duration + " ms");
    }

    public void remover(int id) {
        long start = System.currentTimeMillis();  // ⏱️ início

        collection.deleteOne(Filters.eq("id", id));
        redisCache.del(CACHE_KEY);  // Invalida cache
        System.out.println("Aluno removido com sucesso!");

        long duration = System.currentTimeMillis() - start;  // ⏱️ fim
        System.out.println("Tempo de execução (REMOVER): " + duration + " ms");
    }

    public void fechar() {
        redisCache.close();
    }
}
