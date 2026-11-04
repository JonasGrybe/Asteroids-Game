package Entities;

import java.awt.Rectangle;
import java.io.Serializable;

/**
 * Abstrakti esybių klasė, apibrėžianti padėtį, greitį, dydį ir sąveiką.
 */
public abstract class Entities implements Boostable, Serializable {
    private static final long serialVersionUID = 1L;
    private static int totalEnemies = 0;
    public static final int MAX_VELOCITY = 5;

    public Position position;
    public double velocityX = 0.0;
    public double velocityY = 0.0;
    private boolean active;
    private boolean ability;

    protected int width;
    protected int height;

    /**
     * Grąžina bendrą priešų kiekį.
     * @return visų sukurtų priešo tipo objektų skaičius
     */
    public final static int getTotalEnemies() {
        return totalEnemies;
    }

    /**
     * Sukuria objektą su numatyta pozicija ir dydžiu.
     */
    public Entities() {
        this(new Position(0, 0), 0.0, 0.0, 50, 50); // default hitbox size
    }

    /**
     * Sukuria objektą su nurodyta pozicija, greičiais ir dydžiu.
     * @param position pradinė pozicija
     * @param velocityX horizontalus greitis
     * @param velocityY vertikalus greitis
     * @param width objekto plotis
     * @param height objekto aukštis
     */
    public Entities(Position position, double velocityX, double velocityY, int width, int height) {
        this.position = position;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.width = width;
        this.height = height;
        ability = false;
        active = true;
        totalEnemies++;
    }

    /**
     * Grąžina X koordinatę.
     * @return X reikšmė
     */
    public int getX() { return position.getX(); }

    /**
     * Nustato X koordinatę.
     * @param x nauja X reikšmė
     */
    public void setX(double x) { position.setX((int) x); }

    /**
     * Grąžina Y koordinatę.
     * @return Y reikšmė
     */
    public int getY() { return position.getY(); }

    /**
     * Nustato Y koordinatę.
     * @param y nauja Y reikšmė
     */
    public void setY(double y) { position.setY((int) y); }

    /**
     * Grąžina horizontalų greitį.
     * @return velocityX reikšmė
     */
    public double getVelocityX() { return velocityX; }

    /**
     * Nustato horizontalų greitį (ribojamas iki MAX_VELOCITY).
     * @param velocityX naujas greitis
     */
    public void setVelocityX(double velocityX) {
        this.velocityX = Math.max(-MAX_VELOCITY, Math.min(MAX_VELOCITY, velocityX));
    }

    /**
     * Grąžina vertikalų greitį.
     * @return velocityY reikšmė
     */
    public double getVelocityY() { return velocityY; }

    /**
     * Nustato vertikalų greitį (ribojamas iki MAX_VELOCITY).
     * @param velocityY naujas greitis
     */
    public void setVelocityY(double velocityY) {
        this.velocityY = Math.max(-MAX_VELOCITY, Math.min(MAX_VELOCITY, velocityY));
    }

    /**
     * Pajudina objektą pagal dabartinį greitį.
     */
    public void move() {
        setX(getX() + velocityX);
        setY(getY() + velocityY);
    }

    /**
     * Pajudina objektą pagal greitį ir laiko žingsnį.
     * @param timeStep laiko žingsnis
     */
    public void move(double timeStep) {
        setX(getX() + velocityX * timeStep);
        setY(getY() + velocityY * timeStep);
    }

    /**
     * Pašalina objektą – pažymi kaip neaktyvų ir sumažina bendrą kiekį.
     */
    public void destroy() {
        active = false;
        totalEnemies--;
    }

    /**
     * Grąžina dabartinę objekto poziciją.
     * @return pozicija
     */
    public Position getPosition() {
        return new Position(getX(), getY());
    }

    /**
     * Grąžina objekto plotį.
     * @return plotis
     */
    public int getWidth() { return width; }

    /**
     * Grąžina objekto aukštį.
     * @return aukštis
     */
    public int getHeight() { return height; }

    /**
     * Grąžina objektą aprėpiančią stačiakampę sritį (naudojama susidūrimams).
     * @return ribų stačiakampis
     */
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), width, height);
    }

    /**
     * Įjungia galimybių būseną.
     */
    @Override
    public void powerUp() {
        ability = true;
    }

    /**
     * Išjungia galimybių būseną.
     */
    @Override
    public void powerDown() {
        ability = false;
    }

    /**
     * Tikrina, ar objektas šiuo metu turi galimybių.
     * @return true jei aktyvuota
     */
    @Override
    public boolean isPoweredUp(){
        return ability;
    }

    /**
     * Objekto aprašymas tekstu (naudojamas debuginimui).
     * @return objekto informacija kaip tekstas
     */
    public String toString() {
        return "Entities [x=" + getX() + ", y=" + getY() +
                ", velocityX=" + velocityX + ", velocityY=" + velocityY +
                ", active=" + active + "]";
    }
}
