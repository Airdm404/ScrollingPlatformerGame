package api.view;

import controller.GameController;
import javafx.event.Event;
import javafx.stage.Stage;
import api.model.IGameModel;
import view.GameScene;

import java.util.List;

/**
 * The GameView interface
 */
public interface IGameView {

    /**
     * Begins our view, (i.e. builds the scene and group objects responsible for showing our project)
     *
     * @param sta the main stage of the program
     */
    void start(Stage sta);

    /**
     * Builds the scenes
     */
    void buildScenes();

    /**
     * Builds the list of game scenes used in this view
     */
    void buildScenesList();

    /**
     * Resets lastScene and currentScene to their defaults
     */
    void resetScenes();

    /**
     * Prepares the model that the view will update with an animation timer and display
     */
    void buildModel();

    /**
     * Updates the view
     */
    void update();

    /**
     * Updates the game in a situation where normal gameplay is occurring (i.e. the player in the
     * "play game" screen and has neither lost nor won and is not saving the game or their score)
     */
    void normalUpdate();

    /**
     * Handles the situation where the level has been lost
     */
    void levelLost();

    /**
     * Handles the situation where the level has been one
     */
    void levelWon();

    /**
     * Handles the situation where the user has just completed the final level in a finite level chain
     * (i.e. finished level 3, 3 is the last level
     */
    void finishedFinalLevel();

    /**
     * Attempts to load the next level, and if an error occurs, sends the user back to the menu
     */
    void loadNextLevel(String nextLevel);

    /**
     * gets score from model
     */
    int getScore();

    /**
     * Builds the animation functionality that will run the program
     */
    void prepareAnimation();

    /**
     * Builds listeners for all controllers
     */
    void listenOnControllers();

    /**
     * Handles an event in the controller by screening it and then passing it to performReflection
     *
     * @param cont  the GameController that fired the event
     * @param event the Event that has occured
     */
    void handleControllerEvent(GameController cont, Event event);

    /**
     * Handles the event of a key press
     *
     * @param key the key that has been pressed
     */
    void keyPressed(String key);

    /**
     * Handles the event of a key release
     *
     * @param key the key that has been pressed
     */
    void keyReleased(String key);

    /**
     * Reflectively calls the method as specified in reflectionArgs
     *
     * @param reflectionArgs - either a 1 or 2 String List containing either a method with no
     *                       parameters or a method with one String parameter
     */
    void performReflection(List<String> reflectionArgs);

    /**
     * Switches the stylesheets of all scenes to the stylesheet referenced by name
     *
     * @param name the name of the stylesheet (i.e. dark/light)
     */
    void switchStylesheet(String name);

    /**
     * Updates the language bundles that writes to all of the buttons
     *
     * @param name the name of the resourcebundle
     */
    void switchLanguage(String name);

    /**
     * Switches the scene to the viewName indexed by view
     *
     * @param scene the scene to become the new scene
     */
    void setScene(GameScene scene);

    /**
     * Switches to the menu screen
     */
    void switchToHomeScreen();

    /**
     * Switches to Css Stylesheet Selection Screen
     */
    void switchToSelectCssStylesheetScreen();

    /**
     * Switches to Select Language Screen
     */
    void switchToSelectLanguageScreen();

    /**
     * Switches to Select Game Type Screen
     */
    void selectGameTypeScreen();

    /**
     * Switches to a Texture Selection Screen
     */
    void switchToTextureSwapScreen();

    /**
     * Launches a save box to save the current state of the level in a csv file
     */
    void saveGame();

    /**
     * Ends Game
     */
    void endGame();

    /**
     * select game type
     */
    void switchGame(String type);

    /**
     * Changes the texture file determining textures to the one indexed by path
     *
     * @param texturePath the String path leading to the textures
     */
    void switchTextures(String texturePath);

    /**
     * Switches to the leaderboard screen
     */
    void switchToHighScoresScreen();

    /**
     * Switches to the controller screen
     */
    void switchToControlScreen();

    /**
     * Resets the current level in the model by calling model.resetLevel()
     */
    void resetLevel();

    /**
     * Switches back to the last view
     */
    void back();

    /**
     * Starts the game
     */
    void start();

    /**
     * Returns to the home screen
     */
    void homeScreen();

    /**
     * For testing - return the GameModel
     *
     * @return model
     */
    IGameModel getModel();

    /**
     * For testing - set model = m
     *
     * @param m the model to replace GameView's current model
     */
    void setModel(IGameModel m);

    /**
     * For testing - return the config path
     *
     * @return configPath
     */
    String getConfigPath();

    /**
     * For testing - return the String filepath that's being used to generate textures
     *
     * @return texturer.getPath()
     */
    String getTexturerPath();
}
