package controller;

import api.controller.IKeyInputterMethodCaller;
import api.model.IKeyPressFunctions;
import api.model.IGameModel;

/**
 * This class' job is to call methods on a GameModel in order to control the player movement,
 * jumping or other user-dependent states in the GameModel. It is used primarily by KeyInputter in
 * order to call methods on the GameModel that KeyInputter saved as an instance variable during
 * construction
 *
 * @author Alex Lu
 */
public class KeyInputterMethodCaller implements IKeyInputterMethodCaller {

  private IGameModel model;
  private IKeyPressFunctions functions;

  /**
   * Constructs a KeyInputterMethodCaller object
   *
   * @param mo the GameModel object that this class will call methods on
   */
  public KeyInputterMethodCaller(IGameModel mo) {
    model = mo;
    functions = model.getKeyPressFunctions();
  }

  /**
   * Tells the model to move the player left
   */
  @Override
  public void left() {
    functions.startMovingPlayerLeft();
  }

  /**
   * Tells the model to move right
   */
  @Override
  public void right() {
    functions.startMovingPlayerRight();
  }

  /**
   * Tells the model to move up (i.e. jump)
   */
  @Override
  public void up() {
    functions.startPlayerJumping();
  }

  /**
   * Tells the model to move down (i.e. crouch)
   */
  @Override
  public void down() {
    // DO NOTHING
  }

  /**
   * Tells the model to pause
   */
  @Override
  public void pause() {
    if (functions.isPaused()) {
      functions.resumeGame();
    } else {
      functions.pauseGame();
    }
  }

  /**
   * Tells the model to stop moving the player left
   */
  @Override
  public void leftRelease() {
    functions.stopMovingPlayerLeft();
  }

  /**
   * Tells the model to stop moving the player right
   */
  @Override
  public void rightRelease() {
    functions.stopMovingPlayerRight();
  }

  /**
   * Tells the model to stop the jump
   */
  @Override
  public void upRelease() {
    functions.stopPlayerJumping();
  }

  /**
   * Tells the model to stop the down (i.e. crouch)
   */
  @Override
  public void downRelease() {
    // DO NOTHING
  }

  /**
   * Tells the model to release pause
   */
  @Override
  public void pauseRelease() {
    // DO NOTHING
  }

  /**
   * Resets the game
   */
  @Override
  public void reset() {
    model.getLevel().reinitialize();
  }

  /**
   * Player has released the reset key
   */
  @Override
  public void resetRelease() {
    // DO NOTHING
  }
}
