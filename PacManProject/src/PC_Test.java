import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class PC_Test extends JLabel {
    public ImageIcon icon;
    public int dir;
    public PC_Test() {
        try {
            icon = new ImageIcon(new ImageIcon("PacManProject\\img\\PacManAnimations\\right1.png").getImage().getScaledInstance(27, 27, Image.SCALE_SMOOTH));

        }catch (Exception e){
            e.printStackTrace();
        }
        this.dir = 0;
        setIcon(icon);
    }

}

