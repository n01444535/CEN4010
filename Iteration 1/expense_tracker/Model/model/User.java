package model;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;
    private String passwordHash;

    public User(String firstName, String lastName, String username, String email, String phone, String passwordHash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.passwordHash = passwordHash;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public boolean checkPassword(String input) { return passwordHash.equals(input); }
}
