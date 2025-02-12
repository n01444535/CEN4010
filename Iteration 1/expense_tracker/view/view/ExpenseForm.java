package view;

import controller.ExpenseController;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpenseForm extends JFrame {
    private JTextField categoryField, amountField, noteField;
    private JButton addButton;

    public ExpenseForm() {
        setTitle("Add Expense");
        setSize(300, 200);
        setLayout(null);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(10, 10, 80, 25);
        add(categoryLabel);

        categoryField = new JTextField();
        categoryField.setBounds(100, 10, 150, 25);
        add(categoryField);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(10, 40, 80, 25);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(100, 40, 150, 25);
        add(amountField);

        JLabel noteLabel = new JLabel("Note:");
        noteLabel.setBounds(10, 70, 80, 25);
        add(noteLabel);

        noteField = new JTextField();
        noteField.setBounds(100, 70, 150, 25);
        add(noteField);

        addButton = new JButton("Add");
        addButton.setBounds(100, 110, 80, 30);
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String category = categoryField.getText();
                double amount = Double.parseDouble(amountField.getText());
                String note = noteField.getText();
                ExpenseController.addExpense(category, amount, note);
                JOptionPane.showMessageDialog(null, "Expense Added!");
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}