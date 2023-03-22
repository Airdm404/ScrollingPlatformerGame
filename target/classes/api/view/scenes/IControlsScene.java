package api.view.scenes;

import javafx.event.EventTarget;
import api.view.IGameScene;

/**
 * The ControlsScene interface
 */
public interface IControlsScene extends EventTarget, IGameScene {

    /**
     * Adds a key binder to this scene
     */
    void addKeyBinders();

    /**
     * Updates the resource bundle displaying text for each scene
     *
     * @param name the name of the resource bundle
     */
    @Override
    void updateResources(String name);
}
