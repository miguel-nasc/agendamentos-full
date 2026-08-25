# 🏢 Spring Salas

Sistema de **agendamento de salas** desenvolvido com **Spring Boot**, focado em uma API REST segura, documentada e pronta para rodar em contêineres.

## ✨ Sobre o projeto

A aplicação permite o cadastro e gerenciamento de salas e de seus agendamentos, com autenticação via **JWT** e persistência em **PostgreSQL**. O projeto segue boas práticas de organização de uma API Spring, incluindo versionamento de banco de dados com Flyway e documentação automática via Swagger/OpenAPI.

## 🚀 Tecnologias

- **Java 21**
- **Spring Boot 4.1.0**
    - Spring Data JPA
    - Spring MVC (Web)
    - Spring Security
    - Spring HATEOAS
- **JWT** (io.jsonwebtoken / jjwt) para autenticação
- **BCrypt** (jbcrypt) para hash de senhas
- **PostgreSQL** como banco de dados
- **Flyway** para migrações de banco de dados
- **springdoc-openapi** (Swagger UI) para documentação da API
- **Apache Commons Lang3**
- **Docker & Docker Compose**
- **JUnit 5, Mockito, AssertJ e Spring Security Test** para testes
- **H2** como banco em memória para testes de integração

## 📋 Pré-requisitos

- [Java 21+](https://adoptium.net/)
- [Maven](https://maven.apache.org/) (ou use o wrapper `./mvnw` incluído no projeto)
- [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/) (opcional, para rodar via contêiner)

## ⚙️ Configuração

Antes de rodar a aplicação, crie um arquivo `.env` na raiz do projeto a partir do `.env.example`:

```bash
cp .env.example .env
```

E preencha as variáveis:

```env
# Configuração do banco de dados
DB_HOST=
DB_NAME=
DB_PASSWORD=
DB_URL=
DB_USER=

# Configuração do JWT
JWT_EXPIRE_LENGTH=
JWT_SECRET=
```

## ▶️ Como executar

### Com Docker Compose (recomendado)

O `docker-compose.yml` já sobe a API junto com um banco PostgreSQL configurado:

```bash
docker-compose up --build
```

A API ficará disponível em `http://localhost:8413` e o banco de dados PostgreSQL em `localhost:5430`.

### Localmente, sem Docker

Com um PostgreSQL já rodando e as variáveis de ambiente configuradas:

```bash
./mvnw spring-boot:run
```

## 📖 Documentação da API

Com a aplicação em execução, a documentação interativa (Swagger UI) fica disponível em:

```
http://localhost:8413/swagger-ui.html
```

## 🧪 Testes

O projeto conta com testes unitários e de integração, usando JUnit 5, Mockito, AssertJ e um banco H2 em memória:

```bash
./mvnw test
```

## 🗄️ Migrações de banco de dados

As migrações do banco são gerenciadas pelo **Flyway** e executadas automaticamente na inicialização da aplicação.

## 📌 Status

Projeto em desenvolvimento. Sugestões, issues e pull requests são bem-vindos!
