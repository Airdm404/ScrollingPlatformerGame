package model.scroll;


import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.stage.Stage;
import model.Level;
import model.configuration.*;
import model.entity.Block;
import model.entity.Enemy;
import model.entity.MarioPlayer;
import model.entity.Player;
import api.model.configuration.IGameConfiguration;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests the AutoScroller class
 */
public class AutoScrollerTest extends DukeApplicationTest {

  private static final double XSCROLL = -0.5;
  private static final double YSCROLL = 0.5;
  private static final double PLAYER_XVEL = 0.15;
  private static final double PLAYER_YVEL = 0.35;

  private static final int PLAYERX = 4;
  private static final int PLAYERY = 5;

  private static final int BARRIERX = 12;
  private static final int BARRIERY = 8;

  private static final int ENEMYX = 6;
  private static final int ENEMYY = 7;
  private static final int SCORE_FROM_SCROLL = 1;

  private Player playerEntity;
  private Block barrierBlockEntity;
  private Enemy enemyEntity;

  private Level level;

  @Override
  public void start(Stage stage) throws InvalidFileException {

    playerEntity = new MarioPlayer(PLAYERX, PLAYERY);
    barrierBlockEntity = new Block(BARRIERX, BARRIERY);
    enemyEntity = new Enemy(ENEMYX, ENEMYY);

    IGameConfiguration IGameConfiguration = new GameConfiguration("oneBlock.properties");
    ILevelLoader ILevelLoader = new LevelLoader(IGameConfiguration.getLevelFile(), new EntityFactory());
    level = new Level(ILevelLoader);

    level.addEntity(playerEntity);
    level.addEntity(barrierBlockEntity);
    level.addEntity(enemyEntity);
  }

  /**
   * Tests that the Autoscroller automatically moves all entities by the amounts specified
   */
  @Test
  public void testSimpleScroll() {
    AutoScroller scroller = new AutoScroller(XSCROLL,YSCROLL, false);
    scroller.scroll(level, playerEntity);

    assertEquals(PLAYERX + XSCROLL, playerEntity.getHitBox().getXLeft());
    assertEquals(BARRIERX + XSCROLL, barrierBlockEntity.getHitBox().getXLeft());
    assertEquals(ENEMYX + XSCROLL, enemyEntity.getHitBox().getXLeft());

    assertEquals(PLAYERY + YSCROLL, playerEntity.getHitBox().getYTop());
    assertEquals(BARRIERY + YSCROLL, barrierBlockEntity.getHitBox().getYTop());
    assertEquals(ENEMYY + YSCROLL, enemyEntity.getHitBox().getYTop());
  }

  /**
   * Tests that the Autoscroller scrolls correctly when it is told not to scroll by any amount
   * vertically and only to scroll horizontally
   */
  @Test
  public void testScrollHorizontal() {
    AutoScroller scroller = new AutoScroller(XSCROLL,0, false);
    scroller.scroll(level, playerEntity);

    assertEquals(PLAYERX + XSCROLL, playerEntity.getHitBox().getXLeft());
    assertEquals(BARRIERX + XSCROLL, barrierBlockEntity.getHitBox().getXLeft());
    assertEquals(ENEMYX + XSCROLL, enemyEntity.getHitBox().getXLeft());

    assertEquals(PLAYERY, playerEntity.getHitBox().getYTop());
    assertEquals(BARRIERY, barrierBlockEntity.getHitBox().getYTop());
    assertEquals(ENEMYY, enemyEntity.getHitBox().getYTop());
  }

  /**
   * Tests that the Autoscroller scrolls correctly when it is told not to scroll by any amount
   * horizontally and only to scroll vertically
   */
  @Test
  public void testScrollVertical() {
    AutoScroller scroller = new AutoScroller(XSCROLL,YSCROLL, false);
    scroller.scroll(level, playerEntity);

    assertEquals(PLAYERX + XSCROLL, playerEntity.getHitBox().getXLeft());
    assertEquals(BARRIERX + XSCROLL, barrierBlockEntity.getHitBox().getXLeft());
    assertEquals(ENEMYX + XSCROLL, enemyEntity.getHitBox().getXLeft());

    assertEquals(PLAYERY + YSCROLL, playerEntity.getHitBox().getYTop());
    assertEquals(BARRIERY + YSCROLL, barrierBlockEntity.getHitBox().getYTop());
    assertEquals(ENEMYY + YSCROLL, enemyEntity.getHitBox().getYTop());
  }

  /**
   * Tests that the Autoscroller correctly positions the player when they move right before a scroll
   */
  @Test
  public void testPlayerScroll() {
    AutoScroller scroller = new AutoScroller(XSCROLL,YSCROLL, false);

    playerEntity.setXVel(PLAYER_XVEL);
    playerEntity.setYVel(PLAYER_YVEL);
    playerEntity.updateVelocity(false, true, false);
    playerEntity.updatePosition();
    scroller.scroll(level, playerEntity);

    assertEquals((int)(PLAYERX + XSCROLL + PLAYER_XVEL), (int)playerEntity.getHitBox().getXLeft());

    assertEquals((int)(PLAYERY + YSCROLL + PLAYER_YVEL), (int)(playerEntity.getHitBox().getYTop()));
  }

  /**
   * Tests that the Autoscroller correctly positions the player when the pScrolls variable is set to
   * true (i.e. the autoscroller locks the player in place)
   */
  @Test
  public void testScrollerLocksPlayer() {
    AutoScroller scroller = new AutoScroller(XSCROLL,YSCROLL, true);

    playerEntity.setXVel(PLAYER_XVEL);
    playerEntity.setYVel(PLAYER_YVEL);

    scroller.scroll(level, playerEntity);

    assertEquals(PLAYERX, playerEntity.getHitBox().getXLeft());

    assertEquals(PLAYERY, playerEntity.getHitBox().getYTop());
  }

  /**
   * Tests that when the scroller scrolls (i.e. when level.step() is called, the score increases
   * by one
   */
  @Test
  public void testGetScoreFromScroll() {
    assertEquals(0, level.getScore());
    level.step();
    assertEquals(SCORE_FROM_SCROLL, level.getScore());
  }

}