package test;

import Entities.*;
import java.io.*;
import java.util.List;

public class SaveTask implements Runnable {
    private final List<Entities> aliens;

    public SaveTask(List<Entities> aliens) {
        this.aliens = aliens;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("game_save.bin"))) {
            out.writeObject(aliens);
            System.out.println("Žaidimas išsaugotas.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
