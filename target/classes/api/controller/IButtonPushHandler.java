package api.controller;

/**
 * An interface responsible for handling the event where the user pushes a button -
 * have the user implement handlePush() and then any Buttons created by ButtonBuilder will
 * call handlePush(args) on the user's class when they are pushed. Typically you expect args
 * to reveal something about which button was pushed
 *
 * @author Alex Lu
 */
public interface IButtonPushHandler {

  /**
   * Determines what the user should do after a button is pushed
   * @param type contains information that might help the user decide what to do
   */
  void handlePush(String type);


}
