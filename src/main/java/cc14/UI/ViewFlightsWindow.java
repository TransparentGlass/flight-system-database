package cc14.UI;

import javax.swing.*;

import cc14.Databases.FlightDatabase;
import cc14.models.Flight;

import java.awt.*;
import java.util.ArrayList;

public class ViewFlightsWindow extends JFrame {

    public ViewFlightsWindow() {
        Theme.applyNimbus();
        setTitle("All Flights Created");
        setSize(750, 520);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("All Flights Created");
        title.setFont(Theme.HEAD_FONT);
        title.setBounds(260, 20, 300, 40);
        add(title);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(Theme.TEXT_FONT);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(40, 80, 660, 360);
        add(scroll);

        JButton closeBtn = new JButton("Close");
        closeBtn.setBounds(20, 20, 100, 35);
        ButtonStyler.style(closeBtn);
        add(closeBtn);

        closeBtn.addActionListener(e -> dispose());

        java.util.List<Flight> flights = FlightDatabase.getAllFlights();

        if (flights.isEmpty()) {
            area.append("No flights have been created.\n");
        } else {
            for (Flight f : flights) {
                area.append("Flight No: " + f.getFlightNumber() + "\n");
                area.append("From: " + f.getOrigin() + "\n");
                area.append("To:   " + f.getDestination() + "\n");
                area.append("Time: " + f.getDepartureTime() + " - " + f.getArrivalTime() + "\n");
                area.append("Aircraft: " + f.getAirplane() + "\n");
                area.append("Seats: " + f.getAvailableSeats() + "/" + f.getTotalSeats() + "\n");
                area.append("--------------------------------------------\n");
            }
        }

        setVisible(true);
    }
}
