package cc14.Databases;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import cc14.models.Passenger;

//This is passenger login databse

public class PassengerDatabase {

    public static boolean register(String username, String password, String fullName) {
        if (isUserExist(username) || username == null || username.isBlank() || username.isEmpty()) {
            System.out.println("user exist or username is blank");
            return false;
        }

        String salt = generateSalt(16);
        String saltedPass = salt + password;
        String hash = hash(saltedPass);

        String sql = "INSERT INTO users_table (username, password, full_name, salt) VALUES (?, ?, ?, ?)";
        try {
            Connection Database = DB_connection.connect();
            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, hash);
            pstmt.setString(3, fullName);
            pstmt.setString(4, salt);

            pstmt.executeUpdate();
            System.out.println("Successfull registration. Welcome! " + fullName);
            return true;
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        }
        return false;
    }

    public static String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // SHA-256 should always exist, so this should never happen
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    public static String generateSalt(int length) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt); // convert to string for storage
    }

    // public static Passenger validatePassenger(String username, String password) {
    // String sql = "SELECT * FROM users_table WHERE username = ? and password = ?";
    // try {
    // Connection Database = DB_connection.connect();
    // PreparedStatement pstmt = Database.prepareStatement(sql);
    // pstmt.setString(1, username);
    // pstmt.setString(2, password);
    // ResultSet rs = pstmt.executeQuery();
    // if(rs.next()){
    // Passenger new_Passenger = new Passenger(username, password,
    // rs.getString("full_name"));
    // System.out.println("User validated!");
    // return new_Passenger;
    // }

    // } catch (SQLException e) {
    // System.out.println("Error validating user: " + e.getMessage());
    // }
    // return null;
    // }

    public static Passenger getPassenger(String username) {
        String sql = "SELECT * FROM users_table WHERE username = ?";
        try {
            Connection Database = DB_connection.connect();
            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Passenger new_Passenger = new Passenger(username, rs.getString("password"), rs.getString("full_name"));
                return new_Passenger;
            }

        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println("Error validating user: " + e.getMessage());
        }
        return null;
    }

    public static boolean isUserExist(String username) {
        String sql = "SELECT user_ID FROM users_table WHERE username = ?";
        try {
            Connection Database = DB_connection.connect();
            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                rs.getInt("user_ID");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error finding user: " + e.getMessage());
        }
        return false;
    }

    public static int passwordCheck(String user, String password) {
        user = user.trim();
        password = password.trim();

        try (Connection db = DB_connection.connect()) {
            String sql = "SELECT * FROM users_table WHERE username = ?";
            try (PreparedStatement ps = db.prepareStatement(sql)) {
                ps.setString(1, user);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    String salt = rs.getString("salt");
                    // Compare hash of (salt + input password) with stored hash
                    if (storedHash.equals(hash(salt + password))) {
                        return rs.getInt("user_ID"); // don't store plain password
                    }
                }

            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }

        // Login failed
        return -1;
    }

    public Passenger getPassenger(int id){
        try(Connection db = DB_connection.connect()){
            String sql = "Select username, password, full_name from users_table where user_ID = ?";
            try (PreparedStatement ps = db.prepareStatement(sql)){
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String fullName = rs.getString("full_name");
                    return new Passenger(username, password, fullName);
                }
            } 
        }catch(SQLException e){
            System.err.println("DB error: " + e.getMessage());
        }

        return null;
    }

}
