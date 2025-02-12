package view;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Budget Manangement");
        setSize(400, 300);
        setLayout(null);

        JButton addExpenseButton = new JButton("Add Expense");
        addExpenseButton.setBounds(100, 50, 200, 40);
        add(addExpenseButton);

        addExpenseButton.addActionListener(e -> new ExpenseForm());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}