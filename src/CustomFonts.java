import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CustomFonts {
    public static Font loadCustomFont(String path, float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            return new JLabel().getFont();
        }
    }
}
