package test;

import Entities.*;
import javax.swing.*;
import java.io.*;
import java.util.List;

public class LoadTask implements Runnable {
    private final List<Entities> aliens;
    private final SpaceShip ship;
    private final JPanel panel;

    public LoadTask(List<Entities> aliens, SpaceShip ship, JPanel panel) {
        this.aliens = aliens;
        this.ship = ship;
        this.panel = panel;
    }

    @Override
    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("game_save.bin"))) {
            List<Entities> loadedAliens = (List<Entities>) ois.readObject();
            SpaceShip loadedShip = (SpaceShip) ois.readObject(); // ← naujai nuskaitytas erdvėlaivis

            SwingUtilities.invokeLater(() -> {
                aliens.clear();
                aliens.addAll(loadedAliens);

                // Atnaujiname esamą objektą (vietoj `ship = loadedShip`, kuris neveiktų dėl final)
                ship.getPosition().setX(loadedShip.getPosition().getX());
                ship.getPosition().setY(loadedShip.getPosition().getY());
                try {
                    ship.setLives(loadedShip.getLives());
                } catch (Exception ignored) {}
                ship.setScore(loadedShip.getScore());

                panel.repaint();
            });
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
