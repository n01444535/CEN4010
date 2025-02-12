package controller;

import database.DatabaseManager;
import model.Expense;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class ExpenseController {
    public static boolean addExpense(int userId, int categoryId, double amount, String note) {
        String query = "INSERT INTO Expenses (user_id, category_id, amount, date, note) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, categoryId);
            stmt.setDouble(3, amount);
            stmt.setDate(4, new java.sql.Date(new Date().getTime()));
            stmt.setString(5, note);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}