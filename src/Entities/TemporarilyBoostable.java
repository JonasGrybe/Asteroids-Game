package Entities;

/**
 * Interface for entities that can receive temporary power boosts.
 * Extends the basic Boostable interface by adding time-limited boost functionality.
 * Implementing classes can activate boosts for a specific duration and track remaining time.
 */
public interface TemporarilyBoostable extends Boostable {

    /**
     * Sets the duration for the temporary boost effect.
     *
     * @param seconds the duration of the boost in seconds
     */
    void setDuration(double seconds);

    /**
     * Gets the remaining time for the current boost effect.
     *
     * @return the remaining boost time in seconds, or 0 if no boost is active
     */
    double getRemainingTime();

    /**
     * Updates the boost state based on elapsed time.
     * Should be called every frame to properly handle boost duration.
     *
     * @param deltaTime the time elapsed since the last update in seconds
     */
    void update(double deltaTime);
}