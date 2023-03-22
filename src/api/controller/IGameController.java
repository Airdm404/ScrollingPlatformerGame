package api.controller;

import controller.BuilderInstantiationException;
import javafx.css.Styleable;
import javafx.event.EventTarget;
import javafx.scene.input.KeyEvent;

import java.util.List;

/**
 * The GameController interface
 */
public interface IGameController extends EventTarget, Styleable, IButtonPushHandler {

    /**
     * Adds an OptionsSelector from a folder
     *
     * @param folder    the folder containing the list of options (i.e. "./ooga/resources/buttons")
     * @param extension the allowed extension for each option (i.e. include if ".jpeg")
     * @param method    the method to be called by the OptionsSelector
     */
    void addOptionsSelectorFromFolder(String folder, String extension, String method)
        throws BuilderInstantiationException;

    /**
     * Builds and inserts an OptionsSelector into the children group
     *
     * @param choices the list of options to select from
     * @param method  the String representation of the method to call when one such option is
     *                selected
     * @throws BuilderInstantiationException bie
     */
    void buildOptionsSelector(List<String> choices, String method)
        throws BuilderInstantiationException;

    /**
     * Adds a set of buttons as specified by file to the controller - when they are pushed they will
     * execute handlePush(String methodName) which will use reflection to execute the method called
     * methodName on the view as specified by "view"
     *
     * @param file the filepath of the button file
     */
    void addButtonsFromFile(String file) throws BuilderInstantiationException;

    /**
     * Handles the event where a Button as built by ButtonBuilder has been pushed. When this happens
     * that button calls handlePush with the String type which is a String representation of a method,
     * i.e. back() which will then be called on "view" thus running a method in the view class using
     * reflection
     *
     * @param methodName a String representation of the method to be called on the view class
     */
    @Override
    void handlePush(String methodName);

    /**
     * Handles the event that a key has been pressed
     *
     * @param event the KeyEvent that has happened
     */
    void handleKeyPress(KeyEvent event);

    /**
     * Handles the event that a key has been released
     *
     * @param event the KeyEvent that has happened
     */
    void handleKeyRelease(KeyEvent event);

    /**
     * Updates the ResourceBundle for the GameController
     *
     * @param name the String name of the ResourceBundle (i.e. "English" for English.properties
     */
    void updateResources(String name);

    /**
     * Returns the elements in the buffer, having been defensively copied into bufferHolder
     *
     * @return bufferHolder
     */
    List<String> getBuffer();
}
