package test;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        setTitle("Asteroids");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(new MainMenuPanel(this));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}