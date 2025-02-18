package model;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;
    private String passwordHash;

    public User(int id, String firstName, String lastName, String username, String email, String phone, String passwordHash) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.passwordHash = passwordHash;
    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public boolean checkPassword(String input) { return passwordHash.equals(input); }
}