package model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.GameModel;
import api.model.IGameModel;
import model.Level;
import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import api.model.scroll.Scroller;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests whether the PlayerEntity can actually move
 */
public class MarioPlayerTest extends DukeApplicationTest {

  private static final String TEST_LEVEL_FILE_PATH = "marioMovementTestLevel.properties";

  IGameModel IGameModel = new GameModel(new GameConfiguration(TEST_LEVEL_FILE_PATH, this.getClass()));
  Player player = IGameModel.getLevel().getPlayerList().get(0);

  public MarioPlayerTest() throws InvalidFileException {
  }


  @Test
  public void PlayerMoveLeftTest() {
    double oldXPos = player.getHitBox().getXLeft();
    IGameModel.getKeyPressFunctions().startMovingPlayerLeft();
    IGameModel.getLevel().step();
    double newXPos = player.getHitBox().getXLeft();
    assertTrue(newXPos < oldXPos);
  }

  @Test
  public void PlayerMoveRightTest() {
    double oldXPos = player.getHitBox().getXLeft();
    IGameModel.getKeyPressFunctions().startMovingPlayerRight();
    IGameModel.getLevel().step();
    double newXPos = player.getHitBox().getXLeft();
    assertTrue(newXPos > oldXPos);
  }

  @Test
  public void PlayerJumpPlayerGoesUpTest() {
    double oldYPos = player.getHitBox().getYTop();
    IGameModel.getKeyPressFunctions().startPlayerJumping();
    IGameModel.getLevel().step();
    IGameModel.getLevel().step();
    double newYPos = player.getHitBox().getYTop();
    assertTrue(newYPos < oldYPos);
  }

  @Test
  public void PlayerJumpPlayerGoesBackDownTest() {
    IGameModel.getKeyPressFunctions().startPlayerJumping();
    for(int i=0; i < 50; i++) {
      IGameModel.getLevel().step();
    }
    IGameModel.getKeyPressFunctions().stopPlayerJumping();
    double oldYPos = player.getHitBox().getYTop();
    for(int i=0; i < 10000; i++) {
      IGameModel.getLevel().step();
    }
    double newYPos = player.getHitBox().getYTop();
    assertTrue(newYPos > oldYPos);
  }

  @Test
  public void PlayerJumpStopsAtGroundTest() {
    double oldYPos = player.getHitBox().getYTop();
    IGameModel.getKeyPressFunctions().startPlayerJumping();
    IGameModel.getLevel().step();
    IGameModel.getKeyPressFunctions().stopPlayerJumping();
    for(int i=0; i < 10000; i++) {
      IGameModel.getLevel().step();
    }
    double newYPos = player.getHitBox().getYTop();
    assertTrue(Math.abs(newYPos - oldYPos) < 1);
  }

  /**
   * Checks to make sure that if the player tries to go offscreen to the left, their
   * xLeft is reverted to be just tangent to the edge of the screen
   */
  @Test
  public void PlayerStaysInBoundsLeftTest() {
    Level level = IGameModel.getLevel();

    Player player = level.getPlayerList().get(0);
    player.getHitBox().setXLeft(-5);
    assertEquals(-5, player.getHitBox().getXLeft());

    level.step();
    assertEquals(0, player.getHitBox().getXLeft());

  }

  /**
   * Checks to make sure that if the player tries to go offscreen to the right, their
   * xRight is reverted to be just tangent to the edge of the screen
   */
  @Test
  public void PlayerStaysInBoundsRightTest() {
    Level level = IGameModel.getLevel();

    Player player = level.getPlayerList().get(0);
    player.getHitBox().setXRight(Scroller.NUM_BLOCKS + 2);
    assertEquals(Scroller.NUM_BLOCKS + 2, player.getHitBox().getXRight());

    level.step();
    assertEquals(Scroller.NUM_BLOCKS, player.getHitBox().getXRight());
  }

}
