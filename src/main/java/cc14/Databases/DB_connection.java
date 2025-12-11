package cc14.Databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.spi.DirStateFactory.Result;


public class DB_connection {
    private static String IP_address = "100.108.168.84";

    public static Connection connect() {
        // Placeholder for database connection logic
        String database = "airport_system_db_test";
        
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:mysql://");
        sb.append(IP_address);
        sb.append(":3306/");
        sb.append(database);

        String url = sb.toString();
        String user = "projectuser";
        String password = "strongpassword";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database successfully.");
            return conn;
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    public static void disconnect(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Disconnected from the database.");
            } catch (SQLException e) {
                System.out.println("Disconnection failed: " + e.getMessage());
            }
        }
    }

    public static void changeIP(String newIP) {
        IP_address = newIP;
    }

    public static void main(String[] args) {
    }

    /*
    public void test_flight(){
        dbconnection dbConn = new dbconnection();
        Connection conn = dbconnection.connect();
        try {
            String sql = "INSERT into flights_table (flight_ID, airplane_ID, origin, destination, departure_time, arrival_time, base_fare) VALUES (?, ?, ?, ?, ?, ?, ? );";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "1001");
            pstmt.setString(2, "1001");
            pstmt.setString(3, "New York"); 
            pstmt.setString(4, "Los Angeles");  
            pstmt.setString(5, "2024-07-01 08:00:00");  
            pstmt.setString(6, "2024-07-01 11:00:00");  
            pstmt.setString(7, "199.99");   

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new flight was inserted successfully!");
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Query failed: " + e.getMessage());
        }
    }
     */

    /*
    public static void test_add_airplane(){
        dbconnection dbConn = new dbconnection();
        Connection conn = dbConn.connect();

        try {
            String sql = "INSERT into airplanes_table (airplane_ID, airplane_name, seat_capacity) VALUES (?, ?, ? );";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "1001");
            pstmt.setString(2, "Boeing 737"); 
            pstmt.setString(3, "180");  

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new airplane was inserted successfully!");
            }

        } catch (Exception e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }
        */

}
