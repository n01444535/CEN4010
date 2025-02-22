package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import controller.ExpenseController;
import database.DatabaseManager;
import model.Expense;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExpenseControllerTest {

    private static String testEmail;
    private static String testUsername;
    private static int testExpenseId;
    private static String testCategory;
    private static double testAmount;

    @BeforeEach
    void setup() {
        Random random = new Random();
        testEmail = UUID.randomUUID().toString().substring(0, 8) + "@test.com";
        testUsername = "user_" + UUID.randomUUID().toString().substring(0, 5);
        testCategory = new String[]{"Food", "Transport", "Shopping", "Entertainment"}[random.nextInt(4)];
        testAmount = 10 + (90 * random.nextDouble()); 

        System.out.println("🔹 Test Email: " + testEmail);
        System.out.println("🔹 Test Username: " + testUsername);
        System.out.println("🔹 Test Category: " + testCategory);
        System.out.println("🔹 Test Amount: $" + testAmount);

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO USERS (FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PHONE, PASSWORD_HASH) VALUES (?, ?, ?, ?, ?, ?)"
             )) {
            stmt.setString(1, "Test");
            stmt.setString(2, "User");
            stmt.setString(3, testUsername);
            stmt.setString(4, testEmail);
            stmt.setString(5, "123456789");
            stmt.setString(6, "testpassword");
            stmt.executeUpdate();
        } catch (SQLException e) {
            fail("❌ Setup failed (USER creation): " + e.getMessage());
        }

        boolean added = ExpenseController.addExpense(testEmail, testCategory, testAmount, "Random test expense");
        assertTrue(added, "❌ Failed to add test expense.");

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT ID FROM EXPENSES WHERE USER_ID = (SELECT ID FROM USERS WHERE EMAIL = ?) AND ROWNUM = 1"
             )) {
            stmt.setString(1, testEmail);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                testExpenseId = rs.getInt("ID");
                System.out.println("✅ Retrieved Expense ID: " + testExpenseId);
            } else {
                fail("❌ Failed to retrieve inserted expense.");
            }
        } catch (SQLException e) {
            fail("❌ Database error while retrieving Expense ID: " + e.getMessage());
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
        
        double newAmount = 10 + (90 * new Random().nextDouble());

        boolean updated = ExpenseController.updateExpense(testExpenseId, "Updated_" + testCategory, newAmount, "Updated test note");
        assertTrue(updated, "❌ Expense update failed.");

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT CATEGORY, AMOUNT, NOTE FROM EXPENSES WHERE ID = ?"
             )) {
            stmt.setInt(1, testExpenseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                assertEquals("Updated_" + testCategory, rs.getString("CATEGORY"), "❌ Category update failed.");
                assertEquals(newAmount, rs.getDouble("AMOUNT"), 0.01, "❌ Amount update failed.");
                assertEquals("Updated test note", rs.getString("NOTE"), "❌ Note update failed.");
            } else {
                fail("❌ Updated expense not found.");
            }
        } catch (SQLException e) {
            fail("❌ Database verification error: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    void testDeleteExpenseById() {
        assumeTrue(testExpenseId > 0, "❌ No expense found to delete.");
        boolean deleted = ExpenseController.deleteExpenseById(testExpenseId);
        assertTrue(deleted, "❌ Expense deletion failed.");

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT COUNT(*) FROM EXPENSES WHERE ID = ?"
             )) {
            stmt.setInt(1, testExpenseId);
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
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM USERS WHERE EMAIL = ?")) {
            stmt.setString(1, testEmail);
            stmt.executeUpdate();
        } catch (SQLException e) {
            fail("❌ Cleanup failed: " + e.getMessage());
        }
    }
}