package model;

import api.model.IKeyPressFunctions;

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
public class KeyPressFunctions implements IKeyPressFunctions {

  // NOTE: This is not an "active class," since it only consists of getters
  // and setters. I made this class to reduce the amount that Level needs to do,
  // since I felt that class was already getting too long. In addition, this class makes
  // it easy for the controller to update the game based on what key is pressed, and also
  // makes it easy to add new types of key presses by adding a new boolean / getter / setter
  // to this class. In hindsight, maybe this could have been in Level, but I feel that having
  // this class overall made the project easier to maintain and made it easier to wrap my head
  // around how the key presses affect the game.

  private boolean isPaused = false;
  private boolean movePlayerLeft = false;
  private boolean movePlayerRight = false;
  private boolean jumpPlayer = false;

  /**
   * Getter for isPaused
   * @return isPaused (if it returns true, the model should "short-circuit" the methods in its step
   * method, skipping over them and pausing the game)
   */
  @Override
  public boolean isPaused() {
    return isPaused;
  }

  /**
   * Getter for isPlayerMovingLeft
   * @return movePlayerLeft (if it returns true, the model should attempt to move the player left)
   */
  @Override
  public boolean isPlayerMovingLeft() {
    return movePlayerLeft;
  }

  /**
   * Getter for isPlayerMovingRight
   * @return movePlayerRight (if it returns true, the model should attempt to move the player right)
   */
  @Override
  public boolean isPlayerMovingRight() {
    return movePlayerRight;
  }

  /**
   * Getter for isPlayerJumping
   * @return jumpPlayer (if it returns true, the model should attempt to make the player jump)
   */
  @Override
  public boolean isPlayerJumping() {
    return jumpPlayer;
  }

  /**
   * Method that sets isPaused to true
   */
  @Override
  public void pauseGame() {
    isPaused = true;
  }

  /**
   * Method that sets isPaused to false
   */
  @Override
  public void resumeGame() {
    isPaused = false;
  }

  /**
   * Method that sets movePlayerLeft to true
   */
  @Override
  public void startMovingPlayerLeft() {
    movePlayerLeft = true;
  }

  /**
   * Method that sets movePlayerLeft to false
   */
  @Override
  public void stopMovingPlayerLeft() {
    movePlayerLeft = false;
  }

  /**
   * Method that sets movePlayerRight to true
   */
  @Override
  public void startMovingPlayerRight() {
    movePlayerRight = true;
  }

  /**
   * Method that sets movePlayerRight to false
   */
  @Override
  public void stopMovingPlayerRight() {
    movePlayerRight = false;
  }

  /**
   * Method that sets jumpPlayer to true
   */
  @Override
  public void startPlayerJumping() {
    jumpPlayer = true;
  }

  /**
   * Method that sets jumpPlayer to false
   */
  @Override
  public void stopPlayerJumping() {
    jumpPlayer = false;
  }
}
