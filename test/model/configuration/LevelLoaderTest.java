package model.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.Level;
import api.model.configuration.IGameConfiguration;
import org.junit.jupiter.api.Test;

public class LevelLoaderTest {

  @Test
  public void OneBrickLevelLoadTest() throws InvalidFileException {
    IGameConfiguration IGameConfiguration = new GameConfiguration("oneBlock.properties");
    ILevelLoader ILevelLoader = new LevelLoader(IGameConfiguration.getLevelFile(), new EntityFactory());
    Level level = new Level(ILevelLoader);
    assertEquals(level.getCopyOfEntityList().size(), 1);
  }
}
