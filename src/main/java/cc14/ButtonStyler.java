package cc14;

import java.awt.*;
import javax.swing.*;

/**
 * Applies the same orange style to every JButton.
 * Call: ButtonStyler.style(myButton);
 */
public class ButtonStyler {

    public static void style(JButton btn) {
        btn.setFont(Theme.BTN_FONT);
        btn.setBackground(Theme.ORANGE);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Theme.ORANGE.darker(), 3, true));
        btn.setOpaque(true);

        btn.getModel().addChangeListener(e -> {
            if (btn.getModel().isRollover()) {
                btn.setBackground(Theme.ORANGE_HOVER);
            } else {
                btn.setBackground(Theme.ORANGE);
            }
        });
    }
}
