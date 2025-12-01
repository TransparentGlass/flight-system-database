package cc14.UI;

import javax.swing.*;

import cc14.models.Passenger;

public class PassengerDashboard extends JFrame {

    public PassengerDashboard(Passenger passenger) {
        Theme.applyNimbus();
        setTitle("Passenger Dashboard");
        setSize(450, 380);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Welcome, " + passenger.getFullName());
        title.setFont(Theme.HEAD_FONT);
        title.setBounds(80, 20, 350, 40);
        add(title);

        JButton searchBtn = new JButton("Search Flights");
        searchBtn.setBounds(130, 90, 180, 45);
        ButtonStyler.style(searchBtn);
        add(searchBtn);

        JButton bookingsBtn = new JButton("My Bookings");
        bookingsBtn.setBounds(130, 150, 180, 45);
        ButtonStyler.style(bookingsBtn);
        add(bookingsBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(130, 210, 180, 45);
        ButtonStyler.style(logoutBtn);
        add(logoutBtn);

        searchBtn.addActionListener(e -> new SearchFlightsWindow(passenger));
        bookingsBtn.addActionListener(e -> new ViewBookingsWindow(passenger));
        logoutBtn.addActionListener(e -> {
            dispose();
            new MainMenu();
        });

        setVisible(true);
    }
}
