package cc14;

import javax.swing.*;

import cc14.Databases.FlightDatabase;

public class AddFlightWindow extends JFrame {

    public AddFlightWindow() {
        Theme.applyNimbus();
        setTitle("Add Flight");
        setSize(450, 500);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Add New Flight");
        title.setFont(Theme.HEAD_FONT);
        title.setBounds(140, 20, 250, 40);
        add(title);

        JLabel fNoLbl = new JLabel("Flight No:");
        fNoLbl.setBounds(50, 90, 100, 30);
        add(fNoLbl);

        JTextField fNoField = new JTextField();
        fNoField.setBounds(160, 90, 200, 30);
        add(fNoField);

        JLabel originLbl = new JLabel("Origin:");
        originLbl.setBounds(50, 130, 100, 30);
        add(originLbl);

        JTextField originField = new JTextField();
        originField.setBounds(160, 130, 200, 30);
        add(originField);

        JLabel destLbl = new JLabel("Destination:");
        destLbl.setBounds(50, 170, 100, 30);
        add(destLbl);

        JTextField destField = new JTextField();
        destField.setBounds(160, 170, 200, 30);
        add(destField);

        JLabel depLbl = new JLabel("Departure:");
        depLbl.setBounds(50, 210, 100, 30);
        add(depLbl);

        JTextField depField = new JTextField("2025-12-01 08:30");
        depField.setBounds(160, 210, 200, 30);
        add(depField);

        JLabel arrLbl = new JLabel("Arrival:");
        arrLbl.setBounds(50, 250, 100, 30);
        add(arrLbl);

        JTextField arrField = new JTextField("2025-12-01 10:00");
        arrField.setBounds(160, 250, 200, 30);
        add(arrField);

        JLabel seatLbl = new JLabel("Seats:");
        seatLbl.setBounds(50, 290, 100, 30);
        add(seatLbl);

        JTextField seatField = new JTextField("150");
        seatField.setBounds(160, 290, 200, 30);
        add(seatField);

        JLabel priceLbl = new JLabel("Price:");
        priceLbl.setBounds(50, 330, 100, 30);
        add(priceLbl);

        JTextField priceField = new JTextField("3000");
        priceField.setBounds(160, 330, 200, 30);
        add(priceField);

        JButton addBtn = new JButton("Add Flight");
        addBtn.setBounds(140, 380, 150, 40);
        ButtonStyler.style(addBtn);
        add(addBtn);

        addBtn.addActionListener(e -> {
            try {
                FlightDatabase.addFlight(
                        fNoField.getText().trim(),
                        originField.getText().trim(),
                        destField.getText().trim(),
                        depField.getText().trim(),
                        arrField.getText().trim(),
                        Integer.parseInt(seatField.getText().trim()),
                        Double.parseDouble(priceField.getText().trim())
                );

                JOptionPane.showMessageDialog(this,
                        "Flight added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Invalid input.\nPlease check all fields.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
