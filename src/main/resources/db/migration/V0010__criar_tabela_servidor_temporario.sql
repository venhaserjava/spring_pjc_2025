CREATE TABLE IF NOT EXISTS servidor_temporario (
    st_id SERIAL PRIMARY KEY,
    pes_id BIGINT NOT NULL,
    st_data_admissao DATE NOT NULL,
    st_data_demissao DATE,
    CONSTRAINT fk_servidor_temporario_pessoa FOREIGN KEY (pes_id) REFERENCES pessoa (pes_id)    
);
