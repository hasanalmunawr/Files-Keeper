# Ebook Store project

This repository just for learning

## Features

- **Login/Register**: Support for user login and registration functionalities.
- **Token Management**: Handle OAuth2 tokens securely within your Spring Boot application.
- **User Authentication**: Manage user sessions and permissions based on OAuth2 authentication.
- **Secure Endpoints**: Protect API endpoints and restrict access based on user roles and scopes.
- **Email Validation**: Validate user accounts through email confirmation during the registration process.
- **Role-based Access Control**: Differentiate access and functionalities based on user roles (Admin and User).
    - **Admin Role**: Admin users can create, read, update, and delete (CRUD) eBooks.
    - **User Role**: Regular users can browse, purchase, and download eBooks.

## Getting Started

1. **Clone the repository**:
    ```sh
    git clone https://github.com/hasanalmunawr/Ebook-Store-SpringBoot.git
    ```
2. **Run the application**:
    ```sh
    ./mvnw spring-boot:run
    ```
3. **Open The API Specification**
    ```sh
   http://localhost:8080/swagger-ui/index.html#/
   ```

4. **Access the application**:
   Open your browser and navigate to `http://localhost:8080` to see 

## Technology

- JDK 17 or higher
- Maven
- Spring Boot
- PostgreSQL
- Jwt
- Java Mail Sender
- Paypal 
- Spring Docs

