package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import util.DukeApplicationTest;
import org.junit.jupiter.api.Test;


/**
 * Tests the FolderParser class
 */
public class FolderParserTest extends DukeApplicationTest{

  private static final int CURRENT_NUM_STYLESHEETS = 3;
  /**
   * Tests the FolderParser's ability to perform a simple parse
   */
  @Test
  public void testSimpleParse() {
      FolderParser parser = new FolderParser("./src/resources/cssstylesheets",
          ".css");
      List<String> foundFiles = parser.getFilenamesFromFolder();
      assertEquals(foundFiles.size(), CURRENT_NUM_STYLESHEETS);
      assertTrue(foundFiles.contains("dark"));
      assertTrue(foundFiles.contains("light"));
      assertTrue(foundFiles.contains("default"));
  }
}
