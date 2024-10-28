
# ClothingStore Backend

This is the backend repository for the ClothingStore project, a RESTful API built using Java Spring Boot. The API handles all the core functionalities required for managing the clothing store, including product catalog management, user authentication, shopping cart, and order processing.

## Features

- **Product Management**: Create, read, update, and delete products in the catalog.
- **User Authentication**: Register, login, and manage user sessions with JWT.
- **Shopping Cart**: Add, view, and manage items in the user's cart.
- **Order Processing**: Place and view orders.
- **Admin Dashboard**: Restricted functionalities for managing the store's data and settings.

## Technologies Used

- **Java 21**
- **Spring Boot 6**
- **AWS MySQL** - Relational database for data storage
- **Spring Security** - For authentication and authorization
- **JWT (JSON Web Tokens)** - Secure token-based authentication
- **Maven** - For dependency management and build automation

## Getting Started

### Prerequisites

- Java 21
- Maven
- AWS MySQL

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/Claudiu1503/ClothingStore---Backend.git
   cd ClothingStore---Backend
   ```

2. Configure MySQL:
   - Create a MySQL database named `clothing_store`.
   - Update `application.properties` in `src/main/resources` with your MySQL database credentials.

3. Build and run the application:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

### API Endpoints

| HTTP Method | Endpoint                   | Description                            |
|-------------|----------------------------|----------------------------------------|
| `POST`      | `/api/auth/register`       | Register a new user                   |
| `POST`      | `/api/auth/login`          | Login user and obtain JWT             |
| `GET`       | `/api/products`            | Retrieve all products                 |
| `POST`      | `/api/products`            | Create a new product (Admin only)     |
| `PUT`       | `/api/products/{id}`       | Update product details (Admin only)   |
| `DELETE`    | `/api/products/{id}`       | Delete a product (Admin only)         |
| `POST`      | `/api/cart/add`            | Add item to the user's cart           |
| `GET`       | `/api/orders`              | Retrieve user's order history         |
| `POST`      | `/api/orders/place`        | Place a new order                     |

### Admin Access

Admin privileges are required for certain API endpoints. To assign admin roles, ensure that the user’s role is set to `ROLE_ADMIN` in the database.

## Project Structure

- `src/main/java/com/clothingstore` - Core application code
- `src/main/resources` - Configuration files
- `src/test/java/com/clothingstore` - Test cases

## Contributions

Contributions are welcome! Please create a pull request for any proposed changes or feature additions.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
