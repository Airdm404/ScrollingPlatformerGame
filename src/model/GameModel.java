package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import model.autogenerator.GenerationException;
import model.configuration.*;
import api.model.entity.IEntity;
import api.model.scroll.Scroller;
import model.scroll.ScrollerFactory;
import api.model.IGameModel;
import api.model.IKeyPressFunctions;
import api.model.configuration.IGameConfiguration;


public class GameModel implements IGameModel {

    private IGameConfiguration gameConfiguration;
    private File levelFile;
    private Level level;
    private ScrollerFactory scrollerFactory;
    private api.model.configuration.IEntityFactory IEntityFactory;

    public GameModel() {}

    public GameModel(IGameConfiguration gameConfiguration) throws InvalidFileException,
        NullPointerException, GenerationException {
        this.gameConfiguration = gameConfiguration;
        levelFile = gameConfiguration.getLevelFile();

        IEntityFactory = new EntityFactory();
        String playerType = gameConfiguration.getPlayerType();
        IEntityFactory.updatePlayerMapping(playerType);

        ILevelLoader ILevelLoader = new LevelLoader(levelFile, IEntityFactory);
        this.level = new Level(ILevelLoader);
        setLevelScroller();
    }

    @Override
    public IKeyPressFunctions getKeyPressFunctions() {
        return this.level.getKeyPressFunctions();
    }

    @Override
    public void updateGame(){
        this.level.step();
    }

    /**
     * Sets the scroller on level to a specific Scroller based on the contents of the level
     */
    @Override
    public void setLevelScroller() {
        scrollerFactory = new ScrollerFactory();
        String[] scrollerArgs = gameConfiguration.getScrollerArgs();
        String autoGenerationPath = gameConfiguration.getAutoGeneratorPath();
        Scroller builtScroller = scrollerFactory.buildScroller(scrollerArgs, autoGenerationPath);
        level.setScroller(builtScroller);
    }

    /**
     * Returns a defensively copied list of all of the entities present in level
     * @return defensively copied list of all entities in Level
     */
    @Override
    public List<IEntity> getAllEntitiesInLevel() {
        List<IEntity> defensivelyCopiedEntityList = new ArrayList<>();
        defensivelyCopiedEntityList.addAll(level.getCopyOfEntityList());
        return defensivelyCopiedEntityList;
    }

    /**
     * Returns the String path to the .properties file containing data on key inputs
     * @return the String keyInputsPath
     */
    @Override
    public String getKeyInputsPath() {
        return gameConfiguration.getKeyInputsPath();
    }

    /**
     * Returns the String path to the .properties file containing data on textures
     * @return the String texturesPath
     */
    @Override
    public String getTexturesPath() {
        return gameConfiguration.getTexturesPath();
    }

    /**
     * Returns the String path to the .csv file containing data on high scores
     * @return the String highScoresPath
     */
    @Override
    public String getHighScoresPath() {
        return gameConfiguration.getHighScoresPath();
    }

    /**
     * Returns the String path pointing to the next .properties file used to build a level
     * (this may be NA if no such next file exists or Goal if the current file is the last one
     * one the chain)
     * @return the String nextConfigFilePath
     */
    @Override
    public String getNextConfigFilePath() { return gameConfiguration.getNextConfigFilePath(); }

    /**
     * Returns the level of this model
     * @return level
     */
    @Override
    public Level getLevel() { return this.level; }

    /**
     * Returns the score associated with the level in use by the model
     * @return level.getScore()
     */
    @Override
    public int getScore() { return this.level.getScore(); }

    /**
     * Resets the level to the characteristics in levelLoader
     */
    @Override
    public void resetLevel() throws InvalidFileException {
        ILevelLoader ILevelLoader = new LevelLoader(levelFile, IEntityFactory);
        level.setOrResetLevel(ILevelLoader);
        setLevelScroller();
    }

}
