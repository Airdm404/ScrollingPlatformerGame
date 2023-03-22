package api.model.configuration;

import java.io.File;

/**
 * An interface responsible for creating the game configuration
 * It contains methods for obtaining the configured values from the game configuration
 * for use by the rest of the program
 * @author Mike Garay & Alex Lu
 */
public interface IGameConfiguration {
    /**
     * sets the level file for the level
     */
    void setLevelFile();

    /**
     * Obtains the textures path used for the level
     * @return a String representing the textures path
     */
    String getTexturesPath();

    /**
     * Obtains the key inputs path used for the level
     * @return a String representing the key inputs path
     */
    String getKeyInputsPath();

    /**
     * Obtains the auto generator path used for the level
     * @return a String representing the auto generator path
     */
    String getAutoGeneratorPath();

    /**
     * Obtains the player type for the level
     * @return a String representing the player type class
     */
    String getPlayerType();

    /**
     * Obtains the high scores path used for the level
     * @return a String representing the high scores path
     */
    String getHighScoresPath();

    /**
     * Obtains the next config file path for the level sequence
     * @return A String representing the next config file path
     */
    String getNextConfigFilePath();

    /**
     * Obtains the arguments to pass into the scroller for the level
     * @return A String array representing the arguments to pass into the scroller
     */
    String[] getScrollerArgs();

    /**
     * Obtains the level file used for the current level
     * @return A File representing the level file
     */
    File getLevelFile();
}
