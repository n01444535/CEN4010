package view;

import javax.swing.*;
import controller.UserController;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JCheckBox showPasswordCheckBox;

    public LoginForm() {
        setTitle("Budget Management");
        setSize(350, 280);
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

        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0); 
            } else {
                passwordField.setEchoChar('•'); 
            }
        });

        loginButton = new JButton("LOG IN");
        loginButton.setBounds(40, 200, 250, 35);
        loginButton.setBackground(Color.YELLOW);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(loginButton);

        // 🔥 Gọi phương thức login khi nhấn Enter hoặc nhấn nút
        ActionListener loginAction = e -> loginUser();
        loginButton.addActionListener(loginAction);

        // 👇 Thêm KeyListener để hỗ trợ nhấn Enter trên bàn phím
        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginUser();
                }
            }
        };

        usernameField.addKeyListener(enterKeyListener);
        passwordField.addKeyListener(enterKeyListener);

        JLabel signUpLabel = new JLabel("New Customer? Sign Up");
        signUpLabel.setForeground(Color.YELLOW);
        signUpLabel.setBounds(100, 245, 200, 25);
        signUpLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(signUpLabel);

        signUpLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                dispose(); 
                new RegisterForm();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        boolean success = UserController.authenticateUser(username, password);
        if (success) {
            dispose();
            new MainFrame();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid credentials. Try again.");
        }
    }
}