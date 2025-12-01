package cc14;

import javax.swing.*;

public class ManageFlightsWindow extends JFrame {

    public ManageFlightsWindow() {
        Theme.applyNimbus();
        setTitle("Manage Flights");
        setSize(400, 350);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Manage Flights");
        title.setFont(Theme.HEAD_FONT);
        title.setBounds(120, 20, 200, 40);
        add(title);

        JButton addBtn = new JButton("Add Flight");
        addBtn.setBounds(120, 80, 150, 40);
        ButtonStyler.style(addBtn);

        JButton editBtn = new JButton("Edit Flight");
        editBtn.setBounds(120, 140, 150, 40);
        ButtonStyler.style(editBtn);

        JButton delBtn = new JButton("Delete Flight");
        delBtn.setBounds(120, 200, 150, 40);
        ButtonStyler.style(delBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(120, 260, 150, 35);
        ButtonStyler.style(backBtn);

        add(addBtn);
        add(editBtn);
        add(delBtn);
        add(backBtn);

        addBtn.addActionListener(e -> new AddFlightWindow());
        editBtn.addActionListener(e -> new EditFlightWindow());
        delBtn.addActionListener(e -> new DeleteFlightWindow());

        backBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}
