package model.autogenerator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class UniformRandomizerTest extends DukeApplicationTest {

  /**
   * Tests the UniformRandomizer's ability to generate a value in the [lowBound,highBound]
   * range
   */
  @Test
  public void testSimpleRandomization() {
    String randomizationString = "V(4:5)";
    UniformRandomizer randomizer = new UniformRandomizer(randomizationString);

    int randomValue = randomizer.getUniformValue();
    assertTrue(randomValue >= 4);
    assertTrue(randomValue <= 5);
  }

  /**
   * Tests that when UniformRandomizer is asked to make a value in the [lowBound,highBound]
   * range 100 times, that the value returned is always in that range
   */
  @Test
  public void testStressRandomization() {
    String randomizationString = "V(3:11)";
    UniformRandomizer randomizer = new UniformRandomizer(randomizationString);

    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;

    for (int loop = 1; loop <= 100; loop += 1) {
      int randomValue = randomizer.getUniformValue();
      max = Math.max(max, randomValue);
      min = Math.min(min, randomValue);
    }
    assertTrue(min >= 3);
    assertTrue(max <= 11);
  }

  /**
   * Tests that when UniformRandomizer receives bad input, it throws an exception
   */
  @Test
  public void testBadInput() {

    // Missing front parenthesis
    String randomizationString = "V3:11)";
    assertThrows(IllegalArgumentException.class,
        () -> new UniformRandomizer(randomizationString));

    // Missing back parenthesis
    String randomizationString2 = "V(3:11";
    assertThrows(IllegalArgumentException.class,
        () -> new UniformRandomizer(randomizationString2));

    // Missing both parentheses
    String randomizationString3 = "V3:11";
    assertThrows(IllegalArgumentException.class,
        () -> new UniformRandomizer(randomizationString3));

    // No colon separation
    String randomizationString4 = "V(311)";
    assertThrows(IllegalArgumentException.class,
        () -> new UniformRandomizer(randomizationString4));

    // Arguments for lowBounds and highBounds not integers
    String randomizationString5 = "V(A:B)";
    assertThrows(IllegalArgumentException.class,
        () -> new UniformRandomizer(randomizationString5));

    // Arguments for lowBounds and highBounds doubles
    String randomizationString6 = "V(0.5:0.6)";
    assertThrows(IllegalArgumentException.class,
        () -> new UniformRandomizer(randomizationString6));
  }



}
