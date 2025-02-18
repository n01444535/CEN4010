package model;

import java.util.Date;

public class Expense {
    private int id;
    private int userId;
    private String category;
    private double amount;
    private String note;
    private Date date;

    public Expense(int id, int userId, String category, double amount, String note, Date date) {
        this.id = id;
        this.userId = userId;
        this.category = category;
        this.amount = amount;
        this.note = note;
        this.date = date;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    public String getNote() { return note; }
    public Date getDate() { return date; }
}