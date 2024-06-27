import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Footer extends JPanel {
    public static ArrayList<Heart> hearts;

    public Footer() {
        setPreferredSize(new Dimension(400, 70));
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        showHearts();
        setBackground(Color.BLACK);

    }

    private void showHearts() {
        int maxAmountOfHearts = 3; // Ustawia maksymalną ilość serc na aktualną liczbę żyć
        hearts = new ArrayList<>();

        for (int i = 0; i < maxAmountOfHearts; i++) {
            Heart heart = new Heart();
            hearts.add(heart);
            add(heart);
        }
    }






}
