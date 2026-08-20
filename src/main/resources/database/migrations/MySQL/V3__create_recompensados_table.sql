CREATE TABLE recompensados (
    id INT PRIMARY KEY AUTO_INCREMENT,
    hunter_id INT NOT NULL,
    recompensa_id INT NOT NULL,
    status BOOLEAN NOT NULL,
    deleted_at TIMESTAMP,
    FOREIGN KEY (hunter_id) REFERENCES hunters (id),
    FOREIGN KEY (recompensa_id) REFERENCES recompensas (id)
);