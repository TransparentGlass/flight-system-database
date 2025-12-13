package cc14.UI;

import static cc14.Databases.BookingDatabase.createBooking;


import javax.swing.*;

import cc14.Databases.BookingDatabase;
import cc14.models.Booking;
import cc14.models.Flight;
import cc14.models.Passenger;




public class BookFlightWindow extends JFrame {

    public BookFlightWindow(Passenger passenger, Flight flight) {
        Theme.applyNimbus();
        setTitle("Book Flight");
        setSize(500, 340);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Confirm Booking");
        title.setFont(Theme.HEAD_FONT);
        title.setBounds(160, 20, 250, 40);
        add(title);

        JLabel info = new JLabel("<html><b>" + flight.getFlightNumber() + "</b><br>" +
                flight.getOrigin() + " → " + flight.getDestination() + "<br>" +
                "Time: " + flight.getDepartureTime() + " - " + flight.getArrivalTime() + "<br>" +
                "Aircraft: " + flight.getAirplane() + "<br>" +
                "Price: ₱" + flight.getPrice() +
                "</html>");
        info.setFont(Theme.TEXT_FONT);
        info.setBounds(60, 80, 380, 120);
        add(info);

        JButton confirmBtn = new JButton("Confirm Booking");
        confirmBtn.setBounds(150, 220, 200, 40);
        ButtonStyler.style(confirmBtn);
        add(confirmBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(20, 20, 100, 35);
        ButtonStyler.style(backBtn);
        add(backBtn);

        backBtn.addActionListener(e -> dispose());

        confirmBtn.addActionListener(e -> {
            if (BookingDatabase.hasConflict(passenger, flight)) {
                JOptionPane.showMessageDialog(this,
                        "You already have a flight that overlaps this schedule.\n" +
                        "Booking blocked to avoid conflicts.",
                        "Flight Conflict",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!flight.decreaseAvailableSeat()) {
                JOptionPane.showMessageDialog(this,
                        "No available seats on this flight.",
                        "Fully Booked",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Booking book = createBooking(passenger, flight);

            JOptionPane.showMessageDialog(this,
                    "Booking successful!\nTimestamp: " + book.getTimestamp(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();
        });

        setVisible(true);
    }
}
