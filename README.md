# EmpDB
# Employee Management System

The Employee Management System is a Java-based application built with Swing. It provides a user-friendly interface for managing employee information in a database. With this application, you can perform CRUD (Create, Read, Update, Delete) operations on employee records, making it easy to track and manage employee data.

# Features

Add new employees with details such as name, gender, phone number, email, designation, and salary.
Retrieve employee information by searching for their name.
Update employee details including name, gender, phone number, email, designation, and salary.
Delete employee records by specifying their unique ID.

# Technologies Used

> Java
> Swing (Graphical User Interface)
> PostgreSQL (Database)

# Getting Started

Prerequisites:
> Java Development Kit (JDK) installed
> Apache Maven installed
> PostgreSQL database server

# Installation

1. Clone the repository:
git clone [https://github.com//employee-management-system.git](https://github.com/r45hm33t/EmpDB.git)

2. Navigate to the project directory:
cd employee-management-system

3. Configure the database connection:

i) Open the src/main/resources/application.properties file.
ii) Modify the database connection properties to match your PostgreSQL database configuration.

4. Build and run the application:

mvn package
java -jar target/employee-management-system.jar

# Usage

1. Launch the application by running the JAR file or executing the main class.
2. Fill in the employee details in the provided fields.
3. Click the "Submit" button to add a new employee to the database.
4. Use the "Retrieve" button to search for an employee by their name and view their information.
5. To update an employee's details, enter the ID and new information in the respective fields and click the "Update" button.
6. To delete an employee, enter their ID and click the "Delete" button.

# Contributing

Contributions are welcome! If you have any ideas, improvements, or bug fixes, please open an issue or submit a pull request.

# License

This project is licensed under the MIT License.

# Contact

If you have any questions or suggestions, feel free to contact at kaurrashmeet680@gmail.com.
