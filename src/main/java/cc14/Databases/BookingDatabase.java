package cc14.Databases;

import java.util.ArrayList;

import cc14.UI.FlightTime;
import cc14.models.Booking;
import cc14.models.Flight;
import cc14.models.Passenger;

public class BookingDatabase {
    //TODO: add status (e.g., active, canceled)
    private static final ArrayList<Booking> bookings = new ArrayList<>();

    public static void addBooking(Booking b) {
        bookings.add(b);
    }

    public static ArrayList<Booking> getBookingsFor(Passenger p) {
        ArrayList<Booking> list = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.getPassenger() == p)
                list.add(b);
        }
        return list;
    }

    public static boolean cancelBooking(Passenger p, String flightNumber) {
        Booking target = null;
        for (Booking b : bookings) {
            if (b.getPassenger() == p &&
                b.getFlight().getFlightNumber().equalsIgnoreCase(flightNumber)) {
                target = b;
                break;
            }
        }

        if (target != null) {
            target.getFlight().increaseAvailableSeat();
            bookings.remove(target);
            return true;
        }

        return false;
    }

    public static ArrayList<Booking> getAllBookings() {
        return bookings;
    }


   //conflict check
    public static boolean hasConflict(Passenger p, Flight newFlight) {  
        ArrayList<Booking> existing = getBookingsFor(p);

        long newDep = FlightTime.toMillis(newFlight.getDepartureTime());
        long newArr = FlightTime.toMillis(newFlight.getArrivalTime());

        for (Booking b : existing) {
            Flight f = b.getFlight();

            long dep = FlightTime.toMillis(f.getDepartureTime());
            long arr = FlightTime.toMillis(f.getArrivalTime());

            boolean overlap =
                    (newDep >= dep && newDep <= arr) ||
                    (newArr >= dep && newArr <= arr);

            if (overlap)
                return true;
        }

        return false;
    }
}
