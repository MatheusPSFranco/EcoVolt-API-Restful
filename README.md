# ⚡ EcoVolt API RESTful

API RESTful desenvolvida em **Java 21** com **Spring Boot 4.1.1** para a plataforma **EcoVolt** — um sistema de gamificação voltado à sustentabilidade ambiental e energia limpa, alinhado aos Objetivos de Desenvolvimento Sustentável (ODS) da ONU.

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Arquitetura do Projeto](#-arquitetura-do-projeto)
- [Pré-requisitos](#-pré-requisitos)
- [Configuração e Execução](#-configuração-e-execução)
- [Endpoints da API](#-endpoints-da-api)
- [Padrões de Projeto](#-padrões-de-projeto)
- [Tratamento de Erros](#-tratamento-de-erros)
- [Validações](#-validações)
- [Integrantes do Grupo](#-integrantes-do-grupo)

---

## 📖 Sobre o Projeto

O **EcoVolt** é uma plataforma gamificada que incentiva práticas sustentáveis através de missões, conquistas, rankings e recompensas. Os usuários ganham pontos e XP ao realizar ações sustentáveis, que são validadas por inteligência artificial. O sistema conta com um programa de tiers (ligas) e níveis para engajar os participantes.

### Funcionalidades Principais

- **Gestão de Usuários** — cadastro, autenticação e perfil com níveis e tiers
- **Ações Sustentáveis** — catálogo de ações com pontuação e dificuldade
- **Missões** — desafios semanais compostos por ações sustentáveis
- **Sistema de Pontuação** — XP e pontos obtidos por diversas origens
- **Conquistas** — medalhas desbloqueáveis por critérios específicos
- **Ranking e Tiers** — classificação por período com promoção/rebaixamento
- **Recompensas** — benefícios como subsídio em contas de energia
- **Validação por IA** — verificação automática de evidências enviadas
- **Login Diário** — sequências de login com bônus acumulativos
- **Recuperação de Login** — possibilidade de recuperar dias perdidos

---

## 🛠 Tecnologias Utilizadas

| Tecnologia | Versão | Descrição |
|---|---|---|
| **Java** | 21 | Linguagem de programação |
| **Spring Boot** | 4.1.1 | Framework para aplicações web |
| **Spring Web MVC** | — | Módulo REST/MVC |
| **Spring JDBC** | — | Acesso a dados via JDBC |
| **Spring Validation** | — | Validação de dados com Bean Validation |
| **Oracle Database** | — | Banco de dados relacional |
| **ojdbc17** | — | Driver JDBC para Oracle |
| **ModelMapper** | 3.2.0 | Mapeamento DTO ↔ Entidade |
| **Maven** | — | Gerenciamento de dependências e build |

---

## 🏗 Arquitetura do Projeto

O projeto segue uma arquitetura em camadas com o padrão **DTO (Data Transfer Object)**:

```
src/main/java/br/com/fiap/ecovolt_api/
├── config/                  # Configurações (ModelMapper)
├── controller/              # Controladores REST (22 controllers)
├── dao/                     # Data Access Objects - acesso ao banco (22 DAOs)
├── dto/                     # Data Transfer Objects (66 DTOs)
├── exception/               # Exceções e tratamento global de erros
├── model/                   # Entidades de domínio (22 models)
└── EcovoltApiApplication.java
```

### Fluxo da Requisição

```
Cliente → Controller (recebe DTO) → ModelMapper → DAO (persiste Entidade) → Controller (retorna DTO)
```

### Modelo de Dados

O sistema é composto por **17 entidades com ID simples** e **5 tabelas associativas com chave composta**:

#### Entidades Principais
| Entidade | Descrição |
|---|---|
| `Usuario` | Usuários da plataforma |
| `Tier` | Ligas de classificação (Bronze, Prata, Ouro...) |
| `Nivel` | Níveis de experiência do usuário |
| `Missao` | Desafios semanais |
| `Conquista` | Medalhas e conquistas desbloqueáveis |
| `AcaoSustentavel` | Catálogo de ações sustentáveis |
| `Ods` | Objetivos de Desenvolvimento Sustentável |
| `EnvioAcao` | Evidências de ações realizadas |
| `Validacao` | Validação de evidências por IA |
| `Pontuacao` | Registro de pontos e XP obtidos |
| `Ranking` | Classificação por período |
| `Recompensa` | Benefícios concedidos |
| `ContaEnergia` | Contas de energia dos usuários |
| `Interacao` | Interações do usuário na plataforma |
| `LoginDiario` | Registro de logins diários |
| `RecuperacaoLogin` | Recuperação de dias de login perdidos |
| `RecursoRevisao` | Recursos de revisão de validações |

#### Tabelas Associativas (Chave Composta)
| Entidade | Relacionamento |
|---|---|
| `UsuarioConquista` | Usuário ↔ Conquista |
| `UsuarioMissao` | Usuário ↔ Missão |
| `OdsMissao` | ODS ↔ Missão |
| `AcaoSustentavelMissao` | Ação Sustentável ↔ Missão |
| `EnvioAcaoMissao` | Envio de Ação ↔ Missão |

---

## 📌 Pré-requisitos

- **Java 21** (JDK)
- **Maven 3.9+** (ou usar o wrapper `mvnw` incluso)
- **Oracle Database** acessível (configurado em `application.properties`)

---

## ⚙ Configuração e Execução

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/EcoVolt-API-Restful.git
cd EcoVolt-API-Restful
```

### 2. Configure o banco de dados

Edite o arquivo `src/main/resources/application.properties` com as credenciais do seu banco Oracle:

```properties
spring.datasource.url=jdbc:oracle:thin:@SEU_HOST:1521:orcl
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

### 3. Execute a aplicação

```bash
./mvnw spring-boot:run
```

A API será iniciada em: **http://localhost:8080**

---

## 📡 Endpoints da API

Todos os endpoints seguem o padrão REST com operações CRUD completas.

### Entidades com ID Simples

| Recurso | GET (listar) | GET (buscar) | POST (criar) | PUT (atualizar) | DELETE (excluir) |
|---|---|---|---|---|---|
| **Usuário** | `GET /usuario` | `GET /usuario/{id}` | `POST /usuario` | `PUT /usuario/{id}` | `DELETE /usuario/{id}` |
| **Tier** | `GET /tier` | `GET /tier/{id}` | `POST /tier` | `PUT /tier/{id}` | `DELETE /tier/{id}` |
| **Nível** | `GET /nivel` | `GET /nivel/{id}` | `POST /nivel` | `PUT /nivel/{id}` | `DELETE /nivel/{id}` |
| **Missão** | `GET /missao` | `GET /missao/{id}` | `POST /missao` | `PUT /missao/{id}` | `DELETE /missao/{id}` |
| **Conquista** | `GET /conquista` | `GET /conquista/{id}` | `POST /conquista` | `PUT /conquista/{id}` | `DELETE /conquista/{id}` |
| **Ação Sustentável** | `GET /acao-sustentavel` | `GET /acao-sustentavel/{id}` | `POST /acao-sustentavel` | `PUT /acao-sustentavel/{id}` | `DELETE /acao-sustentavel/{id}` |
| **ODS** | `GET /ods` | `GET /ods/{id}` | `POST /ods` | `PUT /ods/{id}` | `DELETE /ods/{id}` |
| **Envio Ação** | `GET /envio-acao` | `GET /envio-acao/{id}` | `POST /envio-acao` | `PUT /envio-acao/{id}` | `DELETE /envio-acao/{id}` |
| **Validação** | `GET /validacao` | `GET /validacao/{id}` | `POST /validacao` | `PUT /validacao/{id}` | `DELETE /validacao/{id}` |
| **Pontuação** | `GET /pontuacao` | `GET /pontuacao/{id}` | `POST /pontuacao` | `PUT /pontuacao/{id}` | `DELETE /pontuacao/{id}` |
| **Ranking** | `GET /ranking` | `GET /ranking/{id}` | `POST /ranking` | `PUT /ranking/{id}` | `DELETE /ranking/{id}` |
| **Recompensa** | `GET /recompensa` | `GET /recompensa/{id}` | `POST /recompensa` | `PUT /recompensa/{id}` | `DELETE /recompensa/{id}` |
| **Conta Energia** | `GET /conta-energia` | `GET /conta-energia/{id}` | `POST /conta-energia` | `PUT /conta-energia/{id}` | `DELETE /conta-energia/{id}` |
| **Interação** | `GET /interacao` | `GET /interacao/{id}` | `POST /interacao` | `PUT /interacao/{id}` | `DELETE /interacao/{id}` |
| **Login Diário** | `GET /login-diario` | `GET /login-diario/{id}` | `POST /login-diario` | `PUT /login-diario/{id}` | `DELETE /login-diario/{id}` |
| **Recuperação Login** | `GET /recuperacao-login` | `GET /recuperacao-login/{id}` | `POST /recuperacao-login` | `PUT /recuperacao-login/{id}` | `DELETE /recuperacao-login/{id}` |
| **Recurso Revisão** | `GET /recurso-revisao` | `GET /recurso-revisao/{id}` | `POST /recurso-revisao` | `PUT /recurso-revisao/{id}` | `DELETE /recurso-revisao/{id}` |

### Tabelas Associativas (Chave Composta)

| Recurso | GET (listar) | GET (buscar) | POST (criar) | PUT (atualizar) | DELETE (excluir) |
|---|---|---|---|---|---|
| **Usuário-Conquista** | `GET /usuario-conquista` | `GET /usuario-conquista/{idUsuario}/{idConquista}` | `POST /usuario-conquista` | `PUT /usuario-conquista/{idUsuario}/{idConquista}` | `DELETE /usuario-conquista/{idUsuario}/{idConquista}` |
| **Usuário-Missão** | `GET /usuario-missao` | `GET /usuario-missao/{idUsuario}/{idMissao}` | `POST /usuario-missao` | `PUT /usuario-missao/{idUsuario}/{idMissao}` | `DELETE /usuario-missao/{idUsuario}/{idMissao}` |
| **ODS-Missão** | `GET /ods-missao` | `GET /ods-missao/{idOds}/{idMissao}` | `POST /ods-missao` | `PUT /ods-missao/{idOds}/{idMissao}` | `DELETE /ods-missao/{idOds}/{idMissao}` |
| **Ação-Missão** | `GET /acao-sustentavel-missao` | `GET /acao-sustentavel-missao/{idAcao}/{idMissao}` | `POST /acao-sustentavel-missao` | `PUT /acao-sustentavel-missao/{idAcao}/{idMissao}` | `DELETE /acao-sustentavel-missao/{idAcao}/{idMissao}` |
| **Envio-Missão** | `GET /envio-acao-missao` | `GET /envio-acao-missao/{idEnvio}/{idMissao}` | `POST /envio-acao-missao` | `PUT /envio-acao-missao/{idEnvio}/{idMissao}` | `DELETE /envio-acao-missao/{idEnvio}/{idMissao}` |

### Exemplos de Uso

#### Criar um Usuário

```http
POST /usuario
Content-Type: application/json

{
  "nome": "Maria Silva",
  "email": "maria@email.com",
  "senhaHash": "hash123",
  "status": "ATIVO",
  "tipo": "COMUM",
  "ativo": "S",
  "idTier": 1,
  "idNivel": 1
}
```

**Resposta (201 Created):**

```json
{
  "id": 1,
  "nome": "Maria Silva",
  "email": "maria@email.com",
  "dataCadastro": "2026-10-28",
  "status": "ATIVO",
  "tipo": "COMUM",
  "xpTotal": 0,
  "pontuacaoTotal": 0,
  "ativo": "S",
  "idTier": 1,
  "idNivel": 1
}
```

#### Criar uma Missão

```http
POST /missao
Content-Type: application/json

{
  "nome": "Economize Energia",
  "descricao": "Reduza o consumo de energia por 7 dias",
  "dificuldade": "MEDIA",
  "pontuacaoRecompensa": 500,
  "xpRecompensa": 200,
  "quantidadeSemanal": 1,
  "tipo": "SEMANAL",
  "status": true,
  "idTier": 1,
  "idNivel": 2
}
```

#### Criar uma Associação Usuário-Conquista

```http
POST /usuario-conquista
Content-Type: application/json

{
  "idUsuario": 1,
  "idConquista": 3,
  "progressoAtual": 0,
  "status": "EM_ANDAMENTO",
  "visivelPerfil": "S"
}
```

---

## 🎨 Padrões de Projeto

### DTO (Data Transfer Object)

Cada entidade possui **3 DTOs** que separam responsabilidades:

| DTO | Uso | Exemplo |
|---|---|---|
| `CadastroXxxDto` | Recebe dados no `POST` | `CadastroUsuarioDto` |
| `AtualizacaoXxxDto` | Recebe dados no `PUT` | `AtualizacaoUsuarioDto` |
| `DetalhesXxxDto` | Retorna dados nas respostas | `DetalhesUsuarioDto` |

A conversão entre DTO e Entidade é feita com **ModelMapper**:

```java
// DTO → Entidade
Usuario usuario = mapper.map(dto, Usuario.class);

// Entidade → DTO
DetalhesUsuarioDto response = mapper.map(usuario, DetalhesUsuarioDto.class);
```

### Camada DAO

Acesso a dados via **JDBC puro** com `PreparedStatement`, garantindo segurança contra SQL Injection.

---

## ❌ Tratamento de Erros

A API possui tratamento centralizado de exceções com `@RestControllerAdvice`:

| Exceção | Status HTTP | Descrição |
|---|---|---|
| `EntidadeNaoEncontradaException` | `404 Not Found` | Recurso não encontrado |
| `MethodArgumentNotValidException` | `400 Bad Request` | Falha de validação dos dados |
| `IllegalArgumentException` | `400 Bad Request` | Argumento inválido |
| `SQLException` | `500 Internal Server Error` | Erro no banco de dados |

**Formato de resposta de erro:**

```json
{
  "erro": "Entidade não encontrada",
  "detalhe": "Usuário com ID 99 não encontrado."
}
```

---

## ✅ Validações

Os DTOs utilizam **Bean Validation** (Jakarta Validation) para garantir integridade dos dados:

| Anotação | Descrição | Exemplo |
|---|---|---|
| `@NotBlank` | Campo não pode ser nulo/vazio | Nome, e-mail |
| `@NotNull` | Campo não pode ser nulo | IDs de FK |
| `@Email` | Deve ser um e-mail válido | E-mail do usuário |
| `@Positive` | Valor deve ser positivo | IDs, ordem |
| `@PositiveOrZero` | Valor deve ser ≥ 0 | Pontuação, XP |
| `@Size` | Tamanho deve estar no intervalo | Textos com limite |

Exemplo de validação no controller:

```java
@PostMapping
public ResponseEntity<DetalhesUsuarioDto> adicionar(
        @Valid @RequestBody CadastroUsuarioDto dto,
        UriComponentsBuilder uriBuilder) { ... }
```

---
> Projeto desenvolvido como parte da disciplina de **Domain Driven Design** na **FIAP** — 2º semestre de 2026.
