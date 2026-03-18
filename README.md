# UPX3
Esse repositório será utilizado para adicionar itens para o desenvolvimento da UPX3 | ABICAP

# Charge Now ⚡

## 🚀 Tecnologias a serem utilizadas *(Sujeito a alteração)*

### Back End
- **API:** Java Puro
- **Banco de Dados:** H2 Database
- **Teste de Requisição Web:** Postman

### Front End
- **Elaboração de Design:** Figma
- **Página Web:** HTML, CSS e JavaScript

---

## 📖 Explicação das escolhas

### ☕ Java Puro
Escolha lógica, a única linguagem dominada pelo grupo em que é possível a produção de uma API.  
Não é a melhor opção, utilizando o Spring Boot no Java o projeto fica bem mais fácil de se fazer e condizente com a realidade do mercado de trabalho para Java, porém, iria exigir umas 20 a 30 horas de estudo para cada um do projeto.

### 🗄️ H2 Database
- **H2 Database:** Por se tratar de um trabalho simples, o banco de dados H2 criaa um banco em memoria, onde podemos administra-lo pelo proprio navegador e não sera necessário a criação de um servidor para o Banco.

### 📬 Postman
Postman é um aplicativo de teste simples de se usar. Imagine o cenário onde temos que testar um botão do site que diz "Agendar Horário" mas sem o site feito? O Postman faz isso! Você apenas indica a URL da requisição e ele te mostra o que acontece quando aquela ação é feita. Isso é ótimo para testar a lógica do Java e saber se está correta.

### 🎨 Figma
No início do projeto, é essencial a projeção da interface para que a partir dela produzirmos o resto, e o Figma é perfeito para isso.

### 🌐 HTML, CSS e JavaScript
Essas são as tecnologias utilizadas para o front, nossa única alternativa.

# 📌 Funcionalidades da API

## 👤 Usuário

### ➕ Cadastro de Usuário
- Endpoint: `POST /usuarios`
- Descrição: Cria um novo usuário na plataforma.
- Dados esperados:
  - Nome
  - Email
  - Senha

### 🔐 Login
- Endpoint: `POST /login`
- Descrição: Autentica o usuário e retorna um token de acesso.
- Dados esperados:
  - Email
  - Senha

### 📋 Listar Usuários (Admin)
- Endpoint: `GET /usuarios`
- Descrição: Retorna a lista de todos os usuários.
- Permissão: Apenas administradores


---

## ⚡ Eletroposto

### ➕ Cadastrar Eletroposto
- Endpoint: `POST /eletropostos`
- Descrição: Cria um novo eletroposto.
- Dados esperados:
  - Nome
  - Localização
  - Número de vagas
  - Tipos de conectores disponíveis

### 📍 Listar Eletropostos
- Endpoint: `GET /eletropostos`
- Descrição: Retorna todos os eletropostos cadastrados.

### 🔎 Detalhes do Eletroposto
- Endpoint: `GET /eletropostos/{id}`
- Descrição: Retorna informações detalhadas de um eletroposto específico.


---

## 📅 Agendamento

### ➕ Criar Agendamento
- Endpoint: `POST /agendamentos`
- Descrição: Cria um novo agendamento para uso de um eletroposto.
- Dados esperados:
  - ID do usuário
  - ID do eletroposto
  - Data e horário

### 📋 Listar Agendamentos do Usuário
- Endpoint: `GET /agendamentos`
- Descrição: Retorna todos os agendamentos do usuário autenticado.

### ❌ Cancelar Agendamento
- Endpoint: `DELETE /agendamentos/{id}`
- Descrição: Cancela um agendamento existente.




