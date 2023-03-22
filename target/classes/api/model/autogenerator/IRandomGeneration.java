package api.model.autogenerator;

/**
 * The RandomGeneration interface
 */
public interface IRandomGeneration extends IGenerationInstruction {

    /**
     * Recalculates values for this random instruction
     */
    void regenerate();
}
