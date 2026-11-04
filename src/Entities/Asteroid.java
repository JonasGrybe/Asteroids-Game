package Entities;

/**
 * Žymi asteroidus.
 * Jie gali būti sunaikinti, sumažinant jų dydį.
 */
public class Asteroid extends Entities {
    /** Asteroido dydis: 1 (mažas), 2 (vidutinis), 3 (didelis) */
    private int size;

    /**
     * Numatytasis konstruktorius. Sukuria asteroidą pozicijoje (0,0), su 0 greičiu ir dydžiu 1.
     */
    public Asteroid() {
        this(new Position(0, 0), 0.0, 0.0, 1);
    }

    /**
     * Sukuria asteroidą su nurodyta pozicija, greičiais ir dydžiu.
     *
     * @param position pradinė pozicija
     * @param velocityX greitis X ašimi
     * @param velocityY greitis Y ašimi
     * @param size asteroido dydis (1–3). Jei netinkamas, nustatomas į 1
     */
    public Asteroid(Position position, double velocityX, double velocityY, int size) {
        super(position, velocityX, velocityY, 50, 50); // naudokite perduotą position!

        if (size >= 1 && size <= 3) {
            this.size = size;
        } else {
            this.size = 1;
        }
    }

    /**
     * Nustato asteroido dydį.
     * Leidžiamos reikšmės: 1, 2 arba 3. Jei pateikta netinkama, nustatomas 1.
     *
     * @param size naujas dydis
     */
    public void setSize(int size) {
        if (size >= 1 && size <= 3) {
            this.size = size;
        } else {
            this.size = 1;
        }
    }

    /**
     * Grąžina esamą asteroido dydį.
     *
     * @return asteroido dydis
     */
    public int getSize() {
        return size;
    }

    /**
     * Pakeičia asteroido judėjimo kryptį pagal pateiktą kampą (radianais).
     * Naudoja sukimosi matricos formulę.
     *
     * @param angle kampas radianais, kuriuo keisti kryptį
     */
    public void changeDirection(double angle) {
        double newVelocityX = getVelocityX() * Math.cos(angle) - getVelocityY() * Math.sin(angle);
        double newVelocityY = getVelocityX() * Math.sin(angle) + getVelocityY() * Math.cos(angle);

        setVelocityX(newVelocityX);
        setVelocityY(newVelocityY);
    }

    /**
     * Perkelia asteroidą žemyn pagal jo Y greitį.
     * Jei asteroidas iškeliauja už žaidimo ribų (Y > 600), jis sunaikinamas.
     */
    @Override
    public void move() {
        position.setY(position.getY() + (int) velocityY);

        if (position.getY() > 600) {
            destroy();
        }
    }

    /**
     * Sumažina asteroido dydį. Jei dydis pasiekia 0 arba mažesnį — pašalinamas iš žaidimo.
     */
    @Override
    public void destroy() {
        size--;
        if (size <= 0) {
            super.destroy();
        }
    }

    /**
     * Grąžina asteroido būseną kaip tekstą (naudojama derinimui).
     *
     * @return eilutė su asteroido informacija
     */
    @Override
    public String toString() {
        return "Asteroid [x=" + getX() + ", y=" + getY() +
                ", velocity=(" + getVelocityX() + "," + getVelocityY() +
                "), size=" + size + ", active=" + this.isPoweredUp() + "]";
    }
}
