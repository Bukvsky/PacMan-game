import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class WriteGrid extends JPanel {
    private static final int ROWS = 27;
    private static final int COLS = 27;
    public static final int CELL_SIZE = 30; // Rozmiar pojedynczej komórki
    private static final int width = ROWS * CELL_SIZE;
    private static final int height = ROWS * CELL_SIZE;
    public static GridBagConstraints gbc;
    private static Point location;
    private PacMan pacMan;
    private JLayeredPane layeredPane;
    private static Tile[][] tiles;
    public static int point;
    private Thread movementThread;
    private int currentDir;
    public static volatile boolean flag = true;
    int it;
    public static double time;

    private boolean isMoving = false;
    public static boolean ghostStop = false;
    public int bonus = 1;
    private int i = 1;
    public static ArrayList<Ghosts> ghosts;
    public Fruit[][] fruits;
    public int threadTime=200;



    public WriteGrid() {
        tiles = new Tile[ROWS][COLS];
        fruits = new Fruit[ROWS][COLS];
        createNewGamePanel();
        addKeyListener(new PacManKeyListener());
        setFocusable(true); // Umożliwia odbieranie zdarzeń klawiatury
        requestFocusInWindow(); // Ustawia fokus na panelu, aby odbierać zdarzenia klawiatury
    }

    public void createNewGamePanel() {
        setBackground(Color.BLACK);
        setVisible(true);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(width, height));
        layeredPane.setLayout(null);

        gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Tworzenie siatki
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                int ch = Grids.recognizeDot(r, c);
                Tile tile = new Tile(ch, CELL_SIZE, r, c);
                tile.setBounds(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                layeredPane.add(tile, JLayeredPane.DEFAULT_LAYER);
                tiles[r][c] = tile; // Store the tile in the array
                if(!tile.isWall){
                    int randomNum = new Random().nextInt(10);
                    //System.out.println(randomNum);
                    TypeFruits typeFruits;
                    if(randomNum==3){
                        typeFruits = TypeFruits.sugar;
                    }
                    else {
                        int anotherRandom = new Random().nextInt(4);
                        switch (anotherRandom){
                            case 0:{
                                typeFruits = TypeFruits.strawberry;

                            }
                            case 1:{
                                typeFruits = TypeFruits.cherry;

                            }
                            case 2:{
                                typeFruits = TypeFruits.orange;

                            } case 3:{
                                typeFruits = TypeFruits.apple;

                            }
                            default:
                                typeFruits = TypeFruits.sugar;


                        }
                    }
                    Fruit fruit = new Fruit(typeFruits,r,c);
                    tiles[r][c].setVisible(false);
                    fruit.setBounds(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    layeredPane.add(fruit,JLayeredPane.DEFAULT_LAYER);
                    fruits[r][c] = fruit;

                }
                else {
                    fruits[r][c] = null;
                }
            }
        }

        // Tworzenie i umieszczanie PacMan oraz duchów
        Ghosts red = new Ghosts(1, 3, Colors.RED, Grids.gridBin);
        Ghosts pink = new Ghosts(12, 12, Colors.PINK, Grids.gridBin);
        Ghosts blue = new Ghosts(14, 12, Colors.BLUE, Grids.gridBin);
        Ghosts honey = new Ghosts(12, 14, Colors.HONEY, Grids.gridBin);
        ghosts = new ArrayList<>();
        ghosts.add(red);
        ghosts.add(blue);
        ghosts.add(honey);
        ghosts.add(pink);
        pacMan = new PacMan(1, 2);
        location = pacMan.getLocation();

        pacMan.setBounds(2 * CELL_SIZE, 1 * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Ustawienie początkowej pozycji
        layeredPane.add(pacMan, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(red, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(pink, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(blue, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(honey, JLayeredPane.PALETTE_LAYER);
        // Dodanie layeredPane do WriteGrid
        setLayout(new BorderLayout());
        add(layeredPane, BorderLayout.CENTER);
        startChecking();


    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE);
    }

    private boolean isValidMove(int x, int y) {
        int col = x / CELL_SIZE;
        int row = y / CELL_SIZE;
        if (col < 0 || col >= COLS || row < 0 || row >= ROWS) {
            return false;
        }
        return !tiles[row][col].isWall;
    }

    private class PacManKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            int newDir = -1;
            Point location = pacMan.getLocation();
            int x = location.x;
            int y = location.y;

            switch (keyCode) {
                case KeyEvent.VK_UP:
                    if (!isValidMove(x, y - CELL_SIZE)) {
                        newDir = currentDir;
                        break;
                    }
                    newDir = 3;
                    break;
                case KeyEvent.VK_DOWN:
                    if (!isValidMove(x, y + CELL_SIZE)) {
                        newDir = currentDir;
                        break;
                    }
                    newDir = 1;
                    break;
                case KeyEvent.VK_LEFT:
                    if (!isValidMove(x - CELL_SIZE, y)) {
                        newDir = currentDir;
                        break;
                    }
                    newDir = 2;
                    break;
                case KeyEvent.VK_RIGHT:
                    if (!isValidMove(x + CELL_SIZE, y)) {
                        newDir = currentDir;
                        break;
                    }
                    newDir = 0;
                    break;
                case KeyEvent.VK_ESCAPE: {
                    flag = false;
                    ghostStop = true;
                    showEscDialog();
                    break;
                }
            }

            if (newDir != -1) {
                currentDir = newDir;
                if (!isMoving) {
                    startMoving();
                }
            }
        }
    }

    public synchronized void startMoving() {
        if (movementThread != null && movementThread.isAlive()) {
            stopMoving();
        }

        flag = true;
        isMoving = true;

        movementThread = new Thread(() -> {
            try {
                while (flag) {
                    SwingUtilities.invokeLater(() -> {
                        Point location = pacMan.getLocation();
                        int x = location.x;
                        int y = location.y;
                        switch (currentDir) {
                            case 0: // Right
                                if (isValidMove(x + CELL_SIZE, y)) {
                                    x = Math.min(x + CELL_SIZE, (COLS - 1) * CELL_SIZE);
                                } else {
                                    stopMoving();
                                    return;
                                }
                                break;
                            case 1: // Down
                                if (isValidMove(x, y + CELL_SIZE)) {
                                    y = Math.min(y + CELL_SIZE, (ROWS - 1) * CELL_SIZE);
                                } else {
                                    stopMoving();
                                    return;
                                }
                                break;
                            case 2: // Left
                                if (isValidMove(x - CELL_SIZE, y)) {
                                    x = Math.max(x - CELL_SIZE, 0);
                                } else {
                                    stopMoving();
                                    return;
                                }
                                break;
                            case 3: // Up
                                if (isValidMove(x, y - CELL_SIZE)) {
                                    y = Math.max(y - CELL_SIZE, 0);
                                } else {
                                    stopMoving();
                                    return;
                                }
                                break;
                        }
                        pacMan.setDirection(currentDir);
                        if (pacMan.isAlive) {
                            //System.out.println("Turn: " + (i++));
                            pacMan.setLocation(x, y);
                            int fx = x/CELL_SIZE;
                            int fy = y/CELL_SIZE;
                            int points = fruits[fy][fx].getPoints();
                            fruits[fy][fx].visibility = false;
                            //pacMan.collectedPoints+=fruits[fy][fx].points;
                            fruits[fy][fx].setVisible(fruits[fy][fx].visibility);
                           // point = pacMan.collectedPoints;

                            pacMan.collectedPoints+=points;
                            point = pacMan.collectedPoints;
                            //System.out.println(returnPoints());
                           // System.out.println(pacMan.collectedPoints);
                        } else {
                            flag = false;
                            createAskDialog();
                        }
                        pacMan.x = x / CELL_SIZE;
                        pacMan.y = y / CELL_SIZE;
                    });

                    Thread.sleep(threadTime);
                    time+=0.2;
                    System.out.println(time);
                    if((int)time%5 == 0){
                        bonus = 1;
                        threadTime = 200;
                        int num = new Random().nextInt(4);
                        //int currentTime = threadTime;
                        if(num == 0){
                            threadTime = 300;
                        }
                        if(num == 1){
                            threadTime = 100;
                        }
                        if(num == 2){
                            bonus = 2;
                        }
                        if(num == 3){
                            bonus = 4;
                        }
                    }
                    //System.out.println(time);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

            }
        });

        movementThread.start();
        startChecking();


    }

    private void stopMoving() {
        isMoving = false;
        flag = false;
        if (movementThread != null) {
            movementThread.interrupt();
            movementThread = null;
        }
    }
    private void ghostStopMoving() {
        flag = false;
        if (Ghosts.movementThread != null) {
            Ghosts.movementThread.interrupt();
            Ghosts.movementThread = null;
        }
    }

    private  void showEscDialog(){
        int option = JOptionPane.showConfirmDialog(null,
                "Do you want to quit?");
        stopMoving();
        ghostStop = true;
        if (option == JOptionPane.YES_OPTION) {
            createAskDialog();
        }
        else {
            ghostStop = false;
            //System.out.println(flag);

        }
    }

    public void startChecking() {
        Thread checkThread = new Thread(() -> {
            while (pacMan.isAlive) {
                boolean lostLife = false;
                Ghosts deletedGhosts = ghosts.get(0);
                synchronized (ghosts) {
                    int i = 0;
                    for ( i = 0; i < ghosts.size(); i++) {
                        Ghosts ghost = ghosts.get(i);
                        if (ghost.y == pacMan.x && ghost.x == pacMan.y) {
                            lostLife = true;
                            deletedGhosts = ghosts.get(i);
                            ghosts.remove(i);
                            i--;
                            break;
                        }
                    }


                }
                //System.out.println(pacMan.countLife);
                if (lostLife && pacMan.countLife > 0) {
                        pacMan.countLife--;
                        Footer.hearts.get(pacMan.countLife).setVisible(false);
                        try {
                        Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                        }
                        ghosts.add(deletedGhosts);
                }
                else if(pacMan.countLife == 0) {
                    ghostStop = true;
                    Thread.currentThread().interrupt();
                    pacMan.isAlive = false;
                    flag = false;
                    createAskDialog();
                    //System.exit(0);
                    return;
                }


                try {
                    Thread.sleep(lostLife ? 200 : 50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }

            }
        });

        checkThread.start();
    }


    public static int getPacManX() {
        return (int) (location.x / CELL_SIZE);
    }

    public static int getPacManY() {
        return (int) (location.y / CELL_SIZE);
    }



    // Additional methods for collision detection and life logic can be added here
    // public static ArrayList<Ghosts> getGhosts(){
    //     return ghosts;
    // }
    // public boolean checkGhostCollision() {
    //     if(!ghosts.isEmpty()) {
    //         for (Ghosts ghost : ghosts) {
    //             if (ghost.y == pacMan.x && ghost.x == pacMan.y) {  // Odwrócone współrzędne
    //                 return true;
    //             }
    //         }
    //     }
    //     return false;
    // }
    // public void lifeLogic(){
    //     if(checkGhostCollision() &&  pacMan.countLife > 0){
    //         System.out.println(pacMan.countLife);
    //         pacMan.countLife -= 1;
    //         // Footer.hearts.get(countLife).setVisible(false);
    //         if (pacMan.countLife == 0) {
    //             pacMan.isAlive = false;
    //         }
    //     }
    // }
    public static int returnPoints(){
        return point;
    }
    private void createAskDialog(){
        JDialog dialog = new JDialog();
        dialog.setTitle("Enter Nick and Score");
        dialog.setSize(new Dimension(400, 200));
        dialog.setLayout(new BorderLayout());
        dialog.setVisible(true);
        int score = returnPoints();
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        JLabel nameLabel = new JLabel("Enter Nick:");
        JTextField nameField = new JTextField();
        JLabel yourScore = new JLabel("Your Score: "+score);

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(yourScore);

        JButton okButton = new JButton("Save");
        okButton.addActionListener(e -> {
            String name = nameField.getText();

            HighScoreSerializer.addNewScore(name,score);
            System.exit(0);
            dialog.dispose();
        });

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(okButton, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(this);
    }
    }