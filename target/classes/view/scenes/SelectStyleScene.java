package view.scenes;

import javafx.scene.Group;
import view.GameScene;

/**
 * builds the scene that allows the user select different appearances(default, dark, light)
 *
 * @author Edem Ahorlu
 */


public class SelectStyleScene extends GameScene {
  private static final String CSS_FOLDERPATH = "./src/resources/cssstylesheets";
  private static final String ID = "SELECT_CSS_STYLESHEET";
  private static final String CSS_EXTENSION = ".css";
  private static final String BUTTON_FOLDERPATH_SLASH = "./src/resources/buttons/";

  /**
   * Constructs a new SelectStyleScene object
   *
   * @param myRoot a Group node that will be the scene's root
   * @param width  the width of the visible component of the scene
   * @param height the height of the visible component of the scene
   */
  public SelectStyleScene(Group myRoot, double width, double height) {
    super(myRoot, ID, width, height);

    addButtonsToControllerFromFile(
        BUTTON_FOLDERPATH_SLASH + ID.toLowerCase() + "buttons.xml");

    buildOptionsSelectorFromFolderForController(CSS_FOLDERPATH, CSS_EXTENSION,
        "switchStylesheet");
  }
}
