package api.model.autogenerator;

/**
 * The Randomizer interface
 */
public interface IRandomizer {

    /**
     * Fills the data structures necessary to initialize the randomizer
     *
     * @param randomizerString the String argument that contains
     */
    void initializeRandomizer(String randomizerString);

    /**
     * Checks to make sure the values array has the same number of elements in it as the probabilities
     * array and if not throws an exception
     */
    void checkArrayLengthsAlign();

    /**
     * Takes "(" and ")" out of path
     *
     * @param path a String containing exactly one instance of "(" and ")"
     * @return the new String
     */
    String stripParentheses(String path);

    /**
     * Fills the values array with numbers pulled from a String array
     *
     * @param values the String array containing the numbers to be pulled
     * @return toBeFilled the int array to be filled with the values
     */
    int[] fillIntArrayWithStrings(String[] values);

    /**
     * Fills the values array with numbers pulled from a String array
     *
     * @param values the String array containing the numbers to be pulled
     * @return toBeFilled the double array to be filled with the values
     */
    double[] fillDoubleArrayWithStrings(String[] values);

    /**
     * Returns a random entityType in the values array weighted by the probabilties
     *
     * @return a random entityType in the values array
     */
    int getRandomValue();
}
