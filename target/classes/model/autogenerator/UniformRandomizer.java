package model.autogenerator;

import api.model.autogenerator.IUniformRandomizer;

/**
 * This class' job is to take in a properly formatted String and return a random number within the
 * range specified by that String (the format is "U(X:Y)" where the random number spawns within the
 * range [X,Y]. Its primary use is in the RandomGeneration class, which uses it to translate
 * arguments passed to itself formatted in the way shown above into random numbers.
 *
 * @author Alex Lu
 */
public class UniformRandomizer implements IUniformRandomizer {

  private int lowBound;
  private int highBound;
  private static final String EXCEPTION_MESSAGE = "Uniform Generation Failed";

  /**
   * Initializes a UniformRandomizer object The randomizerPath String should take the form "U(X:Y)"
   * with X,Y integers
   *
   * @param randomizerPath the config String
   */
  public UniformRandomizer(String randomizerPath) {
    try {
      initialize(randomizerPath);
    } catch (Exception e) {
      throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }
  }

  /**
   * Sets up the lowBound and highBound values necessary to facilitate random number generation
   *
   * @param randomizerPath the String containing config data to set up lowBound/highBound
   */
  @Override
  public void initialize(String randomizerPath) {
    randomizerPath = stripParentheses(randomizerPath);
    String[] lowHigh = randomizerPath.split(":");

    lowBound = Integer.parseInt(lowHigh[0]);
    highBound = Integer.parseInt(lowHigh[1]);
  }

  /**
   * Takes "(" and ")" out of path
   *
   * @param path a String containing exactly one instance of "(" and ")"
   * @return the new String
   */
  @Override
  public String stripParentheses(String path) {
    return path.substring(path.indexOf("(") + 1, path.indexOf(")"));
  }

  /**
   * Calculates a random value based on the input String
   *
   * @return a random int in the range [lowBound,highBound]
   */
  @Override
  public int getUniformValue() {
    int value = (int) (Math.random() * (highBound - lowBound + 1)) + lowBound;
    return value;
  }
}
