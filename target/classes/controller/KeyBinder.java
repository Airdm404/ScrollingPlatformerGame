package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Pair;

/**
 * A visually interactive object which can be added to a Scene or Group object. It consists of a
 * list of Text labels that correspond to actions that the user can invoke with keystrokes as well
 * as Buttons that when pressed, allow the user to change the keystroke responsible for invoking the
 * action referenced by the corresponding Text label. Additionally, the class contains a Text object
 * responsible for giving the user advice on how to perform these rebindings as well as letting them
 * know if they've committed an error
 *
 * @author Alex Lu
 */
public class KeyBinder extends Group {

  private KeyInputter inputter;
  private static final double WIDTH = 800;
  private static final double HEIGHT = 800;
  private static final double START_VISIBLE_Y = HEIGHT / 8;
  private static final double END_VISIBLE_Y = 7 * HEIGHT / 8;
  private static final double VISIBLE_Y = END_VISIBLE_Y - START_VISIBLE_Y;
  private static final double COLUMN1X = WIDTH / 4;
  private static final double COLUMN2X = 3 * WIDTH / 4;
  private static final double CENTERX = WIDTH / 2;
  private static final double UPDATE_LABEL_Y = HEIGHT / 16;
  private static final String UPDATE_LABEL_TEXT = "UpdateLabelText";
  private static final String BAD_KEY_TEXT = "BadKeyText";
  private static final String WAITING_FOR_UPDATE_TEXT = "WaitingForUpdateText";
  private static final String UPDATE_ID = "UPDATE";
  private boolean isUpdatingKey;
  private String currentKeyBeingUpdated;
  private Text updateLabel;
  private ResourceBundle bundle;

  /**
   * Constructs a KeyBinder object
   *
   * @param resourceBundle the ResourceBundle that this object will use to determine what text
   *                       instructions it will display to the user
   */
  public KeyBinder(ResourceBundle resourceBundle) {
    bundle = resourceBundle;
    isUpdatingKey = false;
    currentKeyBeingUpdated = "";
    setFocusTraversable(true);
    setFocused(true);
    buildUpdateLabel();
  }

  /**
   * Builds the update label and inserts it into this root node
   */
  private void buildUpdateLabel() {
    updateLabel = new Text();
    updateLabel.setId(UPDATE_ID);
    getChildren().add(updateLabel);

    updateLabel.setLayoutY(UPDATE_LABEL_Y - updateLabel.getLayoutBounds().getHeight());
    refactorUpdateLabel(getValueFromBundle(WAITING_FOR_UPDATE_TEXT));
  }

  /**
   * Updates which action to key mappings are displayed as updatable by the user in order to reflect
   * the current configuration provided by KeyInputter in
   *
   * @param in the KeyInputter whose data will fill this KeyInputBinder
   */
  public void updateKeyInputScreen(KeyInputter in) {
    prepareUpdate(in);
    fillScreenWithKeyMap();
    refactorUpdateLabel(getValueFromBundle(WAITING_FOR_UPDATE_TEXT));
  }

  /**
   * Prepares the screen to be filled with the key map
   *
   * @param in the KeyInputter whose data will be changed by this KeyInputBinder
   */
  private void prepareUpdate(KeyInputter in) {
    inputter = in;
  }

  /**
   * Fills the screen with the key -> method map
   */
  private void fillScreenWithKeyMap() {
    List<Pair<String, String>> keyMethodPairs = new ArrayList<>();
    keyMethodPairs.addAll(inputter.getKeyMethodPairings());

    for (int index = 0; index < keyMethodPairs.size(); index += 1) {
      Pair<String, String> pair = keyMethodPairs.get(index);
      showKeyMethodPair(pair, 1.0 * index / keyMethodPairs.size());
    }
  }

  /**
   * Shows the key -> method pair on the screen - builds the Button representing the key and the
   * Text representing the method that the key corresponds to and when the button is pushed, calls
   * the enableUpdate method, passing in its id as a parameter to help identify that that button
   * called the method
   *
   * @param pair   the pair <String, String> of <key, method>
   * @param offset a double multiplier that helps to determine the y coordinate of the method and
   *               keyButton in the parent
   */
  private void showKeyMethodPair(Pair<String, String> pair, double offset) {
    Text method = new Text(pair.getValue());
    method.setLayoutX(COLUMN1X);
    method.setLayoutY(START_VISIBLE_Y + VISIBLE_Y * offset);

    Button keyButton = new Button(pair.getKey());
    keyButton.setId(pair.getKey());
    keyButton.setOnAction(e -> enableUpdate(keyButton.getId()));
    keyButton.setOnKeyPressed(e -> handleKey(e));
    keyButton.setLayoutX(COLUMN2X);
    keyButton.setLayoutY(START_VISIBLE_Y + VISIBLE_Y * offset);

    getChildren().addAll(method, keyButton);
  }

  /**
   * Enables updating of the key -> method pairing - called after the user clicks on a button to
   * update a method to a new key. Identifies which key is to be changed (currentKey) and updates
   * the updateLabel to say something new (i.e. update key 'B')
   *
   * @param currentKey the String representation of the current key being updated
   */
  private void enableUpdate(String currentKey) {
    isUpdatingKey = true;
    currentKeyBeingUpdated = currentKey;
    refactorUpdateLabel(getValueFromBundle(UPDATE_LABEL_TEXT) + currentKey);
  }

  /**
   * Handles the event where a key has been pressed - sets the isUpdatingKey variable to false,
   * clears the updateLabel (i.e. you can't see updating directions any more), and attempts to
   * update the key binding using user input in updateKeyBinding()
   *
   * @param event the KeyEvent that has occurred
   */
  void handleKey(KeyEvent event) {
    if (isUpdatingKey) {
      isUpdatingKey = false;
      refactorUpdateLabel(getValueFromBundle(WAITING_FOR_UPDATE_TEXT));
      updateKeyBinding(event.getCode().toString());
    }
  }

  /**
   * Tries to replace the key currentlyBeingUpdated with the key userKey - first check the inputter
   * to make sure that userKey is a viable replacement for currentlyBeingUpdated, then execute
   * replace
   *
   * @param userKey the key replacing currentlyBeingUpdated
   */
  private void updateKeyBinding(String userKey) {
    if (inputter.isValidKey(userKey)) {
      Button changedButton = (Button) lookup("#" + currentKeyBeingUpdated);
      changedButton.setText(userKey);
      changedButton.setId(userKey);
      inputter.swapKeyInput(currentKeyBeingUpdated, userKey);
    } else {
      refactorUpdateLabel(getValueFromBundle(BAD_KEY_TEXT));
    }
  }

  /**
   * Returns the value corresponding to key in the resouce bundle
   *
   * @param key the key in resourceBundle
   * @return the value in resourceBundle
   */
  public String getValueFromBundle(String key) {
    String value = bundle.getString(key);
    if (value != null) {
      return value;
    }
    return "";
  }

  /**
   * Refactors the text in updateLabel and recenters it
   *
   * @param newText the new text to be displayed to updateLabel
   */
  private void refactorUpdateLabel(String newText) {
    updateLabel.setText(newText);
    updateLabel.setLayoutX(CENTERX - updateLabel.getLayoutBounds().getWidth() / 2);

  }

  /**
   * Sets the bundle from which labels are translated to resourceBundle
   *
   * @param resourceBundle the new bundle
   */
  public void setBundle(ResourceBundle resourceBundle) {
    bundle = resourceBundle;
  }
}
