package Entities;

import Exceptions.InvalidValueException;

/**
 * Žymi žaidėjo kosminį laivą.
 * Laivas gali judėti, šaudyti, kaupti taškus ir prarasti gyvybes.
 */
public class SpaceShip extends Entities {

    /** Dabartinis laivo gyvybių skaičius. */
    private int lives = 3;

    /** Žaidėjo taškai. */
    private int score = 0;

    /** Maksimalus galimų gyvybių skaičius. */
    public static final int MAX_LIVES = 5;

    /**
     * Numatytasis konstruktorius – laivas pradedamas (0, 0) koordinatėse su 0 greičiu.
     */
    public SpaceShip() {
        this(new Position(0, 0), 0.0, 0.0);
    }

    /**
     * Sukuria laivą nurodytoje pozicijoje su duotu greičiu.
     *
     * @param position  Pradinė laivo pozicija.
     * @param velocityX Horizontalus greitis.
     * @param velocityY Vertikalus greitis.
     */
    public SpaceShip(Position position, double velocityX, double velocityY) {
        super(position, velocityX, velocityY, 20, 20);
    }

    /**
     * Laivas šaudo (vietoj tikros logikos – tekstas į konsolę).
     */
    public void shoot() {
        System.out.println("Ship fires!");
    }

    /**
     * Prideda taškų prie bendro žaidėjo rezultato.
     *
     * @param points Taškai, kuriuos reikia pridėti.
     */
    public void addScore(int points) {
        score += points;
    }

    /**
     * Grąžina dabartinį taškų skaičių.
     *
     * @return Žaidėjo taškai.
     */
    public int getScore() {
        return score;
    }

    /**
     * Nustato naują taškų reikšmę.
     *
     * @param score Naujas taškų kiekis.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Grąžina dabartinį gyvybių skaičių.
     *
     * @return Gyvybių kiekis.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Nustato gyvybių kiekį. Tikrina, kad jis būtų tarp 0 ir MAX_LIVES.
     *
     * @param lives Nustatomas gyvybių kiekis.
     * @throws InvalidValueException Jei gyvybių reikšmė yra neteisinga (per daug arba neigiama).
     */
    public void setLives(int lives) throws InvalidValueException {
        if (lives > MAX_LIVES) {
            throw new InvalidValueException("Laivas gali turėti daugiausiai 5 gyvybes!", "Gyvybės", lives, "Žaidėjas");
        }
        if (lives < 0) {
            throw new InvalidValueException("Gyvybės negali būti neigiamos!", "Gyvybės", lives, "Žaidėjas");
        }
        this.lives = lives;
    }

    /**
     * Pajudina laivą į kairę per 10 taškų, neleidžiant išeiti už ribų.
     */
    public void moveLeft() {
        int oldX = getPosition().getX();
        int newX = Math.max(0, oldX - 10);
        getPosition().setX(newX);
        System.out.println("moveLeft: " + oldX + " -> " + newX);
    }

    /**
     * Pajudina laivą į dešinę per 10 taškų, neleidžiant išeiti už panelės pločio.
     *
     * @param panelWidth Panelės plotis (naudojamas ribojimui).
     */
    public void moveRight(int panelWidth) {
        int oldX = getPosition().getX();
        int newX = Math.min(panelWidth - 60, oldX + 10);
        getPosition().setX(newX);
        System.out.println("moveRight: " + oldX + " -> " + newX);
    }

    /**
     * Grąžina tikrąją laivo poziciją (ne kopiją).
     *
     * @return Pozicijos objektas.
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * Sumažina gyvybių kiekį vienetu.
     * Jei gyvybės pasiekia 0 – laivas sunaikinamas.
     */
    @Override
    public void destroy() {
        lives--;
        System.out.println("Laivas gavo smūgį! Liko gyvybių: " + lives);

        if (lives <= 0) {
            System.out.println("Žaidimo pabaiga! Laivas sunaikintas.");
            super.destroy();
        }
    }

    /**
     * Grąžina teksto formos informaciją apie laivą – koordinatės, taškai, gyvybės, aktyvumas.
     *
     * @return Tekstinė laivo būsena.
     */
    @Override
    public String toString() {
        return "SpaceShip [x=" + getX() + ", y=" + getY() + ", taškai=" + score + ", gyvybės=" + lives + ", aktyvus=" + this.isPoweredUp() + "]";
    }
}
