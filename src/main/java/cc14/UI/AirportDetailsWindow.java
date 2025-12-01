package cc14.UI;

import javax.swing.*;

public class AirportDetailsWindow extends JFrame {

    public AirportDetailsWindow(String airportName, String info) {
        Theme.applyNimbus();
        setTitle("Airport Details - " + airportName);
        setSize(450, 300);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel title = new JLabel(airportName);
        title.setFont(Theme.HEAD_FONT);
        title.setBounds(130, 20, 300, 40);
        add(title);

        JTextArea text = new JTextArea(info);
        text.setEditable(false);
        text.setFont(Theme.TEXT_FONT);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(text);
        scroll.setBounds(40, 80, 350, 150);
        add(scroll);

        setVisible(true);
    }
}
