package test;

import Entities.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements KeyListener {
    private Image largeAlienImage;
    private Image smallAlienImage;
    private Image spaceshipImage;
    private Image backgroundImage;
    private Image asteroidImage;

    private final List<Entities> aliens;
    private final Timer timer;
    private int backgroundY;

    private SpaceShip spaceship;
    private long startTime;


    public GamePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(800, 600));

        aliens = new ArrayList<>();

        largeAlienImage = new ImageIcon(getClass().getResource("/Resources/LargeAlien.png")).getImage();
        smallAlienImage = new ImageIcon(getClass().getResource("/Resources/SmallAlien.png")).getImage();
        spaceshipImage = new ImageIcon(getClass().getResource("/Resources/Spaceship.png")).getImage();
        backgroundImage = new ImageIcon(getClass().getResource("/Resources/space_backround.png")).getImage();
        asteroidImage = new ImageIcon(getClass().getResource("/Resources/Asteroid.png")).getImage();

        // Initial alien setup
        aliens.add(new SmallAlien(new Position(150, 100), 4.3, 2.34));
        aliens.add(new LargeAlien(new Position(120, 100), 1.6, 1, 5, false));
        aliens.add(new LargeAlien(new Position(620, 100), 1.6, 1, 5, true));

        // Add asteroid
        aliens.add(new Asteroid(new Position(400, 50), 0.0, 8.0, 1));

        // Setup spaceship
        spaceship = new SpaceShip(new Position(370, 500), 0.0, 0.0); // center-bottom

        // Add buttons
        JButton saveButton = new JButton("Išsaugoti");
        saveButton.setBounds(10, 10, 100, 30);
        add(saveButton);
        saveButton.addActionListener(e -> new Thread(new SaveTask(aliens, spaceship)).start());

        JButton loadButton = new JButton("Nuskaityti");
        loadButton.setBounds(120, 10, 100, 30);
        add(loadButton);
        loadButton.addActionListener(e -> new Thread(new LoadTask(aliens, spaceship, this)).start());

        // Focus settings
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        // Timer for animation
        timer = new Timer(30, e -> {
            for (Entities alien : aliens) {
                alien.move();
            }

            // Scroll background upward
            backgroundY += 2;
            if (backgroundY >= getHeight()) {
                backgroundY = 0;
            }

            repaint();
        });
        timer.start();
        startTime = System.currentTimeMillis();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw scrolling background (looping)
        g.drawImage(backgroundImage, 0, backgroundY, getWidth(), getHeight(), this);
        g.drawImage(backgroundImage, 0, backgroundY - getHeight(), getWidth(), getHeight(), this);

        // Draw aliens and asteroids
        for (Entities entity : aliens) {
            Position entityPos = entity.getPosition();
            if (entity instanceof LargeAlien) {
                g.drawImage(largeAlienImage, entityPos.getX(), entityPos.getY(), 100, 100, this);
            } else if (entity instanceof SmallAlien) {
                g.drawImage(smallAlienImage, entityPos.getX(), entityPos.getY(), 75, 75, this);
            } else if (entity instanceof Asteroid) {
                g.drawImage(asteroidImage, entityPos.getX(), entityPos.getY(), 80, 80, this);
            }
        }

        // Draw spaceship
        if (spaceship != null) {
            Position shipPos = spaceship.getPosition();
            g.drawImage(spaceshipImage, shipPos.getX(), shipPos.getY(), 60, 60, this);
        }
        // === Draw survival time at top-right corner ===
        long elapsedMillis = System.currentTimeMillis() - startTime;
        double seconds = elapsedMillis / 1000.0;

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String timeText = String.format("Time: %.2f", seconds);
        int textWidth = g.getFontMetrics().stringWidth(timeText);
        g.drawString(timeText, getWidth() - textWidth - 10, 30);

        requestFocusInWindow();
    }

    // === KeyListener Methods ===
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            System.out.println("Paspaustas klavišas: A (kairė)");
            spaceship.moveLeft();
        } else if (key == KeyEvent.VK_RIGHT) {
            System.out.println("Paspaustas klavišas: D (dešinė)");
            spaceship.moveRight(getWidth());
        } else {
            System.out.println("Paspaustas kitas klavišas: " + KeyEvent.getKeyText(key));
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}