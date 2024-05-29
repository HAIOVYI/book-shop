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

- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **Swagger**
- **MapStruct**
- **Liquibase**
- **JJWT**
- **MySQL**
- **Docker**

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
    ```plaintext
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
