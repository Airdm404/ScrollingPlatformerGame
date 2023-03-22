package api.model.configuration;

import java.io.IOException;
import java.util.Map;

/**
 * An interface for decoding entities from their ids in a level file
 * @author Alex Lu
 */
public interface ILevelDecoder {

    /**
     * Builds a map, mapping String ids -> String entity types (i.e. "1" -> "PLAYER")
     * @throws IOException if an error occurred when reading from the input stream
     * @throws NullPointerException if the Map is constructed with null values
     */
    void buildKeyToEntityTypeMap() throws IOException, NullPointerException;

    /**
     * Returns the map from keys to entities
     * @return keyToEntityTypeMap
     */
    Map<String, String> getIdToEntityMap();
}
