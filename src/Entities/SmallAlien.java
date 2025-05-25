package Entities;

public class SmallAlien extends Entities implements TemporarilyBoostable, Cloneable {
    private boolean hasCloak;
    private double remainingTime = 0.0;
    private double MAX_remainingTime = 5.0;

    // Zigzago judėjimo parametrai
    private double time;
    private double baseX;

    public SmallAlien() {
        this(new Position(0, 0), 0.0, 0.0);
    }

    public SmallAlien(Position position, double velocityX, double velocityY) {
        super(position, velocityX, velocityY);
        this.hasCloak = false;

        // Zigzago judėjimo nustatymai
        this.time = 0;
        this.baseX = position.getX();
    }

    public void cloak() {
        this.powerUp();
    }

    @Override
    public void setDuration(double duration) {
        this.remainingTime = duration;
    }

    @Override
    public void move() {
        // Judėjimas žemyn
        position.setY(position.getY() + (int)velocityY + 4);

        // Zigzago judėjimas - keičia kryptį kas tam tikrą atstumą
        int segmentLength = 30; // Kiek pikselių juda viena kryptimi
        int currentSegment = (int)(time / segmentLength);

        // Kas antrą segmentą keičia kryptį (zigzagas)
        int direction = (currentSegment % 2 == 0) ? 1 : -1;

        // 90° kampas = juda tiesiai horizontaliai
        double newX = baseX + (time % segmentLength) * 4 * direction;

        position.setX((int)newX);

        // Didina laiką
        time += 1;

        // Kai užbaigia zigzago segmentą, atnaujina baseX
        if (time % segmentLength == 0) {
            baseX = position.getX();
        }

        // Jei nukrito žemiau ekrano, grąžina į viršų
        if (position.getY() > 600) {
            destroy();
        }

        // Jei išėjo už ekrano šonų, keičia kryptį
        if (position.getX() < 0 || position.getX() > 800) {
            baseX = position.getX() < 400 ? 50 : 750;
            time = 0;
        }
    }

    // PowerUp
    @Override
    public void powerUp() {
        this.hasCloak = true;
        this.remainingTime = MAX_remainingTime;
    }

    @Override
    public void powerDown() {
        this.hasCloak = false;
        this.remainingTime = 0;
    }

    @Override
    public boolean isPoweredUp() {
        return hasCloak;
    }

    @Override
    public double getRemainingTime() {
        return remainingTime;
    }

    @Override
    public void update(double deltaTime) {
        if (hasCloak && remainingTime > 0) {
            remainingTime -= deltaTime;
            if (remainingTime <= 0) {
                this.powerDown();
            }
        }
    }

    //KLONAVIMAS
    @Override
    public Object clone() throws CloneNotSupportedException {
        SmallAlien clone = (SmallAlien) super.clone();
        clone.position = this.position.clone();  // gilus klonavimas position objekto
        return clone;
    }

    @Override
    public String toString() {
        return "SmallAlien [x=" + getX() + ", y=" + getY() +
                ", velocity=(" + getVelocityX() + "," + getVelocityY() +
                "), cloaked=" + hasCloak +
                ", active=" + this.isPoweredUp() + "]";
    }
}