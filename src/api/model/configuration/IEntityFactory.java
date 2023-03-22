package api.model.configuration;

import api.model.entity.IEntity;

import java.util.Optional;

/**
 * This interface contains methods responsible for instantiating entities
 * during level-loading by decoding strings obtained from processing the level csv files
 * @author Mike Garay & Alex Lu
 */
public interface IEntityFactory {
    /**
     * Updates the mapped value of a player key to the new string value in the decoder
     * @param newMapping the new String value that should be stored as the player key's value in the mapping
     * @throws NullPointerException if the new mapping is not a valid player type for the level
     */
    void updatePlayerMapping(String newMapping) throws NullPointerException;

    /**
     * Creates an Optional of an IEntity instance whose hitbox is at rowIndex and colIndex
     * You should decode the IEntity class first, then instantiate it using reflectEntity below
     * @param entityString The name of the IEntity class to decode
     * @param rowIndex The x-coordinate to spawn the IEntity at
     * @param colIndex The y-coordinate to spawn the IEntity at
     * @return an Optional of IEntity to add to the level if it is present
     */
    Optional<IEntity> createEntity(String entityString, double rowIndex, double colIndex);

    /**
     * Creates an Optional of an IEntity instance through reflection
     * @param decodedEntityString The name of the decoded IEntity class
     * @param rowIndex The x-coordinate to spawn the IEntity at, used in the reflection construction
     * @param colIndex The y-coordinate to spawn the IEntity at, used in the reflection construction
     * @return an Optional of IEntity that was obtained through reflection
     */
    Optional<IEntity> reflectEntity(String decodedEntityString, double rowIndex, double colIndex);
}
