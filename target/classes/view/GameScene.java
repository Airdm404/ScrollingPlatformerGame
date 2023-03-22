package view;

import controller.BuilderInstantiationException;
import controller.FolderParser;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import controller.GameController;
import javafx.scene.text.Text;
import api.view.IGameScene;

/**
 * Represents one scene in our GameView - holds data and methods beyond that of a typical Scene
 * object like a GameController variable and methods to update the text of all of its children node
 *
 * @author Alex Lu & Edem Ahorlu
 */
public class GameScene extends Scene implements IGameScene {

  private final Group root;
  private final String sceneId;
  protected final double WIDTH;
  protected final double HEIGHT;
  private GameController controller;
  private Text errorLabel;
  protected ResourceBundle bundle;
  private static final String CONTROLLER = "controller";
  protected static final String BACKGROUND = "background";
  private static final String DEFAULT_CSS_FILEPATH = "resources/cssstylesheets/default.css";
  private static final String STYLESHEET_PATH = "resources/cssstylesheets/";
  protected static final String LANGUAGE_FOLDERPATH_LONG = "./src/resources/resourcebundles";
  protected static final String LANGUAGE_FOLDERPATH = "resources/resourcebundles.";
  protected static final String PROPERTIES_EXTENSION = ".properties";
  private static final String STYLESHEET_PATH_LONG = "./src/resources/cssstylesheets";
  private static final String CSS_EXTENSION = ".css";
  private static final String DEFAULT_LANGUAGE = "English";


  /**
   * Constructs a GameScene object
   *
   * @param myRoot the Group object that will become the scene's root node
   * @param id     a String id for the scene to identify itself with
   * @param width  the width of the visual component of the scene
   * @param height the height of the visual component of the scene
   */
  public GameScene(Group myRoot, String id, double width, double height) {
    super(myRoot, width, height);
    root = myRoot;
    WIDTH = width;
    HEIGHT = height;
    sceneId = id;

    bundle = ResourceBundle.getBundle(LANGUAGE_FOLDERPATH + DEFAULT_LANGUAGE);
    setGameController(new GameController());

    makeBackground();
    makeErrorText();

    getStylesheets().add(DEFAULT_CSS_FILEPATH);
  }

  /**
   * Sets the background Rectangle for the game
   */
  @Override
  public void makeBackground() {
    Rectangle background = new Rectangle(WIDTH, HEIGHT, Color.WHITE);
    background.setId(BACKGROUND);
    root.getChildren().add(background);
    background.toBack();
  }

  /**
   * Makes the error text label that will appear at the top of the screen
   */
  @Override
  public void makeErrorText() {
    errorLabel = new Text();
    errorLabel.setText("");
    errorLabel.setId("scoreStyle");
    errorLabel.getStyleClass().add("scoreStyle");
    root.getChildren().add(errorLabel);

    errorLabel.setLayoutX(WIDTH / 2 - errorLabel.getLayoutBounds().getWidth() / 2);
    errorLabel.setLayoutY(HEIGHT / 10);
  }

  /**
   * Updates the error text label that will appear at the top of the screen
   *
   * @param newText the new text to fill that label
   */
  @Override
  public void updateErrorText(String newText) {
    errorLabel.setVisible(true);

    errorLabel.setText(newText);
    errorLabel.setLayoutX(WIDTH / 2 - errorLabel.getLayoutBounds().getWidth() / 2);
  }

  /**
   * Hides the error text from view
   */
  @Override
  public void hideErrorText() {
    errorLabel.setVisible(false);
  }

  /**
   * Returns the text in the error label
   *
   * @return the text of errorLabel
   */
  @Override
  public String getErrorText() {
    return errorLabel.getText();
  }


  /**
   * Sets the controller associated with this particular scene
   *
   * @param cont the controller to serve as the game scene's controller
   */
  @Override
  public void setGameController(GameController cont) {
    controller = cont;
    controller.setId("#" + CONTROLLER);
    root.getChildren().add(controller);
  }

  /**
   * Adds buttons from a file to the controller
   *
   * @param file the file containing the buttons to be included
   */
  @Override
  public void addButtonsToControllerFromFile(String file) {
    try {
      controller.addButtonsFromFile(file);
    } catch (BuilderInstantiationException bie) {
      updateErrorText(getValueFromBundle(bie.getMessage()));
    }
  }

  /**
   * Builds an option selector for the controller associated with the scene
   *
   * @param folder    the folder containing the list of options (i.e. "./ooga/resources/buttons")
   * @param extension the allowed extension for each option (i.e. include if ".jpeg")
   * @param method    the method to be called by the OptionsSelector
   */
  @Override
  public void buildOptionsSelectorFromFolderForController(String folder, String extension,
      String method) {
    try {
      controller.addOptionsSelectorFromFolder(folder, extension, method);
    } catch (BuilderInstantiationException bie) {
      updateErrorText(getValueFromBundle(bie.getMessage()));
    }
  }

  /**
   * Builds an OptionsSelector object from a list
   *
   * @param choices the list of choices from which the user can select
   * @param method  the String representation of the method to be called
   */
  public void buildOptionsSelectorFromListForController(List<String> choices, String method) {
    try {
      controller.buildOptionsSelector(choices, method);
    } catch (BuilderInstantiationException bie) {
      updateErrorText(getValueFromBundle(bie.getMessage()));
    }
  }

  /**
   * Adds a node element to the root node of the GameScene (i.e. button, controller, etc.)
   *
   * @param toBeAdded the Node to be inserted
   */
  @Override
  public void addElementToRoot(Node toBeAdded) {
    root.getChildren().add(toBeAdded);
  }

  /**
   * Returns the controller associated with this GameScene
   *
   * @return controller
   */
  @Override
  public GameController getGameController() {
    return controller;
  }

  /**
   * Removes a node element from the root node of the GameScene (i.e. button, controller, etc.)
   *
   * @param toBeRemoved the Node to be removed
   */
  @Override
  public void removeElementFromRoot(Node toBeRemoved) {
    root.getChildren().remove(toBeRemoved);
  }

  /**
   * Looks up an element in the GameScene and returns it if found, otherwise throws a
   * NullPointerException
   *
   * @param id the id to be looked up
   * @return the node if it exists in the GameScene
   */
  @Override
  public Node lookupElementInRoot(String id) {
    Node element = root.lookup("#" + id);
    if (element != null) {
      return element;
    }
    throw new NullPointerException("Node not found!");
  }

  /**
   * Updates the resource bundle displaying text for each scene
   *
   * @param name the name of the resource bundle
   */
  @Override
  public void updateResources(String name) {
    FolderParser parser = new FolderParser(LANGUAGE_FOLDERPATH_LONG, PROPERTIES_EXTENSION);
    if (parser.getFilenamesFromFolder().contains(name)) {
      controller.updateResources(name);
      bundle = ResourceBundle.getBundle(LANGUAGE_FOLDERPATH + name);
    } else {
      updateErrorText(getValueFromBundle("UPDATE_RESOURCES_ERROR"));
    }
  }

  /**
   * Updates the stylesheet of this GameScene
   *
   * @param name the name of the new stylesheet
   */
  @Override
  public void updateStylesheet(String name) {
    FolderParser parser = new FolderParser(STYLESHEET_PATH_LONG, CSS_EXTENSION);
    if (parser.getFilenamesFromFolder().contains(name)) {
      getStylesheets().clear();
      getStylesheets().add(STYLESHEET_PATH + name + CSS_EXTENSION);
    } else {
      updateErrorText(getValueFromBundle("UPDATE_STYLE_SHEET_ERROR"));
    }
  }

  /**
   * Returns the scene id
   *
   * @return sceneId
   */
  @Override
  public String getSceneId() {
    return sceneId;
  }

  /**
   * Returns the value corresponding to key in the resouce bundle
   *
   * @param key the key in resourceBundle
   * @return the value in resourceBundle
   */
  @Override
  public String getValueFromBundle(String key) {
    String value = bundle.getString(key);
    if (value != null) {
      return value;
    }
    return "";
  }
}
