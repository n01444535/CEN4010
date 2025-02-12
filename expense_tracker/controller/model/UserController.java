package model;
import java.util.Date;

public class Expense {
    private int id;
    private int userId;
    private int categoryId;
    private double amount;
    private Date date;
    private String note;

    public Expense(int id, int userId, int categoryId, double amount, Date date, String note) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
        this.note = note;
    }
}