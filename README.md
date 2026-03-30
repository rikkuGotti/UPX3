# UPX3
Esse repositório será utilizado para adicionar itens para o desenvolvimento da UPX3 | ABICAP

# Charge Now ⚡ (Condomínios)

## 🚀 Tecnologias a serem utilizadas

### Back End
- **Framework:** Spring Boot 4.0 (Java 25)
- **Banco de Dados:** H2 Database (Em memória)
- **Segurança/Sessão:** Cookies (JSESSIONID) e HttpSession
- **Teste de Requisição Web:** Postman

### Front End
- **Elaboração de Design:** Figma
- **Página Web:** HTML5, CSS3 e JavaScript (ES6+)

---

## 📖 Explicação das escolhas

### 🍃 Spring Boot
Substituímos o Java Puro pelo Spring Boot para agilizar o desenvolvimento. O framework lida com a infraestrutura (servidor Tomcat embutido, injeção de dependência e roteamento), permitindo que o grupo foque na regra de negócio dos pontos de recarga. É a tecnologia padrão do mercado atual.

### 🗄️ H2 Database
Por se tratar de um projeto para condomínios, o banco de dados H2 é ideal. Ele roda em memória, facilitando o desenvolvimento sem a necessidade de configurar servidores de banco de dados complexos (como MySQL ou PostgreSQL) em cada máquina do grupo.

### 📬 Postman
Utilizado para validar os Endpoints da API antes mesmo do Front End estar pronto. Com ele, testamos o fluxo de cadastro, login e reserva de pontos de recarga simulando o comportamento real do navegador.

### 🎨 Figma
Utilizado para o protótipo de alta fidelidade. Essencial para definir como o morador do condomínio visualizará a disponibilidade das vagas de recarga.

### 🌐 HTML, CSS e JavaScript
Base do desenvolvimento Web para criar uma interface responsiva onde o usuário poderá gerenciar seus agendamentos de recarga de forma intuitiva.

---

# 📌 Funcionalidades da API (Ponto de Recarga em Condomínios)

## 👤 Usuário & Autenticação

### ➕ Cadastro de Usuário
- **Endpoint:** `POST /auth/cadastro`
- **Descrição:** Registra um novo morador e seu veículo elétrico (vinculado via Cascade JPA).
- **Dados esperados:** Nome, Email, Senha, Dados do Veículo (Placa, Modelo, Conector).

### 🔐 Login (Sessão por Cookie)
- **Endpoint:** `POST /auth/login`
- **Descrição:** Autentica o usuário via `@RequestParam`. Se bem-sucedido, cria uma `HttpSession` e devolve um cookie `JSESSIONID`.

### 🔍 Check de Sessão
- **Endpoint:** `GET /auth/check`
- **Descrição:** Verifica se o usuário possui um cookie válido e retorna os dados de quem está logado.

---

## 🔌 Pontos de Recarga

### ➕ Cadastrar Ponto (Admin)
- **Endpoint:** `POST /pontos`
- **Descrição:** Registra uma nova estação de recarga no condomínio.
- **Dados esperados:** Identificação (Ex: Vaga 42), Tipo de Carga, Status (Disponível/Ocupado).

### 📍 Listar Pontos Disponíveis
- **Endpoint:** `GET /pontos/disponiveis`
- **Descrição:** Retorna todos os pontos de recarga que não possuem agendamento no momento.

### 🔎 Detalhes do Ponto
- **Endpoint:** `GET /pontos/{id}`
- **Descrição:** Retorna informações técnicas sobre a potência e tipo de conector de um ponto específico.

---

## 📅 Reservas (Agendamentos)

### ➕ Criar Reserva
- **Endpoint:** `POST /reservas`
- **Descrição:** O morador reserva um horário para carregar seu veículo.
- **Validação:** Verifica via `HttpSession` se o usuário está logado antes de permitir a reserva.

### 📋 Minhas Reservas
- **Endpoint:** `GET /reservas/meus`
- **Descrição:** Retorna apenas os agendamentos do morador autenticado (filtrado via ID da sessão).

### ❌ Cancelar Reserva
- **Endpoint:** `DELETE /reservas/{id}`
- **Descrição:** Libera o ponto de recarga para outros moradores.
