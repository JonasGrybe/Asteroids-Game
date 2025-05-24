package Entities;

public interface TemporarilyBoostable extends Boostable {
    void setDuration(double seconds);
    double getRemainingTime();
    void update(double deltaTime);
}