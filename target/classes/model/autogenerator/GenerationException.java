package model.autogenerator;

/**
 * This class is an exception which is thrown when the AutoGenerator fails to build a generation, or
 * receives invalid configuration (i.e. bad xml file, improperly formatted String in the xml file,
 * etc.)
 */
public class GenerationException extends RuntimeException {

  public GenerationException(String message) {
    super(message);
  }

  public GenerationException(String message, Throwable cause) {
    super(message, cause);
  }
}
