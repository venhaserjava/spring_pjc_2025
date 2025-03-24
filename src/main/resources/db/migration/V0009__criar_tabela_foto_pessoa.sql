CREATE TABLE foto_pessoa (
    fp_id BIGSERIAL PRIMARY KEY,
    pes_id BIGINT NOT NULL,
    fp_data DATE NOT NULL,
    fp_bucket VARCHAR(50) NOT NULL,
    fp_hash VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT fk_foto_pessoa FOREIGN KEY (pes_id) REFERENCES pessoa (pes_id) ON DELETE CASCADE
);

-- Criando índice para ordenação por data (da mais recente para a mais antiga)
CREATE INDEX idx_foto_pessoa_data ON foto_pessoa (fp_data DESC);
