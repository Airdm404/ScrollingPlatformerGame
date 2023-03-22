package model.autogenerator;

import static org.junit.jupiter.api.Assertions.*;


import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests the ConstantGeneration class
 */
public class ConstantGenerationTest extends DukeApplicationTest{

  private static final int NUM_COLS = 20;
  private static final int NUM_ROWS = 10;

  @Override
  public void start(Stage st) {

  }

  /**
   * Tests that the functionality of a simple ConstantInstruction (i.e. after initialization,
   * has expected startRow, endRow, startCol, endCol and entityType values
   */
  @Test
  public void testSimpleGenerate() {
    String[] constructionArgs = {"Constant", "3", "4:5", "3:9"};
    ConstantGeneration inst = new ConstantGeneration(NUM_ROWS, NUM_COLS, constructionArgs);

    assertEquals(4, inst.getStartRow());
    assertEquals(5, inst.getEndRow());
    assertEquals(3, inst.getStartCol());
    assertEquals(9, inst.getEndCol());
    assertEquals("3", inst.getEntityTypeToInsert());
  }

  /**
   * Tests that when using the * as a parameter in constructionArgs[2], that the ConstantInstruction
   * initializes correctly (i.e. "8:*")
   */
  @Test
  public void testAllRows() {

    // Use star as first argument to constructionArgs[2] to set startRow to 0
    String[] constructionArgs = {"Constant", "3", "*:5", "3:9"};
    ConstantGeneration inst = new ConstantGeneration(NUM_ROWS, NUM_COLS, constructionArgs);
    assertEquals(0, inst.getStartRow());
    assertEquals(5, inst.getEndRow());

    // Use star as second argument to constructionArgs[2] to set endRow to NUM_ROWS - 1
    String[] constructionArgs2 = {"Constant", "3", "4:*", "3:9"};
    ConstantGeneration inst2 = new ConstantGeneration(NUM_ROWS, NUM_COLS, constructionArgs2);
    assertEquals(4, inst2.getStartRow());
    assertEquals(NUM_ROWS - 1, inst2.getEndRow());

    // Use star as first and second argument to constructionArgs[2] to set startRow to 0 and
    // endRow to NUM_ROWS - 1
    String[] constructionArgs3 = {"Constant", "3", "*:*", "3:9"};
    ConstantGeneration inst3 = new ConstantGeneration(NUM_ROWS, NUM_COLS, constructionArgs3);
    assertEquals(0, inst3.getStartRow());
    assertEquals(NUM_ROWS - 1, inst3.getEndRow());
  }

  /**
   * Tests that when using the * as a parameter in constructionArgs[3], that the ConstantInstruction
   * initializes correctly (i.e. "*:7")
   */
  @Test
  public void testAllCols() {
    // Use star as first argument to constructionArgs[3] to set startCol to 0
    String[] constructionArgs = {"Constant", "3", "3:9", "*:5"};
    ConstantGeneration inst = new ConstantGeneration(NUM_ROWS, NUM_COLS, constructionArgs);
    assertEquals(0, inst.getStartCol());
    assertEquals(5, inst.getEndCol());

    // Use star as second argument to constructionArgs[3] to set endCol to NUM_COLS - 1
    String[] constructionArgs2 = {"Constant", "3", "3:9", "4:*"};
    ConstantGeneration inst2 = new ConstantGeneration(NUM_ROWS, NUM_COLS, constructionArgs2);
    assertEquals(4, inst2.getStartCol());
    assertEquals(NUM_COLS - 1, inst2.getEndCol());

    // Use star as first and second argument to constructionArgs[3] to set startCol to 0 and
    // endCol to NUM_COLS - 1
    String[] constructionArgs3 = {"Constant", "3", "3:9", "*:*"};
    ConstantGeneration inst3 = new ConstantGeneration(NUM_ROWS, NUM_COLS, constructionArgs3);
    assertEquals(0, inst3.getStartCol());
    assertEquals(NUM_COLS - 1, inst3.getEndCol());
  }

  /**
   * Tests that the ConstantInstruction throws the correct exception when it is fed
   * invalid data with which to construct a row (i.e. constructionArgs[2])
   */
  @Test
  public void testInvalidRowInput() {

    // startingRow > endingRow
    String[] args = {"Constant", "2", "9:8", "*:*"};
    assertThrows(GenerationException.class,
        () -> new ConstantGeneration(NUM_ROWS, NUM_COLS, args));

    // startingRow < 0
    String[] args2 = {"Constant", "2", "-1:8", "*:*"};
    assertThrows(GenerationException.class,
        () -> new ConstantGeneration(NUM_ROWS, NUM_COLS, args2));

    // endingRow >  NUM_ROWS - 1
    String[] args3 = {"Constant", "2", "5:10", "*:*"};
    assertThrows(GenerationException.class,
        () -> new ConstantGeneration(NUM_ROWS, NUM_COLS, args3));

    // No colon in args4[2]
    String[] args4 = {"Constant", "2", "98", "*:*"};
    assertThrows(GenerationException.class,
        () -> new ConstantGeneration(NUM_ROWS, NUM_COLS, args4));

    // Strange input
    String[] args5 = {"Constant", "2", "abc", "*:*"};
    assertThrows(GenerationException.class,
        () -> new ConstantGeneration(NUM_ROWS, NUM_COLS, args5));
  }

  /**
   * Tests that the ConstantInstruction throws the correct exception when it is fed
   * invalid invalid data with which to construct a column (i.e. constructionArgs[2])
   */
  @Test
  public void testInvalidColInput() {

    // startingCol > endingCol
    String[] args = {"Constant", "2", "*:*", "3:2"};
    assertThrows(GenerationException.class,
        () -> new ConstantGeneration(NUM_ROWS, NUM_COLS, args));

    // startingCol < 0
    String[] args2 = {"Constant", "2", "*:*", "-1:2"};
    assertThrows(GenerationException.class,
        () -> new ConstantGeneration(NUM_ROWS, NUM_COLS, args2));

    // endingCol >  NUM_Col - 1
    String[] args3 = {"Constant", "2", "*:*", "1:20"};
    assertThrows(GenerationException.class,
        () -> new ConstantGeneration(NUM_ROWS, NUM_COLS, args3));

    // No colon in args4[2]
    String[] args4 = {"Constant", "2", "*:*", "87"};
    assertThrows(GenerationException.class,
        () -> new ConstantGeneration(NUM_ROWS, NUM_COLS, args4));

    // Strange input
    String[] args5 = {"Constant", "2", "*:*", ""};
    assertThrows(GenerationException.class,
        () -> new ConstantGeneration(NUM_ROWS, NUM_COLS, args5));
  }

  /**
   * Tests what happens when the array passed in has an invalid number of parameters
   */
  @Test
  public void testBadArrayInput() {
    String[] args = {"Constant", "2", "*:*"};
    assertThrows(GenerationException.class,
        () -> new ConstantGeneration(NUM_ROWS, NUM_COLS, args));
  }

}