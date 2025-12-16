package cc14;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import cc14.Databases.BookingDatabase;
import cc14.Databases.FlightDatabase;
import cc14.Databases.BookingDatabase;
import cc14.models.Booking;
import cc14.models.Flight;
import cc14.models.Passenger;

import static cc14.Databases.BookingDatabase.findBooking;
import static cc14.Databases.BookingDatabase.createBooking;
import static cc14.Databases.BookingDatabase.getAirplaneName;
import static cc14.Databases.BookingDatabase.getAllBookings;
import static cc14.Databases.BookingDatabase.getBookingsFor;
import static cc14.Databases.BookingDatabase.getFlight;
import static cc14.Databases.BookingDatabase.getFlightID;
import static cc14.Databases.BookingDatabase.getPassenger;
import static cc14.Databases.BookingDatabase.getPassengerID;
import static cc14.Databases.BookingDatabase.getTimestamp;
import static cc14.Databases.BookingDatabase.cancelBooking;
import static cc14.Databases.FlightDatabase.createFlight;

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
        int id = BookingDatabase.getFlightID("MNL102");
        Flight f = getFlight(id);
        assert (getFlightID(f) == 1004);
    }

    @Test
    public void test_invalidFlightID() {
        int i = BookingDatabase.getFlightID("INVALID123");
        assert (i == -1);
    }

    @Test
    public void test_nullFlightID() {
        Flight f = null;
        assert (getFlightID(f) == -1);
    }

    @Test
    public void test_CreateBooking() {
        Passenger p = new Passenger("default", "strongpassword", "AJ Thomas J. Sualan");
        int id= BookingDatabase.getFlightID("MNL102");
        Flight f = FlightDatabase.getFlight(id);
        Booking booking = createBooking(p, f);
        assert(booking != null);

    }

    @Test
    public void test_cancelBooking(){
        int id = 12;
        assertTrue(cancelBooking(id));
    }

    @Test
    public void test_getairiplanename(){
        assert(getAirplaneName(1001).equals("Boeing 737"));
        
    }

    @Test
    public void test_getFlight(){
        assert(getFlight(1004).getFlightNumber().equals("MNL102"));
    }

    @Test
    public void test_getTimestamps(){
        assert(getTimestamp(17).equals("2025-12-16 19:18:08"));
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
            assert(bookings.getFlight().getFlightNumber().equals("MNL102"));

        }
    }

    @Test
    public void test_getBookings(){
        ArrayList<Booking> b = getAllBookings();

        for (Booking x: b){
            assert(x != null);
        }

    }

    @Test
    public void test_findBookings(){
        Passenger p = new Passenger("default", "strongpassword", "AJ Thomas J. Sualan");
        String fl_num = "MNL102";
        String timestamp = "2025-12-16 21:52:32";
        int reservation_id = findBooking(p, fl_num, timestamp);
        System.out.print(reservation_id);
        assert(reservation_id == 21);

    }

    @Test
    public void test_createFlights(){

        assertTrue(createFlight("TEST", "TEST", "TEST", "2025-12-01 10:30", "2025-12-01 11:00", 130, 1500) != -1);
    }

    // @Test
    // public void addplanes(){
    //     assertTrue(FlightDatabase.addAirplanes());
    // }

}
