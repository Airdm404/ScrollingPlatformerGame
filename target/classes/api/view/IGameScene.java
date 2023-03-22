package api.view;

import controller.GameController;
import javafx.event.EventTarget;
import javafx.scene.Node;

import java.util.List;

/**
 * The GameScene interface
 */
public interface IGameScene extends EventTarget {

    /**
     * Sets the background Rectangle for the game
     */
    void makeBackground();

    /**
     * Makes the error text label that will appear at the top of the screen
     */
    void makeErrorText();

    /**
     * Updates the error text label that will appear at the top of the screen
     *
     * @param newText the new text to fill that label
     */
    void updateErrorText(String newText);

    /**
     * Hides the error text from view
     */
    void hideErrorText();

    /**
     * Returns the text in the error label
     *
     * @return the text of errorLabel
     */
    String getErrorText();

    /**
     * Sets the controller associated with this particular scene
     *
     * @param cont the controller to serve as the game scene's controller
     */
    void setGameController(GameController cont);

    /**
     * Adds buttons from a file to the controller
     *
     * @param file the file containing the buttons to be included
     */
    void addButtonsToControllerFromFile(String file);

    /**
     * Builds an option selector for the controller associated with the scene
     *
     * @param folder    the folder containing the list of options (i.e. "./ooga/resources/buttons")
     * @param extension the allowed extension for each option (i.e. include if ".jpeg")
     * @param method    the method to be called by the OptionsSelector
     */
    void buildOptionsSelectorFromFolderForController(String folder, String extension,
                                                     String method);

    /**
     * Builds an OptionsSelector object from a list
     *
     * @param choices the list of choices from which the user can select
     * @param method  the String representation of the method to be called
     */
    void buildOptionsSelectorFromListForController(List<String> choices, String method);

    /**
     * Adds a node element to the root node of the GameScene (i.e. button, controller, etc.)
     *
     * @param toBeAdded the Node to be inserted
     */
    void addElementToRoot(Node toBeAdded);

    /**
     * Returns the controller associated with this GameScene
     *
     * @return controller
     */
    GameController getGameController();

    /**
     * Removes a node element from the root node of the GameScene (i.e. button, controller, etc.)
     *
     * @param toBeRemoved the Node to be removed
     */
    void removeElementFromRoot(Node toBeRemoved);

    /**
     * Looks up an element in the GameScene and returns it if found, otherwise throws a
     * NullPointerException
     *
     * @param id the id to be looked up
     * @return the node if it exists in the GameScene
     */
    Node lookupElementInRoot(String id);

    /**
     * Updates the resource bundle displaying text for each scene
     *
     * @param name the name of the resource bundle
     */
    void updateResources(String name);

    /**
     * Updates the stylesheet of this GameScene
     *
     * @param name the name of the new stylesheet
     */
    void updateStylesheet(String name);

    /**
     * Returns the scene id
     *
     * @return sceneId
     */
    String getSceneId();

    /**
     * Returns the value corresponding to key in the resouce bundle
     *
     * @param key the key in resourceBundle
     * @return the value in resourceBundle
     */
    String getValueFromBundle(String key);
}
