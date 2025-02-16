package view;

import controller.ExpenseController;
import controller.UserController;
import model.Expense;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

public class MainFrame extends JFrame {
    private DefaultListModel<String> expenseListModel;
    private JList<String> expenseList;
    private JButton addExpenseButton, deleteExpenseButton;

    public MainFrame() {
        setTitle("Budget Management");
        setSize(400, 350);
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

        deleteExpenseButton = new JButton("Delete Expense");
        deleteExpenseButton.setBounds(200, 50, 130, 40);
        add(deleteExpenseButton);

        expenseListModel = new DefaultListModel<>();
        expenseList = new JList<>(expenseListModel);
        JScrollPane scrollPane = new JScrollPane(expenseList);
        scrollPane.setBounds(50, 100, 300, 180);
        add(scrollPane);

        loadExpenses();

        addExpenseButton.addActionListener(e -> new ExpenseForm(this));

        deleteExpenseButton.addActionListener(e -> deleteSelectedExpense());

        add(userLabel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

     void loadExpenses() {
        expenseListModel.clear();
        List<Expense> expenses = ExpenseController.getExpensesByUser(UserController.getLoggedInUser().getEmail());
        DecimalFormat formatter = new DecimalFormat("#,##0.00"); 
        int index = 1; 

        for (Expense expense : expenses) {
            String categoryFormatted = expense.getCategory().substring(0, 1).toUpperCase() + expense.getCategory().substring(1); 
            String amountFormatted = "$" + formatter.format(expense.getAmount()); 
            String displayText = index + " - " + categoryFormatted + " - " + amountFormatted;

            if (!expense.getNote().isEmpty()) { 
                displayText += " (" + expense.getNote() + ")";
            }
            expenseListModel.addElement(displayText);
            index++; 
        }
    }

    private void deleteSelectedExpense() {
        int selectedIndex = expenseList.getSelectedIndex();
        if (selectedIndex != -1) {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String selectedValue = expenseListModel.getElementAt(selectedIndex);
                String category = selectedValue.split(" - ")[1]; 

                boolean success = ExpenseController.deleteExpenseByCategory(UserController.getLoggedInUser().getEmail(), category);
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