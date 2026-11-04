package Entities;

/**
 * Žymi smulkesnius ateivius žaidime.
 * <p>
 * Šie ateiviai juda zigzago (segmentuoto tangento) trajektorija žemyn
 * ir turi galimybę trumpam pasislėpti („cloak“).
 * Jie sunaikinami, kai išeina už ekrano ribų arba pasiekia ekrano apačią.
 * </p>
 *
 * <p>
 * Implementuoja:
 * <ul>
 *   <li>{@link TemporarilyBoostable} – suteikia galimybę trumpam aktyvuoti „cloak“ režimą.</li>
 *   <li>{@link Cloneable} – leidžia sukurti šio objekto kopiją.</li>
 * </ul>
 * </p>
 */
public class SmallAlien extends Entities implements TemporarilyBoostable, Cloneable {

    /** Ar šiuo metu aktyvuotas cloak (nematomumas). */
    private boolean hasCloak;

    /** Liko sekundžių iki cloak išsijungimo. */
    private double remainingTime = 0.0;

    /** Maksimali cloak trukmė sekundėmis. */
    private double MAX_remainingTime = 5.0;

    // Zigzago judėjimo parametrai

    /** Laikas, leidžiantis valdyti zigzago poziciją. */
    private double time;

    /** Pradinė X pozicija zigzago skaičiavimui. */
    private double baseX;

    /**
     * Sukuria naują smulkųjį ateivį numatytoje pozicijoje (0,0).
     */
    public SmallAlien() {
        this(new Position(0, 0), 0.0, 0.0);
    }

    /**
     * Sukuria naują smulkųjį ateivį nurodytoje pozicijoje su duotu greičiu.
     *
     * @param position pradinė pozicija
     * @param velocityX greitis X ašyje
     * @param velocityY greitis Y ašyje
     */
    public SmallAlien(Position position, double velocityX, double velocityY) {
        super(position, velocityX, velocityY, 55, 55);
        this.hasCloak = false;
        this.time = 0;
        this.baseX = position.getX();
    }

    /**
     * Įjungia cloak režimą (lygiavertis power-up'ui).
     */
    public void cloak() {
        this.powerUp();
    }

    /**
     * Nustato cloak trukmę.
     *
     * @param duration trukmė sekundėmis
     */
    @Override
    public void setDuration(double duration) {
        this.remainingTime = duration;
    }

    /**
     * Valdo smulkaus ateivio judėjimą zigzago forma žemyn.
     * Taip pat sunaikina objektą, jei jis išeina už ekrano ribų.
     */
    @Override
    public void move() {
        // Judėjimas žemyn
        position.setY(position.getY() + (int)velocityY + 4);

        int segmentLength = 30;
        int currentSegment = (int)(time / segmentLength);
        int direction = (currentSegment % 2 == 0) ? 1 : -1;
        double newX = baseX + (time % segmentLength) * 4 * direction;

        position.setX((int)newX);
        time += 1;

        if (time % segmentLength == 0) {
            baseX = position.getX();
        }

        if (position.getY() > 600) {
            destroy();
        }

        if (position.getX() < 0 || position.getX() > 800) {
            baseX = position.getX() < 400 ? 50 : 750;
            time = 0;
        }
    }

    /**
     * Įjungia cloak režimą ir nustato trukmę.
     */
    @Override
    public void powerUp() {
        this.hasCloak = true;
        this.remainingTime = MAX_remainingTime;
    }

    /**
     * Išjungia cloak režimą.
     */
    @Override
    public void powerDown() {
        this.hasCloak = false;
        this.remainingTime = 0;
    }

    /**
     * Tikrina, ar cloak režimas šiuo metu aktyvus.
     *
     * @return true jei aktyvus, false jei ne
     */
    @Override
    public boolean isPoweredUp() {
        return hasCloak;
    }

    /**
     * Grąžina, kiek liko laiko cloak režimui.
     *
     * @return likęs laikas sekundėmis
     */
    @Override
    public double getRemainingTime() {
        return remainingTime;
    }

    /**
     * Atnaujina cloak laikmatį. Jei laikas baigėsi, cloak išjungiamas.
     *
     * @param deltaTime laiko pokytis sekundėmis
     */
    @Override
    public void update(double deltaTime) {
        if (hasCloak && remainingTime > 0) {
            remainingTime -= deltaTime;
            if (remainingTime <= 0) {
                this.powerDown();
            }
        }
    }

    /**
     * Sukuria šio objekto kopiją (gilus klonavimas).
     *
     * @return šio objekto kopija
     * @throws CloneNotSupportedException jei klonavimas nepalaikomas
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        SmallAlien clone = (SmallAlien) super.clone();
        clone.position = this.position.clone();
        return clone;
    }

    /**
     * Grąžina šio objekto informatyvų tekstinį aprašymą.
     *
     * @return objekto būsena kaip tekstas
     */
    @Override
    public String toString() {
        return "SmallAlien [x=" + getX() + ", y=" + getY() +
                ", velocity=(" + getVelocityX() + "," + getVelocityY() +
                "), cloaked=" + hasCloak +
                ", active=" + this.isPoweredUp() + "]";
    }
}
