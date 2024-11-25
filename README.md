# E-commerce Application

This project is a simplified e-commerce application built with Spring Boot and MongoDB. It features user authentication and authorization using JWT, and follows the DTO pattern for data transfer. The application allows users to browse products, add items to their cart, place orders, and manage their orders.

## Features

- **Product Management**: View a list of available products.
- **User Authentication**: Log in to add items to the cart and place orders.
- **Cart Management**: Add products to the cart, view cart items, and remove items from the cart.
- **Order Management**: Place orders from the cart and view order history.
- **Security**: JWT-based authentication and authorization.

## Technologies Used

- **Spring Boot**: Backend RESTful API.
- **MongoDB**: Database for storing product, user, and order data.
- **Spring Security**: User authentication and authorization.
- **JWT**: JSON Web Tokens for secure authentication.
- **DTO Pattern**: Data Transfer Objects for efficient data transfer.
- **Deployment**: Heroku

### Authentication

- **POST /login**: Authenticate a user and generate a JWT token.  
  **Request Body**:  
  ```json
  {
    "username": "user1",
    "password": "password"
  }

## Endpoints
### Products
GET /products: Retrieve all available products.
POST /products: Add a new product.
GET /products/{id}: Retrieve a product by its ID.
PUT /products/{id}: Update a product by its ID.
DELETE /products/{id}: Delete a product by its ID.

### Cart
POST /cart: Add an item to the user's cart.
GET /cart: Retrieve the current user's cart items.

### Orders
POST /orders: Create a new order from the items in the user's cart.
GET /orders: Retrieve all orders for the current user.
GET /orders/{id}: Retrieve an order by its ID.
DELETE /orders/{id}: Delete an order by its ID.
GET /orders/all: Retrieve all orders (admin access).

## Security

The application uses **JWT** for authentication and authorization. The `AuthenticationFilter` class intercepts requests, validates the JWT token, and sets the authentication in the `SecurityContextHolder`. This ensures that the user is authenticated before accessing protected resources. JWT is used to securely transmit user identity and authorization information between the client and the server.

## DTO Pattern

The application follows the **DTO (Data Transfer Object)** pattern to transfer data between the client and server. DTOs are simple objects that encapsulate data, and they are used to prevent exposing the internal structure of the domain entities. This approach ensures a clean separation between the entity models and the data that is sent to and received from the client, making the application more maintainable and secure.
