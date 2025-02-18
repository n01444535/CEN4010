package controller;

import database.DatabaseManager;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {
    private static User loggedInUser = null; 

    public static boolean registerUser(String firstName, String lastName, String username, String email, String phone, String password) {
        if (isEmailRegistered(email)) {
            System.out.println("Email is already registered!");
            return false;
        }
        if (isUsernameTaken(username)) {
            System.out.println("Username is already taken!");
            return false;
        }

        String query = "INSERT INTO Users (first_name, last_name, username, email, phone, password_hash) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, username);
            stmt.setString(4, email);
            stmt.setString(5, phone);
            stmt.setString(6, password);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    public static boolean authenticateUser(String username, String password) {
        System.out.println("DEBUG: Username -> " + username);
        System.out.println("DEBUG: Password -> " + password);

        String query = "SELECT * FROM Users WHERE username = ? AND password_hash = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
            	loggedInUser = new User(
            		    rs.getInt("id"), 
            		    rs.getString("first_name"),
            		    rs.getString("last_name"),
            		    rs.getString("username"),
            		    rs.getString("email"),
            		    rs.getString("phone"),
            		    rs.getString("password_hash")
            		);
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isEmailRegistered(String email) {
        String query = "SELECT email FROM Users WHERE email = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isUsernameTaken(String username) {
        String query = "SELECT username FROM Users WHERE username = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void logout() {
        loggedInUser = null;
    }
}