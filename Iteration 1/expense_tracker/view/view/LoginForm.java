package view;

import controller.UserController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel registerLabel;

    public LoginForm() {
        setTitle("Sign In");
        setSize(350, 350);
        setLayout(null);
        getContentPane().setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("Sign in");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setBounds(120, 20, 200, 30);
        add(titleLabel);

        JLabel emailLabel = new JLabel("Usernamee");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBounds(40, 70, 80, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(40, 100, 250, 30);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(40, 140, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(40, 170, 250, 30);
        add(passwordField);

        loginButton = new JButton("LOG IN");
        loginButton.setBounds(40, 220, 250, 35);
        loginButton.setBackground(Color.YELLOW);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(loginButton);

        registerLabel = new JLabel("New Customer?");
        registerLabel.setForeground(Color.YELLOW);
        registerLabel.setBounds(100, 270, 200, 25);
        add(registerLabel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                boolean success = UserController.authenticateUser(email, password);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Login Successful! Welcome");
                    dispose();
                    new MainFrame();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Try again.");
                }
            }
        });

        registerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new RegisterForm();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
