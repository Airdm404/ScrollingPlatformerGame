package controller;

/**
 * An exception which is thrown in the event that Builder or any of its subclasses are unable to
 * correctly build the list of objects that the user has asked them to
 *
 * @author Alex Lu
 */
public class BuilderInstantiationException extends Exception {

  public BuilderInstantiationException(String message) {
    super(message);
  }
}
