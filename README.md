
E-Commerce API and Site - Capstone Project
Overview
This project is an E-Commerce API and website developed for Capstone 3. It comprises various controllers and DAOs to manage categories, products, shopping carts, profiles, and orders. The backend is implemented in Java and connects to a MySQL database.

Project Structure
Controllers: Manage HTTP requests and route them to the appropriate service methods.
DAOs: Data Access Objects that perform CRUD operations on the database.
Models: Define the data structures used within the application.
Setup
Prerequisites
Java Development Kit (JDK)
MySQL database
Maven
Spring Framework
Database Setup
Execute the create_database.sql script to create the database.
Configure your MySQL database credentials in the application.properties file.


API Endpoints
CategoriesController
GET /categories - Retrieve all categories.
GET /categories/{id} - Retrieve a category by ID.
POST /categories - Create a new category.
PUT /categories/{id} - Update an existing category.
DELETE /categories/{id} - Delete a category.
ProductsController
GET /products - Retrieve all products.
GET /products/{id} - Retrieve a product by ID.
POST /products - Create a new product.
PUT /products/{id} - Update an existing product.
DELETE /products/{id} - Delete a product.
ShoppingCartController
GET /cart - Retrieve the current user's shopping cart.
POST /cart/products/{product_id} - Add a product to the cart.
PUT /cart/products/{product_id} - Update the quantity of a product in the cart.
DELETE /cart - Clear all products from the cart.
ProfileController
GET /profile - Retrieve the current user's profile.
PUT /profile - Update the user's profile.
OrdersController
POST /orders - Create a new order from the current user's shopping cart.
Issues and Challenges
Postman Access Issue
During the development of this project, I encountered a significant issue that hindered my ability to fully test the API endpoints using Postman. Due to network restrictions and access issues, I was unable to access Postman, which is a critical tool for API testing and debugging.

Project Outcome
As a result of the Postman access issue, I faced challenges in verifying the functionality and reliability of the API endpoints. This limitation significantly impacted the project's success, as thorough testing is essential for identifying and resolving potential bugs and ensuring the API meets the required specifications.

Conclusion
Despite the issues faced, the project provided valuable learning experiences in building and structuring an E-Commerce API. Future projects will benefit from these lessons, and I plan to ensure proper access to testing tools to avoid similar issues.
