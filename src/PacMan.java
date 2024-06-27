import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class PacMan extends JLabel implements  Runnable {
    public int x, y;
    private int dir;
    private final int CELL_SIZE = 30;
    private ImageIcon[][] animations;
    private int currentFrame = 0;
    private boolean isAnimating;
    public boolean isDead;
    public  int countLife=3;
    public boolean isAlive;
    public int collectedPoints;

    private Thread animationThread;


    public PacMan(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        this.isAlive = true;
        this.isDead = false;
        this.collectedPoints = 0;
        setBounds(x*CELL_SIZE+1,y*CELL_SIZE,CELL_SIZE,CELL_SIZE);
        this.dir = 0;
        loadAnimations();
        setIcon(animations[dir][0]);
        setOpaque(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));

        new Thread(this).start();
    }

    public void loadAnimations() {
        animations = new ImageIcon[6][4];
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

            animations[4][0] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/die1.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[4][1] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/die2.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[4][2] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/die3.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[4][3] = new ImageIcon(new ImageIcon("PacManProject/img/PacManAnimations/die4.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));

            animations[5][0] = new ImageIcon(new ImageIcon("PacManProject\\img\\barriers\\P.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startAnimationThread() {
       animationThread = new Thread(() -> {
            while (isAlive) {
                if (isAnimating) {
                    currentFrame = (currentFrame + 1) % 4;
                    setIcon(animations[dir][currentFrame]);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("Wątek przerwany");
                }
            }
            currentFrame = -1;
            while (currentFrame<3 && !isDead){
                currentFrame = (currentFrame+1)%4;
                setIcon(animations[4][currentFrame]);
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    System.out.println("Wątek przerwany");
                }
                if(currentFrame==3){
                    isDead = true;
                    setIcon(animations[5][0]);
                }

            }

        });
        animationThread.start();
    }



    public void setDirection(int dir) {
        this.dir = dir;
        isAnimating = true;
    }
//    public boolean checkGhostCollision() {
//        ArrayList<Ghosts> ghosts = WriteGrid.getGhosts();
//        if(!ghosts.isEmpty()) {
//            for (Ghosts ghost : ghosts) {
//                if (ghost.y == this.x && ghost.x ==this.y ) {  //Odwrócone współrzędne
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    public void lifeLogic(){
//        if(checkGhostCollision() && countLife>0){
//            System.out.println(countLife);
//            countLife-=1;
//            //Footer.hearts.get(countLife).setVisible(false);        }
//        if(countLife == 0){
//            isAlive = false;
//        }
//    }}

//    public void perkEffect(Fruit fruit){
//        double currentTime = WriteGrid.time;
//        switch (fruit.typeFruits){
//            case apple:{
//                double localTime = WriteGrid.time;
//                {
//                    for (Ghosts ghost : WriteGrid.getGhosts()) {
//                        ghost.setThreadTime(400);
//                    }
//                }
//            }
//        }
//    }



    @Override
    public void run() {
        startAnimationThread();
    }
}
