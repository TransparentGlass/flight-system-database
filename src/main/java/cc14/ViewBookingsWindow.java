package cc14;

import java.util.ArrayList;
import javax.swing.*;

import cc14.Databases.BookingDatabase;

public class ViewBookingsWindow extends JFrame {

    public ViewBookingsWindow(Passenger passenger) {
        Theme.applyNimbus();
        setTitle("My Bookings");
        setSize(700, 500);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("My Bookings");
        title.setFont(Theme.HEAD_FONT);
        title.setBounds(270, 20, 200, 40);
        add(title);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(Theme.TEXT_FONT);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(50, 80, 600, 320);
        add(scroll);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(20, 20, 100, 35);
        ButtonStyler.style(backBtn);
        add(backBtn);

        backBtn.addActionListener(e -> dispose());

        ArrayList<Booking> list = BookingDatabase.getBookingsFor(passenger);

        if (list.isEmpty()) {
            area.append("You have no bookings.\n");
        } else {
            for (Booking b : list) {
                Flight f = b.getFlight();
                area.append("Passenger: " + passenger.getFullName() + "\n");
                area.append("Flight: " + f.getFlightNumber() + " (" +
                        f.getOrigin() + " → " + f.getDestination() + ")\n");
                area.append("Time: " + f.getDepartureTime() + " - " + f.getArrivalTime() + "\n");
                area.append("Aircraft: " + f.getAirplane() + "\n");
                area.append("Booked at: " + b.getTimestamp() + "\n");
                area.append("-----------------------------------------------\n");
            }
        }

        setVisible(true);
    }

    public ViewBookingsWindow() {
        Theme.applyNimbus();
        setTitle("All Bookings (Admin)");
        setSize(700, 500);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("All Bookings");
        title.setFont(Theme.HEAD_FONT);
        title.setBounds(270, 20, 200, 40);
        add(title);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(Theme.TEXT_FONT);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(50, 80, 600, 320);
        add(scroll);

        JButton closeBtn = new JButton("Close");
        closeBtn.setBounds(20, 20, 100, 35);
        ButtonStyler.style(closeBtn);
        add(closeBtn);

        closeBtn.addActionListener(e -> dispose());

        ArrayList<Booking> list = BookingDatabase.getAllBookings();

        if (list.isEmpty()) {
            area.append("No bookings in the system.\n");
        } else {
            for (Booking b : list) {
                Passenger p = b.getPassenger();
                Flight f = b.getFlight();
                area.append("Passenger: " + p.getFullName() + " (" + p.getUsername() + ")\n");
                area.append("Flight: " + f.getFlightNumber() + " (" +
                        f.getOrigin() + " → " + f.getDestination() + ")\n");
                area.append("Time: " + f.getDepartureTime() + " - " + f.getArrivalTime() + "\n");
                area.append("Aircraft: " + f.getAirplane() + "\n");
                area.append("Booked at: " + b.getTimestamp() + "\n");
                area.append("-----------------------------------------------\n");
            }
        }

        setVisible(true);
    }
}
