package Entities;

public class SmallAlien extends Entities implements TemporarilyBoostable, Cloneable {
    private boolean hasCloak;
    private double remainingTime = 0.0;
    private double MAX_remainingTime = 5.0;

    public SmallAlien() {
        this(new Position(0, 0), 0.0, 0.0);
    }

    public SmallAlien(Position position, double velocityX, double velocityY) {
        super(position, velocityX, velocityY);
        this.hasCloak = false;
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
        super.move();
        setX(getX() + 2);
        setY(getY() + 2);
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