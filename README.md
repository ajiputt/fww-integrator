# Flying Without Wings - Integrator Service

## Requirements

1. Java - 17

2. Maven - 3.x.x

3. PostgresSQL - 16.x.x

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/ajiputt/fww-integrator.git
```

**2. Create PostgresSQL database**

```bash
create database quartz;
```

**3. Create user with username and password and grant all privilege this
user to created database**

```bash
create user <username> with encrypted password <password>;
grant all privileges on database quartz to <username>;
```

**4. Change PostgresSQL username and password as per your MySQL installation**

open `src/main/resources/application.yml`, and change `spring.datasource.
username` and `spring.datasource.password` properties

**5. Create Tables**

Create tables by executing the `quartz.sql` script located
inside `src/main/resources` directory.

**6. Spring Security with Basic Auth**

The app will handle security with Bearer Token, you can register and then
login to get token to access other service

**7. Build and run the app using maven**

Finally, You can run the app by typing the following command from the root
directory of the project. This service depends on fww-core service, you can
check fww-core service
on <a href="https://github.com/ajiputt/fww-core" target="_blank">GitHub</a>

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

## API Reference

### Register Member

```http
  POST /api/v1/auth/register
```

### Login Member

```http
  POST /api/v1/auth/login
```

### Get Airports

```http
  GET /api/v1/airports
```

### Get Schedule

```http
  GET /api/v1/schedules?departure=<dept>&arrival=<arr>&date=<date>
```
