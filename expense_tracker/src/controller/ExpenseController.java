package controller;

import database.DatabaseManager;
import model.Expense;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseController {

    public static boolean addExpense(String email, String category, double amount, String note) {
        String query = "INSERT INTO Expenses (user_id, category, amount, note) VALUES ((SELECT id FROM Users WHERE email = ?), ?, ?, ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, category);
            stmt.setDouble(3, amount);
            stmt.setString(4, note);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding expense: " + e.getMessage());
            return false;
        }
    }

    public static List<Expense> getExpensesByUser(String email) {
        List<Expense> expenses = new ArrayList<>();
        String query = "SELECT * FROM Expenses WHERE user_id = (SELECT id FROM Users WHERE email = ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                expenses.add(new Expense(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("category"),
                        rs.getDouble("amount"),
                        rs.getString("note"),
                        rs.getDate("date")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching expenses: " + e.getMessage());
        }
        return expenses;
    }

    public static boolean deleteExpenseByCategory(String email, String category) {
        String query = "DELETE FROM Expenses WHERE user_id = (SELECT id FROM Users WHERE email = ?) AND category = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, category);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting expense: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean updateExpense(int expenseId, String category, double amount, String note) {
        String query = "UPDATE Expenses SET category = ?, amount = ?, note = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, category);
            stmt.setDouble(2, amount);
            stmt.setString(3, note);
            stmt.setInt(4, expenseId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Error updating expense: " + e.getMessage());
            return false;
        }
    }
}