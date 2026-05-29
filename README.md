# Application (map)

```
sb3-hxh  
├── src  
    └── main  
        ├── java  
        │   └── com.springboot3.sb3hxh
        │       ├── Configuration 
        │       │   ├── IconConfiguration.java    
        │       ├── Controller  
        │       │   ├── HunterController.java  
        │       │   ├── RecompensaController.java  
        │       │   └── RecompensadoController.java  
        │       ├── Converter  
        │       │   ├── HunterConverter.java  
        │       │   ├── RecompensaConverter.java
        │       │   ├── TipoHunterConverter.java  
        │       │   ├── TipoNenConverter.java
        │       │   ├── TipoSanguineoConverter.java  
        │       ├── DAO  
        │       │   ├── HunterDAO.java  
        │       │   ├── RecompensaDAO.java  
        │       │   ├── RecompensadoDAO.java  
        │       ├── Entity  
        │       │   ├── HunterEntity.java  
        │       │   ├── RecompensadoEntity.java  
        │       │   ├── RecompensaEntity.java
        │       ├── Enum  
        │       │   ├── TipoHunterEnum.java  
        │       │   ├── TipoNenEnum.java  
        │       │   ├── TipoSanguineoEnum.java    
        │       ├── Service  
        │       │   ├── HunterService.java  
        │       │   ├── RecompensadoService.java  
        │       │   ├── RecompensaService.java  
        │       └── Validation  
        │           ├── ConstraintValidation  
        │           │   ├── HunterValidation.java  
        │           │   ├── RecompensaConstraintValidation.java   
        │           ├── HunterValidation.java  
        │           ├── RecompensaValidation.java  
        │           ├── RecompensadoValidation.java  
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
CREATE TABLE hunters (
	id SERIAL PRIMARY KEY,
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