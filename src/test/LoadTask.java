package test;

import Entities.*;
import javax.swing.*;
import java.io.*;
import java.util.List;

/**
 * Užduotis, skirta įkelti žaidimo būseną (priešus ir erdvėlaivį) iš failo.
 * Ši klasė vykdoma atskirame gijoje, kad neužšaldytų pagrindinio vartotojo sąsajos (UI) gijos.
 */
public class LoadTask implements Runnable {
    private final List<Entities> aliens;
    private final SpaceShip ship;
    private final JPanel panel;

    /**
     * Sukuria naują įkėlimo užduotį.
     *
     * @param aliens sąrašas, kuriame bus atnaujinti priešai (ateiviai)
     * @param ship žaidėjo erdvėlaivis, kurio būsena bus atnaujinta
     * @param panel komponentas, kurį reikia atvaizduoti iš naujo po duomenų įkėlimo
     */
    public LoadTask(List<Entities> aliens, SpaceShip ship, JPanel panel) {
        this.aliens = aliens;
        this.ship = ship;
        this.panel = panel;
    }

    /**
     * Vykdo įkėlimo logiką: nuskaito išsaugotą žaidimo būseną iš failo "game_save.bin",
     * atnaujina priešų sąrašą ir erdvėlaivio poziciją, gyvybes ir taškus.
     * Duomenų atvaizdavimas atliekamas naudojant `SwingUtilities.invokeLater`,
     * kad užtikrintume teisingą darbą su Swing vartotojo sąsaja.
     */
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
