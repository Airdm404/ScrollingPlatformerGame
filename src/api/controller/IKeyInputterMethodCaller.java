package api.controller;

/**
 * The KeyInputterMethodCaller interface
 */
public interface IKeyInputterMethodCaller {

    /**
     * Tells the model to move the player left
     */
    void left();

    /**
     * Tells the model to move right
     */
    void right();

    /**
     * Tells the model to move up (i.e. jump)
     */
    void up();

    /**
     * Tells the model to move down (i.e. crouch)
     */
    void down();

    /**
     * Tells the model to pause
     */
    void pause();

    /**
     * Tells the model to stop moving the player left
     */
    void leftRelease();

    /**
     * Tells the model to stop moving the player right
     */
    void rightRelease();

    /**
     * Tells the model to stop the jump
     */
    void upRelease();

    /**
     * Tells the model to stop the down (i.e. crouch)
     */
    void downRelease();

    /**
     * Tells the model to release pause
     */
    void pauseRelease();

    /**
     * Resets the game
     */
    void reset();

    /**
     * Player has released the reset key
     */
    void resetRelease();
}
