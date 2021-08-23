# Projeto votacao

### Desenvolvido por Gustavo Oliveira

### Desde 2021-08-19

## Baseado no desafio-back-votos

- https://github.com/rh-southsystem/desafio-back-votos

## [CHANGE_LOG](CHANGELOG.md)

# Arquitetura

Projeto foi escrito em Java 8 usando Spring Boot, com base de dados MySQL e Apache Kafka como plataforma de stream.

Além disso foi utilizado Docker como plataforma de contêiners.

As classes foram nomeadas nos seus correspondentes da língua inglesa. Os nomes de tabelas e campos estão em português.

Foram utilizadas anotações do bean validation para validar as entidades. Além disso foram criadas as constraints na base de dados através das anotações JPA.

A persistência foi realizada com JPA utilizando MySQL como banco de dados, as classes de persistência tem um pós-fixo PU ((P)ersistent (U)nit). Os IDs são strings, geradas a partir de UUID.

As classes de borda utilizadas na interface dos endpoints tem um pós-fixo DTO (Data Transfer Object) mantendo a independência da camada de persisência, ou seja, é possível realizar alterações nos DTOs sem afetar diretamente os PUs.

Os endpoints estão versionados usando _/api/v1/{endpoint}_ trocando o v1 para novas versões ex: v2, v3, etc. **(Tarefa bônus 4)**

As classes DTOs estão mantidas em pacote versionado _(br.com.oneguy.votacao.domain.dto.v1)_, assim como os endpoints _(br.com.oneguy.votacao.endpoints.v1)_.

Os repositórios seguem a convenção do Spring Data, foi apenas criado interfaces extendendo _JpaRepository_.

Para os serviços (_Service_) foram criadas as interfaces e suas implementações

Foi criado **_ICpfValidatorService_** e **_CpfValidatorServiceImpl_** para verificar o CPF do associado **(Tarefa bônus 1)**.

Os endpoints com métodos _POST_, _PUT_ e _DELETE_ são assíncronos, retornando o status _ACCEPTED_ **(Tarefa bônus 3)**

O processo de persistência é realizado da seguinte forma **(Tarefa bônus 2 e 3)**:

1. Endpoint recebe a requisição e envia para o _Service_ correspondente;
2. No _Service_ é feita a validação, e caso não lance exceção é criada uma mensagem, contendo a entidade e ação a ser realizada (CRUD), enviando para o tópico do Kafka;
3. No consumer do Kafka, é realizado novamente as validações, se caso não lançar exceção é persistido no banco de dados;
4. Endpoint recebe uma uri contendo o caminho para recuperar a entidade alterada;

**(Tarefa bônus 3)** Com essa arquitetura utilizando o Kafka, é possível escalonar facilmente a aplicação, já que o consumer tem o mesmo _GROUP ID_ é feito um balanceamento no tópico. E a aplicação foi desenvolvida para utilizar contêiners, permitindo um escalonamento horizontal fácil.

## Principais classes de entidade

### Persistência:

- AssociatePU => Dados do associado;
- Vote => Enum do voto (AGREE(Sim), DISAGREE(Não));
- MinuteMeetingPU => Ata da reunião;
- PollMeetingPU => Sessão da votação;
- AssociateVotePU => Voto do associado na sessão;

### DTO:

- AssociateDTO => Dados do associado;
- MinuteMeetingDTO => Ata da reunião, contendo os totalizadores dos votos;
- PollMeetingDTO => Sessão da votação, usado somente para abrir uma sessão de voto;
- AssociateVoteDTO => Voto do associado na ata;

## Endpoints

Todos os endpoints começam com **_/api/v1/_**

A aplicação roda na porta **_8080_**

Para os endpoints de associado e ata, existem os métodos **GET**, para recuperar por id, adicione a id ao final da url. Além disso é suportado paginação **_(?page=1&size=10)_**.

Para inserção **POST** não é necessário o atributo **_id_**, o mesmo é gerado pela aplicação

Foi disponibilizado interface com o Swagger, para acessá-lo: **_/swagger-ui.html_**

- ### Associado **_/api/v1/associate/_**

> `{ "id": "string", "cpf": "string" }`

- ### Ata **/api/v1/minutemeeting/**

> `{ "id": "string", "resume": "string", "description": "string"}`

- ### Sessão **_/api/v1/pollmeeting/_**

Somente **POST**

> `{ "minuteMeetingId" : "string", "duration": <minutos> }`

- ### Voto **_/api/v1/vote/_**

Somente **POST**

> `{ "minuteMeetingId" : "string", "associateId": "string", "vote": "Sim/Não"}`
