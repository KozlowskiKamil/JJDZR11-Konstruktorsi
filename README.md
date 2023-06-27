# Library
![Logo](https://github.com/infoshareacademy/JJDZR11-Konstruktorsi/blob/KozlowskiKamil-patch-1/src/main/resources/static/images/13.png)

The Library project is a web application for managing a book collection and users in a library. It allows for adding, editing, and deleting books, as well as registering and managing users.

## Table of Contents
- [Requirements](#requirements)
- [Installation Instructions](#installation-instructions)
- [Docker Setup](#docker-setup)
- [Usage](#usage)
- [Configuration](#configuration)
- [Author](#author)

## Requirements

To run the project, you need to have the following installed:
- Java 8 or higher
- Maven
- MySQL database

## Installation Instructions

1. Clone the repository to your device:
git clone <repository_link>


2. Navigate to the project directory:
cd library


3. Run the application using Maven:
mvn spring-boot:run


4. The application will be accessible at: `http://localhost:8080`

## Docker Setup

To run the application using Docker, follow these steps:
1. Pull the latest MySQL Docker image:
```
   docker pull mysql:latest
   ```
   2. Run the MySQL container with the following command:

   ```
docker run -d -p 3308:3306 --restart always --name mysql-container -e MYSQL_ROOT_PASSWORD=book -e MYSQL_DATABASE=library mysql:latest

   ```
   Note: The host URL is still `localhost` because the container is mapped to the host's port `3308`.



## Usage



After installing and running the application, you can use the following features:

- Adding books to the library
- Editing book information
- Deleting books from the library
- Registering new users
- Viewing the list of users
- Managing user accounts
- Browsing borrowed books

## Configuration

The application uses MySQL as the database. The default database connection settings are as follows:

```properties
spring.datasource.url=jdbc:mysql://localhost:3308/library
spring.datasource.username=root
spring.datasource.password=book

If you have different database credentials or configuration, you can modify the application.properties file accordingly.
```
## Author
The Library project was created by [JJDZR11-Konstruktorsi](https://github.com/infoshareacademy/JJDZR11-Konstruktorsi)

- [@kingabiereg](https://github.com/kingabiereg)
- [@MMalinowski19](https://github.com/MMalinowski19)
- [@Wenderholm](https://github.com/Wenderholm)
- [@KozlowskiKamil](https://github.com/KozlowskiKamil)


