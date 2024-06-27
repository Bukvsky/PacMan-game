import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
public class HighScoreSerializer {
    public static void saveScores(HighScore highScore) {
        try (FileOutputStream fos = new FileOutputStream("PacManProject/HighScores.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(highScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HighScore loadScores() {
        HighScore highScore = new HighScore();
        try (FileInputStream fi = new FileInputStream("PacManProject/HighScores.ser");
             ObjectInputStream oi = new ObjectInputStream(fi)) {
            highScore = (HighScore) oi.readObject();
            highScore.sortScores();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return highScore;
    }


    public static void addNewScore(String name, int score) {
        HighScore highScore = HighScoreDeserializer.loadScores();
        highScore.addScores(name, score);
        saveScores(highScore);
    }

    public static void main(String[] args) {
        addNewScore("test",0);
    }

}
