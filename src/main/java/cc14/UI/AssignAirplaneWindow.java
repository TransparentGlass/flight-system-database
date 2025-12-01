package cc14.UI;

import javax.swing.*;

import cc14.Databases.FlightDatabase;
import cc14.models.Flight;

public class AssignAirplaneWindow extends JFrame {

    public AssignAirplaneWindow() {
        Theme.applyNimbus();
        setTitle("Assign Airplane");
        setSize(500, 350);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel lbl1 = new JLabel("Flight Number:");
        lbl1.setBounds(40, 50, 120, 30);
        add(lbl1);

        JTextField flightField = new JTextField();
        flightField.setBounds(160, 50, 200, 30);
        add(flightField);

        JLabel lbl2 = new JLabel("Select Airplane:");
        lbl2.setBounds(40, 100, 120, 30);
        add(lbl2);

        JComboBox<String> planeBox = new JComboBox<>(new String[]{
                "Airbus A320", "Airbus A321 Neo", "Boeing 737-800",
                "Bombardier Q400", "ATR 72-600", "Airbus A330-300"
        });
        planeBox.setBounds(160, 100, 200, 30);
        add(planeBox);

        JButton assignBtn = new JButton("Assign");
        assignBtn.setBounds(160, 170, 160, 40);
        ButtonStyler.style(assignBtn);
        add(assignBtn);

        assignBtn.addActionListener(e -> {
            Flight f = FlightDatabase.findFlight(flightField.getText().trim());
            if (f == null) {
                JOptionPane.showMessageDialog(this, "Flight not found.");
                return;
            }

            f.setAirplane((String) planeBox.getSelectedItem());
            JOptionPane.showMessageDialog(this, "Airplane Assigned!");
            dispose();
        });

        setVisible(true);
    }
}
