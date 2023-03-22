package api.controller;

import javafx.scene.control.Button;

import java.util.List;

/**
 * The ButtonBuilder interface
 */
public interface IButtonBuilder extends IBuilder {

    /**
     * Returns the list of buttons that this ButtonBuilder has found from parsing the file
     *
     * @return a list of buttons
     */
    List<Button> getFoundButtons();
}
