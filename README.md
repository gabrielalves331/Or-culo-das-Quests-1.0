# Oráculo das Quests

Sistema web para gerenciamento de campanhas e missões de RPG com **visualização estrutural em grafos do banco de dados**.

O projeto integra:

* Java + JDBC
* SQLite
* HTML
* Visualização de Grafos com vis-network

---

# Objetivo do Projeto

O **Oráculo das Quests** foi desenvolvido para:

* Gerenciar campanhas de RPG
* Organizar missões
* Controlar usuários
* Visualizar relações entre campanhas e missões
* Representar os dados do banco como um **grafo interativo**

Além do gerenciamento tradicional, o sistema agora permite analisar estruturalmente os dados através de uma **modelagem em grafos**, conectando:

* Campanhas
* Missões
* Relações entre entidades

---

# 🛠️ Tecnologias Utilizadas

| Tecnologia       | Finalidade             |
| ---------------- | ---------------------- |
| Java             | Lógica da aplicação    |
| JDBC             | Comunicação com banco  |
| SQLite           | Banco de dados local   |
| Maven            | Build e gerenciamento  |
| JavaFX / WebView | Interface              |
| HTML             | Estrutura das páginas  |
| vis-network.js   | Visualização de grafos |

---

# Arquitetura do Sistema

O sistema segue uma arquitetura em camadas:

```
Interface HTML
      ↓
Paginas (Controllers)
      ↓
Modelo (Entidades)
      ↓
DAO (Persistência)
      ↓
SQLite
```

Com a adição da camada de visualização em grafos:

```
Banco de Dados
      ↓
GrafoDAO
      ↓
GeradorGrafoJson
      ↓
grafo.json
      ↓
grafo.html (vis-network)
```

---

# Estrutura do Projeto

```
src/main/java
 ├── dao
 │    ├── CampanhaDAO
 │    ├── MissaoDAO
 │    ├── UsuarioDAO
 │    ├── GrafoDAO
 │
 ├── modelo
 │    ├── Campanha
 │    ├── Missao
 │    ├── Usuario
 │
 ├── paginas
 │    ├── LoginWeb
 │    ├── HubWeb
 │    ├── GrafoWeb
 │
 └── util
      ├── Sessao
      ├── GeradorGrafoJson
```

---

#  Visualização em Grafos

O sistema gera automaticamente um arquivo:

```
grafo.json
```

Esse arquivo representa os dados estruturados como:

* Nós (Campanhas e Missões)
* Arestas (Relacionamentos)

A visualização é feita em:

```
grafo.html
```

utilizando a biblioteca:

```
vis-network.min.js
```

A visualização permite:

* Explorar conexões entre campanhas e missões
* Identificar estruturas
* Analisar relações de forma interativa

---

# Banco de Dados

Banco local:

```
oraculo_das_quests.db
```

Contém:

* Usuários
* Campanhas
* Missões

---

# Como Executar o Projeto

## Pré-requisitos

* Java JDK 17+
* Maven

---

## Clonar

```
git clone https://github.com/gabrielalves331/Or-culo-das-Quests-1.0.git
cd Or-culo-das-Quests-1.0
```

---

## Compilar

```
mvn clean install
```

---

## Executar

```
mvn exec:java
```

ou

```
java -jar target/PaginaInicial-1.0.jar
```

---

# Funcionalidades

✔ Cadastro e login de usuários
✔ Criação de campanhas
✔ Inserção e edição de missões
✔ Remoção de dados
✔ Mural informativo
✔ Visualização estrutural em grafos
✔ Geração automática de JSON

---

# Conceito Acadêmico Envolvido

O projeto integra conceitos de:

* Estruturas de Dados (Grafos)
* Persistência com DAO
* Arquitetura em Camadas
* Conversão de dados para JSON
* Visualização de dados
* Integração backend → frontend

---

# Melhorias Futuras

* Algoritmos de busca em grafos (DFS, BFS)
* Caminho mínimo entre missões
* Métricas de centralidade
* Exportação do grafo como imagem
* Deploy web com Spring Boot

---

# Autor

Gabriel Alves
Estudante de Ciência da Computação – IFBA

GitHub:
https://github.com/gabrielalves331
