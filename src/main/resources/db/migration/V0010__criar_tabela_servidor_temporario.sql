CREATE TABLE servidor_temporario (
    pes_id INT NOT NULL,
    st_data_admissao DATE NOT NULL,
    st_data_demissao DATE NULL,
    PRIMARY KEY (pes_id, st_data_admissao),
    CONSTRAINT fk_servidor_temporario_pessoa FOREIGN KEY (pes_id) REFERENCES pessoa (pes_id)
);
