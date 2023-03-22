package model.autogenerator;

import static org.junit.jupiter.api.Assertions.*;


import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Tests the AutoGenerator class
 */
public class AutoGeneratorTest extends DukeApplicationTest{

  private static final double NUM_ROWS = 10;
  private static final double NUM_COLS = 15;
  private static final String TEST_FILEPATH = "testauto.xml";

  private AutoGenerator generator;
  private String[][] generation;

  @Override
  public void start(Stage st) {
      try {
        generator = new AutoGenerator(TEST_FILEPATH);
        generation = generator.generateNextBlock();
      }
      catch (Exception e) {
        System.out.println("test file failed to load");
      }
  }

  /**
   * Tests that the dimensions of the 2D String array spawned by the AutoGeneration are correct
   * based on the file read in
   */
  @Test
  public void testDimensionsCorrect() {
    assertEquals(NUM_ROWS, generation.length);
    assertEquals(NUM_COLS, generation[0].length);
  }

  /**
   * Tests that the default entities of the 2D String array spawned by the AutoGeneration are
   * correct based on the file read in
   */
  @Test
  public void testDefaultCorrect() {
    assertEquals("D", generation[0][0]);
    assertEquals("D", generation[4][5]);
    assertEquals("D", generation[6][14]);
    assertEquals("D", generation[2][3]);
  }

  /**
   * Tests that the constant entities of the 2D String array spawned by the AutoGeneration are
   * correct based on the file read in (i.e. we know that every value in row 9 should be "C"
   * based on the file configuation
   */
  @Test
  public void testConstantsCorrect() {
    for (int column = 0; column < generation[9].length; column+=1) {
      assertEquals("C", generation[9][column]);
    }
  }

  /**
   * Tests that the random generation as specified in the config file, actually happens. There
   * should be exactly one "R" somewhere in row 7 and exactly one "R" somewhere in row 8
   */
  @Test
  public void testRandomsCorrect() {
    int numR = 0;

    for (int column = 0; column < generation[7].length; column+=1) {
      if (generation[7][column].equals("R")) {
        numR+=1;
      }
    }
    assertEquals(3, numR);

    for (int column = 0; column < generation[8].length; column+=1) {
      if (generation[7][column].equals("R")) {
        numR+=1;
      }
    }
    assertEquals(6, numR);
  }

  /**
   * Tests that when an invalid file path is passed to the AutoGenerator, it throws
   * an exception
   */
  @Test
  public void testBadFile() {
    assertThrows(GenerationException.class, () -> new AutoGenerator("hi"));
  }


}