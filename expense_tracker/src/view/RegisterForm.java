package view;

import controller.UserController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterForm extends JFrame {
    private JTextField firstNameField, lastNameField, emailField, phoneField, usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public RegisterForm() {
        setTitle("Sign Up For New Customer");
        setSize(400, 500);
        setLayout(null);
        getContentPane().setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setBounds(140, 20, 200, 30);
        add(titleLabel);

        // 👉 Di chuyển dòng "Already have an account? Sign In" ngay dưới tiêu đề
        JLabel signInLabel = new JLabel("<html><u>Already have an account? Sign In</u></html>");
        signInLabel.setForeground(Color.YELLOW);
        signInLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        signInLabel.setBounds(120, 55, 200, 30); // 🔥 Đặt ngay dưới "Sign Up"
        signInLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(signInLabel);

        signInLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoginForm();
            }
        });

        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setForeground(Color.WHITE);
        firstNameLabel.setBounds(40, 90, 100, 25); // 🔥 Điều chỉnh xuống dưới Sign In
        add(firstNameLabel);

        firstNameField = new JTextField();
        firstNameField.setBounds(40, 115, 300, 30);
        add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setForeground(Color.WHITE);
        lastNameLabel.setBounds(40, 150, 100, 25);
        add(lastNameLabel);

        lastNameField = new JTextField();
        lastNameField.setBounds(40, 175, 300, 30);
        add(lastNameField);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(40, 210, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(40, 235, 300, 30);
        add(usernameField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBounds(40, 270, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(40, 295, 300, 30);
        add(emailField);

        JLabel phoneLabel = new JLabel("Phone Number");
        phoneLabel.setForeground(Color.WHITE);
        phoneLabel.setBounds(40, 330, 100, 25);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(40, 355, 300, 30);
        add(phoneField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(40, 390, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(40, 415, 300, 30);
        add(passwordField);

        registerButton = new JButton("SIGN UP");
        registerButton.setBounds(40, 455, 300, 35);
        registerButton.setBackground(new Color(255, 215, 0)); // Gold/Yellow
        registerButton.setForeground(Color.BLACK);
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        registerButton.setFocusPainted(false);
        add(registerButton);

        registerButton.addActionListener((ActionEvent e) -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String username = usernameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String password = new String(passwordField.getPassword());

            boolean success = UserController.registerUser(firstName, lastName, username, email, phone, password);
            if (success) {
                JOptionPane.showMessageDialog(null, "Registration Successful! Proceed to login.");
                dispose();
                new LoginForm();
            } else {
                JOptionPane.showMessageDialog(null, "Email already registered! Try another email.");
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}