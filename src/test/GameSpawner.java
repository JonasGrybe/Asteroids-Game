package test;

import Entities.*;
import java.util.*;

/**
 * Pagalbinė klasė, atsakinga už žaidimo objektų (asteroidų, ateivių) sukūrimą pagal pasirinkto sudėtingumo lygį.
 */
public class GameSpawner {
    private static final Random random = new Random();
    private static final int SCREEN_WIDTH = 800;
    private static final int ENTITY_WIDTH = 80;
    private static final int Y_START = -50;

    /**
     * Sukuria žaidimo objektų sąrašą (asteroidų, ateivių) pagal pateiktą sudėtingumo lygį.
     *
     * @param difficulty sudėtingumo lygis
     * @return žaidimo objektų sąrašas
     */
    public static List<Entities> spawnEntities(Difficulty difficulty) {
        List<Entities> entities = new ArrayList<>();

        switch (difficulty) {
            case EASY -> entities.addAll(spawnEasy());
            case MEDIUM -> entities.addAll(spawnMedium());
            case HARD -> entities.addAll(spawnHard());
        }

        return entities;
    }

    /**
     * Sugeneruoja sumaišytą x pozicijų sąrašą su atstumais tarp objektų.
     *
     * @param count kiek pozicijų sugeneruoti
     * @return sumaišytų x pozicijų sąrašas
     */
    private static List<Integer> generateShuffledPositions(int count) {
        List<Integer> positions = new ArrayList<>();
        int spacing = (SCREEN_WIDTH - ENTITY_WIDTH) / (count - 1);

        for (int i = 0; i < count; i++) {
            positions.add(i * spacing);
        }

        Collections.shuffle(positions);
        return positions;
    }

    /**
     * Sugeneruoja atsitiktinai pakeistą X poziciją, atsižvelgiant į ekraną ir objekto plotį.
     */
    private static int getRandomizedX(int baseX) {
        int offset = random.nextInt(101) - 50; // nuo -50 iki +50
        int finalX = baseX + offset;
        return Math.max(0, Math.min(finalX, SCREEN_WIDTH - ENTITY_WIDTH));
    }

    /**
     * Sugeneruoja atsitiktinai pakeistą Y poziciją, pradedant nuo Y_START.
     */
    private static int getRandomizedY() {
        int offset = random.nextInt(61) - 30; // nuo -30 iki +30
        return Y_START + offset;
    }

    /**
     * Sukuria objektus EASY (lengvo) sudėtingumo lygiui.
     */
    private static List<Entities> spawnEasy() {
        List<Entities> entities = new ArrayList<>();
        int asteroidCount = 5;

        List<Integer> positions = generateShuffledPositions(7);

        for (int i = 0; i < asteroidCount && i < positions.size(); i++) {
            int finalX = getRandomizedX(positions.get(i));
            int finalY = getRandomizedY();
            entities.add(new Asteroid(new Position(finalX, finalY), 0, 4.0, 1));
        }

        return entities;
    }

    /**
     * Sukuria objektus MEDIUM (vidutinio) sudėtingumo lygiui.
     */
    private static List<Entities> spawnMedium() {
        List<Entities> entities = new ArrayList<>();
        int asteroidCount = 3;
        int largeAlienCount = 1;

        List<Integer> positions = generateShuffledPositions(8);

        // Asteroidai
        for (int i = 0; i < asteroidCount && i < positions.size(); i++) {
            int finalX = getRandomizedX(positions.get(i));
            int finalY = getRandomizedY();
            entities.add(new Asteroid(new Position(finalX, finalY), 0, 4.0, 1));
        }

        // Didelis ateivis
        for (int i = asteroidCount; i < asteroidCount + largeAlienCount && i < positions.size(); i++) {
            int finalX = getRandomizedX(positions.get(i));
            int finalY = getRandomizedY();
            boolean startLeft = random.nextBoolean();
            entities.add(new LargeAlien(new Position(finalX, finalY), 0, 4.0, 3, startLeft));
        }

        return entities;
    }

    /**
     * Sukuria objektus HARD (sudėtingo) lygio žaidimui.
     */
    private static List<Entities> spawnHard() {
        List<Entities> entities = new ArrayList<>();
        int asteroidCount = 2;
        int largeAlienCount = 1;
        int smallAlienCount = 3;

        List<Integer> positions = generateShuffledPositions(8);

        // Asteroidai
        for (int i = 0; i < asteroidCount && i < positions.size(); i++) {
            int finalX = getRandomizedX(positions.get(i));
            int finalY = getRandomizedY();
            entities.add(new Asteroid(new Position(finalX, finalY), 0, 8.0, 1));
        }

        // Didelis ateivis
        for (int i = asteroidCount; i < asteroidCount + largeAlienCount && i < positions.size(); i++) {
            int finalX = getRandomizedX(positions.get(i));
            int finalY = getRandomizedY();
            boolean startLeft = random.nextBoolean();
            entities.add(new LargeAlien(new Position(finalX, finalY), 0, 8.0, 4, startLeft));
        }

        // Maži ateiviai
        for (int i = asteroidCount + largeAlienCount; i < asteroidCount + largeAlienCount + smallAlienCount - 1 && i < positions.size(); i++) {
            int finalX = getRandomizedX(positions.get(i));
            int finalY = getRandomizedY();
            entities.add(new SmallAlien(new Position(finalX, finalY), 0, 8.0));
        }

        return entities;
    }
}
