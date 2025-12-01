package cc14;
public class Flight {

    private int flightId;
    private String flightNumber;
    private String origin;
    private String destination;
    private String departureTime;  
    private String arrivalTime;
    private int totalSeats;
    private int availableSeats;
    private double price;
    private String airplane;        

    public Flight(int flightId, String flightNumber, String origin, String destination,
                  String departureTime, String arrivalTime,
                  int totalSeats, int availableSeats, double price, String airplane) {

        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.price = price;
        this.airplane = airplane;
    }

    public int getFlightId() { return flightId; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String s) { this.flightNumber = s; }

    public String getOrigin() { return origin; }
    public void setOrigin(String s) { this.origin = s; }

    public String getDestination() { return destination; }
    public void setDestination(String s) { this.destination = s; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String s) { this.departureTime = s; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String s) { this.arrivalTime = s; }

    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int i) { this.totalSeats = i; }

    public int getAvailableSeats() { return availableSeats; }
        public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public boolean decreaseAvailableSeat() {
    if (availableSeats > 0) {
        availableSeats--;
        return true;
    }
    return false;
    }

public void increaseAvailableSeat() {
    if (availableSeats < totalSeats) availableSeats++;
}


    public double getPrice() { return price; }
    public void setPrice(double p) { this.price = p; }

    public String getAirplane() { return airplane; }
    public void setAirplane(String a) { this.airplane = a; }

    @Override
    public String toString() {
        return flightNumber + " (" + origin + " → " + destination + 
               ") | " + departureTime + " – " + arrivalTime +
               " | Seats: " + availableSeats + "/" + totalSeats +
               " | Aircraft: " + airplane;
    }
}
