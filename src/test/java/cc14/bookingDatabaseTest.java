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
        int id = BookingDatabase.getFlightID("MNL101");
        Flight f = getFlight(id);
        assert (getFlightID(f) == 1002);
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
        int id= BookingDatabase.getFlightID("MNL101");
        Flight f = FlightDatabase.getFlight(id);
        String timestamp = "2024-06-01 10:00:00";
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
        assert(getFlight(1002).getFlightNumber().equals("MNL101"));
    }

    @Test
    public void test_getTimestamps(){
        assert(getTimestamp(12).equals("2025-12-12 03:26:50"));
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

    @Test
    public void test_getBookings(){
        ArrayList<Booking> b = getAllBookings();

        for (Booking x: b){
            assert(x.getPassenger().getFullName().equals("AJ Thomas J. Sualan"));
        }

    }

    @Test
    public void test_findBookings(){
        Passenger p = new Passenger("default", "strongpassword", "AJ Thomas J. Sualan");
        int i = BookingDatabase.getFlightID("MNL101");
        Flight f = FlightDatabase.getFlight(i);
        String fl_num = "MNL101";
        String timestamp = "2025-12-12 03:26:50";
        int reservation_id = findBooking(p, fl_num, timestamp);
        System.out.print(reservation_id);
        assert(reservation_id == 12);

    }

    @Test
    public void test_createFlights(){
         createFlight("MNL102", "Manila", "Davao", "2025-12-01 13:45", "2025-12-01 15:40", 180, 4500);
        createFlight("MNL103", "Manila", "Iloilo", "2025-12-02 11:00", "2025-12-02 12:10", 180, 2300);

        // CEBU
        createFlight("CEB201", "Cebu", "Manila", "2025-12-01 09:00", "2025-12-01 10:30", 150, 3000);
        createFlight("CEB202", "Cebu", "Cagayan de Oro", "2025-12-01 14:00", "2025-12-01 15:00", 150, 2500);
        createFlight("CEB203", "Cebu", "Davao", "2025-12-02 10:20", "2025-12-02 11:10", 150, 2800);

        // CDO
        createFlight("CDO301", "Cagayan de Oro", "Cebu", "2025-12-01 12:20", "2025-12-01 13:00", 100, 2400);
        createFlight("CDO302", "Cagayan de Oro", "Manila", "2025-12-02 15:00", "2025-12-02 16:30", 100, 3500);

        // DAVAO
        createFlight("DVO401", "Davao", "Cebu", "2025-12-01 06:40", "2025-12-01 07:30", 160, 2800);
        createFlight("DVO402", "Davao", "Manila", "2025-12-01 17:00", "2025-12-01 19:00", 160, 4200);

        // ILOILO
        createFlight("ILO501", "Iloilo", "Manila", "2025-12-01 13:30", "2025-12-01 14:40", 140, 2600);
        createFlight("ILO502", "Iloilo", "Cebu", "2025-12-02 09:00", "2025-12-02 09:40", 140, 1900);

        // // BACOLOD
        // createFlight("BCD601", "Bacolod", "Manila", "2025-12-01 08:10", "2025-12-01
        // 09:20", 150, 2800);
        // createFlight("BCD602", "Bacolod", "Cebu", "2025-12-02 07:50", "2025-12-02
        // 08:30", 150, 1800);

        // CATICLAN
        // createFlight("CAT701", "Caticlan", "Manila", "2025-12-01 16:10", "2025-12-01
        // 17:20", 120, 3400);
        // createFlight("CAT702", "Caticlan", "Cebu", "2025-12-02 10:40", "2025-12-02
        // 11:30", 120, 2500);

        // CLARK
        createFlight("CRK801", "Clark", "Cebu", "2025-12-01 15:00", "2025-12-01 16:30", 180, 3100);
        createFlight("CRK802", "Clark", "Davao", "2025-12-01 06:20", "2025-12-01 08:10", 180, 4500);

        // ZAMBOANGA
        createFlight("ZAM901", "Zamboanga", "Manila", "2025-12-01 12:00", "2025-12-01 14:00", 150, 3900);
        createFlight("ZAM902", "Zamboanga", "Cebu", "2025-12-02 18:00", "2025-12-02 19:00", 150, 2400);

        // BOHOL
        createFlight("TAG1001", "Bohol", "Manila", "2025-12-01 07:30", "2025-12-01 08:45", 130, 3300);
        createFlight("TAG1002", "Bohol", "Cebu", "2025-12-01 10:30", "2025-12-01 11:00", 130, 1500);
    }

    // @Test
    // public void addplanes(){
    //     assertTrue(FlightDatabase.addAirplanes());
    // }

}
