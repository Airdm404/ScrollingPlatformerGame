package api.model;

import model.Level;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

/**
 * An interface for saving levels as CSV files
 * @author Mike Garay
 */
public interface IGameSaver {
    /**
     * Accessor for the stored Level
     * @implNote : This should be amended to retrieve an ILevel, not a Level
     * @return The level instance stored in the IGameSaver instance
     */
    Level getCurrentLevel();

    /**
     * Sets the current Level to store in the IGameSaver instance
     * @implNote : This should be amended to store an ILevel, not a Level
     * @param currentLevel The Level instance to store
     */
    void setCurrentLevel(Level currentLevel);

    /**
     * Writes a new CSV level file, ideally with checks
     * to handle any exceptions before calling writeLevelFile
     * @param fileNameIn The full path of the file to write
     */
    void writeNewLevelCSVFileWithChecks(String fileNameIn);

    /**
     * Writes a new CSV level file
     * @param fileNameToWrite The full path of the file to write
     * @throws IOException If the file path results in the created file to not be valid
     */
    void writeLevelFile(String fileNameToWrite) throws IOException;

    /**
     * Gets a stream of possible keys for a value in a map
     * Source: https://www.baeldung.com/java-map-key-from-value
     * @param map The Map in which to find matching keys for the given value
     * @param value The value to find matching keys for
     * @return A Stream of the same type as the Map's keys that should contain any matching keys for the given value
     */
    default <K, V> Stream<K> getMatchingKeysForValue(Map<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey);
    }
}
