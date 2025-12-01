package cc14;

import cc14.UI.MainMenu;
import cc14.UI.Theme;

public class AppLauncher {
    public static void main(String[] args) {
        Theme.applyNimbus();
        new MainMenu();
    }
}
