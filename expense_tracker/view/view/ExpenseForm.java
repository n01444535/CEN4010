package view;

import controller.ExpenseController;
import controller.UserController;
import model.Expense;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.Locale;

public class ExpenseForm extends JFrame {
    private JTextField categoryField, noteField;
    private JFormattedTextField amountField;
    private JButton addButton, editButton;
    private String amountString = "0";
    private MainFrame parentFrame;
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
    private Expense existingExpense;

    public ExpenseForm(MainFrame parent) {
        this(parent, null);
    }

    public ExpenseForm(MainFrame parent, Expense expense) {
        this.parentFrame = parent;
        this.existingExpense = expense;
        
        setTitle(expense == null ? "Add Expense" : "Edit Expense");
        setSize(350, 300);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(20, 20, 80, 25);
        add(categoryLabel);

        categoryField = new JTextField();
        categoryField.setBounds(120, 20, 180, 25);
        add(categoryField);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(20, 60, 80, 25);
        add(amountLabel);

        amountField = new JFormattedTextField();
        amountField.setBounds(120, 60, 180, 25);
        amountField.setEditable(false);
        add(amountField);

        JLabel noteLabel = new JLabel("Note:");
        noteLabel.setBounds(20, 100, 80, 25);
        add(noteLabel);

        noteField = new JTextField();
        noteField.setBounds(120, 100, 180, 25);
        add(noteField);

        addButton = new JButton("Add Expense");
        addButton.setBounds(50, 150, 120, 35);
        add(addButton);

        editButton = new JButton("Edit Expense");
        editButton.setBounds(180, 150, 120, 35);
        editButton.setVisible(false);
        add(editButton);

        if (existingExpense != null) {
            categoryField.setText(existingExpense.getCategory());
            amountString = String.valueOf((int) (existingExpense.getAmount() * 100));
            amountField.setText(currencyFormat.format(existingExpense.getAmount()));
            noteField.setText(existingExpense.getNote());
            addButton.setVisible(false);
            editButton.setVisible(true);
        } else {
            amountField.setText("$0.00");
        }

        amountField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                    return;
                }
                amountString = amountString.replace(",", "") + c;
                updateAmountField();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && amountString.length() > 1) {
                    amountString = amountString.substring(0, amountString.length() - 1);
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    amountString = "0";
                }
                updateAmountField();
            }
        });

        addButton.addActionListener(e -> handleExpenseAction(false));
        editButton.addActionListener(e -> handleExpenseAction(true));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void handleExpenseAction(boolean isEdit) {
        String category = categoryField.getText().trim();
        if (category.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Category cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String formattedCategory = category.substring(0, 1).toUpperCase() + category.substring(1);
        String note = noteField.getText().trim();
        double amount = Double.parseDouble(amountString) / 100;

        String email = UserController.getLoggedInUser().getEmail();
        if (email == null || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "User is not logged in!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success;
        if (isEdit && existingExpense != null) {
            success = ExpenseController.updateExpense(existingExpense.getId(), formattedCategory, amount, note);
        } else {
            success = ExpenseController.addExpense(email, formattedCategory, amount, note);
        }

        if (success) {
            JOptionPane.showMessageDialog(null, isEdit ? "Expense Updated!" : "Expense Added!");
            parentFrame.loadExpenses();
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Failed to process expense. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateAmountField() {
        double amountValue = Double.parseDouble(amountString) / 100;
        amountField.setText(currencyFormat.format(amountValue));
    }
}