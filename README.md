# Pets SCPS — Sistema de Adoção de Pets

API REST para gerenciamento e adoção de animais, desenvolvida com Java 17 e Spring Boot. Permite o cadastro, consulta, atualização e remoção de pets disponíveis para adoção.

---

## Tecnologias

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL** (via Docker)
- **Springdoc OpenAPI (Swagger UI)**
- **Lombok**
- **Maven**

---

## Funcionalidades

- Cadastro completo de pets (nome, espécie, gênero, idade, peso, endereço)
- CRUD completo de pets
- Validação de dados de entrada
- Tratamento de exceções personalizado
- Testes unitários

---

## Como rodar

```bash
git clone https://github.com/giodmz/pets-scps.git
cd pets-scps
docker compose up -d
mvn spring-boot:run
```

Disponível em: `http://localhost:8081`

---

## Endpoints

**Pets**

| Método | Endpoint              | Descrição                        |
|--------|-----------------------|----------------------------------|
| GET    | `/pets`               | Lista todos os pets              |
| GET    | `/pets/{id}`          | Busca pet por ID                 |
| POST   | `/pets`               | Cadastra um novo pet             |
| PUT    | `/pets/{id}`          | Atualiza dados do pet            |
| DELETE | `/pets/{id}`          | Remove um pet                    |
| GET    | `/pets/{id}/adopters` | Retorna o adotante do pet        |

**Adotantes**

| Método | Endpoint                   | Descrição                        |
|--------|----------------------------|----------------------------------|
| GET    | `/adopters`                | Lista todos os adotantes         |
| GET    | `/adopters/{id}`           | Busca adotante por ID            |
| POST   | `/adopters`                | Cadastra um novo adotante        |
| PUT    | `/adopters/{id}`           | Atualiza dados do adotante       |
| DELETE | `/adopters/{id}`           | Remove um adotante               |
| GET    | `/adopters/{id}/addresses` | Retorna o endereço do adotante   |

---

## Estrutura do projeto

```
src/main/java/com/pets
├── config/        # Configurações da aplicação
├── conn/          # Conexões
├── dto/           # Data Transfer Objects
├── entities/      # Entidades JPA
├── enums/         # Enumerações (espécie, gênero)
├── repository/    # Interfaces Spring Data JPA
├── resources/     # Arquivos de configuração
├── services/      # Regras de negócio
└── exceptions/    # Exceções personalizadas
```

---
