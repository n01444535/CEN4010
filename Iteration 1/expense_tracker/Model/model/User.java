package model;

public class User {
    private String username;
    private String email;
    private String passwordHash;

    public User(String username, String email, String passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public boolean checkPassword(String input) { return passwordHash.equals(input); }
}