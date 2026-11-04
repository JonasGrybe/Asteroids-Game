package Entities;

import Exceptions.InvalidValueException;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * Žymi didesnius ateivius.
 * Šie ateiviai juda vertikalia sinusoide ir turi šarvus bei skydą nuo sunaikinimo.
 * Jie gali būti piešiami ir klonuojami, o jų būsena serializuojama.
 */
public class LargeAlien extends Entities implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L; // Serializacijos versijos ID

    private int armor;               // Šarvų lygis
    private boolean hasShield;      // Ar aktyvuotas skydas
    private double time;            // Laikas sinuso judėjimui
    private double amplitude = 5;   // Judėjimo horizontaliai amplitudė
    private double frequency = 0.05; // Judėjimo dažnis (bangų tankis)
    private double phase;           // Fazės poslinkis – leidžia pradėti iš kairės arba dešinės

    private transient Image image;  // Paveikslėlis, kuris nėra serializuojamas

    public static final int MAX_SHIELD = 5;
    public static final int MIN_SHIELD = 0;

    /**
     * Užkrauna LargeAlien paveikslėlį iš resursų.
     *
     * @return paveikslėlio objektas
     */
    static Image loadImage() {
        ImageIcon icon = new ImageIcon(LargeAlien.class.getResource("/Resources/LargeAlien.png"));
        return icon.getImage();
    }

    /**
     * Numatytasis konstruktorius – pozicija (0,0), greitis 0, šarvai 3, fazė atsitiktinė.
     */
    public LargeAlien() {
        this(new Position(0, 0), 0.0, 0.0, 3, false);
    }

    /**
     * Sukuria didelį ateivį su duotais parametrais.
     *
     * @param position pradinė pozicija
     * @param velocityX horizontalus greitis (nebenaudojamas tiesiogiai, nes sinuso judėjimas)
     * @param velocityY vertikalus greitis
     * @param armor šarvų kiekis (1–5)
     * @param startLeft ar pradėti iš kairės (valdo sinuso fazę)
     */
    public LargeAlien(Position position, double velocityX, double velocityY, int armor, boolean startLeft) {
        super(position, velocityX, velocityY, 70, 70);
        this.image = loadImage();

        if (armor >= 1 && armor <= 5) {
            this.armor = armor;
        } else {
            this.armor = 3;
        }

        this.time = Math.random() * 100;
        this.hasShield = false;
        this.phase = startLeft ? Math.PI : 0;
    }

    /**
     * Po deserializacijos atkuria paveikslėlį.
     */
    private void readObject(java.io.ObjectInputStream in)
            throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.image = loadImage();
    }

    /**
     * Aktyvuoja skydą, kuris apsaugo nuo sunaikinimo pirmo smūgio metu.
     */
    public void activateSpecialDefense() {
        this.hasShield = true;
    }

    /**
     * Grąžina šarvų kiekį.
     *
     * @return šarvų kiekis
     */
    public int getArmor() {
        return armor;
    }

    /**
     * Nustato šarvų kiekį. Turi būti tarp MIN_SHIELD ir MAX_SHIELD.
     *
     * @param armor naujas šarvų lygis
     * @throws InvalidValueException jei reikšmė neleistina
     */
    public void setArmor(int armor) throws InvalidValueException {
        if (armor > MAX_SHIELD) {
            throw new InvalidValueException("The alien can only have level 5 of shield at maximum!", "Armor", armor, "Large Alien");
        }
        if (armor < MIN_SHIELD) {
            throw new InvalidValueException("Armor cannot be negative!", "Armor", armor, "Large Alien");
        }
        this.armor = armor;
    }

    /**
     * Judina objektą žemyn ir sinuso bangos forma horizontaliai.
     * Jei iškrenta už žaidimo ribų (Y > 600), sunaikinamas.
     */
    @Override
    public void move() {
        // Vertikalus judėjimas
        position.setY(position.getY() + (int) velocityY);

        // Horizontaliai per sinuso funkciją
        double newX = position.getX() + amplitude * Math.sin(time * frequency + phase);
        position.setX((int) newX);

        time += 1;

        if (position.getY() > 600) {
            destroy();
        }
    }

    /**
     * Sunaikina ateivį, jei skydas neaktyvus ir šarvų nebelieka.
     */
    @Override
    public void destroy() {
        if (!hasShield) {
            super.destroy(); // Pirmą kartą sunaikinamas
        }

        armor--;

        if (armor < 0) {
            super.destroy();
        }
    }

    /**
     * Sukuria objekto kopiją.
     *
     * @return klonuotas LargeAlien objektas
     * @throws CloneNotSupportedException jei objektas negali būti klonuotas
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        LargeAlien clone = (LargeAlien) super.clone();
        clone.position = new Position(this.position.getX(), this.position.getY());
        clone.image = loadImage();
        return clone;
    }

    /**
     * Grąžina tekstinę informaciją apie objektą (naudojama derinimui).
     *
     * @return eilutė su būsenos informacija
     */
    @Override
    public String toString() {
        return "LargeAlien [x=" + getX() + ", y=" + getY() +
                ", velocity=(" + getVelocityX() + "," + getVelocityY() +
                "), armor=" + armor +
                ", shield=" + hasShield +
                ", active=" + this.isPoweredUp() + "]";
    }

    /**
     * Nupiešia didelį ateivį grafikos kontekste.
     *
     * @param g Graphics objektas, skirtas piešimui
     */
    public void draw(Graphics g) {
        int x = (int) getX();
        int y = (int) getY();
        if (image != null) {
            g.drawImage(image, x, y, null);
        } else {
            g.setColor(Color.RED);
            g.fillRect(x, y, 40, 40);
        }
    }
}
