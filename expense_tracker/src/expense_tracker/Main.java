package expense_tracker;

import database.DatabaseManager;
import view.LoginForm;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.initialize(); // Đảm bảo database được thiết lập
        new LoginForm(); // Khởi chạy giao diện đăng nhập
    }
}