package model;

import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.Stage;
import model.configuration.*;
import model.entity.Player;
import api.model.configuration.IGameConfiguration;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class LevelTest extends DukeApplicationTest {

  private static final int PLAYER_XVEL = 1;
  private static final int PLAYER_YVEL = 4;
  private static final int NEW_PLAYERY = 16;

  private static final int DEFAULTX = 7;
  private static final int DEFAULTY = 6;

  private Level level;
  private ILevelLoader lev;

  @Override
  public void start(Stage stage) throws InvalidFileException {

    IGameConfiguration game = new GameConfiguration("doodlejump.properties");
    EntityFactory factory = new EntityFactory();
    factory.updatePlayerMapping("DoodlePlayer");
    lev = new LevelLoader(game.getLevelFile(), factory);
    level = new Level(lev);
  }

  /**
   * Tests to make sure that when the player falls off the screen, the level is lost
   */
  @Test
    public void testPlayerDies() {
      Player player = level.getPlayerList().get(0);
      assertEquals(DEFAULTX, player.getHitBox().getXLeft());
      assertEquals(DEFAULTY, player.getHitBox().getYTop());

      player.setYVel(PLAYER_YVEL);
      player.setXVel(PLAYER_XVEL);
      player.updatePosition();
      level.step();

      assertNotEquals(DEFAULTX, player.getHitBox().getXLeft());
      assertNotEquals(DEFAULTY, player.getHitBox().getYTop());

      player.getHitBox().setYTop(NEW_PLAYERY);
      level.step();

      assertTrue(level.isLevelLost());

      level.reinitialize();
      player = level.getPlayerList().get(0);

      assertEquals(DEFAULTX, player.getHitBox().getXLeft());
      assertEquals(DEFAULTY, player.getHitBox().getYTop());
    }
}