package model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.stage.Stage;
import api.model.score.IScoreTuple;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ScoreTupleTest extends DukeApplicationTest {

  private IScoreTuple tuple;

  private static final int SCORE = 1000;
  private static final String NAME = "Borat";
  /**
   * Runs upon start of all tests
   * @param stage the stage to be used
   */
  @Override
  public void start(Stage stage) {
    tuple = new ScoreTuple(NAME, SCORE);
  }

  /**
   * Tests the getName() method in ScoreTuple
   */
  @Test
  public void testGetName() {
    assertEquals(NAME, tuple.getName());
  }

  /**
   * Tests the getScore() method in ScoreTuple
   */
  @Test
  public void testGetScore() {
    assertEquals(SCORE, tuple.getScore());
  }

  /**
   * Tests the toString() method in ScoreTuple
   */
  @Test
  public void testToString() {
    assertEquals(NAME + ": " + SCORE, tuple.toString());
  }

  /**
   * Tests the compareTo() method in ScoreTuple
   */
  @Test
  public void testCompareTo() {
    IScoreTuple other = new ScoreTuple(NAME, SCORE + 1);
    int comparison = tuple.compareTo(other);
    assertTrue(comparison > 0);

    List<IScoreTuple> tuples = new ArrayList<>();
    tuples.add(tuple);
    tuples.add(other);
    Collections.sort(tuples);

    assertEquals(other, tuples.get(0));
    assertEquals(tuple, tuples.get(1));
  }
}