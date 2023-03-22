package api.controller;

import controller.KeyInputterMissingMethodException;
import javafx.util.Pair;
import model.configuration.InvalidFileException;

import java.util.List;

/**
 * The KeyInputter interface
 */
public interface IKeyInputter {

    /**
     * Switches the key inputs to those specified by the .txt file specified by path
     *
     * @param path the filepath of the new .txt file
     */
    void loadKeyInputsFromFile(String path) throws InvalidFileException;

    /**
     * Swaps replacementKey for currentKey in the key -> method map. (i.e. if currentKey -> down and
     * replacementKey -> null, then afterwards, replacementKey -> down and currentKey -> null
     *
     * @param currentKey     the key currently associated with the method
     * @param replacementKey the key that you want to replace currentKey as being associated with that
     *                       method
     */
    void swapKeyInput(String currentKey, String replacementKey);

    /**
     * Handles the event that a key has been input - checks to make sure there is a method to call on
     * that key press and if so calls keyPressed
     *
     * @param press the String representation of the key that has been pressed
     */
    void keyPressed(String press) throws KeyInputterMissingMethodException;

    /**
     * Handles the event that a key has been released - checks to make sure there is a method to call
     * on that key press and if so calls keyPressed
     *
     * @param press the String representation of the key that has been pressed
     */
    void keyReleased(String press) throws KeyInputterMissingMethodException;

    /**
     * Handles the event where a key has been pressed (invariant - we know that key corresponds to a
     * method) and then invokes the method stored in the map
     *
     * @param methodPath the String representation of the method to be called
     */
    void invokeMethod(String methodPath) throws KeyInputterMissingMethodException;

    /**
     * Returns a mapping of keys to methods (defensively create rather than return existing map
     *
     * @return a mapping of String keys to String methods
     */
    List<Pair<String, String>> getKeyMethodPairings();

    /**
     * Checks to make sure that a key is valid
     *
     * @param key the String key
     * @return a boolean revealing whether or not the key is valid
     */
    boolean isValidKey(String key);

    /**
     * For testing - return the String representation of the last method to occur out of a key press
     *
     * @return the String representation of the last method to be called
     */
    String getLastPush();
}
