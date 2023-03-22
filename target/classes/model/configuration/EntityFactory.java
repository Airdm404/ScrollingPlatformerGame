package model.configuration;

import api.model.entity.IEntity;
import api.model.configuration.IEntityFactory;
import api.model.configuration.ILevelDecoder;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * A class responsible for instantiating entities during level-loading
 * using an ILevelDecoder and reflection
 * @author Mike Garay and Alex Lu
 */
public class EntityFactory implements IEntityFactory {

  private ILevelDecoder decoder;
  private Map<String, String> idToEntityMap;
  private static final String ENTITY_PACKAGE_PATH = "model.entity.";
  private static final String PLAYER_KEY = "1";
  private final String[] validPlayerValues = {"MarioPlayer", "FlappyPlayer", "DoodlePlayer"};

  /**
   * Constructs an IEntityFactory by instantiating a LevelDecoder and then instantiating the id to entity map
   * obtained from the decoder
   */
  public EntityFactory() {
    try {
      decoder = new LevelDecoder();
      idToEntityMap = new HashMap<>(decoder.getIdToEntityMap());
    }
    catch (IOException e) {

    }
  }

  /**
   * Updates the map so that the <PLAYER_KEY, idToEntityMap.get(PLAYER_KEY)> pair becomes
   * <PLAYER_KEY, newMapping>
   * @param newMapping the new String value that will be stored at PLAYER_KEY in the mapping
   * @throws NullPointerException if the player type is not valid for the level
   */
  @Override
  public void updatePlayerMapping(String newMapping) throws NullPointerException {
    List<String> validPlayerValueList = Arrays.asList(validPlayerValues);
    if (validPlayerValueList.contains(newMapping)) {
      idToEntityMap.put(PLAYER_KEY, newMapping);
    }
    else {
      throw new NullPointerException("Invalid Player Type");
    }
  }

  /**
   * Creates an Optional of an IEntity instance whose hitbox is at rowIndex and colIndex
   * The IEntity class is decoded first, then instantiated using reflectEntity
   * @param entityString The name of the IEntity class to decode
   * @param rowIndex The x-coordinate to spawn the IEntity at
   * @param colIndex The y-coordinate to spawn the IEntity at
   * @return an Optional of IEntity to add to the level if it is present
   */
  @Override
  public Optional<IEntity> createEntity(String entityString, double rowIndex, double colIndex) {
      Optional<IEntity> optionalDecodedEntity = Optional.empty();
      String decodedEntityString = idToEntityMap.get(entityString);
      if(decodedEntityString == null) return optionalDecodedEntity;

      optionalDecodedEntity = reflectEntity(decodedEntityString, rowIndex, colIndex);
      return optionalDecodedEntity;

  }

  /**
   * Creates an Optional of an IEntity instance through reflection
   * It uses Class#forName to obtain the class of the IEntity decoded
   * Then it attempts to obtains the constructor that only takes in an x and y coordinate
   * And then creates a new instance by called newInstance with the rowIndex and colIndex as args
   * @param decodedEntityString The name of the decoded IEntity class
   * @param rowIndex The x-coordinate to spawn the IEntity at, used in the reflection construction
   * @param colIndex The y-coordinate to spawn the IEntity at, used in the reflection construction
   * @return an Optional of IEntity that was attempted to be obtained through reflection
   */
  @Override
  public Optional<IEntity> reflectEntity(String decodedEntityString, double rowIndex, double colIndex) {
    IEntity decodedEntity;
    try {
      Class entityClass = Class.forName(ENTITY_PACKAGE_PATH + decodedEntityString);
      Constructor entityConstructor = entityClass.getConstructor(double.class, double.class);
      decodedEntity = (IEntity) entityConstructor.newInstance(rowIndex, colIndex);
      return Optional.of(decodedEntity);
    } catch (ClassNotFoundException
        | NoSuchMethodException
        | InstantiationException
        | InvocationTargetException
        | IllegalAccessException e) {
      return Optional.empty();
    }
  }
}
