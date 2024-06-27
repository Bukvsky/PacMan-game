import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class HighScore implements Serializable {

    private List<Score> scores;

    public HighScore() {
        scores = new ArrayList<>();
    }

    public List<Score> getScores() {
        return scores;
    }

    public void addScores(String pn, int result){
        Score score = new Score(pn,result);
        scores.add(score);
    }
    public void sortScores(){
        Collections.sort(scores, Comparator.comparing(Score::getScore).reversed());
    }
}
