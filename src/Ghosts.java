import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

class Ghosts extends JLabel implements Runnable{
    public int x, y;
    private int prevX, prevY;
    private static int ROWS = 27;
    private static int COLS = 27;
    private int dir;
    private int CELL_SIZE = 30;
    private Colors color;
    private boolean isAnimating;
    private ImageIcon[][] animations;
    private int[][] grid;
    private int lastHorizontal;
    private boolean ghostsFlag;

    private int currentFrame = 0;
    public static Thread movementThread;
    public  int threadTime = 200;
    public int timeSum = threadTime;
    private double time = 0;

    public Ghosts(int x, int y, Colors color, int[][] grid) {
        super();
        this.x = x;
        this.y = y;
        this.color = color;
        this.ghostsFlag = WriteGrid.flag;
        setBounds(x*CELL_SIZE,y*CELL_SIZE,CELL_SIZE,CELL_SIZE);
        loadAnimations();
        setIcon(animations[0][0]);
        this.grid = grid;
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

    public void loadAnimations() {
        animations = new ImageIcon[2][2];
        String color = getColor();
        try {
            animations[0][0] = new ImageIcon(new ImageIcon("PacManProject\\img\\ghosts\\" + color + "\\left1.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[0][1] = new ImageIcon(new ImageIcon("PacManProject\\img\\ghosts\\" + color + "\\left2.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[1][0] = new ImageIcon(new ImageIcon("PacManProject\\img\\ghosts\\" + color + "\\right1.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            animations[1][1] = new ImageIcon(new ImageIcon("PacManProject\\img\\ghosts\\" + color + "\\right2.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
            //animations[2][0] = new ImageIcon(new ImageIcon("PacManProject\\img\\ghosts\\bonus\\1.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
           // animations[2][1] = new ImageIcon(new ImageIcon("PacManProject\\img\\ghosts\\bonus\\2.png").getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startAnimationThread() {
        Random random = new Random();
        switch (dir){
            case 0:
                dir=1;
                break;
            case 1:
                dir = random.nextInt(2);
                break;
            case 2:
                dir = 0;
                break;
            default:
                dir = random.nextInt();
                break;

        }
        Thread animationThread = new Thread(() -> {
            while (true) {

                currentFrame = (currentFrame + 1) % 2;
                dir = dir % 2;


                setIcon(animations[dir][currentFrame]);



            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        });
        animationThread.start();
    }

    private void setDir(int direction) {
        this.dir = direction;
        isAnimating = true;
    }


//    private void printVisitedMap(boolean[][] visited, int[][] grid) {
//        System.out.println("Visited Map:");
//        for (int i = 0; i < visited.length; i++) {
//            for (int j = 0; j < visited[i].length; j++) {
//                if (i == 12 && j == 14) {
//                    System.out.print("P "); // Pac-Man position
//                } else if (i == 2 && j == 1) {
//                    System.out.print("G "); // Ghost position
//                } else if (visited[i][j]) {
//                    System.out.print("# "); // Visited node
//                } else {
//                    if (grid[i][j] == 1) {
//                        System.out.print("* "); // Wall
//                    } else {
//                        System.out.print("  "); // Unvisited node
//                    }
//                }
//            }
//            System.out.println();
//        }
//    }
//    private static boolean isDestination(int x, int y, int pcX, int pcY) {
//        return (x == pcX && y == pcY);
//    } private static double heuristic(int x, int y, int pcX, int pcY) {
//        return Math.abs(x -pcX) + Math.abs(y - pcY);
//    }
//    private static boolean isValid(int[][] grid,int x, int y) {
//        return (x >= 0 && x < ROWS && y >= 0 && y < COLS && grid[x][y] == 0);
//    }
//
//
//
//    public  int[] getNextMove(int[][] grid, int startX, int startY, int destX, int destY) {
//        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(node -> node.f));
//        boolean[][] closedList = new boolean[ROWS][COLS];
//        Node[][] nodes = new Node[ROWS][COLS];
//
//        for (int i = 0; i < ROWS; i++) {
//            for (int j = 0; j < COLS; j++) {
//                nodes[i][j] = new Node(i, j, (double)(Math.pow(grid.length,2)) , (double)(Math.pow(grid.length,2)), null);
//            }
//        }
//
//        openList.add(new Node(startX, startY, 0.0, 0.0, null));
//        nodes[startX][startY].f = 0.0;
//        nodes[startX][startY].g = 0.0;
//
//        while (!openList.isEmpty()) {
//            Node current = openList.poll();
//            int x = current.x;
//            int y = current.y;
//            printVisitedMap(closedList,grid);
//            if (isDestination(x, y, destX, destY)) {
//                return getPathFromDestination(nodes,destX,destY,startX,startY);
//            }
//
//            closedList[x][y] = true;
//
//            int[] dx = {-1, 0, 1, 0};
//            int[] dy = {0, 1, 0, -1};
//
//            for (int i = 0; i < 4; i++) {
//                int newX = x + dx[i];
//                int newY = y + dy[i];
//
//                if (isValid(grid,newX, newY) && isValid(grid, newX, newY) && !closedList[newX][newY]) {
//                    double gNew = current.g + 1.0;
//                    double hNew = heuristic(newX, newY, destX, destY);
//                    double fNew = gNew + hNew;
//
//                    if (nodes[newX][newY].f == (double)(Math.pow(grid.length,2)) || nodes[newX][newY].f > fNew) {
//                        openList.add(new Node(newX, newY, fNew, gNew, current));
//                        nodes[newX][newY].f = fNew;
//                        nodes[newX][newY].g = gNew;
//                        nodes[newX][newY].parent = current;
//                    }
//                }
//            }
//        }
//    return getRandomMove(startX,startY);    }


//    public int[] getPathFromDestination(Node[][] nodes, int destX, int destY, int startX, int startY) {
//        List<int[]> path = new ArrayList<>();
//        Node current = nodes[destX][destY]; // Zaczynamy od węzła docelowego
//
//        while (current != null) {
//            int[] coordinates = {current.x, current.y};
//            path.add(coordinates);
//            current = current.parent;
//        }
//        Collections.reverse(path);
//        int nextX = path.get(1)[0];
//        int nextY = path.get(1)[1];
//        return path.get(1);
//
//
//    }
//    public int[] getRandomMove(int x, int y) {
//
//        int curX = x;
//        int curY = y;
//        int move = new Random().nextInt(4);
//        while(curX == x && curY == y){
//        switch (move) {
//            case 0: {
//                if (isValid(grid, x, y+1)) {
//                    y++;
//
//                }break;}
//            case 1: {
//                if (isValid(grid, x + 1, y)) {
//                    x++;
//
//                }
//                break;
//            }
//            case 2: {
//                if (isValid(grid, x, y - 1)) {
//                    y--;
//
//                }
//                break;
//            }
//            default:{
//                if (isValid(grid, x-1, y)) {
//                    x--;
//
//                }
//                break;
//            }
//        }
//            move = new Random().nextInt(4);
//        }
//
//        return new int[]{x,y};
//    }
    private static boolean isValid(int[][] grid,int x, int y) {
      return (x >= 0 && x < ROWS && y >= 0 && y < COLS && grid[x][y] == 0);
    }
    public static void moveGhosts(int startX, int startY){
        Random random = new Random();
        int direction = random.nextInt(4); // Losowanie początkowego kierunku

        int x = startX;
        int y = startY;


    }
    public static int[] next(int[][] grid, int x, int y, int prevX, int prevY) {
        int countTrue = 0;
        boolean[] closedList = new boolean[4];

        for (int i = 0; i < 4; i++) {
            int[] nextCoords = getCordsOfNext(grid, x, y, i);
            boolean isValidMove = isValid(grid, nextCoords[0], nextCoords[1]);
            boolean notPreviousMove = nextCoords[0] != prevX || nextCoords[1] != prevY;

            if (isValidMove) {
                if (notPreviousMove) {
                    closedList[i] = true;
                    countTrue++;
                }
            }
        }

        if (countTrue == 0) {
            return new int[]{prevX, prevY}; // Jeśli nie ma dostępnych ruchów (z pominięciem poprzedniego pola), użyj poprzedniego ruchu
        }

        Random random = new Random();
        int k = random.nextInt(countTrue);

        for (int j = 0; j < 4; j++) {
            if (closedList[j]) {
                k--;
                if (k == -1) {
                    int[] nextMove = getCordsOfNext(grid, x, y, j);
                    if (nextMove[1] == 0) { // Jeśli duch jest na lewej krawędzi mapy, teleportuj go na prawą krawędź
                        return new int[]{nextMove[0], grid[0].length - 1};
                    } else if (nextMove[1] == grid[0].length - 1) { // Jeśli duch jest na prawej krawędzi mapy, teleportuj go na lewą krawędź
                        return new int[]{nextMove[0], 0};
                    } else {
                        return nextMove; // W przeciwnym razie wykonaj normalny ruch
                    }
                }
            }
        }

        return new int[]{prevX, prevY};
    }

    public static int[] getCordsOfNext(int[][] grid, int x, int y, int dir) {
        switch (dir) {
            case 0:
                return new int[]{x, y + 1};
            case 1:
                return new int[]{x + 1, y};
            case 2:
                return new int[]{x, y - 1};
            default:
                return new int[]{x - 1, y};
        }
    }




    public void run() {
        isAnimating = true;
        startAnimationThread();
        int prevX = x;
        int prevY = y;

        movementThread = new Thread(() -> {
            while (true) {

                int currentX = this.x;
                int currentY = this.y;


                //System.out.println("("+WriteGrid.getPacManX()+","+WriteGrid.getPacManY()+")");
                //int[] nextMove = getNextMove(this.grid, currentX, currentY, WriteGrid.getPacManX(), WriteGrid.getPacManY());
                int[] nextMove = next(grid, currentX, currentY, this.prevX, this.prevY); // Użyj poprzednich współrzędnych
                this.prevX = currentX;
                this.prevY = currentY;

                int newX = nextMove[0];
                int newY = nextMove[1];
                this.x = newX;
                this.y = newY;
                if(!WriteGrid.ghostStop)    {
                    setBounds(newY * CELL_SIZE, newX * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
                else {
                    waitForGhostStop();
                }

                try {
                    Thread.sleep(threadTime);
                    time+=(threadTime/1000);
                    if(time%5.0 == 0){

                    }



                } catch (InterruptedException e) {
                    System.out.println("Wątek pzerwany");
                }



            }
        });
        movementThread.start();
    }

    public void setThreadTime(int x){
        this.threadTime = x;
    }
    public void waitForGhostStop() {
        try {
            synchronized (Thread.currentThread()) {
                while (!WriteGrid.ghostStop) {
                    Thread.currentThread().wait(); // Wątek czeka na zmianę ghostStop na true
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Wątek przerwany");
            Thread.currentThread().interrupt(); // Obsługa przerwania wątku
        }
    }

}