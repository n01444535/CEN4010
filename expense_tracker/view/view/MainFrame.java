package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Budget Management");
        setSize(400, 300);
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

        userLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userMenu.show(userLabel, 0, userLabel.getHeight()); 
            }
        });

        settingsItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Settings feature coming soon!");
        });

        logoutItem.addActionListener(e -> {
            UserController.logout();
            dispose();
            new LoginForm();
        });

        JButton addExpenseButton = new JButton("Add Expense");
        addExpenseButton.setBounds(120, 100, 150, 40);

        add(userLabel); 
        add(addExpenseButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}