package cc14.models;

public class Booking {

    private Passenger passenger;
    private Flight flight;
    private String timestamp;

    public Booking(Passenger passenger, Flight flight, String timestamp) {
        this.passenger = passenger;
        this.flight = flight;
        this.timestamp = timestamp;
    }

    public Passenger getPassenger() { return passenger; }
    public Flight getFlight() { return flight; }
    public String getTimestamp() { return timestamp; }
}
