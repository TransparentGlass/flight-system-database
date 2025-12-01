package cc14;

import javax.swing.*;

import cc14.Databases.PassengerDatabase;

public class PassengerRegistration extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField nameField;

    public PassengerRegistration() {
        Theme.applyNimbus();
        setTitle("Passenger Registration");
        setSize(450, 360);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Passenger Registration");
        title.setFont(Theme.HEAD_FONT);
        title.setBounds(100, 20, 280, 40);
        add(title);

        JLabel nameLbl = new JLabel("Full Name:");
        nameLbl.setFont(Theme.TEXT_FONT);
        nameLbl.setBounds(60, 90, 100, 25);
        add(nameLbl);

        nameField = new JTextField();
        nameField.setBounds(160, 90, 220, 30);
        add(nameField);

        JLabel userLbl = new JLabel("Username:");
        userLbl.setFont(Theme.TEXT_FONT);
        userLbl.setBounds(60, 130, 100, 25);
        add(userLbl);

        usernameField = new JTextField();
        usernameField.setBounds(160, 130, 220, 25);
        add(usernameField);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setFont(Theme.TEXT_FONT);
        passLbl.setBounds(60, 170, 100, 25);
        add(passLbl);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 170, 220, 25);
        add(passwordField);

        JButton regBtn = new JButton("Register");
        regBtn.setBounds(90, 230, 120, 40);
        ButtonStyler.style(regBtn);
        add(regBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(230, 230, 120, 40);
        ButtonStyler.style(backBtn);
        add(backBtn);

        regBtn.addActionListener(e -> register());
        backBtn.addActionListener(e -> {
            dispose();
            new PassengerLogin();
        });

        setVisible(true);
    }

    private void register() {
        String name = nameField.getText().trim();
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();

        if (name.isEmpty() || user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        boolean ok = PassengerDatabase.register(user, pass, name);
        if (!ok) {
            JOptionPane.showMessageDialog(this, "Username already exists.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Registration successful! You may now log in.");
        dispose();
        new PassengerLogin();
    }
}
