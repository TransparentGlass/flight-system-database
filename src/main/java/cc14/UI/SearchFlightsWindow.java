package cc14.UI;

import javax.swing.*;

import cc14.models.Passenger;

public class SearchFlightsWindow extends JFrame {

    private Passenger passenger;

    public SearchFlightsWindow(Passenger passenger) {
        this.passenger = passenger;

        Theme.applyNimbus();
        setTitle("Search Flights - Map");
        setSize(1000, 800);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel();
        bg.setBackground(Theme.LIGHT_BG);
        bg.setBounds(0, 0, 1080, 800);
        bg.setLayout(null);
        add(bg);

        JLabel title = new JLabel("Search Flights by Airport");
        title.setFont(Theme.HEAD_FONT);
        title.setBounds(320, 20, 400, 40);
        bg.add(title);

        // Airport buttons
        addAirportButton(bg, "Manila",         380, 250);
        addAirportButton(bg, "Clark",          360, 180);
        addAirportButton(bg, "Cebu",           480, 450);
        addAirportButton(bg, "Cagayan de Oro", 510, 500);
        addAirportButton(bg, "Davao",          570, 600);
        addAirportButton(bg, "Iloilo",         420, 390);
        // addAirportButton(bg, "Bacolod",        360, 280);
        addAirportButton(bg, "Bohol",          570, 440);
        // addAirportButton(bg, "Caticlan",       350, 240);
        addAirportButton(bg, "Zamboanga",      450, 550);

        // Map image
        ImageIcon mapIcon = ImageLoader.load("src\\main\\java\\cc14\\assets\\ph_map.png", 393, 567);
        JLabel mapLbl = new JLabel(mapIcon, JLabel.CENTER);
        mapLbl.setBounds(280, 100, mapIcon.getIconWidth(), mapIcon.getIconHeight());
        bg.add(mapLbl);

        

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(20, 20, 100, 35);
        ButtonStyler.style(backBtn);
        bg.add(backBtn);

        backBtn.addActionListener(e -> {
            dispose();
            new PassengerDashboard(passenger);
        });

        setVisible(true);
    }

    private void addAirportButton(JPanel bg, String name, int x, int y) {
        JButton btn = new JButton(name);
        btn.setBounds(x, y, 150, 30);
        ButtonStyler.style(btn);
        bg.add(btn);

        btn.addActionListener(e -> new SearchResultsWindow(passenger, name));
    }

    // public static void main(String[] args) {
    // // // Create a dummy passenger for testing
    // // Passenger dummyPassenger = new Passenger("g", "g", "g");
    
    // // // Launch the window directly
    // // SwingUtilities.invokeLater(() -> new SearchFlightsWindow(dummyPassenger));}

}

