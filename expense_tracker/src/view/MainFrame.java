package view;

import controller.ExpenseController;
import controller.UserController;
import model.Expense;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class MainFrame extends JFrame {
    private DefaultListModel<String> expenseListModel;
    private JList<String> expenseList;
    private JLabel totalExpenseLabel;
    private JButton addExpenseButton, deleteExpenseButton, editExpenseButton;

    public MainFrame() {
        setTitle("Budget Management");
        setSize(400, 450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        User loggedInUser = UserController.getLoggedInUser();
        String userName = (loggedInUser != null) ? loggedInUser.getFirstName() + " " + loggedInUser.getLastName() : "Guest";

        JLabel userLabel = new JLabel("Hello, " + userName + " ▼");
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        userLabel.setBounds(250, 10, 220, 25);
        userLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPopupMenu userMenu = new JPopupMenu();
        JMenuItem settingsItem = new JMenuItem("Settings");
        JMenuItem logoutItem = new JMenuItem("Logout");

        userMenu.add(settingsItem);
        userMenu.addSeparator();
        userMenu.add(logoutItem);

        userLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userMenu.show(userLabel, 0, userLabel.getHeight());
            }
        });

        logoutItem.addActionListener(e -> {
            UserController.logout();
            dispose();
            new LoginForm();
        });

        addExpenseButton = new JButton("Add Expense");
        addExpenseButton.setBounds(50, 50, 130, 40);
        add(addExpenseButton);

        editExpenseButton = new JButton("Edit Expense");
        editExpenseButton.setBounds(220, 50, 130, 40);
        add(editExpenseButton);

        deleteExpenseButton = new JButton("Delete Expense");
        deleteExpenseButton.setBounds(135, 100, 130, 40);
        add(deleteExpenseButton);

        expenseListModel = new DefaultListModel<>();
        expenseList = new JList<>(expenseListModel);
        JScrollPane scrollPane = new JScrollPane(expenseList);
        scrollPane.setBounds(50, 160, 300, 180);
        add(scrollPane);

        totalExpenseLabel = new JLabel("Total Spent: $0.00");
        totalExpenseLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalExpenseLabel.setBounds(50, 350, 300, 25);
        add(totalExpenseLabel);

        loadExpenses();

        addExpenseButton.addActionListener(e -> new ExpenseForm(this));
        editExpenseButton.addActionListener(e -> editSelectedExpense());
        deleteExpenseButton.addActionListener(e -> deleteSelectedExpense());
        settingsItem.addActionListener(e -> new EditInfoForm());

        add(userLabel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    void loadExpenses() {
        expenseListModel.clear();
        List<Expense> expenses = ExpenseController.getExpensesByUser(UserController.getLoggedInUser().getEmail());
        DecimalFormat formatter = new DecimalFormat("#,##0.00"); 
        int index = 1;
        double totalSpent = 0.0;

        for (Expense expense : expenses) {
            String category = (expense.getCategory() == null || expense.getCategory().isEmpty()) ? "Unknown" : expense.getCategory();
            String categoryFormatted = category.substring(0, 1).toUpperCase() + category.substring(1);
            
            double amount = expense.getAmount();
            totalSpent += amount;
            String amountFormatted = "$" + formatter.format(amount);
            String displayText = index + " - " + categoryFormatted + " - " + amountFormatted;

            if (expense.getNote() != null && !expense.getNote().isEmpty()) { 
                displayText += " (" + expense.getNote() + ")";
            }
            
            expenseListModel.addElement(displayText);
            index++;
        }
        
        totalExpenseLabel.setText("Total Spent: $" + formatter.format(totalSpent));

        expenseList.setModel(expenseListModel);
    }

    private void editSelectedExpense() {
        int selectedIndex = expenseList.getSelectedIndex();
        if (selectedIndex != -1) {
            List<Expense> expenses = ExpenseController.getExpensesByUser(UserController.getLoggedInUser().getEmail());
            Expense selectedExpense = expenses.get(selectedIndex);
            new ExpenseForm(this, selectedExpense);
        } else {
            JOptionPane.showMessageDialog(null, "Please select an expense to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedExpense() {
        int selectedIndex = expenseList.getSelectedIndex();
        if (selectedIndex != -1) {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                List<Expense> expenses = ExpenseController.getExpensesByUser(UserController.getLoggedInUser().getEmail());
                Expense selectedExpense = expenses.get(selectedIndex);
                
                boolean success = ExpenseController.deleteExpenseById(selectedExpense.getId());
                if (success) {
                    loadExpenses(); 
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete expense.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select an expense to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}