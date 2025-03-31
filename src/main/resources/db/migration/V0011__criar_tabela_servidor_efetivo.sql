DROP TABLE IF EXISTS servidor_efetivo;
CREATE TABLE IF NOT EXISTS servidor_efetivo (
    se_id SERIAL PRIMARY KEY,
    pes_id bigint NOT NULL,
    se_matricula VARCHAR(20) NOT NULL,    
    CONSTRAINT fk_servidor_efetivo_pessoa FOREIGN KEY (pes_id) REFERENCES pessoa (pes_id)
);

