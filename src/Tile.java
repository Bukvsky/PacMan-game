import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Tile extends JLabel {
    private ImageIcon icon;
    private int id;
    private int cellSize;
    private ImageIcon[] imgs;
    public boolean isWall;
    public boolean isWallForGhosts;
    public int x,y;
    public Tile(int id, int cellSize, int x, int y) {
        super();
        this.x = x;
        this.y=y;
        setLayout(new OverlayLayout(this));

        if (imgs == null) {
            imgs = loadImages();
        }
        if (id ==80 ){
            isWall = false;
            isWallForGhosts = false;
        }
        else {
            if(id==81){
                isWallForGhosts = true;
            }
            isWall = true;
        }
        setOpaque(true);
        setBackground(Color.YELLOW);
        this.icon = new ImageIcon(imgs[id - 65].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_SMOOTH));
        this.id = id;
        this.cellSize = cellSize;
        setIcon(this.icon);
        setOpaque(true);
        setBackground(Color.BLACK);
        // Ustaw preferowany rozmiar Tile na podany rozmiar komórki
        setPreferredSize(new Dimension(cellSize, cellSize));
        setVisible(true);
    }

    private static ImageIcon[] loadImages() {
        ImageIcon[] loadedImages = new ImageIcon[17];
        int i = 65;
        try {
            for (i = 65; i < 82; i++) {
                BufferedImage image = ImageIO.read(new File("PacManProject\\img\\barriers\\" + (char) (i) + ".png"));
                loadedImages[i - 65] = new ImageIcon(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Błąd wczytywania obrazka dla id: " + (char)(i));
            
        }
        return loadedImages;
    }

    public int getCellSize() {
        return cellSize;
    }

    public boolean isValid(){
        return isWall;
    }
}
