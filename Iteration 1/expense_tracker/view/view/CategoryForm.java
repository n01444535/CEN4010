package view;

import controller.CategoryController;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoryForm extends JFrame {
    private JTextField categoryNameField;
    private JButton addButton;

    public CategoryForm() {
        setTitle("Add Category");
        setSize(300, 200);
        setLayout(null);

        JLabel categoryLabel = new JLabel("Category Name:");
        categoryLabel.setBounds(10, 10, 120, 25);
        add(categoryLabel);

        categoryNameField = new JTextField();
        categoryNameField.setBounds(140, 10, 130, 25);
        add(categoryNameField);

        addButton = new JButton("Add");
        addButton.setBounds(100, 50, 100, 30);
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoryName = categoryNameField.getText();
                boolean success = CategoryController.addCategory(categoryName);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Category Added Successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add category. Try again.");
                }
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
