package view;

import controller.GameController;
import controller.KeyBinder;
import controller.KeyInputter;
import controller.KeyInputterMissingMethodException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.GameModel;
import api.model.IGameModel;
import model.autogenerator.GenerationException;
import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import api.model.entity.IEntity;
import view.scenes.*;
import api.view.IGameScene;
import api.view.IGameView;

/**
 * The view for our game - handles scene changes and updates to graphical appearance (i.e. language
 * and stylesheet) and also updates the model
 *
 * @author Alex Lu & Edem Ahorlu
 */
public class GameView extends Application implements IGameView {

  private PlayGameScene playGameScene;
  private MenuScene menuScene;
  private SelectLanguageScene selectLanguageScene;
  private SelectStyleScene selectStyleScene;
  private ControlsScene controlsScene;
  private SelectGameScene selectGameScene;
  private List<IGameScene> IGameScenes;
  private HighScoreScene displayHighScore;
  private TextureSwapScene textureSwapScene;


  private GameScene currentScene;
  private GameScene lastScene;

  private static final double WIDTH = 800;
  private static final double HEIGHT = 800;
  private static final double ANIMATION_SPEED = 1 / 60.0;
  private static final String PROPERTIES_EXTENSION = ".properties";
  private static final String TEXTURES = "textures";
  private static final String SECRET_CONFIG_PATH = "/secret/masteregg.properties";
  private static final String SCORE_LABEL = "ScoreLabel";

  private String configPath = "doodlejump.properties";
  private IGameModel model;
  private Stage stage;
  private Timeline animation;
  private KeyInputter inputter;
  private Texturer texturer;

  /**
   * Launches the application
   *
   * @param args any arguments to be passed in
   */
  public static void beginOoga(String[] args) {
    Application.launch(args);
  }

  /**
   * Begins our view, (i.e. builds the scene and group objects responsible for showing our project)
   *
   * @param sta the main stage of the program
   */
  @Override
  public void start(Stage sta) {
    stage = sta;

    buildScenes();
    buildScenesList();
    listenOnControllers();

    resetScenes();
    configPath = "doodlejump.properties";
    prepareAnimation();
    buildModel();
    resetScenes();

    stage.setScene(menuScene);
    stage.setResizable(false);
    stage.show();
  }

  /**
   * Builds the scenes
   */
  @Override
  public void buildScenes() {
    playGameScene = new PlayGameScene(new Group(), WIDTH, HEIGHT);
    menuScene = new MenuScene(new Group(), WIDTH, HEIGHT);
    selectLanguageScene =
        new SelectLanguageScene(new Group(), WIDTH, HEIGHT);
    selectStyleScene = new SelectStyleScene(new Group(), WIDTH, HEIGHT);
    controlsScene = new ControlsScene(new Group(), WIDTH, HEIGHT);
    selectGameScene = new SelectGameScene(new Group(), WIDTH, HEIGHT);
    displayHighScore = new HighScoreScene(new Group(), WIDTH, HEIGHT);
    textureSwapScene = new TextureSwapScene(new Group(), WIDTH, HEIGHT);
  }

  /**
   * Builds the list of game scenes used in this view
   */
  @Override
  public void buildScenesList() {
    IGameScenes = new ArrayList<>();
    IGameScenes.add(menuScene);
    IGameScenes.add(playGameScene);
    IGameScenes.add(controlsScene);
    IGameScenes.add(selectLanguageScene);
    IGameScenes.add(selectStyleScene);
    IGameScenes.add(selectGameScene);
    IGameScenes.add(displayHighScore);
    IGameScenes.add(textureSwapScene);
  }

  /**
   * Resets lastScene and currentScene to their defaults
   */
  @Override
  public void resetScenes() {
    lastScene = menuScene;
    currentScene = menuScene;
  }

  /**
   * Prepares the model that the view will update with an animation timer and display
   */
  @Override
  public void buildModel() {
    try {
      model = new GameModel(new GameConfiguration(configPath));
      inputter = new KeyInputter(model);
      texturer = new Texturer(WIDTH, HEIGHT, model.getTexturesPath(),
          (Group) playGameScene.lookup("#" + TEXTURES));
      start();
    } catch (InvalidFileException | NullPointerException | GenerationException ex) {
      currentScene.updateErrorText(currentScene.getValueFromBundle("BUILD_MODEL_ERROR"));
    }
  }

  /**
   * Updates the view
   */
  @Override
  public void update() {
    if (!currentScene.equals(playGameScene)) {
      return;
    } else if (model.getLevel().isLevelLost()) {
      levelLost();
    } else if (model.getLevel().isLevelWon()) {
      levelWon();
    } else if (!model.getLevel().isSaving()) {
      normalUpdate();
    }
  }

  /**
   * Updates the game in a situation where normal gameplay is occurring (i.e. the player in the
   * "play game" screen and has neither lost nor won and is not saving the game or their score)
   */
  @Override
  public void normalUpdate() {
    model.updateGame();
    List<IEntity> entityList = model.getAllEntitiesInLevel();
    texturer.updateTextures(entityList, 15, 15);
    playGameScene.updateScoreText(currentScene.getValueFromBundle(SCORE_LABEL)
        + ": " + (getScore()));
  }

  /**
   * Handles the situation where the level has been lost
   */
  @Override
  public void levelLost() {
    playGameScene.inputScore(model.getHighScoresPath(), model.getLevel());
    model.getLevel().setLevelLost(false);
  }

  /**
   * Handles the situation where the level has been one
   */
  @Override
  public void levelWon() {
    String nextLevel = model.getNextConfigFilePath();

    if (nextLevel != null && nextLevel.equals("Goal")) {
      finishedFinalLevel();
    } else {
      loadNextLevel(nextLevel);
    }
    model.getLevel().setLevelWon(false);
  }

  /**
   * Handles the situation where the user has just completed the final level in a finite level chain
   * (i.e. finished level 3, 3 is the last level
   */
  @Override
  public void finishedFinalLevel() {
    playGameScene.inputScore(model.getHighScoresPath(), model.getLevel());
  }

  /**
   * Attempts to load the next level, and if an error occurs, sends the user back to the menu
   */
  @Override
  public void loadNextLevel(String nextLevel) {
    configPath = nextLevel;
    buildModel();
    String badConfig = currentScene.getValueFromBundle("BUILD_MODEL_ERROR");

    if (currentScene.getErrorText().equals(badConfig)) {
      setScene(selectGameScene);
      selectGameScene.updateErrorText(badConfig);
    }
  }

  /**
   * gets score from model
   */
  @Override
  public int getScore() {
    return model.getScore();
  }


  /**
   * Builds the animation functionality that will run the program
   */
  @Override
  public void prepareAnimation() {
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame frame = new KeyFrame(Duration.seconds(ANIMATION_SPEED), e -> update());
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  /**
   * Builds listeners for all controllers
   */
  @Override
  public void listenOnControllers() {
    for (IGameScene scene : IGameScenes) {
      GameController cont = scene.getGameController();
      cont.addEventHandler(EventType.ROOT, event -> handleControllerEvent(cont, event));
    }
  }

  /**
   * Handles an event in the controller by screening it and then passing it to performReflection
   *
   * @param cont  the GameController that fired the event
   * @param event the Event that has occured
   */
  @Override
  public void handleControllerEvent(GameController cont, Event event) {
    if (event.getEventType().getName().equals("controller")) {
      List<String> reflectionArgs = new ArrayList<>();
      reflectionArgs.addAll(cont.getBuffer());
      performReflection(reflectionArgs);
    }
  }

  /**
   * Handles the event of a key press
   *
   * @param key the key that has been pressed
   */
  @Override
  public void keyPressed(String key) {
    try {
      inputter.keyPressed(key);

      if (currentScene.equals(menuScene) && key.equals("T")) {
        configPath = SECRET_CONFIG_PATH;
        buildModel();
      } else if (!model.getLevel().isSaving()) {
        currentScene.updateErrorText("");
      }
    } catch (KeyInputterMissingMethodException kimie) {
      String message = currentScene.getValueFromBundle(kimie.getMessage());
      currentScene.updateErrorText(message);
    }
  }

  /**
   * Handles the event of a key release
   *
   * @param key the key that has been pressed
   */
  @Override
  public void keyReleased(String key) {
    try {
      inputter.keyReleased(key);
    } catch (KeyInputterMissingMethodException kimie) {
      String message = currentScene.getValueFromBundle(kimie.getMessage());
      currentScene.updateErrorText(message);
    }
  }

  /**
   * Reflectively calls the method as specified in reflectionArgs
   *
   * @param reflectionArgs - either a 1 or 2 String List containing either a method with no
   *                       parameters or a method with one String parameter
   */
  @Override
  public void performReflection(List<String> reflectionArgs) {
    try {
      if (reflectionArgs.size() == 1) {
        Method method = this.getClass().getDeclaredMethod(reflectionArgs.get(0));
        method.invoke(this);
      } else if (reflectionArgs.size() == 2) {
        Method method = this.getClass().getDeclaredMethod(reflectionArgs.get(0), String.class);
        method.invoke(this, reflectionArgs.get(1));
      }
    } catch (Exception e) {
      // Do nothing
    }
  }

  /**
   * Switches the stylesheets of all scenes to the stylesheet referenced by name
   *
   * @param name the name of the stylesheet (i.e. dark/light)
   */
  @Override
  public void switchStylesheet(String name) {
    for (IGameScene scene : IGameScenes) {
      scene.updateStylesheet(name);
    }
  }

  /**
   * Updates the language bundles that writes to all of the buttons
   *
   * @param name the name of the resourcebundle
   */
  @Override
  public void switchLanguage(String name) {
    for (IGameScene scene : IGameScenes) {
      scene.updateResources(name);
    }
  }

  /**
   * Switches the scene to the viewName indexed by view
   *
   * @param scene the scene to become the new scene
   */
  @Override
  public void setScene(GameScene scene) {
    if (!model.getLevel().isSaving()) {
      lastScene = currentScene;
      lastScene.updateErrorText("");

      stage.setScene(scene);
      currentScene = scene;
    }
  }

  /**
   * Switches to the menu screen
   */
  @Override
  public void switchToHomeScreen() {
    setScene(menuScene);
  }

  /**
   * Switches to Css Stylesheet Selection Screen
   */
  @Override
  public void switchToSelectCssStylesheetScreen() {
    setScene(selectStyleScene);
  }

  /**
   * Switches to Select Language Screen
   */
  @Override
  public void switchToSelectLanguageScreen() {
    setScene(selectLanguageScene);
  }

  /**
   * Switches to Select Game Type Screen
   */
  @Override
  public void selectGameTypeScreen() {
    setScene(selectGameScene);
  }

  /**
   * Switches to a Texture Selection Screen
   */
  @Override
  public void switchToTextureSwapScreen() {
    setScene(textureSwapScene);
  }

  /**
   * Launches a save box to save the current state of the level in a csv file
   */
  @Override
  public void saveGame() {
    playGameScene.launchSave(model.getLevel());
  }

  /**
   * Ends Game
   */
  @Override
  public void endGame() {
    animation.stop();
    stage.close();
  }

  /**
   * select game type
   */
  @Override
  public void switchGame(String type) {
    configPath = type.toLowerCase().replaceAll(" ", "") + PROPERTIES_EXTENSION;
    buildModel();
  }

  /**
   * Changes the texture file determining textures to the one indexed by path
   *
   * @param texturePath the String path leading to the textures
   */
  @Override
  public void switchTextures(String texturePath) {
    texturer = new Texturer(WIDTH, HEIGHT, (texturePath + PROPERTIES_EXTENSION),
        (Group) playGameScene.lookup("#" + TEXTURES));
  }

  /**
   * Switches to the leaderboard screen
   */
  @Override
  public void switchToHighScoresScreen() {
    setScene(displayHighScore);
    displayHighScore.updateLeaderboards();
  }

  /**
   * Switches to the controller screen
   */
  @Override
  public void switchToControlScreen() {
    KeyBinder binder = (KeyBinder) controlsScene.lookupElementInRoot(
        "KeyBinder");
    binder.updateKeyInputScreen(inputter);
    setScene(controlsScene);
  }

  /**
   * Resets the current level in the model by calling model.resetLevel()
   */
  @Override
  public void resetLevel() {
    try {
      model.resetLevel();
    } catch (InvalidFileException ife) {
      currentScene.updateErrorText(currentScene.getValueFromBundle("RESET_LEVEL_ERROR"));
    }
  }

  /**
   * Switches back to the last view
   */
  @Override
  public void back() {
    setScene(lastScene);
  }

  /**
   * Starts the game
   */
  @Override
  public void start() {
    setScene(playGameScene);
  }

  /**
   * Returns to the home screen
   */
  @Override
  public void homeScreen() {
    setScene(menuScene);
  }

  /**
   * For testing - return the GameModel
   *
   * @return model
   */
  @Override
  public IGameModel getModel() {
    return model;
  }

  /**
   * For testing - set model = m
   *
   * @param m the model to replace GameView's current model
   */
  @Override
  public void setModel(IGameModel m) {
    model = m;
  }

  /**
   * For testing - return the config path
   *
   * @return configPath
   */
  @Override
  public String getConfigPath() {
    return configPath;
  }

  /**
   * For testing - return the String filepath that's being used to generate textures
   *
   * @return texturer.getPath()
   */
  @Override
  public String getTexturerPath() {
    return texturer.getPath();
  }

}
