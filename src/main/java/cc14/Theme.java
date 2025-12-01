package cc14;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Theme {

    public static final Color ORANGE = new Color(255, 140, 0);
    public static final Color ORANGE_HOVER = new Color(255, 160, 40);
    public static final Color LIGHT_BG = new Color(255, 245, 230);
    public static final Color WHITE = Color.WHITE;

    public static final Font HEAD_FONT = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font BTN_FONT  = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 13);

    public static void applyNimbus() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
