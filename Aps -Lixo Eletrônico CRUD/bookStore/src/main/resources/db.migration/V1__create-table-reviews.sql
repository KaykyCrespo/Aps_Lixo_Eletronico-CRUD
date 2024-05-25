CREATE TABLE lixo(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nm_tipo_lixo VARCHAR(50) NOT NULL,
    qtd_lixo BIGINT NOT NULL,
    peso_lixo DOUBLE NOT NULL,
    PRIMARY KEY(id)
);