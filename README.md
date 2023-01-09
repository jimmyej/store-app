# Store management services
This Microservice contains the following endpoints:

- Products

### Tools
- Java 11
- Spring boot 2.7.0
- Postgres DB
- Jacoco code coverage

### Setup
1. Create a application-local.yml file and add the following configuration.
    
    ```properties
    spring:
      datasource:
        url: jdbc:postgresql://postgres:5432/postgres
        username: <your username>
        password: <your password>
    
      jpa:
        properties:
          hibernate:
            ddl-auto: create
            default_schema: storedb
    ```
2. add postgres host in the OS
   ```sh
   /etc/hosts (in Mac OS)
   127.0.0.1	postgres
   ```
   ```sh
   C:\Windows\System32\drivers\etc\hosts (in Windows OS)
   127.0.0.1	postgres
   ```
3. add the following environment variable in your IDE run configuration window.

    ```sh
    spring.profiles.active=local
    ```
### Installation

To install the application dependencies run the following maven command:

- For local env:
```sh
mvn clean install
```
- For Dev or Prod env:
```sh
mvn clean package -DskipTests
```

To collect the code coverage run the following Maven command:

```sh
mvn clean verify -Dspring.profiles.active=test
```

### Prepare to Dev or Prod Env
1. package the application
```shell
mvn clean package -DskipTests
```
2. build the application image
```shell
docker build -t store-core:2.1 .
```
3. startup the containers with docker-compose.yml file
```shell
docker-compose up
```