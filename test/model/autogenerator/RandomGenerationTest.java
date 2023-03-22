package model.autogenerator;

import static org.junit.jupiter.api.Assertions.*;


import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests the RandomGeneration class
 */
public class RandomGenerationTest extends DukeApplicationTest{

  private static final int NUM_COLS = 20;
  private static final int NUM_ROWS = 10;

  @Override
  public void start(Stage st) {

  }

  /**
   * Tests that a RandomGeneration's values (i.e. start row, end row, etc. equal the expected
   * parameters passed into the method
   *
   * @param inst the RandomInstruction
   * @param expectedEntity the expected entity String
   * @param expectedStartRow the expected start row
   * @param expectedEndRow the expected end row
   * @param expectedStartCol the expected start col
   * @param expectedEndCol the expected end col
   */
  private void testInstruction(RandomGeneration inst, String expectedEntity,
      int expectedStartRow, int expectedEndRow,
      int expectedStartCol, int expectedEndCol) {

    assertEquals(expectedEntity, inst.getEntityTypeToInsert());
    assertEquals(expectedStartRow, inst.getStartRow());
    assertEquals(expectedEndRow, inst.getEndRow());
    assertEquals(expectedStartCol, inst.getStartCol());
    assertEquals(expectedEndCol, inst.getEndCol());
  }

  /**
   * Tests that when the random instruction is built with "constant" parameters (i.e. randomizers
   * or uniform ranomizer symbols, it builds correctly
   */
  @Test
  public void testConstantParameters() {
      String[] args = {"Random", "2", "RIGHT:DOWN", "3", "4", "5", "6"};
      RandomGeneration inst = new RandomGeneration(NUM_ROWS, NUM_COLS, args);
      testInstruction(inst, "2", 3, 7,
          4, 9);
  }

  /**
   * Tests that changing the "direction" parameter (args[2] to RIGHT:DOWN means that the width will
   * move to the right and the height down from the origin point specified in args[3] and args[4]
   */
  @Test
  public void testDirectionRightDown() {
    String[] args = {"Random", "2", "RIGHT:DOWN", "5", "10", "3", "2"};
    RandomGeneration inst = new RandomGeneration(NUM_ROWS, NUM_COLS, args);
    testInstruction(inst, "2", 5, 7,
        10, 11);
  }

  /**
   * Tests that changing the "direction" parameter (args[2] to RIGHT:UP means that the width will
   * move to the right and the height up from the origin point specified in args[3] and args[4]
   */
  @Test
  public void testDirectionRightUp() {
    String[] args = {"Random", "2", "RIGHT:UP", "5", "10", "3", "2"};
    RandomGeneration inst = new RandomGeneration(NUM_ROWS, NUM_COLS, args);
    testInstruction(inst, "2", 3, 5,
        10, 11);
  }

  /**
   * Tests that changing the "direction" parameter (args[2] to LEFT:UP means that the width will
   * move to the left and the height up from the origin point specified in args[3] and args[4]
   */
  @Test
  public void testDirectionLeftUp() {
    String[] args = {"Random", "2", "LEFT:UP", "5", "10", "3", "2"};
    RandomGeneration inst = new RandomGeneration(NUM_ROWS, NUM_COLS, args);
    testInstruction(inst, "2", 3, 5,
        9, 10);
  }

  /**
   * Tests that changing the "direction" parameter (args[2] to LEFT:DOWN means that the width will
   * move to the left and the height down from the origin point specified in args[3] and args[4]
   */
  @Test
  public void testDirectionLeftDown() {
    String[] args = {"Random", "2", "LEFT:DOWN", "5", "10", "3", "2"};
    RandomGeneration inst = new RandomGeneration(NUM_ROWS, NUM_COLS, args);
    testInstruction(inst, "2", 5, 7,
        9, 10);
  }

  /**
   * Tests that making the "direction" parameter (i.e. args[2]) invalid throws an error
   */
  @Test
  public void testInvalidDirection() {
    String[] args = {"Random", "2", "LEFT", "10", "5", "3", "2"};
    assertThrows(GenerationException.class,
        () -> new RandomGeneration(NUM_ROWS, NUM_COLS, args));
  }

  /**
   * Tests that when the RandomGenerationInstruction receives U as args[3], the start
   * row generated is between 0 and BLOCKS_WIDE - 1
   */
  @Test
  public void testRandomStartRow() {
    String[] args = {"Random", "2", "RIGHT:DOWN", "U(2:9)", "10", "3", "2"};
    RandomGeneration inst = new RandomGeneration(NUM_ROWS, NUM_COLS, args);

    int maxStartRow = Integer.MIN_VALUE;
    int minStartRow = Integer.MAX_VALUE;

    for (int iteration = 1; iteration <= 100; iteration+=1) {
      maxStartRow = Math.max(maxStartRow, inst.getStartRow());
      minStartRow = Math.min(minStartRow, inst.getStartRow());
    }

    assertTrue(maxStartRow <= 9);
    assertTrue(minStartRow >= 2);
  }

  /**
   * Tests that when the RandomGenerationInstruction receives U as args[4], the start
   * col generated is between 0 and BLOCKS_WIDE - 1
   */
  @Test
  public void testRandomStartCol() {
    String[] args = {"Random", "2", "RIGHT:DOWN", "5", "U(4:13)", "3", "2"};
    RandomGeneration inst = new RandomGeneration(NUM_ROWS, NUM_COLS, args);

    int maxStartCol = Integer.MIN_VALUE;
    int minStartCol = Integer.MAX_VALUE;

    for (int iteration = 1; iteration <= 100; iteration+=1) {
      inst.regenerate();
      maxStartCol = Math.max(maxStartCol, inst.getStartCol());
      minStartCol = Math.min(minStartCol, inst.getStartCol());
    }

    assertTrue(maxStartCol <= 13);
    assertTrue(minStartCol >= 4);
  }

  /**
   * Tests that when the RandomGeneration is fed a randomization string (see: Randomizer.java),
   * for its width in args[5], it correctly sets the end row of the RandomGeneration to be
   * startRow + [randomly generated number based on randomization String]
   *
   * In this case, endRow must be from 5 and 7 because the RandomGeneration can fill 1, 2, or 3
   * rows
   */
  @Test
  public void testRandomWidth() {
    String[] args = {"Random", "2", "RIGHT:DOWN", "5", "3", "R(1,2,3;0.5,0.25,0.25)", "2"};
    RandomGeneration inst = new RandomGeneration(NUM_ROWS, NUM_COLS, args);

    assertEquals(5, inst.getStartRow());
    assertTrue(inst.getEndRow() >= 5);
    assertTrue(inst.getEndRow() <= 7);
  }

  /**
   * Tests that when the RandomGeneration is fed a randomization string (see: Randomizer.java),
   * for its height in args[6], it correctly sets the end column of the RandomGeneration to be
   * startCol + [randomly generated number based on randomization String]
   *
   * In this case, endCol must be from 12 and 15 because the RandomGeneration can fill 3, 4, or 6
   * columns
   */
  @Test
  public void testRandomHeight() {
    String[] args = {"Random", "2", "RIGHT:DOWN", "7", "10", "2", "R(3,4,6;0.5,0.25,0.25)"};
    RandomGeneration inst = new RandomGeneration(NUM_ROWS, NUM_COLS, args);

    assertEquals(10, inst.getStartCol());
    assertTrue(inst.getEndCol() >= 12);
    assertTrue(inst.getEndCol() <= 15);
  }

  /**
   * Tests that the RandomGeneration generator can handle invalid array input
   */
  @Test
  public void testInvalidInput() {

    //Row out of bounds
    String[] args = {"Random", "2", "RIGHT:DOWN", "11", "19", "3", "2"};
    assertThrows(GenerationException.class,
        () -> new RandomGeneration(NUM_ROWS, NUM_COLS, args));

    //Col out of bounds
    String[] args2 = {"Random", "2", "RIGHT:DOWN", "5", "21", "3", "2"};
    assertThrows(GenerationException.class,
        () -> new RandomGeneration(NUM_ROWS, NUM_COLS, args2));

    //Invalid row number (not number or "U")
    String[] args3 = {"Random", "2", "RIGHT:DOWN", "A", "21", "3", "2"};
    assertThrows(GenerationException.class,
        () -> new RandomGeneration(NUM_ROWS, NUM_COLS, args3));

    //Invalid column number (not number or "U")
    String[] args4 = {"Random", "2", "RIGHT:DOWN", "2", "C", "3", "2"};
    assertThrows(GenerationException.class,
        () -> new RandomGeneration(NUM_ROWS, NUM_COLS, args4));

    //Invalid width (not number or "R(...)")
    String[] args5 = {"Random", "2", "RIGHT:DOWN", "2", "2", "W", "2"};
    assertThrows(GenerationException.class,
        () -> new RandomGeneration(NUM_ROWS, NUM_COLS, args5));

    //Invalid height (not number or "R(...)")
    String[] args6 = {"Random", "2", "RIGHT:DOWN", "2", "4", "3", "H"};
    assertThrows(GenerationException.class,
        () -> new RandomGeneration(NUM_ROWS, NUM_COLS, args6));

    //Invalid array length
    String[] args7 = {"Random", "2", "RIGHT:DOWN", "2", "4", "3"};
    assertThrows(GenerationException.class,
        () -> new RandomGeneration(NUM_ROWS, NUM_COLS, args7));

  }

}