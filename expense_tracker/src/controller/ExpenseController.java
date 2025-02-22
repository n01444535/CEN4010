package controller;

import database.DatabaseManager;
import model.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseController {

    public static boolean addExpense(String email, String category, double amount, String note) {
        String query = "INSERT INTO EXPENSES (USER_ID, CATEGORY, AMOUNT, NOTE, EXPENSE_DATE) " +
                       "VALUES ((SELECT ID FROM USERS WHERE EMAIL = ?), ?, ?, ?, CURRENT_TIMESTAMP)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, email);
            stmt.setString(2, category);
            stmt.setDouble(3, amount);
            stmt.setString(4, note);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ Expense added successfully for user: " + email);
                return true;
            } else {
                System.out.println("❌ Failed to add expense for user: " + email);
                return false;
            }

        } catch (SQLException e) {
            System.out.println("❌ ERROR adding expense: " + e.getMessage());
            return false;
        }
    }

    public static List<Expense> getExpensesByUser(String email) {
        List<Expense> expenses = new ArrayList<>();
        String query = "SELECT ID, USER_ID, CATEGORY, AMOUNT, NOTE, EXPENSE_DATE FROM EXPENSES WHERE USER_ID = (SELECT ID FROM USERS WHERE EMAIL = ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                expenses.add(new Expense(
                        rs.getInt("ID"),
                        rs.getInt("USER_ID"),
                        rs.getString("CATEGORY"),
                        rs.getDouble("AMOUNT"),
                        rs.getString("NOTE"),
                        rs.getTimestamp("EXPENSE_DATE")
                ));
            }

            if (expenses.isEmpty()) {
                System.out.println("⚠️ No expenses found for user: " + email);
            } else {
                System.out.println("✅ Retrieved " + expenses.size() + " expenses for user: " + email);
            }

        } catch (SQLException e) {
            System.out.println("❌ ERROR fetching expenses: " + e.getMessage());
        }
        return expenses;
    }

    public static boolean deleteExpenseByCategory(String email, String category) {
        String query = "DELETE FROM EXPENSES WHERE USER_ID = (SELECT ID FROM USERS WHERE EMAIL = ?) AND CATEGORY = ?";
        
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, category);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ Expense(s) deleted for category: " + category + " by user: " + email);
                return true;
            } else {
                System.out.println("⚠️ No expenses found to delete for category: " + category + " by user: " + email);
                return false;
            }

        } catch (SQLException e) {
            System.out.println("❌ ERROR deleting expense: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateExpense(int expenseId, String category, double amount, String note) {
        String query = "UPDATE EXPENSES SET CATEGORY = ?, AMOUNT = ?, NOTE = ? WHERE ID = ?";
        
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, category);
            stmt.setDouble(2, amount);
            stmt.setString(3, note);
            stmt.setInt(4, expenseId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Expense updated successfully for ID: " + expenseId);
                return true;
            } else {
                System.out.println("⚠️ No expense found to update for ID: " + expenseId);
                return false;
            }

        } catch (SQLException e) {
            System.out.println("❌ ERROR updating expense: " + e.getMessage());
            return false;
        }
    }
}