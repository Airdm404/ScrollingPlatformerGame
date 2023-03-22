package model.autogenerator;

import api.model.autogenerator.IAutoGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for building a 2D array of Strings where the Strings represent entity
 * types that a factory can use to make new entities for the level, which provides automatic level
 * generation. It takes as a parameter a String which points to an xml file that configures what the
 * 2D String array will look like.
 *
 * This class is primarily used in AutoGenerationHelper to generate 2D String arrays that the
 * AutoGenerationHelper then translates into entities, and inserts into the Level object passed to
 * it by AutoGenerationScroller or DoodleGenerationScroller, when either of those objects requested
 * automatic level generation.
 *
 * For more details on how to write the xml file to configure automatic level generation, please
 * refer to AUTOGENERATOR.md in the top level of our project
 *
 * @author Alex Lu
 */
public class AutoGenerator implements IAutoGenerator {

  private static final String AUTO_GENERATION_FAILED = "Automatic level generation failed";
  private static final String FILEPATH_START = "./src/resources/game_configuration/auto/";
  private int numCols;
  private int numRows;
  private String defaultValue;
  private String[][] newBlock;

  private List<ConstantGeneration> constantSpecifications;
  private List<RandomGeneration> randomSpecifications;

  /**
   * Instantiates an AutoGenerator object
   *
   * @param path a String pointing to the xml file to be used to configure automatic level
   *             generation
   */
  public AutoGenerator(String path) {
    try {
      XMLHelper helper = new XMLHelper(FILEPATH_START + path);
      buildSpecification(helper);
    } catch (Exception e) {
      throw new GenerationException(AUTO_GENERATION_FAILED);
    }
  }

  /**
   * Builds the specification lists that the AutoGenerator uses to generate levels
   *
   * @param helper the XML helper object which will generate what is necessary
   */
  private void buildSpecification(XMLHelper helper) {
    setDimensions(helper);
    setDefault(helper);

    constantSpecifications = new ArrayList<>(helper.getConstantGenerations());
    randomSpecifications = new ArrayList<>(helper.getRandomGenerations());

  }

  /**
   * Updates the specification of the AutoGenerator such that its default entityType equals
   * something in the lineParts array
   */
  private void setDimensions(XMLHelper helper) {
    numCols = helper.getNumCols();
    numRows = helper.getNumRows();
  }

  /**
   * Updates the specification of the AutoGenerator such that its default entityType equals
   * something in the lineParts array
   */
  private void setDefault(XMLHelper helper) {
    defaultValue = helper.getDefaultEntity();
  }

  /**
   * Builds the 2D String array that represents the entities who fill the next block
   *
   * @return the 2D String array of new entity representations
   */
  @Override
  public String[][] generateNextBlock() {
    newBlock = new String[numRows][numCols];
    fillInDefaultValues();
    executeAllSpecifications();
    return newBlock;
  }

  /**
   * Fills the new block with default entity entityType (i.e. what will be present at a location in
   * the game matrix if there aren't any other entity values to overwrite it)
   */
  private void fillInDefaultValues() {
    for (int row = 0; row < numRows; row += 1) {
      for (int column = 0; column < numCols; column += 1) {
        newBlock[row][column] = defaultValue;
      }
    }
  }

  /**
   * Reads each of the GenerationInstruction objects in the specifications list and then calls
   * executeSpecification on that
   */
  private void executeAllSpecifications() {
    constantSpecifications.forEach(genInst -> executeSpecification(genInst));

    randomSpecifications.forEach(genInst -> genInst.regenerate());
    randomSpecifications.forEach(genInst -> executeSpecification(genInst));
  }

  /**
   * Applies a single GenerationInstruction to the 2D array newBlock
   *
   * @param spec the GenerationInstruction to apply to the newBlock
   */
  private void executeSpecification(GenerationInstruction spec) {
    for (int row = spec.getStartRow(); row <= spec.getEndRow(); row += 1) {
      for (int column = spec.getStartCol(); column <= spec.getEndCol(); column += 1) {
        newBlock[row][column] = spec.getEntityTypeToInsert();
      }
    }
  }
}
