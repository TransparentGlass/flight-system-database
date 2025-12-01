package cc14;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        Theme.applyNimbus();
        setTitle("M.A.M.A Airlines - Main Menu");
        setSize(450, 420);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel bg = new JPanel();
        bg.setBackground(Theme.LIGHT_BG);
        bg.setBounds(0, 0, 450, 420);
        bg.setLayout(null);
        add(bg);

        JLabel title = new JLabel("M.A.M.A Airlines");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBounds(120, 40, 300, 40);
        bg.add(title);

        JLabel subtitle = new JLabel("Flight Booking System");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setBounds(140, 75, 300, 30);
        bg.add(subtitle);

        JButton adminBtn = new JButton("Admin Login");
        adminBtn.setBounds(125, 140, 200, 45);
        ButtonStyler.style(adminBtn);
        bg.add(adminBtn);

        JButton passengerBtn = new JButton("Passenger Login");
        passengerBtn.setBounds(125, 200, 200, 45);
        ButtonStyler.style(passengerBtn);
        bg.add(passengerBtn);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(125, 260, 200, 45);
        ButtonStyler.style(exitBtn);
        bg.add(exitBtn);

        adminBtn.addActionListener(e -> {
            dispose();
            new AdminLogin();
        });

        passengerBtn.addActionListener(e -> {
            dispose();
            new PassengerLogin();
        });

        exitBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
}
