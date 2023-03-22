package api.model.scroll;

import model.autogenerator.GenerationException;

/**
 * The ScrollerFactory interface
 */
public interface IScrollerFactory {

    /**
     * Builds a scroller from a set of arguments
     *
     * @param args          the set of arguments
     * @param generatorPath the String filepath leading to a file to be used for automatic level
     *                      generation
     * @return a new scroller
     */
    Scroller buildScroller(String[] args, String generatorPath);

    /**
     * Builds and returns a Scroller from identifier and constructorArgs
     *
     * @param identifier      a String representing the type of Scroller to build
     * @param constructorArgs the arguments to be passed into its constructor
     * @return a Scroller built from identifier and constructorArgs
     */
    Scroller buildScrollerFromArgs(String identifier, String[] constructorArgs)
            throws GenerationException;

    /**
     * Returns the arguments to be passed to the Scroller constructor (i.e. take param args and strip
     * it of its identifier at index 0 and then add generatorFilepath to the end)
     *
     * @param args              the array containing the arguments passed to buildScroller
     * @param generatorFilePath the String filepath leading to a file to be used for automatic level
     *                          generation
     * @return a new array containing all elements of args besides args[0] and with the newly appended
     * generatorFilePath thrown in
     */
    String[] buildConstructorArgs(String[] args, String generatorFilePath);

    /**
     * Builds and returns a new ManualScroller from args
     *
     * @param args the arguments used to specify the ManualScroller
     * @return a new ManualScroller
     */
    Scroller buildManualScroller(String[] args);

    /**
     * Builds and returns a new AutoScroller from args
     *
     * @param args the arguments used to specify the AutoScroller
     * @return a new AutoScroller
     */
    Scroller buildAutoScroller(String[] args);

    /**
     * Builds and returns a new DoodleGenerationScroller from args
     *
     * @param args the arguments used to specify the DoodleGenerationScroller
     * @return a new DoodleGenerationScroller
     */
    Scroller buildDoodleGenerationScroller(String[] args) throws GenerationException;

    /**
     * Builds and returns a new AutoGenerationScroller from args
     *
     * @param args the arguments used to specify the AutoGenerationScroller
     * @return a new AutoGenerationScroller
     */
    Scroller buildAutoGenerationScroller(String[] args) throws GenerationException;
}
