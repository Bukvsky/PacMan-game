import javax.swing.*;
import java.awt.*;

public class NewGame extends MyFrame {
    JFrame frame = new JFrame();
    JLabel label = new JLabel("New game");

    NewGame(){
        frame.setTitle("New game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.setMinimumSize(new Dimension(800,800));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setIconImage(icon.getImage());
        createGamePanels(frame);
    }

    public static void createGamePanels(JFrame frame){

        JPanel headPanel = new JPanel();
        JPanel left = new JPanel();
        JPanel gamePanel = new JPanel();
        JPanel footer = new JPanel();
        JPanel right = new JPanel();

        left.setBackground(Color.ORANGE);
        headPanel.setBackground(Color.RED);
        gamePanel.setBackground(Color.BLACK);
        footer.setBackground(Color.blue);
        right.setBackground(Color.PINK);

        gamePanel.add(createGrid());


        headPanel.setPreferredSize(new Dimension(800,100));
        gamePanel.setPreferredSize(new Dimension(800,630));
        footer.setPreferredSize(new Dimension(800,70));
        left.setPreferredSize(new Dimension(100,600));
        right.setPreferredSize(new Dimension(100,600));

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
}
