# Auction Java Swing Application

This is a Java Swing application for managing auctions, users, items, and bids, with MySQL as the backend database.

## Prerequisites

- **Java JDK** (version 8 or higher)
- **MySQL Server** running locally
- **MySQL JDBC Driver** (`mysql-connector-j-9.0.0.jar`)
- **VS Code** (optional, for editing)

## Setup Instructions

### 1. Database Setup

1. Start your MySQL server.
2. Create a database named `auctiondb`.
3. Create the required tables (`Users`, `Items`, `Bids`) as per your application's needs.

### 2. MySQL JDBC Driver

- Download the MySQL JDBC driver (`mysql-connector-j-9.0.0.jar`) from [MySQL Downloads](https://dev.mysql.com/downloads/connector/j/).
- Place the JAR file at `C:\mysql-connector-j-9.0.0.jar` (or update the classpath in commands below if you use a different location).

### 3. Compile the Application

Open a terminal in the project directory and run:

```sh
javac -cp "C:\mysql-connector-j-9.0.0.jar" *.java
```

### 4. Run the Application

Run the main class:

```sh
java -cp ".;C:\mysql-connector-j-9.0.0.jar" main
```

> **Note:**  
> Replace `main` with the actual main class name if it is different.

### 5. Cleaning Up

To remove compiled files, run:

```sh
del *.class
```

## Additional Notes

- `.class` files (including those with `$` in the name) are generated automatically by the Java compiler for inner and anonymous classes.
- The `.gitignore` file is set to ignore all `.class` files.

## Troubleshooting

- Ensure your MySQL server is running and accessible.
- Check your database credentials in the code.
- Make sure the JDBC driver path is correct.

---