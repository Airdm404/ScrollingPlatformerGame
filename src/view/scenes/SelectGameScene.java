package view.scenes;

import java.util.Arrays;
import javafx.scene.Group;
import view.GameScene;

/**
 * builds the scene that allows the user to select the game versions available
 *
 * @author Alex Lu & Edem Ahorlu
 */


public class SelectGameScene extends GameScene {

  private static final String ID = "GAMEVERSION";
  private static final String[] GAME_TYPES = {"Super Mario", "Flappy Bird", "Doodle Jump",
      "Mario Infinity", "Doodle Jump 2"};
  private static final String BUTTON_FOLDERPATH_SLASH = "./src/resources/buttons/";

  /**
   * Constructs a new SelectGameScene object
   *
   * @param myRoot a Group node that will be the scene's root
   * @param width  the width of the visible component of the scene
   * @param height the height of the visible component of the scene
   */
  public SelectGameScene(Group myRoot, double width, double height) {
    super(myRoot, ID, width, height);

    addButtonsToControllerFromFile(
        BUTTON_FOLDERPATH_SLASH + ID.toLowerCase() + "buttons.xml");

    buildOptionsSelectorFromListForController(
        Arrays.asList(GAME_TYPES), "switchGame");
  }
}
