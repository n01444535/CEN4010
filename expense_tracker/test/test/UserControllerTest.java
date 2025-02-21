package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import controller.UserController;
import database.DatabaseManager;

class UserControllerTest {

    @BeforeAll
    static void setup() {
        DatabaseManager.initialize(); // Ensure database is ready
    }

    @Test
    void testUserAuthentication_Success() {
        assertTrue(UserController.authenticateUser("validUser", "validPassword"), "❌ User should authenticate successfully!");
    }

    @Test
    void testUserAuthentication_Fail() {
        assertFalse(UserController.authenticateUser("invalidUser", "wrongPassword"), "✅ Expected failure for invalid credentials.");
    }
}