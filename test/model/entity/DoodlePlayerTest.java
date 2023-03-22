package model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DoodlePlayerTest {
  DoodlePlayer doodlePlayer = new DoodlePlayer(20,20);

  @Test
  public void movesLeftTest() {
    doodlePlayer.updateVelocity(true, false, false);
    double oldXPos = doodlePlayer.getHitBox().getXLeft();
    doodlePlayer.updatePosition();
    double newXPos = doodlePlayer.getHitBox().getXLeft();
    assertTrue(newXPos < oldXPos);
  }

  @Test
  public void movesRightTest() {
    doodlePlayer.updateVelocity(false, true, false);
    double oldXPos = doodlePlayer.getHitBox().getXLeft();
    doodlePlayer.updatePosition();
    double newXPos = doodlePlayer.getHitBox().getXLeft();
    assertTrue(newXPos > oldXPos);
  }

  @Test
  public void noXMovementWithoutKeyPressTest() {
    doodlePlayer.updateVelocity(false, false, false);
    double oldXPos = doodlePlayer.getHitBox().getXLeft();
    doodlePlayer.updatePosition();
    double newXPos = doodlePlayer.getHitBox().getXLeft();
    assertEquals(newXPos, oldXPos);
  }

  @Test
  public void ignoresDuplicateDirectionKeysTest() {
    doodlePlayer.updateVelocity(true, true, false);
    double oldXPos = doodlePlayer.getHitBox().getXLeft();
    doodlePlayer.updatePosition();
    double newXPos = doodlePlayer.getHitBox().getXLeft();
    assertEquals(newXPos, oldXPos);
  }

  @Test
  public void ignoresJumpKeyTest() {
    doodlePlayer.updateVelocity(false, true, false);
    double oldYPos = doodlePlayer.getHitBox().getYTop();
    doodlePlayer.updatePosition();
    double newYPos = doodlePlayer.getHitBox().getYTop();
    assertEquals(newYPos, oldYPos, 0.02);
  }

}
