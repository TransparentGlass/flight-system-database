package cc14;

import javax.swing.*;

import cc14.Databases.FlightDatabase;

public class EditFlightWindow extends JFrame {

    private Flight target;

    public EditFlightWindow() {
        Theme.applyNimbus();
        setTitle("Edit Flight");
        setSize(450, 550);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Edit Flight");
        title.setFont(Theme.HEAD_FONT);
        title.setBounds(160, 20, 200, 40);
        add(title);

        JLabel findLbl = new JLabel("Flight No:");
        findLbl.setBounds(50, 80, 100, 30);
        add(findLbl);

        JTextField findField = new JTextField();
        findField.setBounds(150, 80, 200, 30);
        add(findField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(150, 120, 150, 35);
        ButtonStyler.style(searchBtn);
        add(searchBtn);

        JLabel flbl = new JLabel("Flight No:");
        flbl.setBounds(50, 180, 100, 30);
        add(flbl);

        JTextField fField = new JTextField();
        fField.setBounds(160, 180, 200, 30);
        add(fField);

        JLabel oLbl = new JLabel("Origin:");
        oLbl.setBounds(50, 220, 100, 30);
        add(oLbl);

        JTextField oField = new JTextField();
        oField.setBounds(160, 220, 200, 30);
        add(oField);

        JLabel dLbl = new JLabel("Destination:");
        dLbl.setBounds(50, 260, 100, 30);
        add(dLbl);

        JTextField dField = new JTextField();
        dField.setBounds(160, 260, 200, 30);
        add(dField);

        JLabel depLbl = new JLabel("Departure:");
        depLbl.setBounds(50, 300, 100, 30);
        add(depLbl);

        JTextField depField = new JTextField();
        depField.setBounds(160, 300, 200, 30);
        add(depField);

        JLabel arrLbl = new JLabel("Arrival:");
        arrLbl.setBounds(50, 340, 100, 30);
        add(arrLbl);

        JTextField arrField = new JTextField();
        arrField.setBounds(160, 340, 200, 30);
        add(arrField);

        JLabel seatsLbl = new JLabel("Seats:");
        seatsLbl.setBounds(50, 380, 100, 30);
        add(seatsLbl);

        JTextField seatsField = new JTextField();
        seatsField.setBounds(160, 380, 200, 30);
        add(seatsField);

        JLabel priceLbl = new JLabel("Price:");
        priceLbl.setBounds(50, 420, 100, 30);
        add(priceLbl);

        JTextField priceField = new JTextField();
        priceField.setBounds(160, 420, 200, 30);
        add(priceField);

        JButton saveBtn = new JButton("Save Changes");
        saveBtn.setBounds(150, 470, 160, 40);
        ButtonStyler.style(saveBtn);
        add(saveBtn);

        searchBtn.addActionListener(e -> {
            target = FlightDatabase.findFlight(findField.getText().trim());

            if (target == null) {
                JOptionPane.showMessageDialog(this, "Flight not found.");
                return;
            }

            fField.setText(target.getFlightNumber());
            oField.setText(target.getOrigin());
            dField.setText(target.getDestination());
            depField.setText(target.getDepartureTime());
            arrField.setText(target.getArrivalTime());
            seatsField.setText("" + target.getTotalSeats());
            priceField.setText("" + target.getPrice());
        });

        saveBtn.addActionListener(e -> {
            if (target == null) return;

            boolean ok = FlightDatabase.editFlight(
                    target.getFlightId(),
                    fField.getText().trim(),
                    oField.getText().trim(),
                    dField.getText().trim(),
                    depField.getText().trim(),
                    arrField.getText().trim(),
                    Integer.parseInt(seatsField.getText().trim()),
                    Double.parseDouble(priceField.getText().trim())
            );

            if (ok) {
                JOptionPane.showMessageDialog(this, "Flight updated!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error updating flight.");
            }
        });

        setVisible(true);
    }
}
