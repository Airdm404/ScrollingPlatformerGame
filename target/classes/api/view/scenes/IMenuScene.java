package api.view.scenes;

import javafx.event.EventTarget;
import api.view.IGameScene;

/**
 * The MenuScene interface
 */
public interface IMenuScene extends EventTarget, IGameScene {

    /**
     * Adds images to the home screen
     */
    void addImagesToHomeScreen();
}
