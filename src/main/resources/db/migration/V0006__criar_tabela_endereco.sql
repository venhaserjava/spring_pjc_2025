CREATE TABLE IF NOT EXISTS endereco (
    end_id SERIAL PRIMARY KEY,
    end_tipo_logradouro VARCHAR(50) NOT NULL,
    end_logradouro VARCHAR(200) NOT NULL,
    end_numero INT NOT NULL,
    end_bairro VARCHAR(100) NOT NULL,
    cid_id INT NOT NULL,
    CONSTRAINT fk_endereco_cidade FOREIGN KEY (cid_id) REFERENCES cidade (cid_id),
    CONSTRAINT uq_endereco UNIQUE (end_logradouro, end_bairro, end_numero, cid_id)
);
