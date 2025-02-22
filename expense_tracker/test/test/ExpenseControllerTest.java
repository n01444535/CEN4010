package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import controller.ExpenseController;
import database.DatabaseManager;
import model.Expense;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExpenseControllerTest {

    private static String testEmail;
    private static int testExpenseId;

    @BeforeEach
    void setup() {
        testEmail = UUID.randomUUID().toString().substring(0, 8) + "@test.com";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO Users (first_name, last_name, username, email, phone, password_hash) VALUES (?, ?, ?, ?, ?, ?)"
             )) {
            stmt.setString(1, "Test");
            stmt.setString(2, "User");
            stmt.setString(3, "testuser");
            stmt.setString(4, testEmail);
            stmt.setString(5, "123456789");
            stmt.setString(6, "testpassword");
            stmt.executeUpdate();
        } catch (SQLException e) {
            fail("❌ Setup failed: " + e.getMessage());
        }

        boolean added = ExpenseController.addExpense(testEmail, "Food", 15.99, "Lunch at restaurant");
        assertTrue(added, "❌ Failed to add test expense.");

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id FROM Expenses WHERE user_id = (SELECT id FROM Users WHERE email = ?) ORDER BY id DESC LIMIT 1"
             )) {
            stmt.setString(1, testEmail);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                testExpenseId = rs.getInt("id");
            } else {
                fail("❌ Failed to retrieve inserted expense.");
            }
        } catch (SQLException e) {
            fail("❌ Database error: " + e.getMessage());
        }
    }

    @Test
    @Order(1)
    void testGetExpensesByUser() {
        List<Expense> expenses = ExpenseController.getExpensesByUser(testEmail);
        assertNotNull(expenses, "❌ Expenses list should not be null.");
        assertFalse(expenses.isEmpty(), "❌ Expenses list should not be empty.");
    }

    @Test
    @Order(2)
    void testUpdateExpense_Success() {
        assumeTrue(testExpenseId > 0, "❌ No expense found to update.");
        boolean updated = ExpenseController.updateExpense(testExpenseId, "Groceries", 20.50, "Bought fruits");
        assertTrue(updated, "❌ Expense update failed.");

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT category, amount, note FROM Expenses WHERE id = ?"
             )) {
            stmt.setInt(1, testExpenseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                assertEquals("Groceries", rs.getString("category"), "❌ Category update failed.");
                assertEquals(20.50, rs.getDouble("amount"), "❌ Amount update failed.");
                assertEquals("Bought fruits", rs.getString("note"), "❌ Note update failed.");
            } else {
                fail("❌ Updated expense not found.");
            }
        } catch (SQLException e) {
            fail("❌ Database verification error: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    void testDeleteExpenseByCategory() {
        boolean deleted = ExpenseController.deleteExpenseByCategory(testEmail, "Groceries");
        assertTrue(deleted, "❌ Expense deletion by category failed.");

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT COUNT(*) FROM Expenses WHERE user_id = (SELECT id FROM Users WHERE email = ?) AND category = 'Groceries'"
             )) {
            stmt.setString(1, testEmail);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                assertEquals(0, rs.getInt(1), "❌ Expense was not deleted properly.");
            }
        } catch (SQLException e) {
            fail("❌ Database verification error: " + e.getMessage());
        }
    }

    @AfterEach
    void cleanup() {
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE email = ?")) {
            stmt.setString(1, testEmail);
            stmt.executeUpdate();
        } catch (SQLException e) {
            fail("❌ Cleanup failed: " + e.getMessage());
        }
    }
}