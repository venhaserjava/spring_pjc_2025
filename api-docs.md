Documentação da API spring_pjc_2025

📑 Índice

    🔐 Autenticação

    🌆 Cidades

    🏠 Endereços

    🏢 Unidades

    📌 Lotações

    🧑 Servidores

    ⏳ Servidores Temporários

    🧍 Servidores Efetivos



🔐 Autenticação

    🔑 Login
    POST /auth/login
    Autentica o usuário e retorna um token JWT + refresh token.

    Corpo da Requisição
        {
            "username": "usuario",
            "password": "senha"
        }
    Resposta
        {
            "token": "jwt-token",
            "expiresAt": "2025-04-05T18:00:00Z",
            "refreshToken": "uuid-token"
        }

    🔁 Refresh Token
    POST /auth/refresh

    Corpo da Requisição
        {
            "refreshToken": "uuid-token"
        }
    Resposta
        {
        "accessToken": "novo-jwt-token"
        }

🌆 Cidades

    🔍 Buscar cidades
    GET /cidades?nome=xyz&page=0&size=10

    🔍 Buscar cidade por ID
    GET /cidades/{id}

    ➕ Criar cidade
    POST /cidades

    Exemplo de payload
        {
            "nome": "Brasília",
            "estado": "DF"
        }
    📝 Atualizar cidade
    PUT /cidades/{id}

🏠 Endereços

    🔍 Buscar endereços
    GET /enderecos?logradouro=rua+abc&page=0&size=10

    🔍 Buscar por ID
    GET /enderecos/{id}

    ➕ Criar endereço
    POST /enderecos

    📝 Atualizar endereço
    PUT /enderecos/{id}

🧑 Servidores

    ➕ Criar servidor com foto
    POST /servidores
    Tipo: multipart/form-data

    Partes da Requisição

    pessoa: JSON com dados do servidor

    foto: Imagem (formato .png ou .jpg)

    Resposta    
        {
            "id": 1,
            "nome": "João",
            "foto": "https://link-temporario-da-foto"
        }

🏢 Unidades

    🔍 Buscar unidades
    GET /unidades?nome=abc&page=0&size=10

    🔍 Buscar unidade por ID
    GET /unidades/{id}

    ➕ Criar unidade
    POST /unidades

    📝 Atualizar unidade
    PUT /unidades/{id}


📌 Lotações

    ➕ Criar lotação
    POST /lotacoes

    📝 Atualizar lotação
    PUT /lotacoes/{id}

    🔍 Buscar por ID
    GET /lotacoes/{id}

    🔍 Listar todas
    GET /lotacoes?page=0&size=10

    🔍 Buscar endereços funcionais
    GET /lotacoes/enderecos-funcionais?nome=joao&page=0&size=10

⏳ Servidores Temporários

    🔍 Buscar por ID
    GET /servidores-temporarios/{id}

    🔍 Buscar todos
    GET /servidores-temporarios?nome=ana&page=0&size=10

    ➕ Criar
    POST /servidores-temporarios

    📝 Atualizar
    PUT /servidores-temporarios/{id}    

🧍 Servidores Efetivos

    ➕ Criar
    POST /servidores-efetivos

    🔍 Listar por unidade
    GET /servidores-efetivos/unidade/{unidadeId}?page=0&size=10    