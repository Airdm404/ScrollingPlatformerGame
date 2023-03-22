package api.model.autogenerator;

/**
 * The ConstantGeneration interface
 */
public interface IConstantGeneration {

    /**
     * Builds a constant instruction based on a String array. The array should be specified as follows
     * args[0] = type (i.e. "Constant")
     * args[1] = entity type (i.e. "2" for enemy)
     * args[2] = row specification taking the form RowStart:RowEnd (i.e. 3:5)
     * args[3] = column specification taking the form ColStart:ColEnd (i.e. 9:10)
     * Note: "*" as an argument to args[2] or args[3] means start at beginning or end (i.e. args[2] =
     * "*:9" means "0:9" and args[3] = "3:*" means "3:numCols"
     *
     * @param args the array of arguments
     */
    void buildInstruction(String[] args);
}
