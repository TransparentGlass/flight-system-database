package cc14;

import org.junit.Test;

import cc14.Databases.BookingDatabase;
import cc14.Databases.FlightDatabase;
import cc14.models.Booking;
import cc14.models.Flight;
import cc14.models.Passenger;

import static cc14.Databases.BookingDatabase.getFlightID;
import static cc14.Databases.BookingDatabase.getPassengerID;

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

    @Test(expected = NullPointerException.class)
    public void test_nullUserID() {
        Passenger p = null;
        BookingDatabase.getPassengerID(p);
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

        assert(BookingDatabase.CreateBooking(p, f, timestamp) == 1);
    }

}
