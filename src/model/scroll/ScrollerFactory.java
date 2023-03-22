package model.scroll;

import model.autogenerator.GenerationException;
import api.model.scroll.IScrollerFactory;
import api.model.scroll.Scroller;

/**
 * This class's job is to take in a String array of arguments and to use those arguments to generate
 * a Scroller object. Since, of course, this String array could theoretically contain any arguments,
 * there is extensive error checking in this class to make sure the arguments are valid. The
 * ScrollerFactory class is primarily used in GameModel in order to build a Scroller for the level
 * based on configuration in a .properties file
 *
 * @author Alex Lu
 */
public class ScrollerFactory implements IScrollerFactory {

  private static final String MANUAL = "Manual";
  private static final String AUTO = "Auto";
  private static final String DOODLE = "Doodle";
  private static final String AUTO_GENERATION = "AutoGeneration";

  /**
   * Builds a scroller from a set of arguments
   *
   * @param args          the set of arguments
   * @param generatorPath the String filepath leading to a file to be used for automatic level
   *                      generation
   * @return a new scroller
   */
  @Override
  public Scroller buildScroller(String[] args, String generatorPath) {
    String identifier = args[0];
    String[] constructorArgs = buildConstructorArgs(args, generatorPath);

    try {
      Scroller scroller = buildScrollerFromArgs(identifier, constructorArgs);
      return scroller;
    } catch (IndexOutOfBoundsException | NumberFormatException | GenerationException ex) {
      throw new GenerationException("no scrolling allowed for this level - bad configuration");
    }
  }

  /**
   * Builds and returns a Scroller from identifier and constructorArgs
   *
   * @param identifier      a String representing the type of Scroller to build
   * @param constructorArgs the arguments to be passed into its constructor
   * @return a Scroller built from identifier and constructorArgs
   */
  @Override
  public Scroller buildScrollerFromArgs(String identifier, String[] constructorArgs)
      throws GenerationException {
    return switch (identifier) {
      case MANUAL -> buildManualScroller(constructorArgs);
      case AUTO -> buildAutoScroller(constructorArgs);
      case DOODLE -> buildDoodleGenerationScroller(constructorArgs);
      case AUTO_GENERATION -> buildAutoGenerationScroller(constructorArgs);
      default -> new AutoScroller(0, 0, false);
    };
  }

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
  @Override
  public String[] buildConstructorArgs(String[] args, String generatorFilePath) {
    String[] constructorArgs = new String[args.length];

    for (int index = 0; index < args.length - 1; index += 1) {
      constructorArgs[index] = args[index + 1];
    }
    constructorArgs[args.length - 1] = generatorFilePath;

    return constructorArgs;
  }

  /**
   * Builds and returns a new ManualScroller from args
   *
   * @param args the arguments used to specify the ManualScroller
   * @return a new ManualScroller
   */
  @Override
  public Scroller buildManualScroller(String[] args) {
    return new ManualScroller(Double.parseDouble(args[0]), Double.parseDouble(args[1]),
        Double.parseDouble(args[2]), Double.parseDouble(args[3]));
  }

  /**
   * Builds and returns a new AutoScroller from args
   *
   * @param args the arguments used to specify the AutoScroller
   * @return a new AutoScroller
   */
  @Override
  public Scroller buildAutoScroller(String[] args) {
    return new AutoScroller(Double.parseDouble(args[0]), Double.parseDouble(args[1]),
        Boolean.parseBoolean(args[2]));
  }

  /**
   * Builds and returns a new DoodleGenerationScroller from args
   *
   * @param args the arguments used to specify the DoodleGenerationScroller
   * @return a new DoodleGenerationScroller
   */
  @Override
  public Scroller buildDoodleGenerationScroller(String[] args) throws GenerationException {
    return new DoodleGenerationScroller(Double.parseDouble(args[0]), Double.parseDouble(args[1]),
        Double.parseDouble(args[2]), Double.parseDouble(args[3]), args[4]);
  }

  /**
   * Builds and returns a new AutoGenerationScroller from args
   *
   * @param args the arguments used to specify the AutoGenerationScroller
   * @return a new AutoGenerationScroller
   */
  @Override
  public Scroller buildAutoGenerationScroller(String[] args) throws GenerationException {
    return new AutoGenerationScroller(Double.parseDouble(args[0]), Double.parseDouble(args[1]),
        Boolean.parseBoolean(args[2]), args[3]);
  }
}
