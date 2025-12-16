package cc14.Databases;

import static cc14.Databases.BookingDatabase.getAirplaneName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import cc14.models.Booking;
import cc14.models.Flight;
import cc14.models.Passenger;

public class FlightDatabase {

    // TODO: Change flights to SQL Database.
    private static Connection db;

    // auto assign airplanes if not specified
    private static final Integer[] AIRPLANES = {
            1001,
1002,
1003,
1004,
1005,
1006,
1007
    };

    private static int randomAirplane() {
        return AIRPLANES[(int) (Math.random() * AIRPLANES.length)];
    }

    public static synchronized int createFlight(
            String flightNumber,
            String origin,
            String destination,
            String departureTime,
            String arrivalTime,
            int seats,
            double price) {
        try {
            db = DB_connection.connect();
            db.setAutoCommit(false); // 🚨 START TRANSACTION

            String insertSql = "INSERT INTO flights_table (origin, destination, departure_time, arrival_time, base_fare, flight_number, available_seats, total_seats, airplane_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = db.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

                insertStmt.setString(1, origin);
                insertStmt.setString(2, destination);
                insertStmt.setString(3, departureTime);
                insertStmt.setString(4, arrivalTime);
                insertStmt.setDouble(5, price);
                insertStmt.setString(6, flightNumber);
                insertStmt.setInt(7, seats);
                insertStmt.setInt(8, seats);
                insertStmt.setInt(9, randomAirplane());
                insertStmt.executeUpdate();
                 db.commit();

                ResultSet keys = insertStmt.getGeneratedKeys();
                
                if (keys.next()){
                    return keys.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Create flight failed: " + e.getMessage());
            if (db != null) {
                try {
                    db.rollback(); // ✅ REQUIRED
                } catch (SQLException ignored) {
                    return -1;
                }
            }
        }
        return -1;
        
    }

    // public static synchronized Flight createFlight(
    // String flightNumber,
    // String origin,
    // String destination,
    // String departureTime,
    // String arrivalTime,
    // int seats,
    // double price
    // ) {
    // int id = nextFlightId++;

    // Flight f = new Flight(
    // id,
    // flightNumber,
    // origin,
    // destination,
    // departureTime,
    // arrivalTime,
    // seats,
    // seats,
    // price,
    // randomAirplane()
    // );

    // flights.add(f);
    // return f;
    // }

    public static List<Integer> getAllFlights() {
        try (Connection db = DB_connection.connect()) {
            String getFlightID = "Select flight_ID FROM flights_table";
            try (PreparedStatement ps = db.prepareStatement(getFlightID)) {
                ResultSet rs = ps.executeQuery();
                List<Integer> list = new ArrayList<>();
                while (rs.next()) {
                    int FlightID = rs.getInt("flight_ID");
                    list.add(FlightID);

                }
                return list;
            }

        } catch (SQLException e) {
            System.out.println("Error getting flights: " + e.getMessage());
        }
        return null;
    }

    public static int findFlightByFlightNum(String flightNum) {

        try (Connection db = DB_connection.connect()) {
            String getFlightID = "Select flight_ID FROM flights_table WHERE flight_number = ?";
            try (PreparedStatement flight_id_Stmt = db.prepareStatement(getFlightID)) {

                flight_id_Stmt.setString(1, flightNum);

                ResultSet rs = flight_id_Stmt.executeQuery();
                if (rs.next()) {
                    int FlightID = rs.getInt("flight_ID");
                    return FlightID;

                }
            }

        } catch (SQLException e) {
            System.out.println("Error getting flight: " + e.getMessage());
        }
        return -1;

    }

    public static Flight getFlight(int id) {
        try (Connection db = DB_connection.connect()) {
            String getFlightID = "Select * FROM flights_table WHERE flight_ID = ?";
            try (PreparedStatement flight_id_Stmt = db.prepareStatement(getFlightID)) {

                flight_id_Stmt.setInt(1, id);

                ResultSet rs = flight_id_Stmt.executeQuery();
                if (rs.next()) {

                    int flight_ID = rs.getInt("flight_ID");
                    String origin = rs.getString("origin");
                    String destination = rs.getString("destination");
                    String departure_time = rs.getString("departure_time");
                    String arrival_time = rs.getString("arrival_time");
                    Double base_fare = rs.getDouble("base_fare");
                    String flight_number = rs.getString("flight_number");
                    int available_seats = rs.getInt("available_seats");
                    int total_seats = rs.getInt("total_seats");
                    int airplane = rs.getInt("airplane_ID");

                    Flight flight = new Flight(flight_ID, flight_number, origin, destination, departure_time,
                            arrival_time, total_seats, available_seats, base_fare, getAirplaneName(airplane));
                    return flight;

                }
            }

        } catch (SQLException e) {
            System.out.println("Error getting flight: " + e.getMessage());
        }
        return null;
    }

    public String getAirplane(int id){
        try {
            db = DB_connection.connect();
            String sql = "Select airplane_name from airplanes_table where airplane_ID = ?";
            try (PreparedStatement ps = db.prepareStatement(sql)){
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()){
                    return rs.getString("airplane_name");
                }
            }
        } catch (SQLException e){
            System.out.println("Error finding airplane: " + e.getMessage());
        }
        return null;
    }

    // public static Flight getFlightById(int id) {
    // for (Flight f : flights) {
    // if (f.getFlightId() == id)
    // return f;
    // }
    // return null;
    // }

    // public static Flight findFlight(String flightNumber) {
    // for (Flight f : flights) {
    // if (f.getFlightNumber().equalsIgnoreCase(flightNumber))
    // return f;
    // }
    // return null;
    // }

    // public static List<Flight> searchByOrigin(String origin) {
    // ArrayList<Flight> list = new ArrayList<>();
    // for (Flight f : flights) {
    // if (f.getOrigin().equalsIgnoreCase(origin))
    // list.add(f);
    // }
    // return list;
    // }

    public static List<Integer> findFlightsByOrigin(String origin) {

        try (Connection db = DB_connection.connect()) {
            String getFlightID = "Select flight_ID FROM flights_table WHERE origin = ?";
            try (PreparedStatement flight_id_Stmt = db.prepareStatement(getFlightID)) {
                List<Integer> list = new ArrayList<Integer>();

                flight_id_Stmt.setString(1, origin);

                ResultSet rs = flight_id_Stmt.executeQuery();
                while (rs.next()) {
                    int FlightID = rs.getInt("flight_ID");
                    list.add(FlightID);
                }

                return list;
            }

        } catch (SQLException e) {
            System.out.println("Error getting flight: " + e.getMessage());
        }
        return null;

    }

    public static boolean editFlight(
            int flightId,
            String flightNumber,
            String origin,
            String destination,
            String departureTime,
            String arrivalTime,
            int totalSeats,
            double price) {
        Flight f = getFlight(flightId);
        if (f == null)
            return false;

        int alreadyBooked = f.getTotalSeats() - f.getAvailableSeats();
        if (totalSeats < alreadyBooked)
            return false;

        int newAvailableSeats = totalSeats - alreadyBooked;
        try {
            Connection db = DB_connection.connect();
            db.setAutoCommit(false);
            String updateFlightSql = "UPDATE flights_table\n" + //
                    "SET\n" + //
                    "  origin = ?,\n" + //
                    "  destination = ?,\n" + //
                    "  departure_time = ?,\n" + //
                    "  arrival_time = ?,\n" + //
                    "  available_seats = ?," + //
                    "  total_seats = ?,\n" + //
                    "  base_fare = ?,\n" + //
                    "  flight_number = ?" + //
                    "WHERE flight_ID = ?;\n" + //
                    "";
            try (PreparedStatement ps = db.prepareStatement(updateFlightSql)) {
                ps.setString(1, origin);
                ps.setString(2, destination);
                ps.setString(3, departureTime);
                ps.setString(4, arrivalTime);
                ps.setInt(5, newAvailableSeats );
                ps.setInt(6, totalSeats);
                ps.setDouble(7, price);
                ps.setString(8, flightNumber);
                
                ps.setInt(9, flightId);
                int rows = ps.executeUpdate();

                if (rows == 0){
                    db.rollback();
                    return false;
                }
            }

            db.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Create flight failed: " + e.getMessage());
            if (db != null) {
                try {
                    db.rollback(); // ✅ REQUIRED
                } catch (SQLException ignored) {
                }
            }
            return false;

        }

    }

    public static synchronized boolean deleteFlight(String flightNumber) {
        String sql = "DELETE FROM flights_table WHERE flight_number = ?";

        try (Connection db = DB_connection.connect();
                PreparedStatement ps = db.prepareStatement(sql)) {

            db.setAutoCommit(false);

            ps.setString(1, flightNumber);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                db.commit();
                return true;
            } else {
                db.rollback(); // no flight found
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}