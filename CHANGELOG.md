# CHANGELOG

### 2021-08-24

- Adicionada documentação para executar a aplicação;
- Criado script run_dependencies
- Criado arquivo docker-compose-dependencies.yml;
- Ajustado script run;

### 2021-08-23

- Retirada validação de CPF no Consumer Kafka;
- Adicionado paginação nos endpoints de **associados** e **ata**;
- Adicionado interface Swagger;
- Implementado testes com Mockito;
- Adicionado tempo de espera ao iniciar a aplicação, dando chance para o banco de dados subir, tempo de espera configurável através de variável de ambiente;

### 2021-08-22

- Implementado testes unitários de classes DTO;
- Implementado classes de repositório;
- Implementado classes de serviços;
- Implementado classes de endpoints;
- Criação do dockerfile;
- Criação do docker-compose.yml;
- Criação de scripts para build;
- Criação de script para build utilizando conteiner maven (compile_conteiner);
- Criação de documentação README.md;
- Correção da utilização do atributo _id_ nas classes DTO;

### 2021-08-21

- Implementado classes de entidade;
- Implementado testes unitários de classes de entidade;
- Implementando classes de repositório;
- Implementando classes de serviços;
- Implementando classes de endpoints;

### 2021-08-20

- Implementando classes de entidade;
- Implementando classes DTO (JSON);

### 2021-08-19

- Criação do proojeto;
- Configuração do pom.xml;
- Configuração do application.xml;
- Implementando classes de entidade;
