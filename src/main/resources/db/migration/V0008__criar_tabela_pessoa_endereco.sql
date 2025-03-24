-- Migration para criar a tabela pessoa_endereco
CREATE TABLE pessoa_endereco (
    pes_id BIGINT NOT NULL,
    end_id BIGINT NOT NULL,
    PRIMARY KEY (pes_id, end_id),
    CONSTRAINT fk_pessoa FOREIGN KEY (pes_id) REFERENCES pessoa (pes_id) ON DELETE CASCADE,
    CONSTRAINT fk_endereco FOREIGN KEY (end_id) REFERENCES endereco (end_id) ON DELETE CASCADE
);
