package view.scenes;

import controller.KeyBinder;
import javafx.scene.Group;
import view.GameScene;
import api.view.scenes.IControlsScene;

/**
 * Builds a scene that allows the user set different keys to control characters in the game
 *
 * @author Alex Lu
 */


public class ControlsScene extends GameScene implements IControlsScene {

  private static final String ID = "CONTROLS";
  private static final String BUTTON_FOLDERPATH_SLASH = "./src/resources/buttons/";
  private KeyBinder key;

  /**
   * Constructs a new ControlsScene object
   *
   * @param myRoot a Group node that will be the scene's root
   * @param width  the width of the visible component of the scene
   * @param height the height of the visible component of the scene
   */
  public ControlsScene(Group myRoot, double width, double height) {
    super(myRoot, ID, width, height);

    addButtonsToControllerFromFile(
        BUTTON_FOLDERPATH_SLASH + ID.toLowerCase() + "buttons.xml");

    addKeyBinders();
  }

  /**
   * Adds a key binder to this scene
   */
  @Override
  public void addKeyBinders() {
    key = new KeyBinder(bundle);
    key.setId(key.getClass().getSimpleName());
    addElementToRoot(key);
  }

  /**
   * Updates the resource bundle displaying text for each scene
   *
   * @param name the name of the resource bundle
   */
  @Override
  public void updateResources(String name) {
    super.updateResources(name);
    key.setBundle(bundle);
  }
}
