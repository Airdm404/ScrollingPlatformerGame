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
 * Tests the AutoGenerationScroller class
 */

public class AutoGenerationScrollerTest extends DukeApplicationTest{

  private static final int MAX = 15;
  private static final String PATH = "autoflappy.xml";
  private static final double XSCROLL = -0.5;
  private static final double YSCROLL = 0.5;

  private static final int PLAYERX = 4;
  private static final int PLAYERY = 5;

  private static final int BARRIERX = 12;
  private static final int BARRIERY = 8;

  private static final int ENEMYX = 6;
  private static final int ENEMYY = 7;

  private Player playerEntity;
  private Block barrierBlockEntity;
  private Enemy enemyEntity;

  private Level level;

  @Override
  public void start(Stage stage) throws InvalidFileException {

    playerEntity = new MarioPlayer(PLAYERX, PLAYERY);
    barrierBlockEntity = new Block(BARRIERX, BARRIERY);
    enemyEntity = new Enemy(ENEMYX, ENEMYY);

    IGameConfiguration gameConfiguration = new GameConfiguration("oneBlock.properties");
    ILevelLoader ILevelLoader = new LevelLoader(gameConfiguration.getLevelFile(), new EntityFactory());
    level = new Level(ILevelLoader);

    level.addEntity(playerEntity);
    level.addEntity(barrierBlockEntity);
    level.addEntity(enemyEntity);
  }

  /**
   * Tests that the HorizontalGenerationScroller correctly scrolls everything when not generating
   * a new portion of the game
   */
  @Test
  public void testSimpleScroll() {
    AutoGenerationScroller scroller = new AutoGenerationScroller(XSCROLL,YSCROLL,
        false, PATH);
    scroller.scroll(level, playerEntity);

    assertEquals(PLAYERX + XSCROLL, playerEntity.getHitBox().getXLeft());
    assertEquals(BARRIERX + XSCROLL, barrierBlockEntity.getHitBox().getXLeft());
    assertEquals(ENEMYX + XSCROLL, enemyEntity.getHitBox().getXLeft());

    assertEquals(PLAYERY + YSCROLL, playerEntity.getHitBox().getYTop());
    assertEquals(BARRIERY + YSCROLL, barrierBlockEntity.getHitBox().getYTop());
    assertEquals(ENEMYY + YSCROLL, enemyEntity.getHitBox().getYTop());
  }
}