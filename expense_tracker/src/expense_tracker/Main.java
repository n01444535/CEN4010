package expense_tracker;

import view.LoginForm;
import view.MainFrame;
import view.RegisterForm;
import database.DatabaseManager;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.initialize(); // Ensure database is initialized
        new LoginForm(); // Start with Registration Form
    }
}
