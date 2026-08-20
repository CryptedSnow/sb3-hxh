It's necessary install ```JDK```, the minimum version to perfomate Spring Boot 3 is **17** (I usually use **JDK 21** version). Don't forget about to use [IntelliJ IDEA](https://www.jetbrains.com/idea/) to facilitate your experience.

### Application structure pattern

See more about **[Three-Tier Architecture](https://www.ibm.com/topics/three-tier-architecture)**.

1 - Presentation tier:
* ```templates``` folder: Interface files from application (Because redirecting of ```Controller``` class).
* ```static```: CSS e JS files to implements Interface files.

2 - Application tier:
* ```Controller``` folder: HTTP requests (extends ```Entity, Service``` class).
* ```Service``` folder: Logic of application methods (extends ```DAO, Entity``` class).

3 - Data tier:
* ```Repository``` folder: Access the database (extends ```Entity``` class).
* ```Entity``` folder: Represent the database informations.

4 - Foreign key converter
* ```Converter``` folder: Use to define correctly foreign key value.

5 - Enum options
* ```Enum``` folder: Work with predefined sets of values.

6 - Icon
* ```Configuration``` folder: Default icon to browser window.

7 - Others files:
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

```Flyway``` to insert the lines:

```
# MySQL
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.flyway.locations=classpath:database/migrations/MySQL

# Postgres
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.flyway.locations=classpath:database/migrations/PostgreSQL
```

```Flyway``` command without ```Docker```:
```
# MySQL
java -jar target/*.jar --spring.profiles.active=mysql

# PostgreSQL
java -jar target/*.jar --spring.profiles.active=postgres
```

```Flyway``` command with ```Docker```:
```
docker compose up -d --build
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