# ms-library-management

This is a simple library management system (for receptionist) to handle book rentals.

### Below are the assumptions to create the REST service:
 - Receptionist (user) should be able to add new books
 - User should be able to search and find the books
 - User should be able to add member to the library
 - User should be able to get member details (single or all)
 - User should be able to rent a book to a member

### Design decisions
 - H2 database (in mem) for better support from Spring and reduce the dependency on external DB.
 - Model mapper - to convert DTO to entity
 - Lombok - to reduce the boiler plate code

### Tech Stack:
 - Spring boot
 - Spring JPA
 - H2 Database (in mem)

### Build tools:
 - Maven
 - JDK 11
 - Lombok

### Build and Run:

```
mvn clean install && mvn spring-boot:run
```

## Endpoints

Below are the REST endpoints available to utilize the microservice functionality

#### Book Controller
 - Add book
```
POST http://localhost:8080/management/book
```
 - Search book
```
GET http://localhost:8080/management/book?keyword=<keyword>
```
- Get all books
```
GET http://localhost:8080/management/books
```
- Find book by id
```
GET http://localhost:8080/management/book/{id}
```

#### Member Controller
- Add member
```
POST http://localhost:8080/management/member
```
- Search member
```
GET http://localhost:8080/management/member?name=<name>
```
- Get all members
```
GET http://localhost:8080/management/members
```

#### Rental Controller
- Add member
```
POST http://localhost:8080/management/rent-book
```

#### Please note: 
Unit tests are created only at service and controller layer for demo.