import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PacMan extends JLabel implements  Runnable {
    public int x, y;
    private int dir;
    private final int CELL_SIZE = 30;
    private ImageIcon[][] animations;
    private int currentFrame = 0;
    private boolean isAnimating;
    public int countLife = 4;


    public PacMan(int x, int y) {
        super();
        this.x = x; // Starting position
        this.y = y;

        setBounds(x*CELL_SIZE+1,y*CELL_SIZE,CELL_SIZE,CELL_SIZE);
        this.dir = 0; // Initially facing right
        loadAnimations();
        setIcon(animations[dir][0]);
        setOpaque(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
        //setBounds(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        new Thread(this).start();
    }

    public void loadAnimations() {
        animations = new ImageIcon[4][4];
        try {
            animations[0][0] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/right1.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[0][1] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/right2.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[0][2] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/right3.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[0][3] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/right4.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));

            animations[1][0] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/down1.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[1][1] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/down2.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[1][2] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/down3.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[1][3] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/down4.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));

            animations[2][0] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/left1.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[2][1] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/left2.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[2][2] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/left3.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[2][3] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/left4.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));

            animations[3][0] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/up1.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[3][1] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/up2.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[3][2] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/up3.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[3][3] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/up4.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startAnimationThread() {
        Thread animationThread = new Thread(() -> {
            while (true) {
                if (isAnimating) {
                    currentFrame = (currentFrame + 1) % 4;
                    setIcon(animations[dir][currentFrame]);
                }
                try {
                    Thread.sleep(100); // Change frame every 200 ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        animationThread.start();
    }

    public void setDirection(int dir) {
        this.dir = dir;
        isAnimating = true;
    }



    @Override
    public void run() {
        startAnimationThread();
    }
}
