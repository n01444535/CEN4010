package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/ExpenseTracker";
    private static final String USER = "DavidMac";
    private static final String PASSWORD = "THULE.2710";

    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return null;
        }
    }

    public static void initialize() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            String createUsersTable = """
                CREATE TABLE IF NOT EXISTS Users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    first_name VARCHAR(50),
                    last_name VARCHAR(50),
                    username VARCHAR(50) UNIQUE,
                    email VARCHAR(100) UNIQUE,
                    phone VARCHAR(20),
                    password_hash VARCHAR(255)
                );
                """;
            stmt.execute(createUsersTable);

            String createExpensesTable = """
                CREATE TABLE IF NOT EXISTS Expenses (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT,
                    category VARCHAR(50),
                    amount DOUBLE,
                    note TEXT,
                    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
                );
                """;
            stmt.execute(createExpensesTable);

            System.out.println("Database initialized.");
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }
}