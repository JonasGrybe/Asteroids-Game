package Entities;

import java.io.Serializable;

/**
 * Klasė, aprašanti objektų poziciją 2D koordinatėse.
 * Naudojama žaidimo elementų vietai ekrane nustatyti.
 */
public class Position implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;

    /** X koordinatė */
    private int x;

    /** Y koordinatė */
    private int y;

    /**
     * Sukuria naują poziciją su nurodytomis koordinatėmis.
     *
     * @param x X koordinatė
     * @param y Y koordinatė
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Grąžina šios pozicijos kopiją kaip naują objektą.
     *
     * @return naujas {@code Position} objektas su tokia pačia X ir Y reikšme
     */
    public Position getPosition() {
        return new Position((int) getX(), (int) getY());
    }

    /**
     * Grąžina X koordinatę.
     *
     * @return X reikšmė
     */
    public int getX() {
        return x;
    }

    /**
     * Nustato X koordinatę.
     *
     * @param x nauja X reikšmė
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Grąžina Y koordinatę.
     *
     * @return Y reikšmė
     */
    public int getY() {
        return y;
    }

    /**
     * Nustato Y koordinatę.
     *
     * @param y nauja Y reikšmė
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sukuria ir grąžina šios pozicijos kopiją.
     *
     * @return klonuotas {@code Position} objektas
     */
    @Override
    public Position clone() {
        return new Position(x, y);
    }
}
