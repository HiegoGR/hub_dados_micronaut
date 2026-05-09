## Hub de Enriquecimento de Dados

<p align="center"> <img src="https://img.shields.io/badge/Java-21-blue?style=for-the-badge&logo=openjdk"/> <img src="https://img.shields.io/badge/Micronaut-4.x-00acc1?style=for-the-badge&logo=micronaut"/> <img src="https://img.shields.io/badge/Kafka-Event--Driven-black?style=for-the-badge&logo=apachekafka"/> <img src="https://img.shields.io/badge/Docker-Containerized-2496ED?style=for-the-badge&logo=docker"/> <img src="https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow?style=for-the-badge"/> </p>


### Sobre o projeto

Sistema desenvolvido com **Micronaut** + **Kafka** para processamento **assíncrono** e **paralelo** 
de enriquecimento de dados utilizando APIs públicas brasileiras.

### Objetivo

Receber uma requisição com dados básicos (CEP, CNPJ, CAMBIO, FERIADOS), 
processar de forma assíncrona via Kafka e enriquecer as informações 
consumindo múltiplas APIs externas em paralelo

### Arquitetura
``` Controller → Service → Kafka Producer → Kafka Consumer → Processamento paralelo → Persistência → Resultado```
- Fluxo
    - Cliente envia requisição HTTP
    - Sistema cria um jobId
    - Publica evento no Kafka (job.recebido)
    - Consumer recebe evento
    - Executa chamadas externas em paralelo
    - Consolida resultado
    - Atualiza status do job

### Tecnologias
- Java 21
- Micronaut
- Apache Kafka
- Docker
- H2 Database
- JPA / Hibernate
- JUnit 5
- Mockito
- APIs externas:
  - ViaCEP
  - BrasilAPI
    - CNPJ
    - Câmbio
    - Feriados

### Persistência de dados
O sistema salva o resultado do processamento do job em banco de dados utilizando:

- H2 Database
- JPA/Hibernate
- Entidades relacionais


### Processamento paralelo

As chamadas externas são executadas em paralelo utilizando:

 - CompletableFuture
 - ExecutorService

Exemplo de tarefas paralelas:

- Consulta CEP
- Consulta CNPJ
- Consulta Feriados
- Consulta Câmbio

### Testes Unitários
O projeto possui testes unitários para todas as classes da camada service.

### Como executar o projeto

Ao baixar o projeto 

- Na raiz do projeto existe um arquivo chamado ``1_executarDockerComposeLocal.bat
`` para subir o kafka.
- Caso contrário, abra o terminal e digite: `` docker-compose up -d ``

### Build e execução da aplicação
- Abra o projeto na IDE
  - gradle clean
  - gradle build
  - gradle run


### Acessar a aplicação
- Swagger UI

``http://localhost:8080/swagger-ui/index.html#``

- Kafka UI

``http://localhost:8085/ui/clusters/local/all-topics``