package expense_tracker;

import database.DatabaseManager;
import view.LoginForm;

public class Budget {
    public static void main(String[] args) {
        DatabaseManager.initialize();
        new LoginForm();
    }
}