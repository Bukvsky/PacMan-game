import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Images {
    public ImageIcon[] imgs;

    public Images() {
        imgs = loadImages();
    }

    public ImageIcon[] loadImages() {
        ImageIcon[] loadedImages = new ImageIcon[17];
        try {
            for (int i = 65; i < 82; i++) {
                System.out.println((char) i);
                BufferedImage image = ImageIO.read(new File("PacManProject\\img\\barriers\\" + (char) (i) + ".png"));
                loadedImages[i - 65] = new ImageIcon(image);
            }
        } catch (IOException e) {
            // Obsługa błędów wczytywania pliku
            JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas wczytywania obrazów.", "Błąd", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return loadedImages;
    }
}
