CREATE TABLE recompensas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    descricao_recompensa VARCHAR(255) NOT NULL,
    valor_recompensa DECIMAL(10,2) NOT NULL,
    deleted_at TIMESTAMP
);