package api.model.autogenerator;

/**
 * The UniformRandomizer interface
 */
public interface IUniformRandomizer {

    /**
     * Sets up the lowBound and highBound values necessary to facilitate random number generation
     *
     * @param randomizerPath the String containing config data to set up lowBound/highBound
     */
    void initialize(String randomizerPath);

    /**
     * Takes "(" and ")" out of path
     *
     * @param path a String containing exactly one instance of "(" and ")"
     * @return the new String
     */
    String stripParentheses(String path);

    /**
     * Calculates a random value based on the input String
     *
     * @return a random int in the range [lowBound,highBound]
     */
    int getUniformValue();
}
