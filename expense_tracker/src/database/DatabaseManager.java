package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/ExpenseTracker";
    private static final String USER = "DavidMac";
    private static final String PASSWORD = "THULE.2710";

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

            if (!tableExists(conn, "USERS")) {
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
                System.out.println("✅ Table 'Users' created.");
            } else {
                System.out.println("🔹 Table 'Users' already exists.");
            }

            if (!tableExists(conn, "EXPENSES")) {
                String createExpensesTable = """
                    CREATE TABLE Expenses (
                        id NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                        user_id NUMBER,
                        category VARCHAR2(50),
                        amount NUMBER(10,2),
                        note CLOB,
                        date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
                    )
                """;
                stmt.execute(createExpensesTable);
                System.out.println("✅ Table 'Expenses' created.");
            } else {
                System.out.println("🔹 Table 'Expenses' already exists.");
            }

            System.out.println("✅ Database initialized.");
        } catch (SQLException e) {
            System.out.println("❌ Error initializing database: " + e.getMessage());
        }
    }

    private static boolean tableExists(Connection conn, String tableName) {
        try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName.toUpperCase(), null)) {
            return rs.next();
        } catch (SQLException e) {
            System.out.println("❌ Error checking table existence: " + e.getMessage());
            return false;
        }
    }
}