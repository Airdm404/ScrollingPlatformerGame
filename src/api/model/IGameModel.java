package api.model;

import model.Level;
import model.configuration.InvalidFileException;
import api.model.entity.IEntity;

import java.util.List;

public interface IGameModel {
    IKeyPressFunctions getKeyPressFunctions();

    void updateGame();

    void setLevelScroller();

    List<IEntity> getAllEntitiesInLevel();

    String getKeyInputsPath();

    String getTexturesPath();

    String getHighScoresPath();

    String getNextConfigFilePath();

    Level getLevel();

    int getScore();

    void resetLevel() throws InvalidFileException;
}
