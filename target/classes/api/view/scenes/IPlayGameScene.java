package api.view.scenes;

import javafx.event.EventTarget;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.Level;
import api.view.IGameScene;

/**
 * The PlayGameScene interface
 */
public interface IPlayGameScene extends EventTarget, IGameScene {

    /**
     * Updates and recenters the score text
     *
     * @param update the new text to be displayed
     */
    void updateScoreText(String update);

    /**
     * Saves the game
     */
    void launchSave(Level level);

    /**
     * Handles the event that a key was pressed in the textfield
     *
     * @param event the key event that has occurred
     */
    void handleTextFieldPress(KeyEvent event);

    /**
     * Attempts to save the score
     *
     * @param key the key that was pressed
     */
    void attemptScoreSave(KeyEvent key);

    /**
     * Finishing saving the score
     */
    void inputScore(String path, Level level);
}
