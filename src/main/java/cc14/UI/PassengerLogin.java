package cc14.UI;

import static cc14.Databases.BookingDatabase.getPassenger;

import javax.swing.*;

import cc14.Databases.PassengerDatabase;
import cc14.models.Passenger;
import java.util.HashMap;
import java.util.Map;

public class PassengerLogin extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    // Memory-only lockout system
    private static class Attempt {
        int count = 0;
        long lockoutUntil = 0;
    }

    private static final Map<String, Attempt> attempts = new HashMap<>();
    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCKOUT_DURATION = 15 * 60 * 1000; // 15 minutes

    public PassengerLogin() {
        Theme.applyNimbus();
        setTitle("Passenger Login");
        setSize(420, 320);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Passenger Login");
        title.setFont(Theme.HEAD_FONT);
        title.setBounds(115, 20, 250, 40);
        add(title);

        JLabel userLbl = new JLabel("Username:");
        userLbl.setFont(Theme.TEXT_FONT);
        userLbl.setBounds(50, 90, 100, 25);
        add(userLbl);

        usernameField = new JTextField();
        usernameField.setBounds(150, 90, 200, 30);
        add(usernameField);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setFont(Theme.TEXT_FONT);
        passLbl.setBounds(50, 130, 100, 25);
        add(passLbl);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 130, 200, 30);
        add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(50, 190, 120, 35);
        ButtonStyler.style(loginBtn);
        add(loginBtn);

        JButton regBtn = new JButton("Register");
        regBtn.setBounds(190, 190, 120, 35);
        ButtonStyler.style(regBtn);
        add(regBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(140, 235, 120, 35);
        ButtonStyler.style(backBtn);
        add(backBtn);

        loginBtn.addActionListener(e -> login());
        regBtn.addActionListener(e -> {
            dispose();
            new PassengerRegistration();
        });
        backBtn.addActionListener(e -> {
            dispose();
            new MainMenu();
        });

        setVisible(true);
    }

    private void login() {
        String u = usernameField.getText().trim();
        String p = new String(passwordField.getPassword()).trim();

        // Check lockout
        Attempt a = attempts.getOrDefault(u, new Attempt());
        if (System.currentTimeMillis() < a.lockoutUntil) {
            long minutesLeft = (a.lockoutUntil - System.currentTimeMillis()) / 60000 + 1;
            JOptionPane.showMessageDialog(this,
                    "Account locked. Try again in " + minutesLeft + " minute(s).",
                    "Login Locked",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int passenger = PassengerDatabase.passwordCheck(u, p);
        if (passenger == -1) {
            // Record failed attempt
            a.count++;
            if (a.count >= MAX_ATTEMPTS) {
                a.lockoutUntil = System.currentTimeMillis() + LOCKOUT_DURATION;
                a.count = 0; // reset after lockout
            }
            attempts.put(u, a);

            JOptionPane.showMessageDialog(this,
                    "Incorrect username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Successful login → reset attempts
        attempts.remove(u);

        JOptionPane.showMessageDialog(this, "Login successful!");
        dispose();
        new PassengerDashboard(getPassenger(passenger));
    }
}
