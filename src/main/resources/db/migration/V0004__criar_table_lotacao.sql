-- Generated by the database client.
CREATE TABLE IF NOT EXISTS lotacao(
    lot_id bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
    lot_data_lotacao date NOT NULL,
    lot_data_remocao timestamp without time zone,
    lot_portaria varchar(100),
    pes_id bigint NOT NULL,
    unid_id bigint NOT NULL,
    PRIMARY KEY(lot_id),
    CONSTRAINT fks342a0fb9n1leq8098d30yqf6 FOREIGN key(pes_id) REFERENCES pessoa(pes_id),
    CONSTRAINT fkd26eij3w79wqv30y2q5mhhr9v FOREIGN key(unid_id) REFERENCES unidade(unid_id)
);

