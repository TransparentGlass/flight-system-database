package cc14.Databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



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


}
