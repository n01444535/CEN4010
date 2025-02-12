package model;

public class Budget {
    private double limit;
    private double currentSpent;

    public Budget(double limit) {
        this.limit = limit;
        this.currentSpent = 0;
    }

    public void addExpense(double amount) { currentSpent += amount; }
    public boolean isExceeded() { return currentSpent > limit; }
}