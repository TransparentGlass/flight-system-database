package cc14;

import javax.swing.*;

import cc14.Databases.FlightDatabase;

import java.awt.*;
import java.util.List;

public class SearchResultsWindow extends JFrame {

    private Passenger passenger;

    public SearchResultsWindow(Passenger passenger, String origin) {
        this.passenger = passenger;

        Theme.applyNimbus();
        setTitle("Flights from " + origin);
        setSize(700, 500);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel();
        bg.setBackground(Theme.LIGHT_BG);
        bg.setBounds(0, 0, 700, 500);
        bg.setLayout(null);
        add(bg);

        JLabel title = new JLabel("Flights from " + origin);
        title.setFont(Theme.HEAD_FONT);
        title.setBounds(230, 20, 300, 40);
        bg.add(title);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(null);
        listPanel.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBounds(50, 80, 600, 320);
        bg.add(scroll);

        List<Flight> flights = FlightDatabase.searchByOrigin(origin);
        int y = 10;
        for (Flight f : flights) {
            JPanel card = new JPanel();
            card.setLayout(null);
            card.setBackground(Color.WHITE);
            card.setBounds(10, y, 560, 90);

            JLabel info = new JLabel("<html><b>" + f.getFlightNumber() + "</b><br>" +
                    f.getOrigin() + " → " + f.getDestination() + "<br>" +
                    "Time: " + f.getDepartureTime() + " - " + f.getArrivalTime() + "<br>" +
                    "Aircraft: " + f.getAirplane() + "</html>");
            info.setFont(Theme.TEXT_FONT);
            info.setBounds(10, 5, 350, 80);
            card.add(info);

            JButton bookBtn = new JButton("Book");
            bookBtn.setBounds(430, 25, 100, 35);
            ButtonStyler.style(bookBtn);
            card.add(bookBtn);

            bookBtn.addActionListener(e -> new BookFlightWindow(passenger, f));

            listPanel.add(card);
            y += 100;
        }

        listPanel.setPreferredSize(new Dimension(580, y + 10));

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(20, 20, 100, 35);
        ButtonStyler.style(backBtn);
        bg.add(backBtn);

        backBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}
