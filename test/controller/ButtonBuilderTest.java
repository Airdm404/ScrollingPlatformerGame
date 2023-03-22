package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.DukeApplicationTest;
import org.junit.jupiter.api.Test;


/**
 * Tests the ButtonBuilder class
 */
public class ButtonBuilderTest extends DukeApplicationTest{

  private static final String TEST_PATH = "./src/resources/buttons/optionsselectorbuttons.xml";
  private static final double WIDTH = 800;
  private static final double HEIGHT = 800;
  private ButtonBuilder builder;
  private List<Button> foundButtons;

  @Override
  public void start(Stage stage) throws BuilderInstantiationException {
    builder = new ButtonBuilder(WIDTH, HEIGHT, TEST_PATH, type -> {});
    foundButtons = builder.getFoundButtons();
  }

  /**
   * Tests the ButtonBuilder's ability to correctly create the right number of buttons
   * with the right ids from a .txt file
   */
  @Test
  public void testFillButtonListCorrectly() {
    assertEquals(foundButtons.size(), 5);
    List<String> buttonIds = new ArrayList<>();
    foundButtons.forEach(button -> buttonIds.add(button.getId()));
    assertTrue(buttonIds.contains("PrevCommand"));
    assertTrue(buttonIds.contains("NextCommand"));
    assertTrue(buttonIds.contains("Option1"));
    assertTrue(buttonIds.contains("Option2"));
    assertTrue(buttonIds.contains("Option3"));
  }

  /**
   * Tests that a few of the buttons are put in the correct location on the screen based on their
   * specification in the file
   */
  @Test
  public void testButtonPlacementCorrect() {
      Button option1 = null;
      Button prevOptions = null;
      for (Button found : foundButtons) {
        if (found.getId().equals("PrevCommand")) {
          prevOptions = found;
        }
        else if (found.getId().equals("Option1")) {
          option1 = found;
        }
      }
      assertEquals(option1.getLayoutX(), WIDTH * 0.25 - 0.20 * WIDTH / 2);
      assertEquals(option1.getLayoutY(), HEIGHT * 0.50 - 0.10 * HEIGHT / 2);
      assertEquals(prevOptions.getLayoutX(), WIDTH * 0.30 - 0.15 * WIDTH/2);
      assertEquals(prevOptions.getLayoutY(), HEIGHT * 0.90 - 0.05 * HEIGHT/2);
  }
}