CREATE TABLE unidade(
unid_id SERIAL PRIMARY KEY,
unid_nome varchar(200) NOT NULL UNIQUE,
unid_sigla varchar(20) NOT NULL UNIQUE
);