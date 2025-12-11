package cc14.UI;

import javax.swing.*;

import cc14.Databases.BookingDatabase;
import cc14.models.Passenger;

public class CancelBookingWindow extends JFrame {

    public CancelBookingWindow(Passenger passenger, String flightNumber) {
        Theme.applyNimbus();
        setTitle("Cancel Booking");
        setSize(400, 220);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Cancel Booking");
        title.setFont(Theme.HEAD_FONT);
        title.setBounds(120, 20, 250, 40);
        add(title);

        JLabel msg = new JLabel("Cancel booking for flight " + flightNumber + "?");
        msg.setFont(Theme.TEXT_FONT);
        msg.setBounds(60, 80, 300, 25);
        add(msg);

        JButton yesBtn = new JButton("Yes");
        yesBtn.setBounds(80, 130, 100, 35);
        ButtonStyler.style(yesBtn);
        add(yesBtn);

        JButton noBtn = new JButton("No");
        noBtn.setBounds(220, 130, 100, 35);
        ButtonStyler.style(noBtn);
        add(noBtn);

        yesBtn.addActionListener(e -> {
            //TODO: change this so that we actually get the reservation ID rather than the passenger and flight_num
            boolean ok = BookingDatabase.cancelBooking(passenger, flightNumber);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Booking canceled.");
            } else {
                JOptionPane.showMessageDialog(this, "Booking not found.");
            }
            dispose();
        });

        noBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

}
