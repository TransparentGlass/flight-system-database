package cc14.Databases;

import java.util.ArrayList;
import java.util.List;

import cc14.Flight;

public class FlightDatabase {

    //TODO: Change flights to SQL Database.
    private static final List<Flight> flights = new ArrayList<>();
    private static int nextFlightId = 1000;

    //auto assign airplanes if not specified
    private static final String[] AIRPLANES = {
            "Airbus A320",
            "Airbus A321 Neo",
            "Boeing 737-800",
            "Bombardier Q400",
            "ATR 72-600",
            "Airbus A330-300",
            "Boeing 777-300ER"
    };

    private static String randomAirplane() {
        return AIRPLANES[(int) (Math.random() * AIRPLANES.length)];
    }

    public static synchronized Flight addFlight(
            String flightNumber,
            String origin,
            String destination,
            String departureTime,
            String arrivalTime,
            int seats,
            double price
    ) {
        int id = nextFlightId++;

        Flight f = new Flight(
                id,
                flightNumber,
                origin,
                destination,
                departureTime,
                arrivalTime,
                seats,
                seats,
                price,
                randomAirplane()
        );

        flights.add(f);
        return f;
    }

    public static List<Flight> getAllFlights() {
    return flights;
}


    public static Flight getFlightById(int id) {
        for (Flight f : flights) {
            if (f.getFlightId() == id)
                return f;
        }
        return null;
    }

    public static Flight findFlight(String flightNumber) {
        for (Flight f : flights) {
            if (f.getFlightNumber().equalsIgnoreCase(flightNumber))
                return f;
        }
        return null;
    }

    public static List<Flight> searchByOrigin(String origin) {
        ArrayList<Flight> list = new ArrayList<>();
        for (Flight f : flights) {
            if (f.getOrigin().equalsIgnoreCase(origin))
                list.add(f);
        }
        return list;
    }
    
    public static boolean editFlight(
            int flightId,
            String flightNumber,
            String origin,
            String destination,
            String departureTime,
            String arrivalTime,
            int totalSeats,
            double price
    ) {
        Flight f = getFlightById(flightId);
        if (f == null) return false;

        int alreadyBooked = f.getTotalSeats() - f.getAvailableSeats();
        if (totalSeats < alreadyBooked) return false;

        f.setFlightNumber(flightNumber);
        f.setOrigin(origin);
        f.setDestination(destination);
        f.setDepartureTime(departureTime);
        f.setArrivalTime(arrivalTime);
        f.setPrice(price);

        int diff = totalSeats - f.getTotalSeats();
        f.setTotalSeats(totalSeats);
        f.setAvailableSeats(f.getAvailableSeats() + diff);

        return true;
    }

    public static boolean deleteFlight(String flightNumber) {
        Flight f = findFlight(flightNumber);
        if (f == null) return false;
        return flights.remove(f);
    }

    //Default flgihts
    static {
        // MANILA
        addFlight("MNL101", "Manila", "Cebu", "2025-12-01 08:30", "2025-12-01 10:05", 180, 3200);
        addFlight("MNL102", "Manila", "Davao", "2025-12-01 13:45", "2025-12-01 15:40", 180, 4500);
        addFlight("MNL103", "Manila", "Iloilo", "2025-12-02 11:00", "2025-12-02 12:10", 180, 2300);

        // CEBU
        addFlight("CEB201", "Cebu", "Manila", "2025-12-01 09:00", "2025-12-01 10:30", 150, 3000);
        addFlight("CEB202", "Cebu", "Cagayan de Oro", "2025-12-01 14:00", "2025-12-01 15:00", 150, 2500);
        addFlight("CEB203", "Cebu", "Davao", "2025-12-02 10:20", "2025-12-02 11:10", 150, 2800);

        // CDO
        addFlight("CDO301", "Cagayan de Oro", "Cebu", "2025-12-01 12:20", "2025-12-01 13:00", 100, 2400);
        addFlight("CDO302", "Cagayan de Oro", "Manila", "2025-12-02 15:00", "2025-12-02 16:30", 100, 3500);

        // DAVAO
        addFlight("DVO401", "Davao", "Cebu", "2025-12-01 06:40", "2025-12-01 07:30", 160, 2800);
        addFlight("DVO402", "Davao", "Manila", "2025-12-01 17:00", "2025-12-01 19:00", 160, 4200);

        // ILOILO
        addFlight("ILO501", "Iloilo", "Manila", "2025-12-01 13:30", "2025-12-01 14:40", 140, 2600);
        addFlight("ILO502", "Iloilo", "Cebu", "2025-12-02 09:00", "2025-12-02 09:40", 140, 1900);

        // // BACOLOD
        // addFlight("BCD601", "Bacolod", "Manila", "2025-12-01 08:10", "2025-12-01 09:20", 150, 2800);
        // addFlight("BCD602", "Bacolod", "Cebu", "2025-12-02 07:50", "2025-12-02 08:30", 150, 1800);

        // CATICLAN
        // addFlight("CAT701", "Caticlan", "Manila", "2025-12-01 16:10", "2025-12-01 17:20", 120, 3400);
        // addFlight("CAT702", "Caticlan", "Cebu", "2025-12-02 10:40", "2025-12-02 11:30", 120, 2500);

        // CLARK
        addFlight("CRK801", "Clark", "Cebu", "2025-12-01 15:00", "2025-12-01 16:30", 180, 3100);
        addFlight("CRK802", "Clark", "Davao", "2025-12-01 06:20", "2025-12-01 08:10", 180, 4500);

        // ZAMBOANGA
        addFlight("ZAM901", "Zamboanga", "Manila", "2025-12-01 12:00", "2025-12-01 14:00", 150, 3900);
        addFlight("ZAM902", "Zamboanga", "Cebu", "2025-12-02 18:00", "2025-12-02 19:00", 150, 2400);

        // BOHOL
        addFlight("TAG1001", "Bohol", "Manila", "2025-12-01 07:30", "2025-12-01 08:45", 130, 3300);
        addFlight("TAG1002", "Bohol", "Cebu", "2025-12-01 10:30", "2025-12-01 11:00", 130, 1500);
    }
}
