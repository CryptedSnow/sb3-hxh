- application.properties
```
# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/sb3-hxh
spring.datasource.username=postgres
spring.datasource.password=
spring.datasource.driver-class-name=org.postgresql.Driver

# MySQL
spring.datasource.mysql.url=jdbc:mysql://localhost:3306/sb3-hxh
spring.datasource.mysql.username=root
spring.datasource.mysql.password=
spring.datasource.mysql.driver-class-name=com.mysql.cj.jdbc.Driver
```

- Tables
```
CREATE TABLE tipos_hunters (
	id SERIAL PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL,
	deleted_at TIMESTAMP
);

CREATE TABLE tipos_nens (
	id SERIAL PRIMARY KEY,
	descricao VARCHAR(15) NOT NULL,
	deleted_at TIMESTAMP
);

CREATE TABLE tipos_sanguineos (
	id SERIAL PRIMARY KEY,
	descricao VARCHAR(3) NOT NULL,
	deleted_at TIMESTAMP
);

CREATE TABLE hunters (
	id SERIAL PRIMARY KEY,
	nome_hunter VARCHAR(50) NOT NULL,
	idade_hunter INT NOT NULL,
	altura_hunter DECIMAL(3,2) NOT NULL,
	peso_hunter DECIMAL(5,2) NOT NULL,
	tipo_hunter_id INT NOT NULL,
	tipo_nen_id INT NOT NULL,
	tipo_sanguineo_id INT NOT NULL,
	inicio DATE,
	termino DATE,
	deleted_at TIMESTAMP,
	FOREIGN KEY (tipo_hunter_id) REFERENCES tipos_hunters (id),
	FOREIGN KEY (tipo_nen_id) REFERENCES tipos_nens (id),
	FOREIGN KEY (tipo_sanguineo_id) REFERENCES tipos_sanguineos (id)
);

CREATE TABLE recompensas (
	id serial SERIAL PRIMARY KEY,
	descricao_recompensa VARCHAR(255) NOT NULL,
	valor_recompensa DECIMAL(10,2) NOT NULL,
	deleted_at TIMESTAMP
);

CREATE TABLE recompensados (
	id SERIAL PRIMARY KEY,
	hunter_id INT NOT NULL,
	recompensa_id INT NOT NULL,
	status BOOLEAN NOT NULL,
	deleted_at TIMESTAMP,
	FOREIGN KEY (hunter_id) REFERENCES hunters (id),
	FOREIGN KEY (recompensa_id) REFERENCES recompensas (id)
);
```
- Insert commands
```
INSERT INTO tipos_hunters (descricao) VALUES
('Hunter Gourmet'),
('Hunter Arqueólogo'),
('Hunter Cientista ou Hunter Técnico'),
('Hunter Selvagem ou Hunter Ambientalista'),
('Hunter Musical'),
('Hunter Treasure'),
('Hunter Lista Negra'),
('Hunter Mercenário'),
('Hunter Medicinal'),
('Hunter Hacker'),
('Hunter Cabeça'),
('Hunter de Informação'),
('Hunter Jackspot'),
('Hunter Vírus'),
('Hunter da Juventudade e Beleza'),
('Hunter Terrorista'),
('Hunter de Venenos'),
('Hunter Caçador'),
('Hunter Paleógrafo'),
('Hunter Perdido'),
('Hunter Provisório'),
('Hunter Temporário');

INSERT INTO tipos_nens (descricao) VALUES
('Reforço'),
('Emissão'),
('Transformação'),
('Manipulação'),
('Materialização'),
('Especialização');

INSERT INTO tipos_sanguineos (descricao) VALUES
('A+'),
('A-'),
('B+'),
('B-'),
('AB+'),
('AB-'),
('O+'),
('O-');
```