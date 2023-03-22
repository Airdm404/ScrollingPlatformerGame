package model.score;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.stage.Stage;
import api.model.score.IGameLeaderboard;
import api.model.score.IScoreTuple;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Tests the GameLeaderboard class
 */
public class GameLeaderboardTest extends DukeApplicationTest {
  private IGameLeaderboard leaderboard;
  private static final String PATH = "testleaderboard.csv";
  private static final String TITLE = "TEST";

  /**
   * Runs before all tests
   * @param stage the stage to be used for any JavaFX displays
   * @throws FileNotFoundException
   */
  @Override
  public void start(Stage stage) throws FileNotFoundException {
      leaderboard = new GameLeaderboard(PATH);
  }

  /**
   * Tests the getTitle() method
   */
  @Test
  public void testGetTitle() {
    assertEquals(TITLE, leaderboard.getTitle());
  }

  /**
   * Tests that the correct tuple is returned by the getScoreTupleAtPlace() method
   */
  @Test
  public void testGetScoreTupleAtPlace() {
    String tupleLine = leaderboard.getScoreTupleAtPlace(1);
    assertEquals(tupleLine.substring(0, tupleLine.indexOf(":")), "Mike");
    assertEquals(tupleLine.substring(tupleLine.indexOf(" ") + 1), "4");
  }

  /**
   * Tests that the "" is returned by the getScoreTupleAtPlace() method
   * when it is fed an invalid index
   */
  @Test
  public void testGetScoreTupleAtPlaceErrors() {
    String tupleLine = leaderboard.getScoreTupleAtPlace(0);
    assertEquals("", tupleLine);

    tupleLine = leaderboard.getScoreTupleAtPlace(30);
    assertEquals("", tupleLine);
  }

  /**
   * Tests the addScoreTuple() method
   */
  @Test
  public void testAddScoreTuple() throws IOException {
    IScoreTuple tuple = new ScoreTuple("Ryan", 3);
    leaderboard.addScoreTuple(tuple);

    String tupleLine = leaderboard.getScoreTupleAtPlace(2);
    assertEquals(tupleLine.substring(0, tupleLine.indexOf(":")), "Ryan");
    assertEquals(tupleLine.substring(tupleLine.indexOf(" ") + 1), "3");
  }
}