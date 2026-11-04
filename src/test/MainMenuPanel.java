package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Grafinis komponentas, atvaizduojantis pagrindinį žaidimo meniu.
 * Leidžia vartotojui pasirinkti žaidimo pradžią, įkėlimą arba išėjimą.
 */
public class MainMenuPanel extends JPanel {
    private GameFrame parentFrame;

    /**
     * Sukuria pagrindinį meniu panelę.
     *
     * @param parentFrame pagrindinis žaidimo langas, kuriame atvaizduojamas šis meniu
     */
    public MainMenuPanel(GameFrame parentFrame) {
        this.parentFrame = parentFrame;
        setPreferredSize(new Dimension(800, 600));
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        initializeMenu();
    }

    /**
     * Inicializuoja pagrindinio meniu mygtukus (New Game, Load Game, Exit).
     */
    private void initializeMenu() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);

        // Antraštė
        JLabel titleLabel = new JLabel("ASTEROIDS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLabel, gbc);

        // Mygtukas: Naujas žaidimas
        JButton newGameButton = createMenuButton("NEW GAME");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDifficultyMenu();
            }
        });
        gbc.gridy = 1;
        add(newGameButton, gbc);



        // Mygtukas: Išeiti
        JButton exitButton = createMenuButton("EXIT");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gbc.gridy = 3;
        add(exitButton, gbc);
    }

    /**
     * Sukuria vieną standartinį mygtuką meniu su nurodytu tekstu.
     *
     * @param text mygtuko pavadinimas
     * @return suformatuotas JButton objektas
     */
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(70, 70, 70));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        return button;
    }

    /**
     * Parodo papildomą meniu, kuriame galima pasirinkti žaidimo sudėtingumą.
     */
    private void showDifficultyMenu() {
        removeAll();
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);

        // Antraštė
        JLabel titleLabel = new JLabel("PICK A DIFFICULTY:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLabel, gbc);

        // Lengvas lygis
        JButton easyButton = createMenuButton("EASY");
        easyButton.addActionListener(e -> startGame("EASY"));
        gbc.gridy = 1;
        add(easyButton, gbc);

        // Vidutinis lygis
        JButton mediumButton = createMenuButton("MEDIUM");
        mediumButton.addActionListener(e -> startGame("MEDIUM"));
        gbc.gridy = 2;
        add(mediumButton, gbc);

        // Sunkus lygis
        JButton hardButton = createMenuButton("HARD");
        hardButton.addActionListener(e -> startGame("HARD"));
        gbc.gridy = 3;
        add(hardButton, gbc);

        // Grįžti atgal į pagrindinį meniu
        JButton backButton = createMenuButton("BACK");
        backButton.addActionListener(e -> {
            removeAll();
            initializeMenu();
            revalidate();
            repaint();
        });
        gbc.gridy = 4;
        add(backButton, gbc);

        revalidate();
        repaint();
    }

    /**
     * Paleidžia žaidimą su pasirinktu sudėtingumu.
     *
     * @param difficulty pasirinkto žaidimo sudėtingumo lygis (EASY, MEDIUM, HARD arba LOADED)
     */
    private void startGame(String difficulty) {
        System.out.println("Starting the game. Difficulty: " + difficulty);

        Difficulty selectedDifficulty;

        switch (difficulty.toLowerCase()) {
            case "easy":
                selectedDifficulty = Difficulty.EASY;
                break;
            case "medium":
                selectedDifficulty = Difficulty.MEDIUM;
                break;
            case "hard":
                selectedDifficulty = Difficulty.HARD;
                break;
            default:
                // Numatytas variantas, jei perduodamas nežinomas lygis
                selectedDifficulty = Difficulty.EASY;
                System.out.println("Unknown difficulty. Defaulting to EASY.");
        }

        GamePanel gamePanel = new GamePanel(selectedDifficulty);
        parentFrame.setContentPane(gamePanel);
        parentFrame.revalidate();
        parentFrame.repaint();
        gamePanel.requestFocusInWindow();
    }
}
