package test;

import Entities.*;
import java.io.*;
import java.util.List;

/**
 * Užduotis, skirta žaidimo būsenai išsaugoti į failą.
 * Ši klasė įrašo esamus žaidimo objektus (ateivius ir erdvėlaivį) į dvejetainį failą.
 */
public class SaveTask implements Runnable {
    private final List<Entities> aliens;
    private final SpaceShip ship;

    /**
     * Sukuria naują išsaugojimo užduotį.
     *
     * @param aliens sąrašas su visais žaidime esančiais ateiviais ar kliūtimis
     * @param ship žaidėjo valdomas erdvėlaivis, kurio būsena turi būti išsaugota
     */
    public SaveTask(List<Entities> aliens, SpaceShip ship) {
        this.aliens = aliens;
        this.ship = ship;
    }

    /**
     * Vykdo žaidimo išsaugojimą į failą "game_save.bin".
     * Įrašomi du objektai: ateivių sąrašas ir žaidėjo erdvėlaivis.
     * Klaidos atveju išvedamas klaidos pranešimas.
     */
    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("game_save.bin"))) {
            out.writeObject(aliens);
            out.writeObject(ship);
            System.out.println("Žaidimas išsaugotas.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
