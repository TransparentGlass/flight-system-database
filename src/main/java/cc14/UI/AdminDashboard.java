package cc14.UI;

import javax.swing.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        Theme.applyNimbus();
        setTitle("Admin Dashboard");
        setSize(500, 500);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Admin Dashboard");
        title.setFont(Theme.HEAD_FONT);
        title.setBounds(150, 20, 300, 40);
        add(title);

        JButton manageFlights = new JButton("Manage Flights");
        manageFlights.setBounds(150, 100, 200, 40);
        ButtonStyler.style(manageFlights);
        add(manageFlights);

        JButton assignAirplane = new JButton("Assign Airplanes");
        assignAirplane.setBounds(150, 160, 200, 40);
        ButtonStyler.style(assignAirplane);
        add(assignAirplane);

        JButton viewFlights = new JButton("View Flights Created");
        viewFlights.setBounds(150, 220, 200, 40);
        ButtonStyler.style(viewFlights);
        add(viewFlights);

        JButton backBtn = new JButton("Logout");
        backBtn.setBounds(150, 280, 200, 40);
        ButtonStyler.style(backBtn);
        add(backBtn);

        manageFlights.addActionListener(e -> new ManageFlightsWindow());
        assignAirplane.addActionListener(e -> new AssignAirplaneWindow());
        viewFlights.addActionListener(e -> new ViewFlightsWindow());

        backBtn.addActionListener(e -> {
            dispose();
            new MainMenu();
        });

        setVisible(true);
    }
}
