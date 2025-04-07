🛠️ spring_pjc_2025 - PROJETO PRÁTICO IMPLEMENTAÇÃO BACK-END

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![MinIO](https://img.shields.io/badge/MinIO-EF2D5E?style=for-the-badge&logo=min.io&logoColor=white)
![Maven](https://img.shields.io/badge/maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/swagger-%2300C853.svg?style=for-the-badge&logo=swagger&logoColor=white)


Este projeto é uma API construída usando
**Java, Java Spring, Flyway Migrations, PostgresSQL as the database, and Spring Security and JWT for authentication control.**

## Indice

- [descrição](#description)
- [finalidade](#purpose)
- [Tecnologias Utilizadas](#Technologies)
- [instalação e Configuração](#installation)
- [API Endpoints](#api-endpoints)
- [Uso](#usage)
- [Authenticação](#authentication)
- [Database](#database)

---
# Descrição do Projeto

**spring_pjc_2025** é uma **API RESTful** para gerenciamento de pessoas, denominada simplesmente como **Servidor**.

O sistema oferece funcionalidades para: cadastro, autenticação e manipulação de dados pessoais e profissionais.

Os principais recursos utilizados incluem:
- Autenticação via **JWT**
- Persistência com **PostgreSQL**
- Armazenamento de arquivos com **MinIO**
- Empacotamento com **Docker**
- Orquestração com **Docker Compose**

A API permite o registro de Servidores em duas modalidades:
- **Servidor Temporário**
- **Servidor Efetivo** – requer informações adicionais como lotação (local de trabalho)

Além dos dados comuns de cadastro (nome, mãe, pai, sexo, endereço), é esperada também a **fotografia do servidor**.

---

# Finalidade

Este projeto foi desenvolvido para apreciação da comissão avaliadora do:

> **EDITAL DE PROCESSO SELETIVO SIMPLIFICADO Nº 002/2025**  
> **SEPLAG – Secretaria de Estado de Planejamento e Gestão**

---

##  Tecnologias Utilizadas

- Java 21 + Spring Boot 3
- Spring Web / Spring Data JPA / Spring Security
- PostgreSQL
- MinIO (compatível com S3)
- JWT (autenticação)
- Docker + Docker Compose

---

# Instalação

Este guia fornece instruções para instalar o **Docker**, configurar uma instância do **PostgreSQL** e uma instância do **MinIO** utilizando imagens oficiais e recentes.

## 1. Instalar o Docker

### Linux (Ubuntu/Debian)
```sh
sudo apt update && sudo apt upgrade -y
sudo apt install -y ca-certificates curl gnupg
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo tee /etc/apt/keyrings/docker.asc > /dev/null
sudo chmod a+r /etc/apt/keyrings/docker.asc
echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt update
sudo apt install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

### Windows/MacOS
Baixe e instale o Docker Desktop pelo site oficial:
- **Windows/MacOS:** [https://www.docker.com/get-started](https://www.docker.com/get-started)

## 2. Baixar e Rodar a Imagem Mais Recente do PostgreSQL

```sh
docker pull postgres:latest
```
ou para baixar uma imagem mais "leve" usada geralmente para estudar, voce pode usar as versões "alpine",
```sh
docker pull postgres:alpine
```

Para rodar um container PostgreSQL:
```sh
docker run -d \
  --name postgres_container \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=admin \
  -e POSTGRES_DB=meubanco \
  -p 5432:5432 \
  postgres:latest
```

## 3. Baixar e Rodar a Imagem Mais Recente do MinIO

```sh
docker pull minio/minio:latest
```
Para rodar o MinIO no modo standalone:
```sh
docker run -d \
  --name minio_container \
  -e MINIO_ROOT_USER=admin \
  -e MINIO_ROOT_PASSWORD=adminpassword \
  -p 9000:9000 \
  -p 9001:9001 \
  minio/minio server /data --console-address ":9001"
```

## 4. Acessando os Serviços
- **PostgreSQL:** Pode ser acessado via `psql` ou ferramentas como DBeaver e pgAdmin na porta `5432`.

```
DROP DATABASE spring_pjc_2025_db;
CREATE DATABASE spring_pjc_2025_db;


Então, onde e como registrar esse usuário?
Você tem 3 formas principais de registrar o usuário e sua senha:

INSERT INTO usuario (username, password) VALUES (
  'admin',
  '$2a$10$w.xpQKqgqXPLv4l4oLZiUuj83J4tcyEMu7eDuh1vKugEMsC1blf7K'
);
```

- **MinIO Console:** Acesse `http://localhost:9001` e faça login com `admin / adminpassword`.

Pronto! Agora você tem o Docker rodando com PostgreSQL e MinIO. 🚀


# API EndPoints

## 📘 Documentação da API

A documentação completa dos endpoints REST da aplicação está disponível no arquivo [`api-docs.md`](./api-docs.md).

# Uso

## 🚀 Como executar o projeto com Docker

### 1. Pré-requisitos

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

### 2. Build da aplicação

> A imagem da aplicação será criada automaticamente com base no `Dockerfile`.

> docker compose build

### 3. Subir os serviços (API, banco de dados e MinIO)

> docker compose up


### 4. Acessos

Serviço         URL
```
API	            http://localhost:8080
PostgreSQL	    localhost:5432
MinIO Console   http://localhost:9001
```