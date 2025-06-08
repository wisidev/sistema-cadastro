package br.com.grupodois.cadastroturmas.repository;

import br.com.grupodois.cadastroturmas.database.RedisCache;
import br.com.grupodois.cadastroturmas.model.Desafio;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DesafioRepository {
    private final MongoCollection<Document> collection;
    private final RedisCache redisCache;
    private final Gson gson;
    private final String CACHE_KEY = "desafios:listar";

    public DesafioRepository(MongoDatabase database) {
        this.collection = database.getCollection("desafios");
        this.redisCache = new RedisCache();
        this.gson = new Gson();
    }

    public void adicionar(Desafio desafio) {
        long start = System.currentTimeMillis();

        Document doc = new Document("id", desafio.getId())
                .append("nome", desafio.getNome())
                .append("curso", desafio.getCurso())
                .append("periodo", desafio.getPeriodo())
                .append("turma", desafio.getTurma())
                .append("aluno", desafio.getAluno());
        collection.insertOne(doc);
        redisCache.del(CACHE_KEY);

        System.out.println("Desafio cadastrado com sucesso!");

        long duration = System.currentTimeMillis() - start;
        System.out.println("Tempo de execução (ADICIONAR): " + duration + " ms");
    }

    public void listar() {
        long start = System.currentTimeMillis();

        String jsonCache = redisCache.get(CACHE_KEY);

        if (jsonCache != null) {
            // Cache encontrado
            Desafio[] desafios = gson.fromJson(jsonCache, Desafio[].class);
            for (Desafio d : desafios) {
                System.out.println("ID: " + d.getId());
                System.out.println("Nome: " + d.getNome());
                System.out.println("Curso: " + d.getCurso());
                System.out.println("Período: " + d.getPeriodo());
                System.out.println("Turma: " + d.getTurma());
                System.out.println("Aluno: " + d.getAluno());
                System.out.println("-----------------------");
            }

            long duration = System.currentTimeMillis() - start;
            System.out.println("Tempo de execução (LISTAR - CACHE): " + duration + " ms");
        } else {
            // Cache não encontrado
            List<Desafio> desafiosList = new ArrayList<>();
            for (Document doc : collection.find()) {
                Desafio desafio = new Desafio(
                        doc.getInteger("id"),
                        doc.getString("nome"),
                        doc.getString("curso"),
                        doc.getString("periodo"),
                        doc.getString("turma"),
                        doc.getString("aluno")
                );
                desafiosList.add(desafio);
            }
            String json = gson.toJson(desafiosList);
            redisCache.set(CACHE_KEY, json);

            for (Desafio d : desafiosList) {
                System.out.println("ID: " + d.getId());
                System.out.println("Nome: " + d.getNome());
                System.out.println("Curso: " + d.getCurso());
                System.out.println("Período: " + d.getPeriodo());
                System.out.println("Turma: " + d.getTurma());
                System.out.println("Aluno: " + d.getAluno());
                System.out.println("-----------------------");
            }

            long duration = System.currentTimeMillis() - start;
            System.out.println("Tempo de execução (LISTAR - MONGO): " + duration + " ms");
        }
    }

    public void atualizar(int id, String novoNome, String novoCurso, String novoPeriodo, String novaTurma, String novoAluno) {
        long start = System.currentTimeMillis();

        collection.updateOne(Filters.eq("id", id), Updates.combine(
                Updates.set("nome", novoNome),
                Updates.set("curso", novoCurso),
                Updates.set("periodo", novoPeriodo),
                Updates.set("turma", novaTurma),
                Updates.set("aluno", novoAluno)
        ));
        redisCache.del(CACHE_KEY);

        System.out.println("Desafio atualizado com sucesso!");

        long duration = System.currentTimeMillis() - start;
        System.out.println("Tempo de execução (ATUALIZAR): " + duration + " ms");
    }

    public void remover(int id) {
        long start = System.currentTimeMillis();

        collection.deleteOne(Filters.eq("id", id));
        redisCache.del(CACHE_KEY);

        System.out.println("Desafio removido com sucesso!");

        long duration = System.currentTimeMillis() - start;
        System.out.println("Tempo de execução (REMOVER): " + duration + " ms");
    }

    public void fechar() {
        redisCache.close();
    }
}
