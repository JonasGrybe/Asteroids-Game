package Entities;
import Exceptions.InvalidValueException;

public class SpaceShip extends Entities {
    private int lives = 3;
    private int score = 0;
    public static final int MAX_LIVES = 5;

    public SpaceShip() {
        this(new Position(0, 0), 0.0, 0.0);
    }

    public SpaceShip(Position position, double velocityX, double velocityY) {
        super(position, velocityX, velocityY);
    }


    public void shoot() {
        System.out.println("Ship fires!");
    }

    public void addScore(int points) {
        score += points;
    }

    public int getScore(){
        return score;
    }
    public void setScore(int score){
        this.score = score;
    }

    public int getLives(){
        return lives;
    }

    public void setLives(int lives) throws InvalidValueException {
        if (lives > MAX_LIVES) {
            throw new InvalidValueException("The ship can only have 5 lives at maximum!", "Lives", lives, "Player");
        }
        if (lives < 0) {
            throw new InvalidValueException("Lives cannot be negative!", "Lives", lives, "Player");
        }
        this.lives = lives;
    }

    @Override
    public void destroy() {
        lives--;
        System.out.println("Ship hit! Lives left: " + lives);

        if (lives <= 0) {
            System.out.println("Game Over! The spaceship has been destroyed.");
            super.destroy();
        }
    }

    @Override
    public String toString() {
        return "SpaceShip [x=" + getX() + ", y=" + getY() + ", score=" + score + ", lives=" + lives + ", active=" + this.isPoweredUp() + "]";
    }
}