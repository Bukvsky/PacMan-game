import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyFrame extends JFrame {
    private static final Font customFont1 = CustomFonts.loadCustomFont("PacManProject\\Fonts\\pacfont-good-font\\Pacfont-ZEBZ.ttf", 66f);
    private static final Font customFont2 = CustomFonts.loadCustomFont("PacManProject\\Fonts\\emulogic-font\\Emulogic-zrEw.ttf", 40f);
    private static final Font customFont4 = CustomFonts.loadCustomFont("PacManProject\\Fonts\\emulogic-font\\Emulogic-zrEw.ttf", 20f);

    private static final Font customFont3 = CustomFonts.loadCustomFont("PacManProject\\Fonts\\emulogic-font\\Emulogic-zrEw.ttf", 16f);
    protected final static ImageIcon icon = new ImageIcon("PacManProject\\img\\Pacman.png");

    private JPanel mainPanel;
    private CardLayout cardLayout;

    MyFrame() {
        super("PacMan");
        this.getContentPane().setBackground(Color.BLACK);
        this.getRootPane().setBorder(BorderFactory.createEmptyBorder());
        this.setMinimumSize(new Dimension(800, 800));
        this.setVisible(true);
        this.setIconImage(icon.getImage());
        this.setLayout(new BorderLayout());
        createPanels();
    }

    public void createPanels() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel menuPanel = new JPanel(new GridBagLayout()); // Główne menu
        JPanel mapSelectionPanel = new JPanel(new GridBagLayout()); // Panel wyboru mapy

        // Tworzenie panelu menu
        createMenuPanel(menuPanel);
        createMapSelectionPanel(mapSelectionPanel);

        // Dodanie paneli do mainPanel
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(mapSelectionPanel, "MapSelection");

        // Dodanie mainPanel do ramki
        this.add(mainPanel, BorderLayout.CENTER);

        JPanel jPanel1 = new JPanel();
        JPanel jPanel3 = new JPanel(new GridBagLayout());

        jPanel1.setPreferredSize(new Dimension(100, 200));
        jPanel3.setPreferredSize(new Dimension(100, 200));

        // Etykieta / head panel
        JLabel headTitle = addLabel("PacMan", customFont1);
        headTitle.setPreferredSize(new Dimension(800, 200));
        headTitle.setVerticalTextPosition(JLabel.CENTER);
        headTitle.setHorizontalAlignment(JLabel.CENTER);
        headTitle.setBackground(Color.BLACK);
        headTitle.setForeground(Color.YELLOW);

        // Panel 1 / head Panel
        jPanel1.add(headTitle);
        jPanel1.setBackground(Color.BLACK);

        // Ustawienie 3 panelu
        jPanel3.setBackground(Color.BLACK);
        JLabel l1 = allRightsReserved("©2024 igor bukowski");
        JLabel l2 = allRightsReserved("all rights reserved");
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.insets = new Insets(5, 10, 5, 10);
        jPanel3.add(l1, gbc2);
        gbc2.gridy++;
        jPanel3.add(l2, gbc2);

        // Dodawanie paneli do ramki
        this.add(jPanel1, BorderLayout.NORTH);
        this.add(jPanel3, BorderLayout.SOUTH);
    }

    private void createMenuPanel(JPanel menuPanel) {
        JButton button1 = createButton("  New game");
        JButton button2 = createButton("  HighScore");
        JButton button3 = createButton("  Exit");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "MapSelection");
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MyFrame.this, "High Scores will be displayed here.");
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        menuPanel.setBackground(Color.BLACK);
        menuPanel.add(button1, gbc);
        gbc.gridy++;
        menuPanel.add(button2, gbc);
        gbc.gridy++;
        menuPanel.add(button3, gbc);
    }

    private void createMapSelectionPanel(JPanel mapSelectionPanel) {
        JLabel label = new JLabel("Select a map size:");
        String[] sizes = {"10destX0","15destX5","20x20","25x25","30x30","35x35","40x40"};
        JComboBox maps = new JComboBox(sizes);
        maps.setBackground(Color.BLACK);
        maps.setForeground(Color.YELLOW);
        maps.setFont(customFont4);
        maps.setFocusable(false);
        maps.setBorder(BorderFactory.createEmptyBorder());

        label.setForeground(Color.YELLOW);
        label.setFont(customFont4);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backButton = createButton("Back");
        JButton startButton = createButton("Start");
        backButton.setHorizontalTextPosition(JButton.CENTER);
        backButton.setMargin(new Insets(0,10,0,0));
        startButton.setHorizontalTextPosition(JButton.CENTER);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==startButton){
                    NewGame newGame = new NewGame();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Menu");
            }
        });

        BorderLayout bl = new BorderLayout();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 1);

        mapSelectionPanel.setBackground(Color.BLACK);
        mapSelectionPanel.add(label, gbc);
        gbc.gridx++;
        mapSelectionPanel.add(maps, gbc);
        gbc.gridx-=1;// Add the JComboBox here
        gbc.gridy++;
        mapSelectionPanel.add(startButton, gbc);
        gbc.gridy++;
        mapSelectionPanel.add(backButton, gbc);
    }

    public JLabel addLabel(String word, Font font) {
        JLabel label = new JLabel(word, SwingConstants.CENTER);
        label.setFont(font);
        return label;
    }

    public JButton createButton(String word) {
        JButton button = new JButton(word);
        button.setPreferredSize(new Dimension(500, 50));
        button.setFocusable(false);
        button.setFocusPainted(false);
        button.setMargin(new Insets(10, 50, 10, 10)); // Ustawienie marginesu tylko od lewej strony na 50 pikseli
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFont(customFont2);
        button.setUI(new BasicButtonUI());
        button.setForeground(Color.YELLOW);
        button.setBackground(Color.BLACK);
        addHovereffect(button);
        return button;
    }

    public JLabel allRightsReserved(String word) {
        JLabel label = new JLabel(word);
        label.setForeground(Color.white);
        label.setBackground(Color.BLACK);
        label.setFont(customFont3);
        return label;
    }

    private static void addHovereffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.YELLOW);
            }
        });
    }

    public static void addActionExit(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void startGameWithMap(String mapName) {
        JOptionPane.showMessageDialog(this, "Starting game with " + mapName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MyFrame());
    }
}
