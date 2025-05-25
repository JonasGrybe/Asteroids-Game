package Entities;

import Exceptions.InvalidValueException;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class LargeAlien extends Entities implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L; // Serializable versijos numeris

    private int armor;
    private boolean hasShield;
    private double time ; // Laikas sinuso funkcijai
    private double amplitude = 5; // Sinuso amplitudė (kiek toli šonuose juda)
    private double frequency = 0.05;
    private double phase;

    // Paveikslėlio laukas - transient, nes Image ne serializuojamas
    private transient Image image;

    public static final int MAX_SHIELD = 5;
    public static final int MIN_SHIELD = 0;

    // Statinis blokas paveikslėlio užkrovimui (alternatyva konstruktorui)
    static Image loadImage() {
        ImageIcon icon = new ImageIcon(LargeAlien.class.getResource("/Resources/LargeAlien.png"));
        return icon.getImage();
    }

    public LargeAlien() {
        this(new Position(0, 0), 0.0, 0.0, 3, false);
    }

    public LargeAlien(Position position, double velocityX, double velocityY, int armor, boolean startLeft) {
        super(position, velocityX, velocityY);
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

    // Šis metodas reikalingas, kad atkūrus objektą iš failo, paveikslėlis būtų atstatytas
    private void readObject(java.io.ObjectInputStream in)
            throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.image = loadImage();
    }

    public void activateSpecialDefense() {
        this.hasShield = true;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) throws InvalidValueException {
        if (armor > MAX_SHIELD) {
            throw new InvalidValueException("The alien can only have level 5 of shield at maximum!", "Armor", armor, "Large Alien");
        }
        if (armor < MIN_SHIELD) {
            throw new InvalidValueException("Armor cannot be negative!", "Armor", armor, "Large Alien");
        }
        this.armor = armor;
    }

    @Override
    public void move() {
        // Judėjimas žemyn (vertikaliai)
        position.setY(position.getY() + (int)velocityY);

        // Sinuso judėjimas horizontaliai
        double newX = position.getX() + amplitude * Math.sin(time * frequency + phase);
        position.setX((int)newX);

        // Didina laiką sinuso funkcijai
        time += 1;

        // Jei nukrito žemiau ekrano, grąžina į viršų
        if (position.getY() > 600) {
            destroy(); // Resetina sinuso ciklą
        }
    }

    @Override
    public void destroy() {
        if (!hasShield) {
            super.destroy();
        }

        armor--;

        if (armor < 0) {
            super.destroy();
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        LargeAlien clone = (LargeAlien) super.clone();
        clone.position = new Position(this.position.getX(), this.position.getY());
        clone.image = loadImage(); // klonuojant taip pat užkrauname paveikslėlį
        return clone;
    }

    @Override
    public String toString() {
        return "LargeAlien [x=" + getX() + ", y=" + getY() +
                ", velocity=(" + getVelocityX() + "," + getVelocityY() +
                "), armor=" + armor +
                ", shield=" + hasShield +
                ", active=" + this.isPoweredUp() + "]";
    }

    // Naujas metodas, skirtas piešimui
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


