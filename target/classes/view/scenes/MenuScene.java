package view.scenes;

import controller.BuilderInstantiationException;
import controller.ImageBuilder;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import view.GameScene;
import api.view.scenes.IMenuScene;


/**
 * Builds the scene that has all the buttons on the splashscreen
 *
 * @author Alex Lu & Edem Ahorlu
 */

public class MenuScene extends GameScene implements IMenuScene {

  private static final String ID = "HOME_SCREEN";
  private static final String BUTTON_FOLDERPATH_SLASH = "./src/resources/buttons/";
  private static final String HOME_IMAGES_PATH = "./src/resources/images/home_screenimages.xml";

  /**
   * Constructs a new MenuScene object
   *
   * @param myRoot a Group node that will be the scene's root
   * @param width  the width of the visible component of the scene
   * @param height the height of the visible component of the scene
   */
  public MenuScene(Group myRoot, double width, double height) {
    super(myRoot, ID, width, height);

    addButtonsToControllerFromFile(
        BUTTON_FOLDERPATH_SLASH + ID.toLowerCase() + "buttons.xml");

    addImagesToHomeScreen();
  }

  /**
   * Adds images to the home screen
   */
  @Override
  public void addImagesToHomeScreen() {
    Node background = lookupElementInRoot("background");
    removeElementFromRoot(background);

    try {
      ImageBuilder image = new ImageBuilder(WIDTH, HEIGHT,
          HOME_IMAGES_PATH);

      for (ImageView view : image.getFoundImages()) {
        addElementToRoot(view);
        view.toBack();
      }
    } catch (BuilderInstantiationException bie) {
      updateErrorText(getValueFromBundle(bie.getMessage()));
    }
  }
}
