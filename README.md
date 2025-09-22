# Sistema de Usuários e Equipes (Spring Boot + MySQL)

Este projeto foi desenvolvido como avaliação A3 da instituição **Anhembi Morumbi**.  
Ele implementa um **sistema de cadastro de usuários, equipes e projetos**, utilizando **Spring Boot** como backend e **MySQL** como banco de dados relacional.

---

## Tecnologias Utilizadas
- **Java 17+**
- **Spring Boot 3**
  - Spring Web (APIs REST)
  - Spring Data JPA (persistência)
- **MySQL 8**
- **Maven**
- **Postman** (para testes da API)

---

## Estrutura do Projeto
sistema-backend/
├── src/main/java/com/sistema/backend
│ ├── controller/ # Controladores REST
│ ├── model/ # Entidades (Usuario, Equipe, Projeto)
│ ├── repository/ # Interfaces JPA
│ └── BackendApplication.java
└── src/main/resources
├── application.properties
└── data.sql (opcional para popular o banco)

---
Dados iniciais (data.sql)

O projeto já vem com um arquivo data.sql em src/main/resources/ que popula o banco automaticamente quando a aplicação é iniciada.

Esse arquivo cria:

3 usuários (Amanda, Guilherme e Hamilton)

1 equipe (Equipe Backend)

1 projeto (Sistema Web)

E já faz a vinculação de usuários com a equipe e com o projeto.

Estrutura inicial do banco
```properties
-- Criando usuários
INSERT INTO usuario (id, nome, cpf, email, cargo, login, senha)
VALUES 
(1, 'Amanda', '11111111111', 'amanda@email.com', 'Desenvolvedora', 'amandaDev', '123456'),
(2, 'Guilherme', '22222222222', 'guilherme@email.com', 'Analista', 'guiAnalyst', '654321'),
(3, 'Hamilton', '33333333333', 'hamilton@email.com', 'Tester', 'hamTester', 'senha789');

-- Criando equipes
INSERT INTO equipe (id, nome) VALUES
(1, 'Equipe Backend');

-- Criando projetos
INSERT INTO projeto (id, nome, descricao) VALUES
(1, 'Sistema Web', 'Projeto para controle de usuários, equipes e projetos');

-- Relacionando usuários com a equipe
INSERT INTO usuario_equipes (usuario_id, equipe_id) VALUES
(1, 1),
(2, 1),
(3, 1);

-- Relacionando usuários com o projeto
INSERT INTO usuario_projetos (usuario_id, projeto_id) VALUES
(1, 1),
(2, 1),
(3, 1);
```

Como funciona

Assim que você rodar o projeto (mvn spring-boot:run ou no IntelliJ/Eclipse), o Spring Boot lê o arquivo data.sql e insere os dados no banco automaticamente.

Isso garante que você já tenha dados prontos para testar no Postman, sem precisar criar tudo manualmente.

---

## 🗄️ Banco de Dados

O projeto utiliza **MySQL**.  
Configure seu `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sistema_poo
spring.datasource.username=root
spring.datasource.password=SUASENHA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

O Hibernate cria automaticamente as tabelas:

  * usuario

  * equipe

  * projeto

Tabelas intermediárias para relacionamentos (usuario_equipes, usuario_projetos, etc).

---

Relacionamentos entre Entidades

- Usuário
  * Possui atributos: nome, cpf, email, cargo, login, senha
  * Pode participar de várias equipes
  * Pode estar vinculado a vários projetos

- Equipe
  * Possui nome
  * Pode ter vários usuários

- Projeto
  * Possui nome e descricao
  * Pode ter vários usuários

Diagrama simplificado:
```properties
Usuario <-----> Equipe
   |
   +-----> Projeto
```
 
Endpoints da API
Usuários
* Listar todos:
  * GET /usuarios

* Criar novo:
  * POST /usuarios
```properties
{
  "nome": "Amanda",
  "cpf": "12345678900",
  "email": "amanda@email.com",
  "cargo": "Dev",
  "login": "amandaDev",
  "senha": "123456"
}
```

* Atualizar:
  * PUT /usuarios/{id}

* Deletar:
  * DELETE /usuarios/{id}

* Anexar usuário em equipes:
  * PATCH /usuarios/{id}/equipes
```properties
[
  { "id": 1 },
  { "id": 2 }
]
```

* Anexar usuário em projetos:
  * PATCH /usuarios/{id}/projetos
```properties
[
  { "id": 1 }
]
```
  
Equipes
* Listar equipes:
  * GET /equipes

* Criar nova equipe:
  * POST /equipes
```properties
{
  "nome": "Equipe Backend"
}
```

* Adicionar usuários a uma equipe:
  * PATCH /equipes/{id}/usuarios
```properties
[
  { "id": 1 },
  { "id": 2 }
]
```

Projetos
* Listar projetos:
  * GET /projetos

* Criar novo projeto:
  * POST /projetos
```properties
{
  "nome": "Sistema Web",
  "descricao": "Projeto para controle de usuários e equipes"
}
```

Testando no Postman
1. Criar um usuário:
```properties
POST http://localhost:8080/usuarios
```
2. Criar uma equipe:
```properties
POST http://localhost:8080/equipes
```
3. Vincular usuário à equipe:
```properties
PATCH http://localhost:8080/usuarios/1/equipes
Body:
[
  { "id": 1 }
]
```
4. Criar projeto e vincular usuário:
```properties
POST http://localhost:8080/projetos
PATCH http://localhost:8080/usuarios/1/projetos
```

Resetando o Banco
Se precisar zerar o banco:
```properties
DROP DATABASE sistema_poo;
CREATE DATABASE sistema_poo;
```
* Autores
  * Projeto desenvolvido por Guilherme Antônio e Amanda Bueno para fins acadêmicos na disciplina de POO.
