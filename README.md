# BookShop

BookShop is an online store that allows users to browse and purchase books. It supports book categories, user roles, shopping carts, and efficient order management.

## Project Description

The BookShop app includes the following domain models:
- **User**: Contains information about registered users, including authentication and personal details.
- **Role**: Represents user roles such as admin or user.
- **Book**: Represents books available in the store.
- **Category**: Represents categories for books.
- **ShoppingCart**: Represents a user's shopping cart.
- **CartItem**: Represents an item in a user's shopping cart.
- **Order**: Represents orders placed by users.
- **OrderItem**: Represents an item in a user's order.

## Technologies Used

### Spring Boot
Spring Boot was used to create a stand-alone, production-grade Spring-based application with minimal configuration. It facilitated rapid development and deployment.

### Spring Security
Spring Security was implemented to provide robust authentication and authorization mechanisms. It ensured that only authorized users could access certain endpoints, and it handled login and registration processes securely.

### Spring Data JPA
Spring Data JPA was utilized for data access and management. It simplified the implementation of data repositories by providing default methods for common database operations.

### Swagger
Swagger was integrated for API documentation. It allowed developers to interact with the API endpoints through a web interface and provided clear documentation for each available endpoint.

### MapStruct
MapStruct was used to generate type-safe mappers for transferring data between the entity and DTO (Data Transfer Object) layers. It reduced boilerplate code and improved readability.

### Liquibase
Liquibase was employed for database version control and schema migration. It ensured that the database schema could be managed and updated reliably across different environments.

### JWT (JSON Web Tokens)
JWT was implemented for secure token-based authentication. It allowed users to authenticate and receive a token that could be used to access protected endpoints.

### MySQL
MySQL served as the relational database for storing and managing application data. It provided reliable and efficient data storage and retrieval.

### Docker
Docker was used to containerize the application, ensuring consistency across different environments and simplifying deployment.


## Functionalities

### User Functionalities
- **Authentication**: Register and log in.
- **Browse Books**: View and search for books.
- **Shopping Cart**: Add, view, and remove items.
- **Order Management**: Place orders and view order history.

### Admin Functionalities
- **Book Management**: Add, update, and delete books.
- **Category Management**: Manage book categories.
- **Order Management**: Update order statuses.

## Setup Instructions

### Prerequisites
- **Docker**
- **Docker Compose**

### Installation Steps

1. **Clone the repository:**
    ```bash
    git clone git@github.com:HAIOVYI/book-shop.git
    cd book-shop
    ```

2. **Create a `.env` file in the root directory:**
    ```env
    MYSQL_DATABASE=book
    MYSQL_LOCAL_PORT=3308
    MYSQL_DOCKER_PORT=3306
    
    SPRING_LOCAL_PORT=8088
    SPRING_DOCKER_PORT=8080
    DEBUG_PORT=5005
    
    SECRET_STRING=palkakapalkakrestikinolikijwttoken
    JWT_EXPIRATION=300000
    ```

3. **Run the application using Docker Compose:**
    ```bash
    docker-compose up --build
    ```

### Testing

- **Run tests:**
    ```bash
    mvn test
    ```

## Challenges Faced
- **Database migrations**: Implemented using Liquibase for smooth schema updates.
- **Security**: Integrated Spring Security and JWT for robust authentication and authorization.


## API Documentation

You can access the full API documentation at:

### [Swagger UI](http://localhost:8088/swagger-ui/index.html)

### User Registration
```http
POST http://localhost:8088/api/auth/registration
Content-Type: application/json

{
  "email": "some@example.com",
  "password": "12345678",
  "confirmPassword": "12345678",
  "firstName": "Pablo",
  "lastName": "Dine",
  "shippingAddress": "Kyiv"
}
```
### User Login
```http
POST http://localhost:8088/api/auth/login
Content-Type: application/json

{
  "email": "some@example.com",
  "password": "12345678"
}
```
### User Login Response Example
```http
HTTP/1.1 200 OK
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 06 Jun 2024 13:01:53 GMT

{
"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzb21lQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE3Njc4OTEzLCJleHAiOjE3MTc2NzkyMTN9.woqxVS_r-Uc0uue5n-niTD0ViPS_SRXopeTxqGwnIl4"
}
```
### Add Book to Shopping Cart
```http
POST http://localhost:8088/api/cart
Authorization: Bearer YOUR_JWT
Content-Type: application/json

{
  "bookId": 3,
  "quantity": 1
}
```
### Place an Order
```http
POST http://localhost:8088/api/orders
Authorization: Bearer YOUR_JWT
Content-Type: application/json

{
  "shippingAddress": "Kyiv Boris Gmiri 21"
}
```
## Admin Operations
Use the following credentials to login as an admin:

- Email: **admin@god.com**
- Password: **12345678**
### Create a New Book
```http
POST http://localhost:8088/api/books
Authorization: Bearer YOUR_JWT (admin token)
Content-Type: application/json

{
  "title": "New Book Title",
  "author": "Author Name",
  "isbn": "9783161484100",
  "price": 19.99,
  "description": "Book description",
  "coverImage": "http://example.com/cover.jpg",
  "categoryIds": [1, 2, 3]
}
```
### Update Book Information
```http
POST http://localhost:8088/api/books
Authorization: Bearer YOUR_JWT (admin token)
Content-Type: application/json

{
  "title": "Updated Book Title",
  "author": "Updated Author Name",
  "isbn": "9783161484101",
  "price": 29.99,
  "description": "Updated book description",
  "coverImage": "http://example.com/updated-cover.jpg",
  "categoryIds": [1, 2]
}
```
### Delete a Book
```http
DELETE http://localhost:8088/api/books/{id}
Authorization: Bearer YOUR_JWT (admin token)
```
### Update Order status
Statuses: NEW, PENDING, DELIVERED, COMPLETED, CANCELLED
```http
PATCH http://localhost:8088/api/orders/{id}
Authorization: Bearer YOUR_JWT (admin token)
Content-Type: application/json

{
  "status": "PENDING"
}
```
## Contact
[![Linkedin](https://i.sstatic.net/gVE0j.png) LinkedIn](https://www.linkedin.com/in/oleksii-haiovyi-28967b303/)
&nbsp;
[![GitHub](https://i.sstatic.net/tskMh.png) GitHub](https://github.com/HAIOVYI)
