package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests the KeyPressFunctions class
 */
public class KeyPressFunctionsTest extends DukeApplicationTest {

  api.model.IKeyPressFunctions IKeyPressFunctions = new KeyPressFunctions();

  @Test
  public void isPausedTest() {
    assertFalse(IKeyPressFunctions.isPaused());
    IKeyPressFunctions.pauseGame();
    assertTrue(IKeyPressFunctions.isPaused());
    IKeyPressFunctions.resumeGame();;
    assertFalse(IKeyPressFunctions.isPaused());
  }

  @Test
  public void isPlayerMovingLeftTest() {
    assertFalse(IKeyPressFunctions.isPlayerMovingLeft());
    IKeyPressFunctions.startMovingPlayerLeft();
    assertTrue(IKeyPressFunctions.isPlayerMovingLeft());
    IKeyPressFunctions.stopMovingPlayerLeft();
    assertFalse(IKeyPressFunctions.isPlayerMovingLeft());
  }

  @Test
  public void isPlayerMovingRightTest() {
    assertFalse(IKeyPressFunctions.isPlayerMovingRight());
    IKeyPressFunctions.startMovingPlayerRight();
    assertTrue(IKeyPressFunctions.isPlayerMovingRight());
    IKeyPressFunctions.stopMovingPlayerRight();
    assertFalse(IKeyPressFunctions.isPlayerMovingRight());
  }

  @Test
  public void isPlayerJumpingTest() {
    assertFalse(IKeyPressFunctions.isPlayerJumping());
    IKeyPressFunctions.startPlayerJumping();
    assertTrue(IKeyPressFunctions.isPlayerJumping());
    IKeyPressFunctions.stopPlayerJumping();
    assertFalse(IKeyPressFunctions.isPlayerJumping());
  }


}
