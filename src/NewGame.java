import javax.swing.*;
import java.awt.*;
import java.util.List;
public class NewGame extends MyFrame {
    private static final Font customFont = CustomFonts.loadCustomFont("PacManProject\\Fonts\\emulogic-font\\Emulogic-zrEw.ttf", 15f);

    public JFrame frame = new JFrame();
    private JLabel label = new JLabel("New game");
    private static JLabel pointsLabel;

    NewGame(){
        frame.setTitle("New game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000,900);
        frame.setMinimumSize(new Dimension(800,900));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setIconImage(icon.getImage());
        createGamePanels(frame);
    }

    public static void createGamePanels(JFrame frame){

        JPanel headPanel = new JPanel();
        JPanel left = new JPanel();
        JPanel gamePanel = new JPanel();
        JPanel footer = new JPanel(new BorderLayout());
        JPanel right = new JPanel();
        Footer heartsFooter = new Footer();
        JLabel hs = new JLabel("High-Score:");
        hs.setFont(customFont);
        hs.setForeground(Color.white);
        hs.setBackground(Color.black);
        hs.setVisible(true);

        HighScore highScore = HighScoreDeserializer.loadScores();
        JLabel hsInt = new JLabel(String.valueOf(getMax()));
        hsInt.setFont(customFont);
        hsInt.setForeground(Color.white);
        hsInt.setBackground(Color.black);
        hsInt.setVisible(true);

        JLabel ys = new JLabel("Score:");
        ys.setFont(customFont);
        ys.setForeground(Color.white);
        ys.setBackground(Color.black);
        ys.setVisible(true);

        pointsLabel = new JLabel();
        pointsLabel.setFont(customFont);
        pointsLabel.setForeground(Color.white);
        pointsLabel.setBackground(Color.black);
        pointsLabel.setVisible(true);
        getPoints(pointsLabel);





        left.setBackground(Color.BLACK);
        left.setLayout(new BoxLayout(left,BoxLayout.Y_AXIS));
        headPanel.setBackground(Color.BLACK);
        gamePanel.setBackground(Color.BLACK);
        footer.setBackground(Color.BLACK);
        right.setBackground(Color.BLACK);


        gamePanel.add(createGrid());
        footer.add(heartsFooter, BorderLayout.CENTER);
        left.add(hs);
        left.add(Box.createRigidArea(new Dimension(0, 3)));
        left.add(hsInt);
        left.add(left.add(Box.createRigidArea(new Dimension(0, 20))));
        left.add(ys);
        left.add(Box.createRigidArea(new Dimension(0, 3)));
        left.add(pointsLabel);



        headPanel.setPreferredSize(new Dimension(800,30));
        gamePanel.setPreferredSize(new Dimension(1000,830));
        footer.setPreferredSize(new Dimension(800,70));
        left.setPreferredSize(new Dimension(200,600));
        right.setPreferredSize(new Dimension(200,600));

        frame.add(headPanel, BorderLayout.NORTH);
        frame.add(gamePanel,BorderLayout.CENTER);
        frame.add(footer, BorderLayout.SOUTH);
        frame.add(left,BorderLayout.WEST);
        frame.add(right,BorderLayout.EAST);
        frame.pack();
        frame.setVisible(true);


    }




    public static WriteGrid createGrid(){
        return new WriteGrid();


    }
    public static void getPoints(JLabel label){
        Thread point = new Thread(()->{
            try {
                while (true){
                    int points = WriteGrid.returnPoints();
                    //ASystem.out.println(points);
                    SwingUtilities.invokeLater(() -> {
                        label.setText(String.valueOf(points));
                        //System.out.println(points);
                    });
                    Thread.sleep(200);
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        point.start();
    }
    private static int getMax(){
        HighScore hs = HighScoreDeserializer.loadScores();
        List<Score> scores = hs.getScores();
        int maxScore = Integer.MIN_VALUE;
        for(Score score : scores){
            if(score.getScore()>maxScore){
                maxScore = score.getScore();
            }
        }
        return maxScore;
    }
}
