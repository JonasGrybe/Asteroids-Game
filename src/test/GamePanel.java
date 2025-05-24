package test;

import Entities.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private Image largeAlienImage;
    private Image smallAlienImage;
    private final List<Entities> aliens;
    private final Timer timer;

    public GamePanel() {
        setLayout(null); // absolute layout, kad galima būtų dėti mygtukus
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);

        aliens = new ArrayList<>();

        largeAlienImage = new ImageIcon(getClass().getResource("/Resources/LargeAlien.png")).getImage();
        smallAlienImage = new ImageIcon(getClass().getResource("/Resources/SmallAlien.png")).getImage();

        // Vienas mažas ateivis
        Entities smallAlien = new SmallAlien(new Position(150, 100), 1.3, 0.34);

        // Vienas didelis ateivis
        Entities largeAlien = new LargeAlien(new Position(300, 100), 1.6, 1, 5);

        aliens.add(smallAlien);
        aliens.add(largeAlien);


        // Sukuriam mygtukus
        JButton saveButton = new JButton("Išsaugoti");
        saveButton.setBounds(10, 10, 100, 30);
        add(saveButton);

        saveButton.addActionListener(e -> {
            Thread saveThread = new Thread(new SaveTask(aliens));
            saveThread.start();
        });

        JButton loadButton = new JButton("Nuskaityti");
        loadButton.setBounds(120, 10, 100, 30);
        add(loadButton);

        loadButton.addActionListener(e -> {
            Thread loadThread = new Thread(new LoadTask(aliens, this));
            loadThread.start();
        });


        // Timer, kuris judina ateivius kas 30 ms
        timer = new Timer(30, (ActionEvent e) -> {
            for (Entities alien : aliens) {
                alien.move();
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        for (Entities alien : aliens) {
            Position alienPos = alien.getPosition();
            if (alien instanceof LargeAlien) {
                int width = 100;
                int height = 100;
                g.drawImage(largeAlienImage, (int) alienPos.getX(), (int) alienPos.getY(),width, height, this);
            } else {
                int width = 75;
                int height = 75;
                g.drawImage(smallAlienImage, (int) alienPos.getX(), (int) alienPos.getY(),width, height, this);
            }
        }
    }
}
