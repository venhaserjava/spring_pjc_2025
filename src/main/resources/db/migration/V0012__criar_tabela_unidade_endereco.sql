DROP TABLE IF EXISTS unidade_endereco;
CREATE TABLE unidade_endereco (
    unid_id BIGINT NOT NULL,
    end_id BIGINT NOT NULL,
    PRIMARY KEY (unid_id, end_id),
    CONSTRAINT fk_unidade FOREIGN KEY (unid_id) REFERENCES unidade (unid_id) ON DELETE CASCADE,
    CONSTRAINT fk_endereco FOREIGN KEY (end_id) REFERENCES endereco (end_id) ON DELETE CASCADE
);
