import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Fruit extends JLabel {
    public TypeFruits typeFruits;
    public int x,y;
    public ImageIcon[] icon;
    private int CELL_SIZE = WriteGrid.CELL_SIZE;
    public boolean isEaten = false;
    public int[][] ghostsSpawn;
    public boolean visibility = true;
    ArrayList<int[]> path;
    public int points;
    public Fruit(TypeFruits typeFruits, int x, int y) {
        super();
        this.typeFruits = typeFruits;
        this.x = x;
        this.y = y;
        loadImg();
        setIcon(icon[0]);
        setBounds(x*CELL_SIZE,y*CELL_SIZE, CELL_SIZE-1,CELL_SIZE-1);
        setOpaque(true);
        setPreferredSize(new Dimension(CELL_SIZE-1,CELL_SIZE-1));
        setBackground(Color.BLACK);
        setVisible(true);

        this.points = this.getPoints();



    }
    private void loadImg(){
        String type = typeFruits.toString();
        icon = new ImageIcon[2];
        try{
            icon[0] = new ImageIcon(new ImageIcon("PacManProject\\img\\fruits\\"+type+".png").getImage().getScaledInstance(CELL_SIZE,CELL_SIZE,CELL_SIZE));
            icon[1] = new ImageIcon(new ImageIcon("PacManProject\\img\\barriers\\P.png").getImage().getScaledInstance(CELL_SIZE,CELL_SIZE,CELL_SIZE));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private int[][] getGhostsSpawnCords(char[][] arr){
         ghostsSpawn = new int[3][2];
         for(int i = 0; i< arr.length;i++){
             for(int j = 0; j<arr[i].length;i++){
                 if(arr[i][j] == 'Q'){
                     ghostsSpawn[0][0] = i+1;
                     ghostsSpawn[0][1] = j-1;
                     ghostsSpawn[1][0] = i+1;
                     ghostsSpawn[1][1] = j;
                     ghostsSpawn[2][0] = i+1;
                     ghostsSpawn[2][1] = j+1;

                 }
             }
         }
         return ghostsSpawn;
    }

    private void getPathCords(int[][] arr){
        for(int i = 0; i<arr.length;i++){
            for(int j = 0; j<arr[i].length;j++){
                if(arr[i][j] == 0 && i!=ghostsSpawn[0][0]&&
                        j!=ghostsSpawn[0][1] && j!=ghostsSpawn[1][1] && j!=ghostsSpawn[2][1]){
                    path.add(new int[]{i,j});
                }
            }
        }
    }
    public int getPoints(){
        switch (this.typeFruits){
            case sugar -> {
                if(visibility)
                    return 150;
                else return 0;
            }
            case apple, orange -> {
                if(visibility)
                    return 500;
                else return 0;
            }
            case cherry -> {
                if(visibility)
                    return 750;
                else return 0;
            }
            case strawberry ->  {
                if(visibility)
                    return 1500;
                else return 0;
            }
        }
        return 0;
    }


}
