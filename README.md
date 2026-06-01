<div align="center">

<h1>✂️ BarberHub API</h1>

<p>
  <strong>Plataforma REST de agendamento para barbearias e salões</strong><br/>
  Construída com Java 21 · Spring Boot 3.2 · Spring Security + OAuth2 · PostgreSQL
</p>

<p>
  <img src="https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.2-brightgreen?style=flat-square&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Security-OAuth2_+_JWT-6DB33F?style=flat-square&logo=springsecurity&logoColor=white" />
  <img src="https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square&logo=postgresql&logoColor=white" />
  <img src="https://img.shields.io/badge/MapStruct-1.6.3-red?style=flat-square" />
  <img src="https://img.shields.io/badge/OpenAPI-Swagger_UI-85EA2D?style=flat-square&logo=swagger&logoColor=black" />
  <img src="https://img.shields.io/badge/Status-Em_desenvolvimento-yellow?style=flat-square" />
</p>

</div>

---

## 📌 Sobre o projeto

O **BarberHub API** é uma API REST completa para gestão de agendamentos em barbearias e salões de beleza. O sistema permite que clientes façam reservas com profissionais específicos, que estabelecimentos gerenciem sua agenda e serviços, e que administradores tenham controle total sobre a plataforma.

O projeto foi desenvolvido como um **capstone de back-end**, aplicando arquitetura de produção com segurança robusta, separação de responsabilidades e documentação automática.

---

## 🧩 Módulos

| Módulo | Descrição |
|---|---|
| **Auth** | Autenticação via OAuth2 Authorization Server + JWT |
| **Usuários** | Cadastro e gerenciamento de usuários por papel (role) |
| **Estabelecimentos** | CRUD de barbearias e salões |
| **Serviços** | Serviços oferecidos por cada estabelecimento |
| **Profissionais** | Profissionais vinculados a estabelecimentos |
| **Disponibilidade** | Gestão de horários disponíveis por profissional |
| **Agendamentos** | Criação e controle do ciclo de vida dos agendamentos |

---

## 🔐 Segurança e Controle de Acesso

A autenticação é realizada via **OAuth2 Authorization Server** com tokens **JWT**. Cada endpoint é protegido por papéis (roles):

| Role | Permissões |
|---|---|
| `CLIENT` | Buscar serviços, criar agendamentos, avaliar atendimentos |
| `PROFESSIONAL` | Gerenciar própria disponibilidade, visualizar agendamentos |
| `OWNER` | Gerenciar estabelecimento, serviços e profissionais vinculados |
| `ADMIN` | Acesso total à plataforma |

---

## 🔄 Fluxo de Status dos Agendamentos

```
PENDING ──► CONFIRMED ──► COMPLETED
               │
               └──► CANCELLED
```

---

## 🛠️ Stack Tecnológica

### Back-end
- **Java 21** — LTS com records, sealed classes e virtual threads
- **Spring Boot 3.2** — Framework principal
- **Spring Security** — OAuth2 Authorization Server + Resource Server (JWT)
- **Spring Data JPA + Hibernate** — ORM e persistência
- **JPA Specification** — Filtros dinâmicos e consultas flexíveis
- **Bean Validation** — Validação declarativa de DTOs
- **MapStruct 1.6.3** — Mapeamento automático entre entidades e DTOs
- **Lombok** — Redução de boilerplate
- **Thymeleaf** — Template engine (para páginas de login/consent do OAuth2)

### Banco de dados
- **PostgreSQL** — Banco relacional principal

### Documentação
- **SpringDoc OpenAPI 2.6 / Swagger UI** — Documentação interativa em `/swagger-ui.html`

### Build
- **Maven** — Gerenciamento de dependências e build

---

## 📂 Estrutura do Projeto

```
src/
└── main/
    ├── java/
    │   └── com/itoshi_m_dev/schedulingapi/
    │       ├── auth/           # Configuração OAuth2, JWT, SecurityFilterChain
    │       ├── user/           # Módulo de usuários
    │       ├── establishment/  # Módulo de estabelecimentos
    │       ├── service/        # Módulo de serviços
    │       ├── professional/   # Módulo de profissionais
    │       ├── availability/   # Módulo de disponibilidade
    │       ├── appointment/    # Módulo de agendamentos
    │       ├── review/         # Módulo de avaliações
    │       └── shared/         # Exception handler, DTOs genéricos, config global
    └── resources/
        ├── application.properties
        └── templates/          # Templates Thymeleaf (OAuth2 consent page)
```

---

## 🚀 Como rodar localmente

### Pré-requisitos

- Java 21+
- Maven 3.9+
- PostgreSQL 14+ rodando localmente

### 1. Clone o repositório

```bash
git clone https://github.com/itoshi-m-dev/barberHubApi.git
cd barberHubApi
```

### 2. Configure o banco de dados

Crie um banco no PostgreSQL:

```sql
CREATE DATABASE barberhub;
```

### 3. Configure as variáveis de ambiente

Edite o arquivo `src/main/resources/application.properties` (ou use variáveis de ambiente):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/barberhub
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
```

### 4. Build e execução

```bash
./mvnw clean install
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

---

## 📖 Documentação da API

Com a aplicação rodando, acesse a documentação interativa via Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

---


## 📋 Variáveis de Ambiente

| Variável                     | Descrição                       | Padrão |
|------------------------------|---------------------------------|---|
| `SPRING_DATASOURCE_URL`      | URL de conexão com o PostgreSQL | `jdbc:postgresql://localhost:5432/barberhub` |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco                | — |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco                  | — |
| `GOOGLE_CLIENT_ID`           | Chave secreta para Oauth2       | — |
| `GOOGLE_CLIENT_SECRET`       | Chave secreta para Oauth2       | — |
---

## 🗺️ Próximos passos

- [ ] Dockerizar a aplicação com `docker-compose`
- [ ] Implementar notificações por e-mail (confirmação e lembrete de agendamento)
- [ ] Deploy em ambiente cloud (Railway / Render / AWS)
- [ ] Aumentar cobertura de testes com JUnit + Mockito
- [ ] Adicionar rate limiting nos endpoints públicos

---

## 👨‍💻 Autor

Desenvolvido por **Emanuel** — estudante de Java & Spring Boot apaixonado por back-end.

<p>
  <a href="https://github.com/itoshi-m-dev">
    <img src="https://img.shields.io/badge/GitHub-itoshi--m--dev-181717?style=flat-square&logo=github" />
  </a>
  <a href="https://www.linkedin.com/in/emanuel-mellina/">
    <img src="https://img.shields.io/badge/LinkedIn-Conectar-0A66C2?style=flat-square&logo=linkedin" />
  </a>
</p>

---

<div align="center">
  <sub>Feito com ☕ Java e muita determinação.</sub>
</div>
