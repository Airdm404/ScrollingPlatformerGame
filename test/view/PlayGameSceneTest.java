package view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Level;
import model.configuration.*;
import api.model.configuration.IGameConfiguration;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import view.scenes.PlayGameScene;


/**
 * Tests the PlayGameScene class for its ability to save the state of the game into a csv file
 */
public class PlayGameSceneTest extends DukeApplicationTest {

  private static final double WIDTH = 800;
  private static final double HEIGHT = 800;

  private static final String SAVE_INSTRUCTIONS = "Please input a filename and press ENTER";
  private static final String SAVE_ERROR = "Please input a valid filename!";

  private static final String CSV_EXTENSION = ".csv";
  private static final String SAVE_FILEPATH = "data/saves/";
  private static final String TEXTFIELD_ID = "TEXTFIELD";

  private PlayGameScene scene;
  private Level level;
  private TextField field;

  @Override
  public void start(Stage st) throws InvalidFileException {
    scene = new PlayGameScene(new Group(), WIDTH, HEIGHT);

    IGameConfiguration gameConfiguration = new GameConfiguration("oneBlock.properties");
    ILevelLoader ILevelLoader = new LevelLoader(gameConfiguration.getLevelFile(), new EntityFactory());
    level = new Level(ILevelLoader);

    field = (TextField)scene.lookup("#" + TEXTFIELD_ID);
  }

  /**
   * Tests that when the user tries to save the game, the correct instructions appear
   */
  @Test
  public void testSaveLabelAppears() {
    scene.launchSave(level);
    assertEquals(SAVE_INSTRUCTIONS, scene.getErrorText());
  }

  /**
   * Simulates a key press in a textfield
   * @param field the textfield on whom the action will be simulated
   * @param key the KeyCode representing the press
   */
  private void pressTextfield(TextField field, KeyCode key) {
    javafxRun(() -> field.getOnKeyPressed().handle(new KeyEvent(KeyEvent.KEY_PRESSED, key.getChar(),
        key.getName(), key, false, false, false, false)));
  }

  /**
   * Tests that when the user inputs an invalid file name into the field, an error message appears
   */
  @Test
  public void testSaveErrorAppears() {
    scene.launchSave(level);
    assertEquals(SAVE_INSTRUCTIONS, scene.getErrorText());

    field.setText(".");
    pressTextfield(field, KeyCode.ENTER);

    assertEquals(SAVE_ERROR, scene.getErrorText());
  }

  /**
   * Tests that when the user successfully saves, the instructions/error label disappears
   */
  @Test
  public void testLabelDisappearsOnSuccess() {
    scene.launchSave(level);
    assertEquals(SAVE_INSTRUCTIONS, scene.getErrorText());

    field.setText("a");
    pressTextfield(field, KeyCode.ENTER);

    assertEquals("", scene.getErrorText());
  }

  /**
   * Tests to make sure that a new file enters the "saves" folder when the user saves the level
   */
  @Test
  public void checkFileIsSaved() throws InvalidFileException {
    scene.launchSave(level);
    assertEquals(SAVE_INSTRUCTIONS, scene.getErrorText());

    field.setText("b");
    pressTextfield(field, KeyCode.ENTER);

    ILevelLoader ILevelLoader = new LevelLoader(
        new File( SAVE_FILEPATH + "b" + CSV_EXTENSION), new EntityFactory());
    assertDoesNotThrow(() -> new Level(ILevelLoader));

  }
}