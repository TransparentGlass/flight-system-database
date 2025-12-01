package cc14;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;


public class ImageLoader {

    private static final String[] SEARCH_PATHS = {
            "/",                          
            "/CSCC20Project/assets/",    
            "/assets/",                   
            "/resources/",                
            "src/CSCC20Project/assets/",  
            "src/assets/",
            "src/resources/",
            "./assets/",
            "./resources/",
            "."                            
    };
  
    public static ImageIcon load(String fileName, int width, int height) {
        ArrayList<String> attempts = new ArrayList<>();

        for (String base : SEARCH_PATHS) {
            if (!base.startsWith("/")) continue; 
            String fullPath = base + fileName;
            attempts.add("CLASSPATH: " + fullPath);

            try {
                URL url = ImageLoader.class.getResource(fullPath);
                if (url != null) {
                    System.out.println("✔ Loaded image from CLASSPATH: " + fullPath);
                    return scale(new ImageIcon(url), width, height);
                }
            } catch (Exception ignored) { }
        }

        for (String base : SEARCH_PATHS) {
            if (base.startsWith("/")) continue; 
            String fullPath = base.endsWith("/") ? base + fileName : base + "/" + fileName;
            File file = new File(fullPath);
            attempts.add("FILE: " + fullPath);

            if (file.exists()) {
                System.out.println("✔ Loaded image from FILE SYSTEM: " + file.getAbsolutePath());
                return scale(new ImageIcon(file.getAbsolutePath()), width, height);
            }
        }

        System.err.println("IMAGE NOT FOUND: " + fileName);
        System.err.println("   Locations checked:");
        for (String a : attempts) {
            System.err.println("   - " + a);
        }

        return new ImageIcon(); 
    }

    private static ImageIcon scale(ImageIcon icon, int w, int h) {
        if (w <= 0 || h <= 0) return icon;
        Image scaled = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
