package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import controller.UserController;
import database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    private static String randomUsername;
    private static String randomPassword;
    private static String randomEmail;

    @BeforeEach
    void setup() {
        DatabaseManager.initialize(); // Ensure database is ready

        // Generate completely random test user
        randomUsername = "testUser_" + UUID.randomUUID().toString().substring(0, 6);
        randomPassword = "pass_" + UUID.randomUUID().toString().substring(0, 6);
        randomEmail = randomUsername + "@test.com";

        System.out.println("🔹 Creating test user: " + randomUsername);

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO USERS (first_name, last_name, username, email, phone, password_hash) VALUES (?, ?, ?, ?, ?, ?)"
             )) {
            stmt.setString(1, "Random");
            stmt.setString(2, "User");
            stmt.setString(3, randomUsername);
            stmt.setString(4, randomEmail);
            stmt.setString(5, "123456789");
            stmt.setString(6, randomPassword);
            stmt.executeUpdate();
        } catch (SQLException e) {
            fail("❌ Setup failed: Could not insert test user - " + e.getMessage());
        }
    }

    @Test
    @Order(1)
    void testUserAuthentication_Success() {
        assertTrue(UserController.authenticateUser(randomUsername, randomPassword),
                "❌ User should authenticate successfully! (Username: " + randomUsername + ")");
    }

    @Test
    @Order(2)
    void testUserAuthentication_Fail() {
        assertFalse(UserController.authenticateUser("invalidUser_" + UUID.randomUUID(), "wrongPassword"),
                "✅ Expected failure for invalid credentials.");
    }

    @AfterEach
    void cleanup() {
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM USERS WHERE username = ?")) {
            stmt.setString(1, randomUsername);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Test user deleted: " + randomUsername);
            }
        } catch (SQLException e) {
            fail("❌ Cleanup failed: " + e.getMessage());
        }
    }
}