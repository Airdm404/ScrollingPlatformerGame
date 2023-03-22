package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import api.controller.IOptionsSelector;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * Works as a UI - friendly ComboBox of sorts - takes in a list of Strings and presents an interface
 * where the user can select from three choices as well as options to move backwards or forwards in
 * the list of choices (with list wrapping available). In the event that the user clicks on any of
 * the three choices, the OptionsSelector will set the text in its buffer to be the text of the
 * button that the user pressed and then will dispatch an event to reveal that the user has made a
 * choice.
 *
 * @author Alex Lu
 */


public class OptionsSelector extends Group implements IOptionsSelector {

  private static final String OPTION1 = "Option1";
  private static final String OPTION2 = "Option2";
  private static final String OPTION3 = "Option3";
  private static final String TITLE = "SELECT_OPTIONS";
  private static final EventType<Event> EVENT_TYPE = new EventType<>("push");

  private static final int NUM_OPTION_BUTTONS = 3;
  private static final String RESOURCES = "resources/";
  private static final String OPTIONS_SELECTOR_CONFIG_PATH = "./src/" + RESOURCES
      + "buttons/optionsselectorbuttons.xml";

  private ResourceBundle resourceBundle;
  private static final String EXTENSION = ".English";
  private static final String LABELS = "resourcebundles";
  private int choosableOptionOffset;
  private String bufferText;

  private final List<String> choices;
  private final double WIDTH;
  private final double HEIGHT;

  /**
   * Instantiates an OptionsSelector
   *
   * @param w the width of the visible pane that the OptionsSelector will fill
   * @param h the height of the visible pane that the OptionsSelector will fill
   * @param c the list of choices that will fill the OptionsSelector
   */
  public OptionsSelector(double w, double h, List<String> c) throws BuilderInstantiationException {
    WIDTH = w;
    HEIGHT = h;
    choosableOptionOffset = 0;
    bufferText = "";

    choices = new ArrayList<>();
    copyIntoChoices(c);

    resourceBundle = ResourceBundle.getBundle(RESOURCES + LABELS + EXTENSION);

    buildButtons();
    regenerateOptions();
  }

  /**
   * Copies the contents of list c into choices (avoids aliasing issues)
   *
   * @param c the list of things to be copied in
   */
  private void copyIntoChoices(List<String> c) {
    choices.addAll(c);
  }

  /**
   * Creates the buttons associated with this OptionsSelector using a ButtonBuilder reading from a
   * config file as specified by OPTIONS_SELECTOR_CONFIG_PATH
   */
  private void buildButtons() throws BuilderInstantiationException {
    ButtonBuilder builder = new ButtonBuilder(WIDTH, HEIGHT,
        OPTIONS_SELECTOR_CONFIG_PATH, this);
    if (builder.getStateReferenced().equals(TITLE)) {
      super.getChildren().addAll(builder.getFoundButtons());
    }
  }

  /**
   * Is responsible for handling the situation where the user pushes a button - directs the query to
   * a different method depending on which button was pushed
   *
   * @param type the method name to be called which was pushed
   */
  @Override
  public void handlePush(String type) {
    try {
      getClass().getDeclaredMethod(type).invoke(this);
    } catch (Exception e) {
      //DO NOTHING
    }
  }

  /**
   * Regenerates the text on the buttons that select the simulation so that they reflect the current
   * options that the user is choosing from
   */
  @Override
  public void regenerateOptions() {
    int offset = determineSelectionOffset(choices);

    setOptionText(choices, (Button) lookup("#" + OPTION1), offset);
    setOptionText(choices, (Button) lookup("#" + OPTION2), offset + 1);
    setOptionText(choices, (Button) lookup("#" + OPTION3), offset + 2);
  }

  /**
   * Removes an option from the option selector
   *
   * @param option the option to be removed
   */
  @Override
  public void removeOption(String option) {
    choices.remove(option);
    regenerateOptions();

  }

  /**
   * Adds an option to the option selector
   *
   * @param option the option to be removed
   */
  @Override
  public void addOption(String option) {
    choices.add(option);
    regenerateOptions();
  }


  /**
   * Returns the relevant text in the buffer - this will let the user know what this OptionsSelector
   * has currently selected. Then, set the buffer text to "" because it's already been returned
   *
   * @return bufferText
   */
  @Override
  public String getTextInBuffer() {
    String tempBuffer = bufferText;
    bufferText = "";
    return tempBuffer;
  }

  /**
   * Updates the resource bundle associated with this particular OptionsSelector and then sets the
   * text accordingly
   *
   * @param path the String path leading to the properties file
   */
  @Override
  public void updateBundle(String path) {
    resourceBundle = ResourceBundle.getBundle(RESOURCES + LABELS + "." + path);
    updateAllButtonBundles();
    regenerateOptions();
  }

  /**
   * Displays the 3 previous options
   */
  public void prevOptions() {
    choosableOptionOffset -= 1;
    regenerateOptions();
  }

  /**
   * Displays the 3 next options
   */
  public void nextOptions() {
    choosableOptionOffset += 1;
    regenerateOptions();
  }

  /**
   * Determines which page of the options you are currently looking at
   *
   * @return the starting point in the list to generate file names for simulations
   */
  public int determineSelectionOffset(List<String> options) {
    int offset = choosableOptionOffset * OptionsSelector.NUM_OPTION_BUTTONS;
    if (offset >= options.size()) {
      choosableOptionOffset = 0;
      return 0;
    } else if (offset < 0) {
      choosableOptionOffset = Math
          .max((options.size() - 1) / OptionsSelector.NUM_OPTION_BUTTONS, 0);
      return choosableOptionOffset * OptionsSelector.NUM_OPTION_BUTTONS;
    }
    return offset;
  }

  /**
   * Sets the text on the button referenced by button id (i.e. Simulation1, Simulation2 or
   * Simulation3 to the choice at index in choices or "" if that index is out of bounds
   *
   * @param index the index of the option in the list of choices
   */
  public void setOptionText(List<String> choices, Button relevantButton, int index) {
    try {
      relevantButton.setText(choices.get(index));
    } catch (IndexOutOfBoundsException ioobe) {
      relevantButton.setText("");
    }
  }

  /**
   * Sets the buffer text in this OptionsSelector to the text on the top option button
   */
  public void chooseTopChoice() {
    bufferText = ((Button) lookup("#" + OptionsSelector.OPTION1)).getText();
    dispatchEvent();
  }

  /**
   * Sets the buffer text in this OptionsSelector to the text on the middle option button
   */
  public void chooseMiddleChoice() {
    bufferText = ((Button) lookup("#" + OptionsSelector.OPTION2)).getText();
    dispatchEvent();
  }

  /**
   * Sets the buffer text in this OptionsSelector to the text on the bottom option button
   */
  public void chooseBottomChoice() {
    bufferText = ((Button) lookup("#" + OptionsSelector.OPTION3)).getText();
    dispatchEvent();
  }

  /**
   * Creates an event in order to let an ActionListener know that something relevant has happened
   * within the OptionSelector
   */
  public void dispatchEvent() {
    fireEvent(new Event(OptionsSelector.EVENT_TYPE));
  }

  /**
   * Updates the text on all buttons to reflect the text in the resource bundle
   */
  public void updateAllButtonBundles() {
    for (Node n : getChildren()) {
      if (n.getClass().getSimpleName().equals("Button")) {
        ((Button) n).setText(resourceBundle.getString(n.getId()));
      }
    }
  }

}
