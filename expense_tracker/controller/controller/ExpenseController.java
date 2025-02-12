package controller;

import database.DatabaseManager;
import model.Expense;
import java.util.Date;

public class ExpenseController {
    public static void addExpense(String category, double amount, String note) {
        Expense expense = new Expense(category, amount, new Date(), note);
        DatabaseManager.addExpense(expense);
    }
}