package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.GameModel;
import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Tests that KeyBinder actually changes the binding of the keys in testInputter
 */
public class KeyBinderTest extends DukeApplicationTest {

  private static final String DEFAULT_PAUSE = "P";
  private static final String DEFAULT_UP = "W";
  private static final String DEFAULT_RIGHT = "D";
  private static final String DEFAULT_LEFT = "A";
  private static final String DEFAULT_DOWN = "S";

  private static final String LEFT = "left";
  private static final String UP = "up";
  private static final String RIGHT = "right";
  private static final String DOWN = "down";
  private static final String PAUSE = "pause";

  private static final String UPDATE_LABEL_TEXT = "Press a key to replace ";
  private static final String BAD_KEY_TEXT = "That key is invalid!";
  private static final String WAITING_FOR_UPDATE_TEXT = "Press a button to update controls";
  private static final String UPDATE_ID = "UPDATE";

  private static final String RESOURCE_BUNDLE_PATH = "resources/resourcebundles.English";

  private KeyInputter testInputter;
  private KeyBinder testBinder;
  private Text instructionsLabel;

  @Override
  public void start(Stage stage) throws
      InvalidFileException {

    testInputter = new KeyInputter(
        new GameModel(new GameConfiguration("supermario.properties")));

    testBinder = new KeyBinder(ResourceBundle.getBundle(RESOURCE_BUNDLE_PATH));
    testBinder.updateKeyInputScreen(testInputter);
    instructionsLabel = (Text)(testBinder.lookup("#" + UPDATE_ID));
  }

  /**
   * Simulates a key press on testBinder of type code
   * @param code the code
   */
  private void keyPress(KeyCode code) {
    testBinder.handleKey(new KeyEvent(KeyEvent.KEY_PRESSED, code.getChar(),
        code.getName(), code, false, false,
        false, false));
  }

  /**
   * Tests that when the user provides a valid switch (i.e. swaps out a key in the
   * key -> method map for another valid key), that the new key is mapped to the correct method
   */
  @Test
  public void testSimpleSwitch() {
    Button upButton = (Button)(testBinder.lookup("#" + DEFAULT_UP));
    upButton.fire();
    keyPress(KeyCode.C);

    List<Pair<String, String>> keyMethodPairs = testInputter.getKeyMethodPairings();

    boolean foundNewKey = false;
    for (Pair<String, String> pair : keyMethodPairs) {
      assertNotEquals(DEFAULT_UP, pair.getKey());
      if (pair.getKey().equals("C")) {
        foundNewKey = true;
        assertEquals(UP, pair.getValue());
      }
    }
    assertTrue(foundNewKey);
  }

  /**
   * Tests that when the user attempts to swap in an invalid key (i.e. ENTER or a key already
   * mapped to a method) for an existing key, that the map of key -> method doesn't change
   */
  @Test
  public void testInvalidSwitch() {
    Button upButton = (Button)(testBinder.lookup("#" + DEFAULT_UP));
    upButton.fire();
    keyPress(KeyCode.A);

    List<Pair<String, String>> keyMethodPairs = testInputter.getKeyMethodPairings();

    for (Pair<String, String> pair : keyMethodPairs) {
      if (pair.getKey().equals(DEFAULT_UP)) {
        assertEquals(UP, pair.getValue());
      }
      else if (pair.getKey().equals(DEFAULT_LEFT)) {
        assertEquals(LEFT, pair.getValue());
      }
    }

  }

  /**
   * This test makes sure that the label on the button which shows which key is mapped to the method
   * at its left updates to show the new key mapped to that method (i.e. the button says 'A'
   * initially, but then says 'B' after the user clicks 'A' and types 'B')
   */
  @Test
  public void buttonLabelChangesOnValidSwitch() {
    Button leftButton = (Button)(testBinder.lookup("#" + DEFAULT_LEFT));
    assertEquals(DEFAULT_LEFT, leftButton.getText());
    leftButton.fire();

    keyPress(KeyCode.Y);
    assertEquals("Y", leftButton.getText());
  }

  /**
   * This test makes sure that the label on the button which shows which key is mapped to the method
   * at its left does not update if the user tries to replace that key with an invalid replacement
   */
  @Test
  public void buttonLabelDoesNotChangeOnInvalidSwitch() {
    Button leftButton = (Button)(testBinder.lookup("#" + DEFAULT_LEFT));
    assertEquals(DEFAULT_LEFT, leftButton.getText());
    leftButton.fire();

    keyPress(KeyCode.W);
    assertEquals(DEFAULT_LEFT, leftButton.getText());
  }

  /**
   * Tests that when the user presses a button to change its key label, the instructions appear to
   * tell the user what to do
   */
  @Test
  public void testInstructionLabelChangesOnButtonPush() {
    assertEquals(WAITING_FOR_UPDATE_TEXT, instructionsLabel.getText());

    Button pauseButton = (Button)(testBinder.lookup("#" + DEFAULT_PAUSE));
    pauseButton.fire();

    assertEquals(UPDATE_LABEL_TEXT + DEFAULT_PAUSE, instructionsLabel.getText());
  }

  /**
   * Tests that when the user presses a button to change its key label and then passes in a valid
   * key, the instruction text disappears (becomes "") because it is no longer necessary to
   * provide the user with instructions
   */
  @Test
  public void testInstructionLabelResetsOnValidKeyPress() {
    Button pauseButton = (Button)(testBinder.lookup("#" + DEFAULT_PAUSE));
    pauseButton.fire();

    assertEquals(UPDATE_LABEL_TEXT + DEFAULT_PAUSE, instructionsLabel.getText());

    keyPress(KeyCode.F);
    assertEquals(WAITING_FOR_UPDATE_TEXT, instructionsLabel.getText());
  }

  /**
   * Tests that when the user presses a button to change its key label and then passes in an invalid
   * key, the instruction text shows an error
   */
  @Test
  public void testInstructionLabelShowsErrorOnInvalidKeyPress() {
    Button pauseButton = (Button)(testBinder.lookup("#" + DEFAULT_PAUSE));
    pauseButton.fire();

    assertEquals(UPDATE_LABEL_TEXT + DEFAULT_PAUSE, instructionsLabel.getText());

    keyPress(KeyCode.ENTER);
    assertEquals(BAD_KEY_TEXT, instructionsLabel.getText());
  }
}