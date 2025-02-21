package test;

import static org.junit.jupiter.api.Assertions.*;

import model.Expense;
import database.DatabaseManager;
import org.junit.jupiter.api.*;

import controller.ExpenseController;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExpenseControllerTest {
    private static final String TEST_EMAIL = "test@example.com";
    private static int insertedExpenseId;

    @BeforeAll
    static void setupDatabase() {
        DatabaseManager.initialize();
    }

    @Test
    @Order(1)
    void testAddExpense_Success() {
        boolean result = ExpenseController.addExpense(TEST_EMAIL, "Food", 15.99, "Lunch at restaurant");
        assertTrue(result, "❌ Failed to add valid expense!");
    }

    @Test
    @Order(2)
    void testGetExpensesByUser() {
        List<Expense> expenses = ExpenseController.getExpensesByUser(TEST_EMAIL);
        assertNotNull(expenses, "❌ Expenses list should not be null.");
        assertFalse(expenses.isEmpty(), "❌ Expenses list should not be empty.");
        insertedExpenseId = expenses.get(0).getId(); // Lưu ID để kiểm tra cập nhật
    }

    @Test
    @Order(3)
    void testUpdateExpense_Success() {
        boolean updated = ExpenseController.updateExpense(insertedExpenseId, "Groceries", 20.50, "Bought fruits");
        assertTrue(updated, "❌ Expense update failed.");
    }

    @Test
    @Order(4)
    void testDeleteExpenseByCategory() {
        boolean deleted = ExpenseController.deleteExpenseByCategory(TEST_EMAIL, "Groceries");
        assertTrue(deleted, "❌ Expense deletion by category failed.");
    }
}