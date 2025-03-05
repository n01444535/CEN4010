package view;

import controller.UserController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterForm extends JFrame {
    private JTextField firstNameField, lastNameField, emailField, phoneField, usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public RegisterForm() {
        setTitle("Sign Up For New Customer");
        setSize(450, 550); // ✅ Tăng chiều cao cửa sổ
        setMinimumSize(new Dimension(450, 550)); // ✅ Ngăn không cho thu nhỏ quá mức
        setLocationRelativeTo(null); // ✅ Giữ cửa sổ luôn ở giữa màn hình

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout()); // ✅ Giữ bố cục linh hoạt
        mainPanel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10); // ✅ Khoảng cách giữa các thành phần

        JLabel titleLabel = new JLabel("Sign Up", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.YELLOW);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setForeground(Color.WHITE);
        gbc.gridy++;
        mainPanel.add(firstNameLabel, gbc);

        firstNameField = new JTextField();
        gbc.gridy++;
        mainPanel.add(firstNameField, gbc);

        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setForeground(Color.WHITE);
        gbc.gridy++;
        mainPanel.add(lastNameLabel, gbc);

        lastNameField = new JTextField();
        gbc.gridy++;
        mainPanel.add(lastNameField, gbc);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridy++;
        mainPanel.add(usernameLabel, gbc);

        usernameField = new JTextField();
        gbc.gridy++;
        mainPanel.add(usernameField, gbc);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.WHITE);
        gbc.gridy++;
        mainPanel.add(emailLabel, gbc);

        emailField = new JTextField();
        gbc.gridy++;
        mainPanel.add(emailField, gbc);

        JLabel phoneLabel = new JLabel("Phone Number");
        phoneLabel.setForeground(Color.WHITE);
        gbc.gridy++;
        mainPanel.add(phoneLabel, gbc);

        phoneField = new JTextField();
        gbc.gridy++;
        mainPanel.add(phoneField, gbc);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridy++;
        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        gbc.gridy++;
        mainPanel.add(passwordField, gbc);

        registerButton = new JButton("SIGN UP");
        registerButton.setBackground(Color.YELLOW);
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridy++;
        mainPanel.add(registerButton, gbc);

        registerButton.addActionListener(e -> {
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

        JLabel signInLabel = new JLabel("<html><u>Already have an account? Sign In</u></html>", JLabel.CENTER);
        signInLabel.setForeground(Color.YELLOW);
        signInLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        signInLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridy++;
        mainPanel.add(signInLabel, gbc);

        signInLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoginForm();
            }
        });

        // ✅ Thêm JScrollPane nhưng ẩn thanh cuộn
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0)); // ✅ Ẩn thanh cuộn nhưng vẫn cuộn được
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // ✅ Cuộn mượt hơn
        add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}