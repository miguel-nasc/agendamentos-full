# Combinaí

**Seu espaço. Seu horário.**

Combinaí (repositório `agendamentos-full`) é uma aplicação full-stack de agendamento e reserva de salas, dividida em back-end (API REST em Java/Spring Boot) e front-end (React).

## Sobre o projeto

O sistema permite que usuários se cadastrem, façam login e reservem salas disponíveis, acompanhando o status de cada reserva através de um fluxo de estados:

```
marcou → combinou → confirmou
```

- **Marcou**: o usuário solicitou a reserva da sala.
- **Combinou**: a reserva foi alinhada/negociada entre as partes envolvidas.
- **Confirmou**: a reserva está confirmada e garantida.

## 📂 Estrutura do repositório

```
agendamentos-full/
├── back/     # API REST (Java + Spring Boot)
└── front/    # Interface web (React + Vite)
```

## 🛠️ Tecnologias

### Back-end (`/back`)
- **Java 21**
- **Spring Boot 4.1.0** (Web MVC, Data JPA, Security, HATEOAS)
- **JWT** (io.jsonwebtoken) para autenticação
- **PostgreSQL** + **Flyway** para migrações de banco
- **Springdoc OpenAPI** (Swagger UI)
- **ModelMapper** e **Apache Commons Lang3**
- **JUnit 5, Mockito, AssertJ, H2** (testes)
- **Docker** / **Docker Compose**

### Front-end (`/front`)
- **React 19** + **React Router DOM**
- **Vite 8**
- **Tailwind CSS 4** + **Bootstrap 5**
- **Axios** para consumo da API
- **oxlint** para lint

## 🚀 Como rodar o projeto

### Pré-requisitos
- Java 21+
- Node.js 18+
- Docker e Docker Compose (recomendado para o banco de dados)

### 1. Back-end

```bash
cd back

# Copie o arquivo de variáveis de ambiente e preencha os valores
cp .env.example .env
```

Preencha o `.env` com as seguintes variáveis:

```env
# Banco de dados
DB_HOST=
DB_NAME=
DB_PASSWORD=
DB_URL=
DB_USER=

# JWT
JWT_EXPIRE_LENGTH=
JWT_SECRET=
```

Subindo API + banco de dados com Docker Compose:

```bash
docker compose up --build
```

A API ficará disponível em `http://localhost:8413` (mapeada para a porta `8080` do container).

Alternativamente, rodando localmente com Maven (necessário um PostgreSQL configurado):

```bash
./mvnw spring-boot:run
```

**Documentação da API (Swagger):** `http://localhost:8080/swagger-ui`

### 2. Front-end

```bash
cd front
npm install
npm run dev
```

O front-end ficará disponível em `http://localhost:5173` (porta padrão do Vite).

## 📄 Páginas do front-end

| Página     | Descrição                        |
|------------|-----------------------------------|
| Home       | Página inicial de apresentação    |
| Cadastrar  | Criação de conta                  |
| Login      | Autenticação de usuários          |

## 🎨 Identidade visual

- Paleta monocromática
- Logo baseada em uma marca *vesica* (círculos sobrepostos), componentizada em `CombinaLogo.jsx`
- Gerenciamento de tema e autenticação via `ThemeContext` e `AuthContext`

## 📝 Licença

Este projeto está licenciado sob a [Apache License 2.0](./LICENSE).
