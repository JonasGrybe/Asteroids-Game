package test;

import Entities.*;
import javax.swing.*;
import java.io.*;
import java.util.List;

public class LoadTask implements Runnable {
    private final List<Entities> aliens;
    private final JPanel panel;

    public LoadTask(List<Entities> aliens, JPanel panel) {
        this.aliens = aliens;
        this.panel = panel;
    }

    @Override
    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("game_save.bin"))) {
            List<Entities> loadedAliens = (List<Entities>) ois.readObject();

            SwingUtilities.invokeLater(() -> {
                aliens.clear();
                aliens.addAll(loadedAliens);
                panel.repaint();
            });
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
