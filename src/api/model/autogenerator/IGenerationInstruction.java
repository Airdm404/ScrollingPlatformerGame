package api.model.autogenerator;

/**
 * The GenerationInstruction interface
 */
public interface IGenerationInstruction {

    /**
     * @return the row from which to start drawing the entity
     */
    int getStartRow();

    /**
     * @return the column from which to start drawing the entity
     */
    int getStartCol();

    /**
     * @return the row at which to stop drawing the entity
     */
    int getEndRow();

    /**
     * @return the column at which to stop drawing the entity
     */
    int getEndCol();

    /**
     * @return the String entity to fill in between startx and endx and starty and endy
     */
    String getEntityTypeToInsert();

    /**
     * Checks to make sure that startRow, endRow, startCol and endCol all have valid values. If not,
     * throws an exception
     */
    void validate();

    /**
     * Throws an exception to let the user know that the ConstantInstruction failed to build
     */
    void throwGenerationException();
}
