# JDBC Practice Projects

A collection of Java console applications built while learning **JDBC (Java Database Connectivity)** with **MySQL**. Each project demonstrates progressively better database practices — from raw `Statement` with string concatenation to parameterised `PreparedStatement` queries.

> **Learning context:** These projects were built to practise real database connectivity, CRUD operations, and SQL query construction in Java — moving from `Statement` → `PreparedStatement` as understanding deepened.

---

## Table of Contents

- [Projects Overview](#projects-overview)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Database Setup](#database-setup)
- [Project 1 — Hotel Reservation System](#project-1--hotel-reservation-system)
- [Project 2 — Student Management System](#project-2--student-management-system)
- [How to Run](#how-to-run)
- [What I Learned](#what-i-learned)
- [Known Issues & Improvements](#known-issues--improvements)

---

## Projects Overview

| Project | Database | JDBC Style | Status |
|---|---|---|---|
| Hotel Reservation System | `hotel_reservation` | `Statement` (raw SQL) | Complete |
| Student Management System | `hotel_reservation` | `PreparedStatement` | Complete |

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 8+ |
| Database | MySQL 8.x |
| Connectivity | JDBC (`mysql-connector-j 9.2.0`) |
| IDE | VS Code with Java Extension Pack |
| Build | Manual `javac` / VS Code build |

---

## Prerequisites

- **Java JDK 8 or higher** — [Download](https://www.oracle.com/java/technologies/downloads/)
- **MySQL 8.x** — [Download](https://dev.mysql.com/downloads/)
- **MySQL Connector/J 9.2.0** JAR — [Download](https://dev.mysql.com/downloads/connector/j/)
- **VS Code** (optional) with the Java Extension Pack

---

## Database Setup

Both projects share the same MySQL database. Run the following SQL before starting either project.

**Step 1 — Create the database**
```sql
CREATE DATABASE hotel_reservation;
USE hotel_reservation;
```

**Step 2 — Hotel Reservation table**
```sql
CREATE TABLE reservation (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)    NOT NULL,
    room_num    INT             NOT NULL,
    contact_num VARCHAR(20)     NOT NULL,
    reser_date  TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);
```

**Step 3 — Student Management table**
```sql
CREATE TABLE student_db (
    id    INT AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(100) NOT NULL,
    grade VARCHAR(10)  NOT NULL
);
```

**Step 4 — Update credentials in source files**

Both files hardcode the DB credentials. Update these two lines to match your local MySQL setup:

```java
// hotelreservation.java  and  studentManagement.java
private static String url      = "jdbc:mysql://localhost:3306/hotel_reservation";
private static String username = "root";
private static String password = "your_mysql_password";
```

---

## Project 1 — Hotel Reservation System

**File:** `hotelReservation/src/hotelreservation.java`

A console application to manage hotel room reservations backed by a live MySQL database.

### Features

| Option | Feature | Description |
|---|---|---|
| 1 | Reserve a room | Insert guest name, room number, and contact into the DB |
| 2 | View reservations | Display all reservations in a formatted table with timestamps |
| 3 | Get room number | Retrieve a room number by reservation ID + guest name |
| 4 | Update reservation | Edit guest name, room number, or contact by reservation ID |
| 5 | Delete reservation | Remove a reservation by ID (existence check before delete) |
| 6 | Exit | Graceful exit with animated countdown |

### How it works — request lifecycle

```
User input (Scanner)
    └── Switch statement routes to handler method
         └── Handler builds SQL string
              └── stmt.executeUpdate() / stmt.executeQuery()
                   └── ResultSet processed and printed to console
```

### Sample output — View Reservations

```
+---------+-----------------+-------------+------------+--------+
|ID       |NAME             | ROOM NUMBER | CONTACT    | DATE   |
+---------+-----------------+-------------+------------+--------+

| 1              | Alice           | 101           | 9876543210           | 2026-03-15 10:22:31   |
| 2              | Bob             | 205           | 9123456780           | 2026-03-16 14:05:10   |
```

### JDBC style used

This project uses a raw `Statement` object with string-concatenated SQL:

```java
String sql = "INSERT INTO reservation (name, room_num, contact_num)" +
             "VALUES ('" + guestName + "', '" + roomnum + "', '" + contact + "')";
stmt.executeUpdate(sql);
```

> **Note:** This approach is vulnerable to SQL Injection. It was intentionally kept as-is to mark the starting point — the Student Management project below shows the correct `PreparedStatement` approach.

---

## Project 2 — Student Management System

**File:** `hotelReservation/src/studentManagement.java`

A console application to manage student records using `PreparedStatement` — the secure, parameterised alternative to raw `Statement`.

### Features

| Option | Feature | Description |
|---|---|---|
| 1 | Add student | Insert a student name and grade |
| 2 | Display all records | List all students from the database |
| 3 | Update student grade | Change a student's grade by their ID |
| 4 | Delete student record | Remove a student by ID |
| 5 | Search grade by ID | Retrieve the grade for a specific student ID |
| 6 | Exit | Graceful exit with animated countdown |

### JDBC style used — PreparedStatement

This project uses parameterised queries, which prevents SQL injection:

```java
String query = "INSERT INTO student_db(name, grade) VALUES(?, ?)";
PreparedStatement ps = con.prepareStatement(query);
ps.setString(1, name);
ps.setString(2, grade);
ps.executeUpdate();
```

### Difference from Project 1

| | Hotel Reservation | Student Management |
|---|---|---|
| JDBC class | `Statement` | `PreparedStatement` |
| SQL construction | String concatenation | Parameterised `?` placeholders |
| SQL injection risk | Yes | No |
| Query reuse | No | Yes — DB can cache the query plan |
| Code style | Procedural | Slightly more structured |

---

## How to Run

### Option A — VS Code

1. Open the `hotelReservation/` folder in VS Code
2. Ensure the MySQL connector JAR is in the `lib/` folder
3. Open `hotelreservation.java` or `studentManagement.java`
4. Click **Run** (the play button above `main`)

### Option B — Terminal

```bash
# Navigate to the project folder
cd hotelReservation

# Compile (adjust the JAR path to match your system)
javac -cp "lib/mysql-connector-j-9.2.0.jar" -d bin src/hotelreservation.java

# Run
java -cp "bin:lib/mysql-connector-j-9.2.0.jar" hotelreservation
```

> On Windows, replace `:` with `;` in the classpath:
> ```bash
> java -cp "bin;lib/mysql-connector-j-9.2.0.jar" hotelreservation
> ```

---

## What I Learned

Working through these two projects in sequence taught:

**JDBC fundamentals**
- Loading the MySQL driver with `Class.forName()`
- Establishing a connection with `DriverManager.getConnection()`
- The difference between `Statement`, `PreparedStatement`, and when to use each
- Reading results with `ResultSet` and iterating with `.next()`
- Understanding `executeUpdate()` vs `executeQuery()`

**SQL in Java**
- Writing and executing `INSERT`, `SELECT`, `UPDATE`, `DELETE` from Java
- Using `AUTO_INCREMENT` primary keys and `TIMESTAMP DEFAULT CURRENT_TIMESTAMP`
- Checking row existence before update/delete to give meaningful error messages

**Security awareness**
- Why string-concatenated SQL (Project 1) is a SQL Injection vulnerability
- How `PreparedStatement` (Project 2) eliminates that vulnerability by separating query structure from user data

---

## Known Issues & Improvements

These are limitations I identified while building — and what the production fix would be:

| Issue | File | Production Fix |
|---|---|---|
| Hardcoded DB credentials in source | Both | Use environment variables or a `.properties` config file — never commit credentials |
| `Statement` with string concatenation | `hotelreservation.java` | Replace with `PreparedStatement` (already done in `studentManagement.java`) |
| `Connection` never closed — resource leak | Both | Use try-with-resources: `try (Connection con = DriverManager.getConnection(...)) {}` |
| `Scanner` created inside the `while` loop every iteration | `hotelreservation.java` | Create `Scanner` once outside the loop |
| Wrong driver class name | `studentManagement.java` | `com.mysql.jdbc.cj.Driver` should be `com.mysql.cj.jdbc.Driver` |
| Empty `catch` block swallows all exceptions silently | `hotelreservation.java` | Always log or rethrow — a silent catch makes debugging impossible |
| No input validation — entering a String when an int is expected crashes | Both | Wrap `sc.nextInt()` in try-catch or use `sc.nextLine()` + `Integer.parseInt()` |
| Passwords stored and compared in plain text | Both | Hash with BCrypt in any real authentication scenario |
| `text.txt` committed to the repo | `hotelReservation/src/` | Planning notes belong in a `NOTES.md` or a private branch — keep source folders clean |

---

## Project Structure

```
jdbc-practice/
├── hotelReservation/
│   ├── src/
│   │   ├── hotelreservation.java      ← Hotel Reservation System (Statement)
│   │   └── studentManagement.java     ← Student Management System (PreparedStatement)
│   ├── lib/
│   │   └── mysql-connector-j-9.2.0.jar
│   ├── bin/                           ← compiled .class files (git-ignored)
│   └── .vscode/
│       └── settings.json
├── README.md
└── .gitignore
```

---

## .gitignore (recommended)

Add this `.gitignore` to avoid committing compiled files and IDE config:

```gitignore
# Compiled output
bin/
*.class

# VS Code
.vscode/

# MySQL connector (download separately — don't commit JARs)
lib/

# Notes / scratch files
*.txt
```

---

## License

This project is open source and available under the [MIT License](LICENSE).
