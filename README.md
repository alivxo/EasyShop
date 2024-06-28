
# E-Commerce API and Site - Capstone Project

## Overview

This project is an E-Commerce API and Site developed as part of the Capstone 3 project. It includes various controllers and DAOs to handle categories, products, shopping cart, profiles, and orders. The backend is built using Java and interacts with a MySQL database.

## Project Structure

- **Controllers**: Handle HTTP requests and map them to service methods.
- **DAOs**: Data Access Objects interact with the database to perform CRUD operations.
- **Models**: Represent the data structures used in the application.

## Setup

### Prerequisites

- Java Development Kit (JDK)
- MySQL database
- Maven
- Spring Framework

### Database Setup

1. Create the database using the `create_database.sql` script.
2. Update the `application.properties` file with your MySQL database credentials.

## API Endpoints
## CategoriesController
- GET /categories - Retrieve all categories.
- GET /categories/{id} - Retrieve a category by ID.
- POST /categories - Create a new category.
- PUT /categories/{id} - Update an existing category.
- DELETE /categories/{id} - Delete a category.
## ProductsController
- GET /products - Retrieve all products.
- GET /products/{id} - Retrieve a product by ID.
- POST /products - Create a new product.
- PUT /products/{id} - Update an existing product.
- DELETE /products/{id} - Delete a product.
## ShoppingCartController
- GET /cart - Retrieve the current user's shopping cart.
- POST /cart/products/{product_id} - Add a product to the cart.
- PUT /cart/products/{product_id} - Update the quantity of a product in the cart.
- DELETE /cart - Clear all products from the cart.
- ProfileController
- GET /profile - Retrieve the current user's profile.
- PUT /profile - Update the user's profile.
## OrdersController
- POST /orders - Create a new order from the current user's shopping cart.

# Issues and Challenges - Postman Access Issue
During the development of this project, I encountered a significant issue that hindered my ability to fully test the API endpoints using Postman. Due to network restrictions and access issues, I was unable to access Postman, which is a critical tool for API testing and debugging.

# Project Outcome
As a result of the Postman access issue, I faced challenges in verifying the functionality and reliability of the API endpoints. This limitation significantly impacted the project's success, as thorough testing is essential for identifying and resolving potential bugs and ensuring the API meets the required specifications.

# Conclusion
Despite the issues faced, the project provided valuable learning experiences in building and structuring an E-Commerce API. Future projects will benefit from these lessons, and I plan to ensure proper access to testing tools to avoid similar issues.
