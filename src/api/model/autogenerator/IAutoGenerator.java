package api.model.autogenerator;

/**
 * The AutoGenerator interface
 */
public interface IAutoGenerator {

    /**
     * Builds the 2D String array that represents the entities who fill the next block
     *
     * @return the 2D String array of new entity representations
     */
    String[][] generateNextBlock();
}
