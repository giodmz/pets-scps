# Pets SCPS — Sistema de Cadastro e Adoção de Pets

API REST para gerenciamento e adoção de animais, desenvolvida com Java 17 e Spring Boot 3. Permite o cadastro, consulta, atualização e remoção de pets e adotantes, com autenticação via JWT e documentação interativa pelo Swagger UI.

---

## Tecnologias

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 17 |
| Framework | Spring Boot 3.5.4 |
| Persistência | Spring Data JPA + PostgreSQL |
| Segurança | Spring Security + JWT (jjwt 0.12.6) |
| Documentação | Springdoc OpenAPI / Swagger UI |
| Build | Maven |
| Utilitários | Lombok |
| Ambiente | Docker / Docker Compose |

---

## Funcionalidades

- Autenticação via JWT — registro e login de usuários
- CRUD completo de pets (nome, espécie, gênero, idade, peso, status)
- CRUD completo de adotantes (nome, e-mail, contato, endereço)
- Processo de adoção — vinculação de pet a adotante com controle de status
- Listagem paginada de pets e adotantes
- Busca de pets por nome (`/pets/search?name=...`)
- Listagem de pets disponíveis para adoção (`/pets/available`)
- Tratamento centralizado de exceções com respostas padronizadas
- Testes unitários de serviços e controllers

---

## Como rodar

### Pré-requisitos

- Java 17+
- Maven 3.8+
- Docker e Docker Compose

### 1. Clonar o repositório

```bash
git clone https://github.com/giodmz/pets-scps.git
cd pets-scps
```

### 2. Subir o banco de dados

```bash
docker compose up -d
```

Isso sobe o PostgreSQL na porta `5432` e o Adminer na porta `8080`.

### 3. Configurar a chave JWT

Crie o arquivo `src/main/resources/application-secrets.properties`:

```properties
jwt.secret=SUA_CHAVE_SECRETA_AQUI
```

### 4. Rodar a aplicação

```bash
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8081`

---

## Documentação

Acesse o Swagger UI para explorar e testar todos os endpoints interativamente:

```
http://localhost:8081/swagger-ui.html
```

---

## Endpoints

### Autenticação — `/auth`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/auth/register` | Registra um novo usuário |
| POST | `/auth/login` | Autentica e retorna o token JWT |

> Para acessar os demais endpoints, inclua o header: `Authorization: Bearer <token>`

---

### Pets — `/pets`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/pets` | Lista todos os pets (paginado) |
| GET | `/pets/{id}` | Busca pet por ID |
| POST | `/pets` | Cadastra um novo pet |
| PUT | `/pets/{id}` | Atualiza dados do pet |
| DELETE | `/pets/{id}` | Remove um pet |
| GET | `/pets/{id}/adopters` | Retorna o adotante vinculado ao pet |
| GET | `/pets/search?name=` | Busca pets por nome |
| GET | `/pets/available` | Lista pets disponíveis para adoção |
| POST | `/pets/{petId}/adopt/{adopterId}` | Vincula um pet a um adotante |

---

### Adotantes — `/adopters`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/adopters` | Lista todos os adotantes (paginado) |
| GET | `/adopters/{id}` | Busca adotante por ID |
| POST | `/adopters` | Cadastra um novo adotante |
| PUT | `/adopters/{id}` | Atualiza dados do adotante |
| DELETE | `/adopters/{id}` | Remove um adotante |
| GET | `/adopters/{id}/addresses` | Retorna o endereço do adotante |

---

## Enumerações

**Species:** `DOG`, `CAT`

**Gender:** `MALE`, `FEMALE`

**Status:** `AVAILABLE`, `IN_PROCESS`, `ADOPTED`

---

## Estrutura do projeto

```
src/main/java/com/pets
├── config/
│   ├── menu/           # Handlers de menu interativo (CLI)
│   ├── Instantiation   # Dados iniciais (seed)
│   ├── JwtFilter       # Filtro de autenticação JWT
│   ├── ScannerConfig   # Bean do Scanner
│   ├── SecurityConfig  # Configuração do Spring Security
│   └── SpringDocConfig # Configuração do Swagger
├── controllers/        # Endpoints REST (Pet, Adopter, Auth)
├── dto/                # Data Transfer Objects
├── entities/           # Entidades JPA (Pet, Adopter, Address, User)
├── enums/              # Enumerações (Species, Gender, Status)
├── exceptions/         # Tratamento centralizado de erros
├── repository/         # Interfaces Spring Data JPA
└── services/           # Regras de negócio e JWT
```

---

## Banco de dados

| Container | Imagem | Porta |
|-----------|--------|-------|
| db | postgres | 5432 |
| adminer | adminer | 8080 |

O banco de dados `petsdb` é criado automaticamente pelo Docker Compose. O Hibernate gerencia o schema via `spring.jpa.hibernate.ddl-auto=update`.