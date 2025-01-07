## About Project

The project is written in Java using Spring Boot

### Running the app on your local machine
This project runs on java 17 and assumes you have maven and docker installed on your system;

1: To spin up a postgres db instance use this command `docker compose up -d` from the project root folder after the database starts up

2: Also project root, use `./mvnw spring-boot:run` or `mvn spring-boot:run` to start the application. The app binds to port `8080` by default.
To change port edit `resources/application.properties`.

To test the endpoints, you can use the version 2.1 postman collection I provided in the root folder of the project named
`Blusalt The Drone Assesment.postman_collection.json` or navigate to`http://localhost:8080/swagger-ui/index.html`
to use the pleasing and intuitive swagger ui interface I provided to interact with the endpoints.

### Testing

**Automated Tests** Unit tests are provided for `MedicationServiceImpl.java` and `DroneServiceImpl.java`. To run all these tests,
execute the following command at the project root: `./mvnw test` or `mvn test`.

### Tech Stack

- JDK 17
- Spring Boot
- Postgres DB
