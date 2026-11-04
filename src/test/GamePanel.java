package test;

import Entities.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GamePanel yra pagrindinis žaidimo vykdymo paviršius (panelė), kuri:
 * <ul>
 *   <li>Rodo erdvėlaivį, ateivius ir foną</li>
 *   <li>Apdoroja klavišų paspaudimus</li>
 *   <li>Valdo žaidimo logiką ir atnaujinimus per Timer</li>
 *   <li>Tvarko susidūrimus ir žaidimo pabaigos logiką</li>
 * </ul>
 */
public class GamePanel extends JPanel implements KeyListener {
    private Image largeAlienImage;
    private Image smallAlienImage;
    private Image spaceshipImage;
    private Image backgroundImage;
    private Image asteroidImage;

    private final List<Entities> aliens;
    private final Timer gameTimer;
    private final Timer spawnTimer;
    private int backgroundY;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    private SpaceShip spaceship;
    private final Difficulty difficulty;
    private long startTime;

    /**
     * Sukuria naują žaidimo panelę su nurodytu sudėtingumo lygiu.
     * Inicijuoja vaizdus, objektus, mygtukus, laikmačius ir klavišų įvestį.
     *
     * @param difficulty pasirinktas žaidimo sudėtingumo lygis
     */
    public GamePanel(Difficulty difficulty) {
        this.difficulty = difficulty;
        setLayout(null);
        setPreferredSize(new Dimension(800, 600));

        aliens = new ArrayList<>();

        // Įkeliam paveikslėlius
        largeAlienImage = new ImageIcon(getClass().getResource("/Resources/LargeAlien.png")).getImage();
        smallAlienImage = new ImageIcon(getClass().getResource("/Resources/SmallAlien.png")).getImage();
        spaceshipImage = new ImageIcon(getClass().getResource("/Resources/Spaceship.png")).getImage();
        backgroundImage = new ImageIcon(getClass().getResource("/Resources/space_backround.png")).getImage();
        asteroidImage = new ImageIcon(getClass().getResource("/Resources/Asteroid.png")).getImage();

        // Pradinė ateivių banga
        aliens.addAll(GameSpawner.spawnEntities(difficulty));

        spaceship = new SpaceShip(new Position(370, 500), 0.0, 0.0);

        // Išsaugojimo mygtukas
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(10, 10, 100, 30);
        add(saveButton);
        saveButton.addActionListener(e -> new Thread(new SaveTask(aliens, spaceship)).start());

        // Nuskaitymo mygtukas
        JButton loadButton = new JButton("Load");
        loadButton.setBounds(120, 10, 100, 30);
        add(loadButton);
        loadButton.addActionListener(e -> new Thread(new LoadTask(aliens, spaceship, this)).start());

        // Klaviatūros nustatymai
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        // Žaidimo logikos atnaujinimo laikmatis
        gameTimer = new Timer(30, e -> {
            if (leftPressed) spaceship.moveLeft();
            if (rightPressed) spaceship.moveRight(getWidth());

            for (Entities alien : aliens) {
                alien.move();
            }

            backgroundY += 2;
            if (backgroundY >= getHeight()) backgroundY = 0;

            repaint();
        });
        gameTimer.start();

        // Naujų ateivių bangų laikmatis
        spawnTimer = new Timer(1000, e -> {
            if (System.currentTimeMillis() - startTime < 55000) {
                aliens.addAll(GameSpawner.spawnEntities(difficulty));
            }
        });
        spawnTimer.start();

        startTime = System.currentTimeMillis();
    }

    /**
     * Perpiešia komponentus: foną, ateivius, erdvėlaivį, laikmatį ir tvarko susidūrimus bei žaidimo pabaigą.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fonas
        g.drawImage(backgroundImage, 0, backgroundY, getWidth(), getHeight(), this);
        g.drawImage(backgroundImage, 0, backgroundY - getHeight(), getWidth(), getHeight(), this);

        // Ateiviai
        for (Entities entity : aliens) {
            Position pos = entity.getPosition();
            if (entity instanceof LargeAlien) {
                g.drawImage(largeAlienImage, pos.getX(), pos.getY(), 100, 100, this);
            } else if (entity instanceof SmallAlien) {
                g.drawImage(smallAlienImage, pos.getX(), pos.getY(), 75, 75, this);
            } else if (entity instanceof Asteroid) {
                g.drawImage(asteroidImage, pos.getX(), pos.getY(), 80, 80, this);
            }
        }

        // Erdvėlaivis
        if (spaceship != null) {
            Position shipPos = spaceship.getPosition();
            g.drawImage(spaceshipImage, shipPos.getX(), shipPos.getY(), 60, 60, this);
        }

        // Laikmačio tekstas
        long elapsedMillis = System.currentTimeMillis() - startTime;
        double seconds = elapsedMillis / 1000.0;
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String timeText = String.format("Time: %.2f", seconds);
        int textWidth = g.getFontMetrics().stringWidth(timeText);
        g.drawString(timeText, getWidth() - textWidth - 10, 30);

        // Žaidimo laiko patikra: pabaiga po 60 sek.
        if (elapsedMillis > 60000) {
            gameTimer.stop();
            spawnTimer.stop();
            endGame("You won! Game Over!");
        }

        // Susidūrimų tikrinimas
        Rectangle shipBounds = spaceship.getBounds();
        for (Entities alien : aliens) {
            if (shipBounds.intersects(alien.getBounds())) {
                gameTimer.stop();
                spawnTimer.stop();
                endGame("You crashed! 💥 Game Over!");
                break;
            }
        }

        requestFocusInWindow();
    }

    /**
     * Rodo pabaigos pranešimą ir leidžia žaidėjui pasirinkti tarp pagrindinio meniu arba išeiti.
     * @param message žinutė, kurią rodyti pabaigos lange
     */
    private void endGame(String message) {
        int choice = JOptionPane.showOptionDialog(
                this,
                message,
                "Game Over",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null,
                new String[]{"Main Menu", "Exit Game"},
                "Main Menu"
        );

        if (choice == 0) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
            SwingUtilities.invokeLater(() -> new GameFrame().setVisible(true));
        } else {
            System.exit(0);
        }
    }

    // === Klavišų įvykių metodai ===

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = true;
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
