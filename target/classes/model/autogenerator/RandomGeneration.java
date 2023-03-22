package model.autogenerator;

import api.model.autogenerator.IRandomGeneration;

/**
 * The purpose of this class is to serve as a randomized set of instructions, or instructions that
 * will not be the same every time they are queried by AutoGenerator (return unique startRow, endRow
 * etc. when asked where to fill in the generation with entities). AutoGenerator contains a list of
 * these keeping track of all of the different random instructions to execute when making a new
 * generation (2D String array) in AutoGenerator.
 *
 * @author Alex Lu
 */
public class RandomGeneration extends GenerationInstruction implements IRandomGeneration {

  private static final String LEFT = "LEFT";
  private static final String UP = "UP";

  private final String[] specifications;
  private boolean growsLeft;
  private boolean growsUp;

  /**
   * Instantiates a RandomGeneration object
   *
   * @param rows the number of rows in the master 2D array returned by AutoGenerator
   * @param cols the number of cols in the master 2D array returned by AutoGenerator
   * @param args the String arguments used to build this ConstantGeneration object (see
   *             buildInstruction(args) for more details)
   */
  public RandomGeneration(int rows, int cols, String[] args) {

    super(rows, cols);
    specifications = args;

    try {
      buildInstruction(specifications);
      validate();
    } catch (Exception e) {
      throwGenerationException();
    }
  }


  /**
   * Builds a constant instruction based on a String array. The array should be specified as follows
   * args[0] = type (i.e. "Random") args[1] = entity type (i.e. "2" for enemy)
   * <p>
   * args[2] = direction that this random instruction builds in (i.e. LEFT:UP)
   * <p>
   * args[3] = start row specification (i.e. 3) args[4] = start column specification (i.e. 4) Note,
   * an argument of U(x:y) to either args[2] or args[3] means unifomrly generate the start row or
   * column in the range [x,y]
   * <p>
   * args[5] = width specification in the form of a Randomizer String args[6] = height specification
   * in the form of a Randomizer String Note, a randomizer String is R(1,2,3;0.50,0.30,0.20) where
   * the set of arguments before the semi colon is the set of valid values and the set of arguments
   * after the semi-colon is the set of probabilities corresponding to each of those values
   * <p>
   * In this case we would generate 1 with probability 0.50, 2 with probability 0.30 and 3 with
   * probability 0.20
   *
   * @param args the array of arguments
   */
  public void buildInstruction(String[] args) {
    entityType = args[1];
    setDirectionOfGrowth(args[2]);
    buildStartRow(args[3]);
    buildStartCol(args[4]);
    buildRowDepth(args[5]);
    buildColDepth(args[6]);
  }

  /**
   * Extracts data on which directions to expand the generation in from its (x,y) origin from
   * growthString
   * <p>
   * (i.e. "RIGHT:DOWN" would mean that given (X,Y) are the anchor coordinates of the generation and
   * if width is 2 and height is 4, the startRow would be X - 2 and the endRow would be X and the
   * startCol would be Y and endCol would be Y + 4)
   *
   * @param growthString the String specifying the direction the generation grows in
   */
  private void setDirectionOfGrowth(String growthString) {
    String[] xyDirections = growthString.split(":");
    growsLeft = xyDirections[0].equals(RandomGeneration.LEFT);
    growsUp = xyDirections[1].equals(RandomGeneration.UP);
  }

  /**
   * Determines the startRow coordinate from xArg
   *
   * @param xArg either the starting x coordinate or "R" if we should randomize to calculate it
   */
  private void buildStartRow(String xArg) {
    startRow = decodeStartArg(xArg);
  }

  /**
   * Determines the startCol coordinate from yArg
   *
   * @param yArg either the starting y coordinate or "U" if we should randomize to calculate it
   */
  private void buildStartCol(String yArg) {
    startCol = decodeStartArg(yArg);
  }

  /**
   * Creates a int value based on a String passed into args[3] or args[4] (startRow or startCol
   * parameters)
   *
   * @param arg the String configuration argyment
   * @return an int based on the String argument
   */
  private int decodeStartArg(String arg) {
    if (arg.charAt(0) == 'U') {
      UniformRandomizer randomizer = new UniformRandomizer(arg);
      return randomizer.getUniformValue();
    } else {
      return Integer.parseInt(arg);
    }
  }

  /**
   * Calculates a random number from a Randomizer using a String that it reads
   *
   * @param randomizedString the String prepared to be passed into the randomizer
   * @return the random number
   */
  private int getNumberFromRandomizedString(String randomizedString) {
    if (randomizedString.charAt(0) == 'R') {
      Randomizer randomizer = new Randomizer(randomizedString);
      return randomizer.getRandomValue();
    }
    return Integer.parseInt(randomizedString);
  }

  /**
   * @param firstPoint the value of the starting point <<<<<<< HEAD
   * @param length     the length from the firstPoint that the secondPoint is (note, this length may
   *                   be negative)
   * @param maxAllowed the maximum possible size for the start and end points =======
   * @param length     the length from the firstPoint that the secondPoint is (note, this length may
   *                   be negative)
   * @param maxAllowed >>>>>>> ba77acecde0504e1241725b1ca2dd80c3fa638d1
   * @return an array startAndEnd with the following characteristics: startAndEnd[0] <
   * startAndEnd[1] startAndEnd[0] >= 0 startAndEnd[1] <= maxAllowed
   */
  private int[] getStartAndEnd(int firstPoint, int length, int maxAllowed) {
    int secondPoint = firstPoint + length;

    int start = Math.min(firstPoint, secondPoint);
    int end = Math.max(firstPoint, secondPoint);

    int checkedStart = Math.max(start, 0);
    int checkedEnd = Math.min(end, maxAllowed);

    return new int[]{checkedStart, checkedEnd};
  }

  /**
   * Builds the width for the generation
   *
   * @param randomizedWidth the String prepared to be passed into the randomizer
   */
  private void buildRowDepth(String randomizedWidth) {
    int rowDepth = getNumberFromRandomizedString(randomizedWidth) - 1;

    if (growsUp) {
      rowDepth *= -1;
    }

    int[] startAndEnd = getStartAndEnd(startRow, rowDepth, numRows - 1);
    startRow = startAndEnd[0];
    endRow = startAndEnd[1];
  }

  /**
   * Builds the width for the genertation
   *
   * @param randomizedHeight the String prepared to be passed into the randomizer
   */
  private void buildColDepth(String randomizedHeight) {
    int colDepth = getNumberFromRandomizedString(randomizedHeight) - 1;

    if (growsLeft) {
      colDepth *= -1;
    }

    int[] startAndEnd = getStartAndEnd(startCol, colDepth, numCols - 1);
    startCol = startAndEnd[0];
    endCol = startAndEnd[1];
  }

  /**
   * Recalculates values for this random instruction
   */
  @Override
  public void regenerate() {
    buildInstruction(specifications);
  }
}
