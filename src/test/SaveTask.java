package test;

import Entities.*;
import java.io.*;
import java.util.List;

public class SaveTask implements Runnable {
    private final List<Entities> aliens;
    private final SpaceShip ship;

    public SaveTask(List<Entities> aliens, SpaceShip ship) {
        this.aliens = aliens;
        this.ship = ship;
    }

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
