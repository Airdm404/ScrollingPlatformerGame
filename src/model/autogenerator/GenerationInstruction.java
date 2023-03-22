package model.autogenerator;

import api.model.autogenerator.IGenerationInstruction;

/**
 * This class is the abstract super class that ConstantGeneration and RandomGeneration extend. It
 * contains data on the number of max allowed rows (i.e. numRows) and the number of max allowed
 * columns (i.e. numCols) and has a few variables startRow, endRow, startCol, endCol and entityType
 * that allow AutoGenerator to insert entity type into the generation between startRow and endRow
 * and startCol and endCol
 *
 * @author Alex Lu
 */
public abstract class GenerationInstruction implements IGenerationInstruction {

  protected int numRows;
  protected int numCols;

  protected int startRow;
  protected int startCol;
  protected int endRow;
  protected int endCol;
  protected String entityType;

  private static final String EXCEPTION_MESSAGE = "Failed to build Instruction";


  /**
   * Builds a generation instruction (i.e. if you pass (6, 4) your parent generation might look like
   * 0,0,0,0
   * 0,0,0,0
   * 0,0,0,0
   * 0,0,0,0
   * 0,0,0,0
   * 0,0,0,0
   *
   * @param rows the number of blocks wide of the new block that we're building the generation for
   * @param cols the number of blocks tall of the new block that we're building the generation for
   */
  public GenerationInstruction(int rows, int cols) {
    numRows = rows;
    numCols = cols;
  }

  /**
   * @return the row from which to start drawing the entity
   */
  @Override
  public int getStartRow() {
    return startRow;
  }

  /**
   * @return the column from which to start drawing the entity
   */
  @Override
  public int getStartCol() {
    return startCol;
  }

  /**
   * @return the row at which to stop drawing the entity
   */
  @Override
  public int getEndRow() {
    return endRow;
  }

  /**
   * @return the column at which to stop drawing the entity
   */
  @Override
  public int getEndCol() {
    return endCol;
  }

  /**
   * @return the String entity to fill in between startx and endx and starty and endy
   */
  @Override
  public String getEntityTypeToInsert() {
    return entityType;
  }


  /**
   * Checks to make sure that startRow, endRow, startCol and endCol all have valid values. If not,
   * throws an exception
   */
  @Override
  public void validate() {
    if (endCol < startCol || endRow < startRow) {
      throwGenerationException();
    } else if (endCol > numCols - 1 || endRow > numRows - 1) {
      throwGenerationException();
    } else if (startRow < 0 || startCol < 0) {
      throwGenerationException();
    }
  }

  /**
   * Throws an exception to let the user know that the ConstantInstruction failed to build
   */
  @Override
  public void throwGenerationException() {
    throw new GenerationException(EXCEPTION_MESSAGE);
  }
}
