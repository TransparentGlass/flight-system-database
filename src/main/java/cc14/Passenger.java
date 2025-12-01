package cc14;

public class Passenger {

    private String username;
    private String password;
    private String fullName;

    public Passenger(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getFullName() { return fullName; }

    @Override
    public String toString() {
        return fullName + " (" + username + ")";
    }
}
