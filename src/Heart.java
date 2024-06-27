import javax.swing.*;
import java.awt.*;

public class Heart extends JLabel {
    private ImageIcon icon;
    public  boolean visibility = true;
    public Heart() {
        loadImage();
        setIcon(icon);
        setBackground(Color.BLACK);
        setVisible(visibility);
        setPreferredSize(new Dimension(40,40));
    }

    public void loadImage(){
        try {
            icon = new ImageIcon(new ImageIcon("PacManProject\\img\\heart.png").getImage().getScaledInstance(30,30,30));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
