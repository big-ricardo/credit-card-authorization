# Merchant Service Project

Este projeto é um serviço de autorização feito em Spring Boot, utilizando PostgreSQL como banco de dados e Java 22.

## Requisitos

- Java 22
- Maven 3.6.3 ou superior
- PostgreSQL 12 ou superior

## Funcionamento

O projeto consiste em uma rota ``authorize`` que recebe um payload com informações de uma transação, com base nessas 
informaçãoes passadas ela analise com base nas informaççoes da transação, nos creditos da conta, dados de comerciantes salvos.

### Fluxo de Autorização

1. **Recepção da Transação**: A rota `authorize` recebe um payload JSON com os detalhes da transação.
2. **Mapeamento da Categoria**:
    - Verifica se existe um MCC substituto baseado no nome do comerciante.
    - Caso contrário, usa o MCC original da transação.
3. **Autorização**:
    - Tenta aprovar a transação usando o saldo da categoria mapeada (L1).
    - Se a categoria mapeada não for CASH e não tiver saldo suficiente, tenta usar o saldo da categoria **CASH** (L2).
4. **Resultado**:
    - Se o saldo for suficiente em uma das categorias, a transação é aprovada e o saldo correspondente é debitado.
    - Se não houver saldo suficiente, a transação é rejeitada.

## Configuração do Banco de Dados

Se preferir configurar manualmente, siga as instruções abaixo:

1. Instale o PostgreSQL a partir do site oficial: [PostgreSQL Downloads](https://www.postgresql.org/download/) ou via Docker.

## Configuração do Projeto

### Configuração do application.properties

Edite o arquivo `src/main/resources/application.properties` para configurar as informações do banco de dados:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=admin
```

### Build e Execução

1. Clone o repositório do projeto:

2. Compile o projeto usando Maven ou pela Intellij IDEA:

```bash
mvn clean install
```

3. Execute o projeto:

```bash
mvn spring-boot:run
```

O aplicativo estará disponível em `http://localhost:8080`.

### Coleção no Postman

Pode ser importado no postman a collection para a utilização das rotas
