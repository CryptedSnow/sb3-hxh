CREATE TABLE recompensas (
    id SERIAL PRIMARY KEY,
    descricao_recompensa VARCHAR(255) NOT NULL,
    valor_recompensa DECIMAL(10,2) NOT NULL,
    deleted_at TIMESTAMP
);