package controller;

/**
 * An exception which is thrown in the event that a key input configuration file contains an illegal
 * method reference (i.e. has a key that is not a legal method to call in KeyInputterMethod caller)
 * such as hello
 */
public class KeyInputterMissingMethodException extends Exception {

  public KeyInputterMissingMethodException(String message) {
    super(message);
  }
}
