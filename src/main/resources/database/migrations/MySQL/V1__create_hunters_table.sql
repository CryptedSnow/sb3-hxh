CREATE TABLE hunters (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome_hunter VARCHAR(50) NOT NULL,
    idade_hunter INT NOT NULL,
    altura_hunter DECIMAL(3,2) NOT NULL,
    peso_hunter DECIMAL(5,2) NOT NULL,
    tipo_hunter VARCHAR(50) NOT NULL,
    tipo_nen VARCHAR(15) NOT NULL,
    tipo_sanguineo VARCHAR(3) NOT NULL,
    inicio DATE,
    termino DATE,
    deleted_at TIMESTAMP
);