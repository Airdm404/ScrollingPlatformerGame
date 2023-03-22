package api.controller;

import javafx.css.Styleable;
import javafx.event.EventTarget;

/**
 * The OptionsSelector interface
 */
public interface IOptionsSelector extends EventTarget, Styleable, IButtonPushHandler {

    /**
     * Is responsible for handling the situation where the user pushes a button - directs the query to
     * a different method depending on which button was pushed
     *
     * @param type the method name to be called which was pushed
     */
    void handlePush(String type);

    /**
     * Regenerates the text on the buttons that select the simulation so that they reflect the current
     * options that the user is choosing from
     */
    void regenerateOptions();

    /**
     * Removes an option from the option selector
     *
     * @param option the option to be removed
     */
    void removeOption(String option);

    /**
     * Adds an option to the option selector
     *
     * @param option the option to be removed
     */
    void addOption(String option);

    /**
     * Returns the relevant text in the buffer - this will let the user know what this OptionsSelector
     * has currently selected. Then, set the buffer text to "" because it's already been returned
     *
     * @return bufferText
     */
    String getTextInBuffer();

    /**
     * Updates the resource bundle associated with this particular OptionsSelector and then sets the
     * text accordingly
     *
     * @param path the String path leading to the properties file
     */
    void updateBundle(String path);
}
