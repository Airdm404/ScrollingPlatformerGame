package model.scroll;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;
import model.Level;
import model.configuration.*;
import model.entity.Block;
import model.entity.Enemy;
import api.model.entity.IEntity;
import model.entity.MarioPlayer;
import model.entity.Player;
import api.model.configuration.IGameConfiguration;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Tests the ManualScroller class
 */
public class ManualScrollerTest extends DukeApplicationTest {

  private static final int ALWAYSSCROLL = 0;
  private static final int NOSCROLL = -1;

  private static final double PLAYER_XVEL = 0.15;
  private static final double PLAYER_YVEL = 0.35;

  private static final int PLAYERX = 4;
  private static final int PLAYERY = 5;

  private static final int BARRIERX = 12;
  private static final int BARRIERY = 8;

  private static final int ENEMYX = 6;
  private static final int ENEMYY = 7;

  private Player playerEntity;
  private Block barrierBlockEntity;
  private Enemy enemyEntity;

  private List<IEntity> entityList;
  private Level level;

  @Override
  public void start(Stage stage) throws InvalidFileException {
    entityList = new ArrayList<>();

    playerEntity = new MarioPlayer(PLAYERX, PLAYERY);
    barrierBlockEntity = new Block(BARRIERX, BARRIERY);
    enemyEntity = new Enemy(ENEMYX, ENEMYY);

    playerEntity.setXVel(PLAYER_XVEL);
    playerEntity.setYVel(PLAYER_YVEL);

    IGameConfiguration gameConfiguration = new GameConfiguration("oneBlock.properties");
    ILevelLoader ILevelLoader = new LevelLoader(gameConfiguration.getLevelFile(), new EntityFactory());
    level = new Level(ILevelLoader);

    level.addEntity(playerEntity);
    level.addEntity(barrierBlockEntity);
    level.addEntity(enemyEntity);
  }

  /**
   * Tests that when a scroller is set to only scroll horizontally, that when the player moves
   * in the x direction, only the x components of all enemy's hitboxes update
   */
  @Test
  public void testHorizontalScroller() {
    ManualScroller scroller = new ManualScroller(ALWAYSSCROLL,ALWAYSSCROLL,NOSCROLL,NOSCROLL);

    playerEntity.updateVelocity(false,true,true);
    playerEntity.updatePosition();
    scroller.scroll(level , playerEntity);

    assertEquals(PLAYERX, playerEntity.getHitBox().getXLeft());
    assertEquals(BARRIERX - PLAYER_XVEL, barrierBlockEntity.getHitBox().getXLeft());
    assertEquals(ENEMYX - PLAYER_XVEL, enemyEntity.getHitBox().getXLeft());

    assertEquals((int)(PLAYERY - PLAYER_YVEL), (int)(playerEntity.getHitBox().getYTop()));
    assertEquals(BARRIERY, barrierBlockEntity.getHitBox().getYTop());
    assertEquals(ENEMYY, enemyEntity.getHitBox().getYTop());
  }

  /**
   * Tests that when a scroller is set to only scroll vertically, that when the player moves
   * in the y direction, only the y components of all enemy's hitboxes update
   */
  @Test
  public void testVerticalScroller() {
    ManualScroller scroller = new ManualScroller(NOSCROLL, NOSCROLL, ALWAYSSCROLL, ALWAYSSCROLL);

    playerEntity.updateVelocity(false,true,true);
    playerEntity.updatePosition();
    scroller.scroll(level , playerEntity);

    assertEquals(PLAYERX + PLAYER_XVEL, playerEntity.getHitBox().getXLeft());
    assertEquals(BARRIERX, barrierBlockEntity.getHitBox().getXLeft());
    assertEquals(ENEMYX, enemyEntity.getHitBox().getXLeft());

    assertEquals(PLAYERY, playerEntity.getHitBox().getYTop());
    assertEquals((int)(BARRIERY + PLAYER_YVEL), (int)barrierBlockEntity.getHitBox().getYTop());
    assertEquals((int)(ENEMYY + PLAYER_YVEL), (int)enemyEntity.getHitBox().getYTop());
  }

  /**
   * Tests that when a scroller is set to scroll in either direction, that when the player moves
   * both the x and y components update
   */
  @Test
  public void testEitherScroller() {
    ManualScroller scroller = new ManualScroller(ALWAYSSCROLL,ALWAYSSCROLL,
        ALWAYSSCROLL,ALWAYSSCROLL);

    playerEntity.updateVelocity(false,true,true);
    playerEntity.updatePosition();
    scroller.scroll(level, playerEntity);

    assertEquals(PLAYERX, playerEntity.getHitBox().getXLeft());
    assertEquals(BARRIERX - PLAYER_XVEL, barrierBlockEntity.getHitBox().getXLeft());
    assertEquals(ENEMYX - PLAYER_XVEL, enemyEntity.getHitBox().getXLeft());

    assertEquals(PLAYERY, playerEntity.getHitBox().getYTop());
    assertEquals((int)(BARRIERY + PLAYER_YVEL), (int)barrierBlockEntity.getHitBox().getYTop());
    assertEquals((int)(ENEMYY + PLAYER_YVEL), (int)enemyEntity.getHitBox().getYTop());
  }

  /**
   * Tests that when the scroller scrolls (i.e. when level.step() is called, the score doesn't
   * change
   */
  @Test
  public void testGetScoreFromScroll() {
    ManualScroller scroller = new ManualScroller(ALWAYSSCROLL,ALWAYSSCROLL,
        ALWAYSSCROLL,ALWAYSSCROLL);

    assertEquals(0, level.getScore());
    level.setScroller(scroller);
    level.step();
    assertEquals(0, level.getScore());
  }
}