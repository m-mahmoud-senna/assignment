# Technical Assignment

<b>Introduction</b>

IngenicoAssignment project is implementing RESTful APIs to perform the following operations<br>

1- Create Account<br>
2- Get all accounts<br>
3- Find account by account number<br>
4- Transfer amount from account to account<br>

<b>Main Classes</b>

1- IngenicoAssignmentApplication -> Spring Boot Application Initializer <br>
2- BankingController - > API gateway for the application which exposes RESTful interface (JSON/HTTP)<br>
3- TransferService/TransferServiceImpl -> Business service layer<br>
4- DataRepository -> JPA data layer for saving accounts<br>
5- Account -> Data Model for account<br>
6- Transfer -> Data Model for transfer request<br>
7- BankingControllerTest -> Test Cases for BankingController<br>


<b>Technology Used </b>

1- Java 8 <br>
2- Spring Boot 2 <br>
3- JPA/Hibernate <br>
4- Maven <br>
5- Swagger 2 <br>
6- Junit/Hamcrest/spring mock MVC <br>

<b>Limitation</b>

Application uses in-memory H2 database, so all the data will be lost after restart <br>

<b>Testing Application</b>

1- git clone https://github.com/m-mahmoud-senna/assignment <br>
2- cd assignment <br>
3- cd IngenicoAssignment <br>
4- mvn spring-boot:run <br>

The application will be started and the APIs could be access using<br>
http://localhost:8080/ingenico-assignment/*

To check and test API, use , use <br>
http://localhost:8080/ingenico-assignment/swagger-ui.html
