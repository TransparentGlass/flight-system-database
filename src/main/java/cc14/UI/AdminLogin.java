package cc14.UI;

import javax.swing.*;
//username: adminMAMA
//password: adminLogin123
public class AdminLogin extends JFrame {

    public AdminLogin() {
        Theme.applyNimbus();
        setTitle("Admin Login - M.A.M.A Airlines");
        setSize(400, 300);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Admin Login");
        title.setFont(Theme.HEAD_FONT);
        title.setBounds(130, 20, 200, 40);
        add(title);

        JLabel userLbl = new JLabel("Username:");
        userLbl.setFont(Theme.TEXT_FONT);
        userLbl.setBounds(50, 80, 100, 30);
        add(userLbl);

        JTextField userField = new JTextField();
        userField.setBounds(150, 80, 180, 30);
        add(userField);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setFont(Theme.TEXT_FONT);
        passLbl.setBounds(50, 120, 100, 30);
        add(passLbl);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(150, 120, 180, 30);
        add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(150, 170, 120, 35);
        ButtonStyler.style(loginBtn);
        add(loginBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(20, 220, 80, 30);
        ButtonStyler.style(backBtn);
        add(backBtn);

        loginBtn.addActionListener(e -> {
            String u = userField.getText().trim();
            String p = new String(passField.getPassword()).trim();

            if (u.equals("adminMAMA") && p.equals("adminLogin123")) {
                dispose();
                new AdminDashboard();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid admin credentials.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new MainMenu();
        });

        setVisible(true);
    }
}
