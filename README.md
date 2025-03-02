# JDBC Practice Projects

This repository contains various Java projects developed using **JDBC (Java Database Connectivity)** to practice and demonstrate database interactions with **MySQL**. These projects serve as practice exercises for connecting Java applications with a MySQL database, performing CRUD (Create, Read, Update, Delete) operations, and other database manipulations.

### Projects Included:

1. **Student Management System**
2. **Hotel Reservation System**
3. **Banking System**

Each project includes its own set of functionalities designed to manage data, with the core focus being interaction with MySQL databases via JDBC.

---

## 1. Student Management System

### Overview
The **Student Management System** allows users to manage student records in a database. The system supports operations like adding, updating, deleting, and displaying student records, as well as searching for a student by their ID and displaying their grade.

### Features:
- **Add a new student**: Insert a student's name and grade.
- **Display all student records**: View all stored student records.
- **Update student details**: Modify student grades by ID.
- **Delete student record**: Remove student records by their ID.
- **Search student grade**: Retrieve and display the grade of a student by ID.

### Step 2: Create Project-Specific Tables

Create the following table in MySQL to store student data:

```sql
CREATE TABLE student_db (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    grade VARCHAR(10)
);
