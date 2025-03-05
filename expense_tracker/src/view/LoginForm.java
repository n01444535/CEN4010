package view;

import controller.UserController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JCheckBox showPasswordCheckBox;

    public LoginForm() {
        setTitle("Budget Management");
        setSize(400, 450); 
        setMinimumSize(new Dimension(400, 450));
        setLocationRelativeTo(null); 

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10); 

        JLabel titleLabel = new JLabel("SIGN IN", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.YELLOW);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridy++;
        mainPanel.add(usernameLabel, gbc);

        usernameField = new JTextField();
        gbc.gridy++;
        mainPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridy++;
        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        gbc.gridy++;
        mainPanel.add(passwordField, gbc);

        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBackground(Color.BLACK);
        showPasswordCheckBox.setForeground(Color.YELLOW);
        gbc.gridy++;
        mainPanel.add(showPasswordCheckBox, gbc);

        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        });

        loginButton = new JButton("LOG IN");
        loginButton.setBackground(new Color(30, 144, 255));
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridy++;
        mainPanel.add(loginButton, gbc);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            boolean success = UserController.authenticateUser(username, password);
            if (success) {
                dispose();
                new MainFrame();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials. Try again.");
            }
        });

        JLabel signUpLabel = new JLabel("<html><u>New Customer? Sign Up</u></html>", JLabel.CENTER);
        signUpLabel.setForeground(Color.YELLOW);
        signUpLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        signUpLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridy++;
        mainPanel.add(signUpLabel, gbc);

        signUpLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new RegisterForm();
            }
        });

        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setBackground(Color.BLACK);
        wrapperPanel.add(mainPanel);
        add(wrapperPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}