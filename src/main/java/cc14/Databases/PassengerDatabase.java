package cc14.Databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import cc14.models.Passenger;

//This is passenger login databse

public class PassengerDatabase {

    public static boolean register(String username, String password, String fullName) {
        if(isUserExist(username) || username == null || username.isBlank() || username.isEmpty()){
            System.out.println("user exist or username is blank");
            return false;
        }
        
        String sql = "INSERT INTO users_table (username, password, full_name) VALUES (?, ?, ?)";
        try{
            Connection Database = DB_connection.connect();
            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, fullName);
            pstmt.executeUpdate();
            System.out.println("Successfull registration. Welcome! " + fullName);
            return true;
        } catch (SQLException e){
            System.out.println("Error registering user: " + e.getMessage());
        }
        return false;
    }

    public static Passenger validatePassenger(String username, String password) {
        String sql = "SELECT * FROM users_table WHERE username = ? and password = ?";
        try {
            Connection Database = DB_connection.connect();
            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                Passenger new_Passenger = new Passenger(username, password, rs.getString("full_name"));
                System.out.println("User validated!");
                return new_Passenger;
            }
            
        } catch (SQLException e) {
            System.out.println("Error validating user: " + e.getMessage());
        }
        return null;
    }

    public static Passenger getPassenger(String username) {
        String sql = "SELECT * FROM users_table WHERE username = ?";
        try {
            Connection Database = DB_connection.connect();
            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                Passenger new_Passenger = new Passenger(username, rs.getString("password"), rs.getString("full_name"));
                return new_Passenger;
            }
            
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println("Error validating user: " + e.getMessage());
        }
        return null;
    }

    public static boolean isUserExist(String username){
        String sql = "SELECT * FROM users_table WHERE username = ?";
        try {
            Connection Database = DB_connection.connect();
            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                rs.getInt("user_ID");
                return true;
            }
            
        } catch (SQLException e) {
            System.out.println("Error finding user: " + e.getMessage());
        }
        return false;
    }
}
