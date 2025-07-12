# Demo Park API

## 📋 Descrição

Demo Park API é uma aplicação RESTful desenvolvida em Spring Boot que fornece funcionalidades de autenticação e gerenciamento de usuários. O sistema implementa autenticação baseada em JWT (JSON Web Tokens) e controle de acesso baseado em roles, oferecendo uma base sólida para aplicações que necessitam de sistema de autenticação robusto.

## 🚀 Funcionalidades

### Autenticação e Autorização
- **Login com JWT**: Autenticação segura usando tokens JWT
- **Controle de Acesso**: Sistema de roles (ADMIN e CLIENTE)
- **Proteção de Endpoints**: Endpoints protegidos por roles específicas
- **Validação de Tokens**: Verificação automática de validade dos tokens

### Gerenciamento de Usuários
- **Criação de Usuários**: Cadastro de novos usuários com validação
- **Consulta de Usuários**: Busca por ID ou listagem completa
- **Exclusão de Usuários**: Remoção de usuários do sistema
- **Validação de Dados**: Verificação de unicidade de username

### Documentação da API
- **Swagger/OpenAPI**: Documentação interativa da API
- **Endpoints Documentados**: Todos os endpoints com exemplos de uso

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 17**: Linguagem de programação
- **Spring Boot 3.0.6**: Framework principal
- **Spring Security**: Segurança e autenticação
- **Spring Data JPA**: Persistência de dados
- **Hibernate**: ORM (Object-Relational Mapping)
- **JWT (JSON Web Tokens)**: Autenticação stateless
- **BCrypt**: Criptografia de senhas

### Banco de Dados
- **H2 Database**: Banco em memória para desenvolvimento
- **MySQL**: Suporte para produção
- **PostgreSQL**: Suporte alternativo para produção

### Ferramentas de Desenvolvimento
- **Maven**: Gerenciamento de dependências
- **Lombok**: Redução de código boilerplate
- **MapStruct**: Mapeamento entre objetos
- **SpringDoc OpenAPI**: Documentação da API
- **JUnit 5**: Framework de testes
- **Mockito**: Framework de mocking para testes

### Validação e Tratamento de Erros
- **Bean Validation**: Validação de dados de entrada
- **Global Exception Handler**: Tratamento centralizado de exceções
- **Custom Business Exceptions**: Exceções específicas do negócio

## 📁 Estrutura do Projeto

```
demo-park-api/
├── src/
│   ├── main/
│   │   ├── java/com/mballem/demoparkapi/
│   │   │   ├── DemoParkApiApplication.java
│   │   │   └── web/
│   │   │       ├── config/
│   │   │       │   └── WebSecurityConfig.java
│   │   │       ├── controller/
│   │   │       │   ├── AuthController.java
│   │   │       │   └── UserController.java
│   │   │       ├── dto/
│   │   │       │   ├── LoginRequestDTO.java
│   │   │       │   ├── LoginResponseDTO.java
│   │   │       │   ├── UserRequestDTO.java
│   │   │       │   └── UserResponseDTO.java
│   │   │       ├── entity/
│   │   │       │   └── User.java
│   │   │       ├── exception/
│   │   │       │   ├── BusinessException.java
│   │   │       │   └── GlobalExceptionHandler.java
│   │   │       ├── jwt/
│   │   │       │   ├── JwtAuthenticationFilter.java
│   │   │       │   └── JwtUtil.java
│   │   │       ├── mapper/
│   │   │       │   └── UserMapper.java
│   │   │       ├── repository/
│   │   │       │   └── UserRepository.java
│   │   │       ├── service/
│   │   │       │   ├── IUserService.java
│   │   │       │   └── UserService.java
│   │   │       └── validation/
│   │   │           └── ValidationRules.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-test.properties
│   └── test/
│       └── java/com/mballem/demoparkapi/
│           └── web/service/
│               └── UserServiceTest.java
├── pom.xml
└── README.md
```

## 🔧 Configuração e Instalação

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Passos para Execução

1. **Clone o repositório**
   ```bash
   git clone <url-do-repositorio>
   cd demo-park-api
   ```

2. **Compile o projeto**
   ```bash
   mvn clean compile
   ```

3. **Execute a aplicação**
   ```bash
   mvn spring-boot:run
   ```

4. **Acesse a aplicação**
   - API: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui/index.html
   - H2 Console: http://localhost:8080/h2-console

### Configurações do Banco de Dados

#### H2 (Desenvolvimento)
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

#### MySQL (Produção)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/demo_park
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

#### PostgreSQL (Produção)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/demo_park
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## 📚 Endpoints da API

### Autenticação

#### POST /api/v1/auth/login
Realiza o login do usuário e retorna um token JWT.

**Request:**
```json
{
  "username": "usuario",
  "password": "senha123"
}
```

**Response (200):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "usuario",
  "roles": ["ROLE_CLIENTE"]
}
```

### Usuários

#### POST /api/v1/users
Cria um novo usuário (acesso público).

**Request:**
```json
{
  "username": "novo_usuario",
  "password": "senha123"
}
```

**Response (201):**
```json
{
  "id": 1,
  "username": "novo_usuario",
  "role": "ROLE_CLIENTE"
}
```

#### GET /api/v1/users/{id}
Busca um usuário por ID (requer autenticação).

**Headers:**
```
Authorization: Bearer <token_jwt>
```

**Response (200):**
```json
{
  "id": 1,
  "username": "usuario",
  "role": "ROLE_CLIENTE"
}
```

#### GET /api/v1/users
Lista todos os usuários (requer role ADMIN).

**Headers:**
```
Authorization: Bearer <token_jwt>
```

**Response (200):**
```json
[
  {
    "id": 1,
    "username": "usuario1",
    "role": "ROLE_CLIENTE"
  },
  {
    "id": 2,
    "username": "admin",
    "role": "ROLE_ADMIN"
  }
]
```

## 🔐 Segurança

### JWT (JSON Web Tokens)
- **Algoritmo**: HS256
- **Expiração**: 1 hora
- **Claims**: username e roles
- **Secret Key**: Configurável via propriedades

### Roles e Permissões
- **ROLE_ADMIN**: Acesso total ao sistema
- **ROLE_CLIENTE**: Acesso limitado aos próprios dados

### Criptografia
- **BCrypt**: Criptografia de senhas com salt automático
- **CORS**: Configurado para permitir requisições cross-origin

## 🧪 Testes

### Executar Testes
```bash
mvn test
```

### Cobertura de Testes
- **UserService**: Testes unitários com Mockito
- **Validações**: Testes de regras de negócio
- **Exceções**: Testes de cenários de erro

### Estrutura de Testes
```
src/test/java/com/mballem/demoparkapi/web/service/
└── UserServiceTest.java
```

## 📖 Documentação

### Swagger/OpenAPI
- **URL**: http://localhost:8080/swagger-ui/index.html
- **Especificação**: http://localhost:8080/v3/api-docs

### Endpoints Documentados
- Todos os endpoints possuem anotações OpenAPI
- Exemplos de request/response
- Códigos de status HTTP
- Descrições detalhadas

## 🔄 Padrões de Desenvolvimento

### Arquitetura
- **Layered Architecture**: Separação clara entre camadas
- **RESTful API**: Design de API seguindo princípios REST
- **DTO Pattern**: Transferência de dados entre camadas
- **Repository Pattern**: Abstração da camada de dados

### Design Patterns
- **Builder Pattern**: Construção de objetos complexos
- **Factory Pattern**: Criação de objetos
- **Strategy Pattern**: Diferentes estratégias de validação
- **Template Method**: Estrutura comum para operações

### Princípios SOLID
- **Single Responsibility**: Cada classe tem uma responsabilidade
- **Open/Closed**: Aberto para extensão, fechado para modificação
- **Liskov Substitution**: Substituição de implementações
- **Interface Segregation**: Interfaces específicas
- **Dependency Inversion**: Inversão de dependências

## 🚀 Deploy

### Docker
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/demo-park-api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Build para Produção
```bash
mvn clean package -DskipTests
```

### Variáveis de Ambiente
```bash
export SPRING_PROFILES_ACTIVE=prod
export JWT_SECRET=sua_chave_secreta_muito_segura
export DATABASE_URL=jdbc:postgresql://localhost:5432/demo_park
export DATABASE_USERNAME=usuario
export DATABASE_PASSWORD=senha
```

## 📊 Monitoramento

### Logs
- **SLF4J + Logback**: Framework de logging
- **Níveis**: ERROR, WARN, INFO, DEBUG
- **Estrutura**: Logs estruturados para análise

### Métricas
- **Spring Boot Actuator**: Endpoints de monitoramento
- **Health Check**: /actuator/health
- **Info**: /actuator/info

## 🤝 Contribuição

### Como Contribuir
1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

### Padrões de Código
- **Convenções Java**: Seguir convenções de nomenclatura
- **Documentação**: Comentar métodos complexos
- **Testes**: Manter cobertura de testes
- **Commits**: Mensagens descritivas e claras

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👥 Autores

- **Marcelo Ballem** - *Desenvolvimento inicial* - [GitHub](https://github.com/mballem)

## 🙏 Agradecimentos

- Spring Boot Team
- Comunidade Java
- Contribuidores do projeto

## 📞 Suporte

Para dúvidas ou suporte:
- **Email**: contato@exemplo.com
- **Issues**: [GitHub Issues](https://github.com/seu-usuario/demo-park-api/issues)
- **Documentação**: [Wiki do Projeto](https://github.com/seu-usuario/demo-park-api/wiki)

---

**Versão**: 0.0.1-SNAPSHOT  
**Última Atualização**: Dezembro 2024  
**Status**: Em Desenvolvimento 