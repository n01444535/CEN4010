package database;

import model.Expense;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static List<Expense> expenses = new ArrayList<>();

    public static void initialize() {
        expenses = new ArrayList<>();
        System.out.println("Database initialized.");
    }

    public static void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public static List<Expense> getExpenses() {
        return expenses;
    }
} 