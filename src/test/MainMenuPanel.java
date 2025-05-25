package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    private GameFrame parentFrame;

    public MainMenuPanel(GameFrame parentFrame) {
        this.parentFrame = parentFrame;
        setPreferredSize(new Dimension(800, 600));
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        initializeMenu();
    }

    private void initializeMenu() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);

        // Title
        JLabel titleLabel = new JLabel("ASTEROIDS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLabel, gbc);

        // New Game button
        JButton newGameButton = createMenuButton("NEW GAME");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDifficultyMenu();
            }
        });
        gbc.gridy = 1;
        add(newGameButton, gbc);

        // Load Game button
        JButton loadGameButton = createMenuButton("LOAD GAME");
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame("LOADED");
            }
        });
        gbc.gridy = 2;
        add(loadGameButton, gbc);

        // Exit button
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

    private void showDifficultyMenu() {
        removeAll();
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);

        // Title
        JLabel titleLabel = new JLabel("PICK A DIFFICULTY:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLabel, gbc);

        // Easy button
        JButton easyButton = createMenuButton("EASY");
        easyButton.addActionListener(e -> startGame("EASY"));
        gbc.gridy = 1;
        add(easyButton, gbc);

        // Medium button
        JButton mediumButton = createMenuButton("MEDIUM");
        mediumButton.addActionListener(e -> startGame("MEDIUM"));
        gbc.gridy = 2;
        add(mediumButton, gbc);

        // Hard button
        JButton hardButton = createMenuButton("HARD");
        hardButton.addActionListener(e -> startGame("HARD"));
        gbc.gridy = 3;
        add(hardButton, gbc);

        // Back button
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

    private void startGame(String difficulty) {
        System.out.println("Starting the game. Difficulty: " + difficulty);

        // Switch to game panel
        GamePanel gamePanel = new GamePanel();
        parentFrame.setContentPane(gamePanel);
        parentFrame.revalidate();
        parentFrame.repaint();
        gamePanel.requestFocusInWindow();
    }
}