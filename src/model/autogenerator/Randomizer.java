package model.autogenerator;

import api.model.autogenerator.IRandomizer;

/**
 * This class takes a formatted String and returns a random number as configured by this formatted
 * String. The format should be R(x,y,z;a,b,c) where x, y and z are the possible values that could
 * be generated and a, b and c are the weighted probabilties of generating x, y and z respectively.
 *
 * RandomGeneration uses this class to translate these formatted Strings passed in as arguments into
 * random numbers that it can use to build a RandomGeneration.
 *
 * @author Alex Lu
 */
public class Randomizer implements IRandomizer {

  private int[] values;
  private double[] probabilities;
  private static final String EXCEPTION_MESSAGE = "Failed to build Randomizer";

  /**
   * Instantiates a Randomizer object
   *
   * @param randomizerString the formatted String that the Randomizer will use to generate a random
   *                         number
   */
  public Randomizer(String randomizerString) {
    try {
      initializeRandomizer(randomizerString);
    } catch (Exception e) {
      throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }
  }

  /**
   * Fills the data structures necessary to initialize the randomizer
   *
   * @param randomizerString the String argument that contains
   */
  @Override
  public void initializeRandomizer(String randomizerString) {
    randomizerString = stripParentheses(randomizerString);

    String[] pathPieces = randomizerString.split(";");
    String[] valueStrings = pathPieces[0].split(",");
    String[] probabiltiesStrings = pathPieces[1].split(",");

    values = fillIntArrayWithStrings(valueStrings);
    probabilities = fillDoubleArrayWithStrings(probabiltiesStrings);

    checkArrayLengthsAlign();
  }

  /**
   * Checks to make sure the values array has the same number of elements in it as the probabilities
   * array and if not throws an exception
   */
  @Override
  public void checkArrayLengthsAlign() {
    if (values.length != probabilities.length) {
      throw new IllegalArgumentException();
    }
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
   * Fills the values array with numbers pulled from a String array
   *
   * @param values the String array containing the numbers to be pulled
   * @return toBeFilled the int array to be filled with the values
   */
  @Override
  public int[] fillIntArrayWithStrings(String[] values) {
    int[] toBeFilled = new int[values.length];

    for (int index = 0; index < toBeFilled.length; index += 1) {
      toBeFilled[index] = Integer.parseInt(values[index]);
    }
    return toBeFilled;
  }

  /**
   * Fills the values array with numbers pulled from a String array
   *
   * @param values the String array containing the numbers to be pulled
   * @return toBeFilled the double array to be filled with the values
   */
  @Override
  public double[] fillDoubleArrayWithStrings(String[] values) {
    double[] toBeFilled = new double[values.length];

    for (int index = 0; index < toBeFilled.length; index += 1) {
      toBeFilled[index] = Double.parseDouble(values[index]);
    }

    return toBeFilled;
  }

  /**
   * Returns a random entityType in the values array weighted by the probabilties
   *
   * @return a random entityType in the values array
   */
  @Override
  public int getRandomValue() {
    double aggregatedProbability = 0;
    double randomNumber = Math.random();

    for (int index = 0; index < probabilities.length; index += 1) {
      aggregatedProbability += probabilities[index];
      if (randomNumber < aggregatedProbability) {
        return values[index];
      }
    }

    return values[0];
  }
}
