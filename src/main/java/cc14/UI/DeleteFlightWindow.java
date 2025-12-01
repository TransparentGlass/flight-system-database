package cc14.UI;

import javax.swing.*;

import cc14.Databases.FlightDatabase;

public class DeleteFlightWindow extends JFrame {

    public DeleteFlightWindow() {
        Theme.applyNimbus();
        setTitle("Delete Flight");
        setSize(400, 220);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Flight Number:");
        label.setBounds(40, 40, 120, 30);
        add(label);

        JTextField field = new JTextField();
        field.setBounds(160, 40, 180, 30);
        add(field);

        JButton deleteBtn = new JButton("Delete Flight");
        deleteBtn.setBounds(120, 100, 150, 40);
        ButtonStyler.style(deleteBtn);
        add(deleteBtn);

        deleteBtn.addActionListener(e -> {
            boolean ok = FlightDatabase.deleteFlight(field.getText().trim());
            if (ok) {
                JOptionPane.showMessageDialog(this, "Flight deleted.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Flight not found.");
            }
        });

        setVisible(true);
    }
}
