package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import api.controller.IGameController;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;

/**
 * Serves as the controller of our MVC model - handles button pushes and key inputs and keys them to
 * our view and model
 */
public class GameController extends Group implements IGameController {

  private static final double WIDTH = 800;
  private static final double HEIGHT = 800;
  private static final String OPTIONS_SELECTOR_EVENTTYPE = "push";
  private static final EventType CONTROLLER_EVENT = new EventType("controller");
  private final List<String> buffer;

  /**
   * Constructs a GameController object
   */
  public GameController() {
    buffer = new ArrayList<>();
    setOnKeyPressed(this::handleKeyPress);
    setOnKeyReleased(this::handleKeyRelease);
    setFocusTraversable(true);
    setFocused(true);
  }

  /**
   * Adds an OptionsSelector from a folder
   *
   * @param folder    the folder containing the list of options (i.e. "./ooga/resources/buttons")
   * @param extension the allowed extension for each option (i.e. include if ".jpeg")
   * @param method    the method to be called by the OptionsSelector
   */
  @Override
  public void addOptionsSelectorFromFolder(String folder, String extension, String method)
      throws BuilderInstantiationException {
    FolderParser parser = new FolderParser(folder, extension);
    buildOptionsSelector(parser.getFilenamesFromFolder(), method);
  }

  /**
   * Builds and inserts an OptionsSelector into the children group
   *
   * @param choices the list of options to select from
   * @param method  the String representation of the method to call when one such option is
   *                selected
   * @throws BuilderInstantiationException bie
   */
  @Override
  public void buildOptionsSelector(List<String> choices, String method)
      throws BuilderInstantiationException {
    List<String> defensiveChoices = new ArrayList<>();
    defensiveChoices.addAll(choices);

    OptionsSelector selector = new OptionsSelector(WIDTH, HEIGHT, defensiveChoices);
    selector.addEventHandler(EventType.ROOT, event ->
        callMethodOnOptionSelector(event, method, selector.getTextInBuffer()));

    getChildren().add(selector);
  }


  /**
   * Adds a set of buttons as specified by file to the controller - when they are pushed they will
   * execute handlePush(String methodName) which will use reflection to execute the method called
   * methodName on the view as specified by "view"
   *
   * @param file the filepath of the button file
   */
  @Override
  public void addButtonsFromFile(String file) throws BuilderInstantiationException {
    ButtonBuilder builder = new ButtonBuilder(WIDTH, HEIGHT, file, this);
    List<Button> foundButtons = builder.getFoundButtons();

    foundButtons.forEach(button -> button.setFocusTraversable(false));
    getChildren().addAll(foundButtons);
  }

  /**
   * Handles the event where a Button as built by ButtonBuilder has been pushed. When this happens
   * that button calls handlePush with the String type which is a String representation of a method,
   * i.e. back() which will then be called on "view" thus running a method in the view class using
   * reflection
   *
   * @param methodName a String representation of the method to be called on the view class
   */
  @Override
  public void handlePush(String methodName) {
    fillBuffer(methodName, new ArrayList<>());
  }

  /**
   * Handles the event that a key has been pressed
   *
   * @param event the KeyEvent that has happened
   */
  @Override
  public void handleKeyPress(KeyEvent event) {
    List<String> keyArgs = new ArrayList<>();
    keyArgs.add(event.getCode().toString());
    fillBuffer("keyPressed", keyArgs);
  }

  /**
   * Handles the event that a key has been released
   *
   * @param event the KeyEvent that has happened
   */
  @Override
  public void handleKeyRelease(KeyEvent event) {
    List<String> keyArgs = new ArrayList<>();
    keyArgs.add(event.getCode().toString());
    fillBuffer("keyReleased", keyArgs);
  }

  /**
   * Updates the ResourceBundle for the GameController
   *
   * @param name the String name of the ResourceBundle (i.e. "English" for English.properties
   */
  @Override
  public void updateResources(String name) {
    for (Node n : getChildren()) {
      if (n.getClass().getSimpleName().equals("Button")) {
        ((Button) n).setText(ResourceBundle.getBundle("resources/resourcebundles."
            + name).getString(n.getId()));
      } else if (n.getClass().getSimpleName().equals("OptionsSelector")) {
        ((OptionsSelector) n).updateBundle(name);
      }
    }
  }

  /**
   * Returns the elements in the buffer, having been defensively copied into bufferHolder
   *
   * @return bufferHolder
   */
  @Override
  public List<String> getBuffer() {
    List<String> bufferHolder = new ArrayList<>();
    bufferHolder.addAll(buffer);
    return bufferHolder;
  }


  /**
   * @param event  the event that has occurred
   * @param method the method to be called if event matches OPTIONS_SELECTOR_EVENTTYPE
   * @param text   the String parameter to be inserted into the method if event matches
   *               OPTIONS_SELECTOR_EVENTTYPE
   */
  public void callMethodOnOptionSelector(Event event, String method, String text) {
    if (event.getEventType().getName().equals(GameController.OPTIONS_SELECTOR_EVENTTYPE) && !text
        .equals("")) {

      List<String> args = new ArrayList<>();
      args.add(text);
      fillBuffer(method, args);

    }
  }

  /**
   * Fires an event
   */
  public void dispatchEvent() {
    fireEvent(new Event(GameController.CONTROLLER_EVENT));
  }

  /**
   * Empties the buffer List and then fills it with methodName and a list of arguments
   *
   * @param methodName the String representation of the method
   * @param args       a list of arguments
   */
  public void fillBuffer(String methodName, List<String> args) {
    buffer.clear();
    buffer.add(methodName);
    buffer.addAll(args);
    dispatchEvent();
  }
}
