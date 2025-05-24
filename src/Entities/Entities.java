package Entities;

import java.io.Serializable;

public abstract class Entities implements Boostable, Serializable {
    private static final long serialVersionUID = 1L;
    private static int totalEnemies = 0;
    public static final int MAX_VELOCITY = 5;

    public Position position;
    private double velocityX = 0.0;
    private double velocityY = 0.0;
    private boolean active;
    private boolean ability;

    public final static int getTotalEnemies() {
        return totalEnemies;
    }

    public Entities() {
        this(new Position(0, 0), 0.0, 0.0);
    }

    public Entities(Position position, double velocityX, double velocityY) {
        this.position = position;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        ability = false;
        active = true;
        totalEnemies++;
    }

    public int getX() { return position.getX(); }
    public void setX(double x) { position.setX((int) x); }

    public int getY() { return position.getY(); }
    public void setY(double y) { position.setY((int) y); }

    public double getVelocityX() { return velocityX; }
    public void setVelocityX(double velocityX) {
        this.velocityX = Math.max(-MAX_VELOCITY, Math.min(MAX_VELOCITY, velocityX));
    }

    public double getVelocityY() { return velocityY; }
    public void setVelocityY(double velocityY) {
        this.velocityY = Math.max(-MAX_VELOCITY, Math.min(MAX_VELOCITY, velocityY));
    }

    public void move() {
        setX(getX() + velocityX);
        setY(getY() + velocityY);
    }

    public void move(double timeStep) {
        setX(getX() + velocityX * timeStep);
        setY(getY() + velocityY * timeStep);
    }

    public void destroy() {
        active = false;
        totalEnemies--;
    }

    public Position getPosition() {
        return new Position(getX(), getY());
    }

    // PowerUp interface implementation
    @Override
    public void powerUp() {
        ability = true;
    }

    @Override
    public void powerDown() {
        ability = false;
    }

    @Override
    public boolean isPoweredUp(){
        return ability;
    }

    public String toString() {
        return "Entities [x=" + getX() + ", y=" + getY() +
                ", velocityX=" + velocityX + ", velocityY=" + velocityY +
                ", active=" + active + "]";
    }
}