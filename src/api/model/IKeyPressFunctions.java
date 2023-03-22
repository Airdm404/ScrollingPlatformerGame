package api.model;

/**
 * A class that remembers which key functions are activated at any given time.
 * It stores four booleans, which indicate whether the game is paused, whether
 * the player is moving left, whether the player is moving right, and if the player
 * is jumping. This class contains getters and setters for those booleans.
 *
 * This is used by Level to determine how entities should be moved.
 *
 * @author Ryan Krakower
 */
public interface IKeyPressFunctions {

    /**
     * Getter for isPaused
     * @return isPaused (if it returns true, the model should "short-circuit" the methods in its step
     * method, skipping over them and pausing the game)
     */
    boolean isPaused();

    /**
     * Getter for isPlayerMovingLeft
     * @return movePlayerLeft (if it returns true, the model should attempt to move the player left)
     */
    boolean isPlayerMovingLeft();

    /**
     * Getter for isPlayerMovingRight
     * @return movePlayerRight (if it returns true, the model should attempt to move the player right)
     */
    boolean isPlayerMovingRight();

    /**
     * Getter for isPlayerJumping
     * @return jumpPlayer (if it returns true, the model should attempt to make the player jump)
     */
    boolean isPlayerJumping();

    /**
     * Method that sets isPaused to true
     */
    void pauseGame();

    /**
     * Method that sets isPaused to false
     */
    void resumeGame();

    /**
     * Method that sets movePlayerLeft to true
     */
    void startMovingPlayerLeft();

    /**
     * Method that sets movePlayerLeft to false
     */
    void stopMovingPlayerLeft();

    /**
     * Method that sets movePlayerRight to true
     */
    void startMovingPlayerRight();

    /**
     * Method that sets movePlayerRight to false
     */
    void stopMovingPlayerRight();

    /**
     * Method that sets jumpPlayer to true
     */
    void startPlayerJumping();

    /**
     * Method that sets jumpPlayer to false
     */
    void stopPlayerJumping();
}
