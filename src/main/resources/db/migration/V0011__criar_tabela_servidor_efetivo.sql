CREATE TABLE servidor_efetivo (
    pes_id bigint NOT NULL,
    se_matricula VARCHAR(20) NOT NULL,
    PRIMARY KEY (pes_id, se_matricula),
    CONSTRAINT fk_servidor_efetivo_pessoa FOREIGN KEY (pes_id) REFERENCES pessoa (pes_id)
);
