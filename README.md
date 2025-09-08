# Locadora de Filmes - Sistema Spring Boot

Sistema completo de gerenciamento de locadora de filmes desenvolvido com Spring Boot, MySQL e Thymeleaf seguindo o padrão MVC.

## Tecnologias Utilizadas

- **Backend**: Java 17 + Spring Boot 3.2.0
- **Banco de Dados**: MySQL 8.0
- **ORM**: JPA/Hibernate
- **Frontend**: Thymeleaf + Bootstrap 5
- **Build Tool**: Maven

## Funcionalidades

### Entidades Principais
- **Gênero**: Categorização dos filmes
- **Produtora**: Empresas produtoras dos filmes
- **Cliente**: Cadastro de clientes com validação de CPF e email
- **Filme**: Catálogo de filmes com classificação etária
- **Locação**: Controle de empréstimos e devoluções

### Funcionalidades do Sistema
- CRUD completo para todas as entidades
- Validação de dados com Bean Validation
- Relacionamentos JPA entre entidades
- API REST completa para integração
- Interface web responsiva com Bootstrap
- Controle de locações abertas e atrasadas
- Relatórios e dashboards

## Configuração e Execução

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- MySQL 8.0+

### 1. Configuração do Banco de Dados

Crie um banco de dados MySQL:
```sql
CREATE DATABASE locadora_filmes;
```

### 2. Configuração da Aplicação

Edite o arquivo `src/main/resources/application.properties` com suas credenciais do MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/locadora_filmes?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 3. Executando a Aplicação

```bash
# Compilar o projeto
mvn clean compile

# Executar a aplicação
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`


