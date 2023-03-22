package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import model.configuration.EntityFactory;
import model.configuration.ILevelLoader;
import model.configuration.InvalidFileException;
import model.configuration.LevelLoader;
import api.model.IGameSaver;
import org.junit.jupiter.api.Test;
import util.TestFiles;

public class GameSaverTest {

    public static final String TEST_SAVE_PATH = "data/saves/alex_level_test_save.csv";

    @Test
    public void testSave() throws IOException, InvalidFileException {
        ILevelLoader ILevelLoader = new LevelLoader(TestFiles.ALEX_LEVEL, new EntityFactory());
        Level level = new Level(ILevelLoader);
        IGameSaver IGameSaver = new GameSaver(level);
        File previousSaveFile = new File(TEST_SAVE_PATH);
        if(previousSaveFile.isFile()){
            assertTrue(previousSaveFile.delete());
        }
        IGameSaver.writeNewLevelCSVFileWithChecks(TEST_SAVE_PATH);
        File file = new File(TEST_SAVE_PATH);
        assertTrue(file.isFile());
    }

}

