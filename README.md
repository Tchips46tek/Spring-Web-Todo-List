# Spring Web Todo List

This is a Java application built with the Spring Framework, which allows users to manage a todo list. The frontend of the application is developed using HTML and Thymeleaf, while the backend utilizes the Spring framework and interacts with a MySQL database to store and retrieve data.

## Prerequisites

Before running this application, make sure you have the following prerequisites installed:

- Java Development Kit (JDK) 8 or above
- Apache Maven
- MySQL Server

## Setup

1. Clone the repository to your local machine:
```
git clone https://github.com/your-username/Spring-Web-Todo-List.git
```
2. Create a MySQL database for the application.

3. Update the database connection details in the `application.properties` file located in the `src/main/resources` directory. Modify the following properties:
```
spring.datasource.url=jdbc:mysql://localhost:3306/todo_db
spring.datasource.username=root
spring.datasource.password=your_password
```
Replace `your_password` with the password for your MySQL database.

4. Build the application using Maven. Open a terminal or command prompt, navigate to the project's root directory, and run the following command:

5. Run the application:
```
mvn spring-boot:run
```
The application will start running on `http://localhost:8080`.

6. Open your web browser and go to `http://localhost:8080` to access the todo list application.

## Usage

- To add a new todo item, enter the task title and description in the input field and click the "Add" button.
- To change a todo item, click the "Edit" button next to it.
- To delete a todo item, click the "Delete" button next to it.

## Acknowledgements

This application was built using the Spring Framework and Thymeleaf. Special thanks to the open-source community for their valuable contributions.

## Contact

If you have any questions or need further assistance, please email [leo.outmizguine@epitech.eu](mailto:leo.outmizguine@epitech.eu).
