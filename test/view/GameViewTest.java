package view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.GameModel;
import model.configuration.*;
import api.model.IGameModel;
import model.Level;
import model.entity.Player;
import api.model.configuration.IGameConfiguration;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import api.view.IGameScene;

/**
 * Tests the GameView class for its ability to switch between scenes and perform various functions
 */
public class GameViewTest extends DukeApplicationTest {

    private GameView view;
    private Stage stage;
    private Scene currentScene;
    private static final String DEFAULT_STYLESHEET = "resources/cssstylesheets/default.css";
    private static final String DARK_STYLESHEET = "resources/cssstylesheets/dark.css";
    private static final String QUIT_BUTTON_ID = "QuitGameCommand";

    private static final String SECRET_CONFIG_PATH = "/secret/masteregg.properties";
    private static final String DEFAULT_CONFIG_PATH = "doodlejump.properties";

    private static final String DEFAULT_TEXTURE_PATH = "doodletextures.properties";
    private static final String ALTERNATE_TEXTURE_PATH = "mariotextures.properties";


  @Override
    public void start(Stage st) {
      view = new GameView();
      stage = st;
      view.start(stage);
      currentScene = stage.getScene();
    }

  /**
   * Checks to make sure that when the user changes the stylesheet, it updates appropriately
   */
  @Test
    public void testChangeStylesheet() {
      assertEquals(DEFAULT_STYLESHEET, currentScene.getStylesheets().get(0));
      view.switchStylesheet("dark");
      assertEquals(DARK_STYLESHEET, currentScene.getStylesheets().get(0));
    }

  /**
   * Checks to make sure that when the user tries to switch the stylesheet in view to a sheet not in
   * the resources/cssstylesheets folder, the stylesheet does not change
   */
  @Test
    public void testChangeStylesheetNotFound() {
      assertEquals(DEFAULT_STYLESHEET,currentScene.getStylesheets().get(0));
      view.switchStylesheet("dart");
      assertEquals(DEFAULT_STYLESHEET, currentScene.getStylesheets().get(0));
    }

  /**
   * Checks to make sure that the text on the start button changes appropriately in the event that
   * there is a language switch in view
   */
  @Test
    public void testChangeLanguage() {
      Button quitButton = (Button)((IGameScene)currentScene).getGameController()
          .lookup("#" + QUIT_BUTTON_ID);

      assertEquals("Quit",  quitButton.getText());
      javafxRun(() -> view.switchLanguage("Espanol"));
      assertEquals("Dejar", quitButton.getText());
    }

  /**
   * Checks to make sure that when we try to switch the Resource Bundle to a bundle that is not in
   * the resources/resourcebundles folder, it does not work
   */
  @Test
    public void testChangeLanguageNotFound() {
      Button startButton = (Button)((IGameScene)currentScene).getGameController()
          .lookup("#" + QUIT_BUTTON_ID);

      assertEquals("Quit",  startButton.getText());
      javafxRun(() -> view.switchLanguage("Klingon"));
      assertEquals("Quit", startButton.getText());
    }

  /**
   * Tests that the start() method opens the GAME scene
   */
  @Test
    public void testStartGame() {
        assertEquals(((IGameScene)currentScene).getSceneId(), "HOME_SCREEN");
        javafxRun(() -> view.start());

        currentScene = stage.getScene();
        assertEquals(((IGameScene)currentScene).getSceneId(), "GAME");
    }

  /**
   * Tests that the selectGameTypeScreen() method opens the GAMEVERSION scene
   */
  @Test
  public void testGameVersion() {
    assertEquals(((IGameScene)currentScene).getSceneId(), "HOME_SCREEN");
    javafxRun(() -> view.selectGameTypeScreen());

    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(), "GAMEVERSION");
  }

  /**
   * Tests that the switchToControlScreen() method opens the CONTROLS scene
   */
  @Test
  public void testSetControls() {
    assertEquals(((IGameScene)currentScene).getSceneId(),"HOME_SCREEN");
    javafxRun(() -> view.switchToControlScreen());

    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(),"CONTROLS");
  }

  /**
   * Tests that the homeScreen() method opens the HOME_SCREEN scene
   */
  @Test
  public void testBackToMenu() {
    assertEquals(((IGameScene)currentScene).getSceneId(), "HOME_SCREEN");
    javafxRun(() -> view.start());

    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(),"GAME");
    javafxRun(() -> view.homeScreen());

    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(), "HOME_SCREEN");
  }

  /**
   * Tests that the switchToSelectCssStylesheetScreen() method opens the SELECT_CSS_STYLESHEET scene
   */
  @Test
  public void testAppearanceScreen() {
    assertEquals(((IGameScene)currentScene).getSceneId(), "HOME_SCREEN");
    javafxRun(() -> view.switchToSelectCssStylesheetScreen());

    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(), "SELECT_CSS_STYLESHEET");
  }

  /**
   * Tests that the switchToSelectLanguageScreen() method opens the SELECT_RESOURCE_BUNDLE scene
   */
  @Test
  public void testLanguageScreen() {
    assertEquals(((IGameScene)currentScene).getSceneId(), "HOME_SCREEN");
    javafxRun(() -> view.switchToSelectLanguageScreen());

    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(),
        "SELECT_RESOURCE_BUNDLE");
  }

  /**
   * Tests that the switchToTextureSwapScreen() method actually switches to the
   * texture swap screen
   */
  @Test
  public void testTextureScreen() {
    assertEquals(((IGameScene)currentScene).getSceneId(), "HOME_SCREEN");

    javafxRun(() -> view.start());
    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(), "GAME");

    javafxRun(() -> view.switchToTextureSwapScreen());
    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(),
        "TEXTURE_SWAP");
  }

  /**
   * Tests that the switchToSelectLanguageScreen() method opens the SELECT_RESOURCE_BUNDLE scene
   */
  @Test
  public void testLeaderboardScreen() {
    assertEquals(((IGameScene)currentScene).getSceneId(), "HOME_SCREEN");
    javafxRun(() -> view.switchToHighScoresScreen());

    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(),
        "HIGHSCORE");
  }

  /**
   * Tests that the back() button returns the user to the HOME_SCREEN scene
   */
  @Test
  public void testBack() {
    assertEquals(((IGameScene)currentScene).getSceneId(), "HOME_SCREEN");
    javafxRun(() -> view.selectGameTypeScreen());

    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(), "GAMEVERSION");
    javafxRun(() -> view.back());

    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(), "HOME_SCREEN");
  }

  /**
   * Tests that the resetLevel() method actually resets the player's location in the level - first
   * captures that location prior to any action, then moves the player and checks to make sure that
   * the player's location is now different. Then, resets the level and makes sure that the
   * player's location has reset to that original position
   */
  @Test
  public void testResetLevel() {
    assertEquals(((IGameScene)currentScene).getSceneId(), "HOME_SCREEN");
    javafxRun(() -> view.start());

    IGameModel model = view.getModel();
    Level level = model.getLevel();
    Player player = level.getPlayerList().get(0);

    double xLeft = player.getHitBox().getXLeft();
    double yTop = player.getHitBox().getYTop();

    level.translateAllEntities(1,1);
    assertNotEquals(xLeft, player.getHitBox().getXLeft());
    assertNotEquals(yTop, player.getHitBox().getYTop());

    javafxRun(() -> view.resetLevel());
    player = level.getPlayerList().get(0);
    assertEquals(xLeft, player.getHitBox().getXLeft());
    assertEquals(yTop, player.getHitBox().getYTop());
  }

  /**
   * Tests that when a secret key is pressed in the home screen, a secret change happens
   */
  @Test
  public void testSecretConfigSwitch() {
    assertEquals(((IGameScene)currentScene).getSceneId(), "HOME_SCREEN");
    assertEquals(DEFAULT_CONFIG_PATH, view.getConfigPath());

    javafxRun(() ->view.keyPressed("T"));
    assertEquals(SECRET_CONFIG_PATH, view.getConfigPath());
  }

  /**
   * Tests that when a secret key is pressed in a scene other than the home screen, a secret change
   * does not happen
   */
  @Test
  public void testSecretConfigSwitchFails() {
    assertEquals(((IGameScene)currentScene).getSceneId(), "HOME_SCREEN");
    assertEquals(DEFAULT_CONFIG_PATH, view.getConfigPath());

    javafxRun(() -> view.switchToSelectCssStylesheetScreen());
    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(), "SELECT_CSS_STYLESHEET");

    javafxRun(() ->view.keyPressed("T"));
    assertEquals(DEFAULT_CONFIG_PATH, view.getConfigPath());
  }

  /**
   * Tests that the switchTextures() method correctly alters the texture file used to
   * generate textures
   */
  @Test
  public void testSwitchTextures() {
    assertEquals(DEFAULT_TEXTURE_PATH, view.getTexturerPath());
    javafxRun(() -> view.switchTextures("mariotextures"));
    assertEquals(ALTERNATE_TEXTURE_PATH, view.getTexturerPath());
  }

  /**
   * Tests that when the player attains a win condition, the level moves to the next one (i.e.
   * stays with currentScene.getSceneId() = GAME and switches the configPath to
   * mariolevel2.properties
   */
  @Test
  public void testSimpleGoal() throws InvalidFileException {
    javafxRun(() -> view.start());
    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(), "GAME");

    IGameConfiguration IGameConfiguration = new GameConfiguration("supermario.properties");
    view.setModel(new GameModel(IGameConfiguration));

    view.getModel().getLevel().setLevelWon(true);
    javafxRun(() -> view.update());

    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(), "GAME");
    assertEquals("mariolevel2.properties", view.getConfigPath());
  }

  /**
   * Tests that when the player attains a win condition, and the level is the final one in the
   * chain, the correct thing happens (i.e. view stays with currentScene.getSceneId() = GAME
   * and keeps the configPath to mariolevel2.properties)
   */
  @Test
  public void testFinishGoal() throws InvalidFileException {
    javafxRun(() -> view.start());
    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(), "GAME");

    IGameConfiguration IGameConfiguration = new GameConfiguration("supermario.properties");
    view.setModel(new GameModel(IGameConfiguration));

    view.getModel().getLevel().setLevelWon(true);
    javafxRun(() -> view.update());

    // Win a second time in level 2
    view.getModel().getLevel().setLevelWon(true);
    javafxRun(() -> view.update());

    // Win a third time in level 3 - this time don't change levels since it's the last in the chain
    view.getModel().getLevel().setLevelWon(true);
    javafxRun(() -> view.update());

    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(), "GAME");
    assertEquals("mariolevel3.properties", view.getConfigPath());
  }

  /**
   * Tests that when the player attains a win condition, and the level is the final one in the
   * chain, the correct thing happens (i.e. view stays with currentScene.getSceneId() = GAME
   * and keeps the configPath to mariolevel2.properties)
   */
  @Test
  public void testInvalidGoal() throws InvalidFileException {
    javafxRun(() -> view.start());
    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(), "GAME");

    IGameConfiguration IGameConfiguration = new GameConfiguration("marioleveltest.properties");
    ILevelLoader ILevelLoader = new LevelLoader(IGameConfiguration.getLevelFile(), new EntityFactory());
    view.getModel().getLevel().setOrResetLevel(ILevelLoader);

    view.getModel().getLevel().setLevelWon(true);
    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(), "GAME");
    javafxRun(() -> view.update());

    currentScene = stage.getScene();
    assertEquals(((IGameScene)currentScene).getSceneId(), "GAMEVERSION");
  }

}