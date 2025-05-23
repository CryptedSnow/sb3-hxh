# Application (map)

```
sb3-hxh  
├── src  
    └── main  
        ├── java  
        │   └── com.springboot3.sb3hxh  
        │       ├── Controller  
        │       │   ├── HunterController.java  
        │       │   ├── RecompensaController.java  
        │       │   └── RecompensadoController.java  
        │       ├── Converter  
        │       │   ├── HunterConverter.java  
        │       │   ├── RecompensaConverter.java  
        │       │   ├── TipoHunterConverter.java  
        │       │   ├── TipoNenConverter.java  
        │       │   └── TipoSanguineoConverter.java  
        │       ├── DAO  
        │       │   ├── HunterDAO.java  
        │       │   ├── RecompensaDAO.java  
        │       │   ├── RecompensadoDAO.java  
        │       │   ├── TipoHunterDAO.java  
        │       │   ├── TipoNenDAO.java  
        │       │   └── TipoSanguineoDAO.java  
        │       ├── Entity  
        │       │   ├── HunterEntity.java  
        │       │   ├── RecompensadoEntity.java  
        │       │   ├── RecompensaEntity.java  
        │       │   ├── TipoHunterEntity.java  
        │       │   ├── TipoNenEntity.java  
        │       │   └── TipoSanguineoEntity.java  
        │       ├── Service  
        │       │   ├── HunterService.java  
        │       │   ├── RecompensadoService.java  
        │       │   ├── RecompensaService.java  
        │       │   ├── TipoHunterService.java  
        │       │   ├── TipoNenService.java  
        │       │   └── TipoSanguineoService.java  
        │       └── Validation  
        │           ├── ConstraintValidation  
        │           │   ├── HunterValidation.java  
        │           │   ├── RecompensaConstraintValidation.java  
        │           │   ├── TipoHunterConstraintValidation.java  
        │           │   ├── TipoNenConstraintValidation.java  
        │           │   └── TipoSanguineoConstraintValidation.java  
        │           ├── HunterValidation.java  
        │           ├── RecompensaValidation.java  
        │           ├── RecompensadoValidation.java  
        │           ├── TipoHunterValidation.java  
        │           ├── TipoNenValidation.java  
        │           └── TipoSanguineoValidation.java  
        └── resources  
            ├── templates  
            └── application.properties  
```

It is necessary install ```JDK```, the minimum version to perfomate Spring Boot 3 is **17** (I usually use **JDK 21** version). Don't forget about to use [IntelliJ IDEA](https://www.jetbrains.com/idea/) to facilitate your experience.

### Application structure pattern

See more about **[Three-Tier Architecture](https://www.ibm.com/topics/three-tier-architecture)**.

1 - Presentation tier:
* ```templates``` folder: Interface files from application (Because redirecting of ```Controller``` class).

2 - Application tier:
* ```Controller``` folder: HTTP requests (extends ```Entity, Service``` class).
* ```Service``` folder: Logic of application methods (extends ```DAO, Entity``` class).

3 - Data tier:
* ```DAO``` folder: Access the database (extends ```Entity``` class).
* ```Entity``` folder: Represent the database informations.

4 - Foreign key converter
* ```Converter``` folder: Use to define correctly foreign key value.

5 - Exist validation
* ```Validation``` folder: Create a validation to check existence of foreign keys.

6 - Others files:
* ```application.properties```: Application settings file.

## Database

- You can use ```PostgreSQL``` or ```MySQL``` database. Define settings database in ```application.properties``` (Check the database name, user and password of your preference)

```
# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/database_name
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/database_name
spring.datasource.username=postgres
spring.datasource.password=
spring.datasource.driver-class-name=org.postgresql.Driver
```

- If you want use ```Docker``` to insert the lines:

```
# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/sb3-hxh
spring.datasource.username=user
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/sb3-hxh
spring.datasource.username=user
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver
```

### Panels

- phpMyAdmin: http://localhost:8081
    - User: ```user```
    - Password: ```password```
- pgAdmin: http://localhost:8082
    - User: ```admin@admin.com```
    - Password: ```admin```

To create a server to pgAdmin:
- Host name/address: ```pgsql```
- Port: ```5432```
- Maintenance database:	```postgres```
- Username:	```user```
- Password:	```password```

Create table to **PostgreSQL** database:
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
	id SERIAL PRIMARY KEY,
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

Create tables to **MySQL** database:
```
CREATE TABLE tipos_hunters (
	id INT PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL,
	deleted_at TIMESTAMP
);

CREATE TABLE tipos_nens (
	id INT PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(15) NOT NULL,
	deleted_at TIMESTAMP
);

CREATE TABLE tipos_sanguineos (
	id INT PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(3) NOT NULL,
	deleted_at TIMESTAMP
);

CREATE TABLE hunters (
	id INT PRIMARY KEY AUTO_INCREMENT,
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
	id INT PRIMARY KEY AUTO_INCREMENT,
	descricao_recompensa VARCHAR(255) NOT NULL,
	valor_recompensa DECIMAL(10,2) NOT NULL,
	deleted_at TIMESTAMP
);

CREATE TABLE recompensados (
	id INT PRIMARY KEY AUTO_INCREMENT,
	hunter_id INT NOT NULL,
	recompensa_id INT NOT NULL,
	status BOOLEAN NOT NULL,
	deleted_at TIMESTAMP,
	FOREIGN KEY (hunter_id) REFERENCES hunters (id),
	FOREIGN KEY (recompensa_id) REFERENCES recompensas (id)
);
```

Insert commands:
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