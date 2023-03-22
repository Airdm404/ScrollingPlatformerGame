package model.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.collision.Direction;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests the GameScene class
 */
public class CollisionDetectionTest extends DukeApplicationTest {



  @Test
  public void collisionTrueXTest() {
    Player player = new MarioPlayer(5, 5) {

    };
    Block block = new Block(5.8,5);
    player.checkCollision(block);
    assertTrue(player.getCurrentCollision().contains(Direction.RIGHT));
  }

  @Test
  public void collisionTrueYTest() {
    Player player = new MarioPlayer(5, 5);
    player.setGrounded(false);
    Block block = new Block(5,5.9);
    player.checkCollision(block);
    assertTrue(player.getCurrentCollision().contains(Direction.BOTTOM));
  }

  @Test
  public void collisionFalseTest() {
    Player player = new MarioPlayer(25, 25);
    Block block = new Block(10,10);
    player.checkCollision(block);
    assertFalse(player.getCurrentCollision().doesCollide());
  }

  @Test
  public void collisionBorderlineHitTest() {
    Player player = new MarioPlayer(0, 0);
    Block block = new Block(0.99,0.99);
    player.checkCollision(block);
    assertTrue(player.getCurrentCollision().doesCollide());
    assertTrue(player.getCurrentCollision().contains(Direction.RIGHT));
    assertTrue(player.getCurrentCollision().contains(Direction.BOTTOM));
    assertFalse(player.getCurrentCollision().contains(Direction.LEFT));
    assertFalse(player.getCurrentCollision().contains(Direction.TOP));
  }

  @Test
  public void collisionBorderlineMissTest() {
    Player player = new MarioPlayer(0, 0);
    Block block = new Block(1,1);
    player.setXVel(5);
    player.setYVel(5);
    player.checkCollision(block);
    assertFalse(player.getCurrentCollision().doesCollide());
  }
}
