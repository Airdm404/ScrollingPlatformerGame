package model.scroll;

import java.util.Optional;
import model.Level;
import model.autogenerator.AutoGenerator;
import model.autogenerator.GenerationException;
import model.configuration.EntityFactory;
import api.model.configuration.IEntityFactory;
import api.model.entity.IEntity;

/**
 * This class's job is to help AutoGeneratonScroller and DoodleGenerationScroller objects perform
 * automatic level generation. These two aforementioned classes contain an AutoGenerationHelper
 * instance that they instantiate during construction and that they pass a String filepath to in
 * order to allow the AutoGenerationHelper to identify the xml config file it will use for automatic
 * level generation.
 *
 * The AutoGenerationScroller and DoodleGenerationScroller then call
 * autoGenerationHelper.generateForLevel(level, rowOffset, colOffset) in order to allow the
 * AutoGenerationHelper to use an AutoGenerator to create a 2D String array of entity types,
 * translate those Strings to IEntity objects, and then to insert them into the level object so that
 * they become part of gameplay
 *
 * @author Alex Lu
 */
public class AutoGenerationHelper {

  private final AutoGenerator generator;
  private final IEntityFactory factory;
  protected String[][] currentGeneration;


  /**
   * Instantiates an AutoGenerationHelper object
   *
   * @param generatorPath a String filepath which points to the xml file that will be used for
   *                      automatic level generation
   */
  public AutoGenerationHelper(String generatorPath) {
    try {
      factory = new EntityFactory();
      generator = new AutoGenerator(generatorPath);
      currentGeneration = generator.generateNextBlock();
    } catch (Exception e) {
      throw new GenerationException("");
    }
  }


  /**
   * Creates a new part of the level and adds it to the entity list
   *
   * @param level the Level in which to insert the entities
   */
  public void generateForLevel(Level level, double rowOffset, double colOffset) {
    currentGeneration = generator.generateNextBlock();

    for (int row = 0; row < currentGeneration.length; row += 1) {
      for (int column = 0; column < currentGeneration[0].length; column += 1) {
        String entityCode = currentGeneration[row][column];
        insertIntoLevel(entityCode, level,
            rowOffset + row, colOffset + column);
      }
    }
  }

  /**
   * Builds an entity from entityCode and inserts it into level
   *
   * @param entityCode the String containing the type of entity
   * @param level      the Level in which to insert the entity
   */
  private void insertIntoLevel(String entityCode, Level level, double row, double col) {
    Optional<IEntity> optionalEntity = factory.createEntity(entityCode, col, row);
    optionalEntity.ifPresent(level::addEntity);
  }

  /**
   * Reveals the number of new rows that have been added to the Level parameter in
   * generateForLevel() as of the last call to that method
   *
   * @return the number of rows in the "current" currentGeneration array
   */
  public int getAddedNumRows() {
    return currentGeneration.length;
  }

  /**
   * Reveals the number of new columns that have been added to the Level parameter in
   * generateForLevel() as of the last call to that method
   *
   * @return the number of new columns in the "current" currentGeneration array
   */
  public int getAddedNumColumns() {
    return currentGeneration[0].length;
  }
}
