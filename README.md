## Tecnologias Utilizadas
- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL (com Docker)
- Lombok
- Maven

---

## Funcionalidades
- Cadastro de pets com:
  - Nome
  - Espécie (cachorro/gato)
  - Gênero
  - Idade
  - Endereço
  - Peso
- CRUD completo para:
  - Pets
- Validação de dados (ex.: ID válido, campos obrigatórios)
- Tratamento de exceções personalizadas
- Testes unitários

---

## Rodando o Projeto com Docker
1. Clone o repositório:
   ```bash
   git clone https://github.com/seuusuario/pets-management.git
   ```
   ```bash
   cd pets-management
   ```
2. Subir o banco de dados com Docker:
    ```bash
    docker compose up -d
    ```
3. Criar o banco de dados no PostgreSQL:
    ```bash
    CREATE DATABASE petsdb;
    ```
4. Rodar a aplicação:
    ```bash
    mvn spring-boot:run
    ```

Aplicação disponível em: http://localhost:8081

Endpoints Principais:
- GET /pets → lista todos os pets
- POST /pets → cadastra um novo pet
- GET /pets/{id} → busca pet por ID
- PUT /pets/{id} → atualiza dados do pet
- DELETE /pets/{id} → remove pet

Estrutura do projeto:
```bash
src/main/java/com/pets
│── config/       # Configurações da aplicação
│── conn/         # Conexões
│── dto/          # Data Transfer Objects
│── entities/     # Entidades
│── enums/        # Enumerações
│── repository/   # Interfaces do JPA
│── resources/    # Arquivos de configuração e recursos
│── services/     # Regras de negócio
│── exceptions/   # Exceções personalizadas
```

