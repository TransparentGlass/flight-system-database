package cc14.Databases;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import cc14.UI.FlightTime;
import cc14.models.Booking;
import cc14.models.Flight;
import cc14.models.Passenger;

public class BookingDatabase {
    private static enum BookingStatus {
        ACTIVE,
        PROCESSING,
        CANCELLED
    }

    // this fails to function due to flights (other than MNL101) not being added yet
    public static Booking createBooking(Passenger p, Flight f) {
        String ts = new Timestamp(System.currentTimeMillis())
        .toLocalDateTime()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


        Booking booked_flight = new Booking(p, f, ts);

        // get passenger ID by username
        int passenger_id = getPassengerID(booked_flight.getPassenger());

        // get flight ID by flight number
        int flight_id = getFlightID(booked_flight.getFlight());
        try {
            
            Connection Database = DB_connection.connect();

            String sql = "INSERT INTO bookings_table (User_ID, flight_ID, booking_time, status) VALUES (?, ?, ?, ?)";
            String availableSeats_sql = "UPDATE flights_table SET available_seats = ? WHERE flight_id = ?";
            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setInt(1, passenger_id);
            pstmt.setInt(2, flight_id);
            pstmt.setString(3, ts);

            pstmt.setString(4, BookingStatus.ACTIVE.name());
            pstmt.executeUpdate();
            System.out.println("Booking added successfully.");

            pstmt = Database.prepareStatement(availableSeats_sql);
            pstmt.setInt(1, getFlight(flight_id).getAvailableSeats() - 1);
            pstmt.setInt(2, flight_id);
            pstmt.executeUpdate();
            System.out.println("Available seats--" + "AVAIALBLE SEATS = " + (getFlight(flight_id).getAvailableSeats() - 1));

            return booked_flight; // success

        } catch (SQLException e) {
            System.out.println("Error adding booking: " + e.getMessage());
        }

        return null; // failure
    }

    public static ArrayList<Booking> getBookingsFor(Passenger p) {

        ArrayList<Booking> list = new ArrayList<>();

        String sql = "SELECT * from bookings_table where user_id = (Select user_ID from users_table where full_name = ?) ";
        try {
            Connection Database = DB_connection.connect();
            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setString(1, p.getFullName());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int r_id = rs.getInt("reservation_ID");

                int f_id = rs.getInt("flight_ID");

                Flight f = getFlight(f_id);
                String ts = getTimestamp(r_id);

                Booking b = new Booking(p, f, ts);
                list.add(b);
                System.out.println(b.getFlight().getFlightNumber() + " " + b.getPassenger().getFullName());
            }
            return list;

        } catch (SQLException e) {
            System.out.println("No bookings or error: " + e.getMessage());
        }
        return null;
    }

    public static boolean cancelBooking(int reservation_ID) {

        String sql = "UPDATE bookings_table SET status = ? WHERE reservation_ID = ?";
        String availableSeats_sql = "UPDATE flights_table SET available_seats = ?  WHERE flight_id = (SELECT flight_id FROM bookings_table WHERE reservation_id = ?)";
        try {
            Connection Database = DB_connection.connect();
            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setString(1, BookingStatus.CANCELLED.name());
            pstmt.setInt(2, reservation_ID);
            pstmt.executeUpdate();

            pstmt = Database.prepareStatement(availableSeats_sql);
            pstmt.setInt(1, getAvailableSeatsFromBooking(reservation_ID) + 1);
            pstmt.setInt(2, reservation_ID);
            pstmt.executeUpdate();

            System.out.println("Successfully cancelled: " + reservation_ID);

            return true;

        } catch (SQLException e) {
            System.out.println("Error cancelling booking: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteBooking(int reservation_ID) {
        return false;
    }

    public static int getAvailableSeatsFromBooking(int reservation_ID) {

        String sql = "SELECT available_seats from flights_table where flight_ID = (SELECT flight_ID from bookings_table where reservation_ID = ?)";
        try {
            Connection Database = DB_connection.connect();

            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setInt(1, reservation_ID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Successfully retreived available seats");
                return rs.getInt("available_seats");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving passenger ID: " + e.getMessage());
        }

        return -1;
    }

    public static int findBooking(Passenger p, String flight_num, String timestamp) {

        String sql = "SELECT reservation_ID FROM bookings_table WHERE flight_ID = ? and user_ID = ? and booking_time = ?";

        try {
            Connection Database = DB_connection.connect();
            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setInt(1, getFlightID(flight_num));
            pstmt.setInt(2, getPassengerID(p));
            pstmt.setString(3, timestamp);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int reservation_ID = rs.getInt("reservation_ID");
                return reservation_ID;
            }

        } catch (SQLException e) {
            System.out.println("Error finding bookings: " + e.getMessage());
        }
        return -1;
    }

    public static ArrayList<Booking> getAllBookings() {

        ArrayList<Booking> list = new ArrayList<>();

        String sql = "SELECT * from bookings_table";
        try {
            Connection Database = DB_connection.connect();
            PreparedStatement pstmt = Database.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int p_id = rs.getInt("user_ID");
                int r_id = rs.getInt("reservation_ID");
                int f_id = rs.getInt("flight_ID");

                Flight f = getFlight(f_id);
                String ts = getTimestamp(r_id);
                Passenger p = getPassenger(p_id);

                Booking b = new Booking(p, f, ts);
                list.add(b);
                System.out.println(b.getFlight().getFlightNumber() + " " + b.getPassenger().getFullName());
            }
            return list;

        } catch (SQLException e) {
            System.out.println("No bookings or error: " + e.getMessage());
        }
        return null;
    }

    // conflict check
    // TODO: test hasConflict with the new implementation.
    public static boolean hasConflict(Passenger p, Flight newFlight) {
        ArrayList<Booking> existing = getBookingsFor(p);

        long newDep = FlightTime.toMillis(newFlight.getDepartureTime());
        long newArr = FlightTime.toMillis(newFlight.getArrivalTime());

        for (Booking b : existing) {
            Flight f = b.getFlight();

            long dep = FlightTime.toMillis(f.getDepartureTime());
            long arr = FlightTime.toMillis(f.getArrivalTime());

            boolean overlap = (newDep >= dep && newDep <= arr) ||
                    (newArr >= dep && newArr <= arr);

            if (overlap)
                return true;
        }

        return false;
    }

    public static int getPassengerID(Passenger p) {
        if (p == null)
            return -1;

        String sql = "SELECT User_ID FROM users_table WHERE username = ?";
        try {
            Connection Database = DB_connection.connect();

            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setString(1, p.getUsername());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("User_ID");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving passenger ID: " + e.getMessage());
        }

        return -1;

    }

    public static String getTimestamp(int reservation_ID) {
        try {
            Connection Database = DB_connection.connect();
            String sql = "Select * from bookings_table WHERE reservation_ID = ?";
            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setInt(1, reservation_ID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String time = rs.getString("booking_time");
                return time;
            }

        } catch (SQLException e) {
            System.out.println("No timestamp. " + e.getMessage());
        }

        return null;

    }

    public static Passenger getPassenger(int Passenger_ID) {
        try {
            Connection Database = DB_connection.connect();
            String sql = "Select * from users_table WHERE user_ID = ?";
            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setInt(1, Passenger_ID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String u = rs.getString("username");
                String pw = rs.getString("password");
                String fn = rs.getString("full_name");
                Passenger p = new Passenger(u, pw, fn);
                return p;
            }

        } catch (SQLException e) {
            System.out.println("No passengers/Error. " + e.getMessage());
        }

        return null;
    }

    public static Flight getFlight(int Flight_ID) {
        try {
            Connection Database = DB_connection.connect();
            String sql = "Select * from flights_table WHERE flight_ID = ?";
            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setInt(1, Flight_ID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String f_num = rs.getString("flight_number");
                String o = rs.getString("origin");
                String d = rs.getString("destination");
                String dt = rs.getString("departure_time");
                String at = rs.getString("arrival_time");
                int ts = rs.getInt("total_seats");
                int as = rs.getInt("available_seats");
                double bf = rs.getDouble("base_fare");
                String a = getAirplaneName(rs.getInt("airplane_ID"));
                Flight flight = new Flight(Flight_ID, f_num, o, d, dt, at, ts, as, bf, a);
                return flight;
            }

        } catch (SQLException e) {
            System.out.println("No passengers/Error. " + e.getMessage());
        }

        return null;
    }

    public static String getAirplaneName(int airplane_id) {
        try {
            Connection Database = DB_connection.connect();
            String sql = "Select * from airplanes_table WHERE airplane_ID = ?";
            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setInt(1, airplane_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Successfully returned airplane_name: " + rs.getString("airplane_name"));
                return rs.getString("airplane_name");
            }

        } catch (SQLException e) {
            System.out.println("No airplane_id detected. " + e.getMessage());
        }

        return null;
    }

    public static int getFlightID(Flight f) {

        if (f == null)
            return -1;

        String sql = "SELECT Flight_ID FROM Flights_table WHERE Flight_Number = ?";

        try {
            Connection Database = DB_connection.connect();
            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setString(1, f.getFlightNumber());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Flight_ID");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving passenger ID: " + e.getMessage());
        }

        return -1;
    }

    public static int getFlightID(String Flight_number) {
        if (Flight_number == null)
            return -1;

        String sql = "SELECT Flight_ID FROM Flights_table WHERE Flight_Number = ?";

        try {
            Connection Database = DB_connection.connect();
            PreparedStatement pstmt = Database.prepareStatement(sql);
            pstmt.setString(1, Flight_number);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Flight_ID");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving passenger ID: " + e.getMessage());
        }

        return -1;

    }
}
