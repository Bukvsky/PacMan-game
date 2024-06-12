import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WriteGrid extends JPanel {
    private static final int ROWS = 27;
    private static final int COLS = 27;
    private static final int CELL_SIZE = 30; // Rozmiar pojedynczej komórki
    private static final int width = ROWS * CELL_SIZE;
    private static final int height = ROWS * CELL_SIZE;
    public static GridBagConstraints gbc;
    private static Point location;
    private PacMan pacMan;
    private JLayeredPane layeredPane;
    private static Tile[][] tiles; // 2D array to store Tile objects
    private Thread movementThread;
    private int currentDir;

    private boolean isMoving = false;

    public WriteGrid() {
        tiles = new Tile[ROWS][COLS];
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
            }
        }

        // Tworzenie i umieszczanie PacMan
        pacMan = new PacMan(1, 2);
        location = pacMan.getLocation();
        Ghosts red = new Ghosts(14, 12, Colors.RED, Grids.gridBin);
        Ghosts pink = new Ghosts(12, 12, Colors.PINK, Grids.gridBin);
        Ghosts blue = new Ghosts(14, 12, Colors.BLUE,Grids.gridBin);
        Ghosts honey = new Ghosts(12, 14, Colors.HONEY, Grids.gridBin);
        pacMan.setBounds(2 * CELL_SIZE, 1 * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Ustawienie początkowej pozycji
        layeredPane.add(pacMan, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(red, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(pink, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(blue, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(honey, JLayeredPane.PALETTE_LAYER);
        // Dodanie layeredPane do WriteGrid
        setLayout(new BorderLayout());
        add(layeredPane, BorderLayout.CENTER);
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
            }

            if (newDir != -1) {
                currentDir = newDir;
                if (!isMoving) {
                    startMoving();
                }
            }
        }
    }

    private void startMoving() {
        isMoving = true;
        movementThread = new Thread(() -> {
            try {
                while (true) {
                    SwingUtilities.invokeLater(() -> {
                        location = pacMan.getLocation();
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
                        pacMan.setLocation(x, y);
                    });

                    Thread.sleep(300); // Prędkość poruszania się (w milisekundach)
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        movementThread.start();
    }

    private void stopMoving() {
        isMoving = false;
        if (movementThread != null) {
            movementThread.interrupt();
            movementThread = null;
        }
    }

    public static int getPacManX() {
        return (int)(location.x/CELL_SIZE);
    }
    public static int getPacManY(){
        return (int)(location.y/CELL_SIZE);
    }



}
