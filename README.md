# 📚 Sistema de Cadastro com MongoDB, Java e Redis

## 🎯 Objetivo

Este projeto tem como objetivo principal entender o funcionamento de um banco de dados **NoSQL**, integrando o **MongoDB** com **Java** e utilizando o **Redis** como sistema de cache em memória para otimizar a performance nas consultas.

## 📝 Descrição

Trata-se de um sistema de cadastro simples desenvolvido em **Java** que realiza operações de **CRUD** (Create, Read, Update e Delete) em um banco de dados **MongoDB**. O sistema é executado localmente através da IDE (**VS Code**), sem interface gráfica e sem API.

Com a implementação do **Redis**, o sistema realiza cache das consultas realizadas. Ao listar uma informação pela primeira vez, os dados são obtidos do **MongoDB** e armazenados no **Redis**. Nas consultas subsequentes, os dados são recuperados diretamente do **Redis**, evitando acessos repetitivos ao banco de dados e melhorando o desempenho.

As entidades cadastradas incluem:

- Cursos
- Turmas
- Períodos
- Alunos
- Desafios

## ⚙️ Funcionalidades

- Inserção de documentos em diferentes coleções.
- Atualização de campos em documentos existentes.
- Exclusão de registros com base em filtros.
- Listagem dos documentos armazenados nas coleções.
- Utilização de filtros e atualizações com o **MongoDB Driver Sync**.
- Cache em memória com **Redis** para otimização das consultas.

## 🧪 Tecnologias Utilizadas

- [Java 21](https://www.oracle.com/br/java/)
- [MongoDB](https://www.mongodb.com/)
- [MongoDB Driver Sync (4.9.0)](https://mongodb.github.io/mongo-java-driver/)
- [Maven](https://maven.apache.org/)
- [Redis](https://redis.io/)
- [Jedis (Redis Java client)](https://github.com/redis/jedis)

## 🚀 Como Executar

1. Certifique-se de que o **MongoDB** e o **Redis** estejam instalados e executando localmente:  
   Mongo: `mongodb://localhost:27017`  
   Redis: `redis://localhost:6379`

5. Clone este repositório:

   ```bash
   git clone https://github.com/wisidev/CadastroTurmas.git
   cd cadastro
   ```

6. Abra o projeto na sua IDE (**VS Code**).
7. Execute a classe `Main.java`.

## 🎥 Demonstração em Vídeo  
Um vídeo demonstrando o funcionamento do sistema está disponível no YouTube:

🔗 [Assista no YouTube](https://www.youtube.com/watch?v=Bc8vlInBNDU)

