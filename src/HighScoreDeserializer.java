import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class HighScoreDeserializer {
    public static HighScore loadScores() {
        HighScore highScore = new HighScore();
        try (FileInputStream fi = new FileInputStream("PacManProject/HighScores.ser");
             ObjectInputStream oi = new ObjectInputStream(fi)) {
            highScore = (HighScore) oi.readObject();
            highScore.sortScores(); // Sort scores after loading

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return highScore;
    }
}
