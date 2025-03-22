ALTER TABLE lotacao ADD CONSTRAINT fk_lotacao_unidade
FOREIGN KEY (unid_id) REFERENCES unidade (unid_id) ON DELETE RESTRICT;
