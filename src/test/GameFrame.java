package test;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        setTitle("Erdvėlaivio žaidimas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        GamePanel panel = new GamePanel();
        add(panel, BorderLayout.CENTER);

        JButton saveButton = new JButton("Išsaugoti");
        JButton loadButton = new JButton("Nuskaityti");

        JPanel buttons = new JPanel();
        buttons.add(saveButton);
        buttons.add(loadButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameFrame());
    }
}
