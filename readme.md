# 📟 Calculadora Micro Service CI/CD

> Documentação completa, didática e profissional para desenvolvedores de todos os níveis.

---

## Índice

1. [Introdução ao Projeto](#1-introdução-ao-projeto)
2. [Principais Stacks e Tecnologias](#2-principais-stacks-e-tecnologias)
3. [Pré-requisitos para Rodar Localmente](#3-pré-requisitos-para-rodar-localmente)
4. [Tutorial Completo: Como Rodar o Projeto Localmente](#4-tutorial-completo-como-rodar-o-projeto-localmente)
5. [Diagrama de Fluxo e Arquitetura](#5-diagrama-de-fluxo-e-arquitetura)
6. [Documentação da API](#6-documentação-da-api)
7. [Deploy em Produção](#7-deploy-em-produção)
8. [Pipeline CI/CD](#8-pipeline-cicd)
9. [Principais Aprendizados do Projeto](#9-principais-aprendizados-do-projeto)
10. [Estrutura de Pastas do Projeto](#10-estrutura-de-pastas-do-projeto)
11. [Como Rodar Testes](#11-como-rodar-testes)
12. [Variáveis de Ambiente Explicadas](#12-variáveis-de-ambiente-explicadas)

---

## 1. 🧭 Introdução ao Projeto

### Nome Oficial

**Calculadora Micro Service CI/CD**

### Descrição

Este projeto é um sistema distribuído composto por **dois microsserviços Java** que se comunicam entre si de forma assíncrona via Feign Client:

- **calc-app (Aplicação Principal):** Recebe as requisições do usuário, executa os cálculos matemáticos (soma, subtração, multiplicação e divisão) e encaminha os resultados para persistência.
- **calc-application-db (Aplicação de Persistência):** Responsável exclusivamente por salvar e expor as operações realizadas, conectando-se a um banco de dados MySQL. As credenciais de acesso são gerenciadas de forma segura pelo **HashiCorp Vault**.

Toda a infraestrutura é containerizada com **Docker**, as imagens são publicadas no **AWS Elastic Container Registry (ECR)** e o deploy é orquestrado por pipelines **Jenkins** com CI/CD completo e automatizado. O monitoramento em tempo real é feito com **Prometheus** e **Grafana**, com dashboards pré-configurados.

### Objetivo Principal

Demonstrar na prática uma arquitetura de microsserviços *production-ready* com:
- Separação clara de responsabilidades entre serviços
- Segurança de segredos (secrets) com Vault
- Monitoramento e observabilidade com métricas customizadas via AOP
- Pipeline CI/CD automatizado do commit ao deploy

### Principais Funcionalidades

- ➕ Realizar operações matemáticas: **Soma**, **Subtração**, **Multiplicação** e **Divisão**
- 💾 Persistir automaticamente cada operação realizada no banco de dados via Feign Client
- 📋 Listar **todas** as operações já realizadas
- 🔍 Recuperar a **operação mais recente** salva
- 🔐 Gerenciar credenciais de banco de dados com segurança via **HashiCorp Vault** (sem expor senhas em variáveis de ambiente)
- 📊 Monitorar a saúde da aplicação com **Prometheus + Grafana** (5 dashboards pré-configurados)
- 📈 Métricas customizadas por método de serviço usando **Spring AOP** (contadores de sucesso/falha, timers)
- 🚀 Build, push para ECR e deploy automatizados via **Jenkins**
- 🐳 Infraestrutura 100% containerizada com **Docker Compose**
- ✅ Testes unitários e de integração com **JUnit 5 + Mockito**

---

## 2. 🛠️ Principais Stacks e Tecnologias

| Camada | Tecnologia | Versão | Propósito |
|---|---|---|---|
| **Backend** | Java | 21 | Linguagem principal dos microsserviços |
| **Backend** | Spring Boot | 4.0.2 | Framework principal para criação das APIs REST |
| **Backend** | Spring Data JPA | (via Boot) | Mapeamento objeto-relacional e acesso ao banco |
| **Backend** | Spring Web MVC | (via Boot) | Criação dos controllers REST |
| **Backend** | Spring Cloud OpenFeign | 2025.1.0 | Comunicação HTTP declarativa entre microsserviços |
| **Backend** | Spring Cloud Vault Config | 2025.1.0 | Integração com HashiCorp Vault para leitura de secrets |
| **Backend** | Spring Boot Actuator | (via Boot) | Endpoints de saúde, métricas e informações da aplicação |
| **Backend** | Spring AOP | 3.5.x | Interceptação de métodos para métricas customizadas |
| **Banco de Dados** | MySQL | 5.7 | Banco de dados principal para persistência das operações |
| **Banco de Dados** | PostgreSQL | — | Suporte alternativo (scripts SQL incluídos) |
| **Banco de Dados** | SQL Server | — | Suporte alternativo (scripts SQL incluídos) |
| **Segurança** | HashiCorp Vault | 1.18.3 | Gerenciamento seguro de credenciais e secrets |
| **Monitoramento** | Prometheus | latest | Coleta e armazenamento de métricas |
| **Monitoramento** | Grafana | latest (OSS) | Visualização de dashboards e alertas |
| **Monitoramento** | Micrometer + Prometheus Registry | (via Boot) | Exposição de métricas no formato Prometheus |
| **Monitoramento** | MySQL Exporter | latest | Exporta métricas do MySQL para o Prometheus |
| **Monitoramento** | cAdvisor | latest | Métricas de containers Docker |
| **DevOps** | Docker | 24+ | Containerização de todos os serviços |
| **DevOps** | Docker Compose | 2.x | Orquestração local dos containers |
| **DevOps** | Jenkins | — | Servidor de CI/CD para automação de pipelines |
| **DevOps** | AWS ECR | — | Registro privado de imagens Docker na nuvem |
| **DevOps** | AWS IAM | — | Gerenciamento de permissões de acesso à AWS |
| **Build** | Apache Maven | 3.9.12 | Gerenciador de dependências e build do projeto Java |
| **Testes** | JUnit 5 | 5.10.0 | Framework de testes unitários |
| **Testes** | Mockito | 5.4.0 | Criação de mocks para testes isolados |
| **Utilitários** | Lombok | — | Redução de código boilerplate (getters, setters, etc.) |

---

## 3. ✅ Pré-requisitos para Rodar Localmente

Antes de começar, certifique-se de que sua máquina possui todos os itens abaixo instalados e funcionando:

| Ferramenta | Versão Mínima | Como verificar |
|---|---|---|
| **Java (JDK)** | 21 | `java -version` |
| **Apache Maven** | 3.9.x | `mvn -version` |
| **Docker** | 24.x | `docker --version` |
| **Docker Compose** | 2.x | `docker compose version` |
| **Git** | 2.x | `git --version` |
| **AWS CLI** | 2.x | `aws --version` (somente para deploy) |

> 💡 **Dica para iniciantes:** Se você ainda não tem o Java 21 instalado, recomendamos usar o [SDKMAN](https://sdkman.io/) para instalar e gerenciar versões do Java facilmente:
> ```bash
> curl -s "https://get.sdkman.io" | bash
> sdk install java 21-tem
> ```

---

## 4. 🚀 Tutorial Completo: Como Rodar o Projeto Localmente

O projeto é composto por **três repositórios**:
- `Calculadora-MicroServices-CI-CD-DB` — Microsserviço de persistência (calc-app-db)
- `Calculadora-Micro-Services-CI-CD` — Microsserviço principal (este repositório)
- `Calculadora-MicroServices-CI-CD-Infra` — Infraestrutura (Docker Compose, monitoring, etc.)

Siga os passos abaixo **na ordem exata**.

---

### Passo 1 — Clone os três repositórios

Abra o terminal e execute:

```bash
# Clone o repositório de infraestrutura
git clone https://github.com/ThiagoST32/Calculadora-MicroServices-CI-CD-Infra.git

# Clone o microsserviço de persistência (banco de dados)
git clone https://github.com/ThiagoST32/Calculadora-MicroServices-CI-CD-DB.git

# Clone o microsserviço principal
git clone https://github.com/ThiagoST32/Calculadora-Micro-Services-CI-CD.git
```

Após clonar, você terá três pastas no seu diretório atual.

---

### Passo 2 — Configure o arquivo `.env` da aplicação

Navegue até a pasta de infraestrutura e crie o arquivo `.env` baseado no exemplo:

```bash
cd Calculadora-MicroServices-CI-CD-Infra
cp compose/Servers/Application/.env.example compose/Servers/Application/.env
```

Agora abra o arquivo `.env` com seu editor favorito e preencha os valores:

```bash
# Endereço que a aplicação Java usará para se comunicar com o Vault
VAULT_APPLICATION_ADDR=http://vault-calc:8200

# Endereço interno para o script de inicialização do Vault
VAULT_INTERNAL_ADDR=http://127.0.0.1:8200

# Endereço de escuta do Vault (deixe como está para uso local)
VAULT_LISTENING_ADDR=0.0.0.0:8200

# Token de acesso ao Vault — use qualquer string para ambiente local
VAULT_TOKEN=meu-token-secreto-local

# Senha root do MySQL — defina uma senha forte
MYSQL_PASSWORD_ROOT=MinhaSenh@Root123

# Host root do MySQL — use % para aceitar conexões de qualquer origem
MYSQL_ROOT_HOST=%

# URL do repositório ECR — para rodar localmente, deixe vazio (usaremos build local)
ECR_REGISTRY_REPO=

# Nomes das imagens Docker
IMAGE_NAME_DB=calc-db
IMAGE_NAME_APP=calc-db-application
```

> ⚠️ **Atenção:** O arquivo `.env` está no `.gitignore` propositalmente. **Nunca commite esse arquivo** com senhas reais!

---

### Passo 3 — Compile os projetos Java localmente

Antes de subir os containers, precisamos gerar os arquivos `.jar` das aplicações.

**Compilar o microsserviço de persistência:**

```bash
cd ../Calculadora-MicroServices-CI-CD-DB
./mvnw clean package -DskipTests
```

> 💡 O `-DskipTests` pula os testes durante o build para agilizar. Veremos como rodar os testes separadamente na seção 11.

**Compilar o microsserviço principal:**

```bash
cd ../Calculadora-Micro-Services-CI-CD
./mvnw clean package -DskipTests
```

---

### Passo 4 — Construa as imagens Docker localmente

Como estamos rodando sem o AWS ECR, precisamos construir as imagens na própria máquina.

**Imagem do banco de dados MySQL customizado:**

```bash
cd ../Calculadora-MicroServices-CI-CD-DB
docker build -f Servers/MySQL/Dockerfile -t calc-db:1.0 .
```

**Imagem do microsserviço de persistência:**

```bash
docker build -f Servers/JavaApp/Dockerfile -t calc-db-application:1.0 .
```

**Imagem do microsserviço principal** (se o repositório tiver Dockerfile):

```bash
cd ../Calculadora-Micro-Services-CI-CD
docker build -f Servers/Dockerfile -t calc-app:1.0 .
```

---

### Passo 5 — Suba toda a infraestrutura com Docker Compose

Volte para o repositório de infraestrutura:

```bash
cd ../Calculadora-MicroServices-CI-CD-Infra
```

**Suba a aplicação de persistência (MySQL + Vault + calc-application-db):**

```bash
docker compose -f compose/Servers/Application/docker-compose.yml up -d
```

> 🕐 Aguarde cerca de 30 segundos. O Vault precisa inicializar e configurar os secrets antes da aplicação Java subir.

**Verifique se todos os containers estão de pé:**

```bash
docker ps
```

Você deve ver os containers: `mysql-calc`, `vault-calc`, `calc-application-db` com status `healthy` ou `Up`.

---

### Passo 6 — Suba o microsserviço principal

```bash
docker compose -f compose/Servers/ApplicationMain/docker-compose.yml up -d
```

---

### Passo 7 — Suba o stack de monitoramento (opcional, mas recomendado)

```bash
# Primeiro, configure o .env do monitoramento
cp compose/Servers/Monitoring/.env.example compose/Servers/Monitoring/.env
```

Edite o arquivo e defina usuário e senha do Grafana:

```bash
GF_INSTANCE_NAME=calculadora-local
GF_ADMIN=admin
GF_ADMIN_PASSWORD=admin123
MYSQL_USERNAME=calcUserDB
MYSQL_USER_PASSWORD=123456
```

Suba o monitoramento:

```bash
docker compose -f compose/Servers/Monitoring/docker-compose.yml up -d
```

---

### Passo 8 — Acesse as aplicações

| Serviço | URL | Descrição |
|---|---|---|
| **calc-app (Principal)** | http://localhost:8083 | API principal que recebe cálculos |
| **calc-application-db (Persistência)** | http://localhost:8081 | API de persistência |
| **Vault UI** | http://localhost:8200/ui | Interface do HashiCorp Vault |
| **Prometheus** | http://localhost:9090 | Interface do Prometheus |
| **Grafana** | http://localhost:3000 | Dashboards de monitoramento |
| **cAdvisor** | http://localhost:8082 | Métricas dos containers |
| **MySQL Exporter** | http://localhost:9104/metrics | Métricas do MySQL |
| **Actuator (Saúde)** | http://localhost:8081/actuator/health | Health check da aplicação de persistência |

> 💡 **Grafana:** Use `admin` / `admin123` (ou o que você configurou no `.env`) para fazer login. Os dashboards já estão pré-configurados automaticamente!

---

### Passo 9 — Pare a aplicação

Para parar todos os containers sem remover os volumes (dados são preservados):

```bash
docker compose -f compose/Servers/Application/docker-compose.yml stop
docker compose -f compose/Servers/ApplicationMain/docker-compose.yml stop
docker compose -f compose/Servers/Monitoring/docker-compose.yml stop
```

Para parar **e remover** os containers e volumes (dados serão perdidos):

```bash
docker compose -f compose/Servers/Application/docker-compose.yml down -v
docker compose -f compose/Servers/ApplicationMain/docker-compose.yml down -v
docker compose -f compose/Servers/Monitoring/docker-compose.yml down -v
```

---

### 🔧 Troubleshooting — Erros Comuns e Soluções

**❌ Erro: `calc-application-db` fica reiniciando (restart loop)**

*Causa:* A aplicação Java subiu antes do Vault estar pronto ou o MySQL ainda não estava saudável.

*Solução:*
```bash
# Verifique os logs da aplicação
docker logs calc-application-db

# Verifique se o Vault está healthy
docker inspect vault-calc | grep Status

# Force restart após o Vault estar healthy
docker restart calc-application-db
```

---

**❌ Erro: `Connection refused` ao acessar http://localhost:8081**

*Causa:* O container ainda está iniciando.

*Solução:*
```bash
# Acompanhe os logs em tempo real
docker logs -f calc-application-db
# Aguarde aparecer: "Started CalculadoraMicroServicesDbApplication"
```

---

**❌ Erro: `Cannot connect to Database!` nos logs da aplicação**

*Causa:* A senha do MySQL no Vault não foi configurada corretamente, ou o MySQL ainda não está pronto.

*Solução:*
```bash
# Verifique se o script do Vault executou com sucesso
docker logs vault-calc | grep "Vault secrets configured"

# Se não apareceu, reinicie o vault
docker restart vault-calc
# Aguarde 10 segundos e reinicie a aplicação
docker restart calc-application-db
```

---

**❌ Erro: Imagem não encontrada (image not found)**

*Causa:* Você esqueceu de construir as imagens Docker no Passo 4.

*Solução:*
```bash
# Reconstrua as imagens
docker build -f Servers/MySQL/Dockerfile -t calc-db:1.0 .
docker build -f Servers/JavaApp/Dockerfile -t calc-db-application:1.0 .
```

---

**❌ Erro: `Port is already allocated` (porta já em uso)**

*Causa:* Algum serviço na sua máquina já está usando a porta 3306, 8081 ou 8200.

*Solução:*
```bash
# Descubra qual processo está usando a porta (ex: 3306)
sudo lsof -i :3306
# Mate o processo ou altere a porta no docker-compose.yml
```

---

## 5. 📐 Diagrama de Fluxo e Arquitetura

### 5a. Diagrama de Arquitetura Geral (High-Level)

[![](https://mermaid.ink/img/pako:eNqVVN1u0zAUfhXLVyC1a5q0aZsLpDbtNsa6Zf0ZPy1CXuK11pI4spNpY5kEElfcAGIS3E2TkCb2BNz3TfYC7BGw462kFCbhKzvn-853fL4Tn0CXehhacMJQNAWD1jgEYvFkT30YwyFPZheMUFACtk9wGOMxVCC5hqOb80_f7iJgfTBwBNChPA5Q-FLhcOiNw6W8XeIyyjlmh2R2SYHDSOiSCPng-s0ZsOpa3cjrNB1n5CLfLaIoGo_DfiTgE9CiNP4fEQ8DBzNOeDy7EmporlXOa7Vbr_JyPnFRTGhY9Pb-qizXbnO4ORCtOPsI1hGfEpuyCOyixI8FxarrmpYDd5_3dzZHD27Ov7z7-eMD6B6Lo4QZhmY-vP86NCQxZSgQzab5kp3edlfIf74QfaQBjqc44TJlQ2vkldd6zVUJew_WGNpHIcpktT-q6zxzRllRoHMUURZjlqUqa5UczG62d0du0zsknGZx0UX93uLtxyW7nS96o7P15PFWX7btEmzg8ICEPKfQsXuj669vZYuaT_vyuJx-CIrFR6mz3R-AEopI6bBckralcl4UQmwyzComk_B2ToEiMDwRk4BZeuu4Iqh9xtmcXQGOXYZjniqLlyAb7ZadKkcX9dY6iyX1cER_CymsdC0D913RI6wmsYTcOEHC5VI0t3KxwiWatKYkoIy4PFUG_ltATzPvFEAORAbYSTA7TjOCimRZspBNfRwjEMy-SwHE59dVwFsXM6hH3QPMQJTwaSoNUwixWYz6_t2FYEE8PMSDVswSXIABZgGSR3giqWMobh-I98YSWw-xAzk9p4ITofAFpcEdjdFkMoXWPvK5OCWRh2LcJkgMXjD_ysTUYGbTJIyhVTUbWRJoncAjaBnmSlUvVysVrWzUNLMsgsdQ_LUrNd2sNUzDKBt6tVo7LcDXmaq2Uq8Y5bphNOp6xayZVb0AsSf_zK56TLM39fQXe9uw8Q?type=png)](https://mermaid.live/edit#pako:eNqVVN1u0zAUfhXLVyC1a5q0aZsLpDbtNsa6Zf0ZPy1CXuK11pI4spNpY5kEElfcAGIS3E2TkCb2BNz3TfYC7BGw462kFCbhKzvn-853fL4Tn0CXehhacMJQNAWD1jgEYvFkT30YwyFPZheMUFACtk9wGOMxVCC5hqOb80_f7iJgfTBwBNChPA5Q-FLhcOiNw6W8XeIyyjlmh2R2SYHDSOiSCPng-s0ZsOpa3cjrNB1n5CLfLaIoGo_DfiTgE9CiNP4fEQ8DBzNOeDy7EmporlXOa7Vbr_JyPnFRTGhY9Pb-qizXbnO4ORCtOPsI1hGfEpuyCOyixI8FxarrmpYDd5_3dzZHD27Ov7z7-eMD6B6Lo4QZhmY-vP86NCQxZSgQzab5kp3edlfIf74QfaQBjqc44TJlQ2vkldd6zVUJew_WGNpHIcpktT-q6zxzRllRoHMUURZjlqUqa5UczG62d0du0zsknGZx0UX93uLtxyW7nS96o7P15PFWX7btEmzg8ICEPKfQsXuj669vZYuaT_vyuJx-CIrFR6mz3R-AEopI6bBckralcl4UQmwyzComk_B2ToEiMDwRk4BZeuu4Iqh9xtmcXQGOXYZjniqLlyAb7ZadKkcX9dY6iyX1cER_CymsdC0D913RI6wmsYTcOEHC5VI0t3KxwiWatKYkoIy4PFUG_ltATzPvFEAORAbYSTA7TjOCimRZspBNfRwjEMy-SwHE59dVwFsXM6hH3QPMQJTwaSoNUwixWYz6_t2FYEE8PMSDVswSXIABZgGSR3giqWMobh-I98YSWw-xAzk9p4ITofAFpcEdjdFkMoXWPvK5OCWRh2LcJkgMXjD_ysTUYGbTJIyhVTUbWRJoncAjaBnmSlUvVysVrWzUNLMsgsdQ_LUrNd2sNUzDKBt6tVo7LcDXmaq2Uq8Y5bphNOp6xayZVb0AsSf_zK56TLM39fQXe9uw8Q)

---

### 5b. Fluxo Principal da Aplicação (User Journey + Backend)

[![](https://mermaid.ink/img/pako:eNqlVFFu00AQvcpov1pwHDuOndQSlUqaAiKpS5JWAozQYg_Jqs6uWdulbZTDID44AOIEuRhrrx2FBgQS_rF3_N7MmzerWZFIxEh8kuGnAnmEp4zOJV2GHNRDo1xIGCQMeY46lFKZs4illOdwcnEBNIOIJlGLpin4favv7MNOn75_gExYRHMmeCv-UJHsfdLVyeVoVnKe02zBBkKmcEWLJFf4jmXt48evp69GJX58V374jmN5Ide4c5EjiBuUtRRDZ_fhnALjKgNN2D3dfNt8FZqgYa3j4xo3FUoxyylEEmPlEqMsg4NcXCM_1IwK2FIMTfVhgso6TuFyMjKgyIrNF8kEIGTIFxRioXXuK6zNNnRDPpwlxa2AGKFYUhApyl2dNViVrWpeBNMZtGnK2jd2u_Q6DPkqJDc0EdIOiR8S2wqJUUc6VcStAjlLxSkGZfqIiurHNBifhGStC2k3qiIDlbdIKEjM1Dio6uTAtuAxuPAEbNe0rMNdSmPHnrQJpqItcc6yHOVWZ4EBxwdKC5x9FjtadWENKuv9UwPbida2vjifDicz9ZoF2tXyOqqZmqZZ66-AuxMNXv6SqvFD3cbtrzpej8WHVaNW2aQ0wVbx-u-DHyg9JVPCQnm0-S5ZtDf1Rtqz4b65v-97OhwNBzN4BGeTYLzT-R96HqnStLx9zc37gdlDF7bdvv2vGSrn1--IQeaSxcTPZYEGWaJc0vJIVmVVNeYFLlE7GVN5HZKQrxVHbYA3QiwbmhTFfEH8jzTJ1KlIY5o3e20blchjlANR8Jz4rudWSYi_IrfEdzzT7dhut2vZTs_y7COD3BG1d8xex-sdeY5jOx3X7a0Ncl9Vtcx-17H7jnPU73S9nufaBsGYqQUw1su12rHrn-wtruA?type=png)](https://mermaid.live/edit#pako:eNqlVFFu00AQvcpov1pwHDuOndQSlUqaAiKpS5JWAozQYg_Jqs6uWdulbZTDID44AOIEuRhrrx2FBgQS_rF3_N7MmzerWZFIxEh8kuGnAnmEp4zOJV2GHNRDo1xIGCQMeY46lFKZs4illOdwcnEBNIOIJlGLpin4favv7MNOn75_gExYRHMmeCv-UJHsfdLVyeVoVnKe02zBBkKmcEWLJFf4jmXt48evp69GJX58V374jmN5Ide4c5EjiBuUtRRDZ_fhnALjKgNN2D3dfNt8FZqgYa3j4xo3FUoxyylEEmPlEqMsg4NcXCM_1IwK2FIMTfVhgso6TuFyMjKgyIrNF8kEIGTIFxRioXXuK6zNNnRDPpwlxa2AGKFYUhApyl2dNViVrWpeBNMZtGnK2jd2u_Q6DPkqJDc0EdIOiR8S2wqJUUc6VcStAjlLxSkGZfqIiurHNBifhGStC2k3qiIDlbdIKEjM1Dio6uTAtuAxuPAEbNe0rMNdSmPHnrQJpqItcc6yHOVWZ4EBxwdKC5x9FjtadWENKuv9UwPbida2vjifDicz9ZoF2tXyOqqZmqZZ66-AuxMNXv6SqvFD3cbtrzpej8WHVaNW2aQ0wVbx-u-DHyg9JVPCQnm0-S5ZtDf1Rtqz4b65v-97OhwNBzN4BGeTYLzT-R96HqnStLx9zc37gdlDF7bdvv2vGSrn1--IQeaSxcTPZYEGWaJc0vJIVmVVNeYFLlE7GVN5HZKQrxVHbYA3QiwbmhTFfEH8jzTJ1KlIY5o3e20blchjlANR8Jz4rudWSYi_IrfEdzzT7dhut2vZTs_y7COD3BG1d8xex-sdeY5jOx3X7a0Ncl9Vtcx-17H7jnPU73S9nufaBsGYqQUw1su12rHrn-wtruA)

---

### 5c. Fluxo de Dados / Camadas da Aplicação

[![](https://mermaid.ink/img/pako:eNqFVN1umzAUfhXk3mwSiRIcEsrFpJTmolVQU2grbbALF04Sr2AjY6pmSd5hF7vfU-wJ9kJ7hBnIj5E2jRvwd77znfMdY29RwlNALloJUqyNeRAzQz1l9dwCMUpIlvRIUWQ0IZJy1kufY9Sy6ielApIaNx6uzqgXeZxJwbMMRByzuwJEk1wuQJS0lOU5_PmcFUYhiFeagJ5yyDhENLYf-aqtrn6LlBoriAIoeEklFxudeUY18iyaMUllh6iLTe8W0bQslGHF8EEKmrRLva0waiP_9tKJq1Rtbkav92HnrUlOdkaoTabBH0uF-n9FA81xgx5Kwc6YdfpvgjdMgkigkN0qx3AAK5Uq6mKhbqwJzt6KXz-V7CK4O7QCLD160P6bG7YUBJRMJStB9F_mafo4f4h-__j-zXgiVdYZ3sfwfh698zfq9V7D62LRQvAc5Bqq45ZohVvTt9dXnvFKiXG7mO5asTbctu4JSIEllNDSoOwLSJKS0igg47u2qZMNucmgGceSZpl7sVyaygh_AfcCY6xz1FD-R2mUT6yxTkOmOnc0Ra6aEpgoB5GTeom2tUCMlNkcYuSqz5SIl3qIe5VTEPaJ8_yYJni1WiN3SbJSraoiJRKuKVEbkZ9QoWYFwuMVk8i1bacRQe4WvSEXj_u2NbRHo8EQTwbj4aWJNsh1rP7EGk8uxxgPsWXbk72JvjZVB31nhIcOxg4eTRxrbNkmgrQ-S357lzRXyv4Pr3Rdag?type=png)](https://mermaid.live/edit#pako:eNqFVN1umzAUfhXk3mwSiRIcEsrFpJTmolVQU2grbbALF04Sr2AjY6pmSd5hF7vfU-wJ9kJ7hBnIj5E2jRvwd77znfMdY29RwlNALloJUqyNeRAzQz1l9dwCMUpIlvRIUWQ0IZJy1kufY9Sy6ielApIaNx6uzqgXeZxJwbMMRByzuwJEk1wuQJS0lOU5_PmcFUYhiFeagJ5yyDhENLYf-aqtrn6LlBoriAIoeEklFxudeUY18iyaMUllh6iLTe8W0bQslGHF8EEKmrRLva0waiP_9tKJq1Rtbkav92HnrUlOdkaoTabBH0uF-n9FA81xgx5Kwc6YdfpvgjdMgkigkN0qx3AAK5Uq6mKhbqwJzt6KXz-V7CK4O7QCLD160P6bG7YUBJRMJStB9F_mafo4f4h-__j-zXgiVdYZ3sfwfh698zfq9V7D62LRQvAc5Bqq45ZohVvTt9dXnvFKiXG7mO5asTbctu4JSIEllNDSoOwLSJKS0igg47u2qZMNucmgGceSZpl7sVyaygh_AfcCY6xz1FD-R2mUT6yxTkOmOnc0Ra6aEpgoB5GTeom2tUCMlNkcYuSqz5SIl3qIe5VTEPaJ8_yYJni1WiN3SbJSraoiJRKuKVEbkZ9QoWYFwuMVk8i1bacRQe4WvSEXj_u2NbRHo8EQTwbj4aWJNsh1rP7EGk8uxxgPsWXbk72JvjZVB31nhIcOxg4eTRxrbNkmgrQ-S357lzRXyv4Pr3Rdag)

---

## 6. 📋 Documentação da API

### 6a. Microsserviço de Persistência — `calc-application-db` (porta 8081)

Base URL: `http://localhost:8081/api/v1/calcRepo`

#### Endpoints

| Método | Endpoint | Descrição | Parâmetros | Body | Resposta | Status Codes |
|---|---|---|---|---|---|---|
| `POST` | `/register` | Salva uma operação no banco | — | JSON (ver abaixo) | Sem corpo | `200 OK`, `500` |
| `GET` | `/` | Lista todas as operações | — | — | Array JSON | `200 OK` |
| `GET` | `/getPreviousOperation` | Retorna a operação mais recente | — | — | JSON | `200 OK`, `500` |

---

#### `POST /api/v1/calcRepo/register`

Salva uma operação matemática realizada no banco de dados.

**Body da Requisição:**
```json
{
  "valueOne": "10",
  "valueTwo": "5",
  "result": "15.00",
  "tipoDeOperacao": "SOMA"
}
```

> `tipoDeOperacao` aceita: `SOMA`, `SUBTRACAO`, `MULTIPLICACAO`, `DIVISAO`

**Resposta:** `200 OK` (sem corpo)

**Exemplo com cURL:**
```bash
curl -X POST http://localhost:8081/api/v1/calcRepo/register \
  -H "Content-Type: application/json" \
  -d '{"valueOne":"10","valueTwo":"5","result":"15.00","tipoDeOperacao":"SOMA"}'
```

---

#### `GET /api/v1/calcRepo`

Retorna todas as operações já registradas no banco.

**Resposta `200 OK`:**
```json
[
  {
    "valueOne": "10",
    "valueTwo": "5",
    "result": "15.00",
    "tipoDeOperacao": "SOMA"
  },
  {
    "valueOne": "20",
    "valueTwo": "4",
    "result": "5.00",
    "tipoDeOperacao": "DIVISAO"
  }
]
```

**Exemplo com cURL:**
```bash
curl http://localhost:8081/api/v1/calcRepo
```

---

#### `GET /api/v1/calcRepo/getPreviousOperation`

Retorna a operação mais recente registrada no banco.

**Resposta `200 OK`:**
```json
{
  "valueOne": "20",
  "valueTwo": "4",
  "result": "5.00",
  "tipoDeOperacao": "DIVISAO"
}
```

**Exemplo com cURL:**
```bash
curl http://localhost:8081/api/v1/calcRepo/getPreviousOperation
```

---

### 6b. Endpoints do Actuator (Monitoramento)

Base URL: `http://localhost:8081/actuator`

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/actuator/health` | Status de saúde da aplicação |
| `GET` | `/actuator/metrics` | Lista todas as métricas disponíveis |
| `GET` | `/actuator/prometheus` | Métricas no formato Prometheus (scraping) |
| `GET` | `/actuator/info` | Informações gerais da aplicação |

**Exemplo de resposta do `/actuator/health`:**
```json
{
  "status": "UP",
  "components": {
    "db": { "status": "UP" },
    "vault": { "status": "UP" }
  }
}
```

---

### 6c. Microsserviço Principal — `calc-app` (porta 8083)

Base URL: `http://localhost:8083/api/v1/calc`

| Método | Endpoint | Descrição | Body | Resposta |
|---|---|---|---|---|
| `POST` | `/` | Realiza um cálculo e persiste | JSON com valores e operação | Resultado calculado |
| `GET` | `/` | Lista todas as operações do histórico | — | Array de operações |
| `GET` | `/prev` | Retorna a última operação realizada | — | Operação mais recente |

**Exemplo de body para `POST /api/v1/calc`:**
```json
{
  "valor1": "100",
  "valor2": "20",
  "tipoDeOperacao": "DIVISAO"
}
```

**Resposta esperada:**
```json
{
  "resultado": "5.00"
}
```

---

## 7. ☁️ Deploy em Produção

O deploy em produção utiliza **AWS ECR** para armazenar as imagens Docker e o **Jenkins** para automatizar todo o processo.

### Passo 1 — Configure as credenciais AWS no Jenkins

No painel do Jenkins, vá em: `Gerenciar Jenkins → Credenciais → Sistema → Credenciais Globais` e adicione:

| ID da Credencial | Tipo | Descrição |
|---|---|---|
| `AWS-REGION-DEFAULT` | Secret text | Ex: `us-east-1` |
| `ECR-REGISTRY-REPO-CALCULADORA` | Secret text | URL do repositório ECR |
| `IMAGE-NAME-APP` | Secret text | Ex: `calc-db-application` |
| `IMAGE-NAME-DB` | Secret text | Ex: `calc-db` |
| `JenkinsECR` | AWS Credentials | Access Key ID + Secret Key com permissão de push no ECR |
| `VAULT-TOKEN` | Secret text | Token root do Vault em produção |
| `MYSQL-PASSWORD-ROOT` | Secret text | Senha root do MySQL em produção |
| `VAULT-LISTENING-ADDR` | Secret text | Ex: `0.0.0.0:8200` |
| `VAULT-INTERNAL-ADDR` | Secret text | Ex: `http://127.0.0.1:8200` |
| `VAULT-APPLICATION-ADDR` | Secret text | Ex: `http://vault-calc:8200` |
| `MYSQL-ROOT-HOST` | Secret text | Ex: `%` |

---

### Passo 2 — Crie os repositórios ECR na AWS

```bash
# Configure suas credenciais AWS localmente
aws configure

# Crie o repositório para a aplicação Java
aws ecr create-repository --repository-name calc-db-application --region us-east-1

# Crie o repositório para o MySQL customizado
aws ecr create-repository --repository-name calc-db --region us-east-1
```

---

### Passo 3 — Configure o IAM para o Jenkins

O usuário IAM usado pelo Jenkins precisa das seguintes permissões mínimas:

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "ecr:GetAuthorizationToken",
        "ecr:BatchCheckLayerAvailability",
        "ecr:InitiateLayerUpload",
        "ecr:UploadLayerPart",
        "ecr:CompleteLayerUpload",
        "ecr:PutImage"
      ],
      "Resource": "*"
    }
  ]
}
```

---

### Passo 4 — Execute o pipeline principal no Jenkins

No Jenkins, execute o job `Jenkinsfile.deploy.main` (ou `pipelines/Jenkinsfile.deploy.main`). Esse pipeline orquestra todos os outros em sequência:

1. Build das imagens (App + MySQL) em paralelo
2. Deploy da aplicação de banco de dados
3. Deploy da aplicação principal
4. Deploy do monitoramento
5. Testes automatizados no ambiente

---

### Configurações Recomendadas para Produção

- **SSL/TLS:** Configure um reverse proxy (Nginx ou AWS ALB) na frente das aplicações para terminar SSL
- **Vault em produção:** Use o modo `server` do Vault (não `dev`), com storage backend (Consul ou AWS DynamoDB) e unseal automático via AWS KMS
- **MySQL:** Use AWS RDS ou configure replicação master-slave para alta disponibilidade
- **Scaling:** Adicione múltiplas réplicas do `calc-application-db` e use um load balancer

---

### 🔧 Troubleshooting de Deploy

**❌ Erro: `no basic auth credentials` ao fazer push para ECR**

*Solução:*
```bash
# Faça login manual no ECR
aws ecr get-login-password --region us-east-1 | \
  docker login --username AWS --password-stdin SEU_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com
```

**❌ Erro: `denied: User is not authorized to perform: ecr:InitiateLayerUpload`**

*Solução:* Verifique se a política IAM do usuário Jenkins inclui todas as permissões listadas no Passo 3.

**❌ Container em produção não consegue acessar o Vault**

*Solução:* Verifique se a variável `SPRING_CLOUD_VAULT_URI` aponta para o endereço correto do Vault acessível pelo container (use o nome do serviço Docker, não `localhost`).

---

## 8. 🔄 Pipeline CI/CD

O projeto utiliza **Jenkins** com múltiplos Jenkinsfiles organizados por função. O pipeline principal é o `Jenkinsfile.deploy.main`, que chama os demais como sub-pipelines.

### Visão Geral do Fluxo

```
Commit no GitHub
       │
       ▼
[Trigger: githubPush()]
       │
       ▼
┌─────────────────────────────────┐
│  Fase 1 — Build em Paralelo     │
│  ┌─────────────┐ ┌───────────┐  │
│  │ Build App   │ │ Build DB  │  │
│  │ (Java+ECR)  │ │ (MySQL)   │  │
│  └─────────────┘ └───────────┘  │
└─────────────────────────────────┘
       │
       ▼
┌─────────────────────────────────┐
│  Fase 2 — Deploy App DB         │
│  (MySQL + Vault + calc-app-db)  │
└─────────────────────────────────┘
       │
       ▼
┌─────────────────────────────────┐
│  Fase 3 — Deploy App Principal  │
└─────────────────────────────────┘
       │
       ▼
┌─────────────────────────────────┐
│  Fase 4 — Deploy Monitoramento  │
│  (Prometheus + Grafana)         │
└─────────────────────────────────┘
       │
       ▼
┌─────────────────────────────────┐
│  Fase 5 — Testes no Ambiente    │
│  (Smoke tests + Integração)     │
└─────────────────────────────────┘
```

---

### Arquivo de Pipeline Principal — `Jenkinsfile.deploy.main`

```groovy
pipeline {
    agent any  // Roda em qualquer agente Jenkins disponível

    stages {
        // FASE 1: Constrói as imagens em paralelo para economizar tempo
        stage('Fase 1 — Build das imagens') {
            parallel {
                stage('Pipe 01 — App-DB') {
                    steps {
                        // Chama o pipeline de build da aplicação Java de persistência
                        build job: 'DB/Calculadora Micro Services ECR Java Application', wait: true
                    }
                }
                stage('Pipe 02 — Banco') {
                    steps {
                        // Chama o pipeline de build da imagem MySQL customizada
                        build job: 'DB/Calculadora Micro services MySQL ECR', wait: true
                    }
                }
                stage('Pipe 03 - App-Main') {
                    steps {
                        // Chama o pipeline de build da aplicação principal
                        build job: 'APP/Calculador Micro Services ECR Application Main', wait: true
                    }
                }
            }
        }

        // FASE 2: Faz o deploy do stack de banco de dados
        stage('Fase 2 — Subir Aplicação do Banco') {
            steps {
                script {
                    def status = build(
                        job: 'DB/Calculadora Micro Services Up Aplicação DB',
                        wait: true,
                        propagate: false  // Não propaga falha automaticamente (tratamos manualmente)
                    )
                    // Se falhou, aborta o pipeline inteiro
                    env.APP_UP = status.result == 'SUCCESS' ? 'true' : 'false'
                    if(env.APP_UP == 'false') {
                        error("Aplicação falhou ao subir - abortando build!")
                    }
                }
            }
        }

        // FASE 3: Deploy da aplicação principal
        stage('Fase 3 - Subir aplicação principal') {
            steps {
                script {
                    build job: 'APP/Calculador Micro Services Up Aplicação Principal', wait: true
                }
            }
        }

        // FASE 4: Deploy do monitoramento (só executa se a fase 2 foi bem-sucedida)
        stage('Fase 4 - Subir Monitoramento') {
            when {
                expression { env.APP_UP == 'true' }  // Condição de execução
            }
            steps {
                script {
                    build job: 'Monitoring/Calculadora Micro Services Up Monitoramento', wait: true
                }
            }
        }

        // FASE 5: Executa testes automatizados no ambiente real
        stage('Fase 5 - Testes no ambiente') {
            steps {
                script {
                    build job: 'Environment tests/Calculadora Micro Services Testes no Ambiente', wait: true
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline executada!'
        }
        failure {
            echo 'Pipeline falhou — executando tear down de segurança'
        }
    }
}
```

---

### Como Disparar o Pipeline

**Automaticamente:** O pipeline é disparado por um **push no GitHub** (`githubPush()` trigger). Configure um webhook no repositório GitHub apontando para: `http://SEU-JENKINS/github-webhook/`

**Manualmente:** No painel do Jenkins, clique no job `Jenkinsfile.deploy.main` e depois em **"Construir Agora"**.

---

### Pipelines Individuais Disponíveis

| Arquivo | Função |
|---|---|
| `pipelines/Jenkinsfile.deploy.main` | Orquestrador principal (dispara todos os outros) |
| `pipelines/DB/Jenkinsfile.app.build` | Build e push da imagem Java (persistência) para ECR |
| `pipelines/DB/Jenkinsfile.banco.build` | Build e push da imagem MySQL para ECR |
| `pipelines/DB/Jenkinsfile.deploy.application` | Deploy do stack de banco de dados |
| `pipelines/APP/Jenkinsfile.app.build` | Build e push da aplicação principal para ECR |
| `pipelines/APP/Jenkinsfile.app.deploy.application` | Deploy da aplicação principal |
| `pipelines/Jenkinsfile.deploy.monitoring` | Deploy do stack de monitoramento |
| `pipelines/EnviromentTests/Jenkinsfile.deploy.test.application` | Suite completa de testes automatizados |

---

## 9. 💡 Principais Aprendizados do Projeto

### 1. 🔐 Gerenciamento de Secrets com HashiCorp Vault

**O que aprendemos:** Nunca guardar senhas ou credenciais em variáveis de ambiente ou arquivos de configuração versionados. O Vault centraliza e protege todas as credenciais, fornecendo-as dinamicamente para a aplicação na inicialização.

**Porque foi importante:** Em projetos reais, vazamento de credenciais é uma das vulnerabilidades mais comuns e perigosas. O Vault elimina esse risco.

**Dica prática:** Use o modo `dev` do Vault apenas para desenvolvimento local. Em produção, configure `auto-unseal` com AWS KMS para evitar intervenção manual após reinicializações.

---

### 2. 🧩 Separação de Responsabilidades com Microsserviços

**O que aprendemos:** Dividir uma aplicação em serviços menores com responsabilidades bem definidas (um calcula, outro persiste) torna o sistema mais fácil de manter, escalar e testar independentemente.

**Porque foi importante:** Permite escalar apenas o serviço que está sobrecarregado, sem precisar escalar toda a aplicação.

**Dica prática:** Defina contratos de API claros (DTOs) entre serviços antes de começar a codificar. Isso evita retrabalho e acoplamento desnecessário.

---

### 3. 📊 Observabilidade com AOP e Micrometer

**O que aprendemos:** Usar **Spring AOP** para interceptar métodos de serviço e registrar métricas (contadores de sucesso/falha, timers) sem poluir o código de negócio. O **Micrometer** expõe essas métricas no formato do Prometheus.

**Porque foi importante:** Em produção, sem métricas você voa "às cegas". Saber quantas operações falharam, quanto tempo cada método leva e quando o sistema está degradado é fundamental.

**Dica prática:** Crie métricas antes de ter problemas, não depois. Pense nos SLIs (Service Level Indicators) que o negócio precisa monitorar.

---

### 4. 🐳 Docker e Docker Compose para Infraestrutura Reproducível

**O que aprendemos:** Containerizar cada serviço (aplicação, banco, vault, monitoring) garante que o ambiente de desenvolvimento seja idêntico ao de produção, eliminando o famoso "na minha máquina funciona".

**Por que foi importante:** Onboarding de novos desenvolvedores se torna trivial: basta um `docker compose up` para ter todo o ambiente rodando.

**Dica prática:** Sempre defina `healthcheck` nos serviços e use `depends_on: condition: service_healthy` para garantir a ordem correta de inicialização.

---

### 5. 🚀 CI/CD com Jenkins e Pipelines como Código (Pipeline as Code)

**O que aprendemos:** Definir os pipelines em arquivos `Jenkinsfile` versionados no próprio repositório, organizados por responsabilidade (build, deploy, test), permite rastrear mudanças na infraestrutura como qualquer outro código.

**Porque foi importante:** Elimina configuração manual e ad-hoc no servidor CI. Qualquer pessoa pode entender e reproduzir o pipeline.

**Dica prática:** Use pipelines paralelos (`parallel {}`) para estágios independentes (ex: ‘build’ da aplicação e do banco) e economize tempo de execução.

---

### 6. ☁️ Registro de Imagens com AWS ECR

**O que aprendi:** O ECR é um repositório privado de imagens Docker integrado ao ecossistema AWS, com autenticação via IAM, garantindo que apenas usuários/serviços autorizados possam fazer push e pull de imagens.

**Por que foi importante:** Ter as imagens em um registry privado e versionado (`tag: 1.0`) garante deploys reproduzíveis e rastreáveis.

**Dica prática:** Implemente lifecycle policies no ECR para remover automaticamente imagens antigas e evitar custos desnecessários de armazenamento.

---

### 7. 📈 Dashboards Pré-configurados com Grafana Provisioning

**O que aprendi:** O Grafana permite provisionar datasources e dashboards automaticamente via arquivos YAML e JSON, eliminando a necessidade de configuração manual após cada deploy.

**Porque foi importante:** Em um ambiente de CI/CD, o ambiente de monitoramento precisa subir completamente configurado, sem intervenção humana.

**Dica prática:** Exporte os seus dashboards como JSON e coloque-os na pasta de provisioning. Assim, eles são recriados automaticamente a cada `docker compose up`.

---

### 8. 🧪 Testes com Mockito e ArgumentCaptor

**O que aprendemos:** Usar `ArgumentCaptor` do Mockito para capturar os argumentos passados para métodos mockados e verificar se os dados corretos foram persistidos, sem precisar de um banco de dados real nos testes unitários.

**Porque foi importante:** Testes rápidos e isolados permitem detectar regressões imediatamente, durante o desenvolvimento, sem depender de infraestrutura externa.

**Dica prática:** Use `@ExtendWith(MockitoExtension.class)` para configuração automática dos mocks e prefira `verify()` + `ArgumentCaptor` para testar o comportamento, não apenas o retorno.

---

### 9. 🔗 Comunicação entre Serviços com OpenFeign

**O que aprendemos:** O **Spring Cloud OpenFeign** permite declarar clients HTTP como interfaces Java simples, com anotações, eliminando a verbosidade de usar `RestTemplate` ou `WebClient` manualmente.

**Por que foi importante:** Código mais limpo, legível e com menos propensão a erros de configuração de HTTP.

**Dica prática:** Sempre configure timeout e retry no Feign Client para produção. Serviços dependentes podem ficar lentos ou indisponíveis temporariamente.

---

### 10. 🗄️ Suporte Multi-Banco com Detecção Dinâmica

**O que aprendemos:** Ao detectar o tipo de banco de dados em runtime (via `connection.getMetaData().getDatabaseProductName()`), é possível executar scripts SQL específicos para MySQL, PostgreSQL ou SQL Server automaticamente.

**Por que foi importante:** Torna o sistema flexível e portável para diferentes ambientes sem alterações no código.

**Dica prática:** Evite SQL proprietário sempre que possível. Use o dialeto JPA/Hibernate para operações padrão e reserve SQL nativo para casos específicos.

---

## 10. 📁 Estrutura de Pastas do Projeto

### Repositório: `Calculadora-MicroServices-CI-CD-DB` (Persistência)

```
Calculadora-MicroServices-CI-CD-DB/
│
├── 📄 pom.xml                          # Dependências e configuração do Maven
├── 📄 mvnw / mvnw.cmd                  # Maven Wrapper (não precisa instalar Maven globalmente)
│
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/com/trevisan/CalculadoraMicroServicesDB/
│   │   │   ├── 📄 CalculadoraMicroServicesDbApplication.java  # Ponto de entrada (main)
│   │   │   ├── 📁 Controllers/
│   │   │   │   └── 📄 OperationsPersistsController.java       # Endpoints REST
│   │   │   ├── 📁 Services/
│   │   │   │   ├── 📄 OperationPersistService.java            # Lógica de negócio
│   │   │   │   └── 📄 OperationPersistMetricService.java      # Configuração de métricas
│   │   │   ├── 📁 Domain/
│   │   │   │   ├── 📄 Operations.java                         # Entidade JPA
│   │   │   │   └── 📁 Enums/
│   │   │   │       └── 📄 TipoDeOperacao.java                 # Enum das operações
│   │   │   ├── 📁 Dtos/
│   │   │   │   ├── 📄 OperationsRequestDTO.java               # DTO de entrada
│   │   │   │   └── 📄 OperationsResponseDTO.java              # DTO de saída
│   │   │   ├── 📁 Mappers/
│   │   │   │   └── 📄 OperationMappers.java                   # Conversão Entity ↔ DTO
│   │   │   ├── 📁 Repositories/
│   │   │   │   └── 📄 OperationRepository.java                # Interface JPA Repository
│   │   │   └── 📁 Infra/
│   │   │       ├── 📄 ConfigDB.java                           # Verificação de conexão e tabelas
│   │   │       └── 📄 MetricAspect.java                       # AOP para métricas automáticas
│   │   │
│   │   └── 📁 resources/
│   │       ├── 📄 application.yaml                            # Configurações da aplicação
│   │       └── 📁 DBQuerys/
│   │           ├── 📁 MySQL/                                  # Scripts SQL para MySQL
│   │           ├── 📁 Postgres/                               # Scripts SQL para PostgreSQL
│   │           └── 📁 SqlServer/                              # Scripts SQL para SQL Server
│   │
│   └── 📁 test/                                               # Testes unitários
│       └── 📁 java/com/trevisan/CalculadoraMicroServicesDB/
│           ├── 📁 Controllers/    # Testes do controller
│           ├── 📁 Services/       # Testes do service
│           ├── 📁 Dto/            # Testes dos DTOs
│           ├── 📁 Entity/         # Testes da entidade
│           └── 📁 Infra/          # Testes da configuração de DB
│
├── 📁 Servers/
│   ├── 📁 JavaApp/
│   │   └── 📄 Dockerfile           # Imagem Docker da aplicação Java (multi-stage build)
│   └── 📁 MySQL/
│       ├── 📄 Dockerfile           # Imagem Docker customizada do MySQL
│       └── 📄 init.sql             # Script de criação de usuário do banco
│
└── 📁 pipelines/                   # Jenkinsfiles de CI/CD
    ├── 📄 Jenkinsfile.app.build
    ├── 📄 Jenkinsfile.banco.build
    └── 📄 Jenkinsfile.deploy.main
```

### Repositório: `Calculadora-MicroServices-CI-CD-Infra` (Infraestrutura)

```
Calculadora-MicroServices-CI-CD-Infra/
│
├── 📁 compose/
│   └── 📁 Servers/
│       ├── 📁 Application/                     # Stack principal (app + vault + mysql)
│       │   ├── 📄 docker-compose.yml
│       │   ├── 📄 .env.example
│       │   ├── 📁 MySQL/
│       │   │   └── 📄 init.sql
│       │   └── 📁 vault/scripts/
│       │       └── 📄 vault.sh                 # Script de inicialização do Vault
│       │
│       ├── 📁 ApplicationMain/                 # Stack da aplicação principal
│       │   ├── 📄 docker-compose.yml
│       │   └── 📄 .env.example
│       │
│       └── 📁 Monitoring/                      # Stack de monitoramento
│           ├── 📄 docker-compose.yml
│           ├── 📄 prometheus.yml               # Configuração do Prometheus
│           ├── 📄 .env.example
│           └── 📁 Grafana/provisioning/
│               ├── 📁 datasources/             # Datasource do Prometheus
│               └── 📁 dashboards/              # Dashboards pré-configurados (JSON)
│
└── 📁 pipelines/                               # Todos os Jenkinsfiles
    ├── 📁 APP/                                 # Pipelines da aplicação principal
    ├── 📁 DB/                                  # Pipelines de persistência
    ├── 📁 Monitoring/                          # Pipelines de monitoramento
    └── 📁 EnviromentTests/                     # Pipeline de testes no ambiente
```

---

## 11. 🧪 Como Rodar Testes

### Pré-requisito

Certifique-se de estar na pasta do projeto Java:

```bash
cd Calculadora-MicroServices-CI-CD-DB
```

---

### Rodar todos os testes unitários

```bash
./mvnw test
```

Esse comando executa todos os testes dentro de `src/test/java/`.

---

### Rodar testes de uma classe específica

```bash
# Rodar apenas os testes do Service
./mvnw test -Dtest=OperationPersistServiceTest

# Rodar apenas os testes do Controller
./mvnw test -Dtest=OperationsPersistsControllerTest

# Rodar apenas os testes da entidade
./mvnw test -Dtest=OperationEntityTest

# Rodar apenas os testes do DTO
./mvnw test -Dtest=OperationsRequestDTOTest

# Rodar apenas os testes de infraestrutura (ConfigDB)
./mvnw test -Dtest=ConfigDBTest
```

---

### Rodar testes com relatório detalhado

```bash
./mvnw test -Dsurefire.useFile=false
```

Esse comando exibe os resultados diretamente no terminal em vez de salvar em arquivo.

---

### Gerar relatório HTML dos testes

```bash
./mvnw surefire-report:report
```

O relatório será gerado em: `target/site/surefire-report.html`. Abra no navegador para visualizar.

---

### Testes E2E (Smoke Tests no Ambiente Real)

Os testes de ambiente completo são executados pelo Jenkins via o pipeline `EnviromentTests/Jenkinsfile.deploy.test.application`. Para rodar manualmente (com as aplicações em pé localmente):

```bash
# Verifique saúde da aplicação principal
curl -s http://localhost:8083/actuator/health | grep '"status":"UP"'

# Execute um cálculo de soma
curl -s -X POST http://localhost:8083/api/v1/calc \
  -H "Content-Type: application/json" \
  -d '{"valor1":"10","valor2":"5","tipoDeOperacao":"SOMA"}'
# Esperado: {"resultado":"15.00"}

# Verifique se foi persistido
curl -s http://localhost:8081/api/v1/calcRepo/getPreviousOperation
# Esperado: {"valueOne":"10","valueTwo":"5","result":"15.00","tipoDeOperacao":"SOMA"}
```

---

### Cobertura dos Testes

Os testes cobrem os seguintes cenários:

| Classe Testada | Cenários Cobertos |
|---|---|
| `OperationPersistService` | Salvar operação válida, lançar exceção com DTO nulo, listar operações, lista vazia, operação anterior |
| `OperationsPersistsController` | Chamada ao save, retorno 200 com body na listagem, retorno da operação anterior |
| `Operations (Entity)` | Getters retornam valores corretos, equals e hashCode por ID |
| `OperationsRequestDTO` | Getters do record, equals e hashCode |
| `ConfigDB` | Conexão bem-sucedida, conexão fechada, falha no datasource, validação de conexão, verificação de tabelas, tipos de banco |

---

## 12. 🔑 Variáveis de Ambiente Explicadas

### Arquivo: `compose/Servers/Application/.env`

| Variável | Descrição | Exemplo Local | Exemplo Produção |
|---|---|---|---|
| `VAULT_APPLICATION_ADDR` | Endereço do Vault que a **aplicação Java** usará para buscar secrets. Deve ser o nome do serviço Docker. | `http://vault-calc:8200` | `http://vault-calc:8200` |
| `VAULT_INTERNAL_ADDR` | Endereço do Vault usado pelo **script interno** (`vault.sh`) para configurar os secrets. É `localhost` visto de dentro do container do Vault. | `http://127.0.0.1:8200` | `http://127.0.0.1:8200` |
| `VAULT_LISTENING_ADDR` | Endereço em que o Vault ficará escutando conexões. `0.0.0.0` significa aceitar de qualquer origem. | `0.0.0.0:8200` | `0.0.0.0:8200` |
| `VAULT_TOKEN` | Token de autenticação do Vault. Em dev, é o token root. **Em produção, use um token com permissões mínimas.** | `meu-token-local` | `TOKEN_GERADO_PELO_VAULT` |
| `MYSQL_PASSWORD_ROOT` | Senha do usuário `root` do MySQL. **Use uma senha forte em produção.** | `MinhaSenh@123` | `Senha_Muito_Forte_Prod!` |
| `MYSQL_ROOT_HOST` | Hosts de onde o root do MySQL pode conectar. `%` aceita qualquer IP. | `%` | `%` ou IP específico |
| `ECR_REGISTRY_REPO` | URL completa do repositório ECR na AWS (sem o nome da imagem). | *(vazio para build local)* | `123456789.dkr.ecr.us-east-1.amazonaws.com` |
| `IMAGE_NAME_DB` | Nome da imagem Docker do MySQL customizado. | `calc-db` | `calc-db` |
| `IMAGE_NAME_APP` | Nome da imagem Docker da aplicação Java de persistência. | `calc-db-application` | `calc-db-application` |

---

### Arquivo: `compose/Servers/Monitoring/.env`

| Variável | Descrição | Exemplo |
|---|---|---|
| `GF_INSTANCE_NAME` | Nome da instância do Grafana (aparece na interface). | `calculadora-monitoring` |
| `GF_ADMIN` | Nome de usuário do administrador do Grafana. | `admin` |
| `GF_ADMIN_PASSWORD` | Senha do administrador do Grafana. **Use senha forte em produção.** | `Grafan@Senh@123` |
| `MYSQL_USERNAME` | Usuário do MySQL que o **MySQL Exporter** usará para coletar métricas. | `calcUserDB` |
| `MYSQL_USER_PASSWORD` | Senha do usuário acima. | `123456` |

---

### Secrets Configurados Automaticamente no Vault

O script `vault.sh` configura automaticamente os seguintes secrets no caminho `secret/calcDbSecrets`:

| Secret (chave no Vault) | Descrição | Valor Padrão Local |
|---|---|---|
| `spring.datasource.url` | URL de conexão JDBC com o MySQL | `jdbc:mysql://mysql-calc:3306/calc` |
| `spring.datasource.username` | Usuário do banco de dados | `calcUserDB` |
| `spring.datasource.password` | Senha do banco de dados | `123456` |

> ⚠️ **Importante:** Em produção, altere **sempre** a senha padrão `123456` por uma senha gerada aleatoriamente e armazenada de forma segura. Você pode usar o próprio Vault para gerar credenciais dinâmicas com o **Database Secrets Engine**.

---

*Documentação do projeto **Calculadora Micro Service CI/CD** — Versão 1.0*