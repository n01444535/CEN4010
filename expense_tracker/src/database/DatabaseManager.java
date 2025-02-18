package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl";
    private static final String USER = "G27";
    private static final String PASSWORD = "ahjYW2h3";

    public static Connection connect() {
        try {
            System.out.println("🔍Connecting to Oracle DB...");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected successfully!");
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Oracle JDBC Driver not found: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
            return null;
        }
    }

    public static void initialize() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            String createUsersTable = """
                CREATE TABLE Users (
                    id NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                    first_name VARCHAR2(50),
                    last_name VARCHAR2(50),
                    username VARCHAR2(50) UNIQUE,
                    email VARCHAR2(100) UNIQUE,
                    phone VARCHAR2(20),
                    password_hash VARCHAR2(255)
                )
            """;
            stmt.execute(createUsersTable);

            String createExpensesTable = """
                CREATE TABLE Expenses (
                    id NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                    user_id NUMBER,
                    category VARCHAR2(50),
                    amount NUMBER,
                    note CLOB,
                    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
                )
            """;
            stmt.execute(createExpensesTable);

            System.out.println("✅ Database initialized.");
        } catch (SQLException e) {
            System.out.println("❌ Error initializing database: " + e.getMessage());
        }
    }
}