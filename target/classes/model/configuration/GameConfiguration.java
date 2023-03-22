package model.configuration;

import api.model.configuration.IGameConfiguration;

import java.io.File;
import java.util.Properties;
/**
 * An class responsible for creating the game configuration
 * It contains methods for obtaining the configured values from the game configuration
 * for use by the rest of the program
 * @author Mike Garay and Alex Lu
 */
public class GameConfiguration implements IGameConfiguration {
    public static final String LEVEL_KEY = "level";
    private static final String TEXTURES_KEY = "textures";
    private static final String KEYINPUTS_KEY = "keys";
    private static final String SCROLLER_KEY = "scroller";
    private static final String AUTO_KEY = "autofile";
    private static final String PLAYER_KEY = "player";
    private static final String NEXT_FILE_KEY = "nextfile";
    private static final String HIGH_SCORES_KEY = "leaderboard";
    private static final String DEFAULT_LEVEL_FILEPATH = "./data/";
    private static final String ROOT_SOURCE_INDICATOR = "./";
    private final Properties properties;
    private File levelFile;

    /**
     * Constructs a game configuration given a resource file name
     * It uses this class to obtain the class loader for the input stream
     * As well as the resource file name to load the config using the input stream
     * @param resource A String representing the name of a properties file
     */
    public GameConfiguration(String resource){
        this.properties = FileHelper.createPropertiesAndTryLoadFromResource(this.getClass(), resource);
        this.setLevelFile();
    }

    /**
     * Constructs a game configuration given a resource file name and a class
     * It uses the class passed in to obtain the class loader for the input stream
     * As well as the resource file name to load the config using the input stream
     * @param resource A String representing the name of a properties file
     * @param clazz A class used to obtain the class loader for the input stream
     */
    public GameConfiguration(String resource, Class clazz){
        this.properties = FileHelper.createPropertiesAndTryLoadFromResource(clazz, resource);
        this.setLevelFile();
    }
    /**
     * sets the level file for the level
     * If it fails to retrieve the level file from the config file
     * It uses a default level path
     */
    @Override
    public void setLevelFile() {
        String levelFileName = this.properties.getProperty(LEVEL_KEY);
        //make exception here for when levelFileName is null
        if (levelFileName.contains(ROOT_SOURCE_INDICATOR)) {
            this.levelFile = new File(levelFileName);
        } else {
            this.levelFile = new File(DEFAULT_LEVEL_FILEPATH + levelFileName);
        }
    }

    /**
     * Determines the String filepath that holds the textures (i.e. ./.../mariotextures.properties)
     * @return the filepath to the textures file
     */
    @Override
    public String getTexturesPath() { return properties.getProperty(TEXTURES_KEY); }

    /**
     * Determines the String filepath that holds the textures (i.e. ./.../mariokeyinputs.properties)
     * @return the filepath to the key inputs file
     */
    @Override
    public String getKeyInputsPath() { return properties.getProperty(KEYINPUTS_KEY); }

    /**
     * Returns the String path to the .xml file specifying how to automatically generate new parts
     * of this level (i.e. ./.../automario.xml)
     * (will be NA if no such generation is needed)
     *
     * @return the filepath to the .txt file specifying automatic generation
     */
    @Override
    public String getAutoGeneratorPath() { return properties.getProperty(AUTO_KEY); }

    /**
     * Returns the class name for the player class that will run
     * @return the String corresponding to the type of player
     */
    @Override
    public String getPlayerType() { return properties.getProperty(PLAYER_KEY); }

    /**
     * Returns the String filepath that references high scores
     * @return the String corresponding to the high score path
     */
    @Override
    public String getHighScoresPath() { return properties.getProperty(HIGH_SCORES_KEY); }

    /**
     * Returns the String path pointing to the next .properties file used to build a level
     * (this may be NA if no such next file exists or Goal if the current file is the last one
     * one the chain)
     * @return the String corresponding to the next level's .properties file
     */
    @Override
    public String getNextConfigFilePath() { return properties.getProperty(NEXT_FILE_KEY); }

    /**
     * Determines the String array containing data necessary to build a Scroller
     * (i.e. ["Auto", "0.1", "-0.5"])
     * @return the list of arguments related to a scroller as a String array
     */
    @Override
    public String[] getScrollerArgs() {
        String scrollerLine = properties.getProperty(SCROLLER_KEY);
        //if (scrollerLine.indexOf(",") > 0) {
            return scrollerLine.split(",");
        //}
        /*else {
            String[] line = new String[1];
            line[0] = scrollerLine;
            return line;
        }*/
    }

    /**
     * Obtains the level file used for the current level
     * @return A File representing the level file
     */
    @Override
    public File getLevelFile() {
        return this.levelFile;
    }
}
