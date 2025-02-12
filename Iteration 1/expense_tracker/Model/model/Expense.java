package model;

import java.util.Date;

public class Expense {
    private String category;
    private double amount;
    private Date date;
    private String note;

    public Expense(String category, double amount, Date date, String note) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.note = note;
    }

    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    public Date getDate() { return date; }
    public String getNote() { return note; }
}