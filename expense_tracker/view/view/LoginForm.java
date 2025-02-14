package view;

import javax.swing.*;

import controller.UserController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JCheckBox showPasswordCheckBox;

    public LoginForm() {
        setTitle("Budget Management");
        setSize(350, 250);
        setLayout(null);
        getContentPane().setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("Sign In");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setBounds(120, 20, 200, 30);
        add(titleLabel);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(40, 60, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(40, 85, 250, 30);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(40, 120, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(40, 145, 250, 30);
        add(passwordField);

        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBounds(40, 175, 150, 20);
        showPasswordCheckBox.setBackground(Color.BLACK);
        showPasswordCheckBox.setForeground(Color.YELLOW);
        add(showPasswordCheckBox);

        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0); 
                } else {
                    passwordField.setEchoChar('•'); 
                }
            }
        });

        loginButton = new JButton("LOG IN");
        loginButton.setBounds(40, 200, 250, 35);
        loginButton.setBackground(Color.YELLOW);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                boolean success = UserController.authenticateUser(username, password);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Login Successful! Welcome.");
                    dispose();
                    new MainFrame();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Try again.");
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}