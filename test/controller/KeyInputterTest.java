package controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.GameModel;
import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import view.GameView;


public class KeyInputterTest extends DukeApplicationTest {

  private KeyInputter testInputter;

  private static final String DJ_INPUTS = "doodlejumpkeyinputs.properties";
  private static final String DEFAULT_PAUSE = "P";
  private static final String DEFAULT_UP = "W";
  private static final String DEFAULT_RIGHT = "D";
  private static final String DEFAULT_LEFT = "A";
  private static final String DEFAULT_RESET = "R";

  private static final String LEFT = "left";
  private static final String UP = "up";
  private static final String RIGHT = "right";
  private static final String PAUSE = "pause";
  private static final String RESET = "reset";

  @Override
  public void start(Stage stage) throws InvalidFileException {
      testInputter = new KeyInputter(
          new GameModel(new GameConfiguration("supermario.properties")));
  }

  /**
   * Tests to make sure that when the user pushes the key corresponding to left (i.e. for the
   * default key input map, that would be DEFAULT_LEFT or A) the left() method is called
   */
  @Test
  public void testLeft() throws KeyInputterMissingMethodException {
    testInputter.keyPressed(DEFAULT_LEFT);
    assertEquals(LEFT, testInputter.getLastPush());
  }

  /**
   * Tests to make sure that when the user pushes the key corresponding to up (i.e. for the
   * default key input map, that would be DEFAULT_UP or W) the up() method is called
   */
  @Test
  public void testUp() throws KeyInputterMissingMethodException {
    testInputter.keyPressed(DEFAULT_UP);
    assertEquals(UP, testInputter.getLastPush());
  }
  /**
   * Tests to make sure that when the user pushes the key corresponding to right (i.e. for the
   * default key input map, that would be DEFAULT_RIGHT or D) the right() method is called
   */
  @Test
  public void testRight() throws KeyInputterMissingMethodException {
    testInputter.keyPressed(DEFAULT_RIGHT);
    assertEquals(RIGHT, testInputter.getLastPush());
  }

  /**
   * Tests to make sure that when the user pushes the key corresponding to left (i.e. for the
   * default key input map, that would be DEFAULT_LEFT or A) the left() method is called
   */
  @Test
  public void testPause() throws KeyInputterMissingMethodException {
    testInputter.keyPressed(DEFAULT_PAUSE);
    assertEquals(PAUSE, testInputter.getLastPush());
  }

  /**
   * Tests to make sure that when the user pushes the key corresponding to reset (i.e. for the
   * default key input map, that would be DEFAULT_RESET or R) the reset() method is called
   */
  @Test
  public void testReset() throws KeyInputterMissingMethodException {
    testInputter.keyPressed(DEFAULT_RESET);
    assertEquals(RESET, testInputter.getLastPush());
  }

  /**
   * Tests to make sure that when the user pushes the key corresponding to pause (i.e. for the
   * default key input map, that would be DEFAULT_PAUSE or P) the pause() method is called
   */
  @Test
  public void testIrrelevantKeyPush() throws KeyInputterMissingMethodException {
    testInputter.keyPressed("L");
    assertEquals("", testInputter.getLastPush());
  }

  /**
   * Tests the situation where the user switches a valid replacement key (i.e. a key that is not
   * currently mapped to a method) into a valid current key (i.e. a key that is currently mapped
   * to a method)
   */
  @Test
  public void testValidSwap() throws KeyInputterMissingMethodException {
    testInputter.keyPressed(DEFAULT_RIGHT);
    assertEquals(RIGHT, testInputter.getLastPush());

    testInputter.swapKeyInput(DEFAULT_RIGHT, "B");
    testInputter.keyPressed(DEFAULT_RIGHT);
    assertEquals("", testInputter.getLastPush());

    testInputter.keyPressed("B");
    assertEquals(RIGHT, testInputter.getLastPush());
  }

  /**
   * Tests the situation where the user switches a valid replacement key (i.e. a key that is not
   * currently mapped to a method) into an invalid current key (i.e. a key that is not currently
   * mapped to a method)
   */
  @Test
  public void testValidSwapInvalidCurrentKey() throws KeyInputterMissingMethodException {
    testInputter.keyPressed(DEFAULT_RIGHT);
    assertEquals(RIGHT, testInputter.getLastPush());

    testInputter.swapKeyInput("C", "B");
    testInputter.keyPressed("C");
    assertEquals("", testInputter.getLastPush());
    testInputter.keyPressed("B");
    assertEquals("", testInputter.getLastPush());


    // i.e. make sure that all of the default key inputs still call the default methods
    testLeft();
    testPause();
    testRight();
    testUp();

  }

  /**
   * Tests the situation where the user switches an invalid replacement key (i.e. a key that is
   * currently mapped to a method so it can't be a replacement) into a valid current key
   * (i.e. a key that is currently mapped to a method)
   */
  @Test
  public void testValidSwapInvalidReplacementKey() throws KeyInputterMissingMethodException {
    testInputter.keyPressed(DEFAULT_RIGHT);
    assertEquals(RIGHT, testInputter.getLastPush());

    testInputter.swapKeyInput(DEFAULT_RIGHT, DEFAULT_LEFT);

    // i.e. make sure that the default left and right keys (A,D) still call the correct methods
    // (left, right)
    testLeft();
    testRight();
  }

  @Test
  public void testSwitchKeyInputSourceFile() {
    assertDoesNotThrow(() -> testInputter.loadKeyInputsFromFile(DJ_INPUTS));
  }

  /**
   * Tests the situation where the user tries to switch the
   */
  @Test
  public void testSwitchKeyInputBadSourceFile() {
    assertThrows(InvalidFileException.class, () -> testInputter.loadKeyInputsFromFile("hi"));
  }

  /**
   * Tests the method getKeyMethodPairings() to return the key -> method <String, String> pairs
   * that the KeyInputter currently contains
   */
  @Test
  public void testGetKeyMethodPairings() {
    List<Pair<String, String>> keyMethodPairings = testInputter.getKeyMethodPairings();

    assertEquals(5, keyMethodPairings.size());
    String[] keys = {"A", "D", "W", "R", "P"};
    List<String> keysList = Arrays.asList(keys);

    for (Pair<String, String> pair : keyMethodPairings) {
      assertTrue(keysList.contains(pair.getKey()));
      switch (pair.getKey()) {
        case "A" -> assertEquals("left", pair.getValue());
        case "D" -> assertEquals("right", pair.getValue());
        case "W" -> assertEquals("up", pair.getValue());
        case "R" -> assertEquals("reset", pair.getValue());
        case "P" -> assertEquals("pause", pair.getValue());
      }
    }
  }
}
