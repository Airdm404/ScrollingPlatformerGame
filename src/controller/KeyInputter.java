package controller;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import api.controller.IKeyInputter;
import api.controller.IKeyInputterMethodCaller;
import javafx.util.Pair;
import api.model.IGameModel;
import model.configuration.InvalidFileException;
import model.configuration.ModelExceptionReason;

/**
 * This class' job is to take in Strings that correspond to key presses (i.e. a String of "A" means
 * that we're telling KeyInputter that a key A was pressed) and to translate those Strings into
 * method calls, which KeyInputMethodCaller will then call on a GameModel object
 *
 * @author Alex Lu
 */
public class KeyInputter implements IKeyInputter {

  private final IKeyInputterMethodCaller methodCaller;
  private final Map<String, String> keyToMethodMap;
  private String lastMethodFromKeyPress;
  private static final String FILEPATH_START = "resources/keyinputs/";
  private static final String KEY_METHOD_ERROR = "KEY_METHOD_ERROR";
  private final String[] bannedKeys = {"ENTER", "ESC", "TAB"};

  /**
   * @param model the GameModel to whom we will pass inputs captured from the keyboard
   * @throws InvalidFileException ife
   */
  public KeyInputter(IGameModel model) throws InvalidFileException {
    methodCaller = new KeyInputterMethodCaller(model);
    lastMethodFromKeyPress = "";
    keyToMethodMap = new HashMap<>();

    loadKeyInputsFromFile(model.getKeyInputsPath());
  }

  /**
   * Switches the key inputs to those specified by the .txt file specified by path
   *
   * @param path the filepath of the new .txt file
   */
  @Override
  public void loadKeyInputsFromFile(String path) throws InvalidFileException {
    try {
      Properties properties = new Properties();
      InputStream stream = getClass().getClassLoader().getResourceAsStream(FILEPATH_START +
          path);
      properties.load(stream);
      Map<String, String> loadedMap = new TreeMap(properties);

      keyToMethodMap.clear();
      loadedMap.keySet().forEach(key -> keyToMethodMap.put(key, loadedMap.get(key)));

    } catch (Exception e) {
      throw new InvalidFileException(ModelExceptionReason.FILE_NOT_FOUND, "File not found");
    }
  }

  /**
   * Swaps replacementKey for currentKey in the key -> method map. (i.e. if currentKey -> down and
   * replacementKey -> null, then afterwards, replacementKey -> down and currentKey -> null
   *
   * @param currentKey     the key currently associated with the method
   * @param replacementKey the key that you want to replace currentKey as being associated with that
   *                       method
   */
  @Override
  public void swapKeyInput(String currentKey, String replacementKey) {
    if (keyToMethodMap.containsKey(currentKey) && !keyToMethodMap.containsKey(replacementKey)) {
      String correspondingMethod = keyToMethodMap.get(currentKey);
      keyToMethodMap.remove(currentKey);
      keyToMethodMap.put(replacementKey, correspondingMethod);
    }
  }

  /**
   * Handles the event that a key has been input - checks to make sure there is a method to call on
   * that key press and if so calls keyPressed
   *
   * @param press the String representation of the key that has been pressed
   */
  @Override
  public void keyPressed(String press) throws KeyInputterMissingMethodException {
    if (keyToMethodMap.containsKey(press)) {
      String methodPath = keyToMethodMap.get(press);
      invokeMethod(methodPath);
    }
  }

  /**
   * Handles the event that a key has been released - checks to make sure there is a method to call
   * on that key press and if so calls keyPressed
   *
   * @param press the String representation of the key that has been pressed
   */
  @Override
  public void keyReleased(String press) throws KeyInputterMissingMethodException {
    if (keyToMethodMap.containsKey(press)) {
      String methodPath = keyToMethodMap.get(press) + "Release";
      invokeMethod(methodPath);
    }
  }

  /**
   * Handles the event where a key has been pressed (invariant - we know that key corresponds to a
   * method) and then invokes the method stored in the map
   *
   * @param methodPath the String representation of the method to be called
   */
  @Override
  public void invokeMethod(String methodPath) throws KeyInputterMissingMethodException {
    try {
      Method method = methodCaller.getClass().getDeclaredMethod(methodPath);
      method.invoke(methodCaller);
      lastMethodFromKeyPress = methodPath;
    } catch (Exception e) {
      throw new KeyInputterMissingMethodException(KEY_METHOD_ERROR);
    }
  }

  /**
   * Returns a mapping of keys to methods (defensively create rather than return existing map
   *
   * @return a mapping of String keys to String methods
   */
  @Override
  public List<Pair<String, String>> getKeyMethodPairings() {
    List<Pair<String, String>> pairings = new ArrayList<>();
    keyToMethodMap.keySet().forEach(key -> pairings.add(new Pair<>(key, keyToMethodMap.get(key))));
    return pairings;
  }

  /**
   * Checks to make sure that a key is valid
   *
   * @param key the String key
   * @return a boolean revealing whether or not the key is valid
   */
  @Override
  public boolean isValidKey(String key) {
    List<String> blockedKeysList = Arrays.asList(bannedKeys);
    return !keyToMethodMap.containsKey(key) && !blockedKeysList.contains(key);
  }

  /**
   * For testing - return the String representation of the last method to occur out of a key press
   *
   * @return the String representation of the last method to be called
   */
  @Override
  public String getLastPush() {
    String tempPush = lastMethodFromKeyPress;
    lastMethodFromKeyPress = "";
    return tempPush;
  }
}
