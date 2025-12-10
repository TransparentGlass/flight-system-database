package cc14.Databases;

import java.util.ArrayList;

import cc14.models.Passenger;

//This is passenger login databse

public class PassengerDatabase {

    private static final ArrayList<Passenger> passengers = new ArrayList<>();

    public static boolean register(String username, String password, String fullName) {
        if (getPassenger(username) != null)
            return false; // username exists

        passengers.add(new Passenger(username, password, fullName));
        return true;
    }

    public static Passenger validatePassenger(String username, String password) {
        for (Passenger p : passengers) {
            if (p.getUsername().equalsIgnoreCase(username)
                    && p.getPassword().equals(password)) {
                return p;
            }
        }
        return null;
    }

    public static Passenger getPassenger(String username) {
        for (Passenger p : passengers) {
            if (p.getUsername().equalsIgnoreCase(username))
                return p;
        }
        return null;
    }
}
