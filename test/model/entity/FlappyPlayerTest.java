package model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FlappyPlayerTest {
  FlappyPlayer flappyPlayer = new FlappyPlayer(20,20);

  @Test
  public void ignoresLeftTest() {
    flappyPlayer.updateVelocity(true, false, false);
    double oldXPos = flappyPlayer.getHitBox().getXLeft();
    flappyPlayer.updatePosition();
    double newXPos = flappyPlayer.getHitBox().getXLeft();
    assertEquals(newXPos, oldXPos, 0.02);
  }

  @Test
  public void ignoresRightTest() {
    flappyPlayer.updateVelocity(false, true, false);
    double oldXPos = flappyPlayer.getHitBox().getXLeft();
    flappyPlayer.updatePosition();
    double newXPos = flappyPlayer.getHitBox().getXLeft();
    assertEquals(newXPos, oldXPos, 0.02);
  }

  @Test
  public void gravityTest() {
    flappyPlayer.updateVelocity(false, true, false);
    double oldYPos = flappyPlayer.getHitBox().getYTop();
    flappyPlayer.updatePosition();
    double newYPos = flappyPlayer.getHitBox().getYTop();
    assertTrue(oldYPos < newYPos);
  }

  @Test
  public void basicJumpTest() {
    flappyPlayer.updateVelocity(false, false, false);
    double oldXPos = flappyPlayer.getHitBox().getXLeft();
    flappyPlayer.updatePosition();
    double newXPos = flappyPlayer.getHitBox().getXLeft();
    assertEquals(newXPos, oldXPos);
  }

  @Test
  public void ignoresDuplicateDirectionKeysTest() {
    flappyPlayer.updateVelocity(true, true, false);
    double oldXPos = flappyPlayer.getHitBox().getXLeft();
    flappyPlayer.updatePosition();
    double newXPos = flappyPlayer.getHitBox().getXLeft();
    assertEquals(newXPos, oldXPos);
  }

  @Test
  public void ignoresJumpKeyTest() {
    flappyPlayer.updateVelocity(false, true, false);
    double oldYPos = flappyPlayer.getHitBox().getYTop();
    flappyPlayer.updatePosition();
    double newYPos = flappyPlayer.getHitBox().getYTop();
    assertEquals(newYPos, oldYPos, 0.02);
  }

}
