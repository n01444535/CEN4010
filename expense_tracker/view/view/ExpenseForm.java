package view;

import controller.ExpenseController;
import controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class ExpenseForm extends JFrame {
    private JTextField categoryField, noteField;
    private JFormattedTextField amountField;
    private JButton addButton;
    private String amountString = "0";
    private MainFrame parentFrame;
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

    public ExpenseForm(MainFrame parent) {
        this.parentFrame = parent;
        setTitle("Add Expense");
        setSize(350, 270);
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
        amountField.setText("$0.00");
        amountField.setEditable(false);
        add(amountField);

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

        JLabel noteLabel = new JLabel("Note:");
        noteLabel.setBounds(20, 100, 80, 25);
        add(noteLabel);

        noteField = new JTextField();
        noteField.setBounds(120, 100, 180, 25);
        add(noteField);

        addButton = new JButton("Add Expense");
        addButton.setBounds(100, 150, 140, 35);
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String category = categoryField.getText().trim();
                if (category.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Category cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                category = category.substring(0, 1).toUpperCase() + category.substring(1); 
                String note = noteField.getText().trim();
                double amount = Double.parseDouble(amountString) / 100;

                String email = UserController.getLoggedInUser().getEmail();
                if (email == null || email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "User is not logged in!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = ExpenseController.addExpense(email, category, amount, note);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Expense Added!");
                    parentFrame.loadExpenses();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add expense. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void updateAmountField() {
        double amountValue = Double.parseDouble(amountString) / 100;
        amountField.setText(currencyFormat.format(amountValue));
    }
}