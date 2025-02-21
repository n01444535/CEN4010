package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import database.DatabaseManager;

class DatabaseManagerTest {
    private static Connection conn;

    @BeforeAll
    static void setup() {
        conn = DatabaseManager.connect();
        assertNotNull(conn, "❌ Connection failed!");
    }

    @Test
    void testInitialize() {
        assertDoesNotThrow(() -> DatabaseManager.initialize(), "❌ Database initialization failed!");
    }

    @AfterAll
    static void tearDown() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            fail("❌ Failed to close connection: " + e.getMessage());
        }
    }
}