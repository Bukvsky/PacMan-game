import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GhostTest extends JLabel implements Runnable  {
    private int x, y;
    private static int ROWS = 27;
    private static int COLS = 27;
    private int dir;
    private int CELL_SIZE = 30;
    private Colors color;
    private boolean isAnimating;
    private int currentFrame = 0;

    private ImageIcon[][] animations;

    public GhostTest(int x, int y) {
        this.x = x;
        this.y = y;
        setBounds(x*CELL_SIZE,y*CELL_SIZE,CELL_SIZE,CELL_SIZE);
        this.color = color;
        loadAnimations();
        setIcon(animations[0][0]);
        //setBounds(x,y,CELL_SIZE,CELL_SIZE);


        this.dir = 0;
        setOpaque(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(CELL_SIZE,CELL_SIZE));

       new Thread(this).start();
    }




    private String getColor() {
        switch (this.color) {
            case RED -> {
                return "red";
            }
            case BLUE -> {
                return "blue";
            }
            case PINK -> {
                return "PINK";
            }
            case HONEY -> {
                return "HONEY";
            }
            default -> {
                return "";
            }
        }
    }
    private void setDir(int direction) {
        this.dir = direction;
        isAnimating = true;
    }

    public void loadAnimations() {
        animations = new ImageIcon[2][2];
        //String color = getColor();
        try {
            animations[0][0] = new ImageIcon(new ImageIcon("PacManProject\\img\\ghosts\\red\\left1.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[0][1] = new ImageIcon(new ImageIcon("PacManProject\\img\\ghosts\\red\\left2.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[1][0] = new ImageIcon(new ImageIcon("PacManProject\\img\\ghosts\\red\\right1.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[1][1] = new ImageIcon(new ImageIcon("PacManProject\\img\\ghosts\\red\\right2.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[2][0] = new ImageIcon(new ImageIcon("PacManProject\\img\\ghosts\\bonus\\1.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[2][1] = new ImageIcon(new ImageIcon("PacManProject\\img\\ghosts\\bonus\\2.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startAnimationThread() {

        Thread animationThread = new Thread(() -> {
            while (true) {
                if (isAnimating) {
                    currentFrame = (currentFrame + 1) % 2;
                    dir = dir%2;
                    if (dir == 1 || dir == 3) {
                        setIcon(animations[dir][currentFrame]);
                    } else {
                        setIcon(animations[dir][currentFrame]);

                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        animationThread.start();
    }
    @Override
    public void run() {
        startAnimationThread();
        Thread movementThread = new Thread(() -> {
            while (true) {
                int currentX = getX(); // Oblicz bieżące współrzędne siatki w każdej iteracji
                int currentY = getY(); // Oblicz bieżące współrzędne siatki w każdej iteracji
                int nextMove = new Random().nextInt(4)+1;
                int newX = x;
                int newY = y;
                switch (nextMove) {
                    case 0 -> newY++;
                    case 1 -> newX++;
                    case 2 -> newY--;
                    case 3 -> newX--;
                }
//                if (isValidMove(grid, newX, newY)) {
//                    setX(newX);
//                    setY(newY);
//                    setBounds(newX* CELL_SIZE, newY * CELL_SIZE, CELL_SIZE, CELL_SIZE);
//                    x = newX;
//                    y = newY;
//                }
                //setBounds((newX)* CELL_SIZE, newY * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                setDir(nextMove);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        movementThread.start();
    }

}
