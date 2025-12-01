package cc14.UI;

import javax.swing.*;

import cc14.Databases.PassengerDatabase;
import cc14.models.Passenger;

public class PassengerLogin extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

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

        Passenger passenger = PassengerDatabase.validatePassenger(u, p);
        if (passenger == null) {
            JOptionPane.showMessageDialog(this,
                    "Incorrect username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Login successful!");
        dispose();
        new PassengerDashboard(passenger);
    }
}
