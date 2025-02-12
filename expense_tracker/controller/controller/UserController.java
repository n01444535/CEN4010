package controller;

import model.User;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    private static List<User> users = new ArrayList<>();
    private static User loggedInUser = null;

    public static boolean registerUser(String firstName, String lastName, String username, String email, String phone, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) || user.getUsername().equals(username)) {
                return false; // Email or Username already exists
            }
        }
        users.add(new User(firstName, lastName, username, email, phone, password));
        return true;
    }

    public static boolean authenticateUser(String identifier, String password) {
        for (User user : users) {
            if ((user.getEmail().equals(identifier) || user.getUsername().equals(identifier)) && user.checkPassword(password)) {
                loggedInUser = user;
                return true;
            }
        }
        return false;
    }

    public static boolean isUserLoggedIn() {
        return loggedInUser != null;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void logout() {
        loggedInUser = null;
    }
}