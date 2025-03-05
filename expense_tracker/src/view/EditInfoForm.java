package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;

public class EditInfoForm extends JFrame {
    private JTextField firstNameField, lastNameField, emailField, phoneField;
    private JPasswordField oldPasswordField, newPasswordField, confirmPasswordField;
    private JButton saveInfoButton, cancelInfoButton, savePasswordButton, cancelPasswordButton, deleteAccountButton;
    private User loggedInUser;
    private JTabbedPane tabbedPane;

    public EditInfoForm() {
        loggedInUser = UserController.getLoggedInUser();
        if (loggedInUser == null) {
            JOptionPane.showMessageDialog(null, "No user logged in!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setTitle("Settings");
        setSize(400, 350);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        JPanel editInfoPanel = createEditInfoPanel();
        JPanel changePasswordPanel = createChangePasswordPanel();

        tabbedPane.addTab("Edit Info", editInfoPanel);
        tabbedPane.addTab("Change Password", changePasswordPanel);

        add(tabbedPane, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private JPanel createEditInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(20, 20, 100, 25);
        panel.add(firstNameLabel);

        firstNameField = new JTextField(loggedInUser.getFirstName());
        firstNameField.setBounds(140, 20, 200, 25);
        panel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(20, 50, 100, 25);
        panel.add(lastNameLabel);

        lastNameField = new JTextField(loggedInUser.getLastName());
        lastNameField.setBounds(140, 50, 200, 25);
        panel.add(lastNameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 80, 100, 25);
        panel.add(emailLabel);

        emailField = new JTextField(loggedInUser.getEmail());
        emailField.setBounds(140, 80, 200, 25);
        panel.add(emailField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(20, 110, 100, 25);
        panel.add(phoneLabel);

        phoneField = new JTextField(loggedInUser.getPhone());
        phoneField.setBounds(140, 110, 200, 25);
        panel.add(phoneField);

        saveInfoButton = new JButton("Save Changes");
        saveInfoButton.setBounds(140, 150, 120, 35);
        panel.add(saveInfoButton);

        cancelInfoButton = new JButton("Cancel");
        cancelInfoButton.setBounds(260, 150, 80, 35);
        panel.add(cancelInfoButton);

        deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.setBounds(140, 200, 200, 35);
        deleteAccountButton.setForeground(Color.RED);
        panel.add(deleteAccountButton);

        saveInfoButton.addActionListener(e -> updateUserInfo());
        cancelInfoButton.addActionListener(e -> dispose());
        deleteAccountButton.addActionListener(e -> showDeleteAccountDialog());

        return panel;
    }

    private JPanel createChangePasswordPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel oldPasswordLabel = new JLabel("Old Password:");
        oldPasswordLabel.setBounds(20, 20, 120, 25);
        panel.add(oldPasswordLabel);

        oldPasswordField = new JPasswordField();
        oldPasswordField.setBounds(140, 20, 200, 25);
        panel.add(oldPasswordField);

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setBounds(20, 50, 120, 25);
        panel.add(newPasswordLabel);

        newPasswordField = new JPasswordField();
        newPasswordField.setBounds(140, 50, 200, 25);
        panel.add(newPasswordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(20, 80, 120, 25);
        panel.add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(140, 80, 200, 25);
        panel.add(confirmPasswordField);

        savePasswordButton = new JButton("Change Password");
        savePasswordButton.setBounds(140, 120, 160, 35);
        panel.add(savePasswordButton);

        cancelPasswordButton = new JButton("Cancel");
        cancelPasswordButton.setBounds(300, 120, 80, 35);
        panel.add(cancelPasswordButton);

        savePasswordButton.addActionListener(e -> updatePassword());
        cancelPasswordButton.addActionListener(e -> dispose());

        return panel;
    }

    private void showDeleteAccountDialog() {
        int choice = JOptionPane.showConfirmDialog(null,
                "Your account will be permanently deleted in 2 days.\n" +
                        "You can still log in within this period.\n\n" +
                        "Are you sure you want to proceed?",
                "Confirm Account Deletion", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            scheduleAccountDeletion();
        }
    }

    private void scheduleAccountDeletion() {
        boolean success = UserController.scheduleAccountDeletion(loggedInUser.getId());
        if (success) {
            JOptionPane.showMessageDialog(null, "Account scheduled for deletion in 2 days.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Failed to schedule account deletion.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateUserInfo() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = UserController.updateUserInfo(loggedInUser.getId(), firstName, lastName, email, phone, null);
        if (success) {
            JOptionPane.showMessageDialog(null, "Information updated successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Failed to update info.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePassword() {
        String oldPassword = new String(oldPasswordField.getPassword()).trim();
        String newPassword = new String(newPasswordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All password fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!UserController.authenticateUser(loggedInUser.getUsername(), oldPassword)) {
            JOptionPane.showMessageDialog(null, "Old password is incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "New passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = UserController.updateUserInfo(loggedInUser.getId(), null, null, null, null, newPassword);
        if (success) {
            JOptionPane.showMessageDialog(null, "Password updated successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Failed to change password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}