package cc14;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import cc14.Databases.BookingDatabase;
import cc14.Databases.FlightDatabase;
import cc14.models.Booking;
import cc14.models.Flight;
import cc14.models.Passenger;

import static cc14.Databases.BookingDatabase.cancelBooking;
import static cc14.Databases.BookingDatabase.createBooking;
import static cc14.Databases.BookingDatabase.getAirplaneName;
import static cc14.Databases.BookingDatabase.getBookingsFor;
import static cc14.Databases.BookingDatabase.getFlight;
import static cc14.Databases.BookingDatabase.getFlightID;
import static cc14.Databases.BookingDatabase.getPassenger;
import static cc14.Databases.BookingDatabase.getPassengerID;
import static cc14.Databases.BookingDatabase.getTimestamp;

public class bookingDatabaseTest {

    @Test
    public void test_getUserID() {
        Passenger p = new Passenger("default", "strongpassword", "AJ Thomas J. Sualan");
        BookingDatabase.getPassengerID(p);
        assert (getPassengerID(p) == 1);
    }

    @Test
    public void test_invalidUserID() {
        Passenger p = new Passenger("nonexistent", "nopassword", "No Name");
        BookingDatabase.getPassengerID(p);
        assert (getPassengerID(p) == -1);
    }

    @Test
    public void test_nullUserID() {
        Passenger p = null;
        BookingDatabase.getPassengerID(p);
        assert(getPassengerID(p) == -1);
    }

    @Test
    public void test_emptyUsernameUserID() {
        Passenger p = new Passenger("", "", "");
        BookingDatabase.getPassengerID(p);
        assert (getPassengerID(p) == -1);
    }

    @Test
    public void test_specialCharUsernameUserID() {
        Passenger p = new Passenger("user!@#", "pass$%^", "Name*&^");
        BookingDatabase.getPassengerID(p);
        assert (getPassengerID(p) == -1);
    }

    @Test
    public void test_getFlightID() {
        Flight f = FlightDatabase.findFlight("MNL101");
        assert (getFlightID(f) == 1002);
    }

    @Test
    public void test_invalidFlightID() {
        Flight f = FlightDatabase.findFlight("INVALID123");
        assert (getFlightID(f) == -1);
    }

    @Test
    public void test_nullFlightID() {
        Flight f = null;
        assert (getFlightID(f) == -1);
    }

    @Test
    public void test_CreateBooking() {
        Passenger p = new Passenger("default", "strongpassword", "AJ Thomas J. Sualan");
        Flight f = FlightDatabase.findFlight("MNL101");
        String timestamp = "2024-06-01 10:00:00";
        int booking = createBooking(p, f);
        assert(booking == 1);

    }

    @Test
    public void test_cancelBooking(){
        int id = 9;
        assertTrue(cancelBooking(id));
    }

    @Test
    public void test_getairiplanename(){
        assert(getAirplaneName(1001).equals("Boeing 737"));
        
    }

    @Test
    public void test_getFlight(){
        assert(getFlight(1002).getFlightNumber().equals("MNL101"));
    }

    @Test
    public void test_getTimestamps(){
        assert(getTimestamp(3).equals("2025-12-11 22:18:01"));
    }

    @Test
    public void test_getPassenger(){
        assert(getPassenger(1).getFullName().equals("AJ Thomas J. Sualan"));
    }

    @Test
    public void test_getbookingsFor(){
        ArrayList<Booking> b = getBookingsFor(new Passenger("default", "strongpassword", "AJ Thomas J. Sualan"));
        for (Booking bookings: b){
            assert(bookings.getPassenger().getFullName().equals("AJ Thomas J. Sualan"));
            assert(bookings.getFlight().getFlightNumber().equals("MNL101"));
            assert(bookings.getTimestamp().equals("2025-12-11 22:18:01") || bookings.getTimestamp().equals("2025-12-12 00:22:57"));
        }
    }

}
