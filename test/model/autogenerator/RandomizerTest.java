package model.autogenerator;

import static org.junit.jupiter.api.Assertions.*;


import java.util.HashSet;
import java.util.Set;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests the Randomizer class
 */
public class RandomizerTest extends DukeApplicationTest{

  private static final double WIDTH = 800;
  private static final double HEIGHT = 800;
  private Randomizer randomizer;

  @Override
  public void start(Stage st) {

  }

  /**
   * Tests that when a randomizer is fed one value that is to be returned with a probability of 1,
   * it returns that value
   */
  @Test
  public void testBaseCase() {
    Randomizer randomizer = new Randomizer("R(3;1)");
    assertEquals(3, randomizer.getRandomValue());
  }

  /**
   * Tests that when a randomizer is fed an array of values "1,2,3,4" and told to return each with
   * probability 0.25, after 20 calls to return a value on the randomizer, all have been returned at
   * least once
   */
  @Test
  public void testLargerCase() {
    Randomizer randomizer = new Randomizer("R(1,2,3,4;0.25,0.25,0.25,0.25)");
    Set<Integer> foundValues = new HashSet<>();

    for (int loop = 1; loop <=20; loop+=1) {
      int randomInt = randomizer.getRandomValue();
      foundValues.add(randomInt);
    }

    assertEquals(4, foundValues.size());
    assertTrue(foundValues.contains(1));
    assertTrue(foundValues.contains(2));
    assertTrue(foundValues.contains(3));
    assertTrue(foundValues.contains(4));
  }

  /**
   * Tests that the randomizer throws an exception when fed an invalid String
   */
  @Test
  public void testInputMissingParentheses() {

    // Empty input
    assertThrows(IllegalArgumentException.class, () -> new Randomizer("R()"));

    // Missing closing parentheses
    assertThrows(IllegalArgumentException.class,
        () -> new Randomizer("R(1,2,3;0.5,0.2,0.2"));

    // Missing opening parentheses
    assertThrows(IllegalArgumentException.class,
        () -> new Randomizer("R1,2,3;0.5,0.2,0.2)"));

    // Missing both parentheses
    assertThrows(IllegalArgumentException.class,
        () -> new Randomizer("R1,2,3;0.5,0.2,0.2"));
  }

  /**
   * Tests that the randomizer throws an exception when fed an empty input
   */
  @Test
  public void testEmptyInput() {

    // Empty input
    assertThrows(IllegalArgumentException.class, () -> new Randomizer("R()"));

    // Missing value/probability arrays
    assertThrows(IllegalArgumentException.class, () -> new Randomizer("R(;)"));

    // Empty String
    assertThrows(IllegalArgumentException.class,
        () -> new Randomizer(""));
  }

  /**
   * Tests that the randomizer throws an exception when fed misformatted input
   */
  @Test
  public void testStrangeInput() {
    assertThrows(IllegalArgumentException.class,
        () -> new Randomizer("abcdefghi"));
  }

  /**
   * Tests that the randomizer throws an exception when fed arguments to the R(...) that are
   * not numbers
   */
  @Test
  public void testArgumentsNotNumbers() {

    // Values Not Numbers
    assertThrows(IllegalArgumentException.class,
        () -> new Randomizer("R(A,B,C;0.5,0.2,0.2)"));

    // Probabilities Not Numbers
    assertThrows(IllegalArgumentException.class,
        () -> new Randomizer("R(1,2,3;A,B,C)"));

    // Values and Probabilities Not Numbers
    assertThrows(IllegalArgumentException.class,
        () -> new Randomizer("R(D,E,F;A,B,C)"));
  }

  /**
   * Tests that the randomizer throws an exception when the value and probability arrays are different
   * lengths
   */
  @Test
  public void testArrayLengthsMismatched() {

    assertThrows(IllegalArgumentException.class,
        () -> new Randomizer("R(1,2,3;0.2,0.2)"));
  }

}
