package model;

import model.configuration.FileHelper;
import api.model.configuration.ILevelDecoder;
import model.configuration.LevelDecoder;
import api.model.entity.IEntity;
import api.model.IGameSaver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A class responsible for writing an existing state of the simulation to a CSV file
 * @author Mike Garay
 */
public class GameSaver implements IGameSaver {

    private static final String CSV_EXTENSION = ".csv";
    private static final int DEFAULT_ENTITY_KEY = 0;
    public static final String EMPTY = "EMPTY";
    private Level currentLevel;

    /**
     * Constructs a GameSaver
     * @param currentLevel A Level representing the current state of the level
     */
    public GameSaver(Level currentLevel){
        this.currentLevel = currentLevel;
    }

    @Override
    public Level getCurrentLevel() {
        return this.currentLevel;
    }

    @Override
    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * Writes a new CSV file given a file name
     * @param fileNameIn The name of the file to be written
     */
    @Override
    public void writeNewLevelCSVFileWithChecks(String fileNameIn) {
        String fileNameToWrite =
                FileHelper.isCSVFile(fileNameIn) ?
                        fileNameIn :
                        FileHelper.removeFileExtensionIfPresent(fileNameIn) + CSV_EXTENSION;


        try {
            writeLevelFile(fileNameToWrite);
        } catch (IOException ioException){
            // TODO:
            ioException.printStackTrace();
        }

    }

    @Override
    public void writeLevelFile(String fileNameToWrite) throws IOException {
        ILevelDecoder ILevelDecoder = new LevelDecoder();
        Map<String, String> levelDecoderMap = ILevelDecoder.getIdToEntityMap();
        FileWriter levelCSVWriter = new FileWriter(fileNameToWrite);
        for(int yIndex = 0; yIndex < currentLevel.getLevelLength(); yIndex++){
            StringBuilder currentRow = new StringBuilder();
            for(int xIndex = 0; xIndex < currentLevel.getLevelWidth(); xIndex++){
                Optional<IEntity> optionalEntity = currentLevel.getEntityAt(xIndex, yIndex);
                String entityTypeString = EMPTY;
                if (optionalEntity.isPresent()) {
                    entityTypeString = optionalEntity.get().getType();
                }
                Stream<String> matchingKeysForEntity = getMatchingKeysForValue(levelDecoderMap, entityTypeString);
                // Use the first key found, regardless of if there are multiple
                Optional<String> optionalEntityDecoderKey = matchingKeysForEntity.findFirst();
                if(optionalEntityDecoderKey.isPresent()){
                    currentRow.append(optionalEntityDecoderKey.get());
                }
                else{
                    currentRow.append(DEFAULT_ENTITY_KEY);
                }
                if(xIndex < currentLevel.getLevelWidth() - 1){
                    currentRow.append(",");
                }
            }
            levelCSVWriter.append(currentRow);
            if(yIndex < currentLevel.getLevelLength() - 1){
                levelCSVWriter.append("\n");
            }
        }

        levelCSVWriter.flush();
        levelCSVWriter.close();
    }


}

