package Entities;

public class Asteroid extends Entities {
    private int size;

    public Asteroid() {
        this(new Position(0, 0), 0.0, 0.0, 1);
    }

    public Asteroid(Position position, double velocityX, double velocityY, int size) {
        super(new Position(0,0), velocityX, velocityY);

        if (size >= 1 && size <= 3) {
            this.size = size;
        } else {
            this.size = 1;
        }
    }

    public void setSize(int size){
        if (size >= 1 && size <= 3) {
            this.size = size;
        } else {
            this.size = 1;
        }
    }

    public int getSize() {
        return size;
    }

    public void changeDirection(double angle) {
        double newVelocityX = getVelocityX() * Math.cos(angle) - getVelocityY() * Math.sin(angle);
        double newVelocityY = getVelocityX() * Math.sin(angle) + getVelocityY() * Math.cos(angle);

        setVelocityX(newVelocityX);
        setVelocityY(newVelocityY);
    }


    @Override
    public void destroy() {
        size--;
        if(size<=0){
            super.destroy();
        }
    }

    @Override
    public String toString() {
        return "Asteroid [x=" + getX() + ", y=" + getY() +
                ", velocity=(" + getVelocityX() + "," + getVelocityY() +
                "), size=" + size + ", active=" + this.isPoweredUp() + "]";
    }
}